/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.staff;

import com.beaglebuddy.tab.model.Chord;






/**
 * This class represents a beaglebuddy treble staff and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * a treble staff displays its tab staff and has a treble clef in its music staff.
 * </p>
 */
public class TrebleStaff extends Staff
{
   /**
    * default constructor.
    */
   public TrebleStaff()
   {
      setClef(Clef.Treble);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param numTabLines                         number of lines in tab staff.
    * @param standardNotationStaffAboveSpacing   amount of space (in pixels) alloted from the top line of the standard notation staff.
    * @param standardNotationStaffBelowSpacing   amount of space (in pixels) alloted from the last line of the standard notation staff.
    * @param symbolSpacing                       amount of space (in pixels) alloted for symbols located between the standard notation and tab staff.
    * @param tabStaffBelowSpacing                amount of space (in pixels) alloted from the last line of the tab staff.
    * @param lowVoiceChords                      chords for the low  voice melody.
    * @param highVoiceChords                     chords for the high voice melody.
    */
   public TrebleStaff(byte numTabLines, short standardNotationStaffAboveSpacing, short standardNotationStaffBelowSpacing, short symbolSpacing, short tabStaffBelowSpacing, Chord[] lowVoiceChords, Chord[] highVoiceChords)
   {
      super(Clef.Treble, numTabLines, standardNotationStaffAboveSpacing, standardNotationStaffBelowSpacing, symbolSpacing, tabStaffBelowSpacing, lowVoiceChords, highVoiceChords);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param trebleStaff   treble staff whose values will be deep copied.
    */
   public TrebleStaff(TrebleStaff trebleStaff)
   {
      super(trebleStaff);
   }
}
