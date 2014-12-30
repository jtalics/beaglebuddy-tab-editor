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
import com.beaglebuddy.tab.model.attribute.note.Trill;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;




/**
 * This class is a dialog box which allows a user to specify how a note is trilled.
 */
public class TrillDialog extends BaseDialog
{
   // data members
   private Trill trill;

   // controls
   private JSpinner spinnerTrillFret;



   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    * @param trill   the trill to be edited.
    */
   public TrillDialog(MainFrame frame, Trill trill)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.trill");

      // make a copy of the trill the user will be editing
      this.trill = (trill == null ? new Trill() : (Trill)trill.clone());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.trill.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JLabel labelTrillFret = new JLabel(ResourceBundle.getString("dialog.trill.label.trill_fret"));
      spinnerTrillFret      = new JSpinner(new SpinnerNumberModel(trill.getTrillFret().ordinal(), 0, Instrument.MAX_NUM_FRETS, 1));

      // disable keyboard editor in the spinner
      JFormattedTextField tf = ((JSpinner.DefaultEditor)spinnerTrillFret.getEditor()).getTextField();
      tf.setEditable(false);
      // the previous call sets the background to a disabled color (usually gray). to change this disabled color, reset the background color.
      tf.setBackground(Color.white);

      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(labelTrillFret)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(spinnerTrillFret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelTrillFret)
               .addComponent(spinnerTrillFret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
      );

      // invoke the layout manager
      pack();
  }

   /**
    * @return the bend the user edited.
    */
   public Trill getTrill()
   {
      return trill;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      trill.setTrillFret(Instrument.getFret(((Integer)spinnerTrillFret.getValue()).intValue()));
   }
}
