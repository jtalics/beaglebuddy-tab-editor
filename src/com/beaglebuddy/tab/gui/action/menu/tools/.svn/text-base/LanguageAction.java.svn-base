/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.tools;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.mainframe.Preferences;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;




/**
 * called when the user selects the <i>Language</i> menu item from the <i>Tool/Preferences</i> menu.
 */
public class LanguageAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public LanguageAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * switches the user's locale to the selected language.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String language = event.getActionCommand();
      ResourceBundle.switchLanguage(language);

      // switch the help files to the new language and have the frame redraw the menus and toolbars
      frame.setTitle(ResourceBundle.getString("gui.text.beaglebuddy_tab_editor") + (frame.getFilename() == null ? "" : " - " + frame.getFilename()));
      frame.setHelpSystem();
      frame.setMenus();
      frame.setToolbars();
      frame.getStatusBar().cursor(frame.getCurrentLocation());
      frame.setVisible(true);

      // save the user's language preference
      try
      {
         Preferences.setString(Preferences.key_language, ResourceBundle.getCurrentLanguage());
      }
      catch (RuntimeException ex)
      {
         JOptionPane.showMessageDialog(frame, ResourceBundle.getString("gui.error.preferences.language"), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
   }
}
