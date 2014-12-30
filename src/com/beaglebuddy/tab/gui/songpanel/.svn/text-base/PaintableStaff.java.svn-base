/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: Andy Will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import com.beaglebuddy.tab.gui.font.FontManager;
import com.beaglebuddy.tab.gui.font.MusicNotation;
import com.beaglebuddy.tab.gui.mainframe.CursorMap;
import com.beaglebuddy.tab.model.Accidental;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Beam;
import com.beaglebuddy.tab.model.Chord;
import com.beaglebuddy.tab.model.KeySignature;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Note;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.TimeSignature;
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.Volume;
import com.beaglebuddy.tab.model.attribute.chord.ChordAttribute;
import com.beaglebuddy.tab.model.attribute.chord.ChordName;
import com.beaglebuddy.tab.model.instrument.ActiveInstruments;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.staff.Staff;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.LineMetrics;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;



/**
 * paintable version of the staff object.
 * <p>
 * </p>
 */
public class PaintableStaff extends Staff
{
   public  static final int    PIXELS_PER_STANDARD_SPACE     = 6;                                                // number of pixels between standard staff lines
   public  static final int    PIXELS_PER_TAB_LINE           = 9;                                                // number of pixels between standard tab lines
   public  static final int    NUM_STANDARD_LINES            = 5;                                                // number of lines comprising a standard staff
   public  static final int    NUM_STANDARD_SPACES           = NUM_STANDARD_LINES - 1;                           // number of spaced within a standard staff
   public  static final int    STANDARD_STAFF_HEIGHT         = NUM_STANDARD_SPACES * PIXELS_PER_STANDARD_SPACE;  // standard staff height
   private static final double INTERVALIC_DISTANCE_THRESHOLD = STANDARD_STAFF_HEIGHT / 2.0;                      // for beamed notes, this is the minimum distance required between min and max noteheads to relax the stem length rules.
   private static final double STEM_LENGTH                   = (7.0 / 2.0) * PIXELS_PER_STANDARD_SPACE;          // minimum length of a stem
   private static final double STAFF_MIDDLE_Y                = STANDARD_STAFF_HEIGHT/2.0;                        // number of pixels from start to middle of the standard staff
   private static final int    EXTRA_SPACES_ABOVE            = 2;                                                // number of extra space above the staff, in staff line spacing
   private static final int    EXTRA_SPACES_BELOW            = 1;                                                // number of extra space below the staff, in staff line spacing
   public  static final int    DOUBLE_BAR_WIDTH              = 2;                                                // width of a double bar, in pixels
   public  static final int    CLEF_WIDTH                    = 24;                                               // width of a clef symbol, in pixels

   // y offset (in pixels) of flats for a key signature, in the order they appear in a bass clef staff.
   private static final double[] BASS_FLATS =
      new double[] { 3.5*PIXELS_PER_STANDARD_SPACE,
                     2.0*PIXELS_PER_STANDARD_SPACE,
                     4.0*PIXELS_PER_STANDARD_SPACE,
                     2.5*PIXELS_PER_STANDARD_SPACE,
                     4.5*PIXELS_PER_STANDARD_SPACE,
                     3.0*PIXELS_PER_STANDARD_SPACE,
                     5.0*PIXELS_PER_STANDARD_SPACE };

   // y offset (in pixels) of flats for a key signature, in the order they appear in a treble clef staff.
   private static final double[] TREBLE_FLATS =
      new double[] { 2.5*PIXELS_PER_STANDARD_SPACE,
                     1.0*PIXELS_PER_STANDARD_SPACE,
                     3.0*PIXELS_PER_STANDARD_SPACE,
                     1.5*PIXELS_PER_STANDARD_SPACE,
                     3.5*PIXELS_PER_STANDARD_SPACE,
                     2.0*PIXELS_PER_STANDARD_SPACE,
                     4.0*PIXELS_PER_STANDARD_SPACE };

   // y offset (in pixels) of sharps for a key signature, in the order they appear in a bass clef staff.
   private static final double[] BASS_SHARPS =
      new double[] { 1.5*PIXELS_PER_STANDARD_SPACE,
                     3.0*PIXELS_PER_STANDARD_SPACE,
                     1.0*PIXELS_PER_STANDARD_SPACE,
                     2.5*PIXELS_PER_STANDARD_SPACE,
                     0.5*PIXELS_PER_STANDARD_SPACE,
                     2.0*PIXELS_PER_STANDARD_SPACE,
                     0.0*PIXELS_PER_STANDARD_SPACE };

   // y offset (in pixels) of sharps for a key signature, in the order they appear in a treble clef staff.
   private static final double[] TREBLE_SHARPS =
      new double[] { 0.5*PIXELS_PER_STANDARD_SPACE,
                     2.0*PIXELS_PER_STANDARD_SPACE,
                     0.0*PIXELS_PER_STANDARD_SPACE,
                     1.5*PIXELS_PER_STANDARD_SPACE,
                     3.0*PIXELS_PER_STANDARD_SPACE,
                     1.0*PIXELS_PER_STANDARD_SPACE,
                     2.5*PIXELS_PER_STANDARD_SPACE };

   // maps from accidental enum to symbol
   private static final Map<Accidental, MusicNotation> ACCIDENTAL_SYMBOLS = new Hashtable<Accidental, MusicNotation>();

   static
   {
      ACCIDENTAL_SYMBOLS.put(Accidental.Natural, MusicNotation.ACCIDENTAL_NATURAL);
      ACCIDENTAL_SYMBOLS.put(Accidental.Flat,    MusicNotation.ACCIDENTAL_FLAT);
      ACCIDENTAL_SYMBOLS.put(Accidental.Sharp,   MusicNotation.ACCIDENTAL_SHARP);
   }

   // maps from a clef type to a Unicode symbol
   private static final Map<Clef, MusicNotation> CLEF_SYMBOLS = new Hashtable<Clef, MusicNotation>();

   static
   {
      CLEF_SYMBOLS.put(Clef.Bass  , MusicNotation.CLEF_BASS);
      CLEF_SYMBOLS.put(Clef.Drum  , MusicNotation.CLEF_DRUM);
      CLEF_SYMBOLS.put(Clef.Treble, MusicNotation.CLEF_TREBLE);
   }

   // maps from single-digit integers to MusicNotation class
   private static final Map<String, MusicNotation> TIME_SIGNATURE_SYMBOLS = new Hashtable<String, MusicNotation>();

   static
   {
      TIME_SIGNATURE_SYMBOLS.put("0", MusicNotation.TIME_SIGNATURE_0);
      TIME_SIGNATURE_SYMBOLS.put("1", MusicNotation.TIME_SIGNATURE_1);
      TIME_SIGNATURE_SYMBOLS.put("2", MusicNotation.TIME_SIGNATURE_2);
      TIME_SIGNATURE_SYMBOLS.put("3", MusicNotation.TIME_SIGNATURE_3);
      TIME_SIGNATURE_SYMBOLS.put("4", MusicNotation.TIME_SIGNATURE_4);
      TIME_SIGNATURE_SYMBOLS.put("5", MusicNotation.TIME_SIGNATURE_5);
      TIME_SIGNATURE_SYMBOLS.put("6", MusicNotation.TIME_SIGNATURE_6);
      TIME_SIGNATURE_SYMBOLS.put("7", MusicNotation.TIME_SIGNATURE_7);
      TIME_SIGNATURE_SYMBOLS.put("8", MusicNotation.TIME_SIGNATURE_8);
      TIME_SIGNATURE_SYMBOLS.put("9", MusicNotation.TIME_SIGNATURE_9);
   }

   // maps from duration to rest symbol
   private static final Map<Midi.Duration, MusicNotation> REST_SYMBOLS = new Hashtable<Midi.Duration, MusicNotation>();

   static
   {
      REST_SYMBOLS.put(Midi.Duration.NOTE_64TH,    MusicNotation.REST_64TH);
      REST_SYMBOLS.put(Midi.Duration.NOTE_32ND,    MusicNotation.REST_32ND);
      REST_SYMBOLS.put(Midi.Duration.NOTE_16TH,    MusicNotation.REST_16TH);
      REST_SYMBOLS.put(Midi.Duration.NOTE_8TH,     MusicNotation.REST_8TH);
      REST_SYMBOLS.put(Midi.Duration.NOTE_QUARTER, MusicNotation.REST_QUARTER);
      REST_SYMBOLS.put(Midi.Duration.NOTE_HALF,    MusicNotation.REST_HALF);
      REST_SYMBOLS.put(Midi.Duration.NOTE_WHOLE,   MusicNotation.REST_WHOLE);
   }

   // maps from duration to note head symbol
   private static final Map<Midi.Duration, MusicNotation> NOTE_SYMBOLS = new Hashtable<Midi.Duration, MusicNotation>();

   static
   {
      NOTE_SYMBOLS.put(Midi.Duration.NOTE_64TH,    MusicNotation.NOTE_NO_STEM);
      NOTE_SYMBOLS.put(Midi.Duration.NOTE_32ND,    MusicNotation.NOTE_NO_STEM);
      NOTE_SYMBOLS.put(Midi.Duration.NOTE_16TH,    MusicNotation.NOTE_NO_STEM);
      NOTE_SYMBOLS.put(Midi.Duration.NOTE_8TH,     MusicNotation.NOTE_NO_STEM);
      NOTE_SYMBOLS.put(Midi.Duration.NOTE_QUARTER, MusicNotation.NOTE_NO_STEM);
      NOTE_SYMBOLS.put(Midi.Duration.NOTE_HALF,    MusicNotation.NOTE_HALF);
      NOTE_SYMBOLS.put(Midi.Duration.NOTE_WHOLE,   MusicNotation.NOTE_WHOLE);
   }

   // maps from duration to flag symbol
   private static final Map<Midi.Duration, MusicNotation> FLAG_DOWN_SYMBOLS = new Hashtable<Midi.Duration, MusicNotation>();

   static
   {
      FLAG_DOWN_SYMBOLS.put(Midi.Duration.NOTE_64TH,   MusicNotation.FLAG_DOWN_64TH);
      FLAG_DOWN_SYMBOLS.put(Midi.Duration.NOTE_32ND,   MusicNotation.FLAG_DOWN_32ND);
      FLAG_DOWN_SYMBOLS.put(Midi.Duration.NOTE_16TH,   MusicNotation.FLAG_DOWN_16TH);
      FLAG_DOWN_SYMBOLS.put(Midi.Duration.NOTE_8TH,    MusicNotation.FLAG_DOWN_8TH);
   }

   /** Maps from duration to inverted flag symbol. */
   private static final Map<Midi.Duration, MusicNotation> FLAG_UP_SYMBOLS = new Hashtable<Midi.Duration, MusicNotation>();

   static
   {
      FLAG_UP_SYMBOLS.put(Midi.Duration.NOTE_64TH,   MusicNotation.FLAG_UP_64TH);
      FLAG_UP_SYMBOLS.put(Midi.Duration.NOTE_32ND,   MusicNotation.FLAG_UP_32ND);
      FLAG_UP_SYMBOLS.put(Midi.Duration.NOTE_16TH,   MusicNotation.FLAG_UP_16TH);
      FLAG_UP_SYMBOLS.put(Midi.Duration.NOTE_8TH,    MusicNotation.FLAG_UP_8TH);
   }

   private  final  PaintableSection         parentPaintableSection;    // the section that this staff belongs to.
   private  final  int                      staffIndex;                // this staff's index (zero based).
   private         double                   firstBarlineX;             // the x coordinate of the first measure's barline.
   protected       double                   firstY;                    // the y coordinate of the first standard staff line, relative to start of the staff.
   protected       double                   lastY;                     // the y coordinate of the last tab staff line, relative to start of the staff.
   private   final Map<Instrument, Integer> instrumentIns;             // the instrument-ins for this staff, map from instrument to barline index.
   private   final Tuning                   tuning;                    // the tuning for all instruments on this staff.
 //private   final List<List<Chord>>        chordLists;                // the list of chords for each voice in this staff.
   private   final Midi.Note                baseNote;                  // the note that serves as the basis for the clef.
   private   final double                   baseY;                     // the base note's y coordinate, relative to the first line of the staff.
   private         int                      pixelsAboveStaff;          // number of vertical pixels drawn above the staff.
   private         int                      pixelsBelowStaff;          // number of vertical pixels drawn below the staff.





   /**
    * Construct a new paintable staff object.
	 * <br/><br/>
    * @param parent         section that this staff belongs to
    * @param staff          the corresponding model staff
    * @param staffIndex     the index of the staff relative to the parent section
    * @param instrumentIns  list of instrument-ins on this staff
    * @param tuning         the tuning that is active for this staff
    */
   public PaintableStaff(PaintableSection parent, Staff staff, int staffIndex, Map<Instrument, Integer> instrumentIns, Tuning tuning)
   {
      super(staff.getClef(), staff.getNumTabLines(),
            staff.getStandardNotationStaffAboveSpacing(),
            staff.getStandardNotationStaffBelowSpacing(),
            staff.getSymbolSpacing(), staff.getTabStaffBelowSpacing(),
            staff.getChords(Staff.LowVoice).toArray(new Chord[0]),
            staff.getChords(Staff.HighVoice).toArray(new Chord[0]));

      this.parentPaintableSection = parent;
      this.staffIndex = staffIndex;

      // save the instrument-ins and the tuning.
      this.instrumentIns = instrumentIns;
      this.tuning = tuning;

      // clear out the old ledger line information.
      initExtraSpacing();

      // determine reference point for this clef.
      if (getClef() == Staff.Clef.Treble)
      {
         // Treble staff (G clef) is based off the G above middle C.
         this.baseNote = Midi.Note.G4;

         // Its first line is the third (zero indexed).
         this.baseY = 3.0*PIXELS_PER_STANDARD_SPACE;
      }
      else if (getClef() == Staff.Clef.Bass)
      {
         // Bass staff (F clef) is based off the F below middle C.
         this.baseNote = Midi.Note.F3;

         // Its first line is the first (zero indexed).
         this.baseY = 1.0*PIXELS_PER_STANDARD_SPACE;
      }
      else
      {
         // No definition for other non-standard clefs.
         this.baseNote = null;
         this.baseY = 0.0;
      }
   }

   /**
    * sets the extra spacing above and below the standard music staff.
    */
   private void initExtraSpacing()
   {
      setStandardNotationStaffAboveSpacing((short)(EXTRA_SPACES_ABOVE * PIXELS_PER_STANDARD_SPACE + pixelsAboveStaff));
      setStandardNotationStaffBelowSpacing((short)(EXTRA_SPACES_BELOW * PIXELS_PER_STANDARD_SPACE + pixelsBelowStaff));
   }

   /**
    * Given the given min and max y coordinates that were used for drawing the staff, updates the staff's extra pixel spacing.
    * The coordinates are relative to the beginning of the staff, where 0.0 is the start of the staff.
  	 * <br/><br/>
    * @param minY    top-most y coordinate painted within staff
    * @param maxY    bottom-most y coordinate painted within staff
    */
   private void updateExtraSpacing(double minY, double maxY)
   {
      pixelsAboveStaff = Math.max(pixelsAboveStaff, (int)Math.ceil(0.0 - minY                  ));
      pixelsBelowStaff = Math.max(pixelsBelowStaff, (int)Math.ceil(maxY - STANDARD_STAFF_HEIGHT));
   }

   /**
    * @return the zero-based index of this staff
    */
   public int getStaffIndex()
   {
      return this.staffIndex;
   }

   /**
    * @return true if this is the first staff in the section
    */
   public boolean isFirstStaff()
   {
      return (this.staffIndex == 0);
   }

   /**
    * paints the staff.
    * <br/><br/>
    * @return  the height of the staff (in pixels).
    * <br/><br/>
    * @param g               graphics context
    * @param isScreenPaint   if this paint is being done for the screen (true) or printer (false)
    */
   public double paint(Graphics2D g, boolean isScreenPaint)
   {
      // The y coordinate where the next staff component will be drawn.
      double nextY = 0.0;

      // special handling for first staff in section.
      if (isFirstStaff())
      {
         // - chord texts
         for (List<Chord> chords : /*getChordLists()*/getChordLists())
         {
            g.translate(0, nextY);
            double dy = drawChordNames(g, chords);
            if (dy > 0.0 && isScreenPaint)
            {
               Util.drawDashedLine(g, 0, dy, PaintableSection.WIDTH, dy);
            }
            g.translate(0, -nextY);
            nextY += dy;
         }
      }
      else
      {
         // Inter-staff spacing.
         nextY += PIXELS_PER_TAB_LINE;
         if (isScreenPaint)
         {
            // Draw a line dividing the last staff and this one.
            Util.drawDashedLine(g, 0.0, nextY, PaintableSection.WIDTH, nextY);
         }
      }

      // Next, draw the notes on the standard staff,
      // so we know the number of ledger lines.
      g.translate(0, nextY);
      if (getClef() == Staff.Clef.Drum)
      {
         // Draw the drum-clef "notes".
         drawDrumNotes(g);
      }
      else
      {
         // Draw the standard notes.
         drawNotesAndRests(g);
      }
      g.translate(0, -nextY);

      // Draw the barlines for the standard staff.
      nextY += getStandardNotationStaffAboveSpacing();
      this.firstY = nextY;
      g.translate(0.0, nextY);
      drawBarlines(g);
      g.translate(0.0, -nextY);

      // If this is the first staff of the section, draw the measure number.
      if (isFirstStaff())
      {
         g.translate(0.0, nextY);
         drawMeasureNumber(g);
         g.translate(0.0, -nextY);
      }

      // Draw the horizontal lines comprising the standard notation staff.
      for (int i = 0; i < NUM_STANDARD_LINES; ++i)
      {
         g.draw(new Line2D.Double(0.0, nextY, PaintableSection.WIDTH, nextY));
         nextY += PIXELS_PER_STANDARD_SPACE;
      }

      // Skip the number of lines after the standard staff.
      this.lastY = nextY;
      nextY += getStandardNotationStaffBelowSpacing();
      if (isScreenPaint)
      {
         Util.drawDashedLine(g, 0, nextY, PaintableSection.WIDTH, nextY);
      }

      // Draw the instrument-ins.
      g.translate(0, nextY);
      double dy = drawInstrumentIns(g);
      if (isScreenPaint && dy > 0.0)
      {
         Util.drawDashedLine(g, 0, dy, PaintableSection.WIDTH, dy);
      }
      g.translate(0, -nextY);
      nextY += dy;

      // Draw the dynamics.
      g.translate(0, nextY);
      double dy2 = drawDynamics(g);
      if (isScreenPaint && dy2 > 0.0)
      {
         Util.drawDashedLine(g, 0, dy2, PaintableSection.WIDTH, dy2);
      }
      g.translate(0, -nextY);
      nextY += dy2;

      setSymbolSpacing((short)Math.ceil(dy + dy2));

      // Skip the first TAB line.
      nextY += PIXELS_PER_TAB_LINE;
      if (isScreenPaint)
      {
         Util.drawDashedLine(g, 0, nextY, PaintableSection.WIDTH, nextY);
      }

      // Draw each of the bar lines for this tablature staff.
      g.translate(0, nextY);
      drawTabBarlines(g);
      drawTabStaff(g, /*getChordLists()*/getChordLists());
      g.translate(0, -nextY);

      // Draw the tab staff.
      for (int tabIndex = 0; tabIndex < getNumTabLines(); ++tabIndex)
      {
         g.draw(new Line2D.Double(0.0, nextY, PaintableSection.WIDTH, nextY));
         this.lastY = nextY;

         // Add the tab lines to the cursor map.
         if (isScreenPaint)
         {
            double      tabRectangleY      = nextY - Math.ceil(PIXELS_PER_TAB_LINE/2.0) - 1.0;
            double      tabRectangleHeight = PIXELS_PER_TAB_LINE + 1.0;
            Rectangle2D tabRectangle       = new Rectangle2D.Double(PaintableSection.LEFT_MARGIN, tabRectangleY, PaintableSection.WIDTH, tabRectangleHeight);
            CursorMap   cursorMap          = this.parentPaintableSection.paintableSong.getCursorMap();
            cursorMap.addTabLine(g, tabRectangle, this.parentPaintableSection.sectionIndex, this.staffIndex, tabIndex);
         }

         // Move to the next tab line.
         nextY += PIXELS_PER_TAB_LINE;
      }

      // Return the height of this staff.
      // Add a bottom margin the height of a tab line.
      double height = this.lastY + PIXELS_PER_TAB_LINE;
      return height;
   }

   /**
    * draws barlines for the standard music staff.
    * <br/><br/>
    * @param graphics   graphics context.
    */
   public void drawBarlines(Graphics2D graphics)
   {
      final double dy = 0.0;

      // draw the staff, working backwards from the last measure.
      int lastPosition = -1;
      double x = PaintableSection.WIDTH;
      final int nBarlines = this.parentPaintableSection.getBarlines().size() - 1;
      for (int i = nBarlines; i >= 0; --i)
      {
         // determine the x coordinate of this measure.
         Barline barline = this.parentPaintableSection.getBarlines().get(i);
         if (i == 0)
         {
            x = 0.0;
         }
         else if (lastPosition >= 0)
         {
            int nPositions = lastPosition - barline.getPosition();
            x -= nPositions*this.parentPaintableSection.getPositionSpacing();
         }
         double y = dy;
         graphics.translate(x, y);

         // draw the barline for second and following measures.
         double dx = 0.0;
         if (i > 0)
         {
            dx = drawBarline(graphics, barline.getType(), barline.getNumRepeats(), NUM_STANDARD_LINES, PIXELS_PER_STANDARD_SPACE);
         }
         else
         {
            // determine the width of the clef so it can be centered.
            Dimension2D size = CLEF_SYMBOLS.get(getClef()).paint(GraphicsStub.INSTANCE);
            double tmpX = (CLEF_WIDTH - size.getWidth())/2.0;

            // draw the standard clef symbol.
            CLEF_SYMBOLS.get(getClef()).paint(graphics, tmpX, 0.0);
            dx = CLEF_WIDTH;
         }

         // draw the key signature for bass and treble clef staffs.
         // for drum staffs, fill with space where key signature would go.
         if (barline.getKeySignature() != null && getClef() != Clef.Drum)
         {
            graphics.translate(dx, 0);
            double dx2 = drawKeySignature(graphics, getClef(), barline.getKeySignature());
            graphics.translate(-dx, 0);
            dx += dx2;
         }

         // draw the time signature.
         if (barline.getTimeSignature() != null)
         {
            graphics.translate(dx, 0.0);
            double dx2 = drawTimeSignature(graphics, barline.getTimeSignature());
            graphics.translate(-dx, 0.0);
            dx += dx2;
         }

         // draw the first measure's barline, if specialized.
         if (i == 0 && barline.getType() != Barline.Type.Bar)
         {
            this.firstBarlineX = dx;
            graphics.translate(dx, 0.0);
            double dx2 = drawBarline(graphics, barline.getType(), barline.getNumRepeats(), NUM_STANDARD_LINES, PIXELS_PER_STANDARD_SPACE);
            graphics.translate(-dx, 0);
            dx += dx2;
         }

         graphics.translate(-x, -y);
         lastPosition = barline.getPosition();
      }
   }

   /**
    * draws the chord names that appear above the chords in the music staff for this section.
    * <br/><br/>
    * @return height in pixels occupied by the chord nammes, or 0 if no chords have the chord name attribute.
    * <br/><br/>
    * @param graphics  graphics context.
    * @param chords    list of chords for the staff.
    */
   private double drawChordNames(Graphics2D graphics, List<Chord> chords)
   {
      double height = 0.0;   // height of the tallest chord name drawn on the staff

      for (Chord chord : chords)
      {
         ChordName chordName = (ChordName)chord.getAttribute(ChordAttribute.Type.ChordName);
         if (chordName != null)
         {
            // determine the base x coordinate
            int   index = chord.getPosition();
            double x    = parentPaintableSection.xPositions.get(index);

            // get the width of the chord name
            Dimension2D size = drawChordName(GraphicsStub.INSTANCE, chordName);

            // adjust the x coordinate so the chord name is centered above the chord
            x -= size.getWidth()/2.0;
            graphics.translate(x, 0);
            size = drawChordName(graphics, chordName);
            graphics.translate(-x, 0);

            // set the height to that of the tallest chord name
            height = Math.max(height, size.getHeight());
         }
      }
      return Math.ceil(height);
   }

   /**
    * draws a chord name above a chord in the music staff.
    * <br/><br/>
    * @return the height and width (in pixels) of the chord name.
    * <br/><br/>
    * @param graphics   the graphics context.
    * @param chordName  the name of the chord.
    */
   private Dimension2D drawChordName(Graphics2D graphics, ChordName chordName)
   {
      // determine the height in pixels of the chord name
      final Font font = FontManager.MINI_TEXT_FONT;
      graphics.setFont(font);
      final FontMetrics metrics = graphics.getFontMetrics();
      double x  = 0.0;
      double y  = metrics.getHeight() - metrics.getDescent();
      double dy = Math.ceil(y) + Math.max(1.0, metrics.getDescent());


      // check for flat.
      String[] text = chordName.getName().split("_Flat");   // should be b
      if (text.length > 1)
      {
         graphics.drawString(text[0], (float)x, (float)y);
         x += metrics.getStringBounds(text[0], graphics).getWidth();
         x += MusicNotation.MINI_FLAT.paint(graphics, x, y).getWidth();
         graphics.drawString(text[1], (float)x, (float)y);
         x += metrics.getStringBounds(text[1], graphics).getWidth();
      }
      else
      {
         // Check for sharp
         text = chordName.getName().split("_Sharp");   // should be #
         if (text.length > 1)
         {
            graphics.setFont(font);
            graphics.drawString(text[0], (float)x, (float)y);
            x += metrics.getStringBounds(text[0], graphics).getWidth();
            x += MusicNotation.MINI_SHARP.paint(graphics, x, y).getWidth();
            graphics.drawString(text[1], (float)x, (float)y);
            x += metrics.getStringBounds(text[1], graphics).getWidth();
         }
         else
         {
            // no flat or sharp
            text[0] = chordName.getName();
            graphics.drawString(text[0], (float)x, (float)y);
            x += metrics.getStringBounds(text[0], graphics).getWidth();
         }
      }

      // return the size (in pixels) occupied by this chord text
      Dimension size = new Dimension();
      size.setSize(x, dy);

      return size;
   }

   /**
    * draws the measure number to the left of the first standard music staff in the section.
    * <br/><br/>
    * @param graphics    graphics context for drawing.
    */
   private void drawMeasureNumber(Graphics2D graphics)
   {
      // convert the measure number to a string
      String text = Integer.toString(this.parentPaintableSection.measureNumber);
      Font   font = FontManager.SMALL_FONT;

      // determine the extent of the text box
      FontMetrics metrics  = graphics.getFontMetrics(font);
      Rectangle2D bounds2d = metrics.getStringBounds(text, graphics);

      // determine the extent of the text
      double textWidth  = bounds2d.getWidth ();
      double textHeight = bounds2d.getHeight();

      // draw the measure number
      graphics.setFont(font);
      float x = (float)(-textWidth - 1.0);
      float y = (float)(textHeight - 1.0);

      graphics.drawString(text, x, y);
   }

   /**
    * draws the barlines on the tab staff.
    * <br/><br/>
    * @param g graphics 2d context
    */
   public void drawTabBarlines(Graphics2D g)
   {
      // Draw each barline, working backwards from the last measure.
      int lastPosition = -1;
      double x = PaintableSection.WIDTH;
      final int nBarlines = this.parentPaintableSection.getBarlines().size() - 1;
      for (int i = nBarlines; i >= 0; --i)
      {
         // Determine the x coordinate of this measure.
         Barline barline = this.parentPaintableSection.getBarlines().get(i);
         if (i == 0)
         {
            x = 0.0;
         }
         else if (lastPosition >= 0)
         {
            int nPositions = lastPosition - barline.getPosition();
            x -= nPositions*this.parentPaintableSection.getPositionSpacing();
         }
         double y = 0.0;
         g.translate(x, y);

         // Draw the barline for second and following measures.
         if (i > 0)
         {
            drawBarline(g, barline.getType(), barline.getNumRepeats(), getNumTabLines(), PIXELS_PER_TAB_LINE);
         }
         else
         {
            // Draw the TAB clef symbol.
            drawTabClef(g);
         }

         // Draw the first measure's barline, if specialized.
         if (i == 0 && barline.getType() != Barline.Type.Bar)
         {
            g.translate(this.firstBarlineX, 0);
            drawBarline(g, barline.getType(), barline.getNumRepeats(), getNumTabLines(), PIXELS_PER_TAB_LINE);
            g.translate(-this.firstBarlineX, 0);
         }

         g.translate(-x, -y);
         lastPosition = barline.getPosition();
      }
   }

   /** draws the given barline type in the graphics context.
    * <br/><br/>
    * @return width of the barline (in pixels).
    * <br/><br/>
    * @param g              graphics context.
    * @param barlineType    type of barline to draw.
    * @param nRepeats       number of repeats, if any.
    * @param nLines         number of lines in the staff the barline must vertically span.
    * @param spacePerLine   number of pixels between any two lines in the staff.
    */
   private double drawBarline(Graphics2D g, Barline.Type barlineType, int nRepeats, int nLines, int spacePerLine)
   {
      int    nSpaces = nLines - 1;              // number of ?
      double width   = 0;                       // width  of the barline (in pixels)
      int    height  = nSpaces*spacePerLine;    // height of the barline (in pixels)

      switch (barlineType)
      {
         case Bar:
              g.drawLine(0, 0, 0, height);
              width = 1.0;
         break;
         case DoubleBar:
              drawBarline(g, Barline.Type.Bar, 0, nLines, spacePerLine);
              g.translate(-(DOUBLE_BAR_WIDTH + 1), 0);
              drawBarline(g, Barline.Type.Bar, 0, nLines, spacePerLine);
              g.translate(DOUBLE_BAR_WIDTH + 1, 0);
              width = DOUBLE_BAR_WIDTH;
         break;
         case DoubleBarFine:
              g.translate(-(2*DOUBLE_BAR_WIDTH + 1), 0);
              drawBarline(g, Barline.Type.Bar, 0, nLines, spacePerLine);
              g.translate(DOUBLE_BAR_WIDTH + 1, 0);
              g.fillRect(0, 0, DOUBLE_BAR_WIDTH, height);
              g.drawRect(0, 0, DOUBLE_BAR_WIDTH, height);
              g.translate(DOUBLE_BAR_WIDTH, 0);
              width = 2.0*DOUBLE_BAR_WIDTH;
         break;
         case RepeatStart:
              g.fillRect(0, 0, DOUBLE_BAR_WIDTH, height);
              g.drawRect(0, 0, DOUBLE_BAR_WIDTH, height);
              g.translate(2*DOUBLE_BAR_WIDTH + 1, 0);
              drawBarline(g, Barline.Type.Bar, 0, nLines, spacePerLine);
              g.translate(2*DOUBLE_BAR_WIDTH, 0);
              double spacingFactor = ((nLines & 1) == 1) ? 1.0 : 2.0;
              double yFactor = ((nSpaces - spacingFactor)/(2.0*nSpaces));
              int y = (int)Math.round(yFactor*height);
              int circleWidth = (int)Math.ceil(0.1*height);
              g.fillOval(-circleWidth/2, y - circleWidth/2, circleWidth, circleWidth);
              yFactor = ((nSpaces + spacingFactor)/(2.0*nSpaces));
              y = (int)Math.round(yFactor*height);
              g.fillOval(-circleWidth/2, y - circleWidth/2, circleWidth, circleWidth);
              g.translate(-(4*DOUBLE_BAR_WIDTH + 1), 0);
              width = 6.0*DOUBLE_BAR_WIDTH;
         break;
         case RepeatEnd:
              g.scale(-1.0, 1.0);
              drawBarline(g, Barline.Type.RepeatStart, 0, nLines, spacePerLine);
              g.scale(-1.0, 1.0);
              // draw the number of repeats if > 2.
              if (nRepeats > 2)
              {
                 Font font = FontManager.SMALL_BOLD_FONT;
                 g.setFont(font);
                 g.drawString(Integer.toString(nRepeats), -2*DOUBLE_BAR_WIDTH, -2);
              }
              width = 0.0;
         break;
         case RepeatEndStart:
                      drawBarline(g, Barline.Type.RepeatEnd  , nRepeats, nLines, spacePerLine);
              width = drawBarline(g, Barline.Type.RepeatStart, 0       , nLines, spacePerLine);
         break;
         default:
              assert("invalid bar line type" == null);
      }
      return width;
   }

   /**
    * Draws the "TAB" clef at the beginning of the tab staff
    * <br/><br/>
    * @param graphics   graphics context for drawing.
    */
   private void drawTabClef(Graphics2D graphics)
   {
      // Use a weighting function to determine font size based on number
      // of tab lines.
      int numStrings  = getNumTabLines();
      double distance = Instrument.MAX_NUM_STRINGS - Instrument.MIN_NUM_STRINGS;
      double w1       = 1.0 - (Instrument.MAX_NUM_STRINGS - numStrings)/distance;
      double w2       = 1.0 - (numStrings - Instrument.MIN_NUM_STRINGS)/distance;
      double fontSize = w1*FontManager.MAX_TAB_CLEF_FONT_SIZE + w2*FontManager.MIN_TAB_CLEF_FONT_SIZE;
      final Font font = FontManager.deriveFont(FontManager.TAB_CLEF_FONT, (int)Math.ceil(fontSize));
      int tabStaffHeight = (numStrings - 1)*PIXELS_PER_TAB_LINE;

      Dimension clefDimension = new Dimension(CLEF_WIDTH, tabStaffHeight);
      Util.drawVerticalText(graphics, font, clefDimension, "TAB");
   }

   /**
    * draw the specified key signature on the staff with the given clef.
    * <br/><br/>
    * @param graphics      graphics context for drawing.
    * @param clef          the staff's clef.
    * @param keySignature  the key signature to draw.
    * <br/><br/>
    * @return   Delta x for the key signature
    */
   private double drawKeySignature(Graphics2D graphics, Clef clef, KeySignature keySignature)
   {
      // Delta x for the key signature.
      double dx;

      if (keySignature.isShown() && keySignature.hasAccidentals())
      {
         Accidental accidental = keySignature.toAccidental();
         int numAccidentals = keySignature.getNumberOfAccidentals();
         dx = drawAccidentals(graphics, accidental, numAccidentals, lookupAccidentalPositions(clef, keySignature));
      }
      else
      {
         // No accidentals are drawn.
         dx = 0.0;
      }
      return dx;
   }

   /**
    * Returns the array containing the accidentals' vertical positions.
    * @param clef
    * @param keySignature
    * @return
    */
   private double[] lookupAccidentalPositions(Clef clef, KeySignature keySignature)
   {
      return (clef == Clef.Treble)
         ? (keySignature.usesFlats() ? TREBLE_FLATS : TREBLE_SHARPS)
         : (keySignature.usesFlats() ? BASS_FLATS   : BASS_SHARPS  );
   }

   /**
    * draws the accidentals for a given key signature.
    * <br/><br/>
    * @param graphics      graphics context for drawing.
    * @param accidental
    * @param numAccidentals
    * @param yPositions
    * <br/><br/>
    * @return  the width of the accidentals, in pixels
    */
   private double drawAccidentals(Graphics2D graphics, Accidental accidental, int numAccidentals, double[] yPositions)
   {
      // Get the music notation.
      MusicNotation mn = ACCIDENTAL_SYMBOLS.get(accidental);

      // Draw the accidentals.
      double x = 0.0;
      for (int i = 0; i < numAccidentals; ++i)
      {
         graphics.setColor(Color.BLACK);
         double y = yPositions[i] - PIXELS_PER_STANDARD_SPACE/2.0 - 0.5;
         x += mn.paint(graphics, x, y).getWidth();
      }

      // Add extra horizontal space equal to the width of one accidental.
      if (numAccidentals > 0)
      {
         x += x/numAccidentals;
      }
      return x;
   }

   /**
    * draws the specified time signature
    * <br/><br/>
    * @return  width of the time signature in pixels
    * <br/><br/>
    * @param graphics        graphics context for drawing.
    * @param timeSignature   the time signature to draw.
    */
   private double drawTimeSignature(Graphics2D graphics, TimeSignature timeSignature)
   {
      // The width of the time signature, in pixels.
      double dx = 0.0;

      // y coordinate of the top, middle, and bottom of the staff.
      final double yTop    = 0.25*STANDARD_STAFF_HEIGHT;
      final double yMiddle = 0.50*STANDARD_STAFF_HEIGHT;
      final double yBottom = 0.75*STANDARD_STAFF_HEIGHT;

      if (timeSignature.isShown())
      {
         if (timeSignature.isCommonTime())
         {
            dx = MusicNotation.TIME_SIGNATURE_COMMON.paint(graphics, 0.0, yMiddle).getWidth();
         }
         else if (timeSignature.isCutTime())
         {
            dx = MusicNotation.TIME_SIGNATURE_CUT.paint(graphics, 0.0, yMiddle).getWidth();
         }
         else
         {
            // Convert time signature to string.
            String beatsPerMeasure = String.valueOf(timeSignature.getBeatsPerMeasure());
            String beatAmount = String.valueOf(timeSignature.getBeatAmount());

            // Determine if the time signature is all single-digits.
            if (timeSignature.getBeatsPerMeasure() < 10
                && timeSignature.getBeatAmount() < 10)
            {
               // Use large font for time signature.
               MusicNotation mn = TIME_SIGNATURE_SYMBOLS.get(beatsPerMeasure);
               dx = mn.paint(graphics, 0.0, yTop).getWidth();
               mn = TIME_SIGNATURE_SYMBOLS.get(beatAmount);
               mn.paint(graphics, 0.0, yBottom);
            }
            else
            {
               // Get the width of each number.
               double width1 = drawTimeCount(GraphicsStub.INSTANCE, beatsPerMeasure);
               double width2 = drawTimeCount(GraphicsStub.INSTANCE, beatAmount);
               dx = Math.max(width1, width2);

               // Draw beats per measure.
               double x = (dx - width1)/2.0;
               graphics.translate(x, yTop);
               drawTimeCount(graphics, beatsPerMeasure);
               graphics.translate(-x, -yTop);

               // Draw beat amount.
               x = (dx - width2)/2.0;
               graphics.translate(x, yBottom);
               drawTimeCount(graphics, beatAmount);
               graphics.translate(-x, -yBottom);
            }
         }
      }
      return Math.ceil(dx);
   }

   /**
    * draws the time signature number (either beats per minute or beat amount) in the given graphics context.
    * <br/><br/>
    * @return      width occupied by the number
    * <br/><br/>
    * @param graphics   graphics context.
    * @param number
    */
   private double drawTimeCount(Graphics2D graphics, String number)
   {
      double dx = 0.0;

      // split the number into its constituent digits.
      for (char c : number.toCharArray())
      {
         MusicNotation mn = TIME_SIGNATURE_SYMBOLS.get(String.valueOf(c));
         dx += mn.paint(graphics, dx, 0.0).getWidth();
      }
      return dx;
   }

   /**
    * draws the instrument-ins for the given staff.
    * <br/><br/>
    * @return  height of the tallest instrument in name on the staff.
    * <br/><br/>
    * @param graphics   graphics context
    */
   public double drawInstrumentIns(Graphics2D graphics)
   {
      double dy = 0.0;

      if (!instrumentIns.isEmpty())
      {
         // activate the instrument-in font
         Font font = FontManager.MINI_TEXT_FONT;
         graphics.setFont(font);

         // calculate the y coordinate
         FontMetrics metrics = graphics.getFontMetrics();
         LineMetrics lineMetrics = metrics.getLineMetrics("X", graphics);
         dy = lineMetrics.getHeight();
         double y = dy - lineMetrics.getDescent();
         graphics.translate(0.0, y);

         // draw each instrument in
         for (Instrument instrument : this.instrumentIns.keySet())
         {
            int barlineIndex = this.instrumentIns.get(instrument);
            Barline barline = this.parentPaintableSection.getBarlines().get(barlineIndex);
            drawInstrumentIn(graphics, barline.getPosition(), instrument.getName());
         }

         // restore the y coordinate
         graphics.translate(0.0, -y);
      }

      return dy;
   }

   /**
    * draws an individual instrument-in at the given barline drawing position.
    * <br/><br/>
    * @param graphics                graphics context.
    * @param barlineDrawingPosition  the drawing position of the barline where the instrument becomes active.
    * @param instrumentName          the name of the instrument.
    */
   private void drawInstrumentIn(Graphics2D graphics, int barlineDrawingPosition, String instrumentName)
   {                   // string        , x                                                                                                    , y
      graphics.drawString(instrumentName, (float)(parentPaintableSection.xPositions.get(barlineDrawingPosition) + CursorMap.CURSOR_WIDTH / 2.0), 0.0f);
   }

   /**
    * draws the volume symbols that occur in the staff.
    * <br/><br/>
    * @return the height of the volume symbols in the staff (in pixels), or 0 if there staff did not have any.
    * <br/><br/>
    * @param graphics   graphics context.
    */
   public double drawDynamics(Graphics2D graphics)
   {
      double dy = 0.0;

      // examine each barline for a dynamic marker.
      for (Barline barline : this.parentPaintableSection.getBarlines())
      {
         Volume volume = barline.getVolume((byte)this.staffIndex);
         if (volume != null)
         {
            // activate the instrument-in font.
            Font font = FontManager.DYNAMIC_FONT;
            graphics.setFont(font);
            String text = volume.getLevel().text();
            int position = getFirstDrawingPositionInMeasure(barline);
            double x = this.parentPaintableSection.xPositions.get(position);
            FontMetrics metrics = graphics.getFontMetrics();
            Rectangle2D bounds2d = metrics.getStringBounds(text, graphics);
            x -= bounds2d.getWidth()/2.0;
            LineMetrics lineMetrics = metrics.getLineMetrics(text, graphics);
            dy = lineMetrics.getHeight();
            double y = dy - lineMetrics.getDescent();
            graphics.drawString(text, (float)x, (float)y);
         }
      }

      return dy;
   }

   /**
    * draws the durations in the tab staff.
    * <br/><br/>
    * @param graphics    graphics context for drawing.
    * @param chordLists  list of melody lines of a staff.
    */
   private void drawTabStaff(Graphics2D graphics, List<List<Chord>> chordLists)
   {
      for (List<Chord> chordList : chordLists)
      {
         for (Chord chord : chordList)
         {
            List<Note> notes = chord.getNotes();
            for (Note note : notes)
            {
               if (note.isTied())
               {
                  // todo: put code here to handle this case
               }
               else
               {
                  drawFretNumberOnTabStaff(graphics, chord.getPosition(), note.getString(), note.getFret());
               }
            }
         }
      }
   }

   /**
    * draws a fret number on a tab staff line
    * @param graphics   graphics context for drawing.
    * @param position   the drawing position within the section where the tab note is to be drawn.
    * @param string     the string on which the fret number will be displayed.
    * @param fret       the fret number to be displayed.
    */
   private void drawFretNumberOnTabStaff(Graphics2D graphics, int position, Instrument.String string, Instrument.Fret fret)
   {
      // get the fret number as a string
      String      text       = fret == Instrument.Fret.Open ? "0" : fret.text();

      // calculate the y coordinate where the fret number will be drawn
      Font        font       = FontManager.TAB_FONT;
      FontMetrics metrics    = graphics.getFontMetrics(font);
      double      textHeight = metrics.getAscent();
      double      yCenter    = string.ordinal() * PIXELS_PER_TAB_LINE;
      double      y          = (yCenter + textHeight / 2.0) - metrics.getDescent();

      // calculate the x coordinate where the fret number will be drawn
      Rectangle2D bounds2d   = metrics.getStringBounds(text, graphics);
      double      x          = this.parentPaintableSection.xPositions.get(position).doubleValue() - bounds2d.getWidth() / 2.0;

      // draw the fret on the tab staff
      graphics.setFont(font);
      graphics.drawString(text, (float)x, (float)y);
   }

   /**
    * @return the drawing position for the first duration in the specified measure in this staff.
    * <br/><br/>
    * @param barline   the beginning barline of the measure.
    */
   public int getFirstDrawingPositionInMeasure(Barline barline)
   {
      int notePosition = Integer.MAX_VALUE;

      // examine all voices for notes.
      for (List<Chord> chordList : /*this.chordLists*/ this.getChordLists())
      {
         // look for the first note in the chord list.
         for (Chord chord : chordList)
         {
            if (chord.getPosition() > barline.getPosition() && !chord.isRest())
            {
               notePosition = Math.min(notePosition, chord.getPosition());
               break;
            }
         }
      }
      if (notePosition == Integer.MAX_VALUE)
      {
         // no notes in this barline, just use barline's position.
         notePosition = barline.getPosition();
      }
      return notePosition;
   }

   /**
    * draws the chords in both the high and low melody lines of the staff.
    * <br/><br/>
    * @param g   graphics context.
    */
   public void drawNotesAndRests(Graphics2D g)
   {
      // Skip the amount of spacing above the staff.
      double initY = super.getStandardNotationStaffAboveSpacing();
      g.translate(0.0, initY);
      // Clear out the old ledger line information.
      // This is recalculated while drawing the notes.
      this.pixelsAboveStaff = 0;
      this.pixelsBelowStaff = 0;
      // Retrieve the barlines from the parent.
      List<Barline> barlines = parentPaintableSection.getBarlines();
      // Draw the notes for each voice in the staff.
      // TODO force upward/downward stemming if both voices are in use at the
      // same time.
      int barlineIndex = 0;
      for (int iVoice = 0; iVoice < getChordLists().size(); iVoice++)
      {
         List<Chord> voice = getChordLists().get(iVoice);
         // Track the aggregate area for all chords connected by the same beam.
         List<Chord> beamChords = new ArrayList<Chord>();
         List<Rectangle2D> beamBounds = new ArrayList<Rectangle2D>();
         Map<Note, Point> noteToPoint = null, prevNoteToPoint = null;
         for (int iChord = 0; iChord < voice.size(); iChord++)
         {
            Chord chord = voice.get(iChord);
            noteToPoint = new HashMap<Note, Point>();
            // Get the chord's drawing position and its x coordinate.
            int position = chord.getPosition();
            double x = parentPaintableSection.xPositions.get(position)
                  .doubleValue();
            // Update the barline index to match the drawing position.
            while (barlines.get(barlineIndex + 1).getPosition() <= position)
            {
               ++barlineIndex;
            }
            // Draw each of the notes or rests.
            if (chord.isRest())
            {
               g.translate(x, 0.0);
               drawRest(g, chord);
               g.translate(-x, 0.0);
            }
            // Make sure there are notes in this chord.
            else if (chord.getNotes().isEmpty())
            {
               // TODO handle chords with no notes
               // System.err.println("no notes in section " +
               // this.parent.sectionIndex +
               // ", staff " + this.staffIndex + ", position " + position);
            }
            else
            {
               // Track the aggregate area for all notes in this chord.
               Rectangle2D chordRect = null;
               // Obtain the key signature for this measure.
               KeySignature keySignature = barlines.get(barlineIndex)
                     .getKeySignature();
               // Draw each of the notes comprising this chord.
               for (Note note : chord.getNotes())
               {
                  // Get the note and draw it. // TODO this is wrong - it should
                  // come from the tuning
                  Midi.Note midiNote = Midi.getNote(note.getString(), note
                        .getFret(), this.tuning, Instrument.Fret.Not_Used,
                        keySignature.usesSharps());
                  g.translate(x, 0.0);
                  Rectangle2D rect = drawNote(g, chord, midiNote);
                  g.translate(-x, 0.0);
                  noteToPoint
                        .put(note, new Point((int) (x), (int) rect.getY()));
                  // Update the size occupied by the chord.
                  if (chordRect == null)
                  {
                     chordRect = rect;
                  }
                  else
                  {
                     chordRect.add(rect);
                  }
               }
               drawBackTies(g, noteToPoint, prevNoteToPoint);
               prevNoteToPoint = noteToPoint;
               // Normalize the chord rectangle to reflect the current
               // transform.
               chordRect.setRect(chordRect.getX() + x, chordRect.getY(),
                     chordRect.getWidth(), chordRect.getHeight());
               // Use the bounds of the note heads to update the pixels
               // above and below the staff.
               updateExtraSpacing(chordRect.getMinY(), chordRect.getMaxY());
               // Check for beaming continuation.
               Beam beam = chord.getBeam();
               if (!beamChords.isEmpty())
               {
                  // Add this chord and its bounds to the beam list.
                  beamChords.add(chord);
                  beamBounds.add(chordRect);
                  // Check if this chord is the end of the beam.
                  if (beam.isEnd())
                  {
                     // This is the last chord containing the beam.
                     drawBeam(g, beamChords, beamBounds);
                     beamChords.clear();
                     beamBounds.clear();
                  }
               }
               // Check for start of beam.
               else if (beam.isStart())
               {
                  // Add this chord to the list of beam notes.
                  beamChords.add(chord);
                  beamBounds.add(chordRect);
               }
               // Chord is not part of a beam. A stem is required
               // if chord duration is less than a quarter note.
               else if (chord.getDuration().compareTo(Midi.Duration.NOTE_WHOLE) < 0)
               {
                  drawStem(g, chord, chordRect);
               }
            } // end else
            // TODO: START CODE REVIEW
            if (iChord == voice.size() - 1)
            {
               // determine if last chord contains notes which are tied-to from
               // notes of the first chord of staff in next section
               if (parentPaintableSection.paintableSong.getScore()
                     .getSections().size() > parentPaintableSection.sectionIndex + 1)
               {
                  PaintableSection nextPaintableSection = parentPaintableSection.paintableSong
                        .getPaintableScore().getPaintableSections().get(
                              parentPaintableSection.sectionIndex + 1);
                  Location location = new Location(
                        (short) parentPaintableSection.sectionIndex, (byte) -1,
                        (byte) barlineIndex, (short) -1, (byte) -1, null, -1);
                  // Find the staff correspondence across sections with the same instrument
                  ActiveInstruments activeInstruments = parentPaintableSection.paintableSong
                        .getActiveInstruments(location);
                  for (int iInstrument = 0; iInstrument < activeInstruments
                        .getNumInstruments(); iInstrument++)
                  {
                     if (activeInstruments.getInstruments((byte) staffIndex)[iInstrument])
                     {
                        for (int iStaff = 0; iStaff < nextPaintableSection
                              .getPaintableStaffs().size(); iStaff++)
                        {

                           List<PaintableStaff> paintableStaffs = nextPaintableSection
                                 .getPaintableStaffs();
                           if (paintableStaffs.size() > staffIndex)
                           {
                              Location nextLocation = new Location(
                                    (short) parentPaintableSection.sectionIndex,
                                    (byte) -1, (byte) 0, (short) -1, (byte) -1,
                                    null, -1);
                              ActiveInstruments nextActiveInstruments = parentPaintableSection.paintableSong
                                    .getActiveInstruments(nextLocation);
                              if (nextActiveInstruments
                                    .getInstruments((byte) iStaff)[iInstrument])
                              {
                                 PaintableStaff nextPaintableStaff = paintableStaffs
                                       .get(staffIndex);

                                 List<Chord> nextChordList = nextPaintableStaff
                                       .getChords(iVoice);
                                 if (nextChordList.size() > 0)
                                 {
                                    drawForwardTies(g, noteToPoint,
                                          nextChordList.get(0));
                                 }
                              }
                           }
                        }
                     }
                  }

               }
            }
            // TODO: STOP CODE REVIEW
         } // next chord
      } // next voice
      // Undo translation.
      g.translate(0.0, -initY);
      // Now that all the notes are drawn and ledger lines have been
      // recalculated, initialize spacing before and after the standard staff.
      initExtraSpacing();
   }

   // Draw any needed ties to first chord on next staff
   private void drawForwardTies(Graphics2D g, Map<Note, Point> tieMap,
         Chord nextChord)
   {
      // Draw any ties to end of staff
      for (Note note : tieMap.keySet())
      {
         if (note.isTied())
         {
            // determine note to tie back to
            for (Note nextNote : nextChord.getNotes())
            {
               if (nextNote.getFret().equals(note.getFret())
                     && nextNote.getString().equals(note.getString()))
               {
                  Point point = tieMap.get(note);
                  drawTie(g, true,point.x,PaintableSection.WIDTH,point.y);
               }
            }
         }
      }
   }

   // Draw any needed note ties to previous chord.
   private void drawBackTies(Graphics2D g, Map<Note, Point> chordMap, Map<Note, Point> prevChordMap)
   {
      if (prevChordMap == null)
      {
         // tie notes back to start of staff
         for (Note note : chordMap.keySet())
         {
            if (note.isTied())
            {
               Point point = chordMap.get(note);
               drawTie(g,true,point.x,0,point.y);
            }
         }
      }
      else
      {
         for (Note note : chordMap.keySet())
         {
            if (note.isTied())
            {
               // determine note to tie back to
               for (Note prevNote : prevChordMap.keySet())
               {
                  if (prevNote.getFret().equals(note.getFret())
                        && prevNote.getString().equals(note.getString()))
                  {
                     Point point = chordMap.get(note);
                     Point prevPoint = prevChordMap.get(prevNote);
                     drawTie(g,true,point.x,prevPoint.x,point.y);
                  }
               }
            }
         }
      }
   }

   /**
    * draw a tie between two notes.
    * <br/><br/>
    * @param g              graphics context
    * @param curveDown
    * @param x1             ?
    * @param x2             ?
    * @param y              ?
    */
   private void drawTie(Graphics2D g, boolean curveDown, int x1, int x2, int y)
   {
      //g.drawString(""+parentPaintableSection.sectionIndex,x1, y);
      int loX=x1<=x2?x1:x2;
      int hiX=x1>=x2?x1:x2;
      Color c = g.getColor();
      //g.setColor(Color.RED);
      g.translate(0,0);
      if (curveDown) {
         g.drawArc(loX, y-(int)(PIXELS_PER_STANDARD_SPACE*1.5), hiX-loX, (int)(PIXELS_PER_STANDARD_SPACE*3.0),210, 120);
         g.drawArc(loX, y-(int)(PIXELS_PER_STANDARD_SPACE*1.5), hiX-loX, (int)(PIXELS_PER_STANDARD_SPACE*3.0)+1,210, 120);
      }
      else {
         g.drawArc(loX, y-(int)(PIXELS_PER_STANDARD_SPACE*1.5), hiX-loX, (int)(PIXELS_PER_STANDARD_SPACE*3.0),30, 150);
         g.drawArc(loX, y-(int)(PIXELS_PER_STANDARD_SPACE*1.5), hiX-loX, (int)(PIXELS_PER_STANDARD_SPACE*3.0)+1,30, 150);
      }
      //g.setColor(c);
   }

   /**
    * draws the specialized drum notations on the drum staff.
    * <br/><br/>
    * @param graphics  graphics context.
    */
   private void drawDrumNotes(Graphics2D graphics)
   {
      // todo: need to determine the drum scheme
   }

   /**
    * draws the specified rest on the standard music staff
    * <br/><br/>
    * @param graphics  graphics context.
    * @param rest      rest to draw on the standard music staff.
    */
   private void drawRest(Graphics2D graphics, Chord rest)
   {
      MusicNotation mn = REST_SYMBOLS.get(rest.getDuration());
      if (mn == null)
      {
         throw new RuntimeException("no rest symbol for " + rest.getDuration());
      }

      final double width = mn.paint(GraphicsStub.INSTANCE).getWidth();
      final double x = -width/2.0;
      final double y = STAFF_MIDDLE_Y;
      graphics.translate(x, y);
      mn.paint(graphics);
      graphics.translate(-x, -y);
   }

   /**
    * draws the specified note head on the standard music staff.
    * <br/><br/>
    * @return bounding occupied by the note just drawn
    * <br/><br/>
    * @param graphics  graphics context.
    * @param chord     chord that the note belongs to.
    * @param note      note to draw.
    */
   private Rectangle2D drawNote(Graphics2D graphics, Chord chord, Midi.Note note)
   {
      // determine which note to be drawn.
      MusicNotation mn = NOTE_SYMBOLS.get(chord.getDuration());
      if (mn == null)
      {
         throw new RuntimeException("no note symbol for " + chord.getDuration());
      }

      // determine the position of this note, relative to the reference point.
      int octaveDiff = note.octave() - this.baseNote.octave();
      int baseNoteIndex = (this.baseNote.name().charAt(0) - 'C' + 7) % 7;
      int noteIndex = (note.name().charAt(0) - 'C' + 7) % 7;
      int noteDiff = noteIndex - baseNoteIndex;
      if (this.baseNote.pitch() < note.pitch())
      {
         // note is higher than the clef's base note.
         // adjust for negative note differences.
         if (noteDiff < 0)
         {
            noteDiff += 7;
            --octaveDiff;
         }
      }
      else if (this.baseNote.pitch() > note.pitch())
      {
         // note is lower than the clef's base note.
         // adjust for positive note differences.
         if (noteDiff > 0)
         {
            noteDiff -= 7;
            ++octaveDiff;
         }
      }

      // calculate y coordinate based on offset from base
      int diff = 7*octaveDiff + noteDiff;

      // todo: temporary fix to get around note oddities; doesn't handle all case
      if (getClef() == Clef.Treble)
      {
         diff += 7;
      }
      else // if (getClef() == Clef.Bass)
      {
         diff -= 6;
      }
      double y = this.baseY - 0.5*(diff*PIXELS_PER_STANDARD_SPACE);

      // Determine the note's width so it can be centered.
      double width = mn.paint(GraphicsStub.INSTANCE).getWidth();
      double x = -width/2.0;

      // draw the note
      Dimension2D size = mn.paint(graphics, x, y);

      // draw lines outside the staff
      drawLedgerLines(graphics, y, 2.0*width);

      // return the bounds of the note
      Rectangle2D bounds = new Rectangle2D.Double();
      bounds.setRect(x, y, size.getWidth(), size.getHeight());

      return bounds;
   }

   /**
    * draws extended lines if the note is off the standard staff.
    * <br/><br/>
    * @param graphics  graphics context.
    * @param y
    * @param width     width of the line being drawn.
    */
   private void drawLedgerLines(Graphics2D graphics, double y, double width)
   {
      final double staffStart = 0.0;
      final double staffStop  = STANDARD_STAFF_HEIGHT;
      List<Double> ledgerLines = new ArrayList<Double>();

      // if the note head's y coordinate is before the staff begins, add one ledger line at a time until the y coordinate is included
      if (y < staffStart)
      {
         double tmpY = staffStart - PIXELS_PER_STANDARD_SPACE;
         while (tmpY >= y)
         {
            ledgerLines.add(tmpY);
            tmpY -= PIXELS_PER_STANDARD_SPACE;
         }
      }
      // if the note head's y coordinate is after the staff ends, add one ledger line at a time until the y coordinate is included
      else if (y > staffStop)
      {
         double tmpY = staffStop + PIXELS_PER_STANDARD_SPACE;
         while (tmpY <= y)
         {
            ledgerLines.add(tmpY);
            tmpY += PIXELS_PER_STANDARD_SPACE;
         }
      }

      // draw the ledger lines that have just been added
      for (double lineY : ledgerLines)
      {
         // center the ledger line about the note head, making its width twice that of the note head
         final double x = -width/2.0;
         graphics.draw(new Line2D.Double(x, lineY, x + width, lineY));
      }
   }

   /**
    * paints the beam for the given group of chords.
    * <br/><br/>
    * @param graphics      graphics context.
    * @param chords        list of chords.
    * @param chordBounds   bounds for each chord, in the same order as chords parameter.
    */
   private void drawBeam(Graphics2D graphics, List<Chord> chords, List<Rectangle2D> chordBounds)
   {
      // first we have to decide the stem direction for the connected notes.
      // the note head that is farthest from the center of the staff will
      // dictate the beam's direction.
      // also track the note heads with min and max y coordinates.
      double maxDistance = -Double.MIN_VALUE;
      boolean isStemUp   = false;
      double minY        =  Double.MAX_VALUE;
      double maxY        = -Double.MAX_VALUE;

      for (int i = 0; i < chords.size(); ++i)
      {
         // determine distance from middle of the staff for this chord
         Rectangle2D bounds = chordBounds.get(i);
         double distance1 = Math.abs(bounds.getY()                      - STAFF_MIDDLE_Y);
         double distance2 = Math.abs(bounds.getY() + bounds.getHeight() - STAFF_MIDDLE_Y);
         double distance  = Math.max(distance1, distance2);

         // check if this is a new max distance.
         if (distance > maxDistance)
         {
            maxDistance = distance;
            isStemUp = isStemDirectionUp(bounds);
         }

         // update the min and max y coordinates of the note heads
         minY = Math.min(minY, bounds.getY());
         maxY = Math.max(maxY, bounds.getY() + bounds.getHeight());
      }

      // if there is an extreme difference between the min and max note heads, you can relax the stem length rules
      if (maxY - minY > INTERVALIC_DISTANCE_THRESHOLD)
      {
         // re-calculate the shortest stem length.
         // there must be at least one full space between beam and note head plus one space per extra beam.
         int    flagCount  = Midi.Duration.NOTE_QUARTER.ordinal() - chords.get(0).getDuration().ordinal();
         double minSpacing = (flagCount + 0.5) * PIXELS_PER_STANDARD_SPACE;

         if (isStemUp)
            minY -= minSpacing;   // the beam's y coordinate only needs to be minSpacing above the highest note head
         else
            maxY += minSpacing;   // the beam's y coordinate only needs to be minSpacing below the lowest note head
      }
      else
      {
         // Determine the min and max y coordinates by examining each stem.
         minY = Double.MAX_VALUE;
         maxY = -Double.MAX_VALUE;
         for (int i = 0; i < chords.size(); ++i)
         {
            Chord chord = chords.get(i);
            Rectangle2D bounds = chordBounds.get(i);
            Line2D stem = createStem(isStemUp, chord, bounds);
            minY = Math.min(minY, stem.getY1());
            maxY = Math.max(maxY, stem.getY2());
         }
      }

      // now we can draw all the chord stems
      Line2D leftStem  = null;
      Line2D rightStem = null;
      for (int i = 0; i < chords.size(); ++i)
      {
         // get the next stem
         Chord       chord     = chords.get(i);
         Rectangle2D bounds    = chordBounds.get(i);
         Line2D      chordStem = createStem(isStemUp, chord, bounds);

         // extend the stem to the beam
         if (isStemUp)
            chordStem.setLine(chordStem.getX1(), minY             , chordStem.getX2(), chordStem.getY2());
         else
            chordStem.setLine(chordStem.getX1(), chordStem.getY1(), chordStem.getX2(), maxY);

         // update the left/right stems.
         if (leftStem == null)
            leftStem = chordStem;
         rightStem = chordStem;

         // draw the stem.
         graphics.draw(chordStem);

         // use the bounds of the stem to update the pixels above and below the staff.
         updateExtraSpacing(chordStem.getY1(), chordStem.getY2());
      }

      // determine the beam drawing characteristics, based on stem direction
      double leftY;
      double rightY;
      double yIncrement;
      double thickness = PIXELS_PER_STANDARD_SPACE/2.0;

      if (isStemUp)
      {
         leftY      = leftStem .getY1() - thickness/2.0;
         rightY     = rightStem.getY1() - thickness/2.0;
         yIncrement = PIXELS_PER_STANDARD_SPACE;
      }
      else
      {
         leftY      = leftStem .getY2() + thickness/2.0;
         rightY     = rightStem.getY2() + thickness/2.0;
         yIncrement = -PIXELS_PER_STANDARD_SPACE;
      }

      // draw the beam.
      Midi.Duration duration = chords.get(0).getDuration();
      int beamCount = Midi.Duration.NOTE_QUARTER.ordinal() - duration.ordinal();
      for (int i = 0; i < beamCount; ++i)
      {
         Util.drawThickLine(graphics, thickness, leftStem.getX1(), leftY, rightStem.getX1(), rightY);
         leftY  += yIncrement;
         rightY += yIncrement;
      }
   }

   /**
    * draws the stem of the specified chord.
    * <br/><br/>
    * @param graphics    graphics context.
    * @param chord       the chord whose stem is being drawn.
    * @param chordRect   bounding rectangle enclosing the chord.
    */
   private void drawStem(Graphics2D graphics, Chord chord, Rectangle2D chordRect)
   {
      // draw the stem
      boolean isStemUp = isStemDirectionUp(chordRect);
      Line2D  stem     = createStem(isStemUp, chord, chordRect);
      graphics.draw(stem);

      // use the bounds of the stem to update the extra spacing above and below the staff
      updateExtraSpacing(stem.getY1(), stem.getY2());

      // look up the stem flag
      MusicNotation mn = null;
      double flagY = 0.0;
      if (chord.getBeam().getType() == 0)
      {
         if (isStemUp)
         {
            mn = FLAG_DOWN_SYMBOLS.get(chord.getDuration());
            flagY = stem.getY1();
         }
         else
         {
            mn = FLAG_UP_SYMBOLS.get(chord.getDuration());
            flagY = stem.getY2();
         }
      }

      // draw the flag(s) on the stem
      if (mn != null)
      {
         mn.paint(graphics, stem.getX1(), flagY);
      }
   }

   /**
    * @return a line defining the stem for the specified chord providing extra stem length for ledger lines, note flags, and beams.
    * <br/><br/>
    * @param isStemUp   whether the chord stem is drawn up or down.
    * @param chord      the chord whose stem is to be drawn.
    * @param rectangle  the rectangle bounding the noteheads in the chord.
    */
   private Line2D createStem(boolean isStemUp, Chord chord, Rectangle2D rectangle)
   {
      double x = rectangle.getX();
      double y1;
      double y2;

      if (isStemUp)
      {
         // calculate the x and y coordinates of the downwards stem.
         x += rectangle.getWidth();
         y1 = rectangle.getY() - STEM_LENGTH;
         y2 = rectangle.getY() + rectangle.getHeight();

         // the top of the stem must begin no lower than the middle of the staff, minus one space per extra flag
         double maxY = STAFF_MIDDLE_Y;
         if (chord.getDuration().compareTo(Midi.Duration.NOTE_8TH) < 0)
         {
            int extraFlagCount = Midi.Duration.NOTE_8TH.ordinal() - chord.getDuration().ordinal();
            maxY -= extraFlagCount * PIXELS_PER_STANDARD_SPACE;
         }
         if (y1 > maxY)
            y1 = maxY;
      }
      else
      {
         // calculate the x and y coordinates of the upwards stem
         y1 = rectangle.getY();
         y2 = rectangle.getY() + rectangle.getHeight() + STEM_LENGTH;

         // the bottom of the stem must extend to the middle of the staff, plus one space per extra flag.
         double minY = STAFF_MIDDLE_Y;
         if (chord.getDuration().compareTo(Midi.Duration.NOTE_8TH) < 0)
         {
            int extraFlagCount = Midi.Duration.NOTE_8TH.ordinal() - chord.getDuration().ordinal();
            minY += extraFlagCount*PIXELS_PER_STANDARD_SPACE;
         }
         if (y2 < minY)
            y2 = minY;
      }
      return new Line2D.Double(x, y1, x, y2);
   }

   /**
    * @return true if the stem should be upwards, and false otherwise.
    * <br/><br/>
    * todo: this is not correct - the direction of the stem is determined by a number of factors
    * <br/><br/>
    * @param noteHeadsBoundingRectangle   bounding rectangle around the note heads of the chord.
    */
   private boolean isStemDirectionUp(Rectangle2D noteHeadsBoundingRectangle)
   {
      // the top of the staff begins at y coordinate 0
      double distanceToTopStaffLine    = noteHeadsBoundingRectangle.getY();
      double distanceToBottomStaffLine = STANDARD_STAFF_HEIGHT - (noteHeadsBoundingRectangle.getY() + noteHeadsBoundingRectangle.getHeight());

      return distanceToTopStaffLine >= distanceToBottomStaffLine;
   }

   Map<Instrument, Integer> getInstrumentIns()
   {
      // TODO Auto-generated method stub
      return instrumentIns;
   }
}
