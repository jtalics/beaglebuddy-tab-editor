/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.attribute.duration.MultibarRest;
import com.beaglebuddy.tab.model.staff.RhythmStaff;
import com.beaglebuddy.tab.model.staff.Staff;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.Hashtable;




/**
 * This class represents a beaglebuddy tab section and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * A section may have:
 * <ul>
 *    <li>at most one rhythm staff, which, if present, is the first staff.</li>
 *    <li>multiple tab staffs</li>
 * </ul>
 * </p>
 */
public class SectionBase
{
   // data members
   protected RhythmStaff        rhythmStaff;          // rhythm staff
   protected ArrayList<Staff>   staffs;               // list of staves used within the section
   protected ArrayList<Barline> barlines;             // list of barlines used within the section



   /**
    * default constructor.
    */
   public SectionBase()
   {
      rhythmStaff = null;
      setStaffs   (null);
      setBarlines (null);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param rhythmStaff   rhythm staff.
    * @param staffs        list of staves comprising the section.
    * @param barlines      list of barlines within the section.
    */
   public SectionBase(RhythmStaff rhythmStaff, Staff[] staffs, Barline[] barlines)
   {
      this();

      this.rhythmStaff = rhythmStaff;
      setStaffs  (staffs  );
      setBarlines(barlines);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param sectionBase   section base whose values will be deep copied.
    */
   public SectionBase(SectionBase sectionBase)
   {
      this();

      if (sectionBase != null)
      {
         if (sectionBase.rhythmStaff != null)
            this.rhythmStaff = new RhythmStaff(sectionBase.rhythmStaff);

         if (sectionBase.staffs != null)
         {
            this.staffs = new ArrayList<Staff>(sectionBase.staffs.size());
            for(Staff staff : sectionBase.staffs)
               this.staffs.add(new Staff(staff));
         }

         if (sectionBase.barlines != null)
         {
            this.barlines = new ArrayList<Barline>(sectionBase.barlines.size());
            for(Barline barline : sectionBase.barlines)
               this.barlines.add(new Barline(barline));
         }
      }
   }

   /**
    * @return the rhythm staff in the section.
    */
   public RhythmStaff getRhythmStaff()
   {
      return rhythmStaff;
   }

   /**
    * <br/><br/>
    * @param rhythmStaff   the rhythm staff in the section.
    */
   public void setRhythmStaff(RhythmStaff rhythmStaff)
   {
      this.rhythmStaff = (rhythmStaff == null ? new RhythmStaff() : rhythmStaff);
   }

   /**
    * @return the list of staves in the section.
    */
   public ArrayList<Staff> getStaffs()
   {
      return staffs;
   }

   /**
    * sets the staffs in the section.
    * <br/><br/>
    * @param staffs   the staffs in the section.
    */
   public void setStaffs(Staff[] staffs)
   {
      this.staffs = new ArrayList<Staff>(0);

      if (staffs != null)
      {
         for(Staff staff : staffs)
            this.staffs.add(staff);
      }
   }

   /**
    * sets the staffs in the section.
    * <br/><br/>
    * @param staffs   the staffs in the section.
    */
   private void setStaffs(Object[] staffs)
   {
      this.staffs = new ArrayList<Staff>(0);

      if (staffs != null)
      {
         for(Object staff : staffs)
            this.staffs.add((Staff)staff);
      }
   }

   /**
    * @return the list of barlines (not including start and end bars) in the section.
    */
   public ArrayList<Barline> getBarlines()
   {
      return barlines;
   }

   /**
    * sets the barlines in the section.
    * <br/><br/>
    * @param barlines   the barlines in the section.
    */
   public void setBarlines(Barline[] barlines)
   {
      this.barlines = new ArrayList<Barline>(2);

      if (barlines == null)
      {
         // add a beginning bar line
         this.barlines.add(new Barline());

         // find the position of the last chord in any staff within the section
         byte maxPosition = 0;
         for(Staff staff : staffs)
         {
            for(int voice=0; voice<Staff.NumVoices; ++voice)
            {
               ArrayList<Chord> chords = staff.getChords(voice);
               if (chords.size() != 0 && chords.get(chords.size()-1).getPosition() > maxPosition)
                  maxPosition = chords.get(chords.size()-1).getPosition();
            }
         }
         if (rhythmStaff != null && rhythmStaff.getRhythmSlashes().size() != 0 && rhythmStaff.getRhythmSlashes().get(rhythmStaff.getRhythmSlashes().size()-1).getPosition() > maxPosition)
//       if (rhythmStaff.getRhythmSlashes() != null && rhythmStaff.getRhythmSlashes().size() != 0 && rhythmStaff.getRhythmSlashes().get(rhythmStaff.getRhythmSlashes().size()-1).getPosition() > maxPosition)
            maxPosition = rhythmStaff.getRhythmSlashes().get(rhythmStaff.getRhythmSlashes().size()-1).getPosition();

         if (maxPosition == 0)
            maxPosition = 35;     // default number of positions in an empty section

         // add an ending bar line
         this.barlines.add(new Barline(maxPosition));
      }
      else
      {
         for(Barline barline : barlines)
            this.barlines.add(barline);
      }
   }

   /**
    * sets the barlines in the section.
    * <br/><br/>
    * @param barlines   the barlines in the section.
    */
   private void setBarlines(Object[] barlines)
   {
      this.barlines = new ArrayList<Barline>(2);

      if (barlines != null)
      {
         for(Object barline : barlines)
            this.barlines.add((Barline)barline);
      }
   }

   /**
    * @return the barline at the end of the section (time and key signature are not used in this barline).
    */
   public Barline getEndBarline()
   {
      return barlines.get(barlines.size() - 1);
   }

   /**
    * @return the barline at the start of the section.
    */
   public Barline getStartBarline()
   {
      return barlines.get(0);
   }

   /**
    * @return the number of measures in the section.
    */
   public int getNumMeasures()
   {
      // the number of measures will equal the number of barlines minus one unless the section consists of only one staff and a multibar rest
      // chord attribute is found within the staff's measures.
      int numMeasures = barlines.size() - 1;

      // search for a multibar rest chord attribute
      if (staffs.size() == 1)
      {
         ArrayList<Chord> chords = staffs.get(0).getChords(Staff.HighVoice);
         for(Chord chord : chords)
         {
            if (chord.isMultiBarRest())
            {
               MultibarRest multibarRest = (MultibarRest)chord.getDurationAttributes().get(0);
               numMeasures += multibarRest.getNumMeasures() - 1;
            }
         }
      }
      return numMeasures;
   }

   /**
    * @return whether two sections are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Section)
      {
         Section section = (Section)object;

         equal = (this.rhythmStaff == null && section.rhythmStaff == null) ||
                 (this.rhythmStaff != null && section.rhythmStaff != null && this.rhythmStaff.equals(section.rhythmStaff));

         if (equal)
         {
            equal = (this.staffs == null && section.staffs == null) ||
                    (this.staffs != null && section.staffs != null && this.staffs.size() == section.staffs.size());
            if (section.staffs != null)
            {
               for(int i=0; i<staffs.size() && equal; ++i)
                  equal = this.staffs.get(i).equals(section.staffs.get(i));
            }
         }

         if (equal)
         {
            equal = (this.barlines == null && section.barlines == null) ||
                    (this.barlines != null && section.barlines != null && this.barlines.size() == section.barlines.size());
            if (section.barlines != null)
            {
               for(int i=0; i<barlines.size() && equal; ++i)
                  equal = this.barlines.get(i).equals(section.barlines.get(i));
            }
         }
      }
      return equal;
   }

   /**
    * read in a section object from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the section object from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         boolean hasRhythmStaff = file.readBoolean();
         if (hasRhythmStaff)
         {
            rhythmStaff = new RhythmStaff();
            rhythmStaff.load(file);
         }
         setStaffs  (file.readArray(Staff  .class));
         setBarlines(file.readArray(Barline.class));
         file.decrementMeasure();        // don't count the last barline as a measure
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.section"));
      }
   }

   /**
    * save a section to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the section to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeBoolean(rhythmStaff != null);
         if (rhythmStaff != null)
            rhythmStaff.save(file);
         file.writeArray(staffs  .toArray());
         file.writeArray(barlines.toArray());
         file.decrementMeasure();     // don't count the last barline as a measure
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.section"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab section object.
    */
   @Override
   public String toString()
   {
      return toString(9);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab section object.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

                                                                 buffer.append(Utility.pad(ResourceBundle.getString("data_type.staff.rhythm_staff"        ), indentation1) + (rhythmStaff == null ? ResourceBundle.getString("text.none") : String.valueOf(rhythmStaff.getRhythmSlashes().size())) + "\n");
      if (rhythmStaff != null)
      for(int i=0; i<rhythmStaff.getRhythmSlashes().size(); ++i) buffer.append(Utility.pad(ResourceBundle.getString("text.rhythm_slashes") + "[" + i + "]" , indentation2) + rhythmStaff.getRhythmSlashes().get(i).toString(numSpacesToIndent + 6) + "\n");
                                                                 buffer.append(Utility.pad(ResourceBundle.getString("text.staves"                         ), indentation1) + staffs.size()                         + "\n");
      for(int i=0; i<staffs.size();                         ++i) buffer.append(Utility.pad(ResourceBundle.getString("text.staves")         + "[" + i + "]" , indentation2) + staffs.get(i)                         + "\n");
                                                                 buffer.append(Utility.pad(ResourceBundle.getString("text.bar_lines"                      ), indentation1) + barlines.size()                       + "\n");
      for(int i=0; i<barlines.size();                       ++i) buffer.append(Utility.pad(ResourceBundle.getString("text.bar_lines")      + "[" + i + "]" , indentation2) + barlines.get(i)                       + (i == barlines.size() - 1 ? "" : "\n"));

      return buffer.toString();
   }

   /**
    * validates the section, verifying that:
    * <ol>
    *    <li>each measure has the correct duration according to its time signatue</li>
    *    <li>todo: other checks</li>
    * </ol>
    * <br/><br/>
    * @return a list of the errors found and their corresponding locations.
    */
   public Hashtable<Location, String> validate()
   {
      Hashtable<Location, String> errors = new Hashtable<Location, String>();

      return errors;
   }
}
