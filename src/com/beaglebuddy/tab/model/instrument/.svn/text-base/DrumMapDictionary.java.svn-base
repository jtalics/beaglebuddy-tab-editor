/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.instrument;

import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;




/**
 * This singleton class manages the list of predefined beaglebuddy drum maps as well as any user defined custom drum maps.
 */
public class DrumMapDictionary
{
   // class members
   private static final String CHARACTER_COMMENT         = "#";    // lines in the drum map dictionary file that start with # are considered comments
   private static final String CHARACTER_FIELD_SEPARATOR = ",";    // data  in the drum map dictionary file is delimited by commas

   private static       ArrayList<DrumMap> drumMaps = new ArrayList<DrumMap>();





   /**
    * default constructor.
    */
   private DrumMapDictionary()
   {
      // don't allow instances of this class to be created.
   }

   /**
    * add the specified drum map to the drum map dictionary.
    * <br/><br/>
    * @param drumMap   the drum map being added.
    */
   public static void addTuning(DrumMap drumMap)
   {
      if (!isNameUnique(drumMap.getName()))
         throw new IllegalArgumentException(ResourceBundle.format("gui.error.drum_map.dictionary.duplicate_name", drumMap.getName()));

      drumMaps.add(drumMap);
   }

   /**
    * @return the full path to the drum map dictionary file on the user's hard drive.
    */
   public static String getFilename()
   {
      String tabHome = System.getProperty("tab.home");

      return tabHome + "/data/drum_map.dictionary";
   }

   /**
    * @return the requested drum map from the drum map dictionary if it exists.  Otherwise, null is returned.
    * <br/><br/>
    * @param name   the name of the drum map being requested.
    */
   public static DrumMap getDrumMap(String name)
   {
      DrumMap drumMap = null;

      if (name != null)
      {
         for(Iterator<DrumMap> i=drumMaps.iterator(); i.hasNext() && drumMap == null; )
         {
            DrumMap map = i.next();
            if (map.getName().equals(name))
               drumMap = map;
         }
      }
      return drumMap;
   }

   /**
    * @return the list of the available drum maps.
    *         if no matching drum maps are found in the drum map dictionary, then null is returned.
    */
   public static ArrayList<DrumMap> getDrumMaps()
   {
      return drumMaps;
   }

   /**
    * @return the list of names of the available drum maps.
    */
   public static ArrayList<String> getDrumMapNames()
   {
      ArrayList<String> names = new ArrayList<String>(0);

      for(DrumMap drumMap : drumMaps)
         names.add(drumMap.getName());

      return names;
   }

   /**
    * @return whether or not the drum map dictionary already contains a drum map with the specified name.
    * <br/><br/>
    * @param name  tuning name to check for uniqueness.
    */
   public static boolean isNameUnique(String name)
   {
      boolean isUnique = true;
      DrumMap drumMap  = null;

      for(int i=0; i<drumMaps.size() && isUnique; ++i)
      {
         drumMap   = drumMaps.get(i);
         isUnique = !drumMap.getName().equals(name);
      }
      return isUnique;
   }

   /**
    * read in all of the drum maps from the file on disk.
    * <br/><br/>
    * @throws IOException   if the file can not be opened for reading or it can not be parsed.
    */
   public static void load() throws IOException
   {
      String         filename = getFilename();
      String         line     = null;         // a single line read in from the drum map file.
      String[]       data     = null;         // the line split into fields.
      DrumMap        drumMap  = null;         // the fields parsed into a valid drum map
      BufferedReader file     = null;         // file to read in the drum map file

      // reset the list of drum maps
      drumMaps = new ArrayList<DrumMap>();

      // read in the file
      file = new BufferedReader(new FileReader(filename));
      line = file.readLine();
      while (line != null)
      {
         // ignore blank lines and comment lines
         line = line.trim();
         if (line.length() != 0 && !line.startsWith(CHARACTER_COMMENT))
         {
            // split the line into its constituent fields
            data = line.split(CHARACTER_FIELD_SEPARATOR);

            // parse the actual drum map
            drumMap = new DrumMap(data);

            // add it to the list of drum maps
            drumMaps.add(drumMap);
         }
         line = file.readLine();
      }
   }

   /**
    * remove the specified drum map from the drum map dictionary.
    * <br/><br/>
    * @param drumMap  the drum map being removed.
    * <br/><br/>
    * @return whether the drum map dictionary contained the specified drum map.
    */
   public static boolean removeDrumMap(DrumMap drumMap)
   {
      return drumMaps.remove(drumMap);
   }

   /**
    * save all the drum maps to the file on disk.
    * <br/><br/>
    * @throws IOException   if the file can not be opened or the drum maps can not be written.
    */
   public static void save() throws IOException
   {
      String         filename       = getFilename();
      BufferedWriter file           = null;         // file to write to the drum map file
      int            maxNameLength  = 0;            // length of the longest drum map name

      // get the length of the longest drum map name
      for(DrumMap drumMap : drumMaps)
         if (drumMap.getName().length() > maxNameLength)
            maxNameLength = drumMap.getName().length();

      // open the drum_map.dictionary file
      file = new BufferedWriter(new FileWriter(filename));

      // write out each drum map
      for(DrumMap drumMap : drumMaps)
      {
         // write the name of the drum map
         file.write(drumMap.getName() + Utility.indent(maxNameLength - drumMap.getName().length()) + CHARACTER_FIELD_SEPARATOR + " ");

         DrumMapEntry map = null;
         for(int i=0; i<drumMap.getMap().length; ++i)
         {
            map = drumMap.get(i);
            file.write(String.valueOf(map.getMidiPercussion().id()) + (i == DrumMap.NUM_DRUM_NOTES-1 ? "" : CHARACTER_FIELD_SEPARATOR) + " ");
         }
         file.newLine();
      }
      file.newLine();
      file.close();
   }
}
