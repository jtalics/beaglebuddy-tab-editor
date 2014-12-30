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
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JOptionPane;




/**
 * called when the user selects the <i>Print</i> menu item from the <i>Song</i> menu.
 */
public class PrintAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public PrintAction(MainFrame frame)
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
      PrinterJob printJob = PrinterJob.getPrinterJob();
      printJob.setCopies(1);
      SongPanel songPanel = this.frame.getSongWindowPanel();
      printJob.setPrintable(songPanel);
      printJob.setPageable(songPanel);
      try
      {
         if (printJob.printDialog())
              printJob.print();
      }
      catch (HeadlessException ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.format("gui.error.song.unable_to_print", frame.getSong().getInformation().getSongTitle(), ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
      catch(PrinterException ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.format("gui.error.song.unable_to_print", frame.getSong().getInformation().getSongTitle(), ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
   }
}
