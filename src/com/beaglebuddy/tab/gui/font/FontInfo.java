/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andrew Will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.font;




/**
 * contains information describing a font.
 */
public class FontInfo
{
   // data members
   private final String  name;             // name of the font (font face name of font family name)
   private final int     size;             // point size of the font
   private final boolean isBold;           // whether the font is displayed in bold characters
   private final boolean isItalic;         // whether the font is displayed in italic characters



   /**
    * constructor.
    * <br/><br/>
    * @param name       name of the font (font face name of font family name).
    * @param size       point size of the font.
    * @param isBold     whether the font is displayed in bold characters.
    * @param isItalic   whether the font is displayed in italic characters.
    */
   public FontInfo(String name, int size, boolean isBold, boolean isItalic)
   {
      if (name == null || name.trim().length() == 0)
         throw new IllegalArgumentException("Developer error: font name can not be empty.");

      this.name     = name;
      this.size     = size;
      this.isBold   = isBold;
      this.isItalic = isItalic;
   }

   /**
    * @returns a hash code value for the object for the benefit of hashtables such as those provided by java.util.Hashtable.
    */
   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + (isBold       ? 1231 : 1237);
      result = prime * result + (isItalic     ? 1231 : 1237);
      result = prime * result + (name == null ? 0    : name.hashCode());
      result = prime * result + size;
      return result;
   }

   /**
    * @return whether two font information objects are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof FontInfo)
      {
         FontInfo fontInfo = (FontInfo)object;

         equal = name.equals(fontInfo.name) && size == fontInfo.size && isBold == fontInfo.isBold && isItalic == fontInfo.isItalic;
      }
      return equal;
   }
}
