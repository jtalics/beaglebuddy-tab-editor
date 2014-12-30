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
import javax.swing.JComboBox;



/**
 * combo box that lets a user choose from a predefined list of colors.
 */
public class ColorComboBox extends JComboBox
{
   // class members
   private static Color   navy   = new Color(  0,  0, 128);
   private static Color   purple = new Color(128,  0, 128);
   private static Color[] colors = {Color.black    ,
                                    Color.darkGray ,
                                    Color.gray     ,
                                    Color.lightGray,
                                    Color.red      ,
                                    Color.magenta  ,
                                    Color.pink     ,
                                    Color.orange   ,
                                    Color.yellow   ,
                                    Color.green    ,
                                    Color.blue     ,
                                          navy     ,
                                    Color.cyan     ,
                                          purple   };

   // data members
   private Color color;   // the color the user selected



  /**
   * default constructor.
   */
   public ColorComboBox()
   {
      super(colors);

      setRenderer(new ColorComboBoxRenderer());
   }
  }
