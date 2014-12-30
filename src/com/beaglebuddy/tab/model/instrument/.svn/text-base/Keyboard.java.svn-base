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
 * This class represents a beaglebuddy tab keyboard and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class Keyboard extends Instrument
{
   // class members
   private static ArrayList<Midi.Sound> validPresets = new ArrayList<Midi.Sound>();

   static
   {
      validPresets.add(Midi.Sound.AcousticGrand  );
      validPresets.add(Midi.Sound.BrightAcoustic );
      validPresets.add(Midi.Sound.ElectricGrand  );
      validPresets.add(Midi.Sound.HonkyTonk      );
      validPresets.add(Midi.Sound.ElectricPiano1 );
      validPresets.add(Midi.Sound.ElectricPiano2 );
      validPresets.add(Midi.Sound.Harpsichord    );
      validPresets.add(Midi.Sound.Clavinet       );
      validPresets.add(Midi.Sound.DrawbarOrgan   );
      validPresets.add(Midi.Sound.PercussiveOrgan);
      validPresets.add(Midi.Sound.RockOrgan      );
      validPresets.add(Midi.Sound.ChurchOrgan    );
      validPresets.add(Midi.Sound.ReedOrgan      );
      validPresets.add(Midi.Sound.Accordian      );
      validPresets.add(Midi.Sound.Harmonica      );
      validPresets.add(Midi.Sound.TangoAccordian );
   }




   /**
    * default constructor.
    */
   public Keyboard()
   {
      super(Instrument.Type.Keyboards, ResourceBundle.getString("instrument.keyboard"), Midi.Sound.AcousticGrand, TuningDictionary.getTuning(Instrument.Type.Keyboards, 6, "standard"));
   }

   /**
    * constructor
    * <br/><br/>
    * @param number          unique number used to identify the keyboard.
    * @param description     description of the keyboard.
    * @param preset          midi sound (preset) to use during playback.
    * @param initialVolume   initial midi channel volume level for the keyboard.
    * @param pan             channel pan setting for the keyboard.
    * @param reverb          amount of reverb  effect used by the keyboard.
    * @param chorus          amount of chorus  effect used by the keyboard.
    * @param capo            fret placement of capo.
    * @param tuning          tuning for this keyboard.
    */
   public Keyboard(byte number, java.lang.String description, Midi.Sound preset, Volume.Level initialVolume, byte pan, byte reverb, byte chorus, Instrument.Fret capo, Tuning tuning)
   {
      super(Instrument.Type.Keyboards, number, description, preset, initialVolume, pan, reverb, chorus, capo, tuning);

      setPreset(preset);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param keyboard  keyboard whose values will be deep copied.
    */
   public Keyboard(Keyboard keyboard)
   {
      super(keyboard);
   }

   /**
    * @return   a copy of the keyboard.
    */
   @Override
  public Instrument clone()
   {
      return new Keyboard(this);
   }

   /**
    * @return the type of the instrument as a string.
    */
   @Override
  public java.lang.String getTypeName()
   {
      return ResourceBundle.getString("instrument.keyboard");
   }

   /**
    * @return the minimum number of strings a keyboard can have (same as a guitar).
    */
   @Override
  public int getMinNumstrings()
   {
      return 6;
   }

   /**
    * @return the maximum number of strings a keyboard can have (same as a guitar).
    */
   @Override
  public int getMaxNumstrings()
   {
      return 7;
   }

   /**
    * @return the list of midi presets (sounds) that are valid for keyboards.
    */
   public static ArrayList<Midi.Sound> getValidPresets()
   {
      return validPresets;
   }

   /**
    * @return whether the midi sound is a valid preset for keyboards.
    * <br/><br/>
    * @param preset   the midi preset to use during playback for keyboards
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
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.midi_preset", ResourceBundle.getString("instrument.preset.keyboard"), preset));

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
    * @return a string representation of the beaglebuddy tab keyboard.
    */
   @Override
  public java.lang.String toString(int numSpacesToIndent)
   {
      return ResourceBundle.getString("instrument.keyboard") + "\n" + super.toString(numSpacesToIndent);
   }
}
