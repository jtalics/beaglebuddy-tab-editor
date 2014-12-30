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
import com.beaglebuddy.tab.gui.dialog.VolumeDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.Volume;
import java.awt.event.ActionEvent;
import java.util.ArrayList;



/**
 * called when the user clicks the <i>Volume</i> toolbar icon.
 */
public class VolumeAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public VolumeAction(MainFrame frame)
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
      Location location = frame.getCurrentLocation();
      Section  section  = frame.getSong().getScore().getSections().get(location.getSection());
      byte     staff    = location.getStaff();
      Barline  barline  = section.getBarlines().get(location.getBarline());
      Volume   volume   = barline.getVolume(staff);

      // show the dialog and let the user set the volume of the instruments on the staff
      VolumeDialog dialog = new VolumeDialog(frame, volume);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         ArrayList<Volume> volumes = barline.getVolumes();
         if (volume == null)
         {
            volumes.add(dialog.getVolume());
         }
         else
         {
            volumes.remove(volume);
            volumes.add(dialog.getVolume());
            frame.setSongHasBeenEdited(true);
         }
      }
   }
}
