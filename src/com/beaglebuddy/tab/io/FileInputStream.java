/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.io;

import com.beaglebuddy.tab.model.instrument.*;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.EOFException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;





/**
 * this class is used to read data from a beaglebuddy tab file (.bbt).
 */
public class FileInputStream extends java.io.FileInputStream
{
   // class members
   private static Hashtable<Instrument.Type, Class<?>> map = new Hashtable<Instrument.Type, Class<?>>();
   static
   {
      map.put(Instrument.Type.Bass_Guitar , BassGuitar .class);
      map.put(Instrument.Type.Drums       , Drums      .class);
      map.put(Instrument.Type.Guitar      , Guitar     .class);
      map.put(Instrument.Type.Keyboards   , Keyboard   .class);
      map.put(Instrument.Type.Other_Bass  , OtherBass  .class);
      map.put(Instrument.Type.Other_Treble, OtherTreble.class);
      map.put(Instrument.Type.Vocals      , Vocals     .class);
   }

   // data members
   private String filename;      // name of the file being read
   private int    section;       // the current section number being processed
   private int    measure;       // the current measure number being processed





   /**
    * constructor.
    * <br/><br/>
    * @param filename   name of file to open for reading.
    * <br/><br/>
    * @throws FileNotFoundException  if the given file can not be found.
    */
   public FileInputStream(String filename) throws FileNotFoundException
   {
      super(filename);

      this.filename = filename;
   }

   /**
    * @return the name of the file being read.
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
    * reads in an array of objects.
    * <br/><br/>
    * @param clasz the class of the array of objects to be read from the file.
    * <br/><br/>
    * @return the array of objects read from the file.
    * <br/><br/>
    * @throws FileReadException  if the array can not be read in from the file.
    */
   public Object[] readArray(Class<?> clasz) throws FileReadException
   {
      ArrayList<Object> arrayList  = new ArrayList<Object>();

      long pos = -1L;
      try
      {
         pos  = getPosition();

         // read in the number of elements in the array
         int num = readInt();

         if (num != 0)
         {
            // read in the array
            try
            {
               if (clasz == Instrument.class)
               {
                  // create new objects and call the object's load() method to read in the data from the file
                  for(int i=0; i<num; ++i)
                  {
                     Instrument.Type type   = Instrument.getType(read());
                     Class<?>        clas   = map.get(type);
                     Method          method = clas.getMethod("load", FileInputStream.class);   // get the class's load(FileInputStream file) method
                     Object          object = clas.newInstance();                              // create a new instance of the instrument
                     method.invoke(object, this);                                              // call's the object's load(FileInputStream file) method;
                     arrayList.add(object);
                  }
               }
               else
               {
                  // get the class's load(FileInputStream file) method
                  Method method = clasz.getMethod("load", FileInputStream.class);
                  Object object;

                  // create new objects and call the object's load() method to read in the data from the file
                  for(int i=0; i<num; ++i)
                  {
                     object = clasz.newInstance();
                     method.invoke(object, this);   // call's the object's load(FileInputStream file) method;
                     arrayList.add(object);
                  }
               }
            }
            catch (InvocationTargetException ex)
            {
               // since we're using reflection to invoke the load() method calls, java wraps all exceptions thrown from them in an InvocationTargetException.
               // thus, we need to extract the original exception and throw it on up the stack.
               Throwable root = ex.getTargetException();
               if (root instanceof FileReadException)
               {
                  throw (FileReadException)root;
               }
               else
               {
                  System.out.println(ResourceBundle.format("error.unexpected_exception", root.getClass().getName()));
                  root.printStackTrace();
                  System.exit(1);
               }
            }
            catch (Exception ex)
            {
               System.out.println(ResourceBundle.format("error.unexpected_exception", ex.getClass().getName()));
               ex.printStackTrace();
               System.exit(2);
            }
         }
      }
      catch (IOException ex)
      {
         throw new FileReadException(ex, getFilename(), pos, getSection(), getMeasure(), ResourceBundle.format("data_type.array_of", clasz.getName()));
      }
      catch (IllegalArgumentException ex)
      {
         throw new FileReadException(ex, getFilename(), pos, getSection(), getMeasure(), ResourceBundle.format("data_type.array_of", clasz.getName()));
      }
      catch (FileReadException ex)
      {
         throw ex;
      }
      catch (Exception ex)
      {
         System.out.println(ResourceBundle.format("error.unexpected_exception", ex.getClass().getName()));
         ex.printStackTrace();
         System.exit(3);
      }
      return arrayList.toArray();
   }

   /**
    * reads a boolean from the file.
    * <br/><br/>
    * @return the boolean read from the file.
    * <br/><br/>
    * @throws IOException  if the boolean can not be read from the file.
    */
   public boolean readBoolean() throws IOException
   {
      boolean b;
      long    pos = getPosition();

      try
      {
         b = (read() == 1);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.boolean"), filename, pos, ex.getMessage()));
      }
      return b;
   }

   /**
    * reads a unicode character from the file.
    * <br/><br/>
    * @return the unicode character read from the file.
    * <br/><br/>
    * @throws IOException  if the unicode character can not be read from the file.
    */
   public char readCharacter() throws IOException
   {
      char c;
      long pos = getPosition();

      try
      {
         c = (char)readShort();
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.character"), filename, pos, ex.getMessage()));
      }
      return c;
   }

   /**
    * reads an rgb color from the file.
    * <br/><br/>
    * @return the rgb color read from the file.
    * <br/><br/>
    * @throws IOException  if the rgb color can not be read from the file.
    */
   public Color readColor() throws IOException
   {
      Color color;
      long  pos = getPosition();

      try
      {
         color = new Color(read(), read(), read());
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.color"), filename, pos, ex.getMessage()));
      }
      return color;
   }

   /**
    * reads a date from the file.
    * <br/><br/>
    * @return the date read from the file.
    * <br/><br/>
    * @throws IOException  if the date can not be read from the file.
    */
   public Date readDate() throws IOException
   {
      Date date;
      long pos  = getPosition();

      try
      {
         date = new Date(readLong());
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.date"), filename, pos, ex.getMessage()));
      }
      return date;
   }

   /**
    * reads an integer from the file.
    * <br/><br/>
    * @return the integer read from the file.
    * <br/><br/>
    * @throws IOException  if the integer can not be read from the file.
    */
   public int readInt() throws IOException
   {
      int  n;
      long pos = getPosition();

      try
      {
         int[] bytes = new int[4];
         bytes[0] = read();
         bytes[1] = read();
         bytes[2] = read();
         bytes[3] = read();

         if ((bytes[0] | bytes[1] | bytes[2] | bytes[3]) < 0)
            throw new EOFException();

         n = (bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + (bytes[3]);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type_2", ResourceBundle.getString("data_type.int"), filename, pos, ex.getMessage()));
      }
      return n;
   }

   /**
    * reads a long from the file.
    * <br/><br/>
    * @return the long read from the file.
    * <br/><br/>
    * @throws IOException  if the long can not be read from the file.
    */
   public long readLong() throws IOException
   {
      long n;
      long pos = getPosition();

      try
      {
         int[] bytes = new int[8];
         bytes[0] = read();
         bytes[1] = read();
         bytes[2] = read();
         bytes[3] = read();
         bytes[4] = read();
         bytes[5] = read();
         bytes[6] = read();
         bytes[7] = read();

         if ((bytes[0] | bytes[1] | bytes[2] | bytes[3] | bytes[4] | bytes[5] | bytes[6] | bytes[7]) < 0)
            throw new EOFException();

         n = ((long)bytes[0] << 56) + ((long)bytes[1] << 48) + ((long)bytes[2] << 40) + ((long)bytes[3] << 32) + ((long)bytes[4] << 24) + ((long)bytes[5] << 16) + ((long)bytes[6] <<  8) + ((long)bytes[7]);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.long"), filename, pos, ex.getMessage()));
      }
      return n;
   }

   /**
    * reads a rectangle from the file.
    * <br/><br/>
    * @return the rectangle read from the file.
    * <br/><br/>
    * @throws IOException if the rectangle can not be read from the file.
    */
   public Rectangle readRectangle() throws IOException
   {
      Rectangle rectangle;
      long pos    = getPosition();

      try
      {
         int x      = readInt();
         int y      = readInt();
         int width  = readInt();
         int height = readInt();

         if (width  < 0 || height < 0)
            throw new IOException(ResourceBundle.format("error.invalid.rectangle_coordinates", x, y, width, height, filename, pos));
         rectangle = new Rectangle(x, y, width, height);
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.rectangle"), filename, pos, ex.getMessage()));
      }
      return rectangle;
   }

   /**
    * reads a short from the file.
    * <br/><br/>
    * @return the short value read from the file.
    * <br/><br/>
    * @throws IOException  if the short can not be read from the file.
    */
   public short readShort() throws IOException
   {
      short n;
      long  pos = getPosition();

      try
      {
         int[] bytes = new int[2];

         bytes[0] = read();
         bytes[1] = read();

         n = (short)((bytes[0] << 8) + (bytes[1]));
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.short"), filename, pos, ex.getMessage()));
      }
      return n;
   }

   /**
    * reads a string from the file.
    * <br/><br/>
    * @return the string read from the file.
    * <br/><br/>
    * @throws IOException  if the string can not be read from the file.
    */
   public String readString() throws IOException
   {
      String string = null;
      long   pos = getPosition();

      try
      {
         int length = readInt();
         if (length != 0)
         {
            byte[] bytes = new byte[length];
            if (read(bytes) != bytes.length)
               throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.string"), filename, pos, "Unable to read all of the bytes of the string from the file."));
            string = new String(bytes);
         }
      }
      catch (IOException ex)
      {
         throw new IOException(ResourceBundle.format("error.io.unable_to_read_data_type", ResourceBundle.getString("data_type.string"), filename, pos, ex.getMessage()));
      }
      return string;
   }
}
