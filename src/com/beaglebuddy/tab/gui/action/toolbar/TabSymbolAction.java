/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.toolbar;

//import com.beaglebuddy.tab.gui.dialog.tab_symbols.MutedNoteDialog;
//import com.beaglebuddy.tab.gui.dialog.tab_symbols.SlurDialog;
//import com.beaglebuddy.tab.gui.dialog.tab_symbols.TremoloBarDialog;
import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.TextDialog;
import com.beaglebuddy.tab.gui.dialog.tab_symbols.ArpeggioDialog;
import com.beaglebuddy.tab.gui.dialog.tab_symbols.BendDialog;
import com.beaglebuddy.tab.gui.dialog.tab_symbols.FingeringDialog;
import com.beaglebuddy.tab.gui.dialog.tab_symbols.HarmonicDialog;
import com.beaglebuddy.tab.gui.dialog.tab_symbols.PickStrokeDialog;
import com.beaglebuddy.tab.gui.dialog.tab_symbols.TrillDialog;
import com.beaglebuddy.tab.gui.dialog.tab_symbols.VibratoDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Chord;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Note;
import com.beaglebuddy.tab.model.attribute.chord.Appoggiatura;
import com.beaglebuddy.tab.model.attribute.chord.Arpeggio;
import com.beaglebuddy.tab.model.attribute.chord.ChordAttribute;
import com.beaglebuddy.tab.model.attribute.chord.FingerFretHand;
import com.beaglebuddy.tab.model.attribute.chord.FingerPickHand;
import com.beaglebuddy.tab.model.attribute.chord.PickStroke;
import com.beaglebuddy.tab.model.attribute.chord.Tap;
import com.beaglebuddy.tab.model.attribute.chord.Text;
import com.beaglebuddy.tab.model.attribute.chord.TremoloPicking;
import com.beaglebuddy.tab.model.attribute.chord.Vibrato;
import com.beaglebuddy.tab.model.attribute.note.Bend;
import com.beaglebuddy.tab.model.attribute.note.NoteAttribute;
import com.beaglebuddy.tab.model.attribute.note.Trill;
import com.beaglebuddy.tab.model.staff.Staff;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;





/**
 * called when the user selects one of the <i>tab symbol</i> icons from the <i>tab symbol</i> toolbar.
 */
public class TabSymbolAction extends BaseAction
{
   // class members
   public static final String ARPEGGIO           = "arpeggio";
   public static final String BEND               = "bend";
   public static final String FINGERING          = "fingering";
   public static final String GRACE_NOTE         = "grace_note";
   public static final String HARMONIC           = "harmonic";
   public static final String MUTED_NOTE         = "muted_note";
   public static final String PICKSTROKE         = "pickstroke";
   public static final String RAKE               = "rake";
   public static final String SLIDE              = "slide";
   public static final String SLUR               = "slur";
   public static final String TAP                = "tap";
   public static final String TEXT               = "text";
   public static final String TREMOLO_BAR        = "tremolo_bar";
   public static final String TREMOLO_PICKING    = "tremolo_picking";
   public static final String TRILL              = "trill";
   public static final String VIBRATO            = "vibrato";




   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public TabSymbolAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * display the dialog box for the tab symbol the user selected.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String   command  = event.getActionCommand();
      Object   source   = event.getSource();
      Location location = frame.getCurrentLocation();
      Staff    staff    = frame.getSong().getScore().getSections().get(location.getSection()).getStaffs().get(location.getStaff());
      Chord    chord    = staff.getChords(Staff.HighVoice).get(location.getPosition());
      Note     note     = new Note(); //   null;  // todo: add code to cursor management to determine which note is selected

           if (command.equals(ARPEGGIO))
      {
         Arpeggio arpeggio = (Arpeggio)chord.getAttribute(ChordAttribute.Type.Arpeggio);
         ArpeggioDialog dialog = new ArpeggioDialog(frame, arpeggio);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the chord
         if (dialog.wasOKed())
         {
            chord.removeAttribute(ChordAttribute.Type.Arpeggio);
            arpeggio = dialog.getArpeggio();
            if (arpeggio != null)
               chord.addAttribute(arpeggio);
            frame.setSongHasBeenEdited(true);
         }
      }
      else if (command.equals(BEND))
      {
         // show the dialog and let the user pick a new bend type for the current note
         Bend       bend   = (Bend)note.getAttribute(NoteAttribute.Type.Bend);
         BendDialog dialog = new BendDialog(frame, bend);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the note
         if (dialog.wasOKed())
         {
            bend = dialog.getBend();
            if (bend == null)
               note.removeAttribute(NoteAttribute.Type.Bend);
            else
               note.addAttribute(bend);
         }
      }
      else if (command.equals(FINGERING))
      {
         FingerFretHand fretHandFinger = (FingerFretHand)chord.getAttribute(ChordAttribute.Type.FingerFretHand);
         FingerPickHand pickHandFinger = (FingerPickHand)chord.getAttribute(ChordAttribute.Type.FingerPickHand);

         // show the dialog and let the user pick a new fret hand and\or pick hand fingering for the current note
         FingeringDialog dialog = new FingeringDialog(frame, fretHandFinger, pickHandFinger);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the note
         if (dialog.wasOKed())
         {
            fretHandFinger = dialog.getFretHandFinger();
            pickHandFinger = dialog.getPickHandFinger();

            if (fretHandFinger == null)
               chord.removeAttribute(ChordAttribute.Type.FingerFretHand);
            else
               chord.addAttribute(fretHandFinger);

            if (pickHandFinger == null)
               chord.removeAttribute(ChordAttribute.Type.FingerPickHand);
            else
               chord.addAttribute(pickHandFinger);
            frame.setSongHasBeenEdited(true);
         }
      }
      else if (command.equals(GRACE_NOTE))
      {
         if (((JToggleButton)source).isSelected())
            chord.addAttribute(new Appoggiatura());
         else
            chord.removeAttribute(ChordAttribute.Type.Appoggiatura);
         frame.setSongHasBeenEdited(true);
      }
      else if (command.equals(HARMONIC))
      {
         // determine which type of harmonic, if any, the current note has
         NoteAttribute.Type type     = null;
         NoteAttribute      harmonic = null;

              if (note.getAttribute(NoteAttribute.Type.ArtificialHarmonic) != null) type = NoteAttribute.Type.ArtificialHarmonic;
         else if (note.getAttribute(NoteAttribute.Type.NaturalHarmonic)    != null) type = NoteAttribute.Type.NaturalHarmonic;
         else if (note.getAttribute(NoteAttribute.Type.TappedHarmonic)     != null) type = NoteAttribute.Type.TappedHarmonic;
         harmonic = type == null ? null : note.getAttribute(type);

         // display the harmonic dialog box
         HarmonicDialog dialog   = new HarmonicDialog(frame, note.getFret(), harmonic);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the note
         if (dialog.wasOKed())
         {
            note.removeAttribute(type);
            harmonic = dialog.getHarmonic();
            if (harmonic != null)
               note.addAttribute(harmonic);
            frame.setSongHasBeenEdited(true);
         }
      }
      else if (command.equals(MUTED_NOTE))
      {
         super.actionPerformed(event);
      }
      else if (command.equals(PICKSTROKE))
      {
         PickStroke pickStroke = (PickStroke)chord.getAttribute(ChordAttribute.Type.PickStroke);
         PickStrokeDialog dialog = new PickStrokeDialog(frame, pickStroke);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the chord
         if (dialog.wasOKed())
         {
            chord.removeAttribute(ChordAttribute.Type.PickStroke);
            pickStroke = dialog.getPickStroke();
            if (pickStroke != null)
               chord.addAttribute(pickStroke);
            frame.setSongHasBeenEdited(true);
         }
      }
      else if (command.equals(RAKE))
      {
         super.actionPerformed(event);
      }
      else if (command.equals(SLIDE))
      {
         super.actionPerformed(event);
      }
      else if (command.equals(SLUR))
      {
         super.actionPerformed(event);
      }
      else if (command.equals(TAP))
      {
         if (((JToggleButton)source).isSelected())
            chord.addAttribute(new Tap());
         else
            chord.removeAttribute(ChordAttribute.Type.Tap);
         frame.setSongHasBeenEdited(true);
      }
      else if (command.equals(TEXT))
      {
         // show the dialog and let the user pick a new text type for the current note
         Text       text   = (Text)chord.getAttribute(ChordAttribute.Type.Text);
         TextDialog dialog = new TextDialog(frame, text);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the chord
         if (dialog.wasOKed())
         {
            text = dialog.getText();
            if (text == null || text.getText().trim().length() == 0)
               chord.removeAttribute(ChordAttribute.Type.Text);
            else
               chord.addAttribute(text);
            frame.setSongHasBeenEdited(true);
         }
      }
      else if (command.equals(TREMOLO_BAR))
      {
         super.actionPerformed(event);
      }
      else if (command.equals(TREMOLO_PICKING))
      {
         if (((JToggleButton)source).isSelected())
            chord.addAttribute(new TremoloPicking());
         else
            chord.removeAttribute(ChordAttribute.Type.TremoloPicking);
         frame.setSongHasBeenEdited(true);
      }
      else if (command.equals(TRILL))
      {
         Trill trill = (Trill)note.getAttribute(NoteAttribute.Type.Trill);
         TrillDialog dialog = new TrillDialog(frame, trill);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the note
         if (dialog.wasOKed())
         {
            note.removeAttribute(NoteAttribute.Type.Trill);
            trill = dialog.getTrill();
            if (trill != null)
               note.addAttribute(trill);
            frame.setSongHasBeenEdited(true);
         }
      }
      else if (command.equals(VIBRATO))
      {
         Vibrato vibrato = (Vibrato)chord.getAttribute(ChordAttribute.Type.Vibrato);

         // show the dialog and let the user pick a new vibrato type for the current note
         VibratoDialog dialog = new VibratoDialog(frame, vibrato);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the note
         if (dialog.wasOKed())
         {
            vibrato = dialog.getVibrato();
            if (vibrato == null)
               chord.removeAttribute(ChordAttribute.Type.Vibrato);
            else
               chord.addAttribute(vibrato);
            frame.setSongHasBeenEdited(true);
         }
      }
      else
      {
         throw new RuntimeException("Developer error: this should not occur");
      }
   }
}
