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
 * This class represents a beaglebuddy tab instrument and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * Any instrument that is not one of the 5 supported instrument types (bass guitar, drums, guitar, keyboards, or vocals) and uses a bass clef is declared to be of this type.
 */
public class OtherBass extends Instrument
{
   // class members
   private static ArrayList<Midi.Sound> validPresets = new ArrayList<Midi.Sound>();

   static
   {
      validPresets.add(Midi.Sound.AcousticGrand       );
      validPresets.add(Midi.Sound.BrightAcoustic      );
      validPresets.add(Midi.Sound.ElectricGrand       );
      validPresets.add(Midi.Sound.HonkyTonk           );
      validPresets.add(Midi.Sound.ElectricPiano1      );
      validPresets.add(Midi.Sound.ElectricPiano2      );
      validPresets.add(Midi.Sound.Harpsichord         );
      validPresets.add(Midi.Sound.Clavinet            );
      validPresets.add(Midi.Sound.Celesta             );
      validPresets.add(Midi.Sound.Glockenspiel        );
      validPresets.add(Midi.Sound.MusicBox            );
      validPresets.add(Midi.Sound.Vibraphone          );
      validPresets.add(Midi.Sound.Marimba             );
      validPresets.add(Midi.Sound.Xylophone           );
      validPresets.add(Midi.Sound.TubularBells        );
      validPresets.add(Midi.Sound.Dulcimer            );
      validPresets.add(Midi.Sound.DrawbarOrgan        );
      validPresets.add(Midi.Sound.PercussiveOrgan     );
      validPresets.add(Midi.Sound.RockOrgan           );
      validPresets.add(Midi.Sound.ChurchOrgan         );
      validPresets.add(Midi.Sound.ReedOrgan           );
      validPresets.add(Midi.Sound.Accordian           );
      validPresets.add(Midi.Sound.Harmonica           );
      validPresets.add(Midi.Sound.TangoAccordian      );
      validPresets.add(Midi.Sound.NylonStringGuitar   );
      validPresets.add(Midi.Sound.SteelStringGuitar   );
      validPresets.add(Midi.Sound.ElectricGuitarJazz  );
      validPresets.add(Midi.Sound.ElectricGuitarClean );
      validPresets.add(Midi.Sound.ElectricGuitarMuted );
      validPresets.add(Midi.Sound.OverdrivenGuitar    );
      validPresets.add(Midi.Sound.DistortionGuitar    );
      validPresets.add(Midi.Sound.GuitarHarmonics     );
      validPresets.add(Midi.Sound.Violin              );
      validPresets.add(Midi.Sound.Viola               );
      validPresets.add(Midi.Sound.Cello               );
      validPresets.add(Midi.Sound.Contrabass          );
      validPresets.add(Midi.Sound.TremoloStrings      );
      validPresets.add(Midi.Sound.PizzicatoStrings    );
      validPresets.add(Midi.Sound.OrchestralStrings   );
      validPresets.add(Midi.Sound.Timpani             );
      validPresets.add(Midi.Sound.StringEnsemble1     );
      validPresets.add(Midi.Sound.StringEnsemble2     );
      validPresets.add(Midi.Sound.SynthStrings1       );
      validPresets.add(Midi.Sound.SynthStrings2       );
      validPresets.add(Midi.Sound.ChoirAahs           );
      validPresets.add(Midi.Sound.VoiceOohs           );
      validPresets.add(Midi.Sound.SynthVoice          );
      validPresets.add(Midi.Sound.OrchestraHit        );
      validPresets.add(Midi.Sound.Trumpet             );
      validPresets.add(Midi.Sound.Trombone            );
      validPresets.add(Midi.Sound.Tuba                );
      validPresets.add(Midi.Sound.MutedTrumpet        );
      validPresets.add(Midi.Sound.FrenchHorn          );
      validPresets.add(Midi.Sound.BrassSection        );
      validPresets.add(Midi.Sound.SynthBrass1         );
      validPresets.add(Midi.Sound.Synthbrass2         );
      validPresets.add(Midi.Sound.SopranoSax          );
      validPresets.add(Midi.Sound.AltoSax             );
      validPresets.add(Midi.Sound.TenorSax            );
      validPresets.add(Midi.Sound.BaritoneSax         );
      validPresets.add(Midi.Sound.Oboe                );
      validPresets.add(Midi.Sound.EnglishHorn         );
      validPresets.add(Midi.Sound.Bassoon             );
      validPresets.add(Midi.Sound.Clarinet            );
      validPresets.add(Midi.Sound.Piccolo             );
      validPresets.add(Midi.Sound.Flute               );
      validPresets.add(Midi.Sound.Recorder            );
      validPresets.add(Midi.Sound.PanFlute            );
      validPresets.add(Midi.Sound.BlownBottle         );
      validPresets.add(Midi.Sound.Skakuhachi          );
      validPresets.add(Midi.Sound.Whistle             );
      validPresets.add(Midi.Sound.Ocarina             );
      validPresets.add(Midi.Sound.Lead1Square         );
      validPresets.add(Midi.Sound.Lead2Sawtooth       );
      validPresets.add(Midi.Sound.Lead3Calliope       );
      validPresets.add(Midi.Sound.Lead4Chiff          );
      validPresets.add(Midi.Sound.Lead5Charang        );
      validPresets.add(Midi.Sound.Lead6Voice          );
      validPresets.add(Midi.Sound.Lead7Fifths         );
      validPresets.add(Midi.Sound.Lead8BassLead       );
      validPresets.add(Midi.Sound.Pad1NewAge          );
      validPresets.add(Midi.Sound.Pad2Warm            );
      validPresets.add(Midi.Sound.Pad3Polysynth       );
      validPresets.add(Midi.Sound.Pad4Choir           );
      validPresets.add(Midi.Sound.Pad5Bowed           );
      validPresets.add(Midi.Sound.Pad6Metallic        );
      validPresets.add(Midi.Sound.Pad7Halo            );
      validPresets.add(Midi.Sound.Pad8Sweep           );
      validPresets.add(Midi.Sound.Fx1Rain             );
      validPresets.add(Midi.Sound.Fx2Soundtrack       );
      validPresets.add(Midi.Sound.Fx3Crystal          );
      validPresets.add(Midi.Sound.Fx4Atmosphere       );
      validPresets.add(Midi.Sound.Fx5Brightness       );
      validPresets.add(Midi.Sound.Fx6Goblins          );
      validPresets.add(Midi.Sound.Fx7Echoes           );
      validPresets.add(Midi.Sound.Fx8SciFi            );
      validPresets.add(Midi.Sound.Sitar               );
      validPresets.add(Midi.Sound.Banjo               );
      validPresets.add(Midi.Sound.Shamisen            );
      validPresets.add(Midi.Sound.Koto                );
      validPresets.add(Midi.Sound.Kalimba             );
      validPresets.add(Midi.Sound.Bagpipe             );
      validPresets.add(Midi.Sound.Fiddle              );
      validPresets.add(Midi.Sound.Shanai              );
      validPresets.add(Midi.Sound.TinkleBell          );
      validPresets.add(Midi.Sound.Agogo               );
      validPresets.add(Midi.Sound.SteelDrums          );
      validPresets.add(Midi.Sound.Woodblock           );
      validPresets.add(Midi.Sound.TaikoDrum           );
      validPresets.add(Midi.Sound.MelodicTom          );
      validPresets.add(Midi.Sound.SynthDrum           );
      validPresets.add(Midi.Sound.ReverseCymbal       );
      validPresets.add(Midi.Sound.GuitarFretNoise     );
      validPresets.add(Midi.Sound.BreathNoise         );
      validPresets.add(Midi.Sound.Seashore            );
      validPresets.add(Midi.Sound.BirdTweet           );
      validPresets.add(Midi.Sound.TelephoneRing       );
      validPresets.add(Midi.Sound.Helicopter          );
      validPresets.add(Midi.Sound.Applause            );
      validPresets.add(Midi.Sound.Gunshot             );
   }




   /**
    * default constructor.
    */
   public OtherBass()
   {
      super(Instrument.Type.Other_Bass, ResourceBundle.getString("instrument.other.bass"), Midi.Sound.Agogo, TuningDictionary.getTuning(Instrument.Type.Other_Bass, 4, "standard"));
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
   public OtherBass(byte number, java.lang.String description, Midi.Sound preset, Volume.Level initialVolume, byte pan, byte reverb, byte chorus, Instrument.Fret capo, Tuning tuning)
   {
      super(Instrument.Type.Other_Bass, number, description, preset, initialVolume, pan, reverb, chorus, capo, tuning);

      setPreset(preset);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param other  other instrument whose values will be deep copied.
    */
   public OtherBass(OtherBass other)
   {
      super(other);
   }

   /**
    * @return   a copy of the other instrument.
    */
   @Override
  public Instrument clone()
   {
      return new OtherBass(this);
   }

   /**
    * @return the type of the instrument as a string.
    */
   @Override
  public java.lang.String getTypeName()
   {
      return ResourceBundle.getString("instrument.other.bass");
   }

   /**
    * @return the minimum number of strings an other (bass clef) can have.
    */
   @Override
  public int getMinNumstrings()
   {
      return 4;
   }

   /**
    * @return the maximum number of strings an other (bass clef) can have.
    */
   @Override
  public int getMaxNumstrings()
   {
      return 5;
   }

   /**
    * @return the list of midi presets (sounds) that are valid for other bass instruments.
    */
   public static ArrayList<Midi.Sound> getValidPresets()
   {
      return validPresets;
   }

   /**
    * @return whether the midi sound is a valid preset for other bass instruments.
    * <br/><br/>
    * @param preset   the midi preset to use during playback for other bass instruments.
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
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.midi_preset", ResourceBundle.getString("instrument.preset.other"), preset));

      super.setPreset(preset);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab other bass instrument.
    */
   @Override
  public java.lang.String toString(int numSpacesToIndent)
   {
      return ResourceBundle.getString("instrument.other") + "\n" + super.toString(numSpacesToIndent);
   }
}
