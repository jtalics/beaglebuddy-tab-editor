/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andrew Will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import com.beaglebuddy.tab.gui.font.FontManager;
import com.beaglebuddy.tab.gui.mainframe.Preferences;
import com.beaglebuddy.tab.gui.mainframe.SerializablePageFormat;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.song.Song;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.RepaintManager;




/**
 * The SongPanel class contains all the logic needed for painting and printing a song.
 */
public class SongPanel extends JPanel implements Printable, Pageable
{
   private static final long serialVersionUID = 1L;

   /** How much to scale the visible screen. */
   private static final double VIEW_SCALE_FACTOR = 1.0;

   /** The model of the current song. */
   private PaintableSong song;

   /** Last set page format (for printer). */
   private PageFormat pageFormat;

   /** Constructs the song panel given an initial song.
    *  @param initialSong
    */
   public SongPanel(Song initialSong)
   {
      super();
      super.setBackground(Color.WHITE);
      this.song = new PaintableSong(initialSong);
      PrinterJob printJob = PrinterJob.getPrinterJob();
      SerializablePageFormat spf =
         new SerializablePageFormat(printJob.defaultPage());
      this.pageFormat = (SerializablePageFormat)
         Preferences.getObject(Preferences.key_printer_page_format, spf);
   }

   /**
    * Returns the paintable song instance.
    * @return
    */
   public PaintableSong getSong()
   {
      return this.song;
   }

   /** Assign a new song to the panel. Resets all song state information.
    *  @param newSong
    */
   public synchronized void setSong(Song newSong)
   {
      // Assign the new song.
      this.song = new PaintableSong(newSong);

      // Recalculate window size.
      int numSections = song.getScore().getSections().size();
      if (song != null && numSections != 0)
      {
         // Get the minimum size.
         Dimension size = getMinSize();

         // Scale the size to the desired magnification.
         size.setSize(scaleView(size.width), scaleView(size.height));

         // Set window sizes.
         setSize(size);
         setPreferredSize(size);
         setMinimumSize(size);
         setMaximumSize(size);
      }
      this.repaint();
   }

   /**
    * Returns the dimensions of the largest section.
    * @return
    */
   public Dimension getMinSize()
   {
      final int minWidth = 2*PaintableSection.LEFT_MARGIN +
                           PaintableSection.WIDTH;

      SongPanelHeader header = new SongPanelHeader(this.song);
      final int minHeight = (int)Math.ceil(header.getTitleHeight());

      Dimension size = new Dimension(minWidth, minHeight);
      for (Section section : this.song.getScore().getSections())
      {
         if (size.height == 0)
         {
            size.height += PaintableSection.TOP_MARGIN;
         }
         else
         {
            size.height += PaintableSection.INTERSECTION_MARGIN;
         }
         size.height += section.getBoundingRectangle().height;
      }

      return size;
   }

   /**
    * Returns the number of pages in the song.
    * @return
    * @Override
    */
   public int getNumberOfPages()
   {
      // Determine the sections that each page contains.
      Integer[] pages = paginate();
      return pages.length;
   }

   /**
    * Divides the song into pages, using the current page format object.
    * @return the starting section for each page
    */
   private Integer[] paginate()
   {
      List<Integer> pages = new ArrayList<Integer>();
      if (this.song != null)
      {
         // Determine the scale factor that will be applied for all pages.
         double widthInPixels = PaintableSection.WIDTH;
         double widthInPoints = this.pageFormat.getImageableWidth();
         double scaleFactor = widthInPoints/widthInPixels;
         double maxHeightInPoints = this.pageFormat.getImageableHeight();

         // Determine how many sections can fit on the each page.
         // This is determined solely by the vertical constraint of the page.
         int startSection = 0;
         List<Section> sections = song.getScore().getSections();
         while (startSection < sections.size())
         {
            // Sum sections on this page until we reach the last section
            // or the page is full.
            int stopSection = startSection;
            double heightInPoints = 0.0;
            do
            {
               // Add the height of each section.
               int sectionHeightInPixels =
                  sumSectionHeight(startSection, stopSection);

               // Convert the height to points.
               heightInPoints = sectionHeightInPixels*scaleFactor;
            }
            while (++stopSection < sections.size()
                   && heightInPoints < maxHeightInPoints);

            // Don't include the section that caused the loop to terminate.
            --stopSection;

            // If the height was overshot, adjust the stop section.
            if (stopSection > startSection
                && heightInPoints > maxHeightInPoints)
            {
               --stopSection;
            }

            // Add this section index.
            pages.add(new Integer(startSection));

            // Start the next page one section after the last.
            startSection = stopSection + 1;
         }
      }
      return pages.toArray(new Integer[pages.size()]);
   }

   /** Returns the sum of the sections, in pixels, including
    *  margins and headers for each page.
    *  @param startSection
    *  @param stopSection
    *  @return
    */
   private int sumSectionHeight(int startSection, int stopSection)
   {
      // Add the page header.
      double height;
      SongPanelHeader header = new SongPanelHeader(this.song);
      if (startSection == 0)
      {
         height = header.getTitleHeight();
      }
      else
      {
         height = header.getPageNumberHeight(0);
      }

      // Sum the height of each included section.
      List<Section> sections = song.getScore().getSections();
      for (int i = startSection; i <= stopSection; ++i)
      {
         // Add the height of this section.
         height += sections.get(i).getBoundingRectangle().height;

         // Unless this is the first section in the page,
         // add the margin between this section and the last.
         if (i != startSection)
         {
            height += PaintableSection.INTERSECTION_MARGIN;
         }
      }

      return (int)Math.ceil(height);
   }

   /** Assign a new page format object.
    *  Saves this page format as a preference.
    *  @param pageFormat
    */
   public void setPageFormat(PageFormat pageFormat)
   {
      this.pageFormat = new SerializablePageFormat(pageFormat);
      Preferences.setObject(Preferences.key_printer_page_format,
                            this.pageFormat);
   }

   @Override
   public PageFormat getPageFormat(int pageIndex)
      throws IndexOutOfBoundsException
   {
      return this.pageFormat;
   }

   @Override
   public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException
   {
      return this;
   }

   @Override
   public int print(Graphics graphics, PageFormat pf, int pageIndex)
      throws PrinterException
   {
      // Update the page format and repaginate.
      setPageFormat(pf);
      Integer[] pages = paginate();
      if (pages.length == 0 || pageIndex >= pages.length)
      {
         return NO_SUCH_PAGE;
      }

      // Get the first (top) and last (bottom) sections.
      List<PaintableSection> sections =
         this.song.getPaintableScore().getPaintableSections();
      int startSectionIndex = pages[pageIndex].intValue();
      PaintableSection startSection = sections.get(startSectionIndex);
      int stopSectionIndex;
      if (pageIndex + 1 < pages.length)
      {
         stopSectionIndex = pages[pageIndex + 1].intValue() - 1;
      }
      else
      {
         stopSectionIndex = sections.size() - 1;
      }
      PaintableSection stopSection = sections.get(stopSectionIndex);

      // Translate the top-left corner of the start section
      // to the top left corner of the printed page.
      Graphics2D g2d = (Graphics2D)graphics;
      double pageX = this.pageFormat.getImageableX();
      double pageY = this.pageFormat.getImageableY();
      g2d.translate(pageX, pageY);

      // Scale the sections to fit into the printed page.
      double pageWidth = this.pageFormat.getImageableWidth();
      double pageHeight = this.pageFormat.getImageableHeight();
      double sectionX = startSection.getBoundingRectangle().getX();
      double sectionMargin = getMeasureNumberWidth(g2d, stopSection);
      double sectionWidth =
         2.0*sectionMargin + startSection.getBoundingRectangle().getWidth();
      double sectionY = startSection.getBoundingRectangle().getY();
      double sectionHeight = (stopSection.getBoundingRectangle().getY() +
         stopSection.getBoundingRectangle().getHeight()) - sectionY;

      // Account for the header when scaling.
      SongPanelHeader header = new SongPanelHeader(this.song);
      double headerHeight;
      if (startSectionIndex == 0)
      {
         headerHeight = header.getTitleHeight();
      }
      else
      {
         headerHeight = header.getPageNumberHeight(pageIndex + 1);
      }

      double scaleX = pageWidth/sectionWidth;
      double scaleY = pageHeight/(headerHeight + sectionHeight);
      double scale = Math.min(scaleX, scaleY);
      g2d.scale(scale, scale);

      // Turn off double buffering while printing.
      RepaintManager currentManager = RepaintManager.currentManager(this);
      currentManager.setDoubleBufferingEnabled(false);

      try
      {
         // Set the clipping rectangle to include the header only.
         g2d.setClip((int)sectionMargin,
                     (int)0,
                     (int)Math.ceil(sectionWidth + 2.0*sectionMargin),
                     (int)Math.ceil(headerHeight));

         // Print the page header.
         g2d.translate(sectionMargin, 0);
         if (pageIndex == 0)
         {
            header.drawTitle(g2d, sectionWidth - 2.0*sectionMargin);
         }
         else
         {
            header.drawPageNumber(g2d,
                                  sectionWidth - sectionMargin,
                                  pageIndex + 1);
         }
         g2d.translate(-sectionMargin, headerHeight);

         // Calculate the space between the header and the first section.
         Section lastSection;
         int lastSectionIndex = startSectionIndex - 1;
         if (lastSectionIndex >= 0)
         {
            lastSection = sections.get(lastSectionIndex);
         }
         else
         {
            lastSection = null;
         }
         double topMargin = sectionY;
         if (lastSection != null)
         {
            topMargin -= lastSection.getBoundingRectangle().getY() +
                         lastSection.getBoundingRectangle().getHeight();
         }

         // Reset the clipping rectangle to include only the sections being printed.
         g2d.setClip((int)0,
                     (int)topMargin,
                     (int)Math.ceil(sectionWidth + 2.0*sectionX),
                     (int)Math.ceil(sectionHeight));

         // Translate away the left and top margins.
         g2d.translate(-sectionX + sectionMargin, -sectionY + topMargin);

         // Paint the sections.
         final boolean isScreenPaint = false;
         this.paint(g2d, isScreenPaint);
         return(PAGE_EXISTS);
      }
      catch (Exception e)
      {
         return NO_SUCH_PAGE;
      }
      finally
      {
         // Re-enable double buffering
         currentManager.setDoubleBufferingEnabled(true);
      }
   }

   @Override
   public synchronized void paint(Graphics g)
   {
      ((Graphics2D)g).scale(scaleView(1), scaleView(1));
      final boolean isScreenPaint = true;
      paint((Graphics2D)g, isScreenPaint);
   }

   /** Magnifies the dimension by the scale factor.
    *  @param length
    *  @return
    */
   private int scaleView(int length)
   {
      return (int)Math.round(VIEW_SCALE_FACTOR*length);
   }

   /** Paints the song panel.
    *  @param g
    *    graphics context
    *  @param isScreenPaint
    *    true if painting to the screen, false otherwise (e.g. printer device)
    */
   private synchronized void paint(Graphics2D g, boolean isScreenPaint)
   {
      // Remember the initial transformation.
      AffineTransform transform = g.getTransform();

      if (isScreenPaint)
      {
         // Clear everything inside the clipping area.
         Rectangle clipRect = g.getClipBounds();
         g.setColor(Color.WHITE);
         g.fillRect(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
         g.setColor(Color.BLACK);

         // Determine the bounds of the title box.
         g.translate(PaintableSection.LEFT_MARGIN, 0.0);
         SongPanelHeader header = new SongPanelHeader(this.song);
         Rectangle2D textBox = header.drawTitle(g, PaintableSection.WIDTH);
         double dy = textBox.getY() + textBox.getHeight();
         g.translate(-PaintableSection.LEFT_MARGIN, dy);
      }

      // Draw each section that falls within the clipping area.
      for (int sectionNumber = 0;
           sectionNumber < song.getScore().getSections().size();
           ++sectionNumber)
      {
         // The section will be painted if it intersects the clipping plane,
         // or if painting to the screen and it has never been painted before.
         PaintableSection s =
            song.getPaintableScore().getPaintableSection(sectionNumber);
         if (s.getBoundingRectangle().intersects(g.getClipBounds())
             || (isScreenPaint
                 && !this.song.getCursorMap().hasSection(sectionNumber)))
         {
            s.paint(g, isScreenPaint, transform);
         }
      }

      // Draw the cursor.
      if (isScreenPaint)
      {
         drawCursor(g);
      }
   }

   /** Draws the cursor in the given graphics context.
    *  @param g
    */
   private void drawCursor(Graphics2D g)
   {
      // Obtain the current location.
      Location currentLocation = this.song.getCurrentLocation();
      if (currentLocation != null)
      {
         try
         {
            this.song.getCursorMap().drawCursor(g, currentLocation);
         }
         catch (Exception e)
         {
//            System.err.println(e.getMessage());
         }
      }
   }

   /**
    * Returns the width of the measure number field, in pixels.
    * @param g
    * @param stopSection
    * @return
    */
   private int getMeasureNumberWidth(Graphics2D g, PaintableSection stopSection)
   {
      // Draw each section that falls within the clipping area.
      int measureNumber = stopSection.getFirstMeasureNumber();

      // Convert measure number to string.
      String text = Integer.toString(measureNumber);

      // Get the font.
      Font font = FontManager.SMALL_FONT;

      // Determine the extent of the text box.
      FontMetrics metrics = g.getFontMetrics(font);
      Rectangle2D bounds2d = metrics.getStringBounds(text, g);

      // Determine the extent of the text.
      int textWidth = (int)Math.ceil(bounds2d.getWidth());
      return textWidth;
   }
}
