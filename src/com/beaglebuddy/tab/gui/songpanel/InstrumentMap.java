/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andy will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.Tuning;
import com.beaglebuddy.tab.model.TuningDictionary;
import com.beaglebuddy.tab.model.instrument.DrumTuning;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.instrument.InstrumentIn;
import com.beaglebuddy.tab.model.staff.Staff.Clef;
import com.beaglebuddy.tab.model.staff.Staff;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
 * Utility class for determining which instrument is active for a given staff and measure.
 */
public class InstrumentMap
{
   private static final Tuning DefaultDrumTuning = new DrumTuning();

   private final List<Section> sections;
   private final List<Instrument> instruments;

   /**
    * Populates the instrument map with the information from the sections.
    * @param sections
    * @param instruments
    */
   public InstrumentMap(List<Section> sections, List<Instrument> instruments)
   {
      this.sections = sections;
      this.instruments = instruments;
   }

   /**
    * Returns the instrument-ins for the given section index and staff.
    * @param sectionIndex
    * @param staffIndex
    * @return map of instrument-ins, from Instrument to barline index
    */
   public Map<Instrument, Integer> getInstrumentIns(int sectionIndex,
                                                    int staffIndex)
   {
      // Examine all sections leading up to the current staff.
      Map<Instrument, Integer> instruments =
         new LinkedHashMap<Instrument, Integer>();

      // Check all barlines for instrument-ins.
      Section s = this.sections.get(sectionIndex);
      for (int barlineIndex = 0;
           barlineIndex < s.getBarlines().size();
           ++barlineIndex)
      {
         Barline barline = s.getBarlines().get(barlineIndex);
         for (InstrumentIn in : barline.getInstrumentIns())
         {
            // Check this barline for an instrument in on the input staff.
            if (in.getStaff() == staffIndex)
            {
               for (int instrumentIndex : in.getStaffActiveInstruments())
               {
                  Instrument instrument = this.instruments.get(instrumentIndex);
                  instruments.put(instrument, barlineIndex);
               }
            }
         }
      }

      return instruments;
   }

   /**
    * Returns the tuning active for the given section index and staff.
    * @param sectionIndex
    * @param staffIndex
    * @return
    */
   public Tuning getActiveTuning(int sectionIndex, int staffIndex)
   {
      // Examine all sections preceding the given staff's section.
      Tuning tuning = null;
      for (int i = sectionIndex; (i >= 0) && (tuning == null); --i)
      {
         // Check all barlines for instrument-ins.
         Section section = this.sections.get(i);
         for (Barline barline : section.getBarlines())
         {
            for (InstrumentIn in : barline.getInstrumentIns())
            {
               // Check for an instrument-in on the desired staff.
               if (in.getStaff() == staffIndex
                   && in.getStaffActiveInstruments().length > 0)
               {
                  Instrument instrument = this.instruments.get(0);
                  tuning = instrument.getTuning();
                  break;
               }
            }
            if (tuning != null)
            {
               break;
            }
         }
      }

      // Add the default instrument, if no instruments are active.
      if (tuning == null)
      {
         // No active instrument is defined, so use the default standard tuning
         // for the type of staff.
         Staff staff = this.sections.get(sectionIndex).getStaffs().get(staffIndex);
         if (staff.getClef() == Clef.Treble)
         {
            tuning = TuningDictionary.getTuning(Instrument.Type.Guitar,
                                                staff.getNumTabLines(),
                                                TuningDictionary.TUNING_STANDARD);
         }
         else if (staff.getClef() == Clef.Bass)
         {
            tuning = TuningDictionary.getTuning(Instrument.Type.Bass_Guitar,
                                                staff.getNumTabLines(),
                                                TuningDictionary.TUNING_STANDARD);
         }
         else if (staff.getClef() == Clef.Drum)
         {
            tuning = DefaultDrumTuning;
         }
         else
         {
            throw new RuntimeException("unknown staff type " + staff.getClef());
         }
      }

      return tuning;
   }
}
