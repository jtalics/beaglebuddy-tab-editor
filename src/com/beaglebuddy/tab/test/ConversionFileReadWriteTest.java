/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.test;

import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import java.io.IOException;
import java.io.FileWriter;




/**
 * test harness for reading in a power tab 1.70 file and converting it to the beaglebuddy 1.0 format, then saving the file to disk and reading it back in.
 */
public class ConversionFileReadWriteTest
{
   /**
    * this really should be in a junit test case.
    * <br/><br/>
    * @param args command line arguments. args[0] = path to the power tab file, args[1] = directory to save converted file in bbt format
    */
   public static void main(String[] args)
   {
      if (args == null || args.length != 3)
         throw new IllegalArgumentException("You must specify the name of the .ptb file to read, the directory to save the converted file, and the directory to save the text file.");

      String ptbFilename  = args[0];
      String bbtDirectory = args[1];
      String txtDirectory = args[2];
      String bbtFilename  = bbtDirectory + "/" + getName(ptbFilename) + ".bbt";
      String txtFilename  = txtDirectory + "/" + getName(ptbFilename) + ".txt";

      try
      {
         // read in the power tab file and convert it to a beaglebuddy song
         Song song = new Song(ptbFilename);

         // save the song to the specified directory
         song.saveAs(bbtFilename);

         // read the beaglebuddy song back in
         song = new Song(bbtFilename);

         // print out the beaglebuddy song
         FileWriter file = new FileWriter(txtFilename);
         String           text = song.toString();
         file.write(text, 0, text.length());
         file.close();
      }
      catch (FileReadException ex)
      {
         ex.printStackTrace();
      }
      catch (FileWriteException ex)
      {
         ex.printStackTrace();
      }
      catch (IOException ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * strips off any path and extension from a file name and returns just the name of the file.
    * ex: c:/dev/myfile.txt    =>    myfile
    * <br/><br/>
    * @param filename with optional path and extension.
    * <br/><br/>
    * @return the simple filename
    */
   private static String getName(String filename)
   {
      int n1 = filename.lastIndexOf("\\");
      int n2 = filename.lastIndexOf("/");
      int n  = (n1 > n2 ? n1 : n2);

      int m1 = filename.lastIndexOf(".");
      int m  = (m1 == -1 ? filename.length() : m1);

      return (filename.substring(n+1, m));
   }
}
