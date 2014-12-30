/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.edit;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import java.awt.event.ActionEvent;




/**
 * called when the user selects the <i>Copy</i> menu item from the <i>Edit</i> menu.
 */
public class CopyAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public CopyAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * copies the selected content to the clipboard.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      super.actionPerformed(event);
   }
}
