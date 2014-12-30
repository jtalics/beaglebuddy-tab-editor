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
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;




/**
 * This class is a dialog box which allows a user to select a type of barline.
 */
public class BarlineDialog extends BaseDialog
{
   // class members
   private static final int       NUM_BUTTONS             = 7;
   private static final Dimension BUTTON_SIZE             = new Dimension(32, 32);   // height and width in pixels of each button
   private static final int       IMAGE_PANEL_WIDTH       = NUM_BUTTONS * 32;        // width of the image panel

   private static final int       BUTTON_NONE             = 0;
   private static final int       BUTTON_BAR              = 1;
   private static final int       BUTTON_DOUBLE_BAR       = 2;
   private static final int       BUTTON_DOUBLE_BAR_FINE  = 3;
   private static final int       BUTTON_REPEAT_START     = 4;
   private static final int       BUTTON_REPEAT_END       = 5;
   private static final int       BUTTON_REPEAT_END_START = 6;

   // data members
   private Barline     barline;      // the barline being edited
   private boolean     canDelete;    // whether the user can delete the barline

   // dialog box controls
   private JPanel      imagesPanel;
   private JPanel      numRepeatsPanel;
   private ButtonGroup buttonGroup;
   private JButton[]   buttons;
   private JLabel      labelPlay;
   private JLabel      labelTimes;
   private JSpinner    spinnerNumRepeats;
   private String[]    names;




   /**
    * constructor.
    * The <i>can delete</i> parameter specifies whether or not the user can delete the barline.
    * There are two cases where deletion of the barline should be prevented:
    * <ol>
    *    <li>the user is adding a new barline, and thus there is no existing barline for the user to delete.</li>
    *    <li>the user is editing the barline that begins a staff or ends a staff.  these barlines are required and can not be deleted. they can only be edited.</li>
    * </ol>
    * <br/><br/>
    * @param frame       the main application frame.
    * @param barline     the barline to edit.  if the user is creating a new barline, this will be null.
    * @param canDelete   whether the user can delete the barline
    */
   public BarlineDialog(MainFrame frame, Barline barline, boolean canDelete)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.barline");

      // save the barline being edited, and whether or not it can be deleted
      this.barline   = barline;
      this.canDelete = canDelete;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.barline.title"));
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
      Border buttonBorder = BorderFactory.createLineBorder(Color.BLACK);

      // create the buttons
      buttonGroup    = new ButtonGroup();
      buttons        = new JButton[NUM_BUTTONS];
      names          = new String [NUM_BUTTONS];
      String[] names = {"none", "bar", "double_bar", "double_bar_fine", "repeat_start", "repeat_end", "repeat_end_start"};


      for(int i=0; i<NUM_BUTTONS; ++i)
      {
         this.names[i] = names[i];
         buttons[i] = new JButton();
         buttons[i].setIcon        (new ImageIcon(getClass().getResource(ResourceBundle.getString("dialog.barline.button.icon."    + names[i] + ".unselected"))));
         buttons[i].setSelectedIcon(new ImageIcon(getClass().getResource(ResourceBundle.getString("dialog.barline.button.icon."    + names[i] + ".selected"  ))));
         buttons[i].setToolTipText(                                      ResourceBundle.getString("dialog.barline.button.tooltip." + names[i]                ));
         buttons[i].setActionCommand (names[i]);
         buttons[i].addActionListener(this);
//       buttons[i].setBorder        (buttonBorder);
         buttons[i].setMaximumSize   (BUTTON_SIZE);
         buttons[i].setMinimumSize   (BUTTON_SIZE);
         buttons[i].setPreferredSize (BUTTON_SIZE);
         buttonGroup.add(buttons[i]);
      }

      // create the labels and spinner for the number repeats
      labelPlay         = new JLabel(ResourceBundle.getString("dialog.barline.label.play" ));
      labelTimes        = new JLabel(ResourceBundle.getString("dialog.barline.label.times"));
      spinnerNumRepeats = new JSpinner(new SpinnerNumberModel(Barline.MIN_REPEAT_COUNT, Barline.MIN_REPEAT_COUNT, Barline.MAX_REPEAT_COUNT, 1));
      spinnerNumRepeats.setEnabled(false);

      // if the user is not allowed to delete the barline, then disable the "none" button.
      if (!canDelete)
         buttons[BUTTON_NONE].setEnabled(false);

      // set the initially selected image and spinner value
      if (barline == null)
      {
         buttons[BUTTON_BAR].setSelected(true);
      }
      else
      {
         buttons[barline.getType().ordinal()+1].setSelected(true);
         buttonGroup.setSelected(buttons[barline.getType().ordinal()+1].getModel(), true);

         if (barline.getType() == Barline.Type.RepeatEnd || barline.getType() == Barline.Type.RepeatEndStart)
         {
            spinnerNumRepeats.setValue(new Integer(barline.getNumRepeats()));
            spinnerNumRepeats.setEnabled(true);
         }
      }

      // create the panels
      imagesPanel     = new JPanel();
      numRepeatsPanel = new JPanel();

      // add the controls to their respective panels
      for(int i=0; i<NUM_BUTTONS; ++i)
         imagesPanel.add(buttons[i]);

      // layout the image panel
      imagesPanel.setLayout(new BoxLayout(imagesPanel, BoxLayout.LINE_AXIS));

      // layout the num repeats panel
      GroupLayout groupLayout = new GroupLayout(numRepeatsPanel);
      numRepeatsPanel.setLayout(groupLayout);
      groupLayout.setHorizontalGroup(
         groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(groupLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(labelPlay)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(spinnerNumRepeats, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(labelTimes)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      groupLayout.setVerticalGroup(
         groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(groupLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelPlay)
               .addComponent(spinnerNumRepeats, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(labelTimes))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      // layout the controls panel
      GroupLayout  layout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(imagesPanel    , IMAGE_PANEL_WIDTH         , IMAGE_PANEL_WIDTH       , Short.MAX_VALUE)
         .addComponent(numRepeatsPanel, GroupLayout.DEFAULT_SIZE  , GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(imagesPanel    , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(numRepeatsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      // invoke the layout manager
      pack();

      // set the button which will have the initial focus
      buttons[barline == null ? BUTTON_BAR : barline.getType().ordinal()+1].requestFocusInWindow();
   }

   /**
    * implements the ActionListener interface.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String command = event.getActionCommand();

      if (command.equals("none")             || command.equals("bar")              || command.equals("double_bar")       || command.equals("double_bar_fine")  ||
          command.equals("repeat_start")     || command.equals("repeat_end")       || command.equals("repeat_end_start"))
      {
         for(int i=0; i<NUM_BUTTONS; ++i)
            buttons[i].setSelected(command.equals(names[i]));

         spinnerNumRepeats.setEnabled(command.equals("repeat_end") || command.equals("repeat_end_start"));
      }
      else
      {
         // see if the user has chosen to delete the barline
         if (command.equals("ok") && buttons[BUTTON_NONE].isSelected())
         {
            // see if the user has any additional data associated with the barline
            if (barline.getAlternateEnding() != null || barline.getRehearsalSign() != null || barline.getTempoMarker() != null || barline.getDirections().size() != 0 ||
                barline.getVolumes().size()  != 0    || barline.getInstrumentIns().size() != 0)
            {
               String associatedObjects = "tempo marker";

               if (JOptionPane.showConfirmDialog(null, ResourceBundle.format("dialog.barline.delete_warning_message", associatedObjects, associatedObjects),
                   ResourceBundle.getString("gui.text.dialog_title.warning"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION)
               {
                  // do nothing - just continue with the barline dialog
                  return;
               }
            }
         }
         super.actionPerformed(event);
      }
   }

   /**
    * @return the barline the user selected.
    */
   public Barline getBarline()
   {
      return barline;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      if (buttons[BUTTON_NONE].isSelected())
      {
         barline = null;
      }
      else
      {
         // if the user chose to delete the barline
         if (buttons[BUTTON_NONE].isSelected())
         {
            barline = null;
         }
         else
         {
            // if the user is adding a new barline, then create a default one
            if (barline == null)
               barline = new Barline();

            // set the barline's type
            for(int i=BUTTON_BAR; i<NUM_BUTTONS; ++i)
               if (buttons[i].isSelected())
                  barline.setType(Barline.getType(i-1));

            // set the number of repeats for the barline
            barline.setNumRepeats(buttons[BUTTON_REPEAT_END].isSelected() || buttons[BUTTON_REPEAT_END_START].isSelected() ? (byte)(((Integer)spinnerNumRepeats.getValue()).intValue()) : (byte)0);
         }
      }
   }
}
