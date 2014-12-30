/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.chord;




/**
 * This class represents the middle of a multibar rest chord attribute.
 */
public class MultibarRestMiddle extends ChordAttribute
{
   /**
    * default constructor.
    */
   public MultibarRestMiddle()
   {
      super(ChordAttribute.Type.MultibarRestMiddle);
   }

   /**
    * @return a new copy of the multibar middle rest chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new MultibarRestMiddle();
   }
}
