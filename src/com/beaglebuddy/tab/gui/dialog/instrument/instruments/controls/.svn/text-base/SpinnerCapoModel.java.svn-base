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
import javax.swing.SpinnerListModel;



/**
 * spinner model for the fret a capo is applied to an instrument.
 */
public class SpinnerCapoModel extends SpinnerListModel
{
   // class members
   private static final Instrument.Fret[] frets = {Instrument.Fret.Not_Used,
                                                   Instrument.Fret.Fret_1 , Instrument.Fret.Fret_2 , Instrument.Fret.Fret_3 , Instrument.Fret.Fret_4 , Instrument.Fret.Fret_5 ,
                                                   Instrument.Fret.Fret_6 , Instrument.Fret.Fret_7 , Instrument.Fret.Fret_8 , Instrument.Fret.Fret_9 , Instrument.Fret.Fret_10,
                                                   Instrument.Fret.Fret_11, Instrument.Fret.Fret_12, Instrument.Fret.Fret_13, Instrument.Fret.Fret_14, Instrument.Fret.Fret_15,
                                                   Instrument.Fret.Fret_16, Instrument.Fret.Fret_17, Instrument.Fret.Fret_18, Instrument.Fret.Fret_19, Instrument.Fret.Fret_20};
   /**
    * constructor.
    * <br/><br/>
    * @param instrument  the instrument whose fret a capo is applied to should be displayed.
    */
   public SpinnerCapoModel(Instrument instrument)
   {
      super(frets);

      setValue(instrument == null ? Instrument.Fret.Not_Used : instrument.getCapo());
   }
}
