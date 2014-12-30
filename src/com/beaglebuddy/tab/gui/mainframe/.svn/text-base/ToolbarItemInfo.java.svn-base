/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;




/**
 * contains information about a single toolbar item.
 */
public class ToolbarItemInfo
{
   public enum Type {Button, Toggle_Button, Separator}

   // class members
   public  static final int SEPARATOR = 1;



   // data members
   private Type           type;               // type of toolbar item
   private String         actionCommand;      // text internally identifying the command (this is never seen by the user and thus should not be internationalized)
   private ActionListener actionListener;     // class that will be called when the user selects the toolbar item.
   private ImageIcon      icon;               // filename of an image to be used as an icon for the toolbar item.
   private String         tooltip;            // tool tip for the button.
   private boolean        enabled;            // whether the toolbar button is initially enabled or not




   /**
    * constructor for toolbar item separators.
    * <br/><br/>
    * @param separator  constant indicating that this toolbar item is a separator.
    */
   public ToolbarItemInfo(Type separator)
   {
      assert separator == Type.Separator;

      this.type = separator;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param type             the type of toolbar item
    * @param icon             filename of an image to be used as an icon for the toolbar item.
    * @param tooltip          tool tip for the button.
    * @param actionCommand    text internally identifying the command.
    * @param actionListener   class that will be called when the user selects the toolbar item.
    */
   public ToolbarItemInfo(Type type, String icon, String tooltip, String actionCommand, ActionListener actionListener, boolean enabled)
   {
      assert type == Type.Button || type == Type.Toggle_Button;
      assert icon != null && tooltip != null && actionCommand != null && actionListener != null;

      this.type           = type;
      this.actionCommand  = actionCommand;
      this.actionListener = actionListener;
      this.tooltip        = tooltip;
      this.enabled        = enabled;

      // set the icon
      if (icon != null && icon.trim().length() != 0)
      {
         try
         {
            this.icon = new ImageIcon(getClass().getResource(icon));
         }
         catch (Exception ex)
         {
            System.out.println("trying to load " + icon + "\n" + ex.getMessage());
         }
      }
   }

   /**
    * @return a toolbar item based on the information it has.
    */
   public JComponent getToolbarItem()
   {
      JComponent toolbarItem = null;

      if (type == Type.Separator)
      {
         toolbarItem = new JToolBar.Separator();
      }
      else
      {
         AbstractButton button = (type == Type.Button ? new JButton() : new JToggleButton());
         button.setIcon          (icon          );
         button.setActionCommand (actionCommand );
         button.addActionListener(actionListener);
         button.setToolTipText   (tooltip       );
         button.setEnabled       (enabled       );

         toolbarItem = button;
      }
      return toolbarItem;
   }
}
