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
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;




/**
 * This class represents a beaglebuddy tab score and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class Score
{
   // data members
   private ArrayList<FloatingText> floatingTexts;         // floating text in the score
   private ArrayList<Instrument>   instruments;           // instruments used in the score
   private ArrayList<Section>      sections;              // sections in the score




   /**
    * default constructor.
    */
   public Score()
   {
      floatingTexts = new ArrayList<FloatingText>(0);
      instruments   = new ArrayList<Instrument  >(1);
      sections      = new ArrayList<Section     >(1);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param instruments        instruments used in the score.
    * @param floatingTexts      floating text in the score.
    * @param sections           sections in the score.
    */
   public Score(Instrument[] instruments, FloatingText[] floatingTexts, Section[] sections)
   {
      this();

      setInstruments  (instruments  );
      setFloatingTexts(floatingTexts);
      setSections     (sections     );
   }

   /**
    * @return the floating text in the score.
    */
   public ArrayList<FloatingText> getFloatingTexts()
   {
      return floatingTexts;
   }

   /**
    * @param floatingTexts   the floating texts in the score.
    */
   private void setFloatingTexts(Object[] floatingTexts)
   {
      this.floatingTexts = new ArrayList<FloatingText>(0);

      if (floatingTexts != null)
      {
         for(Object floatingText : floatingTexts)
            this.floatingTexts.add((FloatingText)floatingText);
      }
   }

   /**
    * @return the instruments used by the score.
    */
   public ArrayList<Instrument> getInstruments()
   {
      return instruments;
   }

   /**
    * @param instruments   the instruments used by the score.
    */
   private void setInstruments(Object[] instruments)
   {
      this.instruments = new ArrayList<Instrument>(0);

      if (instruments != null)
      {
         for(Object instrument : instruments)
            this.instruments.add((Instrument)instrument);
      }
   }

   /**
    * @param instruments   the instruments used by the score.
    */
   public void setInstruments(ArrayList<Instrument> instruments)
   {
      if (instruments == null)
         this.instruments = new ArrayList<Instrument>(0);
      else
         this.instruments = instruments;
   }

   /**
    * @return whether the score contains the an instrument of the specified type.
    * <br/><br/>
    * @param type   type of instrument being sought.
    */
   public boolean hasInstrument(Instrument.Type type)
   {
      boolean found = false;

      for(Iterator<Instrument> i=instruments.iterator(); i.hasNext() && !found; )
      {
         Instrument instrument = i.next();
         found = (instrument.getType() == type);
      }
      return found;
   }

   /**
    * @return the sections of the score.
    */
   public ArrayList<Section> getSections()
   {
      return sections;
   }

   /**
    * @param sections   the sections of the score.
    */
   private void setSections(Object[] sections)
   {
      this.sections = new ArrayList<Section>(0);

      if (sections != null)
      {
         for(Object section : sections)
            this.sections.add((Section)section);
      }
   }

   /**
    * @return the number of measures in the score.
    */
   public int getNumMeasures()
   {
      return 100;  // todo: fix this
   }

   /**
    * @return the number of sections in the song.
    */
   public int getNumSections()
   {
      return sections.size();
   }

   /**
    * formats the score, setting the drawing positions of all the notes and music symbols as well as setting the spacing between them.
    */
   public void layout()
   {
      // each section within the score is layed out independently
      for (Section section : sections)
         section.layout();
   }

   /**
    * read in a score from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the score from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         setInstruments  (file.readArray(Instrument  .class));
         setFloatingTexts(file.readArray(FloatingText.class));
         setSections     (file.readArray(Section     .class));
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.score"));
      }
   }

   /**
    * save a score to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the score to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.writeArray(instruments  .toArray());
         file.writeArray(floatingTexts.toArray());
         file.writeArray(sections     .toArray());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.score"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy score.
    */
   @Override
   public String toString()
   {
      return toString(3);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy score.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer       = new StringBuffer();
      String       indentation1 = Utility.indent(numSpacesToIndent);
      String       indentation2 = Utility.indent(numSpacesToIndent + 3);

      buffer.append(ResourceBundle.getString("data_type.score") + "\n");
                                                buffer.append(Utility.pad(ResourceBundle.getString("text.instruments"                    ), indentation1) + instruments.size()   + "\n");
      for(int i=0; i<instruments.size();   ++i) buffer.append(Utility.pad(ResourceBundle.getString("text.instruments")    + "[" + i + "]" , indentation2) + instruments.get(i)   + "\n");
                                                buffer.append(Utility.pad(ResourceBundle.getString("text.floating_texts"                 ), indentation1) + floatingTexts.size() + "\n");
      for(int i=0; i<floatingTexts.size(); ++i) buffer.append(Utility.pad(ResourceBundle.getString("text.floating_texts") + "[" + i + "]" , indentation2) + floatingTexts.get(i) + "\n");
                                                buffer.append(Utility.pad(ResourceBundle.getString("text.sections"                       ), indentation1) + sections.size()      + "\n");
      for(int i=0; i<sections.size();      ++i) buffer.append(Utility.pad(ResourceBundle.getString("text.sections")       + "[" + i + "]" , indentation2) + sections.get(i)      + "\n");

      return buffer.toString();
   }

   /**
    * validates the score, verifying that:
    * <ol>
    *    <li>each measure has the correct duration according to its time signatue</li>
    *    <li>each repeat bar line has a matching begin and end</li>
    *    <li>todo: other checks</li>
    * </ol>
    * Thus, this method acts as a <i>compiler</i> of sorts for the score.
    * <br/><br/>
    * @return a list of the errors found and their corresponding locations.
    */
   public Hashtable<String, Location> validate()
   {
      Hashtable<String, Location> errors = new Hashtable<String, Location>();

      return errors;
   }
}
