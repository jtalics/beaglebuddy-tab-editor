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
 * This class represents a tremolo picking chord attribute.
 */
public class TremoloPicking extends ChordAttribute
{
   /**
    * default constructor.
    */
   public TremoloPicking()
   {
      super(ChordAttribute.Type.TremoloPicking);
   }

   /**
    * @return a new copy of the tremolo picking chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new TremoloPicking();
   }
}
