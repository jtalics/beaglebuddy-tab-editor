/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.dictionary;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.dialog.chord.diagram.ChordDiagramCellRenderer;
import com.beaglebuddy.tab.gui.dialog.chord.diagram.ChordDiagramRenderer;
import com.beaglebuddy.tab.gui.dialog.chord.diagram.ChordDiagramsDialog;
import com.beaglebuddy.tab.gui.dialog.chord.diagram.ChordDiagramsTableModel;
import com.beaglebuddy.tab.gui.dialog.chord.name.ChordNameDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.ChordDictionary;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;




/**
 * This class is a dialog box which allows a user to select a chord diagram from the chord dictionary.
 */
public class ChordDictionaryDialog extends ChordNameDialog
{
   // data members
   private ChordDiagram chordDiagram;          // the chord diagram the user has selected from the chord dictionary

   // controls
   private JTable       chordDiagramsTable;    // table containing the list of chord diagrams that match the chord name





   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    */
   public ChordDictionaryDialog(MainFrame frame, String chordName)
   {
      super(frame, chordName, ResourceBundle.getString("dialog.chord_dictionary.title"), "dialog.chord_dictionary");
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JPanel controlsPanel      = getControlsPanel();
      JPanel panelChordName     = createChordNamePanel();
      JPanel panelChordDiagrams = createChordDiagramsPanel();

      // set the layout manager
      GroupLayout  layout       = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(panelChordName    , GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
         .addComponent(panelChordDiagrams, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {panelChordName, panelChordDiagrams});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelChordName    , GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(panelChordDiagrams, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
      );
   }

   /**
    * @return the panel containing the chord diagrams.
    */
   private JPanel createChordDiagramsPanel()
   {
      JPanel      panel      = new JPanel();
      JScrollPane scrollPane = new JScrollPane();
      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.chord_dictionary.chord_diagrams.title")));

      // create the chord diagram table
      chordDiagramsTable = new JTable();
      chordDiagramsTable.setCellSelectionEnabled  (true );
      chordDiagramsTable.setRowSelectionAllowed   (false);
      chordDiagramsTable.setColumnSelectionAllowed(false);
      chordDiagramsTable.setShowHorizontalLines   (false);
      chordDiagramsTable.setShowVerticalLines     (false);
      chordDiagramsTable.setFillsViewportHeight   (true );
      chordDiagramsTable.setTableHeader           (null );

      // set the model to use for the chord diagram table
      ArrayList<ChordDiagram> chordDiagrams = ChordDictionary.getChordDiagrams(chordName.toStringWithoutFretOrType());
      chordDiagramsTable.setModel(new ChordDiagramsTableModel(chordDiagrams));

      // set the custom cell renderer for drawing the chord diagrams in the table
      chordDiagramsTable.setDefaultRenderer(ChordDiagram.class, new ChordDiagramCellRenderer());

      // add the listener for when a user selects a chord diagram from the table
      chordDiagramsTable                 .getSelectionModel().addListSelectionListener(this);   // row selection listener
      chordDiagramsTable.getColumnModel().getSelectionModel().addListSelectionListener(this);   // col selection listener

      // set the height of the rows to a fixed, non-resizable height
      chordDiagramsTable.setRowHeight(ChordDiagramsDialog.TABLE_ROW_HEIGHT);

      // set the columns to a fixed, non-resizable width
      for(int col=0; col<ChordDiagramsTableModel.NUM_COLUMNS; ++col)
      {
         TableColumn column = chordDiagramsTable.getColumnModel().getColumn(col);
         column.setMaxWidth      (ChordDiagramsDialog.TABLE_COLUMN_WIDTH);
         column.setMinWidth      (ChordDiagramsDialog.TABLE_COLUMN_WIDTH);
         column.setPreferredWidth(ChordDiagramsDialog.TABLE_COLUMN_WIDTH);
         column.setResizable(false);
      }

      // add the table to a scroll pane
      scrollPane.setViewportView(chordDiagramsTable);

      // set the layout manager
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, ChordDiagramsDialog.TABLE_WIDTH , Short.MAX_VALUE));
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, ChordDiagramsDialog.TABLE_HEIGHT, Short.MAX_VALUE));

      return panel;
   }

   /**
    * @return the panel containing the chord name.
    */
   private JPanel createChordNamePanel()
   {
      JPanel panel = new JPanel();
      addComponents(panel, ResourceBundle.getString("dialog.chord_dictionary.chord_name.title"));

      return panel;
   }

   /**
    * @return the chord diagram the user selected from the chord dictionary.
    */
   public ChordDiagram getChordDiagram()
   {
      return chordDiagram;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // nothing to do
   }

   /**
    * updates the model based on the current values of the controls.
    */
   @Override
   protected void updateModel()
   {
      super.updateModel();

      if (initialized && chordDiagramsTable != null)
         chordDiagramsTable.setModel(new ChordDiagramsTableModel(ChordDictionary.getChordDiagrams(chordName.toStringWithoutFretOrType())));
   }

   /**
    * implements the ListSelectionListener interface and is called whenever the user selects a chord diagram from the chord dictionary.
    * <br/><br/>
    * @param event  a list box event.
    */
   @Override
   public void valueChanged(ListSelectionEvent event)
   {
      if (!event.getValueIsAdjusting() && initialized)
      {
         // is this an event because the user selected a chord diagram?
         if (chordDiagramsTable != null && (event.getSource() == chordDiagramsTable.getSelectionModel() || event.getSource() == chordDiagramsTable.getColumnModel().getSelectionModel()))
         {
            int row = chordDiagramsTable.getSelectedRow   ();
            int col = chordDiagramsTable.getSelectedColumn();

            // did the user click on a cell in the table with a chord diagram in it
            if (row >= 0 && row < chordDiagramsTable.getRowCount()    &&
                col >= 0 && col < chordDiagramsTable.getColumnCount() &&
                chordDiagramsTable.getValueAt(row, col) != null)
               chordDiagram = (ChordDiagram)chordDiagramsTable.getModel().getValueAt(row, col);
         }
         else
         {
            // it's an event from one of the chord name list boxes
            super.valueChanged(event);
         }
      }
   }
}
