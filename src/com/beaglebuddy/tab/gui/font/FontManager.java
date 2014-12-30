/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andrew Will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.font;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;




/**
 * Utility class for managing fonts within the song panel.
 */
public class FontManager
{
   /** Map contains mapping for each specialized font. */
   private static final Map<FontInfo, Font> fonts;

   public static final Font LARGE_FONT;
   public static final Font MEDIUM_FONT;
   public static final Font SMALL_FONT;
   public static final Font SMALL_BOLD_FONT;
   public static final Font SMALL_ITALIC_FONT;
   public static final Font DYNAMIC_FONT;
   public static final Font TIME_SIGNATURE_FONT;
   public static final Font TAB_CLEF_FONT;
   public static final Font SMALL_TAB_CLEF_FONT;
   public static final Font MINI_TEXT_FONT;
   public static final Font TAB_FONT;

   public static final boolean IS_BOLD       = true;
   public static final boolean IS_NOT_BOLD   = false;
   public static final boolean IS_ITALIC     = true;
   public static final boolean IS_NOT_ITALIC = false;

   private static final String LARGE_FONT_NAME = Font.SERIF;
   private static final String SMALL_FONT_NAME = Font.SANS_SERIF;
   private static final int LARGE_FONT_SIZE = 24;
   private static final int MEDIUM_FONT_SIZE = 18;
   private static final int TIME_SIGNATURE_FONT_SIZE = 17;
   private static final int TAB_CLEF_FONT_SIZE = 14;
   private static final int SMALL_TAB_CLEF_FONT_SIZE = 9;
   private static final int DYNAMIC_FONT_SIZE = 13;
   private static final int SMALL_FONT_SIZE = 12;
   private static final int MINI_TEXT_FONT_SIZE = 11;
   private static final int TAB_FONT_SIZE = 11;
   
   public static final float MAX_TAB_CLEF_FONT_SIZE = 14;
   public static final float MIN_TAB_CLEF_FONT_SIZE = 9;

   static
   {
      // instantiate the font map.
      fonts = new HashMap<FontInfo, Font>();

      // Construct each font.
      LARGE_FONT           = getFont(            LARGE_FONT_SIZE           , IS_BOLD      , IS_NOT_ITALIC);
      MEDIUM_FONT          = getFont(            MEDIUM_FONT_SIZE          , IS_NOT_BOLD  , IS_NOT_ITALIC);
      SMALL_FONT           = getFont(            SMALL_FONT_SIZE           , IS_NOT_BOLD  , IS_NOT_ITALIC);
      SMALL_BOLD_FONT      = getFont(            SMALL_FONT_SIZE           , IS_BOLD      , IS_NOT_ITALIC);
      SMALL_ITALIC_FONT    = getFont(            SMALL_FONT_SIZE           , IS_NOT_BOLD  , IS_ITALIC    );
      DYNAMIC_FONT         = getFont(Font.SERIF, DYNAMIC_FONT_SIZE         , IS_BOLD      , IS_ITALIC    );
      TIME_SIGNATURE_FONT  = getFont(Font.SERIF, TIME_SIGNATURE_FONT_SIZE  , IS_BOLD      , IS_NOT_ITALIC);
      TAB_CLEF_FONT        = getFont(Font.SERIF, TAB_CLEF_FONT_SIZE        , IS_BOLD      , IS_NOT_ITALIC);
      SMALL_TAB_CLEF_FONT  = getFont(Font.SERIF, SMALL_TAB_CLEF_FONT_SIZE  , IS_BOLD      , IS_NOT_ITALIC);
      MINI_TEXT_FONT       = getFont(Font.SERIF, MINI_TEXT_FONT_SIZE       , IS_NOT_BOLD  , IS_NOT_ITALIC);
      TAB_FONT             = getFont(Font.SERIF, TAB_FONT_SIZE             , IS_NOT_BOLD  , IS_NOT_ITALIC);
   }

   /**
    * Obtains the default typeface with the given attributes.
    * @param size
    * @param isBold
    * @param isItalic
    * @return
    */
   public static Font getFont(int size, boolean isBold, boolean isItalic)
   {
      String fontName = size <= SMALL_FONT_SIZE ? SMALL_FONT_NAME : LARGE_FONT_NAME;

      return getFont(fontName, size, isBold, isItalic);
   }

   /**
    * Obtains the named typeface with the given attributes.
    * @param fontName
    * @param size
    * @param isBold
    * @param isItalic
    * @return
    */
   public static Font getFont(String fontName, int size, boolean isBold, boolean isItalic)
   {
      FontInfo hf = new FontInfo(fontName, size, isBold, isItalic);
      if (!fonts.containsKey(hf))
      {
         int style = Font.PLAIN;
         if (isBold)
         {
            style |= Font.BOLD;
         }
         if (isItalic)
         {
            style |= Font.ITALIC;
         }
         Font font = new Font(fontName, style, size);
         fonts.put(hf, font);
      }
      Font font = fonts.get(hf);
      return font;
   }

   /**
    * Obtains a new font size based on an existing font.
    * (The result is cached to minimize object creation.)
    * @param font
    * @param size
    * @return
    */
   public static Font deriveFont(Font font, int size)
   {
      return getFont(font.getName(), size, font.isBold(), font.isItalic());
   }
}
