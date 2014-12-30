/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls;

import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.instrument.Instrument;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;




/**
 * combo box model for selecting a tuning for a given instrument type and number of strings.
 */
public class ComboBoxModelTuning extends DefaultComboBoxModel
{
   /**
    * constructor.
    * <br/><br/>
    * @param instrument   the instrument for which a list of valid tunings are to be presented.
    */
   public ComboBoxModelTuning(Instrument instrument)
   {
      if (instrument != null)
      {
         if (instrument.getType() == Instrument.Type.Drums)
         {
            setSelectedItem(instrument.getTuning().getName());
         }
         else
         {
            setTuningNames(instrument.getType(), instrument.getTuning().getNumStrings());
            setSelectedItem(instrument.getTuning().getName());
         }
      }
   }

   /**
    * constructor.
    * <br/><br/>
    * @param type         the type of instrument for which a list of valid tunings are to be presented.
    * @param numStrings   the the number of strings the instrument has.
    */
   public ComboBoxModelTuning(Instrument.Type type, int numStrings)
   {
      setTuningNames(type, numStrings);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param type    the type of instrument for which a list of valid tunings are to be presented.
    * @param tuning  the tuning to mark as selected, or <i>custom</i> if no matching tuning is found.
    */
   public ComboBoxModelTuning(Instrument.Type type, Tuning tuning)
   {
      if (tuning != null)
      {
         setTuningNames(type, tuning.getNumStrings());
         setSelectedItem(tuning.getName());
      }
   }

   /**
    * set the list of tunings the user will be allowed to choose from based on the instrument type and number of strings.
    * @param type         the type of instrument for which a list of valid tunings are to be presented.
    * @param numStrings   the the number of strings the instrument has.
    */
   private void setTuningNames(Instrument.Type type, int numStrings)
   {
      removeAllElements();

      ArrayList<String> tuningNames = TuningDictionary.getTuningNames(type, numStrings);
      for(String tuningName : tuningNames)
         addElement(tuningName);
   }
}
