/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.drum_map;

import java.util.Comparator;




/*
 * this class is used to sort DrumMapTableCell objects alphabetically in ascending order.
 */
public class DrumMapTableCellComparator implements Comparator<DrumMapTableCell>
{
   /**
    * implements the Comparator interface.
    * it compares the two drum map table cell objects for order and returns:
    * <ul>
    *    <li>a negative integer if the first argument is less than the second</li>
    *    <li>zero               if the first argument is equal to  the second</li>
    *    <li>a positive integer if the first argument is greater than the second</li>
    * </ul>
    * <br/><br/>
    * @return whether one drum map table cell is lexographically less than, equal to, or greater than another.
    * <br/><br/>
    * @param tableCell1   1st drum map table cell object.
    * @param tableCell2   2nd drum map table cell object.
    */
   public int compare(DrumMapTableCell tableCell1, DrumMapTableCell tableCell2)
   {
      int result;

      if (tableCell1 == null && tableCell2 == null)
         result =  0;
      else if (tableCell1 == null && tableCell2 != null)
         result =  1;
      else if (tableCell1 != null && tableCell2 == null)
         result = -1;
      else if (tableCell1.getPercussion() == null && tableCell2.getPercussion() == null)
         result =  0;
      else if (tableCell1.getPercussion() == null && tableCell2.getPercussion() != null)
         result =  1;
      else if (tableCell1.getPercussion() != null && tableCell2.getPercussion() == null)
         result = -1;
      else
         result = tableCell1.getPercussion().text().compareTo(tableCell2.getPercussion().text());

      return result;
   }

   /**
    * implements the Comparator interface.
    * <br/><br/>
    * @return true iff the specified object is also a comparator and it imposes the same ordering as this comparator.
    * <br/><br/>
    * @param object   a comparator to be tested for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      return object != null && object instanceof DrumMapTableCellComparator;
   }
}
