/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.song;

import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.Information;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Score;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.staff.Staff;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;





/**
 * this class contains methods for laying out and validating the music.
 */
public class Song extends MidiSong
{
   /**
    * default constructor.
    */
   public Song()
   {
      // create a song with one section with one staff
      Section section = new Section(new Rectangle(50, 20, 750, 129), (short)20, (short)0, (short)0, (short)0, null, null, null);
      Staff   staff   = new Staff(Staff.Clef.Treble, (byte)6, (byte)15, (byte)9, (byte)17, (byte)0, null, null);

      section.getStaffs().add(staff);
      score.getSections().add(section);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param information    information about the song.
    * @param chordDiagrams  chord diagrams for the score.
    * @param score          the music for the song.
    */
   public Song(Information information, ArrayList<ChordDiagram> chordDiagrams, Score score)
   {
      super(information, chordDiagrams, score);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param filename   name of the beaglebuddy tab (.bbt) file
    * <br/><br/>
    * @throws FileReadException   if the specified beaglebuddy tab (.bbt) file can not be loaded.
    */
   public Song(String filename) throws FileReadException
   {
      super(filename);
   }

   /**
    * formats the song, setting the drawing positions of all the notes and music symbols as well as setting the spacing between them.
    */
   public void layout()
   {
      getScore().layout();
   }

   /**
    * validates the song, verifying that:
    * <ol>
    *    <li>each measure has the correct duration according to its time signatue</li>
    *    <li>each repeat bar line has a matching begin and end</li>
    *    <li>todo: other checks</li>
    * </ol>
    * Thus, this method acts as a <i>compiler</i> of sorts for the song.
    * <br/><br/>
    * @return a list of the errors found and their corresponding locations.
    */
   public Hashtable<String, Location> validate()
   {
      return getScore().validate();
   }
}
