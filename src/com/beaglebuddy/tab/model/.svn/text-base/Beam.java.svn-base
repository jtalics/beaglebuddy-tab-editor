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
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a beaglebuddy tab beam and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * A beam is the bar connecting two or more positions (chords or notes).
 * A given position can have more than one beam type.
 */
public class Beam
{
   // enums
   public enum Type
   {  // a beam can be a combination of the following values
      Start((byte)0x01), FractionalLeft((byte)0x02), FractionalRight((byte)0x04), End((byte)0x08);  // None((byte)0x00),

      // data members
      private byte mask;
      Type(byte mask)    {this.mask = mask;}
      public byte mask() {return mask;}
   }

   // data members
   private byte          type;                        // type(s) of beam
   private Midi.Duration previousBeamDuration;        // midi duration of the previous beam




   /**
    * default constructor.
    */
   public Beam()
   {
      type                 = 0;
      previousBeamDuration = Midi.Duration.NOTE_NONE;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param type                   type(s) of beam
    * @param previousBeamDuration   midi duration of the previous beam
    */
   public Beam(byte type, Midi.Duration previousBeamDuration)
   {
      this.type                 = type;
      this.previousBeamDuration = previousBeamDuration;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param beam   beam whose values will be deep copied.
    */
   public Beam(Beam beam)
   {
      this();

      if (beam != null)
      {
         this.type                 = beam.type;
         this.previousBeamDuration = beam.previousBeamDuration;
      }
   }

   /**
    * @return the beam type(s).
    */
   public byte getType()
   {
      return type;
   }

   /**
    * @return the beam type as a string.
    */
   public String getTypeDescription()
   {
      StringBuffer buffer = new StringBuffer();

      if (type == 0x00)
      {
         buffer.append(ResourceBundle.getString("text.none") + "  ");
      }
      else
      {
         for (Type t : Type.values())
            if ((type & t.mask()) != 0)
               buffer.append(t + ", ");
      }
      String string = buffer.toString();
      return string.substring(0, string.length() - 2);
   }

   /**
    * sets the beam type(s).
    * <br/><br/>
    * @param type   the beam type(s).
    */
   public void setType(byte type)
   {
      this.type = type;
   }

   /**
    * @return if this position has a beam.
    */
   public boolean hasBeam()
   {
      return type != 0;
   }

   /**
    * @return if this is the end of a beam.
    */
   public boolean isEnd()
   {
      return (type & Type.End.mask()) != 0;
   }

   /**
    * @return if this is a fractional left beam.
    */
   public boolean isFractionalLeft()
   {
      return (type & Type.FractionalLeft.mask()) != 0;
   }

   /**
    * @return if this is a fractional right beam.
    */
   public boolean isFractionalright()
   {
      return (type & Type.FractionalRight.mask()) != 0;
   }

   /**
    * @return if this is the start of a beam.
    */
   public boolean isStart()
   {
      return (type & Type.Start.mask()) != 0;
   }

   /**
    * @return the duration of the previous beam in the position.
    */
   public Midi.Duration getPreviousBeamDuration()
   {
      return previousBeamDuration;
   }

   /**
    * sets the duration of the previous beam in the position.
    * <br/><br/>
    * @param duration   the duration of the previous beam in the position.
    */
   public void setPreviousBeamDuration(Midi.Duration duration)
   {
      this.previousBeamDuration = duration;

      assert duration == Midi.Duration.NOTE_NONE || duration == Midi.Duration.NOTE_8TH || duration == Midi.Duration.NOTE_16TH || duration == Midi.Duration.NOTE_32ND || duration == Midi.Duration.NOTE_64TH;
   }

   /**
    * @return whether two beams are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Beam)
      {
         Beam beam = (Beam)object;
         equal = this.type == beam.type && this.previousBeamDuration == beam.previousBeamDuration;
      }
      return equal;
   }

   /**
    * read in a beam from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the beam from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long  pos = -1L;
      try
      {
         pos                  = file.getPosition();
         type                 = (byte)file.read();
         previousBeamDuration = Midi.getDuration(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.beam"));
      }
   }

   /**
    * save a beam to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the beam to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long  pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(type                          );
         file.write(previousBeamDuration.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.beam"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab beam.
    */
   @Override
   public String toString()
   {
      return toString(24);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab beam.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.beam") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.type"                  ), indentation) + getTypeDescription() + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.previous_beam_duration"), indentation) + previousBeamDuration       );

      return buffer.toString();
   }
}
