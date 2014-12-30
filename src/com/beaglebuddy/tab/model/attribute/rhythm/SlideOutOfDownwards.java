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
 * This class represents a beaglebuddy tab slide out of downwards rhythm slash attribute.
 */
public class SlideOutOfDownwards extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public SlideOutOfDownwards()
   {
      super(RhythmSlashAttribute.Type.SlideOutOfDownwards);
   }

   /**
    * @return a new copy of the slide out of downwards rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new SlideOutOfDownwards();
   }
}
