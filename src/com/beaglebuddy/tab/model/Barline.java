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
import com.beaglebuddy.tab.model.instrument.InstrumentIn;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;






/**
 * This class represents a beaglebuddy tab bar line and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class Barline
{
   public enum Type {Bar           ,    // normal bar used to demarcate measures
                     DoubleBar     ,    // used when a key signature change occurs somewhere other than at the start of a new section.
                     DoubleBarFine ,    // marks the end of a score (song)
                     RepeatStart   ,    // used to mark the start of a music passage that is to be repeated.
                     RepeatEnd     ,    // repeat end bar corresponding to a previous repart start bar.
                     RepeatEndStart;    // a repeat end bar and a repart start bar back to back.

                     public boolean isRepeat() {
                        return toString().toLowerCase().indexOf("repeat") >= 0;
                     }}


   // class members
   public static final int MIN_REPEAT_COUNT   = 2;
   public static final int MAX_REPEAT_COUNT   = 24;
   public static final int MAX_NUM_DIRECTIONS = 3;

   // data members
   private   byte                    position;          // drawing position within the section where the bar line occurs.
   private   Type                    type;              // type of bar line
   private   byte                    numRepeats;        // number of repeats
   private   AlternateEnding         alternateEnding;   // alternate ending for this measure
   private   KeySignature            keySignature;      // key signature
   private   RehearsalSign           rehearsalSign;     // rehearsal sign
   private   TimeSignature           timeSignature;     // time signature
   private   TempoMarker             tempoMarker;       // tempo change
   private   ArrayList<Direction>    directions;        // list of musical directions
   private   ArrayList<Volume>       volumes;           // volume changes for a staff
   private   ArrayList<InstrumentIn> instrumentIns;     // which instruments come in at which staff and measure





   /**
    * default constructor.
    */
   public Barline()
   {
      type            = Type.Bar;
      alternateEnding = null;
      keySignature    = new KeySignature ();
      rehearsalSign   = null;
      timeSignature   = new TimeSignature();
      tempoMarker     = null;
      directions      = new ArrayList<Direction   >(0);
      volumes         = new ArrayList<Volume      >(0);
      instrumentIns   = new ArrayList<InstrumentIn>(0);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param position   drawing position within the section where the bar line occurs.
    */
   public Barline(byte position)
   {
      this();

      this.position = position;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param position         drawing position within the section where the bar line occurs.
    * @param type             type of bar line.
    * @param numRepeats       number of repeats.
    * @param keySignature     key signature.
    * @param timeSignature    time signature.
    * @param rehearsalSign    rehearsal sign.
    * @param tempoMarker      tempo change.
    * @param alternateEnding  alternate ending for this measure.
    * @param directions       list of directions.
    * @param volumes          volume changes for staffs.
    * @param instrumentIns    instrument ins for the measure.
    */
   public Barline(byte position, Type type, byte numRepeats, KeySignature  keySignature, TimeSignature timeSignature, RehearsalSign rehearsalSign, TempoMarker tempoMarker, AlternateEnding alternateEnding,
                  Direction[] directions, Volume[] volumes, InstrumentIn[] instrumentIns)
   {
      this();

      this.position        = position;
      this.type            = type;
      this.numRepeats      = numRepeats;
      this.alternateEnding = alternateEnding;
      this.keySignature    = keySignature;
      this.rehearsalSign   = rehearsalSign;
      this.timeSignature   = timeSignature;
      this.tempoMarker     = tempoMarker;

      if (directions != null)
      {
         this.directions = new ArrayList<Direction>(directions.length);
         for(Direction direction : directions)
            this.directions.add(direction);
      }

      if (volumes != null)
      {
         this.volumes  = new ArrayList<Volume>(volumes.length);
         for(Volume volume : volumes)
            this.volumes.add(volume);
      }

      if (instrumentIns != null)
      {
         this.instrumentIns  = new ArrayList<InstrumentIn>(instrumentIns.length);
         for(InstrumentIn instrumentIn : instrumentIns)
            this.instrumentIns.add(instrumentIn);
      }
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param barline   barline whose values will be deep copied.
    */
   public Barline(Barline barline)
   {
      if (barline != null)
      {
         this.position        = barline.position;
         this.type            = barline.type;
         this.numRepeats      = barline.numRepeats;
         this.alternateEnding = barline.alternateEnding == null ? null : new AlternateEnding(barline.alternateEnding);
         this.keySignature    = new KeySignature   (barline.keySignature   );
         this.rehearsalSign   = barline.rehearsalSign   == null ? null : new RehearsalSign  (barline.rehearsalSign  );
         this.timeSignature   = new TimeSignature  (barline.timeSignature  );
         this.tempoMarker     = barline.tempoMarker     == null ? null : new TempoMarker    (barline.tempoMarker    );
         this.directions      = new ArrayList<Direction>(barline.directions.size());
         for(Direction direction : barline.directions)
            this.directions.add(new Direction(direction));
         this.volumes         = new ArrayList<Volume>(barline.volumes.size());
         for(Volume volume : barline.volumes)
            this.volumes.add(new Volume(volume));
         this.instrumentIns   = new ArrayList<InstrumentIn>(barline.instrumentIns.size());
         for(InstrumentIn instrumentIn : barline.instrumentIns)
            this.instrumentIns.add(new InstrumentIn(instrumentIn));
      }
   }

   /**
    * @return the alternate ending.
    */
   public AlternateEnding getAlternateEnding()
   {
      return alternateEnding;
   }

   /**
    * sets the alternate ending.
    * <br/><br/>
    * @param alternateEnding   the alternate ending.
    */
   public void setAlternateEnding(AlternateEnding alternateEnding)
   {
      this.alternateEnding = alternateEnding;
   }

   /**
    * adds directions that occur on this bar line.
    * <br/><br/>
    * @param directions   list of music directions.
    */
   public void addDirections(Direction[] directions)
   {
      if (directions != null)
      {
         for(Direction direction : directions)
            this.directions.add(direction);
      }
   }

   /**
    * @return the list of music directions.
    */
   public ArrayList<Direction> getDirections()
   {
      return directions;
   }

   /**
    * sets the directions that occur on this bar line.
    * <br/><br/>
    * @param directions   the list of music directions.
    */
   public void setDirections(ArrayList<Direction> directions)
   {
      this.directions = new ArrayList<Direction>();

      if (directions != null)
      {
         for(Direction direction : directions)
            this.directions.add(direction);
      }
   }

   /**
    * sets the directions that occur on this bar line.
    * <br/><br/>
    * @param directions   the list of music directions.
    */
   public void setDirections(Direction[] directions)
   {
      this.directions = new ArrayList<Direction>();

      if (directions != null)
      {
         for(Direction direction : directions)
            this.directions.add(direction);
      }
   }

   /**
    * sets the music directions that occur on this bar line.
    * <br/><br/>
    * @param directions   the list of music directions.
    */
   private void setDirections(Object[] directions)
   {
      this.directions = new ArrayList<Direction>(0);

      if (directions != null)
      {
         for(Object direction : directions)
            this.directions.add((Direction)direction);
      }
   }

   /**
    * @return the volume change for the specified staff, or null if there is none.
    */
   public Volume getVolume(byte staff)
   {
      Volume dynamic = null;

      for(Volume volume : volumes)
      {
         if (volume.getStaff() == staff)
         {
            dynamic = volume;
            break;
         }
      }
      return dynamic;
   }

   /**
    * @return the volume changes for all staffs.
    */
   public ArrayList<Volume> getVolumes()
   {
      return volumes;
   }

   /**
    * @param volumes   the volume changes for all staffs.
    */
   public void setVolumes(Volume[] volumes)
   {
      this.volumes = new ArrayList<Volume>(0);

      if (volumes != null)
      {
         for(Volume volume : volumes)
            this.volumes.add(volume);
      }
   }

   /**
    * @param volumes   the volume changes for the staffs.
    */
   private void setVolumes(Object[] volumes)
   {
      this.volumes = new ArrayList<Volume>(0);

      if (volumes != null)
      {
         for(Object volume : volumes)
            this.volumes.add((Volume)volume);
      }
   }

   /**
    * @return the list of the new instruments that come in, if any, on which staffs for this bar line.
    */
   public ArrayList<InstrumentIn> getInstrumentIns()
   {
      return instrumentIns;
   }

   /**
    * sets the new instruments that come in, if any, on which staffs for this bar line.
    * <br/><br/>
    * @param instrumentIns   the list of the new instruments that come in, if any, on which staffs for this bar line.
    */
   public void setInstrumentIns(InstrumentIn[] instrumentIns)
   {
      this.instrumentIns = new ArrayList<InstrumentIn>();

      if (instrumentIns != null)
      {
         for(InstrumentIn instrumentIn : instrumentIns)
            this.instrumentIns.add(instrumentIn);
      }
   }

   /**
    * sets the new instruments that come in, if any, on which staffs for this bar line.
    * <br/><br/>
    * @param instrumentsIns   the list of the new instruments that come in, if any, on which staffs for this bar line.
    */
   private void setInstrumentIns(Object[] instrumentIns)
   {
      this.instrumentIns = new ArrayList<InstrumentIn>(0);

      if (instrumentIns != null)
      {
         for(Object instrumentIn : instrumentIns)
            this.instrumentIns.add((InstrumentIn)instrumentIn);
      }
   }

   /**
    * @return the key signature.
    */
   public KeySignature getKeySignature()
   {
      return keySignature;
   }

   /**
    * sets the key signature.
    * <br/><br/>
    * @param keySignature   the key signature.
    */
   public void setKeySignature(KeySignature keySignature)
   {
      this.keySignature = keySignature;
   }

   /**
    * @return the number of repeats associated with this bar line.
    */
   public byte getNumRepeats()
   {
      return numRepeats;
   }

   /**
    * sets the number of repeats associated with this bar line.  for example, when a repeat end bar line is encountered.
    * <br/><br/>
    * @param numRepeats the number of repeats associated with this bar line.
    */
   public void setNumRepeats(byte numRepeats)
   {
      if (numRepeats != 0 && (numRepeats < MIN_REPEAT_COUNT || numRepeats > MAX_REPEAT_COUNT))
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("text.number_of_repeats"), numRepeats, MIN_REPEAT_COUNT, MAX_REPEAT_COUNT));

      this.numRepeats = numRepeats;
   }

   /**
    * @return the drawing position.
    */
   public byte getPosition()
   {
      return position;
   }

   /**
    * sets the drawing position within the section where the bar line occurs.
    * <br/><br/>
    * @param position   the drawing position within the section where the bar line occurs.
    */
   public void setPosition(byte position)
   {
      this.position = position;
   }

   /**
    * @return the rehearsal sign.
    */
   public RehearsalSign getRehearsalSign()
   {
      return rehearsalSign;
   }

   /**
    * sets the rehearsal sign.
    * <br/><br/>
    * @param rehearsalSign   the rehearsal sign.
    */
   public void setRehearsalSign(RehearsalSign rehearsalSign)
   {
      this.rehearsalSign = rehearsalSign;
   }

   /**
    * @return the time signature.
    */
   public TimeSignature getTimeSignature()
   {
      return timeSignature;
   }

   /**
    * sets the time signature.
    * <br/><br/>
    * @param timeSignature   the time signature.
    */
   public void setTimeSignature(TimeSignature timeSignature)
   {
      this.timeSignature = timeSignature;
   }

   /**
    * @return the tempo change marker.
    */
   public TempoMarker getTempoMarker()
   {
      return tempoMarker;
   }

   /**
    * sets the tempo change marker.
    * <br/><br/>
    * @param tempoMarker   the tempo change marker.
    */
   public void setTempoMarker(TempoMarker tempoMarker)
   {
      this.tempoMarker = tempoMarker;
   }

   /**
    * @return the type of bar line.
    */
   public Type getType()
   {
      return type;
   }

   /**
    * @param type  the bar line type.
    * <br/><br/>
    * @return the bar line type enum corresponding to the integer bar line type.
    */
   public static Type getType(int type)
   {
      for (Type t : Type.values())
         if (type == t.ordinal())
            return t;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.barline"), type, Type.DoubleBarFine.ordinal()));
   }

   /**
    * sets the type of bar line.
    * <br/><br/>
    * @param type  the type of bar line.
    */
   public void setType(Type type)
   {
      this.type = type;
   }

   /**
    * sets the type of bar line from the integer type.
    * <br/><br/>
    * @param type  the integer type of bar line.
    */
   public void setType(int type)
   {
      this.type = getType(type);
   }

   /**
    * @return the number of midi pulses that occur during the measure demarcated by the barline, or 0 if it is the last barline in a section.
    */
   public long getMeasureDuration()
   {
      return Midi.getDuration(timeSignature.getBeatsPerMeasure(), timeSignature.getBeatAmount());
   }

   /**
    * @return whether this barline is an end barline (last barline in a section).
    */
   public boolean isEnd()
   {
      return alternateEnding == null && keySignature.equals(new KeySignature()) && rehearsalSign == null && tempoMarker == null && volumes.size() == 0 &&
             instrumentIns.size() == 0 && timeSignature.getBeatsPerMeasure() == 0 && timeSignature.getBeatAmount() == 0 &&
             !timeSignature.isShown() && !timeSignature.isCommonTime() && !timeSignature.isCutTime();
   }

   /**
    * @return whether this barline is a repeat barline.
    */
   public boolean isRepeat()
   {
      return type == Type.RepeatStart || type == Type.RepeatEnd || type == Type.RepeatEndStart;
   }

   /**
    * @return whether two barlines are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof Barline)
      {
         Barline barline = (Barline)object;
         equal = position              == barline.position              &&
                 type                  == barline.type                  &&
                 numRepeats            == barline.numRepeats            &&
                 directions    .size() == barline.directions    .size() &&
                 volumes.size() == barline.volumes.size() &&
                 instrumentIns .size() == barline.instrumentIns .size() &&
                ((alternateEnding == null && barline.alternateEnding == null) || (alternateEnding != null && barline.alternateEnding != null && alternateEnding.equals(barline.alternateEnding))) &&
                ((keySignature    == null && barline.keySignature    == null) || (keySignature    != null && barline.keySignature    != null && keySignature   .equals(barline.keySignature   ))) &&
                ((rehearsalSign   == null && barline.rehearsalSign   == null) || (rehearsalSign   != null && barline.rehearsalSign   != null && rehearsalSign  .equals(barline.rehearsalSign  ))) &&
                ((timeSignature   == null && barline.timeSignature   == null) || (timeSignature   != null && barline.timeSignature   != null && timeSignature  .equals(barline.timeSignature  ))) &&
                ((tempoMarker     == null && barline.tempoMarker     == null) || (tempoMarker     != null && barline.tempoMarker     != null && tempoMarker    .equals(barline.tempoMarker    )));
         if (equal)
         {
            for(int i=0; i<directions.size()     && equal; ++i)
               equal = directions    .get(i).equals(barline.directions    .get(i));
            for(int i=0; i<volumes.size() && equal; ++i)
               equal = volumes.get(i).equals(barline.volumes.get(i));
            for(int i=0; i<instrumentIns.size()  && equal; ++i)
               equal = instrumentIns .get(i).equals(barline.instrumentIns .get(i));
         }
      }
      return equal;
   }

   /**
    * read in a bar line from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the bar line from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      file.incrementMeasure();

      long pos = -1L;
      try
      {
         pos = file.getPosition();
         position   = (byte)file.read();
         type       = getType(file.read());
         numRepeats = (byte)file.read();

         keySignature .load(file);
         timeSignature.load(file);

         boolean[] has = {file.readBoolean(), file.readBoolean(), file.readBoolean()};
         if (has[0])
         {
            rehearsalSign = new RehearsalSign();
            rehearsalSign.load(file);
         }
         if (has[1])
         {
            tempoMarker = new TempoMarker();
            tempoMarker.load(file);
         }
         if (has[2])
         {
            alternateEnding = new AlternateEnding();
            alternateEnding.load(file);
         }

         setDirections   (file.readArray(Direction   .class));
         setVolumes      (file.readArray(Volume      .class));
         setInstrumentIns(file.readArray(InstrumentIn.class));

         if ((numRepeats < MIN_REPEAT_COUNT || numRepeats > MAX_REPEAT_COUNT) && numRepeats != 0)
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("text.number_of_repeats"), numRepeats, MIN_REPEAT_COUNT, MAX_REPEAT_COUNT));
         if (directions.size() > MAX_NUM_DIRECTIONS)
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("text.number_of_symbols"), directions.size(), 0, MAX_NUM_DIRECTIONS));
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.barline"));
      }
   }

   /**
    * save a bar line to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the bar line to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      file.incrementMeasure();

      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(position      );
         file.write(type.ordinal());
         file.write(numRepeats    );

         keySignature .save(file);
         timeSignature.save(file);

         file.writeBoolean(rehearsalSign   != null);
         file.writeBoolean(tempoMarker     != null);
         file.writeBoolean(alternateEnding != null);

         if (rehearsalSign   != null) rehearsalSign  .save(file);
         if (tempoMarker     != null) tempoMarker    .save(file);
         if (alternateEnding != null) alternateEnding.save(file);

         file.writeArray(directions   .toArray());
         file.writeArray(volumes      .toArray());
         file.writeArray(instrumentIns.toArray());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.barline"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab bar line.
    */
   @Override
   public String toString()
   {
      return toString(15);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab bar line.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(ResourceBundle.getString("data_type.barline") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.position"           ), indentation1) + position              + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.type"               ), indentation1) + type                  + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.num_repeats"        ), indentation1) + numRepeats            + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.key_signature" ), indentation1) + keySignature          + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.time_signature"), indentation1) + timeSignature         + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.rehearsal_sign"), indentation1) + (rehearsalSign   == null ? ResourceBundle.getString("text.none") : rehearsalSign  ) + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.tempo_marker"  ), indentation1) + (tempoMarker     == null ? ResourceBundle.getString("text.none") : tempoMarker    ) + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.alternate_ending"   ), indentation1) + (alternateEnding == null ? ResourceBundle.getString("text.none") : alternateEnding) + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.directions"         ), indentation1) + directions    .size() + "\n");
      for(int i=0; i<directions.size(); ++i)
         buffer.append(Utility.pad(ResourceBundle.getString("text.directions")       + "[" + i + "]", indentation2) + directions.get(i) + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.volumes"            ), indentation1) + volumes.size() + "\n");
      for(int i=0; i<volumes.size(); ++i)
         buffer.append(Utility.pad(ResourceBundle.getString("text.volumes"         ) + "[" + i + "]", indentation2) + volumes.get(i) + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.instrument_ins"     ), indentation1) + instrumentIns.size()  + (instrumentIns .size() == 0 ? "" : "\n"));
      for(int i=0; i<instrumentIns.size(); ++i)
         buffer.append(Utility.pad(ResourceBundle.getString("text.instrument_ins"  ) + "[" + i + "]" , indentation2) + instrumentIns.get(i) + (i == instrumentIns.size() - 1 ? "" : "\n"));

      return buffer.toString();
   }
}
