/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.assignment;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.instrument.ActiveInstruments;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.instrument.InstrumentIn;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;




/**
 * This class is a dialog box which allows a user to assign an instrument(s) to a staff in the song.
 * In order for multiple instruments to be assigned to the same staff, they must:
 * <ol>
 *    <li>have the same number of strings</li>
 *    <li>have the same tuning</li>
 *    <li>have the same capo</li>
 * </ol>
 */
public class InstrumentAssignDialog extends BaseDialog
{
   // class members
   private static final int COLUMN_WIDTH_INSTRUMENT_TYPE   = 125;         // width of the instrument type         column
   private static final int COLUMN_WIDTH_INSTRUMENT_NAME   = 200;         // width of the instrument name         column
   private static final int COLUMN_WIDTH_INSTRUMENT_ACTIVE = 120;         // width of the instrument active state column
   private static final int COLUMN_WIDTH_CHECKBOX          = 75;          // width of the checkbox                column

   private static final int TABLE_HEIGHT                   = 150;         // height of the table
   private static final int TABLE_WIDTH                    = 520;         // width  of the table

   // data members
   private byte                  staff;
   private ArrayList<Instrument> instruments;
   private ActiveInstruments     activeInstruments;
   private InstrumentIn          instrumentIn;               // which instruments the user chose to assign to this staff

   // dialog box controls
   private JLabel      labelInstructions;
   private JScrollPane scrollPane;
   private JTable      table;



   /**
    * constructor.
    * <br/><br/>
    * @param frame              the main application frame.
    * @param staff              staff to which instruments will be assigned.
    * @param instruments        list of instruments defined for the song.
    * @param activeInstruments  contains information about the currently assigned instruments prior to the current barline.
    * @param instrumentIn       the instrument assignments for the current barline on the current staff.
    */
   public InstrumentAssignDialog(MainFrame frame, byte staff, ArrayList<Instrument> instruments, ActiveInstruments activeInstruments, InstrumentIn instrumentIn)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.instrument.assign");

      // save the parameters so they can be used to initialize the dialog box controls
      this.staff             = staff;
      this.instruments       = instruments;
      this.activeInstruments = activeInstruments;
      this.instrumentIn      = instrumentIn;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.instrument.assign.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JPanel controlsPanel = getControlsPanel();

      // create the instructions
      labelInstructions = new JLabel(ResourceBundle.format("dialog.instrument.assign.instructions", (byte)(staff + 1)));

      // create the table
      scrollPane = new JScrollPane();
      table      = new JTable();

      table.setRowSelectionAllowed   (false);
      table.setColumnSelectionAllowed(false);
      table.setShowHorizontalLines   (true );
      table.setShowVerticalLines     (true );
      table.setFillsViewportHeight   (true );

      // set the table model
      table.setModel(new InstrumentAssignTableModel(staff, instruments, activeInstruments, instrumentIn));

      // set the instrument type column to a fixed, non-resizable width
      TableColumn column = table.getColumnModel().getColumn(InstrumentAssignTableModel.COLUMN_INSTRUMENT_TYPE);
      column.setMaxWidth      (COLUMN_WIDTH_INSTRUMENT_TYPE);
      column.setMinWidth      (COLUMN_WIDTH_INSTRUMENT_TYPE);
      column.setPreferredWidth(COLUMN_WIDTH_INSTRUMENT_TYPE);
      column.setResizable(false);

      // set the instrument name column to a fixed, non-resizable width
      column = table.getColumnModel().getColumn(InstrumentAssignTableModel.COLUMN_INSTRUMENT_NAME);
      column.setMaxWidth      (COLUMN_WIDTH_INSTRUMENT_NAME);
      column.setMinWidth      (COLUMN_WIDTH_INSTRUMENT_NAME);
      column.setPreferredWidth(COLUMN_WIDTH_INSTRUMENT_NAME);
      column.setResizable(false);

      // set the instrument active state column to a fixed, non-resizable width
      column = table.getColumnModel().getColumn(InstrumentAssignTableModel.COLUMN_INSTRUMENT_ACTIVE);
      column.setMaxWidth      (COLUMN_WIDTH_INSTRUMENT_ACTIVE);
      column.setMinWidth      (COLUMN_WIDTH_INSTRUMENT_ACTIVE);
      column.setPreferredWidth(COLUMN_WIDTH_INSTRUMENT_ACTIVE);
      column.setResizable(false);

      // set the checkbox column to a fixed, non-resizable width
      column = table.getColumnModel().getColumn(InstrumentAssignTableModel.COLUMN_CHECKBOX);
      column.setMaxWidth      (COLUMN_WIDTH_CHECKBOX);
      column.setMinWidth      (COLUMN_WIDTH_CHECKBOX);
      column.setPreferredWidth(COLUMN_WIDTH_CHECKBOX);
      column.setResizable(false);

      // add the table to a scroll pane
      scrollPane.setViewportView(table);

      // set the layout manager
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      // layout the instructions and table
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
               .addComponent(scrollPane       , GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE  , TABLE_WIDTH, Short.MAX_VALUE)
               .addComponent(labelInstructions, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, TABLE_WIDTH, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
           layout.createParallelGroup(GroupLayout.Alignment.LEADING)
           .addGroup(layout.createSequentialGroup()
              .addComponent(labelInstructions)
              .addGap(15, 15, 15)
              .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, TABLE_HEIGHT, GroupLayout.PREFERRED_SIZE)
              .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

      // invoke the layout manager
      pack();
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

      super.actionPerformed(event);
   }

   /**
    * @return the instrument the user deleted.
    */
   public InstrumentIn getInstrumentIn()
   {
      return instrumentIn;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // copy the value of the checkboxes into the instrument in's staff instruments boolean array
      boolean[]  staffInstruments = instrumentIn.getStaffInstruments();
      TableModel tableModel       = table.getModel();

      for(int i=0; i<tableModel.getRowCount(); ++i)
      {
         staffInstruments[i] = ((Boolean)tableModel.getValueAt(i, InstrumentAssignTableModel.COLUMN_CHECKBOX)).booleanValue();
      }
   }
}
