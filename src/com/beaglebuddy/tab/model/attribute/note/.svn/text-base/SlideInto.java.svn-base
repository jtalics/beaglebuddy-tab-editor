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
 * This class represents a beaglebuddy tab slide into note attribute.
 */
public class SlideInto extends NoteAttribute
{
   // enums
   public enum SlideType
   {
      None                ,
      FromBelow           ,
      FromAbove           ,
      ShiftSlideUpwards   ,     // used for wrapping shift  slides around sections
      ShiftSlideDownwards ,     // used for wrapping shift  slides around sections
      LegatoSlideUpwards  ,     // used for wrapping legato slides around sections
      LegatoSlideDownwards      // used for wrapping legato slides around sections
   }

   // data members
   private SlideType slideType;            // type of slide into




   /**
    * default constructor.
    */
   public SlideInto()
   {
      super(NoteAttribute.Type.SlideInto);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param slideType   type of slide into.
    */
   public SlideInto(SlideType slideType)
   {
      super(NoteAttribute.Type.SlideInto);

      this.slideType = slideType;
   }

   /**
    * @return the type of the slide into.
    */
   public SlideType getSlideType()
   {
      return slideType;
   }

   /**
    * @param slideType  the integer slide into type.
    * <br/><br/>
    * @return the slide into type enum corresponding to the integer slide into type.
    */
   public static SlideType getSlideType(int slideType)
   {
      for (SlideType st : SlideType.values())
         if (slideType == st.ordinal())
            return st;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.note_attribute.slide_into"), slideType, SlideType.LegatoSlideDownwards.ordinal()));
   }

   /**
    * sets the type of the slide into.
    * <br/><br/>
    * @param type   the type of the slide into.
    */
   public void setSlideType(SlideType type)
   {
      this.slideType = type;
   }

   /**
    * @return a new copy of the slide into note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new SlideInto(this.slideType);
   }

   /**
    * @param object  object to check for equality with this slide into attribute.
    * <br/><br/>
    * @return if the two slide into attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof SlideInto && this.slideType == ((SlideInto)object).slideType);
   }

   /**
    * @return that the slide into note attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the slide into note attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the slide into note attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         slideType = getSlideType(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.slide_into"));
      }
   }

   /**
    * save a slide into note attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the slide into note attribute to the beaglebuddy tab file.
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
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.slide_into"));
      }
   }

   /**
    * @return a string representation of the slide into.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" + ResourceBundle.getString("text.type") + ": " + slideType + ")";
   }
}
