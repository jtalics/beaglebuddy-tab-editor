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
 * This class represents a beaglebuddy tab slide into from below rhythm slash attribute.
 */
public class SlideIntoFromBelow extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public SlideIntoFromBelow()
   {
      super(RhythmSlashAttribute.Type.SlideIntoFromBelow);
   }

   /**
    * @return a new copy of the slide into from below rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new SlideIntoFromBelow();
   }
}
