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
import com.beaglebuddy.tab.model.TempoMarker;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Insets;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;




/**
 * This class is a dialog box which allows a user to set the tempo of the song.
 */
public class TempoDialog extends BaseDialog
{
   // class members
   private static final int BEATS_PER_MINUTE_SPINNER_WIDTH = 40;
   private static final int DESCRIPTION_COMBOX_BOX_WIDTH   = 200;

   // data members
   private TempoMarker tempo;              // the tempo marker being edited

   // dialog box controls
   private JLabel    labelBPM;
   private JSpinner  spinnerBPM;
   private JCheckBox checkBoxSwingFeel;
   private JLabel    labelSwingFeel;
   private JLabel    labelDescription;
   private JComboBox comboBoxDescription;





   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    * @param tempo   the tempo marker to edit.  if the user is creating a new tempo marker, this will be null.
    */
   public TempoDialog(MainFrame frame, TempoMarker tempo)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.tempo");

      // save the tempo being edited
      this.tempo = tempo;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.tempo.title"));
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

      // create the labels
      labelBPM         = new JLabel(new ImageIcon(getClass().getResource(ResourceBundle.getString("dialog.tempo.icon.beats_per_minute"))));
      labelSwingFeel   = new JLabel(new ImageIcon(getClass().getResource(ResourceBundle.getString("dialog.tempo.icon.swing_feel"      ))));
      labelDescription = new JLabel(ResourceBundle.getString("dialog.tempo.label.description"));
      labelBPM.setText("    =");
      labelBPM        .setToolTipText(ResourceBundle.getString("dialog.tempo.tooltip.beats_per_minute"));
      labelSwingFeel  .setToolTipText(ResourceBundle.getString("dialog.tempo.tooltip.swing_feel" ));
      labelDescription.setToolTipText(ResourceBundle.getString("dialog.tempo.tooltip.description"));

      // create the spinner
      spinnerBPM = new JSpinner(new SpinnerNumberModel(TempoMarker.DEFAULT_NUM_BEATS_PER_MINUTE, TempoMarker.MIN_NUM_BEATS_PER_MINUTE, TempoMarker.MAX_NUM_BEATS_PER_MINUTE, 1));
      spinnerBPM.setToolTipText(ResourceBundle.getString("dialog.tempo.tooltip.beats_per_minute"));
      if (tempo != null)
         spinnerBPM.setValue(new Integer(tempo.getBeatsPerMinute()));

      // create the "swing feel" check box
      checkBoxSwingFeel = new JCheckBox();
      checkBoxSwingFeel.setToolTipText(ResourceBundle.getString("dialog.tempo.tooltip.swing_feel"));
      checkBoxSwingFeel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxSwingFeel.setMargin(new Insets(0, 0, 0, 0));
      checkBoxSwingFeel.setSelected(tempo != null && tempo.hasSwingFeel());

      // create the combo box
      Vector<String> descriptions = new Vector<String>();
      String         description  = "";

      descriptions.add(tempo == null || tempo.getDescription() == null || tempo.getDescription().trim().length() == 0 ? description : tempo.getDescription());
      for(int i=0; description != null; ++i)
         if ((description = ResourceBundle.getString("dialog.tempo.combo_box.description." + i)) != null)
            descriptions.add(description);
      comboBoxDescription = new JComboBox(descriptions);
      comboBoxDescription.setEditable(true);
      comboBoxDescription.setToolTipText(ResourceBundle.getString("dialog.tempo.tooltip.description"));

      // layout the controls panel
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(labelDescription)
               .addComponent(checkBoxSwingFeel)
               .addComponent(labelBPM))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(spinnerBPM, GroupLayout.PREFERRED_SIZE, BEATS_PER_MINUTE_SPINNER_WIDTH, GroupLayout.PREFERRED_SIZE)
               .addComponent(labelSwingFeel)
               .addComponent(comboBoxDescription, 0, DESCRIPTION_COMBOX_BOX_WIDTH, Short.MAX_VALUE)))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelBPM)
               .addComponent(spinnerBPM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
               .addComponent(labelSwingFeel)
               .addComponent(checkBoxSwingFeel))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelDescription)
               .addComponent(comboBoxDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
      );

      // invoke the layout manager
      pack();

      // set the spinner to have the initial focus
      spinnerBPM.requestFocusInWindow();
   }

   /**
    * @return the tempo the user selected.
    */
   public TempoMarker getTempoMarker()
   {
      return tempo;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      tempo = new TempoMarker((short)(((Integer)spinnerBPM.getValue()).intValue()), (String)comboBoxDescription.getSelectedItem(), checkBoxSwingFeel.isSelected());
   }
}
