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
 * called when the user selects the <i>Insert</i> menu item from the <i>Edit</i> menu.
 */
public class InsertAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public InsertAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * inserts the new content into the song.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      super.actionPerformed(event);
   }
}
