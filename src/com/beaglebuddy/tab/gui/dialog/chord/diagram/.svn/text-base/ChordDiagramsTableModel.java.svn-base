/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.diagram;

import com.beaglebuddy.tab.model.ChordDiagram;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;





/**
 * table model for the list of chord diagrams.
 */
public class ChordDiagramsTableModel extends AbstractTableModel
{
   // class members
   public static final int NUM_COLUMNS = 7;     // number of columns in the table

   // data members
   private Object[][] data;                     // data (chord diagrams) in the table
   private int        numRows;                  // number of rows    of data in the table





   /**
    * constructor.
    * <br/><br/>
    * @param chordDiagrams   list of chord diagrams defined for the song.
    */
   public ChordDiagramsTableModel(ArrayList<ChordDiagram> chordDiagrams)
   {
      numRows = chordDiagrams.size() / NUM_COLUMNS + (chordDiagrams.size() % NUM_COLUMNS == 0 ? 0 : 1);
      data    = new Object[numRows][NUM_COLUMNS];

      // create the columns
      int i = 0;
      for(int row=0; row<numRows; ++row)
         for(int col=0; col<NUM_COLUMNS; ++col)
            data[row][col] = (i < chordDiagrams.size() ? chordDiagrams.get(i++) : null);
   }

   /**
    * adds a row to the end of the table.
    */
   public void addRow()
   {
      // copy the existing data into a new array
      Object[][] newData = new Object[numRows+1][NUM_COLUMNS];
      for(int row=0; row<numRows; ++row)
         for(int col=0; col<NUM_COLUMNS; ++col)
            newData[row][col] = data[row][col];

      // add the new row of data
      for(int col=0; col<NUM_COLUMNS; ++col)
         newData[numRows][col] = null;

      data = newData;
      fireTableRowsInserted(numRows, numRows);
      numRows++;
   }

   /*
    * JTable uses this method to determine the default renderer/editor for each cell.
    * If this method was not overridden, then the default column render would be used.
    * @return the class type of the specified column.
    * <br/><br/>
    * @param col  column whose type is to be returned.
    */
   @Override
   public Class<?> getColumnClass(int col)
   {
      return ChordDiagram.class;
   }

   /**
    * @return the number of columns in the table.
    */
   public int getColumnCount()
   {
      return NUM_COLUMNS;
   }

   /**
    * @return the name of the requested column.
    * <br/><br/>
    * @param col  column for which the header name is being sought.
    */
   @Override
   public String getColumnName(int col)
   {
      return String.valueOf(col);
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
    * @return the table data at the specified row and column.
    * <br/><br/>
    * @param row  row    of the requested table data.
    * @param col  column of the requested table data.
    */
   public Object getValueAt(int row, int col)
   {
      return data[row][col];
   }

   /**
    * @return whether a cell is editable.
    * <br/><br/>
    * @param row  row    of the cell in question.
    * @param col  column of the cell in question.
    */
   @Override
   public boolean isCellEditable(int row, int col)
   {
      return false;
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
