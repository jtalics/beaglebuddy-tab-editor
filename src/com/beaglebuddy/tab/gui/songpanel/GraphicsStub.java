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
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;




/**
 * This class is a stand-in for an actual graphics context, used for calculating
 * the bounds of a PaintableSection.
 * @author ANDY
 */
public class GraphicsStub extends Graphics2D
{
   /** Singleton instance of graphics stub. */
   public static final GraphicsStub INSTANCE = new GraphicsStub();

   private Color background =
      Color.WHITE;
   private Composite composite =
      AlphaComposite.getInstance(AlphaComposite.CLEAR);
   private FontRenderContext fontRenderContext =
      new FontRenderContext(new AffineTransform(), false, false);
   private Paint paint = Color.BLACK;
   private Stroke stroke = new BasicStroke();
   private AffineTransform transform = new AffineTransform();
   private Rectangle clip = new Rectangle();
   private Color color = Color.BLACK;
   private Font font = FontManager.MEDIUM_FONT;

   /** Use INSTANCE field. */
   private GraphicsStub()
   {
   }

   @Override
   public void addRenderingHints(Map<?, ?> hints)
   {
   }

   @Override
   public void clip(Shape s)
   {
   }

   @Override
   public void draw(Shape s)
   {
   }

   @Override
   public void drawGlyphVector(GlyphVector g, float x, float y)
   {
   }

   @Override
   public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs)
   {
      return true;
   }

   @Override
   public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y)
   {
   }

   @Override
   public void drawRenderableImage(RenderableImage img, AffineTransform xform)
   {
   }

   @Override
   public void drawRenderedImage(RenderedImage img, AffineTransform xform)
   {
   }

   @Override
   public void drawString(String str, int x, int y)
   {
   }

   @Override
   public void drawString(String str, float x, float y)
   {
   }

   @Override
   public void drawString(AttributedCharacterIterator iterator, int x, int y)
   {
   }

   @Override
   public void drawString(AttributedCharacterIterator iterator, float x, float y)
   {
   }

   @Override
   public void fill(Shape s)
   {
   }

   @Override
   public Color getBackground()
   {
      return this.background;
   }

   @Override
   public Composite getComposite()
   {
      return this.composite;
   }

   @Override
   public GraphicsConfiguration getDeviceConfiguration()
   {
      return null;
   }

   @Override
   public FontRenderContext getFontRenderContext()
   {
      return this.fontRenderContext;
   }

   @Override
   public Paint getPaint()
   {
      return this.paint;
   }

   @Override
   public Object getRenderingHint(Key hintKey)
   {
      return null;
   }

   @Override
   public RenderingHints getRenderingHints()
   {
      return null;
   }

   @Override
   public Stroke getStroke()
   {
      return this.stroke;
   }

   @Override
   public AffineTransform getTransform()
   {
      return this.transform;
   }

   @Override
   public boolean hit(Rectangle rect, Shape s, boolean onStroke)
   {
      return false;
   }

   @Override
   public void rotate(double theta)
   {
   }

   @Override
   public void rotate(double theta, double x, double y)
   {
   }

   @Override
   public void scale(double sx, double sy)
   {
   }

   @Override
   public void setBackground(Color color)
   {
      this.background = color;
   }

   @Override
   public void setComposite(Composite comp)
   {
      this.composite = comp;
   }

   @Override
   public void setPaint(Paint paint)
   {
      this.paint = paint;
   }

   @Override
   public void setRenderingHint(Key hintKey, Object hintValue)
   {
   }

   @Override
   public void setRenderingHints(Map<?, ?> hints)
   {
   }

   @Override
   public void setStroke(Stroke s)
   {
      this.stroke = s;
   }

   @Override
   public void setTransform(AffineTransform Tx)
   {
      this.transform = Tx;
   }

   @Override
   public void shear(double shx, double shy)
   {
   }

   @Override
   public void transform(AffineTransform Tx)
   {
   }

   @Override
   public void translate(int x, int y)
   {
   }

   @Override
   public void translate(double tx, double ty)
   {
   }

   @Override
   public void clearRect(int x, int y, int width, int height)
   {
   }

   @Override
   public void clipRect(int x, int y, int width, int height)
   {
   }

   @Override
   public void copyArea(int x, int y, int width, int height, int dx, int dy)
   {
   }

   @Override
   public Graphics create()
   {
      return null;
   }

   @Override
   public void dispose()
   {
   }

   @Override
   public void drawArc(int x, int y, int width, int height, int startAngle,
                       int arcAngle)
   {
   }

   @Override
   public boolean drawImage(Image img, int x, int y, ImageObserver observer)
   {
      return false;
   }

   @Override
   public boolean drawImage(Image img, int x, int y, Color bgcolor,
                            ImageObserver observer)
   {
      return false;
   }

   @Override
   public boolean drawImage(Image img, int x, int y, int width, int height,
                            ImageObserver observer)
   {
      return false;
   }

   @Override
   public boolean drawImage(Image img, int x, int y, int width, int height,
                            Color bgcolor, ImageObserver observer)
   {
      return false;
   }

   @Override
   public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
                            int sx1, int sy1, int sx2, int sy2,
                            ImageObserver observer)
   {
      return false;
   }

   @Override
   public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
                            int sx1, int sy1, int sx2, int sy2, Color bgcolor,
                            ImageObserver observer)
   {
      return false;
   }

   @Override
   public void drawLine(int x1, int y1, int x2, int y2)
   {
   }

   @Override
   public void drawOval(int x, int y, int width, int height)
   {
   }

   @Override
   public void drawPolygon(int[] points, int[] points2, int points3)
   {
   }

   @Override
   public void drawPolyline(int[] points, int[] points2, int points3)
   {
   }

   @Override
   public void drawRoundRect(int x, int y, int width, int height, int arcWidth,
                             int arcHeight)
   {
   }

   @Override
   public void fillArc(int x, int y, int width, int height, int startAngle,
                       int arcAngle)
   {
   }

   @Override
   public void fillOval(int x, int y, int width, int height)
   {
   }

   @Override
   public void fillPolygon(int[] points, int[] points2, int points3)
   {
   }

   @Override
   public void fillRect(int x, int y, int width, int height)
   {
   }

   @Override
   public void fillRoundRect(int x, int y, int width, int height, int arcWidth,
                             int arcHeight)
   {
   }

   @Override
   public Shape getClip()
   {
      return this.clip;
   }

   @Override
   public Rectangle getClipBounds()
   {
      return this.clip;
   }

   @Override
   public Color getColor()
   {
      return this.color;
   }

   @Override
   public Font getFont()
   {
      return this.font;
   }

   @Override
   public FontMetrics getFontMetrics(Font f)
   {
      return new FontMetricsStub(f);
   }

   @Override
   public void setClip(Shape clip)
   {
   }

   @Override
   public void setClip(int x, int y, int width, int height)
   {
   }

   @Override
   public void setColor(Color c)
   {
      this.color = c;
   }

   @Override
   public void setFont(Font font)
   {
      this.font = font;
   }

   @Override
   public void setPaintMode()
   {
   }

   @Override
   public void setXORMode(Color c1)
   {
   }
}
