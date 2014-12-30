/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import javax.swing.GroupLayout;
import java.awt.event.ActionListener;
import javax.help.CSH;
import javax.help.HelpBroker;
import javax.swing.JButton;
import javax.swing.JPanel;




/**
 * This class is a panel with fixed size ok, cancel, and help buttons equally spaced in a single horizontal row.
 */
public class OkCancelHelpButtonPanel extends JPanel
{
   // data members
   private JButton okButton;
   private JButton cancelButton;
   private JButton helpButton;




   /**
    * constructor.
    * The buttons have their action command explicitly set so that the action listeners will not be affected by the language translations of the button text.
    * <br/><br/>
    * @param listener     action listener which will process the <i>ok</i> and <i>cancel</i> buttons.
    * @param helpBroker   the java help system manager.
    * @param helpId       the help topic id of the help file that will be displayed if the user presses the <i>Help</i> button.
    */
   public OkCancelHelpButtonPanel(ActionListener listener, HelpBroker helpBroker, String helpId)
   {
      // create the ok button
      okButton = new JButton(ResourceBundle.getString("dialog.button.ok.txt"));
      okButton.setActionCommand("ok");
      okButton.addActionListener(listener);

      // create the cancel button
      cancelButton = new JButton(ResourceBundle.getString("dialog.button.cancel.txt"));
      cancelButton.setActionCommand("cancel");
      cancelButton.addActionListener(listener);

      // create the help button
      helpButton = new JButton(ResourceBundle.getString("dialog.button.help.txt"));
      helpButton.setActionCommand("help");
      // see page 96 and 99 in creating effective java help
      CSH.setHelpIDString(helpButton, helpId);
      helpButton.addActionListener(new CSH.DisplayHelpFromSource(helpBroker));

      // set the layout manager
      GroupLayout groupLayout = new GroupLayout(this);
      setLayout(groupLayout);

      // automatically place gaps between the components
      groupLayout.setAutoCreateGaps         (true);
      groupLayout.setAutoCreateContainerGaps(true);

      // layout the components along the horizontal axis
      GroupLayout.SequentialGroup horizontalGroup = groupLayout.createSequentialGroup();
      horizontalGroup.addComponent(okButton);
      horizontalGroup.addComponent(cancelButton);
      horizontalGroup.addComponent(helpButton);

      // layout the components along the vertical axis
      GroupLayout.ParallelGroup verticalGroup = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
      verticalGroup.addComponent(okButton           , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
      verticalGroup.addComponent(cancelButton       , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
      verticalGroup.addComponent(helpButton         , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);

      // layout the components in the panel
      groupLayout.setHorizontalGroup(horizontalGroup);
      groupLayout.setVerticalGroup  (  verticalGroup);
   }

   /**
    * @return the cancel button.
    */
   public JButton getCancelButton()
   {
      return(cancelButton);
   }

   /**
    * @return the help button.
    */
   public JButton getHelpButton()
   {
      return(helpButton);
   }

   /**
    * @return the ok button.
    */
   public JButton getOkButton()
   {
      return(okButton);
   }
}
