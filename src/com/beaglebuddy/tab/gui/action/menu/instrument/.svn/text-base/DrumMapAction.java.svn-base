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
import com.beaglebuddy.tab.gui.dialog.instrument.drum_map.DrumMapDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.instrument.DrumMap;
import com.beaglebuddy.tab.model.instrument.Drums;
import com.beaglebuddy.tab.model.instrument.Instrument;
import java.awt.event.ActionEvent;
import java.util.ArrayList;




/**
 * called when the user selects the <i>Drum Map</i> menu item from the <i>Instrument</i> menu.
 */
public class DrumMapAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public DrumMapAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays the drum map dialog box and lets the user create a map between drum sounds and drum notes.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the drum map fpr the current song
      ArrayList<Instrument> instruments = frame.getSong().getScore().getInstruments();
      Drums                 drums       = null;
      DrumMap               drumMap     = null;

      for(Instrument instrument : instruments)
         if (instrument instanceof Drums)
         {
            drums = (Drums)instrument;
            drumMap = drums.getDrumMap();
            break;
         }

      // show the dialog and let the user pick new repeat endings
      DrumMapDialog dialog = new DrumMapDialog(frame, drumMap);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to a drum map file.
      if (dialog.wasOKed())
      {
         if (drums != null)
            drums.setDrumMap(dialog.getDrumMap());
      }
   }
}
