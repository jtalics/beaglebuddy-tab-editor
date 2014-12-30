/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.mainframe.PrintOptions;
import com.beaglebuddy.tab.model.Information;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;




/**
 * This class is a dialog box which allows a user to choose what things will be printed.
 * <ol>
 *    <li>song\lesson</li>
 *    <li>performance notes</li>
 *    <li>tab legend</li>
 *    <li>drum map</li>
 * </ol>
 */
public class PrintOptionsDialog extends BaseDialog
{
   // data members
   private Song         song;                      // the song the user is going to print
   private PrintOptions printOptions;              // the things that will be printed.

   // dialog box controls
   private JCheckBox checkBoxDrumMap;
   private JCheckBox checkBoxPerformanceNotes;
   private JCheckBox checkBoxSong;
   private JCheckBox checkBoxTabLegend;




   /**
    * constructor.
    * <br/><br/>
    * @param frame         the main application frame.
    * @param printOptions  the current printer options.
    */
   public PrintOptionsDialog(MainFrame frame, PrintOptions printOptions)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.print_options");

      // save the song the user will be printing and the print options being edited
      this.song         = frame.getSong();
      this.printOptions = printOptions;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.print_options.title"));
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

      // create the check boxes
      Information information = song.getInformation();
      checkBoxSong = new JCheckBox();
      checkBoxSong.setText       (ResourceBundle.getString("dialog.print_options.label."   + (information.getFileType() == Information.FileType.Song ? "song" : "lesson")));
      checkBoxSong.setToolTipText(ResourceBundle.getString("dialog.print_options.tooltip." + (information.getFileType() == Information.FileType.Song ? "song" : "lesson")));
      checkBoxSong.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxSong.setMargin(new Insets(0, 0, 0, 0));
      checkBoxSong.setSelected(printOptions.isSongSelected());

      checkBoxPerformanceNotes = new JCheckBox();
      checkBoxPerformanceNotes.setText       (ResourceBundle.getString("dialog.print_options.label.performance_notes"));
      checkBoxPerformanceNotes.setToolTipText(ResourceBundle.getString("dialog.print_options.tooltip.performance_notes"));
      checkBoxPerformanceNotes.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxPerformanceNotes.setMargin(new Insets(0, 0, 0, 0));
      checkBoxPerformanceNotes.setSelected(printOptions.isPerformanceNotesSelected());
      checkBoxPerformanceNotes.setEnabled(information.getPerformanceNotes() != null && information.getPerformanceNotes().trim().length() != 0);

      checkBoxTabLegend = new JCheckBox();
      checkBoxTabLegend.setText       (ResourceBundle.getString("dialog.print_options.label.tab_legend"));
      checkBoxTabLegend.setToolTipText(ResourceBundle.getString("dialog.print_options.tooltip.tab_legend"));
      checkBoxTabLegend.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxTabLegend.setMargin(new Insets(0, 0, 0, 0));
      checkBoxTabLegend.setSelected(printOptions.isTabLegendSelected());

      checkBoxDrumMap = new JCheckBox();
      checkBoxDrumMap.setText       (ResourceBundle.getString("dialog.print_options.label.drum_map"));
      checkBoxDrumMap.setToolTipText(ResourceBundle.getString("dialog.print_options.tooltip.drum_map"));
      checkBoxDrumMap.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      checkBoxDrumMap.setMargin(new Insets(0, 0, 0, 0));
      checkBoxDrumMap.setSelected(printOptions.isDrumMapSelected());
      checkBoxDrumMap.setEnabled(song.getScore().hasInstrument(Instrument.Type.Drums));

      // layout the controls panel
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(checkBoxSong)
                .addComponent(checkBoxPerformanceNotes)
                .addComponent(checkBoxTabLegend)
                .addComponent(checkBoxDrumMap))
            .addContainerGap(20, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(checkBoxSong)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkBoxPerformanceNotes)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkBoxTabLegend)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkBoxDrumMap))
      );

      // invoke the layout manager
      pack();
   }

   /**
    * @return the print options the user selected.
    */
   public PrintOptions getPrintOptions()
   {
      return printOptions;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      printOptions = new PrintOptions(checkBoxSong.isSelected(), checkBoxPerformanceNotes.isSelected(), checkBoxTabLegend.isSelected(), checkBoxDrumMap.isSelected());
   }
}
