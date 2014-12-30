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
 * This class represents a palm muting chord attribute.
 */
public class PalmMuting extends ChordAttribute
{
   /**
    * default constructor.
    */
   public PalmMuting()
   {
      super(ChordAttribute.Type.PalmMuting);
   }

   /**
    * @return a new copy of the palm muting chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new PalmMuting();
   }
}
