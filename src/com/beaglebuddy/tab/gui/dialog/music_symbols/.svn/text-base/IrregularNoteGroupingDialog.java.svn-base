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
import com.beaglebuddy.tab.model.attribute.duration.IrregularGrouping;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;




/**
 * This class is a dialog box which allows a user to specify an irregular note grouping for a sequence of notes.
 * ex: playing 6 notes over the space of 4 sixteenth notes - 6:4
 */
public class IrregularNoteGroupingDialog extends BaseDialog
{
   // data members
   private IrregularGrouping irregularGrouping;

   // controls
   JSpinner numNotesPlayedSpinner;
   JSpinner numNotesPlayedOverSpinner;




   /**
    * constructor.
    * <br/><br/>
    * @param frame               the main application frame.
    * @param irregularGrouping   the irregular grouping (tuplet) to be edited.
    */
   public IrregularNoteGroupingDialog(MainFrame frame, IrregularGrouping irregularGrouping)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.irregular_note_grouping");

      // save the values the user will be editing
      this.irregularGrouping = irregularGrouping == null ? new IrregularGrouping() : irregularGrouping;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.irregular_note_grouping.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      // create the labels
      JLabel numNotesPlayedLabel     = new JLabel(ResourceBundle.getString("dialog.irregular_note_grouping.num_notes_played.label"));
      JLabel numNotesPlayedOverLabel = new JLabel(ResourceBundle.getString("dialog.irregular_note_grouping.num_notes_played_over.label"));

      // create the spinners
      numNotesPlayedSpinner     = new JSpinner(new SpinnerNumberModel(irregularGrouping.getNumNotesPlayed    (), IrregularGrouping.MIN_NUM_NOTES_PLAYED     , IrregularGrouping.MAX_NUM_NOTES_PLAYED     , 1));
      numNotesPlayedOverSpinner = new JSpinner(new SpinnerNumberModel(irregularGrouping.getNumNotesPlayedOver(), IrregularGrouping.MIN_NUM_NOTES_PLAYED_OVER, IrregularGrouping.MAX_NUM_NOTES_PLAYED_OVER, 1));
      numNotesPlayedSpinner    .setToolTipText(ResourceBundle.getString("dialog.irregular_note_grouping.num_notes_played.tooltip"     ));
      numNotesPlayedOverSpinner.setToolTipText(ResourceBundle.getString("dialog.irregular_note_grouping.num_notes_played_over.tooltip"));

      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(numNotesPlayedLabel)
               .addComponent(numNotesPlayedOverLabel))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
               .addComponent(numNotesPlayedSpinner    )
               .addComponent(numNotesPlayedOverSpinner))
            .addContainerGap())
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {numNotesPlayedLabel  , numNotesPlayedOverLabel  });
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {numNotesPlayedSpinner, numNotesPlayedOverSpinner});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(numNotesPlayedLabel)
               .addComponent(numNotesPlayedSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(numNotesPlayedOverLabel)
               .addComponent(numNotesPlayedOverSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {numNotesPlayedLabel, numNotesPlayedOverLabel, numNotesPlayedSpinner, numNotesPlayedOverSpinner});

      // invoke the layout manager
      pack();
  }

   /**
    * @return the new value the user has set for the irregular grouping.
    */
   public IrregularGrouping getIrregularGrouping()
   {
      return irregularGrouping;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      irregularGrouping.setNumNotesPlayed    ((byte)(((Integer)numNotesPlayedSpinner    .getValue()).intValue()));
      irregularGrouping.setNumNotesPlayedOver((byte)(((Integer)numNotesPlayedOverSpinner.getValue()).intValue()));
   }
}
