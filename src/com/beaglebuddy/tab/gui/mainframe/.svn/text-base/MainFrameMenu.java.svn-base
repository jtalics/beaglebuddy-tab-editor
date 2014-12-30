/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.gui.action.menu.edit.*;
import com.beaglebuddy.tab.gui.action.menu.help.HelpAction;
import com.beaglebuddy.tab.gui.action.menu.instrument.*;
import com.beaglebuddy.tab.gui.action.menu.staff.SectionAction;
import com.beaglebuddy.tab.gui.action.menu.staff.StaffAction;
import com.beaglebuddy.tab.gui.action.menu.song.*;
import com.beaglebuddy.tab.gui.action.menu.tools.*;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import javax.help.CSH;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;






/**
 * This main frame base class manages the following aspects of the application:
 * <ol>
 *    <li>menus</li>
 *    <li>list of most recently used files</li>
 * </ol>
 */
public abstract class MainFrameMenu extends MainFrameToolbar
{
   // class members
   private static final int MAX_NUM_RECENT_FILES = 5;    // the number of most recently used files to remember

   // data members
   private ArrayList<String> recentFiles;                // list of most recently edited beaglebuddy tab files




   /**
    * default constructor.
    */
   public MainFrameMenu()
   {
      // add the menu to the this
      setMenus();

      // load the list of the user's most recently used files
      restoreMostRecentlyUsedFiles();
   }

   /**
    * @return a list of the 5 most recently edited files by the user.
    */
   public ArrayList<String> getRecentFiles()
   {
      return recentFiles;
   }

   /**
    * adds a new filename to the list of the 5 most recently edited files by the user.
    * <br/><br/>
    * @param recentFile   a filename recently edited by the user.
    */
   public void addRecentFile(String recentFile)
   {
      // see if the recent file is already in the list, and if so, remove it
      int index = recentFiles.lastIndexOf(recentFile);
      if (index != -1)
         recentFiles.remove(index);
      // otherwise, see if we've already reached our limit, remove the oldest file name
      else if (recentFiles.size() == MAX_NUM_RECENT_FILES)
         recentFiles.remove(MAX_NUM_RECENT_FILES - 1);

      // add the recent file to the beginning of the list
      recentFiles.add(0, recentFile);

      // recreate the recent file list menu items
      setRecentlyUsedFileMenu();
   }

   /**
    * @return the most recent file edited by the user.
    */
   public String getMostRecentFile()
   {
      return (recentFiles.size() == 0 ? null : recentFiles.get(0));
   }

   /**
    * removes a filename from the list of the 5 most recently edited files by the user.
    * <br/><br/>
    * @param recentFile   a filename recently edited by the user.
    */
   public void removeRecentFile(String recentFile)
   {
      int index = recentFiles.lastIndexOf(recentFile);
      if (index != -1)
         recentFiles.remove(index);

      setRecentlyUsedFileMenu();
   }

   /**
    * restores the list of most recently used files from the preferences.
    */
   private void restoreMostRecentlyUsedFiles()
   {
      recentFiles = new ArrayList<String>();

      int n = Preferences.getInt(Preferences.key_most_recently_used_files_num);
      for(int i=0; i<n; ++i)
         recentFiles.add(Preferences.getString(Preferences.key_most_recently_used_files_file + i));

      setRecentlyUsedFileMenu();
   }

   /**
    * create the menus.
    */
   public void setMenus()
   {
      JMenuBar menuBar = getJMenuBar();

      if (menuBar == null)
      {
         menuBar = new JMenuBar();
         setJMenuBar(menuBar);
      }

      // remove the existing top level menus
      int numTopLevelMenus = menuBar.getMenuCount();
      for(int i=0; i<numTopLevelMenus; ++i)
         menuBar.remove(0);

      // now add the menus to the this
      menuBar.add(buildSongMenu      ());
      menuBar.add(buildEditMenu      ());
      menuBar.add(buildInstrumentMenu());
      menuBar.add(buildStaffMenu     ());
      menuBar.add(buildToolMenu      ());
      menuBar.add(buildHelpMenu      ());
   }

   /**
    * @return the song menu.
    */
   private JMenu buildSongMenu()
   {
      JMenu          menu          = new JMenu        (ResourceBundle.getString("menu.song.text"));
      MenuItemInfo[] menuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.song.new.text"          ), ResourceBundle.getString("menu.song.new.icon"          ), ResourceBundle.getString("menu.song.new.mnemonic"          ), ResourceBundle.getString("menu.song.new.shortcut"          ), null, new NewAction         ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.open.text"         ), ResourceBundle.getString("menu.song.open.icon"         ), ResourceBundle.getString("menu.song.open.mnemonic"         ), ResourceBundle.getString("menu.song.open.shortcut"         ), null, new OpenAction        ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.recent_files.text" ), ResourceBundle.getString("menu.song.recent_files.icon" ), ResourceBundle.getString("menu.song.recent_files.mnemonic" ), ResourceBundle.getString("menu.song.recent_files.shortcut" ), null, null                                   , JMenu    .class),
                                      new MenuItemInfo(MenuItemInfo.SEPARATOR                                                                                                                                                                                                                                                                                        ),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.information.text"  ), ResourceBundle.getString("menu.song.information.icon"  ), ResourceBundle.getString("menu.song.information.mnemonic"  ), ResourceBundle.getString("menu.song.information.shortcut"  ), null, new InformationAction ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(MenuItemInfo.SEPARATOR                                                                                                                                                                                                                                                                                        ),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.save.text"         ), ResourceBundle.getString("menu.song.save.icon"         ), ResourceBundle.getString("menu.song.save.mnemonic"         ), ResourceBundle.getString("menu.song.save.shortcut"         ), null, new SaveAction        ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.save_as.text"      ), ResourceBundle.getString("menu.song.save_as.icon"      ), ResourceBundle.getString("menu.song.save_as.mnemonic"      ), ResourceBundle.getString("menu.song.save_as.shortcut"      ), null, new SaveAsAction      ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(MenuItemInfo.SEPARATOR                                                                                                                                                                                                                                                                                        ),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.print.text"        ), ResourceBundle.getString("menu.song.print.icon"        ), ResourceBundle.getString("menu.song.print.mnemonic"        ), ResourceBundle.getString("menu.song.print.shortcut"        ), null, new PrintAction       ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.print_options.text"), ResourceBundle.getString("menu.song.print_options.icon"), ResourceBundle.getString("menu.song.print_options.mnemonic"), ResourceBundle.getString("menu.song.print_options.shortcut"), null, new PrintOptionsAction((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.print_setup.text"  ), ResourceBundle.getString("menu.song.print_setup.icon"  ), ResourceBundle.getString("menu.song.print_setup.mnemonic"  ), ResourceBundle.getString("menu.song.print_setup.shortcut"  ), null, new PrintSetupAction  ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(MenuItemInfo.SEPARATOR                                                                                                                                                                                                                                                                                        ),
                                      new MenuItemInfo(ResourceBundle.getString("menu.song.exit.text"         ), ResourceBundle.getString("menu.song.exit.icon"         ), ResourceBundle.getString("menu.song.exit.mnemonic"         ), ResourceBundle.getString("menu.song.exit.shortcut"         ), null, new ExitAction        ((MainFrame)this), JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : menuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            menu.addSeparator();
         else
            menu.add(menuItemInfo.getMenuItem());
      }
      return menu;
   }

   /**
    * @return the edit menu.
    */
   private JMenu buildEditMenu()
   {
      JMenu          menu          = new JMenu        (ResourceBundle.getString("menu.edit.text"));
      MenuItemInfo[] menuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.edit.undo.text"        ), ResourceBundle.getString("menu.edit.undo.icon"        ), ResourceBundle.getString("menu.edit.undo.mnemonic"        ), ResourceBundle.getString("menu.edit.undo.shortcut"        ), null, new UndoAction  ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.edit.cut.text"         ), ResourceBundle.getString("menu.edit.cut.icon"         ), ResourceBundle.getString("menu.edit.cut.mnemonic"         ), ResourceBundle.getString("menu.edit.cut.shortcut"         ), null, new CutAction   ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.edit.copy.text"        ), ResourceBundle.getString("menu.edit.copy.icon"        ), ResourceBundle.getString("menu.edit.copy.mnemonic"        ), ResourceBundle.getString("menu.edit.copy.shortcut"        ), null, new CopyAction  ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.edit.paste.text"       ), ResourceBundle.getString("menu.edit.paste.icon"       ), ResourceBundle.getString("menu.edit.paste.mnemonic"       ), ResourceBundle.getString("menu.edit.paste.shortcut"       ), null, new PasteAction ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.edit.insert.text"      ), ResourceBundle.getString("menu.edit.insert.icon"      ), ResourceBundle.getString("menu.edit.insert.mnemonic"      ), ResourceBundle.getString("menu.edit.insert.shortcut"      ), null, new InsertAction((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(MenuItemInfo.SEPARATOR                                                                                                                                                                                                                                                                              ),
                                      new MenuItemInfo(ResourceBundle.getString("menu.edit.go_to.text"       ), ResourceBundle.getString("menu.edit.go_to.icon"       ), ResourceBundle.getString("menu.edit.go_to.mnemonic"       ), ResourceBundle.getString("menu.edit.go_to.shortcut"       ), null, null                             , JMenu    .class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.edit.find.text"        ), ResourceBundle.getString("menu.edit.find.icon"        ), ResourceBundle.getString("menu.edit.find.mnemonic"        ), ResourceBundle.getString("menu.edit.find.shortcut"        ), null, new FindAction  ((MainFrame)this), JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : menuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            menu.addSeparator();
         else
            menu.add(menuItemInfo.getMenuItem());
      }

      GotoAction     gotoAction        = new GotoAction  ((MainFrame)this);
      JMenu          gotoMenu          = (JMenu)menu.getItem(6);
      MenuItemInfo[] gotoMenuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.edit.go_to.measure.text"), ResourceBundle.getString("menu.edit.go_to.measure.icon"), ResourceBundle.getString("menu.edit.go_to.measure.mnemonic"), ResourceBundle.getString("menu.edit.go_to.measure.shortcut"), GotoAction.MEASURE, gotoAction, JMenuItem.class),
                                          new MenuItemInfo(ResourceBundle.getString("menu.edit.go_to.section.text"), ResourceBundle.getString("menu.edit.go_to.section.icon"), ResourceBundle.getString("menu.edit.go_to.section.mnemonic"), ResourceBundle.getString("menu.edit.go_to.section.shortcut"), GotoAction.SECTION, gotoAction, JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : gotoMenuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            gotoMenu.addSeparator();
         else
            gotoMenu.add(menuItemInfo.getMenuItem());
      }
      return menu;
   }

   /**
    * @return the instrument menu.
    */
   private JMenu buildInstrumentMenu()
   {
      JMenu          menu          = new JMenu        (ResourceBundle.getString("menu.instrument.text"));
      MenuItemInfo[] menuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.instrument.chord_diagram.text"    ), ResourceBundle.getString("menu.instrument.chord_diagram.icon"    ), ResourceBundle.getString("menu.instrument.chord_diagram.mnemonic"    ), ResourceBundle.getString("menu.instrument.chord_diagram.shortcut"), null, new ChordDiagramAction    ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.instrument.drum_map.text"         ), ResourceBundle.getString("menu.instrument.drum_map.icon"         ), ResourceBundle.getString("menu.instrument.drum_map.mnemonic"         ), ResourceBundle.getString("menu.instrument.drum_map.shortcut"     ), null, new DrumMapAction         ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(MenuItemInfo.SEPARATOR                                                                                                                                                                                                                                                                                                                                ),
                                      new MenuItemInfo(ResourceBundle.getString("menu.instrument.instruments.text"      ), ResourceBundle.getString("menu.instrument.instruments.icon"      ), ResourceBundle.getString("menu.instrument.instruments.mnemonic"      ), ResourceBundle.getString("menu.instrument.instrument.shortcut"   ), null, new InstrumentsAction     ((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.instrument.instrument_assign.text"), ResourceBundle.getString("menu.instrument.instrument_assign.icon"), ResourceBundle.getString("menu.instrument.instrument_assign.mnemonic"), ResourceBundle.getString("menu.instrument.instrument.shortcut"   ), null, new InstrumentAssignAction((MainFrame)this), JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : menuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            menu.addSeparator();
         else
            menu.add(menuItemInfo.getMenuItem());
      }
      return menu;
   }

   /**
    * @return the staff menu.
    */
   private JMenu buildStaffMenu()
   {
      SectionAction sectionAction = new SectionAction((MainFrame)this);
      StaffAction   staffAction   = new StaffAction  ((MainFrame)this);

      JMenu             menu              = new JMenu        (ResourceBundle.getString("menu.staff.text"));
      MenuItemInfo[]    menuItemInfos     = {new MenuItemInfo(ResourceBundle.getString("menu.staff.section_add.text"           ), ResourceBundle.getString("menu.staff.section_add.icon"           ), ResourceBundle.getString("menu.staff.section_add.mnemonic"           ), ResourceBundle.getString("menu.staff.section_add.shortcut"           ), SectionAction.ADD      , sectionAction, JMenuItem.class),
                                             new MenuItemInfo(ResourceBundle.getString("menu.staff.section_delete.text"        ), ResourceBundle.getString("menu.staff.section_delete.icon"        ), ResourceBundle.getString("menu.staff.section_delete.mnemonic"        ), ResourceBundle.getString("menu.staff.section_delete.shortcut"        ), SectionAction.DELETE   , sectionAction, JMenuItem.class),
                                             new MenuItemInfo(ResourceBundle.getString("menu.staff.section_move.up.text"       ), ResourceBundle.getString("menu.staff.section_move.up.icon"       ), ResourceBundle.getString("menu.staff.section_move.up.mnemonic"       ), ResourceBundle.getString("menu.staff.section_move.up.shortcut"       ), SectionAction.MOVE_UP  , sectionAction, JMenuItem.class),
                                             new MenuItemInfo(ResourceBundle.getString("menu.staff.section_move.down.text"     ), ResourceBundle.getString("menu.staff.section_move.down.icon"     ), ResourceBundle.getString("menu.staff.section_move.down.mnemonic"     ), ResourceBundle.getString("menu.staff.section_move.down.shortcut"     ), SectionAction.MOVE_DOWN, sectionAction, JMenuItem.class),
                                             new MenuItemInfo(MenuItemInfo.SEPARATOR                                                                                                                                                                                                                                                                                                                         ),
                                             new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_add.text"             ), ResourceBundle.getString("menu.staff.staff_add.icon"             ), ResourceBundle.getString("menu.staff.staff_add.mnemonic"             ), ResourceBundle.getString("menu.staff.staff_add.shortcut"             ), null                   , staffAction  , JMenu    .class),
                                             new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_delete.text"          ), ResourceBundle.getString("menu.staff.staff_delete.icon"          ), ResourceBundle.getString("menu.staff.staff_delete.mnemonic"          ), ResourceBundle.getString("menu.staff.staff_delete.shortcut"          ), StaffAction.DELETE     , staffAction  , JMenuItem.class),
                                             new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_move.up.text"         ), ResourceBundle.getString("menu.staff.staff_move.up.icon"         ), ResourceBundle.getString("menu.staff.staff_move.up.mnemonic"         ), ResourceBundle.getString("menu.staff.staff_move.up.shortcut"         ), StaffAction.MOVE_UP    , staffAction  , JMenuItem.class),
                                             new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_move.down.text"       ), ResourceBundle.getString("menu.staff.staff_move.down.icon"       ), ResourceBundle.getString("menu.staff.staff_move.down.mnemonic"       ), ResourceBundle.getString("menu.staff.staff_move.down.shortcut"       ), StaffAction.MOVE_DOWN  , staffAction  , JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : menuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            menu.addSeparator();
         else
            menu.add(menuItemInfo.getMenuItem());
      }

      JMenu          staffAddMenu          = (JMenu)menu.getItem(5);
      MenuItemInfo[] staffAddMenuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_add.bass_staff.text"    ), ResourceBundle.getString("menu.staff.staff_add.bass_staff.icon"    ), ResourceBundle.getString("menu.staff.staff_add.bass_staff.mnemonic"    ), ResourceBundle.getString("menu.staff.staff_add.bass_staff.shortcut"    ), StaffAction.ADD_BASS    , staffAction, JMenuItem.class),
                                              new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_add.drum_staff.text"    ), ResourceBundle.getString("menu.staff.staff_add.drum_staff.icon"    ), ResourceBundle.getString("menu.staff.staff_add.drum_staff.mnemonic"    ), ResourceBundle.getString("menu.staff.staff_add.drum_staff.shortcut"    ), StaffAction.ADD_DRUM    , staffAction, JMenuItem.class),
                                              new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_add.keyboard_staff.text"), ResourceBundle.getString("menu.staff.staff_add.keyboard_staff.icon"), ResourceBundle.getString("menu.staff.staff_add.keyboard_staff.mnemonic"), ResourceBundle.getString("menu.staff.staff_add.keyboard_staff.shortcut"), StaffAction.ADD_KEYBOARD, staffAction, JMenuItem.class),
                                              new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_add.rhythm_staff.text"  ), ResourceBundle.getString("menu.staff.staff_add.rhythm_staff.icon"  ), ResourceBundle.getString("menu.staff.staff_add.rhythm_staff.mnemonic"  ), ResourceBundle.getString("menu.staff.staff_add.rhythm_staff.shortcut"  ), StaffAction.ADD_RHYTHM  , staffAction, JMenuItem.class),
                                              new MenuItemInfo(ResourceBundle.getString("menu.staff.staff_add.tab_staff.text"     ), ResourceBundle.getString("menu.staff.staff_add.tab_staff.icon"     ), ResourceBundle.getString("menu.staff.staff_add.tab_staff.mnemonic"     ), ResourceBundle.getString("menu.staff.staff_add.tab_staff.shortcut"     ), StaffAction.ADD_TAB     , staffAction, JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : staffAddMenuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            staffAddMenu.addSeparator();
         else
            staffAddMenu.add(menuItemInfo.getMenuItem());
      }
      return menu;
   }

   /**
    * @return the tool menu.
    */
   private JMenu buildToolMenu()
   {
      JMenu          menu          = new JMenu        (ResourceBundle.getString("menu.tool.text"));
      MenuItemInfo[] menuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.tool.preferences.text"   ), ResourceBundle.getString("menu.tool.preferences.icon"   ), ResourceBundle.getString("menu.tool.preferences.mnemonic"   ), ResourceBundle.getString("menu.tool.preferences.shortcut"   ), null, null                                    , JMenu    .class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.tool.score.validate.text"), ResourceBundle.getString("menu.tool.score.validate.icon"), ResourceBundle.getString("menu.tool.score.validate.mnemonic"), ResourceBundle.getString("menu.tool.score.validate.shortcut"), null, new ScoreValidateAction((MainFrame)this), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.tool.score.layout.text"  ), ResourceBundle.getString("menu.tool.layout.icon"        ), ResourceBundle.getString("menu.tool.score.layout.mnemonic"  ), ResourceBundle.getString("menu.tool.score.layout.shortcut"  ), null, new ScoreLayoutAction  ((MainFrame)this), JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : menuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            menu.addSeparator();
         else
            menu.add(menuItemInfo.getMenuItem());
      }

      JMenu          preferencesMenu  = (JMenu)menu.getItem(0);
      MenuItemInfo[] subMenuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.tool.preferences.language.text"        ), ResourceBundle.getString("menu.tool.preferences.language.icon"        ), ResourceBundle.getString("menu.tool.preferences.language.mnemonic"        ), ResourceBundle.getString("menu.tool.preferences.language.shortcut"        ), null,                           null , JMenu    .class),
                                         new MenuItemInfo(ResourceBundle.getString("menu.tool.preferences.midi.text"            ), ResourceBundle.getString("menu.tool.preferences.midi.icon"            ), ResourceBundle.getString("menu.tool.preferences.midi.mnemonic"            ), ResourceBundle.getString("menu.tool.preferences.midi.shortcut"            ), null, new MidiAction((MainFrame)this), JMenuItem.class),
                                         new MenuItemInfo(ResourceBundle.getString("menu.tool.preferences.toolbars.text"        ), ResourceBundle.getString("menu.tool.preferences.toolbars.icon"        ), ResourceBundle.getString("menu.tool.preferences.toolbars.mnemonic"        ), ResourceBundle.getString("menu.tool.preferences.toolbars.shortcut"        ), null,                           null , JMenu    .class)};

      for(MenuItemInfo menuItemInfo : subMenuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            preferencesMenu.addSeparator();
         else
            preferencesMenu.add(menuItemInfo.getMenuItem());
      }

      LanguageAction languageAction        = new LanguageAction((MainFrame)this);
      JMenu          languageMenu          = (JMenu)(preferencesMenu.getItem(0));
      MenuItemInfo[] languageMenuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.tool.preferences.language.english.text"), ResourceBundle.getString("menu.tool.preferences.language.english.icon"), ResourceBundle.getString("menu.tool.preferences.language.english.mnemonic"), ResourceBundle.getString("menu.tool.preferences.language.english.shortcut"), "en", languageAction, JMenuItem.class),
                                              new MenuItemInfo(ResourceBundle.getString("menu.tool.preferences.language.german.text" ), ResourceBundle.getString("menu.tool.preferences.language.german.icon" ), ResourceBundle.getString("menu.tool.preferences.language.german.mnemonic" ), ResourceBundle.getString("menu.tool.preferences.language.german.shortcut" ), "de", languageAction, JMenuItem.class),
                                              new MenuItemInfo(ResourceBundle.getString("menu.tool.preferences.language.spanish.text"), ResourceBundle.getString("menu.tool.preferences.language.spanish.icon"), ResourceBundle.getString("menu.tool.preferences.language.spanish.mnemonic"), ResourceBundle.getString("menu.tool.preferences.language.spanish.shortcut"), "es", languageAction, JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : languageMenuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            languageMenu.addSeparator();
         else
            languageMenu.add(menuItemInfo.getMenuItem());
      }
      return menu;
   }

   /**
    * @return the help menu.
    */
   private JMenu buildHelpMenu()
   {
      HelpAction     helpAction    = new HelpAction((MainFrame)this);
      JMenu          menu          =  new JMenu       (ResourceBundle.getString("menu.help.text"));
      MenuItemInfo[] menuItemInfos = {new MenuItemInfo(ResourceBundle.getString("menu.help.contents.text"            ), ResourceBundle.getString("menu.help.contents.icon"            ), ResourceBundle.getString("menu.help.contents.mnemonic"            ), ResourceBundle.getString("menu.help.contents.shortcut"            ), null                 , new CSH.DisplayHelpFromSource(getHelpBroker()), JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.help.submit_bug.text"          ), ResourceBundle.getString("menu.help.submit_bug.icon"          ), ResourceBundle.getString("menu.help.submit_bug.mnemonic"          ), ResourceBundle.getString("menu.help.submit_bug.shortcut"          ), HelpAction.REPORT_BUG, helpAction                                    , JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.help.beaglebuddy_homepage.text"), ResourceBundle.getString("menu.help.beaglebuddy_homepage.icon"), ResourceBundle.getString("menu.help.beaglebuddy_homepage.mnemonic"), ResourceBundle.getString("menu.help.beaglebuddy_homepage.shortcut"), HelpAction.HOME_PAGE , helpAction                                    , JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("menu.help.about.text"               ), ResourceBundle.getString("menu.help.about.icon"               ), ResourceBundle.getString("menu.help.about.mnemonic"               ), ResourceBundle.getString("menu.help.about.shortcut"               ), HelpAction.ABOUT     , helpAction                                    , JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : menuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            menu.addSeparator();
         else
            menu.add(menuItemInfo.getMenuItem());
      }
      return menu;
   }

   /**
    * sets the recently used file menu items.
    */
   private void setRecentlyUsedFileMenu()
   {
      // remove the current recent files list menu items
      JMenu songMenu        = getJMenuBar().getMenu(0);
      JMenu recentFilesMenu = (JMenu)songMenu.getItem(2);
      recentFilesMenu.removeAll();
      MenuItemInfo[] menuItemInfos = new MenuItemInfo[recentFiles.size()];

      for(int i=0; i<recentFiles.size(); ++i)
         menuItemInfos[i] = new MenuItemInfo(recentFiles.get(i), null, null, null, null, new RecentFilesAction((MainFrame)this), JMenuItem.class);

      for(MenuItemInfo menuItemInfo : menuItemInfos)
         recentFilesMenu.add(menuItemInfo.getMenuItem());
   }


   /**
    * left over from the old gui factory class
    */
/* private static JMenu buildToolMenu(ResourceBundle resource, MainFrame frame)
   {
      JMenu            menu      = new JMenu     (resource.getString("menu_tools"                              ));
      String[]         sub       = new String[]  {resource.getString("menu_tools_instruments"                  ),
                                                  resource.getString("menu_tools_toolbars"                     ),
                                                  resource.getString("menu_tools_tuning"                       )};
      String[][]       text      = new String[][]{{resource.getString("menu_tools_instruments_current"         ),
                                                   resource.getString("menu_tools_instruments_default"         )},
                                                  {resource.getString("menu_tools_toolbars_current"            ),
                                                   resource.getString("menu_tools_toolbars_default"            )},
                                                  {resource.getString("menu_tools_tuning_current"              ),
                                                   resource.getString("menu_tools_tuning_default"              )}};
      char[][]         mnemonics = new char[][]  {{resource.getString("menu_tools_instruments_current_mnemonic").charAt(0),
                                                   resource.getString("menu_tools_instruments_default_mnemonic").charAt(0)},
                                                  {resource.getString("menu_tools_toolbars_current_mnemonic"   ).charAt(0),
                                                   resource.getString("menu_tools_toolbars_default_mnemonic"   ).charAt(0)},
                                                  {resource.getString("menu_tools_tuning_current_mnemonic"     ).charAt(0),
                                                   resource.getString("menu_tools_tuning_default_mnemonic"     ).charAt(0)}};
      String[][]       shortcuts = new String[][]{{resource.getString("menu_tools_instruments_current_shortcut"),
                                                   resource.getString("menu_tools_instruments_default_shortcut")},
                                                  {resource.getString("menu_tools_toolbars_current_shortcut"   ),
                                                   resource.getString("menu_tools_toolbars_default_shortcut"   )},
                                                  {resource.getString("menu_tools_tuning_current_shortcut"     ),
                                                   resource.getString("menu_tools_tuning_default_shortcut"     )}};
      ActionListener[][] listeners = new ActionListener[][]{{new ToolInstrumentCurrentAction(frame),
                                                             new ToolInstrumentDefaultAction(frame)},
                                                            {new ToolToolbarsCurrentAction(frame)  ,
                                                             new ToolToolbarsDefaultAction(frame)  },
                                                            {new ToolTuningCurrentAction(frame)    ,
                                                             new ToolTuningDefaultAction(frame)    }};
 */
}
