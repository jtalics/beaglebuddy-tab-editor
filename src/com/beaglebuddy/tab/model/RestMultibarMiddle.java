/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.model.attribute.chord.MultibarRestMiddle;



/**
 * This class represents a middle part of a multi bar rest.
 */
public class RestMultibarMiddle extends Chord
{
   /**
    * default constructor.
    */
   public RestMultibarMiddle()
   {
      setDuration(Midi.Duration.NOTE_WHOLE);
      addAttribute(new MultibarRestMiddle());
   }
}
