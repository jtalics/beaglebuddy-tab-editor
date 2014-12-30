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
 * This class represents a beaglebuddy tab muted note attribute.
 */
public class Muted extends NoteAttribute
{
   /**
    * default constructor.
    */
   public Muted()
   {
      super(NoteAttribute.Type.Muted);
   }

   /**
    * @return a new copy of the muted note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new Muted();
   }
}
