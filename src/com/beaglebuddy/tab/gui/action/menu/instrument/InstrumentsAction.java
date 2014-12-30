/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.instrument;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.instrument.instruments.InstrumentsDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.instrument.InstrumentIn;
import java.awt.event.ActionEvent;
import java.util.ArrayList;





/**
 * called when the user selects the <i>Instruments</i> menu item from the <i>Instruments</i> menu.
 */
public class InstrumentsAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public InstrumentsAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays a dialog displaying all the instruments in the song and lets the user edit them.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the current list of instruments
      ArrayList<Instrument> instruments = frame.getSong().getScore().getInstruments();

      // show the dialog and let the user add, edit, and delete the current set of instruments.
      InstrumentsDialog dialog = new InstrumentsDialog(frame, instruments);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         // get the instruments after the user has edited them
         ArrayList<Instrument> newInstruments = dialog.getInstruments();

         // determine which instruments, if any, were deleted
         ArrayList<Instrument> oldInstruments     = instruments;
         ArrayList<Byte>       deletedInstruments = new ArrayList<Byte>();

         // did the user delete all the instruments?
         if (newInstruments.size() == 0)
         {
            // yes - then get a list of all the ids of the instruments that were deleted
            if (oldInstruments.size() != 0)
            {
               for(Instrument instrument : oldInstruments)
                  deletedInstruments.add(instrument.getId());
            }
         }
         // did the user delete some of the instruments?
         else
         {
            for(int i=0; i<newInstruments.size(); ++i)
            {
               // if he did, then there will be gaps in the sequence of instrument ids where an old instrument used to be
               if (newInstruments.get(i).getId() != (byte)i)
               {
                  for(byte id=(byte)i; id<newInstruments.get(i).getId(); ++id)
                     deletedInstruments.add(id);
               }
            }
         }

         // remove the instruments (and all references to the instruments) the user deleted from the song
         if (deletedInstruments.size() != 0)
         {
            // remove all references to the deleted instruments from all instrument ins
            ArrayList<Section> sections = frame.getSong().getScore().getSections();
            for(Section section : sections)
            {
               ArrayList<Barline> barlines = section.getBarlines();
               for(Barline barline : barlines)
               {
                  ArrayList<InstrumentIn> instrumentIns = barline.getInstrumentIns();
                  for(InstrumentIn instrumentIn : instrumentIns)
                  {
                     boolean[] staffInstruments       = instrumentIn.getStaffInstruments();
                     boolean[] rhythmSlashInstruments = instrumentIn.getRhythmSlashInstruments();

                     for(byte id : deletedInstruments)
                     {
                        staffInstruments      [id] = false;
                        rhythmSlashInstruments[id] = false;
                     }
                  }
               }
            }

            // remove the deleted instruments from the song
            for(Byte id : deletedInstruments)
               instruments.remove(id);
         }

         // set the ids of the instruments in sequential order
         for(byte i=(byte)0; i<newInstruments.size(); ++i)
            newInstruments.get(i).setId(i);

         // save the user's changes to the instruments back to the song
         frame.getSong().getScore().setInstruments(newInstruments);

         frame.setSongHasBeenEdited(true);
      }
   }
}
