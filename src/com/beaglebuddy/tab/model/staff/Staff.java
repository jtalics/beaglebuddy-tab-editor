/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.staff;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.Chord;
import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a beaglebuddy tab staff and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * The Beaglebuddy Tab Editor supports 5 kinds of staffs:
 * <table border="1" cellspacing="10">
 *    <tr><th>&nbsp;</th><th>staff type     </th><th>description                                                                    </th></tr>
 *    <tr><td>1.    </td><td>bass staff     </td><td>tab staff and music staff with a bass clef                                     </td></tr>
 *    <tr><td>2.    </td><td>drum staff     </td><td>tab staff and music staff with a drum clef                                     </td></tr>
 *    <tr><td>3.    </td><td>keyboard staff </td><td>tab staff, a music staff with a treble clef, and a music staff with a bass clef</td></tr>
 *    <tr><td>4.    </td><td>rhythm staff   </td><td>rhythm staff containing rhythm slashes and no clef                             </td></tr>
 *    <tr><td>5.    </td><td>treble staff   </td><td>tab staff and music staff with a treble clef                                   </td></tr>
 * </table>
 * </p>
 * <p>
 * Currently, all of the staffs, with the exception of the rhythm staff, are derived from the <i>Staff</i> class.
 * Although the rhythm staff can be refactored to be a subclass of the <i>Staff</i> class, it will take a lot of rework.
 * So, at this point, it seems best to leave the rhythm staff as it is for now, and save the decision to refactor it for
 * later, since it is a high risk task.
 * </p>
 * <p>
 * All of the staff types store their data in the tab staff, with the corresponding musical staff(s) are drawn from this tab staff data.
 * </p>
 * <p>
 * Each staff has a high and low voice melody line, each of which contains chords (single note, chord, rest, multi bar rest).
 * </p>
 * The following data members are used to set the spacing between the staffs:
 * <table>
 *    <tr><th>spacing                      </th><th>description                                                           </th></tr>
 *    <tr><td>above standard notation staff</td><td>area where high notes are drawn                                       </td></tr>
 *    <tr><td>below standard notation staff</td><td>area where low  notes are drawn                                       </td></tr>
 *    <tr><td>symbol                       </td><td>area where instrument in, palm muting, trill, vibrato, etc. are drawn </td></tr>
 *    <tr><td>below tab staff              </td><td>area where tab, pick stroke up\down, slide, etc. are drawn            </td></tr>
 * </table>
 */
public class Staff extends StaffBase
{
   // enums
   public enum Clef {None, Bass, Drum, Treble}

   // data members
   private Clef  clef;                                 // type of music clef
   private byte  numTabLines;                          // number of lines in tab staff
   private short standardNotationStaffAboveSpacing;    // amount of space (in pixels) alloted from the top line of the standard notation staff
   private short standardNotationStaffBelowSpacing;    // amount of space (in pixels) alloted from the last line of the standard notation staff
   private short symbolSpacing;                        // amount of space (in pixels) alloted for symbols located between the standard notation and tablature staff
   private short tabStaffBelowSpacing;                 // amount of space (in pixels) alloted from the last line of the tablature staff




   /**
    * default constructor.
    */
   public Staff()
   {
      // no code necessary
   }

   /**
    * constructor.
    * <br/><br/>
    * @param clef                                type of music clef.
    * @param numTabLines                         number of lines in tab staff.
    * @param standardNotationStaffAboveSpacing   amount of space (in pixels) alloted from the top line of the standard notation staff.
    * @param standardNotationStaffBelowSpacing   amount of space (in pixels) alloted from the last line of the standard notation staff.
    * @param symbolSpacing                       amount of space (in pixels) alloted for symbols located between the standard notation and tab staff.
    * @param tabStaffBelowSpacing                amount of space (in pixels) alloted from the last line of the tab staff.
    * @param lowVoiceChords                      chords for the low  voice melody.
    * @param highVoiceChords                     chords for the high voice melody.
    */
   public Staff(Clef clef, byte numTabLines, short standardNotationStaffAboveSpacing, short standardNotationStaffBelowSpacing, short symbolSpacing, short tabStaffBelowSpacing, Chord[] lowVoiceChords, Chord[] highVoiceChords)
   {
      super(lowVoiceChords, highVoiceChords);

      this.clef                              = clef;
      this.standardNotationStaffAboveSpacing = standardNotationStaffAboveSpacing;
      this.standardNotationStaffBelowSpacing = standardNotationStaffBelowSpacing;
      this.symbolSpacing                     = symbolSpacing;
      this.tabStaffBelowSpacing              = tabStaffBelowSpacing;

      setNumTabLines(numTabLines);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param staff   staff whose values will be deep copied.
    */
   public Staff(Staff staff)
   {
      super(staff);

      if (staff != null)
      {
         this.clef                              = staff.clef;
         this.standardNotationStaffAboveSpacing = staff.standardNotationStaffAboveSpacing;
         this.standardNotationStaffBelowSpacing = staff.standardNotationStaffBelowSpacing;
         this.symbolSpacing                     = staff.symbolSpacing;
         this.tabStaffBelowSpacing              = staff.tabStaffBelowSpacing;

         setNumTabLines(numTabLines);
      }
   }

   /**
    * @return the clef for the standard music notation staff.
    */
   public Clef getClef()
   {
      return clef;
   }

   /**
    * @param clef  the integer clef.
    * <br/><br/>
    * @return the clef enum corresponding to the integer clef.
    */
   public static Clef getClef(int clef)
   {
      for (Clef c : Clef.values())
         if (clef == c.ordinal())
            return c;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.clef"), clef, Clef.Drum.ordinal()));
   }

   /**
    * sets the clef for the standard music notation staff.
    * <br/><br/>
    * @param clef   the clef for the standard music notation staff.
    */
   public void setClef(Clef clef)
   {
      this.clef = clef;
   }

   /**
    * @return the number of lines in the tab staff.
    */
   public byte getNumTabLines()
   {
      return numTabLines;
   }

   /**
    * sets the number of lines in the tab staff.
    * <br/><br/>
    * @param numTabLines   the number of lines in the tab staff.
    */
   public void setNumTabLines(byte numTabLines)
   {
      if (numTabLines < Instrument.MIN_NUM_STRINGS || numTabLines > Instrument.MAX_NUM_STRINGS)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("text.num_tab_lines"), numTabLines, Instrument.MIN_NUM_STRINGS, Instrument.MAX_NUM_STRINGS));
      this.numTabLines = numTabLines;
   }

   /**
    * @return the amount of space (in pixels) alloted from the top line of the standard notation staff.
    */
   public short getStandardNotationStaffAboveSpacing()
   {
      return standardNotationStaffAboveSpacing;
   }

   /**
    * sets the amount of space (in pixels) alloted from the top line of the standard notation staff.
    * <br/><br/>
    * @param spacing   the amount of space (in pixels) alloted from the top line of the standard notation staff.
    */
   public void setStandardNotationStaffAboveSpacing(short spacing)
   {
      this.standardNotationStaffAboveSpacing = spacing;
   }

   /**
    * @return the amount of space (in pixels) alloted from the last line of the standard notation staff,
    */
   public short getStandardNotationStaffBelowSpacing()
   {
      return standardNotationStaffBelowSpacing;
   }

   /**
    * sets the amount of space (in pixels) alloted from the last line of the standard notation staff.
    * <br/><br/>
    * @param spacing   the amount of space (in pixels) alloted from the last line of the standard notation staff.
    */
   public void setStandardNotationStaffBelowSpacing(short spacing)
   {
      this.standardNotationStaffBelowSpacing = spacing;
   }

   /**
    * @return the amount of space (in pixels) alloted for symbols located between the standard notation and tablature staff.
    */
   public short getSymbolSpacing()
   {
      return symbolSpacing;
   }

   /**
    * sets the amount of space (in pixels) alloted for symbols located between the standard notation and tablature staff.
    * <br/><br/>
    * @param spacing   the amount of space (in pixels) alloted for symbols located between the standard notation and tablature staff.
    */
   public void setSymbolSpacing(short spacing)
   {
      this.symbolSpacing = spacing;
   }

   /**
    * @return the amount of space (in pixels) alloted from the last line of the tablature staff.
    */
   public short getTabStaffBelowSpacing()
   {
      return tabStaffBelowSpacing;
   }

   /**
    * sets the amount of space (in pixels) alloted from the last line of the tablature staff.
    * <br/><br/>
    * @param spacing   the amount of space (in pixels) alloted from the last line of the tablature staff.
    */
   public void setTabStaffBelowSpacing(short spacing)
   {
      this.tabStaffBelowSpacing = spacing;
   }

   /**
    * @return the number of corresponding music staffs that are displayed.
    */
   public int getNumMusicStaffsDisplayed()
   {
      return 1;
   }

   /**
    * @return whether two staffs are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
  public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Staff)
      {
         Staff staff = (Staff)object;
         equal = super.equals(staff) && this.clef == staff.clef && this.numTabLines == staff.numTabLines &&
                 this.standardNotationStaffAboveSpacing == staff.standardNotationStaffAboveSpacing &&
                 this.standardNotationStaffBelowSpacing == staff.standardNotationStaffBelowSpacing &&
                 this.symbolSpacing == staff.symbolSpacing && this.tabStaffBelowSpacing == staff.tabStaffBelowSpacing;
      }
      return equal;
   }

   /**
    * read in a staff from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the staff from the beaglebuddy tab file.
    */
   @Override
  public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos  = file.getPosition();
         clef = getClef(file.read());
         setNumTabLines((byte)file.read());

         standardNotationStaffAboveSpacing = file.readShort();
         standardNotationStaffBelowSpacing = file.readShort();
         symbolSpacing                     = file.readShort();
         tabStaffBelowSpacing              = file.readShort();
         super.load(file);
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.staff"));
      }
   }

   /**
    * save a staff to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the staff to the beaglebuddy tab file.
    */
   @Override
  public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write     (clef.ordinal()                   );
         file.write     (numTabLines                      );
         file.writeShort(standardNotationStaffAboveSpacing);
         file.writeShort(standardNotationStaffBelowSpacing);
         file.writeShort(symbolSpacing                    );
         file.writeShort(tabStaffBelowSpacing             );
         super.save(file);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.staff"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab staff.
    */
   @Override
  public String toString()
   {
      return toString(15);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab staff.
    */
   @Override
  public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.staff") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.clef"                                 ), indentation) + clef                              + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.number_of_lines_in_tab_staff"         ), indentation) + numTabLines                       + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.spacing_above_standard_notation_staff"), indentation) + standardNotationStaffAboveSpacing + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.spacing_below_standard_notation_staff"), indentation) + standardNotationStaffBelowSpacing + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.symbol_spacing"                       ), indentation) + symbolSpacing                     + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.spacing_below_tab_staff"              ), indentation) + tabStaffBelowSpacing              + "\n");
      buffer.append(super.toString(numSpacesToIndent));

      return buffer.toString();
   }
}
