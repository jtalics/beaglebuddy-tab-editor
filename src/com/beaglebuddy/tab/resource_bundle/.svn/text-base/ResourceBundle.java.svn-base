/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.resource_bundle;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import javax.swing.JComponent;




/**
 * utility class for handling internationalization.
 */
public class ResourceBundle
{
   // class members
   private static java.util.ResourceBundle resourceBundle     = java.util.ResourceBundle.getBundle("com.beaglebuddy.tab.resource_bundle.tab");
   private static String[]                 supportedLanguages = {"de", "en", "es"};




   /**
    * formats a message with the specified arguments.
    * <br/><br/>
    * @param key        key of the string in the resource bundle which containes place holders for arguments.
    * @param arguments  the values to replace the place holders in the pattern string.
    * <br/><br/>
    * @return the formatted message.
    */
   public static String format(String key, Object... arguments)
   {
      return MessageFormat.format(getString(key), arguments);
   }

   /**
    * @return the current 2 letter ISO 639 language code.
    */
   public static String getCurrentLanguage()
   {
      Locale locale = Locale.getDefault();

      return locale.getLanguage();
   }

   /**
    * @return the current locale.
    */
   public static Locale getCurrentLocale()
   {
      return Locale.getDefault();
   }

   /**
    * retrieves a string from the resource bundle.
    * <br/><br/>
    * @param key  key of the string to be retrieved from the resource bundle.
    * <br/><br/>
    * @return the requested string from the resource bundle.
    */
   public static String getString(String key)
   {
      String s = null;
      try
      {
         s = resourceBundle.getString(key);
      }
      catch (MissingResourceException ex)
      {
         // key was not found in the resource bundle.  simply return null.
      }
      return s;
   }

   /**
    * @return whether the specified language is one of the supported languages.
    * <br/><br/>
    * @param language  ISO 639 language code.
    */
   private static boolean isSupportedLanguage(String language)
   {
      boolean found = false;
      for(int i=0; i<supportedLanguages.length && !found; ++i)
         found = language.equals(supportedLanguages[i]);

      return found;
   }

   /**
    * switch to a new language.
    * <br/><br/>
    * @param language   2 letter language code.
    */
   public static void switchLanguage(String language)
   {
      if (!isSupportedLanguage(language))
         throw new IllegalArgumentException(format("error.invalid.language", language));

      Locale locale = new Locale(language);
      Locale.setDefault(locale);
      JComponent.setDefaultLocale(locale);
      resourceBundle = java.util.ResourceBundle.getBundle("com.beaglebuddy.tab.resource_bundle.tab");
   }
}
