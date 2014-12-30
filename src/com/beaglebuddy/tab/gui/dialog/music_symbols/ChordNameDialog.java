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
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.attribute.chord.ChordName;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;




/**
 * This class is a dialog box which allows a user to specify the name of a chord.
 */
public class ChordNameDialog extends BaseDialog
{
   // data members
   private ChordName chordName;       // name of the chord to edit
   private String[]  chordNames;      // list of chord names the user can choose from

   // dialog box controls
   private JLabel    chordNameLabel;
   private JComboBox chordNameComboBox;





   /**
    * constructor.
    * <br/><br/>
    * @param frame          the main application frame.
    * @param chordName      the name of the chord to edit.
    * @param chordDiagrams  list of chord diagrams defined for the song from which the user can choose.
    */
   public ChordNameDialog(MainFrame frame, ChordName chordName, ArrayList<ChordDiagram> chordDiagrams)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.chord_name");

      // save the chord name being edited
      this.chordName = chordName == null ? new ChordName() : chordName;

      // get a list of chord names from the chord diagrams
      chordNames = new String[chordDiagrams.size()];
      for(int i=0; i<chordDiagrams.size(); ++i)
         chordNames[i] = chordDiagrams.get(i).getName();

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.chord_name.title"));
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

      // create the controls
      chordNameLabel    = new JLabel(ResourceBundle.getString("dialog.chord_name.label"));
      chordNameComboBox = new JComboBox(chordNames);
      chordNameComboBox.setEditable(false);
      chordNameComboBox.setToolTipText(ResourceBundle.getString("dialog.chord_name.tooltip"));
      chordNameComboBox.setSelectedItem(chordName.getName());

      // layout the controls panel
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(chordNameLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(chordNameComboBox, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(chordNameLabel)
               .addComponent(chordNameComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      // invoke the layout manager
      pack();
   }

   /**
    * @return the chord name the user selected.
    */
   public ChordName getChordName()
   {
      return chordName;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // retrieve the selected chord name
      chordName.setName((String)chordNameComboBox.getSelectedItem());
   }
}
