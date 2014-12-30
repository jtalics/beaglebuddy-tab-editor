/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.tools;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.preferences.MidiPreferencesDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JOptionPane;




/**
 * called when the user selects the <i>Midi</i> menu item from the <i>Tools</i> menu.
 */
public class MidiAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public MidiAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays a dialog box which allows a user to set their midi settings.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get a list of the midi synthesizers installed on this computer
      List<MidiDevice.Info> installedMidiDevices = Midi.getInstalledReceivers();

      // if no midi devices were found, then display an error messsage
      if (installedMidiDevices.size() == 0)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.getString("gui.error.preferences.midi.no_synthesizers_found"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         // create the dialog box and populate the controls with the list of installed midi devices
         MidiPreferencesDialog dialog = new MidiPreferencesDialog(frame);
         // display the dialog box to the user and let them choose which midi device they wish to play songs back on.
         dialog.setVisible(true);
         // if the user pressed ok, then save the user's changes to the song
         if (dialog.wasOKed())
         {
            try
            {
               MidiDevice.Info info       = dialog.getSelectedMidiDeviceInfo();
               MidiDevice      midiDevice = MidiSystem.getMidiDevice(info);
               frame.setMidiDevice(midiDevice);
               frame.saveMidiDeviceInfo(info);
            }
            catch (MidiUnavailableException ex)
            {
               JOptionPane.showMessageDialog(frame, ResourceBundle.getString("gui.error.preferences.midi.no_synthesizers_found"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
            }
         }
      }
   }
}
