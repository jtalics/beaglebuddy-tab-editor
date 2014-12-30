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
 * This class represents a beaglebuddy tab marcato accent rhythm slash attribute.
 */
public class AccentMarcato extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public AccentMarcato()
   {
      super(RhythmSlashAttribute.Type.AccentMarcato);
   }

   /**
    * @return a new copy of the marcato accent rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new AccentMarcato();
   }
}
