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
import com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls.ComboBoxModelDrumMap;
import com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls.ComboBoxModelSound;
import com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls.ComboBoxModelTuning;
import com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls.SpinnerCapoModel;
import com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls.SpinnerNoteModel;
import com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls.SpinnerNumStringsModel;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;





/**
 * This dialog box displays the existing instruments in the song, and allows the user to delete them.
 * It also provides links to other dialog boxes which allows users to edit and add new instruments.
 * <p>
 * The ChangeListener implementation is wired to the number of strings spinner, which in turn notifies the
 * tuning combo box.
 * </p>
 */
public class InstrumentsBaseDialog extends BaseDialog implements ChangeListener, ItemListener
{
   // class members
   private static final int SETTINGS_FIELD_WIDTH      = 201;         // width of the individual fields in the settings panel
   private static final int TUNING_SPINNER_WIDTH      = 47;          // width of the tuning note spinner
   private static final int SLIDER_GAP_WIDTH          = 22;          // width of the gap between vertical sliders (volume, reverb, and chorus)
   private static final int SLIDER_PANEL_GAP          = 58;          // width of gap between the left  border of the settings panel and the vertical sliders panel
   private static final int TUNING_PANEL_GAP          = 53;          // width of gap between the right border of the settings panel and the tuning notes     panel
   private static final int TUNING_SPINNER_NOTE_WIDTH = 50;          // width of each tuning note spinner
   private static final int TUNING_SPINNER_SOUND_GAP  = 15;          // gap between the spinner notes and the sound buttons


   // data members
   protected Instrument  selectedInstrument;

   // dialog box controls

   // field settings
   protected JLabel      labelName;
   protected JLabel      labelSound;
   protected JLabel      labelStrings;
   protected JLabel      labelTuning;
   protected JLabel      labelDrumMap;
   protected JLabel      labelCapo;
   protected JLabel      labelPan;
   protected JTextField  textFieldName;
   protected JComboBox   comboBoxSound;
   protected JComboBox   comboBoxTuning;
   protected JComboBox   comboBoxDrumMap;
   protected JSpinner    spinnerNumStrings;
   protected JSpinner    spinnerCapo;
   protected JSlider     sliderPan;

   // slider settings
   protected JLabel      labelVolume;
   protected JLabel      labelReverb;
   protected JLabel      labelChorus;
   protected JSlider     sliderVolume;
   protected JSlider     sliderReverb;
   protected JSlider     sliderChorus;

   // tuning
   protected JSpinner[]  spinnerNote;
   protected JButton []  buttonNote;
   protected JSpinner    spinnerTuningOffset;




   /**
    * constructor.
    * <br/><br/>
    * @param frame             the main application frame.
    * @param instrument        instrument to display.
    * @param helpId            id of the online help page to display whenever the user presses the "help" button or "F1" key.
    * @param titleResourceId   resource bundle id of the string to be used for the dialog box title.
    */
   public InstrumentsBaseDialog(MainFrame frame, Instrument instrument, String helpId, String titleResourceId)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), helpId);

      // set the instrument that initially will be displayed
      selectedInstrument = instrument;

      // set the dialog box title
      setTitle(ResourceBundle.getString(titleResourceId));
   }

   /**
    * implements the ActionListener interface.
    * this method handles the user clicking one of the three side buttons: "add", "edit", "or "delete",
    * as well as when the user clicks on one of the tuning "speaker" buttons to listen to a sound sample.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   @Override
  public void actionPerformed(ActionEvent event)
   {
      String command = event.getActionCommand();
      Object source  = event.getSource();

      // did the user click a button?
      if (source != null && source instanceof JButton)
      {
         // is it the sound button for a tuning note?
         if (command.startsWith("note_sound_button_"))
         {
            // the user wants to hear the tone\pitch of the note from the tuning
            int index = Integer.valueOf(command.substring(command.length()-1)).intValue();
            playSound((Midi.Sound)comboBoxSound.getSelectedItem(), (Midi.Note)spinnerNote[index].getValue());
         }
         // otherwise, it's one of the bottom buttons: "ok", "cancel", or "help", so let the base class process it.
         else
         {
            super.actionPerformed(event);
         }
      }
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
  public void addComponents()
   {
      // create the instruments list panel and the instruments settings panel
      JPanel controlsPanel           = getControlsPanel();
      JPanel panelTop                = createTopPanel();
      JPanel panelInstrumentSettings = createPanelInstrumentSettings();

      // set the layout manager
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      // layout the horizontal axis
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(panelTop               , GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         .addComponent(panelInstrumentSettings, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      // layout the vertical axis
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelTop               , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(panelInstrumentSettings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );

      // invoke the layout manager
      pack();

      // enable/disable the fields based on which instrument is selected
      updateAllControls(selectedInstrument);
   }

   /**
    * @return the top panel of the dialog box.
    * todo: make this abstract
    */
   protected JPanel createTopPanel()
   {
      return new JPanel();
   }

   /**
    * @return the panel containing the currently selected instrument's settings.
    */
   protected JPanel createPanelInstrumentSettings()
   {
      JPanel panel        = new JPanel();
      JPanel panelFields  = createPanelFields ();
      JPanel panelSliders = createPanelSliders();
      JPanel panelTuning  = createPanelTuning ();

      //
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.instruments.border.title.settings")));

      // layout the components
      GroupLayout panelLayout = new GroupLayout(panel);
      panel.setLayout(panelLayout);

      // layout the components horizontally
      panelLayout.setHorizontalGroup(
         panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelLayout.createSequentialGroup()
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(panelLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(panelFields, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
               .addGroup(panelLayout.createSequentialGroup()
                  .addGap(SLIDER_PANEL_GAP, SLIDER_PANEL_GAP, SLIDER_PANEL_GAP)
                  .addComponent(panelSliders, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
            .addComponent(panelTuning, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(TUNING_PANEL_GAP, TUNING_PANEL_GAP, TUNING_PANEL_GAP))
      );

      // layout the components vertically
      panelLayout.setVerticalGroup(
         panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelLayout.createSequentialGroup()
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(panelTuning, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addGroup(panelLayout.createSequentialGroup()
                  .addComponent(panelFields, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(panelSliders, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      return panel;
   }

   /**
    * @return the panel containing the fields in the top part of the settings panel: sound, num strings, tuning,
    */
   protected JPanel createPanelFields()
   {
      JPanel panel = new JPanel();

      // create the labels
      labelName    = new JLabel(ResourceBundle.getString("dialog.instruments.label.name"    ));
      labelSound   = new JLabel(ResourceBundle.getString("dialog.instruments.label.sound"   ));
      labelStrings = new JLabel(ResourceBundle.getString("dialog.instruments.label.strings" ));
      labelTuning  = new JLabel(ResourceBundle.getString("dialog.instruments.label.tuning"  ));
      labelDrumMap = new JLabel(ResourceBundle.getString("dialog.instruments.label.drum_map"));
      labelCapo    = new JLabel(ResourceBundle.getString("dialog.instruments.label.capo"    ));
      labelPan     = new JLabel(ResourceBundle.getString("dialog.instruments.label.pan"     ));

      // create the instrument name text field
       textFieldName = new JTextField();
       textFieldName.setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.name"));

      // create the sound combo box
      comboBoxSound = new JComboBox();
      comboBoxSound.setEditable(false);
      comboBoxSound.setModel(new ComboBoxModelSound(selectedInstrument));
      comboBoxSound.setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.sound"));

      // create the number of strings spinner
      spinnerNumStrings = new JSpinner(new SpinnerNumStringsModel(selectedInstrument));
      spinnerNumStrings.setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.num_strings"));
      spinnerNumStrings.addChangeListener(this);
      ((JSpinner.DefaultEditor)spinnerNumStrings.getEditor()).getTextField().setEditable(false);
      // disable keyboard editor in the spinner
      JFormattedTextField tf = ((JSpinner.DefaultEditor)spinnerNumStrings.getEditor()).getTextField();
      tf.setEditable(false);
      // the previous call sets the background to a disabled color (usually gray). to change this disabled color, reset the background color.
      tf.setBackground(Color.white);

      // create the tuning combo box
      comboBoxTuning = new JComboBox();
      comboBoxTuning.setModel(new ComboBoxModelTuning(selectedInstrument));
      comboBoxTuning.setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.tuning"));
      comboBoxTuning.addItemListener(this);

      // create the drum map combo box
      comboBoxDrumMap = new JComboBox();
      comboBoxDrumMap.setEditable(false);
      comboBoxDrumMap.setModel(new ComboBoxModelDrumMap(selectedInstrument));
      comboBoxDrumMap.setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.drum_map"));


      // create the capo fret spinner
      spinnerCapo = new JSpinner(new SpinnerCapoModel(selectedInstrument));
      spinnerCapo.setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.capo"));
      ((JSpinner.DefaultEditor)spinnerCapo.getEditor()).getTextField().setEditable(false);

      // create the pan slider
      sliderPan = new JSlider(SwingConstants.HORIZONTAL, 0, Byte.MAX_VALUE, 0);

      // set the spacing of the tick marks
      sliderPan.setPaintTicks(true);
      sliderPan.setMajorTickSpacing(63);  // 127 / 2

      // set the length the slider
      Dimension dimension = new Dimension(400, 40);
      sliderPan.setMaximumSize  (dimension);
      sliderPan.setMinimumSize  (dimension);
      sliderPan.setPreferredSize(dimension);
      sliderPan.setSize         (dimension);

      // add the left, center, and right labels
      Hashtable<Integer, JLabel> sliderPanLabels = new Hashtable<Integer, JLabel>();
      sliderPanLabels.put(0  , new JLabel(ResourceBundle.getString("dialog.instruments.label.pan.left"  )));
      sliderPanLabels.put(63 , new JLabel(ResourceBundle.getString("dialog.instruments.label.pan.center")));
      sliderPanLabels.put(127, new JLabel(ResourceBundle.getString("dialog.instruments.label.pan.right" )));
      sliderPan.setPaintLabels(true);
      sliderPan.setLabelTable(sliderPanLabels);

      // layout the components
      GroupLayout panelLayout = new GroupLayout(panel);
      panel.setLayout(panelLayout);

      // layout the components horizontally
      panelLayout.setHorizontalGroup(
         panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(labelName   )
               .addComponent(labelSound  )
               .addComponent(labelStrings)
               .addComponent(labelTuning )
               .addComponent(labelDrumMap)
               .addComponent(labelCapo   )
               .addComponent(labelPan    ))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
               .addComponent(textFieldName    , GroupLayout.PREFERRED_SIZE, SETTINGS_FIELD_WIDTH    , GroupLayout.PREFERRED_SIZE)
               .addComponent(comboBoxSound    , GroupLayout.PREFERRED_SIZE, SETTINGS_FIELD_WIDTH    , GroupLayout.PREFERRED_SIZE)
               .addComponent(spinnerNumStrings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(comboBoxTuning   , GroupLayout.PREFERRED_SIZE, SETTINGS_FIELD_WIDTH    , GroupLayout.PREFERRED_SIZE)
               .addComponent(comboBoxDrumMap  , GroupLayout.PREFERRED_SIZE, SETTINGS_FIELD_WIDTH    , GroupLayout.PREFERRED_SIZE)
               .addComponent(spinnerCapo      , GroupLayout.PREFERRED_SIZE, 35                      , GroupLayout.PREFERRED_SIZE)
               .addComponent(sliderPan        , GroupLayout.PREFERRED_SIZE, SETTINGS_FIELD_WIDTH    , GroupLayout.PREFERRED_SIZE))
              .addContainerGap())                                      //   GroupLayout.DEFAULT_SIZE
      );
      panelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {textFieldName, comboBoxSound, comboBoxTuning, comboBoxDrumMap, sliderPan});

      // layout the components vertically
      panelLayout.setVerticalGroup(
         panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelName )
               .addComponent(textFieldName    , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelSound)
               .addComponent(comboBoxSound    , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelStrings)
               .addComponent(spinnerNumStrings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelTuning)
               .addComponent(comboBoxTuning   , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelDrumMap)
               .addComponent(comboBoxDrumMap  , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelCapo)
               .addComponent(spinnerCapo      , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(labelPan)
               .addComponent(sliderPan        , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
      );
      panelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {labelName, labelSound, labelStrings, labelTuning, labelDrumMap, labelCapo, labelPan});

      return panel;
   }

   /**
    * @return the panel containing the three vertical sliders: volume, reverb, and chorus
    */
   protected JPanel createPanelSliders()
   {
      JPanel panel = new JPanel();

      // create the labels that appear below the sliders
      labelVolume  = new JLabel(ResourceBundle.getString("dialog.instruments.label.volume" ));
      labelReverb  = new JLabel(ResourceBundle.getString("dialog.instruments.label.reverb" ));
      labelChorus  = new JLabel(ResourceBundle.getString("dialog.instruments.label.chorus" ));

      // create the sliders
      sliderVolume = new JSlider(SwingConstants.VERTICAL, 0, Byte.MAX_VALUE, 0);
      sliderReverb = new JSlider(SwingConstants.VERTICAL, 0, Byte.MAX_VALUE, 0);
      sliderChorus = new JSlider(SwingConstants.VERTICAL, 0, Byte.MAX_VALUE, 0);

      // draw tick marks
      sliderVolume.setPaintTicks(true);
      sliderReverb.setPaintTicks(true);
      sliderChorus.setPaintTicks(true);

      // set the distance between each tick mark
      sliderVolume.setMajorTickSpacing(31);
      sliderReverb.setMajorTickSpacing(31);
      sliderChorus.setMajorTickSpacing(31);

      // set the height of the slider
      Dimension dimension = new Dimension(61, 60);
      sliderVolume.setPreferredSize(dimension);
      sliderReverb.setPreferredSize(dimension);
      sliderChorus.setPreferredSize(dimension);

      // set the slider tick mark labels
      Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
      labels.put(0  , new JLabel("0" ));
      labels.put(63 , new JLabel("5" ));
      labels.put(127, new JLabel("10"));
      sliderVolume.setPaintLabels(true);
      sliderReverb.setPaintLabels(true);
      sliderChorus.setPaintLabels(true);
      sliderVolume.setLabelTable(labels);
      sliderReverb.setLabelTable(labels);
      sliderChorus.setLabelTable(labels);

      // layout the components
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      // layout the components horizontally
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
               .addComponent(labelVolume)
               .addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(SLIDER_GAP_WIDTH, SLIDER_GAP_WIDTH, SLIDER_GAP_WIDTH)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
               .addComponent(labelReverb)
               .addComponent(sliderReverb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(SLIDER_GAP_WIDTH, SLIDER_GAP_WIDTH, SLIDER_GAP_WIDTH)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
               .addComponent(labelChorus)
               .addComponent(sliderChorus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            )
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {sliderVolume, sliderReverb, sliderChorus});

      // layout the components vertically
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
               .addComponent(sliderVolume, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(sliderReverb, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(sliderChorus, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))                               // todo: 74?
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelVolume)
               .addComponent(labelReverb)
               .addComponent(labelChorus))
            .addContainerGap())
      );

      return panel;
   }

   /**
    * @return the panel containing the tuning spinners and sound buttons.
    */
   protected JPanel createPanelTuning()
   {
      JPanel panel = new JPanel();

      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.instruments.border.title.tuning")));

      // create the spinner and button controls
      spinnerNote = new JSpinner[Instrument.MAX_NUM_STRINGS];
      buttonNote  = new JButton [Instrument.MAX_NUM_STRINGS];

      ImageIcon image = new ImageIcon(getClass().getResource(ResourceBundle.getString("dialog.instruments.image.speaker")));
      for(int i=0; i<Instrument.MAX_NUM_STRINGS; ++i)
      {
         spinnerNote[i] = new JSpinner(new SpinnerNoteModel(i, selectedInstrument == null ? null : selectedInstrument.getTuning()));
         buttonNote [i] = new JButton(image);
         buttonNote [i].setActionCommand("note_sound_button_" + i);
         buttonNote [i].addActionListener(this);
         spinnerNote[i].addChangeListener(this);
      }

      // create the controls for the tuning offset
      JLabel labelOffset  = new JLabel(ResourceBundle.getString("dialog.instruments.label.tuning_offset"));
      spinnerTuningOffset = new JSpinner(new SpinnerNumberModel(selectedInstrument == null ? 0 : (int)selectedInstrument.getTuning().getMusicNotationOffset() , (int)Tuning.MIN_MUSIC_NOTATION_OFFSET, (int)Tuning.MAX_MUSIC_NOTATION_OFFSET, 1));
      spinnerTuningOffset.setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.tuning_offset"));
      // disable keyboard editor in the spinner
      JFormattedTextField tf = ((JSpinner.DefaultEditor)spinnerTuningOffset.getEditor()).getTextField();
      tf.setEditable(false);
      // the previous call sets the background to a disabled color (usually gray). to change this disabled color, reset the background color.
      tf.setBackground(Color.white);


      // layout the components
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      // layout the components horizontally
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(spinnerNote[0], GroupLayout.PREFERRED_SIZE, TUNING_SPINNER_NOTE_WIDTH, GroupLayout.PREFERRED_SIZE)  //GroupLayout.DEFAULT_SIZE
                  .addGap(TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP)
                  .addComponent( buttonNote[0]))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(spinnerNote[1], GroupLayout.PREFERRED_SIZE, TUNING_SPINNER_NOTE_WIDTH, GroupLayout.PREFERRED_SIZE)
                  .addGap(TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP)
                  .addComponent( buttonNote[1]))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(spinnerNote[2], GroupLayout.PREFERRED_SIZE, TUNING_SPINNER_NOTE_WIDTH, GroupLayout.PREFERRED_SIZE)
                  .addGap(TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP)
                  .addComponent( buttonNote[2]))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(spinnerNote[3], GroupLayout.PREFERRED_SIZE, TUNING_SPINNER_NOTE_WIDTH, GroupLayout.PREFERRED_SIZE)
                  .addGap(TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP)
                  .addComponent( buttonNote[3]))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(spinnerNote[4], GroupLayout.PREFERRED_SIZE, TUNING_SPINNER_NOTE_WIDTH, GroupLayout.PREFERRED_SIZE)
                  .addGap(TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP)
                  .addComponent( buttonNote[4]))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(spinnerNote[5], GroupLayout.PREFERRED_SIZE, TUNING_SPINNER_NOTE_WIDTH, GroupLayout.PREFERRED_SIZE)
                  .addGap(TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP)
                  .addComponent( buttonNote[5]))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(spinnerNote[6], GroupLayout.PREFERRED_SIZE, TUNING_SPINNER_NOTE_WIDTH, GroupLayout.PREFERRED_SIZE)
                  .addGap(TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP)
                  .addComponent( buttonNote[6]))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(labelOffset)
                  .addGap(TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP, TUNING_SPINNER_SOUND_GAP)
                  .addComponent(spinnerTuningOffset, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {spinnerNote[0], spinnerNote[1], spinnerNote[2], spinnerNote[3], spinnerNote[4], spinnerNote[5], spinnerNote[6]});
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonNote [0], buttonNote [1], buttonNote [2], buttonNote [3], buttonNote [4], buttonNote [5], buttonNote [6]});

      // layout the components vertically
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(spinnerNote[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(buttonNote [0]))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(spinnerNote[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(buttonNote [1]))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(spinnerNote[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(buttonNote [2]))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(spinnerNote[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(buttonNote [3]))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(spinnerNote[4], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(buttonNote [4]))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(spinnerNote[5], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(buttonNote [5]))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(spinnerNote[6], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(buttonNote [6]))
               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)

            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelOffset)
               .addComponent(spinnerTuningOffset, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonNote[0], spinnerNote[0]});
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonNote[0], spinnerNote[1]});
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonNote[0], spinnerNote[2]});
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonNote[0], spinnerNote[3]});
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonNote[0], spinnerNote[4]});
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonNote[0], spinnerNote[5]});
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonNote[0], spinnerNote[6]});

      return panel;
   }

   /**
    * @return the tuning the user has selected via the tuning name combo box, tuning note spinners, and the tuning offset spinner.
    */
   protected Tuning getTuning()
   {
      Midi.Note[] notes  = new Midi.Note[(Integer)spinnerNumStrings.getValue()];
      String      name   = (String)comboBoxTuning.getSelectedItem();
      byte        offset = (byte)(((Integer)spinnerTuningOffset.getValue()).intValue());

      for(int i=0; i<notes.length; ++i)
         notes[i] = (Midi.Note)spinnerNote[i].getValue();

      return new Tuning(name, offset, notes);
   }

   /**
    * implements the ItemListener interface and is called whenever the user selects a different tuning from the tuning combo box.
    * <br/><br/>
    * @param event   the tuning combo box selection event.
    */
   public void itemStateChanged(ItemEvent event)
   {
      if (event.getSource() == comboBoxTuning && event.getStateChange() == ItemEvent.SELECTED)
      {
         Tuning tuning = TuningDictionary.getTuning(selectedInstrument.getType(), ((Integer)spinnerNumStrings.getValue()).intValue(), (String)comboBoxTuning.getSelectedItem());
         // set the currently selected instrument's tuning to the new tuning
         selectedInstrument.setTuning(tuning);
         // display the notes in the tuning
         updateTuningNoteControls(selectedInstrument.getType(), tuning);
      }
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
  public void ok()
   {
      // do nothing - this method is actually implemented in a dervied class.
   }

   /**
    * plays a sample sound of the specified instrument's midi sound.
    * <br/><br/>
    * @param sound   midi sound to play
    * @param note    midi note  to play.  if null, middle c is used.
    */
   protected void playSound(Midi.Sound sound, Midi.Note note)
   {
      Synthesizer synthesizer = null;
      try
      {
         if (note == null)
            note = Midi.Note.Middle_C;

         synthesizer = MidiSystem.getSynthesizer();
         synthesizer.open();
         MidiChannel channel = synthesizer.getChannels()[Midi.Channel.Channel_1.ordinal()];
         channel.programChange(sound.ordinal());
         channel.noteOn(note.pitch(), 120);
         Thread.sleep(250);
         channel.noteOff(note.pitch());
      }
      catch (MidiUnavailableException ex)
      {
         ex.printStackTrace();
      }
      catch (InterruptedException ex)
      {
         // don't do anything
      }
      finally
      {
         if (synthesizer != null)
            synthesizer.close();
      }
   }

   /**
    * implements the ChangeListener interface, and is called whenever the <i>number of strings</i> or one of the <i>tuning note</i> spinners
    * changes value.
    * <br/><br/>
    * @param event   the spinner state change event.
    */
   public void stateChanged(ChangeEvent event)
   {
      if (event.getSource() == spinnerNumStrings)
      {
         int numStrings = ((Integer)spinnerNumStrings.getValue()).intValue();
         ArrayList<Tuning> tunings = TuningDictionary.getTunings(selectedInstrument.getType(), numStrings);
         System.out.println("found " + tunings.size() + " tunings");
         // todo: add code here to enable the correct number of tuning notes and select the correct tuning
         updateTuningNameComboBox();
      }
      else if (event.getSource() == spinnerCapo)
      {
         // no code necessary
      }
      else
      {
         // the user changed one of the notes in the tuning
         updateTuningNameComboBox();
      }
   }

   /**
    * updates all the dialog box controls with the values from the instrument currently selected in the instruments list table.
    * <br/><br/>
    * @param selectedInstrument   the instrument whose attributes are used to the values in the dialog box controls.
    */
   protected void updateAllControls(Instrument selectedInstrument)
   {
      if (selectedInstrument == null)
      {
         // set the instrument name text field and disable it
         textFieldName.setText("");
         textFieldName.setEnabled(false);

         // set the values of the combo boxes
         comboBoxSound  .setModel(new ComboBoxModelSound  (null));
         comboBoxTuning .setModel(new ComboBoxModelTuning (null));
         comboBoxDrumMap.setModel(new ComboBoxModelDrumMap(null));

         // disable the combo boxes
         comboBoxSound  .setEnabled(false);
         comboBoxTuning .setEnabled(false);
         comboBoxDrumMap.setEnabled(false);

         // set the values of the number of strings and capo spinners
         spinnerNumStrings.setModel(new SpinnerNumStringsModel(null));
         spinnerCapo      .setModel(new SpinnerCapoModel      (null));
         ((JSpinner.DefaultEditor)spinnerCapo.getEditor()).getTextField().setEditable(false);

         // disable the number of strings and capo
         spinnerCapo      .setEnabled(false);
         spinnerNumStrings.setEnabled(false);

         // set the values of the sliders
         sliderPan   .setValue(Byte.MAX_VALUE/2);
         sliderVolume.setValue(0);
         sliderReverb.setValue(0);
         sliderChorus.setValue(0);

         // disable the sliders
         sliderPan   .setEnabled(false);
         sliderVolume.setEnabled(false);
         sliderReverb.setEnabled(false);
         sliderChorus.setEnabled(false);

         // set the notes in the tuning note spinners
         for(int i=0; i<Instrument.MAX_NUM_STRINGS; ++i)
            spinnerNote[i].setModel(new SpinnerNoteModel(i, null));

         // disable the tuning note spinners and the sound buttons
         for(int i=0; i<Instrument.MAX_NUM_STRINGS; ++i)
         {
            spinnerNote[i].setEnabled(false);
            buttonNote [i].setEnabled(false);
         }
         // disable the tuning offset spinner
         spinnerTuningOffset.setEnabled(false);
      }
      else
      {
         // determine if the selected instrument is a drum
         boolean isDrum = selectedInstrument.getType() == Instrument.Type.Drums;

         // set the instrument name text field and enable it
         textFieldName.setText(selectedInstrument.getName());
         textFieldName.setEnabled(true);

         // set the values of the combo boxes
         comboBoxSound  .setModel(new ComboBoxModelSound  (selectedInstrument));
         comboBoxTuning .setModel(new ComboBoxModelTuning (selectedInstrument));
         comboBoxDrumMap.setModel(new ComboBoxModelDrumMap(selectedInstrument));

         // enable\disable the combo boxes depending on the type of instrument
         comboBoxSound    .setEnabled(!isDrum);
         comboBoxTuning   .setEnabled(!isDrum);
         comboBoxDrumMap  .setEnabled( isDrum);

         // set the values of the number of strings and capo spinners
         spinnerNumStrings.setModel(new SpinnerNumStringsModel(selectedInstrument));
         spinnerCapo      .setModel(new SpinnerCapoModel      (selectedInstrument));
         // disable keyboard editors in the spinners
         JFormattedTextField tf = ((JSpinner.DefaultEditor)spinnerNumStrings.getEditor()).getTextField();
         tf.setEditable(false);
         // the previous call sets the background to a disabled color (usually gray). to change this disabled color, reset the background color.
         tf.setBackground(Color.white);
         tf = ((JSpinner.DefaultEditor)spinnerCapo.getEditor()).getTextField();
         tf.setEditable(false);
         // the previous call sets the background to a disabled color (usually gray). to change this disabled color, reset the background color.
         tf.setBackground(Color.white);

         // enable/disable the number of strings and capo spinners depending on the type of instrument
         spinnerNumStrings.setEnabled(!isDrum);
         spinnerCapo      .setEnabled(!isDrum);

         // set the values of the sliders
         sliderPan   .setValue(selectedInstrument.getPan   ());
         sliderVolume.setValue(selectedInstrument.getInitialVolume().value());
         sliderReverb.setValue(selectedInstrument.getReverb());
         sliderChorus.setValue(selectedInstrument.getChorus());

         // enable the sliders
         sliderPan   .setEnabled(true);
         sliderVolume.setEnabled(true);
         sliderReverb.setEnabled(true);
         sliderChorus.setEnabled(true);

         // set the notes in the tuning note spinners
         for(int i=0; i<Instrument.MAX_NUM_STRINGS; ++i)
            spinnerNote[i].setModel(new SpinnerNoteModel(i, selectedInstrument.getTuning()));

         // enable the used tuning note spinners and the sound buttons depending on the instrument type
         for(int i=0; i<selectedInstrument.getTuning().getNumStrings(); ++i)
         {
            spinnerNote[i].setEnabled(!isDrum);
            buttonNote [i].setEnabled(!isDrum);
         }

         // disable the unused tuning note spinners and the sound buttons
         for(int i=selectedInstrument.getTuning().getNumStrings(); i<Instrument.MAX_NUM_STRINGS; ++i)
         {
            spinnerNote[i].setEnabled(false);
            buttonNote [i].setEnabled(false);
         }

         // enable the tuning offset spinner
         spinnerTuningOffset.setEnabled(!isDrum);
      }
   }

   /**
    * this method is called whenever the user manually changes one of the tuning note spinner controls to create a custom tuning.
    * When this occurs, the tuning name combo box is updated to reflect the new change to the tuning.
    */
   protected void updateTuningNameComboBox()
   {
      // retrieve the note values from the tuning note spinner controls
      Tuning tuning = getTuning();

      // see if the tuning is in the tuning dictionary
      Tuning t = TuningDictionary.getTuning(selectedInstrument.getType(), tuning.getNotes());

      tuning.setName(t == null ? "custom" : t.getName());
      comboBoxTuning.setModel(new ComboBoxModelTuning(selectedInstrument.getType(), tuning));
   }

   /**
    * this method is called whenever a user selects a new tuning from the tuning dictionary combo box or whenever the user changes the number of strings in the tuning.
    * this method simply updates the tuning note spinner controls to reflect the new tuning.
    * <br/><br/>
    * @param type     type of instrument for which the tuning is being displayed.
    * @param tuning   the tuning to display in the tuning note spinner controls.
    */
   protected void updateTuningNoteControls(Instrument.Type type, Tuning tuning)
   {
      if (tuning == null)
      {
         // set the notes in the tuning note spinners to null
         for(int i=0; i<spinnerNote.length; ++i)
            spinnerNote[i].setModel(new SpinnerNoteModel(i, null));

         // disable all the tuning note spinners and their associated sound buttons
         for(int i=0; i<spinnerNote.length; ++i)
         {
            spinnerNote[i].setEnabled(false);
            buttonNote [i].setEnabled(false);
         }
         // disable the tuning offset spinner
         spinnerTuningOffset.setValue(0);
         spinnerTuningOffset.setEnabled(false);
      }
      else
      {
         // set the notes in the tuning note spinners to the new tuning
         for(int i=0; i<spinnerNote.length; ++i)
            spinnerNote[i].setModel(new SpinnerNoteModel(i, tuning));

         // enable the used tuning note spinners and their associated sound buttons depending on the instrument type
         boolean isDrum = type != null && type == Instrument.Type.Drums;
         for(int i=0; i<tuning.getNumStrings(); ++i)
         {
            spinnerNote[i].setEnabled(!isDrum);
            buttonNote [i].setEnabled(!isDrum);
         }

         // disable the unused tuning note spinners and the sound buttons
         for(int i=tuning.getNumStrings(); i<spinnerNote.length; ++i)
         {
            spinnerNote[i].setEnabled(false);
            buttonNote [i].setEnabled(false);
         }

         // enable the tuning offset spinner
         spinnerTuningOffset.setEnabled(!isDrum);
         spinnerTuningOffset.setValue((int)tuning.getMusicNotationOffset());
      }
   }
}
