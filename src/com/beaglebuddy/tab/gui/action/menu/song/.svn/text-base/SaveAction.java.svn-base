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
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;




/**
 * called when the user selects the <i>Save</i> menu item from the <i>Song</i> menu.
 */
public class SaveAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public SaveAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * saves any changes the user may have made to the current song.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      actionPerformed();
   }

   /**
    * saves any changes the user may have made to the current song.
    * <br/><br/>
    * @return whether the user saved the file or canceled the action.
    */
   public int actionPerformed()
   {
      int option = JOptionPane.YES_OPTION;

      // determine if the user was in the middle of working on song
      if (frame.songHasBeenEdited())
      {
         if (frame.getFilename() == null)
         {
            SaveAsAction saveAsAction = new SaveAsAction(frame);
            option = saveAsAction.actionPerformed();
         }
         else
         {
            try
            {
               FileOutputStream file = new FileOutputStream(frame.getFilename());
               frame.getSong().save(file);
               frame.setSongHasBeenEdited(false);
            }
            catch (Exception ex)
            {
               JOptionPane.showMessageDialog(frame, ResourceBundle.format("gui.error.song.unable_to_save", frame.getFilename()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
               option = JOptionPane.CANCEL_OPTION;
            }
         }
      }
      return option;
   }
}
