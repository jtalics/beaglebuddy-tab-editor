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
import com.beaglebuddy.tab.gui.dialog.instrument.assignment.InstrumentAssignDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.instrument.InstrumentIn;
import com.beaglebuddy.tab.model.song.Song;
import java.awt.event.ActionEvent;
import java.util.ArrayList;





/**
 * called when the user selects the <i>Assign Instrument</i> menu item from the <i>Instrument</i> menu.
 */
public class InstrumentAssignAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public InstrumentAssignAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays a dialog to let the user assign instruments to the current staff in the song.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the instrument in for the selected staff at the current location
      Song                    song                = frame.getSong();
      Location                location            = frame.getCurrentLocation();
      Section                 section             = song.getScore().getSections().get(location.getSection());
      Barline                 barline             = section.getBarlines().get(location.getBarline());
      byte                    staff               = location.getStaff();
      ArrayList<InstrumentIn> instrumentIns       = barline.getInstrumentIns();
      InstrumentIn            instrumentIn        = null;
      InstrumentIn            currentInstrumentIn = null;


      // if there does not already exist at least one instrument in for the given staff, then create a blank new one
      for(int i=0; i<instrumentIns.size() && instrumentIn == null ; ++i)
      {
         if (instrumentIns.get(i).getStaff() == staff)
            instrumentIn = instrumentIns.get(i);
      }
      if (instrumentIn == null)
         instrumentIn = new InstrumentIn();
      else
         currentInstrumentIn = instrumentIn;

      // show the dialog and let the user assign instrument(s) to the current staff
      InstrumentAssignDialog dialog = new InstrumentAssignDialog(frame, staff, song.getScore().getInstruments(), song.getActiveInstruments(frame.getCurrentLocation()), new InstrumentIn(instrumentIn));
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         instrumentIn = dialog.getInstrumentIn();

         // if the user created a new instrument in, then add it to the barline
         if (currentInstrumentIn == null)
         {
            barline.getInstrumentIns().add(instrumentIn);
            frame.setSongHasBeenEdited(true);
         }
         // if the user changed an existing instrument in, then update it in the barline
         else if (!currentInstrumentIn.equals(instrumentIn))
         {
            currentInstrumentIn = instrumentIn;
            frame.setSongHasBeenEdited(true);
         }
         // otherwise, the user didn't make any changes, so there's nothing to do
      }
   }
}
