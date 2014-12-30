/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.drum_map;

import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.Arrays;
import javax.swing.table.AbstractTableModel;




/**
 * table model for the drum map dialog box.
 */
class DrumMapTableModel extends AbstractTableModel
{
   // class members
   public static final int NUM_ROWS    = 12;
   public static final int NUM_COLUMNS = 4;

   // how to order the list of midi percussion sounds
   public enum Order
   {
      Alphabetical(NUM_ROWS, NUM_COLUMNS), MidiId(NUM_ROWS, NUM_COLUMNS), Sound(NUM_ROWS, NUM_COLUMNS);

      private int numRows;
      private int numCols;
      Order(int numRows, int numCols) {this.numRows = numRows; this.numCols = numCols;}
      public  int numRows() {return numRows;}
      public  int numCols() {return numCols;}
   }

   // data members
   private DrumMapTableCell[] percussionMapOrderAlphabetical = {new DrumMapTableCell(Midi.Percussion.AcousticBassDrum         ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.LowMidTom                ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.RideCymbal2              ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.ShortWhistle             ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.BassDrum1                ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.HiMidTom                 ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HiBongo                  ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.LongWhistle              ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.SideStick                ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.CrashCymbal1             ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowBongo                 ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.ShortGuiro               ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.AcousticSnare            ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.HighTom                  ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.MuteHiConga              ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.LongGuiro                ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.HandClap                 ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.RideCymbal1              ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.OpenHiConga              ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.Claves                   ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.ElectricSnare            ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.ChineseCymbal            ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowConga                 ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.HiWoodBlock              ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.LowFloorTom              ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.RideBell                 ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HighTimbale              ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.LowWoodBlock             ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.ClosedHiHat              ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.Tambourine               ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowTimbale               ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.MuteCuica                ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.HighFloorTom             ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.SplashCymbal             ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HighAgogo                ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.OpenCuica                ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.PedalHiHat               ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.Cowbell                  ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowAgogo                 ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.MuteTriangle             ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.LowTom                   ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.CrashCymbal2             ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.Cabasa                   ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.OpenTriangle             ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.OpenHiHat                ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.Vibraslap                ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.Maracas                  ), // col 3
                                                                new DrumMapTableCell(null                                     )};// col 4

   private DrumMapTableCell[] percussionMapOrderMidiId       = {new DrumMapTableCell(Midi.Percussion.AcousticBassDrum         ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.LowMidTom                ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.RideCymbal2              ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.ShortWhistle             ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.BassDrum1                ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.HiMidTom                 ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HiBongo                  ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.LongWhistle              ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.SideStick                ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.CrashCymbal1             ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowBongo                 ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.ShortGuiro               ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.AcousticSnare            ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.HighTom                  ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.MuteHiConga              ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.LongGuiro                ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.HandClap                 ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.RideCymbal1              ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.OpenHiConga              ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.Claves                   ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.ElectricSnare            ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.ChineseCymbal            ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowConga                 ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.HiWoodBlock              ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.LowFloorTom              ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.RideBell                 ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HighTimbale              ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.LowWoodBlock             ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.ClosedHiHat              ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.Tambourine               ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowTimbale               ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.MuteCuica                ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.HighFloorTom             ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.SplashCymbal             ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HighAgogo                ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.OpenCuica                ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.PedalHiHat               ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.Cowbell                  ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowAgogo                 ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.MuteTriangle             ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.LowTom                   ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.CrashCymbal2             ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.Cabasa                   ), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.OpenTriangle             ), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.OpenHiHat                ), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.Vibraslap                ), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.Maracas                  ), // col 3
                                                                new DrumMapTableCell(null                                     )};// col 4

   private DrumMapTableCell[] percussionMapOrderSound        = {new DrumMapTableCell(Midi.Percussion.AcousticBassDrum , "1.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.OpenHiHat        , "13. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowConga         , "25. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.Claves           , "37. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.BassDrum1        , "2.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.PedalHiHat       , "14. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.MuteHiConga      , "26. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.Vibraslap        , "38. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.AcousticSnare    , "3.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.CrashCymbal1     , "15. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.OpenHiConga      , "27. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.Cabasa           , "39. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.ElectricSnare    , "4.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.CrashCymbal2     , "16. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowTimbale       , "28. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.Maracas          , "40. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.SideStick        , "5.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.RideCymbal1      , "17. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HighTimbale      , "29. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.ShortWhistle     , "41. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.LowFloorTom      , "6.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.RideCymbal2      , "18. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowWoodBlock     , "30. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.LongWhistle      , "42. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.HighFloorTom     , "7.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.RideBell         , "19. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HiWoodBlock      , "31. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.ShortGuiro       , "43. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.LowMidTom        , "8.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.SplashCymbal     , "20. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.LowAgogo         , "32. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.LongGuiro        , "44. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.HiMidTom         , "9.  "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.ChineseCymbal    , "21. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.HighAgogo        , "33. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.HandClap         , "45. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.LowTom           , "10. "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.Cowbell          , "22. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.Tambourine       , "34. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.MuteTriangle     , "46. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.HighTom          , "11. "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.LowBongo         , "23. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.MuteCuica        , "35. "), // col 3
                                                                new DrumMapTableCell(Midi.Percussion.OpenTriangle     , "47. "), // col 4
                                                                new DrumMapTableCell(Midi.Percussion.ClosedHiHat      , "12. "), // col 1
                                                                new DrumMapTableCell(Midi.Percussion.HiBongo          , "24. "), // col 2
                                                                new DrumMapTableCell(Midi.Percussion.OpenCuica        , "36. "), // col 3
                                                                new DrumMapTableCell(null                             , null  )};// col 4

   private Object[][] data;           // data in the table
   private String[]   columnNames;    // column headers




   /**
    * constructor.
    * <br/><br/>
    * @param order   whether to order the drum names by their midi id or by their midi name.
    */
   public DrumMapTableModel(Order order)
   {
      // create the columns of midi percussion sounds
      DrumMapTableCell[] percussionSounds = null;

      switch (order)
      {
         case Alphabetical:
              // make a copy of the array and sort it alphabetically
              DrumMapTableCell[] copy = Arrays.copyOf(percussionMapOrderAlphabetical, percussionMapOrderAlphabetical.length);
              Arrays.sort(copy, new DrumMapTableCellComparator());

              // now arrange the real array in columns instead of rows
              for(int col=0; col<NUM_COLUMNS; ++col)
              {
                 for(int row=0; row<NUM_ROWS; ++row)
                 {
                    int a = row * NUM_COLUMNS + col;
                    int c = col * NUM_ROWS    + row;
                    percussionMapOrderAlphabetical[a] = copy[c];
                    // add the numbering to the display
                    String number = "" + (c+1) + ". " + (c < 10 ? " " : "");
                    percussionMapOrderAlphabetical[a].setDisplayNumber(number);
                 }
              }
              percussionSounds = percussionMapOrderAlphabetical;
         break;
         case MidiId:
              // instead of numbering the percussions sounds 1, 2, 3, ..., use their midi id instead.
              for(int row=0; row<NUM_ROWS; ++row)
              {
                 for(int col=0; col<NUM_COLUMNS; ++col)
                 {
                    int    n      = row * NUM_COLUMNS + col;
                    String number = "" + (percussionMapOrderMidiId[n].getPercussion() == null ? "" : percussionMapOrderMidiId[n].getPercussion().id() + ".  ");
                    percussionMapOrderMidiId[n].setDisplayNumber(number);
                 }
              }
              percussionSounds = percussionMapOrderMidiId;
         break;
         case Sound:
              percussionSounds = percussionMapOrderSound;
         break;
      }

      // create the table data
      data        = new Object[order.numRows()][order.numCols()];
      columnNames = new String[order.numCols()];

      for(int row=0; row<order.numRows(); ++row)
         for(int col=0; col<order.numCols(); ++col)
            data[row][col] = percussionSounds[row * order.numCols() + col];

      // create the headings
      for(int col=0; col<order.numCols(); ++col)
         columnNames[col] = "drum column " + col;
   }

   /**
    * @return the number of columns in the table.
    */
   public int getColumnCount()
   {
      return data[0].length;
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
    * @param order   the integer order.
    * <br/><br/>
    * @return the order enum corresponding to the integer order.
    */
   public static Order getOrder(int order)
   {
      for (Order o : Order.values())
         if (order == o.ordinal())
            return o;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.drum_map_ordering"), order, Order.Sound.ordinal()));
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
      return DrumMapTableCell.class;
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
      return false;
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
