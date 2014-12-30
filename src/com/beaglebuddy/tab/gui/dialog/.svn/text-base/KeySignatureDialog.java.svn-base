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
import com.beaglebuddy.tab.model.KeySignature;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;





/**
 * This class is a dialog box which allows a user to select a key signature.
 */
public class KeySignatureDialog extends BaseDialog implements ListSelectionListener
{
   // class members
   private static final int INDEX_MAJOR                  = 0;         // index  of "major"  choice in the list box
   private static final int INDEX_MINOR                  = 1;         // index  of "minor"  choice in the list box
   private static final int INDEX_SHARPS                 = 0;         // index  of "sharps" choice in the list box
   private static final int INDEX_FLATS                  = 1;         // index  of "flats"  choice in the list box

   private static final int COLUMN_KEY_NAME              = 0;         // index  of the key name column in the table
   private static final int COLUMN_NUM_ACCIDENTALS       = 1;         // index  of the number of accidentals column in the table
   private static final int COLUMN_ACCIDENTALS           = 2;         // index  of the key accidentals column in the table
   private static final int TABLE_KEYS_WIDTH             = 375;       // width  of the table containing the list of selectable keys
   private static final int TABLE_KEYS_HEIGHT            = 165;       // height of the table containing the list of selectable keys
   private static final int COLUMN_WIDTH_KEY_NAME        = 65;        // width  of the key name column in the list of keys
   private static final int COLUMN_WIDTH_NUM_ACCIDENTALS = 130;       // width  of number of accidentals column in the list of keys
   private static final int COLUMN_WIDTH_ACCIDENTALS     = 180;       // width  of the key accidentals column in the list of keys


   // data members
   KeySignature keySignature;              // key signature being edited
   int          selectedKey;               // index of the row in the list of keys
   int          selectedType;
   int          selectedAccidental;

   // dialog box controls
   private JScrollPane  scrollPaneKeys;           // scroll pane containing the table of keys
   private JRadioButton buttonMajor;
   private JRadioButton buttonMinor;
   private JRadioButton buttonSharps;
   private JRadioButton buttonFlats;
   private ButtonGroup  buttonGroupAccidentals;
   private ButtonGroup  buttonGroupType;
   private JTable       tableKeys;                // table of keys the user can choose


   /**
    * constructor.
    * <br/><br/>
    * @param frame          the main application frame.
    * @param keySignature   the key signature to edit.
    */
   public KeySignatureDialog(MainFrame frame, KeySignature keySignature)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.key_signature");

      // save the key signature for editing
      this.keySignature = keySignature;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.key_signature.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JPanel controlsPanel   = getControlsPanel();
      JPanel typePanel       = createTypePanel();
      JPanel accidentalPanel = createAccidentalPanel();

      // create a border
//    controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      // create the list of keys
      selectedType       = (buttonMajor .isSelected() ? INDEX_MAJOR  : INDEX_MINOR);
      selectedAccidental = (buttonSharps.isSelected() ? INDEX_SHARPS : INDEX_FLATS);

      Object[][] keysData   = getKeysData(selectedType, selectedAccidental);
      String[]   keysHeader = {ResourceBundle.getString("dialog.key_signature.table.header.key"), ResourceBundle.getString("dialog.key_signature.table.header.num_accidentals"), ResourceBundle.getString("dialog.key_signature.table.header.accidentals")};
      tableKeys      = new JTable(keysData, keysHeader);
      scrollPaneKeys = new JScrollPane();

      tableKeys.setRowSelectionAllowed   (true );
      tableKeys.setColumnSelectionAllowed(false);
      tableKeys.setShowHorizontalLines   (false);
      tableKeys.setShowVerticalLines     (false);
      tableKeys.setFillsViewportHeight   (true );
      tableKeys.getSelectionModel().addListSelectionListener(this);
      tableKeys.getSelectionModel().setSelectionInterval(keySignature.getNumberOfAccidentals(), keySignature.getNumberOfAccidentals());

      // set the key name column to a fixed, non-resizable width
      TableColumn column = tableKeys.getColumnModel().getColumn(COLUMN_KEY_NAME);
      column.setMaxWidth      (COLUMN_WIDTH_KEY_NAME);
      column.setMinWidth      (COLUMN_WIDTH_KEY_NAME);
      column.setPreferredWidth(COLUMN_WIDTH_KEY_NAME);
      column.setResizable(false);

      // set the number of accidentals column to a fixed, non-resizable width
      column = tableKeys.getColumnModel().getColumn(COLUMN_NUM_ACCIDENTALS);
      column.setMaxWidth      (COLUMN_WIDTH_NUM_ACCIDENTALS);
      column.setMinWidth      (COLUMN_WIDTH_NUM_ACCIDENTALS);
      column.setPreferredWidth(COLUMN_WIDTH_NUM_ACCIDENTALS);
      column.setResizable(false);

      // set the list of accidentals column to a fixed, non-resizable width
      column = tableKeys.getColumnModel().getColumn(COLUMN_ACCIDENTALS);
      column.setMaxWidth      (COLUMN_WIDTH_ACCIDENTALS);
      column.setMinWidth      (COLUMN_WIDTH_ACCIDENTALS);
      column.setPreferredWidth(COLUMN_WIDTH_ACCIDENTALS);
      column.setResizable(false);

      scrollPaneKeys.setViewportView(tableKeys);

      // set the layout manager
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(typePanel      , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(accidentalPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
               .addComponent(scrollPaneKeys, GroupLayout.PREFERRED_SIZE, TABLE_KEYS_WIDTH, GroupLayout.PREFERRED_SIZE)))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {typePanel, accidentalPanel});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
               .addComponent(accidentalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(typePanel      , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(18, 18, 18)
            .addComponent(scrollPaneKeys, GroupLayout.PREFERRED_SIZE, TABLE_KEYS_HEIGHT, GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );

      layout.linkSize(SwingConstants.VERTICAL, new Component[] {typePanel, accidentalPanel});

      // invoke the layout manager
      pack();
   }

   /**
    * @return the key signature accindetal panel (sharps or flats).
    */
   private JPanel createAccidentalPanel()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.key_signature.title.accidental")));

      // create the sharps\flats button group
      buttonGroupAccidentals = new ButtonGroup();
      buttonSharps = new JRadioButton(ResourceBundle.getString("dialog.key_signature.data.accidentals.sharps"));
      buttonFlats  = new JRadioButton(ResourceBundle.getString("dialog.key_signature.data.accidentals.flats" ));

      buttonSharps.setToolTipText(ResourceBundle.getString("dialog.key_signature.tooltip.accidentals"));
      buttonFlats .setToolTipText(ResourceBundle.getString("dialog.key_signature.tooltip.accidentals"));
      buttonSharps.addActionListener(this);
      buttonFlats .addActionListener(this);

      buttonGroupAccidentals.add(buttonSharps);
      buttonGroupAccidentals.add(buttonFlats );

      // set which accidental type is initially selected
      buttonGroupAccidentals.setSelected(!keySignature.hasAccidentals() || keySignature.getNumberOfSharps() != 0 ? buttonSharps.getModel() : buttonFlats.getModel(), true);

      // layout the panel
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(buttonSharps)
            .addGap(20, 20, 20)
            .addComponent(buttonFlats)
            .addContainerGap(36, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonSharps, buttonFlats});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(buttonSharps)
               .addComponent(buttonFlats))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonSharps, buttonFlats});

      return panel;
   }

   /**
    * @return the key signature type panel (major or minor).
    */
   private JPanel createTypePanel()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.key_signature.title.type")));

      // create the major\minor button group
      buttonGroupType = new ButtonGroup();
      buttonMajor = new JRadioButton(ResourceBundle.getString("dialog.key_signature.data.type.major"));
      buttonMinor = new JRadioButton(ResourceBundle.getString("dialog.key_signature.data.type.minor"));

      buttonMajor.setToolTipText(ResourceBundle.getString("dialog.key_signature.tooltip.type"));
      buttonMinor.setToolTipText(ResourceBundle.getString("dialog.key_signature.tooltip.type"));
      buttonMajor.addActionListener(this);
      buttonMinor.addActionListener(this);

      buttonGroupType.add(buttonMajor);
      buttonGroupType.add(buttonMinor);

      // set which key type is initially selected
      buttonGroupType.setSelected(keySignature.isMinor() ? buttonMinor.getModel() : buttonMajor.getModel(), true);

      // layout the panel
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(buttonMajor)
            .addGap(20, 20, 20)
            .addComponent(buttonMinor)
            .addContainerGap(36, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonMajor, buttonMinor});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(buttonMinor)
               .addComponent(buttonMajor))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonMajor, buttonMinor});

      return panel;
   }

   /**
    * @return the list of keys that a user can choose from based on their preference of using major or minor keys, and whether they want to use sharps or flats.
    * <br/><br/>
    * @param typeIndex          the index of the selection from the type        listbox.
    * @param accidentalsIndex   the index of the selection from the accidentals listbox.
    */
   private Object[][] getKeysData(int typeIndex, int accidentalsIndex)
   {
      Object[][] data  = null;
      String     major = ResourceBundle.getString("text.major");
      String     minor = ResourceBundle.getString("text.minor");

      switch (typeIndex)
      {
         case INDEX_MAJOR:
              switch (accidentalsIndex)
              {
                 case INDEX_SHARPS:
                      Object[][] data1 = {{"C  " + major, "               0", ""                          },
                                          {"G  " + major, "               1", "F#"                        },
                                          {"D  " + major, "               2", "F#, C#"                    },
                                          {"A  " + major, "               3", "F#, C#, G#"                },
                                          {"E  " + major, "               4", "F#, C#, G#, D#"            },
                                          {"B  " + major, "               5", "F#, C#, G#, D#, A#"        },
                                          {"F# " + major, "               6", "F#, C#, G#, D#, A#, E#"    },
                                          {"C# " + major, "               7", "F#, C#, G#, D#, A#, E#, B#"}};
                      data = data1;
                 break;
                 case INDEX_FLATS:
                      Object[][] data2 = {{"C  " + major, "               0", ""                          },
                                          {"F  " + major, "               1", "Bb"                        },
                                          {"Bb " + major, "               2", "Bb, Eb"                    },
                                          {"Eb " + major, "               3", "Bb, Eb, Ab"                },
                                          {"Ab " + major, "               4", "Bb, Eb, Ab, Db"            },
                                          {"Db " + major, "               5", "Bb, Eb, Ab, Db, Gb"        },
                                          {"Gb " + major, "               6", "Bb, Eb, Ab, Db, Gb, Cb"    },
                                          {"Cb " + major, "               7", "Bb, Eb, Ab, Db, Gb, Cb, Fb"}};
                      data = data2;
                 break;
              }
         break;
         case INDEX_MINOR:
              switch (accidentalsIndex)
              {
                 case INDEX_SHARPS:
                      Object[][] data3 = {{"A  " + minor, "               0", ""                          },
                                          {"E  " + minor, "               1", "F#"                        },
                                          {"B  " + minor, "               2", "F#, C#"                    },
                                          {"F# " + minor, "               3", "F#, C#, G#"                },
                                          {"C# " + minor, "               4", "F#, C#, G#, D#"            },
                                          {"G# " + minor, "               5", "F#, C#, G#, D#, A#"        },
                                          {"D# " + minor, "               6", "F#, C#, G#, D#, A#, E#"    },
                                          {"A# " + minor, "               7", "F#, C#, G#, D#, A#, E#, B#"}};
                      data = data3;
                 break;
                 case INDEX_FLATS:
                      Object[][] data4 = {{"A  " + minor, "               0", ""                          },
                                          {"D  " + minor, "               1", "Bb"                        },
                                          {"Gb " + minor, "               2", "Bb, Eb"                    },
                                          {"Cb " + minor, "               3", "Bb, Eb, Ab"                },
                                          {"Fb " + minor, "               4", "Bb, Eb, Ab, Db"            },
                                          {"Bb " + minor, "               5", "Bb, Eb, Ab, Db, Gb"        },
                                          {"Eb " + minor, "               6", "Bb, Eb, Ab, Db, Gb, Cb"    },
                                          {"Ab " + minor, "               7", "Bb, Eb, Ab, Db, Gb, Cb, Fb"}};
                      data = data4;
                 break;
              }
         break;
      }
      return data;
   }

   /**
    * implements the ActionListener interface.
    * reloads the list of keys the user can select from whenever the user chooses a new key type (major or minor) or whenever the
    * user chooses a different accidental type to use (sharps or flats).
    * <br/><br/>
    * @param event   the radio button the user selected.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // is the button one of the radio buttons
      if (event.getSource() == buttonMajor || event.getSource() == buttonMinor || event.getSource() == buttonSharps || event.getSource() == buttonFlats)
      {
         if (event.getSource() == buttonMajor || event.getSource() == buttonMinor)
            selectedType       = (buttonMajor.isSelected()  ? INDEX_MAJOR : INDEX_MINOR);
         else if (event.getSource() == buttonSharps || event.getSource() == buttonFlats)
            selectedAccidental = (buttonSharps.isSelected() ? INDEX_SHARPS : INDEX_FLATS);

         TableModel model = tableKeys.getModel();
         Object[][] data  = getKeysData(selectedType, selectedAccidental);

         for(int i=0; i<data.length; ++i)
            for(int j=0; j<data[0].length; ++j)
               model.setValueAt(data[i][j], i, j);
      }
      // otherwise, its either the ok, cancel, or help buttons
      else
      {
         super.actionPerformed(event);
      }
   }

   /**
    * implements the ListSelectionListener interface.
    * saves the key data whenever the user chooses a new key.
    * <br/><br/>
    * @param event   the key list item the user selected.
    */
   public void valueChanged(ListSelectionEvent event)
   {
      if (!event.getValueIsAdjusting() && event.getSource() == tableKeys.getSelectionModel())
         selectedKey = tableKeys.getSelectedRow();
   }

   /**
    * @return the number of accidentals in the key signature as an enum.
    * <br/><br/>
    * @param accidentalsIndex   the index of the selection from the accidentals listbox.
    * @param numAccidentals     number of accidentals in the selected key.
    */
   private KeySignature.Accidentals getAccidentals(int accidentalsIndex, int numAccidentals)
   {
      KeySignature.Accidentals accidentals = KeySignature.Accidentals.None;

      switch (accidentalsIndex)
      {
         case INDEX_SHARPS:
              switch (numAccidentals)
              {
                 case 0: accidentals = KeySignature.Accidentals.None;         break;
                 case 1: accidentals = KeySignature.Accidentals.OneSharp;     break;
                 case 2: accidentals = KeySignature.Accidentals.TwoSharps;    break;
                 case 3: accidentals = KeySignature.Accidentals.ThreeSharps;  break;
                 case 4: accidentals = KeySignature.Accidentals.FourSharps;   break;
                 case 5: accidentals = KeySignature.Accidentals.FiveSharps;   break;
                 case 6: accidentals = KeySignature.Accidentals.SixSharps;    break;
                 case 7: accidentals = KeySignature.Accidentals.SevenSharps;  break;
              }
         break;
         case INDEX_FLATS:
              switch (numAccidentals)
              {
                 case 0: accidentals = KeySignature.Accidentals.None;         break;
                 case 1: accidentals = KeySignature.Accidentals.OneFlat;      break;
                 case 2: accidentals = KeySignature.Accidentals.TwoFlats;     break;
                 case 3: accidentals = KeySignature.Accidentals.ThreeFlats;   break;
                 case 4: accidentals = KeySignature.Accidentals.FourFlats;    break;
                 case 5: accidentals = KeySignature.Accidentals.FiveFlats;    break;
                 case 6: accidentals = KeySignature.Accidentals.SixFlats;     break;
                 case 7: accidentals = KeySignature.Accidentals.SevenFlats;   break;
              }
         break;
      }
      return accidentals;
   }

   /**
    * @return the key signature the user selected.
    */
   public KeySignature getKeySignature()
   {
      return keySignature;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // get the key signature the user selected
      TableModel               tableModel     = tableKeys.getModel();
      String                   string         = (String)tableModel.getValueAt(selectedKey, COLUMN_NUM_ACCIDENTALS);
      int                      numAccidentals = Integer.valueOf(string.trim());
      KeySignature.Type        type           = buttonMajor.isSelected()? KeySignature.Type.Major : KeySignature.Type.Minor;
      KeySignature.Accidentals accidentals    = getAccidentals(selectedAccidental, numAccidentals);

      keySignature = new KeySignature(accidentals, type);
   }
}
