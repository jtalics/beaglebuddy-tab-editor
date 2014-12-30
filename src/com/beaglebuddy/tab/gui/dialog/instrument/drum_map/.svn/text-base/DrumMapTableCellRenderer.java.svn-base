/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.drum_map;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;




/**
 * renders the percussion sound names in the table by setting the foreground and background colors of each cell in the table,
 * depending on whether the cell is selected or not.
 */
class DrumMapTableCellRenderer extends DefaultTableCellRenderer
{
   /**
    * default constructor.
    */
   DrumMapTableCellRenderer()
   {
      // no code necessary
   }

   /**
    * implements the <i>TableCellRenderer</i> interface.
    * <br/><br/>
    * @param table         the table whose cells are being rendered.
    * @param value         the contents of the table cell.
    * @param isSelected    whether the table cell is selected.
    * @param isFocus       whether the table cell has the focus.
    * @param row           the row    index of the table cell.
    * @param column        the column index of the table cell.
    * <br/><br/>
    * @return the component used to render the contents of the table cell.
    */
   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int column)
   {
      if (value instanceof DrumMapTableCell)
      {
         super.getTableCellRendererComponent(table, value, isSelected, isFocus, row, column);

         // set the foreground and background color, depending on whether the cell is selected or not.
         if (isSelected || isFocus)
         {
            setBackground(Color.blue );
            setForeground(Color.white);
         }
         else
         {
            setBackground(null);
            setForeground(null);
         }
      }
      return this;
   }
}
