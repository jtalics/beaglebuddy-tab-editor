/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.note;

import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a beaglebuddy tab artificial harmonic note attribute.
 */
public class ArtificialHarmonic extends NoteAttribute
{
   // data members
   private Midi.Note         note;         // pitch at which the artificial harmonic sounds.
   private Octave.OctaveType octave;       // octave at which the note sounds.




   /**
    * default constructor.
    */
    public ArtificialHarmonic()
    {
       this(Midi.Note.Middle_C, Octave.OctaveType.OctaveLoco);
    }

   /**
    * constructor.
    * <br/><br/>
    * @param note     pitch at which the artificial harmonic sounds.
    * @param octave   octave at which the note sounds.
    */
   public ArtificialHarmonic(Midi.Note note, Octave.OctaveType octave)
   {
      super(NoteAttribute.Type.ArtificialHarmonic);

      this.note   = note;
      this.octave = octave;
   }

   /**
    * @return the pitch at which the artificial harmonic sounds.
    */
   public Midi.Note getNote()
   {
      return note;
   }

   /**
    * sets the pitch at which the artificial harmonic sounds.
    * <br/><br/>
    * @param note   the pitch at which the artificial harmonic sounds.
    */
   public void setNote(Midi.Note note)
   {
      this.note = note;
   }

   /**
    * @return the octave.
    */
   public Octave.OctaveType getOctave()
   {
      return octave;
   }

   /**
    * sets the octave.
    * <br/><br/>
    * @param octave   the octave.
    */
   public void setOctave(Octave.OctaveType octave)
   {
      this.octave = octave;
   }

   /**
    * @return a new copy of the artificial harmonic note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new ArtificialHarmonic(this.note, this.octave);
   }

   /**
    * @param object  object to check for equality with this artificial harmonic attribute.
    * <br/><br/>
    * @return if the two artificial harmonic attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof ArtificialHarmonic)
      {
         ArtificialHarmonic ah = (ArtificialHarmonic)object;
         result = this.note == ah.note && this.octave == ah.octave;
      }
      return result;
   }

   /**
    * @return that the artificial harmonic note attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the artificial harmonic note attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the artificial harmonic note attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         note   = Midi.getNote        (file.read());
         octave = Octave.getOctaveType(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.artificial_harmonic"));
      }
   }

   /**
    * save a artificial harmonic note attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the artificial harmonic note attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(note  .ordinal());
         file.write(octave.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.artificial_harmonic"));
      }
   }
   /**
    * @return a string representation of the artificial harmonic.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append(getType().text() + " (");
      buffer.append(ResourceBundle.getString("data_type.note") + ": " + note   + ", ");
      buffer.append(ResourceBundle.getString("text.octave"   ) + ": " + octave + ")" );

      return buffer.toString();
   }
}
