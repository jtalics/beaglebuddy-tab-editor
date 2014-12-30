
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
 * This class represents a appoggiatura chord attribute (also known as grace note).
 */
public class Appoggiatura extends ChordAttribute
{
   /**
    * default constructor.
    */
   public Appoggiatura()
   {
      super(ChordAttribute.Type.Appoggiatura);
   }

   /**
    * @return a new copy of the appoggiatura chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new Appoggiatura();
   }
}
