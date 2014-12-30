/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Rectangle;
import java.io.IOException;





/**
 * This class represents a beaglebuddy tab text and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class FloatingText
{
   public enum Alignment {Left, Center, Right}

   // data members
   private String      text;                // text to be output
   private Rectangle   boundingRectangle;   // bounding rectangle for the text
   private Alignment   alignment;           // whether the text is left, center, or right aligned
   private boolean     border;              // whether the text has a border around it
   private FontSetting fontSettings;        // font settings to use when drawing the text





   /**
    * default constructor.
    */
   public FloatingText()
   {
      boundingRectangle = new Rectangle();
      fontSettings      = new FontSetting();
   }

   /**
    * constructor.
    * <br/><br/>
    * @param text                text to be displayed.
    * @param boundingRectangle   bounding rectangle for the text.
    * @param alignment           whether the text is left, center, or right aligned.
    * @param border              whether the text has a border around it.
    * @param fontSettings        font settings to use when drawing the text.
    */
   public FloatingText(String text, Rectangle boundingRectangle, Alignment alignment, boolean border, FontSetting fontSettings)
   {
      this.text              = text;
      this.boundingRectangle = boundingRectangle;
      this.alignment         = alignment;
      this.border            = border;
      this.fontSettings      = fontSettings;
   }

   /**
    * default constructor.
    * <br/><br/>
    * @param floatingText    floating text whose values will be deep copied.
    */
   public FloatingText(FloatingText floatingText)
   {
      this();

      if (floatingText != null)
      {
         this.text              = floatingText.text == null ? "" : new String(floatingText.text);
         this.boundingRectangle = boundingRectangle == null ? new Rectangle() : new Rectangle(boundingRectangle);
         this.alignment         = floatingText.alignment;
         this.border            = floatingText.border;
         this.fontSettings      = new FontSetting(floatingText.fontSettings);
      }
   }

   /**
    * @return the text to be output.
    */
   public String getText()
   {
      return text;
   }

   /**
    * @return the bounding rectangle for the text.
    */
   public Rectangle getBoundingRectangle()
   {
      return boundingRectangle;
   }

   /**
    * @return the alignment of the floating text within the bounding rectangle.
    */
   public Alignment getAlignment()
   {
      return alignment;
   }

   /**
    * @return the alignment corresponding to the integer alignment number.
    * <br/><br/>
    * @param alignment   the integer alignment.
    */
   public static Alignment getAlignment(int alignment)
   {
      for (Alignment a : Alignment.values())
         if (alignment == a.ordinal())
            return a;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.floating_text.alignment"), alignment, Alignment.Right.ordinal()));
   }

   /**
    * @return whether the text is left aligned.
    */
   public boolean isAlignedLeft()
   {
      return alignment == Alignment.Left;
   }

   /**
    * @return whether the text is center aligned.
    */
   public boolean isAlignedCenter()
   {
      return alignment == Alignment.Center;
   }

   /**
    * @return whether the text is right aligned.
    */
   public boolean isAlignedRight()
   {
      return alignment == Alignment.Right;
   }

   /**
    * sets the alignment of the floating text within the bounding rectangle.
    * <br/><br/>
    * @param alignment   alignment of the floating text within the bounding rectangle.
    */
   public void setAlignment(Alignment alignment)
   {
      this.alignment = alignment;
   }

   /**
    * @return whether the text is surrounded by a border.
    */
   public boolean hasBorder()
   {
      return border;
   }

   /**
    * @return the font settings to use when drawing the text.
    */
   public FontSetting getFontSettings()
   {
      return fontSettings;
   }

   /**
    * @return whether two floating texts are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof FloatingText)
      {
         FloatingText floatingText = (FloatingText)object;
         equal = this.text.equals(floatingText.text) && this.boundingRectangle.equals(floatingText.boundingRectangle) &&
                 this.alignment == floatingText.alignment && this.border == floatingText.border && this.fontSettings.equals(floatingText.fontSettings);
      }
      return equal;
   }

   /**
    * read in a text from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the text from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         text              = file.readString();
         boundingRectangle = file.readRectangle();
         alignment         = getAlignment(file.read());
         border            = file.readBoolean();
         fontSettings.load(file);
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.floating_text"));
      }
   }

   /**
    * save a text to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the text to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeString   (text               );
         file.writeRectangle(boundingRectangle  );
         file.write         (alignment.ordinal());
         file.writeBoolean  (border             );
         fontSettings.save(file);
      }
      catch (IOException ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.floating_text"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab text.
    */
   @Override
   public String toString()
   {
      return toString(9);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab text.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.floating_text") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.text"                        ), indentation) + text              + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.bounding_rectangle"          ), indentation) + boundingRectangle + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.floating_text.alignment"), indentation) + alignment         + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.has_border"                  ), indentation) + border            + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.font.settings"          ), indentation) + fontSettings            );

      return buffer.toString();
   }
}
