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
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.model.Volume;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;




/**
 * This class represents a beaglebuddy tab instrument and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * note: the initial volume data member is very clever.
 *       one might think that the <i>initial volume</i> data member does not belong as a property of an instrument, but rather that it should instead simply be a
 *       dynamic volume change on the staff to which the instrument is assigned.  This is a reasonable position to take.  However, making it a data member of the
 *       instrument class has the following benefits:
 * <ol>
 *    <li>a dynamic will be graphically displayed above the barline it is attached to, whereas as an initial volume setting of an instrument is not displayed at all.</li>
 *    <li>as a data member of instrument, even if two instruments (ex: rh. gtr 1 and rh. gtr 2) are assigned to the same staff, they can play the music back at different volumes.</li>
 * </ol>
 * </p>
 */
public class Instrument
{
   // enums
   public enum Type {Bass_Guitar, Drums, Guitar, Keyboards, Other_Bass, Other_Treble, Vocals}

   public enum String {String_1, String_2, String_3, String_4, String_5, String_6, String_7}

   public enum Fret
   {
      Open    (new java.lang.String("o")), Fret_1 (new java.lang.String( "1")), Fret_2 (new java.lang.String( "2")), Fret_3 (new java.lang.String( "3")), Fret_4 (new java.lang.String( "4")), Fret_5 (new java.lang.String( "5")), Fret_6 (new java.lang.String( "6")), Fret_7 (new java.lang.String( "7")), Fret_8 (new java.lang.String( "8")), Fret_9 (new java.lang.String( "9")), Fret_10(new java.lang.String("10")), Fret_11(new java.lang.String("11")), Fret_12(new java.lang.String("12")),
                                           Fret_13(new java.lang.String("13")), Fret_14(new java.lang.String("14")), Fret_15(new java.lang.String("15")), Fret_16(new java.lang.String("16")), Fret_17(new java.lang.String("17")), Fret_18(new java.lang.String("18")), Fret_19(new java.lang.String("19")), Fret_20(new java.lang.String("20")), Fret_21(new java.lang.String("21")), Fret_22(new java.lang.String("22")), Fret_23(new java.lang.String("23")), Fret_24(new java.lang.String("24")),
      Not_Used(new java.lang.String("x"));

      // data members
      private java.lang.String text;
      Fret  (java.lang.String text)      {this.text = text;}
      public java.lang.String text()     {return text;}
      @Override
      public java.lang.String toString() {return text;}
   }

   // class members
   public static final int MIN_NUM_STRINGS = 3;
   public static final int MAX_NUM_STRINGS = 7;
   public static final int MAX_NUM_FRETS   = 24;

   // data members
   private Type             type;               // type of instrument
   private byte             id;                 // unique number used to identify the instrument (zero-based)
   private java.lang.String name;               // name or description of the instrument (ex: rhythm guitar 1)
   private Midi.Sound       preset;             // midi sound (preset) to use during playback
   private Volume.Level     initialVolume;      // initial midi channel volume level for the instrument
   private byte             pan;                // channel pan setting for the instrument
   private byte             reverb;             // amount of reverb  effect used by the instrument
   private byte             chorus;             // amount of chorus  effect used by the instrument
   private Fret             capo;               // fret placement of capo
   private Tuning           tuning;             // tuning for this instrument
   private Midi.Channel     midiChannel;        // the midi channel this instrument is assigned to play back on




   /**
    * default constructor.
    */
   public Instrument()
   {
      this(Type.Other_Treble, null, Midi.Sound.Applause, TuningDictionary.getTuning(Instrument.Type.Other_Treble, 6, "standard"));
   }

   /**
    * constructor.
    * <br/><br/>
    * @param type     type of instrument
    * @param name     name or description of the instrument.
    * @param preset   midi sound (preset) to use during playback.
    * @param tuning   tuning for this instrument.
    */
   public Instrument(Type type, java.lang.String name, Midi.Sound preset, Tuning tuning)
   {
      this(type, (byte)-1, name, preset, Volume.Level.F, (byte)64, (byte)0, (byte)0, Fret.Not_Used, tuning);
   }

   /**
    * constructor
    * <br/><br/>
    * @param type            type of instrument
    * @param id              unique number used to identify the instrument.
    * @param name            name or description of the instrument.
    * @param preset          midi sound (preset) to use during playback.
    * @param initialVolume   initial midi channel volume level for the instrument.
    * @param pan             channel pan setting for the instrument.
    * @param reverb          amount of reverb  effect used by the instrument.
    * @param chorus          amount of chorus  effect used by the instrument.
    * @param capo            fret placement of capo.
    * @param tuning          tuning for this instrument.
    */
   public Instrument(Type type, byte id, java.lang.String name, Midi.Sound preset, Volume.Level initialVolume, byte pan, byte reverb, byte chorus, Fret capo, Tuning tuning)
   {
      this.type          = type;
      this.id            = id;
      this.name          = name;
      this.preset        = preset;
      this.initialVolume = initialVolume;
      this.pan           = pan;
      this.reverb        = reverb;
      this.chorus        = chorus;
      this.capo          = capo;
      this.tuning        = tuning;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param instrument  instrument whose values will be deep copied.
    */
   public Instrument(Instrument instrument)
   {
      this.type          = instrument.type;
      this.id            = instrument.id;
      this.name          = new java.lang.String(instrument.name);
      this.preset        = instrument.preset;
      this.initialVolume = instrument.initialVolume;
      this.pan           = instrument.pan;
      this.reverb        = instrument.reverb;
      this.chorus        = instrument.chorus;
      this.capo          = instrument.capo;
      this.tuning        = new Tuning(instrument.tuning);
   }

   /**
    * @return   a copy of the instrument.
    */
   @Override
  public Instrument clone()
   {
      return new Instrument(this);
   }

   /**
    * @return the type of the instrument.
    */
   public Type getType()
   {
      return type;
   }

   /**
    * @return the type of the instrument as a string.
    */
   public java.lang.String getTypeName()
   {
      return ResourceBundle.getString("instrument.other");
   }

   /**
    * @param type   the integer type.
    * <br/><br/>
    * @return the type corresponding to the integer type.
    */
   public static Type getType(int type)
   {
      for (Type t : Type.values())
         if (type == t.ordinal())
            return t;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.instrument"), type, Type.Vocals.ordinal()));
   }

   /**
    * sets the type of the instrument.
    * <br/><br/>
    * @param type   the type of the instrument.
    */
   public void setType(Type type)
   {
      this.type = type;
   }

   /**
    * @return the id of the instrument.
    */
   public byte getId()
   {
      return id;
   }

   /**
    * sets the id of the instrument.
    * <br/><br/>
    * @param id   the id of the instrument.
    */
   public void setId(byte id)
   {
      this.id = id;
   }

   /**
    * @return name or description of the instrument.
    */
   public java.lang.String getName()
   {
      return name;
   }

   /**
    * sets name or description of the instrument (ex: rhythm guitar 1).
    * <br/><br/>
    * @param name   the name of the instrument.
    */
   public void setName(java.lang.String name)
   {
      this.name = name;
   }

   /**
    * @return the midi preset to use during playback.
    */
   public Midi.Sound getPreset()
   {
      return preset;
   }

   /**
    * sets the midi sound to use during playback.
    * <br/><br/>
    * @param preset   the midi preset to use during playback.
    */
   public void setPreset(Midi.Sound preset)
   {
      this.preset = preset;
   }

   /**
    * @return the list of midi presets (sounds) that are valid for this instrument.
    * java does not allow abstract static methods, so it was necessary to create a default empty method.
    */
   public static ArrayList<Midi.Sound> getValidPresets()
   {
      return null;
   }

   /**
    * @return whether the midi sound is a valid preset for this instrument.
    * <br/><br/>
    * @param preset   the midi preset to use during playback for this instrument.
    */
   public static boolean isValidPreset(Midi.Sound preset)
   {
      return false;
   }

   /**
    * @return the initial midi channel volume level for the instrument.
    */
   public Volume.Level getInitialVolume()
   {
      return initialVolume;
   }

   /**
    * sets the initial midi channel volume level for the instrument.
    * <br/><br/>
    * @param volume   the initial midi channel volume level for the instrument.
    */
   public void setInitialVolume(Volume.Level volume)
   {
      this.initialVolume = volume;
   }

   /**
    * @return the channel pan setting for the instrument.
    */
   public byte getPan()
   {
      return pan;
   }

   /**
    * sets the channel pan setting for the instrument.
    * <br/><br/>
    * @param pan   the channel pan setting for the instrument.
    */
   public void setPan(byte pan)
   {
      this.pan = pan;
   }

   /**
    * @return the amount of reverb effect used by the instrument.
    */
   public byte getReverb()
   {
      return reverb;
   }

   /**
    * sets the amount of reverb effect used by the instrument.
    * <br/><br/>
    * @param reverb   the amount of reverb effect used by the instrument.
    */
   public void setReverb(byte reverb)
   {
      this.reverb = reverb;
   }

   /**
    * @return the amount of chorus effect used by the instrument.
    */
   public byte getChorus()
   {
      return chorus;
   }

   /**
    * sets the amount of chorus effect used by the instrument.
    * <br/><br/>
    * @param chorus   the amount of chorus effect used by the instrument.
    */
   public void setChorus(byte chorus)
   {
      this.chorus = chorus;
   }

   /**
    * @return the fret placement of a capo.
    */
   public Fret getCapo()
   {
      return capo;
   }

   /**
    * @return the first fret on the instrument.
    */
   public Fret getTopFret()
   {
      return capo == Fret.Not_Used ? Fret.Open : capo;
   }

   /**
    * sets the fret placement of a capo.
    * <br/><br/>
    * @param capo   the fret placement of a capo.
    */
   public void setCapo(Fret capo)
   {
      this.capo = capo;
   }

   /**
    * @return the tuning used for this instrument.
    */
   public Tuning getTuning()
   {
      return tuning;
   }

   /**
    * sets the tuning used for this instrument.
    * <br/><br/>
    * @param tuning   the tuning used for this instrument.
    */
   public void setTuning(Tuning tuning)
   {
      this.tuning = tuning;
   }

   /**
    * @return the number of strings this instrument has.
    */
   public int getNumStrings()
   {
      return tuning.getNumStrings();
   }

   /**
    * @param fret   the integer fret number.
    * <br/><br/>
    * @return the fret corresponding to the integer fret number.
    */
   public static Fret getFret(int fret)
   {
      for (Fret f : Fret.values())
         if (fret == f.ordinal())
            return f;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.instrument.fret"), fret, Fret.Not_Used.ordinal()));
   }

   /**
    * @param string   the integer string number.
    * <br/><br/>
    * @return the string corresponding to the integer string number.
    */
   public static String getString(int string)
   {
      for (String s : String.values())
         if (string == s.ordinal())
            return s;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.instrument.string"), string, String.String_7.ordinal()));
   }

   /**
    * @return the channel number on which the instrument is being played.
    */
   public Midi.Channel getMidiChannel()
   {
      return midiChannel;
   }

   /**
    * sets the midi channel on which this instrument will be played.
    * <br/><br/>
    * @param midiChannel  the number of the midi channel.
    */
   public void setMidiChannel(Midi.Channel midiChannel)
   {
      this.midiChannel = midiChannel;
   }

   /**
    * @return the minimum number of strings an instrument can have.
    */
   public int getMinNumstrings()
   {
      return MIN_NUM_STRINGS;
   }

   /**
    * @return the maximum number of strings an instrument can have.
    */
   public int getMaxNumstrings()
   {
      return MAX_NUM_STRINGS;
   }

   /**
    * @return whether an instrument can have chord diagrams associated with it.
    */
   public boolean canHaveAssociatedChordDiagrams()
   {
      return false;
   }

   /**
    * @return whether two instruments are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
  public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Instrument)
      {
         Instrument instrument = (Instrument)object;
         // are the repeat ending numbers the same?
         equal = type == instrument.type && id == instrument.id && pan == instrument.pan && reverb == instrument.reverb && chorus == instrument.chorus &&
                ((name          == null && instrument.name          == null) || (name          != null && instrument.name          != null && name.equals      (instrument.name        ))) &&
                ((preset        == null && instrument.preset        == null) || (preset        != null && instrument.preset        != null && preset        ==  instrument.preset       )) &&
                ((initialVolume == null && instrument.initialVolume == null) || (initialVolume != null && instrument.initialVolume != null && initialVolume ==  instrument.initialVolume)) &&
                ((capo          == null && instrument.capo          == null) || (capo          != null && instrument.capo          != null && capo          ==  instrument.capo         )) &&
                ((tuning        == null && instrument.tuning        == null) || (tuning        != null && instrument.tuning        != null && tuning.equals    (instrument.tuning      ))) &&
                ((midiChannel   == null && instrument.midiChannel   == null) || (midiChannel   != null && instrument.midiChannel   != null && midiChannel   ==  instrument.midiChannel  ));
      }
      return equal;
   }

   /**
    * read in an instrument from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the instrument from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos           = file.getPosition();
         // the type of instrument is read in by the FileInputStream.readArray() method
         id            = (byte)file.read();
         name          = file.readString();
         preset        = Midi.getSound(file.read());
         initialVolume = Volume.getLevel(file.read());
         pan           = (byte)file.read();
         reverb        = (byte)file.read();
         chorus        = (byte)file.read();
         capo          = getFret(file.read());
         tuning.load(file);
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
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write      (type         .ordinal());
         file.write      (id                     );
         file.writeString(name                   );
         file.write      (preset       .ordinal());
         file.write      (initialVolume.ordinal());
         file.write      (pan                    );
         file.write      (reverb                 );
         file.write      (chorus                 );
         file.write      (capo         .ordinal());
         tuning.save(file);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.instrument"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab instrument.
    */
   @Override
  public java.lang.String toString()
   {
      return toString(9);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab instrument.
    */
   public java.lang.String toString(int numSpacesToIndent)
   {
      StringBuffer     buffer      = new StringBuffer();
      java.lang.String indentation = Utility.indent(numSpacesToIndent);

      buffer.append(Utility.pad(ResourceBundle.getString("text.number"           ), indentation) + id            + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.name"             ), indentation) + name          + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.preset"           ), indentation) + preset        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.initial_volume"   ), indentation) + initialVolume + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.pan"              ), indentation) + pan           + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.reverb"           ), indentation) + reverb        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.chorus"           ), indentation) + chorus        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.capo"             ), indentation) + capo          + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.midi.channel"), indentation) + midiChannel   + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.tuning"           ), indentation) + tuning              );

      return buffer.toString();
   }
}
