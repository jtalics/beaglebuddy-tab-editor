/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.help;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.AboutDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import java.awt.event.ActionEvent;




/**
 * called when the user selects the <i>About</i> menu item from the <i>Help</i> menu.
 */
public class HelpAction extends BaseAction
{
   // class members
   public static final String REPORT_BUG = "report_bug";
   public static final String HOME_PAGE  = "home_page";
   public static final String ABOUT      = "about";




   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public HelpAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays a dialog box containing information about the beaglebuddy tab editor.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String command = event.getActionCommand() ;

      AboutDialog dialog = new AboutDialog(frame);

      if (command.equals(REPORT_BUG))
      {
         // invoke the user's e-mail client
         dialog.invokeEmailClient();
      }
      else if (command.equals(HOME_PAGE))
      {
         // invoke the user's browser
         dialog.invokeBrowser2();
      }
      else if (command.equals(ABOUT))
      {
         dialog.setVisible(true);
      }
   }
}
