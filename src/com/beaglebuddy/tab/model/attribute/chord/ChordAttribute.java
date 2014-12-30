/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.chord;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a chord attribute and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class ChordAttribute
{
   // enums
   public enum Type
   {
      AccentMarcato          (ResourceBundle.getString("chord_attribute_accent_marcato"      )),    // standard accent
      AccentSforzando        (ResourceBundle.getString("chord_attribute_accent_sforzando"    )),    // heavy    accent
      Acciaccatura           (ResourceBundle.getString("chord_attribute_acciaccatura"        )),    // crushing note
      Appoggiatura           (ResourceBundle.getString("chord_attribute_appoggiatura"        )),    // leaning note
      Arpeggio               (ResourceBundle.getString("chord_attribute_arpeggio"            )),    // play a chord as an arpeggio (up or down)
      ChordName              (ResourceBundle.getString("chord_attribute_chord_name"          )),    // name of the chord displayed above it
      Fermata                (ResourceBundle.getString("chord_attribute_fermata"             )),    // hold the note or chord longer than is notated
      FingerBass             (ResourceBundle.getString("chord_attribute_finger_bass"         )),    // slap, pop, etc.  mostly for notating funk bass parts
      FingerFretHand         (ResourceBundle.getString("chord_attribute_finger_fret_hand"    )),    // which finger to fret the note with
      FingerPickHand         (ResourceBundle.getString("chord_attribute_finger_pick_hand"    )),    // for notating finger picking
      LetRing                (ResourceBundle.getString("chord_attribute_let_ring"            )),
      MultibarRestBegin      (ResourceBundle.getString("chord_attribute_rest_multibar_begin" )),
      MultibarRestMiddle     (ResourceBundle.getString("chord_attribute_rest_multibar_middle")),
      MultibarRestEnd        (ResourceBundle.getString("chord_attribute_rest_multibar_end"   )),
      None                   (ResourceBundle.getString("chord_attribute_none"                )),    // dummy attribute used by the conversion routines for attributes in other tab editors that don't have a beaglebuddy equivalent
      PalmMuting             (ResourceBundle.getString("chord_attribute_palm_muting"         )),
      PickStroke             (ResourceBundle.getString("chord_attribute_pick_stroke"         )),    // pick the chord with the specified stroke (up or down)
      Staccato               (ResourceBundle.getString("chord_attribute_staccato"            )),
      Tap                    (ResourceBundle.getString("chord_attribute_tap"                 )),
      Text                   (ResourceBundle.getString("chord_attribute_text"                )),    // useful for vocals
      TremoloBar             (ResourceBundle.getString("chord_attribute_tremolo_bar"         )),
      TremoloPicking         (ResourceBundle.getString("chord_attribute_tremolo_picking"     )),
      TripletFeel1st         (ResourceBundle.getString("chord_attribute_triplet_feel_1st"    )),    // the notes should be played as if it were the 1st note in a triplet feel duet
      TripletFeel2nd         (ResourceBundle.getString("chord_attribute_triplet_feel_2nd"    )),
      Vibrato                (ResourceBundle.getString("chord_attribute_vibrato"             )),
      VolumeSwell            (ResourceBundle.getString("chord_attribute_volume_swell"        ));

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
    * @param type   type of chord attribute.
    */
   public ChordAttribute(Type type)
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
    * @param type  the integer chord attribute type.
    * <br/><br/>
    * @return the chord attribute type enum corresponding to the integer type.
    */
   public static Type getType(int type)
   {
      for (Type t : Type.values())
         if (type == t.ordinal())
            return t;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.chord_attribute"), type, Type.VolumeSwell.ordinal()));
   }

   /**
    * @return a new copy of the chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new ChordAttribute(this.type);
   }

   /**
    * @return if the two chord attributes are equal.
    * <br/><br/>
    * @param object  object to check for equality with this chord attribute.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof ChordAttribute && this.type == ((ChordAttribute)object).type);
   }

   /**
    * @return whether the chord attribute has any variation between instances.
    * for example, every instance of the vibrato chord attribute is the same, whereas instances of the volume swell chord attribute
    * can have different start\end volumes as well as durations.
    */
   public boolean isVariable()
   {
      return false;
   }

   /**
    * read in the chord attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the chord attribute can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      // don't do anything
      // this will be handled by the chord containing the attributes
   }

   /**
    * save a chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the chord attribute to the beaglebuddy tab file.
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
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute"));
      }
   }

   /**
    * @return a string representation of the chord attribute.
    */
   @Override
   public String toString()
   {
      return toString(24);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the chord attribute.
    */
   public String toString(int numSpacesToIndent)
   {
      return type.text();
   }
}
