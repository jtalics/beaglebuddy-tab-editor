/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.instruments;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.instrument.BassGuitar;
import com.beaglebuddy.tab.model.instrument.Drums;
import com.beaglebuddy.tab.model.instrument.Guitar;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.instrument.Keyboard;
import com.beaglebuddy.tab.model.instrument.OtherBass;
import com.beaglebuddy.tab.model.instrument.OtherTreble;
import com.beaglebuddy.tab.model.instrument.Vocals;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;




/**
 * This dialog box allows the user to create a new instrument.
 * Since drums can only be played back on midi channel 10, there can only be one set of drums per song.
 * So, if the song already has a set of drums defined, then the user can not add another set.
 */
public class InstrumentAddDialog extends BaseDialog
{
   // class members
   protected static final int RADIO_BUTTON_SPACING = 15;

   // data members
   boolean    allowDrums;        // whether to allow the user to create a set of drums.
   Instrument instrument;        // the new instrument the user creates

   // controls
   private ButtonGroup  buttonGroup;
   private JRadioButton radioButtonTypeVocals;
   private JRadioButton radioButtonTypeGuitar;
   private JRadioButton radioButtonTypeKeyboards;
   private JRadioButton radioButtonTypeBass;
   private JRadioButton radioButtonTypeDrums;
   private JRadioButton radioButtonTypeOtherBass;
   private JRadioButton radioButtonTypeOtherTreble;




   /**
    * constructor.
    * <br/><br/>
    * @param frame       the main application frame.
    * @param allowDrums  whether to allow the user to create a set of drums.
    */
   public InstrumentAddDialog(MainFrame frame, boolean allowDrums)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.instrument.add");

      this.allowDrums = allowDrums;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.instrument.add.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      // create the radio buttons
      radioButtonTypeVocals      = new JRadioButton(ResourceBundle.getString("instrument.vocals"      ));
      radioButtonTypeGuitar      = new JRadioButton(ResourceBundle.getString("instrument.guitar"      ));
      radioButtonTypeKeyboards   = new JRadioButton(ResourceBundle.getString("instrument.keyboard"    ));
      radioButtonTypeBass        = new JRadioButton(ResourceBundle.getString("instrument.bass_guitar" ));
      radioButtonTypeDrums       = new JRadioButton(ResourceBundle.getString("instrument.drums"       ));
      radioButtonTypeOtherBass   = new JRadioButton(ResourceBundle.getString("instrument.other.bass"  ));
      radioButtonTypeOtherTreble = new JRadioButton(ResourceBundle.getString("instrument.other.treble"));

      // put all the radio buttons into a single group so that only one can be selected at a time
      buttonGroup = new ButtonGroup();
      buttonGroup.add(radioButtonTypeVocals     );
      buttonGroup.add(radioButtonTypeGuitar     );
      buttonGroup.add(radioButtonTypeKeyboards  );
      buttonGroup.add(radioButtonTypeBass       );
      buttonGroup.add(radioButtonTypeDrums      );
      buttonGroup.add(radioButtonTypeOtherBass  );
      buttonGroup.add(radioButtonTypeOtherTreble);

      // initially select the "vocals" radio button
      radioButtonTypeVocals.setSelected(true);

      // enable\disable drums
      radioButtonTypeDrums.setEnabled(allowDrums);

      // set the layout manager
      JPanel      controlsPanel = getControlsPanel();
      GroupLayout layout        = new GroupLayout(controlsPanel);

      // create a titled border around the radio buttons
      controlsPanel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.instrument.add.border.title.instrument_type")));

      // layout the controls
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(radioButtonTypeVocals)     .addGap(RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING)
            .addComponent(radioButtonTypeGuitar)     .addGap(RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING)
            .addComponent(radioButtonTypeKeyboards)  .addGap(RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING)
            .addComponent(radioButtonTypeBass)       .addGap(RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING)
            .addComponent(radioButtonTypeDrums)      .addGap(RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING)
            .addComponent(radioButtonTypeOtherBass)  .addGap(RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING, RADIO_BUTTON_SPACING)
            .addComponent(radioButtonTypeOtherTreble)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(radioButtonTypeVocals)
            .addComponent(radioButtonTypeGuitar)
            .addComponent(radioButtonTypeKeyboards)
            .addComponent(radioButtonTypeBass)
            .addComponent(radioButtonTypeDrums)
            .addComponent(radioButtonTypeOtherBass)
            .addComponent(radioButtonTypeOtherTreble))
      );

      // invoke the layout manager
      pack();
   }

   /**
    * @return the instrument the user created.
    */
   public Instrument getInstrument()
   {
      return instrument;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
           if (radioButtonTypeVocals     .isSelected()) instrument = new Vocals     ();
      else if (radioButtonTypeGuitar     .isSelected()) instrument = new Guitar     ();
      else if (radioButtonTypeKeyboards  .isSelected()) instrument = new Keyboard   ();
      else if (radioButtonTypeBass       .isSelected()) instrument = new BassGuitar ();
      else if (radioButtonTypeDrums      .isSelected()) instrument = new Drums      ();
      else if (radioButtonTypeOtherBass  .isSelected()) instrument = new OtherBass  ();
      else if (radioButtonTypeOtherTreble.isSelected()) instrument = new OtherTreble();
   }
}
