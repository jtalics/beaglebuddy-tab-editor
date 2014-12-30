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
import com.beaglebuddy.tab.gui.dialog.color.ColorComboBox;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.gui.mainframe.ToolbarItemInfo;
import com.beaglebuddy.tab.model.FontSetting;
import com.beaglebuddy.tab.model.attribute.chord.Text;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;



/**
 * This class is a dialog box which allows a user to specify that a chord is played as an text.
 */
public class TextDialog extends BaseDialog implements ItemListener
{
   // class members
   private static final Integer[] FONT_POINT_SIZES = {6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
   private static final int       TEXT_FIELD_WIDTH = 250;

   // data members
   private Text text;

   // controls
   JLabel        labelText;
   JTextField    textFieldText;
   JLabel        labelFont;
   JComboBox     comboBoxFontName;
   JComboBox     comboBoxFontSize;
   ColorComboBox comboBoxColor;
   JToolBar      toolBarFontStyle;



   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    * @param text    the text to be edited.
    */
   public TextDialog(MainFrame frame, Text text)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.text");

      // make a copy of the text the user will be editing
      this.text = (text == null ? new Text() : (Text)text.clone());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.text.title"));
   }

   /**
    * implements the ActionListener interface.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      String key = event.getActionCommand();

      if (key.equals("bold") || key.equals("italic"))
         updateModel();
      else
         super.actionPerformed(event);
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      labelText = new JLabel(ResourceBundle.getString("dialog.text.label.text"));
      labelFont = new JLabel(ResourceBundle.getString("dialog.text.label.font"));

      // create the text box
      textFieldText = new JTextField(text.getText());

      // create the font name combo box
      FontSetting fontSettings = text.getFontSettings();
      String[] fontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(ResourceBundle.getCurrentLocale());
      comboBoxFontName = new JComboBox(fontFamilyNames);
      comboBoxFontName.setSelectedItem(fontSettings.getFaceName());
      comboBoxFontName.addItemListener(this);

      // create the font size combo box
      comboBoxFontSize = new JComboBox(FONT_POINT_SIZES);
      comboBoxFontSize.setSelectedItem(fontSettings.getPointSize());
      comboBoxFontSize.addItemListener(this);

      // create the color chooser combo box
      comboBoxColor = new ColorComboBox();
      comboBoxColor.setSelectedItem(fontSettings.getColor());
      comboBoxColor.addItemListener(this);

      // create the toolbar with the bold, italic, and underlined buttons
      toolBarFontStyle = buildFontStyleToolbar(fontSettings.isBold(), fontSettings.isItalic());

      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);
      controlsPanel.setBorder(BorderFactory.createTitledBorder(""));

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(labelText)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(textFieldText, GroupLayout.PREFERRED_SIZE, TEXT_FIELD_WIDTH, GroupLayout.PREFERRED_SIZE))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(labelFont)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxFontName, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxFontSize, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE , GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(comboBoxColor   , GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE , GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(toolBarFontStyle, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE , GroupLayout.PREFERRED_SIZE))))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelText)
               .addComponent(textFieldText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(labelFont)
                  .addComponent(comboBoxFontName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addComponent(comboBoxFontSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addComponent(comboBoxColor   , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addComponent(toolBarFontStyle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
      );

      // invoke the layout manager
      pack();
  }

   /**
    * @return the barline toolbar.
    * <br/><br/>
    * @param isBold     whether the bold   toolbar icon is initially toggled as selected.
    * @param isItalic   whether the italic toolbar icon is initially toggled as selected.
    */
   private JToolBar buildFontStyleToolbar(boolean isBold, boolean isItalic)
   {
      JToolBar          toolbar          = new JToolBar        (ResourceBundle.getString("dialog.text.toolbar.title"));
      ToolbarItemInfo[] toolbarItemInfos = {new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("dialog.text.toolbar.bold.icon"  ), ResourceBundle.getString("dialog.text.toolbar.bold.tooltip"  ), "bold"  , this, true ),
                                            new ToolbarItemInfo(ToolbarItemInfo.Type.Toggle_Button, ResourceBundle.getString("dialog.text.toolbar.italic.icon"), ResourceBundle.getString("dialog.text.toolbar.italic.tooltip"), "italic", this, true )};

      toolbar.setBorderPainted(true);
      toolbar.setFloatable(false);
      for(ToolbarItemInfo toolbarItemInfo : toolbarItemInfos)
            toolbar.add(toolbarItemInfo.getToolbarItem());

      // set the toggle buttons to their initial state
      ((JToggleButton)toolbar.getComponentAtIndex(0)).setSelected(isBold  );
      ((JToggleButton)toolbar.getComponentAtIndex(1)).setSelected(isItalic);

      return toolbar;
   }

   /**
    * implements the ItemListener interface and is called whenever a user makes a selection from one of the combo boxes.
    * <br/><br/>
    * @param event  the combo box event.
    */
   public void itemStateChanged(ItemEvent event)
   {
      updateModel();
   }

   /**
    * @return the text the user edited.
    */
   public Text getText()
   {
      return text;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      text.setText(textFieldText.getText());
   }

   /**
    * update the text object the user is editing.
    */
   private void updateModel()
   {
      text.setText(textFieldText.getText());
      text.setFontSettings(new FontSetting((String)comboBoxFontName.getSelectedItem(), (Integer)comboBoxFontSize.getSelectedItem(), ((JToggleButton)toolBarFontStyle.getComponentAtIndex(0)).isSelected(),
                                           ((JToggleButton)toolBarFontStyle.getComponentAtIndex(1)).isSelected(), (Color)comboBoxColor.getSelectedItem()));
   }
}
