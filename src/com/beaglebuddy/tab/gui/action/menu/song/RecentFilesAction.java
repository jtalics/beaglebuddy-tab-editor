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
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;




/**
 * called when the user selects the <i>Recent Files</i> menu item from the <i>Song</i> menu.
 */
public class RecentFilesAction extends SongHasBeenEditedAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public RecentFilesAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * saves any changes the user may have made to the current song and then loads the selected song for editing.
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
         String filename = event.getActionCommand();
         // if its not the song we're currently editing, then open it.
         if (!filename.equals(frame.getFilename()))
         {
            try
            {
               frame.setSong(new Song(filename));
               frame.setFilename(filename);
               frame.setSongHasBeenEdited(false);
               frame.addRecentFile(filename);
               frame.setLastUsedDirectory(filename.substring(0, filename.lastIndexOf(File.separatorChar) + 1));
               frame.setTitle(ResourceBundle.getString("gui.text.beaglebuddy_tab_editor") + " - " + filename);
               // update the toolbar states
               JToolBar toolbar = null;

               toolbar = frame.getToolbar(MainFrame.TOOLBAR_MAIN_NAME);
               toolbar.getComponent(MainFrame.TOOLBAR_MAIN_SONG_SAVE ).setEnabled(true);
               toolbar.getComponent(MainFrame.TOOLBAR_MAIN_SONG_PRINT).setEnabled(true);

               toolbar = frame.getToolbar(MainFrame.TOOLBAR_MIDI_NAME);
               toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FOM_BEGINNING).setEnabled(true);
               toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FROM_CURRENT ).setEnabled(true);
               frame.validate();
            }
            catch (FileReadException ex)
            {
               JOptionPane.showMessageDialog(frame, ResourceBundle.format("gui.error.song.unable_to_open", filename, ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
               frame.removeRecentFile(filename);
            }
         }
      }
   }
}
