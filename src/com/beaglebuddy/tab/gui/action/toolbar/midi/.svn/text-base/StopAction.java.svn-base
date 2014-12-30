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
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;




/**
 * called when the user selects the <i>stop</i> icon from the <i>midi</i> toolbar.
 */
public class StopAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public StopAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * stops the playing of the currrent song on the selected output midi device.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      try
      {
         frame.getSong().stop();
         // update the toolbar states
         JToolBar toolbar = frame.getToolbar(MainFrame.TOOLBAR_MIDI_NAME);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FOM_BEGINNING).setEnabled(true );
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FROM_CURRENT ).setEnabled(true );
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PAUSE             ).setEnabled(false);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_STOP              ).setEnabled(false);
         frame.validate();
      }
      catch (Exception ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.format("error.midi.unable_to_play_song", frame.getMidiDevice().getDeviceInfo().getName(), ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
   }
}
