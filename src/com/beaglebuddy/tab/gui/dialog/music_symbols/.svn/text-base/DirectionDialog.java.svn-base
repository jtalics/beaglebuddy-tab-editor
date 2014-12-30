/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.music_symbols;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Direction;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;




/**
 * This class is a dialog box which allows a user to set the tempo of the song.
 */
public class DirectionDialog extends BaseDialog
{
   // class members

   // data members
   private ArrayList<Direction> directions;         // the directions for being edited

   // dialog box controls





   /**
    * constructor.
    * <br/><br/>
    * @param frame        the main application frame.
    * @param directions   the list of directions to edit.
    */
   public DirectionDialog(MainFrame frame, ArrayList<Direction> directions)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.direction");

      // save the directions being edited
      this.directions = directions;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.direction.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JPanel controlsPanel = getControlsPanel();

      // create a border
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));


      // layout the controls panel
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);


      // invoke the layout manager
      pack();
   }

   /**
    * @return the tempo the user selected.
    */
   public ArrayList<Direction> getDirections()
   {
      return directions;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {

   }
}
