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
import com.beaglebuddy.tab.model.RehearsalSign;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;




/**
 * This class is a dialog box which allows a user to set a rehearsal sign at the curent barline in the song.
 */
public class RehearsalSignDialog extends BaseDialog
{
   // class members
   private static final int LETTER_COMBOX_BOX_WIDTH   = 100;

   // data members
   private RehearsalSign     rehearsalSign;              // the rehearsal sign being edited
   private Vector<Character> lettersInUse;               // letters already taken by other rehearsal signs

   // dialog box controls
   private JLabel    labelLetter;
   private JComboBox comboBoxLetter;
   private JLabel    labelDescription;
   private JComboBox comboBoxDescription;



   /**
    * constructor.
    * <br/><br/>
    * @param frame           the main application frame.
    * @param rehearsalSign   the rehearsal sign to edit.  if the user is creating a new rehearsal sign, this will be null.
    */
   public RehearsalSignDialog(MainFrame frame, RehearsalSign rehearsalSign, Vector<Character> lettersInUse)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.rehearsal_sign");

      // save the rehearsal sign being edited
      this.rehearsalSign = rehearsalSign;
      this.lettersInUse  = lettersInUse;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.rehearsal_sign.title"));
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

      // create the labels
      labelLetter      = new JLabel(ResourceBundle.getString("dialog.rehearsal_sign.label.letter"     ));
      labelDescription = new JLabel(ResourceBundle.getString("dialog.rehearsal_sign.label.description"));

      // create the letter combo box
      int               selected   = -1;
      Character[]       characters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
      Vector<Character> letters    = new Vector<Character>();

      for(Character character : characters)
         if (!lettersInUse.contains(character))
            letters.add(character);

      selected = rehearsalSign == null ? 0 : letters.indexOf(new Character(rehearsalSign.getLetter()));
      comboBoxLetter = new JComboBox(letters);
      comboBoxLetter.setSelectedIndex(selected);
      comboBoxLetter.setToolTipText(ResourceBundle.getString("dialog.rehearsal_sign.tooltip.letter"));

      // create the description combo box
      Vector<String> descriptions = new Vector<String>();
      String         description  = "";

      for(int i=0; description != null; ++i)
         if ((description = ResourceBundle.getString("dialog.rehearsal_sign.combo_box.description." + i)) != null)
            descriptions.add(description);

      // if this is a new rehearsal sign or there is no description
      if (rehearsalSign == null || rehearsalSign.getDescription() == null || rehearsalSign.getDescription().trim().length() == 0)
      {
         descriptions.add(0, "");
         selected = 0;
      }
      else
      {
         // if user chose one of the pre-defined descriptions
         if (descriptions.contains(rehearsalSign.getDescription()))
         {
            descriptions.add(0, "");
            selected = descriptions.indexOf(rehearsalSign.getDescription());
         }
         // user entered their own description
         else
         {
            descriptions.add(0, rehearsalSign.getDescription());
            selected = 0;
         }
      }
      comboBoxDescription = new JComboBox(descriptions);
      comboBoxDescription.setSelectedIndex(selected);
      comboBoxDescription.setEditable(true);
      comboBoxDescription.setToolTipText(ResourceBundle.getString("dialog.rehearsal_sign.tooltip.description"));

      // layout the controls panel
      GroupLayout layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(labelLetter)
                    .addComponent(labelDescription))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(comboBoxLetter     , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxDescription, 0                         , 212                     , Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLetter)
                    .addComponent(comboBoxLetter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDescription)
                    .addComponent(comboBoxDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

      // invoke the layout manager
      pack();
   }

   /**
    * @return the rehearsal sign the user constructed.
    */
   public RehearsalSign getRehearsalSign()
   {
      return rehearsalSign;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      rehearsalSign = new RehearsalSign(((Character)comboBoxLetter.getSelectedItem()).charValue(), (String)comboBoxDescription.getSelectedItem());
   }
}
