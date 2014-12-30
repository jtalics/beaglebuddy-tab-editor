/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.song;

import com.beaglebuddy.tab.gui.mainframe.FileFilter;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;




/**
 * called when the user selects the <i>Open</i> menu item from the <i>Song</i> menu.
 */
public class OpenAction extends SongHasBeenEditedAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public OpenAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * saves any changes the user may have made to the current song and then presents the user with a file deialog box to select a new song from a file on disk.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // see if the user is currently editing a song, and if so, ask them to save it first.
      int option = super.actionPerformed();

      if (option != JOptionPane.CANCEL_OPTION)
      {
         // let the user pick a new tab file to load
         JFileChooser fileChooser = new JFileChooser(frame.getLastUsedDirectory());

         fileChooser.setDialogTitle(ResourceBundle.getString("gui.text.file_chooser.title.open"));
         fileChooser.setAcceptAllFileFilterUsed(false);
         fileChooser.addChoosableFileFilter(new FileFilter("ptb", ResourceBundle.getString("gui.text.file_chooser.ptb_description")));
         fileChooser.addChoosableFileFilter(new FileFilter("bbt", ResourceBundle.getString("gui.text.file_chooser.bbt_description")));

         if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
         {
            File   file     = fileChooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            try
            {
               frame.setSong(new Song(filename));
               // if a power tab (.ptb) file is loaded, it is automatically converted to the beaglebuddy tab (.bbt) format, so change the extension.
               String newFilename = replaceExtension(filename);
               frame.setFilename(newFilename);
               frame.setSongHasBeenEdited(!newFilename.equals(filename));
               frame.addRecentFile(newFilename);
               frame.setLastUsedDirectory(filename.substring(0, filename.lastIndexOf(file.getName())));
               frame.setTitle(ResourceBundle.getString("gui.text.beaglebuddy_tab_editor") + " - " + newFilename);
               // update the toolbar states
               JToolBar toolbar = null;

               toolbar = frame.getToolbar(MainFrame.TOOLBAR_MAIN_NAME);
               toolbar.getComponent(MainFrame.TOOLBAR_MAIN_SONG_SAVE ).setEnabled(true);
               toolbar.getComponent(MainFrame.TOOLBAR_MAIN_SONG_PRINT).setEnabled(true);

               toolbar = frame.getToolbar(MainFrame.TOOLBAR_MIDI_NAME);
               toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FOM_BEGINNING).setEnabled(true);
               toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FROM_CURRENT ).setEnabled(true);

               // have the frame call the layout manager and redraw itself
               frame.validate();
            }
            catch (FileReadException ex)
            {
               JOptionPane.showMessageDialog(frame, ResourceBundle.format("gui.error.song.unable_to_open", filename, ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
            }
            catch (IllegalArgumentException ex)
            {
               JOptionPane.showMessageDialog(frame, ex.getMessage(), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
            }
         }
      }
   }

   /**
    * @return the filename with a .bbt extenstion.
    * <br/><br/>
    * @param filename  the name of the file whose extension will be set to bbt.
    */
   private String replaceExtension(String filename)
   {
      int i = filename.lastIndexOf('.');

      if (i <= 0 || i != filename.length() - 4)
         throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.file.extension", filename));
      String extension = filename.substring(i+1);
       // if the file is a power tab (.ptb) file, change the extension to bbt
      if (extension.equals("ptb"))
         filename = filename.substring(0, i+1) + "bbt";
      return filename;
   }
}
