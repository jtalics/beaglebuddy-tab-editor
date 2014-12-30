/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andy will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import com.beaglebuddy.tab.gui.font.FontManager;
import com.beaglebuddy.tab.gui.font.MusicNotation;
import com.beaglebuddy.tab.gui.mainframe.CursorMap;
import com.beaglebuddy.tab.model.AlternateEnding;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.RehearsalSign;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.TempoMarker;
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.staff.Staff;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;




/**
 * This class encapsulates a section model that can be painted in a
 * graphics context.
 */
public class PaintableSection extends Section
{
   /** Fixed width of a section, in pixels. */
   public static final int WIDTH = 750;

   /** Fixed distance above the first section. */
   public static final int TOP_MARGIN = 20;

   /** Fixed left margin of a section, in pixels. */
   public static final int LEFT_MARGIN = 50;

   /** The number of pixels between each section. */
   public static final int INTERSECTION_MARGIN = 26;


   // data members
   protected final PaintableSong        paintableSong;          // the song that this section is a part of.
   protected final int                  sectionIndex;           // the index of this section within the song.
   private   final List<PaintableStaff> paintableStaffs;        // list of staffs within this section, paintable.
   protected final List<Double>         xPositions;             // array of increasing x-coordinates corresponding to positions.
   private         boolean              isLayoutNeeded;         // set to true if the section's layout has changed since the last paint.
   private         int                  startY;                 // the y coordinate that the section begins drawing.
   protected       int                  measureNumber;          // the number of this section's first measure, 1-indexed.





   /**
    * constructor.
    * <br/><br/>
    * @param song           the song.
    * @param s              the section model object to be made paintable
    * @param sectionIndex   the index of this section within the song
    * @param measureNumber  the number of the first measure in the section
    * @param y              the y coordinate this section starts at
    */
   public PaintableSection(PaintableSong song, Section s, int sectionIndex, int measureNumber, int y)
   {
      super();

      this.paintableSong = song;
      this.sectionIndex = sectionIndex;
      setBoundingRectangle(s.getBoundingRectangle());
      setPositionSpacing(s.getPositionSpacing());
      setRhythmSlashSpacingAbove(s.getRhythmSlashSpacingAbove());
      setRhythmSlashSpacingBelow(s.getRhythmSlashSpacingBelow());
      setExtraSpacing(s.getExtraSpacing());
      setRhythmStaff(s.getRhythmStaff());

      // construct the paintable version of all staffs contained in the section.
      int numStaffs = s.getStaffs().size();
      this.paintableStaffs = new ArrayList<PaintableStaff>(numStaffs);
      for (int staffIndex = 0; staffIndex < numStaffs; ++staffIndex)
      {
         // get the instruments on this staff.
         Map<Instrument, Integer> instrumentIns = this.paintableSong.getInstrumentMap().getInstrumentIns(sectionIndex, staffIndex);
         Tuning                   tuning        = this.paintableSong.getInstrumentMap().getActiveTuning(sectionIndex, staffIndex);

         // Construct the paintable version of this staff.
         Staff staff = s.getStaffs().get(staffIndex);
         PaintableStaff ps = new PaintableStaff(this, staff, staffIndex, instrumentIns, tuning);
         this.paintableStaffs.add(ps);
      }

      setStaffs(this.paintableStaffs.toArray(new Staff[numStaffs]));
      this.setBarlines(s.getBarlines().toArray(new Barline[0]));
      this.xPositions = new ArrayList<Double>();
      this.isLayoutNeeded = true;
      this.measureNumber = measureNumber;
      this.startY = y;
   }

   /**
    * @return the number of the first measure in the section.
    */
   public int getFirstMeasureNumber()
   {
      return measureNumber;
   }

   /**
    * assign a new initial measure number for this section.
    * <br/><br/>
    * @param measureNumber  the measure number.
    */
   public void setFirstMeasureNumber(int measureNumber)
   {
      this.measureNumber = measureNumber;
   }

   /**
    * @return the list of paintable staffs within the section.
    */
   public List<PaintableStaff> getPaintableStaffs()
   {
      return paintableStaffs;
   }

   /**
    * @return the starting y coordinate for this section.
    */
   public int getStartY()
   {
      return startY;
   }

   /**
    * assigns a new starting y coordinate for this section.
    * <br/><br/>
    * @param startY   the starting y coordinate for this section.
    */
   public void setStartY(int startY)
   {
      // calculate the y difference, and adjust the bounding rectangle.
      int dy = startY - this.startY;
      Rectangle bounds = super.getBoundingRectangle();
      bounds.y += dy;
      super.setBoundingRectangle(bounds);

      // Save the new y coordinate.
      this.startY = startY;
   }

   /**
    * @return whether the section needs to be layed out,
    */
   public boolean isLayoutNeeded()
   {
      return this.isLayoutNeeded;
   }

   /**
    * marks the section as needing to be layed out.
    */
   public void setLayoutNeeded()
   {
      xPositions.clear();
      isLayoutNeeded = true;
   }

   /**
    * @return the bounding rectangle for the section.
    */
   @Override
   public Rectangle getBoundingRectangle()
   {
      if (this.isLayoutNeeded)
      {
         Rectangle boundingRectangle = calculateBoundingRectangle();
         super.setBoundingRectangle(boundingRectangle);
         this.isLayoutNeeded = false;
      }
      return super.getBoundingRectangle();
   }

   /**
    * calculates the bounding rectangle for this section, the main thing being determined is the height of the section, as the width, margins, etc., are all fixed or derived.
    * <br/><br/>
    * @return the bounding rectangle for the section.
    */
   private Rectangle calculateBoundingRectangle()
   {
      // remember the y coordinates of the first and last staff lines
      double nextY = this.startY;

      // paint everything that appears before the staff
      final boolean isScreenPaint = false;
      nextY += paintPreStaff(GraphicsStub.INSTANCE, isScreenPaint);

      // draw each staff
      for (PaintableStaff staff : this.paintableStaffs)
      {
         // paint this staff
         double staffHeight = staff.paint(GraphicsStub.INSTANCE, isScreenPaint);

         // start the next staff right after the last one
         nextY += staffHeight;
      }

      // construct the bounding rectangle
      Rectangle bounds = new Rectangle();
      bounds.x = LEFT_MARGIN;
      bounds.y = this.startY;
      bounds.height = (int)Math.ceil(nextY - this.startY);
      bounds.width = WIDTH;

      return bounds;
   }

   /**
    * paints this section in the given graphics context.
    * <br><br/>
    * @param g               graphics context
    * @param isScreenPaint   true if painting to screen device, false otherwise (e.g., printer)
    * @param transform       the default transformation matrix.  todo: ???
    */
   public void paint(Graphics2D g, boolean isScreenPaint, AffineTransform transform)
   {
      // Get the cursor map and section bounds, which are used for indexing
      // this section.
      CursorMap cursorMap = this.paintableSong.getCursorMap();
      Rectangle2D sectionBounds = getBoundingRectangle();

      if (isScreenPaint)
      {
         // Add this section to the map.
         cursorMap.addSection(g, transform, sectionBounds, this.sectionIndex);

         // Draw the section's bounding box.
         g.setColor(Util.GRAY);
         g.draw(sectionBounds);
         g.setColor(Color.BLACK);
      }

      // Transform to the section's origin (top left corner).
      g.translate(sectionBounds.getX(), sectionBounds.getY());

      // Paint everything that appears before the staff.
      double nextY = paintPreStaff(g, isScreenPaint);

      // Tracks the first and last y coordinates of the staff lines.
      double firstY = Double.MAX_VALUE;
      double lastY = Double.MIN_VALUE;

      // Draw each staff.
      for (PaintableStaff staff : this.paintableStaffs)
      {
         // Paint this staff.
         g.translate(0.0, nextY);
         double staffHeight = staff.paint(g, isScreenPaint);
         g.translate(0.0, -nextY);

         // Adjust the first and last staff line y coordinates.
         firstY = Math.min(firstY, nextY + staff.firstY);
         lastY = Math.max(lastY, nextY + staff.lastY);

         // Add this staff rectangle to the cursor map.
         if (isScreenPaint)
         {
            // Add this staff to the cursor map.
            Rectangle2D staffRectangle = new Rectangle2D.Double(LEFT_MARGIN, nextY, WIDTH, staffHeight);
            cursorMap.addStaff(g, transform, staffRectangle, this.sectionIndex, staff.getStaffIndex());
         }

         // Start the next staff right after the last one.
         nextY += staffHeight;
      }

      // Add the barlines and measures to the cursor map.
      if (isScreenPaint)
      {
         int lastBarlineIndex = getBarlines().size() - 1;
         for (int barlineIndex = 0;
              barlineIndex < lastBarlineIndex;
              ++barlineIndex)
         {
            Barline barline = getBarlines().get(barlineIndex);
            double barlineX = this.xPositions.get(barline.getPosition());
            Barline nextBarline = getBarlines().get(barlineIndex + 1);
            double nextBarlineX = this.xPositions.get(nextBarline.getPosition());
            Rectangle2D rect = new Rectangle2D.Double(barlineX,                    // x
                                                      0.0,                         // y
                                                      nextBarlineX - barlineX,     // width,
                                                      sectionBounds.getHeight());  // height
            cursorMap.addMeasure(g, rect, this.sectionIndex, this.measureNumber, barlineIndex);
         }

         // Add the positions to the cursor map.
         int lastPosIndex = this.xPositions.size() - 1;
         double lastRightX = 0.0;
         for (int posIndex = 0; posIndex < lastPosIndex; ++posIndex)
         {
            double leftX = lastRightX;
            double posX = this.xPositions.get(posIndex);
            double nextPosX = this.xPositions.get(posIndex + 1);
            double rightX;
            if (posIndex == lastPosIndex - 1)
            {
               rightX = nextPosX;
            }
            else
            {
               rightX = posX + (nextPosX - posX)/2.0;
            }
            double y = 0.0;
            double width = 2.0*Math.min(posX - leftX, rightX - posX);
            Rectangle2D rect = new Rectangle2D.Double(posX - width/2.0,
                                      y,
                                      width,                    // width
                                      sectionBounds.getHeight() // height
                                      );
            cursorMap.addPosition(g, rect, this.sectionIndex, posIndex);
            lastRightX = rightX;
         }
      }

      // Draw initial bar line (spans height of entire section).
      g.draw(new Line2D.Double(0.0, firstY, 0.0, lastY));

      // Restore transformation matrix.
      g.translate(-sectionBounds.getX(), -sectionBounds.getY());
   }

   /**
    * Paints elements that appear at the start of the section, but before the first staff.
    * @param g                the graphics context in which to do the paint
    * @param isScreenPaint    true if painting to the screen, false otherwise
    * @return                 height of the pre-staff portion, in pixels
    */
   private double paintPreStaff(Graphics2D g, boolean isScreenPaint)
   {
      // TODO must finalize the X spacing to avoid crowding
      if (this.xPositions.isEmpty())
      {
         double x = WIDTH;
         int maxPosition = this.getNumDrawingPositions();
         while (maxPosition-- > 0)
         {
            this.xPositions.add(0, x);
            x -= getPositionSpacing();
         }
         this.xPositions.add(0, 0.0);
      }

      double height = 0.0;

      // - rehearsal sign
      double dy = drawRehearsalSigns(g);
      if (dy > 0.0 && isScreenPaint)
      {
         Util.drawDashedLine(g, 0, dy, WIDTH, dy);
      }
      height += dy;

      // - tempo marker
      g.translate(0, height);
      dy = drawTempoMarkers(g);
      if (dy > 0.0 && isScreenPaint)
      {
         Util.drawDashedLine(g, 0, dy, WIDTH, dy);
      }
      g.translate(0, -height);
      height += dy;

      // - alternate ending
      g.translate(0, height);
      dy = drawAlternateEndings(g);
      if (dy > 0.0 && isScreenPaint)
      {
         Util.drawDashedLine(g, 0, dy, WIDTH, dy);
      }
      g.translate(0, -height);
      height += dy;

      // Assign the extra spacing.
      setExtraSpacing((short)Math.ceil(height));

      return height;
   }

   /**
    * draws the rehearsal signs at the top of the section.
    * <br/><br/>
    * @return the height (in pixels) of the rehearsal signs.
    * <br/><br/>
    * @param graphics   graphics context.
    */
   private double drawRehearsalSigns(Graphics2D graphics)
   {
      double dy = 0.0;      // height (in pixels) of the tallest rehearsal sign for this section

      for (Barline barline : barlines)
      {
         RehearsalSign rehearsalSign = barline.getRehearsalSign();
         if (rehearsalSign != null)
         {
            double x = this.xPositions.get(barline.getPosition());
            graphics.translate(x, 0);
            dy = Math.max(dy, drawRehearsalSign(graphics, rehearsalSign));
            graphics.translate(-x, 0);
         }
      }
      return dy;
   }

   /**
    * draws a rehearsal sign.
    * <br/><br/>
    * @return the height (in pixels) of the rehearsal sign.
    * <br/><br/>
    * @param graphics        graphics context.
    * @param rehearsalSign   rehearsal sign to draw.
    */
   private double drawRehearsalSign(Graphics2D graphics, RehearsalSign rehearsalSign)
   {
      // get the font
      Font        font    = FontManager.SMALL_BOLD_FONT;
      FontMetrics metrics = graphics.getFontMetrics(font);
      double      dy      = metrics.getHeight();

      // draw the letter symbol
      String      text    = Character.toString(rehearsalSign.getLetter());
      double      advance = metrics.getStringBounds("X", graphics).getWidth();
      double      width   = 2.0 * advance;
      Rectangle2D rect    = metrics.getStringBounds(text, graphics);
      double      x       = (width - rect.getWidth())/2.0 + 1;
      double      y       = dy - metrics.getDescent();

      graphics.setFont(font);
      graphics.drawString(text, (float)x, (float)y);

      // draw the square around the letter symbol
      graphics.draw(new Rectangle2D.Double(0.0, 0.0, width, dy));

      // draw the description
      x = width + advance;
      graphics.drawString(rehearsalSign.getDescription(), (float)x, (float)y);

      return dy;
   }

   /**
    * draws the tempo markers for the section.
    * <br/><br/>
    * @return the height (in pixels) of the tempo markers.
    * <br/><br/>
    * @param graphics   graphics context.
    */
   private double drawTempoMarkers(Graphics2D graphics)
   {
      double        dy       = 0.0;
      List<Barline> barlines = this.barlines;

      for (int i=0; i<barlines.size(); ++i)
      {
         Barline     barline     = barlines.get(i);
         TempoMarker tempoMarker = barline.getTempoMarker();

         if (tempoMarker != null)
         {
            int index = barline.getPosition();
            if (i + 1 < barlines.size())
            {
               // tempo marker should be drawn above the measure's first position, if possible.
               Barline nextBarline = barlines.get(i + 1);
               if (index + 1 < nextBarline.getPosition())
                  index += 1;
            }
            final double x = this.xPositions.get(index);
            graphics.translate(x, 0);
            dy = Math.max(dy, drawTempoMarker(graphics, tempoMarker));
            graphics.translate(-x, 0);
         }
      }
      return dy;
   }

   /**
    * draws a single tempo marker.
    * <br/><br/>
    * @return the height (in pixels) of the tempo marker.
    * <br/><br/>
    * @param graphics   graphics context.
    * @param marker     tempo marker to draw.
    */
   private double drawTempoMarker(Graphics2D graphics, TempoMarker tempoMarker)
   {
      // get the font
      Font        font    = FontManager.SMALL_BOLD_FONT;
      FontMetrics metrics = graphics.getFontMetrics(font);
      double      dy      = metrics.getHeight();

      // draw the description
      double x           = 0.0;
      double y           = dy - metrics.getDescent();
      String description = tempoMarker.getDescription();

      if (description != null)
      {
         graphics.setFont(font);
         graphics.drawString(description, (float)x, (float)y);
         Rectangle2D rect = metrics.getStringBounds(description, graphics);
         x += rect.getWidth();
      }

      // draw the note image
      Dimension2D size = MusicNotation.MINI_NOTE.paint(graphics, x, y);
      x += size.getWidth();

      // draw the tempo number
      font = FontManager.SMALL_BOLD_FONT;
      graphics.setFont(font);
      graphics.drawString(" = " + tempoMarker.getBeatsPerMinute(), (float)x, (float)y);

      return dy;
   }

   /**
    * draws the the alternate endings for the section.
    * <br/><br/>
    * @return the height (in pixels) of the alternate ending.
    * <br/><br/>
    * @param graphics   graphics context.
    */
   private double drawAlternateEndings(Graphics2D graphics)
   {
      double        dy       = 0.0;
      List<Barline> barlines = this.barlines;

      for (int i=0; i<barlines.size(); ++i)
      {
         Barline         barline = barlines.get(i);
         AlternateEnding ending  = barline.getAlternateEnding();

         if (ending != null)
         {
            int index = barline.getPosition();
            double x1 = this.xPositions.get(index);
            double x2 = x1;
            if (i + 1 < barlines.size())
            {
               // finish drawing above the next measure's barline.
               Barline nextBarline = barlines.get(i + 1);
               x2 = this.xPositions.get(nextBarline.getPosition());
               if (nextBarline.isRepeat())
               {
                  x2 -= 2.0*PaintableStaff.DOUBLE_BAR_WIDTH;
               }
            }
            graphics.translate(x1, 0);
            dy = Math.max(dy, drawAlternateEnding(graphics, x2 - x1, ending));
            graphics.translate(-x1, 0);
         }
      }
      return dy;
   }

   /**
    * draws the alternate ending in the specified width.
    * <br/><br/>
    * @return the height (in pixels) of the alternate ending numbers (does not include alternate ending border lines).
    * <br/><br/>
    * @param graphics         graphics context for drawing.
    * @param width            width of the border lines drawn around the alternate ending numbers.
    * @param alternateEnding  the alternate ending to draw.
    */
   private double drawAlternateEnding(Graphics2D graphics, double width, AlternateEnding alternateEnding)
   {
      // get the font.
      Font        font    = FontManager.SMALL_FONT;
      FontMetrics metrics = graphics.getFontMetrics(font);

      // the y-coordinate delta is just the font height
      double dy = metrics.getHeight();

      // draw the alternate ending numbers
      String text = alternateEnding.getNumbersAsString();
      double y    = dy - metrics.getDescent();

      graphics.setFont(font);
      graphics.drawString(text, 0.0f, (float)y);

      // draw the borders
      graphics.draw(new Line2D.Double(  0.0, 2.0, width,      2.0));
      graphics.draw(new Line2D.Double(  0.0, 2.0,   0.0, dy - 2.0));
      graphics.draw(new Line2D.Double(width, 2.0, width, dy - 2.0));

      return dy;
   }
}
