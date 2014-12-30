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
 * This class represents the end of a multibar rest chord attribute.
 */
public class MultibarRestEnd extends ChordAttribute
{
   /**
    * default constructor.
    */
   public MultibarRestEnd()
   {
      super(ChordAttribute.Type.MultibarRestEnd);
   }

   /**
    * @return a new copy of the multibar end rest chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new MultibarRestEnd();
   }
}
