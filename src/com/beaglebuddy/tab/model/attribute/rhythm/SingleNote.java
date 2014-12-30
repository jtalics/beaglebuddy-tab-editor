/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.rhythm;

import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a beaglebuddy tab single note rhythm slash attribute.
 */
public class SingleNote extends RhythmSlashAttribute
{
   // data members
   private Instrument.String string;
   private Instrument.Fret   fret;




   /**
    * default constructor.
    */
   public SingleNote()
   {
      super(RhythmSlashAttribute.Type.SingleNote);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param string  the string the note is played on.
    * @param fret    the fret the note is played on.
    */
   public SingleNote(Instrument.String string, Instrument.Fret fret)
   {
      super(RhythmSlashAttribute.Type.SingleNote);

      this.string = string;
      this.fret   = fret;
   }

   /**
    * @return the string on which the note is played.
    */
   public Instrument.String getString()
   {
      return string;
   }

   /**
    * set the string on which the note is played.
    * <br/><br/>
    * @param string  the string on which the note is played.
    */
   public void setString(Instrument.String string)
   {
      this.string = string;
   }

   /**
    * @return the fret at which the note is played.
    */
   public Instrument.Fret getFret()
   {
      return fret;
   }

   /**
    * set the fret at which the note is played.
    * <br/><br/>
    * @param fret  the fret at which the note is played.
    */
   public void setFret(Instrument.Fret fret)
   {
      this.fret = fret;
   }

   /**
    * @return a new copy of the single note rhythm slash attribute.
    */
   @Override
   public RhythmSlashAttribute clone()
   {
      return new SingleNote(this.string, this.fret);
   }

   /**
    * @param object  object to check for equality with this single note attribute.
    * <br/><br/>
    * @return if the two single note attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof SingleNote && this.string == ((SingleNote)object).string && this.fret == ((SingleNote)object).fret);
   }

   /**
    * @return that the single note rhythm slash attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the single note rhythm slash attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the single note rhythm slash attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         this.string = Instrument.getString(file.read());
         this.fret   = Instrument.getFret  (file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.rhythm_attribute.single_note"));
      }
   }

   /**
    * save a single note rhythm slash attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the single note rhythm slash attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(string.ordinal());
         file.write(fret  .ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), "data_type.rhythm_attribute.single_note");
      }
   }

   /**
    * @return a string representation of the single note rhythm slash attribute.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" +
             ResourceBundle.getString("data_type.instrument.string") + ": " + string + ", " +
             ResourceBundle.getString("data_type.instrument.fret"  ) + ": " + fret   + ")";
   }
}
