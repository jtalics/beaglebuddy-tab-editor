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
 * This class represents a marcato accent chord attribute.
 */
public class AccentMarcato extends ChordAttribute
{
   /**
    * default constructor.
    */
   public AccentMarcato()
   {
      super(ChordAttribute.Type.AccentMarcato);
   }

   /**
    * @return a new copy of the marcato accent chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new AccentMarcato();
   }
}
