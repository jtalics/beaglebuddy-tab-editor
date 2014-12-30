/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.name;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.dialog.CustomFocusTraversalPolicy;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Accidental;
import com.beaglebuddy.tab.model.Note;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;




/**
 * This class is a dialog box which allows a user to specify the name of a chord.
 */
public class ChordNameDialog extends BaseDialog implements ItemListener, ListSelectionListener
{
   // class members
   private final static int      CHORD_NAME_TEXT_FIELD_WIDTH    = 200;
   private final static int      ROOT_NOTE_LIST_WIDTH           = 50;
   private final static int      ACCIDENTAL_TOGGLE_BUTTON_WIDTH = 20;
   private final static int      BASS_NOTE_LIST_WIDTH           = 50;
   private final static String[] SHARP_NOTES                    = Note.getNoteStringList(Accidental.Sharp);
   private final static String[] FLAT_NOTES                     = Note.getNoteStringList(Accidental.Flat );

   // data members
   protected ChordName chordName;               // model - the name of the chord and how it is constructed
   protected boolean   initialized;             // whether all the dialog controls have been created and initialized
   private   boolean   bassNoteExplicitlySet;   // this is set to true if a user explicitly selects a bass note

   // controls
   private JTextField    textFieldChordName;    // name of chord
   private JList         listRootNote;          // list of root notes
   private JToggleButton toggleButtonSharp;     // sharp toggle button
   private JToggleButton toggleButtonFlat;      // flat  toggle button
   private JComboBox     comboBoxBassNote;      // bass note combo box
   private JList         listChordType;         // Major, Minor, ..., Augmented
   private JList         listExt;               // 9, 11, and 13 extensions
   private JCheckBox     checkBoxAdd2;          // addX check boxes
   private JCheckBox     checkBoxAdd4;
   private JCheckBox     checkBoxAdd6;
   private JCheckBox     checkBoxAdd9;
   private JCheckBox     checkBoxAdd11;
   private JComboBox     comboBoxAlt5th;        // altered 5th - # or b
   private JComboBox     comboBoxAlt9th;
   private JComboBox     comboBoxAlt11th;
   private JComboBox     comboBoxAlt13th;
   private JComboBox     comboBoxFret;          // I, II, III, IV, ..., XX
   private JComboBox     comboBoxType;          // type 1, type 2, ..., type 8





   /**
    * constructor.
    * <br/><br/>
    * @param frame      the main application frame.
    * @param chordName  the name of the chord to be edited.
    */
   public ChordNameDialog(MainFrame frame, String chordName)
   {
      this(frame, chordName, ResourceBundle.getString("dialog.chord_name.title"), "dialog.chord_name");
   }

   /**
    * constructor.
    * <br/><br/>
    * @param frame      the main application frame.
    * @param chordName  the name of the chord to be edited.
    * @param title      the title of the dialog box.
    * @param helpId     topic id of the help file to display when the user presses the F1 key or the <i>Help</i> button.
    */
   public ChordNameDialog(MainFrame frame, String chordName, String title, String helpId)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), helpId);

      // parse the chord name the user will be editing
      this.chordName = chordName == null ? new ChordName() : new ChordName(chordName);

      // set the dialog box title
      setTitle(title);

      // mark the dialog box as not initialized
      initialized = false;
   }


   /**
    * implements the ActionListener interface.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   public void actionPerformed(ActionEvent event)
   {
      String  command = event.getActionCommand();

      if (command.equals("#"))
      {
         int rootNoteIndex = listRootNote.getSelectedIndex();
         int bassNoteIndex = comboBoxBassNote.getSelectedIndex();

         toggleButtonFlat.setSelected(false);
         listRootNote.setListData(SHARP_NOTES);
         listRootNote.setSelectedIndex(rootNoteIndex);
         comboBoxBassNote.setModel(new DefaultComboBoxModel(SHARP_NOTES));
         comboBoxBassNote.setSelectedIndex(bassNoteIndex);
      }
      else if (command.equals("b"))
      {
         int rootNoteIndex = listRootNote.getSelectedIndex();
         int bassNoteIndex = comboBoxBassNote.getSelectedIndex();

         toggleButtonSharp.setSelected(false);
         listRootNote.setListData(FLAT_NOTES);
         listRootNote.setSelectedIndex(rootNoteIndex);
         comboBoxBassNote.setModel(new DefaultComboBoxModel(FLAT_NOTES));
         comboBoxBassNote.setSelectedIndex(bassNoteIndex);
      }
      else if (command.equals("add2"))
      {
         checkBoxAdd9.setSelected(false);
         updateModel();
      }
      else if (command.equals("add4"))
      {
         checkBoxAdd11.setSelected(false);
         updateModel();
      }
      else if (command.equals("add6"))
      {
         updateModel();
      }
      else if (command.equals("add9"))
      {
         checkBoxAdd2.setSelected(false);
         updateModel();
      }
      else if (command.equals("add11"))
      {
         checkBoxAdd4.setSelected(false);
         updateModel();
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
      JPanel controlsPanel = getControlsPanel();
      addComponents(controlsPanel, "");
   }

   /**
    * this method add the swing gui controls to the specified panel.
    * <br/><br/>
    * @param panel  panel to which gui controls will be added.
    * @param title  title to be displayed on the top left corner of the panel border.
    */
   protected void addComponents(JPanel panel, String title)
   {
      JPanel panel0 = createPanelChordName();
      JPanel panel1 = createPanel1();
      JPanel panel2 = createPanel2();
      JPanel panel3 = createPanel3();
      JPanel panel4 = createPanel4();

      // set the layout manager
      GroupLayout  layout = new GroupLayout(panel);
      panel.setLayout(layout);
      panel.setBorder(BorderFactory.createTitledBorder(title));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
               .addComponent(panel0   , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panel0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
      );

      // mark the dialog box as initialized
      initialized = true;
      valueChanged(new ListSelectionEvent(listChordType, listChordType.getSelectedIndex(), listChordType.getSelectedIndex(), false));
      valueChanged(new ListSelectionEvent(listExt      , listExt      .getSelectedIndex(), listExt      .getSelectedIndex(), false));

      // invoke the layout manager
      pack();

      // exclude the chord name text field from the controls to which users can move (tab) to
      setFocusTraversalPolicy();

      // set the root note list box as initially having the focus
      listRootNote.requestFocusInWindow();
   }

   /**
    * @return the panel containing the root note panel and the bass note panel.
    */
   private JPanel createPanel1()
   {
      // determine whether the chord name contains a sharp or flat in its name
      Accidental accidental = chordName.getRootNote().length() == 1 ? Accidental.Sharp : (chordName.getRootNote().charAt(1) == '#' ? Accidental.Sharp : Accidental.Flat);

      // create the panels
      JPanel panel = new JPanel();
      JPanel panelRootNote = createPanelRootNote(accidental);
      JPanel panelBassNote = createPanelBassNote(accidental);

      bassNoteExplicitlySet = !chordName.getRootNote().equals(chordName.getBassNote());

      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelRootNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(panelBassNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelRootNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(panelBassNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      return panel;
   }

   /**
    * @return the panel containing the chord type list box.
    */
   private JPanel createPanel2()
   {
      JPanel panel     = new JPanel();
      JPanel panelType = createPanelType();

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(panelType, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      return panel;
   }

   /**
    * @return the panel containing the extensions, additions, and alterations panels.
    */
   private JPanel createPanel3()
   {
      JPanel panelVariations  = new JPanel();
      JPanel panelExtensions  = createPanelExtensions();
      JPanel panelAdditions   = createPanelAdditions();
      JPanel panelAlterations = createPanelAlterations();

      // set the layout manager
      GroupLayout panelVariationsLayout = new GroupLayout(panelVariations);
      panelVariations.setLayout(panelVariationsLayout);

      panelVariationsLayout.setHorizontalGroup(
         panelVariationsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelVariationsLayout.createSequentialGroup()
            .addGroup(panelVariationsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
               .addComponent(panelExtensions , GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(panelAdditions  , GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(panelAlterations, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
      );
      panelVariationsLayout.setVerticalGroup(
         panelVariationsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelVariationsLayout.createSequentialGroup()
            .addComponent(panelExtensions , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(panelAdditions  , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(panelAlterations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      return panelVariations;
   }

   /**
    * @return the panel containing the additional naming.
    */
   private JPanel createPanel4()
   {
      JPanel panel                 = new JPanel();
      JPanel panelAdditionalNaming = createPanelAdditionalNaming();

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(panelAdditionalNaming, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelAdditionalNaming, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      return panel;
   }

   /**
    * @return the panel containing the additional naming.
    */
   private JPanel createPanelAdditionalNaming()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_name.additional_naming.title")));

      // create the fret combo box
      String[] frets     = {"", "open", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI","XVII","XVIII", "XIX", "XX"};
      JLabel   labelFret = new JLabel("Fret");
      comboBoxFret       = new JComboBox(frets);
      comboBoxFret.addItemListener(this);
      comboBoxFret.setSelectedItem(chordName.getFret());

      // create the type combo box
      String[] types     = {"", ResourceBundle.format("dialog.chord_name.type.text", 2), ResourceBundle.format("dialog.chord_name.type.text", 3),
                                ResourceBundle.format("dialog.chord_name.type.text", 4), ResourceBundle.format("dialog.chord_name.type.text", 5),
                                ResourceBundle.format("dialog.chord_name.type.text", 6), ResourceBundle.format("dialog.chord_name.type.text", 7),
                                ResourceBundle.format("dialog.chord_name.type.text", 8)};

      JLabel   labelType = new JLabel("Type");
      comboBoxType       = new JComboBox(types);
      comboBoxType.addItemListener(this);
      if (chordName.getType() == null || chordName.getType().isEmpty())
         comboBoxType.setSelectedIndex(0);
      else
         comboBoxType.setSelectedItem(chordName.getType());

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

       layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(labelFret)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxFret, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))    // 70 middle
               .addGroup(layout.createSequentialGroup()
                  .addComponent(labelType)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))))  // 70 middle
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {labelFret   , labelType   });
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {comboBoxFret, comboBoxType});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelFret)
               .addComponent(comboBoxFret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelType)
               .addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {labelFret   , labelType   });
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {comboBoxFret, comboBoxType});

      return panel;
   }

   /**
    * @return the panel containing the chord additions.
    */
   private JPanel createPanelAdditions()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_name.additions.title")));

      checkBoxAdd2  = new JCheckBox(ResourceBundle.format("dialog.chord_name.additions.add.label", 2 ));
      checkBoxAdd4  = new JCheckBox(ResourceBundle.format("dialog.chord_name.additions.add.label", 4 ));
      checkBoxAdd6  = new JCheckBox(ResourceBundle.format("dialog.chord_name.additions.add.label", 6 ));
      checkBoxAdd9  = new JCheckBox(ResourceBundle.format("dialog.chord_name.additions.add.label", 9 ));
      checkBoxAdd11 = new JCheckBox(ResourceBundle.format("dialog.chord_name.additions.add.label", 11));

      checkBoxAdd2 .setActionCommand("add2" );
      checkBoxAdd4 .setActionCommand("add4" );
      checkBoxAdd6 .setActionCommand("add6" );
      checkBoxAdd9 .setActionCommand("add9" );
      checkBoxAdd11.setActionCommand("add11");

      checkBoxAdd2 .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxAdd4 .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxAdd6 .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxAdd9 .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxAdd11.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

      checkBoxAdd2 .setMargin(new Insets(0, 0, 0, 0));
      checkBoxAdd4 .setMargin(new Insets(0, 0, 0, 0));
      checkBoxAdd6 .setMargin(new Insets(0, 0, 0, 0));
      checkBoxAdd9 .setMargin(new Insets(0, 0, 0, 0));
      checkBoxAdd11.setMargin(new Insets(0, 0, 0, 0));

      checkBoxAdd2 .addActionListener(this);
      checkBoxAdd4 .addActionListener(this);
      checkBoxAdd6 .addActionListener(this);
      checkBoxAdd9 .addActionListener(this);
      checkBoxAdd11.addActionListener(this);

      checkBoxAdd2 .setSelected(chordName.getAdd2nd ());
      checkBoxAdd4 .setSelected(chordName.getAdd4th ());
      checkBoxAdd6 .setSelected(chordName.getAdd6th ());
      checkBoxAdd9 .setSelected(chordName.getAdd9th ());
      checkBoxAdd11.setSelected(chordName.getAdd11th());

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                     .addComponent(checkBoxAdd2)
                     .addComponent(checkBoxAdd4)
                     .addComponent(checkBoxAdd6))
                  .addGap(15, 15, 15)
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                     .addComponent(checkBoxAdd9)
                     .addComponent(checkBoxAdd11)))))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {checkBoxAdd2, checkBoxAdd4, checkBoxAdd6, checkBoxAdd9, checkBoxAdd11});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(checkBoxAdd2)
               .addComponent(checkBoxAdd9))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(checkBoxAdd4)
               .addComponent(checkBoxAdd11))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkBoxAdd6))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {checkBoxAdd2, checkBoxAdd4, checkBoxAdd6, checkBoxAdd9, checkBoxAdd11});

      return panel;
   }

   /**
    * @return the panel containing the chord alterations.
    */
   private JPanel createPanelAlterations()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_name.alterations.title")));

      String[] accidentals  = {"", "b", "#"};
      JLabel   labelAlt5th  = new JLabel(ResourceBundle.getString("dialog.chord_name.alterations.5th" ));
      JLabel   labelAlt9th  = new JLabel(ResourceBundle.getString("dialog.chord_name.alterations.9th" ));
      JLabel   labelAlt11th = new JLabel(ResourceBundle.getString("dialog.chord_name.alterations.11th"));
      JLabel   labelAlt13th = new JLabel(ResourceBundle.getString("dialog.chord_name.alterations.13th"));

      comboBoxAlt5th  = new JComboBox(accidentals);
      comboBoxAlt9th  = new JComboBox(accidentals);
      comboBoxAlt11th = new JComboBox(accidentals);
      comboBoxAlt13th = new JComboBox(accidentals);

      comboBoxAlt5th .addItemListener(this);
      comboBoxAlt9th .addItemListener(this);
      comboBoxAlt11th.addItemListener(this);
      comboBoxAlt13th.addItemListener(this);

      comboBoxAlt5th .setSelectedIndex(chordName.getAlt5th () == Accidental.Natural ? 0 : (chordName.getAlt5th () == Accidental.Flat ? 1 : 2));
      comboBoxAlt9th .setSelectedIndex(chordName.getAlt9th () == Accidental.Natural ? 0 : (chordName.getAlt9th () == Accidental.Flat ? 1 : 2));
      comboBoxAlt11th.setSelectedIndex(chordName.getAlt11th() == Accidental.Natural ? 0 : (chordName.getAlt11th() == Accidental.Flat ? 1 : 2));
      comboBoxAlt13th.setSelectedIndex(chordName.getAlt13th() == Accidental.Natural ? 0 : (chordName.getAlt13th() == Accidental.Flat ? 1 : 2));

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(labelAlt5th)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxAlt5th, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(labelAlt9th)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxAlt9th, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 10)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addComponent(labelAlt11th)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxAlt11th, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
               .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addComponent(labelAlt13th)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxAlt13th, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {comboBoxAlt5th, comboBoxAlt9th, comboBoxAlt11th, comboBoxAlt13th});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelAlt5th)
               .addComponent(comboBoxAlt5th , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(labelAlt11th)
               .addComponent(comboBoxAlt11th, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelAlt9th)
               .addComponent(comboBoxAlt9th , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(labelAlt13th)
               .addComponent(comboBoxAlt13th, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {comboBoxAlt5th, comboBoxAlt9th, comboBoxAlt11th, comboBoxAlt13th});

      return panel;
   }

   /**
    * @return the panel containing the bass note combo box.
    * <br/><br/>
    * @param accidental   whether to include sharped notes or flatted notes in the notes list.
    */
   private JPanel createPanelBassNote(Accidental accidental)
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_name.bass_note.title")));

      // create the bass note combo box
      comboBoxBassNote = new JComboBox(accidental == Accidental.Sharp ? SHARP_NOTES : FLAT_NOTES);
      comboBoxBassNote.addItemListener(this);
      comboBoxBassNote.setSelectedItem(chordName.getBassNote());

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(comboBoxBassNote, GroupLayout.PREFERRED_SIZE, BASS_NOTE_LIST_WIDTH, GroupLayout.PREFERRED_SIZE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(comboBoxBassNote, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      return panel;
   }

   /**
    * @return the panel containing the resulting chord name.
    */
   private JPanel createPanelChordName()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(""));

      JLabel labelChordName = new JLabel("chord name");
      textFieldChordName = new JTextField(chordName.toString());
      textFieldChordName.setEditable(false);
      textFieldChordName.setBackground(Color.white);       // the previous call sets the background to a disabled color (usually gray). to change this disabled color, reset the background color.

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(labelChordName)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textFieldChordName, GroupLayout.PREFERRED_SIZE, CHORD_NAME_TEXT_FIELD_WIDTH, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(150, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(labelChordName)
            .addComponent(textFieldChordName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      return panel;
   }

   /**
    * @return the panel containing the chord extensions.
    */
   private JPanel createPanelExtensions()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_name.extensions.title")));

      String[] extensions = {" ",
                             ResourceBundle.getString("dialog.chord_name.extensions.label.9th" ),
                             ResourceBundle.getString("dialog.chord_name.extensions.label.11th"),
                             ResourceBundle.getString("dialog.chord_name.extensions.label.13th")};
      listExt = new JList(extensions);
      listExt.addListSelectionListener(this);
      listExt.setSelectedIndex(chordName.getExtension().ordinal());
      listExt.setVisibleRowCount(extensions.length);
      listExt.setEnabled(chordName.has7th());
      JScrollPane scrollPaneExt = new JScrollPane();
      scrollPaneExt.setViewportView(listExt);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(scrollPaneExt, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(scrollPaneExt, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
      );

      return panel;
   }

   /**
    * @return the panel containing the root note list and sharp\flat toggle buttons.
    * <br/><br/>
    * @param accidental   whether to include sharped notes or flatted notes in the notes list.
    */
   private JPanel createPanelRootNote(Accidental accidental)
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_name.root_note.title")));

      // create the root note combo box
      listRootNote = new JList(accidental == Accidental.Sharp ? SHARP_NOTES : FLAT_NOTES);
      listRootNote.addListSelectionListener(this);
      listRootNote.setVisibleRowCount(listRootNote.getModel().getSize());    // SHARP_NOTES.length);
      listRootNote.setSelectedValue(chordName.getRootNote(), false);
      JScrollPane scrollPaneRootNote = new JScrollPane();
      scrollPaneRootNote.setViewportView(listRootNote);

      // create the sharp and flat buttons
      toggleButtonSharp = new JToggleButton("#");
      toggleButtonFlat  = new JToggleButton("b");
      toggleButtonSharp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      toggleButtonFlat .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      toggleButtonSharp.setMargin(new Insets(0, 0, 0, 0));
      toggleButtonFlat .setMargin(new Insets(0, 0, 0, 0));
      toggleButtonSharp.addActionListener(this);
      toggleButtonFlat .addActionListener(this);
      toggleButtonSharp.setSelected(accidental == Accidental.Sharp);
      toggleButtonFlat .setSelected(accidental == Accidental.Flat );

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(scrollPaneRootNote, GroupLayout.DEFAULT_SIZE, ROOT_NOTE_LIST_WIDTH, ROOT_NOTE_LIST_WIDTH)
         .addGroup(layout.createSequentialGroup()
            .addComponent(toggleButtonSharp, ACCIDENTAL_TOGGLE_BUTTON_WIDTH, ACCIDENTAL_TOGGLE_BUTTON_WIDTH, ACCIDENTAL_TOGGLE_BUTTON_WIDTH)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(toggleButtonFlat , ACCIDENTAL_TOGGLE_BUTTON_WIDTH, ACCIDENTAL_TOGGLE_BUTTON_WIDTH, ACCIDENTAL_TOGGLE_BUTTON_WIDTH))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {toggleButtonFlat, toggleButtonSharp});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(scrollPaneRootNote, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)  // 97 middle
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(toggleButtonSharp)
               .addComponent(toggleButtonFlat)))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {toggleButtonFlat, toggleButtonSharp});

      return panel;
   }

   /**
    * @return the panel containing the chord type list box.
    */
   private JPanel createPanelType()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_name.type.title")));

      String[] types = ChordName.getChordTypeNames();
      listChordType = new JList(types);
      listChordType.addListSelectionListener(this);
      listChordType.setSelectedIndex(chordName.getChordType().ordinal());
      listChordType.setVisibleRowCount(types.length);
      JScrollPane scrollPaneType = new JScrollPane();
      scrollPaneType.setViewportView(listChordType);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(scrollPaneType, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(scrollPaneType, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
      );
      return panel;
   }

   /**
    * @return the name of the chord.
    */
   public String getChordName()
   {
      return chordName.toString();
   }

   /**
    * implements the ItemListener interface and is called whenever a user selects an entry in a combo box.
    */
   public void itemStateChanged(ItemEvent event)
   {
      JComboBox comboBox = (JComboBox)event.getSource();

      if (event.getStateChange() == ItemEvent.SELECTED)
      {
         String string = (String)event.getItem();
         int    index  = comboBox.getSelectedIndex();

         if (comboBox == comboBoxBassNote)
         {
            bassNoteExplicitlySet = !listRootNote.getSelectedValue().equals(comboBoxBassNote.getSelectedItem());
         }
         updateModel();
      }
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      updateModel();
   }

   /**
    * updates the model based on the current values of the controls.
    */
   protected void updateModel()
   {
      // while creating the controls and setting their initial values, this method is called several times.
      // however, this method wasn't meant to be called until all the controls have been created and the user starts clicking around.
      if (!initialized)
         return;

      chordName = new ChordName((String)listRootNote.getSelectedValue(),
                                (String)comboBoxBassNote.getSelectedItem(),
                                ChordName.getChordType(listChordType.getSelectedIndex()),
                                listExt.isEnabled() ? ChordName.getExtension(listExt.getSelectedIndex()) : ChordName.Extension.None,
                                checkBoxAdd2   .isEnabled() && checkBoxAdd2 .isSelected(),
                                checkBoxAdd4   .isEnabled() && checkBoxAdd4 .isSelected(),
                                checkBoxAdd6   .isEnabled() && checkBoxAdd6 .isSelected(),
                                checkBoxAdd9   .isEnabled() && checkBoxAdd9 .isSelected(),
                                checkBoxAdd11  .isEnabled() && checkBoxAdd11.isSelected(),
                                comboBoxAlt5th .isEnabled() ?  Accidental.getAccidental(comboBoxAlt5th .getSelectedIndex()) : Accidental.Natural,
                                comboBoxAlt9th .isEnabled() ?  Accidental.getAccidental(comboBoxAlt9th .getSelectedIndex()) : Accidental.Natural,
                                comboBoxAlt11th.isEnabled() ?  Accidental.getAccidental(comboBoxAlt11th.getSelectedIndex()) : Accidental.Natural,
                                comboBoxAlt13th.isEnabled() ?  Accidental.getAccidental(comboBoxAlt13th.getSelectedIndex()) : Accidental.Natural,
                                (String)comboBoxFret.getSelectedItem(),
                                (String)comboBoxType.getSelectedItem());

      // update the chord name
      textFieldChordName.setText(chordName.toString());

      // update the traversal policy
      setFocusTraversalPolicy();
   }

   /**
    * sets the focus traversal policy, which is the list of components the user is allowed to move to.
    * basically, this is all the enabled controls with the exception of the chord name text box, which is at the top of the dialog box.
    */
   private void setFocusTraversalPolicy()
   {
      Component[]          allComponents     = {listRootNote, toggleButtonSharp, toggleButtonFlat, comboBoxBassNote,
                                                listChordType, listExt,
                                                checkBoxAdd2, checkBoxAdd4, checkBoxAdd6, checkBoxAdd9, checkBoxAdd11,
                                                comboBoxAlt5th, comboBoxAlt9th, comboBoxAlt11th, comboBoxAlt13th,
                                                comboBoxFret, comboBoxType};
      ArrayList<Component> enabledComponents = new ArrayList<Component>();

      // get a list of the components that are enabled
      for(Component component : allComponents)
         if (component.isEnabled())
            enabledComponents.add(component);

      // convert the list of enabled components to an array
      Component[] components = new Component[enabledComponents.size()];
      components = enabledComponents.toArray(components);

      // specify which components the user is alloed to move to
      setFocusTraversalPolicy(new CustomFocusTraversalPolicy(components));
   }

   /**
    * implements the ListSelectionListener interface and is called whenever a user selects an entry in one of the three list boxes
    * (root note, chord type, or extension) or replaces the list elements (root note), in which case the selected index is -1.
    * <br/><br/>
    * @param event  a list box event.
    */
   public void valueChanged(ListSelectionEvent event)
   {
      JList listBox = (JList)event.getSource();

      if (!event.getValueIsAdjusting())
      {
         int index = event.getFirstIndex();
         index = event.getLastIndex();

         if (listBox == listRootNote)
         {
            index = listRootNote.getSelectedIndex();

            // unless the user has explicitly set the bass note, set the bass note to the same note as the root note.
            if (!bassNoteExplicitlySet && comboBoxBassNote != null)
               comboBoxBassNote.setSelectedIndex(index);
         }
         else if (listBox == listChordType)
         {
            index = listChordType.getSelectedIndex();

            if (initialized)
            {
               switch (ChordName.getChordType(listChordType.getSelectedIndex()))
               {
                  case Major:
                  case Minor:
                       listExt        .setEnabled      (false);
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (false);
                       comboBoxAlt11th.setEnabled      (false);
                       comboBoxAlt13th.setEnabled      (false);
                       listExt        .setSelectedIndex(0);
                       comboBoxAlt9th .setSelectedIndex(0);
                       comboBoxAlt11th.setSelectedIndex(0);
                       comboBoxAlt13th.setSelectedIndex(0);
                  break;
                  case Augmented:   // 1,  3, #5
                  case Diminished:  // 1, b3, b5
                       listExt        .setEnabled      (false);
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (false);
                       comboBoxAlt9th .setEnabled      (false);
                       comboBoxAlt11th.setEnabled      (false);
                       comboBoxAlt13th.setEnabled      (false);
                       listExt        .setSelectedIndex(0);
                       comboBoxAlt5th .setSelectedIndex(0);
                       comboBoxAlt9th .setSelectedIndex(0);
                       comboBoxAlt11th.setSelectedIndex(0);
                       comboBoxAlt13th.setSelectedIndex(0);
                  break;
                  case Suspended2nd:  // 1, 2, 5       ex: C, D, G
                       listExt        .setEnabled      (false);
                       checkBoxAdd2   .setEnabled      (false);
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (false);
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (false);
                       comboBoxAlt11th.setEnabled      (false);
                       comboBoxAlt13th.setEnabled      (false);
                       listExt        .setSelectedIndex(0);
                       checkBoxAdd2   .setSelected     (false);
                       checkBoxAdd9   .setSelected     (false);
                       comboBoxAlt9th .setSelectedIndex(0);
                       comboBoxAlt11th.setSelectedIndex(0);
                       comboBoxAlt13th.setSelectedIndex(0);
                  break;
                  case Suspended4th:  // 1, 4, 5       ex: C, F, G
                       listExt        .setEnabled      (false);
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (false);
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (false);
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (false);
                       comboBoxAlt11th.setEnabled      (false);
                       comboBoxAlt13th.setEnabled      (false);
                       listExt        .setSelectedIndex(0);
                       checkBoxAdd4   .setSelected     (false);
                       checkBoxAdd11  .setSelected     (false);
                       comboBoxAlt9th .setSelectedIndex(0);
                       comboBoxAlt11th.setSelectedIndex(0);
                       comboBoxAlt13th.setSelectedIndex(0);
                  break;
                  case Power5th:        // 1, 5       ex: C, G
                       listExt        .setEnabled      (false);
                       checkBoxAdd2   .setEnabled      (false);
                       checkBoxAdd4   .setEnabled      (false);
                       checkBoxAdd6   .setEnabled      (false);
                       checkBoxAdd9   .setEnabled      (false);
                       checkBoxAdd11  .setEnabled      (false);
                       comboBoxAlt5th .setEnabled      (false);
                       comboBoxAlt9th .setEnabled      (false);
                       comboBoxAlt11th.setEnabled      (false);
                       comboBoxAlt13th.setEnabled      (false);
                       listExt        .setSelectedIndex(0);
                       checkBoxAdd2   .setSelected     (false);
                       checkBoxAdd4   .setSelected     (false);
                       checkBoxAdd6   .setSelected     (false);
                       checkBoxAdd9   .setSelected     (false);
                       checkBoxAdd11  .setSelected     (false);
                       comboBoxAlt5th .setSelectedIndex(0);
                       comboBoxAlt9th .setSelectedIndex(0);
                       comboBoxAlt11th.setSelectedIndex(0);
                       comboBoxAlt13th.setSelectedIndex(0);
                  break;
                  case Major6th:  // 1, 3 , 5, 6     ex: C, E , G, A
                  case Minor6th:  // 1, b3, 5, 6     ex: C, Eb, G, A
                       listExt        .setEnabled      (false);
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (false);
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (false);
                       comboBoxAlt11th.setEnabled      (false);
                       comboBoxAlt13th.setEnabled      (false);
                       listExt        .setSelectedIndex(0);
                       checkBoxAdd6   .setSelected     (false);
                       comboBoxAlt9th .setSelectedIndex(0);
                       comboBoxAlt11th.setSelectedIndex(0);
                       comboBoxAlt13th.setSelectedIndex(0);
                  break;
                  case Dominant7th:    // 1, 3, 5, b7         ex: C, E , G , Bb
                       listExt        .setEnabled      (true );
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (true );
                       comboBoxAlt11th.setEnabled      (true );
                       comboBoxAlt13th.setEnabled      (true );
                  break;
                  case Major7th:       // 1,  3, 5,  7        ex: C, E , G , B
                       listExt        .setEnabled      (true );
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (true );
                       comboBoxAlt11th.setEnabled      (true );
                       comboBoxAlt13th.setEnabled      (true );
                  break;
                  case Minor7th:       // 1, b3, 5, b7        ex: C, Eb, G , Bb
                       listExt        .setEnabled      (true );
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (true );
                       comboBoxAlt11th.setEnabled      (true );
                       comboBoxAlt13th.setEnabled      (true );
                  break;
                  case MinorMajor7th:  // 1, b3, 5, 7         ex: C, Eb, G , B
                       listExt        .setEnabled      (true );
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (true );
                       comboBoxAlt11th.setEnabled      (true );
                       comboBoxAlt13th.setEnabled      (true );
                  break;
                  case Augmented7th:   // 1, 3 , #5,   7      ex: C, E , G#, B
                       listExt        .setEnabled      (true );
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (false);
                       comboBoxAlt9th .setEnabled      (true );
                       comboBoxAlt11th.setEnabled      (true );
                       comboBoxAlt13th.setEnabled      (true );
                       comboBoxAlt5th .setSelectedIndex(0);
                  break;
                  case Diminished7th: // 1, b3, b5, bb7 = 6    ex: C, Eb, Gb, A
                       listExt        .setEnabled      (true );
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (false);
                       checkBoxAdd6   .setEnabled      (false);
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (false);
                       comboBoxAlt5th .setEnabled      (false);
                       comboBoxAlt9th .setEnabled      (true );
                       comboBoxAlt11th.setEnabled      (false);
                       comboBoxAlt13th.setEnabled      (true );
                       comboBoxAlt5th .setSelectedIndex(0);
                       comboBoxAlt11th.setSelectedIndex(0);
                  break;
                  case Dominant7thSuspended2nd: // 1, 2, 5, b7    ex: C, D, G, Bb
                       listExt        .setEnabled      (true );
                       checkBoxAdd2   .setEnabled      (false);
                       checkBoxAdd4   .setEnabled      (true );
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (false);
                       checkBoxAdd11  .setEnabled      (true );
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (false);
                       comboBoxAlt11th.setEnabled      (true );
                       comboBoxAlt13th.setEnabled      (true );
                       checkBoxAdd2   .setSelected     (false);
                       checkBoxAdd9   .setSelected     (false);
                       comboBoxAlt9th .setSelectedIndex(0);
                  break;
                  case Dominant7thSuspended4th: // 1, 4, 5, b7    ex: C, F, G, Bb
                       checkBoxAdd2   .setEnabled      (true );
                       checkBoxAdd4   .setEnabled      (false);
                       checkBoxAdd6   .setEnabled      (true );
                       checkBoxAdd9   .setEnabled      (true );
                       checkBoxAdd11  .setEnabled      (false);
                       comboBoxAlt5th .setEnabled      (true );
                       comboBoxAlt9th .setEnabled      (true );
                       comboBoxAlt11th.setEnabled      (false);
                       comboBoxAlt13th.setEnabled      (true );
                       checkBoxAdd4   .setSelected     (false);
                       checkBoxAdd11  .setSelected     (false);
                       comboBoxAlt11th.setSelectedIndex(0);
                  break;
               }
            }
         }
         else if (listBox == listExt)
         {
            if (initialized)
            {
               index = listExt.getSelectedIndex();
               switch (ChordName.getExtension(listExt.getSelectedIndex()))
               {
                  case None:
                  break;
                  case Extended9th:
                       checkBoxAdd2   .setEnabled (false);
                       checkBoxAdd4   .setEnabled (true );
                       checkBoxAdd6   .setEnabled (true );
                       checkBoxAdd9   .setEnabled (false);
                       checkBoxAdd11  .setEnabled (true );
                       comboBoxAlt5th .setEnabled (true );
                       comboBoxAlt9th .setEnabled (false);
                       comboBoxAlt11th.setEnabled (true );
                       comboBoxAlt13th.setEnabled (true );
                       checkBoxAdd2   .setSelected(false);
                       checkBoxAdd9   .setSelected(false);
                       comboBoxAlt9th .setSelectedIndex(0);
                       switch (chordName.getChordType())
                       {
                          case Augmented7th:
                               comboBoxAlt5th .setEnabled (false);
                               comboBoxAlt13th.setEnabled (false);
                               comboBoxAlt5th .setSelectedIndex(0);
                               comboBoxAlt13th.setSelectedIndex(0);
                          break;
                          case Diminished7th:
                               comboBoxAlt5th .setEnabled (false);
                               comboBoxAlt11th.setEnabled (false);
                               comboBoxAlt5th .setSelectedIndex(0);
                               comboBoxAlt11th.setSelectedIndex(0);
                          break;
                       }
                  break;
                  case Extended11th:
                       checkBoxAdd2   .setEnabled (false);
                       checkBoxAdd4   .setEnabled (false);
                       checkBoxAdd6   .setEnabled (true );
                       checkBoxAdd9   .setEnabled (false);
                       checkBoxAdd11  .setEnabled (false);
                       comboBoxAlt5th .setEnabled (true );
                       comboBoxAlt9th .setEnabled (true );
                       comboBoxAlt11th.setEnabled (false);
                       comboBoxAlt13th.setEnabled (true );
                       checkBoxAdd2   .setSelected(false);
                       checkBoxAdd4   .setSelected(false);
                       checkBoxAdd9   .setSelected(false);
                       checkBoxAdd11  .setSelected(false);
                       comboBoxAlt11th.setSelectedIndex(0);
                       switch (chordName.getChordType())
                       {
                          case Augmented7th:
                               comboBoxAlt5th .setEnabled (false);
                               comboBoxAlt13th.setEnabled (false);
                               comboBoxAlt5th .setSelectedIndex(0);
                               comboBoxAlt13th.setSelectedIndex(0);
                          break;
                          case Diminished7th:
                               comboBoxAlt5th .setEnabled (false);
                               comboBoxAlt5th .setSelectedIndex(0);
                          break;
                       }
                  break;
                  case Extended13th:
                       checkBoxAdd2   .setEnabled (false);
                       checkBoxAdd4   .setEnabled (false);
                       checkBoxAdd6   .setEnabled (false);
                       checkBoxAdd9   .setEnabled (false);
                       checkBoxAdd11  .setEnabled (false);
                       comboBoxAlt5th .setEnabled (true );
                       comboBoxAlt9th .setEnabled (true );
                       comboBoxAlt11th.setEnabled (true );
                       comboBoxAlt13th.setEnabled (false);
                       checkBoxAdd2   .setSelected(false);
                       checkBoxAdd4   .setSelected(false);
                       checkBoxAdd6   .setSelected(false);
                       checkBoxAdd9   .setSelected(false);
                       checkBoxAdd11  .setSelected(false);
                       comboBoxAlt13th.setSelectedIndex(0);
                       switch (chordName.getChordType())
                       {
                          case Augmented7th:
                               comboBoxAlt5th .setEnabled (false);
                               comboBoxAlt5th .setSelectedIndex(0);
                          break;
                          case Diminished7th:
                               comboBoxAlt5th .setEnabled (false);
                               comboBoxAlt11th.setEnabled (false);
                               comboBoxAlt5th .setSelectedIndex(0);
                               comboBoxAlt11th.setSelectedIndex(0);
                          break;
                       }
                  break;
               }
            }
         }
         if (index != -1)
            updateModel();
      }
   }
}
