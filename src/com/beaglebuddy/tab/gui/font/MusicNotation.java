/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andrew Will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.font;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.beaglebuddy.tab.gui.songpanel.GraphicsStub;
import com.beaglebuddy.tab.gui.songpanel.PaintableStaff;




/**
 * Encapsulates logic for drawing musical notation symbols.
 */
public enum MusicNotation
{
   ACCIDENTAL_FLAT   (BeaglebuddyFont.getFont(23), BeaglebuddyFont.ACCIDENTAL_FLAT   , new Point2D.Double(1.0 ,  0.0), new Dimension( 2,  0)),
   ACCIDENTAL_NATURAL(BeaglebuddyFont.getFont(23), BeaglebuddyFont.ACCIDENTAL_NATURAL, new Point2D.Double(1.0 ,  0.0), new Dimension( 2,  0)),
   ACCIDENTAL_SHARP  (BeaglebuddyFont.getFont(23), BeaglebuddyFont.ACCIDENTAL_SHARP  , new Point2D.Double(1.0 ,  0.0), new Dimension( 2,  0)),

   CLEF_BASS         (BeaglebuddyFont.getFont(23), BeaglebuddyFont.CLEF_BASS         , new Point2D.Double(0.0 ,  7.0), new Dimension( 0,  0)),
   CLEF_DRUM         (BeaglebuddyFont.getFont(25), BeaglebuddyFont.CLEF_DRUM         , new Point2D.Double(0.0 , 12.0), new Dimension( 0,  0)),
   CLEF_TREBLE       (BeaglebuddyFont.getFont(23), BeaglebuddyFont.CLEF_TREBLE       , new Point2D.Double(0.0 , 18.0), new Dimension( 0,  0)),

   MINI_FLAT         (BeaglebuddyFont.getFont(14), BeaglebuddyFont.ACCIDENTAL_FLAT   , new Point2D.Double(0.0 , -3.0), new Dimension( 1,  0)),
   MINI_SHARP        (BeaglebuddyFont.getFont(16), BeaglebuddyFont.ACCIDENTAL_SHARP  , new Point2D.Double(0.25, -4.5), new Dimension( 0,  0)),
   MINI_NOTE         (BeaglebuddyFont.getFont(14), BeaglebuddyFont.NOTEHEAD_BLACK    , new Point2D.Double(          ), new Dimension(-1, -2))
   {
      @Override
      public Dimension2D paint(Graphics2D g)
      {
         Dimension2D size = super.paint(g);
         g.draw(new Line2D.Double(size.getWidth(), 0.0, size.getWidth(), -size.getHeight()));
         return size;
      }
   },
   NOTE_NO_STEM(BeaglebuddyFont.getFont(21),                BeaglebuddyFont.NOTEHEAD_BLACK)
   {
      @Override
      public Dimension2D paint(Graphics2D g)
      {
         Dimension2D size = super.paint(g);
         size.setSize(size.getWidth(), 0.0);
         return size;
      }
   },
   NOTE_HALF(BeaglebuddyFont.getFont(21),            BeaglebuddyFont.NOTEHEAD_HALF)
   {
      @Override
      public Dimension2D paint(Graphics2D g)
      {
         Dimension2D size = super.paint(g);
         size.setSize(size.getWidth(), 0.0);
         return size;
      }
   },

   NOTE_WHOLE     (BeaglebuddyFont.getFont(18), BeaglebuddyFont.NOTEHEAD_WHOLE                      ),
   FLAG_DOWN_128TH(BeaglebuddyFont.getFont(32), BeaglebuddyFont.QUAVER_DOWN_ONE_HUNDRED_TWENTY_EIGTH),
   FLAG_DOWN_64TH (BeaglebuddyFont.getFont(32), BeaglebuddyFont.QUAVER_DOWN_SIXTY_FOURTH            ),
   FLAG_DOWN_32ND (BeaglebuddyFont.getFont(32), BeaglebuddyFont.QUAVER_DOWN_THIRTY_SECOND           ),
   FLAG_DOWN_16TH (BeaglebuddyFont.getFont(32), BeaglebuddyFont.QUAVER_DOWN_SIXTEENTH               ),
   FLAG_DOWN_8TH  (BeaglebuddyFont.getFont(32), BeaglebuddyFont.QUAVER_DOWN_EIGTH                   ),
   FLAG_UP_128TH  (BeaglebuddyFont.getFont(21), BeaglebuddyFont.QUAVER_UP_ONE_HUNDRED_TWENTY_EIGTH  ),
   FLAG_UP_64TH   (BeaglebuddyFont.getFont(21), BeaglebuddyFont.QUAVER_UP_SIXTY_FOURTH              ),
   FLAG_UP_32ND   (BeaglebuddyFont.getFont(21), BeaglebuddyFont.QUAVER_UP_THIRTY_SECOND             ),
   FLAG_UP_16TH   (BeaglebuddyFont.getFont(21), BeaglebuddyFont.QUAVER_UP_SIXTEENTH                 ),
   FLAG_UP_8TH    (BeaglebuddyFont.getFont(21), BeaglebuddyFont.QUAVER_UP_EIGTH                     ),
   REST_128TH     (BeaglebuddyFont.getFont(22), BeaglebuddyFont.REST_ONE_HUNDRED_TWENTY_EIGTH       ),
   REST_64TH      (BeaglebuddyFont.getFont(22), BeaglebuddyFont.REST_SIXTY_FOURTH                   ),
   REST_32ND      (BeaglebuddyFont.getFont(22), BeaglebuddyFont.REST_THIRTY_SECOND                  ),
   REST_16TH      (BeaglebuddyFont.getFont(22), BeaglebuddyFont.REST_SIXTEENTH                      ),
   REST_8TH       (BeaglebuddyFont.getFont(22), BeaglebuddyFont.REST_EIGTH                          ),
   REST_QUARTER   (BeaglebuddyFont.getFont(22), BeaglebuddyFont.REST_QUARTER                        ),
// REST_HALF      (BeaglebuddyFont.getFont(22), BeaglebuddyFont.REST_HALF                           ),
// REST_WHOLE     (BeaglebuddyFont.getFont(22), BeaglebuddyFont.REST_WHOLE                          ),
   REST_HALF
   {
      @Override
      public Dimension2D paint(Graphics2D g)
      {
         // Determine the width of the rest.
         double width = NOTE_HALF.paint(GraphicsStub.INSTANCE).getWidth();
         double y1 = -0.5*PaintableStaff.PIXELS_PER_STANDARD_SPACE;
         double y2 = 0.0;
         Rectangle2D rect = new Rectangle2D.Double(0.0, y1, width, y2 - y1);
         g.draw(rect);
         g.fill(rect);
         return rect.getBounds().getSize();
      }
   },
   REST_WHOLE
   {
      @Override
      public Dimension2D paint(Graphics2D g)
      {
         // Determine the width of the rest.
         double width = NOTE_WHOLE.paint(GraphicsStub.INSTANCE).getWidth();
         double y1 = -1.0*PaintableStaff.PIXELS_PER_STANDARD_SPACE;
         double y2 = -0.5*PaintableStaff.PIXELS_PER_STANDARD_SPACE;
         Rectangle2D rect = new Rectangle2D.Double(0.0, y1, width, y2 - y1);
         g.draw(rect);
         g.fill(rect);
         return rect.getBounds().getSize();
      }
   },

   TIME_SIGNATURE_COMMON(BeaglebuddyFont.getFont(           24), BeaglebuddyFont.TIME_SIGNATURE_COMMON_TIME                                                     ),
   TIME_SIGNATURE_CUT   (BeaglebuddyFont.getFont(           24), BeaglebuddyFont.TIME_SIGNATURE_CUT_TIME                                                        ),
   TIME_SIGNATURE_0     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_0     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0)),
   TIME_SIGNATURE_1     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_1     ,  new Point2D.Double(1.0, 0.0), new Dimension(1, 0)),
   TIME_SIGNATURE_2     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_2     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0)),
   TIME_SIGNATURE_3     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_3     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0)),
   TIME_SIGNATURE_4     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_4     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0)),
   TIME_SIGNATURE_5     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_5     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0)),
   TIME_SIGNATURE_6     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_6     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0)),
   TIME_SIGNATURE_7     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_7     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0)),
   TIME_SIGNATURE_8     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_8     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0)),
   TIME_SIGNATURE_9     (BeaglebuddyFont.getFont(Font.BOLD, 22), BeaglebuddyFont.TIME_SIGNATURE_LARGE_9     ,  new Point2D.Double(1.0, 0.0), new Dimension(2, 0));





   // data members
   private final Font      font;             // font used to draw the music symbol
   private final char      ch;               // code point in the beaglebuddy font for the music symbol
   private final Point2D   positionDelta;    // todo: ?
   private final Dimension sizeDelta;        // todo: ?


   /**
    * default constructor.
    */
   private MusicNotation()
   {
      this(null, ' ', null, null);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param font   font used to draw the music symbol.
    * @param ch     code point in the beaglebuddy font for the music symbol.
    */
   private MusicNotation(Font font, char ch)
   {
      this(font, ch, new Point2D.Double(), new Dimension());
   }

   /**
    * constructor.
    * <br/><br/>
    * @param font            font used to draw the music symbol.
    * @param ch              code point in the beaglebuddy font for the music symbol.
    * @param positionDelta   todo: ?
    * @param sizeDelta       todo: ?
    */
   private MusicNotation(Font font, char ch, Point2D positionDelta, Dimension sizeDelta)
   {
      this.font          = font;
      this.ch            = ch;
      this.positionDelta = positionDelta;
      this.sizeDelta     = sizeDelta;
   }

   /**
    * draws the musical symbol.
    * <br/><br/>
    * @return the dimension (in pixels) of the symbol.
    * <br/><br/>
    * @param graphics   graphics context.
    */
   public Dimension2D paint(Graphics2D graphics)
   {
      // save the current font so that it can be restored later
      Font oldFont = graphics.getFont();

      // switch to the new font
      graphics.setFont(font);

      // Adjust the clip to trim the marker tail.
//      Shape saveClip = g.getClip();
//      Rectangle2D newClip = new Rectangle2D.Double(g.getClipBounds().getX(),
//                                0.0, // y
//                                g.getClipBounds().getWidth(),
//                                g.getClipBounds().getHeight());
//      g.clip(newClip);

      String text = Character.toString(ch);
      graphics.translate ( positionDelta.getX(),  positionDelta.getY());
      graphics.drawString(text, 0.0f, 0.0f);
      graphics.translate (-positionDelta.getX(), -positionDelta.getY());
//      g.setClip(saveClip);

      // determine the dimensions occupied by the symbol.
      FontMetrics metrics = graphics.getFontMetrics();
      Rectangle2D rect = metrics.getStringBounds(text, graphics);

      // restore the old font
      graphics.setFont(oldFont);

      // return the symbol size
      Dimension2D size = new Dimension();
      size.setSize(rect.getWidth() + this.sizeDelta.getWidth(), rect.getHeight() + this.sizeDelta.getHeight());

      return size;
   }

   /**
    * Paints the music symbol at the given x and y coordinates
    * @param g
    *    current graphics context
    * @param x
    *    left x coordinate
    * @param y
    *    bottom y coordinate
    * @return
    *    size occupied by the painted symbol
    */
   public Dimension2D paint(Graphics2D g, double x, double y)
   {
      g.translate(x, y);
      Dimension2D size = paint(g);
      g.translate(-x, -y);
      return size;
   }
}
