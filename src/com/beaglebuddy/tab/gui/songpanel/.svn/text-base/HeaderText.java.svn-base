/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andrew Will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



/**
 * Class used for encapsulating a formatted line of text.
 */
public class HeaderText
{
   /** Specifies horizontal alignment of text. */
   public enum Alignment
   {
      LEFT,
      CENTER,
      RIGHT
   }

   /** Indicates a newline should follow this text. */
   public static final boolean IS_EOL = true;

   /** Indicates no newline should follow this text. */
   public static final boolean IS_NOT_EOL = false;

   private String    text;
   private Font      font;
   private Alignment alignment;
   private boolean   isEol;

   /**
    * Constructs a new header text object.
    * @param text string associated with this line
    * @param font font used to paint text
    * @param alignment horizontal alignment of text
    * @param isEol if true, a newline should follow the painting of this text
    */
   public HeaderText(String text,
                     Font font,
                     Alignment alignment,
                     boolean isEol)
   {
      this.text = text;
      this.font = font;
      this.alignment = alignment;
      this.isEol = isEol;
   }

   /** Returns the text of this object.
    *  @return
    */
   public String getText()
   {
      return text;
   }

   /** Assign new text to this object.
    *  @param text
    */
   public void setText(String text)
   {
      this.text = text;
   }

   /** Returns the font used to paint this object.
    *  @return
    */
   public Font getFont()
   {
      return font;
   }

   /** Assigns a new font for this object.
    *  @param font
    */
   public void setFont(Font font)
   {
      this.font = font;
   }

   /** Returns the horizontal alignment for this object.
    *  @return
    */
   public Alignment getAlignment()
   {
      return alignment;
   }

   /** Assigns a new alignment for this object.
    *  @param alignment
    */
   public void setAlignment(Alignment alignment)
   {
      this.alignment = alignment;
   }

   /** Returns true if a newline will be added following painting this line.
    *  @return
    */
   public boolean isEol()
   {
      return isEol;
   }

   /** Assigns a new EOL policy for this object.
    *  @param isEol
    */
   public void setEol(boolean isEol)
   {
      this.isEol = isEol;
   }

   /**
    * Returns the width occupied by this header.
    * @return
    */
   public double getWidth()
   {
      Graphics2D g = GraphicsStub.INSTANCE;
      FontMetrics metrics = g.getFontMetrics(this.font);
      Rectangle2D bounds = metrics.getStringBounds(this.text, g);
      return bounds.getWidth();
   }

   /**
    * Paints this header text in the given graphics context.
    * @param g
    * @param boundRectangle
    */
   public void paint(Graphics2D g,
                     Rectangle2D boundRectangle)
   {
      // Get the clipping rectangle.
      Rectangle2D clipRect = g.getClipBounds();

      // Determine the extent of the text box.
      FontMetrics metrics = g.getFontMetrics(this.font);
      Rectangle2D textBounds2d = metrics.getStringBounds(this.text, g);
      Rectangle2D bounds = new Rectangle();
      bounds.setRect(0.0,
                     boundRectangle.getHeight(),
                     textBounds2d.getWidth(),
                     textBounds2d.getHeight());
      if (bounds.intersects(clipRect))
      {
         // Paint the line of text.
         double leftMargin;
         if (this.alignment == HeaderText.Alignment.LEFT)
         {
            leftMargin = 0.0;
         }
         else if (this.alignment == HeaderText.Alignment.RIGHT)
         {
            leftMargin = boundRectangle.getWidth() - bounds.getWidth();
         }
         else // alignment is HeaderText.Alignment.CENTER:
         {
            leftMargin = (boundRectangle.getWidth() - bounds.getWidth())/2.0;
         }
         double topMargin = boundRectangle.getHeight() + metrics.getAscent();
         g.setFont(font);
         g.drawString(this.text, (float)leftMargin, (float)topMargin);
      }

      // Add an eol, if needed.
      if (this.isEol)
      {
         double newHeight = boundRectangle.getHeight() + bounds.getHeight();
         boundRectangle.setRect(boundRectangle.getX(),
                                boundRectangle.getY(),
                                boundRectangle.getWidth(),
                                newHeight);
      }
   }

   /**
    * Returns a new list containing this header text split into a new list
    * formatted for the width.
    * @param maxWidth
    * @return
    */
   public List<HeaderText> reformat(double maxWidth)
   {
      LinkedList<HeaderText> headers = new LinkedList<HeaderText>();
      HeaderText header = new HeaderText("", this.font, this.alignment, this.isEol);
      headers.add(header);
      LinkedList<String> parts = new LinkedList<String>();
      parts.addAll(Arrays.asList(this.text.split(" ")));

      // Proceed through all words in the header.
      while (!parts.isEmpty())
      {
         // Add the next word to the header.
         String oldText = header.text;
         header.text = header.text + parts.getFirst();
         if (oldText.length() == 0 || header.getWidth() <= maxWidth)
         {
            // Commit the word to this header and suffix with a space.
            parts.removeFirst();
            header.text = header.text + ' ';
         }
         else
         {
            header.text = oldText;
            header = new HeaderText("", this.font, this.alignment, this.isEol);
            headers.add(header);
         }
      }

      return headers;
   }
}
