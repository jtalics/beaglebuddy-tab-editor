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
import java.awt.event.ActionEvent;
import java.util.ArrayList;




/**
 * called when the user selects one of the menu items <i>Add Section</i>, <i>Delete Section</i>, <i>Move Section Up</i>, or <i>Move Section Down</i> from the <i>Staff</i> menu.
 */
public class SectionAction extends BaseAction
{
   // class members
   public static final String ADD        = "add";
   public static final String DELETE     = "delete";
   public static final String MOVE_UP    = "move_up";
   public static final String MOVE_DOWN  = "move_down";



   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public SectionAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * perform the selected section operation.
    * <br/><br/>
    * @param event   menu choice event.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // determine which "section" menu the user selected
      String command = event.getActionCommand();

      // get the current section
      Song               song     = frame.getSong();
      Location           location = frame.getCurrentLocation();
      ArrayList<Section> sections = song.getScore().getSections();

      if (command.equals(ADD))
      {
         sections.add(location.getSection() + 1, new Section());
      }
      else if (command.equals(DELETE))
      {
         sections.remove(location.getSection());
      }
      else if (command.equals(MOVE_UP))
      {
         Section section = sections.get(location.getSection());
         sections.remove(location.getSection());
         sections.add   (location.getSection() - 1, section);
      }
      else if (command.equals(MOVE_DOWN))
      {
         Section section = sections.get(location.getSection());
         sections.remove(location.getSection());
         sections.add   (location.getSection() + 1, section);
      }
   }
}
