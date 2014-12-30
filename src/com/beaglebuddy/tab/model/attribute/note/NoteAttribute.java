/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.note;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a beaglebuddy tab note attribute provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class NoteAttribute
{
   // enums
   public enum Type
   {
      ArtificialHarmonic     (ResourceBundle.getString("note_attribute.artificial_harmonic"        )),
      Bend                   (ResourceBundle.getString("note_attribute.bend"                       )),
      GhostNote              (ResourceBundle.getString("note_attribute.ghost_note"                 )),
      HammerOn               (ResourceBundle.getString("note_attribute.hammer_on"                  )),
      HammerPullFromToNowhere(ResourceBundle.getString("note_attribute.hammer_pull_from_to_nowhere")),
      Muted                  (ResourceBundle.getString("note_attribute.muted"                      )),
      NaturalHarmonic        (ResourceBundle.getString("note_attribute.natural_harmonic"           )),
      Octave                 (ResourceBundle.getString("note_attribute.octave"                     )),
      PullOff                (ResourceBundle.getString("note_attribute.pull_off"                   )),
      SlideInto              (ResourceBundle.getString("note_attribute.slide_into"                 )),
      SlideOutOf             (ResourceBundle.getString("note_attribute.slide_out_of"               )),
      TappedHarmonic         (ResourceBundle.getString("note_attribute.tapped_harmonic"            )),
      Tie                    (ResourceBundle.getString("note_attribute.tie"                        )),
      TieWrap                (ResourceBundle.getString("note_attribute.tie_wrap"                   )),
      Trill                  (ResourceBundle.getString("note_attribute.trill"                      ));

      // data members
      private String text;
      Type(String text) {this.text = text;}
      public String text() {return text;}
   }

   // data members
   private Type type;





   /**
    * constructor.
    * <br/><br/>
    * @param type   type of note attribute.
    */
   public NoteAttribute(Type type)
   {
      this.type = type;
   }

   /**
    * @return the type of attribute.
    */
   public Type getType()
   {
      return type;
   }

   /**
    * @param type  the integer note attribute type.
    * <br/><br/>
    * @return the note attribute type enum corresponding to the integer type.
    */
   public static Type getType(int type)
   {
      for (Type t : Type.values())
         if (type == t.ordinal())
            return t;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.note_attribute"), type, Type.Trill.ordinal()));
   }

   /**
    * @return a new copy of the note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new NoteAttribute(this.type);
   }

   /**
    * @param object  object to check for equality with this note attribute.
    * <br/><br/>
    * @return if the two note attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof NoteAttribute && this.type == ((NoteAttribute)object).type);
   }

   /**
    * @return whether the note attribute has any variation between instances.
    * for example, every instance of the muted note attribute is the same, whereas instances of the tap harmonic note attribute
    * can have different frets where the user taps the fretboard.
    */
   public boolean isVariable()
   {
      return false;
   }

   /**
    * read in the note attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the note attribute can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      // don't do anything
      // this will be handled by the note containing the attributes
   }

   /**
    * save a note attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the note attribute to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(type.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute"));
      }
   }

   /**
    * @return a string representation of the note attribute.
    */
   @Override
   public String toString()
   {
      return toString(27);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the note attribute.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(type.text());

      return buffer.toString();
   }
}
