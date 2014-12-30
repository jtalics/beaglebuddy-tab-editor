/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.instrument;

import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * maps an individual drum note to a midi percussion sound.
 */
public class DrumMapEntry
{
   // data members
   private Midi.Note        midiNote;          // indicates where the note is drawn on the staff
   private Midi.Percussion  midiPercussion;    // what midi percussion sound to play
   private String           noteImagePath;     // path to the note body's image for drawing



   /**
    * constructor.
    * <br/><br/>
    * @param midiNote           indicates where the note is drawn on the staff.
    * @param midiPercussion     what midi percussion sound to play.
    * @param noteImagePath      path to the note body's image for drawing.
    */
   public DrumMapEntry(Midi.Note midiNote, Midi.Percussion  midiPercussion, String noteImagePath)
   {
      this.midiNote       = midiNote;
      this.midiPercussion = midiPercussion;
      this.noteImagePath  = new String(noteImagePath);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param drumMapEntry  drum map entry whose values will be deep copied.
    */
   public DrumMapEntry(DrumMapEntry drumMapEntry)
   {
      this.midiNote       = drumMapEntry.midiNote;
      this.midiPercussion = drumMapEntry.midiPercussion;
      this.noteImagePath  = drumMapEntry.noteImagePath;
   }

   /**
    * @return the midi note.
    */
   public Midi.Note getMidiNote()
   {
      return midiNote;
   }

   /**
    * @return the midi percussion sound.
    */
   public Midi.Percussion getMidiPercussion()
   {
      return midiPercussion;
   }

   /**
    * sets the midi percussion sound.
    * <br/><br/>
    * @param midiPercussion   the midi percussion sound to play.
    */
   public void setMidiPercussion(Midi.Percussion midiPercussion)
   {
      this.midiPercussion = midiPercussion;
   }

   /**
    * @return the path to the note's image file.
    */
   public String getNoteImagePath()
   {
      return noteImagePath;
   }

   /**
    * @return whether two drum map entries are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
  public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof DrumMapEntry)
      {
         DrumMapEntry drumMapEntry = (DrumMapEntry)object;
         equal = this.midiNote == drumMapEntry.midiNote && this.midiPercussion == drumMapEntry.midiPercussion && this.noteImagePath.equals(noteImagePath);
      }
      return equal;
   }

   /**
    * @return a string representation of the drum map entry.
    */
   @Override
  public java.lang.String toString()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(ResourceBundle.getString("data_type.midi.note") + ": " + midiNote.description() +  ", " + ResourceBundle.getString("data_type.midi.percussion") + ": " + midiPercussion.toString());

      return buffer.toString();
   }
}
