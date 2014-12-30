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
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.songpanel.SongPanel;

import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;



/**
 * called when the user selects the <i>PrintSetup</i> menu item from the <i>Song</i> menu.
 */
public class PrintSetupAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public PrintSetupAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * show a print setup dialog to the user.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      PrinterJob printJob = PrinterJob.getPrinterJob();
      SongPanel songPanel = this.frame.getSongWindowPanel();
      printJob.setPrintable(songPanel);
      printJob.setPageable (songPanel);
      PageFormat pf = songPanel.getPageFormat(0);
      pf = printJob.pageDialog(pf);
      songPanel.setPageFormat(pf);
   }
}
