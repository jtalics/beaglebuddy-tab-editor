/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.assignment;

import com.beaglebuddy.tab.model.instrument.ActiveInstruments;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.instrument.InstrumentIn;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;




/**
 * table model for the instrument assign dialog box.
 */
class InstrumentAssignTableModel extends AbstractTableModel
{
   // class members
   public static final int COLUMN_INSTRUMENT_TYPE   = 0;
   public static final int COLUMN_INSTRUMENT_NAME   = 1;
   public static final int COLUMN_INSTRUMENT_ACTIVE = 2;
   public static final int COLUMN_CHECKBOX          = 3;
   public static final int NUM_COLUMNS              = 4;

   // data members
   private Object[][] data;           // data in the table
   private String[]   columnNames;    // column headers




   /**
    * constructor.
    * <br/><br/>
    * @param staff              staff to which instruments will be assigned.
    * @param instruments        list of instruments defined for the song.
    * @param activeInstruments  contains information about the currently assigned instruments prior to the current barline.
    * @param instrumentIn       the instrument assignments for the current barline on the current staff.
    */
   public InstrumentAssignTableModel(byte staff, ArrayList<Instrument> instruments, ActiveInstruments activeInstruments, InstrumentIn instrumentIn)
   {
      if (activeInstruments.getNumInstruments() != instruments.size())
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.number_active_instruments", activeInstruments.getNumInstruments(), instruments.size()));

      data        = new Object[instruments.size()][NUM_COLUMNS];
      columnNames = new String[NUM_COLUMNS];

      // create the column of instrument types
      for(int i=0; i<instruments.size(); ++i)
         data[i][COLUMN_INSTRUMENT_TYPE] = instruments.get(i).getTypeName();


      // create the column of instrument names
      for(int i=0; i<instruments.size(); ++i)
         data[i][COLUMN_INSTRUMENT_NAME] = instruments.get(i).getName();

      // create the column of instrument active states
      for(int i=0; i<instruments.size(); ++i)
      {
         Instrument instrument    = instruments.get(i);
         byte       assignedStaff = activeInstruments.getAssignedStaff(instrument.getId());

         data[i][COLUMN_INSTRUMENT_ACTIVE] = (assignedStaff == -1 ? "" : ResourceBundle.format("text.active_instruments.music_staff", assignedStaff));
      }

      // create the column of check boxes
      for(int i=0; i<data.length; ++i)
         data[i][COLUMN_CHECKBOX] = new Boolean(instrumentIn.getStaffInstruments()[i]);

      // create the headings
      columnNames[COLUMN_INSTRUMENT_TYPE  ] = ResourceBundle.getString("dialog.instrument.assign.col_header.instrument_type");
      columnNames[COLUMN_INSTRUMENT_NAME  ] = ResourceBundle.getString("dialog.instrument.assign.col_header.instrument_name");
      columnNames[COLUMN_INSTRUMENT_ACTIVE] = ResourceBundle.getString("dialog.instrument.assign.col_header.active"         );
      columnNames[COLUMN_CHECKBOX         ] = ResourceBundle.getString("dialog.instrument.assign.col_header.assign"         );
   }

   /**
    * @return the number of columns in the table.
    */
   public int getColumnCount()
   {
      return NUM_COLUMNS;
   }

   /**
    * @return the number of rows in the table.
    */
   public int getRowCount()
   {
      return data.length;
   }

   /**
    * @return the name of the requested column.
    * <br/><br/>
    * @param col  column for which the header name is being sought.
    */
   @Override
   public String getColumnName(int col)
   {
      return columnNames[col];
   }

   /**
    * @return the table data at the specified row and column.
    * <br/><br/>
    * @param row  row    of the requested table data.
    * @param col  column of the requested table data.
    */
   public Object getValueAt(int row, int col)
   {
      return data[row][col];
   }

   /*
    * JTable uses this method to determine the default renderer/editor for each cell.
    * If this method was not overridden, then the default column render would be used, and the boolean values would be displayed as
    * the text "true" and "false" instead of a check box.
    * @return the class type of the specified column.
    * <br/><br/>
    * @param col  column whose type is to be returned.
    */
   @Override
   public Class<?> getColumnClass(int col)
   {
      return getValueAt(0, col).getClass();
   }

   /**
    * @return whether a cell is editable.
    * only allow the checkbox column cells to be editable.
    * <br/><br/>
    * @param row  row    of the cell in question.
    * @param col  column of the cell in question.
    */
   @Override
   public boolean isCellEditable(int row, int col)
   {
      return col == COLUMN_CHECKBOX;
   }

   /**
    * sets the value of the table data at the specified row and column.
    * <br/><br/>
    * @param value  data to be set.
    * @param row    row    of the cell whose data is to be set.
    * @param col    column of the cell whose data is to be set.
    */
   @Override
   public void setValueAt(Object value, int row, int col)
   {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
   }
}
