/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.instrument;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.io.IOException;




/**
 * This class represents a beaglebuddy tab drum map and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * The beaglebuddy tab editor supports drum instruments by associating a drum instrument with a drum map.
 * A drum map lets the user define a drum kit and to map a subset of the general midi percussion sounds to a set of 17 drum beat symbols.
 * The user uses the numbers [1, 17] to write out drum music on the drum tab staff, which consists of three lines:
 * <ol>
 *    <li>for percussion controlled by the feet - kick drum, pedal high hat, etc</li>
 *    <li>for percussion hit by the hands       - snare, toms, etc</li>
 *    <li>for cymbals                           - cymbals, cow bell, wood block, etc</li>
 * </ol>
 * </p>
 */
public class DrumMap
{
   // class members
   public  static final int NUM_DRUM_NOTES = 17;          // number of drum notes in map

   // data members
   private String         name;     // the name of the drum map
   private DrumMapEntry[] map;      // the actual note mappings




   /**
    * default constructor.
    */
   public DrumMap()
   {
      name = "default drum map";
      DrumMapEntry[] ini = {new DrumMapEntry(Midi.Note.A1, Midi.Percussion.AcousticBassDrum, ResourceBundle.getString("image.note.drum.0" )),   // drums
                            new DrumMapEntry(Midi.Note.B1, Midi.Percussion.LowFloorTom     , ResourceBundle.getString("image.note.drum.1" )),
                            new DrumMapEntry(Midi.Note.C2, Midi.Percussion.HighFloorTom    , ResourceBundle.getString("image.note.drum.2" )),
                            new DrumMapEntry(Midi.Note.D2, Midi.Percussion.LowMidTom       , ResourceBundle.getString("image.note.drum.3" )),
                            new DrumMapEntry(Midi.Note.E2, Midi.Percussion.AcousticSnare   , ResourceBundle.getString("image.note.drum.4" )),
                            new DrumMapEntry(Midi.Note.F2, Midi.Percussion.HiMidTom        , ResourceBundle.getString("image.note.drum.5" )),
                            new DrumMapEntry(Midi.Note.G2, Midi.Percussion.LowTom          , ResourceBundle.getString("image.note.drum.6" )),
                            new DrumMapEntry(Midi.Note.A2, Midi.Percussion.HighTom         , ResourceBundle.getString("image.note.drum.7" )),
                            new DrumMapEntry(Midi.Note.A1, Midi.Percussion.PedalHiHat      , ResourceBundle.getString("image.note.drum.8" )),   // cymbols
                            new DrumMapEntry(Midi.Note.E2, Midi.Percussion.SideStick       , ResourceBundle.getString("image.note.drum.9" )),
                            new DrumMapEntry(Midi.Note.B2, Midi.Percussion.ClosedHiHat     , ResourceBundle.getString("image.note.drum.10")),
                            new DrumMapEntry(Midi.Note.B2, Midi.Percussion.OpenHiHat       , ResourceBundle.getString("image.note.drum.11")),
                            new DrumMapEntry(Midi.Note.B2, Midi.Percussion.RideCymbal1     , ResourceBundle.getString("image.note.drum.12")),
                            new DrumMapEntry(Midi.Note.B2, Midi.Percussion.RideBell        , ResourceBundle.getString("image.note.drum.13")),
                            new DrumMapEntry(Midi.Note.B2, Midi.Percussion.CrashCymbal1    , ResourceBundle.getString("image.note.drum.14")),
                            new DrumMapEntry(Midi.Note.B2, Midi.Percussion.SplashCymbal    , ResourceBundle.getString("image.note.drum.15")),
                            new DrumMapEntry(Midi.Note.B2, Midi.Percussion.Cowbell         , ResourceBundle.getString("image.note.drum.16"))};
      map = ini;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param drumMap   drum map whose values will be deep copied.
    */
   public DrumMap(DrumMap drumMap)
   {
      this();

      if (drumMap != null)
      {
         name = new String(drumMap.getName());
         for(int i=0; i<map.length; ++i)
            this.map[i].setMidiPercussion(drumMap.map[i].getMidiPercussion());
      }
   }

   /**
    * constructor for parsing data from the tuning dictionary.
    * the data fields are as follows:
    * 0  - name of the drum map
    * 1  - midi percussion sound for note 0
    * 2  - midi percussion sound for note 1
    * 3  - etc...
    * 17 - midi percussion sound for note 16
    * <br/><br/>
    * param data   fields read in from the drum_map.dictionary file.
    * <br/><br/>
    * @throws IOException    if the data can not be parsed into a valid drum map.
    */
   public DrumMap(String[] data) throws IOException
   {
      this();

      if (data.length != (NUM_DRUM_NOTES + 1))   // num drum notes + 1 for the drum map name
         throw new IOException(ResourceBundle.format("gui.error.invalid.drum_map.num_fields_in_data", data.length));

      // parse the name
      this.name = data[0].trim();

      // parse the midi percussion values
      for(int i=0; i<map.length; ++i)
      {
         try
         {
            this.map[i].setMidiPercussion(Midi.getPercussion(Integer.valueOf(data[i+1].trim()).intValue()));
         }
         catch (Exception ex)
         {
            throw new IOException(ResourceBundle.format("gui.error.invalid.drum_map.percussion", name, i+1, data[i+1]));
         }
      }
   }

   /**
    * @return the entries in the drum map.
    */
   public DrumMapEntry[] getMap()
   {
      return map;
   }

   /**
    * @return the name of the drum map.
    */
   public String getName()
   {
      return name;
   }

   /**
    * sets the name of the drum map.
    * <br/><br/>
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the ith entry in the drum map.
    * <br/><br/>
    * @param i   the index of the desired drum map entry.
    */
   public DrumMapEntry get(int i)
   {
      return map[i];
   }

   /**
    * sets the general midi percussion sounds associated with the drum notes in the mapping.
    * <br/><br/>
    * @param midiPercussions   midi percussion sounds associated with the drum notes in the mapping.
    */
   public void setPercussionSounds(Midi.Percussion[] midiPercussions)
   {
      assert(midiPercussions != null);
      assert(midiPercussions.length == NUM_DRUM_NOTES);

      for(int i=0; i<midiPercussions.length; ++i)
         this.map[i].setMidiPercussion(midiPercussions[i]);
   }

   /**
    * @return whether two drum maps are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
  public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof DrumMap)
      {
         DrumMap drumMap = (DrumMap)object;
         equal = this.name.equals(drumMap.name) && this.map.length == drumMap.map.length;
         for(int i=0; i<map.length && equal; ++i)
            equal = this.map[i].equals(drumMap.map[i]);
      }
      return equal;
   }

   /**
    * read in a drum map from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the drum map from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         this.name = file.readString();
         short n = file.readShort();

         if (name == null || name.trim().length() == 0)
            throw new IllegalArgumentException(ResourceBundle.getString("error.invalid.drum_map_name"));
         if (n != NUM_DRUM_NOTES)
            throw new FileReadException(ResourceBundle.format("error.invalid.drum_map", n, NUM_DRUM_NOTES));

         for(short i=0; i<n; ++i)
            map[i].setMidiPercussion(Midi.getPercussion(file.read()));
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.instrument.midi_drum_mappping"));
      }
   }

   /**
    * save a drum map to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the drum map to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeString(this.name);
         file.writeShort((short)map.length);
         for(short i=0; i<map.length; ++i)
            file.write(map[i].getMidiPercussion().id());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.instrument.midi_drum_mappping"));
      }
   }

   /**
    * @return a string representation of the drum map.
    */
   @Override
  public String toString()
   {
      return toString(9);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the drum map.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(Utility.pad(ResourceBundle.getString("data_type.instrument.midi_drum_mappping"), indentation1) + map.length + "\n");

      for(int i=0; i<map.length; ++i)
         buffer.append(Utility.pad("entry [" + i + (i<10 ? " " : "") + "]", indentation2) + map[i] + (i == map.length - 1 ? "" : "\n"));

      return buffer.toString();
   }
}
