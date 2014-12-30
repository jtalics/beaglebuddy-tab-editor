/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.instrument.instruments.controls;

import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.Tuning;
import javax.swing.SpinnerListModel;



/**
 * spinner model for the tuning notes of a string.
 */
public class SpinnerNoteModel extends SpinnerListModel
{
   // class members
   private static final Midi.Note[] notes = {Midi.Note.CN1     , Midi.Note.CSHARPN1, Midi.Note.DFLATN1 , Midi.Note.DN1     , Midi.Note.DSHARPN1, Midi.Note.EFLATN1 , Midi.Note.EN1     , Midi.Note.FN1     , Midi.Note.FSHARPN1, Midi.Note.GFLATN1 , Midi.Note.GN1     , Midi.Note.GSHARPN1, Midi.Note.AFLATN1 , Midi.Note.AN1     , Midi.Note.ASHARPN1, Midi.Note.BFLATN1 , Midi.Note.BN1     ,
                                             Midi.Note.C0      , Midi.Note.CSHARP0 , Midi.Note.DFLAT0  , Midi.Note.D0      , Midi.Note.DSHARP0 , Midi.Note.EFLAT0  , Midi.Note.E0      , Midi.Note.F0      , Midi.Note.FSHARP0 , Midi.Note.GFLAT0  , Midi.Note.G0      , Midi.Note.GSHARP0 , Midi.Note.AFLAT0  , Midi.Note.A0      , Midi.Note.ASHARP0 , Midi.Note.BFLAT0  , Midi.Note.B0      ,
                                             Midi.Note.C1      , Midi.Note.CSHARP1 , Midi.Note.DFLAT1  , Midi.Note.D1      , Midi.Note.DSHARP1 , Midi.Note.EFLAT1  , Midi.Note.E1      , Midi.Note.F1      , Midi.Note.FSHARP1 , Midi.Note.GFLAT1  , Midi.Note.G1      , Midi.Note.GSHARP1 , Midi.Note.AFLAT1  , Midi.Note.A1      , Midi.Note.ASHARP1 , Midi.Note.BFLAT1  , Midi.Note.B1      ,
                                             Midi.Note.C2      , Midi.Note.CSHARP2 , Midi.Note.DFLAT2  , Midi.Note.D2      , Midi.Note.DSHARP2 , Midi.Note.EFLAT2  , Midi.Note.E2      , Midi.Note.F2      , Midi.Note.FSHARP2 , Midi.Note.GFLAT2  , Midi.Note.G2      , Midi.Note.GSHARP2 , Midi.Note.AFLAT2  , Midi.Note.A2      , Midi.Note.ASHARP2 , Midi.Note.BFLAT2  , Midi.Note.B2      ,
                                             Midi.Note.C3      , Midi.Note.CSHARP3 , Midi.Note.DFLAT3  , Midi.Note.D3      , Midi.Note.DSHARP3 , Midi.Note.EFLAT3  , Midi.Note.E3      , Midi.Note.F3      , Midi.Note.FSHARP3 , Midi.Note.GFLAT3  , Midi.Note.G3      , Midi.Note.GSHARP3 , Midi.Note.AFLAT3  , Midi.Note.A3      , Midi.Note.ASHARP3 , Midi.Note.BFLAT3  , Midi.Note.B3      ,
                                             Midi.Note.C4      , Midi.Note.CSHARP4 , Midi.Note.DFLAT4  , Midi.Note.D4      , Midi.Note.DSHARP4 , Midi.Note.EFLAT4  , Midi.Note.E4      , Midi.Note.F4      , Midi.Note.FSHARP4 , Midi.Note.GFLAT4  , Midi.Note.G4      , Midi.Note.GSHARP4 , Midi.Note.AFLAT4  , Midi.Note.A4      , Midi.Note.ASHARP4 , Midi.Note.BFLAT4  , Midi.Note.B4      ,
                                             Midi.Note.C5      , Midi.Note.CSHARP5 , Midi.Note.DFLAT5  , Midi.Note.D5      , Midi.Note.DSHARP5 , Midi.Note.EFLAT5  , Midi.Note.E5      , Midi.Note.F5      , Midi.Note.FSHARP5 , Midi.Note.GFLAT5  , Midi.Note.G5      , Midi.Note.GSHARP5 , Midi.Note.AFLAT5  , Midi.Note.A5      , Midi.Note.ASHARP5 , Midi.Note.BFLAT5  , Midi.Note.B5      ,
                                             Midi.Note.C6      , Midi.Note.CSHARP6 , Midi.Note.DFLAT6  , Midi.Note.D6      , Midi.Note.DSHARP6 , Midi.Note.EFLAT6  , Midi.Note.E6      , Midi.Note.F6      , Midi.Note.FSHARP6 , Midi.Note.GFLAT6  , Midi.Note.G6      , Midi.Note.GSHARP6 , Midi.Note.AFLAT6  , Midi.Note.A6      , Midi.Note.ASHARP6 , Midi.Note.BFLAT6  , Midi.Note.B6      ,
                                             Midi.Note.C7      , Midi.Note.CSHARP7 , Midi.Note.DFLAT7  , Midi.Note.D7      , Midi.Note.DSHARP7 , Midi.Note.EFLAT7  , Midi.Note.E7      , Midi.Note.F7      , Midi.Note.FSHARP7 , Midi.Note.GFLAT7  , Midi.Note.G7      , Midi.Note.GSHARP7 , Midi.Note.AFLAT7  , Midi.Note.A7      , Midi.Note.ASHARP7 , Midi.Note.BFLAT7  , Midi.Note.B7      ,
                                             Midi.Note.C8      , Midi.Note.CSHARP8 , Midi.Note.DFLAT8  , Midi.Note.D8      , Midi.Note.DSHARP8 , Midi.Note.EFLAT8  , Midi.Note.E8      , Midi.Note.F8      , Midi.Note.FSHARP8 , Midi.Note.GFLAT8  , Midi.Note.G8      , Midi.Note.GSHARP8 , Midi.Note.AFLAT8  , Midi.Note.A8      , Midi.Note.ASHARP8 , Midi.Note.BFLAT8  , Midi.Note.B8      ,
                                             Midi.Note.C9      , Midi.Note.CSHARP9 , Midi.Note.DFLAT9  , Midi.Note.D9      , Midi.Note.DSHARP9 , Midi.Note.EFLAT9  , Midi.Note.E9      , Midi.Note.F9      , Midi.Note.FSHARP9 , Midi.Note.GFLAT9  , Midi.Note.G9      };

   /**
    * constructor.
    * <br/><br/>
    * @param string   the number of the string to be tuned.
    * @param tuning   the tuning for the instrument.
    */
   public SpinnerNoteModel(int string, Tuning tuning)
   {
      super(notes);

      setValue(tuning == null || tuning.getNotes().length == 0 || string >= tuning.getNotes().length ? Midi.Note.CN1 : tuning.getNotes()[string]);
   }
}
