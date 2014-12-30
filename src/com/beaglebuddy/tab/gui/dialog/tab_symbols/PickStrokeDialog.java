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
import com.beaglebuddy.tab.model.attribute.chord.PickStroke;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;




/**
 * This class is a dialog box which allows a user to specify the direction in which the user strikes the strings with the pick.
 */
public class PickStrokeDialog extends BaseDialog
{
   // data members
   private PickStroke pickStroke;

   // controls
   private JRadioButton radioButtonNone;
   private JRadioButton radioButtonUp;
   private JRadioButton radioButtonDown;
   private ButtonGroup  buttonGroup;




   /**
    * constructor.
    * <br/><br/>
    * @param frame        the main application frame.
    * @param pickStroke   the pick stroke to be edited.
    */
   public PickStrokeDialog(MainFrame frame, PickStroke pickStroke)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.pickstroke");

      // make a copy of the pickStroke the user will be editing
      this.pickStroke = (pickStroke == null ? null : (PickStroke)pickStroke.clone());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.pickstroke.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      radioButtonNone = new JRadioButton(ResourceBundle.getString("dialog.pickstroke.label.none"));
      radioButtonDown = new JRadioButton(ResourceBundle.getString("dialog.pickstroke.label.down"));
      radioButtonUp   = new JRadioButton(ResourceBundle.getString("dialog.pickstroke.label.up"  ));

//    System.out.println("path: " + getClass().getResource(ResourceBundle.getString("dialog.pickstroke.image.none")).getPath());
      radioButtonNone.setText(ResourceBundle.format("dialog.pickstroke.label.format", "file:/dev/projects/tab/src/com/beaglebuddy/tab/gui/images/dialogs/pickstroke/none.gif", radioButtonNone.getText()));
      radioButtonDown.setText(ResourceBundle.format("dialog.pickstroke.label.format", "file:/dev/projects/tab/src/com/beaglebuddy/tab/gui/images/dialogs/pickstroke/down.gif", radioButtonDown.getText()));
      radioButtonUp  .setText(ResourceBundle.format("dialog.pickstroke.label.format", "file:/dev/projects/tab/src/com/beaglebuddy/tab/gui/images/dialogs/pickstroke/up.gif"  , radioButtonUp  .getText()));
//    radioButtonDown.setText(ResourceBundle.format("dialog.pickstroke.label.format", "file:///dev/projects/tab/lib/beaglebuddy_tab.jar!/images/dialogs/pickstroke/down.gif"    , radioButtonDown.getText()));
//    radioButtonUp  .setText(ResourceBundle.format("dialog.pickstroke.label.format", getClass().getResource(ResourceBundle.getString("dialog.pickstroke.image.up"  )).getPath(), radioButtonUp  .getText()));

      radioButtonNone.setActionCommand("none");
      radioButtonDown.setActionCommand("down");
      radioButtonUp  .setActionCommand("up"  );

      radioButtonNone.setToolTipText(ResourceBundle.getString("dialog.pickstroke.tooltip.none"));
      radioButtonDown.setToolTipText(ResourceBundle.getString("dialog.pickstroke.tooltip.down"));
      radioButtonUp  .setToolTipText(ResourceBundle.getString("dialog.pickstroke.tooltip.up"  ));

      // create the button group that groups the radio buttons together
      buttonGroup = new ButtonGroup();
      buttonGroup.add(radioButtonNone);
      buttonGroup.add(radioButtonDown);
      buttonGroup.add(radioButtonUp  );

      // set the initially selected radio button
           if (pickStroke                == null                     ) radioButtonNone.setSelected(true);
      else if (pickStroke.getDirection() == PickStroke.Direction.Down) radioButtonDown.setSelected(true);
      else if (pickStroke.getDirection() == PickStroke.Direction.Up  ) radioButtonUp  .setSelected(true);


      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
//          .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(radioButtonNone)
               .addComponent(radioButtonDown)
               .addComponent(radioButtonUp  )))
//          .addContainerGap(14, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {radioButtonNone, radioButtonDown, radioButtonUp});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
//          .addContainerGap()
            .addComponent(radioButtonNone)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonDown)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonUp))
//          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {radioButtonNone, radioButtonDown, radioButtonUp});

      // invoke the layout manager
      pack();
  }

   /**
    * @return the pickStroke the user edited.
    */
   public PickStroke getPickStroke()
   {
      return pickStroke;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
           if (radioButtonNone.isSelected()) pickStroke = null;
      else if (radioButtonUp  .isSelected()) pickStroke = new PickStroke(PickStroke.Direction.Up  );
      else if (radioButtonDown.isSelected()) pickStroke = new PickStroke(PickStroke.Direction.Down);
   }
}
