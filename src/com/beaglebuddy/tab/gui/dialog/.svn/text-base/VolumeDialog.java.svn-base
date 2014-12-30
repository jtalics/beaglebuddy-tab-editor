/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import com.beaglebuddy.tab.gui.font.BeaglebuddyFont;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Volume;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;





/**
 * This class is a dialog box which allows a user to select a volume for the instruments assigned to a staff.
 */
public class VolumeDialog extends BaseDialog
{
   // class members
   private static final int            NUM_BUTTONS             = 9;
   private static final Dimension      BUTTON_SIZE             = new Dimension(32, 32);   // height and width in pixels of each button

   private static final int            BUTTON_NONE             = 0;
   private static final int            BUTTON_PPP              = 1;
   private static final int            BUTTON_PP               = 2;
   private static final int            BUTTON_P                = 3;
   private static final int            BUTTON_MP               = 4;
   private static final int            BUTTON_MF               = 5;
   private static final int            BUTTON_F                = 6;
   private static final int            BUTTON_FF               = 7;
   private static final int            BUTTON_FFF              = 8;

   private static final String[]       commands = {"none", "ppp", "pp", "p", "mp", "mf", "f", "ff", "fff"};
   private static final Volume.Level[] levels   = {Volume.Level.Off, Volume.Level.Ppp, Volume.Level.Pp, Volume.Level.P, Volume.Level.Mp, Volume.Level.Mf, Volume.Level.F, Volume.Level.Ff, Volume.Level.Fff};
   private static final String[]       text     = {"",
                                                   BeaglebuddyFont.getCodePoint(BeaglebuddyFont.VOLUME_PPP), BeaglebuddyFont.getCodePoint(BeaglebuddyFont.VOLUME_PP), BeaglebuddyFont.getCodePoint(BeaglebuddyFont.VOLUME_P  ),
                                                   BeaglebuddyFont.getCodePoint(BeaglebuddyFont.VOLUME_MP ), BeaglebuddyFont.getCodePoint(BeaglebuddyFont.VOLUME_MF),
                                                   BeaglebuddyFont.getCodePoint(BeaglebuddyFont.VOLUME_F  ), BeaglebuddyFont.getCodePoint(BeaglebuddyFont.VOLUME_FF), BeaglebuddyFont.getCodePoint(BeaglebuddyFont.VOLUME_FFF)};


   // data members
   private Volume      volume;         // the volume being edited

   // dialog box controls
   private ButtonGroup buttonGroup;
   private JButton[]   buttons;




   /**
    * constructor.
    * The <i>can delete</i> parameter specifies whether or not the user can delete the barline.
    * There are two cases where deletion of the barline should be prevented:
    * <ol>
    *    <li>the user is adding a new barline, and thus there is no existing barline for the user to delete.</li>
    *    <li>the user is editing the barline that begins a staff or ends a staff.  these barlines are required and can not be deleted. they can only be edited.</li>
    * </ol>
    * <br/><br/>
    * @param frame    the main application frame.
    * @param volume   the volume to edit.  if the user is creating a new volume, this will be null.
    */
   public VolumeDialog(MainFrame frame, Volume volume)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.volume");

      // save the volume being edited
      this.volume = volume;

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.volume.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
	  JPanel controlsPanel = getControlsPanel();

      // create a border for the panel
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      // create the buttons
      Font font   = BeaglebuddyFont.getFont(32);
      buttonGroup = new ButtonGroup();
      buttons     = new JButton[NUM_BUTTONS];


      for(int i=0; i<NUM_BUTTONS; ++i)
      {
         buttons[i] = new JButton();
         buttons[i].setMargin(new Insets(0,0,15,0));
         buttons[i].setText          (text[i]);
         buttons[i].setToolTipText   (ResourceBundle.getString("dialog.volume.button.tooltip." + commands[i]));
         buttons[i].setActionCommand (commands[i]);
         buttons[i].addActionListener(this);
         buttons[i].setMaximumSize   (BUTTON_SIZE);
         buttons[i].setMinimumSize   (BUTTON_SIZE);
         buttons[i].setPreferredSize (BUTTON_SIZE);
         if (i != 0)
            buttons[i].setFont(font);
         buttonGroup.add(buttons[i]);
      }

      // set the initially selected image
      if (volume == null)
      {
         buttons[BUTTON_P].setSelected(true);
      }
      else
      {
         buttons[volume.getLevel().ordinal()].setSelected(true);
         buttonGroup.setSelected(buttons[volume.getLevel().ordinal()].getModel(), true);
      }

      // add the controls to their respective panels
      for(int i=0; i<NUM_BUTTONS; ++i)
         controlsPanel.add(buttons[i]);

      // layout the controls panel
      controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.LINE_AXIS));

      // invoke the layout manager
      pack();

      // set the button which will have the initial focus
      buttons[volume == null ? BUTTON_P : volume.getLevel().ordinal()].requestFocusInWindow();
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

      if (command.equals("none") ||
          command.equals("ppp")  || command.equals("pp"  ) || command.equals("p")    || command.equals("mp") ||
          command.equals("fff")  || command.equals("ff"  ) || command.equals("f")    || command.equals("mf"))
      {
         for(int i=0; i<NUM_BUTTONS; ++i)
            buttons[i].setSelected(command.equals(commands[i]));
      }
      else
      {
         super.actionPerformed(event);
      }
   }

   /**
    * @return the volume the user selected.
    */
   public Volume getVolume()
   {
      return volume;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // if the user chose to delete the volume
      if (buttons[BUTTON_NONE].isSelected())
      {
         volume = null;
      }
      else
      {
         // if the user is adding a new barline, then create a default one
         if (volume == null)
            volume = new Volume();

         for(int i=0; i<NUM_BUTTONS; ++i)
         {
            if (buttons[i].isSelected())
            {
               volume.setLevel(levels[i]);
               break;
            }
         }
      }
   }
}
