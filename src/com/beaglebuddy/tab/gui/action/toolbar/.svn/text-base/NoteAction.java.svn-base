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
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Location;
import java.awt.event.ActionEvent;




/**
 * called when the user selects one of the <i>note</i> icons from the <i>note</i> toolbar.
 */
public class NoteAction extends BaseAction
{
   // class members
   public static final String WHOLE         = "whole";
   public static final String HALF          = "half";
   public static final String QUARTER       = "quarter";
   public static final String EIGTH         = "eigth";
   public static final String SIXTEENTH     = "sixteenth";
   public static final String THIRTY_SECOND = "thirty_second";
   public static final String DOTTED        = "dotted";
   public static final String DOUBLE_DOTTED = "double_dotted";




   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public NoteAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * toggle the type of note to the song at the current location.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String   command  = event.getActionCommand();
      Location location = frame.getCurrentLocation();

      // todo: toggle the note to the song at the current location.

      super.actionPerformed(event);
   }
}
