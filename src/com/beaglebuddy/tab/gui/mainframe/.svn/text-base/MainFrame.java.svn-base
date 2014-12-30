/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.gui.songpanel.SongPanel;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 * This is the main frame application window.
 * In addition to launching the application, it lays out the frame in three regions:
 * <ol>
 *    <li>toolbar area at the top of the application frame where all the toolbars dock.</li>
 *    <li>main window in the center where the song is displayed.</li>
 *    <li>status bar at the bottom of the application frame.</li>
 * </ol>
 */
public class MainFrame extends MainFrameMenu
{
   private static final long serialVersionUID = 1L;

   // data members
   private SongPanel    songWindowPanel;     // main central window where the song is displayed.
   private JScrollPane  songWindowScroller;  // component which allows the user to scroll the main song window.
   private StatusBar    statusBar;           // status bar at the bottom of the application frame.
   private PrintOptions printOptions;        // what the user wants to print


   /**
    * default constructor.
    */
   public MainFrame()
   {
      // create the 3 regions within the main application frame
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

      // add the panels to the frame
      songWindowPanel    = new SongPanel(this.getSong());
      songWindowScroller = new JScrollPane(songWindowPanel);
      statusBar          = new StatusBar(getCurrentLocation());

      songWindowScroller.getVerticalScrollBar().setUnitIncrement(10*songWindowScroller.getVerticalScrollBar().getUnitIncrement());
      songWindowScroller.setFocusable(false);
      songWindowPanel.setBackground(Color.white);
      songWindowPanel.setFocusable(true);
      songWindowPanel.addKeyListener        (this);
      songWindowPanel.addMouseListener      (this);
      songWindowPanel.addMouseMotionListener(this);
//    songWindowPanel.addMouseWheelListener (this);
      addCursorListener(statusBar);

      contentPane.add(getToolBarPanel() , BorderLayout.NORTH );
      contentPane.add(songWindowScroller, BorderLayout.CENTER);
      contentPane.add(statusBar         , BorderLayout.SOUTH );

      setTitle(ResourceBundle.getString("gui.text.beaglebuddy_tab_editor"));

      // load the last song the user was working on
      String filename = getMostRecentFile();
      if (filename == null)
      {
         setSong(new Song());
         filename = "";
      }
      else
      {
         try
         {
            setSong(new Song(filename));
         }
         catch (FileReadException ex)
         {
            JOptionPane.showMessageDialog(this, ResourceBundle.format("gui.error.song.unable_to_open", filename, ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
            removeRecentFile(filename);
            setSong(new Song());
            filename = "";
         }
      }

      setFilename(filename);
      setSongHasBeenEdited(false);
      setTitle(ResourceBundle.getString("gui.text.beaglebuddy_tab_editor") + (filename.length() == 0 ? "" : " - ") + filename);

      // set the list of default things that will be printed whenever a user prints a song
      printOptions = new PrintOptions();

      // update the toolbar states
      JToolBar toolbar = null;

      toolbar = getToolbar(MainFrame.TOOLBAR_MAIN_NAME);
      toolbar.getComponent(MainFrame.TOOLBAR_MAIN_SONG_SAVE ).setEnabled(true);
      toolbar.getComponent(MainFrame.TOOLBAR_MAIN_SONG_PRINT).setEnabled(true);

      toolbar = getToolbar(MainFrame.TOOLBAR_MIDI_NAME);
      toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FOM_BEGINNING).setEnabled(true);
      toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FROM_CURRENT ).setEnabled(true);
   }

   @Override
   public SongPanel getSongWindowPanel()
   {
      return songWindowPanel;
   }

   @Override
   public JScrollPane getSongWindowScroller()
   {
      return songWindowScroller;
   }

   /**
    * @return the status bar at the bottom of the application frame.
    */
   public StatusBar getStatusBar()
   {
      return statusBar;
   }

   /**
    * @return the user's print options.
    */
   public PrintOptions getPrintOptions()
   {
      return printOptions;
   }

   /**
    * sets the user's print options.
    * <br/><br/>
    * @param printOptions   the user's print options.
    */
   public void setPrintOptions(PrintOptions printOptions)
   {
      this.printOptions = printOptions;
   }

   @Override
   public void setSong(Song song)
   {
      super.setSong(song);
      this.songWindowPanel.setSong(song);
      this.setLocation(new Location());
      this.getSongWindowScroller().getVerticalScrollBar().setValue(0);
      this.getSongWindowScroller().getHorizontalScrollBar().setValue(0);
   }

   /**
    * creates the gui and starts it running.
    * <br/><br/>
    * @param args  command line arguments.
    */
   public static void main(String[] args)
   {
      // Register the song panel for keyboard focus.
      final MainFrame frame = new MainFrame();
      frame.addWindowListener(
         new WindowAdapter()
         {
            @Override
            public void windowGainedFocus(WindowEvent e)
            {
                frame.getSongWindowPanel().requestFocusInWindow();
            }
         }
      );
      frame.pack();
      frame.getSongWindowPanel().requestFocusInWindow();
      frame.setVisible(true);
   }
}
