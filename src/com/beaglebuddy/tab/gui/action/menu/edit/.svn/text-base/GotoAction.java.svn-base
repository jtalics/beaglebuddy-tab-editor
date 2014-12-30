/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.edit;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.GotoMeasureDialog;
import com.beaglebuddy.tab.gui.dialog.GotoSectionDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.song.Song;
import java.awt.event.ActionEvent;




/**
 * called when the user selects the <i>Goto</i> menu item from the <i>Edit</i> menu.
 */
public class GotoAction extends BaseAction
{
   // class members
   public static final String MEASURE = "measure";
   public static final String SECTION = "section";




   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public GotoAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * moves the mouse pointer to the selected location.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String   command  = event.getActionCommand();
      Location location = frame.getCurrentLocation();
      Song     song     = frame.getSong();


      if (command.equals(MEASURE))
      {
         int numMeasuresInSong = song.getNumMeasures();

         // show the dialog and let the user choose a measure to go to
         GotoMeasureDialog dialog = new GotoMeasureDialog(frame, location.getMeasure()+1, numMeasuresInSong);
         dialog.setVisible(true);

         // if the user pressed ok, then go to the specified measure
         if (dialog.wasOKed())
         {
            int measure = dialog.getMeasure();

            // todo: set the location to the new measure and go there
         }
      }
      else if (command.equals(SECTION))
      {
         int numSectionsInSong = song.getNumSections();

         // show the dialog and let the user choose a section to go to
         GotoSectionDialog dialog = new GotoSectionDialog(frame, location.getSection()+1, numSectionsInSong);
         dialog.setVisible(true);

         // if the user pressed ok, then go to the specified section
         if (dialog.wasOKed())
         {
            int section = dialog.getSection();

            // todo: set the location to the new section and go there
         }
      }
   }
}
