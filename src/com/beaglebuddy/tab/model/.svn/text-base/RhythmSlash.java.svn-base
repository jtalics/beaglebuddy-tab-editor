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
import com.beaglebuddy.tab.model.attribute.duration.DurationAttribute;
import com.beaglebuddy.tab.model.attribute.rhythm.*;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.Hashtable;


// todo: fix the reading of beam groups to use ordinal, etc.


/**
 * This class represents a beaglebuddy tab rhythm slash and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class RhythmSlash extends Duration
{
   // enums
   public enum BeamGroup    {None, Start, Fractional, End}
   public enum TripletGroup {None, Start, Middle    , End}

   // class members
   private static Hashtable<RhythmSlashAttribute.Type, RhythmSlashAttribute> map = new Hashtable<RhythmSlashAttribute.Type, RhythmSlashAttribute>();
   static
   {
      map.put(RhythmSlashAttribute.Type.AccentMarcato      , new AccentMarcato      ());
      map.put(RhythmSlashAttribute.Type.AccentSforzando    , new AccentSforzando    ());
      map.put(RhythmSlashAttribute.Type.ArpeggioDown       , new ArpeggioDown       ());
      map.put(RhythmSlashAttribute.Type.ArpeggioUp         , new ArpeggioUp         ());
      map.put(RhythmSlashAttribute.Type.ChordName          , new ChordName          ());
      map.put(RhythmSlashAttribute.Type.Muted              , new Muted              ());
      map.put(RhythmSlashAttribute.Type.PickStrokeDown     , new PickStrokeDown     ());
      map.put(RhythmSlashAttribute.Type.PickStrokeUp       , new PickStrokeUp       ());
      map.put(RhythmSlashAttribute.Type.SingleNote         , new SingleNote         ());
      map.put(RhythmSlashAttribute.Type.SlideIntoFromAbove , new SlideIntoFromAbove ());
      map.put(RhythmSlashAttribute.Type.SlideIntoFromBelow , new SlideIntoFromBelow ());
      map.put(RhythmSlashAttribute.Type.SlideOutOfDownwards, new SlideOutOfDownwards());
      map.put(RhythmSlashAttribute.Type.SlideOutOfUpwards  , new SlideOutOfUpwards  ());
      map.put(RhythmSlashAttribute.Type.Staccato           , new Staccato           ());
      map.put(RhythmSlashAttribute.Type.Tied               , new Tied               ());
      map.put(RhythmSlashAttribute.Type.TripletFeel1st     , new TripletFeel1st     ());
      map.put(RhythmSlashAttribute.Type.TripletFeel2nd     , new TripletFeel2nd     ());
   }

   // data members
   private BeamGroup                       beamGroup;             // beam grouping, if any
   private TripletGroup                    tripletGroup;          // triplet grouping, if any
   private Midi.Duration                   previousBeamDuration;  // duration of the previous rhythm slash in the beam group
   private ArrayList<RhythmSlashAttribute> attributes;            // attributes of the rhythm slash





   /**
    * default constructor.
    */
   public RhythmSlash()
   {
      attributes = new ArrayList<RhythmSlashAttribute>();
   }

   /**
    * constructor.
    * <br/><br/>
    * @param position             drawing position within the section where the rhythm slash is anchored.
    * @param duration             duration of the rhythm slash.
    * @param durationAttributes   duration attributes of the rhythm slash.
    * @param beamGroup            beam grouping, if any.
    * @param tripletGroup         triplet grouping, if any.
    * @param previousBeamDuration duration of the previous rhythm slash in the beam group.
    * @param attributes           attributes of the rhythm slash.
    */
   public RhythmSlash(byte position, Midi.Duration duration, DurationAttribute[] durationAttributes, BeamGroup beamGroup, TripletGroup  tripletGroup, Midi.Duration previousBeamDuration, RhythmSlashAttribute[] attributes)
   {
      super(position, duration, durationAttributes);

      this.beamGroup            = beamGroup;
      this.tripletGroup         = tripletGroup;
      this.previousBeamDuration = previousBeamDuration;
      setAttributes(attributes);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param rhythmSlash   rhythmSlash whose values will be deep copied.
    */
   public RhythmSlash(RhythmSlash rhythmSlash)
   {
      super(rhythmSlash);

      if (rhythmSlash != null)
      {
         this.beamGroup            = rhythmSlash.beamGroup;
         this.tripletGroup         = rhythmSlash.tripletGroup;
         this.previousBeamDuration = rhythmSlash.previousBeamDuration;
         if (rhythmSlash.attributes != null)
         {
            attributes = new ArrayList<RhythmSlashAttribute>(rhythmSlash.attributes.size());
            for(RhythmSlashAttribute attribute : rhythmSlash.attributes)
               this.attributes.add(attribute.isVariable() ? attribute.clone() : attribute);
         }
      }
   }

   /**
    * @return the beam grouping for this rhythm slash.
    */
   public BeamGroup getBeamGroup()
   {
      return beamGroup;
   }

   /**
    * @return the beam grouping of the rhythm slash.
    * <br/><br/>
    * @param data   raw data read in from a . file.
    */
   public static BeamGroup getBeamGroup(int data)
   {
      BeamGroup beamGroup = BeamGroup.None;

           if ((data & 0x04) == 0x04) beamGroup = BeamGroup.Start;
      else if ((data & 0x08) == 0x08) beamGroup = BeamGroup.Fractional;
      else if ((data & 0x10) == 0x10) beamGroup = BeamGroup.End;

      return beamGroup;
   }

   /**
    * sets the beam grouping for this rhythm slash.
    * <br/><br/>
    * @param beamGroup   the beam grouping for this rhythm slash.
    */
   public void setBeamGroup(BeamGroup beamGroup)
   {
      this.beamGroup = beamGroup;
   }

   /**
    * @return the triplet grouping for this rhythm slash.
    */
   public TripletGroup getTripletGroup()
   {
      return tripletGroup;
   }

   /**
    * @return the triplet grouping of the rhythm slash.
    * <br/><br/>
    * @param data   raw data read in from a . file.
    */
   public static TripletGroup getTripletGroup(int data)
   {
      TripletGroup tripletGroup = TripletGroup.None;

           if ((data & 0x20) == 0x20) tripletGroup = TripletGroup.Start;
      else if ((data & 0x40) == 0x40) tripletGroup = TripletGroup.Middle;
      else if ((data & 0x80) == 0x80) tripletGroup = TripletGroup.End;

      return tripletGroup;
   }

   /**
    * sets the triplet grouping for this rhythm slash.
    * <br/><br/>
    * @param tripletGroup   the triplet grouping for this rhythm slash.
    */
   public void setTripletGroup(TripletGroup tripletGroup)
   {
      this.tripletGroup = tripletGroup;
   }

   /**
    * @return the midi duration for the previous rhythm slash.
    */
   public Midi.Duration getPreviousBeamDuration()
   {
      return previousBeamDuration;
   }

   /**
    * sets the midi duration for the previous rhythm slash.
    * <br/><br/>
    * @param duration   the midi duration for the previous rhythm slash.
    */
   public void setPreviousBeamDuration(Midi.Duration duration)
   {
      this.previousBeamDuration = duration;
   }

   /**
    * adds an attribute to the rhythm slash.
    * If the rhythm slash already has an attribute of the specified type, the existing attribute is first removed before the new attribute is added.
    * <br/><br/>
    * @param attribute   the rhythm slash attribute to add.
    */
   public void addAttribute(RhythmSlashAttribute attribute)
   {
      removeAttribute(attribute.getType());
      attributes.add(attribute);
   }

   /**
    * @return the specified attribute of the rhythm slash if present and null otherwise.
    * <br/><br/>
    * @param attributeType   the type of rhythm slash attribute to search for.
    */
   public RhythmSlashAttribute getAttribute(RhythmSlashAttribute.Type attributeType)
   {
      for(RhythmSlashAttribute attribute : attributes)
         if (attributeType == attribute.getType())
            return attribute;
      return null;
   }

   /**
    * @return the attributes of this rhythm slash.
    */
   public ArrayList<RhythmSlashAttribute> getAttributes()
   {
      return attributes;
   }

   /**
    * @param attributeType   the type of rhythm slash attribute to search for.
    * <br/><br/>
    * @return whether this rhythm slash has the specified attribute.
    */
   public boolean hasAttribute(RhythmSlashAttribute.Type attributeType)
   {
      for(RhythmSlashAttribute attribute : attributes)
         if (attributeType == attribute.getType())
            return true;
      return false;
   }

   /**
    * removes the specified attribute from the rhythm slash.
    * <br/><br/>
    * @param attributeType   the type of slash attribute to remove.
    */
   public void removeAttribute(RhythmSlashAttribute.Type attributeType)
   {
      RhythmSlashAttribute attribute = getAttribute(attributeType);

      if (attribute != null)
         attributes.remove(attribute);
   }

   /**
    * sets the attributes of this rhythm slash.
    * <br/><br/>
    * @param attributes   the attributes of this rhythm slash.
    */
   public void setAttributes(RhythmSlashAttribute[] attributes)
   {
      if (attributes == null || attributes.length == 0)
      {
         this.attributes = new ArrayList<RhythmSlashAttribute>(0);
      }
      else
      {
         this.attributes = new ArrayList<RhythmSlashAttribute>(attributes.length);
         for(RhythmSlashAttribute attribute : attributes)
            this.attributes.add(attribute);
      }
   }

   /**
    * @return whether the rhythm slash is a single note.
    */
   public boolean isSingleNote()
   {
      return hasAttribute(RhythmSlashAttribute.Type.SingleNote);
   }

   /**
    * @return the number of midi pulses for this rhythm slash.  This is the midi duration plus any dots.
    */
   @Override
   public long getMidiPulses()
   {
      long pulses = super.getMidiPulses();

      if (tripletGroup != TripletGroup.None)
         pulses = pulses * 2 / 3;

      return pulses;
   }

   /**
    * @return whether two rhythm slashes are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof RhythmSlash)
      {
         RhythmSlash rhythmSlash = (RhythmSlash)object;
         equal = super.equals(rhythmSlash) && this.beamGroup == rhythmSlash.beamGroup && this.tripletGroup == rhythmSlash.tripletGroup &&
                 this.previousBeamDuration == rhythmSlash.previousBeamDuration && this.attributes.size() == rhythmSlash.attributes.size();
         for(int i=0; i<attributes.size() && equal; ++i)
            equal = this.attributes.get(i).equals(rhythmSlash.attributes.get(i));
      }
      return equal;
   }

   /**
    * read in a rhythm slash from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the rhythm slash from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {

      long pos = -1L;
      try
      {
         pos                  = file.getPosition();
         super.load(file);
         beamGroup            = getBeamGroup    ((byte)file.read());
         tripletGroup         = getTripletGroup ((byte)file.read());
         previousBeamDuration = Midi.getDuration(file.read());

         // read in the rhythm slash attributes
         int numAttributes = file.readShort();
         attributes = new ArrayList<RhythmSlashAttribute>(numAttributes);
         for(short i=0; i<numAttributes; ++i)
         {
            RhythmSlashAttribute.Type type      = RhythmSlashAttribute.getType(file.read());
            RhythmSlashAttribute      attribute = map.get(type);
            attribute = (attribute.isVariable() ? attribute.clone() : attribute);
            attribute.load(file);
            attributes.add(attribute);
         }
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.rhythm_slash"));
      }
   }

   /**
    * save a rhythm slash to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the rhythm slash to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(beamGroup           .ordinal());
         file.write(tripletGroup        .ordinal());
         file.write(previousBeamDuration.ordinal());
         file.writeShort((short)attributes.size());
         for(RhythmSlashAttribute attribute : attributes)
            attribute.save(file);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.rhythm_slash"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab rhythm slash.
    */
   @Override
   public String toString()
   {
      return toString(6);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab rhythm slash.
    */
   @Override
  public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

           if (isRest())       buffer.append(ResourceBundle.getString("text.rest")                    + "\n");
      else if (isSingleNote()) buffer.append(ResourceBundle.getString("rhythm_attribute_single_note") + "\n");
      else                     buffer.append(ResourceBundle.getString("data_type.rhythm_slash")       + "\n");
      buffer.append(super.toString(numSpacesToIndent));
      buffer.append(Utility.pad(ResourceBundle.getString("text.beam_grouping"         ), indentation1) + beamGroup                                        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.triplet_grouping"      ), indentation1) + tripletGroup                                     + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.previous_beam_duration"), indentation1) + previousBeamDuration                             + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.attributes"            ), indentation1) + attributes.size() + (attributes.size() == 0 ? "" : "\n"));
      for(int i=0; i<attributes.size(); ++i)
         buffer.append(Utility.pad("" + i, indentation2) + attributes.get(i) + (i == attributes.size() -1 ? "" : "\n"));

      return buffer.toString();
   }
}
