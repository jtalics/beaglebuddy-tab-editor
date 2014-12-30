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
 * This class represents a beaglebuddy tab ghost note note attribute.
 */
public class GhostNote extends NoteAttribute
{
   /**
    * default constructor.
    */
   public GhostNote()
   {
      super(NoteAttribute.Type.GhostNote);
   }

   /**
    * @return a new copy of the ghost note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new GhostNote();
   }
}
