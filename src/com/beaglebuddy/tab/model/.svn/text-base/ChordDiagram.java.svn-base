/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a beaglebuddy tab chord diagram and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * Beaglebuddy only supports chord diagrams for the following instruments, and which must have either 6 or 7 strings:
 * <ul>
 *    <li>guitar</li>
 *    <li>keyboards</li>
 *    <li>other (treble)</li>
 *    <li>vocals</li>
 * </ul>
 * <p>
 * A chord diagram is associated with an instrument and its tuning.
 * Since the tuning of an instrument is stored in the instrument class, we only store the id of the instrument whose tuning the
 * chord diagram is associated with.
 * </p>
 * <p>
 * The reason a chord diagram needs to be associated with a tuning, is that a chord is fretted very differently depending on the
 * tuning.  Show below is an example of how a G chord would be played in various tunings:
 *
 *               standard      dropped D     open G
 *               --------      ---------     ------
 * string 1         3             3           open
 * string 2       open          open          open
 * string 3       open          open          open
 * string 4       open          open          open
 * string 5        2             2            open
 * string 6        3            open          open
 * </p>
 * <p>
 * As you can see, a chord diagram for a G chord would look very different depending on the tuning used.
 * For this reason, we store the instrument Id whose tuning will be associated with the chord diagram
 * in this class.
 * </p>
 */
public class ChordDiagram
{
   // class members
   private static final int DEFAULT_NUM_STRINGS = 6;

   // data members
   private byte            instrumentId;   // id of the instrument whose tuning is used for the chord diagram
   private String          name;           // name of the chord that appears above the chord diagram
   private Instrument.Fret topFret;        // number of the first physical fret in the chord diagram (not the fret position)
   private Fretting[]      fretting;       // fret numbers and fingering for each string in the chord (fretting[5] => fretting for string 5, etc)



   /**
    * default constructor.
    */
   public ChordDiagram()
   {
      this(DEFAULT_NUM_STRINGS);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param  numStrings   number of strings the chord diagram will display.
    */
   public ChordDiagram(int numStrings)
   {
      this((byte)-1, numStrings, Instrument.Fret.Open);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param instrumentId  id of the instrument with which this chord diagram is associated, or -1, if not yet associated with any instrument.
    * @param numStrings    number of strings the chord diagram will display.
    * @param topFret       number of the first fret in the chord diagram.
    */
   public ChordDiagram(byte instrumentId, int numStrings, Instrument.Fret topFret)
   {
      // only support 6 and 7 string chord diagrams
      assert(numStrings == 6 || numStrings == 7);

      this.instrumentId = instrumentId;
      this.name         = "";
      this.topFret      = topFret;
      this.fretting     = new Fretting[numStrings];

      for(int i=0; i<fretting.length; ++i)
         this.fretting[i] = new Fretting();
   }

   /**
    * constructor.
    * <br/><br/>
    * @param name           chord name that appears above the chord diagram.
    * @param topFret        number of the first fret in the chord diagram.
    * @param fretting       fret numbers and fingering for each string in the chord.
    */
   public ChordDiagram(String name, Instrument.Fret topFret, Fretting[] fretting)
   {
      this((byte)-1, name, topFret, fretting);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param instrumentId   id of the instrument whose tuning is used for the chord diagram.
    * @param name           chord name that appears above the chord diagram.
    * @param topFret        number of the first fret in the chord diagram.
    * @param fretting       fret numbers and fingering for each string in the chord.
    */
   public ChordDiagram(byte instrumentId, String name, Instrument.Fret topFret, Fretting[] fretting)
   {
      // only support 6 and 7 string chord diagrams
      assert(fretting != null && (fretting.length == 6 || fretting.length == 7));

      this.instrumentId = instrumentId;
      this.name         = name;
      this.topFret      = topFret;
      this.fretting     = fretting;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param chordDiagram   chord diagram whose values will be deep copied.
    */
   public ChordDiagram(ChordDiagram chordDiagram)
   {
      this.instrumentId = chordDiagram.instrumentId;
      this.name         = chordDiagram.name == null ? "" : new String(chordDiagram.name);
      this.topFret      = chordDiagram.topFret;
      this.fretting     = new Fretting[chordDiagram.fretting.length];

      for(int i=0; i<fretting.length; ++i)
         this.fretting[i] = new Fretting(chordDiagram.fretting[i]);

      // only support 6 and 7 string chord diagrams
      assert(fretting != null && (fretting.length == 6 || fretting.length == 7));
   }

   /**
    * @return the id of the instrument whose tuning is used for the chord diagram.
    */
   public byte getInstrumentId()
   {
      return instrumentId;
   }

   /**
    * sets the id of the instrument whose tuning is used for the chord diagram.
    * <br/><br/>
    * @param instrumentId   id of the instrument whose tuning is used for the chord diagram.
    */
   public void setInstrumentId(byte instrumentId)
   {
      this.instrumentId = instrumentId;
   }

   /**
    * @return the chord name that appears above the chord diagram.
    */
   public String getName()
   {
      return name;
   }

   /**
    * sets the name of the chord that appears above the chord diagram.
    * <br/><br/>
    * @param name   the name of the chord that appears above the chord diagram.
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the fret represented at the top of the chord diagram.
    */
   public Instrument.Fret getTopFret()
   {
      return topFret;
   }

   /**
    * sets the fret number at the top of the chord diagram.
    * <br/><br/>
    * @param topFret   the fret at the top of the chord diagram.
    */
   public void setTopFret(Instrument.Fret topFret)
   {
      this.topFret = topFret;
   }

   /**
    * @return the fretting for each string in the chord.
    */
   public Fretting[] getFretting()
   {
      return fretting;
   }

   /**
    * sets the fretting for each string in the chord.
    * <br/><br/>
    * @param fretting   the fretting of each string in the chord.
    */
   public void setFretting(Fretting[] fretting)
   {
      this.fretting = fretting;
   }

   /**
    * @return the list of midi notes the chord diagram is comprised of.
    * <br/><br/>
    * @param tuning      the tuning used for the instrument on which the chord diagram is based.
    * @param capo        the capo, if any, used by the instrument.
    * @param usesSharps  whether to use sharps or flats in resolving two notes that have the same pitch (ex: F# and Gb)
    */
   public Midi.Note[] getNotes(Tuning tuning, Instrument.Fret capo, boolean usesSharps)
   {
      int         numStrings = getNumStrings();
      Midi.Note[] notes      = new Midi.Note[fretting.length];

      for(int string=0; string<numStrings; ++string)
         notes[string] = Midi.getNote(Instrument.getString(string), fretting[string].getFret(), tuning, capo, usesSharps);

      return notes;
   }

   /**
    * @return the number of strings in the chord diagram.
    */
   public int getNumStrings()
   {
      return fretting.length;
   }

   /**
    * @return whether the chord diagram consists entirely of unused strings.  ie, it doesn't have any open strings or fretted notes.
    */
   public boolean isBlank()
   {
      boolean isBlank = true;

      for(int i=0; i<fretting.length && isBlank; ++i)
         isBlank = (fretting[i].getFret() == Instrument.Fret.Not_Used);

      return isBlank;
   }

   /**
    * for the purposes of this method, two chord diagrams are considered equal when they have the same fretting.
    * neither the name of the chord diagrams, their fingering, nor the instrument (tuning or capo) is considered when dertermining equality.
    * <br/><br/>
    * @return whether two durations are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof ChordDiagram)
      {
         ChordDiagram chordDiagram = (ChordDiagram)object;
         equal  = this.topFret == chordDiagram.topFret && this.fretting.length == chordDiagram.fretting.length;
         for(int i=0; i<fretting.length && equal; ++i)
            equal = this.fretting[i].getFret() == chordDiagram.fretting[i].getFret();
      }
      return equal;
   }

   /**
    * read in a chord diagram from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the chord diagram from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         instrumentId = (byte)file.read();
         name         = file.readString();
         topFret      = Instrument.getFret(file.read());

         byte numStrings = (byte)file.read();
         fretting = new Fretting[numStrings];
         for(byte i=0; i<numStrings; ++i)
         {
            fretting[i] = new Fretting();
            fretting[i].load(file);
         }

         // top fret must be at least four less than the maximum allowed = Fret_24 - 4 = Fret_20
         if (topFret.ordinal() > Instrument.Fret.Fret_20.ordinal())
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.instrument.fret_top"), topFret, 0, Instrument.Fret.Fret_20));
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord.diagram"));
      }
   }

   /**
    * save a chord diagram to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the chord diagram to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(instrumentId);
         file.writeString(name);
         file.write(topFret.ordinal());
         file.write(fretting.length);
         for(Fretting fret : fretting)
            fret.save(file);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord.diagram"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab chord diagram.
    */
   @Override
   public String toString()
   {
      return toString(9);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab chord diagram.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(ResourceBundle.getString("data_type.chord.diagram") + "\n");

      buffer.append(Utility.pad(ResourceBundle.getString("data_type.instrument.id"      ), indentation1) + instrumentId             + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.name"                    ), indentation1) + name                     + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.instrument.fret_top"), indentation1) + topFret                  + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.strings"                 ), indentation1) + fretting.length + "\n");
      for(int i=0; i<fretting.length; ++i)
      {
         buffer.append(Utility.pad(ResourceBundle.getString("text.string") + "[" + i + "] " + fretting[i], indentation2));
         buffer.append(i == fretting.length - 1 ? "" : "\n");
      }
      return buffer.toString();
   }
}
