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
 * This class represents a beaglebuddy tab guitar and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class Guitar extends Instrument
{
   // class members
   private static ArrayList<Midi.Sound> validPresets = new ArrayList<Midi.Sound>();

   static
   {
      validPresets.add(Midi.Sound.NylonStringGuitar  );
      validPresets.add(Midi.Sound.SteelStringGuitar  );
      validPresets.add(Midi.Sound.ElectricGuitarJazz );
      validPresets.add(Midi.Sound.ElectricGuitarClean);
      validPresets.add(Midi.Sound.ElectricGuitarMuted);
      validPresets.add(Midi.Sound.OverdrivenGuitar   );
      validPresets.add(Midi.Sound.DistortionGuitar   );
      validPresets.add(Midi.Sound.GuitarHarmonics    );
   }



   /**
    * default constructor.
    */
   public Guitar()
   {
      super(Instrument.Type.Guitar, ResourceBundle.getString("instrument.guitar"), Midi.Sound.ElectricGuitarClean, TuningDictionary.getTuning(Instrument.Type.Guitar, 6, "standard"));
   }

   /**
    * constructor.
    * <br/><br/>
    * @param number          unique number used to identify the guitar.
    * @param description     description of the guitar.
    * @param preset          midi sound (preset) to use during playback.
    * @param initialVolume   initial midi channel volume level for the guitar.
    * @param pan             channel pan setting for the guitar.
    * @param reverb          amount of reverb  effect used by the guitar.
    * @param chorus          amount of chorus  effect used by the guitar.
    * @param capo            fret placement of capo.
    * @param tuning          tuning for this guitar.
    */
   public Guitar(byte number, java.lang.String description, Midi.Sound preset, Volume.Level initialVolume, byte pan, byte reverb, byte chorus, Instrument.Fret capo, Tuning tuning)
   {
      super(Instrument.Type.Guitar, number, description, preset, initialVolume, pan, reverb, chorus, capo, tuning);

      setPreset(preset);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param guitar  guitar whose values will be deep copied.
    */
   public Guitar(Guitar guitar)
   {
      super(guitar);
   }

   /**
    * @return   a copy of the guitar.
    */
   @Override
  public Instrument clone()
   {
      return new Guitar(this);
   }

   /**
    * @return the type of the instrument as a string.
    */
   @Override
  public java.lang.String getTypeName()
   {
      return ResourceBundle.getString("instrument.guitar");
   }

   /**
    * @return the minimum number of strings a guitar can have.
    */
   @Override
  public int getMinNumstrings()
   {
      return 6;
   }

   /**
    * @return the maximum number of strings a guitar can have.
    */
   @Override
  public int getMaxNumstrings()
   {
      return 7;
   }

   /**
    * @return the list of midi presets (sounds) that are valid for guitar.
    */
   public static ArrayList<Midi.Sound> getValidPresets()
   {
      return validPresets;
   }

   /**
    * @return whether the midi sound is a valid preset for guitar.
    * <br/><br/>
    * @param preset   the midi preset to use during playback for guitar.
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
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.midi_preset", ResourceBundle.getString("instrument.preset.guitar"), preset));

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
    * @return a string representation of the beaglebuddy tab guitar.
    */
   @Override
  public java.lang.String toString(int numSpacesToIndent)
   {
      return ResourceBundle.getString("instrument.guitar") + "\n" + super.toString(numSpacesToIndent);
   }
}
