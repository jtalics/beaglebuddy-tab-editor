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
 * This class represents a beaglebuddy tab tied rhythm slash attribute.
 */
public class Tied extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public Tied()
   {
      super(RhythmSlashAttribute.Type.Tied);
   }

   /**
    * @return a new copy of the tied rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new Tied();
   }
}
