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
import java.awt.Color;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;




/**
 * this class is used to write data to a beaglebuddy tab file (.bbt).
 */
public class FileOutputStream extends java.io.FileOutputStream
{
   // data members
   private String filename;     // name of the file being written to
   private int    section;      // the current section number being processed
   private int    measure;      // the current measure number being processed




   /**
    * constructor.
    * <br/><br/>
    * @param filename   name of file to open for writing.
    * <br/><br/>
    * @throws FileNotFoundException  if the given file can not be created.
    */
   public FileOutputStream(String filename) throws FileNotFoundException
   {
      super(filename);

      this.filename = filename;
   }

   /**
    * @return the name of the file being written.
    */
   public String getFilename()
   {
      return filename;
   }

   /**
    * @return the current measure.
    */
   public int getMeasure()
   {
      return measure;
   }

   /**
    * decrements the measure.
    */
   public void decrementMeasure()
   {
      measure--;
   }

   /**
    * increments the measure.
    */
   public void incrementMeasure()
   {
      measure++;
   }

   /**
    * @return the position (in bytes) of the pointer within the file.
    * <br/><br/>
    * @throws IOException  if the file position can not be obtained.
    */
   public long getPosition() throws IOException
   {
      return getChannel().position();
   }

   /**
    * @return the current section.
    */
   public int getSection()
   {
      return section;
   }

   /**
    * increments the section.
    */
   public void incrementSection()
   {
      section++;
   }

   /**
    * writes an array of objects to the file.
    * <br/><br/>
    * @param array   the array of objects to write to the file.
    * <br/><br/>
    * @throws FileWriteException  if the array can not be written to the file.
    */
   public void writeArray(Object[] array) throws FileWriteException
   {
      Class<?> clasz = null;
      long     pos   = -1L;

      try
      {
         pos  = getPosition();

         // write the number of elements in the array
         writeInt(array.length);

         if (array.length != 0)
         {
            // write the array
            try
            {
               clasz = array[0].getClass();
               // get the class's save(FileOutputStream file) method
               Method method = clasz.getMethod("save", FileOutputStream.class);

               // call the object's save() method to write the data to the file
               for(Object object : array)
                  method.invoke(object, this);   // call's the object's save(FileOutputStream file) method;
            }
            catch (InvocationTargetException ex)
            {
               // since we're using reflection to invoke the save() method calls, java wraps all exceptions thrown from them in an InvocationTargetException.
               // thus, we need to extract the original exception and throw it on up the stack.
               Throwable root = ex.getTargetException();
               if (root instanceof FileWriteException)
               {
                  throw (FileWriteException)root;
               }
               else
               {
                  System.err.println(ResourceBundle.format("error.unexpected_exception", root.getClass().getName()));
                  root.printStackTrace();
                  System.exit(1);
               }
            }
            catch (Exception ex)
            {
               System.err.println(ResourceBundle.format("error.unexpected_exception", ex.getClass().getName()));
               ex.printStackTrace();
               System.exit(2);
            }
         }
      }
      catch (IOException ex)
      {
         throw new FileWriteException(ex, getFilename(), pos, getSection(), getMeasure(), ResourceBundle.format("data_type.array_of", clasz.getName()));
      }
      catch (IllegalArgumentException ex)
      {
         throw new FileWriteException(ex, getFilename(), pos, getSection(), getMeasure(), ResourceBundle.format("data_type.array_of", clasz.getName()));
      }
      catch (FileWriteException ex)
      {
         throw ex;
      }
      catch (Exception ex)
      {
         System.out.println(ResourceBundle.format("error.unexpected_exception", ex.getClass().getName()));
         ex.printStackTrace();
         System.exit(3);
      }
   }

   /**
    * writes a boolean to the file.
    * <br/><br/>
    * @param b  the boolean to write to the file.
    * <br/><br/>
    * @throws IOException  if the boolean can not be written to the file.
    */
   public void writeBoolean(boolean b) throws IOException
   {
      long pos = getPosition();
      try
      {
         write((byte)(b ? 1 : 0));
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.boolean"), b, filename, pos, ex.getMessage()));
      }
   }

   /**
    * writes a unicode character to the file.
    * <br/><br/>
    * @param c the unicode character to write to the file.
    * <br/><br/>
    * @throws IOException  if the unicode character can not be written to the file.
    */
   public void writeCharacter(char c) throws IOException
   {
      long pos = getPosition();

      try
      {
         writeShort((short)c);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.character"), c, filename, pos, ex.getMessage()));
      }
    }

   /**
    * writes an rgb color to the file.
    * <br/><br/>
    * @param color   the rgb color to write to the file.
    * <br/><br/>
    * @throws IOException  if the rgb color can not be written to the file.
    */
   public void writeColor(Color color) throws IOException
   {
      byte[] bytes = new byte[3];

      bytes[0] = (byte)(color.getRed  ());
      bytes[1] = (byte)(color.getGreen());
      bytes[2] = (byte)(color.getBlue ());

      long pos = getPosition();
      try
      {
         write(bytes);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.color"), color, filename, pos, ex.getMessage()));
      }
   }

   /**
    * writes a date to the file.
    * <br/><br/>
    * @param date   the date to write to the file.
    * <br/><br/>
    * @throws IOException  if the date can not be written to the file.
    */
   public void writeDate(Date date) throws IOException
   {
      long pos = getPosition();
      try
      {
         writeLong(date.getTime());
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.date"), date, filename, pos, ex.getMessage()));
      }
   }

   /**
    * writes an integer to the file.
    * <br/><br/>
    * @param n   the integer to write to the file.
    * <br/><br/>
    * @throws IOException  if the integer can not be written to the file.
    */
   public void writeInt(int n) throws IOException
   {
      byte[] bytes = new byte[4];

      bytes[0] = (byte)((n & 0xff000000) >>> 24);
      bytes[1] = (byte)((n & 0x00ff0000) >>> 16);
      bytes[2] = (byte)((n & 0x0000ff00) >>>  8);
      bytes[3] = (byte)((n & 0x000000ff)       );

      long pos = getPosition();
      try
      {
         write(bytes);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.int"), n, filename, pos, ex.getMessage()));
      }
   }

   /**
    * writes a long to the file.
    * <br/><br/>
    * @param n    the long to write to the file.
    * <br/><br/>
    * @throws IOException  if the long can not be written to the file.
    */
   public void writeLong(long n) throws IOException
   {
      long pos = getPosition();
      try
      {
         byte[] bytes = new byte[8];

         bytes[0] = (byte)((n & 0xff00000000000000L) >>> 56);
         bytes[1] = (byte)((n & 0x00ff000000000000L) >>> 48);
         bytes[2] = (byte)((n & 0x0000ff0000000000L) >>> 40);
         bytes[3] = (byte)((n & 0x000000ff00000000L) >>> 32);
         bytes[4] = (byte)((n & 0x00000000ff000000L) >>> 24);
         bytes[5] = (byte)((n & 0x0000000000ff0000L) >>> 16);
         bytes[6] = (byte)((n & 0x000000000000ff00L) >>>  8);
         bytes[7] = (byte)((n & 0x00000000000000ffL)       );

         write(bytes);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.long"), n, filename, pos, ex.getMessage()));
      }
   }

   /**
    * writes a rectangle to the file.
    * <br/><br/>
    * @param rectangle   the rectangle to write to the file.
    * <br/><br/>
    * @throws IOException if the rectangle can not be written to the file.
    */
   public void writeRectangle(Rectangle rectangle) throws IOException
   {
      long pos = getPosition();

      try
      {
         writeInt((int)rectangle.getX     ());
         writeInt((int)rectangle.getY     ());
         writeInt((int)rectangle.getWidth ());
         writeInt((int)rectangle.getHeight());
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.rectangle"), rectangle, filename, pos, ex.getMessage()));
      }
   }

   /**
    * writes a short to the file.
    * <br/><br/>
    * @param n   the short value to write to the file.
    * <br/><br/>
    * @throws IOException  if the short can not be written to the file.
    */
   public void writeShort(short n) throws IOException
   {
      byte[] bytes = new byte[2];

      bytes[0] = (byte)((n & 0xff00) >>> 8);
      bytes[1] = (byte)((n & 0x00ff)      );

      long pos = getPosition();
      try
      {
         write(bytes);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.short"), n, filename, pos, ex.getMessage()));
      }
   }

   /**
    * writes a string to the file.
    * <br/><br/>
    * @param s   the string to write to the file.
    * <br/><br/>
    * @throws IOException  if the string can not be written to the file.
    */
   public void writeString(String s) throws IOException
   {
      long pos = getPosition();

      try
      {
         if (s == null || s.length() == 0)
         {
            writeInt(0);
         }
         else
         {
            byte[] bytes = s.getBytes();
            writeInt(bytes.length);
            write   (bytes);
         }
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_write_data_type", ResourceBundle.getString("data_type.string"), s, filename, pos, ex.getMessage()));
      }
   }
}
