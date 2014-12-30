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
 * This class represents a beaglebuddy tab slide out note attribute.
 */
public class SlideOutOf extends NoteAttribute
{
   // enums
   public enum SlideType {None, ShiftSlide, LegatoSlide, Downwards, Upwards}

   // data members
   private SlideType slideType;       // type of slide out of
   private byte      numHalfSteps;    // number of half steps to slide out



   /**
    * default constructor.
    */
   public SlideOutOf()
   {
      super(NoteAttribute.Type.SlideOutOf);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param slideType     type of slide out of.
    * @param numHalfSteps  number of half steps to slide out.
    */
   public SlideOutOf(SlideType slideType, byte numHalfSteps)
   {
      super(NoteAttribute.Type.SlideOutOf);

      this.slideType    = slideType;
      this.numHalfSteps = numHalfSteps;
   }

   /**
    * @return the type of the slide out.
    */
   public SlideType getSlideType()
   {
      return slideType;
   }

   /**
    * @param slideType  the integer slide out type.
    * <br/><br/>
    * @return the slide out type enum corresponding to the integer slide out type.
    */
   public static SlideType getSlideType(int slideType)
   {
      for (SlideType st : SlideType.values())
         if (slideType == st.ordinal())
            return st;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.note_attribute.slide_out_of"), slideType, SlideType.Upwards.ordinal()));
   }

   /**
    * sets the type of the slide out of.
    * <br/><br/>
    * @param type   the type of the slide out of.
    */
   public void setSlideType(SlideType type)
   {
      this.slideType = type;
   }

   /**
    * @return the number of half steps to slide out.
    */
   public byte getNumHalfSteps()
   {
      return numHalfSteps;
   }

   /**
    * sets the number of half steps to slide out.
    * <br/><br/>
    * @param numHalfSteps   the number of half steps to slide out.
    */
   public void setNumHalfSteps(byte numHalfSteps)
   {
      this.numHalfSteps = numHalfSteps;
   }

   /**
    * @return a new copy of the slide out of note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new SlideOutOf(this.slideType, this.numHalfSteps);
   }

   /**
    * @param object  object to check for equality with this slide out of attribute.
    * <br/><br/>
    * @return if the two slide out of attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof SlideOutOf && this.slideType == ((SlideOutOf)object).slideType && this.numHalfSteps == ((SlideOutOf)object).numHalfSteps);
   }

   /**
    * @return that the slide out note attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the slide out of note attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the slide out of note attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         slideType    = getSlideType(file.read());
         numHalfSteps = (byte)file.read();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.slide_out_of"));
      }
   }

   /**
    * save a slide out of note attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the slide out of note attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(slideType.ordinal());
         file.write(numHalfSteps);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.slide_out_of"));
      }
   }

   /**
    * @return a string representation of the slide out.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" + ResourceBundle.getString("text.type") + ": " + slideType + ", " + ResourceBundle.getString("text.num_half_steps") + ": " + numHalfSteps + ")";
   }
}
