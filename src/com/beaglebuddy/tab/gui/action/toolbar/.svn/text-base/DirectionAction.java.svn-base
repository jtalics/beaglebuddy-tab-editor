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
import com.beaglebuddy.tab.gui.dialog.music_symbols.DirectionDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Direction;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import java.awt.event.ActionEvent;
import java.util.ArrayList;



/**
 * called when the user clicks the <i>Direction</i> toolbar icon.
 */
public class DirectionAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public DirectionAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * lets the user choose a new direction for the song.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the current list of directions at the cuurent barline
      Location             location   = frame.getCurrentLocation();
      Section              section    = frame.getSong().getScore().getSections().get(location.getSection());
      Barline              barline    = section.getBarlines().get(location.getBarline());
      ArrayList<Direction> directions = barline.getDirections();

      // show the dialog and let the user edit the directions at the current barline
      DirectionDialog dialog = new DirectionDialog(frame, directions);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         barline.setDirections(dialog.getDirections());
         frame.setSongHasBeenEdited(true);
      }
   }
}
