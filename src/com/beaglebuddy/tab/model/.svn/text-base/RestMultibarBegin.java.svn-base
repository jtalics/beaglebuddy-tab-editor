/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.model.attribute.chord.MultibarRestBegin;


/**
 * This class represents a multi bar rest.
 */
public class RestMultibarBegin extends Chord
{
   /**
    * constructor.
    * <br/><br/>
    * @param numMeasures   the number of measures to rest.
    */
   public RestMultibarBegin(byte numMeasures)
   {
      setDuration(Midi.Duration.NOTE_WHOLE);
      addAttribute(new MultibarRestBegin(numMeasures));
   }
}
