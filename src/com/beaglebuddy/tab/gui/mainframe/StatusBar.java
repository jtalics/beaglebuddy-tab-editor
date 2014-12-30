/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;




/**
 * This class displays the current location (section, staff, measure, position) within the song that is being edited.
 */
@SuppressWarnings("serial")
public class StatusBar extends JPanel implements CursorListener
{
   // data members
   Location location;       // the current location within the song that is being edited.
   JLabel   label;          // text that is displayed to the user



   /**
    * default constructor.
    */
   public StatusBar(Location initialLocation)
   {
      location = initialLocation;

      // get the text for the location labels
      label = new JLabel();
      label.setText(getText());
      label.setHorizontalAlignment(JLabel.LEFT);
//    label.setBorder(new BevelBorder(BevelBorder.LOWERED));
      label.setToolTipText(ResourceBundle.getString("status_bar_tooltip"));
      label.setForeground(Color.black);

      // lay out the components
      setLayout(new BorderLayout());
      add(label, BorderLayout.EAST);
   }

   /**
    * implements the LocationListener interface.
    * this method is invoked whenever the cursor is moved or an object is highlighted in the song, and it simply updates the status bar and has it redraw itself.
    * <br/><br/>
    * @param location   the location of the cursor within the song.
    */
   public void cursor(Location location)
   {
      this.location = location;
      label.setText(getText());
      repaint();
   }

   /**
    * @return the current location as text that can be displayed to the user.
    */
   private String getText()
   {
      return ResourceBundle.format("status_bar_label", location.getSection()+1, location.getStaff()+1, location.getMeasure()+1, location.getPosition());
   }
}
