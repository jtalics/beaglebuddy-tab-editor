/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.model.attribute.note.*;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.Hashtable;




/**
 * This class represents a beaglebuddy tab note and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class Note
{
   // class members
   private static Midi.Note[]                                  flatNoteList    = {Midi.Note.C4, Midi.Note.DFLAT4 , Midi.Note.D4, Midi.Note.EFLAT4 , Midi.Note.E4, Midi.Note.F4, Midi.Note.GFLAT4 , Midi.Note.G4, Midi.Note.AFLAT4 , Midi.Note.A4 , Midi.Note.BFLAT4 , Midi.Note.B4};
   private static Midi.Note[]                                  naturalNoteList = {Midi.Note.C4,                    Midi.Note.D4,                    Midi.Note.E4, Midi.Note.F4,                    Midi.Note.G4,                    Midi.Note.A4 ,                    Midi.Note.B4};
   private static Midi.Note[]                                  sharpNoteList   = {Midi.Note.C4, Midi.Note.CSHARP4, Midi.Note.D4, Midi.Note.DSHARP4, Midi.Note.E4, Midi.Note.F4, Midi.Note.FSHARP4, Midi.Note.G4, Midi.Note.GSHARP4, Midi.Note.A4 , Midi.Note.ASHARP4, Midi.Note.B4};
   private static Hashtable<Accidental, Midi.Note[]>           noteList = new Hashtable<Accidental, Midi.Note[]>();
   private static Hashtable<NoteAttribute.Type, NoteAttribute> map      = new Hashtable<NoteAttribute.Type, NoteAttribute>();
   static
   {
      map.put(NoteAttribute.Type.ArtificialHarmonic     , new ArtificialHarmonic     ());
      map.put(NoteAttribute.Type.Bend                   , new Bend                   ());
      map.put(NoteAttribute.Type.GhostNote              , new GhostNote              ());
      map.put(NoteAttribute.Type.HammerOn               , new HammerOn               ());
      map.put(NoteAttribute.Type.HammerPullFromToNowhere, new HammerPullFromToNowhere());
      map.put(NoteAttribute.Type.Muted                  , new Muted                  ());
      map.put(NoteAttribute.Type.NaturalHarmonic        , new NaturalHarmonic        ());
      map.put(NoteAttribute.Type.Octave                 , new Octave                 ());
      map.put(NoteAttribute.Type.PullOff                , new PullOff                ());
      map.put(NoteAttribute.Type.SlideInto              , new SlideInto              ());
      map.put(NoteAttribute.Type.SlideOutOf             , new SlideOutOf             ());
      map.put(NoteAttribute.Type.TappedHarmonic         , new TappedHarmonic         ());
      map.put(NoteAttribute.Type.Tie                    , new Tie                    ());
      map.put(NoteAttribute.Type.TieWrap                , new TieWrap                ());
      map.put(NoteAttribute.Type.Trill                  , new Trill                  ());

      noteList.put(Accidental.Flat   , flatNoteList   );
      noteList.put(Accidental.Natural, naturalNoteList);
      noteList.put(Accidental.Sharp  , sharpNoteList  );
   }

   // data members
   private Instrument.String        string;         // string the note is played on
   private Instrument.Fret          fret;           // fret   the note is played on
   private ArrayList<NoteAttribute> attributes;     // attributes of the note


   /**
    * default constructor.
    */
   public Note()
   {
      this(Instrument.String.String_1, Instrument.Fret.Open, null);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param string       string the note is played on.
    * @param fret         fret   the note is played on.
    * @param attributes   attributes of the note.
    */
   public Note(Instrument.String string, Instrument.Fret fret, NoteAttribute[] attributes)
   {
      this.string = string;
      this.fret   = fret;
      setAttributes(attributes);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param note   note whose values will be deep copied.
    */
   public Note(Note note)
   {
      this();

      if (note != null)
      {
         this.string     = note.string;
         this.fret       = note.fret;
         this.attributes = new ArrayList<NoteAttribute>(note.attributes.size());

         for(NoteAttribute attribute : note.attributes)
            this.attributes.add(attribute.isVariable() ? attribute.clone() : attribute);
      }
   }

   /**
    * @return the string the note is played on.
    */
   public Instrument.String getString()
   {
      return string;
   }

   /**
    * sets
    * <br/><br/>
    * @param string   the string the note is played on.
    */
   public void setString(Instrument.String string)
   {
      this.string = string;
   }

   /**
    * @return the fret the note is played on.
    */
   public Instrument.Fret getFret()
   {
      return fret;
   }

   /**
    * sets the fret the note is played on.
    * <br/><br/>
    * @param fret  the fret the note is played on.
    */
   public void setFret(Instrument.Fret fret)
   {
      this.fret = fret;
   }

   /**
    * adds the specified note attribute.
    * <br/><br/>
    * @param attribute   the note attribute to add.
    */
   public void addAttribute(NoteAttribute attribute)
   {
      removeAttribute(attribute.getType());

      attributes.add(attribute);
   }

   /**
    * @return the specified note attribute if found of null otherwise.
    * <br/><br/>
    * @param attributeType   the type of note attribute to search for.
    */
   public NoteAttribute getAttribute(NoteAttribute.Type attributeType)
   {
      for(NoteAttribute attribute : attributes)
         if (attributeType == attribute.getType())
            return attribute;
      return null;
   }

   /**
    * @return the the list of attributes for this note.
    */
   public ArrayList<NoteAttribute> getAttributes()
   {
      return attributes;
   }

   /**
    * @param attributeType   the type of note attribute to search for.
    * <br/><br/>
    * @return whether this note has the specified attribute.
    */
   public boolean hasAttribute(NoteAttribute.Type attributeType)
   {
      for(NoteAttribute attribute : attributes)
         if (attributeType == attribute.getType())
            return true;
      return false;
   }

   /**
    * removes the specified attribute from the note.
    * <br/><br/>
    * @param attributeType   the type of note attribute to remove.
    */
   public void removeAttribute(NoteAttribute.Type attributeType)
   {
      NoteAttribute attribute = getAttribute(attributeType);

      if (attribute != null)
         attributes.remove(attribute);
   }

   /**
    * sets the attributes of the note.
    * <br/><br/>
    * @param attributes   the attributes of the note.
    */
   public void setAttributes(NoteAttribute[] attributes)
   {
      this.attributes = new ArrayList<NoteAttribute>(0);

      if (attributes != null)
      {
         for(NoteAttribute attribute : attributes)
            this.attributes.add(attribute);
      }
   }

   /**
    * @return a list of notes, in a single octave, starting from middle C up to B, which includes the specified type of notes.
    * <br/><br/>
    * @param accidental  the type of note list desired.
    */
   public static Midi.Note[] getNoteList(Accidental accidental)
   {
      return noteList.get(accidental);
   }

   /**
    * @return a list of notes, as strings, in a single octave, starting from middle C up to B, which includes the specified type of notes.
    * <br/><br/>
    * @param accidental  the type of note list desired.
    */
   public static String[] getNoteStringList(Accidental accidental)
   {
      Midi.Note[] notes   = noteList.get(accidental);
      String   [] strings = new String[notes.length];

      for(int i=0; i<notes.length; ++i)
         strings[i] = notes[i].toString().substring(0, 2).trim();

      return strings;
   }

   /**
    * @return whether this note is tied to the same note in the previous position.
    */
   public boolean isTied()
   {
      return hasAttribute(NoteAttribute.Type.Tie);
   }

   /**
    * @return whether two notes are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Note)
      {
         Note note = (Note)object;
         equal = this.string == note.string && this.fret == note.fret && this.attributes.size() == note.attributes.size();
         for(int i=0; i<attributes.size() && equal; ++i)
            equal = this.attributes.get(i).equals(note.attributes.get(i));
      }
      return equal;
   }

   /**
    * read in the note from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the note can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos    = file.getPosition();
         string = Instrument.getString(file.read());
         fret   = Instrument.getFret  (file.read());

         // read in the note attributes
         int numAttributes = file.readShort();
         ArrayList<NoteAttribute> noteAttributes = new ArrayList<NoteAttribute>(numAttributes);
         for(short i=0; i<numAttributes; ++i)
         {
            NoteAttribute.Type type      = NoteAttribute.getType(file.read());
            NoteAttribute      attribute = map.get(type);
            attribute = (attribute.isVariable() ? attribute.clone() : attribute);
            attribute.load(file);
            noteAttributes.add(attribute);
         }
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note"));
      }
   }

   /**
    * save a note to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the note to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(string.ordinal());
         file.write(fret  .ordinal());
         file.writeShort((short)attributes.size());
         for(NoteAttribute attribute : attributes)
            attribute.save(file);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note"));
      }
   }

   /**
    * @return a string representation of the note.
    */
   @Override
   public String toString()
   {
      return toString(27);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the note.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(ResourceBundle.getString("data_type.note") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.instrument.string"), indentation1) + string                                           + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.instrument.fret"  ), indentation1) + fret                                             + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.attributes"            ), indentation1) + attributes.size() + (attributes.size() == 0 ? "" : "\n"));
      for(int i=0; i<attributes.size(); ++i)
         buffer.append(Utility.pad("" + i, indentation2) + attributes.get(i) + (i == attributes.size() - 1 ? "" : "\n"));

      return buffer.toString();
   }
}
