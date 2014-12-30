/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.diagram;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.dialog.chord.dictionary.ChordDictionaryDialog;
import com.beaglebuddy.tab.gui.dialog.chord.name.ChordNameDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.mainframe.ToolbarItemInfo;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.ChordDictionary;
import com.beaglebuddy.tab.model.Fretting;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;




/**
 * This class is a dialog box which allows a user to edit the chord diagrams used in the song.
 */
public class ChordDiagramsDialog extends BaseDialog implements ItemListener, ListSelectionListener, Observer
{
   // class members
   public  static final int       TABLE_WIDTH                           = 395;                           // width  of the chord diagram table list
   public  static final int       TABLE_HEIGHT                          = 185;                           // height of the chord diagram table list
   public  static final int       TABLE_COLUMN_WIDTH                    = 55;                            // width  of each cell in the chord diagram list table
   public  static final int       TABLE_ROW_HEIGHT                      = 90;                            // height of each cell in the chord diagram list table
   private static final int       CHORD_NAME_LIST_HEIGHT                = 145;                           // height of the suggested chord name list box
   private static final int       CHORD_NAME_LIST_WIDTH                 = 150;                           // height of the suggested chord name list box
   private static final Dimension COMBO_BOX_INSTRUMENT_SIZE             = new Dimension(150, 16);        // width of the instrument combo box on the toolbar

   private static final String    BUTTON_CHORD_DIAGRAM_INSTRUMENT       = "instrument";                  // chord diagram list toolbar button controls
   private static final String    BUTTON_CHORD_DIAGRAM_CHORD_DICTIONARY = "chord_dictionary";
   private static final String    BUTTON_CHORD_DIAGRAM_ADD              = "add";
   private static final String    BUTTON_CHORD_DIAGRAM_DELETE           = "delete";
   private static final String    BUTTON_CHORD_DIAGRAM_MOVE_LEFT        = "move_left";
   private static final String    BUTTON_CHORD_DIAGRAM_MOVE_RIGHT       = "move_right";
   private static final String    BUTTON_EDIT_CHORD_NAME                = "edit_chord_name";             // edit chord name button
   private static final String    BUTTON_CLEAR_CHORD_DIAGRAM            = "clear_chord_diagram";         // clear chord diagram changes button
   private static final String    BUTTON_SAVE_CHORD_DIAGRAM             = "save_chord_diagram";          // save  chord diagram changes button

   private static final String    TOOLBAR_NAME                          = "chord_diagram_list_toolbar";  // components on the barline toolbar
   private static final int       TOOLBAR_INSTRUMENT                    = 0;
   private static final int       TOOLBAR_CHORD_DICTIONARY              = 1;
   private static final int       TOOLBAR_SEPARATOR                     = 2;
   private static final int       TOOLBAR_ADD                           = 3;
   private static final int       TOOLBAR_DELETE                        = 4;
   private static final int       TOOLBAR_MOVE_LEFT                     = 5;
   private static final int       TOOLBAR_MOVE_RIGHT                    = 6;

   // data members
   private MainFrame                                      frame;                      // parent application window frame
   private Hashtable<Instrument, ArrayList<ChordDiagram>> instrumentChordDiagrams;    // map of instruments and their associated chord diagrams.
   private Instrument                                     selectedInstrument;         // the currently instrument selected from the combo box
   private int                                            selectedInstrumentIndex;    // index in the combo box of the currently selected instrument
   private int                                            selectedChordDiagram;       // index of the chord diagram selected from the list shown at the bottom of the dialog box.  it will be -1 if no chord diagram is currently hi lighted.
   private int                                            selectedChordDiagramRow;    // table row where the selected chord diagram is located
   private int                                            selectedChordDiagramCol;    // table col where the selected chord diagram is located
   private boolean                                        isMovingToAdjacentCell;     // set to true when the user clicks on the "left" or "right" movement icons in the toolbar
   private boolean                                        isResettingInstrumentIndex; // set to true when the user is editing a chord diagram, and selects a different instrument, and then selects cancel
   private boolean                                        usesSharps;                 // whether to use sharps or flats in resolving two notes that have the same pitch (ex: F# and Gb)

   // controls
   private JToolBar                                       toolbar;                    // chord diagram list toolbar
   private JTable                                         table;                      // chord diagram list table
   private JList                                          listChordNames;             // list of possible chord names for the chord diagram currently being edited
   private JButton                                        buttonEditChordName;        // button which displays the chord name dialog and lets a user construct his own name for the chord being edited
   private JPanel                                         panelChordDiagramEdit;      // panel where the user edits a chord diagram
   private ChordDiagramEditor                             panelFretboard;             // panel where the fretboard is drawn
   private JButton                                        buttonClearFretting;        // button which clears the current chord diagram being edited
   private JButton                                        buttonSaveChanges;          // button which saves  the current chord diagram being edited
   private JComboBox                                      comboBoxInstruments;        // combo box in the toolbar containing the list of instruments which can have chord diagrams.





   /**
    * constructor.
    * <br/><br/>
    * @param frame           the main application frame.
    * @param instruments     the list of instruments defined in the song which are allowsed to have chord diagrams associated with them.
    * @param chordDiagrams   the list of chord diagrams defined for the song.
    */
   public ChordDiagramsDialog(MainFrame frame, ArrayList<Instrument> instruments, ArrayList<ChordDiagram> chordDiagrams)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.chord_diagrams");

      assert(instruments.size() != 0);

      // save the main frame so that it can be used to create the chord name dialog box and the chord dictionary dialog box
      this.frame = frame;

      // sort the list of chord diagrams according to their respective instruments
      instrumentChordDiagrams = new Hashtable<Instrument, ArrayList<ChordDiagram>>();
      for(Instrument instrument : instruments)
      {
         ArrayList<ChordDiagram> associatedChordDiagrams = new ArrayList<ChordDiagram>();
         instrumentChordDiagrams.put(instrument, associatedChordDiagrams);
         for(ChordDiagram chordDiagram : chordDiagrams)
            if (instrument.getId() == chordDiagram.getInstrumentId())
                associatedChordDiagrams.add(new ChordDiagram(chordDiagram));
      }

      // find the first instrument which has associated chord diagrams and make it the initially selected instrument
      int index = -1;
      for(Instrument instrument : instrumentChordDiagrams.keySet())
      {
         index++;
         if (instrumentChordDiagrams.get(instrument).size() != 0)
         {
            this.selectedInstrument      = instrument;
            this.selectedInstrumentIndex = index;
            break;
         }
      }

      // if there aren't any chord diagrams, then simply choose the first instrument in the list
      if (selectedInstrument == null)
      {
         this.selectedInstrument      = instrumentChordDiagrams.keySet().iterator().next();
         this.selectedInstrumentIndex = 0;
      }

      // todo: fix this - implement some gui code
      usesSharps = true;

      // initially, no chord diagram is selected
      this.selectedChordDiagram    = -1;
      this.selectedChordDiagramRow = -1;
      this.selectedChordDiagramCol = -1;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.chord_diagrams.title"));
   }

   /**
    * implements the ActionListener interface.
    * this method handles the user clicking one of the toolbar buttons add, edit, delete, move left, and move right,
    * or if the user clicked the "edit chord name" button.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String                  command       = event.getActionCommand();
      Object                  source        = event.getSource();
      ArrayList<ChordDiagram> chordDiagrams = instrumentChordDiagrams.get(selectedInstrument);
      Tuning                  tuning        = selectedInstrument.getTuning();

      if (command.equals(BUTTON_CHORD_DIAGRAM_CHORD_DICTIONARY))
      {
         // display the chord dictionary dialog box
         ChordDictionaryDialog chordDictionaryDialog = new ChordDictionaryDialog(frame, panelFretboard.getChordDiagram().getName());
         chordDictionaryDialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the list of chord diagrams
         if (chordDictionaryDialog.wasOKed())
         {
            chordDiagrams.add(chordDictionaryDialog.getChordDiagram());
            table.setModel(new ChordDiagramsTableModel(chordDiagrams));
         }
      }
      else if (command.equals(BUTTON_CHORD_DIAGRAM_ADD))
      {
         int option = JOptionPane.YES_OPTION;

         // if the user has modified the current chord diagram, then prompt them to save their changes first
         if (panelFretboard.hasBeenChanged())
         {
            option = JOptionPane.showConfirmDialog(this, ResourceBundle.getString("gui.error.chord_diagram.save_changes"),
                                                   ResourceBundle.getString("gui.text.dialog_title.warning"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION)
            {
               if (!saveChordDiagram())
                  option = JOptionPane.CANCEL_OPTION;
            }
         }
         if (option != JOptionPane.CANCEL_OPTION)
         {
            // clear the chord diagram editor
            panelFretboard.setChordDiagram(new ChordDiagram(selectedInstrument.getId(), selectedInstrument.getNumStrings(), selectedInstrument.getTopFret()));
            // set that the user has not modified the current chord diagram
            panelFretboard.resetHasBeenChanged();
            // say that no chord diagram is currently selected
            selectedChordDiagram    = -1;
            selectedChordDiagramRow = -1;
            selectedChordDiagramCol = -1;
            // update the toolbar and save/clear buttons
            updateControls();
            // redraw the chord diagram editor
            repaint();
         }
      }
      else if (command.equals(BUTTON_CHORD_DIAGRAM_DELETE))
      {
         // remove the selected chord diagram
         chordDiagrams.remove(selectedChordDiagram);

         // set the chord diagram editor to a blank new chord diagram
         panelFretboard.setChordDiagram(new ChordDiagram(selectedInstrument.getId(), selectedInstrument.getNumStrings(), selectedInstrument.getTopFret()));

         // set that the user has not modified the current chord diagram
         panelFretboard.resetHasBeenChanged();

         // update the state to indicate that no chord diagram is now selected
         selectedChordDiagram    = -1;
         selectedChordDiagramRow = -1;
         selectedChordDiagramCol = -1;

         // update the table model
         table.setModel(new ChordDiagramsTableModel(chordDiagrams));

         // update the toolbar and save/clear buttons
         updateControls();

         // redraw the chord diagram editor
         repaint();
      }
      else if (command.equals(BUTTON_CHORD_DIAGRAM_MOVE_LEFT))
      {
         // move the chord diagram to the left in the list
         ChordDiagram chordDiagram = chordDiagrams.get(selectedChordDiagram);
         chordDiagrams.remove(selectedChordDiagram);
         chordDiagrams.add   (selectedChordDiagram - 1, chordDiagram);

         // get the current
         int selectedRow = table.getSelectedRow();
         int selectedCol = table.getSelectedColumn();
         int columnCount = table.getColumnCount();
         isMovingToAdjacentCell = true;

         // set the table model and return focus to the selected chord diagram
         table.setModel(new ChordDiagramsTableModel(chordDiagrams));
         table.changeSelection(selectedCol == 0 ? selectedRow - 1 : selectedRow, selectedCol == 0 ? columnCount - 1 : selectedCol - 1, false, false);
         table.requestFocusInWindow();
      }
      else if (command.equals(BUTTON_CHORD_DIAGRAM_MOVE_RIGHT))
      {
         // move the chord diagram to the right in the list
         ChordDiagram chordDiagram = chordDiagrams.get(selectedChordDiagram);
         chordDiagrams.remove(selectedChordDiagram);
         chordDiagrams.add   (selectedChordDiagram + 1, chordDiagram);

         int selectedRow = table.getSelectedRow();
         int selectedCol = table.getSelectedColumn();
         int columnCount = table.getColumnCount();
         isMovingToAdjacentCell = true;

         // set the table model and return focus to the selected chord diagram
         table.setModel(new ChordDiagramsTableModel(chordDiagrams));
         table.changeSelection(selectedCol == columnCount - 1 ? selectedRow + 1 : selectedRow, selectedCol == columnCount - 1 ? 0 : selectedCol + 1, false, false);
         table.requestFocusInWindow();
      }
      else if (command.equals(BUTTON_EDIT_CHORD_NAME))
      {
         // re-select the current chord diagram in the table
         table.requestFocusInWindow();

         // display the chord name dialog box
         ChordNameDialog chordNameDialog = new ChordNameDialog(frame, panelFretboard.getChordDiagram().getName());
         chordNameDialog.setVisible(true);

         // if the user pressed ok, then save the chord name the user chose as the name of the current chord
         if (chordNameDialog.wasOKed())
         {
            panelFretboard.setChordName(chordNameDialog.getChordName());
            updateControls();

            // redraw the chord diagram editor
            repaint();
         }
      }
      else if (command.equals(BUTTON_SAVE_CHORD_DIAGRAM))
      {
         saveChordDiagram();
      }
      else if (command.equals(BUTTON_CLEAR_CHORD_DIAGRAM))
      {
         // get the current chord diagram's name
         String chordName = panelFretboard.getChordDiagram().getName();

         // clear the chord diagram
         resetChordDiagramEditor(selectedChordDiagram != -1);

         if (selectedChordDiagram != -1)
         {
            panelFretboard.getChordDiagram().setName(chordName);
            table.changeSelection(selectedChordDiagramRow, selectedChordDiagramCol, false, false);
            table.requestFocusInWindow();
         }
         buttonClearFretting.setEnabled(false);
      }
      // otherwise its either the ok, cancel, or help button
      else
      {
         super.actionPerformed(event);
      }
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JPanel     panelTop  = createPanelTop();                 // creates the top panel with the chord diagram and suggested chord name list box
      JPanel     panelList = createPanelChordDiagramsList();   // creates the bottom panel with the table of chord diagrams
      JSeparator separator = new JSeparator();

      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(panelTop , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         .addComponent(panelList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelTop , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(panelList, GroupLayout.DEFAULT_SIZE  , GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      // set the initial state of the toolbar components
      updateControls();

      // invoke the layout manager
      pack();
      repaint();
   }

   /**
    * @return a panel which will be used for displaying the list of defined chord diagrams.
    */
   private JPanel createPanelChordDiagramsList()
   {
      toolbar = createToolbar();

      JPanel panel           = new JPanel();
      JScrollPane scrollPane = new JScrollPane();

      // create the table
      table = new JTable();
      table.setCellSelectionEnabled  (true );
      table.setRowSelectionAllowed   (false);
      table.setColumnSelectionAllowed(false);
      table.setShowHorizontalLines   (false);
      table.setShowVerticalLines     (false);
      table.setFillsViewportHeight   (true );
      table.setTableHeader           (null );

      // set the table model
      ArrayList<ChordDiagram> chordDiagrams = instrumentChordDiagrams.get(selectedInstrument);
      table.setModel(new ChordDiagramsTableModel(chordDiagrams));

      // set the custom cell renderer for drawing the chord diagrams in the table
      table.setDefaultRenderer(ChordDiagram.class, new ChordDiagramCellRenderer());

      // add the listener for when a user selects a chord diagram from the table
      table                 .getSelectionModel().addListSelectionListener(this);   // row selection listener
      table.getColumnModel().getSelectionModel().addListSelectionListener(this);   // col selection listener

      // set the height of the rows to a fixed, non-resizable height
      table.setRowHeight(TABLE_ROW_HEIGHT);

      // set the columns to a fixed, non-resizable width
      for(int col=0; col<ChordDiagramsTableModel.NUM_COLUMNS; ++col)
      {
         TableColumn column = table.getColumnModel().getColumn(col);
         column.setMaxWidth      (TABLE_COLUMN_WIDTH);
         column.setMinWidth      (TABLE_COLUMN_WIDTH);
         column.setPreferredWidth(TABLE_COLUMN_WIDTH);
         column.setResizable(false);
      }

      // add the table to a scroll pane
      scrollPane.setViewportView(table);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(toolbar, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
         .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, TABLE_WIDTH, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(toolbar, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, TABLE_HEIGHT, Short.MAX_VALUE)
            .addContainerGap())
      );
      return panel;
   }

   /**
    * @return the panel containing the suggested chord name list and the "edit chord name" button.
    */
   private JPanel createPanelChordName()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_diagrams.border.chord_name")));

      // create the chord name list box
      listChordNames = new JList(new ListModelChordNames());
//    listChordNames.setToolTipText();
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(listChordNames);
      listChordNames.addListSelectionListener(this);

      // create the custom chord name button
      buttonEditChordName = new JButton(ResourceBundle.getString("dialog.chord_diagrams.button.edit_chord_name"));
      buttonEditChordName.setToolTipText(ResourceBundle.getString("dialog.chord_diagrams.tooltip.edit_chord_name"));
      buttonEditChordName.setActionCommand(BUTTON_EDIT_CHORD_NAME);
      buttonEditChordName.addActionListener(this);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
               .addComponent(scrollPane         , GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, CHORD_NAME_LIST_WIDTH, Short.MAX_VALUE)
               .addComponent(buttonEditChordName, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, CHORD_NAME_LIST_WIDTH, Short.MAX_VALUE))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, CHORD_NAME_LIST_HEIGHT, Short.MAX_VALUE)
            .addGap(10, 10, 10)
            .addComponent(buttonEditChordName))
      );
      return panel;
   }

   /**
    * @return the panel which allows the user to edit a chord diagram.
    */
   private JPanel createPanelEditChordDiagram()
   {
      // create the container panel
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_diagrams.border.edit_chord")));

      createPanelEditChordDiagramComponents(panel, false);

      return panel;
   }

   /**
    * this method creates the components that comprise the chord diagram panel.  namely, the chord diagram editor and the save\clear buttons.
    * it was necessary to break this code out into its own method because the chord diagram editor needs to be recreated whenever a user changes the instrument.
    * <br/><br/>
    * @param panel            the panel containing the chord diagram editor and the save\clear buttons.
    * @param hasBeenChanged   whether to mark the chord diagram as having been changed or not.
    */
   private void createPanelEditChordDiagramComponents(JPanel panel, boolean hasBeenChanged)
   {
      // create the panel which draws the chord diagram
      panelFretboard = new ChordDiagramEditor(new ChordDiagram(selectedInstrument.getId(), selectedInstrument.getNumStrings(), selectedInstrument.getTopFret()),
                                              selectedInstrument.getCapo(), this, hasBeenChanged);

      // create the "save" button
      buttonSaveChanges = new JButton(ResourceBundle.getString("dialog.chord_diagrams.button.save_chord_diagram"));
      buttonSaveChanges.setToolTipText(ResourceBundle.getString("dialog.chord_diagrams.tooltip.save_chord_diagram"));
      buttonSaveChanges.setActionCommand(BUTTON_SAVE_CHORD_DIAGRAM);
      buttonSaveChanges.addActionListener(this);
      buttonSaveChanges.setEnabled(false);

      // create the "clear" button
      buttonClearFretting = new JButton(ResourceBundle.getString("dialog.chord_diagrams.button.clear_chord_diagram"));
      buttonClearFretting.setToolTipText(ResourceBundle.getString("dialog.chord_diagrams.tooltip.clear_chord_diagram"));
      buttonClearFretting.setActionCommand(BUTTON_CLEAR_CHORD_DIAGRAM);
      buttonClearFretting.addActionListener(this);
      buttonClearFretting.setEnabled(false);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(panelFretboard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(buttonSaveChanges)
                  .addGap(10, 10, 10)
                  .addComponent(buttonClearFretting))))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelFretboard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(buttonSaveChanges )
               .addComponent(buttonClearFretting)))
      );
   }

   /**
    * @return a panel which will be used for editing a chord diagram and setting the chord name.
    */
   private JPanel createPanelTop()
   {
      JPanel panelTop              = new JPanel();
             panelChordDiagramEdit = createPanelEditChordDiagram();
      JPanel panelChordName        = createPanelChordName();

      // set the layout manager
      GroupLayout layout = new GroupLayout(panelTop);
      panelTop.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(panelChordDiagramEdit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(panelChordName       , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(panelChordDiagramEdit,                                 GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
         .addComponent(panelChordName       , GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
      );
      return panelTop;
   }

   /**
    * @return the chord diagram list toolbar.
    */
   private JToolBar createToolbar()
   {
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("dialog.chord_diagrams.toolbar.title"));
      ToolbarItemInfo[] toolbarItemInfos = {new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("dialog.chord_diagrams.toolbar.chord_dictionary.icon"), ResourceBundle.getString("dialog.chord_diagrams.toolbar.chord_dictionary.tooltip"), BUTTON_CHORD_DIAGRAM_CHORD_DICTIONARY, this, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Separator                                                                                                                                                                                                                      ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("dialog.chord_diagrams.toolbar.add.icon"             ), ResourceBundle.getString("dialog.chord_diagrams.toolbar.add.tooltip"             ), BUTTON_CHORD_DIAGRAM_ADD             , this, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("dialog.chord_diagrams.toolbar.delete.icon"          ), ResourceBundle.getString("dialog.chord_diagrams.toolbar.delete.tooltip"          ), BUTTON_CHORD_DIAGRAM_DELETE          , this, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("dialog.chord_diagrams.toolbar.move_left.icon"       ), ResourceBundle.getString("dialog.chord_diagrams.toolbar.move_left.tooltip"       ), BUTTON_CHORD_DIAGRAM_MOVE_LEFT       , this, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("dialog.chord_diagrams.toolbar.move_right.icon"      ), ResourceBundle.getString("dialog.chord_diagrams.toolbar.move_right.tooltip"      ), BUTTON_CHORD_DIAGRAM_MOVE_RIGHT      , this, true )};

      // create the instruments combo box
      // due to a quirk in the JComboBox class, cells that have the same text are considered equal, and thus, even though a user may select a different cell,
      // if the new cell has the same text as the old cell, then no item change or action is fired.  For example, if cell 3 displayed "rhythm guitar" and
      // cell 7 also has "rhythm guitar", then no events are fired by the combo box if the user changes his selection from cell 3 to cell 7.
      // Thus, we prepend the index of the cell before the instrument's name to ensure uniqueness and that list selection events are therefore fired.
      Set<Instrument> instruments = instrumentChordDiagrams.keySet();
      String[]        choices     = new String[instruments.size()];
      int             i           = 0;
      for(Instrument instrument : instruments)
      {
         choices[i] = (i + 1) + ". " + instrument.getName();
         i++;
      }

      comboBoxInstruments = new JComboBox(choices);
      comboBoxInstruments.setEnabled(true);
      comboBoxInstruments.setSelectedIndex(selectedInstrumentIndex);
      comboBoxInstruments.setPreferredSize(COMBO_BOX_INSTRUMENT_SIZE);
      comboBoxInstruments.setMaximumSize  (COMBO_BOX_INSTRUMENT_SIZE);
      comboBoxInstruments.setToolTipText(ResourceBundle.getString("dialog.chord_diagrams.toolbar.instrument.tooltip"));
      comboBoxInstruments.addItemListener(this);

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      toolbar.add(comboBoxInstruments);

      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());
      return toolbar;
   }

   /**
    * @return the list of chord diagrams for all defined instruments.
    */
   public ArrayList<ChordDiagram> getChordDiagrams()
   {
      ArrayList<ChordDiagram> chordDiagrams = new ArrayList<ChordDiagram>();
      for(Instrument instrument : instrumentChordDiagrams.keySet())
      {
         for(ChordDiagram chordDiagram : instrumentChordDiagrams.get(instrument))
            chordDiagrams.add(chordDiagram);
      }
      return chordDiagrams;
   }

   /**
    * implements the ItemListener interface and is called whenever the instruments combo box in the toolbar changes.
    * <br/><br/>
    * @param event  combo box event.
    */
   public void itemStateChanged(ItemEvent event)
   {
      if (event.getStateChange() == ItemEvent.SELECTED)
      {
         if (isResettingInstrumentIndex)
         {
            isResettingInstrumentIndex = false;
         }
         else
         {
            int option = JOptionPane.YES_OPTION;

            // was the user in the middle of editing a chord diagram
            if (panelFretboard.hasBeenChanged())
            {
               option = JOptionPane.showConfirmDialog(this, ResourceBundle.getString("gui.error.chord_diagram.save_changes"),
                                                      ResourceBundle.getString("gui.text.dialog_title.warning"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
               if (option == JOptionPane.YES_OPTION)
               {
                  if (!saveChordDiagram())
                     option = JOptionPane.CANCEL_OPTION;
               }
            }
            if (option == JOptionPane.CANCEL_OPTION)
            {
               // restore the combo box back to the current instrument choice
               isResettingInstrumentIndex = true;
               comboBoxInstruments.setSelectedIndex(selectedInstrumentIndex);
            }
            else
            {
               // set the selected instrument
               selectedInstrumentIndex = comboBoxInstruments.getSelectedIndex();
               int i     = 0;
               for(Instrument instrument : instrumentChordDiagrams.keySet())
               {
                  if (i == selectedInstrumentIndex)
                  {
                     selectedInstrument = instrument;
                     break;
                  }
                  i++;
               }

               // reset the selected chord diagram
               selectedChordDiagram = -1;
               // set the chord diagrams to display for the selected instrument
               table.setModel(new ChordDiagramsTableModel(instrumentChordDiagrams.get(selectedInstrument)));

               // remove the old chord diagram editor and create a new one
               // this is necessary because the new instrument may have a capo (which affects the top fret), may have a different number of strings (6 or 7), etc.
               resetChordDiagramEditor(false);
            }
         }
      }
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // save any changes to the current chord diagram being edited
      saveChordDiagram();

      // no other code necessary
      // chord diagrams are obtained from the dialog via the getChordDiagrams() method
   }

   /**
    * reset the chord diagram editor to have a blank chord diagram.
    * this is done by removing the old chord diagram editor and creating a new one.
    * <br/><br/>
    * @param hasBeenChanged whether to mark the chord diagram being edited as having been changed or not.
    */
   private void resetChordDiagramEditor(boolean hasBeenChanged)
   {
      panelChordDiagramEdit.remove(panelFretboard);
      panelChordDiagramEdit.remove(buttonSaveChanges);
      panelChordDiagramEdit.remove(buttonClearFretting);
      createPanelEditChordDiagramComponents(panelChordDiagramEdit, hasBeenChanged);
      panelChordDiagramEdit.validate();
   }

   /**
    * @return whether the current chord diagram being edited could be saved to the list of chord diagrams.
    */
   private boolean saveChordDiagram()
   {
      boolean saved = false;

      if (panelFretboard.getChordDiagram().getName() == null || panelFretboard.getChordDiagram().getName().trim().length() == 0)
      {
          JOptionPane.showMessageDialog(this, ResourceBundle.getString("gui.error.chord.diagram.no_chord_name_specified"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         ArrayList<ChordDiagram> chordDiagrams = instrumentChordDiagrams.get(selectedInstrument);
         Tuning                  tuning        = selectedInstrument.getTuning();

         // are we editing a new chord diagram
         if (selectedChordDiagram == -1)
         {  // add it to the end of the list
            chordDiagrams.add(new ChordDiagram(panelFretboard.getChordDiagram()));
            // update the table model
            table.setModel(new ChordDiagramsTableModel(chordDiagrams));
            // select the newly saved chord diagram
            selectedChordDiagram    = chordDiagrams.size() - 1;
            selectedChordDiagramRow = table.getModel().getRowCount() - 1;
            selectedChordDiagramCol = chordDiagrams.size() % table.getModel().getColumnCount() == 0 ? table.getModel().getColumnCount() - 1 : chordDiagrams.size() % table.getModel().getColumnCount() - 1;
         }
         // or are we editing an existing chord diagram
         else
         {
            // remove the old, original chord diagram, and then replace it with a copy of the the newly edited one
            chordDiagrams.remove(selectedChordDiagram);
            chordDiagrams.add(selectedChordDiagram, new ChordDiagram(panelFretboard.getChordDiagram()));
            table.setModel(new ChordDiagramsTableModel(chordDiagrams));
         }
         // set the focus on the newly saved chord diagram
         table.changeSelection(selectedChordDiagramRow, selectedChordDiagramCol, false, false);
         table.requestFocusInWindow();

         // set that the user has not modified the current chord diagram
         panelFretboard.resetHasBeenChanged();
         // update the toolbar and save/clear buttons
         updateControls();
         // redraw the chord diagram editor
         repaint();

         saved = true;
      }
      return saved;
   }

   /**
    * implements the Observer interface.
    * this method is called whenever a user makes a change to the chord diagram in the chord diagram editor.
    * <br/><br/>
    * @param observable  chord diagram editor that the user has used to edit the current chord diagram.
    * @param arg         not used - will always be null.
    */
   public void update(Observable observable, Object arg)
   {
      updateControls();
   }

   /**
    * updates various controls in the dialog box depending on the state of the chord diagram being edited..
    */
   private void updateControls()
   {
      boolean                 chordDiagramIsSelected = selectedChordDiagram != -1;       // whether the user has hi-lighted a chord diagram from the list at the bottom of the dialog
      boolean                 chordDiagramInProgress = panelFretboard.hasBeenChanged();  // whether the user has already begun editing a chord diagram in the upper left hand corner of the dialog
      ArrayList<ChordDiagram> chordDiagrams          = instrumentChordDiagrams.get(selectedInstrument);

      // update the toolbar
      toolbar.getComponent(TOOLBAR_DELETE          ).setEnabled(chordDiagramIsSelected);
      toolbar.getComponent(TOOLBAR_MOVE_LEFT       ).setEnabled(chordDiagramIsSelected && selectedChordDiagram != 0                       );
      toolbar.getComponent(TOOLBAR_MOVE_RIGHT      ).setEnabled(chordDiagramIsSelected && selectedChordDiagram != chordDiagrams.size() - 1);

      // update the "save" and "clear" buttons in the chord diagram editor
      buttonClearFretting.setEnabled(!panelFretboard.getChordDiagram().isBlank());
      buttonSaveChanges  .setEnabled(chordDiagramInProgress);

      // update the list of possible names for the chord diagram
      listChordNames.setModel(new ListModelChordNames(panelFretboard.getChordDiagram(), selectedInstrument.getTuning(), selectedInstrument.getCapo(), usesSharps));

      // have the dialog call the layout manager and redraw itself
      invalidate();
   }

   /**
    * implements the ListSelectionListener interface, and is called whenever a user selects a chord diagram in the chord diagram list table.
    * It is called once if the newly selected chord diagram is in a different row    than the previously selected chord diagram, and
    * it is called once if the newly selected chord diagram is in a different column than the previously selected chord diagram.
    * So, if the user chooses a new chord diagram in both a different row and a different column, this method is called twice.
    * Also, if the table window loses focus, this method is called twice with the selected row and column values both set to -1.
    * <p>
    * this method is also called whenever the user chooses a chord name from the chord name list box in the upper right of the dialog box.
    * </p>
    * <br/><br/>
    * @param event   the event generated by the user selecting a chord diagram from the table.
    */
   public void valueChanged(ListSelectionEvent event)
   {
      if (!event.getValueIsAdjusting())
      {
         if (event.getSource() == table.getSelectionModel() || event.getSource() == table.getColumnModel().getSelectionModel())
         {
            int row = table.getSelectedRow   ();
            int col = table.getSelectedColumn();

//          System.out.println("Selected (" + row + ", " + col + "), source: " + (event.getSource() == table.getSelectionModel() ? "row model" : "col model"));

            // did the user click on a cell in the table or did it simply lose the focus
            if (row >= 0 && row < table.getRowCount() && col >= 0 && col < table.getColumnCount())
            {
               // did the user click on an empty cell
               if (table.getValueAt(row, col) == null)
               {
                  System.out.println("Selected an empty cell");
                  if (selectedChordDiagramRow != -1 && selectedChordDiagramCol != -1)
                  {
                     System.out.println("Restoring to previously selected cell (" + selectedChordDiagramRow + ", " + selectedChordDiagramCol + ")");
                     table.changeSelection(selectedChordDiagramRow, selectedChordDiagramCol, false, false);
                  }
               }
               // or did the user click on a cell that has a chord diagram in it
               else
               {
                  // did the user click on a different chord diagram?
                  if (selectedChordDiagramRow != row || selectedChordDiagramCol != col)
                  {
                     // did the user click on either the "left" or "right" movement icons in the toolbar
                     if (isMovingToAdjacentCell)
                     {
                        // the chord diagram has been moved. reset this to false.
                        isMovingToAdjacentCell = false;

                        // save the linear index of the chord diagram the user selected
                        selectedChordDiagram    = row * table.getColumnCount() + col;
                        selectedChordDiagramRow = row;
                        selectedChordDiagramCol = col;

                        // update the toolbar and save/clear buttons
                        updateControls();
                     }
                     // no - then the user clicked on a different chord diagram to edit
                     else
                     {
                        int option = JOptionPane.YES_OPTION;

                        // see if the user has modified the current chord diagram
                        if (panelFretboard.hasBeenChanged())
                        {
                           option = JOptionPane.showConfirmDialog(this, ResourceBundle.getString("gui.error.chord_diagram.save_changes"),
                                                                  ResourceBundle.getString("gui.text.dialog_title.warning"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                           if (option == JOptionPane.YES_OPTION)
                           {
                              if (!saveChordDiagram())
                                 option = JOptionPane.CANCEL_OPTION;
                           }
                        }
                        // set the currently selected chord diagram being edited back to the old chord diagram
                        if (option == JOptionPane.CANCEL_OPTION)
                        {
                           if (selectedChordDiagramRow == -1 && selectedChordDiagramCol == -1)
                           {
                              table.clearSelection();
                              panelFretboard.requestFocusInWindow();
                           }
                           else
                           {
                              table.changeSelection(selectedChordDiagramRow, selectedChordDiagramCol, false, false);
                              table.requestFocusInWindow();
                           }
                        }
                        // set the chord diagram currently being edited to the newly selected chord diagram
                        else
                        {
                           // save the linear index of the chord diagram the user selected
                           selectedChordDiagram    = row * table.getColumnCount() + col;
                           selectedChordDiagramRow = row;
                           selectedChordDiagramCol = col;

                           // update the chord diagram editor
                           ArrayList<ChordDiagram> chordDiagrams = instrumentChordDiagrams.get(selectedInstrument);
                           ChordDiagram            chordDiagram  = chordDiagrams.get(selectedChordDiagram);
                           panelFretboard.setChordDiagram(new ChordDiagram(chordDiagram));
                           panelFretboard.resetHasBeenChanged();

                           table.changeSelection(selectedChordDiagramRow, selectedChordDiagramCol, false, false);
                           table.requestFocusInWindow();

                           // update the toolbar and save/clear buttons
                           updateControls();

                           // redraw the chord diagram editor
                           repaint();
                        }
                     }
                  }
               }
            }
         }
         else if (event.getSource() == listChordNames)
         {
//          System.out.println("chord name list: " + listChordNames.getSelectedValue()  + " (" + listChordNames.getSelectedIndex() + ")");
            if (listChordNames.getSelectedIndex() != -1)
            {
               panelFretboard.setChordName((String)listChordNames.getSelectedValue());

               // update the toolbar and save/clear buttons
               updateControls();

               // re-select the current chord diagram in the table
               table.requestFocusInWindow();

               // redraw the chord diagram editor
               repaint();
            }
         }
      }
   }
}
