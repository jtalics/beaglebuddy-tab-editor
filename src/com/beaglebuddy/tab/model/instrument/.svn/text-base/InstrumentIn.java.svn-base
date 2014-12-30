/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.instrument;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;




/**
 * This class represents a beaglebuddy tab instrument to staff assignment and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * It indicates which instruments come in, ie, become active, on a given barline in a given staff.
 * <p>
 * InstrumentIn's behave in many ways like key signatures:
 * <ol>
 *    <li>they can only occur at the beginning of a measure</li>
 *    <li>if there was an instrument in specified in a previous measure, an instrument in at the current measure cancels the old one out.</li>
 *    <li>there can only be one instrument in for a given measure in a given staff.</li>
 * </ol>
 * See the online help for the <i>Instrument Assignment Dialog</i> for examples that illustrate these properties.
 * </p>
 * <p>
 * In order for multiple instruments to be assigned to the same staff, they must all have:
 * <ol>
 *    <li>the same number of strings as the staff they are being assigned to</li>
 *    <li>the same tuning</li>
 *    <li>the same capo</li>
 * </ol>
 * </p>
 * <p>
 * There is no corresponding "InstrumentOut".  Instruments become inactive when:
 * <ol>
 *    <li>the staff they are assigned is no longer present.  ex: a section has 3 staffs, then the next section has 2 staffs</li>
 *    <li>another "instrument in" is defined and does not include the instrument.
 * </p>
 * This class is used in conjunction with ActiveInstruments in order to determine which instruments are active at any given staff/measure.
 */
public class InstrumentIn
{
   // class members
   public final static int MAX_NUM_INSTRUMENTS = 16;

   // data members
   private byte      staff;                    // the staff within the section where the instrument comes in.
   private boolean[] staffInstruments;         // which instruments are being activated\deactivated on the staff. [0] => instrument 0, [1] => instrument 1, etc.
   private boolean[] rhythmSlashInstruments;   // which instruments are being activated\deactivated for the rhythm slash staff.




   /**
    * default constructor.
    */
   public InstrumentIn()
   {
      // all instruments are inactive
      staffInstruments       = new boolean[MAX_NUM_INSTRUMENTS];
      rhythmSlashInstruments = new boolean[MAX_NUM_INSTRUMENTS];
   }

   /**
    * constructor.
    * <br/><br/>
    * @param staff                    index of the staff within the section for which the instrument-in is activating\deactivating instruments.
    * @param staffInstruments         which instruments are being activated\deactivated on the staff. [0] => instrument 0, [1] => instrument 1, etc.
    * @param rhythmSlashInstruments   which instruments are being activated\deactivated for the rhythm slash staff.
    */
   public InstrumentIn(byte staff, boolean[] staffInstruments, boolean[] rhythmSlashInstruments)
   {
      assert(staffInstruments.length       == MAX_NUM_INSTRUMENTS);
      assert(rhythmSlashInstruments.length == MAX_NUM_INSTRUMENTS);

      this.staff                  = staff;
      this.staffInstruments       = staffInstruments;
      this.rhythmSlashInstruments = rhythmSlashInstruments;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param instrumentIn   instrument in whose values will be deep copied.
    */
   public InstrumentIn(InstrumentIn instrumentIn)
   {
      assert(instrumentIn.staffInstruments.length       == MAX_NUM_INSTRUMENTS);
      assert(instrumentIn.rhythmSlashInstruments.length == MAX_NUM_INSTRUMENTS);

      this.staff                  = instrumentIn.staff;
      this.staffInstruments       = new boolean[instrumentIn.staffInstruments      .length];
      this.rhythmSlashInstruments = new boolean[instrumentIn.rhythmSlashInstruments.length];

      for(int i=0; i<staffInstruments.length; ++i)
         this.staffInstruments[i] = instrumentIn.staffInstruments[i];
      for(int i=0; i<rhythmSlashInstruments.length; ++i)
         this.rhythmSlashInstruments[i] = instrumentIn.rhythmSlashInstruments[i];
   }


   /**
    * @return the staff within the section where the instrument in is anchored.
    */
   public byte getStaff()
   {
      return staff;
   }

   /**
    * sets the staff within the section.
    * <br/><br/>
    * @param staff   the zero-based index of the staff within the section where the instrument in is anchored.
    */
   public void setStaff(byte staff)
   {
      this.staff = staff;
   }

   /**
    * @return the status of all instruments for this staff. [0] => instrument 0, [1] => instrument 1, etc.
    */
   public boolean[] getStaffInstruments()
   {
      return staffInstruments;
   }

   /**
    * @return the list of instrument ids of active instruments for this staff. 0 => instrument 0, 1 => instrument 1, etc.
    */
   public int[] getStaffActiveInstruments()
   {
      ArrayList<Integer> activeStaffInstruments = new ArrayList<Integer>(0);
      for(int i=0; i<staffInstruments.length; ++i)
      {
         if (staffInstruments[i])
            activeStaffInstruments.add(i);
      }
      // convert the array list to an array
      int[] array = new int[activeStaffInstruments.size()];
      for(int i=0; i<array.length; ++i)
         array[i] = activeStaffInstruments.get(i).intValue();

      return array;
   }

   /**
    * @return the number of active instruments for this staff.
    */
   public int getNumStaffActiveInstruments()
   {
      int numActiveInstruments = 0;

      for(int i=0; i<staffInstruments.length; ++i)
         if (staffInstruments[i])
            numActiveInstruments++;

      return numActiveInstruments;
   }

   /**
    * @return the list of instrument ids of inactive instruments for this staff. 0 => instrument 0, 1 => instrument 1, etc.
    */
   public int[] getStaffInactiveInstruments()
   {
      ArrayList<Integer> inactiveStaffInstruments = new ArrayList<Integer>(0);
      for(int i=0; i<staffInstruments.length; ++i)
      {
         if (!staffInstruments[i])
            inactiveStaffInstruments.add(i);
      }
      // convert the array list to an array
      int[] array = new int[inactiveStaffInstruments.size()];
      for(int i=0; i<array.length; ++i)
         array[i] = inactiveStaffInstruments.get(i).intValue();

      return array;
   }

   /**
    * @return the status of all instruments for the rhythm staff. [0] => instrument 0, [1] => instrument 1, etc.
    */
   public boolean[] getRhythmSlashInstruments()
   {
      return rhythmSlashInstruments;
   }

   /**
    * @return the list of instrument ids of active instruments for the rhythm staff. 0 => instrument 0, 1 => instrument 1, etc.
    */
   public int[] getRhythmActiveInstruments()
   {
      ArrayList<Integer> activeRhythmInstruments = new ArrayList<Integer>(0);
      for(int i=0; i<rhythmSlashInstruments.length; ++i)
      {
         if (rhythmSlashInstruments[i])
            activeRhythmInstruments.add(i);
      }
      // convert the array list to an array
      int[] array = new int[activeRhythmInstruments.size()];
      for(int i=0; i<array.length; ++i)
         array[i] = activeRhythmInstruments.get(i).intValue();

      return array;
   }

   /**
    * @return the list of instrument ids of inactive instruments for the rhythm staff. 0 => instrument 0, 1 => instrument 1, etc.
    */
   public int[] getRhythmInactiveInstruments()
   {
      ArrayList<Integer> inactiveRhythmInstruments = new ArrayList<Integer>(0);
      for(int i=0; i<rhythmSlashInstruments.length; ++i)
      {
         if (!rhythmSlashInstruments[i])
            inactiveRhythmInstruments.add(i);
      }
      // convert the array list to an array
      int[] array = new int[inactiveRhythmInstruments.size()];
      for(int i=0; i<array.length; ++i)
         array[i] = inactiveRhythmInstruments.get(i).intValue();

      return array;
   }
   /**
    * @return a string representation of which instruments are active.
    * <br/><br/>
    * @param instruments  array indicating which instruments are active. [0] => instrument 0, [1] => instrument 1, etc.
    */
   public String getActiveInstruments(boolean[] instruments)
   {
      StringBuffer buffer = new StringBuffer();

      boolean hasOne = false;
      for(int i=0; i<instruments.length; ++i)
      {
         if (instruments[i])
         {
            if (hasOne)
               buffer.append(", ");
            buffer.append(i);
            hasOne = true;
         }
      }
      if (!hasOne)
         buffer.append(ResourceBundle.getString("text.none"));

      return buffer.toString();
   }

   /**
    * @return whether this instrument in object is for the specified staff.
    * <br/><br/>
    * @param staff      the index of the staff
    */
   public boolean equals(byte staff)
   {
      return this.staff == staff;
   }

   /**
    * @return whether two instrumentIns are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
  public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof InstrumentIn)
      {
         InstrumentIn instrumentIn = (InstrumentIn)object;
         equal = this.staff                         == instrumentIn.staff                   &&
                 this.staffInstruments.length       == instrumentIn.staffInstruments.length &&
                 this.rhythmSlashInstruments.length == instrumentIn.rhythmSlashInstruments.length;
         for(int i=0; i<staffInstruments.length && equal; ++i)
            equal = this.staffInstruments[i] == instrumentIn.staffInstruments[i];
         for(int i=0; i<rhythmSlashInstruments.length && equal; ++i)
            equal = this.rhythmSlashInstruments[i] == instrumentIn.rhythmSlashInstruments[i];
      }
      return equal;
   }

   /**
    * read in an instrument in from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the instrument in from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos   = file.getPosition();
         staff = (byte)file.read();
         for(int i=0; i<MAX_NUM_INSTRUMENTS; ++i)
            staffInstruments[i] = file.readBoolean();
         for(int i=0; i<MAX_NUM_INSTRUMENTS; ++i)
            rhythmSlashInstruments[i] = file.readBoolean();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.instrument.in"));
      }
   }

   /**
    * save an instrument in to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the instrument in to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(staff);
         for(int i=0; i<MAX_NUM_INSTRUMENTS; ++i)
            file.writeBoolean(staffInstruments[i]);
         for(int i=0; i<MAX_NUM_INSTRUMENTS; ++i)
            file.writeBoolean(rhythmSlashInstruments[i]);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.instrument.in"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab instrument in.
    */
   @Override
  public String toString()
   {
      return toString(21);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab instrument in.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.instrument.in") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.staff"                   ), indentation) + staff                                        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.staff_instruments"       ), indentation) + getActiveInstruments(staffInstruments)       + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.rhythm_slash_instruments"), indentation) + getActiveInstruments(rhythmSlashInstruments)       );

      return buffer.toString();
   }
}
