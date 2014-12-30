/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.chord;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a tremolo bar chord attribute.
 */
public class TremoloBar extends ChordAttribute
{
   // enums
   public enum TremoloBarType  {Dip, DiveAndRelease, DiveAndHold, Release, ReturnAndRelease, ReturnAndHold, InvertedDip}

   // class members
   public static final byte MAX_TREMOLO_BAR_NOTES_DURATION = 8;
   public static final byte MAX_TREMOLO_BAR_PITCH          = 28;

   // data members
   private TremoloBarType tremoloBarType;     // type of tremolo bar use.
   private byte           notesDuration;      // the number of chords over which the tremolo bar occurs - 0 = occurs over the current chord, 1 and up = occurs over next n chords.
   private byte           pitch;              // pitch of the tremolo bar (in quarter steps.  so 2 = 1/2 step, 4 = 1 step, 8 = 2 steps, etc.)




   /**
    * default constructor.
    */
   public TremoloBar()
   {
      super(ChordAttribute.Type.TremoloBar);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param tremoloBarType   type of tremolo bar use.
    * @param notesDuration    the number of chords over which the tremolo bar occurs.  0 = occurs over chord, 1 and up = occurs over next n chords.
    * @param pitch            pitch of the tremolo bar (in quarter steps.  so 2 = 1/2 step, 4 = 1 step, 8 = 2 steps, etc.)
    */
   public TremoloBar(TremoloBarType tremoloBarType, byte notesDuration, byte pitch)
   {
      super(ChordAttribute.Type.TremoloBar);

      setTremoloBarType(tremoloBarType);
      setNotesDuration (notesDuration);
      setPitch         (pitch);
   }

   /**
    * @return the type of tremolo bar use.
    */
   public TremoloBarType getTremoloBarType()
   {
      return tremoloBarType;
   }

   /**
    * @param tremoloBarType  the integer tremolo bar type.
    * <br/><br/>
    * @return the tremolo bar type enum corresponding to the integer tremolo bar type.
    */
   public static TremoloBarType getTremoloBarType(int tremoloBarType)
   {
      for (TremoloBarType tbt : TremoloBarType.values())
         if (tremoloBarType == tbt.ordinal())
            return tbt;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.chord_attribute.tremolo_bar"), tremoloBarType, TremoloBarType.InvertedDip.ordinal()));
   }

   /**
    * sets the type of tremolo bar use.
    * <br/><br/>
    * @param tremoloBarType   the type of tremolo bar use.
    */
   public void setTremoloBarType(TremoloBarType tremoloBarType)
   {
      this.tremoloBarType = tremoloBarType;
   }

   /**
    * @return the duration of the tremolo bar. 0 = occurs over the current chord, 1 and up = occurs over next n chords.
    */
   public byte getNotesDuration()
   {
      return notesDuration;
   }

   /**
    * sets the duration of the tremolo bar.
    * <br/><br/>
    * @param notesDuration   the duration of the tremolo bar. 0 = occurs over the current chord, 1 and up = occurs over next n chords.
    */
   public void setNotesDuration(byte notesDuration)
   {
      if (notesDuration < 0 || notesDuration > MAX_TREMOLO_BAR_NOTES_DURATION)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.chord_attribute.tremolo_bar.notes_duration"), notesDuration, 0, MAX_TREMOLO_BAR_NOTES_DURATION));
      this.notesDuration = notesDuration;
   }

   /**
    * @return the pitch of the tremolo bar.
    */
   public byte getPitch()
   {
      return pitch;
   }

   /**
    * sets the pitch of the tremolo bar.
    * <br/><br/>
    * @param pitch   the pitch of the tremolo bar.
    */
   public void setPitch(byte pitch)
   {
      if (pitch < 0 || pitch > MAX_TREMOLO_BAR_PITCH)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.chord_attribute.tremolo_bar.pitch"), pitch, 0, MAX_TREMOLO_BAR_PITCH));
      this.pitch = pitch;
   }

   /**
    * @return a new copy of the tremolo bar chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new TremoloBar(this.tremoloBarType, this.notesDuration, this.pitch);
   }

   /**
    * @param object  object to check for equality with this tremolo bar attribute.
    * <br/><br/>
    * @return if the two tremolo bar attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof TremoloBar)
      {
         TremoloBar tremoloBar = (TremoloBar)object;
         result = this.tremoloBarType == tremoloBar.tremoloBarType && this.notesDuration == tremoloBar.notesDuration && this.pitch == tremoloBar.pitch;
      }
      return result;
   }

   /**
    * @return that the tremolo bar chord attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the tremolo bar chord attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the tremolo bar chord attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         tremoloBarType = getTremoloBarType(file.read());
         notesDuration  = (byte)file.read();
         pitch          = (byte)file.read();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.tremolo_bar"));
      }
   }

   /**
    * save a tremolo bar chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the tremolo bar chord attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(tremoloBarType.ordinal());
         file.write(notesDuration);
         file.write(pitch        );
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.tremolo_bar"));
      }
   }

   /**
    * @return a string representation of the tremolo bar.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" +
             ResourceBundle.getString("text.tremolo.bar_type") + ": " + tremoloBarType + ", " +
             ResourceBundle.getString("text.duration"        ) + ": " + notesDuration  + ", " +
             ResourceBundle.getString("text.pitch"           ) + ": " + pitch          + ")";
   }
}
