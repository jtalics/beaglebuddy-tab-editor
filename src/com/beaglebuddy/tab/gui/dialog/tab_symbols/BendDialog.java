/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * � 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.tab_symbols;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.attribute.note.Bend;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;




/**
 * This class is a dialog box which allows a user to specify how a note is bent.
 */
public class BendDialog extends BaseDialog
{
   // data members
   private Bend bend;




   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    * @param bend    the bend to be edited.
    */
   public BendDialog(MainFrame frame, Bend bend)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.bend");

      // make a copy of the bend the user will be editing
      this.bend = (Bend)bend.clone();

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.bend.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      // create the midi device label
      JLabel label = new JLabel("bend");

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
    * @return the bend the user edited.
    */
   public Bend getBend()
   {
      return bend;
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
