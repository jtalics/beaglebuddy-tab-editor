/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls;

import com.beaglebuddy.tab.model.instrument.DrumMap;
import com.beaglebuddy.tab.model.instrument.DrumMapDictionary;
import com.beaglebuddy.tab.model.instrument.Drums;
import com.beaglebuddy.tab.model.instrument.Instrument;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;




/**
 * combo box model for selecting a drum map for a drum set.
 */
public class ComboBoxModelDrumMap extends DefaultComboBoxModel
{
   /**
    * constructor.
    * <br/><br/>
    * @param instrument   the instrument for which a list of valid drum maps are to be presented.
    */
   public ComboBoxModelDrumMap(Instrument instrument)
   {
      setDrumMapNames(instrument == null ? Instrument.Type.Other_Treble : instrument.getType());

      if (instrument != null && instrument.getType() == Instrument.Type.Drums)
      {
         Drums drums = (Drums)instrument;
         // todo: see if the name is in the drum map dictionary - if so select it [- else add it to the list and select it
//       addElement(drums.getDrumMap().getName());
      }
   }

   /**
    * constructor.
    * <br/><br/>
    * @param type   the type of instrument for which a list of valid drum maps are to be presented.
    */
/* public ComboBoxModelDrumMap(Instrument.Type type)
   {
      setDrumMapNames(type);
   }
*/
   /**
    * set the list of drum maps the user will be allowed to choose from based on the instrument type.
    * @param type  the type of instrument for which a list of valid drum maps are to be presented.
    */
   private void setDrumMapNames(Instrument.Type type)
   {
      removeAllElements();

      if (type == Instrument.Type.Drums)
      {
         ArrayList<DrumMap> drumMaps = DrumMapDictionary.getDrumMaps();
         for(DrumMap drumMap : drumMaps)
            addElement(drumMap.getName());
      }
   }
}
