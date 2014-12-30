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
 * This class represents a beaglebuddy tab tapped harmonic note attribute.
 */
public class TappedHarmonic extends NoteAttribute
{
   // data members
   private Instrument.Fret fret;   // fret to tap harmonic at



   /**
    * default constructor.
    */
   public TappedHarmonic()
   {
      super(NoteAttribute.Type.TappedHarmonic);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param fret    fret to tap harmonic at.
    */
   public TappedHarmonic(Instrument.Fret fret)
   {
      super(NoteAttribute.Type.TappedHarmonic);

      this.fret = fret;
   }

   /**
    * @return the fret number where the harmonic is tapped.
    */
   public Instrument.Fret getFret()
   {
      return fret;
   }

   /**
    * sets the fret number where the harmonic is tapped.
    * <br/><br/>
    * @param fret  the fret number where the harmonic is tapped.
    */
   public void setFret(Instrument.Fret fret)
   {
      this.fret = fret;
   }

   /**
    * @return a new copy of the tapped harmonic note attribute.
    */
   @Override
   public NoteAttribute clone()
   {
      return new TappedHarmonic(this.fret);
   }

   /**
    * @param object  object to check for equality with this tapped harmonic attribute.
    * <br/><br/>
    * @return if the two tapped harmonic attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof TappedHarmonic && this.fret == ((TappedHarmonic)object).fret);
   }

   /**
    * @return that the tapped harmonic note attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the tapped harmonic note attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the tapped harmonic note attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         fret = Instrument.getFret(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.tapped_harmonic"));
      }
   }

   /**
    * save a tapped harmonic note attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the tapped harmonic note attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(fret.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.note_attribute.tapped_harmonic"));
      }
   }

   /**
    * @return a string representation of the tapped harmonic.
    */
   @Override
   public String toString()
   {
      return getType().text() + " (" + ResourceBundle.getString("data_type.instrument.fret") + ": " + fret + ")";
   }
}
