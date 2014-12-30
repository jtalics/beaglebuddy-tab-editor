/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.song;

import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import javax.swing.JOptionPane;




/**
 * base class which checks if the current song has been edited, and if so, prompts the user to save it.
 */
public class SongHasBeenEditedAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public SongHasBeenEditedAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * determine if the current song has been edited, and if so, prompt the user to save it.
    * <br/><br/>
    * @return the users choice (yes, no, or cancel) to save the current song.
    */
   public int actionPerformed()
   {
      int option = JOptionPane.YES_OPTION;

      // determine if the user was in the middle of working on a song
      if (frame.songHasBeenEdited())
      {
         // ask the user to save the current song first
         option = JOptionPane.showConfirmDialog(frame, ResourceBundle.getString("gui.text.save_changes_dialog.text"), ResourceBundle.getString("gui.text.save_changes_dialog.title"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
         switch (option)
         {
            case JOptionPane.CANCEL_OPTION:
            break;
            case JOptionPane.NO_OPTION:
                 frame.setSongHasBeenEdited(false);
            break;
            case JOptionPane.YES_OPTION:
                 if (frame.getFilename() == null || frame.getFilename().trim().length() == 0)
                 {
                    SaveAsAction saveAsAction = new SaveAsAction(frame);
                    option = saveAsAction.actionPerformed();
                 }
                 else
                 {
                    SaveAction saveAction = new SaveAction(frame);
                    option = saveAction.actionPerformed();
                 }
                 if (option != JOptionPane.CANCEL_OPTION)
                    frame.setSongHasBeenEdited(false);
            break;
         }
      }
      return option;
   }
}
