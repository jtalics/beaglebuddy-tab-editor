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
 * This class represents a fret hand fingering chord attribute.
 */
public class FingerFretHand extends ChordAttribute
{
   // enums
   public enum Finger
   {
      Thumb("Th"), One("1"), Two("2"), Three("3"), Four("4");

      // data members
      private String text;
      Finger(String text)      {this.text = text;}
      public String text()     {return text;}
      @Override
      public String toString() {return text;}
   }

   // data members
   private Finger finger;




   /**
    * default constructor.
    */
   public FingerFretHand()
   {
      super(ChordAttribute.Type.FingerFretHand);
   }

   /**
    * constructor.
    */
   public FingerFretHand(Finger finger)
   {
      super(ChordAttribute.Type.FingerFretHand);

      this.finger = finger;
   }

   /**
    * @return the fret hand finger.
    */
   public Finger getFinger()
   {
      return finger;
   }

   /**
    * @param finger  the integer finger.
    * <br/><br/>
    * @return the finger enum corresponding to the integer finger.
    */
   public static Finger getFinger(int finger)
   {
      for (Finger f : Finger.values())
         if (finger == f.ordinal())
            return f;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.chord_attribute.fret_hand_finger"), finger, Finger.Four.ordinal()));
   }

   /**
    * @param finger  the text finger.
    * <br/><br/>
    * @return the finger enum corresponding to the text finger.
    */
   public static Finger getFinger(String finger)
   {
      for (Finger f : Finger.values())
         if (f.text().equals(finger))
            return f;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.chord_attribute.fret_hand_finger"), finger, Finger.Four.ordinal()));
   }

   /**
    * @return a new copy of the fret hand finger chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new FingerFretHand(this.finger);
   }

   /**
    * @param object  object to check for equality with this fret hand finger chord attribute.
    * <br/><br/>
    * @return if the two fret hand finger chord attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof FingerFretHand)
      {
         FingerFretHand fingerFretHand = (FingerFretHand)object;
         result = this.finger == fingerFretHand.finger;
      }
      return result;
   }

   /**
    * @return that the fret hand finger chord attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the fret hand finger chord attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the fret hand finger chord attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos    = file.getPosition();
         finger = getFinger(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.fret_hand_finger"));
      }
   }

   /**
    * save a fret hand finger chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the fret hand finger chord attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(finger.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.fret_hand_finger"));
      }
   }

   /**
    * @return a string representation of the fret hand fingering.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append(getType().text() + " (finger: " + finger + ")");

      return buffer.toString();
   }
}
