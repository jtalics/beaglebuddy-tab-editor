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
import com.beaglebuddy.tab.gui.font.BeaglebuddyFont;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.attribute.note.Octave;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;




/**
 * This class is a dialog box which allows a user to specify an octave.
 */
public class OctaveDialog extends BaseDialog
{
   // data members
   private Octave octave;

   // controls
   JRadioButton radioButtonNone;
   JRadioButton radioButton8va;
   JRadioButton radioButton8vb;
   JRadioButton radioButton15ma;
   JRadioButton radioButton15mb;
   ButtonGroup  buttonGroup;




   /**
    * constructor.
    * <br/><br/>
    * @param frame    the main application frame.
    * @param octave   the octave to be edited.
    */
   public OctaveDialog(MainFrame frame, Octave octave)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.octave");

      // make a copy of the octave the user will be editing
      this.octave = (octave == null ? null : (Octave)octave.clone());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.octave.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      radioButtonNone = new JRadioButton(ResourceBundle.getString("text.none"));
      radioButton8va  = new JRadioButton(BeaglebuddyFont.getCodePoint(BeaglebuddyFont.OCTAVE_8VA ));
      radioButton8vb  = new JRadioButton(BeaglebuddyFont.getCodePoint(BeaglebuddyFont.OCTAVE_8VB ));
      radioButton15ma = new JRadioButton(BeaglebuddyFont.getCodePoint(BeaglebuddyFont.OCTAVE_15MA));
      radioButton15mb = new JRadioButton(BeaglebuddyFont.getCodePoint(BeaglebuddyFont.OCTAVE_15MB));

      radioButtonNone.setActionCommand("none");
      radioButton8va .setActionCommand("8va" );
      radioButton8vb .setActionCommand("8vb" );
      radioButton15ma.setActionCommand("15ma");
      radioButton15mb.setActionCommand("15mb");

      // set the font for the octave types
      Font font = BeaglebuddyFont.getFont(20);
      radioButton8va .setFont(font);
      radioButton8vb .setFont(font);
      radioButton15ma.setFont(font);
      radioButton15mb.setFont(font);

      // set the tooltips
      radioButtonNone.setToolTipText(ResourceBundle.getString("dialog.octave.none.tooltip"));
      radioButton8va .setToolTipText(ResourceBundle.getString("dialog.octave.8va.tooltip" ));
      radioButton8vb .setToolTipText(ResourceBundle.getString("dialog.octave.8vb.tooltip" ));
      radioButton15ma.setToolTipText(ResourceBundle.getString("dialog.octave.15ma.tooltip"));
      radioButton15mb.setToolTipText(ResourceBundle.getString("dialog.octave.15mb.tooltip"));

      // create the button group that groups the radio buttons together
      buttonGroup = new ButtonGroup();
      buttonGroup.add(radioButtonNone);
      buttonGroup.add(radioButton8va );
      buttonGroup.add(radioButton8vb );
      buttonGroup.add(radioButton15ma);
      buttonGroup.add(radioButton15mb);

      // set the initially selected radio button
           if (octave                 == null                        ) radioButtonNone.setSelected(true);
      else if (octave.getOctaveType() == Octave.OctaveType.Octave8va ) radioButton8va .setSelected(true);
      else if (octave.getOctaveType() == Octave.OctaveType.Octave8vb ) radioButton8vb .setSelected(true);
      else if (octave.getOctaveType() == Octave.OctaveType.Octave15ma) radioButton15ma.setSelected(true);
      else if (octave.getOctaveType() == Octave.OctaveType.Octave15mb) radioButton15mb.setSelected(true);

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
               .addComponent(radioButtonNone)
               .addComponent(radioButton8va )
               .addComponent(radioButton8vb )
               .addComponent(radioButton15ma)
               .addComponent(radioButton15mb))
            .addContainerGap(14, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {radioButtonNone, radioButton8va, radioButton8vb, radioButton15ma, radioButton15mb});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(radioButtonNone)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButton8va )
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButton8vb )
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButton15ma)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButton15mb)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {radioButtonNone, radioButton8va, radioButton8vb, radioButton15ma, radioButton15mb});

      // invoke the layout manager
      pack();
   }

   /**
    * @return the octave the user selected.
    */
   public Octave getOctave()
   {
      return octave;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
           if (radioButtonNone  .isSelected()) octave = null;
      else if (radioButton8va   .isSelected()) octave = new Octave(Octave.OctaveType.Octave8va );
      else if (radioButton8vb   .isSelected()) octave = new Octave(Octave.OctaveType.Octave8vb );
      else if (radioButton15ma  .isSelected()) octave = new Octave(Octave.OctaveType.Octave15ma);
      else if (radioButton15mb  .isSelected()) octave = new Octave(Octave.OctaveType.Octave15mb);
   }
}
