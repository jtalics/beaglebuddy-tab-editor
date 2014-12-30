/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import java.awt.Frame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * stores and retrieves a user's preferences.
 * <p>
 * on windows, this is in the windows registry under HKEY_CURRENT_USER/Software/JavaSoft/Prefs/com/beaglebuddy/tab/gui/mainframe/.
 * for whatever reason, a copy is also placed in     HKEY_USERS/xxxxxx/Software/JavaSoft/Prefs/com/beaglebuddy/tab/gui/mainframe/.
 * </p>
 * <p>
 * on linux, this will be in a file called .preferences in the user's home directory.
 * </p>
 */
public class Preferences
{
   // keys
   public static final String key_drum_map_sorting_method                   = "drum_map/sorting_method";           // sorting method of percussion sounds in a drum map dialog

   public static final String key_window_location_x                         = "window/location/x";                 // application window
   public static final String key_window_location_y                         = "window/location/y";
   public static final String key_window_location_height                    = "window/location/height";
   public static final String key_window_location_width                     = "window/location/width";
   public static final String key_window_state                              = "window/state";

   public static final String key_most_recently_used_files_num              = "most_recently_used_files/num";      // list of the most recent files the user has opened
   public static final String key_most_recently_used_files_file             = "most_recently_used_files/file_";

   public static final String key_last_used_directory                       = "last_used_directory";               // last directory the user accessed

   public static final String key_language                                  = "language";                          // user's language preference

   public static final String key_help_helpset_jar_file                     = "help/helpset_jar_file";             // online help system
   public static final String key_help_location_x                           = "help/location/x";
   public static final String key_help_location_y                           = "help/location/y";
   public static final String key_help_location_height                      = "help/location/height";
   public static final String key_help_location_width                       = "help/location/width";

   public static final String key_printer_page_format                       = "printer/page/format";


   public static final String key_midi_device_description                   = "midi_device/description";           // midi device on which user plays back songs
   public static final String key_midi_device_name                          = "midi_device/name";
   public static final String key_midi_device_vendor                        = "midi_device/vendor";
   public static final String key_midi_device_version                       = "midi_device/version";



   // default values
   public static final String default_value_drum_map_sorting_method         = "sound";

   public static final int    default_value_help_location_x                 = 100;
   public static final int    default_value_help_location_y                 = 100;
   public static final int    default_value_help_location_height            = 600;
   public static final int    default_value_help_location_width             = 600;

   public static final String default_value_language                        = "en";

   public static final int    default_value_window_location_x               = 100;
   public static final int    default_value_window_location_y               = 100;
   public static final int    default_value_window_location_height          = 600;
   public static final int    default_value_window_location_width           = 600;
   public static final int    default_value_window_state                    = Frame.NORMAL;

   // all preferences are stored under the user preferences root.
   private static final java.util.prefs.Preferences preferences = java.util.prefs.Preferences.userNodeForPackage(Preferences.class);




   /**
    * default constructor.
    */
   private Preferences()
   {
      // do not allow this class to be instantiated
   }

   /**
    * returns the user's preference stored at the specified key.
    * <br/>br/>
    * @param key   location of user's preference.
    * <br/>br/>
    * @return the user's preference stored at the specified key.
    */
   public static boolean getBoolean(String key)
   {
      return preferences.getBoolean(key, false);
   }

   /**
    * returns the user's preference stored at the specified key.
    * <br/>br/>
    * @param key           location of user's preference.
    * @param defaultValue  default value to return if no preference is found.
    * <br/>br/>
    * @return the user's preference stored at the specified key.
    */
   public static boolean getBoolean(String key, boolean defaultValue)
   {
      return preferences.getBoolean(key, defaultValue);
   }

   /**
    * sets the user's preference stored at the specified key.
    * <br/>br/>
    * @param key     location of user's preference.
    * @param value   the user's preference.
    */
   public static void setBoolean(String key, boolean value)
   {
      preferences.putBoolean(key, value);
   }
   /**
    * returns the user's preference stored at the specified key.
    * <br/>br/>
    * @param key   location of user's preference.
    * <br/>br/>
    * @return the user's preference stored at the specified key.
    */
   public static int getInt(String key)
   {
      return preferences.getInt(key, 0);
   }

   /**
    * returns the user's preference stored at the specified key.
    * <br/>br/>
    * @param key           location of user's preference.
    * @param defaultValue  default value to return if no preference is found.
    * <br/>br/>
    * @return the user's preference stored at the specified key.
    */
   public static int getInt(String key, int defaultValue)
   {
      return preferences.getInt(key, defaultValue);
   }

   /**
    * sets the user's preference stored at the specified key.
    * <br/>br/>
    * @param key     location of user's preference.
    * @param value   the user's preference.
    */
   public static void setInt(String key, int value)
   {
      preferences.putInt(key, value);
   }

   /**
    * returns the user's preference stored at the specified key.
    * <br/>br/>
    * @param key   location of user's preference.
    * <br/>br/>
    * @return the user's preference stored at the specified key.
    */
   public static String getString(String key)
   {
      return preferences.get(key, null);
   }

   /**
    * returns the user's preference stored at the specified key.
    * <br/>br/>
    * @param key           location of user's preference.
    * @param defaultValue  default value to return if no preference is found.
    * <br/>br/>
    * @return the user's preference stored at the specified key.
    */
   public static String getString(String key, String defaultValue)
   {
      return preferences.get(key, defaultValue);
   }

   /**
    * sets the user's preference stored at the specified key.
    * <br/>br/>
    * @param key     location of user's preference.
    * @param value   the user's preference.
    */
   public static void setString(String key, String value)
   {
      preferences.put(key, value);
   }

   /**
    * returns the user's preference stored at the specified key.
    * <br/>br/>
    * @param key           location of user's preference.
    * @param defaultValue  default value to return if no preference is found.
    * <br/>br/>
    * @return the user's preference stored at the specified key.
    */
   public static Object getObject(String key, Object defaultValue) {
      Object ret = defaultValue;
      byte[] serializedObject = preferences.getByteArray(key, null);
      if (serializedObject != null) {
         ByteArrayInputStream bais = null;
         ObjectInputStream ois = null;
         try {
            bais = new ByteArrayInputStream(serializedObject);
            ois = new ObjectInputStream(bais);
            ret = ois.readObject();
         } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
         } finally {
            if (bais != null) {
               try {
                  bais.close();
               } catch (IOException e) {
               }
            }
            if (ois != null) {
               try {
                  ois.close();
               } catch (IOException e) {
               }
            }
         }
      }
      return ret;
   }

   /**
    * sets the user's preference stored at the specified key.
    * <br/>br/>
    * @param key     location of user's preference.
    * @param value   the user's preference.
    */
   public static void setObject(String key, Object value)
   {
      ObjectOutputStream oos = null;
      ByteArrayOutputStream baos = null;
      try {
         baos = new ByteArrayOutputStream();
         oos = new ObjectOutputStream(baos);
         oos.writeObject(value);
         oos.close();
         baos.flush();
         byte[] serializedObject = baos.toByteArray();
         preferences.putByteArray(key, serializedObject);
      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace();
      } finally {
         if (baos != null) {
            try {
               baos.close();
            } catch (IOException e) {
            }
         }
         if (oos != null) {
            try {
               oos.close();
            } catch (IOException e) {
            }
         }
      }
   }
}
