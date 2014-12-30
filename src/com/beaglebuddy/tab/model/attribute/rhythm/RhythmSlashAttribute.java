/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.rhythm;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a beaglebuddy tab rhythm slash attribute.
 */
public class RhythmSlashAttribute
{
   // enums
   public enum Type
   {
      AccentMarcato      (ResourceBundle.getString("rhythm_attribute_accent_marcato"        )),         // standard accent
      AccentSforzando    (ResourceBundle.getString("rhythm_attribute_accent_sforzando"      )),         // heavy    accent
      ArpeggioDown       (ResourceBundle.getString("rhythm_attribute_arpeggio_down"         )),
      ArpeggioUp         (ResourceBundle.getString("rhythm_attribute_arpeggio_up"           )),
      ChordName          (ResourceBundle.getString("rhythm_attribute_chord_name"            )),
      Muted              (ResourceBundle.getString("rhythm_attribute_muted"                 )),
      PickStrokeDown     (ResourceBundle.getString("rhythm_attribute_pick_stroke_down"      )),
      PickStrokeUp       (ResourceBundle.getString("rhythm_attribute_pick_stroke_up"        )),
      SingleNote         (ResourceBundle.getString("rhythm_attribute_single_note"           )),
      SlideIntoFromAbove (ResourceBundle.getString("rhythm_attribute_slide_into_from_above" )),
      SlideIntoFromBelow (ResourceBundle.getString("rhythm_attribute_slide_into_from_below" )),
      SlideOutOfDownwards(ResourceBundle.getString("rhythm_attribute_slide_out_of_downwards")),
      SlideOutOfUpwards  (ResourceBundle.getString("rhythm_attribute_slide_out_of_upwards"  )),
      Staccato           (ResourceBundle.getString("rhythm_attribute_staccato"              )),
      Tied               (ResourceBundle.getString("rhythm_attribute_tied"                  )),
      TripletFeel1st     (ResourceBundle.getString("rhythm_attribute_tripletfeel_1st"       )),         // the notes should be played as if it were the 1st note in a triplet feel duet
      TripletFeel2nd     (ResourceBundle.getString("rhythm_attribute_tripletfeel_2nd"       ));

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
    * @param type   type of rhythm slash attribute.
    */
   public RhythmSlashAttribute(Type type)
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
    * @param type  the integer rhythm slash attribute type.
    * <br/><br/>
    * @return the rhythm slash attribute type enum corresponding to the integer type.
    */
   public static Type getType(int type)
   {
      for (Type t : Type.values())
         if (type == t.ordinal())
            return t;
      throw new IllegalArgumentException("Invalid rhythm slash attribute type " + type + ".");
   }

   /**
    * @return a new copy of the rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new RhythmSlashAttribute(this.type);
   }

   /**
    * @param object  object to check for equality with this rhythem slash attribute.
    * <br/><br/>
    * @return if the two rhythem slash attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof RhythmSlashAttribute && this.type == ((RhythmSlashAttribute)object).type);
   }

   /**
    * @return whether the rhythm slash attribute has any variation between instances.
    * for example, every instance of the muted rhythm slash attribute is the same, whereas instances of the chord name rhythm slash attribute
    * can have different chord names.
    */
   public boolean isVariable()
   {
      return false;
   }

   /**
    * read in the rhythm slash attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the rhythm slash attribute can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      // don't do anything
      // this will be handled by the rhythm slash containing the attributes
   }

   /**
    * save a rhythm slash attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the rhythm slash attribute to the beaglebuddy tab file.
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
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.rhythm_attribute"));
      }
   }

   /**
    * @return a string representation of the rhythm slash attribute.
    */
   @Override
   public String toString()
   {
      return toString(24);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the rhythm slash attribute.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(type.text());

      return buffer.toString();
   }
}
