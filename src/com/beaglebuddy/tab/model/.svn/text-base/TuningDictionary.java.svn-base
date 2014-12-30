/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;




/**
 * This singleton class manages the list of predefined beaglebuddy tunings.
 * <p>
 * The tuning.dictionary file stores notes from low to high (string 6 to string 1),
 * while the Tuning class stores them from high to low      (string 1 to string 6).
 * The conversion is handled appropriately by the <i>load()</i> and <i>save()</i> methods, so that tunings are correctly converted
 * back and forth to the Tuning class's format.
 * </p>
 */
public class TuningDictionary
{
   // class members
   private static final String CHARACTER_COMMENT         = "#";           // lines in the tuning dictionary file that start with # are considered comments
   private static final String CHARACTER_FIELD_SEPARATOR = ",";           // data  in the tuning dictionary file is delimited by commas
   private static final int    FIELD_INSTRUMENT_TYPE     = 0;             // index of the instrument type   field in a line from the tuning dictionary
   private static final int    FIELD_TUNING_NAME         = 1;             // index of the tuning name       field in a line from the tuning dictionary
   private static final int    FIELD_NUM_STRINGS         = 2;             // index of the number of strings field in a line from the tuning dictionary
   private static final int    FIELD_TUNING_OFFSET       = 3;             // index of the tuning offset     field in a line from the tuning dictionary
   private static final int    NUM_FIXED_FIELDS          = 4;             // number of fields that every tuning has (instrument type, tuning name, number of strings, and tuning offset).
                                                                          // as opposed to the number of variable fields which are the notes of the tuning, which depend on how many strings the instrument has

   private static       Hashtable<Integer, ArrayList<Tuning>> guitarTunings = new Hashtable<Integer, ArrayList<Tuning>>();
   private static       Hashtable<Integer, ArrayList<Tuning>> bassTunings   = new Hashtable<Integer, ArrayList<Tuning>>();

   // names of the tunings provided in the tuning dictionary
   public  static final String TUNING_STANDARD           = "standard";
   public  static final String TUNING_DOWN_1_2_STEP      = "down ½ step";
   public  static final String TUNING_DOWN_1_STEP        = "down 1 step";
   public  static final String TUNING_DOWN_1_1_2_STEPS   = "down 1½ steps";
   public  static final String TUNING_DROPPED_D          = "dropped D";
   public  static final String TUNING_DOUBLE_DROPPED_D   = "double dropped D";
   public  static final String TUNING_MODAL_D            = "modal D";
   public  static final String TUNING_OPEN_A             = "open A";
   public  static final String TUNING_OPEN_C             = "open C";
   public  static final String TUNING_OPEN_D             = "open D";
   public  static final String TUNING_OPEN_E             = "open E";
   public  static final String TUNING_OPEN_G             = "open G";
   public  static final String TUNING_BASS               = "bass";
   public  static final int    NUM_TUNINGS               = 13;






   /**
    * default constructor.
    */
   private TuningDictionary()
   {
      // don't allow instances of this class to be created.
   }

   /**
    * add the specified tuning to the tuning dictionary.
    * <br/><br/>
    * @param type     the type of instrument the tuning is for.  Valid values are Guitar and Bass_Guitar.
    * @param tuning   the tuning being added.
    */
   public static void addTuning(Instrument.Type type, Tuning tuning)
   {
      if (type != Instrument.Type.Bass_Guitar && type != Instrument.Type.Guitar)
         throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.tuning.type", Instrument.Type.Bass_Guitar.ordinal(), Instrument.Type.Guitar.ordinal()));

      int               numStrings = tuning.getNumStrings();
      ArrayList<Tuning> tunings    = null;

      if (!isNameUnique(tuning.getName(), type, numStrings))
         throw new IllegalArgumentException(ResourceBundle.format("gui.error.tuning.dictionary.duplicate_name", tuning.getName(),
                                           (type == Instrument.Type.Guitar ? ResourceBundle.getString("instrument.guitars") : ResourceBundle.getString("instrument.bass_guitars")),
                                           numStrings));

      if (type == Instrument.Type.Guitar)
      {
         if (numStrings != 6 && numStrings != 7)
            throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.tuning.num_strings_2", Instrument.Type.Guitar, numStrings, tuning.getName()));

         tunings = guitarTunings.get(numStrings);
      }
      else
      {
         if (numStrings != 4 && numStrings != 5 && numStrings != 6)
            throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.tuning.num_strings_2", Instrument.Type.Bass_Guitar, numStrings, tuning.getName()));

         tunings = bassTunings.get(numStrings);
      }
      tunings.add(tuning);
   }

   /**
    * @return the full path to the tuning dictionary file on the user's hard drive.
    */
   public static String getFilename()
   {
      String tabHome = System.getProperty("tab.home");

      return tabHome + "/data/tuning.dictionary";
   }

   /**
    * searches the tuning dictionary for the tuning for the given instrument type with the specified number of strings and tuning notes.
    * <br/><br/>
    * @return the requested tuning from the tuning dictionary that matches the given type, number of strings, and tuning notes, if it exists.
    * otherwise, null is returned.
    * <br/><br/>
    * @param type    the type of instrument.
    * @param notes   notes in the tuning.
    */
   public static Tuning getTuning(Instrument.Type type, Midi.Note[] notes)
   {
      ArrayList<Tuning> tunings = getTunings(type, notes.length);
      Tuning            tuning  = null;

      if (tunings != null)
      {
         for(Iterator<Tuning> i=tunings.iterator(); i.hasNext() && tuning == null; )
         {
            Tuning  t     = i.next();
            boolean match = true;
            for(int n=0; n<notes.length && match; ++n)
               match = t.getNotes()[n] == notes[n];

            if (match)
               tuning = t;
         }
      }
      return tuning == null ? null : new Tuning(tuning);
   }

   /**
    * searches the tuning dictionary for the tuning for the given instrument type with the specified number of strings and tuning name.
    * <br/><br/>
    * @return the requested tuning from the tuning dictionary that matches the given type, number of strings, and name, if it exists.
    * Otherwise, null is returned.
    * <br/><br/>
    * @param type         the type of instrument.
    * @param numStrings   the number of strings the instrument has.
    * @param name         the name of the tuning being requested.
    */
   public static Tuning getTuning(Instrument.Type type, int numStrings, String name)
   {
      ArrayList<Tuning> tunings = getTunings(type, numStrings);
      Tuning            tuning  = null;

      if (name != null && tunings != null)
      {
         for(Iterator<Tuning> i=tunings.iterator(); i.hasNext() && tuning == null; )
         {
            Tuning t = i.next();
            if (t.getName().equals(name))
               tuning = t;
         }
      }
      return tuning == null ? null : new Tuning(tuning);
   }

   /**
    * @return the list of the available tunings for the specified type of instrument and number of strings.
    *         if no matching tunings are found in the tuning dictionary, then null is returned.
    * <br/><br/>
    * @param type         the type of instrument.
    * @param numStrings   the number of strings the instrument has.
    */
   public static ArrayList<Tuning> getTunings(Instrument.Type type, int numStrings)
   {
      assert(type != Instrument.Type.Drums);
      assert(numStrings >= Instrument.MIN_NUM_STRINGS && numStrings <= Instrument.MAX_NUM_STRINGS);

      ArrayList<Tuning> tunings = null;

      switch (type)
      {
         case Bass_Guitar:
         case Other_Bass:
              assert(numStrings == 4 || numStrings == 5 || numStrings == 6);
              tunings = bassTunings.get(numStrings);
         break;
         case Guitar:
         case Keyboards:
         case Other_Treble:
         case Vocals:
              assert(numStrings == 6 || numStrings == 7);
              tunings = guitarTunings.get(numStrings);
         break;
        case Drums:
          break;
      }
      return tunings;
   }

   /**
    * @return the list of names of the available tunings for the specified type of instrument and number of strings.
    * <br/><br/>
    * @param type         the type of instrument.
    * @param numStrings   the number of strings the instrument has.
    */
   public static ArrayList<String> getTuningNames(Instrument.Type type, int numStrings)
   {
      ArrayList<Tuning> tunings = getTunings(type, numStrings);
      ArrayList<String> names   = new ArrayList<String>(0);

      if (tunings != null)
      {
         for(Tuning tuning : tunings)
            names.add(tuning.getName());
      }
      return names;
   }

   /**
    * @return whether or not the tuning dictionary already contains a tuning with the specified name for the given type of instrument and number of strings.
    * <br/><br/>
    * @param name         tuning name to check for uniqueness.
    * @param type         the type of instrument.
    * @param numStrings   the number of strings the instrument has.
    */
   public static boolean isNameUnique(String name, Instrument.Type type, int numStrings)
   {
      boolean           isUnique = true;
      ArrayList<Tuning> tunings  = getTunings(type, numStrings);
      Tuning            tuning   = null;

      if (tunings != null)
      {
         for(int i=0; i<tunings.size() && isUnique; ++i)
         {
            tuning   = tunings.get(i);
            isUnique = !tuning.getName().equals(name);
         }
      }
      return isUnique;
   }

   /**
    * read in all of the tunings from the file on disk.
    * <br/><br/>
    * @throws IOException   if the file can not be opened for reading or it can not be parsed.
    */
   public static void load() throws IOException
   {
      String            filename = getFilename();
      String            line     = null;         // a single line read in from the tuning file.
      String[]          data     = null;         // the line split into fields.
      Tuning            tuning   = null;         // the fields parsed into a valid tuning
      Instrument.Type   type     = null;         // type of tuning {bass guitar or guitar}
      BufferedReader    file     = null;         // file to read in the tuning file
      ArrayList<Tuning> guitar6  = new ArrayList<Tuning>();
      ArrayList<Tuning> guitar7  = new ArrayList<Tuning>();
      ArrayList<Tuning> bass4    = new ArrayList<Tuning>();
      ArrayList<Tuning> bass5    = new ArrayList<Tuning>();
      ArrayList<Tuning> bass6    = new ArrayList<Tuning>();

      // reset the tunings
      guitarTunings = new Hashtable<Integer, ArrayList<Tuning>>();
      bassTunings   = new Hashtable<Integer, ArrayList<Tuning>>();

      // read in the tunings.dictionary file
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
            try
            {
               // read in the tuning type
               type = Instrument.getType(Integer.valueOf(data[FIELD_INSTRUMENT_TYPE]).intValue());
            }
            catch (Exception ex)
            {
               throw new IOException(ResourceBundle.format("gui.error.invalid.tuning.type", data[FIELD_INSTRUMENT_TYPE], Instrument.Type.Bass_Guitar.ordinal(), Instrument.Type.Guitar.ordinal()));
            }

            // parse the actual tuning
            tuning = parse(data);

            // add it to the list of tunings
            switch (type)
            {
               case Bass_Guitar:
                    switch (tuning.getNotes().length)
                    {
                       case 4:
                            bass4.add(tuning);
                       break;
                       case 5:
                            bass5.add(tuning);
                       break;
                       case 6:
                            bass6.add(tuning);
                       break;
                       default:
                          throw new IOException(ResourceBundle.format("gui.error.invalid.tuning.num_strings_2", type, tuning.getNotes().length, data[1]));
                    }
               break;
               case Guitar:
                    switch (tuning.getNotes().length)
                    {
                       case 6:
                            guitar6.add(tuning);
                       break;
                       case 7:
                            guitar7.add(tuning);
                       break;
                       default:
                          throw new IOException(ResourceBundle.format("gui.error.invalid.tuning.num_strings_2", type, tuning.getNotes().length, data[1]));
                    }
               break;
               default:
                  throw new IOException(ResourceBundle.format("gui.error.invalid.tuning.type", data[0], Instrument.Type.Bass_Guitar.ordinal(), Instrument.Type.Guitar.ordinal()));
            }
         }
         line = file.readLine();
      }

      guitarTunings.put(6, guitar6);
      guitarTunings.put(7, guitar7);
      bassTunings  .put(4, bass4  );
      bassTunings  .put(5, bass5  );
      bassTunings  .put(6, bass6  );
   }

   /**
    * @return the tuning parsed from the data.
    * <br/><br/>
    * the data fields are as follows:
    * 0 - type of tuning
    * 1 - name
    * 2 - number of strings
    * 3 - tuning offset
    * 4 - string 0 note
    * 5 - string 1 note
    * 6 - etc...
    * 7 - string n note
    * <br/><br/>
    * param data   fields read in from the tuning.dictionary file.
    * <br/><br/>
    * @throws IOException    if the data can not be parsed into a valid tuning.
    */
   public static Tuning parse(String[] data) throws IOException
   {
      String name;          // name of the tuning
      int    numStrings;    // number of strings the instrument has
      int    offset;        // tuning offset
      int    note;          // midi note number

      // parse the data
      name = data[FIELD_TUNING_NAME].trim();

      // read in the number of notes in the tuning
      try
      {
         numStrings = Integer.valueOf(data[FIELD_NUM_STRINGS].trim());
      }
      catch (NumberFormatException ex)
      {
         throw new IOException(ResourceBundle.format("gui.error.invalid.tuning.num_strings_1", name, data[FIELD_NUM_STRINGS]));
      }
      if (numStrings < Instrument.MIN_NUM_STRINGS || numStrings > Instrument.MAX_NUM_STRINGS)
         throw new IOException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("text.num_strings"), numStrings, Instrument.MIN_NUM_STRINGS, Instrument.MAX_NUM_STRINGS));
      if (data.length != (NUM_FIXED_FIELDS + numStrings))
         throw new IOException(ResourceBundle.format("gui.error.invalid.tuning.num_fields_in_data", name, data.length, NUM_FIXED_FIELDS + numStrings));

      // read in the tuning offset
      try
      {
         offset = Integer.valueOf(data[FIELD_TUNING_OFFSET].trim());
      }
      catch (NumberFormatException ex)
      {
         throw new IOException(ResourceBundle.format("gui.error.invalid.tuning.offset", name, numStrings, data[FIELD_TUNING_OFFSET]));
      }

      // read in the actual note tunings for each string
      // the tuning.dictionary file stores notes from low to high (string 6 to string 1)
      // while the tuning class stores them from high to low      (string 1 to string 6)
      Midi.Note[] notes = new Midi.Note[numStrings];
      for(int i=numStrings-1; i>=0; --i)
      {
         try
         {
            note = Integer.valueOf(data[NUM_FIXED_FIELDS + i].trim());
            notes[i] = Midi.getNote(note);
         }
         catch (Exception ex)
         {
            throw new IOException(ResourceBundle.format("gui.error.invalid.tuning.note", name, i, data[NUM_FIXED_FIELDS + i]));
         }
      }
      return new Tuning(name, (byte)offset, notes);
   }

   /**
    * remove the specified tuning from the tuning dictionary.
    * <br/><br/>
    * @param type     the type of instrument the tuning is for.  Valid values are Guitar and Bass_Guitar.
    * @param tuning   the tuning being removed.
    * <br/><br/>
    * @return whether the tuning dictionary contained the specified tuning.
    */
   public static boolean removeTuning(Instrument.Type type, Tuning tuning)
   {
      ArrayList<Tuning> tunings = getTunings(type, tuning.getNumStrings());

      return tunings.remove(tuning);
   }

   /**
    * save all the tunings to the file on disk.
    * <br/><br/>
    * @throws IOException   if the file can not be opened or the tunings can not be written.
    */
   public static void save() throws IOException
   {
      String                       filename       = getFilename();
      BufferedWriter               file           = null;         // file to write to the tuning file
      int                          maxNameLength  = 0;            // length of the longest tuning name
      ArrayList<ArrayList<Tuning>> allTunings     = new ArrayList<ArrayList<Tuning>>();
      String[]                     labels         = {"6 string guitar tunings", "7 string guitar tunings",
                                                     "4 string bass guitar tunings", "5 string bass guitar tunings", "6 string bass guitar tunings"};
      Instrument.Type[]            instrumentType = {Instrument.Type.Guitar, Instrument.Type.Guitar,
                                                     Instrument.Type.Bass_Guitar, Instrument.Type.Bass_Guitar, Instrument.Type.Bass_Guitar};

      // get all the tuning groups into one list that can be iterated over
      allTunings.add(guitarTunings.get(6));
      allTunings.add(guitarTunings.get(7));
      allTunings.add(bassTunings  .get(4));
      allTunings.add(bassTunings  .get(5));
      allTunings.add(bassTunings  .get(6));

      // get the length of the longest tuning name
      for(ArrayList<Tuning> tunings : allTunings)
         for(Tuning tuning : tunings)
            if (tuning.getName().length() > maxNameLength)
               maxNameLength = tuning.getName().length();


      // open the tunings.dictionary file
      file = new BufferedWriter(new FileWriter(filename));

      // write out each tuning group
      for(int i=0; i<allTunings.size(); ++i)
      {
         ArrayList<Tuning> tunings = allTunings.get(i);
         file.write(CHARACTER_COMMENT + " " + labels[i]);
         file.newLine();

         for(Tuning tuning : tunings)
         {
            // write the instrument type
            file.write(String.valueOf(instrumentType[i].ordinal()) + CHARACTER_FIELD_SEPARATOR + " ");

            // write the name of the tuning
            file.write(tuning.getName() + Utility.indent(maxNameLength - tuning.getName().length()) + CHARACTER_FIELD_SEPARATOR + " ");

            // write the number of strings
            file.write(String.valueOf(tuning.getNumStrings()) + CHARACTER_FIELD_SEPARATOR + " ");

            // write the tuning offset
            file.write(String.valueOf(tuning.getMusicNotationOffset()) + CHARACTER_FIELD_SEPARATOR + " ");

            // write the tuning
            switch (tuning.getNumStrings())
            {
               case 4: file.write(Utility.indent(8)); break;
               case 5: file.write(Utility.indent(8)); break;
               case 6: file.write(Utility.indent(4)); break;
            }
            for(int j=tuning.getNumStrings()-1; j>=0; --j)
               file.write(String.valueOf(tuning.getNotes()[j].ordinal()) + (j == 0 ? "" : CHARACTER_FIELD_SEPARATOR) + " ");
            file.newLine();
         }
         file.newLine();
      }
      file.close();
   }
}
