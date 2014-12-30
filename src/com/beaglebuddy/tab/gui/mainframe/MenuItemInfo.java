/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Event;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;



/**
 * contains information about a single menu item.
 */
public class MenuItemInfo
{
   // data members
   public  static final int                        SEPARATOR    = 1;
   private static final Hashtable<String, Integer> keyModifiers = new Hashtable<String, Integer>();
   static
   {
      keyModifiers.put("alt"  , Event.ALT_MASK   );
      keyModifiers.put("ctrl" , Event.CTRL_MASK  );
      keyModifiers.put("shift", Event.SHIFT_MASK);
   }

   // data members
   private boolean        separator;          // whether the menu item is a separator.
   private String         text;               // text of the menu choice.
   private String         actionCommand;      // text that is returned by the ActionEvent.getActionCommand() when a user selects a menu item
   private ImageIcon      icon;               // filename of an image to be used as an icon for the menu item.
   private char           mnemonic;           // single character in the menu text which will be underlined.
   private KeyStroke      accelerator;        // two-key shortcut for selecting the menu item (ex: ctrl-C).
   private ActionListener actionListener;     // class that will be called when the user selects the menu item.
   private Class<?>       clas;               // whether this item is a menu or a menu item



   /**
    * constructor for menu item separators.
    * <br/><br/>
    * @param separator  constant indicating that this menu item is a separator.
    */
   public MenuItemInfo(int separator)
   {
      if (separator != SEPARATOR)
         throw new IllegalArgumentException(ResourceBundle.getString("gui.error.invalid.menu_item.separator"));
      this.separator = true;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param text             text of the menu choice.
    * @param icon             filename of an image to be used as an icon for the menu item.
    * @param mnemonic         single character in the menu text which will be underlined.
    * @param accelerator      two-key shortcut for selecting the menu item (ex: ctrl-C).
    * @param actionCommand    text that is returned by the ActionEvent.getActionCommand() when a user selects a menu item.
    * @param actionListener   class that will be called when the user selects the menu item.
    * @param clas             whether this item is a menu or a menu item.
    */
   public MenuItemInfo(String text, String icon, String mnemonic, String accelerator, String actionCommand, ActionListener actionListener, Class<?> clas)
   {
      // set the menu text
      if (text != null && text.trim().length() != 0)
         this.text = text;

      // set the menu icon
      if (icon != null && icon.trim().length() != 0)
      {
         try
         {
            this.icon = new ImageIcon(getClass().getResource(icon));
         }
         catch (Exception ex)
         {
            System.out.println("trying to load " + icon);
         }
      }

      if (this.text == null && this.icon == null)
         throw new IllegalArgumentException(ResourceBundle.getString("gui.error.invalid.menu_item.text_and_icon"));

      // set the menu mnemonic
      if (mnemonic != null && mnemonic.trim().length() != 0)
      {
         if (mnemonic.trim().length() != 1)
            throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.menu_item.mnemonic", mnemonic));
         if (this.text == null)
            throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.menu_item.mnemonic_no_text", mnemonic));
         int i;
         for(i=0; i<text.length() && text.charAt(i) != mnemonic.charAt(0); ++i){}
         if (i == text.length())
            throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.menu_item.mnemonic_not_in_text", mnemonic, text));
         this.mnemonic = mnemonic.charAt(0);
      }

      // set the menu accelerator
      if (accelerator != null && accelerator.trim().length() != 0)
      {
         String[] keys = accelerator.split("_", 2);
         if (keys.length != 2 || keyModifiers.get(keys[0].toLowerCase()) == null)
            throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.menu_item.accelerator", accelerator, text));
         if (keys[1].length() != 1)
            throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.menu_item.accelerator", accelerator, text));
         this.accelerator = KeyStroke.getKeyStroke(keys[1].charAt(0), keyModifiers.get(keys[0].toLowerCase()), false);
      }

      // set the menu action command
      if (actionCommand != null && actionCommand.trim().length() != 0)
         this.actionCommand = actionCommand.trim();
      else if (text != null && text.trim().length() != 0)
         this.actionCommand = text.trim();
      else
         this.actionCommand = icon.trim();

      // set the menu action listener class
      if (actionListener == null && clas.getName().equals(JMenuItem.class.getName()))
         throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.menu_item.action_listener", text));
      this.actionListener = actionListener;

      if (clas == null || !(clas.getName().equals(JMenuItem.class.getName()) || clas.getName().equals(JMenu.class.getName())))
         throw new IllegalArgumentException(ResourceBundle.format("gui.error.invalid.menu_item.class", text));
      this.clas = clas;
   }

   /**
    * @return a menu item based on the information it has.
    */
   public JMenuItem getMenuItem()
   {
      JMenuItem menuItem = null;

      if (!isSeparator())
      {
         menuItem = (clas.getName().equals(JMenu.class.getName()) ? new JMenu() : new JMenuItem());
         if (text           != null) menuItem.setText          (text          );
         if (icon           != null) menuItem.setIcon          (icon          );
         if (mnemonic       != 0   ) menuItem.setMnemonic      (mnemonic      );
         if (accelerator    != null) menuItem.setAccelerator   (accelerator   );
         if (actionCommand  != null) menuItem.setActionCommand (actionCommand );
         if (actionListener != null) menuItem.addActionListener(actionListener);
      }
      return menuItem;
   }

   /**
    * @return whether this menu item is a separator.
    */
   public boolean isSeparator()
   {
      return separator;
   }
}
