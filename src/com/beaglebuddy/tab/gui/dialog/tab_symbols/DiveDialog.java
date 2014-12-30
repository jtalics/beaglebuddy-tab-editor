/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.tab_symbols;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.attribute.chord.TremoloBar;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;




/**
 * This class is a dialog box which allows a user to specify how a chord is dived with the tremolo bar.
 */
public class DiveDialog extends BaseDialog
{
   // data members
   private TremoloBar dive;




   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    * @param dive    the tremolo bar dive to be edited.
    */
   public DiveDialog(MainFrame frame, TremoloBar dive)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.dive");

      // make a copy of the dive the user will be editing
      this.dive = (TremoloBar)dive.clone();

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.dive.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      // create the midi device label
      JLabel label = new JLabel("dive");

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
   public TremoloBar getDive()
   {
      return dive;
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
