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
 * This class represents an empty chord attribute.
 * It is not an actual chord attribute, but rather is a dummy attribute used by the conversion routines to convert chord attributes in other tab editors
 * that don't have a corresponding attribute in the beaglebuddy tab editor.
 */
public class None extends ChordAttribute
{
   /**
    * default constructor.
    */
   public None()
   {
      super(ChordAttribute.Type.None);
   }

   /**
    * @return a new copy of the none chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new None();
   }
}
