/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action;

import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;




/**
 * This class is a base class for all actions.  It holds a reference to the main tab application frame.
 */
public class BaseAction implements ActionListener
{
   // data members
   protected MainFrame frame;

   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public BaseAction(MainFrame frame)
   {
      this.frame = frame;
   }

   /**
    * default implementation of the ActionListener interface which simply displays the menu choice selected.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   public void actionPerformed(ActionEvent event)
   {
      JOptionPane.showMessageDialog(null, "action selected: " + event.getActionCommand(), "Information", JOptionPane.INFORMATION_MESSAGE);
   }
}
