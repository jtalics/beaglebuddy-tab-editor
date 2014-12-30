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
 * This class represents a beaglebuddy tab tie wrap note attribute.
 * <p>
 * A "tie wrap" note attribute is used instead of a "tie" when the last note in a staff is tied to the first note in the next section.
 * Otherwise, if the two notes that are tied are on the same staff, then a "tie" note attribute is used.
 * </p>
 */
public class TieWrap extends NoteAttribute
{
   /**
    * default constructor.
    */
   public TieWrap()
   {
      super(NoteAttribute.Type.TieWrap);
   }

   /**
    * @return a new copy of the tie wrap attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new TieWrap();
   }
}
