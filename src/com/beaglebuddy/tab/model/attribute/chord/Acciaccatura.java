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
 * This class represents a acciaccatura chord attribute (grace note).
 */
public class Acciaccatura extends ChordAttribute
{
   /**
    * default constructor.
    */
   public Acciaccatura()
   {
      super(ChordAttribute.Type.Acciaccatura);
   }

   /**
    * @return a new copy of the acciaccatura chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new Acciaccatura();
   }
}
