/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.note;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a beaglebuddy tab bend note attribute.
 */
public class Bend extends NoteAttribute
{
   // enums
   public enum BendType     {Normal, BendAndRelease, BendAndHold, PreBend, PreBendAndRelease, PreBendAndHold, GradualRelease, ImmediateRelease}
   public enum DrawingPoint {LowPoint, MidPoint, HighPoint}

   // class members
   public static final int MAX_BEND_PITCH    = 12;
   public static final int MAX_BEND_DURATION = 9;    // number of notes (max == 8) + current note

   // data members
   private BendType     bendType;
   private byte         bentPitch;         // how many quarter steps the pitch is bent.
   private byte         releasePitch;      // how many quarter steps the pitch is released.
   private byte         duration;          // how many notes to hold the bend (0 =default, and occurs over a 32nd note. 1 = occurs over duration of the note. 2 = occurs over duration of the note + the next note, etc.)
   private DrawingPoint drawStartPoint;
   private DrawingPoint drawEndPoint;




   /**
    * default constructor.
    */
   public Bend()
   {
      this(BendType.Normal, (byte)1, (byte)0, (byte)0, DrawingPoint.LowPoint, DrawingPoint.HighPoint);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param bendType        type of bend.
    * @param bentPitch       how many quarter steps the pitch is bent.
    * @param releasePitch    how many quarter steps the pitch is released.
    * @param duration        how many notes to hold the bend.
    *                        0 =default, and occurs over a 32nd note.
    *                        1 = occurs over duration of the note.
    *                        2 = occurs over duration of the note + the next note, etc.
    * @param drawStartPoint  vertical draw start point for the bend.
    * @param drawEndPoint    vertical draw end   point for the bend.
    */
   public Bend(BendType bendType, byte bentPitch, byte releasePitch, byte duration, DrawingPoint drawStartPoint, DrawingPoint drawEndPoint)
   {
      super(NoteAttribute.Type.Bend);

      this.bendType = bendType;
      setBentPitch   (bentPitch);
      setReleasePitch(releasePitch);
      setDuration    (duration);
      this.drawStartPoint = drawStartPoint;
      this.drawEndPoint   = drawEndPoint;

      if (!isValid())
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.bend_settings", this));
   }

   /**
    * constructor.
    * <br/><br/>
    * @param bendType        type of bend.
    * @param bentPitch       how many quarter steps the pitch is bent.
    * @param releasePitch    how many quarter steps the pitch is released.
    * @param duration        how many notes to hold the bend.
    *                        0 =default, and occurs over a 32nd note.
    *                        1 = occurs over duration of the note.
    *                        2 = occurs over duration of the note + the next note, etc.
    * @param drawStartPoint  vertical draw start point for the bend.
    * @param drawEndPoint    vertical draw end   point for the bend.
    */
   public Bend(byte bendType, byte bentPitch, byte releasePitch, byte duration, byte drawStartPoint, byte drawEndPoint)
   {
      this(getBendType(bendType), bentPitch, releasePitch, duration, getDrawingPoint(drawStartPoint), getDrawingPoint(drawEndPoint));
   }

   /**
    * @return the type of bend.
    */
   public BendType getBendType()
   {
      return bendType;
   }

   /**
    * @param bendType  the integer bend type.
    * <br/><br/>
    * @return the bend type enum corresponding to the integer bend type.
    */
   public static BendType getBendType(int bendType)
   {
      for (BendType bt : BendType.values())
         if (bendType == bt.ordinal())
            return bt;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.note_attribute.bend"), bendType, BendType.ImmediateRelease.ordinal()));
   }

   /**
    * sets the type of bend.
    * <br/><br/>
    * @param bendType  the type of bend.
    */
   public void setBendType(BendType bendType)
   {
      this.bendType = bendType;
   }

   /**
    * @return how many quarter steps the pitch is bent.
    */
   public byte getBentPitch()
   {
      return bentPitch;
   }

   /**
    * sets how many quarter steps the pitch is bent.
    * <br/><br/>
    * @param bentPitch   how many quarter steps the pitch is bent.
    */
   public void setBentPitch(byte bentPitch)
   {
      if (bentPitch < 0 || bentPitch > MAX_BEND_PITCH)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.note_attribute.bend.bent_pitch"), bentPitch, 0, MAX_BEND_PITCH));
      this.bentPitch = bentPitch;
   }

   /**
    * @return how many quarter steps the pitch is released.
    */
   public byte getReleasePitch()
   {
      return releasePitch;
   }

   /**
    * sets how many quarter steps the pitch is released.
    * <br/><br/>
    * @param releasePitch   how many quarter steps the pitch is released.
    */
   public void setReleasePitch(byte releasePitch)
   {
      if (releasePitch < 0 || releasePitch > MAX_BEND_PITCH)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.note_attribute.bend.release_pitch"), releasePitch, 0, MAX_BEND_PITCH));
      this.releasePitch = releasePitch;
   }

   /**
    * @return how many notes to hold the bend.
    * 0 = default, and occurs over a 32nd note.                 <br/>
    * 1 = occurs over duration of the note.                     <br/>
    * 2 = occurs over duration of the note + the next note, etc.<br/>
    */
   public byte getDuration()
   {
      return duration;
   }

   /**
    * sets the number of additional notes to hold the bend for.
    * <br/><br/>
    * @param duration the number of additional notes to hold the bend for.
    */
   public void setDuration(byte duration)
   {
      if (duration < 0 || duration > MAX_BEND_DURATION)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.note_attribute.bend.duration"), duration, 0, MAX_BEND_DURATION));
      this.duration = duration;
   }

   /**
    * @return the vertical draw start point for the bend.
    */
   public DrawingPoint getDrawStartPoint()
   {
      return drawStartPoint;
   }

   /**
    * sets the vertical draw start point for the bend.
    * <br/><br/>
    * @param drawingPoint   the vertical draw start point for the bend.
    */
   public void setDrawStartPoint(DrawingPoint drawingPoint)
   {
      this.drawStartPoint = drawingPoint;
   }

   /**
    * @return the vertical draw end point for the bend.
    */
   public DrawingPoint getDrawEndPoint()
   {
      return drawEndPoint;
   }

   /**
    * sets the vertical draw end point for the bend.
    * <br/><br/>
    * @param drawingPoint   the vertical draw end point for the bend.
    */
   public void setDrawEndPoint(DrawingPoint drawingPoint)
   {
      this.drawEndPoint = drawingPoint;
   }

   /**
    * @return whether the bend is a valid bend.
    */
   private boolean isValid()
   {
      boolean valid = false;
      switch (bendType)
      {
         case Normal:
         case BendAndHold:
              // a) Bent pitch must be greater than zero
              // b) Release pitch must be zero
              // c) Duration may be any value
              // d) Draw start must be low or mid and end must be higher
              valid = bentPitch > 0 && releasePitch == 0 && (drawStartPoint == DrawingPoint.LowPoint || drawStartPoint == DrawingPoint.MidPoint) && drawEndPoint.ordinal() > drawStartPoint.ordinal();
         break;
         case BendAndRelease:
              // a) Bent pitch must be greater than zero
              // b) Release pitch must be less than bent pitch
              // c) Duration must be zero
              // d) Draw start must be low or mid and drawEndPoint must be low or mid
              valid = bentPitch > 0 && releasePitch < bentPitch && duration == 0 && (drawStartPoint == DrawingPoint.LowPoint || drawStartPoint == DrawingPoint.MidPoint) && (drawEndPoint == DrawingPoint.LowPoint || drawEndPoint == DrawingPoint.MidPoint);
         break;
         case PreBend:
         case PreBendAndHold:
              // a) bent pitch must be greater than zero
              // b) release pitch must be zero
              // c) duration must be zero
              // d) draw start must be low, and end must be higher
              valid = bentPitch > 0 && releasePitch == 0 && duration == 0 && drawStartPoint == DrawingPoint.LowPoint && drawEndPoint.ordinal() > drawStartPoint.ordinal();
         break;
         case PreBendAndRelease:
              // a) bent pitch must be greater than zero
              // b) release pitch must be less than bent pitch
              // c) duration must be zero
              // d) draw start and end must be low
              valid = bentPitch > 0 && releasePitch < bentPitch && duration == 0 && drawStartPoint == DrawingPoint.LowPoint && drawEndPoint == DrawingPoint.LowPoint;
         break;
         case GradualRelease:
              // a) bent pitch must be zero
              // b) release pitch must be standard to 2 3/4
              // c) duration must be > 0
              // d) draw start must be high or mid point, draw end must be lower
              valid = bentPitch == 0 && releasePitch <= 11 && duration > 0 && (drawStartPoint == DrawingPoint.MidPoint || drawStartPoint == DrawingPoint.HighPoint) && drawEndPoint.ordinal() < drawStartPoint.ordinal();
         break;
         case ImmediateRelease:
              // a) bent pitch must be zero
              // b) release pitch must be zero
              // c) duration must be zero
              // d) draw start must be high or mid, and match draw end
              valid = bentPitch == 0 && releasePitch == 0 && duration == 0 && (drawStartPoint == DrawingPoint.MidPoint || drawStartPoint == DrawingPoint.HighPoint) && drawStartPoint == drawEndPoint;
         break;
      }
      return valid;
   }

   /**
    * @param drawingPoint  the integer drawing point.
    * <br/><br/>
    * @return the drawing point enum corresponding to the integer drawing point.
    */
   public static DrawingPoint getDrawingPoint(int drawingPoint)
   {
      for (DrawingPoint dp : DrawingPoint.values())
         if (drawingPoint == dp.ordinal())
            return dp;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.note_attribute.bend.drawing_point"), drawingPoint, DrawingPoint.HighPoint.ordinal()));
   }

   /**
    * @return a new copy of the bend note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new Bend(this.bendType, this.bentPitch, this.releasePitch, this.duration, this.drawStartPoint, this.drawEndPoint);
   }

   /**
    * @param object  object to check for equality with this bend attribute.
    * <br/><br/>
    * @return if the two bend attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof Bend)
      {
         Bend bend = (Bend)object;
         result = this.bendType == bend.bendType && this.bentPitch == bend.bentPitch && this.releasePitch == bend.releasePitch && this.duration == bend.duration && this.drawStartPoint == bend.drawStartPoint && this.drawEndPoint == bend.drawEndPoint;
      }
      return result;
   }

   /**
    * @return that the bend note attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the bend note attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the bend note attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         bendType = getBendType(file.read());
         bentPitch      = (byte)file.read();
         releasePitch   = (byte)file.read();
         duration       = (byte)file.read();
         drawStartPoint = getDrawingPoint((byte)file.read());
         drawEndPoint   = getDrawingPoint((byte)file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.bend"));
      }
   }

   /**
    * save a bend note attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the bend note attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(bendType      .ordinal());
         file.write(bentPitch               );
         file.write(releasePitch            );
         file.write(duration                );
         file.write(drawStartPoint.ordinal());
         file.write(drawEndPoint  .ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.bend"));
      }
   }

   /**
    * @return a string representation of the bend.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append(getType().text() + " (");
      buffer.append(ResourceBundle.getString("text.bend.type"         ) + ": " + bendType       + ", ");
      buffer.append(ResourceBundle.getString("text.bend.pitch"        ) + ": " + bentPitch      + ", ");
      buffer.append(ResourceBundle.getString("text.bend.release_pitch") + ": " + releasePitch   + ", ");
      buffer.append(ResourceBundle.getString("text.duration"          ) + ": " + duration       + ", ");
      buffer.append(ResourceBundle.getString("text.draw_start_point"  ) + ": " + drawStartPoint + ", ");
      buffer.append(ResourceBundle.getString("text.draw_end_point"    ) + ": " + drawEndPoint   + ")" );

      return buffer.toString();
   }
}
