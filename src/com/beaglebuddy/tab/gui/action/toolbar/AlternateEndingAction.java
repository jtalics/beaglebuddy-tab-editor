/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.toolbar;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.AlternateEndingDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.AlternateEnding;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import java.awt.event.ActionEvent;




/**
 * called when the user clicks the <i>Alternate Ending</i> toolbar icon.
 */
public class AlternateEndingAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public AlternateEndingAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays the alternate ending dialog box and lets the user select which repeats the user wants this measure to be played.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the current alternate ending
      Location        location               = frame.getCurrentLocation();
      Section         section                = frame.getSong().getScore().getSections().get(location.getSection());
      Barline         barline                = section.getBarlines().get(location.getBarline());
      AlternateEnding currentAlternateEnding = barline.getAlternateEnding();

      // show the dialog and let the user pick new repeat endings
      AlternateEndingDialog dialog = new AlternateEndingDialog(frame);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         AlternateEnding newAlternateEnding = dialog.getAlternateEnding();
         frame.setSongHasBeenEdited((currentAlternateEnding == null && newAlternateEnding != null) ||
                                    (currentAlternateEnding != null && newAlternateEnding == null) ||
                                    (currentAlternateEnding != null && newAlternateEnding != null  && !currentAlternateEnding.equals(newAlternateEnding)));
         barline.setAlternateEnding(newAlternateEnding);
      }
   }
}
