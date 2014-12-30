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
import com.beaglebuddy.tab.gui.dialog.BarlineDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import java.awt.event.ActionEvent;
import java.util.ArrayList;




/**
 * called when the user clicks the <i>Barline</i> toolbar icon.
 */
public class BarlineAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public BarlineAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays the barline dialog box and lets the user select which type of barline they want, including no barline at all,
    * in which case the barline at the current location is deleted.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the current barline at the specified location
      Location location       = frame.getCurrentLocation();
      Section  section        = frame.getSong().getScore().getSections().get(location.getSection());
      Barline  currentBarline = section.getBarlines().get(location.getBarline());

      // show the dialog and let the user pick new repeat endings
      boolean       canDelete = !(currentBarline == null || currentBarline == section.getStartBarline() || currentBarline == section.getEndBarline());
      BarlineDialog dialog    = new BarlineDialog(frame, currentBarline, canDelete);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         Barline newBarline = dialog.getBarline();
         frame.setSongHasBeenEdited(true);
//       frame.setSongHasBeenEdited((currentBarline == null && newBarline != null) ||
//                                  (currentBarline != null && newBarline == null) ||
//                                  (currentBarline != null && newBarline != null))&& !currentBarline.equals(newBarline)));


         ArrayList<Barline> barlines = section.getBarlines();

         // if the user added a new barline to the section
         if (currentBarline == null && newBarline != null)
         {
            // set the drawing position where the new barline is located
            newBarline.setPosition(location.getPosition());

            // add the new barline to the section
            for(int i=0; i<barlines.size(); ++i)
            {
               if (barlines.get(i).getPosition() > newBarline.getPosition())
               {
                  barlines.add(i-1, newBarline);
                  break;
               }
            }
         }
         // if the user deleted the current barline
         else if (currentBarline != null && newBarline == null)
         {
            section.getBarlines().remove(currentBarline);
         }
         // otherwise, the user modified the current barline
         else
         {
            currentBarline = newBarline;
         }
      }
   }
}
