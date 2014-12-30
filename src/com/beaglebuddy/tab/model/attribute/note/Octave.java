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
 * This class represents a beaglebuddy tab octave note attribute.
 */
public class Octave extends NoteAttribute
{
   // enums
   public enum OctaveType
   {
      Octave15mb("15mb"),    // two octaves lower
      Octave8vb ("8vb" ),    // one octave  lower
      OctaveLoco("loco"),    // same pitch
      Octave8va ("8va" ),    // one octave  higher
      Octave15ma("15ma");    // two octaves higher

      // data members
      private String text;
      OctaveType(String text) {this.text = text;}
      public String text() {return text;}
      public String toString() {return text;}
   }

   // data members
   private OctaveType octaveType;




   /**
    * default constructor.
    */
   public Octave()
   {
      super(NoteAttribute.Type.Octave);

      octaveType = OctaveType.Octave15ma;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param octaveType  type of octave.
    */
   public Octave(OctaveType octaveType)
   {
      super(NoteAttribute.Type.Octave);

      this.octaveType = octaveType;
   }

   /**
    * @return the type of octave.
    */
   public OctaveType getOctaveType()
   {
      return octaveType;
   }

   /**
    * @param type  the integer octave type.
    * <br/><br/>
    * @return the octave type enum corresponding to the integer octave type.
    */
   public static OctaveType getOctaveType(int type)
   {
      for (OctaveType ot : OctaveType.values())
         if (type == ot.ordinal())
            return ot;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.note_attribute.octave"), type, OctaveType.Octave15ma.ordinal()));
   }

   /**
    * sets the type of octave.
    * <br/><br/>
    * @param type   the type of octave.
    */
   public void setOctaveType(OctaveType type)
   {
      this.octaveType = type;
   }

   /**
    * @return a new copy of the octave note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new Octave(this.octaveType);
   }

   /**
    * @param object  object to check for equality with this octave attribute.
    * <br/><br/>
    * @return if the two octave attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof Octave && this.octaveType == ((Octave)object).octaveType);
   }

   /**
    * @return that the octave note attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the octave note attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the octave note attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         octaveType = getOctaveType(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.octave"));
      }
   }

   /**
    * save a octave note attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the octave note attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(octaveType.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.octave"));
      }
   }

   /**
    * @return a string representation of the octave.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" + octaveType.text() + ")";
   }
}
