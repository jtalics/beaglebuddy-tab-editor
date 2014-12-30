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
 * This class represents a beaglebuddy tab version and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class Version
{
   // class members
   private static final int    CURRENT_VERSION_MAJOR = 0;
   private static final int    CURRENT_VERSION_MINOR = 0;
   private static final String TAG                   = "beaglebuddy";

   // data members
   private int major;     // major version
   private int minor;     // minor version



   /**
    * default constructor.
    */
   public Version()
   {
      this.major = CURRENT_VERSION_MAJOR;
      this.minor = CURRENT_VERSION_MINOR;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param major  major version
    * @param minor  minor version
    */
   public Version(int major, int minor)
   {
      this.major = major;
      this.minor = minor;
   }

   /**
    * @return the major version.
    */
   public int getMajor()
   {
      return major;
   }

   /**
    * sets the major version.
    * <br/><br/>
    * @param major the major version.
    */
   public void setMajor(int major)
   {
      this.major = major;
   }

   /**
    * @return the minor version.
    */
   public int getMinor()
   {
      return minor;
   }

   /**
    * sets the minor version.
    * <br/><br/>
    * @param minor the minor version.
    */
   public void setMinor(int minor)
   {
      this.minor = minor;
   }

   /**
    * @return a string representation of the version.
    */
   public String getDescription()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(String.valueOf(major));
      buffer.append(".");
      buffer.append(minor < 10 ? "0" : "");
      buffer.append(String.valueOf(minor));

      return buffer.toString();
   }

   /**
    * @return whether this is this is the current version.
    */
   public boolean isCurrent()
   {
      return major == CURRENT_VERSION_MAJOR && minor == CURRENT_VERSION_MINOR;
   }

   /**
    * read in the version from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the version can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();

         String tag = file.readString();
         if (tag == null || !(tag.equals(TAG)))
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.file_marker", tag));

         setMajor(file.read());
         setMinor(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.version"));
      }
   }

   /**
    * save a version to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the version to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();

         file.writeString(TAG);
         file.write(major);
         file.write(minor);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.version"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab version.
    */
   @Override
   public String toString()
   {
      return getDescription();
   }
}
