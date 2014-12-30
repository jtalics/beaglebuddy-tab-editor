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
import com.beaglebuddy.tab.model.TimeSignature;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 * This class is a dialog box which allows a user to set the time signature for a measure.
 */
public class TimeSignatureDialog extends BaseDialog implements ChangeListener, ItemListener
{
   // class members
   private static final int NUM_BEAMING_PATTERN_FIELDS      = 4;
   private static final int BEATS_PER_MEASURE_SPINNER_WIDTH = 35;

   // data members
   private TimeSignature timeSignature;              // the time signature being edited

   // dialog box controls
   private JPanel       panelMeter;                  // panel which holds the meter controls
   private JPanel       panelBeamingPattern;         // panel which holds the eigth note beaming controls
   private JLabel       labelBeatsPerMeasure;        // beats per measure
   private JSpinner     spinnerBeatsPerMeasure;
   private JLabel       labelBeatValue;              // beat value
   private JComboBox    comboBoxBeatValue;
   private JCheckBox    checkBoxCommonTime;          // common time
   private JCheckBox    checkBoxCutTime;             // cut time
   private JCheckBox    checkBoxSetBeamingPattern;   // set eigth note beaming pattern manually
   private JTextField[] textFieldBeamingPattern;     // eigth note beaming pattern
   private JLabel[]     labelSign;                   // 3 "+" signs and an "=" sign





   /**
    * constructor.
    * <br/><br/>
    * @param frame           the main application frame.
    * @param timeSignature   the time signature to edit.  if the user is creating a new time signature, this will be null.
    */
   public TimeSignatureDialog(MainFrame frame, TimeSignature timeSignature)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.time_signature");

      // make a copy of the time signature for editing
      this.timeSignature = new TimeSignature(timeSignature);

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.time_signature.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JPanel controlsPanel = getControlsPanel();
      panelMeter           = createMeterPanel();
      panelBeamingPattern  = createBeamingPatternPanel();

      // layout the controls panel
      GroupLayout layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(panelMeter         ,                                 GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(panelBeamingPattern, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelMeter         , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(panelBeamingPattern, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {panelMeter, panelBeamingPattern});

      // initially enable\disable the meter controls based on the common time\cut time, set beaming pattern checkboxes
      enableControls("common_time");
      enableControls("set_beaming_pattern");

      // invoke the layout manager
      pack();
   }

   /**
    * @return the panel containing the meter controls.
    */
   private JPanel createMeterPanel()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.time_signature.border_title.meter")));

      // create the beats per minute controls
      labelBeatsPerMeasure   = new JLabel(ResourceBundle.getString("dialog.time_signature.label.beats_per_measure"));
      spinnerBeatsPerMeasure = new JSpinner(new SpinnerNumberModel(TimeSignature.DEFAULT_NUM_BEATS_PER_MEASURE, TimeSignature.MIN_NUM_BEATS_PER_MEASURE, TimeSignature.MAX_NUM_BEATS_PER_MEASURE, 1));
      spinnerBeatsPerMeasure.setValue(new Integer(timeSignature.getBeatsPerMeasure()));
      spinnerBeatsPerMeasure.setToolTipText(ResourceBundle.getString("dialog.time_signature.tooltip.beats_per_measure"));
      spinnerBeatsPerMeasure.addChangeListener(this);

      // create the beat value controls
      labelBeatValue = new JLabel(ResourceBundle.getString("dialog.time_signature.label.beat_value"));
      Integer[] beatValues = {2, 4, 8, 16, 32};
      comboBoxBeatValue = new JComboBox(beatValues);
      comboBoxBeatValue.setSelectedItem(new Integer(timeSignature.getBeatAmount()));
      comboBoxBeatValue.setToolTipText(ResourceBundle.getString("dialog.time_signature.tooltip.beat_value"));
      comboBoxBeatValue.addItemListener(this);

      // create the common time, cut time, and manually set beaming pattern  controls
      checkBoxCommonTime        = new JCheckBox(ResourceBundle.getString("dialog.time_signature.check_box.common_time"        ));
      checkBoxCutTime           = new JCheckBox(ResourceBundle.getString("dialog.time_signature.check_box.cut_time"           ));
      checkBoxSetBeamingPattern = new JCheckBox(ResourceBundle.getString("dialog.time_signature.check_box.set_beaming_pattern"));
      checkBoxCommonTime       .setToolTipText (ResourceBundle.getString("dialog.time_signature.tooltip.common_time"          ));
      checkBoxCutTime          .setToolTipText (ResourceBundle.getString("dialog.time_signature.tooltip.cut_time"             ));
      checkBoxSetBeamingPattern.setToolTipText (ResourceBundle.getString("dialog.time_signature.tool_tip.set_beaming_pattern" ));
      checkBoxCommonTime       .setSelected(timeSignature.isCommonTime());
      checkBoxCutTime          .setSelected(timeSignature.isCutTime   ());
      checkBoxSetBeamingPattern.setSelected(false                       );
      checkBoxCommonTime       .setActionCommand("common_time"        );
      checkBoxCutTime          .setActionCommand("cut_time"           );
      checkBoxSetBeamingPattern.setActionCommand("set_beaming_pattern");
      checkBoxCommonTime       .addActionListener(this);
      checkBoxCutTime          .addActionListener(this);
      checkBoxSetBeamingPattern.addActionListener(this);
      checkBoxCommonTime       .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxCutTime          .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxSetBeamingPattern.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxCommonTime       .setMargin(new Insets(0, 0, 0, 0));
      checkBoxCutTime          .setMargin(new Insets(0, 0, 0, 0));
      checkBoxSetBeamingPattern.setMargin(new Insets(0, 0, 0, 0));

      // layout the meter panel
      GroupLayout groupLayout = new GroupLayout(panel);
      panel.setLayout(groupLayout);

      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(groupLayout.createSequentialGroup()
                  .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                     .addComponent(labelBeatsPerMeasure)
                     .addComponent(labelBeatValue))
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                     .addComponent(spinnerBeatsPerMeasure   , GroupLayout.PREFERRED_SIZE, BEATS_PER_MEASURE_SPINNER_WIDTH, GroupLayout.PREFERRED_SIZE)
                     .addComponent(comboBoxBeatValue        , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE       , GroupLayout.PREFERRED_SIZE)))
               .addComponent(checkBoxCommonTime       )
               .addComponent(checkBoxCutTime          )
               .addComponent(checkBoxSetBeamingPattern)))
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelBeatsPerMeasure)
               .addComponent(spinnerBeatsPerMeasure, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelBeatValue)
               .addComponent(comboBoxBeatValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkBoxCommonTime)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkBoxCutTime)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkBoxSetBeamingPattern))
      );
      return panel;
   }

   /**
    * @return the panel containing the eigth note beaming pattern controls.
    */
   private JPanel createBeamingPatternPanel()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.time_signature.border_title.beaming_pattern")));

      // create the beaming pattern text fields
      textFieldBeamingPattern = new JTextField[NUM_BEAMING_PATTERN_FIELDS+1];
      labelSign               = new JLabel    [NUM_BEAMING_PATTERN_FIELDS];
      String[] signs          = {" + ",  " + ",  " + ",  " = "};
      byte[]   beamingPattern = timeSignature.getBeamingPattern();

      // create the beaming pattern text fields
      for(int i=0; i<NUM_BEAMING_PATTERN_FIELDS; ++i)
      {
         textFieldBeamingPattern[i] = new JTextField(2);
         textFieldBeamingPattern[i].setToolTipText(ResourceBundle.getString("dialog.time_signature.tool_tip.beaming_pattern_i"));
         if (beamingPattern[i] != 0)
            textFieldBeamingPattern[i].setText(Byte.toString(beamingPattern[i]));
      }
      textFieldBeamingPattern[NUM_BEAMING_PATTERN_FIELDS] = new JTextField(2);
      textFieldBeamingPattern[NUM_BEAMING_PATTERN_FIELDS].setToolTipText(ResourceBundle.getString("dialog.time_signature.tool_tip.beaming_pattern_total"));

      // create the beaming pattern plus and equals signs
      for(int i=0; i<NUM_BEAMING_PATTERN_FIELDS; ++i)
         labelSign[i] = new JLabel(signs[i]);
      setBeamingPatternTotal();

      // layout the beaming pattern panel
      GroupLayout groupLayout = new GroupLayout(panel);
      panel.setLayout(groupLayout);

      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(groupLayout.createSequentialGroup()
            .addComponent(textFieldBeamingPattern[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(labelSign[0])
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textFieldBeamingPattern[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(labelSign[1])
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textFieldBeamingPattern[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(labelSign[2])
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textFieldBeamingPattern[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(labelSign[3])
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textFieldBeamingPattern[4], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(textFieldBeamingPattern[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(labelSign[0])
               .addComponent(textFieldBeamingPattern[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(labelSign[1])
               .addComponent(textFieldBeamingPattern[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(labelSign[2])
               .addComponent(textFieldBeamingPattern[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(labelSign[3])
               .addComponent(textFieldBeamingPattern[4], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
      );
      return panel;
   }

   /**
    * enable or disable dialog controls based on which boxes are cheked
    * <br/><br/>
    * @param command   which checkbox the user has changed.
    */
   private void enableControls(String command)
   {
      // if either the common time or cut time boxes are checked, then disable the beats per measure and value beat controls.
      // otherwise, if neither box is checked, enable the beats per measure and value beat controls.
      if (command.equals("common_time") || command.equals("cut_time"))
      {
         boolean checkBoxSelected = checkBoxCommonTime.isSelected() || checkBoxCutTime.isSelected();

         spinnerBeatsPerMeasure.setEnabled(!checkBoxSelected);
         comboBoxBeatValue     .setEnabled(!checkBoxSelected);

         if (command.equals("common_time") && checkBoxCommonTime.isSelected())
            checkBoxCutTime   .setSelected(false);
         if (command.equals("cut_time"   ) && checkBoxCutTime   .isSelected())
            checkBoxCommonTime.setSelected(false);
      }
      // if the set beaming pattern manually box is checked, then enable the eigth notes beaming pattern controls.
      else if (command.equals("set_beaming_pattern"))
      {
         for(int i=0; i<=NUM_BEAMING_PATTERN_FIELDS; ++i)
            textFieldBeamingPattern[i].setEnabled(checkBoxSetBeamingPattern.isSelected());
      }
      setBeamingPatternTotal();
   }

   /**
    * sets the eigth note beaming pattern.
    */
   private void setBeamingPatternTotal()
   {
      int value = 0;

      if (checkBoxCommonTime.isSelected())
         value = 8;
      else if (checkBoxCutTime.isSelected())
         value = 4;
      else
      {
         int beatsPerMeasure = ((Integer)spinnerBeatsPerMeasure.getValue()).intValue();
         int beatValue       = ((Integer)comboBoxBeatValue.getSelectedItem()).intValue();
         switch (beatValue)
         {
            case 2:  value = 4 * beatsPerMeasure;  break;
            case 4:  value = 2 * beatsPerMeasure;  break;
            case 8:  value = beatsPerMeasure;      break;
            case 16: value = beatsPerMeasure / 2;  break;
            case 32: value = beatsPerMeasure / 4;  break;
         }
      }
      textFieldBeamingPattern[NUM_BEAMING_PATTERN_FIELDS].setText(String.valueOf(value));
   }

   /**
    * implements the ActionListener interface.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String command = event.getActionCommand();

      if (command.equals("common_time") || command.equals("cut_time") || command.equals("set_beaming_pattern"))
         enableControls(command);
      else if (command.equals("ok"))
      {
         if (checkBoxSetBeamingPattern.isSelected())
         {
            // todo: add up all the beam groupings and make sure that add up to the required total number
            //       if ther is an error, display an error message to the user
            ResourceBundle.getString("dialog.time_signature.error_msg.invalid_beaming_pattern");
            // set the focus to the 1st beaming text field
            textFieldBeamingPattern[0].requestFocusInWindow();
         }
      }
      else
         super.actionPerformed(event);
   }

   /**
    * implements the ChangeListener interface.
    * called whenever the beats per measure spinner changes, this method updates the total number of beats in the eigth note beaming pattern.
    * <br/><br/>
    * @param event   the spinner event.
    */
   public void stateChanged(ChangeEvent event)
   {
      int value = ((Integer)spinnerBeatsPerMeasure.getValue()).intValue();
      textFieldBeamingPattern[NUM_BEAMING_PATTERN_FIELDS].setText(String.valueOf(value));
   }

   /**
    * implements the ItemListener interface.
    * <br/><br/>
    * @param event   the index of list item the user selected.
    */
   public void itemStateChanged(ItemEvent event)
   {
      if (event.getStateChange() == ItemEvent.SELECTED)
         setBeamingPatternTotal();
   }

   /**
    * @return the time signature the user selected.
    */
   public TimeSignature getTimeSignature()
   {
      return timeSignature;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      if (checkBoxCommonTime.isSelected())
         timeSignature.setCommonTime();
      else if (checkBoxCutTime.isSelected())
         timeSignature.setCutTime();
      else
         timeSignature.setMeter((byte)(((Integer)spinnerBeatsPerMeasure.getValue()).intValue()), (byte)(((Integer)comboBoxBeatValue.getSelectedItem()).intValue()));

      byte[] beamingPattern = new byte[NUM_BEAMING_PATTERN_FIELDS];
      for(int i=0; i<NUM_BEAMING_PATTERN_FIELDS; ++i)
      {
         String text = textFieldBeamingPattern[i].getText().trim();
         beamingPattern[i] = text.length() == 0 ? (byte)0 : Byte.valueOf(text).byteValue();
      }
      timeSignature.setBeamingPattern(beamingPattern);
   }
}
