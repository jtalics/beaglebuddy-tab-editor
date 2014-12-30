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
import com.beaglebuddy.tab.model.staff.RhythmStaff;
import com.beaglebuddy.tab.model.staff.Staff;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Rectangle;




/**
 * This class represents a beaglebuddy tab section and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * This class contains the methods for laying out and drawing a section.
 * <p>
 * A section, as defined in the beaglebuddy tab editor, is a group of staffs.
 * </p>
 */
public class Section extends SectionBase
{
   // data members
   private Rectangle boundingRectangle;            // bounding rectangle for the section
   private short     positionSpacing;              // spacing between each duration in the section
   private short     rhythmSlashSpacingAbove;      // spacing above the rhythm slashes
   private short     rhythmSlashSpacingBelow;      // spacing below the rhythm slashes
   private short     extraSpacing;                 // extra spacing used within the section (for rehearsal signs and tempo markers)




   /**
    * default constructor.
    */
   public Section()
   {
      boundingRectangle = new Rectangle();
   }

   /**
    * constructor.
    * <br/><br/>
    * @param boundingRectangle         bounding rectangle for the section.
    * @param positionSpacing           spacing between each duration in the section.
    * @param rhythmSlashSpacingAbove   spacing above the rhythm slashes.
    * @param rhythmSlashSpacingBelow   spacing below the rhythm slashes.
    * @param extraSpacing              extra spacing used within the section (for rehearsal signs and tempo markers).
    * @param rhythmStaff               rhythm staff for the section.
    * @param staffs                    list of tab staves used within the section.
    * @param barlines                  list of barlines (including start and end bars) used within the section.
    */
   public Section(Rectangle boundingRectangle, short positionSpacing, short rhythmSlashSpacingAbove, short rhythmSlashSpacingBelow, short extraSpacing, RhythmStaff rhythmStaff, Staff[] staffs, Barline[] barlines)
   {
      super(rhythmStaff, staffs, barlines);

      this.boundingRectangle       = boundingRectangle;
      this.positionSpacing         = positionSpacing;
      this.rhythmSlashSpacingAbove = rhythmSlashSpacingAbove;
      this.rhythmSlashSpacingBelow = rhythmSlashSpacingBelow;
      this.extraSpacing            = extraSpacing;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param section   section whose values will be deep copied.
    */
   public Section(Section section)
   {
      super(section);

      if (section != null)
      {
         this.boundingRectangle       = section.boundingRectangle == null ? new Rectangle() : new Rectangle(section.boundingRectangle);
         this.positionSpacing         = section.positionSpacing;
         this.rhythmSlashSpacingAbove = section.rhythmSlashSpacingAbove;
         this.rhythmSlashSpacingBelow = section.rhythmSlashSpacingBelow;
         this.extraSpacing            = section.extraSpacing;
      }
   }

   /**
    * @return the bounding rectangle for the section.
    */
   public Rectangle getBoundingRectangle()
   {
      return boundingRectangle;
   }

   /**
    * sets the bounding rectangle for the section.
    * <br/><br/>
    * @param boundingRectangle   the bounding rectangle for the section.
    */
   public void setBoundingRectangle(Rectangle boundingRectangle)
   {
      this.boundingRectangle = boundingRectangle;
   }

   /**
    * @return the spacing between each drawing position in the section.
    */
   public short getPositionSpacing()
   {
      return positionSpacing;
   }

   /**
    * sets the spacing between each drawing position in the section.
    * <br/><br/>
    * @param spacing   the spacing between each drawing position in the section.
    */
   public void setPositionSpacing(short spacing)
   {
      this.positionSpacing = spacing;
   }

   /**
    * @return the spacing above the rhythm slashes.
    */
   public short getRhythmSlashSpacingAbove()
   {
      return rhythmSlashSpacingAbove;
   }

   /**
    * sets the spacing above the rhythm slashes.
    * <br/><br/>
    * @param spacing  the spacing above the rhythm slashes.
    */
   public void setRhythmSlashSpacingAbove(short spacing)
   {
      this.rhythmSlashSpacingAbove = spacing;
   }

   /**
    * @return the spacing below the rhythm slashes.
    */
   public short getRhythmSlashSpacingBelow()
   {
      return rhythmSlashSpacingBelow;
   }

   /**
    * sets the spacing below the rhythm slashes.
    * <br/><br/>
    * @param spacing  the spacing below the rhythm slashes.
    */
   public void setRhythmSlashSpacingBelow(short spacing)
   {
      this.rhythmSlashSpacingBelow = spacing;
   }

   /**
    * @return the extra spacing in the section (for rehearsal signs + tempo markers).
    */
   public short getExtraSpacing()
   {
      return extraSpacing;
   }

   /**
    * sets the extra spacing in the section (for rehearsal signs + tempo markers).
    * <br/><br/>
    * @param spacing   the extra spacing in the section.
    */
   public void setExtraSpacing(short spacing)
   {
      this.extraSpacing = spacing;
   }

   /**
    * @return the total number of drawing positions for this section.
    */
   public byte getNumDrawingPositions()
   {
      return getEndBarline().getPosition();
   }

   /**
    * @return whether the chords specified by the "i" array are past the measure specified by the barline index for the melody line.
    * <br/><br/>
    * @param i                       array containg the index of the chord for each melody line.
    * @param lastDurationInMeasure   array containing the last duration's position in each measure for each melody line.
    * @param barline                 the index of the beginning barline of the measure.
    * @param numMelodyLines          the number of non-empty melody lines in this section.
    */
   private boolean isEndOfMeasure(int[] i, byte[][] lastDurationInMeasure, int barline, int numMelodyLines)
   {
      // for each melody line, determine if the end of measure has been reached for the current measure
      boolean[] endOfMeasure = new boolean[numMelodyLines];
      for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
         endOfMeasure[melodyLine] = i[melodyLine] > lastDurationInMeasure[melodyLine][barline];

      // determine if the end of measure has been reached for all melody lines
      boolean allEndOfMeasure = true;
      for(int melodyLine=0; melodyLine<numMelodyLines && allEndOfMeasure; ++melodyLine)
         allEndOfMeasure = endOfMeasure[melodyLine];

      return allEndOfMeasure;
   }

   /**
    * @return   the number of drawing positions that a duration of the number of specified pulses should occupy.
    * <br/><br/>
    * @param pulses   the number of midi pulses in the duration
    */
   private static byte getDrawingIncrement(long pulses)
   {
      byte increment = 1;

           if (pulses < Midi.Duration.NOTE_QUARTER.pulses())           // quarter note
         increment = 1;
      else if (pulses < Midi.Duration.NOTE_QUARTER.pulses() * 3 / 2)   // dotted quarter note
         increment = 2;
      else if (pulses < Midi.Duration.NOTE_HALF   .pulses()        )   // half  note
         increment = 3;
      else if (pulses < Midi.Duration.NOTE_HALF   .pulses() * 3 / 2)   // dotted half note
         increment = 4;
      else if (pulses < Midi.Duration.NOTE_HALF   .pulses() * 7 / 4)   // double dotted half note
         increment = 6;
      else if (pulses < Midi.Duration.NOTE_WHOLE  .pulses()        )   // whole note
         increment = 7;
      else if (pulses < Midi.Duration.NOTE_WHOLE  .pulses() * 3 / 2)   // dotted whole note
         increment = 8;
      else if (pulses < Midi.Duration.NOTE_WHOLE  .pulses() * 7 / 4)   // double dotted whole note
         increment = 12;
      else
         increment = 14;

      return increment;
   }

   /**
    * formats the song, setting the drawing positions of all the chords and rhythm slashes as well as setting the spacing between them.
    * This method is analagous to a java swing layout manager.  It determines how many drawing positions are needed to layout the section
    * and where each duration (note, chord, rest, multi bar rest, or rhythm slash) should be placed.
    * <p>
    * This method will work even on measures that are invalid, ie, have more or less notes than their time signature specifies.
    * For this reason, there are a number of lines of code that contain the following check:
    * <code>&& i[melodyLine] < durations[melodyLine].length<code>
    * </p>
    */
   public void layout()
   {
      // get some objects as native arrays instead of ArrayLists
      Barline    [] barlines      = new Barline    [getBarlines                                           ().size()];
      Staff      [] staffs        = new Staff      [getStaffs                                             ().size()];
      RhythmSlash[] rhythmSlashes = new RhythmSlash[rhythmStaff == null ? 0 : rhythmStaff.getRhythmSlashes().size()];

      getBarlines().toArray(barlines);
      getStaffs  ().toArray(staffs  );
      if (rhythmStaff != null)
         rhythmStaff.getRhythmSlashes().toArray(rhythmSlashes);

      // for each staff, determine which melody voice lines are not empty (ie, have chords)
      int numMelodyLines = 0;
      for(int staff=0; staff<staffs.length; ++staff)
         for(int voice=Staff.LowVoice; voice<Staff.NumVoices; ++voice)
            if (staffs[staff].getChords(voice).size() != 0)
               numMelodyLines++;
      // add another if there are rhythm slashes
      if (rhythmSlashes.length != 0)
         numMelodyLines++;

      // create some variables based on the number of non-empty melody lines found
      Duration[][] durations             = new Duration[numMelodyLines][];                  // the list of durations for each non-empty melody line in the section
      int     []   i                     = new int     [numMelodyLines];                    // the current index within each melody line
      long    []   melodyMidiTime        = new long    [numMelodyLines];                    // the current midi time in each melody line
      long    []   barlineMidiTime       = new long    [barlines.length];                   // the midi time at each bar line
      long         midiTime              = 0L;                                              // the midi time at the current drawing position
      byte         drawingPosition       = 1;                                               // the current drawing position within the section
      byte    [][] lastDurationInMeasure = new byte [numMelodyLines][barlines.length-1];    // the last position in each measure for each melody line.

      // calculate the midi time at each barline, except for the last one (end of staff bar line)
      for(int barline=1; barline<barlines.length-1; ++barline)
         barlineMidiTime[barline] = barlineMidiTime[barline-1] + barlines[barline-1].getTimeSignature().getMidiPulses();

      // store a reference to each non-empty melody line in an array so that they are easier to work with
      for(int melodyLine=0, staff=0; staff<staffs.length; ++staff)
      {
         for(int voice=Staff.LowVoice; voice<Staff.NumVoices; ++voice)
         {
            if (staffs[staff].getChords(voice).size() != 0)
            {
               Duration[] array = new Duration[staffs[staff].getChords(voice).size()];
               durations[melodyLine++] = staffs[staff].getChords(voice).toArray(array);
            }
         }
      }
      // if there are any rhythm slashes, store them in the last row of the array
      if (rhythmSlashes.length != 0)
         durations[numMelodyLines-1] = rhythmSlashes;

      // for each melody line, determine which durations are in each measure
      for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
      {
         for(int barline=0; barline<barlines.length-1; ++barline)
         {
            lastDurationInMeasure[melodyLine][barline]=(byte)-1;
            for(int duration=0; duration<durations[melodyLine].length; ++duration)
            {
               if (durations[melodyLine][duration].getPosition() >= barlines[barline    ].getPosition() &&
                   durations[melodyLine][duration].getPosition() <  barlines[barline + 1].getPosition())
                  lastDurationInMeasure[melodyLine][barline] = (byte)duration;
            }
         }
      }

      // iterate over each measure (demarcated by bar lines)
      for (int barline=0; barline<(barlines.length-1); ++barline)
      {
         // set the current midi time and the midi time of each melody line
         midiTime = barlineMidiTime[barline];
         for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
            melodyMidiTime[melodyLine] = barlineMidiTime[barline];

         // if this measure doesn't have any durations in it, then increment the midi time by the time signature
         for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
         {
            if (lastDurationInMeasure[melodyLine][barline] == -1)
               melodyMidiTime[melodyLine] += barlines[barline].getTimeSignature().getMidiPulses();
         }

         // only consider those durations that are defined in the current measure
         while (!isEndOfMeasure(i, lastDurationInMeasure, barline, numMelodyLines))
         {
            // for each melody line, set the drawing positions for the current drawing position
            for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
            {
               if (i[melodyLine] < durations[melodyLine].length && lastDurationInMeasure[melodyLine][barline] != -1 && i[melodyLine] <= lastDurationInMeasure[melodyLine][barline] && melodyMidiTime[melodyLine] == midiTime)
                  durations[melodyLine][i[melodyLine]].setPosition(drawingPosition);
            }

            // within all the melody lines, find the smallest duration for the durations at the current drawing position in the measure
            long smallestPulses = Midi.Duration.NOTE_WHOLE.pulses() * 2;
            for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
            {
               if (i[melodyLine] < durations[melodyLine].length && lastDurationInMeasure[melodyLine][barline] != -1 && i[melodyLine] <= lastDurationInMeasure[melodyLine][barline] && melodyMidiTime[melodyLine] == midiTime &&
                   durations[melodyLine][i[melodyLine]].getMidiPulses() < smallestPulses)
               {
                  if (durations[melodyLine][i[melodyLine]].isMultiBarRest())
                     smallestPulses = barlines[barline].getTimeSignature().getMidiPulses();
                  else
                     smallestPulses = durations[melodyLine][i[melodyLine]].getMidiPulses();
               }
            }
            // see if there were any previous durations whose end duration falls in between the current midi time and the next midi time
            long nextMidiTime = midiTime + smallestPulses;
            for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
            {
               if (melodyMidiTime[melodyLine] > midiTime && melodyMidiTime[melodyLine] < nextMidiTime)
                  smallestPulses = melodyMidiTime[melodyLine] - midiTime;
            }

            // for each melody line, set the midi time for the current chord in the measure
            for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
            {
               if (i[melodyLine] < durations[melodyLine].length && i[melodyLine] <= lastDurationInMeasure[melodyLine][barline] && melodyMidiTime[melodyLine] == midiTime)
               {
                  if (durations[melodyLine][i[melodyLine]].isMultiBarRest())
                     melodyMidiTime[melodyLine] += barlines[barline].getTimeSignature().getMidiPulses();
                  else
                     melodyMidiTime[melodyLine] += durations[melodyLine][i[melodyLine]].getMidiPulses();
               }
            }

            // increment the measure midi time
            midiTime += smallestPulses;

            // for each melody line, increment the chord positions (indexes)
            for(int melodyLine=0; melodyLine<numMelodyLines; ++melodyLine)
            {
               if (lastDurationInMeasure[melodyLine][barline] != -1 && i[melodyLine] <= lastDurationInMeasure[melodyLine][barline] && melodyMidiTime[melodyLine] <= midiTime)
                  i[melodyLine]++;
            }

            // set the next drawing position in the measure based on the current smallest duration
            drawingPosition += getDrawingIncrement(smallestPulses);
         }

         // set the drawing position of the measure's ending barline
         barlines[barline + 1].setPosition(drawingPosition);

         // increment the drawing position for the measure's ending barline
         drawingPosition++;
      }
      barlines[barlines.length - 1].setPosition((byte)(drawingPosition - 1));
   }

   /**
    * @return whether two sections are equal.
    * <br/><br/>
    * @param object  the object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Section)
      {
         Section section = (Section)object;
         equal = super.equals(section) && this.boundingRectangle.equals(section.boundingRectangle) && this.positionSpacing == section.positionSpacing &&
                 this.rhythmSlashSpacingAbove == section.rhythmSlashSpacingAbove && this.rhythmSlashSpacingBelow == section.rhythmSlashSpacingBelow &&
                 this.extraSpacing == section.extraSpacing;
      }
      return equal;
   }

   /**
    * read in a section object from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the section object from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      file.incrementSection();

      long pos = -1L;
      try
      {
         pos = file.getPosition();
         boundingRectangle       = file.readRectangle();
         positionSpacing         = file.readShort();
         rhythmSlashSpacingAbove = file.readShort();
         rhythmSlashSpacingBelow = file.readShort();
         extraSpacing            = file.readShort();
         super.load(file);
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.section"));
      }
   }

   /**
    * save a section to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the section to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      file.incrementSection();

      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeRectangle(boundingRectangle      );
         file.writeShort    (positionSpacing        );
         file.writeShort    (rhythmSlashSpacingAbove);
         file.writeShort    (rhythmSlashSpacingBelow);
         file.writeShort    (extraSpacing           );
         super.save(file);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.section"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab section object.
    */
   @Override
   public String toString()
   {
      return toString(9);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab section object.
    */
   @Override
  public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.section") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.bounding_rectangle"        ), indentation) + boundingRectangle        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.position_spacing"          ), indentation) + positionSpacing          + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.rhythm_slash_spacing_above"), indentation) + rhythmSlashSpacingAbove  + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.rhythm_slash_spacing_below"), indentation) + rhythmSlashSpacingBelow  + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.extra_spacing"             ), indentation) + extraSpacing             + "\n");
      buffer.append(super.toString(numSpacesToIndent));

      return buffer.toString();
   }
}
