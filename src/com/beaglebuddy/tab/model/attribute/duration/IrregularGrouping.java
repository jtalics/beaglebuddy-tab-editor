/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.duration;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;



/**
 * This class represents an irregular grouping duration attribute.
 * For example, when playing 5 sixteenth notes in the space of time that 4 sixteenth notes would be played.
 * Notated: 5:4
 */
public class IrregularGrouping extends DurationAttribute
{
   // which part of the irregular grouping is this chord
   public enum Part {Begin, Middle, End}

   // class members
   public static final int MIN_NUM_NOTES_PLAYED      = 3;
   public static final int MAX_NUM_NOTES_PLAYED      = 16;
   public static final int MIN_NUM_NOTES_PLAYED_OVER = 2;
   public static final int MAX_NUM_NOTES_PLAYED_OVER = 8;

   // data members
   private Part part;                // start, middle, or end of an irregular grouping of chords
   private byte numNotesPlayed;
   private byte numNotesPlayedOver;  // ex: playing 6 notes over the space of 4 sixteenth notes - 6:4




   /**
    * default constructor.
    */
   public IrregularGrouping()
   {
      this(Part.Begin);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param part   which part of the irregular grouping is this chord.
    */
   public IrregularGrouping(Part part)
   {
      this(part, (byte)MIN_NUM_NOTES_PLAYED, (byte)MIN_NUM_NOTES_PLAYED_OVER);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param part                where in the irregular grouping is this chord.
    * @param numNotesPlayed      the number of notes to play.
    * @param numNotesPlayedOver  notes are pplayed in the space of this many notes.
    */
   public IrregularGrouping(Part part, byte numNotesPlayed, byte numNotesPlayedOver)
   {
      super(DurationAttribute.Type.IrregularGrouping);

      if (numNotesPlayed < MIN_NUM_NOTES_PLAYED      || numNotesPlayed > MAX_NUM_NOTES_PLAYED)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.duration_attribute.irregular_grouping.num_notes_played"     ), numNotesPlayed    , MIN_NUM_NOTES_PLAYED     , MAX_NUM_NOTES_PLAYED     ));
      if (numNotesPlayed < MIN_NUM_NOTES_PLAYED_OVER || numNotesPlayed > MAX_NUM_NOTES_PLAYED_OVER)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.duration_attribute.irregular_grouping.num_notes_played_over"), numNotesPlayedOver, MIN_NUM_NOTES_PLAYED_OVER, MAX_NUM_NOTES_PLAYED_OVER));

      this.part               = part;
      this.numNotesPlayed     = numNotesPlayed;
      this.numNotesPlayedOver = numNotesPlayedOver;
   }

   /**
    * @return the part of the irregular grouping of this chord.
    */
   public Part getPart()
   {
      return part;
   }

   /**
    * @param part   the integer part of the irregular grouping of this chord.
    * <br/><br/>
    * @return the part enum corresponding to the integer part.
    */
   public static Part getPart(int part)
   {
      for (Part p : Part.values())
         if (part == p.ordinal())
            return p;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.duration_attribute.irregular_grouping.part"), part, Part.End.ordinal()));
   }

   /**
    * set the part of the irregular grouping of this chord.
    * <br/><br/>
    * @param part   which part of the irregular grouping is this chord.
    */
   public void setPart(Part part)
   {
      this.part = part;
   }

   /**
    * @return the number of notes played in the irregular grouping.
    */
   public byte getNumNotesPlayed()
   {
      return numNotesPlayed;
   }

   /**
    * sets the number of notes played in the irregular grouping.
    * <br/><br/>
    * @param numNotesPlayed   the number of notes played in the irregular grouping.
    */
   public void setNumNotesPlayed(byte numNotesPlayed)
   {
      this.numNotesPlayed = numNotesPlayed;
   }

   /**
    * @return the number of notes to be played over in the irregular grouping.
    */
   public byte getNumNotesPlayedOver()
   {
      return numNotesPlayedOver;
   }

   /**
    * sets the number of notes to be played over in the irregular grouping.
    * <br/><br/>
    * @param numNotesPlayedOver   the number of notes to be played over in the irregular grouping.
    */
   public void setNumNotesPlayedOver(byte numNotesPlayedOver)
   {
      this.numNotesPlayedOver = numNotesPlayedOver;
   }

   /**
    * @return a new copy of the irregular grouping duration attribute.
    */
   @Override
   public DurationAttribute clone()
   {
      return new IrregularGrouping(part, numNotesPlayed, numNotesPlayedOver);
   }

   /**
    * @param object  object to check for equality with this irregular grouping attribute.
    * <br/><br/>
    * @return if the two irregular grouping attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof IrregularGrouping)
      {
         IrregularGrouping irregularGrouping = (IrregularGrouping)object;
         result = this.part               == irregularGrouping.part           &&
                  this.numNotesPlayed     == irregularGrouping.numNotesPlayed &&
                  this.numNotesPlayedOver == irregularGrouping.numNotesPlayedOver;
      }
      return result;
   }

   /**
    * @return whether the irregular grouping duration attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the irregular grouping duration attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the irregular grouping duration attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         part               = getPart(file.read());
         numNotesPlayed     = (byte)file.read();
         numNotesPlayedOver = (byte)file.read();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.duration_attribute.irregular_grouping"));
      }
   }

   /**
    * save an irregular grouping duration attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the irregular grouping duration attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(part.ordinal());
         file.write(numNotesPlayed    );
         file.write(numNotesPlayedOver);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.duration_attribute.irregular_grouping"));
      }
   }

   /**
    * @return a string representation of the irregular grouping.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer(getType().text() + " ");

      buffer.append(numNotesPlayed + ":" + numNotesPlayedOver + " - ");
           if (part == Part.Begin ) buffer.append(ResourceBundle.getString("text.begin" ));
      else if (part == Part.Middle) buffer.append(ResourceBundle.getString("text.middle"));
      else                          buffer.append(ResourceBundle.getString("text.end"   ));

      return buffer.toString();
   }
}
