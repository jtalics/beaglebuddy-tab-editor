/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.model.attribute.chord.MultibarRestEnd;



/**
 * This class represents the end of a multi bar rest.
 */
public class RestMultibarEnd extends Chord
{
   /**
    * default constructor.
    */
   public RestMultibarEnd()
   {
      setDuration(Midi.Duration.NOTE_WHOLE);
      addAttribute(new MultibarRestEnd());
   }
}
