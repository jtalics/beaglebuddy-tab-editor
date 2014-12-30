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
 * This class represents a beaglebuddy tab chord name chord attribute and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * A chord name is the name of chord defined in a chord diagram and is displayed as the name of the chord above the chord.
 */
public class ChordName extends ChordAttribute
{
   // data members
   private String name;     // name of the chord




   /**
    * default constructor.
    */
   public ChordName()
   {
      super(ChordAttribute.Type.ChordName);

      name = "";
   }

   /**
    * constructor.
    * <br/><br/>
    * @param name   name of the chord.
    */
   public ChordName(String name)
   {
      super(ChordAttribute.Type.ChordName);

      this.name = name;
   }

   /**
    * @return the name of the chord.
    */
   public String getName()
   {
      return name;
   }

   /**
    * sets the name of the chord.
    * <br/><br/>
    * @param name   the name of the chord.
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return a new copy of the chord name chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new ChordName(this.name);
   }

   /**
    * @param object  object to check for equality with this chord name attribute.
    * <br/><br/>
    * @return if the two chord name attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof ChordName)
      {
         ChordName chordName = (ChordName)object;
         result = this.name.equals(chordName.getName());
      }
      return result;
   }

   /**
    * @return that the chord name chord attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in a chord name chord attribute from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the chord name from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         name  = file.readString();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.chord_name"));
      }
   }

   /**
    * save an chord name chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the chord name to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.writeString(name);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.chord_name"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab chord text.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" + name + ")";
   }
}
