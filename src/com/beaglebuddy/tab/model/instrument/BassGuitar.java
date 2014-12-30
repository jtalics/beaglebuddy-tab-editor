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
 * This class represents a beaglebuddy tab bass guitar and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class BassGuitar extends Instrument
{
   // class members
   private static ArrayList<Midi.Sound> validPresets = new ArrayList<Midi.Sound>();

   static
   {
      validPresets.add(Midi.Sound.AcousticBass      );
      validPresets.add(Midi.Sound.ElectricBassFinger);
      validPresets.add(Midi.Sound.ElectricBassPick  );
      validPresets.add(Midi.Sound.FretlessBass      );
      validPresets.add(Midi.Sound.SlapBass1         );
      validPresets.add(Midi.Sound.SlapBass2         );
      validPresets.add(Midi.Sound.SynthBass1        );
      validPresets.add(Midi.Sound.SynthBass2        );
      validPresets.add(Midi.Sound.Lead8BassLead     );
   }




   /**
    * default constructor.
    */
   public BassGuitar()
   {
      super(Instrument.Type.Bass_Guitar, ResourceBundle.getString("instrument.bass_guitar"), Midi.Sound.ElectricBassFinger, TuningDictionary.getTuning(Instrument.Type.Bass_Guitar, 4, "standard"));
   }

   /**
    * constructor.
    * <br/><br/>
    * @param number          unique number used to identify the bass guitar.
    * @param description     description of the bass guitar.
    * @param preset          midi sound (preset) to use during playback.
    * @param initialVolume   initial midi channel volume level for the bass guitar.
    * @param pan             channel pan setting for the bass guitar.
    * @param reverb          amount of reverb  effect used by the bass guitar.
    * @param chorus          amount of chorus  effect used by the bass guitar.
    * @param capo            fret placement of capo.
    * @param tuning          tuning for this bass guitar.
    */
   public BassGuitar(byte number, java.lang.String description, Midi.Sound preset, Volume.Level initialVolume, byte pan, byte reverb, byte chorus, Instrument.Fret capo, Tuning tuning)
   {
      super(Instrument.Type.Bass_Guitar, number, description, preset, initialVolume, pan, reverb, chorus, capo, tuning);

      setPreset(preset);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param bassGuitar  bass guitar whose values will be deep copied.
    */
   public BassGuitar(BassGuitar bassGuitar)
   {
      super(bassGuitar);
   }

   /**
    * @return   a copy of the bass guitar.
    */
   @Override
  public Instrument clone()
   {
      return new BassGuitar(this);
   }

   /**
    * @return the type of the instrument as a string.
    */
   @Override
  public java.lang.String getTypeName()
   {
      return ResourceBundle.getString("instrument.bass_guitar");
   }

   /**
    * @return the minimum number of strings a bass guitar can have.
    */
   @Override
  public int getMinNumstrings()
   {
      return 4;
   }

   /**
    * @return the maximum number of strings a bass guitar can have.
    */
   @Override
  public int getMaxNumstrings()
   {
      return 6;
   }

   /**
    * @return the list of midi presets (sounds) that are valid for bass guitar.
    */
   public static ArrayList<Midi.Sound> getValidPresets()
   {
      return validPresets;
   }

   /**
    * @return whether the midi sound is a valid preset for bass guitar.
    * <br/><br/>
    * @param preset   the midi preset to use during playback for bass guitar.
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
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.midi_preset", ResourceBundle.getString("instrument.preset.bass_guitar"), preset));

      super.setPreset(preset);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab bass guitar.
    */
   @Override
  public java.lang.String toString(int numSpacesToIndent)
   {
      return ResourceBundle.getString("instrument.bass_guitar") + "\n" + super.toString(numSpacesToIndent);
   }
}
