/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.instrument;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Volume;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;




/**
 * This class represents a beaglebuddy tab drums and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * In the Beaglebuddy Tab Editor, drums have 3 tab lines:
 * <ol>
 *    <li>for percussion controlled by the feet - kick drum, pedal high hat, etc</li>
 *    <li>for percussion hit by the hands       - snare, toms, etc</li>
 *    <li>for cymbals                           - cymbals, cow bell, wood block, etc</li>
 * </ol>
 * While drums use a drum clef for their staffs, the tuning uses the same notes as the bottom 3 strings of a stndard bass guitar tuning:
 * E1, A1, D2
 */
public class Drums extends Instrument
{
   // class members
   private static ArrayList<Midi.Sound> validPresets = new ArrayList<Midi.Sound>();

   // data members
   private DrumMap drumMap;

   static
   {
      validPresets.add(Midi.Sound.SynthDrum);
   }




   /**
    * default constructor.
    */
   public Drums()
   {
      super(Instrument.Type.Drums, ResourceBundle.getString("instrument.drums"), Midi.Sound.SynthDrum, new DrumTuning());

      drumMap = new DrumMap();
      setMidiChannel(Midi.Drum_Channel);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param number          unique number used to identify the instrument.
    * @param description     description of the instrument.
    * @param initialVolume   initial midi channel volume level for the instrument.
    * @param pan             channel pan setting for the instrument.
    * @param reverb          amount of reverb  effect used by the instrument.
    * @param chorus          amount of chorus  effect used by the instrument.
    * @param drumMap         drum map between drum notes and midi percussion sounds.
    */
   public Drums(byte number, java.lang.String description, Volume.Level initialVolume, byte pan, byte reverb, byte chorus, DrumMap drumMap)
   {
      super(Instrument.Type.Drums, number, description, Midi.Sound.SynthDrum, initialVolume, pan, reverb, chorus, Fret.Not_Used, new DrumTuning());

      this.drumMap = drumMap;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param drums  drums whose values will be deep copied.
    */
   public Drums(Drums drums)
   {
      super(drums);

      this.drumMap = new DrumMap(drumMap);
   }

   /**
    * @return   a copy of the drums.
    */
   @Override
  public Instrument clone()
   {
      return new Drums(this);
   }

   /**
    * @return the type of the instrument as a string.
    */
   @Override
  public java.lang.String getTypeName()
   {
      return ResourceBundle.getString("instrument.drums");
   }

   /**
    * @return the minimum number of strings a drum set can have.
    */
   @Override
  public int getMinNumstrings()
   {
      return 3;
   }

   /**
    * @return the maximum number of strings a drum set can have.
    */
   @Override
  public int getMaxNumstrings()
   {
      return 3;
   }

   /**
    * @return the list of midi presets (sounds) that are valid for drums.
    */
   public static ArrayList<Midi.Sound> getValidPresets()
   {
      return validPresets;
   }

   /**
    * @return whether the midi sound is a valid preset for drums.
    * <br/><br/>
    * @param preset   the midi preset to use during playback for drums.
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
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.midi_preset", ResourceBundle.getString("instrument.preset.drums"), preset));

      super.setPreset(preset);
   }

   /**
    * sets the midi channel on which this instrument will be played.
    * <br/><br/>
    * @param channel  the midi channel to use when playing back this instrument in the song.
    */
   @Override
  public void setMidiChannel(Midi.Channel channel)
   {
      if (channel != Midi.Drum_Channel)
         throw new IllegalArgumentException(ResourceBundle.format("error.midi.invalid_drum_channel", channel, Midi.Drum_Channel));

      super.setMidiChannel(channel);
   }

   /**
    * @return the midi drum mapping to use during playback.
    */
   public DrumMap getDrumMap()
   {
      return drumMap;
   }

   /**
    * sets the midi drum mapping.
    * <br/><br/>
    * @param drumMap   the midi drum mapping to use during playback.
    */
   public void setDrumMap(DrumMap drumMap)
   {
      this.drumMap = drumMap;
   }

   /**
    * read in an instrument from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the instrument from the beaglebuddy tab file.
    */
   @Override
  public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         drumMap.load(file);
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.instrument"));
      }
   }

   /**
    * save an instrument to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the instrument to the beaglebuddy tab file.
    */
   @Override
  public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         drumMap.save(file);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.instrument"));
      }
   }


   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab drum.
    */
   @Override
  public java.lang.String toString(int numSpacesToIndent)
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(ResourceBundle.getString("instrument.drums") + "\n");
      buffer.append(super.toString(numSpacesToIndent) + "\n");
      buffer.append(drumMap.toString(numSpacesToIndent));

      return buffer.toString();
   }
}
