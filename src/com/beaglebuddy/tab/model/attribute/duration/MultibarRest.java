/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.attribute.duration;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a multi bar rest duration attribute.
 * It is used when there is only one staff.
 * When there is more than one staff present, the multi bar rest begin, middle, and end attributes are used.
 */
public class MultibarRest extends DurationAttribute
{
   // class members
   public static final int MIN_MULTIBAR_REST_MEASURE_COUNT = 2;
   public static final int MAX_MULTIBAR_REST_MEASURE_COUNT = 255;

   // data members
   private byte numMeasures;    // number of measures to rest



   /**
    * default constructor.
    */
   public MultibarRest()
   {
      this((byte)MIN_MULTIBAR_REST_MEASURE_COUNT);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param numMeasures   the number of measures to rest.
    */
   public MultibarRest(byte numMeasures)
   {
      super(DurationAttribute.Type.MultibarRest);
      setNumMeasures(numMeasures);
   }

   /**
    * @return the number of measures to rest.
    */
   public byte getNumMeasures()
   {
      return numMeasures;
   }

   /**
    * sets the number of measures to rest.
    * <br/><br/>
    * @param numMeasures   the number of measures to rest.
    */
   public void setNumMeasures(byte numMeasures)
   {
      if (numMeasures < MIN_MULTIBAR_REST_MEASURE_COUNT)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.value", ResourceBundle.getString("text.rest.multibar.num_measures"), numMeasures, MIN_MULTIBAR_REST_MEASURE_COUNT, MAX_MULTIBAR_REST_MEASURE_COUNT));
      this.numMeasures = numMeasures;
   }

   /**
    * @return a new copy of the multibar rest duration attribute.
    */
   @Override
   public DurationAttribute clone()
   {
      return new MultibarRest(this.numMeasures);
   }

   /**
    * @param object  object to check for equality with this multi bar rest attribute.
    * <br/><br/>
    * @return if the two multi bar rest attributes are equal.
    */
   @Override
   public boolean equals(Object object)
   {
      return (object != null && object instanceof MultibarRest && this.numMeasures == ((MultibarRest)object).numMeasures);
   }

   /**
    * @return that the multibar rest duration attribute varies between instances.
    */
   @Override
   public boolean isVariable()
   {
      return true;
   }

   /**
    * read in the multi bar rest duration attribute from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the multi bar rest duration attribute can not be read from the beaglebuddy tab file.
    */
   @Override
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.load(file);
         numMeasures = (byte)file.read();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.duration_attribute.multibar_rest"));
      }
   }

   /**
    * save a multi bar rest duration attribute to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the multi bar rest duration attribute to the beaglebuddy tab file.
    */
   @Override
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         super.save(file);
         file.write(numMeasures);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.duration_attribute.multibar_rest"));
      }
   }

   /**
    * @return a string representation of the multi bar rest.
    */
   @Override
   public String toString()
   {
      return getType().text() + "(" + ResourceBundle.getString("text.num_measures") + ": " + numMeasures + ")";
   }
}
