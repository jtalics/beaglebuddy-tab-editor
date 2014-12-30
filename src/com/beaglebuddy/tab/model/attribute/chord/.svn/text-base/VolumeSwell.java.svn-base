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
import com.beaglebuddy.tab.model.Volume;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a volume swell chord attribute.
 */
public class VolumeSwell extends ChordAttribute
{
   // class members
   public static final byte MAX_VOLUME_SWELL_NOTES_DURATION = 9;

   // data members
   private Volume.Level startVolume;     // starting volume of the swell.
   private Volume.Level endVolume;       // ending volume of the swell.
   private byte         notesDuration;   // the number of notes over which the volume swell occurs - 0 = occurs over the current chord, 1 and up = occurs over next n chords




   /**
    * default constructor.
    */
   public VolumeSwell()
   {
      this(Volume.Level.Off, Volume.Level.Fff, (byte)1);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param startVolume     starting volume of the swell.
    * @param endVolume       ending volume of the swell.
    * @param notesDuration   the number of notes over which the volume swell occurs.
    */
   public VolumeSwell(Volume.Level startVolume, Volume.Level endVolume, byte notesDuration)
   {
      super(ChordAttribute.Type.VolumeSwell);

      setStartVolume  (startVolume  );
      setEndVolume    (endVolume    );
      setNotesDuration(notesDuration);

      if (startVolume == endVolume)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.volume_swell.values", ResourceBundle.getString("data_type.chord_attribute.volume_swell"), startVolume, endVolume));
   }

   /**
    * @return the starting volume of the swell.
    */
   public Volume.Level getStartVolume()
   {
      return startVolume;
   }

   /**
    * sets the starting volume of the swell.
    * <br/><br/>
    * @param volume   the starting volume of the swell.
    */
   public void setStartVolume(Volume.Level volume)
   {
      this.startVolume = volume;
   }

   /**
    * @return the ending volume of the swell.
    */
   public Volume.Level getEndVolume()
   {
      return startVolume;
   }

   /**
    * sets the ending volume of the swell.
    * <br/><br/>
    * @param volume   the ending volume of the swell.
    */
   public void setEndVolume(Volume.Level volume)
   {
      this.endVolume = volume;
   }

   /**
    * @return the number of notes over which the volume swell occurs.  0 = occurs over the position, 1 and up = occurs over next n positions.
    */
   public byte getNotesDuration()
   {
      return notesDuration;
   }

   /**
    * sets the number of notes over which the volume swell occurs.
    * <br/><br/>
    * @param notesDuration   the number of notes over which the volume swell occurs. 0 = occurs over the position, 1 and up = occurs over next n positions.
    */
   public void setNotesDuration(byte notesDuration)
   {
      if (notesDuration < 0 || notesDuration > MAX_VOLUME_SWELL_NOTES_DURATION)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.chord_attribute.volume_swell.notes_duration"), notesDuration, 0, MAX_VOLUME_SWELL_NOTES_DURATION));
      this.notesDuration = notesDuration;
   }

   /**
    * @return a new copy of the volume swell chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new VolumeSwell(this.startVolume, this.endVolume, this.notesDuration);
   }

   /**
    * @param object  object to check for equality with this volume swell attribute.
    * <br/><br/>
    * @return if the two volume swell attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof VolumeSwell)
      {
         VolumeSwell volumeSwell = (VolumeSwell)object;
         result = this.startVolume == volumeSwell.startVolume && this.endVolume == volumeSwell.endVolume && this.notesDuration == volumeSwell.notesDuration;
      }
      return result;
   }

   /**
    * @return whether the volume swell chord attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the volume swell chord attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the volume swell chord attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         startVolume   = Volume.getLevel(file.read());
         endVolume     = Volume.getLevel(file.read());
         notesDuration = (byte)file.read();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.volume_swell"));
      }
   }

   /**
    * save a volume swell chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the volume swell chord attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(startVolume.ordinal());
         file.write(  endVolume.ordinal());
         file.write(notesDuration);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.volume_swell"));
      }
   }

   /**
    * @return a string representation of the volume swell.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" +
             ResourceBundle.getString("text.start_volume") + ": " + startVolume   + ", " +
             ResourceBundle.getString("text.end_volume"  ) + ": " + endVolume     + ", " +
             ResourceBundle.getString("text.duration"    ) + ": " + notesDuration + ")";
   }
}
