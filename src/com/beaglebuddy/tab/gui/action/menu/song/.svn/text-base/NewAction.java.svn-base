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
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;




/**
 * called when the user selects the <i>New</i> menu item from the <i>Song</i> menu.
 */
public class NewAction extends SongHasBeenEditedAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public NewAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * saves any changes the user may have made to the current song and then creates a new song for editing.
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
         Song song = new Song();

         frame.setSong(song);
         frame.setFilename(null);
         frame.setSongHasBeenEdited(false);
         frame.setTitle(ResourceBundle.getString("gui.text.beaglebuddy_tab_editor"));
         // update the toolbar states
         JToolBar toolbar = null;

         toolbar = frame.getToolbar(MainFrame.TOOLBAR_MAIN_NAME);
         toolbar.getComponent(MainFrame.TOOLBAR_MAIN_SONG_SAVE ).setEnabled(true);
         toolbar.getComponent(MainFrame.TOOLBAR_MAIN_SONG_PRINT).setEnabled(true);

         toolbar = frame.getToolbar(MainFrame.TOOLBAR_MIDI_NAME);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FOM_BEGINNING).setEnabled(false);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FROM_CURRENT ).setEnabled(false);
         frame.validate();
      }
   }
}
