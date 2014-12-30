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
import java.awt.Color;




/**
 * This class represents a beaglebuddy tab font setting and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class FontSetting
{
   // class members
   private static final int MIN_POINTSIZE = 1;
   private static final int MAX_POINTSIZE = 72;

   // data members
   private String     faceName;         // face name of the font; i.e. Arial, Times New Roman, etc.
   private int        pointSize;        // height of the font (in points)
   private boolean    bold;             // font weight (see enum for values)
   private boolean    italic;           // whether or not the font uses italics
   private Color      color;            // rgb color of the font



   /**
    * default constructor.
    */
   public FontSetting()
   {
      this.faceName  = "Times New Roman";
      this.pointSize = 8;
      this.bold      = false;
      this.italic    = false;
      this.color     = Color.BLACK;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param faceName    face name of the font; i.e. Arial, Times New Roman, etc.
    * @param pointSize   height of the font (in points)
    * @param bold        whether or not the font is bold.
    * @param italic      whether or not the font uses italics.
    * @param color       rgb color of the font.
    */
   public FontSetting(String faceName, int pointSize, boolean bold, boolean italic, Color color)
   {
      this.faceName  = faceName;
      this.pointSize = pointSize;
      this.bold      = bold;
      this.italic    = italic;
      this.color     = color;
   }
   /**
    * copy constructor.
    * <br/><br/>
    * @param fontSetting   font setting whose values will be deep copied.
    */
   public FontSetting(FontSetting fontSetting)
   {
      this();

      if (fontSetting != null)
      {
         this.faceName  = fontSetting.faceName == null ? null : new String(fontSetting.faceName);
         this.pointSize = fontSetting.pointSize;
         this.bold      = fontSetting.bold;
         this.italic    = fontSetting.italic;
         this.color     = fontSetting.color == null ? Color.BLACK : new Color(fontSetting.color.getRed(), fontSetting.color.getGreen(), fontSetting.color.getBlue());
      }
   }

   /**
    * @return the font's name.
    */
   public String getFaceName()
   {
      return faceName;
   }

   /**
    * @return the font's point size.
    */
   public int getPointSize()
   {
      return pointSize;
   }

   /**
    * @return whether the font is bold.
    */
   public boolean isBold()
   {
      return bold;
   }

   /**
    * @return whether the font is italic.
    */
   public boolean isItalic()
   {
      return italic;
   }

   /**
    * @return the font rgb color.
    */
   public Color getColor()
   {
      return color;
   }

   /**
    * @return whether two font settings are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof FontSetting)
      {
         FontSetting fontSetting = (FontSetting)object;
         equal = this.faceName.equals(fontSetting.faceName) && this.pointSize == fontSetting.pointSize && this.bold == fontSetting.bold && this.italic == fontSetting.italic && this.color.equals(fontSetting.color);
      }
      return equal;
   }

   /**
    * read in the font setting from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the font setting can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         faceName  = file.readString();
         pointSize = file.readInt();
         bold      = file.readBoolean();
         italic    = file.readBoolean();
         color     = file.readColor();

         if (pointSize < MIN_POINTSIZE || pointSize > MAX_POINTSIZE)
            throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("data_type.font.point_size"), pointSize, MIN_POINTSIZE, MAX_POINTSIZE));
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.font.settings"));
      }
   }

   /**
    * save an font setting to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the font setting to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeString (faceName);
         file.writeInt    (pointSize);
         file.writeBoolean(bold);
         file.writeBoolean(italic);
         file.writeColor  (color);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.font.settings"));
      }
   }

   /**
    * @return a string representation of the font setting.
    */
   @Override
   public String toString()
   {
      return toString(12);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the font setting.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.font.settings") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.face_name"    ), indentation) + faceName  + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.point_size"   ), indentation) + pointSize + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.is_bold"      ), indentation) + bold      + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.is_italic"    ), indentation) + italic    + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.rgb_color"    ), indentation) + color           );

      return buffer.toString();
   }
}
