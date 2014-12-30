/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls;

import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;





/**
 * table model for the list of instruments.
 */
public class InstrumentsTableModel extends AbstractTableModel
{
   // class members
   public static final int COLUMN_CHECKBOX        = 0;
   public static final int COLUMN_INSTRUMENT_TYPE = 1;
   public static final int COLUMN_INSTRUMENT_NAME = 2;
   public static final int NUM_COLUMNS            = 3;

   // data members
   private Object[][] data;           // data in the table
   private String[]   columnNames;    // column headers




   /**
    * constructor.
    * <br/><br/>
    * @param instruments   list of instruments defined for the song.
    */
   public InstrumentsTableModel(ArrayList<Instrument> instruments)
   {
      data        = new Object[instruments.size()][NUM_COLUMNS];
      columnNames = new String[NUM_COLUMNS];

      // create the column of check boxes
      for(int i=0; i<data.length; ++i)
         data[i][COLUMN_CHECKBOX] = new Boolean(false);

      // create the column of instrument types
      for(int i=0; i<data.length; ++i)
         data[i][COLUMN_INSTRUMENT_TYPE] = instruments.get(i).getTypeName();

      // create the column of instrument names
      for(int i=0; i<data.length; ++i)
         data[i][COLUMN_INSTRUMENT_NAME] = instruments.get(i).getName();

      // create the headings
      columnNames[COLUMN_CHECKBOX       ] = ResourceBundle.getString("dialog.instruments.col_header.delete"         );
      columnNames[COLUMN_INSTRUMENT_TYPE] = ResourceBundle.getString("dialog.instruments.col_header.instrument_type");
      columnNames[COLUMN_INSTRUMENT_NAME] = ResourceBundle.getString("dialog.instruments.col_header.instrument_name");
   }

   /**
    * adds the specified instrument's data to the end of the table in a new row.
    * <br/><br/>
    * @param instrument  the instrument whose data is to be added to the table.
    */
   public void addRow(Instrument instrument)
   {
      // copy the existing data into a new array
      Object[][] newData = new Object[data.length+1][NUM_COLUMNS];
      for(int row=0; row<data.length; ++row)
         for(int col=0; col<NUM_COLUMNS; ++col)
            newData[row][col] = data[row][col];

      // add the new row of data
      int row = data.length;
      newData[row][COLUMN_CHECKBOX]        = new Boolean(false);
      newData[row][COLUMN_INSTRUMENT_TYPE] = instrument.getTypeName();
      newData[row][COLUMN_INSTRUMENT_NAME] = instrument.getName();

      data = newData;

      fireTableRowsInserted(row, row);
   }

   /**
    * @return the number of columns in the table.
    */
   public int getColumnCount()
   {
      return NUM_COLUMNS;
   }

   /**
    * @return the table model's data.
    */
   public Object[][] getData()
   {
      return data;
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
    * removes the specified row of data from the table.
    * <br/><br/>
    * @param row   row of data that is to be deleted.
    */
   public void removeRow(int row)
   {
      // copy the existing data into a new array
      Object[][] newData = new Object[data.length-1][NUM_COLUMNS];
      for(int r=0; r<row; ++r)
         for(int c=0; c<NUM_COLUMNS; ++c)
            newData[r][c] = data[r][c];

      for(int r=row+1; r<data.length; ++r)
         for(int c=0; c<NUM_COLUMNS; ++c)
            newData[r-1][c] = data[r][c];

      data = newData;

      fireTableRowsDeleted(row, row);
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
