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
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;




/**
 * called when the user selects the <i>pause</i> icon from the <i>midi</i> toolbar.
 */
public class PauseAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public PauseAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * pauses the playing of the song on the selected midi output device.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      super.actionPerformed(event);

      // update the toolbar states
      JToolBar toolbar = frame.getToolbar(MainFrame.TOOLBAR_MIDI_NAME);
      toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FOM_BEGINNING).setEnabled(true );
      toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FROM_CURRENT ).setEnabled(true );
      toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PAUSE             ).setEnabled(false);
      toolbar.getComponent(MainFrame.TOOLBAR_MIDI_STOP              ).setEnabled(true );
      frame.validate();
   }
}
