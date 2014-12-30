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
import com.beaglebuddy.tab.gui.mainframe.FileFilter;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;




/**
 * called when the user selects the <i>Save As</i> menu item from the <i>Song</i> menu.
 */
public class SaveAsAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public SaveAsAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * invoked by the swing framework, this method presents the user with a file chooser dialog box so that he can choose a different filename for the song.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      actionPerformed();
   }

   /**
    * presents the user with a file chooser dialog box so that he can choose a different filename for the song.
    * <br/><br/>
    * @return whether the user saved the file or canceled the action.
    */
   public int actionPerformed()
   {
      JFileChooser fileChooser = new JFileChooser(frame.getLastUsedDirectory());

      fileChooser.setDialogTitle(ResourceBundle.getString("gui.text.file_chooser.title.save_as"));
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.addChoosableFileFilter(new FileFilter("bbt", ResourceBundle.getString("gui.text.file_chooser.bbt_description")));
      int option = fileChooser.showSaveDialog(frame);

      switch (option)
      {
         case JFileChooser.APPROVE_OPTION:
              option = JOptionPane.YES_OPTION;
              File file = fileChooser.getSelectedFile();
              String filename = (file == null ? null : file.getAbsolutePath());
              if (filename != null)
              {
                 try
                 {
                    frame.getSong().saveAs(filename);
                    frame.setFilename(filename);
                    frame.setSongHasBeenEdited(false);
                    frame.addRecentFile(filename);
                    frame.setLastUsedDirectory(filename.substring(0, filename.lastIndexOf(file.getName())));
                 }
                 catch (FileWriteException ex)
                 {
                    JOptionPane.showMessageDialog(frame, ResourceBundle.format("gui.error.song.unable_to_save", filename), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
                    option = JOptionPane.CANCEL_OPTION;
                 }
              }
         break;
         case JFileChooser.CANCEL_OPTION:
              option = JOptionPane.CANCEL_OPTION;
         break;
      }
      return option;
   }
}
