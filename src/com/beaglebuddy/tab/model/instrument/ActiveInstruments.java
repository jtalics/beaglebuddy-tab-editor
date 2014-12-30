/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.instrument;

import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;




/**
 * This class holds information which indicates which instruments are currently active on which staffs.
 * <p>
 * Since each instrument can appear on its own staff, we need to have a matrix with a row dimension equal to the number of instruments in the song.
 * Since we could also have a single staff with all instruments playing the same musical line, we need to have a matrix with a column dimension also
 * equal to the number of instruments in the song.  In order to support these possibilities as well as all other combinations of numbers of staffs
 * versus number of instruments active on a staff, we use a square matrix whose row and column dimensions are that of the number of instruments
 * in the song.
 * </p>
 * <p>
 * The rows of the matrix are indexed by the staff.
 * Thus, the entries in row 5 (activeInstruments[5][]) contain which instruments are active on staff 5.
 * Similarly, columns of the matrix are indexed by instrument.
 * Thus, the entries in column 8 (activeInstruments[][8]) contain which staff instrument 8 is active on.
 * Since an instrument can only be active on a single staff, there will never be more than one entry in a given column.
 * This is quite different from a row in the matrix, which can have multiple entries since many instruments can play the same music on a given staff.
 * </p>
 * <p>
 * Putting it all together, consider the following example:
 *
 * activeInstruments[2][6] - whether instrument 6 is active on the 2nd staff.
 * activeInstruments[3][6] - whether instrument 6 is active on the 3rd staff.
 *
 * either one of the above values could be true, but not both.
 * this is because a given instrument (instrument 6 in this case) can only be active on a single staff at a time.
 * it can not be active on both staff 2 and staff 3.
 * </p>
 * <p>
 * shown below is a typical usage:
 *
 *
 *                 |         | rhythm  | rhythm  | lead    | bass    |         |         |
 *                 | vocals  | guitar 1| guitar 2| guitar  | guitar  |keyboard |  drums  |
 *                 ----------------------------------------------------------------------|
 *       rhythm    |         |         |         |         |         |         |         |
 *                 ----------------------------------------------------------------------|
 *       staff 0   |    x    |         |         |         |         |         |         |
 *                 ----------------------------------------------------------------------|
 *       staff 1   |         |    x    |    x    |         |         |         |         |
 *                 ----------------------------------------------------------------------|
 *       staff 2   |         |         |         |         |         |         |         |
 *                 ----------------------------------------------------------------------|
 *       staff 3   |         |         |         |    x    |         |         |         |
 *                 ----------------------------------------------------------------------|
 *       staff 4   |         |         |         |         |    x    |         |         |
 *                 ----------------------------------------------------------------------|
 *       staff 5   |         |         |         |         |         |    x    |         |
 *                 ----------------------------------------------------------------------|
 *       staff 6   |         |         |         |         |         |         |    x    |
 *                 ----------------------------------------------------------------------|
 * </p>
 */
public class ActiveInstruments
{
   // data members
   private boolean  [] rhythmInstruments;    // instruments active on the rhythm slash staff    - 0 => instrument 0, 1 => instrument 1, etc.
   private boolean[][] staffInstruments;     // instruments active on one of the musical staffs - 0 => instrument 0, 1 => instrument 1, etc.



   /**
    * default constructor.
    */
   public ActiveInstruments()
   {
      staffInstruments  = new boolean [0][0];
      rhythmInstruments = new boolean    [0];
   }

   /**
    * constructor.
    * <br/><br/>
    * @param instruments   list of instruments contained in the song.
    */
   public ActiveInstruments(ArrayList<Instrument> instruments)
   {
      if (instruments == null)
      {
         staffInstruments  = new boolean [0][0];
         rhythmInstruments = new boolean    [0];
      }
      else
      {
         staffInstruments  = new boolean [instruments.size()][instruments.size()];
         rhythmInstruments = new boolean                     [instruments.size()];
      }
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param activeInstruments   contains information about which instruments are active on which staffs.
    */
   public ActiveInstruments(ActiveInstruments activeInstruments)
   {
      if (activeInstruments == null)
      {
         staffInstruments  = new boolean [0][0];
         rhythmInstruments = new boolean    [0];
      }
      else
      {
         staffInstruments  = new boolean [activeInstruments.staffInstruments.length][activeInstruments.staffInstruments.length];
         rhythmInstruments = new boolean                                            [activeInstruments.staffInstruments.length];

         for(int i=0; i<staffInstruments.length; ++i)
            for(int j=0; j<staffInstruments.length; ++j)
               staffInstruments[i][j] = activeInstruments.staffInstruments[i][j];

         for(int i=0; i<rhythmInstruments.length; ++i)
            rhythmInstruments[i] = activeInstruments.rhythmInstruments[i];
      }
   }

   /**
    * @return the number of instruments for which staff activation information is held.
    */
   public int getNumInstruments()
   {
      return staffInstruments.length;
   }

   /**
    * @return the number of staffs for which staff activation information is held.
    */
   public int getNumStaffs()
   {
      return staffInstruments.length;
   }

   /**
    * @return the activity state for all instruments regarding the specified staff.
    * <br/><br/>
    * @param staff   the staff for which the instrument status's are to be retrieved.
    */
   public boolean[] getInstruments(byte staff)
   {
      if (staff > staffInstruments.length)
         throw new IllegalArgumentException("todo");

      return staffInstruments[staff];
   }

   /**
    * @return the staff to which the specified instrument is assigned, or -1 if it is not assigned.
    * <br/><br/>
    * @param instrument   the number of the instrument whose active status is desired.
    */
   public byte getAssignedStaff(byte instrument)
   {
      if (instrument > staffInstruments.length)
         throw new IllegalArgumentException("todo: create an error msg in the resource bundle");

      byte staff;
      for(staff=0; staff <staffInstruments.length && !staffInstruments[staff][instrument]; ++staff){}

      return staff == staffInstruments.length ? -1 : staff;
   }


   /**
    * activate\deactivate the specified instrument(s).
    * <br/><br>
    * @param instrumentIns   a list of instruments which are to be activated\deactivated.
    */
   public void set(ArrayList<InstrumentIn> instrumentIns)
   {
      if (instrumentIns != null)
      {
         for(InstrumentIn instrumentIn : instrumentIns)
         {
            // turn off the inactive instruments on this staff
            int[] inactiveStaffInstruments = instrumentIn.getStaffInactiveInstruments();
            for(int i=0; i < staffInstruments.length && i < inactiveStaffInstruments.length && inactiveStaffInstruments[i] < staffInstruments.length; ++i)
               staffInstruments[instrumentIn.getStaff()][inactiveStaffInstruments[i]] = false;

            // turn off the inactive instruments on the rhythm staff
            int[] inactiveRhythmInstruments = instrumentIn.getRhythmInactiveInstruments();
            for(int i=0; i < rhythmInstruments.length && i < inactiveRhythmInstruments.length && inactiveRhythmInstruments[i] < rhythmInstruments.length; ++i)
               rhythmInstruments[inactiveRhythmInstruments[i]] = false;

            // turn on the active instruments on this staff
            int[] activeStaffInstruments = instrumentIn.getStaffActiveInstruments();
            for(int i=0; i < staffInstruments.length && i < activeStaffInstruments.length && activeStaffInstruments[i] < staffInstruments.length; ++i)
            {
               // if the instrument was previously active on another staff, turn it off there
               reset(activeStaffInstruments[i]);

               // and turn it on here for this staff
               staffInstruments[instrumentIn.getStaff()][activeStaffInstruments[i]] = true;
            }

            // turn on the active instruments on the rhythm staff
            int[] activeRhythmInstruments = instrumentIn.getRhythmActiveInstruments();
            for(int i=0; i < rhythmInstruments.length && i < activeRhythmInstruments.length && activeRhythmInstruments[i] < rhythmInstruments.length; ++i)
               rhythmInstruments[activeRhythmInstruments[i]] = true;
         }
      }
   }

   /**
    * turn off the specified instrument for the rhythm staff and all music staffs.
    * <br/><br/>
    * @param instrument   the index of the instrument to turn off
    */
   private void reset(int instrument)
   {
      rhythmInstruments[instrument] = false;

      for(int staff=0; staff<staffInstruments.length; ++staff)
         staffInstruments[staff][instrument] = false;
   }

   /**
    * @return a string representation of the active instruments.
    */
   @Override
  public String toString()
   {
      return toString(21);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the active instruments.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("text.active_instruments") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.active_instruments.rhythm_staff"), indentation));
      for(int i=0; i<rhythmInstruments.length; ++i)
         buffer.append((rhythmInstruments[i] ? "x" : ".") + " ");
      buffer.append("\n");

      for(int i=0; i<staffInstruments.length; ++i)
      {
         buffer.append(Utility.pad(ResourceBundle.format("text.active_instruments.music_staff", i), indentation));
         for(int j=0; j<staffInstruments[i].length; ++j)
            buffer.append((staffInstruments[i][j] ? "x" : ".") + " ");
         buffer.append("\n");
      }

      return buffer.toString();
   }
}
