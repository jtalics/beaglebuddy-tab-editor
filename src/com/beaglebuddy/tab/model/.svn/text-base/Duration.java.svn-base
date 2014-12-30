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
import com.beaglebuddy.tab.model.attribute.duration.*;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.Hashtable;





/**
 * This base class represents a musical duration and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * A music duration is any musical object that has a time duration, such as a note, a chord, a rhythm slash, a rest, or a multibar rest.
 * <p>
 * This base class only holds the base duration of the object (ex: quarter note).
 * This duration does not include whether the duration is dotted (ex: dotted quarter note), or double dotted (ex: double dotted quarter note).
 * Information such as this is held in derived classes.
 * This information, however, is needed to fully determine the duration of a musical object.
 * Since the needed information is not available to this class, the <i>getMidiPulses()</i> method is declared abstract, and is implemented in derived classes
 * where the information is available.
 * </p>
 */
public abstract class Duration
{
   // class members
   private static Hashtable<DurationAttribute.Type, DurationAttribute> map = new Hashtable<DurationAttribute.Type, DurationAttribute>();
   static
   {
      map.put(DurationAttribute.Type.Dotted           , new Dotted           ());
      map.put(DurationAttribute.Type.DottedDouble     , new DottedDouble     ());
      map.put(DurationAttribute.Type.IrregularGrouping, new IrregularGrouping());
      map.put(DurationAttribute.Type.Rest             , new Rest             ());
      map.put(DurationAttribute.Type.MultibarRest     , new MultibarRest     ());
   }

   // data members
   private byte                         position;               // the drawing position within the section
   private Midi.Duration                duration;               // the corresponding midi duration
   private ArrayList<DurationAttribute> durationAttributes;     // attributes of the duration




   /**
    * default constructor.
    */
   public Duration()
   {
      duration           = Midi.Duration.NOTE_NONE;
      durationAttributes = new ArrayList<DurationAttribute>(0);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param position     index of the drawing position within the section.
    * @param duration     corresponding midi duration.
    * @param attributes   attributes of the duration.
    */
   public Duration(byte position, Midi.Duration duration, DurationAttribute[] attributes)
   {
      this.position = position;
      this.duration = duration;
      setDurationAttributes(attributes);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param duration   duration whose values will be deep copied.
    */
   public Duration(Duration duration)
   {
      this();

      if (duration != null)
      {
         this.position           = duration.position;
         this.duration           = duration.duration;
         this.durationAttributes = new ArrayList<DurationAttribute>(0);

         if (duration.getDurationAttributes() != null)
         {
            for(DurationAttribute attribute : duration.getDurationAttributes())
               this.durationAttributes.add(attribute.isVariable() ? attribute.clone() : attribute);
         }
      }
   }

   /**
    * @return the drawing position within the section.
    */
   public byte getPosition()
   {
      return position;
   }

   /**
    * sets the drawing position within the section.
    * <br/><br/>
    * @param position   the drawing position within the section.
    */
   public void setPosition(byte position)
   {
      this.position = position;
   }

   /**
    * @return the corresponding midi duration.
    */
   public Midi.Duration getDuration()
   {
      return duration;
   }

   /**
    * sets the corresponding midi duration.
    * <br/><br/>
    * @param duration  corresponding midi duration.
    */
   public void setDuration(Midi.Duration duration)
   {
      this.duration = duration;
   }

   /**
    * adds an attribute to the duration.
    * If the duration already has an attribute of the specified type, the existing attribute is first removed before the new attribute is added.
    * <br/><br/>
    * @param attribute   the duration attribute to add.
    */
   public void addDurationAttribute(DurationAttribute attribute)
   {
      removeDurationAttribute(attribute.getType());
      durationAttributes.add(attribute);
   }

   /**
    * @return the specified attribute of the duration if present and null otherwise.
    * <br/><br/>
    * @param attributeType   the type of duration attribute to search for.
    */
   public DurationAttribute getDurationAttribute(DurationAttribute.Type attributeType)
   {
      for(DurationAttribute attribute : durationAttributes)
         if (attributeType == attribute.getType())
            return attribute;
      return null;
   }

   /**
    * @return the attributes of the duration.
    */
   public ArrayList<DurationAttribute> getDurationAttributes()
   {
      return durationAttributes;
   }

   /**
    * @param attributeType   the type of duration attribute to search for.
    * <br/><br/>
    * @return whether the duration has the specified attribute.
    */
   public boolean hasDurationAttribute(DurationAttribute.Type attributeType)
   {
      for(DurationAttribute attribute : durationAttributes)
         if (attributeType == attribute.getType())
            return true;
      return false;
   }

   /**
    * removes the specified attribute from the duration.
    * <br/><br/>
    * @param attributeType   the type of duration attribute to remove.
    */
   public void removeDurationAttribute(DurationAttribute.Type attributeType)
   {
      DurationAttribute attribute = getDurationAttribute(attributeType);

      if (attribute != null)
         durationAttributes.remove(attribute);
   }

   /**
    * sets the attributes of the duration.
    * <br/><br/>
    * @param attributes   the attributes of the duration.
    */
   public void setDurationAttributes(DurationAttribute[] attributes)
   {
      this.durationAttributes = new ArrayList<DurationAttribute>(0);

      if (attributes != null)
      {
         for(DurationAttribute attribute : attributes)
            durationAttributes.add(attribute);
      }
   }

   /**
    * @return whether this duration is dotted (has its duration plus another half)
    */
   public boolean isDotted()
   {
      return hasDurationAttribute(DurationAttribute.Type.Dotted);
   }

   /**
    * @return whether this duration is double dotted (has its duration plus another half plus another quarter)
    */
   public boolean isDoubleDotted()
   {
      return hasDurationAttribute(DurationAttribute.Type.DottedDouble);
   }

   /**
    * @return whether the duration is part of an irregular grouping (tuplet).
    */
   public boolean isPartOfIrregularGrouping()
   {
      return hasDurationAttribute(DurationAttribute.Type.IrregularGrouping);
   }

   /**
    * @return whether the duration is a simple rest.
    */
   public boolean isRest()
   {
      return hasDurationAttribute(DurationAttribute.Type.Rest);
   }

   /**
    * @return whether the duration is a multi bar rest.
    */
   public boolean isMultiBarRest()
   {
      return duration.pulses() == Midi.Duration.NOTE_WHOLE.pulses() && durationAttributes.size() == 1 && hasDurationAttribute(DurationAttribute.Type.MultibarRest);
   }

   /**
    * @return the number of midi pulses for this duration.  This is the midi duration plus any dots as well as any irregular groupings (tuplets).
    */
   public long getMidiPulses()
   {
      long pulses = duration.pulses();

      if (isDotted())
         pulses = 3 * pulses / 2;         // duration = duration + ½ duration
      else if (isDoubleDotted())
         pulses = 7 * pulses / 4;         // duration = duration + ½ duration  + ¼ duration

      if (isPartOfIrregularGrouping())
      {
         IrregularGrouping irregularGrouping = (IrregularGrouping)getDurationAttribute(DurationAttribute.Type.IrregularGrouping);
         pulses = pulses * irregularGrouping.getNumNotesPlayedOver() / irregularGrouping.getNumNotesPlayed();
      }
      return pulses;
   }

   /**
    * @return whether two durations are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Duration)
      {
         Duration duration = (Duration)object;
         equal = this.position == duration.position && this.duration == duration.duration && this.durationAttributes.size() == duration.durationAttributes.size();
         for(int i=0; i<durationAttributes.size() && equal; ++i)
            equal = this.durationAttributes.get(i).equals(duration.durationAttributes.get(i));
      }
      return equal;
   }

   /**
    * read in a duration from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the duration from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos      = file.getPosition();
         position = (byte)file.read();
         duration = Midi.getDuration(file.read());

         // read in the duration attributes
         int numAttributes = file.readShort();
         durationAttributes = new ArrayList<DurationAttribute>(numAttributes);
         for(short i=0; i<numAttributes; ++i)
         {
            DurationAttribute.Type type      = DurationAttribute.getType(file.read());
            DurationAttribute      attribute = map.get(type);
            attribute = (attribute.isVariable() ? attribute.clone() : attribute);
            attribute.load(file);
            durationAttributes.add(attribute);
         }
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.duration"));
      }
   }

   /**
    * save a duration to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the duration to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(position);
         file.write(duration.ordinal());
         file.writeShort((short)durationAttributes.size());
         for(DurationAttribute attribute : durationAttributes)
            attribute.save(file);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.duration"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab duration.
    */
   @Override
   public String toString()
   {
      return toString(21);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab duration.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(Utility.pad(ResourceBundle.getString("data_type.midi.duration"), indentation1) + duration + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.position"          ), indentation1) + position + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.attributes"        ), indentation1) + durationAttributes.size() + "\n");
      for(int i=0; i<durationAttributes.size(); ++i)    buffer.append(Utility.pad("" + i, indentation2) + durationAttributes.get(i) + "\n");

      return buffer.toString();
   }
}
