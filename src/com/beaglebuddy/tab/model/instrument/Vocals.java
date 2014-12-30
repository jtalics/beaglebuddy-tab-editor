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
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.Volume;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;




/**
 * This class represents a beaglebuddy tab vocals and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class Vocals extends Instrument
{
   // class members
   private static ArrayList<Midi.Sound> validPresets = new ArrayList<Midi.Sound>();

   static
   {
      validPresets.add(Midi.Sound.ChoirAahs );
      validPresets.add(Midi.Sound.Lead6Voice);
      validPresets.add(Midi.Sound.Pad4Choir );
      validPresets.add(Midi.Sound.SynthVoice);
      validPresets.add(Midi.Sound.VoiceOohs );
   }




   /**
    * default constructor.
    */
   public Vocals()
   {
      super(Instrument.Type.Vocals, ResourceBundle.getString("instrument.vocals"), Midi.Sound.SynthVoice, TuningDictionary.getTuning(Instrument.Type.Vocals, 6, "standard"));
   }

   /**
    * constructor
    * <br/><br/>
    * @param number          unique number used to identify the instrument.
    * @param description     description of the instrument.
    * @param preset          midi sound (preset) to use during playback.
    * @param initialVolume   initial midi channel volume level for the instrument.
    * @param pan             channel pan setting for the instrument.
    * @param reverb          amount of reverb  effect used by the instrument.
    * @param chorus          amount of chorus  effect used by the instrument.
    * @param capo            fret placement of capo.
    * @param tuning          tuning for this instrument.
    */
   public Vocals(byte number, java.lang.String description, Midi.Sound preset, Volume.Level initialVolume, byte pan, byte reverb, byte chorus, Instrument.Fret capo, Tuning tuning)
   {
      super(Instrument.Type.Vocals, number, description, preset, initialVolume, pan, reverb, chorus, capo, tuning);

      setPreset(preset);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param vocals  vocals whose values will be deep copied.
    */
   public Vocals(Vocals vocals)
   {
      super(vocals);
   }

   /**
    * @return   a copy of the vocals.
    */
   @Override
  public Instrument clone()
   {
      return new Vocals(this);
   }

   /**
    * @return the type of the instrument as a string.
    */
   @Override
  public java.lang.String getTypeName()
   {
      return ResourceBundle.getString("instrument.vocals");
   }

   /**
    * @return the minimum number of strings a vocal can have (same as a guitar).
    */
   @Override
  public int getMinNumstrings()
   {
      return 6;
   }

   /**
    * @return the maximum number of strings a vocal can have (same as a guitar).
    */
   @Override
  public int getMaxNumstrings()
   {
      return 7;
   }

   /**
    * @return the list of midi presets (sounds) that are valid for vocals.
    */
   public static ArrayList<Midi.Sound> getValidPresets()
   {
      return validPresets;
   }

   /**
    * @return whether the midi sound is a valid preset for vocals.
    * <br/><br/>
    * @param preset   the midi preset to use during playback for vocals.
    */
   public static boolean isValidPreset(Midi.Sound preset)
   {
      return validPresets.contains(preset);
   }

   /**
    * sets the midi sound to use during playback.
    * <br/><br/>
    * @param preset   the midi preset to use during playback.
    */
   @Override
  public void setPreset(Midi.Sound preset)
   {
      if (!isValidPreset(preset))
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.midi_preset", ResourceBundle.getString("instrument.preset.vocals"), preset));

      super.setPreset(preset);
   }

   /**
    * @return whether an instrument can have chord diagrams associated with it.
    */
   @Override
  public boolean canHaveAssociatedChordDiagrams()
   {
      return true;
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab vocals.
    */
   @Override
  public java.lang.String toString(int numSpacesToIndent)
   {
      return ResourceBundle.getString("instrument.vocals") + "\n" + super.toString(numSpacesToIndent);
   }
}
