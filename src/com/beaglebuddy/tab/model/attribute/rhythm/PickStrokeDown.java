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
 * This class represents a beaglebuddy tab down pick stroke rhythm slash attribute.
 */
public class PickStrokeDown extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public PickStrokeDown()
   {
      super(RhythmSlashAttribute.Type.PickStrokeDown);
   }

   /**
    * @return a new copy of the down pick stroke rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new PickStrokeDown();
   }
}
