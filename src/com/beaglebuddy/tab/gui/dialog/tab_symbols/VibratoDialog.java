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
import com.beaglebuddy.tab.model.attribute.chord.Vibrato;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;




/**
 * This class is a dialog box which allows a user to specify how a chord is vibrato'd.
 */
public class VibratoDialog extends BaseDialog
{
   // data members
   private Vibrato vibrato;

   // controls
   JRadioButton radioButtonNone;
   JRadioButton radioButtonNormal;
   JRadioButton radioButtonWide;
   ButtonGroup  buttonGroup;




   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    * @param vibrato    the vibrato to be edited.
    */
   public VibratoDialog(MainFrame frame, Vibrato vibrato)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.vibrato");

      // make a copy of the vibrato the user will be editing
      this.vibrato = (vibrato == null ? null : (Vibrato)vibrato.clone());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.vibrato.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      radioButtonNone   = new JRadioButton(ResourceBundle.getString("text.none"  ));
      radioButtonNormal = new JRadioButton(ResourceBundle.getString("text.normal"));
      radioButtonWide   = new JRadioButton(ResourceBundle.getString("text.wide"  ));

      radioButtonNone  .setActionCommand("none"  );
      radioButtonNormal.setActionCommand("normal");
      radioButtonWide  .setActionCommand("wide"  );

      radioButtonNone  .setToolTipText(ResourceBundle.getString("dialog.vibrato.none.tooltip"  ));
      radioButtonNormal.setToolTipText(ResourceBundle.getString("dialog.vibrato.normal.tooltip"));
      radioButtonWide  .setToolTipText(ResourceBundle.getString("dialog.vibrato.wide.tooltip"  ));

      // create the button group that groups the radio buttons together
      buttonGroup = new ButtonGroup();
      buttonGroup.add(radioButtonNone  );
      buttonGroup.add(radioButtonNormal);
      buttonGroup.add(radioButtonWide  );

      // set the initially selected radio button
           if (vibrato             == null)                  radioButtonNone  .setSelected(true);
      else if (vibrato.getAmount() == Vibrato.Amount.Normal) radioButtonNormal.setSelected(true);
      else if (vibrato.getAmount() == Vibrato.Amount.Wide  ) radioButtonWide  .setSelected(true);


      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(radioButtonWide  )
               .addComponent(radioButtonNormal)
               .addComponent(radioButtonNone  ))
            .addContainerGap(14, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {radioButtonNone, radioButtonNormal, radioButtonWide});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(radioButtonNone)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonNormal)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonWide)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {radioButtonNone, radioButtonNormal, radioButtonWide});

      // invoke the layout manager
      pack();
  }

   /**
    * @return the vibrato the user edited.
    */
   public Vibrato getVibrato()
   {
      return vibrato;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
           if (radioButtonNone  .isSelected()) vibrato = null;
      else if (radioButtonNormal.isSelected()) vibrato = new Vibrato(Vibrato.Amount.Normal);
      else if (radioButtonWide  .isSelected()) vibrato = new Vibrato(Vibrato.Amount.Wide  );
   }
}
