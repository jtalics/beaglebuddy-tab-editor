/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.model.ChordDictionary;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.instrument.DrumMapDictionary;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.help.UnsupportedOperationException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;





/**
 * This main frame base class manages the following aspects of the application:
 * <ol>
 *    <li>look and feel</li>
 *    <li>user's language preference</li>
 *    <li>the frame's initial size, location and state (minimized, normal, maximized)</li>
 *    <li>the help system</li>
 *    <li>the last directory the user accessed</li>
 *    <li>the chord    dictionary</li>
 *    <li>the drum map dictionary</li>
 *    <li>the tuning   dictionary</li>
 * </ol>
 */
@SuppressWarnings("serial")
public class MainFrameBase extends JFrame
{
   // data members
   private HelpBroker helpBroker;            // on-line context sensitive help broker
   private HelpSet    helpSet;               // on-line context sensitive help set
   private String     lastUsedDirectory;     // directory the user was in the last time they used a file open, save, or save as dialog chooser.



   /**
    * default constructor.
    */
   public MainFrameBase()
   {
      // set the application name in the title bar of the application window
      super(ResourceBundle.getString("gui.text.frame_title"));

      // set the gui look and feel to that of the native operating system.
      try
      {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception ex)
      {
         JOptionPane.showMessageDialog(this, ResourceBundle.getString("gui.error.look_and_feel.unable_to_set"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         System.exit(1);
      }

      // restore the user's language preference
      restoreLanguage();

      // load the directory the user was in the last time they used a file open, save, or save as dialog chooser.
      lastUsedDirectory = Preferences.getString(Preferences.key_last_used_directory);

       // restore the application window's previous location, size, and state
      restoreApplicationWindow();

      // set the class that will be called when the user tries to exit the application
      setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      addWindowListener(new WindowMonitor());

      // initialize the context sensitive help system
      setHelpSystem();

      // load the drum map dictionary
      try
      {
         DrumMapDictionary.load();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         JOptionPane.showMessageDialog(this, ResourceBundle.format("gui.error.drum_map.dictionary.unable_to_load", DrumMapDictionary.getFilename()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         System.exit(1);
      }

      // load the tuning dictionary
      try
      {
         TuningDictionary.load();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         JOptionPane.showMessageDialog(this, ResourceBundle.format("gui.error.tuning.dictionary.unable_to_load", TuningDictionary.getFilename()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         System.exit(1);
      }

      // load the chord dictionary
      try
      {
         ChordDictionary.load();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         JOptionPane.showMessageDialog(this, ResourceBundle.format("gui.error.tuning.dictionary.unable_to_load", ChordDictionary.getFilename()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         System.exit(1);
      }

      // set the icon displayed in the upper left hand corner of the application's title bar
      setIconImage(new ImageIcon(getClass().getResource(ResourceBundle.getString("gui.mainframe.icon"))).getImage());
   }

   /**
    * @return the context sensitive help broker.
    */
   public HelpBroker getHelpBroker()
   {
      return helpBroker;
   }

   /**
    * @return the context sensitive help set.
    */
   public HelpSet getHelpSet()
   {
      return helpSet;
   }

   /**
    * @return the directory the user was in the last time they used a file open, save, or save as dialog chooser.
    */
   public String getLastUsedDirectory()
   {
      return lastUsedDirectory;
   }

   /**
    * sets the directory the user was in the last time they used a file open, save, or save as dialog chooser.
    * <br/><br/>
    * @param lastUsedDirectory   the directory the user was in the last time they used a file open, save, or save as dialog chooser.
    */
   public void setLastUsedDirectory(String lastUsedDirectory)
   {
      this.lastUsedDirectory = lastUsedDirectory;
   }

   /**
    * initializes the context sensitive help system based on the user's language preference.
    */
   public void setHelpSystem()
   {
      // get the location of the jar file containing the context sensitive help files
      String helpJarFile = Preferences.getString(Preferences.key_help_helpset_jar_file);
      if (helpJarFile == null)
      {
         // get the current directory
         String directory = new File(".").getAbsolutePath();
         // remove the trailing "."
         // if this jvm is being run on a windows box, remove the drive letter as well
         directory = directory.substring(directory.charAt(1) == ':' ? 2 : 0, directory.length() - 1);
         // make sure the separator character is forward slashes regardless of the operating system
         directory = directory.replaceAll("\\\\", "/");
         // the application should have been launched from the /bin directory.   so replace /bin/ with /lib/, which is where the help jar files are located
         if (directory.endsWith("/bin/"))
            directory = directory.substring(0, directory.length() - 4) + "lib/";

         helpJarFile = "jar:file://" + directory + "beaglebuddy_tab_help.jar";
         Preferences.setString(Preferences.key_help_helpset_jar_file, helpJarFile);
      }

      // select the correct set of help files based on the user's language preference
      String language = ResourceBundle.getCurrentLanguage();
      helpJarFile = helpJarFile.replaceFirst("help.jar", "help." + language + ".jar");

      // set up the help system
      try
      {
         // see Creating Effective Java Help, page 97. for format used when help is in a jar file.
         // see also in the java help directory, the file doc/jhug.pdf, page 15
         URL url    = new URL(helpJarFile + "!/helpset.hs");
         helpSet    = new HelpSet(null, url);
         helpBroker = helpSet.createHelpBroker();
         helpBroker.enableHelpKey(getRootPane(), "intro", helpSet);   // enable function key F1
         helpBroker.setLocale(ResourceBundle.getCurrentLocale());

         // set the size of the help window and the location on the screen where it will appear
         restoreHelpWindow();
      }
      catch (MalformedURLException ex)
      {
         // this should never happen
         ex.printStackTrace();
      }
      catch (UnsupportedOperationException ex)
      {
         // this should never happen
         ex.printStackTrace();
      }
      catch (HelpSetException ex)
      {
         JOptionPane.showMessageDialog(null, ResourceBundle.format("gui.error.help.unable_to_find", helpJarFile), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         System.exit(1);
      }
   }

   /**
    * closes down the frame.
    */
   public void exit()
   {
      WindowEvent event = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
      processEvent(event);
      System.exit(0);
   }
   /**
    * This method restores the online help system's window's size and location so that the help system window comes up just as it was when the user ran the application the last time.
    */
   private void restoreHelpWindow()
   {
      helpBroker.setLocation(new Point    (Preferences.getInt(Preferences.key_help_location_x    , Preferences.default_value_help_location_x    ), Preferences.getInt(Preferences.key_help_location_y     , Preferences.default_value_help_location_y     )));
      helpBroker.setSize    (new Dimension(Preferences.getInt(Preferences.key_help_location_width, Preferences.default_value_help_location_width), Preferences.getInt(Preferences.key_help_location_height, Preferences.default_value_help_location_height)));
   }

   /**
    * restores a user's language preference.
    */
   private void restoreLanguage()
   {
      String language = Preferences.getString(Preferences.key_language, Preferences.default_value_language);

      try
      {
         ResourceBundle.switchLanguage(language);
      }
      catch (IllegalArgumentException ex)
      {
         ex.printStackTrace();
         ResourceBundle.switchLanguage("en");
      }
   }

   /**
    * This method restores the window's size, location and state (minimized, normal, maximized) so that the window comes up just as it was when the user ran the application the last time.
    */
   private void restoreApplicationWindow()
   {
      setLocation     (Preferences.getInt(Preferences.key_window_location_x    , Preferences.default_value_window_location_x    ), Preferences.getInt(Preferences.key_window_location_y     , Preferences.default_value_window_location_y     ));
      setSize         (Preferences.getInt(Preferences.key_window_location_width, Preferences.default_value_window_location_width), Preferences.getInt(Preferences.key_window_location_height, Preferences.default_value_window_location_height));
      setExtendedState(Preferences.getInt(Preferences.key_window_state         , Preferences.default_value_window_state));
      setResizable(true);
   }
}
