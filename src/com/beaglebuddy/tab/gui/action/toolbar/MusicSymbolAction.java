/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.toolbar;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.music_symbols.ChordNameDialog;
import com.beaglebuddy.tab.gui.dialog.music_symbols.IrregularNoteGroupingDialog;
import com.beaglebuddy.tab.gui.dialog.music_symbols.OctaveDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Chord;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Note;
import com.beaglebuddy.tab.model.attribute.chord.AccentMarcato;
import com.beaglebuddy.tab.model.attribute.chord.AccentSforzando;
import com.beaglebuddy.tab.model.attribute.chord.ChordAttribute;
import com.beaglebuddy.tab.model.attribute.chord.ChordName;
import com.beaglebuddy.tab.model.attribute.chord.Fermata;
import com.beaglebuddy.tab.model.attribute.chord.LetRing;
import com.beaglebuddy.tab.model.attribute.chord.Staccato;
import com.beaglebuddy.tab.model.attribute.duration.DurationAttribute;
import com.beaglebuddy.tab.model.attribute.duration.IrregularGrouping;
import com.beaglebuddy.tab.model.attribute.note.NoteAttribute;
import com.beaglebuddy.tab.model.attribute.note.Octave;
import com.beaglebuddy.tab.model.staff.Staff;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.JToolBar;




/**
 * called when the user selects one of the <i>music symbol</i> icons from the <i>music symbol</i> toolbar.
 */
public class MusicSymbolAction extends BaseAction
{
   // class members
   public static final String IRREGULAR_GROUPING = "irregular_grouping";
   public static final String TIE                = "tie";
   public static final String STACCATO           = "staccato";
   public static final String ACCENT_MARCATO     = "accent_marcato";
   public static final String ACCENT_SFORZANDO   = "accent_sforzando";
   public static final String OCTAVE             = "octave";
   public static final String LET_RING           = "let_ring";
   public static final String FERMATA            = "fermata";
   public static final String CHORD_NAME         = "chord_name";




   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public MusicSymbolAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * display the dialog box for the music symbol the user selected.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String   command  = event.getActionCommand();
      Location location = frame.getCurrentLocation();
      Staff    staff    = frame.getSong().getScore().getSections().get(location.getSection()).getStaffs().get(location.getStaff());
      Chord    chord    = staff.getChords(Staff.HighVoice).get(location.getPosition());
      Note     note     = chord.getNotes().get(location.getNote());
      JToolBar toolbar  = frame.getToolbar(MainFrame.TOOLBAR_MUSIC_SYMBOL_NAME);

      if (command.equals(IRREGULAR_GROUPING))
      {
         // todo: this applies to a group of durations - not just a single duration
         IrregularGrouping irregularGrouping = (IrregularGrouping)chord.getDurationAttribute(DurationAttribute.Type.IrregularGrouping);
         IrregularNoteGroupingDialog dialog = new IrregularNoteGroupingDialog(frame, irregularGrouping);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the chords
         if (dialog.wasOKed())
         {
            irregularGrouping = dialog.getIrregularGrouping();
            if (irregularGrouping == null)
               chord.removeDurationAttribute(DurationAttribute.Type.IrregularGrouping);
            else
               chord.addDurationAttribute(irregularGrouping);
         }
      }
      else if (command.equals(TIE))
      {
         super.actionPerformed(event);
      }
      else if (command.equals(STACCATO))
      {
         // get the "staccato" toolbar button
         AbstractButton button = (AbstractButton)toolbar.getComponentAtIndex(MainFrame.TOOLBAR_MUSIC_SYMBOL_STACCATO);

         if (button.isSelected())
            chord.addAttribute(new Staccato());
         else
            chord.removeAttribute(ChordAttribute.Type.Staccato);
      }
      else if (command.equals(ACCENT_MARCATO))
      {
         // get the "marcato" toolbar button
         AbstractButton button = (AbstractButton)toolbar.getComponentAtIndex(MainFrame.TOOLBAR_MUSIC_SYMBOL_ACCENT_MARCATO);

         if (button.isSelected())
            chord.addAttribute(new AccentMarcato());
         else
            chord.removeAttribute(ChordAttribute.Type.AccentMarcato);
      }
      else if (command.equals(ACCENT_SFORZANDO))
      {
         // get the "sforzando" toolbar button
         AbstractButton button = (AbstractButton)toolbar.getComponentAtIndex(MainFrame.TOOLBAR_MUSIC_SYMBOL_ACCENT_SFORZANDO);

         if (button.isSelected())
            chord.addAttribute(new AccentSforzando());
         else
            chord.removeAttribute(ChordAttribute.Type.AccentSforzando);
      }
      else if (command.equals(OCTAVE))
      {
         // show the dialog and let the user select an octave for the current note
         Octave       octave = (Octave)note.getAttribute(NoteAttribute.Type.Octave);
         OctaveDialog dialog = new OctaveDialog(frame, octave);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the note
         if (dialog.wasOKed())
         {
            octave = dialog.getOctave();
            if (octave == null)
               note.removeAttribute(NoteAttribute.Type.Octave);
            else
               note.addAttribute(octave);
         }
      }
      else if (command.equals(LET_RING))
      {
         // get the "let ring" toolbar button
         AbstractButton button = (AbstractButton)toolbar.getComponentAtIndex(MainFrame.TOOLBAR_MUSIC_SYMBOL_LET_RING);

         if (button.isSelected())
            chord.addAttribute(new LetRing());
         else
            chord.removeAttribute(ChordAttribute.Type.LetRing);
      }
      else if (command.equals(FERMATA))
      {
         // get the "fermata" toolbar button
         AbstractButton button = (AbstractButton)toolbar.getComponentAtIndex(MainFrame.TOOLBAR_MUSIC_SYMBOL_FERMATA);

         if (button.isSelected())
            chord.addAttribute(new Fermata());
         else
            chord.removeAttribute(ChordAttribute.Type.Fermata);
      }
      else if (command.equals(CHORD_NAME))
      {
         // show the dialog and let the user select a chord name for the current chord
         ArrayList<ChordDiagram> chordDiagrams = frame.getSong().getChordDiagrams();
         ChordName               chordName     = (ChordName)chord.getAttribute(ChordAttribute.Type.ChordName);
         ChordNameDialog         dialog        = new ChordNameDialog(frame, chordName, chordDiagrams);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the chord
         if (dialog.wasOKed())
         {
            chordName = dialog.getChordName();
            if (chordName == null)
               chord.removeAttribute(ChordAttribute.Type.ChordName);
            else
               chord.addAttribute(chordName);
         }
      }
      else
      {
         throw new RuntimeException("Developer error: this should not occur");
      }
   }
}
