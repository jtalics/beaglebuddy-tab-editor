package com.beaglebuddy.tab.gui.mainframe;

import javax.swing.*;

/**
 * try to remove the menus from a JFrame.
 *
 * This class demonstrates the approaches I've taken and how they've all failed.
 */
public class Test
{
   /**
    * creates the gui and starts it running.
    * <br/><br/>
    * @param args  command line arguments.
    */
   public static void main(String[] args)
   {
      // create a menu bar and add it to the frame
      JFrame   jframe   = new JFrame("menu test");
      JMenuBar jmenuBar = jframe.getJMenuBar();

      if (jmenuBar == null)
      {
         jmenuBar = new JMenuBar();
         jframe.setJMenuBar(jmenuBar);
      }

      // add 3 top level menus to the menu bar
      jmenuBar.add(new JMenu("Menu 1"));
      jmenuBar.add(new JMenu("Menu 2"));
      jmenuBar.add(new JMenu("Menu 3"));


      // try to remove the jmenu bar -  note: this doesn't work
//    jframe.remove(jmenuBar);

      // so instead, leave the jmenubar and try and remove the top level menus
      // note: this also doesn't work - you get the following exception
      //       Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 2
      //       at java.awt.Container.remove(Container.java:1110)
      int numTopLevelMenus = jmenuBar.getMenuCount();
      for(int i=0; i<numTopLevelMenus; ++i)
         jmenuBar.remove(0);


      // add 3 top level menus to the menu bar
      jmenuBar.add(new JMenu("Menu 4"));
      jmenuBar.add(new JMenu("Menu 5"));
      jmenuBar.add(new JMenu("Menu 6"));


      jframe.setVisible(true);
   }
}
