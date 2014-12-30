/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.AlternateEnding;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;




/**
 * This class is a dialog box which allows a user to select a number of alternate endings.
 */
public class AlternateEndingDialog extends BaseDialog
{
   // class members
   private static final int GAP_WIDTH = 20;          // width in pixels between each check box

   // data members
   AlternateEnding alternateEnding;

   // dialog box controls
   private JCheckBox[] checkBoxes;




   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    */
   public AlternateEndingDialog(MainFrame frame)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.alternate_ending");

      // retrieve the current alternate ending at the current location
      Location location = frame.getCurrentLocation();
      Section  section  = frame.getSong().getScore().getSections().get(location.getSection());
      Barline  barline  = section.getBarlines().get(location.getBarline());

      alternateEnding = new AlternateEnding(barline.getAlternateEnding());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.alternate_ending.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
	  JPanel controlsPanel = getControlsPanel();

      // create a border
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      // create the checkboxes
      byte[] endings = alternateEnding.getNumbers();
      checkBoxes = new JCheckBox[AlternateEnding.MAX_NUMBER];
      for(int i=0; i<checkBoxes.length; ++i)
      {
         boolean checked = false;
         for(int j=0; j<endings.length && !checked; ++j)
            checked  = endings[j] == (i + 1);

         checkBoxes[i] = new JCheckBox("" + (i + 1) + ".", checked);
         checkBoxes[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
         checkBoxes[i].setMargin(new Insets(0, 0, 0, 0));
      }

      // set the layout manager
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      // layout the checkboxes
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(checkBoxes[0])
               .addComponent(checkBoxes[4]))
            .addGap(GAP_WIDTH, GAP_WIDTH, GAP_WIDTH)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(checkBoxes[1])
               .addComponent(checkBoxes[5]))
            .addGap(GAP_WIDTH, GAP_WIDTH, GAP_WIDTH)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(checkBoxes[2])
               .addComponent(checkBoxes[6]))
            .addGap(GAP_WIDTH, GAP_WIDTH, GAP_WIDTH)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(checkBoxes[7])
               .addComponent(checkBoxes[3]))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(checkBoxes[0])
               .addComponent(checkBoxes[1])
               .addComponent(checkBoxes[2])
               .addComponent(checkBoxes[3]))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(checkBoxes[4])
               .addComponent(checkBoxes[5])
               .addComponent(checkBoxes[6])
               .addComponent(checkBoxes[7])))
      );

      // invoke the layout manager
      pack();
  }

   /**
    * @return the alternate ending(s) the user selected.
    */
   public AlternateEnding getAlternateEnding()
   {
      return alternateEnding;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // determine how many endings the user selected
      int numEndings = 0;
      for(int i=0; i<checkBoxes.length; ++i)
      {
         if (checkBoxes[i].isSelected())
            numEndings++;
      }

      // if no endings were selected, then there is no need to have an alternate ending object
      if (numEndings == 0)
      {
         alternateEnding = null;
      }
      else
      {
         // otherwise, save the new endings
         byte[] endings = new byte[numEndings];
         for(int i=0, j=0; i<checkBoxes.length; ++i)
         {
            if (checkBoxes[i].isSelected())
               endings[j++] = (byte)(i+1);
         }
         alternateEnding = new AlternateEnding(endings);
      }
   }
}
