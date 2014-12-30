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
import com.beaglebuddy.tab.gui.action.menu.song.*;
import com.beaglebuddy.tab.gui.action.toolbar.*;
import com.beaglebuddy.tab.gui.action.toolbar.midi.*;
import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;




/**
 * This main frame base class manages the following aspects of the application:
 * <ol>
 *    <li>the toolbars</li>
 *    <li>receiving a notification when the song is finished playing back on a midi device</li>
 * </ol>
 */
public abstract class MainFrameToolbar extends MainFrameCursor implements MetaEventListener
{
   // class members
   private static final Dimension MELODY_LINE_COMBO_BOX_SIZE                 = new Dimension(140, 16);

   public static final String     TOOLBAR_MAIN_NAME                          = "main";               // components on the main toolbar
   public static final int        TOOLBAR_MAIN_SONG_NEW                      = 0;
   public static final int        TOOLBAR_MAIN_SONG_OPEN                     = 1;
   public static final int        TOOLBAR_MAIN_SONG_SAVE                     = 2;
   public static final int        TOOLBAR_MAIN_SONG_PRINT                    = 3;
   public static final int        TOOLBAR_MAIN_SEPARATOR                     = 4;
   public static final int        TOOLBAR_MAIN_EDIT_CUT                      = 5;
   public static final int        TOOLBAR_MAIN_EDIT_COPY                     = 6;
   public static final int        TOOLBAR_MAIN_EDIT_PASTE                    = 7;

   public static final String     TOOLBAR_MIDI_NAME                          = "midi";               // components on the midi toolbar
   public static final int        TOOLBAR_MIDI_PLAY_FOM_BEGINNING            = 0;
   public static final int        TOOLBAR_MIDI_PLAY_FROM_CURRENT             = 1;
   public static final int        TOOLBAR_MIDI_PAUSE                         = 2;
   public static final int        TOOLBAR_MIDI_STOP                          = 3;

   public static final String     TOOLBAR_NOTE_NAME                          = "note";               // components on the note toolbar
   public static final int        TOOLBAR_NOTE_MELODY_LINE                   = 0;
   public static final int        TOOLBAR_NOTE_WHOLE                         = 1;
   public static final int        TOOLBAR_NOTE_HALF                          = 2;
   public static final int        TOOLBAR_NOTE_QUARTER                       = 3;
   public static final int        TOOLBAR_NOTE_EIGTH                         = 4;
   public static final int        TOOLBAR_NOTE_SIXTEENTH                     = 5;
   public static final int        TOOLBAR_NOTE_THIRTY_SECOND                 = 6;
   public static final int        TOOLBAR_NOTE_DOTTED                        = 7;
   public static final int        TOOLBAR_NOTE_DOUBLE_DOTTED                 = 8;

   public static final String     TOOLBAR_REST_NAME                          = "rest";               // components on the rest toolbar
   public static final int        TOOLBAR_REST_MELODY_LINE                   = 0;
   public static final int        TOOLBAR_REST_WHOLE                         = 1;
   public static final int        TOOLBAR_REST_HALF                          = 2;
   public static final int        TOOLBAR_REST_QUARTER                       = 3;
   public static final int        TOOLBAR_REST_EIGTH                         = 4;
   public static final int        TOOLBAR_REST_SIXTEENTH                     = 5;
   public static final int        TOOLBAR_REST_THIRTY_SECOND                 = 6;
   public static final int        TOOLBAR_REST_DOTTED                        = 7;
   public static final int        TOOLBAR_REST_DOUBLE_DOTTED                 = 8;

   public static final String     TOOLBAR_BARLINE_NAME                       = "barline";            // components on the barline toolbar
   public static final int        TOOLBAR_BARLINE_BARLINE                    = 0;
   public static final int        TOOLBAR_BARLINE_ALTERNATE_ENDING           = 1;
   public static final int        TOOLBAR_BARLINE_KEY_SIGNATURE              = 2;
   public static final int        TOOLBAR_BARLINE_REHEARSAL_SIGN             = 3;
   public static final int        TOOLBAR_BARLINE_TIME_SIGNATURE             = 4;
   public static final int        TOOLBAR_BARLINE_TEMPO_MARKER               = 5;
   public static final int        TOOLBAR_BARLINE_DIRECTION                  = 6;
   public static final int        TOOLBAR_BARLINE_DYNAMIC                    = 7;

   public static final String     TOOLBAR_TAB_SYMBOL_NAME                    = "tab_symbols";        // components on the tab symbols toolbar
   public static final int        TOOLBAR_TAB_SYMBOL_ARPEGGIO                = 0;
   public static final int        TOOLBAR_TAB_SYMBOL_BEND                    = 1;
   public static final int        TOOLBAR_TAB_SYMBOL_FINGERING               = 2;
   public static final int        TOOLBAR_TAB_SYMBOL_GRACE_NOTE              = 3;
   public static final int        TOOLBAR_TAB_SYMBOL_HARMONIC                = 4;
   public static final int        TOOLBAR_TAB_SYMBOL_MUTED_NOTE              = 5;
   public static final int        TOOLBAR_TAB_SYMBOL_PICKSTROKE              = 6;
   public static final int        TOOLBAR_TAB_SYMBOL_RAKE                    = 7;
   public static final int        TOOLBAR_TAB_SYMBOL_SLIDE                   = 8;
   public static final int        TOOLBAR_TAB_SYMBOL_SLUR                    = 9;
   public static final int        TOOLBAR_TAB_SYMBOL_TAP                     = 10;
   public static final int        TOOLBAR_TAB_SYMBOL_TEXT                    = 11;
   public static final int        TOOLBAR_TAB_SYMBOL_TREMOLO_BAR             = 12;
   public static final int        TOOLBAR_TAB_SYMBOL_TREMOLO_PICKING         = 13;
   public static final int        TOOLBAR_TAB_SYMBOL_TRILL                   = 14;
   public static final int        TOOLBAR_TAB_SYMBOL_VIBRATO                 = 15;

   public static final String     TOOLBAR_MUSIC_SYMBOL_NAME                  = "music_symbols";        // components on the music symbols toolbar
   public static final int        TOOLBAR_MUSIC_SYMBOL_DIRECTION             = 0;
   public static final int        TOOLBAR_MUSIC_SYMBOL_IRREGULAR_GROUPING    = 1;
   public static final int        TOOLBAR_MUSIC_SYMBOL_TIE                   = 2;
   public static final int        TOOLBAR_MUSIC_SYMBOL_STACCATO              = 3;
   public static final int        TOOLBAR_MUSIC_SYMBOL_ACCENT_MARCATO        = 4;
   public static final int        TOOLBAR_MUSIC_SYMBOL_ACCENT_SFORZANDO      = 5;
   public static final int        TOOLBAR_MUSIC_SYMBOL_OCTAVE                = 6;
   public static final int        TOOLBAR_MUSIC_SYMBOL_LET_RING              = 7;
   public static final int        TOOLBAR_MUSIC_SYMBOL_FERMATA               = 8;
   public static final int        TOOLBAR_MUSIC_SYMBOL_CHORD_NAME            = 9;

   // data members
   private JPanel                      toolbarPanel;                    // panel at the top of the application frame where all the toolbars dock.
   private Hashtable<String, JToolBar> toolbars;                        // list of toolbars and their names




   /**
    * default constructor.
    */
   public MainFrameToolbar()
   {
      toolbarPanel = new JPanel();

      // demark the toolbar panel with an etched border
      toolbarPanel.setBorder(new EtchedBorder());
      // set the layout manager for the toolbars
      toolbarPanel.setLayout(new GridLayout(4,2));

      setToolbars();
   }

   /**
    * @return the toolbar with the given name.
    * <br/><br/>
    * @param name   the name of the toolbar.
    */
   public JToolBar getToolbar(String name)
   {
      return toolbars.get(name);
   }

   /**
    * @return the toolbar panel at the top of the application frame where all the toolbars dock.
    */
   public JPanel getToolBarPanel()
   {
      return toolbarPanel;
   }

   /**
    * turn on\off the toolbars according to the user's preferences.
    */
   public void setToolbars()
   {
      // todo: read in toolbar preferences for which toolbars the user wants displayed

      // remove the current toolbars
      if (toolbars != null && toolbars.get(TOOLBAR_MAIN_NAME        ) != null) toolbarPanel.remove(toolbars.get(TOOLBAR_MAIN_NAME        ));
      if (toolbars != null && toolbars.get(TOOLBAR_NOTE_NAME        ) != null) toolbarPanel.remove(toolbars.get(TOOLBAR_NOTE_NAME        ));
      if (toolbars != null && toolbars.get(TOOLBAR_MIDI_NAME        ) != null) toolbarPanel.remove(toolbars.get(TOOLBAR_MIDI_NAME        ));
      if (toolbars != null && toolbars.get(TOOLBAR_REST_NAME        ) != null) toolbarPanel.remove(toolbars.get(TOOLBAR_REST_NAME        ));
      if (toolbars != null && toolbars.get(TOOLBAR_BARLINE_NAME     ) != null) toolbarPanel.remove(toolbars.get(TOOLBAR_BARLINE_NAME     ));
      if (toolbars != null && toolbars.get(TOOLBAR_TAB_SYMBOL_NAME  ) != null) toolbarPanel.remove(toolbars.get(TOOLBAR_TAB_SYMBOL_NAME  ));
      if (toolbars != null && toolbars.get(TOOLBAR_MUSIC_SYMBOL_NAME) != null) toolbarPanel.remove(toolbars.get(TOOLBAR_MUSIC_SYMBOL_NAME));

      // create the new ones so that images and tooltips can be reloaded from the resource bundle when the user selects a new language
      toolbars = new Hashtable<String, JToolBar>();
      toolbars.put(TOOLBAR_MAIN_NAME        , buildMainToolbar       ());
      toolbars.put(TOOLBAR_MIDI_NAME        , buildMidiToolbar       ());
      toolbars.put(TOOLBAR_NOTE_NAME        , buildNoteToolbar       ());
      toolbars.put(TOOLBAR_REST_NAME        , buildRestToolbar       ());
      toolbars.put(TOOLBAR_BARLINE_NAME     , buildBarlineToolbar    ());
      toolbars.put(TOOLBAR_TAB_SYMBOL_NAME  , buildTabSymbolToolbar  ());
      toolbars.put(TOOLBAR_MUSIC_SYMBOL_NAME, buildMusicSymbolToolbar());

      toolbarPanel.add(toolbars.get(TOOLBAR_MAIN_NAME        ));
      toolbarPanel.add(toolbars.get(TOOLBAR_NOTE_NAME        ));
      toolbarPanel.add(toolbars.get(TOOLBAR_MIDI_NAME        ));
      toolbarPanel.add(toolbars.get(TOOLBAR_REST_NAME        ));
      toolbarPanel.add(toolbars.get(TOOLBAR_BARLINE_NAME     ));
      toolbarPanel.add(toolbars.get(TOOLBAR_TAB_SYMBOL_NAME  ));
      toolbarPanel.add(toolbars.get(TOOLBAR_MUSIC_SYMBOL_NAME));
   }

   /**
    * @return the barline toolbar.
    */
   private JToolBar buildBarlineToolbar()
   {
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("toolbar.barline.title"));
      ToolbarItemInfo[] toolbarItemInfos = {new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.barline.barline.icon"         ), ResourceBundle.getString("toolbar.barline.barline.tooltip"         ), "barline"         , new BarlineAction        ((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.barline.alternate_ending.icon"), ResourceBundle.getString("toolbar.barline.alternate_ending.tooltip"), "alternate_ending", new AlternateEndingAction((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.barline.key_signature.icon"   ), ResourceBundle.getString("toolbar.barline.key_signature.tooltip"   ), "key_signature"   , new KeySignatureAction   ((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.barline.time_signature.icon"  ), ResourceBundle.getString("toolbar.barline.time_signature.tooltip"  ), "time_signature"  , new TimeSignatureAction  ((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.barline.tempo.icon"           ), ResourceBundle.getString("toolbar.barline.tempo.tooltip"           ), "tempo"           , new TempoAction          ((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.barline.rehearsal_sign.icon"  ), ResourceBundle.getString("toolbar.barline.rehearsal_sign.tooltip"  ), "rehearsal_sign"  , new RehearsalSignAction  ((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.barline.dynamic.icon"         ), ResourceBundle.getString("toolbar.barline.dynamic.tooltip"         ), "dynamic"         , new VolumeAction         ((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.barline.direction.icon"       ), ResourceBundle.getString("toolbar.barline.direction.tooltip"       ), "direction"       , new DirectionAction      ((MainFrame)this), true )};

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());
      return toolbar;
   }

   /**
    * build the bass toolbar
    */
/* private static JToolBar buildBassToolbar(ResourceBundle resource, Properties properties, MainFrame frame, String root) throws MissingResourceException
   {
      String           title     =                     resource.getString("toolbar_bass");
      String[]         iconFiles = new String[]        {root + properties.getProperty(Properties.KEY_TOOLBAR_BASS_ICON_SLAP),
                                                        root + properties.getProperty(Properties.KEY_TOOLBAR_BASS_ICON_POP )};
      String[]         tooltips  = new String[]        {resource.getString("tooltip_bass_slap"),
                                                        resource.getString("tooltip_bass_pop" )};
      String[]         ids       = new String[]        {"slap",
                                                        "pop" };

      return(buildToolbar(title, iconFiles, tooltips, ids, frame, new BassAction(frame)));
   }
  */

   /**
    * @return the main toolbar.
    */
   private JToolBar buildMainToolbar()
   {
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("toolbar.main.title"));
      ToolbarItemInfo[] toolbarItemInfos = {new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.main.new.icon"  ), ResourceBundle.getString("toolbar.main.new.tooltip"  ), "new"  , new NewAction  ((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.main.open.icon" ), ResourceBundle.getString("toolbar.main.open.tooltip" ), "open" , new OpenAction ((MainFrame)this), true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.main.save.icon" ), ResourceBundle.getString("toolbar.main.save.tooltip" ), "save" , new SaveAction ((MainFrame)this), false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.main.print.icon"), ResourceBundle.getString("toolbar.main.print.tooltip"), "print", new PrintAction((MainFrame)this), false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Separator                                                                                                                                                            ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.main.cut.icon"  ), ResourceBundle.getString("toolbar.main.cut.tooltip"  ), "cut"  , new CutAction  ((MainFrame)this), false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.main.copy.icon" ), ResourceBundle.getString("toolbar.main.copy.tooltip" ), "copy" , new CopyAction ((MainFrame)this), false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.main.paste.icon"), ResourceBundle.getString("toolbar.main.paste.tooltip"), "paste", new PasteAction((MainFrame)this), false)};

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());
      return toolbar;
   }

   /**
    * @return the midi toolbar.
    */
   private JToolBar buildMidiToolbar()
   {
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("toolbar.midi.title"));
      ToolbarItemInfo[] toolbarItemInfos = {new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.midi.play.from_beginning.icon"       ), ResourceBundle.getString("toolbar.midi.play.from_beginning.tooltip"       ), "play_from_beginning"       , new PlayAction ((MainFrame)this), false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.midi.play.from_current_location.icon"), ResourceBundle.getString("toolbar.midi.play.from_current_location.tooltip"), "play_from_current_location", new PlayAction ((MainFrame)this), false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.midi.stop.icon"                      ), ResourceBundle.getString("toolbar.midi.stop.tooltip"                      ), "stop"                      , new StopAction ((MainFrame)this), false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button, ResourceBundle.getString("toolbar.midi.pause.icon"                     ), ResourceBundle.getString("toolbar.midi.pause.tooltip"                     ), "pause"                     , new PauseAction((MainFrame)this), false)};

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());
      return toolbar;
   }

   /**
    * @return the note toolbar.
    */
   private JToolBar buildNoteToolbar()
   {
      NoteAction        noteAction       = new NoteAction((MainFrame)this);
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("toolbar.note.title"));
      ToolbarItemInfo[] toolbarItemInfos = {new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.note.whole.icon"        ), ResourceBundle.getString("toolbar.note.whole.tooltip"        ), NoteAction.WHOLE        , noteAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.note.half.icon"         ), ResourceBundle.getString("toolbar.note.half.tooltip"         ), NoteAction.HALF         , noteAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.note.quarter.icon"      ), ResourceBundle.getString("toolbar.note.quarter.tooltip"      ), NoteAction.QUARTER      , noteAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.note.eigth.icon"        ), ResourceBundle.getString("toolbar.note.eigth.tooltip"        ), NoteAction.EIGTH        , noteAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.note.sixteenth.icon"    ), ResourceBundle.getString("toolbar.note.sixteenth.tooltip"    ), NoteAction.SIXTEENTH    , noteAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.note.thirty_second.icon"), ResourceBundle.getString("toolbar.note.thirty_second.tooltip"), NoteAction.THIRTY_SECOND, noteAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.note.dotted.icon"       ), ResourceBundle.getString("toolbar.note.dotted.tooltip"       ), NoteAction.DOTTED       , noteAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.note.double_dotted.icon"), ResourceBundle.getString("toolbar.note.double_dotted.tooltip"), NoteAction.DOUBLE_DOTTED, noteAction, false)};

      String[]  choices  = {ResourceBundle.getString("toolbar.note.combo_box.high_melody_line"), ResourceBundle.getString("toolbar.note.combo_box.low_melody_line")};
      JComboBox comboBox = new JComboBox(choices);
      comboBox.setEnabled(false);
      comboBox.setPreferredSize(MELODY_LINE_COMBO_BOX_SIZE);
      comboBox.setMaximumSize  (MELODY_LINE_COMBO_BOX_SIZE);

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      toolbar.add(comboBox);
      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());
      return toolbar;
   }

   /**
    * @return the rest toolbar.
    */
   private JToolBar buildRestToolbar()
   {
      RestAction        restAction       = new RestAction((MainFrame)this);
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("toolbar.rest.title"));
      ToolbarItemInfo[] toolbarItemInfos = {new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.rest.whole.icon"        ), ResourceBundle.getString("toolbar.rest.whole.tooltip"        ), RestAction.WHOLE        , restAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.rest.half.icon"         ), ResourceBundle.getString("toolbar.rest.half.tooltip"         ), RestAction.HALF         , restAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.rest.quarter.icon"      ), ResourceBundle.getString("toolbar.rest.quarter.tooltip"      ), RestAction.QUARTER      , restAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.rest.eigth.icon"        ), ResourceBundle.getString("toolbar.rest.eigth.tooltip"        ), RestAction.EIGTH        , restAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.rest.sixteenth.icon"    ), ResourceBundle.getString("toolbar.rest.sixteenth.tooltip"    ), RestAction.SIXTEENTH    , restAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.rest.thirty_second.icon"), ResourceBundle.getString("toolbar.rest.thirty_second.tooltip"), RestAction.THIRTY_SECOND, restAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.rest.dotted.icon"       ), ResourceBundle.getString("toolbar.rest.dotted.tooltip"       ), RestAction.DOTTED       , restAction, false),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.rest.double_dotted.icon"), ResourceBundle.getString("toolbar.rest.double_dotted.tooltip"), RestAction.DOUBLE_DOTTED, restAction, false)};

      String[]   choices  = {ResourceBundle.getString("toolbar.rest.combo_box.high_melody_line"), ResourceBundle.getString("toolbar.rest.combo_box.low_melody_line")};
      JComboBox comboBox = new JComboBox(choices);
      comboBox.setEnabled(false);
      comboBox.setPreferredSize(MELODY_LINE_COMBO_BOX_SIZE);
      comboBox.setMaximumSize  (MELODY_LINE_COMBO_BOX_SIZE);

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      toolbar.add(comboBox);
      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());
      return toolbar;
   }

   /**
    * @return the tab symbol toolbar.
    */
   private JToolBar buildTabSymbolToolbar()
   {
      TabSymbolAction   action           = new TabSymbolAction((MainFrame)this);
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("toolbar.tab_symbol.title"));
      ToolbarItemInfo[] toolbarItemInfos = {new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.arpeggio.icon"          ), ResourceBundle.getString("toolbar.tab_symbol.arpeggio.tooltip"          ), TabSymbolAction.ARPEGGIO          , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.bend.icon"              ), ResourceBundle.getString("toolbar.tab_symbol.bend.tooltip"              ), TabSymbolAction.BEND              , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.fingering.icon"         ), ResourceBundle.getString("toolbar.tab_symbol.fingering.tooltip"         ), TabSymbolAction.FINGERING         , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.tab_symbol.grace_note.icon"        ), ResourceBundle.getString("toolbar.tab_symbol.grace_note.tooltip"        ), TabSymbolAction.GRACE_NOTE        , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.harmonic.icon"          ), ResourceBundle.getString("toolbar.tab_symbol.harmonic.tooltip"          ), TabSymbolAction.HARMONIC          , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.muted_note.icon"        ), ResourceBundle.getString("toolbar.tab_symbol.muted_note.tooltip"        ), TabSymbolAction.MUTED_NOTE        , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.pickstroke.icon"        ), ResourceBundle.getString("toolbar.tab_symbol.pickstroke.tooltip"        ), TabSymbolAction.PICKSTROKE        , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.rake.icon"              ), ResourceBundle.getString("toolbar.tab_symbol.rake.tooltip"              ), TabSymbolAction.RAKE              , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.slide.icon"             ), ResourceBundle.getString("toolbar.tab_symbol.slide.tooltip"             ), TabSymbolAction.SLIDE             , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.slur.icon"              ), ResourceBundle.getString("toolbar.tab_symbol.slur.tooltip"              ), TabSymbolAction.SLUR              , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.tab_symbol.tap.icon"               ), ResourceBundle.getString("toolbar.tab_symbol.tap.tooltip"               ), TabSymbolAction.TAP               , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.text.icon"              ), ResourceBundle.getString("toolbar.tab_symbol.text.tooltip"              ), TabSymbolAction.TEXT              , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.tremolo_bar.icon"       ), ResourceBundle.getString("toolbar.tab_symbol.tremolo_bar.tooltip"       ), TabSymbolAction.TREMOLO_BAR       , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.tab_symbol.tremolo_picking.icon"   ), ResourceBundle.getString("toolbar.tab_symbol.tremolo_picking.tooltip"   ), TabSymbolAction.TREMOLO_PICKING   , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.trill.icon"             ), ResourceBundle.getString("toolbar.tab_symbol.trill.tooltip"             ), TabSymbolAction.TRILL             , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.tab_symbol.vibrato.icon"           ), ResourceBundle.getString("toolbar.tab_symbol.vibrato.tooltip"           ), TabSymbolAction.VIBRATO           , action, true )};

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());
      return toolbar;
   }

   /**
    * @return the music symbol toolbar.
    */
   private JToolBar buildMusicSymbolToolbar()
   {
      MusicSymbolAction action           = new MusicSymbolAction((MainFrame)this);
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("toolbar.music_symbol.title"));
      ToolbarItemInfo[] toolbarItemInfos = {
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.music_symbol.irregular_grouping.icon"), ResourceBundle.getString("toolbar.music_symbol.irregular_grouping.tooltip"), MusicSymbolAction.IRREGULAR_GROUPING, action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.music_symbol.tie.icon"               ), ResourceBundle.getString("toolbar.music_symbol.tie.tooltip"               ), MusicSymbolAction.TIE               , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.music_symbol.staccato.icon"          ), ResourceBundle.getString("toolbar.music_symbol.staccato.tooltip"          ), MusicSymbolAction.STACCATO          , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.music_symbol.accent_marcato.icon"    ), ResourceBundle.getString("toolbar.music_symbol.accent_marcato.tooltip"    ), MusicSymbolAction.ACCENT_MARCATO    , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.music_symbol.accent_sforzando.icon"  ), ResourceBundle.getString("toolbar.music_symbol.accent_sforzando.tooltip"  ), MusicSymbolAction.ACCENT_SFORZANDO  , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.music_symbol.octave.icon"            ), ResourceBundle.getString("toolbar.music_symbol.octave.tooltip"            ), MusicSymbolAction.OCTAVE            , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.music_symbol.let_ring.icon"          ), ResourceBundle.getString("toolbar.music_symbol.let_ring.tooltip"          ), MusicSymbolAction.LET_RING          , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("toolbar.music_symbol.fermata.icon"           ), ResourceBundle.getString("toolbar.music_symbol.fermata.tooltip"           ), MusicSymbolAction.FERMATA           , action, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Button       , ResourceBundle.getString("toolbar.music_symbol.chord_name.icon"        ), ResourceBundle.getString("toolbar.music_symbol.chord_name.tooltip"        ), MusicSymbolAction.CHORD_NAME        , action, true )};

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());
      return toolbar;
   }

   /**
    * implements the MetaEventListener interface.
    * Since the sequencer's start() method does not block, code execution continues once a song's playback has started and thus we need to listen for the "end of track" event to
    * know when the sequence has finished playing or the user has pressed the "stop" button on the toolbar.
    * <br/><br/>
    * @param event
    */
   public void meta(MetaMessage event)
   {
      if (event.getType() == Midi.EVENT_END_OF_TRACK)
      {
         // end of track action here
         getSong().stop();
         // disable "stop" toolbar icon, enable "play" toolbar icon
         MainFrame frame   = (MainFrame)this;
         JToolBar  toolbar = frame.getToolbar(MainFrame.TOOLBAR_MIDI_NAME);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FOM_BEGINNING).setEnabled(true );
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PLAY_FROM_CURRENT ).setEnabled(true );
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_PAUSE             ).setEnabled(false);
         toolbar.getComponent(MainFrame.TOOLBAR_MIDI_STOP              ).setEnabled(false);
         frame.validate();
      }
   }
}
