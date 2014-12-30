/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.name;

import com.beaglebuddy.tab.model.Accidental;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a beaglebuddy tab chord name and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class ChordName
{
   // enums
   public enum Extension
   {
      None(""), Extended9th("9"), Extended11th("11"), Extended13th("13");

      // data members
      private String text;
      Extension(String text)   {this.text = text;}
      public String text()     {return text;}
      @Override
      public String toString() {return text;}
   };

   public enum ChordType
   {
      Major                     (ResourceBundle.getString("text.chord_type.major"            ), ""      ),
      Minor                     (ResourceBundle.getString("text.chord_type.minor"            ), "m"     ),
      Augmented                 (ResourceBundle.getString("text.chord_type.augmented"        ), "+"     ),
      Diminished                (ResourceBundle.getString("text.chord_type.diminished"       ), "°"     ),
      Suspended2nd              (ResourceBundle.getString("text.chord_type.suspended.2nd"    ), "sus2"  ),
      Suspended4th              (ResourceBundle.getString("text.chord_type.suspended.4th"    ), "sus4"  ),
      Power5th                  (ResourceBundle.getString("text.chord_type.5th"              ), "5"     ),
      Major6th                  (ResourceBundle.getString("text.chord_type.6th.major"        ), "6"     ),
      Minor6th                  (ResourceBundle.getString("text.chord_type.6th.minor"        ), "m6"    ),
      Dominant7th               (ResourceBundle.getString("text.chord_type.7th.dominant"     ), "7"     ),
      Major7th                  (ResourceBundle.getString("text.chord_type.7th.major"        ), "maj7"  ),
      Minor7th                  (ResourceBundle.getString("text.chord_type.7th.minor"        ), "m7"    ),
      MinorMajor7th             (ResourceBundle.getString("text.chord_type.7th.min_maj"      ), "m/maj7"),
      Augmented7th              (ResourceBundle.getString("text.chord_type.7th.augmented"    ), "+7"    ),
      Diminished7th             (ResourceBundle.getString("text.chord_type.7th.diminished"   ), "°7"    ),
      Dominant7thSuspended2nd   (ResourceBundle.getString("text.chord_type.7th.suspended.2nd"), "7sus2" ),
      Dominant7thSuspended4th   (ResourceBundle.getString("text.chord_type.7th.suspended.4th"), "7sus4" );

      // data members
      private String type;
      private String abbreviation;
      ChordType(String type, String abbreviation)     {this.type = type; this.abbreviation = abbreviation;}
      public String type()          {return type;}
      public String abbreviation()  {return abbreviation;}
      @Override
      public String toString() {return abbreviation;}
   }





   // data members
   private String     rootNote;          // root note of chord  (ex: C/E  : C is the tonic, E is the bass note)
   private String     bassNote;          //
   private ChordType  chordType;         // the main type of chord.
   private Extension  extension;         // whether the chord is an extension of a type of 7th chord, and if so, which note is added.
   private boolean    add2nd;            // whether the  2nd note of the chord is added.
   private boolean    add4th;            // whether the  4th note of the chord is added.
   private boolean    add6th;            // whether the  6th note of the chord is added.
   private boolean    add9th;            // whether the  9th note of the chord is added.
   private boolean    add11th;           // whether the 11th note of the chord is added.
   private Accidental alt5th;            // whether the  5th note of the chord is altered (raised or lowered)
   private Accidental alt9th;            // whether the  9th note of the chord is altered (raised or lowered)
   private Accidental alt11th;           // whether the 11th note of the chord is altered (raised or lowered)
   private Accidental alt13th;           // whether the 13th note of the chord is altered (raised or lowered)
   private String     fret;              // the optional fret ("I", "II", ..., "XX)
   private String     type;              // the optional type ("type 2", "type 3", ..., "type 8")





   /**
    * default constructor.
    */
   public ChordName()
   {
      this.rootNote  = "C";
      this.bassNote  = "C";
      this.chordType = ChordType.Major;
      this.extension = Extension.None;
      this.add2nd    = false;
      this.add4th    = false;
      this.add6th    = false;
      this.add9th    = false;
      this.add11th   = false;
      this.alt5th    = Accidental.Natural;
      this.alt9th    = Accidental.Natural;
      this.alt11th   = Accidental.Natural;
      this.alt13th   = Accidental.Natural;
      this.fret      = "";
      this.type      = "";
   }

   /**
    * constructor.
    * <br/><br/>
    * tokens: ^[root note]([chord type] || [chord type(len-1)][extension] || [extension][susX] || [extension]) [addX]* || [alteredX]* || [/bass note]? || [fret]? || [type]?$
    * <br/><br/>
    * see the javadocs for java.util.regex.Pattern for regular expression syntax.
    * ^  - beginning of line
    * $  - end of line
    * X  - once and required
    * X? - once or not at all
    * X* - zero or more times
    * <br/><br/>
    * @param chordName   name of the chord.
    */
   public ChordName(String chordName)
   {
      this();

      // parse the name into its constituent parts
      if (chordName != null && chordName.trim().length() != 0)
      {
                 chordName = chordName.trim();             // chord name with all pre and post whitespace characters removed
         int     index     = 0;                            // parser position within the chord name
         boolean error     = false;                        // whether the parser has detected an invalid chord name

         // get the root note
         rootNote = parseNote(chordName, index);
         if (rootNote == null)
         {
            System.err.println("Unable to parse [root note] from " + chordName);
            error = true;
         }
         else
         {
            index += rootNote.length();
         }

         // next, there are four possible valid tokens that can occur
         if (!error)
         {
            String chordType = null;
            String extension = null;
            String suspended = null;

            // try parsing [extension][susX]
            if ((extension = parseExtension(chordName, index)) != null && (suspended = parseSuspension(chordName, index + extension.length())) != null)
            {
               this.chordType = getChordType("7" + suspended);
               this.extension = getExtension(extension);
               index += extension.length() + suspended.length();
            }
            // try parsing [chord type(len-1)][extension]
            else if ((chordType = parse7thChordType(chordName, index)) != null && (extension = parseExtension(chordName, index + chordType.length())) != null)
            {
               this.chordType = getChordType(chordType + "7");
               this.extension = getExtension(extension);
               index += chordType.length() + extension.length();
            }
            // try parsing [chord type]
            else if ((chordType = parseChordType(chordName, index)) != null)
            {
               this.chordType = getChordType(chordType);
               this.extension = Extension.None;
               index += chordType.length();
            }
            // try parsing [extension]
            else if ((extension = parseExtension(chordName, index)) != null)
            {
               this.chordType = ChordType.Dominant7th;
               this.extension = getExtension(extension);
               index += extension.length();
            }
            // major chord
            else
            {
               this.chordType = ChordType.Major;
               this.extension = Extension.None;
            }
         }

         // try parsing [addX]*
         if (!error)
         {
            String add = null;
            while ((add = parseAddition(chordName, index)) != null)
            {
               setAddition(add);
               index += add.length();
            }
         }

         // try parsing [alteredX]*
         if (!error)
         {
            String altered = null;
            while ((altered = parseAlteration(chordName, index)) != null)
            {
               setAlteration(altered);
               index += altered.length();
            }
         }

         // try parsing [/bass note]?
         if (index + 2 <= chordName.length() && chordName.charAt(index)=='/')
         {
            bassNote = parseNote(chordName, index+1);
            if (bassNote != null)
               index += 1 + bassNote.length();
         }
         else
         {
            bassNote = rootNote;
         }

         // try parsing [fret]?
         fret = parseFret(chordName, index);
         if (fret != null)
            index += fret.length();

         // try parsing [type]?
         type = parseType(chordName, index);
         if (type != null)
            index += type.length() + 2;         // include 2 characters extra for parentheses '(type 5)'

         // we should reached the end of the chord name
         if (index != chordName.length())
            System.err.println("characters left over in chord name at position " + index + ": " + chordName);
      }
   }

   /**
    * constructor.
    * <br/><br/>
    * @param rootNote    the root note of the chord.
    * @param bassNote    the bass note of the chord.
    * @param chordType   the main type of chord.
    * @param extension   whether the chord is an extension of a type of 7th chord, and if so, which note is added.
    * @param add2nd      whether the  2nd note of the chord is added.
    * @param add4th      whether the  4th note of the chord is added.
    * @param add6th      whether the  6th note of the chord is added.
    * @param add9th      whether the  9th note of the chord is added.
    * @param add11th     whether the 11th note of the chord is added.
    * @param alt5th      whether the  5th note of the chord is altered (raised or lowered)
    * @param alt9th      whether the  9th note of the chord is altered (raised or lowered)
    * @param alt11th     whether the 11th note of the chord is altered (raised or lowered)
    * @param alt13th     whether the 13th note of the chord is altered (raised or lowered)
    * @param fret        the optional fret ("I", "II", ..., "XX)
    * @param type        the optional type ("type 2", "type 3", ..., "type 8")
    */
   public ChordName(String rootNote, String bassNote, ChordType chordType, Extension extension,
                    boolean add2nd, boolean add4th, boolean add6th, boolean add9th, boolean add11th,
                    Accidental alt5th, Accidental alt9th, Accidental alt11th, Accidental alt13th,
                    String fret, String type)
   {
      this.rootNote  = rootNote;
      this.bassNote  = bassNote;
      this.chordType = chordType;
      this.extension = extension;
      this.add2nd    = add2nd;
      this.add4th    = add4th;
      this.add6th    = add6th;
      this.add9th    = add9th;
      this.add11th   = add11th;
      this.alt5th    = alt5th;
      this.alt9th    = alt9th;
      this.alt11th   = alt11th;
      this.alt13th   = alt13th;
      this.fret      = fret;
      this.type      = type;
   }

   /**
    * @return the root note of the chord.
    */
   public String getRootNote()
   {
      return rootNote;
   }

   /**
    * @return the bass note of the chord.
    */
   public String getBassNote()
   {
      return bassNote;
   }

   /**
    * @return the main type of chord.
    */
   public ChordType getChordType()
   {
      return chordType;
   }

   /**
    * @param chordType  the string chord type.
    * <br/><br/>
    * @return the chord type enum corresponding to the string chord type.
    */
   public static ChordType getChordType(String chordType)
   {
      for (ChordType ct : ChordType.values())
         if (ct.abbreviation().equals(chordType))
            return ct;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.chord.type"), chordType, ChordType.Dominant7thSuspended4th.ordinal()));
   }

   /**
    * @param chordType  the integer chord type.
    * <br/><br/>
    * @return the chord type enum corresponding to the integer chord type.
    */
   public static ChordType getChordType(int chordType)
   {
      for (ChordType ct : ChordType.values())
         if (chordType == ct.ordinal())
            return ct;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.chord.type"), chordType, ChordType.Dominant7thSuspended4th.ordinal()));
   }

   /**
    * @return a list of the chord type names.
    */
   public static String[] getChordTypeNames()
   {
      String[] chordTypeNames = new String[ChordType.values().length];

      for(ChordType chordType : ChordType.values())
         chordTypeNames[chordType.ordinal()] = chordType.type();

      return chordTypeNames;
   }

   /**
    * @return a list of the chord type abbreviations.
    */
   public static String[] getChordTypeAbbreviations()
   {
      String[] chordTypeAbbreviations = new String[ChordType.values().length];

      for(ChordType chordType : ChordType.values())
         chordTypeAbbreviations[chordType.ordinal()] = chordType.abbreviation();

      return chordTypeAbbreviations;
   }

   /**
    * @return whether the chord has a 7th.
    */
   public boolean has7th()
   {
      return chordType == ChordType.Dominant7th || chordType == ChordType.Major7th || chordType == ChordType.Minor7th || chordType == ChordType.MinorMajor7th || chordType == ChordType.Augmented7th || chordType == ChordType.Diminished7th || chordType == ChordType.Dominant7thSuspended2nd || chordType == ChordType.Dominant7thSuspended4th;
   }

   /**
    * @return whether the chord is an extension of a type of 7th chord, and if so, which note is added.
    */
   public Extension getExtension()
   {
      return extension;
   }

   /**
    * @param extension  the string extension.
    * <br/><br/>
    * @return the extension enum corresponding to the string extension.
    */
   public static Extension getExtension(String extension)
   {
      for (Extension ext : Extension.values())
         if (ext.text().equals(extension))
            return ext;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.extension"), extension, Extension.Extended13th.ordinal()));
   }

   /**
    * @param extension  the integer extension.
    * <br/><br/>
    * @return the extension enum corresponding to the integer extension.
    */
   public static Extension getExtension(int extension)
   {
      for (Extension ext : Extension.values())
         if (extension == ext.ordinal())
            return ext;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.extension"), extension, Extension.Extended13th.ordinal()));
   }

   /**
    * sets the addition data member corresponding to the string addition to true.
    * <br/><br/>
    * @param addition  the string addition.
    */
   public void setAddition(String addition)
   {
      assert(addition != null);

           if (addition.equals("add2" )) add2nd  = true;
      else if (addition.equals("add4" )) add4th  = true;
      else if (addition.equals("add6" )) add6th  = true;
      else if (addition.equals("add7" )) add9th  = true;
      else if (addition.equals("add9" )) add9th  = true;
      else if (addition.equals("add11")) add11th = true;
      else
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.chord_name.addition", addition));
   }

   /**
    * sets the alteration data member corresponding to the string alteration to true.
    * <br/><br/>
    * @param alteration  the string alteration.
    */
   public void setAlteration(String alteration)
   {
      assert(alteration != null);

           if (alteration.equals( "+5")) alt5th  = Accidental.Sharp;
      else if (alteration.equals( "-5")) alt5th  = Accidental.Flat ;
      else if (alteration.equals( "+9")) alt9th  = Accidental.Sharp;
      else if (alteration.equals( "-9")) alt9th  = Accidental.Flat ;
      else if (alteration.equals("+11")) alt11th = Accidental.Sharp;
      else if (alteration.equals("-11")) alt11th = Accidental.Flat ;
      else if (alteration.equals("+13")) alt13th = Accidental.Sharp;
      else if (alteration.equals("-13")) alt13th = Accidental.Flat ;
      else
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.chord_name.alteration", alteration));
   }

   /**
    * @return whether the 2nd note of the chord is added.
    */
   public boolean getAdd2nd()
   {
      return add2nd;
   }

   /**
    * @return whether the 4th note of the chord is added.
    */
   public boolean getAdd4th()
   {
      return add4th;
   }

   /**
    * @return whether the 6th note of the chord is added.
    */
   public boolean getAdd6th()
   {
      return add6th;
   }

   /**
    * @return whether the 9th note of the chord is added.
    */
   public boolean getAdd9th()
   {
      return add9th;
   }

   /**
    * @return whether the 11th note of the chord is added.
    */
   public boolean getAdd11th()
   {
      return add11th;
   }

   /**
    * @return whether the 5th note of the chord is altered (raised or lowered)
    */
   public Accidental getAlt5th()
   {
      return alt5th;
   }

   /**
    * @return whether the 9th note of the chord is altered (raised or lowered)
    */
   public Accidental getAlt9th()
   {
      return alt9th;
   }

   /**
    * @return whether the 11th note of the chord is altered (raised or lowered)
    */
   public Accidental getAlt11th()
   {
      return alt11th;
   }

   /**
    * @return whether the 13th note of the chord is altered (raised or lowered)
    */
   public Accidental getAlt13th()
   {
      return alt13th;
   }

   /**
    * @return the optional fret ("I", "II", ..., "XX).
    */
   public String getFret()
   {
      return fret;
   }

   /**
    * @return the optional type ("type 2", "type 3", ..., "type 8").
    */
   public String getType()
   {
      return type;
   }

   /**
    * parse an addition from the chord name at the specified position.
    * <br/><br/>
    * @return a valid addition if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parseAddition(String chordName, int index)
   {
      String addition = null;

      if (index + 4 <= chordName.length())
      {
         String add = chordName.substring(index, index + 4);
         if (add.equals("add2") || add.equals("add4") || add.equals("add6") || add.equals("add9") ||
            (index + 5 <= chordName.length() && (add = chordName.substring(index, index + 5)).equals("add11")))
            addition = add;
      }
      return addition;
   }

   /**
    * parse an alteration from the chord name at the specified position.
    * <br/><br/>
    * @return a valid alteration if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parseAlteration(String chordName, int index)
   {
      String alteration = null;

      if (index + 2 <= chordName.length())
      {
         String altered = chordName.substring(index, index + 2);
         if (altered.equals("+5") || altered.equals("-5") || altered.equals("+9") || altered.equals("-9"))
            alteration = altered;
         else if (index + 3 <= chordName.length())
         {
            altered = chordName.substring(index, index + 3);
            if (altered.equals("+11") || altered.equals("-11") || altered.equals("+13") || altered.equals("-13"))
               alteration = altered;
         }
      }
      return alteration;
   }

   /**
    * parse the 7th chord type from the chord name at the specified position.
    * <br/><br/>
    * @return a valid chord type if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parse7thChordType(String chordName, int index)
   {
      // list the valid chord types in string length order, from longest to shortest
      String[] abbreviations = {ChordType.MinorMajor7th.abbreviation().substring(0, ChordType.MinorMajor7th.abbreviation().length() - 1),
                                ChordType.Major7th     .abbreviation().substring(0, ChordType.Major7th     .abbreviation().length() - 1),
                                ChordType.Augmented7th .abbreviation().substring(0, ChordType.Augmented7th .abbreviation().length() - 1),
                                ChordType.Diminished7th.abbreviation().substring(0, ChordType.Diminished7th.abbreviation().length() - 1),
                                ChordType.Minor7th     .abbreviation().substring(0, ChordType.Minor7th     .abbreviation().length() - 1),
                                ChordType.Dominant7th  .abbreviation().substring(0, ChordType.Dominant7th  .abbreviation().length() - 1)};

      String chordType = null;

      for(int i=0; i<abbreviations.length && chordType == null; ++i)
         if (index + abbreviations[i].length() + 1 <= chordName.length() && abbreviations[i].equals(chordName.substring(index, index + abbreviations[i].length())))
            chordType = abbreviations[i];

      return chordType;
   }

   /**
    * parse the chord type from the chord name at the specified position.
    * <br/><br/>
    * @return a valid chord type if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parseChordType(String chordName, int index)
   {
      // list the valid chord types in string length order, from longest to shortest
      String[] abbreviations = {ChordType.MinorMajor7th          .abbreviation(),
                                ChordType.Dominant7thSuspended2nd.abbreviation(),
                                ChordType.Dominant7thSuspended4th.abbreviation(),
                                ChordType.Suspended2nd           .abbreviation(),
                                ChordType.Suspended4th           .abbreviation(),
                                ChordType.Major7th               .abbreviation(),
                                ChordType.Augmented7th           .abbreviation(),
                                ChordType.Diminished7th          .abbreviation(),
                                ChordType.Minor6th               .abbreviation(),
                                ChordType.Minor7th               .abbreviation(),
                                ChordType.Minor                  .abbreviation(),
                                ChordType.Augmented              .abbreviation(),
                                ChordType.Diminished             .abbreviation(),
                                ChordType.Power5th               .abbreviation(),
                                ChordType.Major6th               .abbreviation(),
                                ChordType.Dominant7th            .abbreviation()};

      String chordType = null;

      for(int i=0; i<abbreviations.length && chordType == null; ++i)
      {
         if (index + abbreviations[i].length() <= chordName.length() && abbreviations[i].equals(chordName.substring(index, index + abbreviations[i].length())))
         {
            // this is a littl bit of a hack.
            // the problem is that the abbreviation used for augmented chords, namely the plus sign '+', is also used for altered notes that are raised (sharped).
            // thus, G+5 is not an augmented chord, its actually a G Major chord with an altered, raised 5th.
            // but, G++9 is an augmented chord, and has a raised 9th as well.
             if (abbreviations[i].equals(ChordType.Augmented.abbreviation()))
             {
                 if (index + abbreviations[i].length() + 1 > chordName.length() || !Character.isDigit(chordName.charAt(index + abbreviations[i].length())))
                   chordType = abbreviations[i];
             }
             else
             {
                chordType = abbreviations[i];
             }
         }
      }
      return chordType;
   }

   /**
    * parse an extension from the chord name at the specified position.
    * <br/><br/>
    * @return a valid extension if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parseExtension(String chordName, int index)
   {
      String extension = null;

      if (index < chordName.length())
      {
         if (chordName.charAt(index) == '9')
         {
            extension = "9";
         }
         else if (index + 1 <= chordName.length())
         {
            if (chordName.charAt(index) == '1' && chordName.charAt(index + 1) == '1')
               extension = "11";
            else if (chordName.charAt(index) == '1' && chordName.charAt(index + 1) == '3')
               extension = "13";
         }
      }
      return extension;
   }

   /**
    * parse the fret from the chord name at the specified position.
    * <br/><br/>
    * @return a valid fret if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parseFret(String chordName, int index)
   {
      // list the valid frets in string length order, from longest to shortest
      String[] frets = {"XVIII", "open", "XVII", "XIII", "VIII", "III", "VII", "XII", "XIV", "XVI", "XIX", "II", "IV", "VI", "XX", "XV", "IX", "XI", "I", "V", "X"};
      String   fret  = null;

      for(int i=0; i<frets.length && fret == null; ++i)
         if (index + frets[i].length() <= chordName.length() && chordName.substring(index, index + frets[i].length()).equals(frets[i]))
            fret = frets[i];

      return fret;
   }

   /**
    * parse a note name from the chord name at the specified position.
    * <br/><br/>
    * @return a valid note name if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parseNote(String chordName, int index)
   {
      StringBuffer buffer = new StringBuffer();
      char[]       notes  = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

      if (index < chordName.length())
      {
         boolean found = false;
         char    note  = chordName.charAt(index);

         for(int i=0; i<notes.length && !found; ++i)
            found = note == notes[i];

         if (found)
         {
            buffer.append(note);
            if (index + 1 < chordName.length() && (chordName.charAt(index + 1) == '#' || chordName.charAt(index + 1) == 'b'))
               buffer.append(chordName.charAt(index + 1));
         }
      }
      return buffer.length() == 0 ? null : buffer.toString();
   }

   /**
    * parse a suspension from the chord name at the specified position.
    * <br/><br/>
    * @return a valid suspension if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parseSuspension(String chordName, int index)
   {
      String suspension = null;

      if (index + 4 <= chordName.length())
      {
         String sus = chordName.substring(index, index + 4);
         if (sus.equals("sus2") || sus.equals("sus4"))
            suspension = sus;
      }
      return suspension;
   }

   /**
    * parse the type from the chord name at the specified position.
    * <br/><br/>
    * @return a valid type if one can be parsed and null otherwise.
    * <br/><br/>
    * @param chordName  the chord name to parse.
    * @param index      the position within the chord name where the parsing will start.
    */
   private String parseType(String chordName, int index)
   {
      // list the valid types in string length order, from longest to shortest
      String[] types = {"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "2", "3", "4", "5", "6", "7", "8", "9"};
      String   type  = null;

      if (index + 8 <= chordName.length() && chordName.substring(index, index + 6).equals("(type "))
      {
         for(int i=0; i<types.length && type == null; ++i)
            if (index + 6 + types[i].length() <= chordName.length()                            &&
                chordName.substring(index + 6, index + 6 + types[i].length()).equals(types[i]) &&
                chordName.charAt(index + 6 + types[i].length()) == ')')
               type = "type " + types[i];
      }
      return type;
   }

   /**
    * @return a string representation of the chord name.
    */
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(toStringWithoutFretOrType());

      if (fret != null && !fret.isEmpty())
         buffer.append(fret);
      if (type != null && !type.isEmpty())
         buffer.append("(" + type + ")");

      return buffer.toString();
   }

   /**
    * @return a string representation of the chord name, but removes any fret or type information at the end of the chord name.
    * <code>
    * exmple: A(type 8) -> A
    *         AmVIII    -> Am
    * </code>
    */
   public String toStringWithoutFretOrType()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(rootNote);

      if (extension == Extension.None)
      {
         buffer.append(chordType.abbreviation());
      }
      else
      {
         switch (chordType)
         {
            case Dominant7thSuspended2nd:
            case Dominant7thSuspended4th:
                 buffer.append(extension);
                 buffer.append(chordType.abbreviation().substring(1));
            break;
            case Dominant7th:
                 buffer.append(extension);
            break;
            default:
                 buffer.append(chordType.abbreviation().substring(0, chordType.abbreviation().length()-1));
                 buffer.append(extension);
            break;
         }
      }

      if (add2nd ) buffer.append("add2" );
      if (add4th ) buffer.append("add4" );
      if (add6th ) buffer.append("add6" );
      if (add9th ) buffer.append("add9" );
      if (add11th) buffer.append("add11");

      switch (alt5th)
      {
         case Natural:                        break;
         case Flat:     buffer.append("-5" ); break;
         case Sharp:    buffer.append("+5" ); break;
      }
      switch (alt9th)
      {
         case Natural:                        break;
         case Flat:     buffer.append("-9" ); break;
         case Sharp:    buffer.append("+9" ); break;
      }
      switch (alt11th)
      {
         case Natural:                        break;
         case Flat:     buffer.append("-11"); break;
         case Sharp:    buffer.append("+11"); break;
      }
      switch (alt13th)
      {
         case Natural:                        break;
         case Flat:     buffer.append("-13"); break;
         case Sharp:    buffer.append("+13"); break;
      }

      if (!rootNote.equals(bassNote))
         buffer.append("/" + bassNote);

      return buffer.toString();
   }
}
