/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.drum_map;

import javax.swing.JTable;




/**
 * This class is used to select the cell specified by the row and column arguments in the midi percussion sound table.
 * Don't ask me how this works, but it does.  I found it posted on a website:
 * http://forum.java.sun.com/thread.jspa?threadID=440154&messageID=1986588
 */
class DrumMapTableCellSelector implements Runnable
{
   // data members
   private JTable table;
   private int    row;
   private int    col;


   /**
    * constructor.
    * <br/><br/>
    * @param table  the table containing the cell that will be selcted.
    * @param row    the row    index of the cell to be selected.
    * @param col    the column index of the cell to be selected.
    */
   DrumMapTableCellSelector(JTable table, int row, int col)
   {
      this.table = table;
      this.row   = row;
      this.col   = col;
   }

   /**
    * implements the <i>Runnable</i> interface.
    */
   public void run()
   {
      table.requestFocusInWindow();
      table.changeSelection(row, col, true, false);
   }
}
