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
 * This class represents a fermata chord attribute.
 */
public class Fermata extends ChordAttribute
{
   /**
    * default constructor.
    */
   public Fermata()
   {
      super(ChordAttribute.Type.Fermata);
   }

   /**
    * @return a new copy of the fermata chord attribute.
    */
   @Override
    public ChordAttribute clone()
   {
      return new Fermata();
   }
}
