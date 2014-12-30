/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.instruments;

import com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls.InstrumentsTableModel;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.Volume;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;




/**
 * This dialog box displays the existing instruments in the song, and allows the user to edit them.
 * It also provides buttons to add new instruments or delete existing instruments.
 */
public class InstrumentsDialog extends InstrumentsBaseDialog implements ListSelectionListener, TableModelListener
{
   // class members
   private static final int TABLE_HEIGHT                       = 140;         // height of the instruments list table
   private static final int TABLE_WIDTH                        = 425;         // width  of the instruments list table
   private static final int TABLE_COLUMN_WIDTH_CHECKBOX        = 60;          // width of the checkbox        column
   private static final int TABLE_COLUMN_WIDTH_INSTRUMENT_TYPE = 100;         // width of the instrument type column
   private static final int TABLE_COLUMN_WIDTH_INSTRUMENT_NAME = 300;         // width of the instrument name column

   // data members
   protected ArrayList<Instrument> instruments;                               // list of the currently defined instruments for the song
   protected byte                  nextInstrumentId;                          // next id to use when a user adds a new instrument
   protected int                   selectedInstrumentIndex;                   // index in the instruments array list of the currently selected instrument, or -1 if there are no instruments

   // dialog controls
   protected JScrollPane scrollPaneInstrumentList;
   protected JTable      tableInstruments;
   protected JButton     buttonAddInstrument;
   protected JButton     buttonDeleteInstrument;



   /**
    * constructor.
    * <br/><br/>
    * @param frame         the main application frame.
    * @param instruments   list of instruments to display.
    */
   public InstrumentsDialog(MainFrame frame, ArrayList<Instrument> instruments)
   {
      super(frame, instruments == null || instruments.size() == 0 ? null : instruments.get(0), "dialog.instruments", "dialog.instruments.title");

      // make a copy of the instruments for the user to edit
      ArrayList<Instrument> newInstruments = new ArrayList<Instrument>(instruments.size());
      for(Instrument instrument : instruments)
         newInstruments.add(instrument.clone());
      this.instruments = newInstruments;

      // set the instrument that initially will be displayed
      selectedInstrument = instruments == null || instruments.size() == 0 ? null : instruments.get(0);

      // set the next id to use when creating a new instrument
      nextInstrumentId = -1;
      if (instruments != null)
      {
         for(Instrument instrument : instruments)
            if (instrument.getId() > nextInstrumentId)
               nextInstrumentId = instrument.getId();
         nextInstrumentId++;
      }

      // set the index of the currently selected instrument in the instruments list table
      selectedInstrumentIndex = (instruments == null || instruments.size() == 0 ? -1 : 0);
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

      // did the user click the add, edit, or delete button?
      if (source != null && source instanceof JButton)
      {
         if (command.equals("add"))
         {
            InstrumentAddDialog instrumentAddDialog = new InstrumentAddDialog((MainFrame)getParent(), !hasDrums());
            instrumentAddDialog.setVisible(true);

            // if the user pressed ok, then add the new instrument
            if (instrumentAddDialog.wasOKed())
            {
               Instrument instrument = instrumentAddDialog.getInstrument();
               instrument.setId(nextInstrumentId++);
               instruments.add(instrument);

               // add the instrument to the table
               InstrumentsTableModel model = (InstrumentsTableModel)tableInstruments.getModel();
               model.addRow(instrument);
               tableInstruments.changeSelection(instruments.size()-1, 0, false, false);
            }
         }
         else if (command.equals("delete"))
         {
            // because the removeRow() method of the table model fires an event, if we delete the instrument(s)
            // from the array list before deleting them from the table, we get an index out of bounds exception.
            // thus, we first remove them from the table, and then actually remove them from the array list.

            // get a list of which instruments that were "checked" for deletion
            boolean[] instrumentChecks = getInstrumentChecks();

            // remove the instrument(s) from the table
            InstrumentsTableModel model      = (InstrumentsTableModel)tableInstruments.getModel();
            int                   numDeleted = 0;
            for(int i=0; i<instrumentChecks.length; ++i)
            {
               if (instrumentChecks[i])
               {
                  model.removeRow(i - numDeleted);
                  numDeleted++;
               }
            }
            // remove all the instrument(s) that were "checked" off in the table for deletion.
            numDeleted = 0;
            for(int i=0; i<instrumentChecks.length; ++i)
            {
               if (instrumentChecks[i])
               {
                  instruments.remove(i - numDeleted);
                  numDeleted++;
               }
            }

            // highlight the first instrument in the table
            if (instruments.size() != 0)
            {
               tableInstruments.changeSelection(0, 0, false, false);
               // get the index of the newly selected instrument
               selectedInstrumentIndex = tableInstruments.getSelectedRow();
               // set the selected instrument
               selectedInstrument = (selectedInstrumentIndex >= 0 && selectedInstrumentIndex < instruments.size() ? instruments.get(selectedInstrumentIndex) : null);
               // update the instrument settings with ones from the newly selected instrument
               updateAllControls(selectedInstrument);
            }
            else
            {
               selectedInstrumentIndex = -1;
            }
         }
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
	   super.addComponents();

	   // set the initial focus on the 1st instrument in the table
      tableInstruments.requestFocusInWindow();
      if (instruments.size() != 0)
         tableInstruments.changeSelection(0, 0, false, false);
   }

   /**
    * @return the top panel containing the list of instruments.
    */
   @Override
   protected JPanel createTopPanel()
   {
      JPanel panel = new JPanel();

      panel.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.instruments.border.title.instruments")));

      // create the table
      scrollPaneInstrumentList = new JScrollPane();
      tableInstruments         = new JTable();
      tableInstruments.setCellSelectionEnabled  (false);
      tableInstruments.setRowSelectionAllowed   (true );
      tableInstruments.setColumnSelectionAllowed(false);
      tableInstruments.setShowHorizontalLines   (true );
      tableInstruments.setShowVerticalLines     (true );
      tableInstruments.setFillsViewportHeight   (true );

      // set the table model
      tableInstruments.setModel(new InstrumentsTableModel(instruments));

      // add the listener for when a user selects an instrument from the table
      ListSelectionModel selectionModel = tableInstruments.getSelectionModel();
      selectionModel.addListSelectionListener(this);

      // add the listener for when the user checks/unchecks a checkbox
      tableInstruments.getModel().addTableModelListener(this);

      // set the checkbox column to a fixed, non-resizable width
      TableColumn column = tableInstruments.getColumnModel().getColumn(InstrumentsTableModel.COLUMN_CHECKBOX);
      column.setMaxWidth      (TABLE_COLUMN_WIDTH_CHECKBOX);
      column.setMinWidth      (TABLE_COLUMN_WIDTH_CHECKBOX);
      column.setPreferredWidth(TABLE_COLUMN_WIDTH_CHECKBOX);
      column.setResizable(false);

      // set the instrument type column to a fixed, non-resizable width
      column = tableInstruments.getColumnModel().getColumn(InstrumentsTableModel.COLUMN_INSTRUMENT_TYPE);
      column.setMaxWidth      (TABLE_COLUMN_WIDTH_INSTRUMENT_TYPE);
      column.setMinWidth      (TABLE_COLUMN_WIDTH_INSTRUMENT_TYPE);
      column.setPreferredWidth(TABLE_COLUMN_WIDTH_INSTRUMENT_TYPE);
      column.setResizable(false);

      // set the instrument name column to a fixed, non-resizable width
      column = tableInstruments.getColumnModel().getColumn(InstrumentsTableModel.COLUMN_INSTRUMENT_NAME);
      column.setMaxWidth      (TABLE_COLUMN_WIDTH_INSTRUMENT_NAME);
      column.setMinWidth      (TABLE_COLUMN_WIDTH_INSTRUMENT_NAME);
      column.setPreferredWidth(TABLE_COLUMN_WIDTH_INSTRUMENT_NAME);
      column.setResizable(false);

      // add the table to a scroll pane
      scrollPaneInstrumentList.setViewportView(tableInstruments);

      // create the three "add", "edit", and "delete" buttons
      buttonAddInstrument    = new JButton(ResourceBundle.getString("dialog.instruments.button.add"   ));
      buttonDeleteInstrument = new JButton(ResourceBundle.getString("dialog.instruments.button.delete"));
      buttonAddInstrument   .addActionListener(this);
      buttonDeleteInstrument.addActionListener(this);
      buttonAddInstrument   .setActionCommand("add"   );
      buttonDeleteInstrument.setActionCommand("delete");
      buttonAddInstrument   .setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.add"   ));
      buttonDeleteInstrument.setToolTipText(ResourceBundle.getString("dialog.instruments.tooltip.delete"));

      // layout the components
      GroupLayout panelLayout = new GroupLayout(panel);
      panel.setLayout(panelLayout);

      // layout the components horizontally
      panelLayout.setHorizontalGroup(
         panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(scrollPaneInstrumentList, GroupLayout.DEFAULT_SIZE, TABLE_WIDTH, Short.MAX_VALUE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(buttonAddInstrument   )
               .addComponent(buttonDeleteInstrument))
            .addContainerGap())
      );
      panelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonAddInstrument, buttonDeleteInstrument});

      // layout the components vertically
      panelLayout.setVerticalGroup(
         panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelLayout.createSequentialGroup()
            .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
               .addGroup(panelLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(buttonAddInstrument)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(buttonDeleteInstrument))
               .addComponent(scrollPaneInstrumentList, GroupLayout.PREFERRED_SIZE, TABLE_HEIGHT, GroupLayout.PREFERRED_SIZE)))
      );
      panelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonAddInstrument, buttonDeleteInstrument});

      return panel;
   }

   /**
    * @return the instrument(s) the user created.
    */
   public ArrayList<Instrument> getInstruments()
   {
      return instruments;
   }

   /**
    * @return an array of booleans indicating which instruments the user has checked (marked for deletion) and which ones he hasn't.
    */
   protected boolean[] getInstrumentChecks()
   {
      InstrumentsTableModel model   = (InstrumentsTableModel)tableInstruments.getModel();
      boolean[]            checked = new boolean[model.getRowCount()];

      for(int row=0; row<model.getRowCount(); ++row)
         checked[row] = ((Boolean)(model.getValueAt(row, InstrumentsTableModel.COLUMN_CHECKBOX))).booleanValue();

      return checked;
   }

   /**
    * @return whether the list of instruments contains a drum set.
    */
   protected boolean hasDrums()
   {
      boolean hasDrums = false;

      for(int i=0; i<instruments.size() && !hasDrums; ++i)
         hasDrums = instruments.get(i).getType() == Instrument.Type.Drums;

      return hasDrums;
   }

   /**
    * @return whether the user has checked (marked for deletion) any of the instruments in the table.
    */
   protected boolean hasInstrumentsChecked()
   {
      boolean   checked          = false;
      boolean[] instrumentChecks = getInstrumentChecks();

      for(int i=0; i<instrumentChecks.length && !checked; ++i)
         checked = instrumentChecks[i];

      return checked;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // save the current values of the controls to the previously selected instrument
      updateInstrument();
   }

   /**
    * implements the ChangeListener interface, and is called whenever the <i>number of strings</i> or one of the <i>tuning note</i> spinners
    * changes value.
    * <br/><br/>
    * @param event   the spinner state change event.
    */
   @Override
   public void stateChanged(ChangeEvent event)
   {
      if (event.getSource() == spinnerNumStrings)
      {
         int    numStrings = ((Integer)spinnerNumStrings.getValue()).intValue();
         String tuningName = (String)comboBoxTuning.getSelectedItem();

         if (tuningName.equals("custom"))
         {
            // retrieve the current tuning notes from the tuning note spinner controls
            Tuning tuning = getTuning();
            updateTuningNoteControls(selectedInstrument.getType(), tuning);
         }
         else
         {
            // see if there is a predefined tuning in the tuning dictionary with the same name for the new number of strings
            // for example: if the user currently has "standard" selected for a guitar with 6 strings, and then changes the number of strings to 7,
            //              see if there is a standard tuning for guitars with 7 strings called "standard".
            Tuning tuning = TuningDictionary.getTuning(selectedInstrument.getType(), numStrings, tuningName);
            if (tuning == null)
            {
               // this is now a custom tuning
               // however, this case can be avoided by making sure the tuning dictionary has a given tuning defined for all number of strings
               // thus, we'll display a little error message indicating that the tuning dictionary needs to fixed
               JOptionPane.showMessageDialog(null, "The tuning dictionary is inconsistent for the tuning " + tuningName, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
               // then the tunging name is ok, we just need to update the tuning note spinner controls
               updateTuningNoteControls(selectedInstrument.getType(), tuning);
            }
         }
      }
      else if (event.getSource() == spinnerCapo)
      {
         // no code necessary
      }
      else
      {
         // the user manually changed one of the notes in the tuning via the note spinners
         updateTuningNameComboBox();
      }
   }

   /**
    * implements the TableModelListener interface, and is called whenever the user checks or unchecks a checkbox in the table.
    * <br/><br/>
    * param event  the table cell update event.
    */
   public void tableChanged(TableModelEvent event)
   {
      if (event.getFirstRow() == event.getLastRow() && event.getColumn() == InstrumentsTableModel.COLUMN_CHECKBOX && event.getType() == TableModelEvent.UPDATE)
         // enable\disable the "delete" button
         buttonDeleteInstrument.setEnabled(hasInstrumentsChecked());
   }

   /**
    * updates all the dialog box controls with the values from the instrument currently selected in the instruments list table.
    * <br/><br/>
    * @param selectedInstrument   the instrument whose attributes are used to the values in the dialog box controls.
    */
   @Override
   protected void updateAllControls(Instrument selectedInstrument)
   {
      super.updateAllControls(selectedInstrument);

      // the add button is always enabled
      // no code necessary

      // enable\disable the "delete" button
      buttonDeleteInstrument.setEnabled(hasInstrumentsChecked());
   }

   /**
    * updates the instrument currently selected in the instruments list table with the values from the dialog box controls.
    */
   protected void updateInstrument()
   {
      if (selectedInstrumentIndex != -1)
      {
         Instrument instrument = instruments.get(selectedInstrumentIndex);
         instrument.setName         (textFieldName.getText().trim().length() == 0 ? ResourceBundle.getString("text.untitled") : textFieldName.getText().trim());
         instrument.setPreset       ((Midi.Sound)comboBoxSound.getSelectedItem());
         instrument.setPan          ((byte)sliderPan   .getValue());
         instrument.setReverb       ((byte)sliderReverb.getValue());
         instrument.setChorus       ((byte)sliderChorus.getValue());
         instrument.setCapo         ((Instrument.Fret)spinnerCapo.getValue());
         instrument.setTuning       (getTuning());

         // convert the slider volume into one of the discrete volume levels
         // since there are 9 distinct volume levels whose values are equally spaced up to 128, each interval between volume levels is approximately 16.
         // thus, we check if the slider value is within half the interval distance on either side of the discrete volume level.
         int          volume      = sliderVolume.getValue();
         Volume.Level volumeLevel = Volume.Level.Off;
         for(Volume.Level level : Volume.Level.values())
         {
            if (level.value() - 8 <= volume && volume <= level.value() + 8)
            {
               volumeLevel = level;
               break;
            }
         }
         instrument.setInitialVolume(volumeLevel);
      }
   }

   /**
    * implements the ListSelectionListener interface, and is called whenever a user selects a row in the instruments list table.
    * <br/><br/>
    * @param event   the index of list item the user selected.
    */
   public void valueChanged(ListSelectionEvent event)
   {
      if (!event.getValueIsAdjusting())
      {
         if (event.getSource() == tableInstruments.getSelectionModel())
         {
            InstrumentsTableModel model = (InstrumentsTableModel)tableInstruments.getModel();

            if (selectedInstrumentIndex != -1 && selectedInstrumentIndex < model.getRowCount())
            {
               // save the current values of the controls to the previously selected instrument
               updateInstrument();

               // update the name of the previously selected instrument in the instruments list table.
               model.setValueAt(selectedInstrument.getName(), selectedInstrumentIndex, InstrumentsTableModel.COLUMN_INSTRUMENT_NAME);
            }

            // get the index of the newly selected instrument
            selectedInstrumentIndex = tableInstruments.getSelectedRow();

            // set the selected instrument
            selectedInstrument = (selectedInstrumentIndex >= 0 && selectedInstrumentIndex < instruments.size() ? instruments.get(selectedInstrumentIndex) : null);

            // update the instrument settings with ones from the newly selected instrument
            updateAllControls(selectedInstrument);
//          if (selectedInstrumentIndex != -1 && selectedInstrumentIndex < model.getRowCount())
//          {
//          }
         }
      }
   }
}
