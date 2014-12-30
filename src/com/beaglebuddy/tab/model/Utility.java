/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;




/**
 * utility class containing miscellaneous static methods.
 */
public class Utility
{
   /**
    * default constructor made private so that no instances of this class can be created.
    */
   private Utility()
   {
      // no code necessary
   }

   /**
    * @param data  integer data whose value will be converted to hex.
    * <br/><br/>
    * @return the hex representation of an integer.
    */
   public static String hex(int data)
   {
      StringBuffer buffer  = new StringBuffer();
      char[]       hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

      buffer.append("0x");
      buffer.append(hexChar[(byte)((data & 0xF0000000) >>> 28)]);
      buffer.append(hexChar[(byte)((data & 0x0F000000) >>  24)]);
      buffer.append(' ');
      buffer.append(hexChar[(byte)((data & 0x00F00000) >>  20)]);
      buffer.append(hexChar[(byte)((data & 0x000F0000) >>  16)]);
      buffer.append(' ');
      buffer.append(hexChar[(byte)((data & 0x0000F000) >>  12)]);
      buffer.append(hexChar[(byte)((data & 0x00000F00) >>   8)]);
      buffer.append(' ');
      buffer.append(hexChar[(byte)((data & 0x000000F0) >>   4)]);
      buffer.append(hexChar[(byte)((data & 0x0000000F)       )]);

      return buffer.toString();
   }

   /**
    * @param data  short data whose value will be converted to hex.
    * <br/><br/>
    * @return the hex representation of a short.
    */
   public static String hex(short data)
   {
      StringBuffer buffer  = new StringBuffer();
      char[]       hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

      buffer.append("0x");
      buffer.append(hexChar[(byte)((data & 0xF000) >>> 12)]);
      buffer.append(hexChar[(byte)((data & 0x0F00) >>   8)]);
      buffer.append(' ');
      buffer.append(hexChar[(byte)((data & 0x00F0) >>   4)]);
      buffer.append(hexChar[(byte)((data & 0x000F)       )]);

      return buffer.toString();
   }

   /**
    * @param data  byte data whose value will be converted to hex.
    * <br/><br/>
    * @return the hex representation of a byte.
    */
   public static String hex(byte data)
   {
      StringBuffer buffer  = new StringBuffer();
      char[]       hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

      buffer.append("0x");
      buffer.append(hexChar[(byte)((data & 0xF0) >>   4)]);
      buffer.append(hexChar[(byte)((data & 0x0F)       )]);

      return buffer.toString();
   }

   /**
    * @return a string of blank spaces of the specified length.
    * <br/><br/>
    * @param numSpaces  the number of spaces to use for the indentation;
    */
   public static String indent(int numSpaces)
   {
      StringBuffer buffer = new StringBuffer();
      for(int i=0; i<numSpaces; ++i)
         buffer.append(" ");
      return buffer.toString();
   }

   /**
    * @param string       the string to be right padded with dots terminating in a colon.
    * @param indentation  the string of spaces used to indent the string to be padded.
    * <br/><br/>
    * @return the specified string padded to the right with dots and terminated with a colon.
    */
   public static String pad(String string, String indentation)
   {
      StringBuffer buffer    = new StringBuffer();
      int          numDots   = 60 - string.length() - indentation.length();

      buffer.append(indentation);
      buffer.append(string);
      for(int i=0; i<numDots; ++i)
         buffer.append(".");
      buffer.append(": ");

      return buffer.toString();
   }
}
