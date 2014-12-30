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
import com.beaglebuddy.tab.gui.dialog.TempoDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.TempoMarker;
import java.awt.event.ActionEvent;



/**
 * called when the user clicks the <i>Tempo</i> toolbar icon.
 */
public class TempoAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public TempoAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * lets the user choose a new tempo for the song.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the current tempo marker at the specified location
      Location    location = frame.getCurrentLocation();
      Section     section  = frame.getSong().getScore().getSections().get(location.getSection());
      Barline     barline  = section.getBarlines().get(location.getBarline());
      TempoMarker tempo    = barline.getTempoMarker();

      // show the dialog and let the user set the tempo of the song
      TempoDialog dialog = new TempoDialog(frame, tempo);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         barline.setTempoMarker(dialog.getTempoMarker());
         frame.setSongHasBeenEdited(true);
      }
   }
}
