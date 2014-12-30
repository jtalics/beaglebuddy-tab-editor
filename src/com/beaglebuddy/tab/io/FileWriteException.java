/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.io;

import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * An exception class for errors that occur while writing to a beaglebuddy tab file.
 */
public class FileWriteException extends FileException
{
   /**
    * constructor.
    * <br/><br/>
    * @param ex        root cause of the exception.
    * @param filename  name of the beaglebuddy tab file in which the error occurred.
    * @param pos       file position where the error occurred.
    * @param section   current section.
    * @param measure   current measure.
    * @param object    type of object being written when the error occurred.
    */
   public FileWriteException(Exception ex, String filename, long pos, int section, int measure, String object)
   {
      this(ex, filename, pos, section, measure, object, null);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param ex            root cause of the exception.
    * @param filename      name of the beaglebuddy tab file in which the error occurred.
    * @param pos           file position where the error occurred.
    * @param section   current section.
    * @param measure       current measure.
    * @param object        type of object being read when the error occurred.
    * @param description   description of the error.
    */
   public FileWriteException(Exception ex, String filename, long pos, int section, int measure, String object, String description)
   {
      super(ResourceBundle.getString("error.io.write_exception"), ex, filename, pos, section, measure, object, description);
   }
}
