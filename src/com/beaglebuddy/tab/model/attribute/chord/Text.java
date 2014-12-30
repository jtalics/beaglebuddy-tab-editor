/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.chord;

import com.beaglebuddy.tab.model.FontSetting;
import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Rectangle;
import java.io.IOException;




/**
 * This class represents a text chord attribute.
 */
public class Text extends ChordAttribute
{
   // data members
   private String      text;                // text to be output
   private Rectangle   boundingRectangle;   // bounding rectangle for the text
   private boolean     alignedLeft;
   private boolean     alignedCenter;
   private boolean     alignedRight;
   private boolean     border;
   private FontSetting fontSettings;        // font settings to use when drawing the text




   /**
    * default constructor.
    */
   public Text()
   {
      super(ChordAttribute.Type.Text);
      boundingRectangle = new Rectangle();
      fontSettings      = new FontSetting();
   }

   /**
    * constructor.
    * <br/><br/>
    * @param text                text.
    * @param boundingRectangle   bounding rectangle enclosing the text.
    * @param fontSetting         font settings used to draw the text.
    */
   public Text(String text, Rectangle boundingRectangle, FontSetting fontSetting)
   {
      super(ChordAttribute.Type.Text);
      this.text              = text;
      this.boundingRectangle = boundingRectangle;
      this.fontSettings      = fontSetting;
   }

   /**
    * @return the text to be displayed.
    */
   public String getText()
   {
      return text;
   }

   /**
    * sets the text to be displayed.
    * <br/><br/>
    * @param text   the text to be output.
    */
   public void setText(String text)
   {
      this.text = text;
   }

   /**
    * @return the bounding rectangle for the text.
    */
   public Rectangle getBoundingRectangle()
   {
      return boundingRectangle;
   }

   /**
    * sets the bounding rectangle for the text.
    * <br/><br/>
    * @param rectangle   the bounding rectangle for the text.
    */
   public void setBoundingRectangle(Rectangle rectangle)
   {
      this.boundingRectangle = rectangle;
   }

   /**
    * @return whether the text is left aligned.
    */
   public boolean isAlignedLeft()
   {
      return alignedLeft;
   }

   /**
    * sets whether the text is left aligned or not.
    * <br/><br/>
    * @param set   whether the text is left aligned or not.
    */
   public void setAlignedLeft(boolean set)
   {
      if (set)
      {
         alignedLeft   = true;
         alignedCenter = false;
         alignedRight  = false;
      }
      else
      {
         alignedLeft   = false;
      }
   }

   /**
    * @return whether the text is center aligned.
    */
   public boolean isAlignedCenter()
   {
      return alignedCenter;
   }

   /**
    * sets whether the text is center aligned.
    * <br/><br/>
    * @param set  whether the text is center aligned.
    */
   public void setAlignedCenter(boolean set)
   {
      if (set)
      {
         alignedLeft   = false;
         alignedCenter = true;
         alignedRight  = false;
      }
      else
      {
         alignedCenter = false;
      }
   }

   /**
    * @return whether the text is right aligned.
    */
   public boolean isAlignedRight()
   {
      return alignedRight;
   }

   /**
    * sets whether the text is right aligned.
    * <br/><br/>
    * @param set   whether the text is right aligned.
    */
   public void setAlignedRight(boolean set)
   {
      if (set)
      {
         alignedLeft   = false;
         alignedCenter = false;
         alignedRight  = true;
      }
      else
      {
         alignedRight  = false;
      }
   }

   /**
    * @return whether the text is surrounded by a border.
    */
   public boolean hasBorder()
   {
      return border;
   }

   /**
    * sets whether the text is surrounded by a border.
    * <br/><br/>
    * @param border   whether the text is surrounded by a border.
    */
   public void setBorder(boolean border)
   {
      this.border = border;
   }

   /**
    * @return the font settings to use when drawing the text.
    */
   public FontSetting getFontSettings()
   {
      return fontSettings;
   }

   /**
    * sets the font settings to use when drawing the text.
    * <br/><br/>
    * @param fontSettings   the font settings to use when drawing the text.
    */
   public void setFontSettings(FontSetting fontSettings)
   {
      this.fontSettings = fontSettings;
   }

   /**
    * @return a new copy of the text chord attribute.
    */
   @Override
   public ChordAttribute clone()
   {
      return new Text(this.text, this.boundingRectangle, this.fontSettings);
   }

   /**
    * @param object  object to check for equality with this text attribute.
    * <br/><br/>
    * @return if the two text chord attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean result = false;

      if (object != null && object instanceof Text)
      {
         Text attribute = (Text)object;
         result = this.text.equals(attribute.text) && this.boundingRectangle.equals(attribute.boundingRectangle) && this.fontSettings.equals(attribute.fontSettings) &&
                  this.alignedLeft  == attribute.alignedLeft && this.alignedCenter == attribute.alignedCenter && this.alignedRight == attribute.alignedRight &&
                  this.border       == attribute.border;
      }
      return result;
   }

   /**
    * @return that the text chord attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in a text from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the text from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         text              = file.readString();
         boundingRectangle = file.readRectangle();
         alignedLeft       = file.readBoolean();
         alignedCenter     = file.readBoolean();
         alignedRight      = file.readBoolean();
         border            = file.readBoolean();
         fontSettings.load(file);
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.text"));
      }
   }

   /**
    * save a text chord attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the text chord attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeString   (text);
         file.writeRectangle(boundingRectangle);
         file.writeBoolean  (alignedLeft  );
         file.writeBoolean  (alignedCenter);
         file.writeBoolean  (alignedRight );
         file.writeBoolean  (border       );
         fontSettings.save(file);
      }
      catch (IOException ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.chord_attribute.text"));
      }
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab text.
    */
   @Override
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.chord_attribute.text") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.text"              ), indentation) + text              + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.bounding_rectangle"), indentation) + boundingRectangle + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.aligned_left"      ), indentation) + alignedLeft       + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.aligned_center"    ), indentation) + alignedCenter     + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.aligned_right"     ), indentation) + alignedRight      + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.has_border"        ), indentation) + border            + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.font.settings"), indentation) + fontSettings            );

      return buffer.toString();
   }
}
