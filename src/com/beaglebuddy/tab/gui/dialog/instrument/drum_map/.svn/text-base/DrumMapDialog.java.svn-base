/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.drum_map;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.mainframe.Preferences;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.instrument.DrumMap;
import com.beaglebuddy.tab.model.instrument.DrumMapEntry;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;




/**
 * This class is a dialog box which allows a user to create a map between drum sounds and drum notes.
 */
public class DrumMapDialog extends BaseDialog implements ListSelectionListener
{
   // class members
// private static final int MAP_NAME_WIDTH           = 210;         // width of the drum map name text field
// private static final int COLUMN_WIDTH_DRUM_NAME   = 150;         // width of a drum name column
   private static final int TABLE_HEIGHT             = 256;         // height of drum name table
   private static final int TABLE_WIDTH              = 545;         // width  of drum name table
// private static final int DRUM_NOTE_IMAGE_WIDTH    = 20;          // width  of a drum note image
// private static final int DRUM_NOTE_IMAGE_HEIGHT   = 64;          // height of a drum note image

   // data members
   private DrumMap      drumMap;                   // drum map that is being edited
   private DrumMapEntry selectedDrumMapEntry;      // currently selected drum map entry the user is working on

   // dialog box controls
   private JTextField textFieldMapName;            // name of the drum map
   private JComboBox  comboBoxSortMethod;          // contains the list of ways to display the midi percussion sounds in the table
   private JTable     table;                       // contains the list of midi percussion sounds
   private JButton[]  buttonDrumNotes;             // the list of notes in the drum map that are to be mapped to midi percussion sounds




   /**
    * constructor.
    * <br/><br/>
    * @param frame        the main application frame.
    * @param drumMap      the drum map to edit
    */
   public DrumMapDialog(MainFrame frame, DrumMap drumMap)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.drum_map");

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.drum_map.title"));

      // make a copy of the drum map to be edited
      this.drumMap = drumMap == null ? new DrumMap() : new DrumMap(drumMap);

      // set the starting drum map entry to edit
      selectedDrumMapEntry = this.drumMap.get(0);
   }

   /**
    * implements the ActionListener interface.
    * this method handles the user choosing a sort method from the combo box and the user selecting a new drum note button
    * from the bottom of the dialog.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
//    String command = event.getActionCommand();
      Object source  = event.getSource();

      // did the user choose a new sort method from the combo box for displaying the table of percussion sounds
      if (source != null && source instanceof JComboBox && source == comboBoxSortMethod)
      {
         int order = comboBoxSortMethod.getSelectedIndex();
         if (table != null)
            table.setModel(new DrumMapTableModel(DrumMapTableModel.getOrder(order)));
      }
      else if (source != null && source instanceof JButton)
      {
         // see if its one of the drum note buttons
         boolean found = false;
         for(int i=0; i<buttonDrumNotes.length && !found; ++i) {
           found = (source == buttonDrumNotes[i]);
           if ((found))
               selectedDrumMapEntry = drumMap.get(i);
         }
         // if it is, then highlight the percussion sound that the drum note is mapped to in the percussion sound table
         if (found)
         {
            Midi.Percussion  percussion       = selectedDrumMapEntry.getMidiPercussion();
            TableModel       model            = table.getModel();
            DrumMapTableCell drumMapTableCell = null;
            int              row              = 0;
            int              col              = 0;
                             found            = false;

            // find the row and column coordinates of the cell that contains the percussion sound
            for(row=0; row<model.getRowCount() && !found; ++row)
            {
               for(col=0; col<model.getColumnCount() && !found; ++col)
               {
                  drumMapTableCell = (DrumMapTableCell)model.getValueAt(row, col);
                  found            = percussion.id() == drumMapTableCell.getPercussion().id();
               }
            }
            if (found)
            {
//             SwingUtilities.invokeLater(new DrumMapTableCellSelector(table, row, col));
               table.requestFocusInWindow();
               table.changeSelection(row-1, col-1, false, false);
            }
         }
         // if its the ok button, then make sure the user entered a name for the drum map.
         else if (source == getOkCancelHelpButtonPanel().getOkButton())
         {
            String name = textFieldMapName.getText().trim();
            if (name.length() == 0)
            {
               JOptionPane.showMessageDialog(this, ResourceBundle.getString("gui.error.drum_map.name_missing"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
            }
         }
         // otherwise, its either the cancel or the help button, so let the base class process it
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
      JPanel controlsPanel   = getControlsPanel();
      JPanel panelMapName    = createMapNamePanel();
      JPanel panelDrumSounds = createDrumSoundsPanel();
      JPanel panelDrumNotes  = createDrumNotesPanel();

      // set the layout manager
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      // layout the horizontal axis
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(panelMapName   , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         .addComponent(panelDrumNotes , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         .addComponent(panelDrumSounds, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      // layout the vertical axis
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelMapName   , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(panelDrumSounds, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE           )
            .addComponent(panelDrumNotes , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );

      // invoke the layout manager
      pack();

      // set the initial focus on the 1st drum note button
      buttonDrumNotes[0].requestFocusInWindow();
   }

   /**
    * @return the panel which contains the list of midi drum sounds.
    */
   private JPanel createDrumSoundsPanel()
   {
      JPanel      panelDrumSounds = new JPanel();
      JScrollPane scrollPane      = new JScrollPane();

      table = new JTable();
      table.setCellSelectionEnabled  (true );
      table.setRowSelectionAllowed   (false);
      table.setColumnSelectionAllowed(false);
      table.setShowHorizontalLines   (false);
      table.setShowVerticalLines     (false);
      table.setFillsViewportHeight   (true );
      table.setTableHeader           (null );

      // set the table model
      table.setModel(new DrumMapTableModel(DrumMapTableModel.getOrder(comboBoxSortMethod.getSelectedIndex())));

      // set the custom cell render
      DrumMapTableCellRenderer drumMapTableCellRenderer = new DrumMapTableCellRenderer();
      for(int i=0; i<DrumMapTableModel.NUM_COLUMNS; ++i)
         table.getColumnModel().getColumn(i).setCellRenderer(drumMapTableCellRenderer);

      // this doesn't work for single cell selection tables
//    table.setSelectionBackground   (Color.cyan );
//    table.setSelectionForeground   (Color.white);

      // add the listener for when a user selects a midi percussion sound from the table
      ListSelectionModel selectionModel = table.getSelectionModel();
      selectionModel.addListSelectionListener(this);

      // set each column to an equal, fixed width
/*    for(int i=0; i<DrumMapTableModel.NUM_COLUMNS; ++i)
      {
         TableColumn column = table.getColumnModel().getColumn(i);
         column.setMaxWidth      (COLUMN_WIDTH_DRUM_NAME);
         column.setMinWidth      (COLUMN_WIDTH_DRUM_NAME);
         column.setPreferredWidth(COLUMN_WIDTH_DRUM_NAME);
         column.setResizable(false);
      }
*/
      // add the table to a scroll pane
      scrollPane.setViewportView(table);

      // layout the table in the panel
      GroupLayout panelDrumSoundsLayout = new GroupLayout(panelDrumSounds);
      panelDrumSounds.setLayout(panelDrumSoundsLayout);

      // layout the horizontal axis
      panelDrumSounds.setLayout(panelDrumSoundsLayout);
      panelDrumSoundsLayout.setHorizontalGroup(
         panelDrumSoundsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, TABLE_WIDTH, Short.MAX_VALUE)
      );
      // layout the vertical axis
      panelDrumSoundsLayout.setVerticalGroup(
         panelDrumSoundsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelDrumSoundsLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, TABLE_HEIGHT, Short.MAX_VALUE)
            .addContainerGap())
      );

      return panelDrumSounds;
   }

   /**
    * @return the panel which contains the drum note image buttons.
    */
   private JPanel createDrumNotesPanel()
   {
      JPanel panelDrumNotes = new JPanel();

      panelDrumNotes.setLayout(new BoxLayout(panelDrumNotes, BoxLayout.X_AXIS));
      panelDrumNotes.setAlignmentY(TOP_ALIGNMENT);
      panelDrumNotes.setAlignmentX(LEFT_ALIGNMENT);
      panelDrumNotes.setBorder(BorderFactory.createTitledBorder(""));

      // create the drum note image buttons
      buttonDrumNotes = new JButton[DrumMap.NUM_DRUM_NOTES];
      ButtonGroup buttonGroup = new ButtonGroup();
      Dimension   gap         = new Dimension(1,1);   // space between each button

      for(int i=0; i<DrumMap.NUM_DRUM_NOTES; ++i)
      {
         String image = ResourceBundle.format("dialog.drum_map.button.image.unselected", i);
         buttonDrumNotes[i] = new JButton(new ImageIcon(getClass().getResource(image)));
         image = ResourceBundle.format("dialog.drum_map.button.image.selected", i);
         buttonDrumNotes[i].setRolloverIcon(new ImageIcon(getClass().getResource(image)));
         buttonDrumNotes[i].setPressedIcon (new ImageIcon(getClass().getResource(image)));
         buttonDrumNotes[i].setMargin(new Insets(0,0,0,0));
         buttonDrumNotes[i].setActionCommand("button " + i);
         buttonDrumNotes[i].addActionListener(this);
         buttonGroup   .add(buttonDrumNotes[i]);
         panelDrumNotes.add(buttonDrumNotes[i]);
         panelDrumNotes.add(Box.createRigidArea(gap));
      }
      buttonDrumNotes[0].setSelected(true);

      return panelDrumNotes;
   }

   /**
    * @return the top panel containing the map name text field.
    */
   private JPanel createMapNamePanel()
   {
      JPanel panelMapName = new JPanel();

      // create the map name text field
      JLabel labelMapName = new JLabel(ResourceBundle.getString("dialog.drum_map.label.map_name"));
      textFieldMapName = new JTextField();
      textFieldMapName.setToolTipText(ResourceBundle.getString("dialog.drum_map.tooltip.map_name"));
      textFieldMapName.setText(drumMap.getName());

      // create the sort method combo box
      JLabel labelSortMethod = new JLabel(ResourceBundle.getString("dialog.drum_map.label.sort_method"));
      String[] sortMethods = {ResourceBundle.getString("dialog.drum_map.combo_box.sort_method.alphabetically"),
                              ResourceBundle.getString("dialog.drum_map.combo_box.sort_method.midi_id"       ),
                              ResourceBundle.getString("dialog.drum_map.combo_box.sort_method.sound"         )};
      comboBoxSortMethod = new JComboBox(sortMethods);
      comboBoxSortMethod.setEditable(false);
      comboBoxSortMethod.setToolTipText(ResourceBundle.getString("dialog.drum_map.tooltip.sort_method"));
      comboBoxSortMethod.addActionListener(this);

      // set the initially selected item
      String selectedSortMethod = Preferences.getString(Preferences.key_drum_map_sorting_method, Preferences.default_value_drum_map_sorting_method);
      int    selectedIndex      = -1;
      for (int i=0; i<sortMethods.length && selectedIndex == -1; ++i)
        if (sortMethods[i].equals(selectedSortMethod))
           selectedIndex = i;
      comboBoxSortMethod.setSelectedIndex(selectedIndex == -1 ? 0 : selectedIndex);

      // layout the components within the panel
      GroupLayout panelMapNameLayout = new GroupLayout(panelMapName);
      panelMapName.setLayout(panelMapNameLayout);

      // layout the horizontal axis
      panelMapNameLayout.setHorizontalGroup(
         panelMapNameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelMapNameLayout.createSequentialGroup()
            .addGroup(panelMapNameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(panelMapNameLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(labelMapName))
               .addComponent(labelSortMethod))
            .addGap(16, 16, 16)
            .addGroup(panelMapNameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(textFieldMapName, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
               .addComponent(comboBoxSortMethod, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(194, Short.MAX_VALUE))
      );
      // layout the vertical axis
      panelMapNameLayout.setVerticalGroup(
         panelMapNameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelMapNameLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelMapNameLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelMapName)
               .addComponent(textFieldMapName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelMapNameLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelSortMethod)
               .addComponent(comboBoxSortMethod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      return panelMapName;
   }

   /**
    * @return the drum map the user created.
    */
   public DrumMap getDrumMap()
   {
      return drumMap;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // nothing to do - the drum map has been updated as the user made their selections
   }

   /**
    * plays a sample sound of the specified midi percussion.
    * <br/><br/>
    * @param percussion   midi sound to play
    */
   private void playSound(Midi.Percussion percussion)
   {
      Synthesizer synthesizer = null;
      try
      {
         synthesizer = MidiSystem.getSynthesizer( );
         synthesizer.open();
         MidiChannel channel = synthesizer.getChannels( )[Midi.Drum_Channel.ordinal()];
         channel.noteOn(percussion.id(), 120);
         Thread.sleep(300);
         channel.noteOff(percussion.id());
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
    * implements the ListSelectionListener interface.
    * this method is called whenever a user clicks on a cell in the percussion sound table.
    * <br/><br/>
    * @param event   the index of list item the user selected.
    */
   public void valueChanged(ListSelectionEvent event)
   {
      if (!event.getValueIsAdjusting())
      {
         if (event.getSource() == table.getSelectionModel())
         {
            // get the selected cell coordinates
            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();
            assert(table.isCellSelected(row, col));

            // get the selected midi percussion sound
            TableModel       model      = table.getModel();
            DrumMapTableCell cell       = (DrumMapTableCell)model.getValueAt(row, col);
            Midi.Percussion  percussion = cell.getPercussion();

            // store the selected percussion sound in the drum map
            selectedDrumMapEntry.setMidiPercussion(percussion);

            // play a sample of the selected midi percussion sound
            playSound(percussion);
         }
      }
   }
}
