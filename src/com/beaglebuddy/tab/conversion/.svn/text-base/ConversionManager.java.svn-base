/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.conversion;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.Information;
import com.beaglebuddy.tab.model.Score;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.Version;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.io.FileNotFoundException;
import java.util.ArrayList;




/**
 * This utility class is responsible for converting older versions of beaglebuddy tab files (.bbt),
 * power tab 1.70 files (.ptb) and guitar pro 5 files (.gp5) to the current beaglebuddy version format.
 */
public class ConversionManager
{
   static
   {
      try
      {
         TuningDictionary.load();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * default constructor.
    * don't allow instances of this class to be created.
    */
   private ConversionManager()
   {
      // no code necessary
   }

   /**
    * convert the chord diagram objects read in from the file to chord diagram objects.
    * <br/><br/>
    * @param chordDiagrams   the chord diagrams used by the score.
    */
   private static ArrayList<ChordDiagram> getChordDiagrams(Object[] chordDiagrams)
   {
      ArrayList<ChordDiagram> chordDiagrms = new ArrayList<ChordDiagram>(0);

      if (chordDiagrams != null)
      {
         for(Object chordDiagram : chordDiagrams)
            chordDiagrms.add((ChordDiagram)chordDiagram);
      }
      return chordDiagrms;
   }

   // for the future, when other versions of beaglebuddy tab are released
   // BBT_1_0_0_Song loadBBT_1_0_0_Song(String filename);
   // BBT_1_0_1_Song loadBBT_1_0_1_Song(String filename);
   // BBT_1_0_2_Song loadBBT_1_0_2_Song(String filename);
   // ...
   // BBT_1_0_1_Song convert(BBT_1_0_0_Song);
   // BBT_1_0_2_Song convert(BBT_1_0_1_Song);
   // ...

   /**
    * @return the extension of the file (text occuring after the last period in the filename)
    * <br/><br/>
    * @param filename  name of the file.
    */
   private static String getExtension(String filename)
   {
      int period = filename.lastIndexOf('.');
      return (period == -1 || period == (filename.length() - 1) ? null : filename.substring(period + 1));
   }

   /**
    * try to read in a tab file and, if needed, convert it to the current beaglebuddy version.
    * <br/><br/>
    * @param filename   the name of the tab file to load.
    * <br/><br/>
    * @throws FileReadException if the file can not be read in and parsed.
    * <br/><br/>
    * @return the parsed tab file in the current beaglebuddy song format.
    */
   public static Song load(String filename) throws FileReadException
   {
      Song   song;
      String extension = getExtension(filename);

      // see if it is a power tab file
      if (extension.equals("ptb"))
      {
         // convert the power tab 1.70 file to the current beaglebuddy song format
         song = Converter_PTB_1_7_0_to_BBT_1_0.convert(filename);
         song.layout();
      }
      // see if it is a guitar pro 5 tab file
      else if (extension.equals("gp5"))
      {
         // convert the guitar pro 5 file to the current beaglebuddy song format
         song = Converter_GP5_to_BBT_1_0.convert(filename);
         song.layout();
      }
      // see if it is a beaglebuddy tab file
      else if (extension.equals("bbt"))
      {
         FileInputStream file = null;
         try
         {
            file = new FileInputStream(filename);
         }
         catch (FileNotFoundException ex)
         {
            throw new FileReadException(ResourceBundle.format("error.io.read_file_not_found", filename));
         }

         // find out what version it is
         Version version = new Version();
         version.load(file);

         if (version.isCurrent())
         {
            Information             information   = new Information();
            ArrayList<ChordDiagram> chordDiagrams = null;
            Score                   score         = new Score();

            information.load(file);
            chordDiagrams = getChordDiagrams(file.readArray(ChordDiagram.class));
            score      .load(file);

            song = new Song(information, chordDiagrams, score);
         }
         else
         {
            // see if it is one of the valid released versions
            // currently, there are no other valid release versions

            // otherwise, throw an exception
            throw new FileReadException(null, file.getFilename(), 0L, 0, 0, ResourceBundle.getString("data_type.version"), ResourceBundle.format("error.invalid.version", version));
         }
      }
      else
      {
         throw new FileReadException(null, filename, 0L, 0, 0, ResourceBundle.getString("text.file_extension"), ResourceBundle.format("error.invalid.extension", extension));
      }
      return song;
   }
}
