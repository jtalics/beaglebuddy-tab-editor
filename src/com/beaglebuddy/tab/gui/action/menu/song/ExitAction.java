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
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;




/**
 * called when the user selects the <i>Exit</i> menu item from the <i>Song</i> menu.
 */
public class ExitAction extends SongHasBeenEditedAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public ExitAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * saves any changes the user may have made to the current song.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // see if the user is currently editing a song, and if so, ask them to save it first.
      if (super.actionPerformed() != JOptionPane.CANCEL_OPTION)
         frame.exit();
   }
}
