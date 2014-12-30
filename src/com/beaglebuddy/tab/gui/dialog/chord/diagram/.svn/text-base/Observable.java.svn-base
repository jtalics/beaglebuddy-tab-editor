/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.diagram;




/**
 * due to a design flaw in java's Observable class, it was necessary to create this class.
 * for some unknown reason, the Observable class's designers made the setChanged() and cleanChanged() methods protected.
 * since notifyObservers() does nothing unless setChanged() has been called, this forces all classes to extend from Observable.
 * but what if you are already extending from a different java class, you ask?
 * well, you'd be fucked. just like I am now, and you'd have to hack up a crappy class like this.
 */
public class Observable extends java.util.Observable
{
   /**
    * default constructor.
    */
   public Observable()
   {
      setChanged();
   }

   /**
    * marks the observable object as having been changed.
    */
   public void setDirty()
   {
      setChanged();
   }

   /**
    * marks the observable object as not having been changed.
    */
   public void clearDirty()
   {
      clearChanged();
   }

   /**
    * marks the observable object as having been changed so that calls to notifyObservers() will actually work.
    */
   @Override
   public void notifyObservers()
   {
      setChanged();
      super.notifyObservers();
   }
}
