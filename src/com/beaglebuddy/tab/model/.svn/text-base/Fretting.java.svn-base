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
import com.beaglebuddy.tab.model.attribute.chord.FingerFretHand;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;



/**
 * This class represents a beaglebuddy fretting and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * When a user plays a note on a guitar, this class specifies where the note is to be fretted and which fret hand finger to use.
 * This information is used by ChordDiagram to show the user how to play\finger a chord.
 * <p>
 * note that the <i>fret</i> data member can not be null while the associated <i>finger</i> data member is optional and thus can be null.
 * </p>
 */
public class Fretting
{
   // data members
   private Instrument.Fret       fret;      // where to fret the note
   private FingerFretHand.Finger finger;    // the fret hand finger used to fret the note




   /**
    * default constructor.
    */
   public Fretting()
   {
      this(Instrument.Fret.Not_Used, null);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param fret     where the note is fretted.
    * @param finger   the fret hand finger used to fret the note.
    */
   public Fretting(Instrument.Fret fret, FingerFretHand.Finger finger)
   {
      this.fret   = fret;
      this.finger = finger;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param fretting   fretting whose values will be deep copied.
    */
   public Fretting(Fretting fretting)
   {
      this.fret   = fretting.fret;
      this.finger = fretting.finger;
   }

   /**
    * @return where the note is fretted.
    */
   public Instrument.Fret getFret()
   {
      return fret;
   }

   /**
    * set where the note is fretted.
    * <br/><br/>
    * @param fret   where the note is fretted.
    */
   public void setFret(Instrument.Fret fret)
   {
      this.fret = fret;
   }

   /**
    * @return the fret hand finger used to fret the note.
    */
   public FingerFretHand.Finger getFinger()
   {
      return finger;
   }

   /**
    * sets the fret hand finger used to fret the note.
    */
   public void setFinger(FingerFretHand.Finger finger)
   {
      this.finger = finger;
   }

   /**
    * read in a fretting from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the fretting from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         fret = Instrument.getFret(file.read());
         if (file.readBoolean())
            finger = FingerFretHand.getFinger(file.read());
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.fretting"));
      }
   }

   /**
    * save a chord diagram to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the chord diagram to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(fret.ordinal());
         file.writeBoolean(finger != null);
         if (finger != null)
            file.write(finger.ordinal());
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.fretting"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy fretting.
    */
   @Override
   public String toString()
   {
      return ResourceBundle.getString("data_type.instrument.fret") + fret + ", " + ResourceBundle.getString("chord_attribute_finger_fret_hand") + finger;
   }
}
