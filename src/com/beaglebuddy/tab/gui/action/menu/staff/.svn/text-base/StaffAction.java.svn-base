/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.staff;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.model.staff.BassStaff;
import com.beaglebuddy.tab.model.staff.DrumStaff;
import com.beaglebuddy.tab.model.staff.KeyboardStaff;
import com.beaglebuddy.tab.model.staff.RhythmStaff;
import com.beaglebuddy.tab.model.staff.Staff;
import com.beaglebuddy.tab.model.staff.TrebleStaff;
import java.awt.event.ActionEvent;
import java.util.ArrayList;




/**
 * called when the user selects one of the menu items <i>Add Staff</i>, <i>Delete Staff</i>, <i>Move Staff Up</i>, or <i>Move Staff Down</i> from the <i>Staff</i> menu.
 */
public class StaffAction extends BaseAction
{
   // class members
   public static final String ADD          = "add_";
   public static final String ADD_BASS     = "add_bass";
   public static final String ADD_DRUM     = "add_drum";
   public static final String ADD_KEYBOARD = "add_keyboard";
   public static final String ADD_RHYTHM   = "add_rhythm";
   public static final String ADD_TAB      = "add_tab";
   public static final String DELETE       = "delete";
   public static final String MOVE_UP      = "move_up";
   public static final String MOVE_DOWN    = "move_down";



   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public StaffAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * perform the selected staff operation.
    * <br/><br/>
    * @param event   menu choice event.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // determine which "staff" menu the user selected
      String command = event.getActionCommand();

      // get the current section
      Song             song     = frame.getSong();
      Location         location = frame.getCurrentLocation();
      Section          section  = song.getScore().getSections().get(location.getSection());
      ArrayList<Staff> staffs   = section.getStaffs();


      if (command.startsWith(ADD))
      {
         // todo: look into refactoring RhythmStaff so that it is derived from Staff like all the other types - it may not be possible though
         Staff       staff       = null;
         RhythmStaff rhythmStaff = null;

              if (command.equals(ADD_BASS    )) staff       = new BassStaff();
         else if (command.equals(ADD_DRUM    )) staff       = new DrumStaff();
         else if (command.equals(ADD_KEYBOARD)) staff       = new KeyboardStaff();
         else if (command.equals(ADD_RHYTHM  )) rhythmStaff = new RhythmStaff();
         else if (command.equals(ADD_TAB     )) staff       = new TrebleStaff();

         if (command.equals(ADD_RHYTHM))
            section.setRhythmStaff(rhythmStaff);
         else
            section.getStaffs().add(location.getStaff() + 1, staff);
      }
      else if (command.equals(DELETE))
      {
         // remove the current staff
         section.getStaffs().remove(location.getStaff());
      }
      else if (command.equals(MOVE_UP))
      {
      }
      else if (command.equals(MOVE_DOWN))
      {
      }

      super.actionPerformed(event);
   }
}
