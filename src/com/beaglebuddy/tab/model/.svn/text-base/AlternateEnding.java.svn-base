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
 * This class represents a beaglebuddy tab alternate ending and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * An alternate ending looks like the following:
 *  --------------------     ------------------
 * | 1,2,3                  | 4
 */
public class AlternateEnding
{
   // class members
   public static final int MIN_NUMBER = 1;    // repeat ending numbers supported
   public static final int MAX_NUMBER = 8;

   // data members
   private byte[] numbers;                    // repeat ending numbers




   /**
    * default constructor.
    */
   public AlternateEnding()
   {
      numbers = new byte[0];
   }

   /**
    * constructor.
    * <br/><br/>
    * @param numbers   repeat ending numbers.
    */
   public AlternateEnding(byte[] numbers)
   {
      setNumbers(numbers);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param alternateEnding   alternate ending whose values will be deep copied.
    */
   public AlternateEnding(AlternateEnding alternateEnding)
   {
      if (alternateEnding == null || alternateEnding.numbers == null)
      {
         numbers = new byte[0];
      }
      else
      {
         numbers = new byte[alternateEnding.numbers.length];
         for(int i=0; i<numbers.length; ++i)
            numbers[i] = alternateEnding.numbers[i];
      }
   }

   /**
    * @return the numbers for the alternate ending.
    */
   public byte[] getNumbers()
   {
      return numbers;
   }

   /**
    * @return the numbers for the alternate ending as a string with the numbers separated by commas.
    */
   public String getNumbersAsString()
   {
      StringBuffer buffer = new StringBuffer(" ");

      for(int i=0; i<numbers.length; ++i)
         buffer.append((buffer.length() > 1 ? "," : "") + numbers[i] + ".");

      return buffer.toString();
   }

   /**
    * sets the alternate ending numbers.
    * <br/><br/>
    * @param numbers   the numbers for the alternate ending.
    */
   public void setNumbers(byte[] numbers)
   {
      if (numbers == null)
         numbers = new byte[0];
      if (numbers.length > MAX_NUMBER)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.alternate_ending.num_numbers"), numbers.length, 0, MAX_NUMBER));
      for(byte number : numbers)
      {
         if (number < MIN_NUMBER || number > MAX_NUMBER)
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.alternate_ending.number"), numbers.length, MIN_NUMBER, MAX_NUMBER));
      }
      this.numbers = numbers;
   }

   /**
    * @return whether two alternate endings are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof AlternateEnding)
      {
         AlternateEnding alternateEnding = (AlternateEnding)object;
         // are the repeat ending numbers the same?
         equal = numbers.length == alternateEnding.numbers.length;
         for(int i=0; i<numbers.length && equal; ++i)
            equal = numbers[i] == alternateEnding.numbers[i];
      }
      return equal;
   }

   /**
    * read in an alternate ending from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the alternate ending from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         byte num = (byte)file.read();
         numbers = new byte[num];
         for(int i=0; i<num; ++i)
            numbers[i] = (byte)file.read();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.alternate_ending"));
      }
   }

   /**
    * save an alternate ending to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the alternate ending to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(numbers.length);
         for(byte number : numbers)
            file.write(number);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.alternate_ending"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab alternate ending.
    */
   @Override
   public String toString()
   {
      return toString(18);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab alternate ending.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(ResourceBundle.getString("data_type.alternate_ending") + "\n");
                                          buffer.append(Utility.pad(ResourceBundle.getString("text.numbers")                , indentation1) + numbers.length + numbers.length + (0 == numbers.length    ? "" : "\n"));
      for(int i=0; i<numbers.length; ++i) buffer.append(Utility.pad(ResourceBundle.getString("text.numbers") + "[" + i + "]", indentation2) + numbers[i]     + numbers[i]     + (i == numbers.length -1 ? "" : "\n"));
      return buffer.toString();
   }
}
