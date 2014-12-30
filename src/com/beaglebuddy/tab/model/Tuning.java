/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.io.IOException;




/**
 * This class represents a beaglebuddy tab guitar tuning and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * The tuning is stored from high string to low string, so that:
 * notes[0] -> 1st string : high e string
 * notes[1] -> 2nd string :      b string
 * notes[2] -> 3rd string :      g string
 * notes[3] -> 4th string :      d string
 * notes[4] -> 5th string :      a string
 * notes[5] -> 6th string : low  e string
 * </p>
 * <p>
 * the offset is used when displaying notes in a song on the standard music staff.
 * it is most commonly used for songs that have instruments tuned down a half step.
 * if the offset is 0, and the tuning is down ½ step, then the notes will be drawn with lots of flats, which is not what is generally wanted.
 * instead, if the offset is set to +1 (half step), then the notes in the song will be drawn half a step higher, and the flats will go away.
 * </p>
 * <p>
 * ex: tuning: down ½ step
 *     offset: +1
 *     e note: (1st string, open) - drawn as e natural
 * </p>
 * <p>
 * ex: tuning: down ½ step
 *     offset: 0
 *     e note: (1st string, open) - drawn as e flat
 * </p>
 */
public class Tuning
{
   // class members
   public static final byte MAX_MUSIC_NOTATION_OFFSET =  8;
   public static final byte MIN_MUSIC_NOTATION_OFFSET = -8;

   // data members
   protected String      name;                // name (or description) of the tuning
   protected byte        musicNotationOffset; // offset in semi-tones (half steps)
   protected Midi.Note[] notes;               // midi notes of the tuning, ordered from high string to low string




   /**
    * default constructor.
    */
   public Tuning()
   {
      name                = ResourceBundle.getString("text.not_used");
      notes               = new Midi.Note[0];
      musicNotationOffset = (byte)0;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param name                 name (or description) of the tuning.
    * @param musicNotationOffset  offset in semi-tones (half steps)
    * @param notes                midi notes of the tuning, ordered from high string to low string.
    */
   public Tuning(String name, byte musicNotationOffset, Midi.Note[] notes)
   {
      this.name                = name;
      this.musicNotationOffset = musicNotationOffset;
      this.notes               = notes;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param tuning  tuning whose values will be deep copied.
    */
   public Tuning(Tuning tuning)
   {
      this.name                = new String(tuning.getName());
      this.musicNotationOffset = tuning.musicNotationOffset;
      this.notes               = new Midi.Note[tuning.getNumStrings()];
      for(int i=0; i<tuning.getNotes().length; ++i)
         this.notes[i] = tuning.getNotes()[i];
   }

   /**
    * @return the name of the tuning.
    */
   public String getName()
   {
      return name;
   }

   /**
    * sets the name of the tuning.
    * <br/><br/>
    * @param name  the name of the tuning.
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return offset in semi-tones (half steps)
    */
   public byte getMusicNotationOffset()
   {
      return musicNotationOffset;
   }

   /**
    * @return midi notes of the tuning, ordered from high string to low string.
    */
   public Midi.Note[] getNotes()
   {
      return notes;
   }

   /**
    * @return the number of strings in the tuning.
    */
   public int getNumStrings()
   {
      return notes.length;
   }

   /**
    * @return whether two tunings are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Tuning)
      {
         Tuning tuning = (Tuning)object;
         equal = this.name.equals(tuning.name) && this.musicNotationOffset == tuning.musicNotationOffset && this.notes.length == tuning.notes.length;
         for(int i=0; i<notes.length && equal; ++i)
            equal = this.notes[i].equals(tuning.notes[i]);
      }
      return equal;
   }

   /**
    * read in an instrument tuning from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the instrument tuning from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         name                = file.readString();
         musicNotationOffset = (byte)file.read();

         int numNotes = file.readShort();
         notes = new Midi.Note[numNotes];
         for(int i=0; i<numNotes; ++i)
            notes[i] = Midi.getNote(file.read());

         if (name == null || name.trim().length() == 0)
            throw new IllegalArgumentException(ResourceBundle.getString("error.invalid.tuning_name"));
         if (musicNotationOffset < -12 || musicNotationOffset > 12)
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("text.music_notation_offset"), musicNotationOffset, -12, 12));
         if (numNotes < Instrument.MIN_NUM_STRINGS || numNotes > Instrument.MAX_NUM_STRINGS)
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("text.num_strings"), numNotes, Instrument.MIN_NUM_STRINGS, Instrument.MAX_NUM_STRINGS));
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.tuning"));
      }
   }

   /**
    * save an instrument tuning to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the instrument tuning to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeString(name);
         file.write(musicNotationOffset);
         file.writeShort((short)notes.length);
         for(Midi.Note note : notes)
            file.write(note.ordinal());
      }
      catch (IOException ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.tuning"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab guitar tuning.
    */
   @Override
   public String toString()
   {
      return toString(12);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab guitar tuning.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(ResourceBundle.getString("data_type.tuning") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.name"                 ), indentation1) + name                                     + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.music_notation_offset"), indentation1) + musicNotationOffset                      + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.strings"              ), indentation1) + (notes.length == 0 ? "0" : (notes.length + "\n")));
      for(int i=0; i<notes.length; ++i)
         buffer.append(Utility.pad(ResourceBundle.getString("text.string") + "[" + i + "]" , indentation2) + notes[i] + (i == notes.length - 1 ? "" : "\n"));

      return buffer.toString();
   }
}
