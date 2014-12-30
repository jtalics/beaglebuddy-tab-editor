/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import com.beaglebuddy.tab.model.attribute.chord.FingerFretHand;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;




/**
 * This singleton class manages the list of predefined chords diagrams.
 * In order to save memory, be more efficient, and make the chord dictionary more manageable, only 7 string chord diagrams based on the standard tuning are stored in the dictionary.
 * However, 6 string chord diagrams are also supported by simply ignoring the bottom string of those chord diagrams stored in the chord dictionary.
 * <p>
 * Tunings other than the standard tuning are supported via the getChordDiagramNotes() method.
 * </p>
 */
public class ChordDictionary
{
   // class members
   private static final String CHARACTER_COMMENT         = "#";           // lines in the chord dictionary file that start with # are considered comments
   private static final String CHARACTER_FIELD_SEPARATOR = ",";           // data  in the chord dictionary file is delimited by commas
   private static final String FRETTING_FIELD_SEPARATOR  = "/";           // data  in the fretting field is delimited by forward slashes
   private static final int    NUM_STRINGS               = 7;             // only 7 string chord diagrams based on the standard tuning are stored in the chord dictionary.
   private static final int    FIELD_CHORD_NAME          = 0;             // index of the chord name             field in a line from the chord dictionary
   private static final int    FIELD_TOP_FRET            = 1;             // index of the chord diagram top fret field in a line from the chord dictionary
   private static final int    FIELD_FRETTING            = 2;             // index of the 1st fretting           field in a line from the chord dictionary
   private static final int    NUM_FIELDS                = 9;             // number of fields that every line in the chord dictionary file has
   private static final int    FRETTING_FIELD_FRET       = 0;             // fret   field of a fretting
   private static final int    FRETTING_FIELD_FINGER     = 1;             // finger field of a fretting

   private static ArrayList<ChordDiagram> chordDictionary = new ArrayList<ChordDiagram>();
   private static Tuning                  tuning          = TuningDictionary.getTuning(Instrument.Type.Guitar, NUM_STRINGS, TuningDictionary.TUNING_STANDARD);




   /**
    * default constructor.
    */
   private ChordDictionary()
   {
      // don't allow instances of this class to be created.
   }

   /**
    * @return the full path to the chord dictionary file on the user's hard drive.
    */
   public static String getFilename()
   {
      String tabHome = System.getProperty("tab.home");

      return tabHome + "/data/chord.dictionary";
   }

   /**
    * @return a list of possible names for the given chord diagram.
    * <br/><br/>
    * @param chordDiagram   chord diagram.
    * @param tuning         the tuning used for the instrument on which the chord diagram is based.
    * @param capo           the capo, if any, used by the instrument.
    * @param usesSharps     whether to use sharps or flats in resolving two notes that have the same pitch (ex: F# and Gb)
    */
   public static ArrayList<String> getChordNames(ChordDiagram chordDiagram, Tuning tuning, Instrument.Fret capo, boolean usesSharps)
   {
      Midi.Note[]       chordDiagramNotes    = chordDiagram.getNotes(tuning, capo, usesSharps);    // get the midi notes in the chord diagram
      Midi.Note[]       chordDictionaryNotes = null;
      int               numStrings           = chordDiagram.getNumStrings();
      boolean           possibleMatch        = false;
      ArrayList<String> names                = new ArrayList<String>();

      // todo: return the list of names sorted according to which chord dictionary diagram name matched the most down to the least

      // iterate over the chord diagrams in the chord dictionary, saving the names of those that match the given chord diagram
      for(ChordDiagram cd : chordDictionary)
      {
         chordDictionaryNotes = cd.getNotes(ChordDictionary.tuning, Instrument.Fret.Not_Used, usesSharps);
         possibleMatch        = true;
         for(int string=0; string<numStrings && possibleMatch; ++string)
         {
            if (chordDictionaryNotes[string] == null && chordDiagramNotes[string] != null)
               possibleMatch = false;
            else if (chordDictionaryNotes[string] != null && chordDiagramNotes[string] != null)
               possibleMatch = chordDictionaryNotes[string] == chordDiagramNotes[string];
         }
         if (possibleMatch && !names.contains(cd.getName()))
            names.add(cd.getName());
      }

      return names;
   }

   /**
    * @return a list of all the chord diagrams with the given name.
    * <br/><br/>
    * @param chordName   name of the chord whose chord diagrams are to be retrieved.
    */
   public static ArrayList<ChordDiagram> getChordDiagrams(String chordName)
   {
      ArrayList<ChordDiagram> chordDiagrams = new ArrayList<ChordDiagram>();

      for(ChordDiagram chordDiagram : chordDictionary)
      {
         if (chordName.equals(chordDiagram.getName()))
            chordDiagrams.add(chordDiagram);
      }
      return chordDiagrams;
   }

   /**
    * @return a list of matching chord diagrams.
    */
   public ArrayList<ChordDiagram> getMatchingChordDiagrams()
   {
      ArrayList<ChordDiagram> chordDiagrams = new ArrayList<ChordDiagram>();

      return chordDiagrams;
   }

   /**
    * read in all of the chord diagrams from the file on disk.
    * <br/><br/>
    * @throws IOException   if the file can not be opened for reading or it can not be parsed.
    */
   public static void load() throws IOException
   {
      String                  filename      = getFilename();
      BufferedReader          file          = null;         // file to read in the chord.dictionary file
      int                     lineNumber    = 0;            // line number being read from the chord.dictionary file
      String                  line          = null;         // a single line read in from the chord.dictionary file.
      String[]                data          = null;         // the line split into fields.
      ChordDiagram            chordDiagram  = null;         // the fields parsed into a valid chord diagram

      // read in the chord.dictionary file
      file = new BufferedReader(new FileReader(filename));
      line = file.readLine();
      while (line != null)
      {
         // ignore blank lines and comment lines
         line = line.trim();
         lineNumber++;
         if (line.length() != 0 && !line.startsWith(CHARACTER_COMMENT))
         {
            // split the line into its constituent fields
            data = line.split(CHARACTER_FIELD_SEPARATOR);

            // parse the chord diagram data
            chordDiagram = parse(data, lineNumber);

            // add it to the chord dictionary
            chordDictionary.add(chordDiagram);
         }
         line = file.readLine();
      }
   }

   /**
    * @return the chord diagram parsed from the data.
    * <br/><br/>
    * the data fields are as follows:
    * 1 - chord name
    * 2 - top fret
    * 3 - fretting for string 7
    * 4 - fretting for string 6
    * 5 - fretting for string 5
    * 6 - fretting for string 4
    * 7 - fretting for string 3
    * 8 - fretting for string 2
    * 9 - fretting for string 1
    * <br/><br/>
    * param data        fields read in from the chord.dictionary file.
    * param lineNumber  number of the line in the chord.dictionary file being parsed.
    * <br/><br/>
    * @throws IOException    if the data can not be parsed into a valid chord diagram.
    */
   public static ChordDiagram parse(String[] data, int lineNumber) throws IOException
   {
      int             tuningId;     // id of the tuning (see TuningDictionary class for a list of tuning ids)
      String          name;         // name of the chord
      Instrument.Fret topFret;      // top fret of the chord diagram
      Fretting[]      fretting;     // fretting of the chord diagram (which finger is used to play which fret)

      // make sure that the number of fields are correct
      if (data.length != NUM_FIELDS)
         throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.num_fields", lineNumber, data.length, NUM_FIELDS));

      // parse the name of the chord diagram
      name = data[FIELD_CHORD_NAME].trim();
      if (name.length() == 0)
         throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.name", lineNumber, data[FIELD_CHORD_NAME]));

      // read in the top fret of the chord diagram
      try
      {
         topFret = Instrument.getFret(Integer.valueOf(data[FIELD_TOP_FRET].trim()));
         if (topFret == Instrument.Fret.Not_Used)
            throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.top_fret", lineNumber, data[FIELD_TOP_FRET]));
      }
      catch (Exception ex)
      {
         throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.top_fret", lineNumber, data[FIELD_TOP_FRET]));
      }

      // read in the fretting of the chord for each string
      // the chord.dictionary file stores notes from low to high (string 6 to string 1)
      // while the chord class stores them from high to low      (string 1 to string 6)
      fretting = new Fretting[NUM_STRINGS];
      for(int i=NUM_STRINGS-1, j=0; i>=0; --i, j++)
         fretting[i] = parseFretting(data[FIELD_FRETTING + j].trim(), lineNumber);

      return new ChordDiagram(name, topFret, fretting);
   }

   /**
    * @return the fretting parsed from the data.
    * <br/><br/>
    * There are two formats for a fretting:
    * <ol>
    *    <li> ( fret )            </li>
    *    <li> ( fret / fingering) </li>
    * </ol>
    * <br/><br/>
    * @param data       fretting field read from the chord.dictionary.
    * param lineNumber  number of the line in the chord.dictionary file being parsed.
    * <br/><br/>
    * @throws IOException    if the data can not be parsed into a valid fretting.
    */
   private static Fretting parseFretting(String data, int lineNumber) throws IOException
   {
      Fretting fretting = new Fretting();

      data = data.trim();

      // parse the enclosing parentheses
      if (data.charAt(0) != '(' || data.charAt(data.length()-1) != ')')
         throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.fretting.parentheses", lineNumber, data));

      // remove the enclosing parentheses
      String frettingData = data.substring(1, data.length() - 1).trim();

      // parse the fretting field into a fret number and an optional fingering
      String[] fields = frettingData.split(FRETTING_FIELD_SEPARATOR);

      switch (fields.length)
      {
         case 2:
              fields[FRETTING_FIELD_FINGER] = fields[FRETTING_FIELD_FINGER].trim();
              if (fields[FRETTING_FIELD_FINGER].length() == 0)
                 throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.fretting.finger.blank", lineNumber, frettingData));
              try
              {
                 fretting.setFinger(FingerFretHand.getFinger(Integer.valueOf(fields[FRETTING_FIELD_FINGER])));
              }
              catch (Exception ex)
              {
                 throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.fretting.finger.value", lineNumber, frettingData));
              }
         case 1:
              fields[FRETTING_FIELD_FRET] = fields[FRETTING_FIELD_FRET].trim();
              if (fields[FRETTING_FIELD_FRET].length() == 0)
                 throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.fretting.fret.blank", lineNumber, frettingData));
              if (fields[FRETTING_FIELD_FRET].equals("x"))
              {
                 fretting.setFret(Instrument.Fret.Not_Used);
              }
              else
              {
                 try
                 {
                    fretting.setFret(Instrument.getFret(Integer.valueOf(fields[FRETTING_FIELD_FRET])));
                 }
                 catch (Exception ex)
                 {
                    throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.fretting.fret.value", lineNumber, frettingData));
                 }
              }
         break;
         default:
            throw new IOException(ResourceBundle.format("error.invalid.chord_dictionary.fretting.num_fields", lineNumber, data));
      }
      return fretting;
   }
}
