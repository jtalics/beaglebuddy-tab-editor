/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.instrument;

import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a beaglebuddy tab drum tuning and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class DrumTuning extends Tuning
{
   /**
    * default constructor.
    */
   public DrumTuning()
   {
      name                = ResourceBundle.getString("text.drum_tuning");
      musicNotationOffset = (byte)0;
      notes               = new Midi.Note[3];
      notes[0]            = Midi.Note.D2;
      notes[1]            = Midi.Note.A1;
      notes[2]            = Midi.Note.E1;
   }
}
