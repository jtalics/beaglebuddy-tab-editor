/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.io;

import java.text.MessageFormat;




/**
 * An exception class for errors that occur while reading from or writing to a beaglebuddy tab file.
 */
public class FileException extends Exception
{
   /**
    * constructor.
    * <br/><br/>
    * @param message  message describing the error.
    */
   public FileException(String message)
   {
      super(message);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param message      error message that will be formatted with the other arguments.
    * @param ex           root cause of the exception.
    * @param filename     name of the beaglebuddy tab file in which the error occurred.
    * @param pos          file position where the error occurred.
    * @param section      current section.
    * @param measure      current measure.
    * @param object       type of object being read when the error occurred.
    * @param description  description of the error.
    */
   public FileException(String message, Exception ex, String filename, long pos, int section, int measure, String object, String description)
   {
      super(MessageFormat.format(message, object, filename, pos, section, measure) + (description == null ? "" : "\n" + description) + (ex == null ? "" : "\n" + ex.getMessage()));
   }
}
