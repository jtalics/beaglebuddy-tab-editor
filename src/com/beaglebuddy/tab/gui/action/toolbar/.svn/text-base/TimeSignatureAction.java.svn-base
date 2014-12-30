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
import com.beaglebuddy.tab.gui.dialog.TimeSignatureDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.TimeSignature;
import java.awt.event.ActionEvent;



/**
 * called when the user clicks the <i>TimeSignature</i> toolbar icon.
 */
public class TimeSignatureAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public TimeSignatureAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * lets the user choose a new time signature for the measure.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the current time signature at the specified location
      Location      location      = frame.getCurrentLocation();
      Section       section       = frame.getSong().getScore().getSections().get(location.getSection());
      Barline       barline       = section.getBarlines().get(location.getBarline());
      TimeSignature timeSignature = barline.getTimeSignature();

      // show the dialog and let the user set the time signature for the measure
      TimeSignatureDialog dialog = new TimeSignatureDialog(frame, timeSignature);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         barline.setTimeSignature(dialog.getTimeSignature());
         frame.setSongHasBeenEdited(true);
      }
   }
}
