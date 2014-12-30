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
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;



/**
 * This class is a dialog box which lists some information about beaglebuddy software and the beaglebuddy tab editor.
 */
public class AboutDialog extends JDialog implements ActionListener, MouseListener
{
   // locations for centering the dialog
   public enum LocationCenter {SCREEN, PARENT}

   // class members
   private static final int IMAGE_HEIGHT = 110;
   private static final int IMAGE_WIDTH  = 340;

   // get the values of some system properties
   private static final String JAVA_VERSION = System.getProperty("java.vm.version");
   private static final String JAVA_VENDOR  = System.getProperty("java.vm.vendor");
   private static final String OS_NAME     = System.getProperty("os.name");
   private static final String OS_VERSION   = System.getProperty("os.version");
   private static final String TAB_HOME     = System.getProperty("tab.home");
   private static final String BBTE_VERSION = ResourceBundle.getString("gui.beaglebuddy_tab_editor_version");



   // data members
   private JPanel  controlsPanel;          // panel at the top    of the dialog box where controls reside
   private JPanel  okButtonPanel;          // panel at the bottom of the dialog box which contains the ok button
   private JButton okButton;               // ok button
   private JLabel  labelImage;             // beaglebuddy image at top of the dialog box
   private JLabel  labelVersion;
   private JLabel  labelVersionValue;
   private JLabel  labelCopyright;
   private JLabel  labelCopyrightValue;
   private JLabel  labelHomePage;
   private JLabel  labelHomePageValue;
   private JLabel  labelSubmitBug;
   private JLabel  labelSubmitBugValue;




   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    */
   public AboutDialog(MainFrame frame)
   {
      super(frame);

      // create two panels for the dialog box
      controlsPanel = createControlsPanel();
      okButtonPanel = createOkButtonPanel();

      // set the layout manager
      GroupLayout groupLayout = new GroupLayout(getContentPane());
      getContentPane().setLayout(groupLayout);

      // automatically place gaps between the components
      groupLayout.setAutoCreateGaps         (true);
      groupLayout.setAutoCreateContainerGaps(true);

      // layout the panels along the horizontal axis
      GroupLayout.ParallelGroup   horizontalGroup = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
      horizontalGroup.addComponent(controlsPanel);
      horizontalGroup.addComponent(okButtonPanel);

      // layout the panels along the vertical axis
      GroupLayout.SequentialGroup verticalGroup = groupLayout.createSequentialGroup();
      verticalGroup  .addComponent(controlsPanel);
      verticalGroup  .addComponent(okButtonPanel);

      // layout the panels in the dialog box
      groupLayout.setHorizontalGroup(horizontalGroup);
      groupLayout.setVerticalGroup  (  verticalGroup);

      // set some attributes of the dialog box
      setLocale(ResourceBundle.getCurrentLocale());
      setResizable(true);
      setModal(true);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

      // make the "ok" button the default button
      getRootPane().setDefaultButton(okButton);

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.about.title"));

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
      String key = event.getActionCommand();

      if (key.equals("ok"))
         ok();

      setVisible(false);
      dispose();
   }

   /**
    * this method sets the location of the dialog to the center of the parent window.
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
    * create the controls for the top half of the dialog box.
    */
   public JPanel createControlsPanel()
   {
      // create the panel
      JPanel controlsPanel = new JPanel();
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      // create the labels
      String icon = ResourceBundle.getString("dialog.about.image");
      try
      {
         labelImage = new JLabel(new ImageIcon(getClass().getResource(icon)));
      }
      catch (Exception ex)
      {
         // this should never happen, unless the developer made a mistake in the resource bundle
         System.out.println("trying to load " + icon);
      }

      labelVersion       = new JLabel(ResourceBundle.getString("dialog.about.label.version"         ));
      labelVersionValue  = new JLabel(ResourceBundle.getString("dialog.about.label.version.value"   ));

      labelCopyright     = new JLabel(ResourceBundle.getString("dialog.about.label.copyright"       ));
      labelCopyrightValue= new JLabel(ResourceBundle.getString("dialog.about.label.copyright.value" ));

      labelHomePage      = new JLabel(ResourceBundle.getString("dialog.about.label.home_page"       ));
      labelHomePageValue = new JLabel(ResourceBundle.getString("dialog.about.label.home_page.value" ));
      labelHomePageValue.addMouseListener(this);

      labelSubmitBug     = new JLabel(ResourceBundle.getString("dialog.about.label.submit_bug"      ));
      labelSubmitBugValue= new JLabel(ResourceBundle.getString("dialog.about.label.submit_bug.value"));
      labelSubmitBugValue.addMouseListener(this);

      // set the layout manager
      GroupLayout controlsPanelLayout = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(controlsPanelLayout);

      // layout the horizontal axis
      controlsPanelLayout.setHorizontalGroup(
         controlsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(controlsPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(controlsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(labelVersion)
               .addComponent(labelCopyright)
               .addComponent(labelHomePage)
               .addComponent(labelSubmitBug))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(controlsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(labelVersionValue)
               .addComponent(labelCopyrightValue)
               .addComponent(labelHomePageValue)
               .addComponent(labelSubmitBugValue))
            .addContainerGap(50, Short.MAX_VALUE))
         .addComponent(labelImage, GroupLayout.DEFAULT_SIZE, IMAGE_WIDTH, Short.MAX_VALUE)
      );
      controlsPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {labelCopyright     , labelHomePage     , labelSubmitBug     , labelVersion     });
      controlsPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {labelCopyrightValue, labelHomePageValue, labelSubmitBugValue, labelVersionValue});

      // layout the vertical axis
      controlsPanelLayout.setVerticalGroup(
         controlsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(controlsPanelLayout.createSequentialGroup()
            .addComponent(labelImage, GroupLayout.PREFERRED_SIZE, IMAGE_HEIGHT, GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addGroup(controlsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelVersion)
               .addComponent(labelVersionValue))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(controlsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelCopyright)
               .addComponent(labelCopyrightValue))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(controlsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelHomePage)
               .addComponent(labelHomePageValue))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(controlsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelSubmitBug)
               .addComponent(labelSubmitBugValue))
            .addContainerGap(16, Short.MAX_VALUE))
      );
      controlsPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {labelCopyright     , labelHomePage     , labelSubmitBug     , labelVersion     });
      controlsPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {labelCopyrightValue, labelHomePageValue, labelSubmitBugValue, labelVersionValue});

      return controlsPanel;
   }

   /**
    * @return the ok button panel.
    */
   JPanel createOkButtonPanel()
   {
      // create the panel
      JPanel okButtonPanel = new JPanel();

      // create the ok button
      okButton = new JButton(ResourceBundle.getString("dialog.button.ok.txt"));
      okButton.setActionCommand("ok");
      okButton.addActionListener(this);

      // layout the ok button in the panel
      GroupLayout okButtonPanelLayout = new GroupLayout(okButtonPanel);
      okButtonPanel.setLayout(okButtonPanelLayout);
      okButtonPanelLayout.setHorizontalGroup(
         okButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(okButtonPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(okButton)
            .addContainerGap())
      );
      okButtonPanelLayout.setVerticalGroup(
         okButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, okButtonPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(okButton)
            .addContainerGap())
      );
      return okButtonPanel;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   public void ok()
   {
      // nothing to do.
   }

   /**
    * copied and pasted this code from a page on the internet.
    * I have no idea how it works, only that it does.
    */
   public final boolean invokeBrowser()
   {
      String  url    = "http://www.beaglebuddy.com";
      boolean result = true;     // whether the browser was successfully loaded the beaglebuddy home page

      // determine if the os the app is being run on is windows
      String  os        = System.getProperty("os.name");
      boolean isWindows = os != null && os.startsWith("Windows");

      // invoke the user's browser and have it load the beaglebuddy home page
      String  command = null;
      Process process = null;

      try
      {
         if (isWindows)
         {
            command = "rundll32 url.dll,FileProtocolHandler " + url;
            Runtime.getRuntime().exec(command);
         }
         else
         {
            command = "netscape -remote openURL(" + url + ")";
            process = Runtime.getRuntime().exec(command);
            try
            {
               if(process.waitFor() != 0)
               {
                  command = "netscape " + url;
                  Runtime.getRuntime().exec(command);
               }
            }
            catch(InterruptedException ex)
            {
               JOptionPane.showMessageDialog(null, ResourceBundle.format("gui.error.unable_to_invoke_browser", command, ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
               result = false;
            }
         }
      }
      catch(IOException ex)
      {
         JOptionPane.showMessageDialog(null, ResourceBundle.format("gui.error.unable_to_invoke_browser", command, ex.getMessage()), ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      return result;
   }

   /**
    * invoke the user's browser and navigate to the beaglebuddy home page.
    */
   public final void invokeBrowser2()
   {
      // check whether the desktop api is supported by this particular virtual machine (VM) on this particular operating system
      if (Desktop.isDesktopSupported())
      {
         Desktop desktop = Desktop.getDesktop();
         if (desktop.isSupported(Desktop.Action.BROWSE))
         {
            try
            {
               desktop.browse(new URI("http://www.beaglebuddy.com"));
            }
            catch(Exception exception)
            {
               exception.printStackTrace();
            }
         }
         else
         {
            JOptionPane.showMessageDialog(null, ResourceBundle.format("gui.error.desktop_browser_not_supported", OS_NAME + " " + OS_VERSION, ResourceBundle.getString("dialog.about.mail.text.address")),
                                          ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         }
      }
   }

   /**
    * invoke the host system's e-mail client
    */
   public void invokeEmailClient()
   {
      // check whether the desktop api is supported by this particular virtual machine (VM) on this particular operating system
      if (Desktop.isDesktopSupported())
      {
         Desktop desktop = Desktop.getDesktop();
         if (desktop.isSupported(Desktop.Action.MAIL))
         {
            try
            {
               StringBuffer buffer = new StringBuffer();
               buffer.append(ResourceBundle.getString("dialog.about.mail.text.address"));
               buffer.append("?SUBJECT=" + ResourceBundle.getString("dialog.about.mail.text.bug_report"));
               buffer.append("&BODY=");
               buffer.append(ResourceBundle.getString("dialog.about.mail.text.os")           + ": " + OS_NAME     + " " + OS_VERSION   + "\n");
               buffer.append(ResourceBundle.getString("dialog.about.mail.text.java_vm")      + ": " + JAVA_VENDOR + " " + JAVA_VERSION + "\n");
               buffer.append(ResourceBundle.getString("dialog.about.mail.text.tab_home")     + ": " + TAB_HOME                         + "\n");
               buffer.append(ResourceBundle.getString("dialog.about.mail.text.bbte_version") + ": " + BBTE_VERSION                     + "\n\n");
               buffer.append(ResourceBundle.getString("dialog.about.mail.text.description")                                            + "\n");
               URI uri = new URI("mailto", buffer.toString(), null);
               desktop.mail(uri);
            }
            catch(Exception exception)
            {
               exception.printStackTrace();
            }
         }
         else
         {
            JOptionPane.showMessageDialog(null, ResourceBundle.format("gui.error.desktop_mail_not_supported", OS_NAME + " " + OS_VERSION, ResourceBundle.getString("dialog.about.mail.text.address")),
                                          ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
         }
      }
   }

   /**
    * implements a method in the mouse listener interface and invokes the user's browser and loads the beaglebuddy home page.
    * <br/><br/>
    * @param event    mouse click that triggered this event.
    */
   public void mouseClicked (MouseEvent event)
   {
      if (event.getButton() == MouseEvent.BUTTON1)
      {
         JLabel source = (JLabel)event.getSource();
         if (source == labelHomePageValue)
            invokeBrowser();
         else if (source == labelSubmitBugValue)
            invokeEmailClient();
      }
   }

   /**
    * implements a method in the mouse listener interface, but is not used.
    * <br/><br/>
    * @param event    mouse movement that triggered this event.
    */
   public void mouseEntered (MouseEvent event)
   {
     // no code necessary
   }

   /**
    * implements a method in the mouse listener interface, but is not used.
    * <br/><br/>
    * @param event    mouse movement that triggered this event.
    */
   public void mouseExited(MouseEvent event)
   {
     // no code necessary
   }

   /**
    * implements a method in the mouse listener interface, but is not used.
    * <br/><br/>
    * @param event    mouse button press that triggered this event.
    */
   public void mousePressed(MouseEvent event)
   {
     // no code necessary
   }

   /**
    * implements a method in the mouse listener interface, but is not used.
    * <br/><br/>
    * @param event    mouse button release that triggered this event.
    */
   public void mouseReleased(MouseEvent event)
   {
     // no code necessary
   }

   /**
    * if the <visible</i> parameter is true, then the dialog box is displayed on the screen.
    * Otherwise, it is hidden and removed from the screen.
    * <br/><br/>
    * @param visible   whether to display the dialog box or to hide it.
    */
   @Override
   public void setVisible(boolean visible)
   {
      if (visible)
      {
         centerParent();
         getContentPane().validate();
      }
      super.setVisible(visible);
   }
}
