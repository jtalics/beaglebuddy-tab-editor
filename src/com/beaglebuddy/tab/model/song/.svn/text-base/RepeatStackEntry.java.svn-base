/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.song;



/**
 *
 */
public class RepeatStackEntry
{
   // data members
   private byte position;
   private byte numRepeats;
   private byte remainingRepeats;
   private int  sectionNumber;



   /**
    * constructor.
    * <br/><br/>
    * @param sectionNumber
    * @param position
    * @param numRepeats
    */
   public RepeatStackEntry(int sectionNumber, byte position, byte numRepeats)
   {
      this.sectionNumber    = sectionNumber;
      this.position         = position;
      this.numRepeats       = numRepeats;
      this.remainingRepeats = this.numRepeats;
   }

   public byte getPosition()
   {
      return position;
   }

   public void setPosition(byte position)
   {
      this.position = position;
   }

   public byte getNumRepeats()
   {
      return numRepeats;
   }

   public byte getRemainingRepeats()
   {
      return remainingRepeats;
   }

   public void setNumRepeats(byte numRepeats)
   {
      this.numRepeats = numRepeats;
   }

   public int getSectionNumber()
   {
      return sectionNumber;
   }

   public void setSectionNumber(int sectionNumber)
   {
      this.sectionNumber = sectionNumber;
   }

   public boolean toBeRepeated()
   {
      return remainingRepeats-- > 0;
   }

   public void reInit()
   {
      remainingRepeats = numRepeats;
   }

   @Override
   public String toString()
   {
      return "section number: " + sectionNumber + ", position: " + position + ", num repeats: " + numRepeats + ", remaining repeats: " + remainingRepeats;
   }
}
