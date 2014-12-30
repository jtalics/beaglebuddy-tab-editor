/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.toolbar.midi;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;





/**
 * called when the user selects one of the <i>play</i> (from the current location or from the beginning) icons from the <i>midi</i> toolbar.
 */
public class PlayAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public PlayAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * plays the song on the selected midi output device starting at the specified location.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // determine if the user wants to play the song starting at the current location or from the beginning.
      Location location = (event.getActionCommand().equals("play_from_beginning") ? new Location() : frame.getCurrentLocation());

      try
      {
         frame.getSong().play(frame.getMidiDevice(), frame, location);

         // update the toolbar states
         JToolBar toolbar = frame.getToolbar(MainFrame.TOOLBAR_MIDI_NAME);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FOM_BEGINNING).setEnabled(false);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FROM_CURRENT ).setEnabled(false);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PAUSE             ).setEnabled(true );
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_STOP              ).setEnabled(true );
         frame.validate();
      }
      catch (InvalidMidiDataException ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.format("error.midi.unable_to_play_song", frame.getMidiDevice().getDeviceInfo().getName(), ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
      catch (MidiUnavailableException ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.format("error.midi.unable_to_play_song", frame.getMidiDevice().getDeviceInfo().getName(), ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
   }
}
