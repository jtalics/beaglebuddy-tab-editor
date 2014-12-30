/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.song;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.PrintOptionsDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.mainframe.PrintOptions;
import java.awt.event.ActionEvent;




/**
 * called when the user selects the <i>Print Options</i> menu item from the <i>Song</i> menu.
 */
public class PrintOptionsAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public PrintOptionsAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * sends the current song to the printer.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the song's current information
      PrintOptions printOptions = frame.getPrintOptions();

      // show the dialog and let the user select which things he wants printed.
      PrintOptionsDialog dialog = new PrintOptionsDialog(frame, printOptions);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's print choices
      if (dialog.wasOKed())
         frame.setPrintOptions(dialog.getPrintOptions());
   }
}
