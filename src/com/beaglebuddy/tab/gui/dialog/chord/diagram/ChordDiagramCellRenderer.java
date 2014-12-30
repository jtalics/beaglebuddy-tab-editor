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
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;




/**
 * renders a chord diagram in a table cell, and sets the foreground and background colors of the cell depending on whether the cell is selected or not.
 */
public class ChordDiagramCellRenderer extends ChordDiagramRenderer implements TableCellRenderer
{
   /**
    * default constructor.
    */
   public ChordDiagramCellRenderer()
   {
      super(Size.Small);

      // setting opaque to true allows calls to setBackground() to work.
      setOpaque(true);
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
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int column)
   {
      if (value == null)
      {
         chordDiagram = null;
         setForeground(Color.BLACK);
         setBackground(Color.WHITE);
      }
      else
      {
         chordDiagram = (ChordDiagram)value;

         // set the foreground and background color, depending on whether the cell is selected or not.
         if (isSelected || isFocus)
         {
            setForeground(Color.WHITE);
            setBackground(Color.BLUE );
         }
         else
         {
            setForeground(Color.BLACK);
            setBackground(Color.WHITE);
         }
      }
      return this;
   }
}
