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
import com.beaglebuddy.tab.gui.dialog.RehearsalSignDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.RehearsalSign;
import com.beaglebuddy.tab.model.Section;
import java.awt.event.ActionEvent;
import java.util.Vector;



/**
 * called when the user clicks the <i>rehearsal sign</i> toolbar icon.
 */
public class RehearsalSignAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public RehearsalSignAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * lets the user set a rehearsal sign at the current barline in the song.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the current rehearsal sign at the current barline
      Location      location      = frame.getCurrentLocation();
      Section       section       = frame.getSong().getScore().getSections().get(location.getSection());
      Barline       barline       = section.getBarlines().get(location.getBarline());
      RehearsalSign rehearsalSign = barline.getRehearsalSign();

      // get a list of all the rehearsal signs defined in the song
      Vector<Character> lettersInUse = new Vector<Character>();
      for(Section s : frame.getSong().getScore().getSections())
      {
         for(Barline b : s.getBarlines())
         {
        	RehearsalSign rs = b.getRehearsalSign();
            if (rs != null && rs != rehearsalSign)
               lettersInUse.add(rs.getLetter());
         }
      }

      // show the dialog and let the user set a rehearsal sign at the current barline in the song
      RehearsalSignDialog dialog = new RehearsalSignDialog(frame, rehearsalSign, lettersInUse);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's changes to the song
      if (dialog.wasOKed())
      {
         barline.setRehearsalSign(dialog.getRehearsalSign());
         frame.setSongHasBeenEdited(true);
      }
   }
}
