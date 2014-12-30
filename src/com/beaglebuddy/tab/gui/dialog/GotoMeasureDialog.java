/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;



/**
 * This class is a dialog box which allows a user to go to a specific measure in the song.
 */
public class GotoMeasureDialog extends BaseDialog
{
   // data members
   private int measure;             // the measure the user wants to go to.
   private int numMeasuresInSong;   // the number of measures in the song.

   // controls
   private JLabel   label;
   private JSpinner spinner;





   /**
    * constructor.
    * <br/><br/>
    * @param frame               the main application frame.
    * @param numMeasuresInSong   the number of measures in the song.
    */
   public GotoMeasureDialog(MainFrame frame, int measure, int numMeasuresInSong)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.goto.measure");

      // save the current measure the user is in and the total number of measures in the song
      this.measure           = measure;
      this.numMeasuresInSong = numMeasuresInSong;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.goto.measure.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      // create the midi device label
      label   = new JLabel(ResourceBundle.getString("dialog.goto.measure.label.measure"));
      spinner = new JSpinner(new SpinnerNumberModel(measure, 1, numMeasuresInSong, 1));

      spinner.setToolTipText(ResourceBundle.getString("dialog.goto.measure.tooltip.measure"));
      // disable keyboard editor in the spinner
      JFormattedTextField tf = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
      tf.setEditable(false);
      // the previous call sets the background to a disabled color (usually gray). to change this disabled color, reset the background color.
      tf.setBackground(Color.white);



      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(label)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(label)
               .addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      // invoke the layout manager
      pack();
  }

   /**
    * @return the measure the user wants to go to.
    */
   public int getMeasure()
   {
      return measure;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      measure = ((Integer)spinner.getValue()).intValue();
   }
}
