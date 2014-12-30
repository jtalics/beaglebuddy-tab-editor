/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.tools;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import javax.swing.JOptionPane;



/**
 * called when the user selects the <i>Validate</i> menu item from the <i>Tools</i> menu.
 */
public class ScoreValidateAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public ScoreValidateAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * validates the song, verifying that:
    * <ol>
    *    <li>each measure has the correct duration according to its time signatue</li>
    *    <li>each repeat bar line has a matching begin and end</li>
    *    <li>todo: other checks</li>
    * </ol>
    * An error dialog is displayed if any errors are found, indicating the section, measure, and staff where the error(s) occurred,
    * analgous to a compiler output.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // validate the song
      Hashtable<String, Location> errors = frame.getSong().validate();

      // display any errors found
      if (errors.size() == 0)
      {
         JOptionPane.showMessageDialog(null, ResourceBundle.getString("gui.text.song_validate.valid"), ResourceBundle.getString("menu.tool.score.validate.text"), JOptionPane.INFORMATION_MESSAGE);
      }
      else
      {
         // todo: show a dialog box with the errors that were found
         super.actionPerformed(event);
      }
   }
}
