/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.gui.songpanel.SongPanel;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.song.Song;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JViewport;




/**
 * This main frame base class manages the screen cursor in the application.
 */
public abstract class MainFrameCursor extends MainFrameSong implements KeyListener, MouseListener, MouseMotionListener
{
   /** List of objects registered for cursor movement\change events */
   private List<CursorListener> listeners;

   /** Counter value at last repaint request. */
   private int lastRepaintCounter;

   /** Paint counter, used for calling repaint() more than once. */
   private int paintCounter;

   /**
    * default constructor.
    */
   public MainFrameCursor()
   {
      listeners = new ArrayList<CursorListener>();
   }

   /**
    * @return the main window where the song is displayed.
    */
   public abstract SongPanel getSongWindowPanel();

   /**
    * @return the scroll pane which allows the user to scroll the main song window.
    */
   public abstract JScrollPane getSongWindowScroller();

   /**
    * @return the current location within the song where the cursor is.
    */
   public Location getCurrentLocation()
   {
      return getSongWindowPanel().getSong().getCurrentLocation();
   }

   /**
    * Assigns a new location to where the cursor is.
    * The window is scrolled to make the new cursor visible.
    * <br/><br/>
    * @param location  the current cursor location within the song.
    */
   public void setCurrentLocation(Location location)
   {
      if (location != null)
      {
         try
         {
            handleNewLocation(location);
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      setLocation(location);
   }

   /**
    * Sets the current location where the cursor is and notifies listeners.
    * This method does not scroll the window to the new location.
    * <br/><br/>
    * @param location  the current cursor location within the song.
    * @see #setCurrentLocation
    */
   protected void setLocation(Location location)
   {
      getSongWindowPanel().getSong().setCurrentLocation(location);
      notifyListeners();
   }

   /**
    * register the listener for changes in the cursor location.
    * <br/><br/>
    * @param listener   object that wishes to be notified of changes in the cursor location.
    */
   public void addCursorListener(CursorListener listener)
   {
      if (listener != null && !listeners.contains(listener))
      {
         listeners.add(listener);
      }
   }

   /**
    * notify registered listeners that the cursor location has changed.
    */
   public void notifyListeners()
   {
      Location location = getCurrentLocation();
      for(CursorListener listener : listeners)
      {
         listener.cursor(location);
      }
   }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse clicked event.
    */
   public void mouseClicked(MouseEvent event)
   {
/*    todo: for debugging purposes only - remove when finished debugging
      System.out.println("mouse clicked");
      System.out.println("description...: " + event.paramString());
      System.out.println("relative point: " + event.getX()         + ", " + event.getY()        );
      System.out.println("absolute point: " + event.getXOnScreen() + ", " + event.getYOnScreen());
      System.out.println("---------------------------------------------------------------------");
*/
      if (event.getButton() == MouseEvent.BUTTON1)
      {
         Point clickPoint = new Point(event.getX(), event.getY());
         Location newLocation = toLocation(clickPoint);
         if (newLocation != null)
         {
            setLocation(newLocation);
            getContentPane().repaint();
         }
      }
   }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse entered event.
    */
   public void mouseEntered(MouseEvent event) { }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse exited event.
    */
   public void mouseExited(MouseEvent event) { }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse pressed event.
    */
   public void mousePressed(MouseEvent event) { }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse released event.
    */
   public void mouseReleased(MouseEvent event) { }

   /**
    *
    */
   public void mouseMoved(MouseEvent event) { }

   public void mouseDragged(MouseEvent event)
   {
      System.out.println("mouse dragged");
      System.out.println("description...: " + event.paramString());
      System.out.println("relative point: " + event.getX()         + ", " + event.getY()        );
      System.out.println("absolute point: " + event.getXOnScreen() + ", " + event.getXOnScreen());
      System.out.println("---------------------------------------------------------------------");
   }

   @Override
   public void keyPressed(KeyEvent ke)
   {
      // Handle the new location.
      Location newLocation = toLocation(ke);

      // Assign the new location.
      setLocation(newLocation);
   }

   @Override
   public void keyReleased(KeyEvent ke) { }

   @Override
   public void keyTyped(KeyEvent e) { }

   /** Assigns a new location, given a point clicked on the screen.
    *  @param point
    *  @return
    */
   private Location toLocation(Point point)
   {
      // Translate the point to a location on the song.
      CursorMap cursorMap = getSongWindowPanel().getSong().getCursorMap();
      Location l = CursorUtil.mapLocation(point, this.song, cursorMap);

      // Get the current location.
      Location currentLocation = getCurrentLocation();

      boolean isLocationChanged;
      if (currentLocation == null || l == null)
      {
         isLocationChanged = (currentLocation != l);
      }
      else
      {
         isLocationChanged = !currentLocation.equals(l);
      }

      // Handle the new location.
      if (isLocationChanged)
      {
         syncRepaint();
         if (l != null)
         {
            // Return focus to the song panel.
            getSongWindowPanel().requestFocusInWindow();
         }
      }

      return l;
   }
   
   /** Assigns a new location given a keyboard event.
    *  @param ke key event
    *  @return
    */
   private Location toLocation(KeyEvent ke)
   {
      Location newLocation = null;
      try
      {
         // Obtain the new location from the old location and the keyboard event.
         Song song = getSongWindowPanel().getSong();
         Location currentLocation = getCurrentLocation();
         newLocation = CursorUtil.mapLocation(ke, song, currentLocation);

         boolean isLocationChanged;
         if (currentLocation == null || newLocation == null)
         {
            isLocationChanged = (currentLocation != newLocation);
         }
         else
         {
            isLocationChanged = !currentLocation.equals(newLocation);
         }

         if (isLocationChanged)
         {
            // Handle the new location.
//          System.out.println("OLD LOCATION: " + oldLoc);
//          System.out.println("NEW LOCATION: " + newLoc);
            if (newLocation != null)
            {
               handleNewLocation(newLocation);
            }

            // The keyboard action resulted in a location change, so consume
            // the event so it won't be handled by the scroll panel.
            ke.consume();
         }

         // Return focus to the song panel.
         getSongWindowPanel().requestFocusInWindow();
      }
      catch (RuntimeException re)
      {
         System.err.println(re);
         re.printStackTrace();
      }
      catch (Exception e)
      {
         // Couldn't handle the key event.
         ke.consume();
      }

      return newLocation;
   }

   /**
    * Verifies the new location is within the viewport; scrolls if needed.
    * Only used for keyboard movement.
    * @param newLoc
    */
   private void handleNewLocation(Location newLoc) throws Exception
   {
      // If the new cursor's bounds lie outside the viewport,
      // we need to adjust the viewport's origin.
      // Access the viewport location and dimensions.
      JScrollPane scroller = getSongWindowScroller();
      JViewport viewport = scroller.getViewport();
      Point viewportOrigin = viewport.getViewPosition();
      Dimension viewportExtent = viewport.getExtentSize();

      // Get the cursor bounds, adjusted for the viewport.
      Rectangle cursorBounds = getCursorBounds(newLoc);
      cursorBounds.x += viewportOrigin.x;
      cursorBounds.y += viewportOrigin.y;

      // Check if cursor's left edge is to the left of the viewport, or
      // if the cursor's right edge is to the right of the viewport.
      if ((viewportOrigin.x > cursorBounds.x)
          || ((viewportOrigin.x + viewportExtent.width) < cursorBounds.getMaxX()))
      {
         // Center the viewport horizontally.
         int xDiff = (viewportExtent.width - cursorBounds.width)/2;
         viewportOrigin.x = cursorBounds.x - xDiff;
         if (viewportOrigin.x < 0)
         {
            viewportOrigin.x = 0;
         }
         else
         {
            final int maxX = viewport.getView().getWidth()
                             - viewportExtent.width;
            if (viewportOrigin.x > maxX)
            {
               viewportOrigin.x = maxX;
            }
         }
      }

      // Check if cursor's top edge is above the viewport, or
      // if the cursor's bottom edge is below the viewport.
      if ((viewportOrigin.y > cursorBounds.y)
          || ((viewportOrigin.y + viewportExtent.height) <
              (cursorBounds.y + cursorBounds.height)))
      {
         // Center the viewport vertically.
         int yDiff = (viewportExtent.height - cursorBounds.height)/2;
         viewportOrigin.y = cursorBounds.y - yDiff;
         if (viewportOrigin.y < 0)
         {
            viewportOrigin.y = 0;
         }
         else
         {
            final int maxY = viewport.getView().getHeight()
                             - viewportExtent.height;
            if (viewportOrigin.y > maxY)
            {
               viewportOrigin.y = maxY;
            }
         }
      }

      if (!viewportOrigin.equals(viewport.getViewPosition()))
      {
         // Update the viewport's origin.
//         System.out.println("OLD VIEWPORT = " + viewport.getViewPosition());
//         System.out.println("NEW VIEWPORT = " + viewportOrigin);
         // Toss keyboard event if repaint is pending.
         if (isRepaintPending())
         {
            throw new Exception("can't handle new location, repaint pending");
         }
         viewport.setViewPosition(viewportOrigin);
      }

      // Repaint the pane so the new location can be drawn.
      syncRepaint();
   }

   /** Returns the bounds of the given location.
    *  Helpful in ensuring the cursor is visible at all times.
    *  @param l
    *  @return
    *  @throws RuntimeException if cursor location is unknown
    */
   private Rectangle getCursorBounds(Location l) throws RuntimeException
   {
      CursorMap cursorMap = getSongWindowPanel().getSong().getCursorMap();
      Rectangle cursor = cursorMap.getCursorBounds(l);
      cursor = cursorMap.transform(cursor);
      return cursor;
   }   

   /** Returns true if a repaint event is still pending.
    *  @return
    */
   private boolean isRepaintPending()
   {
      return (this.lastRepaintCounter == this.paintCounter);
   }

   /** Issues a repaint if one is not already pending.
    * 
    */
   private void syncRepaint()
   {
      if (!isRepaintPending())
      {
         this.lastRepaintCounter = this.paintCounter;
         repaint();
      }
   }

   @Override
   public void paint(Graphics g)
   {
      super.paint(g);
      ++paintCounter;
   }
}
