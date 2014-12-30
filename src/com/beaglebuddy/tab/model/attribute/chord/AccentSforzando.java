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
 * This class represents a sforzando accent chord attribute.
 */
public class AccentSforzando extends ChordAttribute
{
   /**
    * default constructor.
    */
   public AccentSforzando()
   {
      super(ChordAttribute.Type.AccentSforzando);
   }

   /**
    * @return a new copy of the sforzando accent chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new AccentSforzando();
   }
}
