/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.staff;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.Chord;
import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.List;





/**
 * This class represents a beaglebuddy tab base staff and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class StaffBase
{
   // class members
   public static final int  LowVoice = 0;
   public static final int HighVoice = 1;
   public static final int NumVoices = 2;

   // data members
   protected ArrayList<Chord>  lowVoiceChords;       // chords - one for the low  voice melody
   protected ArrayList<Chord> highVoiceChords;       // chords - one for the high voice melody





   /**
    * default constructor.
    */
   public StaffBase()
   {
      lowVoiceChords  = new ArrayList<Chord>(0);
      highVoiceChords = new ArrayList<Chord>(0);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param highVoiceChords  chords in the high voice melody.
    * @param lowVoiceChords   chords in the low  voice melody.
    */
   public StaffBase(Chord[] lowVoiceChords, Chord[] highVoiceChords)
   {
      setChords( lowVoiceChords, Staff. LowVoice);
      setChords(highVoiceChords, Staff.HighVoice);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param staff   staff.
    */
   public StaffBase(StaffBase staff)
   {
      this();

      if (staff != null)
      {
         if (staff.lowVoiceChords != null)
         {
            lowVoiceChords = new ArrayList<Chord>(staff.lowVoiceChords.size());
            for(Chord chord : staff.lowVoiceChords)
               lowVoiceChords.add(new Chord(chord));
         }
         if (staff.highVoiceChords != null)
         {
            highVoiceChords = new ArrayList<Chord>(staff.highVoiceChords.size());
            for(Chord chord : staff.highVoiceChords)
               highVoiceChords.add(new Chord(chord));
         }
      }
   }

   /**
    * @return both the high voice and the low voice melody lines.
    */
   public List<List<Chord>> getChordLists()
   {
      ArrayList<List<Chord>> melodyLines = new ArrayList<List<Chord>>();

      melodyLines.add(lowVoiceChords );
      melodyLines.add(highVoiceChords);

      return melodyLines;
   }

   /**
    * @return the specified melody line.
    * <br/><br/>
    * @param voice   which melody line to return, either the high voice or low voice.
    */
   public ArrayList<Chord> getChords(int voice)
   {
      if (voice < LowVoice || voice > HighVoice)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("text.staff_voice"), voice, HighVoice));

      return (voice == LowVoice ? lowVoiceChords : highVoiceChords);
   }

   /**
    * sets the chords (chords and rests) for the specified melody line.
    * <br/><br/>
    * @param chords   the chords for the specified melody line.
    * @param voice    which melody line the chords are for.
    */
   public void setChords(Chord[] chords, int voice)
   {
      if (voice < LowVoice || voice > HighVoice)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("text.staff_voice"), voice, HighVoice));

      if (voice == LowVoice)
      {
         this.lowVoiceChords = new ArrayList<Chord>(0);

         if (chords != null)
         {
            for(Chord chord : chords)
               this.lowVoiceChords.add(chord);
         }
      }
      else
      {
         this.highVoiceChords = new ArrayList<Chord>(0);

         if (chords != null)
         {
            for(Chord chord : chords)
               this.highVoiceChords.add(chord);
         }
      }
   }

   /**
    * sets the chords (chords and rests) for the specified melody line.
    * <br/><br/>
    * @param chords   the chords for the specified melody line.
    * @param voice    which melody line the chords are for.
    */
   private void setChords(Object[] chords, int voice)
   {
      if (voice < LowVoice || voice > HighVoice)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("text.staff_voice"), voice, HighVoice));

      if (voice == LowVoice)
      {
         this.lowVoiceChords = new ArrayList<Chord>(0);

         if (chords != null)
         {
            for(Object chord : chords)
               this.lowVoiceChords.add((Chord)chord);
         }
      }
      else
      {
         this.highVoiceChords = new ArrayList<Chord>(0);

         if (chords != null)
         {
            for(Object chord : chords)
               this.highVoiceChords.add((Chord)chord);
         }
      }
   }

   /**
    * @return whether two staffs are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
  public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof StaffBase)
      {
         StaffBase staff = (StaffBase)object;
         equal = (this.lowVoiceChords == null && staff.lowVoiceChords == null) ||
                 (this.lowVoiceChords != null && staff.lowVoiceChords != null && this.lowVoiceChords.size() == staff.lowVoiceChords.size());
         for(int i=0; i<lowVoiceChords.size() && equal; ++i)
            equal = this.lowVoiceChords.get(i).equals(staff.lowVoiceChords.get(i));

         if (equal)
         {
            equal = (this.highVoiceChords == null && staff.highVoiceChords == null) ||
                    (this.highVoiceChords != null && staff.highVoiceChords != null && this.highVoiceChords.size() == staff.highVoiceChords.size());
            for(int i=0; i<highVoiceChords.size() && equal; ++i)
               equal = this.highVoiceChords.get(i).equals(staff.highVoiceChords.get(i));
         }
      }
      return equal;
   }

   /**
    * @return whether the tab staff has an associated music staff with a bass clef.
    */
   public boolean hasBassMusicStaff()
   {
      return false;
   }

   /**
    * @return whether the tab staff has an associated music staff with a treble clef.
    */
   public boolean hasTrebleMusicStaff()
   {
      return false;
   }

   /**
    * read in a staff from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the staff from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos  = file.getPosition();
         setChords(file.readArray(Chord.class), LowVoice );
         setChords(file.readArray(Chord.class), HighVoice);
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.staff"));
      }
   }

   /**
    * save a staff to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the staff to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeArray( lowVoiceChords.toArray());
         file.writeArray(highVoiceChords.toArray());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.staff"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab staff.
    */
   @Override
  public String toString()
   {
      return toString(15);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab staff.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(Utility.pad(ResourceBundle.getString("text.low_voice_chords"         ), indentation1) +  lowVoiceChords.size() + "\n");
      for(int i=0; i<lowVoiceChords.size();  ++i)
         buffer.append(Utility.pad(ResourceBundle.getString("text.chord") + "[" + i + "]" , indentation2) +  lowVoiceChords.get(i) + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.high_voice_chords"        ), indentation1) + highVoiceChords.size() + (highVoiceChords.size() == 0 ? "" : "\n"));
      for(int i=0; i<highVoiceChords.size(); ++i)
         buffer.append(Utility.pad(ResourceBundle.getString("text.chord") + "[" + i + "]" , indentation2) + highVoiceChords.get(i) + (i == highVoiceChords.size() - 1 ? "" : "\n"));

      return buffer.toString();
   }
}
