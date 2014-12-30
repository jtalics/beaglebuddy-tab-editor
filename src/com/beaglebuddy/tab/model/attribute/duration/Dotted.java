/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.duration;




/**
 * This class represents a dotted duration attribute.
 */
public class Dotted extends DurationAttribute
{
   /**
    * default constructor.
    */
   public Dotted()
   {
      super(DurationAttribute.Type.Dotted);
   }

   /**
    * @return a new copy of the dotted duration attribute.
    */
   @Override
   public DurationAttribute clone()
   {
      return new Dotted();
   }
}
