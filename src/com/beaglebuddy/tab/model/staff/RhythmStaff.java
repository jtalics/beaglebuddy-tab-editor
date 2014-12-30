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
import com.beaglebuddy.tab.model.RhythmSlash;
import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;





/**
 * This class represents a beaglebuddy rhythm slash staff and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class RhythmStaff
{
   // data members
   private ArrayList<RhythmSlash> rhythmSlashes;           // list of rhythm slashes



   /**
    * default constructor.
    */
   public RhythmStaff()
   {
      rhythmSlashes = new ArrayList<RhythmSlash>(0);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param rhythmSlashes   array of rhythm slashes.
    */
   public RhythmStaff(RhythmSlash[] rhythmSlashes)
   {
      setRhythmSlashes(rhythmSlashes);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param rhythmStaff  rhythm staff whose values will be deep copied.
    */
   public RhythmStaff(RhythmStaff rhythmStaff)
   {
      rhythmSlashes = new ArrayList<RhythmSlash>(0);

      if (rhythmStaff != null)
      {
         for(RhythmSlash rhythmSlash : rhythmStaff.rhythmSlashes)
            this.rhythmSlashes.add(new RhythmSlash(rhythmSlash));
      }
   }


   /**
    * @return the clef for the standard music notation staff.
    */
   public Staff.Clef getClef()
   {
      return Staff.Clef.None;
   }

   /**
    * @return the list of rhythm slashes in the staff.
    */
   public ArrayList<RhythmSlash> getRhythmSlashes()
   {
      return rhythmSlashes;
   }

   /**
    * sets the rhythm slashes in the staff.
    * <br/><br/>
    * @param rhythmSlashes   the rhythm slashes in the staff.
    */
   public void setRhythmSlashes(RhythmSlash[] rhythmSlashes)
   {
      this.rhythmSlashes = new ArrayList<RhythmSlash>(0);

      if (rhythmSlashes != null)
      {
         for(RhythmSlash rhythmSlash : rhythmSlashes)
            this.rhythmSlashes.add(rhythmSlash);
      }
   }

   /**
    * sets the rhythm slashes in the staff.
    * <br/><br/>
    * @param rhythmSlashes   the rhythm slashes in the staff.
    */
   private void setRhythmSlashes(Object[] rhythmSlashes)
   {
      this.rhythmSlashes = new ArrayList<RhythmSlash>(0);

      if (rhythmSlashes != null)
      {
         for(Object rhythmSlash : rhythmSlashes)
            this.rhythmSlashes.add((RhythmSlash)rhythmSlash);
      }
   }

   /**
    * @return whether two rhythm staffs are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
  public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof RhythmStaff)
      {
         RhythmStaff rhythmStaff = (RhythmStaff)object;

         if (this.rhythmSlashes.size() == rhythmStaff.rhythmSlashes.size())
         {
            for(int i=0; i<rhythmSlashes.size() && equal; ++i)
               equal = this.rhythmSlashes.get(i).equals(rhythmStaff.rhythmSlashes.get(i));
         }
      }
      return equal;
   }

   /**
    * @return whether the tab staff should be displayed.
    */
   public boolean isTabStaffDisplayed()
   {
      return false;
   }

   /**
    * read in a rhythm staff from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the rhythm staff from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         setRhythmSlashes(file.readArray(RhythmSlash.class));
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.staff.rhythm_staff"));
      }
   }

   /**
    * save a rhythm staff to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the rhythm staff to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeArray(rhythmSlashes.toArray());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.staff.rhythm_staff"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab staff.
    */
   @Override
  public String toString()
   {
      return toString(12);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy rhythm staff.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(ResourceBundle.getString("data_type.staff.rhythm_staff") + "\n");
                                                  buffer.append(Utility.pad(ResourceBundle.getString("text.rhythm_slashes"                 ), indentation1) + rhythmSlashes.size()     + "\n");
       for(int i=0; i<rhythmSlashes.size(); ++i)  buffer.append(Utility.pad(ResourceBundle.getString("text.rhythm_slashes") + "[" + i + "]" , indentation2) + rhythmSlashes.get(i)     + (i == rhythmSlashes.size() - 1 ? "" : "\n"));

      return buffer.toString();
   }
}
