/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.note;




/**
 * This class represents a beaglebuddy tab pull off note attribute.
 */
public class PullOff extends NoteAttribute
{
   /**
    * default constructor.
    */
   public PullOff()
   {
      super(NoteAttribute.Type.PullOff);
   }

   /**
    * @return a new copy of the pull off note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new PullOff();
   }
}
