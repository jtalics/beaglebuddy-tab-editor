/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;





/**
 * This base dialog class standardizes the layout of all dialog boxes in the beaglebuddy tab application.
 * Namely, it forces all derived dialog boxes to:
 * <ul>
 *    <li>have a title.</li>
 *    <li>have their controls in the top half of the dialog box.</li>
 *    <li>have "ok", "cancel", and "help" buttons along the bottom of the dialog box.</li>
 *    <li>be modal (user must close the dialog box before doing anything else).</li>
 *    <li>be centered either in the screen or relative to the main application window.</li>
 * </ul>
 * Derived dialog classes should implement the <i>addComponents()</i> method which places the dialog controls in the top half of the dialog box
 * and the <i>ok()</i> method to retrieve the values the user entered into the dialog controls.
 * <p>
 * Building a dialog is a two step process.
 * 1. call the constructor.
 * 2. call the setVisible(true) method.
 * </p>
 */
public abstract class BaseDialog extends JDialog implements ActionListener
{
   // locations for centering the dialog
   public enum LocationCenter {SCREEN, PARENT}

   // data members
   private JPanel                  controlsPanel;             // panel where derived dialog boxes place their controls
   private OkCancelHelpButtonPanel okCancelHelpButtonPanel;   // panel at the bottom of the dialog box which contains the standard ok, cancel, and help buttons
   private String                  key;                       // key the user pressed: either "ok" or "cancel"




   /**
    * constructor.
    * <br/><br/>
    * @param dialog      the owner of this dialog box.
    * @param helpBroker  java help system manager.
    * @param helpSet     set of help files displayed by the help broker.
    * @param helpId      topic id of the help file to display when the user presses the F1 key or the <i>Help</i> button.
    */
   public BaseDialog(Dialog dialog, HelpBroker helpBroker, HelpSet helpSet, String helpId)
   {
      super(dialog);
      init(helpBroker, helpSet, helpId);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param parent      the parent window of the dialog box.
    * @param helpBroker  java help system manager.
    * @param helpSet     set of help files displayed by the help broker.
    * @param helpId      topic id of the help file to display when the user presses the F1 key or the <i>Help</i> button.
    */
   public BaseDialog(Frame parent, HelpBroker helpBroker, HelpSet helpSet, String helpId)
   {
      super(parent);
      init(helpBroker, helpSet, helpId);
   }

   /**
    * creates two panels, a top one containing the dialog controls and a bottom one containing the <i>ok</i>, <i>cancel</i>, and <i>help</i> buttons.
    * <br/><br/>
    * @param helpBroker  java help system manager.
    * @param helpSet     set of help files displayed by the help broker.
    * @param helpId      topic id of the help file to display when the user presses the F1 key or the <i>Help</i> button.
    */
   private void init(HelpBroker helpBroker, HelpSet helpSet, String helpId)
   {
      this.key = null;

      // create two panels for the dialog box
      controlsPanel           = new JPanel();
      okCancelHelpButtonPanel = new OkCancelHelpButtonPanel(this, helpBroker, helpId);

      // set the layout manager
      GroupLayout groupLayout = new GroupLayout(getContentPane());
      getContentPane().setLayout(groupLayout);

      // automatically place gaps between the components
      groupLayout.setAutoCreateGaps         (true);
      groupLayout.setAutoCreateContainerGaps(true);

      // layout the panels along the horizontal axis
      GroupLayout.ParallelGroup   horizontalGroup = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER );
      horizontalGroup.addComponent(controlsPanel          );
      horizontalGroup.addComponent(okCancelHelpButtonPanel);

      // layout the panels along the vertical axis
      GroupLayout.SequentialGroup verticalGroup = groupLayout.createSequentialGroup();
      verticalGroup  .addComponent(controlsPanel          );
      verticalGroup  .addComponent(okCancelHelpButtonPanel);

      // layout the panels in the dialog box
      groupLayout.setHorizontalGroup(horizontalGroup);
      groupLayout.setVerticalGroup  (  verticalGroup);

      // set some attributes of the dialog box
      setLocale(ResourceBundle.getCurrentLocale());
      setResizable(true);
      setModal(true);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

      // make the "ok" button the default button
      getRootPane().setDefaultButton(okCancelHelpButtonPanel.getOkButton());
      // enable the F1 function key for invoking "help"
      helpBroker.enableHelpKey(getRootPane(), helpId, helpSet);
      CSH.setHelpIDString(getRootPane(), helpId);

      // invoke the layout manager
      pack();
   }

   /**
    * implements the ActionListener interface.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   public void actionPerformed(ActionEvent event)
   {
      key = event.getActionCommand();

      // call the ok() method in the derived class
      if (key.equals("ok"))
         ok();

      setVisible(false);
      dispose();
   }

   /**
    * derived classes must implement this method to add controls to the dialog box by adding their controls to the controlsPanel.
    */
   public abstract void addComponents();

   /**
    * this method positions the dialog either in the center of the screen or in the center of the parent window.
    * <br/><br/>
    * @param location whether to position the dialog box in the center of the screen or in the center of the parent window.
    */
   private void center(LocationCenter location)
   {
      switch (location)
      {
         case SCREEN:
              centerScreen();
         break;
         case PARENT:
              centerParent();
         break;
      }
   }

   /**
    * this method sets the location of the dialog to the center of the parent window.
    *
    * note: the original code was posted on codeguru.com by Real Gagnon.
    */
   private void centerParent()
   {
      // get the parent
      Container parent     = getParent();
      Point     topLeft    = parent.getLocationOnScreen();
      Dimension parentSize = parent.getSize();
      Dimension dlgSize    = getSize();

      int x = topLeft.x,
          y = topLeft.y;

      if (parentSize.width > dlgSize.width)
         x += ((parentSize.width - dlgSize.width)/2);

      if (parentSize.height > dlgSize.height)
         y += ((parentSize.height - dlgSize.height)/2);

      setLocation (x, y);
   }

   /**
    * this method sets the location of the dialog to the center of the screen.
    *
    * note: the original code was posted on codeguru.com by Real Gagnon.
    */
   private void centerScreen()
   {
      Dimension dimension = getToolkit().getScreenSize();
      Rectangle bounds    = getBounds();
      setLocation((dimension.width - bounds.width) / 2, (dimension.height - bounds.height) / 2);
   }

   /**
    * @return the panel containing the dialog controls.
    */
   protected JPanel getControlsPanel()
   {
      return controlsPanel;
   }

   /**
    * @return the key pressed that ended the dialog.  possible values are "ok" and "cancel";
    */
   public String getKey()
   {
      return(key);
   }

   /**
    * @return the panel containing the ok, cancel, and help buttons.
    */
   protected OkCancelHelpButtonPanel getOkCancelHelpButtonPanel()
   {
      return okCancelHelpButtonPanel;
   }

   /**
    * called by the swing framework when the user presses the <i>ok</i> button.
    * derrived classes must implement this method to retrieve the values from the dialog box controls.
    */
   public abstract void ok();

   /**
    * if the <visible</i> parameter is true, then the addComponents() method is called to set the dialog boxes controls.
    * then, the dialog box is displayed on the screen.  Otherwise, it is hidden and removed from the screen.
    * <br/><br/>
    * @param visible   whether to display the dialog box or to hide it.
    */
   @Override
   public void setVisible(boolean visible)
   {
      if (visible)
      {
         // add the controls to the main panel and center it on the screen.
         addComponents();
         center(LocationCenter.PARENT);
         getContentPane().validate();
      }
      super.setVisible(visible);
   }

   /**
    * @return whether the user pressed the cancel button to end the dialog.
    */
   public boolean wasCanceled()
   {
      return key == null || key.equals("cancel");
   }

   /**
    * @return whether the user pressed the ok button to end the dialog.
    */
   public boolean wasOKed()
   {
      return key != null && key.equals("ok");
   }
}
