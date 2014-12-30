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
 * This class represents a beaglebuddy drum staff and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * a drum staff always has a 2 line tab staff and has a music staff with a drum clef.
 * </p>
 */
public class DrumStaff extends Staff
{
   /**
    * default constructor.
    */
   public DrumStaff()
   {
      setClef                (Clef.Drum);
      setNumTabLines         ((byte )3);
      setTabStaffBelowSpacing((short)0);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param standardNotationStaffAboveSpacing   amount of space (in pixels) alloted from the top line of the standard notation staff.
    * @param standardNotationStaffBelowSpacing   amount of space (in pixels) alloted from the last line of the standard notation staff.
    * @param symbolSpacing                       amount of space (in pixels) alloted for symbols located between the standard notation and tab staff.
    * @param lowVoiceChords                      chords for the low  voice melody.
    * @param highVoiceChords                     chords for the high voice melody.
    */
   public DrumStaff(short standardNotationStaffAboveSpacing, short standardNotationStaffBelowSpacing, short symbolSpacing, Chord[] lowVoiceChords, Chord[] highVoiceChords)
   {
      super(Clef.Drum, (byte)3, standardNotationStaffAboveSpacing, standardNotationStaffBelowSpacing, symbolSpacing, (short)0, lowVoiceChords, highVoiceChords);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param drumStaff   drum staff whose values will be deep copied.
    */
   public DrumStaff(DrumStaff drumStaff)
   {
      super(drumStaff);
   }
}
