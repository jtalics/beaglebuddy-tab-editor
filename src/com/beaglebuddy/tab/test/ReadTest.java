/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.io.FileReadException;




/**
 * test harness for reading in a beaglebuddy tab file or a power tab 1.70 file (and converting it to the beaglebuddy format) and printing it out.
 */
public class ReadTest
{
   /**
    * this really should be in a junit test case.
    */
   public static void main(String[] args)
   {
      if (args == null || args.length != 1)
         throw new IllegalArgumentException("You must specify the name of the tab file (.bbt or .ptb) file to read.");

      String filename = args[0];

      try
      {
         Song song = new Song(filename);
         song.buildMidiSequence();
         System.out.println(song.toString());
      }
      catch (FileReadException ex)
      {
         ex.printStackTrace();
      }
      catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
      catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
   }
}
