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
 * This class represents a beaglebuddy tab tempo marker and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * A tempo marker is used to indicate a change in tempo, and is allowed only at bar lines, which demarcate the beginning of a measure.
 */
public class TempoMarker
{
   // class members
   public static final int MIN_NUM_BEATS_PER_MINUTE     = 40;
   public static final int MAX_NUM_BEATS_PER_MINUTE     = 300;
   public static final int DEFAULT_NUM_BEATS_PER_MINUTE = 120;

   // data members
   private short   beatsPerMinute;      // number of beats per minute
   private boolean swingFeel;           // whether two eigth notes are played like a quarter note and eith note triplet
   private String  description;         // text to be output




   /**
    * default constructor.
    */
   public TempoMarker()
   {
      beatsPerMinute = DEFAULT_NUM_BEATS_PER_MINUTE;
      swingFeel      = false;
      description    = null;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param beatsPerMinute number of beats per minute.
    * @param description    text to be output.
    * @param swingFeel      whether the song is played with a swing feel.
    */
   public TempoMarker(short beatsPerMinute, String description, boolean swingFeel)
   {
      this.beatsPerMinute = beatsPerMinute;
      this.description    = description;
      this.swingFeel      = swingFeel;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param tempoMarker   the tempo marker whose values will be deep copied.
    */
   public TempoMarker(TempoMarker tempoMarker)
   {
      this();

      if (tempoMarker != null)
      {
         this.beatsPerMinute = tempoMarker.beatsPerMinute;
         this.description    = tempoMarker.description == null ? null : new String(tempoMarker.description);
         this.swingFeel      = tempoMarker.swingFeel;
      }
   }

   /**
    * @return the number of beats per minute.
    */
   public short getBeatsPerMinute()
   {
      return beatsPerMinute;
   }

   /**
    * @return the description to be output.
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * @return whether the tempo has a swing feel.
    */
   public boolean hasSwingFeel()
   {
      return swingFeel;
   }

   /**
    * @return whether two tempo markers are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof TempoMarker)
      {
         TempoMarker tempoMarker = (TempoMarker)object;
         equal = this.beatsPerMinute == tempoMarker.beatsPerMinute   &&
                 this.swingFeel      == tempoMarker.swingFeel        &&
               ((this.description == null && tempoMarker.description == null) ||
                (this.description != null && tempoMarker.description != null && this.description.equals(tempoMarker.description)));
      }
      return equal;
   }

   /**
    * read in a tempo marker from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the tempo marker text from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         beatsPerMinute = file.readShort();
         description    = file.readString();
         swingFeel      = file.readBoolean();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.tempo_marker"));
      }
   }

   /**
    * save a tempo marker to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the tempo marker to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeShort  (beatsPerMinute);
         file.writeString (description);
         file.writeBoolean(swingFeel);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.tempo_marker"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab tempo marker.
    */
   @Override
   public String toString()
   {
      return toString(18);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab tempo marker.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.tempo_marker") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.beats_per_minute"), indentation) + beatsPerMinute + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.description"     ), indentation) + description    + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.swing_feel"      ), indentation) + swingFeel            );

      return buffer.toString();
   }
}
