/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.duration;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a duration attribute and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class DurationAttribute
{
   // enums
   public enum Type
   {
      Dotted           (ResourceBundle.getString("duration_attribute_dotted"            )),
      DottedDouble     (ResourceBundle.getString("duration_attribute_dotted_double"     )),
      IrregularGrouping(ResourceBundle.getString("duration_attribute_irregular_grouping")),  // ex. 5:4 - playing 5 sixteenth notes in the space of 4
      MultibarRest     (ResourceBundle.getString("duration_attribute_rest_multibar"     )),
      Rest             (ResourceBundle.getString("duration_attribute_rest"              ));

      // data members
      private String text;
      Type(String text) {this.text = text;}
      public String text() {return text;}
   }

   // data members
   private Type type;




   /**
    * constructor.
    * <br/><br/>
    * @param type   type of duration attribute.
    */
   public DurationAttribute(Type type)
   {
      this.type = type;
   }

   /**
    * @return the type of attribute.
    */
   public Type getType()
   {
      return type;
   }

   /**
    * @param type  the integer duration attribute type.
    * <br/><br/>
    * @return the duration attribute type enum corresponding to the integer type.
    */
   public static Type getType(int type)
   {
      for (Type t : Type.values())
         if (type == t.ordinal())
            return t;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.duration_attribute"), type, Type.MultibarRest.ordinal()));
   }

   /**
    * @return a new copy of the duration attribute.
    */
   @Override
   public DurationAttribute clone()
   {
      return new DurationAttribute(this.type);
   }

   /**
    * @param object  object to check for equality with this duration attribute.
    * <br/><br/>
    * @return if the two duration attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof DurationAttribute && this.type == ((DurationAttribute)object).type);
   }

   /**
    * @return whether the duration attribute has any variation between instances.
    * for example, every instance of the dotted duration attribute is the same, whereas instances of the multibar rest suration attribute
    * can have different number of measures.
    */
   public boolean isVariable()
   {
      return false;
   }

   /**
    * read in the duration attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the duration attribute can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      // don't do anything
      // this will be handled by the duration containing the attributes
   }

   /**
    * save a duration attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the duration attribute to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(type.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.duration_attribute"));
      }
   }

   /**
    * @return a string representation of the duration attribute.
    */
   @Override
   public String toString()
   {
      return toString(24);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the duration attribute.
    */
   public String toString(int numSpacesToIndent)
   {
      return type.text();
   }
}
