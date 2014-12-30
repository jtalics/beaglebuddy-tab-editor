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
 * This class represents a bass fingering chord attribute.
 */
public class FingerBass extends ChordAttribute
{
   // enums
   public enum Finger
   {
      Slap("S"), Pop("P");

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
   public FingerBass()
   {
      super(ChordAttribute.Type.FingerBass);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param finger   bass finger.
    */
   public FingerBass(Finger finger)
   {
      super(ChordAttribute.Type.FingerBass);
      this.finger = finger;
   }

   /**
    * @return the bass finger.
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
   private static Finger getFinger(int finger)
   {
      for (Finger f : Finger.values())
         if (finger == f.ordinal())
            return f;
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.chord_attribute.bass_finger.finger"), finger, 0, Finger.Pop.ordinal()));
   }

   /**
    * @return a new copy of the bass finger chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new FingerBass(this.finger);
   }

   /**
    * @param object  object to check for equality with this bass finger chord attribute.
    * <br/><br/>
    * @return if the two bass finger chord attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof FingerBass)
      {
         FingerBass fingerBass = (FingerBass)object;
         result = this.finger == fingerBass.finger;
      }
      return result;
   }

   /**
    * @return that the bass finger chord attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the bass finger chord attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the bass finger chord attribute can not be read from the beaglebuddy tab file.
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
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), "bass finger chord attribute");
      }
   }

   /**
    * save a bass finger chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the bass finger chord attribute to the beaglebuddy tab file.
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
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), "bass finger chord attribute");
      }
   }

   /**
    * @return a string representation of the bass fingering.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("type: " + getType().text() + ", (finger: " + finger + ")");

      return buffer.toString();
   }
}
