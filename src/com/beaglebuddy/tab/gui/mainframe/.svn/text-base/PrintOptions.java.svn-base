/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This bean class is used to hold the list of things a user wants to print.
 * <ol>
 *    <li>song\lesson</li>
 *    <li>performance notes</li>
 *    <li>tab legend</li>
 *    <li>drum map</li>
 * </ol>
 */
public class PrintOptions
{
   // data members
   private boolean songSelected;                   // whether to print out the song
   private boolean performanceNotesSelected;       // whether to print out any performance notes
   private boolean tabLegendSelected;              // whether to print out a legend of tab symbols
   private boolean drumMapSelected;                // whether to print out the drum map



   /**
    * default constructor.
    */
   public PrintOptions()
   {
      this.songSelected             = true;
      this.performanceNotesSelected = false;
      this.tabLegendSelected        = false;
      this.drumMapSelected          = false;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param songSelected                whether to print out the song.
    * @param performanceNotesSelected    whether to print out any performance notes.
    * @param tabLegendSelected           whether to print out a legend of tab symbols.
    * @param drumMapSelected             whether to print out the song's drum map (if it has one).
    */
   public PrintOptions(boolean songSelected, boolean performanceNotesSelected, boolean tabLegendSelected, boolean drumMapSelected)
   {
      this.songSelected             = songSelected;
      this.performanceNotesSelected = performanceNotesSelected;
      this.tabLegendSelected        = tabLegendSelected;
      this.drumMapSelected          = drumMapSelected;
   }

   /**
    * @return whether to print out the song.
    */
   public boolean isSongSelected()
   {
      return songSelected;
   }

   /**
    * @return whether to print out the performance notes.
    */
   public boolean isPerformanceNotesSelected()
   {
      return performanceNotesSelected;
   }

   /**
    * @return whether to print out a legend of tab symbols.
    */
   public boolean isTabLegendSelected()
   {
      return tabLegendSelected;
   }

   /**
    * @return whether to print out the drum map.
    */
   public boolean isDrumMapSelected()
   {
      return drumMapSelected;
   }

   /**
    * @return a string representation of the print options.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(Utility.pad(ResourceBundle.getString("dialog.print_options.label.song"             ), "") + songSelected             + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("dialog.print_options.label.performance_notes"), "") + performanceNotesSelected + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("dialog.print_options.label.tab_legend"       ), "") + tabLegendSelected        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("dialog.print_options.label.drum_map"         ), "") + drumMapSelected                );

      return buffer.toString();
   }
}
