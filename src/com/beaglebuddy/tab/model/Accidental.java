/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.resource_bundle.ResourceBundle;




/**
 * This class represents a beaglebuddy accidental.
 */
public enum Accidental
{
   Natural, Flat, Sharp;

   /**
    * @param accidental  the integer accidental.
    * <br/><br/>
    * @return the accidental enum corresponding to the integer accidental.
    */
   public static Accidental getAccidental(int accidental)
   {
      for (Accidental a : Accidental.values())
         if (accidental == a.ordinal())
            return a;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("text.accidental"), accidental, Accidental.Sharp.ordinal()));
   }
}
