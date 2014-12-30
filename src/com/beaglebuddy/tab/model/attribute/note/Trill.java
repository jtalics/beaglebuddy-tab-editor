/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.note;

import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a beaglebuddy tab trill note attribute.
 */
public class Trill extends NoteAttribute
{
   // data members
   private Instrument.Fret trillFret;     // fret to trill on



   /**
    * default constructor.
    */
   public Trill()
   {
      this(Instrument.Fret.Open);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param fret    fret to trill on.
    */
   public Trill(Instrument.Fret fret)
   {
      super(NoteAttribute.Type.Trill);

      setTrillFret(fret);
   }

   /**
    * @return the fret to trill on.
    */
   public Instrument.Fret getTrillFret()
   {
      return trillFret;
   }

   /**
    * sets the fret to trill on.
    * <br/><br/>
    * @param fret  the fret to trill on.
    */
   public void setTrillFret(Instrument.Fret fret)
   {
      this.trillFret = fret;
   }

   /**
    * @return a new copy of the trill note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new Trill(this.trillFret);
   }

   /**
    * @param object  object to check for equality with this trill attribute.
    * <br/><br/>
    * @return if the two trill attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof Trill && this.trillFret == ((Trill)object).trillFret);
   }

   /**
    * @return that the trill note attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the trill note attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the trill note attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         trillFret = Instrument.getFret(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.trill"));
      }
   }

   /**
    * save a trill note attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the trill note attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(trillFret.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.trill"));
      }
   }

   /**
    * @return a string representation of the trill.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" + ResourceBundle.getString("data_type.instrument.fret") + ": " + trillFret + ")";
   }
}
