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
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import java.awt.event.ActionEvent;



/**
 * called when the user selects the <i>Score Format</i> menu item from the <i>Tools</i> menu.
 */
public class ScoreLayoutAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public ScoreLayoutAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * formats the song, setting the drawing positions of all the notes and music symbols as well as setting the spacing between them.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // format the song
      frame.getSong().layout();
      frame.setSongHasBeenEdited(true);

      // have the frame call its layout manager and redraw itself
      frame.validate();
   }
}
