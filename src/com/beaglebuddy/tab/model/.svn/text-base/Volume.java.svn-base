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
 * This class represents a beaglebuddy tab volume and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * volume changes can only occur at the beginning of a measure (ie, on a barline).
 * </p>
 * todo: how to set the volume for the rhythm staff?
 *       this has not been decided, since we are still deciding whether to make RhythmStaff its own class or for it to extend from Staff like all the other
 *       types of staffs.
 *       or,
 *       do we simply use the staff data member here, when set to 0, to mean the rhythm staff, if its present, and the first staff if not?
 */
public class Volume
{
   // enums
   public enum Level
   {
      Off(0, ResourceBundle.getString("text.off")), Ppp(16, "ppp"), Pp(32, "pp"), P(48, "p"), Mp(64, "mp"), Mf(80, "mf"), F(96, "f"), Ff(112, "ff"), Fff(127, "fff");

      // data members
      private int    value;
      private String text;

      Level(int value, String text) {this.value=value; this.text=text;}
      public int    value   () {return value;}
      public String text    () {return text; }
      @Override
      public String toString() {return text + " (" + value + ")";}
   }

   // data members
   private byte  staff;      // the staff within the section where the volume changes.
   private Level level;      // volume of the staff.



   /**
    * default constructor.
    */
   public Volume()
   {
      staff  = 0;
      level = Level.P;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param staff   the staff within the section where the volume changes.
    * @param level   volume level of the staff.
    */
   public Volume(byte staff, Level level)
   {
      this.staff = staff;
      this.level = level;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param volume   the volume whose values will be deep copied.
    */
   public Volume(Volume volume)
   {
      this();

      if (volume != null)
      {
         this.staff = volume.staff;
         this.level = volume.level;
      }
   }

   /**
    * @return the staff within the section where the volume changes.
    */
   public byte getStaff()
   {
      return staff;
   }

   /**
    * sets the staff within the section where the volume changes.
    * <br/><br/>
    * @param staff   the staff within the section where the volume changes.
    */
   public void setStaff(byte staff)
   {
      this.staff = staff;
   }

   /**
    * @return the volume level.
    */
   public Level getLevel()
   {
      return level;
   }

   /**
    * sets the volume level.
    * <br/><br/>
    * @param level   the volume level.
    */
   public void setLevel(Level level)
   {
      this.level = level;
   }

   /**
    * @param level  the integer volume level.
    * <br/><br/>
    * @return the volume level enum corresponding to the integer volume.
    */
   public static Level getLevel(int level)
   {
      for (Level l : Level.values())
         if (level == l.ordinal())
            return l;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.volume.level"), level, Level.Fff.ordinal()));
   }

   /**
    * @return whether two volumes are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof Volume)
      {
         Volume volume = (Volume)object;
         equal = this.staff == volume.staff && this.level == volume.level;
      }
      return equal;
   }

   /**
    * read in the volume from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the volume can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos   = file.getPosition();
         staff = (byte)file.read();
         level = getLevel(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.volume.level"));
      }
   }

   /**
    * save a volume to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the volume to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(staff          );
         file.write(level.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.volume.level"));
      }
   }

   /**
    * @return a string representation of the volume.
    */
   @Override
   public String toString()
   {
      return toString(21);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the volume.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.volume") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.staff"            ), indentation) + staff + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.volume.level"), indentation) + level       );

      return buffer.toString();
   }
}
