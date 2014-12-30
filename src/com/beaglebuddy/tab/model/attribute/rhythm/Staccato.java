/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.rhythm;




/**
 * This class represents a beaglebuddy tab staccato rhythm slash attribute.
 */
public class Staccato extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public Staccato()
   {
      super(RhythmSlashAttribute.Type.Staccato);
   }

   /**
    * @return a new copy of the staccato rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new Staccato();
   }
}
