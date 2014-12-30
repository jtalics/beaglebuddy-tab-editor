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
 * This class represents a beaglebuddy tab triplet feel 2nd rhythm slash attribute.
 */
public class TripletFeel2nd extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public TripletFeel2nd()
   {
      super(RhythmSlashAttribute.Type.TripletFeel2nd);
   }

   /**
    * @return a new copy of the 2nd triplet feel rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new TripletFeel2nd();
   }
}
