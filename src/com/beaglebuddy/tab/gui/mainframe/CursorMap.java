/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andrew will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.model.Location;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;




/**
 * Internal class used for organizing regions within each section.
 */
class SectionMap
{
   public final short sectionIndex;
   public final Map<Rectangle2D,StaffMap> staffMap;
   public final Map<Rectangle2D,Byte> barlineMap;
   public final Map<Rectangle2D,Short> measureMap;
   public final Map<Rectangle2D,Byte> positionMap;

   /** Constructs an object with the given section index.
    *  @param sectionIndex
    */
   public SectionMap(short sectionIndex)
   {
      this.sectionIndex = sectionIndex;
      staffMap = new HashMap<Rectangle2D,StaffMap>();
      barlineMap = new HashMap<Rectangle2D,Byte>();
      measureMap = new HashMap<Rectangle2D,Short>();
      positionMap = new HashMap<Rectangle2D,Byte>();
   }

   /** Clears all maps contained within this section.
    *  Useful when repainting a section is required.
    */
   public void clear()
   {
      staffMap.clear();
      barlineMap.clear();
      measureMap.clear();
      positionMap.clear();
   }

   /** Returns the rectangle object corresponding to the given position
    *  within this section. The rectangle spans all staves.
    *  @param positionIndex
    *  @return
    */
   public Rectangle2D getPositionRectangle(byte positionIndex)
   {
      Rectangle2D rect = null;
      for (Rectangle2D r : positionMap.keySet())
      {
         byte index = positionMap.get(r);
         if (index == positionIndex)
         {
            rect = r;
            break;
         }
      }
      return rect;
   }

   /** Returns the rectangle corresponding to the given staff index for this
    *  section.
    *  @param staffIndex
    *  @return
    */
   public Rectangle2D getStaffRectangle(byte staffIndex)
   {
      Rectangle2D rect = null;
      for (Rectangle2D r : staffMap.keySet())
      {
         byte index = staffMap.get(r).staffIndex;
         if (index == staffIndex)
         {
            rect = r;
            break;
         }
      }
      return rect;
   }

   /** Obtain the object that maps rectangles to staves for this section.
    *  @param staffIndex
    *  @return
    */
   public StaffMap getStaffMap(byte staffIndex)
   {
      for (Rectangle2D r : staffMap.keySet())
      {
         StaffMap sm = staffMap.get(r);
         if (sm.staffIndex == staffIndex)
         {
            return sm;
         }
      }

      // Staff not found.
      return null;
   }

   /** Obtain the object that maps rectangles to staves for this section,
    *  removing it from the section map if found.
    *  @param staffIndex
    *  @return
    */
   public StaffMap removeStaffMap(byte staffIndex)
   {
      Rectangle2D key = null;
      for (Rectangle2D r : staffMap.keySet())
      {
         StaffMap sm = staffMap.get(r);
         if (sm.staffIndex == staffIndex)
         {
            key = r;
            break;
         }
      }

      // Staff not found.
      if (key == null)
      {
         return null;
      }

      // Return the staff map object, removing it from the map in the process.
      return this.staffMap.remove(key);
   }
}

/** Class that contains tab regions within a given staff rectangle.
 *  @author ANDY
 */
class StaffMap
{
   public final byte staffIndex;
   public final Map<Rectangle2D,Integer> tabLineMap;

   /** Constructs a new object for the given staff index.
    *  The section is inferred from the owning object.
    *  @param staffIndex
    */
   public StaffMap(byte staffIndex)
   {
      this.staffIndex = staffIndex;
      tabLineMap = new HashMap<Rectangle2D,Integer>();
   }

   /** Clears the tab rectangles managed by this object. */
   public void clear()
   {
      tabLineMap.clear();
   }

   /** Returns the rectangular region corresponding to the given tab index.
    *  @param tabLineIndex
    *  @return
    */
   public Rectangle2D getTabLineRectangle(int tabLineIndex)
   {
      Rectangle2D rect = null;
      for (Rectangle2D r : tabLineMap.keySet())
      {
         int index = tabLineMap.get(r);
         if (index == tabLineIndex)
         {
            rect = r;
            break;
         }
      }
      return rect;
   }
}

/** Class used to manage mappings between song panel elements, represented
 *  as rectangular regions, and location elements, which are logical indexes
 *  for section, staff, position, etc.
 *  @author ANDY
 */
public class CursorMap
{
   public static final int CURSOR_WIDTH = 20;
   private AffineTransform lastTransform;
   private final Map<Rectangle2D,SectionMap> sectionMaps;

   /** Constructs an object to manage song panel regions for a song. */
   public CursorMap()
   {
      this.sectionMaps = new HashMap<Rectangle2D,SectionMap>();
   }

   /** Clears any regions previously stored in the map. */
   public void clear()
   {
      this.sectionMaps.clear();
   }

   /** Clears a given section's region mappings. Useful before a paint.
    *  @param sectionIndex
    */
   public void clearSection(int sectionIndex)
   {
      SectionMap sectionMap = getSectionMap(sectionIndex);
      if (sectionMap != null)
      {
         sectionMap.clear();
      }
   }

   /** Adds a section to rectangular mapping.
    *  @param g graphics context in which section is painted
    *  @param transform the base transformation active on the graphics context
    *  @param sectionRectangle the region corresponding to the section
    *  @param sectionIndex the number to identify the section within the song
    */
   public void addSection(Graphics2D g,
                          AffineTransform transform,
                          Rectangle2D sectionRectangle,
                          int sectionIndex)
   {
      // Convert rectangle to regular coordinates.
      sectionRectangle = normalizeRectangle(g, transform, sectionRectangle);

      // Obtain the map for this section, creating it if necessary.
      SectionMap sectionMap = removeSectionMap(sectionIndex);
      if (sectionMap == null)
      {
         // Construct a new section.
         sectionMap = new SectionMap((short)sectionIndex);
      }
      else
      {
         // Clear out the old section regions.
         sectionMap.clear();
      }

      // Place the section inside the map using the rectangle as key.
      this.sectionMaps.put(sectionRectangle, sectionMap);
   }

   /** Returns the section map corresponding to the given section index.
    *  @param sectionIndex
    *  @return
    */
   private SectionMap getSectionMap(int sectionIndex)
   {
      for (SectionMap sm : this.sectionMaps.values())
      {
         if (sm.sectionIndex == sectionIndex)
         {
            return sm;
         }
      }
      return null;
   }

   /**
    * @return true if the cursor map contains bounds for the given section.
    * <br/><br/>
    * @param sectionIndex   the index of the section within the song.
    */
   public boolean hasSection(int sectionIndex)
   {
      return (getSectionMap(sectionIndex) != null);
   }

   /** Returns the section map corresponding to the given section index,
    *  removing it from the map structure if it exists.
    *  @param sectionIndex
    *  @return
    */
   private SectionMap removeSectionMap(int sectionIndex)
   {
      // Search for the matching section map.
      Rectangle2D key = null;
      for (Rectangle2D r : this.sectionMaps.keySet())
      {
         if (this.sectionMaps.get(r).sectionIndex == sectionIndex)
         {
            key = r;
            break;
         }
      }

      // Check if the section exists.
      if (key == null)
      {
         return null;
      }

      // Return the section map object, removing it from the map.
      return this.sectionMaps.remove(key);
   }

   /**
    * @return the smallest section index contained in this cursor map object.
    */
   public short getMinSectionIndex()
   {
      short minSectionIndex = Short.MAX_VALUE;
      for (SectionMap sm : this.sectionMaps.values())
      {
         minSectionIndex = (short)Math.min(minSectionIndex, sm.sectionIndex);
      }
      return minSectionIndex;
   }

   /**
    * @return the largest section index contained in this map.
    */
   public short getMaxSectionIndex()
   {
      short maxSectionIndex = Short.MIN_VALUE;
      for (SectionMap sm : this.sectionMaps.values())
      {
         maxSectionIndex = (short)Math.max(maxSectionIndex, sm.sectionIndex);
      }
      return maxSectionIndex;
   }

   /**
    * Maps the given rectangle to the staff corresponding to the section and staff indexes.
    * <br/><br/>
    * @param g                graphics context in which the staff is painted
    * @param transform        the base transformation active on the graphics context
    * @param staffRectangle   rectangle that defines the staff
    * @param sectionIndex     index of the section being defined
    * @param staffIndex       index of the staff being defined
    */
   public void addStaff(Graphics2D g, AffineTransform transform, Rectangle2D staffRectangle, int sectionIndex, int staffIndex)
   {
      // Convert rectangle to normal coordinates.
      staffRectangle = this.normalizeRectangle(g, transform, staffRectangle);

      // Obtain the map object for the section.
      SectionMap sectionMap = getSectionMap(sectionIndex);

      // Obtain the map object for the section's staff, creating it if needed.
      StaffMap staffMap = sectionMap.removeStaffMap((byte)staffIndex);
      if (staffMap == null)
      {
         // Create a new staff map object.
         staffMap = new StaffMap((byte)staffIndex);
      }
      else
      {
         // Don't remove prior staff mappings, as the staff map is sometimes
         // added after it already contains the tab line rectangles.
      }

      // Replace the staff in the map.
      sectionMap.staffMap.put(staffRectangle, staffMap);
   }

   /**
    * maps the given rectangle corresponding to the given section and measure indexes.
    * <br/><br/>
    * @param g                   graphics context in which the staff is painted
    * @param measureRectangle    rectangle that defines the measure
    * @param sectionIndex        index of the section being mapped
    * @param measureNumber       number of the measure being mapped
    * @param barlineIndex        zero-based index of the measure being mapped, relative to the start of the section
    */
   public void addMeasure(Graphics2D g, Rectangle2D measureRectangle, int sectionIndex, int measureNumber, int barlineIndex)
   {
      // Convert measure rectangle to absolute coordinates.
      measureRectangle = normalizeRectangle(g, this.lastTransform,
                                            measureRectangle);

      // Get the map object for this section.
      SectionMap sectionMap = getSectionMap(sectionIndex);

      // Assign the rectangle to the given measure and barline map.
      short measureIndex = (short)(measureNumber + barlineIndex - 1);
      sectionMap.measureMap.put(measureRectangle, measureIndex);
      sectionMap.barlineMap.put(measureRectangle, (byte)(barlineIndex));
   }

   /** Maps the given rectangle corresponding to the given section and
    *  position indexes.
    *  @param g
    *    graphics context in which the staff is painted
    *  @param positionRectangle
    *    rectangle that defines the position within the section
    *  @param sectionIndex
    *    index of the section being mapped
    *  @param posIndex
    *    zero-based index of the position being mapped,
    *    relative to the start of the section
    */
   public void addPosition(Graphics2D g, Rectangle2D positionRectangle,
                           int sectionIndex, int posIndex)
   {
      // Convert the rectangle to absolute coordinates.
      positionRectangle = normalizeRectangle(g, this.lastTransform,
                                             positionRectangle);

      // Add the position rectangle to the section map.
      SectionMap sectionMap = getSectionMap(sectionIndex);
      sectionMap.positionMap.put(positionRectangle, (byte)(posIndex));
   }

   /** Maps the given rectangle corresponding to a staff's tabline region.
    *  @param g
    *    graphics context in which the staff is painted
    *  @param tabLineRectangle
    *    rectangle that defines the tab line within the staff
    *  @param sectionIndex
    *    index of the section being mapped
    *  @param staffIndex
    *    zero-based index of the staff being mapped,
    *    relative to the start of the section
    *  @param tabLineIndex
    *    zero-based index of the tabline being mapped
    */
   public void addTabLine(Graphics2D g, Rectangle2D tabLineRectangle,
                          int sectionIndex, int staffIndex, int tabLineIndex)
   {
      // Convert the rectangle to absolute coordinates.
      tabLineRectangle = normalizeRectangle(g, this.lastTransform,
                                            tabLineRectangle);

      // Obtain the map for this section and staff combination.
      SectionMap sectionMap = getSectionMap(sectionIndex);
      StaffMap staffMap = sectionMap.getStaffMap((byte)staffIndex);

      // If map was not found for the staff, construct a new one
      if (staffMap == null)
      {
         staffMap = new StaffMap((byte)staffIndex);
         sectionMap.staffMap.put(new Rectangle2D.Double(), staffMap);
      }

      // Place the tab line rectangle into the staff map.
      staffMap.tabLineMap.put(tabLineRectangle, tabLineIndex);
   }

   /** Converts the given rectangle into coordinates which are relative
    *  to the graphics context.
    *  @param g current graphics context
    *  @param at the base affine transformation on the graphics context
    *  @param rIn rectangle being converted
    *  @return
    */
   private Rectangle2D normalizeRectangle(Graphics2D g,
                                          AffineTransform at,
                                          Rectangle2D rIn)
   {
      // If transform has changed, clear out the cursor map and
      // save the new transform.
      if (!at.equals(this.lastTransform))
      {
         this.lastTransform = at;
      }

      // Convert the top left point by inverting the active transformation.
      Point2D topLeftIn = new Point2D.Double(rIn.getX(), rIn.getY());
      Point2D topLeftOut = new Point2D.Double();
      try
      {
         // Remove the scrollable window pane transform.
         at.createInverse().transform(topLeftIn, topLeftOut);
      }
      catch (NoninvertibleTransformException nte)
      {
         // Will never get here.
         nte.printStackTrace();
      }

      // Apply the current graphics transform to get the inner window coordinates.
      AffineTransform t = g.getTransform();
      t.transform(topLeftOut, topLeftOut);
      Rectangle2D rOut =
         new Rectangle2D.Double(topLeftOut.getX(),
                                topLeftOut.getY(),
                                rIn.getWidth(),
                                rIn.getHeight());
      return rOut;
   }

   /**
    * @return a new location object calculated from the given point's x, y coordinates.
    * <br/><br/>
    * @param point   point to serve as the basis for the new Location object.
    */
   public Location toLocation(Point point)
   {
      // Initialize the return object.
      Location l = new Location();
      l.setPoint(point);
      l.setSection((short)-1);

      // Assign section.
      Rectangle2D section = null;
      for (Rectangle2D r : this.sectionMaps.keySet())
      {
         if (r.contains(point))
         {
            section = r;
            break;
         }
      }
      if (section == null)
      {
         // Section can't be found -- return null location.
         return null;
      }
      SectionMap sectionMap = this.sectionMaps.get(section);
      l.setSection(sectionMap.sectionIndex);

      // Assign staff.
      for (Rectangle2D r : sectionMap.staffMap.keySet())
      {
         if (r.contains(point))
         {
            StaffMap staffMap = sectionMap.staffMap.get(r);
            l.setStaff(staffMap.staffIndex);

            // Assign tab line for this staff.
            for (Rectangle2D s : staffMap.tabLineMap.keySet())
            {
               if (s.contains(point))
               {
                  l.setTabIndex(staffMap.tabLineMap.get(s));
                  break;
               }
            }

            break;
         }
      }

      // Assign barline.
      for (Rectangle2D r : sectionMap.barlineMap.keySet())
      {
         if (r.contains(point))
         {
            l.setBarline(sectionMap.barlineMap.get(r));
            break;
         }
      }

      // Assign measure.
      for (Rectangle2D r : sectionMap.measureMap.keySet())
      {
         if (r.contains(point))
         {
            l.setMeasure(sectionMap.measureMap.get(r));
            break;
         }
      }

      // Assign position of this point within the section.
      for (Rectangle2D r : sectionMap.positionMap.keySet())
      {
         if (r.contains(point))
         {
            l.setPosition(sectionMap.positionMap.get(r));
            break;
         }
      }

      return l;
   }

   /** Draws the cursor within the graphics context.
    *  @param g active graphics context
    *  @param l location to be painted
    *  @throws Exception if cursor cannot be drawn (if it's unknown)
    */
   public void drawCursor(Graphics2D g, Location l) throws Exception
   {
      // Get the cursor rectangle.
      Rectangle2D cursor = getCursor(l);

      // Apply scrollbar transform.
      AffineTransform at = g.getTransform();
      g.setTransform(this.lastTransform);

      // Draw the cursor.
      Color oldColor = g.getColor();
      g.setColor(Color.BLUE);
      g.draw(cursor);
      g.setColor(oldColor);

      // Ensure the rectangle lies completely in the clipping plane.
      Rectangle2D clipRect = g.getClipBounds();
      Exception e;
      if (!clipRect.contains(cursor))
      {
         e = new Exception("position is not completely drawable");
      }
      else
      {
         e = null;
      }

      // Restore the old transform.
      g.setTransform(at);

      // If an exception is pending, throw it now.
      if (e != null)
      {
         throw e;
      }
   }

   /** Returnns the rectangle that defines the visible bounds of the cursor
    *  defined by the given location.
    *  @param l location of interest
    *  @return bounds of cursor as a Rectangle
    *  @throws RuntimeException if the cursor coordinates are unknown
    */
   public Rectangle getCursorBounds(Location l) throws RuntimeException
   {
      // Look-up the position.
      SectionMap sectionMap = getSectionMap(l.getSection());
      if (sectionMap == null)
      {
         throw new RuntimeException("section has not been drawn");
      }

      // Obtain the position intersection with the staff.
      Rectangle2D position = sectionMap.getPositionRectangle(l.getPosition());
      Rectangle2D staff = sectionMap.getStaffRectangle(l.getStaff());
      if (position == null)
      {
         throw new RuntimeException("no position");
      }
      else if (staff == null)
      {
         throw new RuntimeException("no staff");
      }

      // Take the intersection of the position and staff.
      Rectangle cursor = new Rectangle();
      Rectangle2D.intersect(staff, position, cursor);
      return cursor;
   }

   /** Returns the rectangle associated with the given rectangle.
    *  @param l location of interest
    *  @return the rectangle defining the cursor
    *  @throws Exception if the cursor rectangle is unknown
    */
   private Rectangle2D getCursor(Location l) throws Exception
   {
      // Look-up the position.
      SectionMap sectionMap = getSectionMap(l.getSection());
      if (sectionMap == null)
      {
         throw new Exception("section has not been drawn");
      }

      // Obtain the position intersection with the staff.
      Rectangle2D position = sectionMap.getPositionRectangle(l.getPosition());
      StaffMap staffMap = sectionMap.getStaffMap(l.getStaff());
      Rectangle2D tabLine = staffMap.getTabLineRectangle(l.getTabIndex());
      if (position == null)
      {
         throw new Exception("no position");
      }
      else if (tabLine == null)
      {
         throw new Exception("no tab line");
      }

      // Take the intersection of the position and tab line.
      Rectangle2D cursor = new Rectangle2D.Double();
      Rectangle2D.intersect(tabLine, position, cursor);

      // Fix the width and back it off to center it.
      double margin = (cursor.getWidth() - CURSOR_WIDTH)/2.0;
      cursor.setRect(cursor.getX() + margin,
                     cursor.getY(),
                     CURSOR_WIDTH,
                     cursor.getHeight());

      return cursor;
   }

   /**
    * @return   the transformation of the given rectangle using the transformation last active on the graphics context.
    * @param cursorIn   the rectangle to be transformed.
    */
   public Rectangle transform(Rectangle cursorIn)
   {
      // Prepare input and output point for transformation.
      Point topLeftIn  = new Point(cursorIn.x, cursorIn.y);
      Point topLeftOut = new Point();

      // Transform the rectangle.
      this.lastTransform.transform(topLeftIn, topLeftOut);

      // Prepare the transformed cursor for output.
      Rectangle cursorOut = new Rectangle(topLeftOut.x, topLeftOut.y, cursorIn.width, cursorIn.height);
      return cursorOut;
   }
}
