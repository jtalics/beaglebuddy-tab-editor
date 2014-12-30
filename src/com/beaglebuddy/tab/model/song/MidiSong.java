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
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Chord;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.Information;
import com.beaglebuddy.tab.model.KeySignature;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Note;
import com.beaglebuddy.tab.model.Score;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.TempoMarker;
import com.beaglebuddy.tab.model.Volume;
import com.beaglebuddy.tab.model.attribute.duration.DurationAttribute;
import com.beaglebuddy.tab.model.attribute.duration.MultibarRest;
import com.beaglebuddy.tab.model.attribute.note.NoteAttribute;
import com.beaglebuddy.tab.model.instrument.ActiveInstruments;
import com.beaglebuddy.tab.model.instrument.Drums;
import com.beaglebuddy.tab.model.instrument.Instrument.Fret;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.staff.Staff;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;




/**
 * this class can play songs on midi devices.
 * <p>
 * to play a song on a midi device, the song is converted to a midi sequence, which is a collection of midi events representing the musical notes of the song.
 * the structure of a midi file and sequence are shown below:
 *
 * midi file
 *    header
 *    midi sequence
 *      track 1 (for instrument 1)
 *         midi event 1, midi event 2, midi event 3, ..., midi event n
 *      track 2 (for instrument 2)
 *         midi event 1, midi event 2, midi event 3, ..., midi event n
 *      track 3 (for instrument 3)
 *         midi event 1, midi event 2, midi event 3, ..., midi event n
 *        .
 *        .
 *        .
 *      track n (for instrument n)
 *         midi event 1, midi event 2, midi event 3, ..., midi event n
 * </p>
 */
public class MidiSong extends BaseSong
{
   // data members
   private Sequencer  sequencer;     // the midi sequencer used to create the midi sequence representation of the song
   private MidiDevice midiDevice;    // the midi device on which the song will be played back on
   public Sequence sequence;        // the collection of tracks with midi events


   /**
    * default constructor.
    */
   public MidiSong()
   {
      setSequencer();
   }

   /**
    * constructor.
    * <br/><br/>
    * @param filename   name of the beaglebuddy tab (.bbt) file
    * <br/><br/>
    * @throws FileReadException   if the specified beaglebuddy tab (.bbt) file can not be loaded.
    */
   public MidiSong(String filename) throws FileReadException
   {
      super(filename);
      setSequencer();
   }

   /**
    * constructor.
    * <br/><br/>
    * @param information    information about the song.
    * @param chordDiagrams  chord diagrams for the score.
    * @param score          the music for the song.
    */
   public MidiSong(Information information, ArrayList<ChordDiagram> chordDiagrams, Score score)
   {
      super(information, chordDiagrams, score);
      setSequencer();
   }

   /**
    * assigns each instrument in the song to a unique midi channel.
    */
   private void assignMidiChannelsToInstruments()
   {
      int channel = 0;
      for(Instrument instrument : getScore().getInstruments())
      {
         if (instrument instanceof Drums)
         {
            instrument.setMidiChannel(Midi.Drum_Channel);
         }
         else
         {
            instrument.setMidiChannel(Midi.getChannel(channel));
            channel++;
            if (channel == Midi.Drum_Channel.ordinal())
               channel++;
         }
      }
   }

   /**
    * @return a midi track containing the midi events for playing the specified instrument's part of the song.
    * <br/><br/>
    * @param track               an empty midi track that will be filled in with the specified instrument's part of the song.
    * @param instrument          the instrument for which this track is being created.
    * @param activeInstruments   data structure used to keep track of which instruments are assigned to which staffs.
    * @param sections            the score's sections containing the music to be played.
    * <br/><br/>
    * @throws InvalidMidiDataException   if the midi data is invalid.
    */
   private Track createTrack(Track track, Instrument instrument, ActiveInstruments activeInstruments, ArrayList<Section> sections) throws InvalidMidiDataException
   {
      // if there isn't any music to play, then simply return the empty midi track
      if (sections == null || sections.size() == 0)
         return track;

      Staff          staff               = null;                           // the current staff being processed
      Barline        bar                 = null;                           // temporary work variable
      Barline        barline             = null;                           // the barline for the measure being processed
      Chord          chord               = null;                           // current chord being processed
      long           midiTime            = 0L;                             // the current midi time in the track
      long           midiDuration        = 0L;                             // the midi duration of a note
      byte           numDrawingPositions = 0;                              // number of drawing positions in a staff
      Volume.Level   midiVolume          = instrument.getInitialVolume();  // the midi volume the instrument is being played at
      KeySignature   key                 = null;                           // key for the current measure
                                                                           //
      Stack<RepeatStackEntry> repeats = new Stack<RepeatStackEntry>();
      // tell the track which midi sound to use for this instrument during playback
      track.add(Midi.createEventProgramChange(0L, instrument.getMidiChannel(), instrument.getPreset()));
      boolean isAlternateEndingNotFound = false;

      Map<Note,MidiEvent> prevChordMap=null, currentChordMap=null; // for processing ties

      // create the midi sequence for the song
      for (int sectionNumber = 0; sectionNumber < sections.size(); sectionNumber++)
      {
         Section section = sections.get(sectionNumber);
         numDrawingPositions  = section.getNumDrawingPositions();
         byte drawingPosition = 0;
         for (; drawingPosition <= numDrawingPositions; drawingPosition++)
         {
            // see if there is a bar line at this drawing position
            if ((bar = getBarline(section, drawingPosition)) != null)
            {
               barline = bar;
//             midiTime += measureDuration;
//             measureDuration = barline.getMeasureDuration();

               // get any key changes for this measure
               key = barline.getKeySignature();
               // update the active instruments with any new instrument ins at this measure
               activeInstruments.set(barline.getInstrumentIns());

               // get the staff to which the instrument is assigned, or -1 if it is not assigned.
               byte staffNumber = activeInstruments.getAssignedStaff(instrument.getId());
               if (staffNumber == -1 || staffNumber >= section.getStaffs().size())
               {
                  staff = null;
                  // if the barline is not an ending barline (ie, the last barline in the section)
                  if (!barline.isEnd())
                     midiTime += barline.getMeasureDuration();
               }
               else
               {
                  staff = section.getStaffs().get(staffNumber);
               }

               // see if there is a tempo change at this measure
               TempoMarker tempoMarker = barline.getTempoMarker();
               if (tempoMarker != null)
               {
                  MidiEvent midiEventTempoChange = Midi.createEventTempoChange(midiTime, tempoMarker.getBeatsPerMinute());
                  track.add(midiEventTempoChange);
               }

               // see if there is a volume change at this measure
               Volume volume = barline.getVolume(staffNumber);
               if (volume != null)
                  midiVolume = volume.getLevel();

               // Process alternate ending and repeats
               if (barline.getType() == Barline.Type.RepeatStart)
               {
                  if (repeats.isEmpty())
                  {
                     repeats.push(new RepeatStackEntry(sectionNumber, barline.getPosition(), (byte) (-1)));
                  }
                  else
                  {
                     RepeatStackEntry lastEntry = repeats.pop();
                     if(repeats.isEmpty() || repeats.peek().getPosition() != barline.getPosition())
                     {
                        repeats.push(lastEntry);
                        repeats.push(new RepeatStackEntry(sectionNumber, barline.getPosition(), (byte) (-1)));
                     }
                     else
                     {
                        repeats.push(lastEntry);
                     }
                  }
               }
               else if (barline.getType() == Barline.Type.RepeatEnd)
               {
                  if (repeats.peek().getPosition() != barline.getPosition())
                  {  // alternate ending becomes the num repeat
                     if (repeats.peek().getNumRepeats() == -1)
                        repeats.push(new RepeatStackEntry(sectionNumber, barline.getPosition(),(byte) (barline.getNumRepeats() - 1)));
                     else
                        repeats.push(new RepeatStackEntry(sectionNumber, barline.getPosition(), repeats.peek().getNumRepeats()));
                  }
                  RepeatStackEntry lastEntry  = repeats.pop();
                  RepeatStackEntry startEntry = repeats.pop();
                  if (lastEntry.toBeRepeated())
                  {
                     sectionNumber = startEntry.getSectionNumber();
                     drawingPosition = (byte)(startEntry.getPosition() - 1); // for re-init objects at this positions (barlines,staves etc)
                     section = sections.get(sectionNumber);
                     numDrawingPositions = section.getNumDrawingPositions();
                     repeats.push(startEntry);
                     repeats.push(lastEntry);
                  }
                  else
                  {
                     sectionNumber = lastEntry.getSectionNumber();
                     drawingPosition = lastEntry.getPosition();
                     section = sections.get(sectionNumber);
                     numDrawingPositions = section.getNumDrawingPositions();
                  }
               }
               else
               {
                  if ((barline.getAlternateEnding() != null && barline.getAlternateEnding().getNumbers().length > 0)|| isAlternateEndingNotFound)
                  {
                     if (!repeats.isEmpty())
                     {  // condition will only be true when there is only the
                        // repeat start in stack
                        if (repeats.peek().getNumRepeats() == -1)
                        {
                           RepeatStackEntry startEntry = repeats.pop();
                           startEntry.setNumRepeats((byte) (barline.getAlternateEnding().getNumbers().length));
                           repeats.push(startEntry);
                        }
                        else if (repeats.peek().getRemainingRepeats() == 0)
                        {  // do not repeat this bar , move to next repeat end bar
                           Barline tmpBarline = new Barline();
                           while ((tmpBarline = getBarline(section, ++drawingPosition)) == null){}
                           isAlternateEndingNotFound = tmpBarline.getType() != Barline.Type.RepeatEnd;

                           if (numDrawingPositions != drawingPosition) // otherwise it will loop
                              --drawingPosition;
                        }
                     }
                  }
               }
            } // end if barline
            // if the instrument is not assigned to this staff
            else if (staff != null)
            {
               // is there a chord at this drawing position?
               if ((chord = getChord(staff, drawingPosition)) != null)
               {
                  if (chord.isRest())
                  {
                     midiTime += chord.getMidiPulses();
                  }
                  else if (chord.isMultiBarRest())
                  {
                     MultibarRest multibarRest = (MultibarRest)chord.getDurationAttribute(DurationAttribute.Type.MultibarRest);
                     midiTime += barline.getTimeSignature().getMidiPulses() * multibarRest.getNumMeasures();
                  }
                  // otherwise, it's a single note or a real musical chord
                  else
                  {
                     ArrayList<Note> notes = chord.getNotes();
                     currentChordMap = new HashMap<Note,MidiEvent>();
                     for(Note note : notes)
                     {
                        // calculate what note to play
                        // determine what musical note it is from the instrument's string, fret, and tuning
                        Midi.Note midiNote = Midi.getNote(note.getString(), note.getFret(), instrument.getTuning(), instrument.getCapo(), key.usesSharps());
                        midiDuration = chord.getMidiPulses();
                        if (note.isTied())
                        {
                           // Don't add note-on event, it was already taken care of by the first note in the tie sequence,
                           // but remove the note-off event to the tied-to note.
                           if (prevChordMap != null) {
                              Fret fret = note.getFret();
                              Instrument.String string = note.getString();
                              for (Note prevNote : prevChordMap.keySet()) {
                                 if (prevNote.getFret().equals(fret) && prevNote.getString().equals(string)) {
                                    track.remove(prevChordMap.get(prevNote));
                                 }
                              }
                           }
                        }
                        else
                        {
                           track.add(Midi.createEventNoteOn(midiTime, instrument.getMidiChannel(), midiNote, midiVolume));

                        }
                        // Add the note-off event to the track but if any notes in the next 
                        // chord tie back to this note, we'll need to remove tied-to note-off event.
                        MidiEvent noteOffEvent = Midi.createEventNoteOff(midiTime + midiDuration, instrument.getMidiChannel(), midiNote);
                        currentChordMap.put(note,noteOffEvent);
                        track.add(noteOffEvent);
                     } // next note in chord
                     prevChordMap = currentChordMap;
                     midiTime += midiDuration;
                  }
               } // end if chord
            } // end if staff
         } // next drawing position
         // System.out.println("createTrack: section " + sectionNumber);
      }
      return track;
   }

   /**
    * @return the instrument's staff assignments at the specified location.
    * all "instrument ins" are processed up to the specified location, but does not include the barline at the specified location.
    * <br/><br/>
    * @param location   the location at which the instrument's staff assignments are desired.
    */
   public ActiveInstruments getActiveInstruments(Location location)
   {
      ActiveInstruments activeInstruments = new ActiveInstruments(getScore().getInstruments());

      // iterate over the sections up to, but not including, the specified location, applying the "instrument ins" as they are encountered
      for(int i=0; i<location.getSection(); ++i)
      {
         Section section = getScore().getSections().get(i);
         for(Barline barline : section.getBarlines())
            activeInstruments.set(barline.getInstrumentIns());
      }

      // apply the "instrument ins" at the section spcified by the location
      Section section = getScore().getSections().get(location.getSection());

      // only process the barlines in the section up to the location, but not including the barline at the specified location
      ArrayList<Barline> barlines = section.getBarlines();
      for(int i=0; i<location.getBarline(); ++i)
         activeInstruments.set(barlines.get(i).getInstrumentIns());

      return activeInstruments;
   }

   /**
    * @return the barline that occurs at the specified drawing position if there is one, and null otherwise.
    * <br/><br/>
    * @param section    the section.
    * @param position   the drawing position.
    */
   private Barline getBarline(Section section, byte position)
   {
      Barline            barline  = null;
      ArrayList<Barline> barlines = section.getBarlines();

      for (Barline bar : barlines)
      {
         if (bar.getPosition() == position)
         {
            barline = bar;
            break;
         }
      }
      return barline;
   }

   /**
    * @return the music chord that occurs at the specified drawing position on the staff if there is one, and null otherwise.
    * <br/><br/>
    * @param staff             the staff
    * @param drawingPosition   the drawing position.
    */
   private Chord getChord(Staff staff, byte drawingPosition)
   {
      Chord            chord  = null;
      ArrayList<Chord> chords = staff.getChords(Staff.HighVoice);      // todo: fix this - need to check both high and low voices

      for(Chord c : chords)
      {
         if (c.getPosition() == drawingPosition)
         {
            chord = c;
            break;
         }
      }
      return chord;
   }

   /**
    * @return the index of the chord which has the specified drawing position on the given staff, or -1 if no chord is found at that drawing position.
    * <br/><br/>
    * @param melodyLine
    * @param drawingPosition
    */
   private byte getMelodyLineIndex(ArrayList<Chord> melodyLine, byte drawingPosition)
   {
      boolean found = false;
      byte    index = 0;

      for(index=0; index<melodyLine.size() && !found; ++index)
      {
         Chord chord = melodyLine.get(index);
         found = chord.getPosition() == drawingPosition;
      }
      return (byte)(found ? index-1 : -1);
   }

   /**
    * @return the song represented as a midi sequence, using a separate track for each instrument in the song.
    * <br/><br/>
    * @throws MidiUnavailableException   if
    * @throws InvalidMidiDataException   if
    */
   private void createSequence() throws MidiUnavailableException, InvalidMidiDataException
   {
      // create the midi sequence that will use pulses for timing
      sequence = new Sequence(Sequence.PPQ, Midi.Duration.PPQN.pulses());

      // create a separate track for each instrument
      ArrayList<Instrument> instruments = getScore().getInstruments();
      for (Instrument instrument : instruments)
         if (Instrument.Type.Guitar.equals(instrument.getType()))  //FIXME: Remove this code later
            createTrack(sequence.createTrack(), instrument, new ActiveInstruments(instruments),getScore().getSections());
   }

   /**
    * sets the sequencer which will be used to play songs back on the user's computer.
    */
   private void setSequencer()
   {
      try
      {
         sequencer = Midi.getSequencer();
      }
      catch (MidiUnavailableException ex)
      {
         // no code necessary.  users will simply not be able to play songs back.
      }
   }

   /**
    * plays the song back on the specified midi device starting at the given location.
    * <br/><br/>
    * @param midiDevice   the midi synthesizer on which the song will be played.
    * @param listener     an object that implements the MetaEventListener interface and which listens for the Midi.EVENT_END_OF_TRACK event.
    * @param location     location within the song to start playing from.
    * <br/><br/>
    * @throws InvalidMidiDataException  if the midi sequence contains an invalid midi event.
    * @throws MidiUnavailableException  if the midi device on which the sequence is to be played is unavailable.
    */
   public void play(MidiDevice midiDevice, MetaEventListener listener, Location location) throws InvalidMidiDataException, MidiUnavailableException
   {
      // save the midi device the song is being played on so that we can stop it later on
      this.midiDevice = midiDevice;

      // assign each instrument in the song to a unique midi channel
      assignMidiChannelsToInstruments();

      // create a midi sequence representing the song
      createSequence();

      // todo: remove - this code is for debugging purposes only
      try
      {
         String     tabHome = System.getProperty("tab.home");
         FileWriter file    = new FileWriter(tabHome + "/sequence.txt");
         file.write(Midi.toString(sequence));
         file.close();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
      // end todo:

      sequencer.open();
      // add a listener for when the midi sequence stops playing
      if (listener != null)
         sequencer.addMetaEventListener(listener);

      // hook up the sequencer to the receiving midi device
      Receiver    midiReceiver    = midiDevice.getReceiver();
      Transmitter midiTransmitter = sequencer .getTransmitter();
      midiTransmitter.setReceiver(midiReceiver);

      // specify the midi sequence to play
      sequencer.setSequence(sequence);

      // play the sequence starting at the specified location
      long midiStartTime = 0L;
      if (location.getSection() != 1 || location.getStaff() != 1 || location.getMeasure() != 1 || location.getPosition() != 1)
      {
         // midiStartTime = todo: calculate the number of midi pulses from the measure
      }
      sequencer.setTickPosition(midiStartTime);
      sequencer.start();
   }

   /**
    * stops the song playing back.
    */
   public void stop()
   {
      if (sequencer != null && sequencer.isOpen())
         sequencer.close();
      if (midiDevice != null && midiDevice.isOpen())
         midiDevice.close();
      this.midiDevice = null;
   }
   
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(super.toString());
    if (sequence == null) {
      sb.append("SEQUENCE is null.");
    }
    else {
      sb.append("SEQUENCE ---");
      sb.append("\nDivision type: " + sequence.getDivisionType());
      sb.append("\nMicrosecond length: " + sequence.getMicrosecondLength());
      sb.append("\nResolution: " + sequence.getResolution());
      sb.append("\nTick length: " + sequence.getTickLength());
      sb.append("\nPATCHLIST ---");
      for (Patch patch : sequence.getPatchList()) {
        sb.append("\nPATCH" + MidiSongDump.toStringPatch(patch));
      }
      sb.append("\nTRACKS ---");
      Track[] tracks = sequence.getTracks();
      for (int i = 0; i < tracks.length; i++) {
        Track track = tracks[i];
        sb.append("\nTRACK[" + i + "]" + MidiSongDump.toStringTrack(track));
      }
    }
    return sb.toString();
  }
  
  public void buildMidiSequence() throws MidiUnavailableException, InvalidMidiDataException {
    assignMidiChannelsToInstruments();
    createSequence();
  }

}


/** Purpose is to provide methods to decode Midi for a debug dump
 * @author jtalafous
 * TODO: move to class Midi
 */
class MidiSongDump {
  static String toStringTrack(Track track) {
    StringBuilder sb = new StringBuilder();
    sb.append("\n   Tick count: " + track.ticks());
    sb.append("\n   Midi event count: " + track.size());
    int n = track.size();
    for (int i = 0; i < n; i++) {
      MidiEvent midiEvent = track.get(i);
      sb.append("\n   Midi event " + i);
      sb.append("\n      time: " + midiEvent.getTick());
      MidiMessage midiMessage = midiEvent.getMessage();
      // sb.append("\n         raw status: " + midiMessage.getStatus());
      // int len = midiMessage.getLength();
      // sb.append("\n         message byte length: "+len);
      // for (int j=0; j<len; j++) {
      // sb.append("\n         byte["+j+"]: " + midiMessage.getMessage()[j]);
      // }
      String strMessage = null;
      if (midiMessage instanceof ShortMessage) {
        strMessage = decodeMessage((ShortMessage) midiMessage);
      }
      else if (midiMessage instanceof SysexMessage) {
        strMessage = decodeMessage((SysexMessage) midiMessage);
      }
      else if (midiMessage instanceof MetaMessage) {
        strMessage = decodeMessage((MetaMessage) midiMessage);
      }
      else {
        strMessage = "unknown message type";
      }
      sb.append("\n      message: " + strMessage);
    }
    sb.append("\n   ( e n d   t r a c k )");
    return sb.toString();
  }

  public static String decodeMessage(ShortMessage message) {
    String strMessage = null;
    switch (message.getCommand()) {
    case ShortMessage.NOTE_OFF: // 0x80:
      strMessage = "note Off " + getKeyName(message.getData1())
          + " velocity: " + message.getData2();
      break;
    case ShortMessage.NOTE_ON: // 0x90:
      strMessage = "note On " + getKeyName(message.getData1())
          + " velocity: " + message.getData2();
      break;
    case ShortMessage.POLY_PRESSURE: // 0xa0:
      strMessage = "polyphonic key pressure "
          + getKeyName(message.getData1()) + " pressure: "
          + message.getData2();
      break;
    case ShortMessage.CONTROL_CHANGE: // 0xb0:
      strMessage = "control change " + message.getData1() + " value: "
          + message.getData2();
      break;
    case ShortMessage.PROGRAM_CHANGE: // 0xc0:
      strMessage = "program change " + message.getData1();
      break;
    case ShortMessage.CHANNEL_PRESSURE:// 0xd0:
      strMessage = "key pressure " + getKeyName(message.getData1())
          + " pressure: " + message.getData2();
      break;
    case ShortMessage.PITCH_BEND: // 0xe0:
      strMessage = "pitch wheel change "
          + get14bitValue(message.getData1(), message.getData2());
      break;
    case 0xF0: // first bit set on status byte
      strMessage = SYSTEM_MESSAGE_TEXT[message.getChannel()];
      switch (message.getChannel()) {
      case 0x1:
        int nQType = (message.getData1() & 0x70) >> 4;
        int nQData = message.getData1() & 0x0F;
        if (nQType == 7) {
          nQData = nQData & 0x1;
        }
        strMessage += QUARTER_FRAME_MESSAGE_TEXT[nQType] + nQData;
        if (nQType == 7) {
          int nFrameType = (message.getData1() & 0x06) >> 1;
          strMessage += ", frame type: " + FRAME_TYPE_TEXT[nFrameType];
        }
        break;
      case 0x2:
        strMessage += get14bitValue(message.getData1(), message.getData2());
        break;
      case 0x3:
        strMessage += message.getData1();
        break;
      }
      break;
    default:
      strMessage = "unknown message: status = " + message.getStatus()
          + ", byte1 = " + message.getData1() + ", byte2 = "
          + message.getData2();
      break;
    }
    if (message.getCommand() != 0xF0) {
      int nChannel = message.getChannel() + 1;
      String strChannel = "channel " + nChannel + ": ";
      strMessage = strChannel + strMessage;
    }
    smCount++;
    smByteCount += message.getLength();
    return "[" + getHexString(message) + "] " + strMessage;
  }

  public static String decodeMessage(SysexMessage message) {
    byte[] abData = message.getData();
    String strMessage = null;
    // System.out.println("sysex status: " + message.getStatus());
    if (message.getStatus() == SysexMessage.SYSTEM_EXCLUSIVE) {
      strMessage = "Sysex message: F0" + getHexString(abData);
    }
    else if (message.getStatus() == SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE) {
      strMessage = "Continued Sysex message F7" + getHexString(abData);
      seByteCount--; // do not count the F7
    }
    seByteCount += abData.length + 1;
    seCount++; // for the status byte
    return strMessage;
  }

  public static String decodeMessage(MetaMessage message) {
    byte[] abMessage = message.getMessage();
    byte[] abData = message.getData();
    int nDataLength = message.getLength();
    String strMessage = null;
    // System.out.println("data array length: " + abData.length);
    switch (message.getType()) {
    case 0:
      int nSequenceNumber = ((abData[0] & 0xFF) << 8) | (abData[1] & 0xFF);
      strMessage = "Sequence Number: " + nSequenceNumber;
      break;
    case 1:
      String strText = new String(abData);
      strMessage = "Text Event: " + strText;
      break;
    case 2:
      String strCopyrightText = new String(abData);
      strMessage = "Copyright Notice: " + strCopyrightText;
      break;
    case 3:
      String strTrackName = new String(abData);
      strMessage = "Sequence/Track Name: " + strTrackName;
      break;
    case 4:
      String strInstrumentName = new String(abData);
      strMessage = "Instrument Name: " + strInstrumentName;
      break;
    case 5:
      String strLyrics = new String(abData);
      strMessage = "Lyric: " + strLyrics;
      break;
    case 6:
      String strMarkerText = new String(abData);
      strMessage = "Marker: " + strMarkerText;
      break;
    case 7:
      String strCuePointText = new String(abData);
      strMessage = "Cue Point: " + strCuePointText;
      break;
    case 0x20:
      int nChannelPrefix = abData[0] & 0xFF;
      strMessage = "MIDI Channel Prefix: " + nChannelPrefix;
      break;
    case 0x2F:
      strMessage = "End of Track";
      break;
    case 0x51:
      int nTempo = ((abData[0] & 0xFF) << 16) | ((abData[1] & 0xFF) << 8)
          | (abData[2] & 0xFF); // tempo in microseconds per beat
      float bpm = convertTempo(nTempo);
      // truncate it to 2 digits after dot
      bpm = (float) (Math.round(bpm * 100.0f) / 100.0f);
      strMessage = "Set Tempo: " + bpm + " bpm";
      break;
    case 0x54:
      // System.out.println("data array length: " + abData.length);
      strMessage = "SMTPE Offset: " + (abData[0] & 0xFF) + ":"
          + (abData[1] & 0xFF) + ":" + (abData[2] & 0xFF) + "."
          + (abData[3] & 0xFF) + "." + (abData[4] & 0xFF);
      break;
    case 0x58:
      strMessage = "Time Signature: " + (abData[0] & 0xFF) + "/"
          + (1 << (abData[1] & 0xFF)) + ", MIDI clocks per metronome tick: "
          + (abData[2] & 0xFF) + ", 1/32 per 24 MIDI clocks: "
          + (abData[3] & 0xFF);
      break;
    case 0x59:
      String strGender = (abData[1] == 1) ? "minor" : "major";
      strMessage = "Key Signature: " + sm_astrKeySignatures[abData[0] + 7]
          + " " + strGender;
      break;
    case 0x7F:
      // TODO: decode vendor code, dump data in rows
      String strDataDump = getHexString(abData);
      strMessage = "Sequencer-Specific Meta event: " + strDataDump;
      break;
    default:
      String strUnknownDump = getHexString(abData);
      strMessage = "unknown Meta event: " + strUnknownDump;
      break;
    }
    return strMessage;
  }

  public static String getKeyName(int nKeyNumber) {
    if (nKeyNumber > 127) {
      return "illegal value";
    }
    else {
      int nNote = nKeyNumber % 12;
      int nOctave = nKeyNumber / 12;
      return sm_astrKeyNames[nNote] + (nOctave - 1);
    }
  }

  public static int get14bitValue(int nLowerPart, int nHigherPart) {
    return (nLowerPart & 0x7F) | ((nHigherPart & 0x7F) << 7);
  }

  private static int signedByteToUnsigned(byte b) {
    return b & 0xFF;
  }

  // convert from microseconds per quarter note to beats per minute and vice
  // versa
  private static float convertTempo(float value) {
    if (value <= 0) {
      value = 0.1f;
    }
    return 60000000.0f / value;
  }

  public static long seByteCount = 0;
  public static long smByteCount = 0;
  public static long seCount = 0;
  public static long smCount = 0;
  private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  public static String getHexString(byte[] aByte) {
    StringBuffer sbuf = new StringBuffer(aByte.length * 3 + 2);
    for (int i = 0; i < aByte.length; i++) {
      sbuf.append(' ');
      sbuf.append(hexDigits[(aByte[i] & 0xF0) >> 4]);
      sbuf.append(hexDigits[aByte[i] & 0x0F]);
      /*
       * byte bhigh = (byte) ((aByte[i] & 0xf0) >> 4); sbuf.append((char)
       * (bhigh > 9 ? bhigh + 'A' - 10: bhigh + '0')); byte blow = (byte)
       * (aByte[i] & 0x0f); sbuf.append((char) (blow > 9 ? blow + 'A' - 10:
       * blow + '0'));
       */
    }
    return new String(sbuf);
  }

  private static String intToHex(int i) {
    return "" + hexDigits[(i & 0xF0) >> 4] + hexDigits[i & 0x0F];
  }

  public static String getHexString(ShortMessage sm) {
    // bug in J2SDK 1.4.1
    // return getHexString(sm.getMessage());
    int status = sm.getStatus();
    String res = intToHex(sm.getStatus());
    // if one-byte message, return
    switch (status) {
    case 0xF6: // Tune Request
    case 0xF7: // EOX
      // System real-time messages
    case 0xF8: // Timing Clock
    case 0xF9: // Undefined
    case 0xFA: // Start
    case 0xFB: // Continue
    case 0xFC: // Stop
    case 0xFD: // Undefined
    case 0xFE: // Active Sensing
    case 0xFF:
      return res;
    }
    res += ' ' + intToHex(sm.getData1());
    // if 2-byte message, return
    switch (status) {
    case 0xF1: // MTC Quarter Frame
    case 0xF3: // Song Select
      return res;
    }
    switch (sm.getCommand()) {
    case 0xC0:
    case 0xD0:
      return res;
    }
    // 3-byte messages left
    res += ' ' + intToHex(sm.getData2());
    return res;
  }

  private static final String[] SYSTEM_MESSAGE_TEXT = {
      "System Exclusive (should not be in ShortMessage!)",
      "MTC Quarter Frame: ", "Song Position: ", "Song Select: ", "Undefined",
      "Undefined", "Tune Request",
      "End of SysEx (should not be in ShortMessage!)", "Timing clock",
      "Undefined", "Start", "Continue", "Stop", "Undefined",
      "Active Sensing", "System Reset" };
  private static final String[] QUARTER_FRAME_MESSAGE_TEXT = {
      "frame count LS: ", "frame count MS: ", "seconds count LS: ",
      "seconds count MS: ", "minutes count LS: ", "minutes count MS: ",
      "hours count LS: ", "hours count MS: " };
  private static final String[] FRAME_TYPE_TEXT = { "24 frames/second",
      "25 frames/second", "30 frames/second (drop)",
      "30 frames/second (non-drop)", };
  private static final String[] sm_astrKeyNames = { "C", "C#", "D", "D#",
      "E", "F", "F#", "G", "G#", "A", "A#", "B" };
  private static final String[] sm_astrKeySignatures = { "Cb", "Gb", "Db",
      "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#" };

  static String toStringPatch(Patch patch) {
    StringBuilder sb = new StringBuilder();
    sb.append("\n   Bank: " + patch.getBank());
    sb.append("\n   Program: " + patch.getProgram());
    return sb.toString();
  }
}
