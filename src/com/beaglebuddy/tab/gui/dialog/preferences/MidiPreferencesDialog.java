/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.preferences;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.mainframe.Preferences;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.MidiDevice;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;



/**
 * This class is a dialog box which allows a user to select the midi device on which songs will be played.
 */
public class MidiPreferencesDialog extends BaseDialog
{
   // class members
   private static final int COMBO_BOX_SIZE = 190;          // width in pixels of the combo box

   // data members
   private MidiDevice.Info       selectedMidiDeviceInfo;   // information about the selected midi device
   private List<MidiDevice.Info> installedMidiDevices;     // list of all midi output devices found on the user's computer

   // dialog box controls
   private JComboBox             comboBoxMidiDevices;     // combo box to display the list of midi output devices




   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    */
   public MidiPreferencesDialog(MainFrame frame)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.preferences.midi");

      // get a list of all the installed midi devices which have a midi receiver
      List<MidiDevice.Info> midiDeviceInfos = Midi.getInstalledReceivers();
      // remove the "Microsoft Midi Mapper" and Sun's "Real Time Sequencer" if they are present
      this.installedMidiDevices = new ArrayList<MidiDevice.Info>();
      for(MidiDevice.Info midiDeviceInfo : midiDeviceInfos)
      {
         if (!(midiDeviceInfo.getName().equals("Microsoft MIDI Mapper") && midiDeviceInfo.getVendor().equals("Unknown vendor")) &&
             !(midiDeviceInfo.getName().equals("Real Time Sequencer"  ) && midiDeviceInfo.getVendor().equals("Sun Microsystems")))
            installedMidiDevices.add(midiDeviceInfo);
      }

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.preferences.midi.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
  public void addComponents()
   {
      // create the midi device label
      JLabel label = new JLabel(ResourceBundle.getString("dialog.preferences.midi.label.output_device") + " ");
      label.setHorizontalAlignment(JLabel.RIGHT);

      // create the midi device combo box
      String[] midiDevices = new String[installedMidiDevices.size()];
      for(int i=0; i<midiDevices.length; ++i)
         midiDevices[i] = installedMidiDevices.get(i).getName();
      comboBoxMidiDevices = new JComboBox(midiDevices);
      comboBoxMidiDevices.setToolTipText(ResourceBundle.getString("dialog.preferences.midi.tooltip.output_device"));

      // set the initially selected item
      String selectedMidiDevice = Preferences.getString(Preferences.key_midi_device_name);
      int    selectedIndex      = -1;
      for (int i=0; i<installedMidiDevices.size() && selectedIndex == -1; ++i)
        if (installedMidiDevices.get(i).getName().equals(selectedMidiDevice))
           selectedIndex = i;
      comboBoxMidiDevices.setSelectedIndex(selectedIndex == -1 ? 0 : selectedIndex);

      // set the layout manager
      JPanel       controlsPanel  = getControlsPanel();
      GroupLayout  groupLayout    = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(groupLayout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(label)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboBoxMidiDevices, GroupLayout.PREFERRED_SIZE, COMBO_BOX_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(comboBoxMidiDevices, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(label)))
      );

      // invoke the layout manager
      pack();
  }

   /**
    * @return the midi device information for the selected midi output device.
    */
   public MidiDevice.Info getSelectedMidiDeviceInfo()
   {
      return selectedMidiDeviceInfo;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
  public void ok()
   {
      // retrieve the selected midi synthesizer
      int index = comboBoxMidiDevices.getSelectedIndex();
      selectedMidiDeviceInfo = installedMidiDevices.get(index);
   }
}
