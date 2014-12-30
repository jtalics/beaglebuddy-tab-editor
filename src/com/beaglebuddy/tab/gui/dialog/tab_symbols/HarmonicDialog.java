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
import com.beaglebuddy.tab.model.Accidental;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Note;
import com.beaglebuddy.tab.model.attribute.note.ArtificialHarmonic;
import com.beaglebuddy.tab.model.attribute.note.NaturalHarmonic;
import com.beaglebuddy.tab.model.attribute.note.Octave;
import com.beaglebuddy.tab.model.attribute.note.NoteAttribute;
import com.beaglebuddy.tab.model.attribute.note.TappedHarmonic;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;





/**
 * This class is a dialog box which allows a user to specify how a chord is dived with the tremolo bar.
 */
public class HarmonicDialog extends BaseDialog
{
   // data members
   private Instrument.Fret fret;      // the fret the note is played on
   private NoteAttribute   harmonic;  // the harmonic attribute of the note

   // controls
   private JRadioButton radioButtonNone;
   private JRadioButton radioButtonNatural;
   private JRadioButton radioButtonArtificial;
   private JRadioButton radioButtonTapped;
   private ButtonGroup  radioButtonGroup;
   private JComboBox    comboBoxNote;
   private JComboBox    comboBoxOctave;
   private JSpinner     spinnerTappedFret;




   /**
    * constructor.
    * <br/><br/>
    * @param frame      the main application frame.
    * @param fret       the fret the note is played on.
    * @param harmonic   the harmoic to be edited.
    */
   public HarmonicDialog(MainFrame frame, Instrument.Fret fret, NoteAttribute harmonic)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.harmonic");

      // make a copy of the harmonic the user will be editing
      this.fret     = fret;
      this.harmonic = (harmonic == null ? null : harmonic.clone());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.harmonic.title"));
   }

   /**
    * implements the ActionListener interface.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String key = event.getActionCommand();

      if (key.equals("none") || key.equals("natural") || key.equals("artificial") || key.equals("tapped"))
      {
         comboBoxNote     .setEnabled(key.equals("artificial"));
         comboBoxOctave   .setEnabled(key.equals("artificial"));
         spinnerTappedFret.setEnabled(key.equals("tapped"    ));
      }
      else
         super.actionPerformed(event);
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JPanel panelType   = createPanelType  ();
      JPanel panelPitch  = createPanelPitch ();
      JPanel panelTapped = createPanelTapped(fret);

      // create the radio button group
      radioButtonGroup = new ButtonGroup();
      radioButtonGroup.add(radioButtonNone      );
      radioButtonGroup.add(radioButtonNatural   );
      radioButtonGroup.add(radioButtonArtificial);
      radioButtonGroup.add(radioButtonTapped    );

      // initialize the components
      radioButtonNone      .setSelected(harmonic == null);
      radioButtonNatural   .setSelected(harmonic != null && harmonic.getType() == NoteAttribute.Type.NaturalHarmonic   );
      radioButtonArtificial.setSelected(harmonic != null && harmonic.getType() == NoteAttribute.Type.ArtificialHarmonic);
      radioButtonTapped    .setSelected(harmonic != null && harmonic.getType() == NoteAttribute.Type.TappedHarmonic    && fret.ordinal() != Instrument.MAX_NUM_FRETS);

      comboBoxNote         .setEnabled (harmonic != null && harmonic.getType() == NoteAttribute.Type.ArtificialHarmonic);
      comboBoxOctave       .setEnabled (harmonic != null && harmonic.getType() == NoteAttribute.Type.ArtificialHarmonic);
      spinnerTappedFret    .setEnabled (harmonic != null && harmonic.getType() == NoteAttribute.Type.TappedHarmonic    && fret.ordinal() != Instrument.MAX_NUM_FRETS);

      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(panelPitch , GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(panelTapped, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
               .addComponent(panelType, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {panelPitch, panelTapped});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelType, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(panelPitch , GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(panelTapped, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {panelType, panelPitch, panelTapped});

      // invoke the layout manager
      pack();
   }

   /**
    * @return the panel for the type of harmonic.
    */
   private JPanel createPanelType()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.harmonic.title.type")));
      radioButtonNone       = new JRadioButton(ResourceBundle.getString("dialog.harmonic.radio_button.none"       ));
      radioButtonNatural    = new JRadioButton(ResourceBundle.getString("dialog.harmonic.radio_button.natural"    ));
      radioButtonArtificial = new JRadioButton(ResourceBundle.getString("dialog.harmonic.radio_button.artificial" ));
      radioButtonTapped     = new JRadioButton(ResourceBundle.getString("dialog.harmonic.radio_button.tapped_fret"));

      radioButtonNone      .setActionCommand("none"       );
      radioButtonNatural   .setActionCommand("natural"    );
      radioButtonArtificial.setActionCommand("artificial" );
      radioButtonTapped    .setActionCommand("tapped"     );

      radioButtonNone      .addActionListener(this);
      radioButtonNatural   .addActionListener(this);
      radioButtonArtificial.addActionListener(this);
      radioButtonTapped    .addActionListener(this);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(radioButtonNone)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonNatural)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonArtificial)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonTapped))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(radioButtonNone      )
            .addComponent(radioButtonNatural   )
            .addComponent(radioButtonArtificial)
            .addComponent(radioButtonTapped    ))
      );
      return panel;
   }

   /**
    * @return the panel for the pitch controls for the artificial harmonic.
    */
   private JPanel createPanelPitch()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.harmonic.title.pitch")));

      // create the note combo box
      comboBoxNote = new JComboBox(Note.getNoteStringList(Accidental.Sharp));

      // choose the initially selected note
      if (harmonic != null && harmonic.getType() == NoteAttribute.Type.ArtificialHarmonic)
      {
         Midi.Note[] pitches       = Note.getNoteList(Accidental.Sharp);
         Midi.Note   note          = ((ArtificialHarmonic)harmonic).getNote();
         int         selectedIndex = -1;
         for(int i=0; i<pitches.length && selectedIndex == -1; ++i)
         {
            if (note == pitches[i])
               selectedIndex = i;
         }
         comboBoxNote.setSelectedIndex(selectedIndex);
      }
      else
      {
         comboBoxNote.setSelectedIndex(0);
      }

      // create the octave combo box
      Octave.OctaveType[] octaves = {Octave.OctaveType.OctaveLoco, Octave.OctaveType.Octave8va, Octave.OctaveType.Octave15ma};
      comboBoxOctave = new JComboBox(octaves);
      comboBoxOctave.setSelectedIndex(0);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(comboBoxNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboBoxOctave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(comboBoxNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(comboBoxOctave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
      );
      return panel;
   }

   /**
    * @return the panel for the tapped harmonic.
    * <br/><br/>
    * @param fret  the fret the note is played on.
    */
   private JPanel createPanelTapped(Instrument.Fret fret)
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.harmonic.title.tapped_fret")));

      // create the spinner for the tapped fret
      spinnerTappedFret = new JSpinner(new SpinnerNumberModel((fret.ordinal() + 12 <= Instrument.MAX_NUM_FRETS ? fret.ordinal() + 12 : Instrument.MAX_NUM_FRETS),
                                                               fret.ordinal()      <  Instrument.MAX_NUM_FRETS ? fret.ordinal() + 1  : Instrument.MAX_NUM_FRETS,
                                                               Instrument.MAX_NUM_FRETS, 1));

      // disable keyboard editor in the spinner
      JFormattedTextField tf = ((JSpinner.DefaultEditor)spinnerTappedFret.getEditor()).getTextField();
      tf.setEditable(false);
      // the previous call sets the background to a disabled color (usually gray). to change this disabled color, reset the background color.
      tf.setBackground(Color.white);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(spinnerTappedFret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(spinnerTappedFret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      return panel;
   }

   /**
    * @return the harmonic the user edited.
    */
   public NoteAttribute getHarmonic()
   {
      return harmonic;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
           if (radioButtonNone      .isSelected()) harmonic = null;
      else if (radioButtonNatural   .isSelected()) harmonic = new NaturalHarmonic   ();
      else if (radioButtonArtificial.isSelected()) harmonic = new ArtificialHarmonic(Note.getNoteList(Accidental.Sharp)[comboBoxNote.getSelectedIndex()], (Octave.OctaveType)comboBoxOctave.getSelectedItem());
      else if (radioButtonTapped    .isSelected()) harmonic = new TappedHarmonic    (Instrument.getFret(((Integer)spinnerTappedFret.getValue()).intValue()));
   }
}
