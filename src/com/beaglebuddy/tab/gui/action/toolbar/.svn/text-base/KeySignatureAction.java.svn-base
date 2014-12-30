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
import com.beaglebuddy.tab.gui.dialog.KeySignatureDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.KeySignature;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import java.awt.event.ActionEvent;




/**
 * called when the user clicks the <i>Key Signature</i> toolbar icon.
 */
public class KeySignatureAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public KeySignatureAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * displays the barline dialog box and lets the user select which type of barline they want to mark the end of the measure.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
     */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the barline at the current location
      Location location  = frame.getCurrentLocation();
      Section  section   = frame.getSong().getScore().getSections().get(location.getSection());
      Barline  barline   = section.getBarlines().get(location.getBarline());

      // get the key signature there
      KeySignature keySignature = barline.getKeySignature();

      // show the dialog and let the user choose a different key signature
      KeySignatureDialog dialog = new KeySignatureDialog(frame, keySignature);
      dialog.setVisible(true);

      // if the user pressed ok, then save the user's selected key signature the song
      if (dialog.wasOKed())
      {
         barline.setKeySignature(dialog.getKeySignature());
      }
   }
}
