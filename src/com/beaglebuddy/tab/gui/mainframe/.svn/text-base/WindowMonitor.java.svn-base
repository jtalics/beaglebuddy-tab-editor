/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.help.HelpBroker;
import javax.swing.JOptionPane;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import com.beaglebuddy.tab.gui.action.menu.song.SongHasBeenEditedAction;



/**
 * This class shuts down the main frame window and saves various settings to the preferences.
 */
public class WindowMonitor extends WindowAdapter
{
   /**
    * this method is called when the user closes the application, either by clicking on the close button or by selecting "Exit" from the "Song" menu.
    * <br/><br/>
    * @param event  window event causing the application to shut down.
    */
   @Override
   public void windowClosing(WindowEvent event)
   {
      MainFrame frame = (MainFrame)event.getWindow();

      // determine if the user was in the middle of working on song, and if so, give them the chance to save their work first
      SongHasBeenEditedAction action = new SongHasBeenEditedAction(frame);
      // if the user decided not to exit the beaglebuddy tab editor after all, then return to the application
      if (action.actionPerformed() == JOptionPane.CANCEL_OPTION)
         return;

      // save the frame's current position, size, and state
      try
      {
         Preferences.setInt(Preferences.key_window_location_x     , frame.getX            ());
         Preferences.setInt(Preferences.key_window_location_y     , frame.getY            ());
         Preferences.setInt(Preferences.key_window_location_width , frame.getWidth        ());
         Preferences.setInt(Preferences.key_window_location_height, frame.getHeight       ());
         Preferences.setInt(Preferences.key_window_state          , frame.getExtendedState());
      }
      catch (RuntimeException ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.getString("gui.error.preferences.app_window"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }

      // save the context sensitive online help system's current position and size
      try
      {
         HelpBroker helpBroker = frame.getHelpBroker();

         Preferences.setInt(Preferences.key_help_location_x     , (int)helpBroker.getLocation().getX     ());
         Preferences.setInt(Preferences.key_help_location_y     , (int)helpBroker.getLocation().getY     ());
         Preferences.setInt(Preferences.key_help_location_width , (int)helpBroker.getSize    ().getWidth ());
         Preferences.setInt(Preferences.key_help_location_height, (int)helpBroker.getSize    ().getHeight());
      }
      catch (RuntimeException ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.getString("gui.error.preferences.help_window"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }

      // save the most recently used file list
      try
      {
         ArrayList<String> recentFiles = frame.getRecentFiles();

         Preferences.setInt(Preferences.key_most_recently_used_files_num, recentFiles.size());
         for(int i=0; i<recentFiles.size(); ++i)
         {
            String file = recentFiles.get(i);
            Preferences.setString(Preferences.key_most_recently_used_files_file + i, file);
         }
      }
      catch (RuntimeException ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.getString("gui.error.preferences.mruf"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }

      // save the directory the user was in the last time they used a file open, save, or save as dialog chooser.
      if (frame.getLastUsedDirectory() != null)
      {
         try
         {
            Preferences.setString(Preferences.key_last_used_directory, frame.getLastUsedDirectory());
         }
         catch (RuntimeException ex)
         {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, ResourceBundle.getString("gui.error.preferences.lud"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         }
      }

      frame.setVisible(false);
      frame.dispose();
      System.exit(0);
   }
}
