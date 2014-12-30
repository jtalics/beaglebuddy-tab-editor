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
 * This class represents a beaglebuddy tab slide out of upwards rhythm slash attribute.
 */
public class SlideOutOfUpwards extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public SlideOutOfUpwards()
   {
      super(RhythmSlashAttribute.Type.SlideOutOfUpwards);
   }

   /**
    * @return a new copy of the slide out of upwards rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new SlideOutOfUpwards();
   }
}
