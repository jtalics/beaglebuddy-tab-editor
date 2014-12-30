/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.diagram;

import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.ChordDictionary;
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.instrument.Instrument;
import javax.swing.DefaultListModel;
import java.util.ArrayList;




/**
 * list model for selecting a chord name for a given chord diagram.
 * todo - take the instrument as a constructor arg
 */
public class ListModelChordNames extends DefaultListModel
{
   /**
    * default constructor.
    */
   public ListModelChordNames()
   {
      this(null);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param chordDiagram   the chord diagram for which a list of possible chord names are to be presented.
    */
   public ListModelChordNames(ChordDiagram chordDiagram)
   {
      this(chordDiagram, TuningDictionary.getTuning(Instrument.Type.Guitar, chordDiagram == null ? 6 : chordDiagram.getNumStrings(), TuningDictionary.TUNING_STANDARD), Instrument.Fret.Not_Used, true);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param chordDiagram   the chord diagram for which a list of possible chord names are to be presented.
    * @param tuning         the tuning used for the instrument on which the chord diagram is based.
    * @param capo           the capo, if any, used by the instrument.
    * @param usesSharps     whether to use sharps or flats in resolving two notes that have the same pitch (ex: F# and Gb)
    */
   public ListModelChordNames(ChordDiagram chordDiagram, Tuning tuning, Instrument.Fret capo, boolean usesSharps)
   {
      if (chordDiagram != null)
         setChordNames(chordDiagram, tuning, capo, usesSharps);
   }

   /**
    * sets the list of chord names that the user will be allowed to choose from based on the chord diagram.
    * <br/><br/>
    * @param chordDiagram   the chord diagram for which a list of possible chord names are to be presented.
    * @param tuning         the tuning used for the instrument on which the chord diagram is based.
    * @param capo           the capo, if any, used by the instrument.
    * @param usesSharps     whether to use sharps or flats in resolving two notes that have the same pitch (ex: F# and Gb)
    */
   private void setChordNames(ChordDiagram chordDiagram, Tuning tuning, Instrument.Fret capo, boolean usesSharps)
   {
      ArrayList<String> chordNames = chordDiagram.isBlank() ? new ArrayList<String>() : ChordDictionary.getChordNames(chordDiagram, tuning, capo, usesSharps);
      chordNames.add("Gsus4");   // todo: remove when done debugging - just for test purposes

      removeAllElements();
      for(String chordName : chordNames)
         addElement(chordName);
   }
}
