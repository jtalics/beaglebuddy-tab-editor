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
 * This class represents a vibrato chord attribute.
 */
public class Vibrato extends ChordAttribute
{
   // amount of vibrato to use when playing the note
   public enum Amount {Normal, Wide}

   // data members
   private Amount amount;




   /**
    * default constructor.
    */
   public Vibrato()
   {
      this(Amount.Normal);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param amount  the amount of vibrato to use when playing the note.
    */
   public Vibrato(Amount amount)
   {
      super(ChordAttribute.Type.Vibrato);

      this.amount = amount;
   }

   /**
    * @return the amount of vibrato to use when playing the note.
    */
   public Amount getAmount()
   {
      return amount;
   }

   /**
    * @param amount   the integer amount.
    * <br/><br/>
    * @return the amount enum corresponding to the integer amount.
    */
   public static Amount getAmount(int amount)
   {
      for (Amount a : Amount.values())
         if (amount == a.ordinal())
            return a;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.vibrato.amount"), amount, Amount.Wide.ordinal()));
   }

   /**
    * set the amount of vibrato to use when playing the note.
    * <br/><br/>
    * @param amount  the amount of vibrato to use when playing the note.
    */
   public void setAmount(Amount amount)
   {
      this.amount = amount;
   }

   /**
    * @return a new copy of the vibrato chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new Vibrato(amount);
   }

   /**
    * @param object  object to check for equality with this vibrato attribute.
    * <br/><br/>
    * @return if the two vibrato attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof Vibrato)
      {
         Vibrato vibrato = (Vibrato)object;
         result = this.amount == vibrato.amount;
      }
      return result;
   }

   /**
    * @return whether the vibrato chord attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the vibrato chord attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the vibrato chord attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         amount = getAmount(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.vibrato"));
      }
   }

   /**
    * save an vibrato chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the vibrato chord attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(amount.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.vibrato"));
      }
   }

   /**
    * @return a string representation of the vibrato.
    */
   @Override
   public String toString()
   {
      return getType().text() + " " + (amount == Amount.Normal ? ResourceBundle.getString("text.normal") : ResourceBundle.getString("text.wide"));
   }
}
