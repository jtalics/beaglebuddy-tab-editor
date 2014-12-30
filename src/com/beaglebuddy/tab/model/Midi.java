/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * � 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;





/**
 * This class contains both midi constants and methods for playing\recording midi sequences.
 * <p>
 * Note: java's midi implementation in the javax.sound.midi package differs from the Midi spec when specifying time values in midi events.
 *       The authors of the java sound packages made midi events specify a cumulative time instead of the normal delta time that is found in the midi standard.
 *       Midi events in the midi standard specify the duration of the midi event.
 *       Java on the other hand make you keep track of the total time for all midi events in a given track.
 * See: http://java.sun.com/j2se/1.5.0/docs/guide/sound/programmer_guide/chapter11.html
 * </p>
 */
public class Midi
{
   // midi channels
   public enum Channel
   {
      Channel_1 (ResourceBundle.getString("midi.channel_0" )), Channel_2 (ResourceBundle.getString("midi.channel_1" )), Channel_3 (ResourceBundle.getString("midi.channel_2" )), Channel_4 (ResourceBundle.getString("midi.channel_3" )),
      Channel_5 (ResourceBundle.getString("midi.channel_4" )), Channel_6 (ResourceBundle.getString("midi.channel_5" )), Channel_7 (ResourceBundle.getString("midi.channel_6" )), Channel_8 (ResourceBundle.getString("midi.channel_7" )),
      Channel_9 (ResourceBundle.getString("midi.channel_8" )), Channel_10(ResourceBundle.getString("midi.channel_9" )), Channel_11(ResourceBundle.getString("midi.channel_10")), Channel_12(ResourceBundle.getString("midi.channel_11")),
      Channel_13(ResourceBundle.getString("midi.channel_12")), Channel_14(ResourceBundle.getString("midi.channel_13")), Channel_15(ResourceBundle.getString("midi.channel_14")), Channel_16(ResourceBundle.getString("midi.channel_15"));

      // data members
      private String text;
      Channel(String text)     {this.text = text;}
      public String text()     {return text;}
      @Override
      public String toString() {return text;}
   }

   // midi durations
   public enum Duration
   {
      NOTE_NONE(ResourceBundle.getString("midi.duration.none"),    0), NOTE_64TH (ResourceBundle.getString("midi.duration.64th"   ),   45), NOTE_32ND   (ResourceBundle.getString("midi.duration.32nd"                   ),   90),
      NOTE_16TH(ResourceBundle.getString("midi.duration.16th"),  180), NOTE_8TH  (ResourceBundle.getString("midi.duration.8th"    ),  360), NOTE_QUARTER(ResourceBundle.getString("midi.duration.quarter"                ),  720),
      NOTE_HALF(ResourceBundle.getString("midi.duration.half"), 1440), NOTE_WHOLE(ResourceBundle.getString("midi.duration.whole"  ), 2880), PPQN        (ResourceBundle.getString("midi.duration.pulses_per_quarter_note"),  720);

      // data members
      private String text;
      private int    pulses;
      Duration(String text, int pulses) {this.text = text; this.pulses = pulses;}
      public String text    () {return text;}
      public int    pulses  () {return pulses;}
      @Override
      public String toString() {return text + " (" + pulses() + ")";}
   }

   // midi keys
   public enum Key
   {
      KEY_C(0)    , KEY_CSHARP(1), KEY_DFLAT(1) , KEY_D(2)    , KEY_DSHARP(3), KEY_EFLAT(3) , KEY_E(4)    , KEY_F(5), KEY_FSHARP(6),
      KEY_GFLAT(6), KEY_G(7)     , KEY_GSHARP(8), KEY_AFLAT(8), KEY_A(9)     , KEY_ASHARP(1), KEY_BFLAT(1), KEY_B(1);

      // data members
      private int value;
      Key(int key) {value = key;}
      public int value() {return value;}
   }

   // midi notes
   public enum Note
   {
      CN1     (  0, "C -1"),                            CSHARPN1(  1, "C# -1"), DFLATN1 (  1, "Db -1"), DN1     (  2, "D -1"), DSHARPN1(  3, "D# -1"), EFLATN1 (  3, "Eb -1"), EN1     (  4, "E -1"), FN1     (  5, "F -1"), FSHARPN1(  6, "F# -1"), GFLATN1 (  6, "Gb -1"), GN1     (  7, "G -1"), GSHARPN1(  8, "G# -1"), AFLATN1 (  8, "Ab -1"), AN1     (  9, "A -1"), ASHARPN1( 10, "A# -1"), BFLATN1 ( 10, "Bb -1"), BN1     ( 11, "B -1"),
      C0      ( 12, "C 0" ),                            CSHARP0 ( 13, "C# 0" ), DFLAT0  ( 13, "Db 0" ), D0      ( 14, "D 0" ), DSHARP0 ( 15, "D# 0" ), EFLAT0  ( 15, "Eb 0" ), E0      ( 16, "E 0" ), F0      ( 17, "F 0" ), FSHARP0 ( 18, "F# 0" ), GFLAT0  ( 18, "Gb 0" ), G0      ( 19, "G 0" ), GSHARP0 ( 20, "G# 0" ), AFLAT0  ( 20, "Ab 0" ), A0      ( 21, "A 0" ), ASHARP0 ( 22, "A# 0" ), BFLAT0  ( 22, "Bb 0" ), B0      ( 23, "B 0" ),
      C1      ( 24, "C 1" ),                            CSHARP1 ( 25, "C# 1" ), DFLAT1  ( 25, "Db 1" ), D1      ( 26, "D 1" ), DSHARP1 ( 27, "D# 1" ), EFLAT1  ( 27, "Eb 1" ), E1      ( 28, "E 1" ), F1      ( 29, "F 1" ), FSHARP1 ( 30, "F# 1" ), GFLAT1  ( 30, "Gb 1" ), G1      ( 31, "G 1" ), GSHARP1 ( 32, "G# 1" ), AFLAT1  ( 32, "Ab 1" ), A1      ( 33, "A 1" ), ASHARP1 ( 34, "A# 1" ), BFLAT1  ( 34, "Bb 1" ), B1      ( 35, "B 1" ),
      C2      ( 36, "C 2" ),                            CSHARP2 ( 37, "C# 2" ), DFLAT2  ( 37, "Db 2" ), D2      ( 38, "D 2" ), DSHARP2 ( 39, "D# 2" ), EFLAT2  ( 39, "Eb 2" ), E2      ( 40, "E 2" ), F2      ( 41, "F 2" ), FSHARP2 ( 42, "F# 2" ), GFLAT2  ( 42, "Gb 2" ), G2      ( 43, "G 2" ), GSHARP2 ( 44, "G# 2" ), AFLAT2  ( 44, "Ab 2" ), A2      ( 45, "A 2" ), ASHARP2 ( 46, "A# 2" ), BFLAT2  ( 46, "Bb 2" ), B2      ( 47, "B 2" ),
      C3      ( 48, "C 3" ),                            CSHARP3 ( 49, "C# 3" ), DFLAT3  ( 49, "Db 3" ), D3      ( 50, "D 3" ), DSHARP3 ( 51, "D# 3" ), EFLAT3  ( 51, "Eb 3" ), E3      ( 52, "E 3" ), F3      ( 53, "F 3" ), FSHARP3 ( 54, "F# 3" ), GFLAT3  ( 54, "Gb 3" ), G3      ( 55, "G 3" ), GSHARP3 ( 56, "G# 3" ), AFLAT3  ( 56, "Ab 3" ), A3      ( 57, "A 3" ), ASHARP3 ( 58, "A# 3" ), BFLAT3  ( 58, "Bb 3" ), B3      ( 59, "B 3" ),
      C4      ( 60, "C 4" ), Middle_C(60, "middle C" ), CSHARP4 ( 61, "C# 4" ), DFLAT4  ( 61, "Db 4" ), D4      ( 62, "D 4" ), DSHARP4 ( 63, "D# 4" ), EFLAT4  ( 63, "Eb 4" ), E4      ( 64, "E 4" ), F4      ( 65, "F 4" ), FSHARP4 ( 66, "F# 4" ), GFLAT4  ( 66, "Gb 4" ), G4      ( 67, "G 4" ), GSHARP4 ( 68, "G# 4" ), AFLAT4  ( 68, "Ab 4" ), A4      ( 69, "A 4" ), ASHARP4 ( 70, "A# 4" ), BFLAT4  ( 70, "Bb 4" ), B4      ( 71, "B 4" ),
      C5      ( 72, "C 5" ),                            CSHARP5 ( 73, "C# 5" ), DFLAT5  ( 73, "Db 5" ), D5      ( 74, "D 5" ), DSHARP5 ( 75, "D# 5" ), EFLAT5  ( 75, "Eb 5" ), E5      ( 76, "E 5" ), F5      ( 77, "F 5" ), FSHARP5 ( 78, "F# 5" ), GFLAT5  ( 78, "Gb 5" ), G5      ( 79, "G 5" ), GSHARP5 ( 80, "G# 5" ), AFLAT5  ( 80, "Ab 5" ), A5      ( 81, "A 5" ), ASHARP5 ( 82, "A# 5" ), BFLAT5  ( 82, "Bb 5" ), B5      ( 83, "B 5" ),
      C6      ( 84, "C 6" ),                            CSHARP6 ( 85, "C# 6" ), DFLAT6  ( 85, "Db 6" ), D6      ( 86, "D 6" ), DSHARP6 ( 87, "D# 6" ), EFLAT6  ( 87, "Eb 6" ), E6      ( 88, "E 6" ), F6      ( 89, "F 6" ), FSHARP6 ( 90, "F# 6" ), GFLAT6  ( 90, "Gb 6" ), G6      ( 91, "G 6" ), GSHARP6 ( 92, "G# 6" ), AFLAT6  ( 92, "Ab 6" ), A6      ( 93, "A 6" ), ASHARP6 ( 94, "A# 6" ), BFLAT6  ( 94, "Bb 6" ), B6      ( 95, "B 6" ),
      C7      ( 96, "C 7" ),                            CSHARP7 ( 97, "C# 7" ), DFLAT7  ( 97, "Db 7" ), D7      ( 98, "D 7" ), DSHARP7 ( 99, "D# 7" ), EFLAT7  ( 99, "Eb 7" ), E7      (100, "E 7" ), F7      (101, "F 7" ), FSHARP7 (102, "F# 7" ), GFLAT7  (102, "Gb 7" ), G7      (103, "G 7" ), GSHARP7 (104, "G# 7" ), AFLAT7  (104, "Ab 7" ), A7      (105, "A 7" ), ASHARP7 (106, "A# 7" ), BFLAT7  (106, "Bb 7" ), B7      (107, "B 7" ),
      C8      (108, "C 8" ),                            CSHARP8 (109, "C# 8" ), DFLAT8  (109, "Db 8" ), D8      (110, "D 8" ), DSHARP8 (111, "D# 8" ), EFLAT8  (111, "Eb 8" ), E8      (112, "E 8" ), F8      (113, "F 8" ), FSHARP8 (114, "F# 8" ), GFLAT8  (114, "Gb 8" ), G8      (115, "G 8" ), GSHARP8 (116, "G# 8" ), AFLAT8  (116, "Ab 8" ), A8      (117, "A 8" ), ASHARP8 (118, "A# 8" ), BFLAT8  (118, "Bb 8" ), B8      (119, "B 8" ),
      C9      (120, "C 9" ),                            CSHARP9 (121, "C# 9" ), DFLAT9  (121, "Db 9" ), D9      (122, "D 9" ), DSHARP9 (123, "D# 9" ), EFLAT9  (123, "Eb 9" ), E9      (124, "E 9" ), F9      (125, "F 9" ), FSHARP9 (126, "F# 9" ), GFLAT9  (126, "Gb 9" ), G9      (127, "G 9" );

      // data members
      private int    pitch;
      private String text;
      private int    octave;
      Note(int pitch, String text)
      {
         this.pitch = pitch;
         this.text = text;
         this.octave = pitch/12 - 1;
      }
      public int    octave()       {return octave;}
      public int    pitch()        {return pitch;}
      public String text()         {return text;}
      public String description()  {return "(ordinal: " + ordinal() + (ordinal() < 10 ? " " : "") +  ", pitch: " + pitch + ", " + text + ")";}
      @Override
      public String toString()     {return text;}
   }

   // midi percussion sounds
   public enum Percussion
   {
      AcousticBassDrum (35, ResourceBundle.getString("midi.percussion.1" )), BassDrum1        (36, ResourceBundle.getString("midi.percussion.2" )), SideStick        (37, ResourceBundle.getString("midi.percussion.3" )), AcousticSnare    (38, ResourceBundle.getString("midi.percussion.4" )), HandClap         (39, ResourceBundle.getString("midi.percussion.5" )),
      ElectricSnare    (40, ResourceBundle.getString("midi.percussion.6" )), LowFloorTom      (41, ResourceBundle.getString("midi.percussion.7" )), ClosedHiHat      (42, ResourceBundle.getString("midi.percussion.8" )), HighFloorTom     (43, ResourceBundle.getString("midi.percussion.9" )), PedalHiHat       (44, ResourceBundle.getString("midi.percussion.10")),
      LowTom           (45, ResourceBundle.getString("midi.percussion.11")), OpenHiHat        (46, ResourceBundle.getString("midi.percussion.12")), LowMidTom        (47, ResourceBundle.getString("midi.percussion.13")), HiMidTom         (48, ResourceBundle.getString("midi.percussion.14")), CrashCymbal1     (49, ResourceBundle.getString("midi.percussion.15")),
      HighTom          (50, ResourceBundle.getString("midi.percussion.16")), RideCymbal1      (51, ResourceBundle.getString("midi.percussion.17")), ChineseCymbal    (52, ResourceBundle.getString("midi.percussion.18")), RideBell         (53, ResourceBundle.getString("midi.percussion.19")), Tambourine       (54, ResourceBundle.getString("midi.percussion.20")),
      SplashCymbal     (55, ResourceBundle.getString("midi.percussion.21")), Cowbell          (56, ResourceBundle.getString("midi.percussion.22")), CrashCymbal2     (57, ResourceBundle.getString("midi.percussion.23")), Vibraslap        (58, ResourceBundle.getString("midi.percussion.24")), RideCymbal2      (59, ResourceBundle.getString("midi.percussion.25")),
      HiBongo          (60, ResourceBundle.getString("midi.percussion.26")), LowBongo         (61, ResourceBundle.getString("midi.percussion.27")), MuteHiConga      (62, ResourceBundle.getString("midi.percussion.28")), OpenHiConga      (63, ResourceBundle.getString("midi.percussion.29")), LowConga         (64, ResourceBundle.getString("midi.percussion.30")),
      HighTimbale      (65, ResourceBundle.getString("midi.percussion.31")), LowTimbale       (66, ResourceBundle.getString("midi.percussion.32")), HighAgogo        (67, ResourceBundle.getString("midi.percussion.33")), LowAgogo         (68, ResourceBundle.getString("midi.percussion.34")), Cabasa           (69, ResourceBundle.getString("midi.percussion.35")),
      Maracas          (70, ResourceBundle.getString("midi.percussion.36")), ShortWhistle     (71, ResourceBundle.getString("midi.percussion.37")), LongWhistle      (72, ResourceBundle.getString("midi.percussion.38")), ShortGuiro       (73, ResourceBundle.getString("midi.percussion.39")), LongGuiro        (74, ResourceBundle.getString("midi.percussion.40")),
      Claves           (75, ResourceBundle.getString("midi.percussion.41")), HiWoodBlock      (76, ResourceBundle.getString("midi.percussion.42")), LowWoodBlock     (77, ResourceBundle.getString("midi.percussion.43")), MuteCuica        (78, ResourceBundle.getString("midi.percussion.44")), OpenCuica        (79, ResourceBundle.getString("midi.percussion.45")),
      MuteTriangle     (80, ResourceBundle.getString("midi.percussion.46")), OpenTriangle     (81, ResourceBundle.getString("midi.percussion.47"));

      private String text;
      private int    id;
      Percussion(int id, String text) {this.id=id; this.text = text;}
      public int    id()       {return id;}
      public String text()     {return text;}
      @Override
      public String toString() {return "(ordinal: " + ordinal() + (ordinal() < 10 ? " " : "") + ", " + "id: " + id() + ", text: " + text + ")";}
   }

   // midi sound (preset) constants - http://jedi.ks.uiuc.edu/~johns/links/music/gm.htm#Patch
   public enum Sound
   {
      /* pianos               */   AcousticGrand       (ResourceBundle.getString("midi.sound.1"  )), BrightAcoustic      (ResourceBundle.getString("midi.sound.2"  )), ElectricGrand       (ResourceBundle.getString("midi.sound.3"  )), HonkyTonk           (ResourceBundle.getString("midi.sound.4"  )), ElectricPiano1      (ResourceBundle.getString("midi.sound.5"  )), ElectricPiano2      (ResourceBundle.getString("midi.sound.6"  )), Harpsichord         (ResourceBundle.getString("midi.sound.7"  )), Clavinet            (ResourceBundle.getString("midi.sound.8"  )),
      /* chromatic percussion */   Celesta             (ResourceBundle.getString("midi.sound.9"  )), Glockenspiel        (ResourceBundle.getString("midi.sound.10" )), MusicBox            (ResourceBundle.getString("midi.sound.11" )), Vibraphone          (ResourceBundle.getString("midi.sound.12" )), Marimba             (ResourceBundle.getString("midi.sound.13" )), Xylophone           (ResourceBundle.getString("midi.sound.14" )), TubularBells        (ResourceBundle.getString("midi.sound.15" )), Dulcimer            (ResourceBundle.getString("midi.sound.16" )),
      /* organs               */   DrawbarOrgan        (ResourceBundle.getString("midi.sound.17" )), PercussiveOrgan     (ResourceBundle.getString("midi.sound.18" )), RockOrgan           (ResourceBundle.getString("midi.sound.19" )), ChurchOrgan         (ResourceBundle.getString("midi.sound.20" )), ReedOrgan           (ResourceBundle.getString("midi.sound.21" )), Accordian           (ResourceBundle.getString("midi.sound.22" )), Harmonica           (ResourceBundle.getString("midi.sound.23" )), TangoAccordian      (ResourceBundle.getString("midi.sound.24" )),
      /* guitars              */   NylonStringGuitar   (ResourceBundle.getString("midi.sound.25" )), SteelStringGuitar   (ResourceBundle.getString("midi.sound.26" )), ElectricGuitarJazz  (ResourceBundle.getString("midi.sound.27" )), ElectricGuitarClean (ResourceBundle.getString("midi.sound.28" )), ElectricGuitarMuted (ResourceBundle.getString("midi.sound.29" )), OverdrivenGuitar    (ResourceBundle.getString("midi.sound.30" )), DistortionGuitar    (ResourceBundle.getString("midi.sound.31" )), GuitarHarmonics     (ResourceBundle.getString("midi.sound.32" )),
      /* bass                 */   AcousticBass        (ResourceBundle.getString("midi.sound.33" )), ElectricBassFinger  (ResourceBundle.getString("midi.sound.34" )), ElectricBassPick    (ResourceBundle.getString("midi.sound.35" )), FretlessBass        (ResourceBundle.getString("midi.sound.36" )), SlapBass1           (ResourceBundle.getString("midi.sound.37" )), SlapBass2           (ResourceBundle.getString("midi.sound.38" )), SynthBass1          (ResourceBundle.getString("midi.sound.39" )), SynthBass2          (ResourceBundle.getString("midi.sound.40" )),
      /* strings              */   Violin              (ResourceBundle.getString("midi.sound.41" )), Viola               (ResourceBundle.getString("midi.sound.42" )), Cello               (ResourceBundle.getString("midi.sound.43" )), Contrabass          (ResourceBundle.getString("midi.sound.44" )), TremoloStrings      (ResourceBundle.getString("midi.sound.45" )), PizzicatoStrings    (ResourceBundle.getString("midi.sound.46" )), OrchestralStrings   (ResourceBundle.getString("midi.sound.47" )), Timpani             (ResourceBundle.getString("midi.sound.48" )),
      /* ensemble             */   StringEnsemble1     (ResourceBundle.getString("midi.sound.49" )), StringEnsemble2     (ResourceBundle.getString("midi.sound.50" )), SynthStrings1       (ResourceBundle.getString("midi.sound.51" )), SynthStrings2       (ResourceBundle.getString("midi.sound.52" )), ChoirAahs           (ResourceBundle.getString("midi.sound.53" )), VoiceOohs           (ResourceBundle.getString("midi.sound.54" )), SynthVoice          (ResourceBundle.getString("midi.sound.55" )), OrchestraHit        (ResourceBundle.getString("midi.sound.56" )),
      /* brass                */   Trumpet             (ResourceBundle.getString("midi.sound.57" )), Trombone            (ResourceBundle.getString("midi.sound.58" )), Tuba                (ResourceBundle.getString("midi.sound.59" )), MutedTrumpet        (ResourceBundle.getString("midi.sound.60" )), FrenchHorn          (ResourceBundle.getString("midi.sound.61" )), BrassSection        (ResourceBundle.getString("midi.sound.62" )), SynthBrass1         (ResourceBundle.getString("midi.sound.63" )), Synthbrass2         (ResourceBundle.getString("midi.sound.64" )),
      /* reed                 */   SopranoSax          (ResourceBundle.getString("midi.sound.65" )), AltoSax             (ResourceBundle.getString("midi.sound.66" )), TenorSax            (ResourceBundle.getString("midi.sound.67" )), BaritoneSax         (ResourceBundle.getString("midi.sound.68" )), Oboe                (ResourceBundle.getString("midi.sound.69" )), EnglishHorn         (ResourceBundle.getString("midi.sound.70" )), Bassoon             (ResourceBundle.getString("midi.sound.71" )), Clarinet            (ResourceBundle.getString("midi.sound.72" )),
      /* pipe                 */   Piccolo             (ResourceBundle.getString("midi.sound.73" )), Flute               (ResourceBundle.getString("midi.sound.74" )), Recorder            (ResourceBundle.getString("midi.sound.75" )), PanFlute            (ResourceBundle.getString("midi.sound.76" )), BlownBottle         (ResourceBundle.getString("midi.sound.77" )), Skakuhachi          (ResourceBundle.getString("midi.sound.78" )), Whistle             (ResourceBundle.getString("midi.sound.79" )), Ocarina             (ResourceBundle.getString("midi.sound.80" )),
      /* synth lead           */   Lead1Square         (ResourceBundle.getString("midi.sound.81" )), Lead2Sawtooth       (ResourceBundle.getString("midi.sound.82" )), Lead3Calliope       (ResourceBundle.getString("midi.sound.83" )), Lead4Chiff          (ResourceBundle.getString("midi.sound.84" )), Lead5Charang        (ResourceBundle.getString("midi.sound.85" )), Lead6Voice          (ResourceBundle.getString("midi.sound.86" )), Lead7Fifths         (ResourceBundle.getString("midi.sound.87" )), Lead8BassLead       (ResourceBundle.getString("midi.sound.88" )),
      /* synth pad            */   Pad1NewAge          (ResourceBundle.getString("midi.sound.89" )), Pad2Warm            (ResourceBundle.getString("midi.sound.90" )), Pad3Polysynth       (ResourceBundle.getString("midi.sound.91" )), Pad4Choir           (ResourceBundle.getString("midi.sound.92" )), Pad5Bowed           (ResourceBundle.getString("midi.sound.93" )), Pad6Metallic        (ResourceBundle.getString("midi.sound.94" )), Pad7Halo            (ResourceBundle.getString("midi.sound.95" )), Pad8Sweep           (ResourceBundle.getString("midi.sound.96" )),
      /* synth effects        */   Fx1Rain             (ResourceBundle.getString("midi.sound.97" )), Fx2Soundtrack       (ResourceBundle.getString("midi.sound.98" )), Fx3Crystal          (ResourceBundle.getString("midi.sound.99" )), Fx4Atmosphere       (ResourceBundle.getString("midi.sound.100")), Fx5Brightness       (ResourceBundle.getString("midi.sound.101")), Fx6Goblins          (ResourceBundle.getString("midi.sound.102")), Fx7Echoes           (ResourceBundle.getString("midi.sound.103")), Fx8SciFi            (ResourceBundle.getString("midi.sound.104")),
      /* ethnic               */   Sitar               (ResourceBundle.getString("midi.sound.105")), Banjo               (ResourceBundle.getString("midi.sound.106")), Shamisen            (ResourceBundle.getString("midi.sound.107")), Koto                (ResourceBundle.getString("midi.sound.108")), Kalimba             (ResourceBundle.getString("midi.sound.109")), Bagpipe             (ResourceBundle.getString("midi.sound.110")), Fiddle              (ResourceBundle.getString("midi.sound.111")), Shanai              (ResourceBundle.getString("midi.sound.112")),
      /* percussive           */   TinkleBell          (ResourceBundle.getString("midi.sound.113")), Agogo               (ResourceBundle.getString("midi.sound.114")), SteelDrums          (ResourceBundle.getString("midi.sound.115")), Woodblock           (ResourceBundle.getString("midi.sound.116")), TaikoDrum           (ResourceBundle.getString("midi.sound.117")), MelodicTom          (ResourceBundle.getString("midi.sound.118")), SynthDrum           (ResourceBundle.getString("midi.sound.119")), ReverseCymbal       (ResourceBundle.getString("midi.sound.120")),
      /* sound effects        */   GuitarFretNoise     (ResourceBundle.getString("midi.sound.121")), BreathNoise         (ResourceBundle.getString("midi.sound.122")), Seashore            (ResourceBundle.getString("midi.sound.123")), BirdTweet           (ResourceBundle.getString("midi.sound.124")), TelephoneRing       (ResourceBundle.getString("midi.sound.125")), Helicopter          (ResourceBundle.getString("midi.sound.126")), Applause            (ResourceBundle.getString("midi.sound.127")), Gunshot             (ResourceBundle.getString("midi.sound.128"));

      private String text;
      Sound(String text)       {this.text = text;}
      public String text()     {return text;}
      @Override
      public String toString() {return text + " (" + ordinal() + ")";}
   }

   // class members
    public static final int MIN_CHANNEL_VOLUME                = 0;                // minimum volume level for a MIDI channel
    public static final int MAX_CHANNEL_VOLUME                = 0x7f;             // maximum volume level for a MIDI channel

    public static final int MIN_CHANNEL_EFFECT_LEVEL          = 0;                // miniumum level for channel effects (chorus, reverb, etc.)
    public static final int MAX_CHANNEL_EFFECT_LEVEL          = 0x7f;             // maximum  level for channel effects (chorus, reverb, etc.)

    // see http://www.midi.org/about-midi/table3.shtml for a complete list of control changes
    public static final int CONTROL_CHANGE_CHORUS             = 0x5d;             // 93 - chorus
    public static final int CONTROL_CHANGE_PAN                = 0x0a;             // 10 - pan
    public static final int CONTROL_CHANGE_PHASER             = 0x5f;             // 95 - phaser
    public static final int CONTROL_CHANGE_REVERB             = 0x5b;             // 91 - reverb
    public static final int CONTROL_CHANGE_SUSTAIN            = 0x40;             // 64 - sustain
    public static final int CONTROL_CHANGE_TREMOLO            = 0x5c;             // 92 - tremolo
    public static final int CONTROL_CHANGE_VOLUME             = 0x07;             //  7 - volume


    public static final int EVENT_END_OF_TRACK                = 47;               // java midi end of track event

    public static final int NUM_MIDI_CHANNELS_PER_PORT        = 16;
    public static final int NUM_MIDI_NOTES                    = 128;

    public static final int PPQN                              = Duration.NOTE_QUARTER.pulses();       // amount of pulses (midi time) per quarter note

    public static final Channel Drum_Channel                  = Channel.Channel_10;                   // midi channel on which drums are played back
    public static final int NUM_MIDI_DRUMS                    = 47;                                   // number of midi drums


    private static Hashtable<Integer, Duration> beatAmountMap = new Hashtable<Integer, Duration>();   // map of time signature beat amounts to midi durations

    static
    {
       beatAmountMap.put(1 , Duration.NOTE_WHOLE  );
       beatAmountMap.put(2 , Duration.NOTE_HALF   );
       beatAmountMap.put(4 , Duration.NOTE_QUARTER);
       beatAmountMap.put(8 , Duration.NOTE_8TH    );
       beatAmountMap.put(16, Duration.NOTE_16TH   );
       beatAmountMap.put(32, Duration.NOTE_32ND   );
    }






   /**
    * create a midi event to set the amount of chorus.
    * <br/><br/>
    * @param cumulativeTime  the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param channel         the midi channel on which the event will be sent.
    * @param chorus          the amount of chorus.
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventChorus(long cumulativeTime, Channel channel, byte chorus) throws InvalidMidiDataException
   {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.CONTROL_CHANGE, channel.ordinal(), CONTROL_CHANGE_CHORUS, chorus);
      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * create a midi event to stop playing a note.
    * <br/><br/>
    * @param cumulativeTime  the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param channel         the midi channel on which the event will be sent.
    * @param note            the pitch of the note.
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventNoteOff(long cumulativeTime, Channel channel, Note note) throws InvalidMidiDataException
   {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_OFF, channel.ordinal(), note.pitch(), 0);
      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * create a midi event to play a note.
    * <br/><br/>
    * @param cumulativeTime  the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param channel         the midi channel on which the event will be sent.
    * @param note            the pitch    of the note.
    * @param volume          the loadness of the note.
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventNoteOn(long cumulativeTime, Channel channel, Note note, Volume.Level volume) throws InvalidMidiDataException
   {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_ON, channel.ordinal(), note.pitch(), volume.value());
      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * create a midi event to set the panning within the stero space.
    * <br/><br/>
    * @param cumulativeTime the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param channel        the midi channel on which the event will be sent.
    * @param pan            the pan setting (0 == left, 64 = center, 127 == right).
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventPan(long cumulativeTime, Channel channel, byte pan) throws InvalidMidiDataException
   {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.CONTROL_CHANGE, channel.ordinal(), CONTROL_CHANGE_PAN, pan);
      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * create a midi event to switch to the new instrument (sound patch\program).
    * <br/><br/>
    * @param cumulativeTime the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param channel        the midi channel on which the event will be sent.
    * @param sound          the instrument (sound patch) to switch to.
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventProgramChange(long cumulativeTime, Channel channel, Sound sound) throws InvalidMidiDataException
   {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.PROGRAM_CHANGE, channel.ordinal(), sound.ordinal(), 0);
      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * create a midi event to set the amount of reverb.
    * <br/><br/>
    * @param cumulativeTime the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param channel        the midi channel on which the event will be sent.
    * @param reverb         the amount of reverb.
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventReverb(long cumulativeTime, Channel channel, byte reverb) throws InvalidMidiDataException
   {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.CONTROL_CHANGE, channel.ordinal(), CONTROL_CHANGE_REVERB, reverb);
      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * create a midi event to change the tempo.
    * <p>
    * midi meta messages use the last three bytes to specify the number of 탎 per beat.
    * thus, to convert the tempo from beats per minute (bpm), we use the following formula:
    *
    *   탎    1 minute      60 seconds   1,000,000 탎     60000000 탎
    *  ---- = ----------- � ---------- � ------------  =  -----------
    *  beat   tempo beats    1 minute      1 second       tempo beats
    *
    * ex: 160 bpm
    *
    *     1 minute    60 seconds   1,000,000 탎
    *     --------- � ---------- � ------------ = 375,000 탎\beat = 0x05 b8 d8
    *     160 beats    1 minute      1 second
    *
    *
    * ex: 120 bpm
    *
    *     60000000 탎
    *     ----------- = 500,000 탎\beat = 0x07 a1 20
    *      120 beats
    * </p>
    * <br/><br/>
    * @param cumulativeTime   the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param bpmTempo         the new tempo in beats per minute.
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventTempoChange(long cumulativeTime, int bpmTempo) throws InvalidMidiDataException
   {
      int         us_beat = 60000000 / bpmTempo;
      byte[]      data    = {(byte)((us_beat & 0xff0000) >> 16), (byte)((us_beat & 0x00ff00) >> 8), (byte)(us_beat & 0x0000ff)};
      MetaMessage message = new MetaMessage();
      message.setMessage(0x51, data, data.length);

      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * create a midi event to set the volume.
    * <br/><br/>
    * @param cumulativeTime  the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param channel         the midi channel on which the event will be sent.
    * @param volume          the volume level.
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventVolume(long cumulativeTime, Channel channel, byte volume) throws InvalidMidiDataException
   {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.CONTROL_CHANGE, channel.ordinal(), CONTROL_CHANGE_VOLUME, volume);
      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * creates a pitch bend event.
    * <br/><br/>
    * @param cumulativeTime  the previous midi event's time value plus the previous midi event's duration (delta time value).
    * @param channel         the midi channel on which the event will be sent.
    * @param pitch           the volume level.
    * Pitch bend has two data bytes ,
    * LSB followed by MSB
    * <br/><br/>
    * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
    */
   public static MidiEvent createEventPitchBend(long cumulativeTime, Channel channel, int pitch) throws InvalidMidiDataException
   {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.PITCH_BEND, channel.ordinal(),pitch & 0x7F,pitch >>7);
      return new MidiEvent(message, cumulativeTime);
   }

   /**
    * @param channel  the integer midi channel.
    * <br/><br/>
    * @return the midi channel enum corresponding to the integer midi channel.
    */
   public static Channel getChannel(int channel)
   {
      for (Channel ch : Channel.values())
         if (channel == ch.ordinal())
            return ch;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.midi.channel"), channel, Channel.Channel_16.ordinal()));
   }

   /**
    * @return the specified midi device if it is installed on the computer and null otherwise.
    * <br/><br/>
    * @param name          name of the midi device.
    * @param vendor        name of the company who provides the device.
    * @param description   a description of the device.
    * @param version       version information for the device.
    */
   public static MidiDevice getDevice(String name, String vendor, String description, String version)
   {
      MidiDevice        midiDevice      = null;
      MidiDevice.Info[] midiDeviceInfos = MidiSystem.getMidiDeviceInfo();

      for (MidiDevice.Info midiDeviceInfo : midiDeviceInfos)
      {
         try
         {
            if (midiDeviceInfo.getName().equals(name) && midiDeviceInfo.getVendor().equals(vendor) && midiDeviceInfo.getDescription().equals(description) && midiDeviceInfo.getVersion().equals(version))
            {
               midiDevice = MidiSystem.getMidiDevice(midiDeviceInfo);
               break;
            }
         }
         catch (MidiUnavailableException ex)
         {
            // if the device is installed but unavailable, then don't return it.
         }
      }
       return midiDevice;
   }

   /**
    * @param duration   the integer duration.
    * <br/><br/>
    * @return the midi duration enum corresponding to the integer duration.
    */
   public static Duration getDuration(int duration)
   {
      for (Duration d : Duration.values())
         if (duration == d.ordinal())
            return d;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.midi.duration"), duration, Duration.NOTE_WHOLE.ordinal()));
   }

   /**
    * @return the number of midi pulses that occur during a measure with the specified time signature (bpm / ba).
    * <br/><br/>
    * @param beatsPerMeasure   the number of beats per measure - the numerator   in a time signature.
    * @param beatAmount        the amount each beat receives   - the denominator in a time signature.
    */
   public static long getDuration(int beatsPerMeasure, int beatAmount)
   {
	   Duration duration = beatAmountMap.get(beatAmount);
	   if (duration == null)
        throw new IllegalArgumentException(ResourceBundle.getString("error.invalid.time_signature"));

	   return (long)beatsPerMeasure * (long)duration.pulses();
   }

   /**
    * @return a list of the midi devices installed on the computer that are capable of receiving midi messages.
    */
   public static List<MidiDevice.Info> getInstalledReceivers()
   {
      ArrayList<MidiDevice.Info> receiverInfos = new ArrayList<MidiDevice.Info>();

      MidiDevice.Info[] midiDeviceInfos = MidiSystem.getMidiDeviceInfo();
      for (MidiDevice.Info midiDeviceInfo : midiDeviceInfos)
      {
         try
         {
            MidiDevice device = MidiSystem.getMidiDevice(midiDeviceInfo);
            if (device.getReceiver() != null)
                receiverInfos.add(midiDeviceInfo);
         }
         catch (MidiUnavailableException ex)
         {
            // if the device is unavailable, don't include it in the list
         }
      }
      return receiverInfos;
   }

   /**
    * @return a list of the synthesizers installed on the computer.
    */
   public static List<MidiDevice.Info> getInstalledSynthesizers()
   {
      ArrayList<MidiDevice.Info> synthesizerInfos = new ArrayList<MidiDevice.Info>();

      MidiDevice.Info[] midiDeviceInfos = MidiSystem.getMidiDeviceInfo();
      for (MidiDevice.Info midiDeviceInfo : midiDeviceInfos)
      {
         try
         {
            MidiDevice device = MidiSystem.getMidiDevice(midiDeviceInfo);
            if (device instanceof Synthesizer)
                synthesizerInfos.add(midiDeviceInfo);
         }
         catch (MidiUnavailableException ex)
         {
            // if the device is unavailable, don't include it in the list
         }
      }
      return synthesizerInfos;
   }

   /**
    * @param note  the integer midi note.
    * <br/><br/>
    * @return the midi note enum corresponding to the integer midi note.
    */
   public static Note getNote(int note)
   {
      for (Note n : Note.values())
         if (note == n.ordinal())
            return n;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.midi.note"), note, Note.G9.ordinal()));
   }

   /**
    * @return the midi note enum corresponding to the integer midi note pitch.
    * <br/><br/>
    * @param pitch      the integer midi note pitch.
    * @param useSharps  if two notes have the same value (like G# and A flat), whether to use the sharp version or the flat version.
    */
   public static Note getNote(int pitch, boolean useSharps)
   {
      ArrayList<Note> notes = new ArrayList<Note>();
      Note            note  = null;

      for (Note n : Note.values())
         if (pitch == n.pitch() && n.ordinal() != Note.Middle_C.ordinal())
            notes.add(n);

      if (notes.size() == 0)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.midi.note"), pitch, Note.G9.ordinal()));

      // if only one matching note was found, return it
      if (notes.size() == 1)
         note = notes.get(0);
      // otherwise, return the one which matches the specified sharp\flat parameter
      else if (notes.size() == 2)
         note = notes.get(useSharps ? 0 : 1);

      return note;
   }

   /**
    * @return the midi note enum(s) corresponding to the integer midi note pitch.
    * <br/><br/>
    * @param pitch      the integer midi note pitch.
    */
   public static ArrayList<Note> getNotes(int pitch)
   {
      ArrayList<Note> notes = new ArrayList<Note>();

      for (Note n : Note.values())
         if (pitch == n.pitch() && n.ordinal() != Note.Middle_C.ordinal())
            notes.add(n);

      if (notes.size() == 0)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.midi.note"), pitch, Note.G9.ordinal()));

      return notes;
   }

   /**
    * @param string      the string on which the note is played.
    * @param fret        the fret at which the note is played.
    * @param tuning      the tuning used for the instrument on which the note is played.
    * @param capo        the capo, if any, used by the instrument.
    * @param usesSharps  whether to use sharps or flats in resolving two notes that have the same pitch (ex: F# and Gb)
    * <br/><br/>
    * @return the midi note enum corresponding to the location on the fret board, or null if the string is not used.
    */
   public static Note getNote(Instrument.String string, Instrument.Fret fret, Tuning tuning, Instrument.Fret capo, boolean usesSharps)
   {
      Note note = null;

      if (fret != Instrument.Fret.Not_Used)
      {
         // start the note at the open string on which the note is played
         note = tuning.getNotes()[string.ordinal()];

         // if the instrument uses a capo and the string is played open (not fretted), then  the capo
         if (capo != Instrument.Fret.Not_Used && fret == Instrument.Fret.Open)
            note = getNote(note.pitch() + capo.ordinal(), usesSharps);
         else
         // otherwise, add the fret to the string's open pitch to get the note's pitch.
            note = getNote(note.pitch() + fret.ordinal(), usesSharps);
      }
		return note;
   }

   /**
    * @param percussion  the integer midi percussion.
    * <br/><br/>
    * @return the midi percussion enum corresponding to the integer midi percussion.
    */
   public static Percussion getPercussion(int percussion)
   {
      for (Percussion p : Percussion.values())
         if (percussion == p.id())
            return p;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.midi.percussion"), percussion, Percussion.OpenTriangle.ordinal()));
   }

   /**
    * @return the default midi sequencer.
    * <br/><br/>
    * @throws MidiUnavailableException  if there is no default midi sequencer for this computer or if it can not be retrieved.
    */
   public static Sequencer getSequencer() throws MidiUnavailableException
   {
      Sequencer sequencer = MidiSystem.getSequencer();
      if (sequencer == null)
         throw new MidiUnavailableException(ResourceBundle.getString("error.midi.unable_to_obtain_default_sequencer"));

      return sequencer;
   }

   /**
    * @param sound  the integer midi sound.
    * <br/><br/>
    * @return the midi sound enum corresponding to the integer midi sound.
    */
   public static Sound getSound(int sound)
   {
      for (Sound s : Sound.values())
         if (sound == s.ordinal())
            return s;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.midi.sound"), sound, Sound.Gunshot.ordinal()));
   }

   /**
    * @return the volume corresponding to the midi volume.
    * <br/><br/>
    * @param midiVolume   the midi volume level.
    */
   private static Volume.Level getVolume(int midiVolume)
   {
      for (Volume.Level v : Volume.Level.values())
         if (midiVolume == v.value())
            return v;

      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.volume.level"), midiVolume, Volume.Level.Fff.value()));
   }

   /**
    * @return whether the specified midi device is installed on the computer.
    * <br/><br/>
    * @param name          name of the midi device.
    * @param vendor        name of the company who provides the device.
    * @param description   a description of the device.
    * @param version       version information for the device.
    */
   public static boolean isDeviceInstalled(String name, String vendor, String description, String version)
   {
      boolean deviceInstalled = false;

      for (MidiDevice.Info midiDeviceInfo : MidiSystem.getMidiDeviceInfo())
      {
         if (midiDeviceInfo.getName().equals(name) && midiDeviceInfo.getVendor().equals(vendor) && midiDeviceInfo.getDescription().equals(description) && midiDeviceInfo.getVersion().equals(version))
         {
            deviceInstalled = true;
            break;
         }
      }
      return deviceInstalled;
   }

   /**
    * @return a string representation of the midi sequence.
    * <br/><br/>
    * @param sequence  midi sequence to be converted to a string.
    */
   public static String toString(Sequence sequence)
   {
      StringBuffer buffer      = new StringBuffer();
      int          trackNumber = 0;


      buffer.append("sequence: " + sequence.getTracks().length + " tracks\n");
      for(Track track : sequence.getTracks())
      {
         buffer.append("   track " + (trackNumber) + ": " + track.size() + " midi events\n");

         for(int eventNumber=0; eventNumber<track.size(); ++eventNumber)
         {
            MidiEvent       midiEvent   = track.get(eventNumber);
            MidiMessage     midiMessage = midiEvent.getMessage();
            long            midiTime    = midiEvent.getTick();
            byte[]          data        = midiMessage.getMessage();
            int             status      = midiMessage.getStatus();
            int             type        = 0;
            int             pitch       = 0;
            ArrayList<Note> notes       = null;

            switch (status)
            {
               case MetaMessage.META:
                    MetaMessage metaMessage = (MetaMessage)midiMessage;

                    type = metaMessage.getType();
                    data = metaMessage.getData();

                    buffer.append("      " + eventNumber + ": meta message\n");
                    buffer.append("         time...: " + midiTime + "\n");

                    switch (type)
                    {
                       case 81:
                            if (data[0] == 0x03)
                            {
                               int time     = ((int)data[0] << 16) + ((int)data[1] << 8) + (int)data[2];
                               int bpmTempo = 60000000 / time;
                               buffer.append("         type...: tempo change\n");
                               buffer.append("         tempo..: " + bpmTempo + "\n");
                            }
                            else
                            {
                               buffer.append("         type...: unknown\n");
                            }
                       break;
                       default:
                            buffer.append("         type...: unknown\n");
                       break;
                    }
               break;
               case SysexMessage.SYSTEM_EXCLUSIVE:
               case SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE:
                    SysexMessage sysexMessage = (SysexMessage)midiMessage;

                    buffer.append("      " + eventNumber + ": sys ex message\n");
                    buffer.append("         time...: " + midiTime + "\n");
                    buffer.append("         type...: not working yet: todo: write code\n");
               break;
               default:
                    ShortMessage shortMessage = (ShortMessage)midiMessage;

                    buffer.append("      " + eventNumber + ": short message\n");
                    buffer.append("         time...: " + midiTime + "\n");

                    switch (status)
                    {
                       case ShortMessage.ACTIVE_SENSING:
                       case ShortMessage.CONTINUE:
                       case ShortMessage.END_OF_EXCLUSIVE:
                       case ShortMessage.MIDI_TIME_CODE:
                       case ShortMessage.SONG_POSITION_POINTER:
                       case ShortMessage.SONG_SELECT:
                       case ShortMessage.START:
                       case ShortMessage.STOP:
                       case ShortMessage.SYSTEM_RESET:
                       case ShortMessage.TIMING_CLOCK:
                       case ShortMessage.TUNE_REQUEST:
                            buffer.append("         data...: ");
                            for(int k=0; k<data.length; ++k)
                               buffer.append(Utility.hex(data[k]) + "  ");
                            buffer.append("\n");
                       break;
                       default:   // channel messages
                            Midi.Channel channel  = Midi.getChannel((shortMessage.getStatus() & 0x0F));

                            type = shortMessage.getStatus() & 0xF0;

                            buffer.append("         channel: " + channel  + "\n");

                            switch (type)
                            {
                               case ShortMessage.CHANNEL_PRESSURE:
                                    buffer.append("         type...: channel pressure\n");
                               break;
                               case ShortMessage.CONTROL_CHANGE:
                                    buffer.append("         type...: control change\n");
                                    buffer.append("         data 1.: ");
                                    switch (shortMessage.getData1())
                                    {
                                       case CONTROL_CHANGE_CHORUS:
                                            buffer.append("chorus");
                                       break;
                                       case CONTROL_CHANGE_PAN:
                                            buffer.append("pan");
                                       break;
                                       case CONTROL_CHANGE_REVERB:
                                            buffer.append("reverb");
                                       break;
                                       case CONTROL_CHANGE_SUSTAIN:
                                            buffer.append("sustain");
                                       break;
                                       case CONTROL_CHANGE_VOLUME:
                                            buffer.append("volume");
                                       break;
                                       default:
                                            buffer.append("unknown - " + shortMessage.getData1());
                                       break;
                                    }
                                    buffer.append("\n");
                                    buffer.append("         data 2.: " + shortMessage.getData2() + "\n");
                               break;
                               case ShortMessage.NOTE_ON:
                                    pitch = data[1];
                                    notes = getNotes(pitch);

                                    buffer.append("         type...: note on\n");
                                    buffer.append("         pitch..: " + pitch + " = {" + notes.get(0) + (notes.size() == 1 ? "" : ", " + notes.get(1)) + "}\n");
                                    buffer.append("         volume.: " + getVolume   (data[2]) + "\n");
                               break;
                               case ShortMessage.NOTE_OFF:
                                    pitch = data[1];
                                    notes = getNotes(pitch);

                                    buffer.append("         type...: note off\n");
                                    buffer.append("         pitch..: " + pitch + " = {" + notes.get(0) + (notes.size() == 1 ? "" : ", " + notes.get(1)) + "}\n");
                               break;
                               case ShortMessage.PITCH_BEND:
                        	    pitch =(data[1] & 0x7F) | ((data[2] & 0x7F) << 7);
                                    buffer.append("         type...: pitch bend\n");
                                    buffer.append("         pitch wheel change..: " + pitch +"\n");
                               break;
                               case ShortMessage.POLY_PRESSURE:
                                    buffer.append("         type...: polyphonic key pressure\n");
                               break;
                               case ShortMessage.PROGRAM_CHANGE:
                                    buffer.append("         type...: program change\n");
                                    buffer.append("         preset.: " + Midi.getSound(data[1]) + "\n");
                               break;
                               default:
                                    buffer.append("         type...: unknown\n");
                               break;
                            }
                       break;
                    }
               break;
            }
         }
         trackNumber++;
      }
      return buffer.toString();
   }
}
