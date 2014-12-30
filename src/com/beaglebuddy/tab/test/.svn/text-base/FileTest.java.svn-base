/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.test;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.model.Volume;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * test harness for reading and writing a beaglebuddy tab file.
 */
public class FileTest
{
   /**
    * this really should be in a junit test case.
    * <br/><br/>
    * @param args command line arguments.
    */
   public static void main(String[] args)
   {
      String filename = "d:/test.bbt";

      try
      {
         FileOutputStream file = new FileOutputStream(filename);

         // write shorts
         file.writeShort((short)56      );
         file.writeShort(Short.MAX_VALUE);
         file.writeShort(Short.MIN_VALUE);

         // write ints
         file.writeInt(213013           );
         file.writeInt(Integer.MAX_VALUE);
         file.writeInt(Integer.MIN_VALUE);

         // write longs
         file.writeLong(1234567L       );
         file.writeLong(Long.MAX_VALUE);
         file.writeLong(Long.MIN_VALUE);

         // write dates
         Calendar calendar = new GregorianCalendar();
         Date     date     = new Date();
         calendar.setTime(date);
         calendar.set(Calendar.SECOND      , 0);
         calendar.set(Calendar.MILLISECOND , 0);
         date = calendar.getTime();
         file.writeDate(date);

         // write strings
         file.writeString(null);
         file.writeString("");
         file.writeString("beaglebuddy");

         // write colors
          file.writeColor(Color.GREEN);
          file.writeColor(Color.BLACK);
          file.writeColor(Color.WHITE);
          file.writeColor(Color.ORANGE);

         // write rectangles
          file.writeRectangle(new Rectangle(100000, 50000, 250, 500));
          file.writeRectangle(new Rectangle(500000, 20000, 100000, 50000));

          // write arrays
          Volume[] volumes = {new Volume((byte)1, Volume.Level.Off),
                              new Volume((byte)9, Volume.Level.Pp ),
                              new Volume((byte)4, Volume.Level.Mf )};
          file.writeArray(volumes);

          file.close();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }


      try
      {
         FileInputStream file = new FileInputStream(filename);

         short     n1;
         int       n2;
         long      n3;
         Date      d;
         String    s;
         Color     c;
         Rectangle r;
         Object[]  volumes;


         // read shorts
         n1 = file.readShort();
         n1 = file.readShort();
         n1 = file.readShort();

         // read ints
         n2 = file.readInt();
         n2 = file.readInt();
         n2 = file.readInt();

         // read longs
         n3 = file.readLong();
         n3 = file.readLong();
         n3 = file.readLong();

         // read dates
         d = file.readDate();

         // read strings
         s = file.readString();
         s = file.readString();
         s = file.readString();

         // read colors
         c = file.readColor();
         c = file.readColor();
         c = file.readColor();
         c = file.readColor();

         // read rectangles
         r = file.readRectangle();
         r = file.readRectangle();

         // read arrays
         volumes = file.readArray(Volume.class);

         file.close();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
}
