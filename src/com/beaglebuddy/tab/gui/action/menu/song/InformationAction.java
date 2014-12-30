/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.song;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.SongInformationDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Information;
import java.awt.event.ActionEvent;




/**
 * called when the user selects the <i>Information</i> menu item from the <i>Song</i> menu.
 */
public class InformationAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public InformationAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays a dialog to the user in which he can edit the song information.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the song's current information
      Information currentInformation = frame.getSong().getInformation();

      // show the dialog and let the user enter new information about the song
      SongInformationDialog dialog = new SongInformationDialog(frame);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         Information newInformation = dialog.getInformation();
         frame.setSongHasBeenEdited(!currentInformation.equals(newInformation));
         frame.getSong().setInformation(newInformation);
      }
   }
}
