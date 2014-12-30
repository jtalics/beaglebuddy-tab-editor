/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls;

import com.beaglebuddy.tab.model.instrument.Instrument;
import javax.swing.SpinnerNumberModel;



/**
 * spinner model for the number of strings an instrument has.
 */
public class SpinnerNumStringsModel extends SpinnerNumberModel
{
   /**
    * constructor.
    * <br/><br/>
    * @param instrument  the instrument whose number of strings should be displayed.
    */
   public SpinnerNumStringsModel(Instrument instrument)
   {
      super(instrument == null ? Instrument.MIN_NUM_STRINGS : instrument.getTuning().getNumStrings(),
            instrument == null ? Instrument.MIN_NUM_STRINGS : instrument.getMinNumstrings(),
            instrument == null ? Instrument.MAX_NUM_STRINGS : instrument.getMaxNumstrings(), 1);
   }
}
