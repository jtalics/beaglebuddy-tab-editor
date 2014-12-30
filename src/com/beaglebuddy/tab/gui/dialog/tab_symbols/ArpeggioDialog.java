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
import com.beaglebuddy.tab.model.attribute.chord.Arpeggio;
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
 * This class is a dialog box which allows a user to specify that a chord is played as an arpeggio.
 */
public class ArpeggioDialog extends BaseDialog
{
   // data members
   private Arpeggio arpeggio;

   // controls
   private JRadioButton radioButtonNone;
   private JRadioButton radioButtonUp;
   private JRadioButton radioButtonDown;
   private ButtonGroup  buttonGroup;




   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    * @param arpeggio    the arpeggio to be edited.
    */
   public ArpeggioDialog(MainFrame frame, Arpeggio arpeggio)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.arpeggio");

      // make a copy of the arpeggio the user will be editing
      this.arpeggio = (arpeggio == null ? null : (Arpeggio)arpeggio.clone());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.arpeggio.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      radioButtonNone = new JRadioButton(ResourceBundle.getString("dialog.arpeggio.label.none"));
      radioButtonDown = new JRadioButton(ResourceBundle.getString("dialog.arpeggio.label.down"));
      radioButtonUp   = new JRadioButton(ResourceBundle.getString("dialog.arpeggio.label.up"  ));

      radioButtonNone.setActionCommand("none");
      radioButtonDown.setActionCommand("down");
      radioButtonUp  .setActionCommand("up"  );

      radioButtonNone.setToolTipText(ResourceBundle.getString("dialog.arpeggio.tooltip.none"));
      radioButtonDown.setToolTipText(ResourceBundle.getString("dialog.arpeggio.tooltip.down"));
      radioButtonUp  .setToolTipText(ResourceBundle.getString("dialog.arpeggio.tooltip.up"  ));

      // create the button group that groups the radio buttons together
      buttonGroup = new ButtonGroup();
      buttonGroup.add(radioButtonNone);
      buttonGroup.add(radioButtonDown);
      buttonGroup.add(radioButtonUp  );

      // set the initially selected radio button
           if (arpeggio                == null                   ) radioButtonNone.setSelected(true);
      else if (arpeggio.getDirection() == Arpeggio.Direction.Down) radioButtonDown.setSelected(true);
      else if (arpeggio.getDirection() == Arpeggio.Direction.Up  ) radioButtonUp  .setSelected(true);


      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(radioButtonNone)
               .addComponent(radioButtonDown)
               .addComponent(radioButtonUp  )))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {radioButtonNone, radioButtonDown, radioButtonUp});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(radioButtonNone)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonDown)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonUp))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {radioButtonNone, radioButtonDown, radioButtonUp});

      // invoke the layout manager
      pack();
  }

   /**
    * @return the arpeggio the user edited.
    */
   public Arpeggio getArpeggio()
   {
      return arpeggio;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
           if (radioButtonNone.isSelected()) arpeggio = null;
      else if (radioButtonUp  .isSelected()) arpeggio = new Arpeggio(Arpeggio.Direction.Up  );
      else if (radioButtonDown.isSelected()) arpeggio = new Arpeggio(Arpeggio.Direction.Down);
   }
}
