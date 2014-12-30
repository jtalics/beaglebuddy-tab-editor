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
import java.io.IOException;





/**
 * This class represents a beaglebuddy tab time signature and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * meter = beatsPerMeasure / beatAmount
 */
public class TimeSignature
{
   // class members
   public static final int DEFAULT_NUM_BEATS_PER_MEASURE = 4;
   public static final int MIN_NUM_BEATS_PER_MEASURE     = 1;
   public static final int MAX_NUM_BEATS_PER_MEASURE     = 32;

   // data members
   private byte    beatsPerMeasure;   // number of beats per measure (top    half of time signature)
   private byte    beatAmount;        // beat amount                 (bottom half of time signature)
   private byte[]  beamingPattern;    // beaming pattern for eigth notes
   private boolean show;              // show the time signature
   private boolean commonTime;        // whether the time signature is in common time (4/4) and uses the common time symbol.
   private boolean cutTime;           // whether the time signature is in cut    time (2/2) and uses the cut    time symbol.



   /**
    * default constructor.
    */
   public TimeSignature()
   {
      beatsPerMeasure = 4;
      beatAmount      = 4;
      beamingPattern  = new byte[4];
   }

   /**
    * constructor.
    * <br/><br/>
    * @param beatsPerMeasure number of beats per measure (top    half of time signature).
    * @param beatAmount      beat amount                 (bottom half of time signature).
    * @param beamingPattern  beaming pattern for eigth notes
    * @param show            show the time signature.
    * @param commonTime      whether the time signature is in common time (4/4) and uses the common time symbol.
    * @param cutTime         whether the time signature is in cut    time (2/2) and uses the cut    time symbol.
    */
   public TimeSignature(byte beatsPerMeasure, byte beatAmount, byte[] beamingPattern, boolean show, boolean commonTime, boolean cutTime)
   {
      this.beatsPerMeasure = beatsPerMeasure;
      this.beatAmount      = beatAmount;
      this.beamingPattern  = beamingPattern;
      this.show            = show;
      this.commonTime      = commonTime;
      this.cutTime         = cutTime;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param timeSignature   time signature whose values will be deep copied.
    */
   public TimeSignature(TimeSignature timeSignature)
   {
      this.beatsPerMeasure = timeSignature.beatsPerMeasure;
      this.beatAmount      = timeSignature.beatAmount;
      this.show            = timeSignature.show;
      this.commonTime      = timeSignature.commonTime;
      this.cutTime         = timeSignature.cutTime;
      this.beamingPattern  = new byte[timeSignature.beamingPattern.length];
      for(int i=0; i<timeSignature.beamingPattern.length; ++i)
         this.beamingPattern[i] = timeSignature.beamingPattern[i];
   }

   /**
    * @return the beats per measure (top half of the time signature).
    */
   public byte getBeatsPerMeasure()
   {
      return beatsPerMeasure;
   }

   /**
    * sets the number of beats per measure (top half of the time signature).
    * <br/><br/>
    * @param beats  the number of beats per measure.
    */
   public void setBeatsPerMeasure(byte beats)
   {
      beatsPerMeasure = beats;
      clearCommonCutTime();
   }

   /**
    * @return the beat amount (bottom half of the time signature).
    */
   public byte getBeatAmount()
   {
      return beatAmount;
   }

   /**
    * sets the amount each beat has (bottom half of the time signature).
    * <br/><br/>
    * @param amount  the amount each beat has.
    */
   public void setBeatAmount(byte amount)
   {
      beatAmount = amount;
      clearCommonCutTime();
   }

   /**
    * simultaneously sets the beats per measure and beat value.
    * <br/><br/>
    * @param beatsPerMeasure   the number of beats per measure.
    * @param beatValue         the amount each beat has.
    */
   public void setMeter(byte beatsPerMeasure, byte beatValue)
   {
      setBeatsPerMeasure(beatsPerMeasure);
      setBeatAmount     (beatValue);
   }

   /**
    * @return the eigth note beaming pattern.
    */
   public byte[] getBeamingPattern()
   {
      return beamingPattern;
   }

   /**
    * set the eigth note beaming pattern.
    * <br/><br/>
    * @param beamingPattern   the eigth note beaming pattern.
    */
   public void setBeamingPattern(byte[] beamingPattern)
   {
      this.beamingPattern = beamingPattern;
   }

   /**
    * @return whether the time signature is displayed.
    */
   public boolean isShown()
   {
      return show;
   }

   /**
    * @return whether the time signature is in common time (4/4) and uses the common time symbol.
    */
   public boolean isCommonTime()
   {
      return commonTime;
   }

   /**
    * sets the time signature to common time (4/4) and uses the common time symbol.
    */
   public void setCommonTime()
   {
      commonTime      = true;
      cutTime         = false;
      beatsPerMeasure = 4;
      beatAmount      = 4;
   }

   /**
    * @return whether the time signature is in cut time (2/2) and uses the cut time symbol.
    */
   public boolean isCutTime()
   {
      return cutTime;
   }

   /**
    * sets the time signature to cut time (2/2) and uses the cut time symbol.
    */
   public void setCutTime()
   {
      commonTime      = false;
      cutTime         = true;
      beatsPerMeasure = 2;
      beatAmount      = 2;
   }

   /**
    * clears the common time (4/4) and cut time (2/2) flags;
    */
   public void clearCommonCutTime()
   {
      commonTime = false;
      cutTime    = false;
   }

   /**
    *  @return the total time (in midi pulses) for the measure that uses the time signature.
    */
   public int getMidiPulses()
   {
      return ((Midi.PPQN * 4) / beatAmount) * beatsPerMeasure;
   }

   /**
    * @return whether two time signatures are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof TimeSignature)
      {
         TimeSignature timeSignature = (TimeSignature)object;
         equal = this.beatsPerMeasure == timeSignature.beatsPerMeasure &&
                 this.beatAmount      == timeSignature.beatAmount      &&
                 this.beamingPattern  == timeSignature.beamingPattern  &&
                 this.show            == timeSignature.show            &&
                 this.commonTime      == timeSignature.commonTime      &&
                 this.cutTime         == timeSignature.cutTime         &&
               ((this.beamingPattern == null && timeSignature.beamingPattern == null) ||
                (this.beamingPattern.length == timeSignature.beamingPattern.length));
         for(int i=0; i<beamingPattern.length && equal; ++i)
            equal = this.beamingPattern[i] == timeSignature.beamingPattern[i];
      }
      return equal;
   }


   /**
    * read in a time signature from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the time signature from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos               = file.getPosition();
         beatsPerMeasure   = (byte)file.read();
         beatAmount        = (byte)file.read();
         beamingPattern[0] = (byte)file.read();
         beamingPattern[1] = (byte)file.read();
         beamingPattern[2] = (byte)file.read();
         beamingPattern[3] = (byte)file.read();
         show              = file.readBoolean();
         commonTime        = file.readBoolean();
         cutTime           = file.readBoolean();

         if (commonTime)
         {
            beatsPerMeasure = 4;
            beatAmount      = 4;
         }
         if (cutTime)
         {
            beatsPerMeasure = 2;
            beatAmount      = 2;
         }
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.time_signature"));
      }
   }

   /**
    * save a time signature to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the time signature to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(beatsPerMeasure);
         file.write(beatAmount);
         file.write(beamingPattern[0]);
         file.write(beamingPattern[1]);
         file.write(beamingPattern[2]);
         file.write(beamingPattern[3]);
         file.writeBoolean(show      );
         file.writeBoolean(commonTime);
         file.writeBoolean(cutTime   );
      }
      catch (IOException ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.time_signature"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab time signature.
    */
   @Override
   public String toString()
   {
      return toString(18);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab time signature.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.time_signature") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.beats_per_measure"), indentation) + beatsPerMeasure + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.beat_amount"      ), indentation) + beatAmount      + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.beaming_pattern"  ), indentation) + beamingPattern[0] + " " + beamingPattern[1] + " " + beamingPattern[2] + " " +beamingPattern[3] + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.show"             ), indentation) + show            + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.common_time"      ), indentation) + commonTime      + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.cut_time"         ), indentation) + cutTime               );

      return buffer.toString();
   }
}
