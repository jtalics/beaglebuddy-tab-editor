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
 * This class represents a beaglebuddy tab sforzando accent rhythm slash attribute.
 */
public class AccentSforzando extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public AccentSforzando()
   {
      super(RhythmSlashAttribute.Type.AccentSforzando);
   }

   /**
    * @return a new copy of the sforzando accent rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new AccentSforzando();
   }
}
