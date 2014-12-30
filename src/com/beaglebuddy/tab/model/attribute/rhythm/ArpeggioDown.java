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
 * This class represents a beaglebuddy tab arpeggio down rhythm slash attribute.
 */
public class ArpeggioDown extends RhythmSlashAttribute
{
   /**
    * default constructor.
    */
   public ArpeggioDown()
   {
      super(RhythmSlashAttribute.Type.ArpeggioDown);
   }

   /**
    * @return a new copy of the arpeggio down rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new ArpeggioDown();
   }
}
