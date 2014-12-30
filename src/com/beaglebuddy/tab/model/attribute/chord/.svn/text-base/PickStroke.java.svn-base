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
 * This class represents a pick stroke chord attribute.
 */
public class PickStroke extends ChordAttribute
{
   // direction in which to pick
   public enum Direction {Down, Up}

   // data members
   private Direction direction;



   /**
    * default constructor.
    */
   public PickStroke()
   {
      this(Direction.Up);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param direction   direction in which to pick the note.
    */
   public PickStroke(Direction direction)
   {
      super(ChordAttribute.Type.PickStroke);

      this.direction = direction;
   }

   /**
    * @return the direction in which the chord will be played.
    */
   public Direction getDirection()
   {
      return direction;
   }

   /**
    * @param direction   the integer direction.
    * <br/><br/>
    * @return the direction enum corresponding to the integer direction.
    */
   public static Direction getDirection(int direction)
   {
      for (Direction d : Direction.values())
         if (direction == d.ordinal())
            return d;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.chord_attribute.pickstroke.direction"), direction, Direction.Up.ordinal()));
   }

   /**
    * set the direction in which the chord will be played.
    * <br/><br/>
    * @param direction   direction in which to play the chord.
    */
   public void setDirection(Direction direction)
   {
      this.direction = direction;
   }

   /**
    * @return a new copy of the pick stroke chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new PickStroke(direction);
   }

   /**
    * @param object  object to check for equality with this pick stroke attribute.
    * <br/><br/>
    * @return if the two pick stroke attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof PickStroke)
      {
         PickStroke pickstroke = (PickStroke)object;
         result = this.direction == pickstroke.direction;
      }
      return result;
   }

   /**
    * @return whether the pick stroke chord attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the pick stroke chord attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the pick stroke chord attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         direction = getDirection(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.pickstroke"));
      }
   }

   /**
    * save an pick stroke chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the pick stroke chord attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(direction.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.pickstroke"));
      }
   }

   /**
    * @return a string representation of the pick stroke.
    */
   @Override
   public String toString()
   {
      return getType().text() + " " + (direction == Direction.Down ? ResourceBundle.getString("text.down") : ResourceBundle.getString("text.up"));
   }
}
