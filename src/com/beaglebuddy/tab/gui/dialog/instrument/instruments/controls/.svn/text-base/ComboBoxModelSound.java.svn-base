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
import com.beaglebuddy.tab.model.instrument.*;
import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;




/**
 * combo box model for selecting a midi sound for a given instrument type.
 */
public class ComboBoxModelSound extends DefaultComboBoxModel
{
   /**
    * constructor.
    * <br/><br/>
    * @param instrument   the instrument for which a list of valid midi sounds are to be presented.
    */
   public ComboBoxModelSound(Instrument instrument)
   {
      if (instrument != null)
      {
         setSounds(instrument.getType());
         setSelectedItem(instrument.getPreset());
      }
   }

   /**
    * set the list of midi sounds the user will be allowed to choose from based on the instrument type.
    * <br/><br/>
    * @param type   the type of instrument for which a list of valid midi sounds are to be presented.
    */
   private void setSounds(Instrument.Type type)
   {
      ArrayList<Midi.Sound> sounds = null;

      switch (type)
      {
         case Bass_Guitar:  sounds = BassGuitar .getValidPresets();    break;
         case Drums:        sounds = Drums      .getValidPresets();    break;
         case Guitar:       sounds = Guitar     .getValidPresets();    break;
         case Keyboards:    sounds = Keyboard   .getValidPresets();    break;
         case Other_Bass:   sounds = OtherBass  .getValidPresets();    break;
         case Other_Treble: sounds = OtherTreble.getValidPresets();    break;
         case Vocals:       sounds = Vocals     .getValidPresets();    break;
      }

      removeAllElements();
      for(Midi.Sound sound : sounds)
         addElement(sound);
   }
}
