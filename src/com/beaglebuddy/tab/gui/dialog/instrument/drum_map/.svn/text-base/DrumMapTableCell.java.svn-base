/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.drum_map;

import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/*
 * used for displaying midi percussion sounds in the drum map dialog box.
 * this class is used to display a number and then the localized name of the midi percussion sound.
 * ex: 11. accoustic bass drum
 *     20. cowbell
 */
public class DrumMapTableCell
{
   // data members
   private Midi.Percussion percussion;
   private String          displayName;



   /**
    * constructor.
    * <br/><br/>
    * @param percussion   midi percussion sound.
    */
   public DrumMapTableCell(Midi.Percussion percussion)
   {
      this.percussion = percussion;
      setDisplayNumber("");
   }

   /**
    * constructor.
    * <br/><br/>
    * @param percussion      midi percussion sound.
    * @param displayNumber   number used as a prefix in displaying the name of the midi percussion sound in the table.
    */
   public DrumMapTableCell(Midi.Percussion percussion, String displayNumber)
   {
      this.percussion = percussion;
      setDisplayNumber(displayNumber);
   }

   /**
    * @return the midi percussion.
    */
   public Midi.Percussion getPercussion()
   {
      return percussion;
   }

   /**
    * sets the display name.
    * <br/><br/>
    * @param displayNumber   number used as a prefix in displaying the name of the midi percussion sound in the table.
    */
   public void setDisplayNumber(String displayNumber)
   {
      this.displayName = (percussion == null ? "" : displayNumber + ResourceBundle.getString("midi.percussion." + (percussion.ordinal() + 1)));
   }

   /**
    * @return the name of the midi percussion sound.
    */
   @Override
   public String toString()
   {
      return displayName;
   }
}
