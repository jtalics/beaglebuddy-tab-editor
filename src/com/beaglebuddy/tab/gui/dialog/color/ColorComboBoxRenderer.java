/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.color;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ToolTipManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;




/**
 * renders a cell in a combo box as a colored rectangle.
 */
public class ColorComboBoxRenderer extends JPanel implements ListCellRenderer
{
   // data members
   protected Color color;     // color to draw in the cell



  /**
   * default constructor.
   */
  public ColorComboBoxRenderer()
  {
     setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, Color.white), new LineBorder(Color.black)));

     color = Color.black;
  }

  /**
   * @param list          the list
   * @param object        the cell in the list being painted.
   * @param index         the index of the cell in the list being painted.
   * @param isSelected    whether the cell is selected.
   * @param cellHasFocus  whether the cell has the focus.
   */
  public Component getListCellRendererComponent(JList list, Object object, int index, boolean isSelected, boolean cellHasFocus)
  {
     if (object instanceof Color)
        color = (Color)object;

     return this;
  }

  /**
   * @param graphics  the graphics context used to draw the color.
   */
  @Override
public void paint(Graphics graphics)
  {
     setBackground(color);
     super.paint(graphics);
  }
}
