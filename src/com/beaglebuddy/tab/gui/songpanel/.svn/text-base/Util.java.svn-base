/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andy wills $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;




/**
 * utility class containing miscellaneous static methods for drawing.
 * <p>
 * the dashed pattern parameter used to draw dashed ot dotted lines is specified in the following way:
 * the first entry holds the length of the opaque dash, while the second entry holds the length of the transparent blank space between the opaque dashes.
 * </p>
 * <p>
 * see the com.beaglebuddy.tab.gui.songpanel.Unicode javadocs for an explanation of supplementary unicode characters.
 * </p>
 */
public class Util
{
   // class members
   private static final Stroke DASHED_STROKE_LINE = new BasicStroke(0.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] {2.0f, 3.0f}, 0.0f);   // dashes are 2 pixels, spaces are 3 pixels
   private static final Stroke DASHED_STROKE_RECT = new BasicStroke(0.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] {1.0f, 1.0f}, 0.0f);   // dashes are 1 pixel , spaces are 1 pixel
   public  static final Color  GRAY               = new Color(0.5f, 0.5f, 0.5f, 0.4f);    // todo: why not use Color.GRAY





   /**
    * draws a dashed line in the specified graphics context.
    * <br/><br/>
    * @param graphics  graphics context for drawing.
    * @param x1        starting x coordinate of line.
    * @param y1        starting y coordinate of line.
    * @param x2        ending   x coordinate of line.
    * @param y2        ending   y coordinate of line.
    */
   public static void drawDashedLine(Graphics2D graphics, double x1, double y1, double x2, double y2)
   {
      // save the current drawing color and stroke
      Stroke oldStroke = graphics.getStroke();
      Color  oldColor  = graphics.getColor ();

      // set the new drawing color and stroke
      graphics.setStroke(DASHED_STROKE_LINE);
      graphics.setColor (GRAY);

      // draw the dashed line
      graphics.draw(new Line2D.Double(x1, y1, x2, y2));

      // restore the old drawing color and stroke
      graphics.setStroke(oldStroke);
      graphics.setColor (oldColor );
   }

   /**
    * Draws a dashed rectangle in the graphics context, with an X through the middle.
    * Used for diagnostic painting.
    * <br/><br/>
    * @param graphics   graphics context for drawing.
    * @param rectangle  the rectangle to be drawn.
    */
   public static void drawDashedRect(Graphics2D graphics, Rectangle2D rectangle)
   {
      // save the current stroke
      Stroke oldStroke = graphics.getStroke();

      // set the new drawing stroke to a dashed line
      graphics.setStroke(DASHED_STROKE_RECT);

      // draw the dashed rectangle
      graphics.draw(rectangle);

      // Draw an X through the box.
      graphics.draw(new Line2D.Double(rectangle.getX(), rectangle.getY(),    rectangle.getMaxX(), rectangle.getMaxY()));
      graphics.draw(new Line2D.Double(rectangle.getX(), rectangle.getMaxY(), rectangle.getMaxX(), rectangle.getY()));

      // restore the old stroke
      graphics.setStroke(oldStroke);
   }

   /**
    * draws the given unicode characters horizontally using the specified font.
    * note: this method handles standard unicode characters as well as the supplementary unicode characters in the range 0x010000 - 0x10FFFF.
    * <br/><br/>
    * @param graphics    graphics context for drawing.
    * @param font        font used for drawing the characters
    * @param dimension   dimension in which to center the text.
    * @param text        unicode characters to be drawn.
    */
   public static void drawText(Graphics2D graphics, Font font, Dimension2D dimension, String text)
   {
      // determine the extent of the text box
      FontMetrics       metrics  = graphics.getFontMetrics(font);
      Rectangle2D       bounds2d = metrics.getStringBounds(text, graphics);

      // determine the extent of the text
      double x = (dimension.getWidth() - bounds2d.getWidth())/2.0;
      double y = bounds2d.getHeight()/2.0 - metrics.getLeading();

      // draw the text
      graphics.setFont(font);
      graphics.drawString(text, (float)x, (float)y);
   }

   /**
    * draws the given text vertically using the specified font.
    * note: each characters in the text must be in the orignal unicode range of 0x0000 - 0xFFFF.
    * <br/><br/>
    * @param graphics   graphics context for drawing.
    * @param font       font used for drawing the text.
    * @param dimension  dimension in which to center the text.
    * @param text       standard unicode text (0x0000 - 0xFFFF)to be drawn.
    */
   public static void drawVerticalText(Graphics2D graphics, Font font,
                                       Dimension2D dimension, String text)
   {
      List<String> strings = new ArrayList<String>();
      for (Character c : text.toCharArray())
      {
         strings.add(c.toString());
      }
      drawVerticalText(graphics, font, dimension, strings);
   }

   /**
    * Draws each strings using the specified font.
    * Each string in the list is centered horizontally, and spaced vertically
    * to fill in the space given by the dimension.
    * <br/><br/>
    * @param graphics
    *    graphics context for drawing.
    * @param font
    *    font used for drawing the strings
    * @param dimension
    *    dimension in which to center the strings
    * @param strings
    *    list of strings to draw in the dimension
    */
   public static void drawVerticalText(Graphics2D graphics, Font font,
                                       Dimension2D dimension,
                                       List<String> strings)
   {
      // determine the extent of the text.
      FontMetrics       metrics    = graphics.getFontMetrics(font);
      double            textHeight = metrics.getAscent();
      double            leading    = metrics.getLeading();
      double            descent    = metrics.getDescent();

      // Vertical offset between each line of text.
      double vOffset = dimension.getHeight()/strings.size();

      // loop through each character
      for (int i = 0; i < strings.size(); ++i)
      {
         // determine the extent of the text box.
         String text = strings.get(i);
         Rectangle2D bounds2d  = metrics.getStringBounds(text, graphics);

         // Get the top and bottom y coordinates that the string is centered in.
         double startY = i*vOffset;
         double stopY  = (i + 1)*vOffset;

         // get how much white space is left in the vertical area.
         double verticalSpace = (stopY - startY) - textHeight;

         // center the text in the area.
         double x = (dimension.getWidth() - bounds2d.getWidth())/2.0;
         double y = stopY - verticalSpace/2.0;

         // Draw the text.
         graphics.setFont(font);
         graphics.drawString(text, (float)x, (float)(y - leading - descent/2.0));
      }
   }

   /**
    * Draws a line of given thickness
    * @param graphics
    *    graphics context
    * @param thickness
    *    how thick the line should be
    * @param x1
    *    X coordinate of line's first point
    * @param y1
    *    Y coordinate of line's first point
    * @param x2
    *    X coordinate of line's second point
    * @param y2
    *    Y coordinate of line's second point
    */
   public static void drawThickLine(Graphics2D graphics,
                                    double thickness,
                                    double x1,
                                    double y1,
                                    double x2,
                                    double y2)
   {
      // Save the current drawing stroke.
      Stroke oldStroke = graphics.getStroke();
      
      // Define a new stroke style based on the given thickness.
      Stroke stroke = new BasicStroke((float)thickness);

      // Draw the line with the defined stroke.
      graphics.setStroke(stroke);
      graphics.draw(new Line2D.Double(x1 + thickness/2.0, y1, x2, y2));
      graphics.setStroke(oldStroke);
   }
}
