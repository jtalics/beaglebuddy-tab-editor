/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.conversion;

import com.beaglebuddy.ptb.*;
import com.beaglebuddy.ptb.attribute.note.*;
import com.beaglebuddy.ptb.attribute.position.*;
import com.beaglebuddy.ptb.attribute.rhythm.*;
import com.beaglebuddy.ptb.io.PTBFileException;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.model.*;
import com.beaglebuddy.tab.model.attribute.chord.Arpeggio;
import com.beaglebuddy.tab.model.attribute.chord.ChordAttribute;
import com.beaglebuddy.tab.model.attribute.chord.PickStroke;
import com.beaglebuddy.tab.model.attribute.chord.TremoloBar;
import com.beaglebuddy.tab.model.attribute.chord.Vibrato;
import com.beaglebuddy.tab.model.attribute.chord.VolumeSwell;
import com.beaglebuddy.tab.model.attribute.duration.DurationAttribute;
import com.beaglebuddy.tab.model.attribute.duration.IrregularGrouping;
import com.beaglebuddy.tab.model.attribute.duration.MultibarRest;
import com.beaglebuddy.tab.model.attribute.note.*;
import com.beaglebuddy.tab.model.attribute.rhythm.*;
import com.beaglebuddy.tab.model.instrument.*;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.model.staff.BassStaff;
import com.beaglebuddy.tab.model.staff.DrumStaff;   // todo: remove this when done developing\debugging drums
import com.beaglebuddy.tab.model.staff.RhythmStaff;
import com.beaglebuddy.tab.model.staff.Staff;
import com.beaglebuddy.tab.model.staff.TrebleStaff;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;





/**
 * This utility class is responsible for converting power tab 1.70 version files (.ptb) to the beaglebuddy 1.0 version format.
 */
public class Converter_PTB_1_7_0_to_BBT_1_0
{
   // class members
   private static Hashtable<PTBDirection.ActiveSymbol   , Direction.Symbol    > activationDirectionMap = new Hashtable<PTBDirection.ActiveSymbol   , Direction.Symbol    >();
   private static Hashtable<PTBArtificialHarmonic.Octave, Octave.OctaveType   > ahOctaveMap            = new Hashtable<PTBArtificialHarmonic.Octave, Octave.OctaveType   >();
   private static Hashtable<PTBBarline.Type             , Barline.Type        > barlineMap             = new Hashtable<PTBBarline.Type             , Barline.Type        >();
   private static Hashtable<PTBPositionAttribute.Type   , ChordAttribute      > chordMap               = new Hashtable<PTBPositionAttribute.Type   , ChordAttribute      >();
   private static Hashtable<PTBPositionAttribute.Type   , DurationAttribute   > chordDurationMap       = new Hashtable<PTBPositionAttribute.Type   , DurationAttribute   >();
   private static Hashtable<PTBStaff.Clef               , Staff.Clef          > clefMap                = new Hashtable<PTBStaff.Clef               , Staff.Clef          >();
   private static Hashtable<PTBDirection.SymbolType     , Direction.Symbol    > directionMap           = new Hashtable<PTBDirection.SymbolType     , Direction.Symbol    >();
   private static Hashtable<PTBKey.Key                  , Midi.Note           > keyMidiNoteMap         = new Hashtable<PTBKey.Key                  , Midi.Note           >();
   private static Hashtable<PTBMidi.Duration            , Midi.Duration       > midDurationiMap        = new Hashtable<PTBMidi.Duration            , Midi.Duration       >();
   private static Hashtable<PTBNoteAttribute.Type       , NoteAttribute       > noteMap                = new Hashtable<PTBNoteAttribute.Type       , NoteAttribute       >();
   private static Hashtable<PTBOctave.OctaveType        , Octave.OctaveType   > octaveMap              = new Hashtable<PTBOctave.OctaveType        , Octave.OctaveType   >();
   private static Hashtable<PTBRhythmSlashAttribute.Type, RhythmSlashAttribute> rhythmMap              = new Hashtable<PTBRhythmSlashAttribute.Type, RhythmSlashAttribute>();
   private static Hashtable<PTBRhythmSlashAttribute.Type, DurationAttribute   > rhythmDurationMap      = new Hashtable<PTBRhythmSlashAttribute.Type, DurationAttribute   >();
   private static Hashtable<PTBDynamic.Volume           , Volume.Level        > volumeMap              = new Hashtable<PTBDynamic.Volume           , Volume.Level        >();

   static
   {
      activationDirectionMap.put(PTBDirection.ActiveSymbol.None                   , Direction.Symbol.None                                                  );
      activationDirectionMap.put(PTBDirection.ActiveSymbol.DaCapo                 , Direction.Symbol.DaCapo                                                );
      activationDirectionMap.put(PTBDirection.ActiveSymbol.DalSegno               , Direction.Symbol.DalSegno                                              );
      activationDirectionMap.put(PTBDirection.ActiveSymbol.DalSegnoSegno          , Direction.Symbol.DalSegnoSegno                                         );

      ahOctaveMap.put           (PTBArtificialHarmonic.Octave.OctaveLoco          , Octave.OctaveType.OctaveLoco                                           );
      ahOctaveMap.put           (PTBArtificialHarmonic.Octave.Octave8va           , Octave.OctaveType.Octave8va                                            );
      ahOctaveMap.put           (PTBArtificialHarmonic.Octave.Octave15ma          , Octave.OctaveType.Octave15ma                                           );

      barlineMap.put            (PTBBarline.Type.Bar                              , Barline.Type.Bar                                                       );
      barlineMap.put            (PTBBarline.Type.DoubleBar                        , Barline.Type.DoubleBar                                                 );
      barlineMap.put            (PTBBarline.Type.DoubleBarFine                    , Barline.Type.DoubleBarFine                                             );
      barlineMap.put            (PTBBarline.Type.FreeTimeBar                      , Barline.Type.DoubleBar                                                 );
      barlineMap.put            (PTBBarline.Type.RepeatStart                      , Barline.Type.RepeatStart                                               );
      barlineMap.put            (PTBBarline.Type.RepeatEnd                        , Barline.Type.RepeatEnd                                                 );

      chordMap.put              (PTBPositionAttribute.Type.AccentMarcato          , new com.beaglebuddy.tab.model.attribute.chord.AccentMarcato          ());
      chordMap.put              (PTBPositionAttribute.Type.AccentSforzando        , new com.beaglebuddy.tab.model.attribute.chord.AccentSforzando        ());
      chordMap.put              (PTBPositionAttribute.Type.Acciaccatura           , new com.beaglebuddy.tab.model.attribute.chord.Acciaccatura           ());
      chordMap.put              (PTBPositionAttribute.Type.Appoggiatura           , new com.beaglebuddy.tab.model.attribute.chord.Appoggiatura           ());
      chordMap.put              (PTBPositionAttribute.Type.ArpeggioDown           , new com.beaglebuddy.tab.model.attribute.chord.Arpeggio               (Arpeggio.Direction.Down));
      chordMap.put              (PTBPositionAttribute.Type.ArpeggioUp             , new com.beaglebuddy.tab.model.attribute.chord.Arpeggio               (Arpeggio.Direction.Up  ));
      chordMap.put              (PTBPositionAttribute.Type.Fermata                , new com.beaglebuddy.tab.model.attribute.chord.Fermata                ());
      chordMap.put              (PTBPositionAttribute.Type.LetRing                , new com.beaglebuddy.tab.model.attribute.chord.LetRing                ());
      chordMap.put              (PTBPositionAttribute.Type.PalmMuting             , new com.beaglebuddy.tab.model.attribute.chord.PalmMuting             ());
      chordMap.put              (PTBPositionAttribute.Type.PickStrokeDown         , new com.beaglebuddy.tab.model.attribute.chord.PickStroke             (PickStroke.Direction.Down));
      chordMap.put              (PTBPositionAttribute.Type.PickStrokeUp           , new com.beaglebuddy.tab.model.attribute.chord.PickStroke             (PickStroke.Direction.Up  ));
      chordMap.put              (PTBPositionAttribute.Type.Staccato               , new com.beaglebuddy.tab.model.attribute.chord.Staccato               ());
      chordMap.put              (PTBPositionAttribute.Type.Tap                    , new com.beaglebuddy.tab.model.attribute.chord.Tap                    ());
      chordMap.put              (PTBPositionAttribute.Type.TremoloBar             , new com.beaglebuddy.tab.model.attribute.chord.TremoloBar             ());
      chordMap.put              (PTBPositionAttribute.Type.TremoloPicking         , new com.beaglebuddy.tab.model.attribute.chord.TremoloPicking         ());
      chordMap.put              (PTBPositionAttribute.Type.TripletFeel1st         , new com.beaglebuddy.tab.model.attribute.chord.TripletFeel1st         ());
      chordMap.put              (PTBPositionAttribute.Type.TripletFeel2nd         , new com.beaglebuddy.tab.model.attribute.chord.TripletFeel2nd         ());
      chordMap.put              (PTBPositionAttribute.Type.Vibrato                , new com.beaglebuddy.tab.model.attribute.chord.Vibrato                (Vibrato.Amount.Normal));
      chordMap.put              (PTBPositionAttribute.Type.VibratoWide            , new com.beaglebuddy.tab.model.attribute.chord.Vibrato                (Vibrato.Amount.Wide  ));
      chordMap.put              (PTBPositionAttribute.Type.VolumeSwell            , new com.beaglebuddy.tab.model.attribute.chord.VolumeSwell            ());

      chordDurationMap.put      (PTBPositionAttribute.Type.Dotted                 , new com.beaglebuddy.tab.model.attribute.duration.Dotted              ());
      chordDurationMap.put      (PTBPositionAttribute.Type.DottedDouble           , new com.beaglebuddy.tab.model.attribute.duration.DottedDouble        ());
      chordDurationMap.put      (PTBPositionAttribute.Type.IrregularGroupingStart , new com.beaglebuddy.tab.model.attribute.duration.IrregularGrouping   (IrregularGrouping.Part.Begin ));
      chordDurationMap.put      (PTBPositionAttribute.Type.IrregularGroupingMiddle, new com.beaglebuddy.tab.model.attribute.duration.IrregularGrouping   (IrregularGrouping.Part.Middle));
      chordDurationMap.put      (PTBPositionAttribute.Type.IrregularGroupingEnd   , new com.beaglebuddy.tab.model.attribute.duration.IrregularGrouping   (IrregularGrouping.Part.End   ));
      chordDurationMap.put      (PTBPositionAttribute.Type.Rest                   , new com.beaglebuddy.tab.model.attribute.duration.Rest                ());
      chordDurationMap.put      (PTBPositionAttribute.Type.RestMultibar           , new com.beaglebuddy.tab.model.attribute.duration.MultibarRest        ());

      clefMap.put               (PTBStaff.Clef.Bass                               , Staff.Clef.Bass                                                        );
      clefMap.put               (PTBStaff.Clef.Treble                             , Staff.Clef.Treble                                                      );

      directionMap.put          (PTBDirection.SymbolType.Coda                     , Direction.Symbol.Coda                                                  );
      directionMap.put          (PTBDirection.SymbolType.DoubleCoda               , Direction.Symbol.DoubleCoda                                            );
      directionMap.put          (PTBDirection.SymbolType.Segno                    , Direction.Symbol.Segno                                                 );
      directionMap.put          (PTBDirection.SymbolType.SegnoSegno               , Direction.Symbol.SegnoSegno                                            );
      directionMap.put          (PTBDirection.SymbolType.Fine                     , Direction.Symbol.Fine                                                  );
      directionMap.put          (PTBDirection.SymbolType.DaCapo                   , Direction.Symbol.DaCapo                                                );
      directionMap.put          (PTBDirection.SymbolType.DalSegno                 , Direction.Symbol.DalSegno                                              );
      directionMap.put          (PTBDirection.SymbolType.DalSegnoSegno            , Direction.Symbol.DalSegnoSegno                                         );
      directionMap.put          (PTBDirection.SymbolType.ToCoda                   , Direction.Symbol.ToCoda                                                );
      directionMap.put          (PTBDirection.SymbolType.ToDoubleCoda             , Direction.Symbol.ToDoubleCoda                                          );
      directionMap.put          (PTBDirection.SymbolType.DaCapoAlCoda             , Direction.Symbol.DaCapoAlCoda                                          );
      directionMap.put          (PTBDirection.SymbolType.DaCapoAlDoubleCoda       , Direction.Symbol.DaCapoAlDoubleCoda                                    );
      directionMap.put          (PTBDirection.SymbolType.DalSegnoAlCoda           , Direction.Symbol.DalSegnoAlCoda                                        );
      directionMap.put          (PTBDirection.SymbolType.DalSegnoAlDoubleCoda     , Direction.Symbol.DalSegnoAlDoubleCoda                                  );
      directionMap.put          (PTBDirection.SymbolType.DalSegnoSegnoAlCoda      , Direction.Symbol.DalSegnoSegnoAlCoda                                   );
      directionMap.put          (PTBDirection.SymbolType.DalSegnoSegnoAlDoubleCoda, Direction.Symbol.DalSegnoSegnoAlDoubleCoda                             );
      directionMap.put          (PTBDirection.SymbolType.DaCapoAlFine             , Direction.Symbol.DaCapoAlFine                                          );
      directionMap.put          (PTBDirection.SymbolType.DalSegnoAlFine           , Direction.Symbol.DalSegnoAlFine                                        );
      directionMap.put          (PTBDirection.SymbolType.DalSegnoSegnoAlFine      , Direction.Symbol.DalSegnoSegnoAlFine                                   );

      keyMidiNoteMap.put        (PTBKey.Key.C                                     , Midi.Note.Middle_C                                                     );
      keyMidiNoteMap.put        (PTBKey.Key.C_Sharp                               , Midi.Note.CSHARP4                                                      );
      keyMidiNoteMap.put        (PTBKey.Key.D                                     , Midi.Note.D4                                                           );
      keyMidiNoteMap.put        (PTBKey.Key.E_Flat                                , Midi.Note.EFLAT4                                                       );
      keyMidiNoteMap.put        (PTBKey.Key.E                                     , Midi.Note.E4                                                           );
      keyMidiNoteMap.put        (PTBKey.Key.F                                     , Midi.Note.F4                                                           );
      keyMidiNoteMap.put        (PTBKey.Key.F_Sharp                               , Midi.Note.FSHARP4                                                      );
      keyMidiNoteMap.put        (PTBKey.Key.G                                     , Midi.Note.G4                                                           );
      keyMidiNoteMap.put        (PTBKey.Key.A_Flat                                , Midi.Note.AFLAT4                                                       );
      keyMidiNoteMap.put        (PTBKey.Key.A                                     , Midi.Note.A4                                                           );
      keyMidiNoteMap.put        (PTBKey.Key.B_Flat                                , Midi.Note.BFLAT4                                                       );
      keyMidiNoteMap.put        (PTBKey.Key.B                                     , Midi.Note.B4                                                           );

      midDurationiMap.put       (PTBMidi.Duration.NOTE_NONE                       , Midi.Duration.NOTE_NONE                                                );
      midDurationiMap.put       (PTBMidi.Duration.NOTE_64TH                       , Midi.Duration.NOTE_64TH                                                );
      midDurationiMap.put       (PTBMidi.Duration.NOTE_32ND                       , Midi.Duration.NOTE_32ND                                                );
      midDurationiMap.put       (PTBMidi.Duration.NOTE_16TH                       , Midi.Duration.NOTE_16TH                                                );
      midDurationiMap.put       (PTBMidi.Duration.NOTE_8TH                        , Midi.Duration.NOTE_8TH                                                 );
      midDurationiMap.put       (PTBMidi.Duration.NOTE_QUARTER                    , Midi.Duration.NOTE_QUARTER                                             );
      midDurationiMap.put       (PTBMidi.Duration.NOTE_HALF                       , Midi.Duration.NOTE_HALF                                                );
      midDurationiMap.put       (PTBMidi.Duration.NOTE_WHOLE                      , Midi.Duration.NOTE_WHOLE                                               );

      noteMap.put               (PTBNoteAttribute.Type.ArtificialHarmonic         , new com.beaglebuddy.tab.model.attribute.note.ArtificialHarmonic      ());
      noteMap.put               (PTBNoteAttribute.Type.Bend                       , new com.beaglebuddy.tab.model.attribute.note.Bend                    ());
      noteMap.put               (PTBNoteAttribute.Type.GhostNote                  , new com.beaglebuddy.tab.model.attribute.note.GhostNote               ());
      noteMap.put               (PTBNoteAttribute.Type.HammerOn                   , new com.beaglebuddy.tab.model.attribute.note.HammerOn                ());
      noteMap.put               (PTBNoteAttribute.Type.HammerPullFromToNowhere    , new com.beaglebuddy.tab.model.attribute.note.HammerPullFromToNowhere ());
      noteMap.put               (PTBNoteAttribute.Type.Muted                      , new com.beaglebuddy.tab.model.attribute.note.Muted                   ());
      noteMap.put               (PTBNoteAttribute.Type.NaturalHarmonic            , new com.beaglebuddy.tab.model.attribute.note.NaturalHarmonic         ());
      noteMap.put               (PTBNoteAttribute.Type.Octave                     , new com.beaglebuddy.tab.model.attribute.note.Octave                  ());
      noteMap.put               (PTBNoteAttribute.Type.PullOff                    , new com.beaglebuddy.tab.model.attribute.note.PullOff                 ());
      noteMap.put               (PTBNoteAttribute.Type.SlideInto                  , new com.beaglebuddy.tab.model.attribute.note.SlideInto               ());
      noteMap.put               (PTBNoteAttribute.Type.SlideOutOf                 , new com.beaglebuddy.tab.model.attribute.note.SlideOutOf              ());
      noteMap.put               (PTBNoteAttribute.Type.TappedHarmonic             , new com.beaglebuddy.tab.model.attribute.note.TappedHarmonic          ());
      noteMap.put               (PTBNoteAttribute.Type.Tied                       , new com.beaglebuddy.tab.model.attribute.note.Tie                     ());
      noteMap.put               (PTBNoteAttribute.Type.TieWrap                    , new com.beaglebuddy.tab.model.attribute.note.TieWrap                 ());
      noteMap.put               (PTBNoteAttribute.Type.Trill                      , new com.beaglebuddy.tab.model.attribute.note.Trill                   ());

      octaveMap.put             (PTBOctave.OctaveType.Octave15ma                  , Octave.OctaveType.Octave15ma                                           );
      octaveMap.put             (PTBOctave.OctaveType.Octave15mb                  , Octave.OctaveType.Octave15mb                                           );
      octaveMap.put             (PTBOctave.OctaveType.Octave8va                   , Octave.OctaveType.Octave8va                                            );
      octaveMap.put             (PTBOctave.OctaveType.Octave8vb                   , Octave.OctaveType.Octave8vb                                            );

      rhythmMap.put             (PTBRhythmSlashAttribute.Type.AccentMarcato       , new com.beaglebuddy.tab.model.attribute.rhythm.AccentMarcato         ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.AccentSforzando     , new com.beaglebuddy.tab.model.attribute.rhythm.AccentSforzando       ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.ArpeggioDown        , new com.beaglebuddy.tab.model.attribute.rhythm.ArpeggioDown          ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.ArpeggioUp          , new com.beaglebuddy.tab.model.attribute.rhythm.ArpeggioUp            ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.Muted               , new com.beaglebuddy.tab.model.attribute.rhythm.Muted                 ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.PickStrokeDown      , new com.beaglebuddy.tab.model.attribute.rhythm.PickStrokeDown        ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.PickStrokeUp        , new com.beaglebuddy.tab.model.attribute.rhythm.PickStrokeUp          ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.SingleNote          , new com.beaglebuddy.tab.model.attribute.rhythm.SingleNote            ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.SlideIntoFromAbove  , new com.beaglebuddy.tab.model.attribute.rhythm.SlideIntoFromAbove    ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.SlideIntoFromBelow  , new com.beaglebuddy.tab.model.attribute.rhythm.SlideIntoFromBelow    ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.SlideOutOfDownwards , new com.beaglebuddy.tab.model.attribute.rhythm.SlideOutOfDownwards   ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.SlideOutOfUpwards   , new com.beaglebuddy.tab.model.attribute.rhythm.SlideOutOfUpwards     ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.Staccato            , new com.beaglebuddy.tab.model.attribute.rhythm.Staccato              ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.Tied                , new com.beaglebuddy.tab.model.attribute.rhythm.Tied                  ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.TripletFeel1st      , new com.beaglebuddy.tab.model.attribute.rhythm.TripletFeel1st        ());
      rhythmMap.put             (PTBRhythmSlashAttribute.Type.TripletFeel2nd      , new com.beaglebuddy.tab.model.attribute.rhythm.TripletFeel2nd        ());

      rhythmDurationMap.put     (PTBRhythmSlashAttribute.Type.Dotted              , new com.beaglebuddy.tab.model.attribute.duration.Dotted              ());
      rhythmDurationMap.put     (PTBRhythmSlashAttribute.Type.DottedDouble        , new com.beaglebuddy.tab.model.attribute.duration.DottedDouble        ());
      rhythmDurationMap.put     (PTBRhythmSlashAttribute.Type.Rest                , new com.beaglebuddy.tab.model.attribute.duration.Rest                ());

      volumeMap.put(PTBDynamic.Volume.Off                                         , Volume.Level.Off                                                       );
      volumeMap.put(PTBDynamic.Volume.Ppp                                         , Volume.Level.Ppp                                                       );
      volumeMap.put(PTBDynamic.Volume.Pp                                          , Volume.Level.Pp                                                        );
      volumeMap.put(PTBDynamic.Volume.P                                           , Volume.Level.P                                                         );
      volumeMap.put(PTBDynamic.Volume.Mp                                          , Volume.Level.Mp                                                        );
      volumeMap.put(PTBDynamic.Volume.Mf                                          , Volume.Level.Mf                                                        );
      volumeMap.put(PTBDynamic.Volume.F                                           , Volume.Level.F                                                         );
      volumeMap.put(PTBDynamic.Volume.Ff                                          , Volume.Level.Ff                                                        );
      volumeMap.put(PTBDynamic.Volume.Fff                                         , Volume.Level.Fff                                                       );
      volumeMap.put(PTBDynamic.Volume.NotSet                                      , Volume.Level.Off                                                       );
   }





   /**
    * default constructor.
    * don't allow instances of this class to be created.
    */
   private Converter_PTB_1_7_0_to_BBT_1_0()
   {
      // no code necessary
   }

   /**
    * converts a 1.70 version power tab file (.ptb) to a beaglebuddy 1.0 song.
    * <br/><br/>
    * @param filename   the name of the power tab 1.70 file that is to be converted.
    * <br/><br/>
    * @return the converted power tab file as a beaglebuddy 1.0 song.
    * <br/><br/>
    * @throws FileReadException  if the power tab file can not be found, is not a valid 1.70 power tab file, or can not be read in and parsed.
    */
   public static Song convert(String filename) throws FileReadException
   {
      PTBFile                 ptbFile       = loadPtbFile(filename);
      Information             information   = convertInformation(ptbFile.getHeader());
      ArrayList<ChordDiagram> chordDiagrams = convertChordDiagrams(ptbFile.getGuitarScore().getChordDiagrams(), ptbFile.getBassScore().getChordDiagrams());
      Score                   score         = convertScores(ptbFile.getGuitarScore(), ptbFile.getBassScore());
      Song                    song          = null;

      if (score == null)
      {
         song = new Song();
         song.setInformation  (information);
         song.setChordDiagrams(chordDiagrams);
         score = song.getScore();
      }
      else
      {
         song = new Song(information, chordDiagrams, score);
      }

      // power tab only supported standard tuning chord diagrams, and thus did not associate a chord diagram with a particular instrument\tuning.
      // it was decided to simply associate all chord diagrams with the first valid instrument\tuning (guitar, keyboards, other (treble), or vocals).
      ArrayList<Instrument> instruments   = score.getInstruments();
      Instrument            instr         = null;

      // get the 1st instrument which can have chord diagrams associated with it.
      for(Instrument instrument : instruments)
      {
         if (instrument.canHaveAssociatedChordDiagrams())
         {
            instr = instrument;
            break;
         }
      }

      // if there were no instruments found that could have chord diagrams associated with it, then discard the chord diagrams
      if (instr == null)
      {
         song.setChordDiagrams(null);
      }
      else
      {
         for(ChordDiagram chordDiagram : chordDiagrams)
            chordDiagram.setInstrumentId(instr.getId());
      }

      // power tab allowed multiple instruments to be assigned to the same staff irregardless of tuning or capo setting.
      // beaglebuddy does not allow this, as it makes no sense.
      // thus, for staffs with multiple instruments assigned, make sure they all have the same number of strings, tuning, and capo setting.
      // if they don't, keep the first insutrument and remove any that don't agree with this first instrument
      ActiveInstruments       activeInstruments          = new ActiveInstruments(instruments); // data structure used to keep track of which instruments are assigned to which staffs.
      ArrayList<Section>      sections                   = song.getScore().getSections();
      ArrayList<Barline>      barlines                   = null;
      boolean[]               staffInstrumentAssignments = null;                               // a boolean for each instrument on a staff indicating whether it is active or not
      ArrayList<Instrument>   assignedInstruments        = null;                               // instruments assigned to the current staff

      int s=0;
      for(Section section : sections)
      {
         int b=0;
         barlines = section.getBarlines();
         for(Barline barline : barlines)
         {
            activeInstruments.set(barline.getInstrumentIns());

            // check that all the instruments assigned to a specific staff are compatible
            for(byte staff=0; staff<activeInstruments.getNumStaffs(); ++staff)
            {
               staffInstrumentAssignments = activeInstruments.getInstruments(staff);
               assignedInstruments        = new ArrayList<Instrument>(0);
               for(int i=0; i<staffInstrumentAssignments.length; ++i)
               {
                  if (staffInstrumentAssignments[i])
                  {
                     assignedInstruments.add(instruments.get(i));
                     assert((byte)i == instruments.get(i).getId());
                  }
               }
               if (assignedInstruments.size() > 1)
               {
                  Instrument firstInstrument = assignedInstruments.get(0);
                  Instrument instrument      = null;

                  for(int i=1; i<assignedInstruments.size(); ++i)
                  {
                     instrument = assignedInstruments.get(i);
                     // if any of the subsequent instruments are not compatible with the first instrument assigned to the staff, then remove them
                     if (instrument.getNumStrings() != firstInstrument.getNumStrings() ||
                         instrument.getCapo()       != firstInstrument.getCapo()       ||
                        !instrument.getTuning().equals(firstInstrument.getTuning()))
                     {
                        // remove the conflicting instrument from the instrument in
                        ArrayList<InstrumentIn> instrumentIns = barline.getInstrumentIns();
                        for(InstrumentIn instrumentIn : instrumentIns)
                        {
                           // find the instrument in for the staff
                           if (instrumentIn.getStaff() == staff)
                           {
                              boolean[] staffInstruments = instrumentIn.getStaffInstruments();
                              staffInstruments[instrument.getId()] = false;
                              assert(instrumentIn.getNumStaffActiveInstruments() != 0);
                              activeInstruments.set(instrumentIns);
                           }
                        }
                        System.err.println("instrument in compatability error: section: " + s + ", staff: " + staff + ", bar: " + b + ", instruments: " + firstInstrument.getId() + ", " + instrument.getId());
                     }
                  }
               }
            }
            b++;
         }
         s++;
      }
      return song;
   }

   /**
    * convert power tab 1.70 active instruments to beaglebuddy 1.0 active instruments.
    * power tab only allows a song to have a total of 6 guitars defined.
    * <br/><br/>
    * @param ptbActiveInstruments    an array of 8 booleans, with each boolean indicating whether the corresponding guitar is being activated.
    * @param numGuitars              when converting guitar ins from the ptb bass score, we need to offset the guitar in ids by the number of guitars.
    * <br/><br/>
    * @return an array of 16 booleans with the ptb active guitars in the first eight, and the second eight all set to false.
    */
   private static boolean[] convertActiveInstruments(boolean[] ptbActiveInstruments, int numGuitars)
   {
      boolean[] active = new boolean[InstrumentIn.MAX_NUM_INSTRUMENTS];
      for(int i=0; i<ptbActiveInstruments.length; ++i)
         active[i+numGuitars] = ptbActiveInstruments[i];
      return active;
   }

   /**
    * convert a power tab 1.70 floating text alignment to a beaglebuddy 1.0 alignment.
    */
   private static FloatingText.Alignment convertAlignment(boolean alignedLeft, boolean alignedCenter, boolean alignedRight)
   {
      return (alignedLeft ? FloatingText.Alignment.Left : (alignedCenter ? FloatingText.Alignment.Center : FloatingText.Alignment.Right));
   }

   /**
    * convert a power tab 1.70 alternate ending to a beaglebuddy 1.0 alternate ending.
    */
   private static AlternateEnding convertAlternateEnding(PTBAlternateEnding ptbAlternateEnding)
   {
      return new AlternateEnding(ptbAlternateEnding.getNumbers());
   }

   /**
    * convert a power tab 1.70 bar line to a beaglebuddy 1.0 bar line.
    */
   private static Barline convertBarline(PTBBarline ptbBarline)
   {
      return new Barline(ptbBarline.getPosition(), barlineMap.get(ptbBarline.getType()), ptbBarline.getNumRepeats(), convertKeySignature(ptbBarline.getKeySignature()),
                         convertTimeSignature(ptbBarline.getTimeSignature()), convertRehearsalSign(ptbBarline.getRehearsalSign()), null, null, new Direction[0], new Volume[0], new InstrumentIn[0]);
   }

   /**
    * convert an array of power tab 1.70 bar lines to an array of beaglebuddy 1.0 bar lines.
    */
   private static Barline[] convertBarlines(PTBBarline ptbStartBarline, PTBBarline[] ptbBarlines, PTBBarline ptbEndBarline)
   {
      Barline[] barlines = new Barline[ptbBarlines.length + 2];
      barlines[0] = convertBarline(ptbStartBarline);
      for(int i=0; i<ptbBarlines.length; ++i)
         barlines[i+1] = convertBarline(ptbBarlines[i]);
      barlines[barlines.length-1] = convertBarline(ptbEndBarline);

      return barlines;
   }

   /**
    * convert a power tab 1.70 beam to a beaglebuddy 1.0 beam.
    */
   private static Beam convertBeam(PTBBeam ptbBeam)
   {
      return new Beam(convertBeamType(ptbBeam.getType()), midDurationiMap.get(ptbBeam.getPreviousBeamDuration()));
   }

   /**
    * convert a power tab 1.70 beam type to a beaglebuddy 1.0 beam type.
    */
   private static byte convertBeamType(short ptbBeamType)
   {
      byte beamType = (byte)0x00;

      if ((ptbBeamType & PTBBeam.Type.Start.mask()) != 0)
         beamType |= Beam.Type.Start.mask();
      if ((ptbBeamType & PTBBeam.Type.FractionalLeft.mask()) != 0)
         beamType |= Beam.Type.FractionalLeft.mask();
      if ((ptbBeamType & PTBBeam.Type.FractionalRight.mask()) != 0)
         beamType |= Beam.Type.FractionalRight.mask();
      if ((ptbBeamType & PTBBeam.Type.End.mask()) != 0)
         beamType |= Beam.Type.End.mask();

      return beamType;
   }

   /**
    * convert power tab 1.70 position attributes to beaglebuddy 1.0 chord attributes.
    */
   private static ChordAttribute[] convertChordAttributes(PTBPositionAttribute[] ptbPositionAttributes)
   {
      ArrayList<ChordAttribute> chordAttributes = new ArrayList<ChordAttribute>();

      for(PTBPositionAttribute ptbPositionAttribute : ptbPositionAttributes)
      {
         ChordAttribute chordAttribute = chordMap.get(ptbPositionAttribute.getType());
         // the chord attribute will be null if the ptb position attribute is mapped to a beaglebuddy duration attribute
         if (chordAttribute != null)
         {
            chordAttribute = chordAttribute.clone();
            if (chordAttribute instanceof TremoloBar)
            {
                  TremoloBar    tremoloBar = (TremoloBar   )   chordAttribute;
               PTBTremoloBar ptbTremoloBar = (PTBTremoloBar)ptbPositionAttribute;
               tremoloBar.setTremoloBarType(TremoloBar.getTremoloBarType(ptbTremoloBar.getTremoloBarType().ordinal()));
               tremoloBar.setNotesDuration (ptbTremoloBar.getDuration());
               tremoloBar.setPitch         (ptbTremoloBar.getPitch   ());
            }
            else if (chordAttribute instanceof VolumeSwell)
            {
                  VolumeSwell    volumeSwell = (VolumeSwell   )   chordAttribute;
               PTBVolumeSwell ptbVolumeSwell = (PTBVolumeSwell)ptbPositionAttribute;
               volumeSwell.setStartVolume  (volumeMap.get(ptbVolumeSwell.getStartVolume()));
               volumeSwell.setEndVolume    (volumeMap.get(ptbVolumeSwell.getEndVolume  ()));
               volumeSwell.setNotesDuration(ptbVolumeSwell.getDuration());
            }
            chordAttributes.add(chordAttribute);
         }
      }

      // convert to array
      ChordAttribute[] attributes = new ChordAttribute[chordAttributes.size()];
      attributes = chordAttributes.toArray(attributes);

      return attributes;
   }

   /**
    * convert power tab 1.70 position attributes to beaglebuddy 1.0 duration attributes.
    */
   private static DurationAttribute[] convertChordDurationAttributes(PTBPositionAttribute[] ptbPositionAttributes, PTBBeam ptbBeam)
   {
      ArrayList<DurationAttribute> durationAttributes = new ArrayList<DurationAttribute>(0);

      for(PTBPositionAttribute ptbPositionAttribute : ptbPositionAttributes)
      {
         DurationAttribute durationAttribute = chordDurationMap.get(ptbPositionAttribute.getType());
         // the chord attribute will be null if the ptb position attribute is mapped to a beaglebuddy chord attribute and not to a duration attribute
         if (durationAttribute != null)
         {
            durationAttribute = durationAttribute.clone();
            if (durationAttribute instanceof MultibarRest)
            {
                  MultibarRest    multibarRest = (   MultibarRest)      durationAttribute;
               PTBRestMultibar ptbRestMultibar = (PTBRestMultibar)ptbPositionAttribute;
               multibarRest.setNumMeasures(ptbRestMultibar.getNumMeasures());
            }
            else if (durationAttribute instanceof IrregularGrouping)
            {
               IrregularGrouping irregularGrouping = (IrregularGrouping)durationAttribute;
               irregularGrouping.setNumNotesPlayed    (ptbBeam.getIrregularGroupingNotesPlayed    ());
               irregularGrouping.setNumNotesPlayedOver(ptbBeam.getIrregularGroupingNotesPlayedOver());
            }
            durationAttributes.add(durationAttribute);
         }
      }

      // note: this should never, ever happen, but I've found files where it occurs (ex: ac dc\back in black.ptb (first measure in bass score)
      if (durationAttributes.size() >= 2 && hasDurationAttribute(durationAttributes, DurationAttribute.Type.Rest) && hasDurationAttribute(durationAttributes, DurationAttribute.Type.MultibarRest))
      {
         // remove the extra "rest"
         for(DurationAttribute attribute : durationAttributes)
            if (attribute.getType() == DurationAttribute.Type.Rest)
               durationAttributes.remove(attribute);
      }

      // convert to an array
      DurationAttribute[] attributes = new DurationAttribute[durationAttributes.size()];
      attributes = durationAttributes.toArray(attributes);

      return attributes;
   }

   /**
    * convert a power tab 1.70 chord diagram to a beaglebuddy 1.0 chord diagram.
    */
   private static ChordDiagram convertChordDiagram(PTBChordDiagram ptbChordDiagram)
   {
      String ptbName = ptbChordDiagram.getName().getName();

      // convert the power tab chord name to a beaglebuddy chord name
      ptbName = ptbName.replaceAll("_Sharp", "#");
      ptbName = ptbName.replaceAll("_Flat" , "b");

      // what power tab calls the top fret in a chord diagram is actually off by 1 fret, except for the nut.
      Instrument.Fret topFret = convertFret(ptbChordDiagram.getTopFret());
      if (topFret != Instrument.Fret.Open)
         topFret = Instrument.getFret(topFret.ordinal() - 1);

      // power tab chord diagrams only supported standard tuning, so they are not associated with a particular instrument or tuning.
      // since the instruments have not yet been converted yet, we set the instrument id to -1 for now, and then, once the instruments
      // have been converted, go back and associate all the chord diagrams with the 1st valid instrument (guitar, keyboards, other (treble), or vocals).
      ChordDiagram chordDiagram = null;
      Fretting[]   fretting     = convertFretting(ptbChordDiagram.getFretNumberOffsets());
      if (fretting.length == 6 || fretting.length == 7)
      {
         chordDiagram = new ChordDiagram((byte)-1, ptbName, topFret, fretting);
         // power tab chord diagrams don't specify the fingering, so we look up the diagram in the beaglebuddy dictionary and if we have it, then we use
         // that one.  Otherwise, we use the converted power tab chord diagram which does not have the fingering.
         ArrayList<ChordDiagram> chordDiagrams = ChordDictionary.getChordDiagrams(chordDiagram.getName());
         for(ChordDiagram cd : chordDiagrams)
         {
            if (chordDiagram.equals(cd))
            {
               chordDiagram = new ChordDiagram(cd);
               break;
            }
         }
      }
      return chordDiagram;
   }

   /**
    * convert power tab 1.70 chord diagrams to beaglebuddy 1.0 chord diagrams.
    * beaglebuddy only supports 6 and 7 string guitar chord diagrams.  It does not support bass chord diagrams.
    */
   private static ArrayList<ChordDiagram> convertChordDiagrams(PTBChordDiagram[] ptbGuitarChordDiagrams, PTBChordDiagram[] ptbBassChordDiagrams)
   {
      ArrayList<ChordDiagram> chordDiagrams = new ArrayList<ChordDiagram>();
      ChordDiagram            chordDiagram  = null;

      for(int i=0; i<ptbGuitarChordDiagrams.length; ++i)
      {
         chordDiagram = convertChordDiagram(ptbGuitarChordDiagrams[i]);
         if (chordDiagram != null && chordDiagram.getNumStrings() >= 6)
            chordDiagrams.add(new ChordDiagram(chordDiagram));
      }
      for(int i=0; i<ptbBassChordDiagrams.length; ++i)
      {
         chordDiagram = convertChordDiagram(ptbBassChordDiagrams[i]);
         if (chordDiagram != null && chordDiagram.getNumStrings() >= 6)
            chordDiagrams.add(new ChordDiagram(chordDiagram));
      }
      return chordDiagrams;
   }

   /**
    * convert an array of power tab 1.70 directions to an array of beaglebuddy 1.0 directions.
    * power tab used a single direction which would contain up to three musical direction symbols.
    * beaglebuddy uses a single direction for each direction symbol.
    * Thus, converting a single power tab direction may result in up to three beaglebuddy directions being created.
    */
   private static Direction[] convertDirection(PTBDirection ptbDirection)
   {
      Direction[]                    directions = null;
      PTBDirection.InnerSymbolType[] symbols    = ptbDirection.getInnerSymbolTypes();

      directions = new Direction[symbols.length];
      for(int i=0; i<directions.length; ++i)
         directions[i] = new Direction(directionMap.get(symbols[i].symbolType()), activationDirectionMap.get(symbols[i].activeSymbol()), symbols[i].numRepeats());

      return directions;
   }

   /**
    * convert an array of power tab 1.70 direction symbol types to an array of beaglebuddy 1.0 direction symbols.
    */
   private static Direction.Symbol[] convertDirectionSymbolTypes(PTBDirection.SymbolType[] ptbDirectionSymbols)
   {
      Direction.Symbol[] symbols = new Direction.Symbol[ptbDirectionSymbols.length];

      for(int i=0; i<symbols.length; ++i)
         symbols[i] = directionMap.get(ptbDirectionSymbols[i]);

      return symbols;
   }

   /**
    * convert a power tab 1.70 dynamic to a beaglebuddy 1.0 dynamic.
    * <p>
    * power tab dynamic markers contained one volume for a staff and one volume for the rhythm staff.
    * this necessitated the dynamic enum to have a "not set" in the list of valid volumes.
    * </p>
    * <p>
    * the beaglebuddy volume class only has one volume, namely for one staff, and thus does not have a "not set" volume.
    * </p>
    * todo: right now, the rhythm staff volumes are ignored.
    *       this needs to be fixed once we figure out how rhythm staffs are represented in the model:
    *       as their own class or as a class from the base staff class.
    */
   private static Volume convertDynamicMarker(PTBDynamic ptbDynamic)
   {
//    return new Volume(ptbDynamic.getStaff(), Dynamic.getVolume(ptbDynamic.getStaffVolume().ordinal()), Dynamic.getVolume(ptbDynamic.getRhythmSlashVolume().ordinal()));
      return ptbDynamic.getStaffVolume() == PTBDynamic.Volume.NotSet ? null : new Volume(ptbDynamic.getStaff(), volumeMap.get(ptbDynamic.getStaffVolume()));
   }

   /**
    * convert the power tab 1.70 guitar and bass scores to a beaglebuddy 1.0 score.
    */
   private static FloatingText convertFloatingText(PTBFloatingText ptbFloatingText)
   {
      return new FloatingText(ptbFloatingText.getText(), ptbFloatingText.getBoundingRectangle(),
                              convertAlignment(ptbFloatingText.isAlignedLeft(), ptbFloatingText.isAlignedCenter(), ptbFloatingText.isAlignedRight()),
                              ptbFloatingText.hasBorder(), convertFontSetting(ptbFloatingText.getFontSettings()));
   }

   /**
    * convert the power tab 1.70 guitar and bass floating texts to beaglebuddy 1.0 floating texts.
    */
   private static FloatingText[] convertFloatingTexts(PTBFloatingText[] ptbGuitarFloatingTexts, PTBFloatingText[]  ptbBassFloatingTexts)
   {
      FloatingText[] floatingTexts = new FloatingText[ptbGuitarFloatingTexts.length + ptbBassFloatingTexts.length];

      for(int i=0; i<ptbGuitarFloatingTexts.length; ++i)
         floatingTexts[i] = convertFloatingText(ptbGuitarFloatingTexts[i]);
      for(int i=0; i<ptbBassFloatingTexts.length; ++i)
         floatingTexts[ptbGuitarFloatingTexts.length + i] = convertFloatingText(ptbBassFloatingTexts[i]);

      return floatingTexts;
   }

   /**
    * convert a power tab 1.70 font setting to a beaglebuddy 1.0 font setting.
    */
   private static FontSetting convertFontSetting(PTBFontSetting ptbFontSetting)
   {
      return new FontSetting(ptbFontSetting.getFaceName(), ptbFontSetting.getPointSize(), ptbFontSetting.getWeight().ordinal() > 9, ptbFontSetting.isItalic(), ptbFontSetting.getColor());
   }

   /**
    * convert a power tab 1.70 fret to a beaglebuddy 1.0 fret.
    */
   private static Instrument.Fret convertFret(int fret)
   {
      Instrument.Fret fr = Instrument.Fret.Not_Used;

      if (fret == 0xff)
         fr = Instrument.Fret.Not_Used;
      else if (fret == 0xfe)
         fr = Instrument.Fret.Not_Used;
      else if (fret > Instrument.Fret.Fret_24.ordinal())
         fr = Instrument.Fret.Fret_24;   // there is a bug in power tab 1.70 that allows frets up to 29. also, in previous versions of power tab, frets up to 30 were allowed. convert these "illegal" frets to the 24th fret.
      else
      {
         for (Instrument.Fret f : Instrument.Fret.values())
            if (fret == f.ordinal())
            {
               fr = f;
               break;
            }
      }
      return fr;
   }

   /**
    * convert an array of power tab 1.70 fret numbers to an array of beaglebuddy 1.0 frets.
    */
   private static Fretting[] convertFretting(int[] ptbFretOffsets)
   {
      Fretting[] fretting = new Fretting[ptbFretOffsets.length];

      for(int i=0; i<fretting.length; ++i)
         fretting[i] = new Fretting(convertFret(ptbFretOffsets[i]), null);

      return fretting;
   }

   /**
    * convert power tab 1.70 guitars to beaglebuddy 1.0 instruments.
    */
   private static Instrument[] convertGuitars(PTBGuitar[] ptbGuitars, PTBGuitar[] ptbBasses)
   {
      // add a set of drums for development\debugging purposes
      // todo: remove this when finished debugging                                   + 1 for a set of drums - remove when done
      Instrument[] instruments = new Instrument[ptbGuitars.length + ptbBasses.length + 1];

      for(int i=0; i<ptbGuitars.length; ++i)
      {
         Midi.Sound preset = Midi.getSound(ptbGuitars[i].getPreset().ordinal());
         Tuning     tuning = convertTuning(ptbGuitars[i].getTuning());

         // determine the type of instrument
         if (Guitar.isValidPreset(preset) && tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7)
            instruments[i] = new Guitar     (ptbGuitars[i].getNumber(), ptbGuitars[i].getDescription(), preset, volumeMap.get(ptbGuitars[i].getInitialVolume()),
                                             ptbGuitars[i].getPan(), ptbGuitars[i].getReverb(), ptbGuitars[i].getChorus(),
                                            (ptbGuitars[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbGuitars[i].getCapo())), tuning);
         else if (Vocals.isValidPreset(preset) && tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7 && ptbGuitars[i].getDescription().indexOf("vocals") != -1)
            instruments[i] = new Vocals     (ptbGuitars[i].getNumber(), ptbGuitars[i].getDescription(), preset, volumeMap.get(ptbGuitars[i].getInitialVolume()),
                                             ptbGuitars[i].getPan(), ptbGuitars[i].getReverb(), ptbGuitars[i].getChorus(),
                                            (ptbGuitars[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbGuitars[i].getCapo())), tuning);
         else if (Keyboard.isValidPreset(preset) && tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7 &&
                                            (ptbGuitars[i].getDescription().toLowerCase().indexOf("piano") != -1 || ptbGuitars[i].getDescription().toLowerCase().indexOf("keyboard") != -1 ||
                                             ptbGuitars[i].getDescription().toLowerCase().indexOf("keybd") != -1 || ptbGuitars[i].getDescription().toLowerCase().indexOf("synth"   ) != -1))
            instruments[i] = new Keyboard   (ptbGuitars[i].getNumber(), ptbGuitars[i].getDescription(), preset, volumeMap.get(ptbGuitars[i].getInitialVolume()),
                                             ptbGuitars[i].getPan(), ptbGuitars[i].getReverb(), ptbGuitars[i].getChorus(),
                                            (ptbGuitars[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbGuitars[i].getCapo())), tuning);
         else if (OtherTreble.isValidPreset(preset) && tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7)
            instruments[i] = new OtherTreble(ptbGuitars[i].getNumber(), ptbGuitars[i].getDescription(), preset, volumeMap.get(ptbGuitars[i].getInitialVolume()),
                                             ptbGuitars[i].getPan(), ptbGuitars[i].getReverb(), ptbGuitars[i].getChorus(),
                                            (ptbGuitars[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbGuitars[i].getCapo())), tuning);
         else if (tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7)  // otherwise, convert the instrument to an "other" instrument
            instruments[i] = new OtherTreble(ptbGuitars[i].getNumber(), ptbGuitars[i].getDescription(), Midi.Sound.Agogo, volumeMap.get(ptbGuitars[i].getInitialVolume()),
                                             ptbGuitars[i].getPan(), ptbGuitars[i].getReverb(), ptbGuitars[i].getChorus(),
                                            (ptbGuitars[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbGuitars[i].getCapo())), tuning);
         else if (BassGuitar.isValidPreset(preset) && tuning.getNotes().length >= 4 && tuning.getNotes().length <= 6)
            instruments[i] = new BassGuitar (ptbGuitars[i].getNumber(), ptbGuitars[i].getDescription(), preset, volumeMap.get(ptbGuitars[i].getInitialVolume()),
                                             ptbGuitars[i].getPan(), ptbGuitars[i].getReverb(), ptbGuitars[i].getChorus(),
                                            (ptbGuitars[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbGuitars[i].getCapo())), tuning);
         else if (OtherBass.isValidPreset(preset) && tuning.getNotes().length >= 4 && tuning.getNotes().length <= 5)
            instruments[i] = new OtherBass  (ptbGuitars[i].getNumber(), ptbGuitars[i].getDescription(), preset, volumeMap.get(ptbGuitars[i].getInitialVolume()),
                                             ptbGuitars[i].getPan(), ptbGuitars[i].getReverb(), ptbGuitars[i].getChorus(),
                                            (ptbGuitars[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbGuitars[i].getCapo())), tuning);
         else
            throw new RuntimeException("Unsupported instrument type: " + ptbGuitars[i].getDescription() + ", " + tuning.getNumStrings() + " strings, midi sound: " + preset);

         // convert the names of standard, predefined tunings to the beaglebuddy names
         Tuning t = TuningDictionary.getTuning(instruments[i].getType(), tuning.getNotes());
         if (t != null)
         {
            instruments[i].setTuning(t);
         }
         else
         {
            // change the name of the tuning to custom
            instruments[i].getTuning().setName("custom");

            // todo: for debugging purposes only - remove when done
            if (t == null)
            {
//             System.out.println(instruments[i].getTuning().getName() + " tuning with " + instruments[i].getTuning().getNumStrings() + " strings for instrument (" + instruments[i].getType() + ") " + instruments[i].getName() + " not found in tuning dictionary.");
//             System.out.println(instruments[i].getTuning());
            }
         }
      }
      byte numGuitars = (byte)ptbGuitars.length;
      for(int i=0; i<ptbBasses.length; ++i)
      {
         Midi.Sound preset = Midi.getSound(ptbBasses[i].getPreset().ordinal());
         Tuning     tuning = convertTuning(ptbBasses[i].getTuning());

         // determine the type of instrument
         if (Guitar.isValidPreset(preset) && tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7)
            instruments[numGuitars + i] = new Guitar     ((byte)(ptbBasses[i].getNumber() + numGuitars), ptbBasses[i].getDescription(), preset, volumeMap.get(ptbBasses[i].getInitialVolume()),
                                                          ptbBasses[i].getPan(), ptbBasses[i].getReverb(), ptbBasses[i].getChorus(),
                                                         (ptbBasses[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbBasses[i].getCapo())), tuning);
         else if (Vocals.isValidPreset(preset) && tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7 && ptbBasses[i].getDescription().indexOf("vocals") != -1)
            instruments[numGuitars + i] = new Vocals     ((byte)(ptbBasses[i].getNumber() + numGuitars), ptbBasses[i].getDescription(), preset, volumeMap.get(ptbBasses[i].getInitialVolume()),
                                                          ptbBasses[i].getPan(), ptbBasses[i].getReverb(), ptbBasses[i].getChorus(),
                                                         (ptbBasses[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbBasses[i].getCapo())), tuning);
         else if (Keyboard.isValidPreset(preset) && tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7 &&
                                                         (ptbBasses[i].getDescription().toLowerCase().indexOf("piano") != -1 || ptbBasses[i].getDescription().toLowerCase().indexOf("keyboard") != -1 ||
                                                          ptbBasses[i].getDescription().toLowerCase().indexOf("keybd") != -1 || ptbBasses[i].getDescription().toLowerCase().indexOf("synth"   ) != -1))
            instruments[numGuitars + i] = new Keyboard   ((byte)(ptbBasses[i].getNumber() + numGuitars), ptbBasses[i].getDescription(), preset, volumeMap.get(ptbBasses[i].getInitialVolume()),
                                                          ptbBasses[i].getPan(), ptbBasses[i].getReverb(), ptbBasses[i].getChorus(),
                                                         (ptbBasses[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbBasses[i].getCapo())), tuning);
         else if (OtherTreble.isValidPreset(preset) && tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7)
            instruments[numGuitars + i] = new OtherTreble((byte)(ptbBasses[i].getNumber() + numGuitars), ptbBasses[i].getDescription(), preset, volumeMap.get(ptbBasses[i].getInitialVolume()),
                                                          ptbBasses[i].getPan(), ptbBasses[i].getReverb(), ptbBasses[i].getChorus(),
                                                         (ptbBasses[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbBasses[i].getCapo())), tuning);
         else if (tuning.getNotes().length >= 6 && tuning.getNotes().length <= 7) // otherwise, convert the instrument to an "other" instrument
            instruments[numGuitars + i] = new OtherTreble((byte)(ptbBasses[i].getNumber() + numGuitars), ptbBasses[i].getDescription(), Midi.Sound.Agogo, volumeMap.get(ptbBasses[i].getInitialVolume()),
                                                          ptbBasses[i].getPan(), ptbBasses[i].getReverb(), ptbBasses[i].getChorus(),
                                                         (ptbBasses[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbBasses[i].getCapo())), tuning);
         else if (BassGuitar.isValidPreset(preset) && tuning.getNotes().length >= 4 && tuning.getNotes().length <= 6)
            instruments[numGuitars + i] = new BassGuitar ((byte)(ptbBasses[i].getNumber() + numGuitars), ptbBasses[i].getDescription(), preset, volumeMap.get(ptbBasses[i].getInitialVolume()),
                                                          ptbBasses[i].getPan(), ptbBasses[i].getReverb(), ptbBasses[i].getChorus(),
                                                         (ptbBasses[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbBasses[i].getCapo())), tuning);
         else if (OtherBass.isValidPreset(preset) && tuning.getNotes().length >= 4 && tuning.getNotes().length <= 5)
            instruments[numGuitars + i] = new OtherBass  ((byte)(ptbBasses[i].getNumber() + numGuitars), ptbBasses[i].getDescription(), preset, volumeMap.get(ptbBasses[i].getInitialVolume()),
                                                          ptbBasses[i].getPan(), ptbBasses[i].getReverb(), ptbBasses[i].getChorus(),
                                                         (ptbBasses[i].getCapo() == 0 ? Instrument.Fret.Not_Used : convertFret(ptbBasses[i].getCapo())), tuning);
         else
            throw new RuntimeException("Unsupported instrument type: " + ptbGuitars[i].getDescription() + ", " + tuning.getNumStrings() + " strings, midi sound: " + preset);

         // convert the names of standard, predefined tunings to the beaglebuddy names
         Tuning t = TuningDictionary.getTuning(instruments[numGuitars + i].getType(), tuning.getNotes());
         if (t != null)
         {
            instruments[numGuitars + i].setTuning(t);
         }
         else
         {
            instruments[numGuitars + i].getTuning().setName("custom");;

            // todo: for debugging purposes only - remove when done
            if (t == null)
            {
//             System.out.println(instruments[numGuitars + i].getTuning().getName() + " tuning with " + instruments[numGuitars + i].getTuning().getNumStrings() + " strings for instrument (" + instruments[numGuitars + i].getType() + ") " + instruments[numGuitars + i].getName() + " not found in tuning dictionary.");
//             System.out.println(instruments[numGuitars + i].getTuning());
            }
         }
      }

      // add a set of drums for development\debugging purposes
      // todo: remove this when finished debugging
      Drums drums = new Drums();
      drums.setId((byte)(instruments.length - 1));
      instruments[instruments.length - 1] = drums;

      return instruments;
   }

   /**
    * convert power tab 1.70 guitar ins to beaglebuddy 1.0 instrument ins.
    * <br/><br/>
    * @param numGuitars   when converting guitar ins from the ptb bass score, we need to offset the guitar in ids by the number of guitars.
    */
   private static InstrumentIn convertGuitarIn(PTBGuitarIn ptbGuitarIn, int numGuitars)
   {
      return new InstrumentIn(ptbGuitarIn.getStaff(), convertActiveInstruments(ptbGuitarIn.getStaffGuitars(), numGuitars), convertActiveInstruments(ptbGuitarIn.getRhythmSlashGuitars(), numGuitars));
   }

   /**
    * convert the power tab 1.70 header to a beaglebuddy 1.0 information.
    */
   private static Information convertInformation(PTBHeader ptbHeader)
   {
      Information information = new Information();

      information.setFileType        (ptbHeader.getFileType() == PTBHeader.FileType.Song ? Information.FileType.Song : Information.FileType.Lesson);
      information.setArtist          (ptbHeader.getArtist                  ());
      information.setAlbumTitle      (ptbHeader.getAlbumTitle              ());
      information.setSongTitle       (ptbHeader.getSongTitle               ());
      information.setAlbumReleaseYear(ptbHeader.getAlbumReleaseYear        ());
      information.setMusicBy         (ptbHeader.getMusicBy                 ());
      information.setWordsBy         (ptbHeader.getWordsBy                 ());
      information.setTranscribedBy   (ptbHeader.getGuitarScoreTranscribedBy() + (ptbHeader.getBassScoreTranscribedBy() == null || ptbHeader.getBassScoreTranscribedBy().length() == 0 ? "" : ", " + ptbHeader.getBassScoreTranscribedBy()));
      information.setCopyright       (ptbHeader.getCopyright               ());
      information.setPerformanceNotes(ptbHeader.getGuitarPerformanceNotes  () + (ptbHeader.getBassPerformanceNotes  () == null || ptbHeader.getBassPerformanceNotes  ().length() == 0  ? "" : "\n\n" + ptbHeader.getBassPerformanceNotes()));
      information.setLessonTitle     (ptbHeader.getLessonTitle             ());
      information.setLessonSubTitle  (ptbHeader.getLessonSubTitle          ());
      information.setLessonBy        (ptbHeader.getLessonBy                ());

      return information;
   }

   /**
    * convert a power tab 1.70 key signature to a beaglebuddy 1.0 key signature.
    */
   private static KeySignature convertKeySignature(PTBKeySignature ptbKeySignature)
   {
	 return new KeySignature(KeySignature.getAccidentals(ptbKeySignature.getAccidentals().ordinal()), ptbKeySignature.isMinor() ? KeySignature.Type.Minor : KeySignature.Type.Major,
                             ptbKeySignature.isShown(), ptbKeySignature.isCancellation());
   }

   /**
    * convert a power tab 1.70 midi duration to a beaglebuddy 1.0 midi duration.
    */
   private static Midi.Duration convertMidiDuration(PTBMidi.Duration ptbDuration)
   {
      return midDurationiMap.get(ptbDuration);
   }

   /**
    * convert a power tab 1.70 note to a beaglebuddy 1.0 note.
    */
   private static Note convertNote(PTBNote ptbNote)
   {
      return new Note(Instrument.getString(ptbNote.getString()), convertFret(ptbNote.getFret()), convertNoteAttributes(ptbNote.getAttributes()));
   }

   /**
    * convert an array of power tab 1.70 notes to an array of beaglebuddy 1.0 notes.
    */
   private static Note[] convertNotes(PTBNote[] ptbNotes)
   {
      Note[] notes = new Note[ptbNotes.length];

      for(int i=0; i<notes.length; ++i)
         notes[i] = convertNote(ptbNotes[i]);

      return notes;
   }

   /**
    * convert power tab 1.70 note attributes to beaglebuddy 1.0 note attributes.
    */
   private static NoteAttribute[] convertNoteAttributes(PTBNoteAttribute[] ptbNoteAttributes)
   {
      ArrayList<NoteAttribute> noteAttributes = new ArrayList<NoteAttribute>(0);

      for(PTBNoteAttribute ptbNoteAttribute : ptbNoteAttributes)
      {
         NoteAttribute noteAttribute = noteMap.get(ptbNoteAttribute.getType());
         if (noteAttribute != null)
         {
            noteAttribute = noteAttribute.clone();
            if (noteAttribute instanceof ArtificialHarmonic)
            {
               ArtificialHarmonic    ah    = (ArtificialHarmonic   )noteAttribute;
               PTBArtificialHarmonic ptbAh = (PTBArtificialHarmonic)ptbNoteAttribute;
               ah.setNote  (keyMidiNoteMap.get(ptbAh.getKey()));
               ah.setOctave(ahOctaveMap.get(ptbAh.getOctave()));
            }
            else if (noteAttribute instanceof Bend)
            {
               Bend    bend    = (Bend   )noteAttribute;
               PTBBend ptbBend = (PTBBend)ptbNoteAttribute;
               bend.setBendType      (Bend.getBendType(ptbBend.getBendType().ordinal()));
               bend.setBentPitch     (ptbBend.getBentPitch());
               bend.setReleasePitch  (ptbBend.getReleasePitch());
               bend.setDuration      (ptbBend.getDuration());
               bend.setDrawStartPoint(Bend.getDrawingPoint(ptbBend.getDrawStartPoint().ordinal()));
               bend.setDrawEndPoint  (Bend.getDrawingPoint(ptbBend.getDrawEndPoint  ().ordinal()));
               // there is a bug in power tab.   convert the bent pitch and release pitch for gradual releases.
               if (bend.getBendType() == Bend.BendType.GradualRelease && bend.getBentPitch() != 0 && bend.getReleasePitch() == 0)
               {
                  bend.setBentPitch(bend.getReleasePitch());
                  bend.setReleasePitch((byte)0);
               }
            }
            else if (noteAttribute instanceof Octave)
            {
               Octave    octave    = (Octave   )noteAttribute;
               PTBOctave ptbOctave = (PTBOctave)ptbNoteAttribute;
               octave.setOctaveType(octaveMap.get(ptbOctave.getOctaveType()));
            }
            else if (noteAttribute instanceof SlideInto)
            {
               SlideInto    slideInto    = (SlideInto   )noteAttribute;
               PTBSlideInto ptbSlideInto = (PTBSlideInto)ptbNoteAttribute;
               slideInto.setSlideType(SlideInto.getSlideType(ptbSlideInto.getSlideType().ordinal()));
            }
            else if (noteAttribute instanceof SlideOutOf)
            {
               SlideOutOf    slideOutOf    = (SlideOutOf   )noteAttribute;
               PTBSlideOutOf ptbSlideOutOf = (PTBSlideOutOf)ptbNoteAttribute;
               slideOutOf.setSlideType(SlideOutOf.getSlideType(ptbSlideOutOf.getSlideType().ordinal()));
               slideOutOf.setNumHalfSteps(ptbSlideOutOf.getNumHalfSteps());
            }
            else if (noteAttribute instanceof TappedHarmonic)
            {
               TappedHarmonic    th    = (TappedHarmonic   )noteAttribute;
               PTBTappedHarmonic ptbTh = (PTBTappedHarmonic)ptbNoteAttribute;
               th.setFret(convertFret(ptbTh.getFretNumber()));
            }
            else if (noteAttribute instanceof Trill)
            {
               Trill    trill    = (Trill   )noteAttribute;
               PTBTrill ptbTrill = (PTBTrill)ptbNoteAttribute;
               trill.setTrillFret(convertFret(ptbTrill.getTrillFret()));
            }
         }
         noteAttributes.add(noteAttribute);
      }

      // convert to array
      NoteAttribute[] attributes = new NoteAttribute[noteAttributes.size()];
      attributes = noteAttributes.toArray(attributes);

      return attributes;
   }

   /**
    * convert a power tab 1.70 position to a beaglebuddy 1.0 chord.
    */
   private static Chord convertPosition(PTBPosition ptbPosition)
   {
      return new Chord(ptbPosition.getPosition(), convertMidiDuration(ptbPosition.getDuration()), convertChordDurationAttributes(ptbPosition.getAttributes(), ptbPosition.getBeam()),
                       convertBeam(ptbPosition.getBeam()), convertNotes(ptbPosition.getNotes()), convertChordAttributes(ptbPosition.getAttributes()));
   }

   /**
    * convert an array of power tab 1.70 positions to an array of beaglebuddy 1.0 chords.
    */
   private static Chord[] convertPositions(PTBPosition[] ptbPositions)
   {
      Chord[] chords = new Chord[ptbPositions.length];

      for(int i=0; i<chords.length; ++i)
         chords[i] = convertPosition(ptbPositions[i]);

      return chords;
   }

   /**
    * convert a power tab 1.70 rehearsal sign to a beaglebuddy 1.0 rehearsal sign.
    */
   private static RehearsalSign convertRehearsalSign(PTBRehearsalSign ptbRehearsalSign)
   {
      return ptbRehearsalSign.getLetter() == PTBRehearsalSign.NOT_SET ? null : new RehearsalSign(ptbRehearsalSign.getLetter(), ptbRehearsalSign.getDescription());
   }

   /**
    * convert a power tab 1.70 rhythm slash to a beaglebuddy 1.0 rhythm slash.
    */
   private static RhythmSlash convertRhythmSlash(PTBRhythmSlash ptbRhythmSlash)
   {
      return new RhythmSlash(ptbRhythmSlash.getPosition(), convertMidiDuration(ptbRhythmSlash.getDuration()), convertRhythmSlashDurationAttributes(ptbRhythmSlash.getAttributes()),
                             RhythmSlash.getBeamGroup(ptbRhythmSlash.getBeamGroup().ordinal()), RhythmSlash.getTripletGroup(ptbRhythmSlash.getTripletGroup().ordinal()),
                             convertMidiDuration(ptbRhythmSlash.getPreviousBeamDuration()), convertRhythmSlashAttributes(ptbRhythmSlash.getAttributes()));
   }

   /**
    * convert an array of power tab 1.70 rhythm slashes to a beaglebuddy 1.0 rhythm staff.
    */
   private static RhythmStaff convertRhythmSlashes(PTBRhythmSlash[] ptbRhythmSlashes)
   {
      RhythmStaff rhythmStaff = null;

      if (ptbRhythmSlashes != null && ptbRhythmSlashes.length != 0)
      {
         RhythmSlash[] rhythmSlashes = new RhythmSlash[ptbRhythmSlashes.length];
         for(int i=0; i<rhythmSlashes.length; ++i)
            rhythmSlashes[i] = convertRhythmSlash(ptbRhythmSlashes[i]);

         rhythmStaff = new RhythmStaff(rhythmSlashes);
      }
      return rhythmStaff;
   }

   /**
    * convert power tab 1.70 rhythm slash attributes to beaglebuddy 1.0 rhythm slash attributes.
    */
   private static RhythmSlashAttribute[] convertRhythmSlashAttributes(PTBRhythmSlashAttribute[] ptbRhythmSlashAttributes)
   {
      ArrayList<RhythmSlashAttribute> rhythmSlashAttributes = new ArrayList<RhythmSlashAttribute>();

      for(PTBRhythmSlashAttribute ptbRhythmSlashAttribute : ptbRhythmSlashAttributes)
      {
         RhythmSlashAttribute rhythmSlashAttribute = rhythmMap.get(ptbRhythmSlashAttribute.getType());
         // the rhythm slash attribute will be null if the ptb rhythm slash attribute is mapped to a beaglebuddy duration attribute
         if (rhythmSlashAttribute != null)
         {
            rhythmSlashAttribute = rhythmSlashAttribute.clone();
            if (rhythmSlashAttribute instanceof SingleNote)
            {
               SingleNote    singleNote = (   SingleNote)   rhythmSlashAttribute;
               PTBSingleNote ptbSingleNote = (PTBSingleNote)ptbRhythmSlashAttribute;
               singleNote.setString(Instrument.getString(ptbSingleNote.getStringNumber()));
               singleNote.setFret  (convertFret  (ptbSingleNote.getFretNumber  ()));
            }
            rhythmSlashAttributes.add(rhythmSlashAttribute);
         }
      }

      // convert to array
      RhythmSlashAttribute[] attributes = new RhythmSlashAttribute[rhythmSlashAttributes.size()];
      attributes = rhythmSlashAttributes.toArray(attributes);

      return attributes;
   }

   /**
    * convert power tab 1.70 rhythm slash attributes to beaglebuddy 1.0 duration attributes.
    */
   private static DurationAttribute[] convertRhythmSlashDurationAttributes(PTBRhythmSlashAttribute[] ptbRhythmSlashAttributes)
   {
      ArrayList<DurationAttribute> durationAttributes = new ArrayList<DurationAttribute>(0);

      for(PTBRhythmSlashAttribute ptbRhythmSlashAttribute : ptbRhythmSlashAttributes)
      {
         DurationAttribute durationAttribute = rhythmDurationMap.get(ptbRhythmSlashAttribute.getType());
         // the rhythm slash attribute will be null if the ptb rhythm slash attribute is mapped to a beaglebuddy rhythm slash attribute
         if (durationAttribute != null)
            durationAttributes.add(durationAttribute);
      }
      DurationAttribute[] attributes = new DurationAttribute[durationAttributes.size()];
      attributes = durationAttributes.toArray(attributes);

      return attributes;
   }


   /**
    * convert the power tab 1.70 guitar and bass scores to a beaglebuddy 1.0 score.
    */
   private static Score convertScores(PTBScore ptbGuitarScore, PTBScore ptbBassScore)
   {
      boolean guitarScoreEmpty = false,
                bassScoreEmpty = false;

      // if the guitar score is simply the "empty" score that a user is presented with when power tab is first started up, remove the vestigal items.
      guitarScoreEmpty = ptbGuitarScore.isEmpty();
      if ((guitarScoreEmpty))
      {
         ptbGuitarScore.setGuitars  (new PTBGuitar  [0]);
         ptbGuitarScore.setGuitarIns(new PTBGuitarIn[0]);
         ptbGuitarScore.setSystems  (new PTBSystem  [0]);
      }
      bassScoreEmpty = ptbBassScore.isEmpty();
      if ((bassScoreEmpty))
      {
         ptbBassScore.setGuitars  (new PTBGuitar  [0]);
         ptbBassScore.setGuitarIns(new PTBGuitarIn[0]);
         ptbBassScore.setSystems  (new PTBSystem  [0]);
      }
      return (guitarScoreEmpty && bassScoreEmpty ? null :
                                                   new Score(convertGuitars      (ptbGuitarScore.getGuitars         (), ptbBassScore.getGuitars         ()),
                                                             convertFloatingTexts(ptbGuitarScore.getFloatingTexts   (), ptbBassScore.getFloatingTexts   ()),
                                                             convertSystems      (ptbGuitarScore.getGuitars() == null ? 0 : ptbGuitarScore.getGuitars().length,
                                                                                  ptbGuitarScore.getSystems         (), ptbBassScore.getSystems         (),
                                                                                  ptbGuitarScore.getTempoMarkers    (), ptbBassScore.getTempoMarkers    (),
                                                                                  ptbGuitarScore.getAlternateEndings(), ptbBassScore.getAlternateEndings(),
                                                                                  ptbGuitarScore.getDynamicMarkers  (), ptbBassScore.getDynamicMarkers  (),
                                                                                  ptbGuitarScore.getGuitarIns       (), ptbBassScore.getGuitarIns       ())));
   }

   /**
    * convert a power tab 1.70 staff to a beaglebuddy 1.0 staff.
    */
   private static Staff convertStaff(PTBStaff ptbStaff)
   {
      Staff.Clef clef = clefMap.get(ptbStaff.getClef());
      if (clef == Staff.Clef.Treble)
         return new TrebleStaff(ptbStaff.getNumTabLines(), ptbStaff.getStandardNotationStaffAboveSpacing(), ptbStaff.getStandardNotationStaffBelowSpacing(),
                                ptbStaff.getSymbolSpacing(), ptbStaff.getTabStaffBelowSpacing(), convertPositions(ptbStaff.getLowVoice()), convertPositions(ptbStaff.getHighVoice()));
      else
         return new BassStaff  (ptbStaff.getNumTabLines(), ptbStaff.getStandardNotationStaffAboveSpacing(), ptbStaff.getStandardNotationStaffBelowSpacing(),
                                ptbStaff.getSymbolSpacing(), ptbStaff.getTabStaffBelowSpacing(), convertPositions(ptbStaff.getLowVoice()), convertPositions(ptbStaff.getHighVoice()));
   }

   /**
    * convert an array of power tab 1.70 staffs to an array of beaglebuddy 1.0 staffs.
    */
   private static Staff[] convertStaffs(PTBStaff[] ptbStaffs)
   {
      Staff[] staffs = new Staff[ptbStaffs.length];
      for(int i=0; i<staffs.length; ++i)
         staffs[i] = convertStaff(ptbStaffs[i]);

      return staffs;
   }

   /**
    * convert a power tab 1.70 system to a beaglebuddy 1.0 section.
    */
   private static Section convertSystem(PTBSystem ptbSystem, PTBTempoMarker[] ptbTempoMarkers, PTBAlternateEnding[] ptbAlternateEndings, PTBDynamic[] ptbDynamicMarkers, PTBGuitarIn[] ptbGuitarIns, int numGuitars)
   {
      Section section = new Section(ptbSystem.getBoundingRectangle(), ptbSystem.getPositionSpacing(), ptbSystem.getRhythmSlashSpacingAbove(), ptbSystem.getRhythmSlashSpacingBelow(), ptbSystem.getExtraSpacing(),
                                    convertRhythmSlashes(ptbSystem.getRhythmSlashes()), convertStaffs(ptbSystem.getStaffs()),
                                    convertBarlines(ptbSystem.getStartBar(), ptbSystem.getBarlines(), ptbSystem.getEndBar()));

      // set the drawing position for the end bar
      section.getEndBarline().setPosition(getMaxDrawingPosition(section));
      PTBDirection[] ptbDirections = ptbSystem.getDirections();
      PTBChordText[] ptbChordTexts = ptbSystem.getChordTexts();
      for(PTBDirection ptbDirection : ptbDirections)
         if (ptbDirection.getPosition() > section.getEndBarline().getPosition())
            section.getEndBarline().setPosition((byte)(ptbDirection.getPosition() + 1));
      for(PTBTempoMarker ptbTempoMarker : ptbTempoMarkers)
         if (ptbTempoMarker.getPosition() > section.getEndBarline().getPosition())
            section.getEndBarline().setPosition((byte)(ptbTempoMarker.getPosition() + 1));
      for(PTBDynamic ptbDynamicMarker : ptbDynamicMarkers)
         if (ptbDynamicMarker.getPosition() > section.getEndBarline().getPosition())
            section.getEndBarline().setPosition((byte)(ptbDynamicMarker.getPosition() + 1));
      for(PTBChordText ptbChordText : ptbChordTexts)
         if (ptbChordText.getPosition() > section.getEndBarline().getPosition())
            section.getEndBarline().setPosition((byte)(ptbChordText.getPosition() + 1));
      for(PTBGuitarIn ptbGuitarIn : ptbGuitarIns)
         if (ptbGuitarIn.getPosition() > section.getEndBarline().getPosition())
            section.getEndBarline().setPosition((byte)(ptbGuitarIn.getPosition() + 1));


      ArrayList<Barline> barlines = section.getBarlines();

      // convert any tempo changes, setting their drawing position to that of the previous bar line
      // note: power tab allowed tempo changes to occur anywhere in a measure.
      //       beaglebuddy only allows them to occur on bar lines (which demarcate the beginning or end of a measure).
      //       if more than one tempo marker is found within a measure in the power tab file, the last one is used.
      for(PTBTempoMarker ptbTempoMarker : ptbTempoMarkers)
      {
         for(int barline=0; barline<barlines.size()-1; ++barline)
         {
            if (ptbTempoMarker.getPosition() >= barlines.get(barline  ).getPosition() &&
                ptbTempoMarker.getPosition() <  barlines.get(barline+1).getPosition() &&
                ptbTempoMarker.getBeatsPerMinute() != 0)
               barlines.get(barline).setTempoMarker(convertTempoMarker(ptbTempoMarker));
         }
      }

      // convert any alternate endings, setting their drawing position to that of the previous bar line
      // note: power tab allowed alternate endings to occur anywhere in a measure.
      //       beaglebuddy only allows them to occur on bar lines (which demarcate the beginning or end of a measure).
      //       if more than one alternate ending is found within a measure in the power tab file, the last one is used.
      // note: power tab allowed alternate endings to also contain musical directions (D.C., D.S., or D.S.S)
      //       beaglebuddy does not allow this.  instead these musical directions found with a power tab alternate
      //       ending are placed on the next barline.
      for(PTBAlternateEnding ptbAlternateEnding : ptbAlternateEndings)
      {
         for(int barline=0; barline<barlines.size()-1; ++barline)
         {
            if (ptbAlternateEnding.getPosition() >= barlines.get(barline  ).getPosition() &&
                ptbAlternateEnding.getPosition() <  barlines.get(barline+1).getPosition())
            {
               barlines.get(barline).setAlternateEnding(convertAlternateEnding(ptbAlternateEnding));
               Direction.Symbol[] directions = convertDirectionSymbolTypes(ptbAlternateEnding.getDirections());
               if (directions.length != 0)
               {
                  ArrayList<Direction> nextBarlineDirections = barlines.get(barline+1).getDirections();
                  for(Direction.Symbol direction : directions)
                     nextBarlineDirections.add(new Direction(direction, Direction.Symbol.None, (byte)0));
               }
            }
         }
      }

      // convert any directions, setting their drawing position to that of the nearest bar line
      // note: power tab allowed music directions to occur anywhere in a measure.
      //       beaglebuddy only allows them to occur on bar lines (which demarcate the beginning or end of a measure).
      for(PTBDirection ptbDirection : ptbDirections)
      {
         for(int j=0; j<barlines.size()-1; ++j)
         {
            if (ptbDirection.getPosition() >= barlines.get(j  ).getPosition() &&
                ptbDirection.getPosition() <  barlines.get(j+1).getPosition())
            {
               Direction[] directions = convertDirection(ptbDirection);
               int barline = ((ptbDirection.getPosition() - barlines.get(j).getPosition()) <= (barlines.get(j+1).getPosition() - ptbDirection.getPosition()) ? j : j + 1);
               barlines.get(barline).addDirections(directions);
               break;
            }
         }
      }

      // convert any volume changes, setting their drawing position to that of the previous bar line
      // note: power tab allowed volume changes to occur anywhere in a measure.
      //       beaglebuddy only allows them to occur on bar lines (which demarcate the beginning or end of a measure).
      for(PTBDynamic ptbDynamicMarker : ptbDynamicMarkers)
      {
         for(int barline=0; barline<barlines.size()-1; ++barline)
         {
            if (ptbDynamicMarker.getPosition() >= barlines.get(barline  ).getPosition() &&
                ptbDynamicMarker.getPosition() <  barlines.get(barline+1).getPosition())
            {
               // volume will be null if the dynamic marker is set only for the rhythm staff
               // in that case, we ignore it.  see the comments for the convertDynamicMarker() method.
               Volume volume = convertDynamicMarker(ptbDynamicMarker);
               if (volume != null)
                  barlines.get(barline).getVolumes().add(volume);
            }
         }
      }

      // convert any guitar ins, setting their drawing position to that of the previous bar line.
      // note: power tab allowed music guitar ins to occur anywhere in a measure.
      //       beaglebuddy only allows them to occur on bar lines (which demarcate the beginning or end of a measure).
      //       thus, if multiple instrument in's are found within a given measure for a staff, then the first one encountered
      //       in the measure is kept and the others discarded.
      for(PTBGuitarIn ptbGuitarIn : ptbGuitarIns)
      {
         for(int barline=0; barline<barlines.size()-1; ++barline)
         {
            // process the guitar ins for this section that occur in each measure
            if (ptbGuitarIn.getPosition() >= barlines.get(barline  ).getPosition() &&
                ptbGuitarIn.getPosition() <  barlines.get(barline+1).getPosition())
            {
               // convert the power tab guitar in to a beaglebuddy instrument in
               InstrumentIn newInstrumentIn = convertGuitarIn(ptbGuitarIn, numGuitars);

               // see if there is already an instrument in defined for the current measure and staff
               ArrayList<InstrumentIn> instrumentIns = barlines.get(barline).getInstrumentIns();
               boolean                 found         = false;
               for(int i=0; i<instrumentIns.size() && !found; ++i)
               {
                  InstrumentIn instrumentIn = instrumentIns.get(i);
                  found = instrumentIn.getStaff() == newInstrumentIn.getStaff();
               }
               // if the barline doesn't already have an instrument in defined for the same staff, then add the new instrument in
               if (!found)
                  instrumentIns.add(newInstrumentIn);
            }
         }
      }

      // convert any chord texts, setting their drawing position to that of the nearest chord or rhythm  slash
      // note: power tab allowed chord names to occur anywhere in a measure.  if a section had multiple staffs and the chord name is on the same drawing position
      //       as a chord in either one or all of the staffs, power tab associates the chord name with the drawing position of the chord in the top most staff.
      //       beaglebuddy does not allow chord names to occur at arbitrary drawing positions within a measure, but rather requires a user to attach them to a chord or
      //       rhythm slash.  while this is less flexible than the power tab approach, in practice, chord names occur on a chord or rhythm slash position about 99+% of
      //       the time.  given this high percentage, and since associating chord names with existing chord positions negates the need for additional code in the layout
      //       manager, it was therefore decided that the beaglebuddy tab editor would sacrafice the flexibilty of allowing chord names at arbitrary drawing positions
      //       for the sake of simplicity, reduced code complexity, and thus reduced maintenance.
      //       Thus, for conversion purposes, if a chord text does not fall on a chord or rhythm slash, beaglebuddy discards it.
      // note: tests run on approximately 600 existing power tab files showed the following:
      //       chord texts aligned on a chord or rhythm slash: 19254
      //       chord texts in between a chord or rhythm slash: 53
      //       chord texts empty: 2
      for(PTBChordText ptbChordText : ptbChordTexts)
      {
         boolean found = false;

         // see if there is a rhythm slash in the section that occupies the same drawing position.
         if (section.getRhythmStaff() != null)
         {
            ArrayList<RhythmSlash> rhythmSlashes = section.getRhythmStaff().getRhythmSlashes();
            for(RhythmSlash rhythmSlash : rhythmSlashes)
            {
               if (ptbChordText.getPosition() == rhythmSlash.getPosition())
               {  // convert the chord text to an attribute of the matching rhythm slash
                  com.beaglebuddy.tab.model.attribute.rhythm.ChordName chordName = new com.beaglebuddy.tab.model.attribute.rhythm.ChordName(ptbChordText.getChordName().getName());
                  rhythmSlash.addAttribute(chordName);
               }
            }
         }

         // if there wasn't rhythm slash that had the same drawing position as the chord text,
         // then see if there is a chord in one of the staffs that occupies the same drawing position.
         if (!found)
         {
            ArrayList<Staff> staffs = section.getStaffs();
            for(int staff=0; staff<staffs.size() && !found; ++staff)
            {
               // search through the chords of each melody line in the staffs.
               for(int voice=Staff.LowVoice; voice<Staff.NumVoices && !found; ++voice)
               {
                  ArrayList<Chord> chords = staffs.get(staff).getChords(voice);
                  for(int chord=0; chord<chords.size() && !found; ++chord)
                  {
                     if (ptbChordText.getPosition() == chords.get(chord).getPosition())
                     {  // convert the chord text to an attribute of the matching chord
                        com.beaglebuddy.tab.model.attribute.chord.ChordName chordName = new com.beaglebuddy.tab.model.attribute.chord.ChordName(ptbChordText.getChordName().getName());
                        chords.get(chord).addAttribute(chordName);
                        found = true;
                     }
                  }
               }
            }
         }
      }

      // convert any barline pairs that are a repeat end bar directly followed by a repeat start bar to a single RepeatEndBegin barline
      for(int barline=1; barline<barlines.size()-2; ++barline)
      {
         if (barlines.get(barline).getType() == Barline.Type.RepeatEnd && barlines.get(barline + 1).getType() == Barline.Type.RepeatStart)
         {
            // see if there is anything in between these two barlines, or if it is an empty measure
            boolean isEmpty = true;

            // check the rhythm staff
            if (section.getRhythmStaff() != null)
            {
               ArrayList<RhythmSlash> rhythmSlashes = section.getRhythmStaff().getRhythmSlashes();
               for(int i=0; i<rhythmSlashes.size() && isEmpty; ++i)
                  isEmpty = !(rhythmSlashes.get(i).getPosition() >= barlines.get(barline).getPosition() && rhythmSlashes.get(i).getPosition() < barlines.get(barline + 1).getPosition());
            }

            // check the chords in each melody line in each tab staff
            ArrayList<Staff> staffs = section.getStaffs();
            for(int staff=0; staff<staffs.size() && isEmpty; ++staff)
            {
               for(int voice=Staff.LowVoice; voice<Staff.NumVoices && isEmpty; ++voice)
               {
                  ArrayList<Chord> chords = staffs.get(staff).getChords(voice);
                  for(int i=0; i<chords.size() && isEmpty; ++i)
                     isEmpty = !(chords.get(i).getPosition() >= barlines.get(barline).getPosition() && chords.get(i).getPosition() < barlines.get(barline + 1).getPosition());
               }
            }
            if (isEmpty)
            {
               // transfer some of the attributes of the repeat end barline to the repeat start bar line
               barlines.get(barline+1).setNumRepeats(barlines.get(barline).getNumRepeats());
               for(Direction direction : barlines.get(barline).getDirections())
                  barlines.get(barline+1).getDirections().add(direction);

               // convert the repeat start bar line to a repeat end\start bar line
               barlines.get(barline+1).setType(Barline.Type.RepeatEndStart);

               // remove the repeat end bar line
               barlines.remove(barline);
            }
         }
      }

      return section;
   }

   /**
    * convert power tab 1.70 systems to beaglebuddy 1.0 sections.
    *
    * since power tab stored the bass line and guitar lines in separate scores, whereas beaglebuddy stores them in a single score, we need to combine
    * the power tab guitar and bass scores somehow.  After careful thought, it was decided to simply append the power tab bass score on to the end of
    * the guitar score.  User's will then have to manually "merge" the bass line into the single beaglebuddy score.
    */
   private static Section[] convertSystems(int numGuitars,
                                           PTBSystem         [] ptbGuitarSystems         , PTBSystem         [] ptbBassSystems         ,
                                           PTBTempoMarker    [] ptbGuitarTempoMarkers    , PTBTempoMarker    [] ptbBassTempoMarkers    ,
                                           PTBAlternateEnding[] ptbGuitarAlternateEndings, PTBAlternateEnding[] ptbBassAlternateEndings,
                                           PTBDynamic        [] ptbGuitarDynamicMarkers  , PTBDynamic        [] ptbBassDynamicMarkers  ,
                                           PTBGuitarIn       [] ptbGuitarGuitarIns       , PTBGuitarIn       [] ptbBassGuitarIns       )
   {
      Section[] sections = new Section[ptbGuitarSystems.length + ptbBassSystems.length];

      for(int i=0; i<ptbGuitarSystems.length; ++i)
      {
         // get the tempo markers that occur in this section
         ArrayList<PTBTempoMarker> ptbTempoMarkers = new ArrayList<PTBTempoMarker>(0);
         for(int j=0; j<ptbGuitarTempoMarkers.length; ++j)
         {
            if (ptbGuitarTempoMarkers[j].getSystem() == i)
               ptbTempoMarkers.add(ptbGuitarTempoMarkers[j]);
         }
         PTBTempoMarker[] arrayTempoMarkers = new PTBTempoMarker[ptbTempoMarkers.size()];
         ptbTempoMarkers.toArray(arrayTempoMarkers);

         // get the alternate endings that occur in this section
         ArrayList<PTBAlternateEnding> ptbAlternateEndings = new ArrayList<PTBAlternateEnding>(0);
         for(int j=0; j<ptbGuitarAlternateEndings.length; ++j)
         {
            if (ptbGuitarAlternateEndings[j].getSystem() == i)
               ptbAlternateEndings.add(ptbGuitarAlternateEndings[j]);
         }
         PTBAlternateEnding[] arrayAlternateEndings = new PTBAlternateEnding[ptbAlternateEndings.size()];
         ptbAlternateEndings.toArray(arrayAlternateEndings);

         // get the dynamic markers that occur in this section
         ArrayList<PTBDynamic> ptbDynamicMarkers = new ArrayList<PTBDynamic>(0);
         for(int j=0; j<ptbGuitarDynamicMarkers.length; ++j)
         {
            if (ptbGuitarDynamicMarkers[j].getSystem() == i)
               ptbDynamicMarkers.add(ptbGuitarDynamicMarkers[j]);
         }
         PTBDynamic[] arrayDynamicMarkers = new PTBDynamic[ptbDynamicMarkers.size()];
         ptbDynamicMarkers.toArray(arrayDynamicMarkers);

         // get the guitar ins that occur in this section
         ArrayList<PTBGuitarIn> ptbGuitarIns = new ArrayList<PTBGuitarIn>(0);
         for(int j=0; j<ptbGuitarGuitarIns.length; ++j)
         {
            if (ptbGuitarGuitarIns[j].getSystem() == i)
               ptbGuitarIns.add(ptbGuitarGuitarIns[j]);
         }
         PTBGuitarIn[] arrayGuitarIns = new PTBGuitarIn[ptbGuitarIns.size()];
         ptbGuitarIns.toArray(arrayGuitarIns);

         sections[i] = convertSystem(ptbGuitarSystems[i], arrayTempoMarkers, arrayAlternateEndings, arrayDynamicMarkers, arrayGuitarIns, 0);
      }

      // add the bass score on to the end of the guitar score
      int yOffset = (ptbGuitarSystems.length == 0 ? 0 : (int)sections[ptbGuitarSystems.length-1].getBoundingRectangle().getY() + (int)sections[ptbGuitarSystems.length-1].getBoundingRectangle().getHeight()) + 26;
      for(int i=0; i<ptbBassSystems.length; ++i)
      {
         // get the tempo markers that occur in this section
         ArrayList<PTBTempoMarker> ptbTempoMarkers = new ArrayList<PTBTempoMarker>(0);
         for(int j=0; j<ptbBassTempoMarkers.length; ++j)
         {
            if (ptbBassTempoMarkers[j].getSystem() == i)
               ptbTempoMarkers.add(ptbBassTempoMarkers[j]);
         }
         PTBTempoMarker[] arrayTempoMarkers = new PTBTempoMarker[ptbTempoMarkers.size()];
         ptbTempoMarkers.toArray(arrayTempoMarkers);

         // get the alternate endings that occur in this section
         ArrayList<PTBAlternateEnding> ptbAlternateEndings = new ArrayList<PTBAlternateEnding>(0);
         for(int j=0; j<ptbBassAlternateEndings.length; ++j)
         {
            if (ptbBassAlternateEndings[j].getSystem() == i)
               ptbAlternateEndings.add(ptbBassAlternateEndings[j]);
         }
         PTBAlternateEnding[] arrayAlternateEndings = new PTBAlternateEnding[ptbAlternateEndings.size()];
         ptbAlternateEndings.toArray(arrayAlternateEndings);

         // get the dynamic markers that occur in this section
         ArrayList<PTBDynamic> ptbDynamicMarkers = new ArrayList<PTBDynamic>(0);
         for(int j=0; j<ptbBassDynamicMarkers.length; ++j)
         {
            if (ptbBassDynamicMarkers[j].getSystem() == i)
               ptbDynamicMarkers.add(ptbBassDynamicMarkers[j]);
         }
         PTBDynamic[] arrayDynamicMarkers = new PTBDynamic[ptbDynamicMarkers.size()];
         ptbDynamicMarkers.toArray(arrayDynamicMarkers);

         // get the guitar ins that occur in this section
         ArrayList<PTBGuitarIn> ptbGuitarIns = new ArrayList<PTBGuitarIn>(0);
         for(int j=0; j<ptbBassGuitarIns.length; ++j)
         {
            if (ptbBassGuitarIns[j].getSystem() == i)
               ptbGuitarIns.add(ptbBassGuitarIns[j]);
         }
         PTBGuitarIn[] arrayGuitarIns = new PTBGuitarIn[ptbGuitarIns.size()];
         ptbGuitarIns.toArray(arrayGuitarIns);

         sections[ptbGuitarSystems.length + i] = convertSystem(ptbBassSystems[i], arrayTempoMarkers, arrayAlternateEndings, arrayDynamicMarkers, arrayGuitarIns, numGuitars);

         // update the drawing coordinates for the bass sections
         Rectangle rectangle = sections[ptbGuitarSystems.length + i].getBoundingRectangle();
         sections[ptbGuitarSystems.length + i].setBoundingRectangle(new Rectangle((int)rectangle.getX(), (int)rectangle.getY() + yOffset, (int)rectangle.getWidth(), (int)rectangle.getHeight()));
      }

      // multi bar rest check
      // multi bar rests, as defined in power tab, only make sense if they are for a single instrument on a single staff.
      // thus, if we find any that are contained in a section with multiple staffs, then we convert them to a simple rest.
      for(int section=0; section<sections.length; ++section)
      {
         ArrayList<Staff> staffs = sections[section].getStaffs();
         for(int staff=0; staff<staffs.size(); ++staff)
         {
            for(int voice=Staff.LowVoice; voice<Staff.NumVoices; ++voice)
            {
               ArrayList<Chord> chords = staffs.get(staff).getChords(voice);
               for(int chord=0; chord<chords.size(); ++chord)
               {
                  if (chords.get(chord).isMultiBarRest() && staffs.size() != 1)
                  {
                     // find which measure the multibar rest is in and get the time signature
                     Chord              multibarRest  = chords.get(chord);
                     ArrayList<Barline> barlines      = sections[section].getBarlines();
                     TimeSignature      timeSignature = null;
                     for(int barline=0; barline<(barlines.size() -1) && timeSignature == null; ++barline)
                     {
                        if (multibarRest.getPosition() >= barlines.get(barline    ).getPosition() &&
                           (multibarRest.getPosition() <  barlines.get(barline + 1).getPosition() ||
                            barlines.get(barline + 1).getPosition() == -1))
                           timeSignature = barlines.get(barline).getTimeSignature();
                     }
                     // replace the multi bar rest with a single rest
                     chords.set(chord, createRest(timeSignature, multibarRest.getPosition()));
                  }
               }
            }
         }
      }

      // todo: remove this when finished developing\debugging drums
      sections[0].getStaffs().add(new DrumStaff());

      return sections;
   }

   /**
    * convert a power tab 1.70 tempo marker to a beaglebuddy 1.0 tempo marker.
    */
   private static TempoMarker convertTempoMarker(PTBTempoMarker ptbTempoMarker)
   {
      return new TempoMarker(ptbTempoMarker.getBeatsPerMinute(), ptbTempoMarker.getDescription(), ptbTempoMarker.getTripletFeelType() == PTBTempoMarker.TripletFeelType.TripletFeelEighth);
   }

   /**
    * convert a power tab 1.70 time signature to a beaglebuddy 1.0 time signature.
    */
   private static TimeSignature convertTimeSignature(PTBTimeSignature ptbTimeSignature)
   {
      return new TimeSignature(ptbTimeSignature.getBeatsPerMeasure(), ptbTimeSignature.getBeatAmount(), ptbTimeSignature.getBeamingPattern(),
                               ptbTimeSignature.isShown(), ptbTimeSignature.isCommonTime(), ptbTimeSignature.isCutTime());
   }

   /**
    * convert a power tab 1.70 tuning to a beaglebuddy 1.0 tuning.
    */
   private static Tuning convertTuning(PTBTuning ptbTuning)
   {
      Midi.Note   [] notes      = null;
      PTBMidi.Note[] ptbNotes   = ptbTuning.getNotes();

      notes = new Midi.Note[ptbNotes.length];
      for(int i=0; i<ptbNotes.length; ++i)
         notes[i] = Midi.getNote(ptbNotes[i].ordinal());

       // Although the power tab editor version 1.70 does not allow users to create tunings without a name, this was not
       // the case in previous versions.  Thus, some earler ptb files that were later "converted" to 1.70 by the PTE
       // do indeed have tunings without a name.  For these tunings, set the name to "custom"
      String name = ptbTuning.getName();
      name = (name == null || name.trim().length() == 0 ? "custom" : name.toLowerCase());

      return new Tuning(name, ptbTuning.getMusicNotationOffset(), notes);
   }

   /**
    * @return a rest with a duration equal to the duration of the time signature at the specified drawing position.
    * <br/><br/>
    * @param timeSignature   the time signature of the measure in which the rest occurs.
    * @param position        the drawing position of the rest.
    */
   private static Chord createRest(TimeSignature timeSignature, byte position)
   {
      Chord         rest       = new Chord();
      int           midiPulses = timeSignature.getMidiPulses();

      rest.setPosition(position);
      for (Midi.Duration d : Midi.Duration.values())
      {
         if (midiPulses == d.pulses())
         {
            rest.setDuration(d);
            break;
         }
         else if (midiPulses < d.pulses())
         {
            rest.setDuration(Midi.getDuration(d.ordinal() - 1));
            rest.addDurationAttribute(rest.getDuration().pulses() * 3 / 2 == midiPulses ? new com.beaglebuddy.tab.model.attribute.duration.Dotted() : new com.beaglebuddy.tab.model.attribute.duration.DottedDouble());
            break;
         }
      }
      return rest;
   }

   /**
    * @return the total number of drawing positions for this section.
    */
   private static byte getMaxDrawingPosition(Section section)
   {
      byte maxDrawingPosition = 0;

      // check the chords in each of the melody lines in each tab staff
      ArrayList<Chord> chords             = null;
      for (Staff staff : section.getStaffs())
      {
         for(int voice=Staff.LowVoice; voice<Staff.NumVoices; ++voice)
         {
            chords = staff.getChords(voice);
            if (chords.size() != 0 && chords.get(chords.size() -1).getPosition() > maxDrawingPosition)
               maxDrawingPosition = chords.get(chords.size() -1).getPosition();
         }
      }

      // then check the rhythm staff
      if (section.getRhythmStaff() != null)
      {
         ArrayList<RhythmSlash> rhythmSlashes = section.getRhythmStaff().getRhythmSlashes();
         if (rhythmSlashes.size() != 0 && rhythmSlashes.get(rhythmSlashes.size() -1).getPosition() > maxDrawingPosition)
            maxDrawingPosition = rhythmSlashes.get(rhythmSlashes.size() -1).getPosition();
      }

      // check the second to last bar line
      // if there weren't any chords in the last measure, just add some arbitrary number of drawing positions
      ArrayList<Barline> barlines = section.getBarlines();
      if (barlines.get(barlines.size()-2).getPosition() >= maxDrawingPosition)
         maxDrawingPosition = (byte)(barlines.get(barlines.size()-2).getPosition() + 8);

      return (byte)(maxDrawingPosition + 1);
   }

   /**
    * @param attributeType   the type of duration attribute to search for.
    * <br/><br/>
    * @return whether the duration has the specified attribute.
    */
   private static boolean hasDurationAttribute(ArrayList<DurationAttribute> durationAttributes, DurationAttribute.Type attributeType)
   {
      for(DurationAttribute attribute : durationAttributes)
         if (attributeType == attribute.getType())
            return true;
      return false;
   }

   /**
    * loads a power tab 1.70 file.
    * <br/><br/>
    * @param ptbFilename name of the power tab file to load.
    * <br/><br/>
    * @return the parsed power tab file.
    * <br/><br/>
    * @throws FileReadException  if the power tab file can not be found, is not a valid 1.70 power tab file, or can not be read in.
    */
   private static PTBFile loadPtbFile(String ptbFilename) throws FileReadException
   {
      PTBFile ptbFile = null;
      try
      {
         ptbFile  = new PTBFile(ptbFilename);
      }
      catch (PTBFileException ex)
      {
         throw new FileReadException(ex.getMessage());
      }
      return ptbFile;
   }
}
