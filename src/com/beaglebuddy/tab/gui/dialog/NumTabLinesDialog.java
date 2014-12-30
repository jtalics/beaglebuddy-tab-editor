/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;




/**
 * This class is a dialog box which allows a user to specify how many lines the tab staff should have.
 */
public class NumTabLinesDialog extends BaseDialog
{
   // data members
   private int numTabLines;
   private int minNumTabLines;
   private int maxNumTabLines;




   /**
    * constructor.
    * <br/><br/>
    * @param frame            the main application frame.
    * @param minNumTabLines   the minimum number of lines the tab staff should have.
    * @param maxNumTabLines   the maximum number of lines the tab staff should have.
    */
   public NumTabLinesDialog(MainFrame frame, int minNumTabLines, int maxNumTabLines)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.num_tab_lines");

      // save the range of allowable number of lines in the tab staff
      this.numTabLines    = minNumTabLines;
      this.minNumTabLines = minNumTabLines;
      this.maxNumTabLines = maxNumTabLines;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.num_tab_lines.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      // create the midi device label
      JLabel label = new JLabel("num lines in tab staff");

      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(label)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(label)
            .addContainerGap())
      );

      // invoke the layout manager
      pack();
  }

   /**
    * @return the number of lines the user wants in the tab staff.
    */
   public int getNumTabLines()
   {
      return numTabLines;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // todo: add code here
   }
}
