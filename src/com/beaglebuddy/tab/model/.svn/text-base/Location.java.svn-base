/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import java.awt.Point;




/**
 * this class holds the details of the current cursor location within the song.
 */
public class Location
{
   // class members
   /** The minimum barline index. */
   public static final byte MINIMUM_BARLINE = 0;

   /** The minimum position index. */
   public static final byte MINIMUM_POSITION = 1;

   /** Point used to designate locations arrived by keyboard. */
   public static final Point DEFAULT_POINT = new Point(0, 0);

   // data members
   private short section;
   private byte  staff;
   private byte  barline;
   private short measure;
   private byte  position;
   private byte  note;          // the note within the chord that the cursor is on
   private Point point;
   private int   tabIndex;      // the current line on the tab staff.




   /**
    * default constructor.
    */
   public Location()
   {
      this.section  = 0;
      this.staff    = 0;
      this.barline  = MINIMUM_BARLINE;
      this.measure  = 0;
      this.position = MINIMUM_POSITION;
      this.point    = DEFAULT_POINT;
      this.tabIndex = 0;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param section    the current section in the song.
    * @param staff      the current staff in the section.
    * @param barline    the current barline in the section.         todo: or previous barline ???
    * @param measure    the current measure in the song.
    * @param position   the current drawing position in the section.
    * @param point      the current screen position in pixels.
    * @param tabIndex   the current line on the tab staff.
    */
   public Location(short section, byte staff, byte barline, short measure, byte position, Point point, int tabIndex)
   {
      this.section  = section;
      this.staff    = staff;
      this.barline  = barline;
      this.measure  = measure;
      this.position = position;
      this.point    = point;
      this.tabIndex = tabIndex;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param location   location whose values will be deep copied.
    */
   public Location(Location location)
   {
      this.section  = location.section;
      this.staff    = location.staff;
      this.barline  = location.barline;
      this.measure  = location.measure;
      this.position = location.position;
      this.point    = new Point(location.point);
      this.tabIndex = location.tabIndex;
   }

   /**
    * @return the current section in the song.
    */
   public short getSection()
   {
      return section;
   }

   /**
    * set the current section in the song.
    * <br/><br/>
    * @param section   the index of the current section in the song.
    */
   public void setSection(short section)
   {
      this.section = section;
   }

   /**
    * @return the current staff in the section.
    */
   public byte getStaff()
   {
      return staff;
   }

   /**
    * set the current staff in the section.
    * <br/><br/>
    * @param staff  the index of the current staff in the section.
    */
   public void setStaff(byte staff)
   {
      this.staff = staff;
   }

   /**
    * @return current barline in the section, such that getSong().getScore().getSections().get(<section number>).getBarlines().get(location.getBarline()) will return
    * the current barline, or -1 if the cursor is not positioned over a barline.
    */
   public byte getBarline()
   {
      return barline;
   }

   /**
    * sets the index of the current barline in the section.
    * <br/><br/>
    * @param barline   the index of the current barline in the section.
    */
   public void setBarline(byte barline)
   {
      this.barline = barline;
   }

   /**
    * @return current measure in the song.
    */
   public short getMeasure()
   {
      return measure;
   }

   /**
    * set the current measure in the song.
    * <br/><br/>
    * @param measure   the current measure in the song.
    */
   public void setMeasure(short measure)
   {
      this.measure = measure;
   }

   /**
    * @return the current drawing position in the section.
    */
   public byte getPosition()
   {
      return position;
   }

   /**
    * set the current drawing position in the section.
    * <br/><br/>
    * @param position  the current drawing position in the section
    */
   public void setPosition(byte position)
   {
      this.position = position;
   }

   /**
    * @return the current note within the chord at the current drawing position.
    */
   public byte getNote()
   {
      return note;
   }

   /**
    * @return the current screen position in pixels.
    */
   public Point getPoint()
   {
      return point;
   }

   /**
    * set the current screen position in pixels.
    * <br/><br/>
    * @param point  the current screen position in pixels.
    */
   public void setPoint(Point point)
   {
      this.point = point;
   }

   /**
    * @return the current line index on the tab staff.
    */
   public int getTabIndex()
   {
      return tabIndex;
   }

   /**
    * set the current line index on the tab staff.
    * <br/><br/>
    * @param tabIndex the new line index on the tab staff.
    */
   public void setTabIndex(int tabIndex)
   {
      this.tabIndex = tabIndex;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + measure;
      result = prime * result + section;
      result = prime * result + tabIndex;
      return result;
   }

   /**
    * @return whether two locations are equal.
    * <br/><br/>
    * @param object   object to be checked for equality.  <br/>note: the point data member is not considered in the test.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Location)
      {
         Location location = (Location)object;
         equal = this.section == location.section && this.staff == location.staff && this.barline == location.barline && this.measure == location.measure && this.position == location.position && this.tabIndex == location.tabIndex;
      }
      return equal;
   }

   /**
    * @return a string representation of the cursor location.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(3);

      buffer.append("location\n");
      buffer.append(Utility.pad("section" , indentation) + section  + "\n");
      buffer.append(Utility.pad("staff"   , indentation) + staff    + "\n");
      buffer.append(Utility.pad("barline" , indentation) + barline  + "\n");
      buffer.append(Utility.pad("measure" , indentation) + measure  + "\n");
      buffer.append(Utility.pad("position", indentation) + position + "\n");
      buffer.append(Utility.pad("note"    , indentation) + note     + "\n");
      buffer.append(Utility.pad("tab line", indentation) + tabIndex + "\n");
      buffer.append(Utility.pad("point"   , indentation) + point          );

      return buffer.toString();
   }
}
