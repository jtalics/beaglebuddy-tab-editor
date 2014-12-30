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
 * This class represents a beaglebuddy tab rehearsal sign and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class RehearsalSign
{
   // data members
   private char   letter;         // the letter used to uniquely identify the rehearsal sign (i.e. A, B, F, etc. - must be a capital letter)
   private String description;    // a description that indicates the passage the rehearsal sign is marking (i.e. Chorus, Intro, etc.)




   /**
    * default constructor.
    */
   public RehearsalSign()
   {
      letter = 'A';
   }

   /**
    * constructor.
    * <br/><br/>
    * @param letter        the letter used to uniquely identify the rehearsal sign (i.e. A, B, F, etc. - must be a capital letter).
    * @param description   a description that indicates the passage the rehearsal sign is marking (i.e. Chorus, Intro, etc.).
    */
   public RehearsalSign(char letter, String description)
   {
      this.letter      = letter;
      this.description = description;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param rehearsalSign   the rehearsal sign whose values will be deep copied.
    */
   public RehearsalSign(RehearsalSign rehearsalSign)
   {
      this();

      if (rehearsalSign != null)
      {
         this.letter      = rehearsalSign.letter;
         this.description = rehearsalSign.description == null ? null : new String(rehearsalSign.description);
      }
   }

   /**
    * @return the unique letter identifying the rehearsal sign.
    */
   public char getLetter()
   {
      return letter;
   }

   /**
    * @return the description.
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * @return whether two rehearsal signs are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof RehearsalSign)
      {
         RehearsalSign rehearsalSign = (RehearsalSign)object;
         equal = this.letter == rehearsalSign.letter &&
               ((this.description == null && rehearsalSign.description == null) ||
                (this.description != null && rehearsalSign.description != null && this.description.equals(rehearsalSign.description)));
      }
      return equal;
   }

   /**
    * read in a rehearsal sign from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the rehearsal sign from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos         = file.getPosition();
         letter      = file.readCharacter();
         description = file.readString();

         if (letter < 'A' || letter > 'Z')
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", "text.rehearsal_sign.letter", letter, 'A', 'Z'));
         if (description == null || description.length() == 0)
            throw new IllegalArgumentException("error.invalid.rehearsal_sign_description");
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.rehearsal_sign"));
      }
   }

   /**
    * save a rehearsal sign to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the rehearsal sign to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();

         file.writeCharacter(letter     );
         file.writeString   (description);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.rehearsal_sign"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab rehearsal sign.
    */
   @Override
   public String toString()
   {
      return toString(18);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab rehearsal sign.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.rehearsal_sign") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.letter"     ), indentation) + letter + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.description"), indentation) + description);

      return buffer.toString();
   }
}
