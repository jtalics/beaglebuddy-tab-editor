/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.attribute.chord.*;
import com.beaglebuddy.tab.model.attribute.note.NoteAttribute;
import com.beaglebuddy.tab.model.attribute.duration.DurationAttribute;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.Hashtable;




/**
 * This class represents a beaglebuddy chord and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * This chord class represents more objects than just a musical chord.
 * It can represent the following four musical objects:
 * <table border="1" cellspacing="10" cellpadding="10">
 *    <tr><td>1.</td><td>Rest          </td><td>Chord with no notes                                     </td></tr>
 *    <tr><td>2.</td><td>Multi bar Rest</td><td>Chord with no notes and a single multibar rest attribute</td></tr>
 *    <tr><td>3.</td><td>Single Note   </td><td>Chord with one note                                     </td></tr>
 *    <tr><td>4.</td><td>Chord         </td><td>Chord with more than one note                           </td></tr>
 * </table>
 * <p>
 * Note attributes are those that can be applied to a single note within a chord, while the chord attributes are those that apply to all the notes in the chord.
 * </p>
 */
public class Chord extends Duration
{
   // class members
   private static Hashtable<ChordAttribute.Type, ChordAttribute> map = new Hashtable<ChordAttribute.Type, ChordAttribute>();
   static
   {
      map.put(ChordAttribute.Type.AccentMarcato     , new AccentMarcato     ());
      map.put(ChordAttribute.Type.AccentSforzando   , new AccentSforzando   ());
      map.put(ChordAttribute.Type.Acciaccatura      , new Acciaccatura      ());
      map.put(ChordAttribute.Type.Appoggiatura      , new Appoggiatura      ());
      map.put(ChordAttribute.Type.Arpeggio          , new Arpeggio          ());
      map.put(ChordAttribute.Type.ChordName         , new ChordName         ());
      map.put(ChordAttribute.Type.Fermata           , new Fermata           ());
      map.put(ChordAttribute.Type.FingerBass        , new FingerBass        ());
      map.put(ChordAttribute.Type.FingerFretHand    , new FingerFretHand    ());
      map.put(ChordAttribute.Type.FingerPickHand    , new FingerPickHand    ());
      map.put(ChordAttribute.Type.LetRing           , new LetRing           ());
      map.put(ChordAttribute.Type.MultibarRestBegin , new MultibarRestBegin ());
      map.put(ChordAttribute.Type.MultibarRestMiddle, new MultibarRestMiddle());
      map.put(ChordAttribute.Type.MultibarRestEnd   , new MultibarRestEnd   ());
      map.put(ChordAttribute.Type.PalmMuting        , new PalmMuting        ());
      map.put(ChordAttribute.Type.PickStroke        , new PickStroke        ());
      map.put(ChordAttribute.Type.Staccato          , new Staccato          ());
      map.put(ChordAttribute.Type.Tap               , new Tap               ());
      map.put(ChordAttribute.Type.Text              , new Text              ());
      map.put(ChordAttribute.Type.TremoloBar        , new TremoloBar        ());
      map.put(ChordAttribute.Type.TremoloPicking    , new TremoloPicking    ());
      map.put(ChordAttribute.Type.TripletFeel1st    , new TripletFeel1st    ());
      map.put(ChordAttribute.Type.TripletFeel2nd    , new TripletFeel2nd    ());
      map.put(ChordAttribute.Type.Vibrato           , new Vibrato           ());
      map.put(ChordAttribute.Type.VolumeSwell       , new VolumeSwell       ());
   }

   // data members
   private Beam                      beam;             // beam information
   private ArrayList<Note>           notes;            // notes for the chord
   private ArrayList<ChordAttribute> attributes;       // attributes of the chord




   /**
    * default constructor.
    */
   public Chord()
   {
      beam       = new Beam();
      notes      = new ArrayList<Note          >(0);
      attributes = new ArrayList<ChordAttribute>(0);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param position             drawing position of the chord within the section.
    * @param duration             midi duration of the chord.
    * @param durationAttributes   duration attributes of the chord.
    * @param beam                 beam information.
    * @param notes                notes for the chord.
    * @param attributes           attributes of the chord.
    */
   public Chord(byte position, Midi.Duration duration, DurationAttribute[] durationAttributes, Beam beam, Note[] notes, ChordAttribute[] attributes)
   {
      super(position, duration, durationAttributes);

      this.beam = beam;
      setNotes     (notes     );
      setAttributes(attributes);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param chord   chord whose values will be deep copied.
    */
   public Chord(Chord chord)
   {
      super(chord);

      if (chord != null)
      {
         this.beam  = new Beam(chord.beam);
         this.notes = new ArrayList<Note>(0);
         if (chord.notes != null)
         {
            for(Note note : chord.notes)
               this.notes.add(new Note(note));
         }
         this.attributes = new ArrayList<ChordAttribute>(0);
         if (chord.attributes != null)
         {
            for(ChordAttribute attribute : chord.attributes)
               this.attributes.add(attribute.isVariable() ? attribute.clone() : attribute);
         }
      }
   }

   /**
    * @return the beam for the chord.
    */
   public Beam getBeam()
   {
      return beam;
   }

   /**
    * sets the beam for the chord.
    * <br/><br/>
    * @param beam  the beam for the chord.
    */
   public void setBeam(Beam beam)
   {
      this.beam = beam;
   }

   /**
    * @return the notes in the chord.
    */
   public ArrayList<Note> getNotes()
   {
      return notes;
   }

   /**
    * @return the number of notes in the chord that have ties.
    */
   public int getNumTiedNotes()
   {
      int numTiedNotes = 0;

      // note: a note can have either a tie or a tie wrap attribute, but not both
      for(Note note : notes)
         if (note.hasAttribute(NoteAttribute.Type.Tie) || note.hasAttribute(NoteAttribute.Type.TieWrap))
            numTiedNotes++;

      return numTiedNotes;
   }

   /**
    * sets the notes in the chord.
    * <br/><br/>
    * @param notes   the notes in the chord.
    */
   public void setNotes(Note[] notes)
   {
      this.notes = new ArrayList<Note>(0);

      if (notes != null)
      {
         for(Note note : notes)
            this.notes.add(note);
      }
   }

   /**
    * sets the notes in the chord.
    * <br/><br/>
    * @param notes   the notes in the chord.
    */
   private void setNotes(Object[] notes)
   {
      this.notes = new ArrayList<Note>(0);

      if (notes != null)
      {
         for(Object note : notes)
            this.notes.add((Note)note);
      }
   }

   /**
    * adds an attribute to the chord.
    * If the chord already has an attribute of the specified type, the existing attribute is first removed before the new attribute is added.
    * <br/><br/>
    * @param attribute   the chord attribute to add.
    */
   public void addAttribute(ChordAttribute attribute)
   {
      removeAttribute(attribute.getType());
      attributes.add(attribute);
   }

   /**
    * @return the specified attribute of the chord if present and null otherwise.
    * <br/><br/>
    * @param attributeType   the type of chord attribute to search for.
    */
   public ChordAttribute getAttribute(ChordAttribute.Type attributeType)
   {
      for(ChordAttribute attribute : attributes)
         if (attributeType == attribute.getType())
            return attribute;
      return null;
   }

   /**
    * @return the attributes of the chord.
    */
   public ArrayList<ChordAttribute> getAttributes()
   {
      return attributes;
   }

   /**
    * @param attributeType   the type of chord attribute to search for.
    * <br/><br/>
    * @return whether the chord has the specified attribute.
    */
   public boolean hasAttribute(ChordAttribute.Type attributeType)
   {
      for(ChordAttribute attribute : attributes)
         if (attributeType == attribute.getType())
            return true;
      return false;
   }

   /**
    * removes the specified attribute from the chord.
    * <br/><br/>
    * @param attributeType   the type of chord attribute to remove.
    */
   public void removeAttribute(ChordAttribute.Type attributeType)
   {
      ChordAttribute attribute = getAttribute(attributeType);

      if (attribute != null)
         attributes.remove(attribute);
   }

   /**
    * sets the attributes of the chord.
    * <br/><br/>
    * @param attributes   the attributes of the chord.
    */
   public void setAttributes(ChordAttribute[] attributes)
   {
      this.attributes = new ArrayList<ChordAttribute>(0);

      if (attributes != null)
      {
         for(ChordAttribute attribute : attributes)
            this.attributes.add(attribute);
      }
   }

   /**
    * @return whether the duration is a simple rest.
    */
   @Override
   public boolean isRest()
   {
      return notes.size() == 0 && super.isRest();
   }

   /**
    * @return whether the duration is a multi bar rest.
    */
   @Override
   public boolean isMultiBarRest()
   {
      return notes.size() == 0 && super.isMultiBarRest();
   }

   /**
    * @return whether the chord is the beginning of a multi bar rest.
    */
   public boolean isMultiBarRestBegin()
   {
      return notes.size() == 0 && getDuration().pulses() == Midi.Duration.NOTE_WHOLE.pulses() && getAttributes().size() == 1 && hasAttribute(ChordAttribute.Type.MultibarRestBegin);
   }

   /**
    * @return whether the chord is the middle of a multi bar rest.
    */
   public boolean isMultiBarRestMiddle()
   {
      return notes.size() == 0 && getDuration().pulses() == Midi.Duration.NOTE_WHOLE.pulses() && getAttributes().size() == 1 && hasAttribute(ChordAttribute.Type.MultibarRestMiddle);
   }

   /**
    * @return whether the chord is the end of a multi bar rest.
    */
   public boolean isMultiBarRestEnd()
   {
      return notes.size() == 0 && getDuration().pulses() == Midi.Duration.NOTE_WHOLE.pulses() && getAttributes().size() == 1 && hasAttribute(ChordAttribute.Type.MultibarRestEnd);
   }

   /**
    * @return whether two chords are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Chord)
      {
         Chord chord = (Chord)object;
         equal = super.equals(chord) && this.beam.equals(chord.beam) && this.notes.size() == chord.notes.size() && this.attributes.size() == chord.attributes.size();
         for(int i=0; i<notes.size() && equal; ++i)
            equal = this.notes.get(i).equals(chord.notes.get(i));
         for(int i=0; i<attributes.size() && equal; ++i)
            equal = this.attributes.get(i).equals(chord.attributes.get(i));
      }
      return equal;
   }

   /**
    * read in a chord from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the chord from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         beam .load(file);

         // read in the chord attributes
         int numAttributes = file.readShort();
         attributes = new ArrayList<ChordAttribute>(numAttributes);
         for(short i=0; i<numAttributes; ++i)
         {
            ChordAttribute.Type type      = ChordAttribute.getType(file.read());
            ChordAttribute      attribute = map.get(type);
            attribute = (attribute.isVariable() ? attribute.clone() : attribute);
            attribute.load(file);
            attributes.add(attribute);
         }

         // read in the notes
         setNotes(file.readArray(Note.class));
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord"));
      }
   }

   /**
    * save a chord to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the chord to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         beam .save(file);
         file.writeShort((short)attributes.size());
         for(ChordAttribute attribute : attributes)
            attribute.save(file);
         file.writeArray(notes.toArray());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord"));
      }
   }

   /**
    * @return a string representation of the chord.
    */
   @Override
   public String toString()
   {
      return toString(21);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the chord.
    */
   @Override
  public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

           if (isRest())                        buffer.append(ResourceBundle.getString("text.rest")          + "\n");
      else if (isMultiBarRestBegin())           buffer.append(ResourceBundle.getString("text.rest.multibar") + "\n");
      else if (notes.size() == 1)               buffer.append(ResourceBundle.getString("text.note")          + "\n");
      else                                      buffer.append(ResourceBundle.getString("text.chord")         + "\n");
                                                buffer.append(super.toString(numSpacesToIndent));
                                                buffer.append(Utility.pad(ResourceBundle.getString("data_type.beam"         ), indentation1) + beam              + "\n");
                                                buffer.append(Utility.pad(ResourceBundle.getString("text.attributes"        ), indentation1) + attributes.size() + "\n");
      for(int i=0; i<attributes.size(); ++i)    buffer.append(Utility.pad("" + i, indentation2) + attributes.get(i) + "\n");
                                                buffer.append(Utility.pad(ResourceBundle.getString("data_type.notes"), indentation1) + notes.size() + (notes.size() == 0 ? "" : "\n"));
      for(int i=0; i<notes.size();      ++i)    buffer.append(Utility.pad(ResourceBundle.getString("data_type.note" ) + "[" + i + "]", indentation2) + notes.get(i) + (i == notes.size() -1 ? "" : "\n"));

      return buffer.toString();
   }
}
