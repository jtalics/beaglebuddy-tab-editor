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
 * This class represents a beaglebuddy keyboard staff and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * A keyboard staff displays both a treble music staff and a bass music staff.
 * The high voice melody line is drawn on the treble music staff, while the low voice melody line is drawn on the bass music staff.
 * The corresponding tab staff is also drawn.
 * </p>
 */
public class KeyboardStaff extends Staff
{
   /**
    * default constructor.
    */
   public KeyboardStaff()
   {
      //  no code necessary
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
   public KeyboardStaff(byte numTabLines, short standardNotationStaffAboveSpacing, short standardNotationStaffBelowSpacing, short symbolSpacing, short tabStaffBelowSpacing, Chord[] lowVoiceChords, Chord[] highVoiceChords)
   {
      super(Clef.Treble, numTabLines, standardNotationStaffAboveSpacing, standardNotationStaffBelowSpacing, symbolSpacing, tabStaffBelowSpacing, lowVoiceChords, highVoiceChords);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param keyboardStaff   keyboard staff whose values will be deep copied.
    */
   public KeyboardStaff(KeyboardStaff keyboardStaff)
   {
      super(keyboardStaff);
   }

   /**
    * @return the number of corresponding music staffs that are displayed.
    */
   @Override
  public int getNumMusicStaffsDisplayed()
   {
      return 2;
   }
}
