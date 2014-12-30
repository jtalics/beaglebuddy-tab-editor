/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.layout;

import java.awt.Rectangle;




/**
 * This class is used by the PoinLayout manager to control the placement of
 * componenents within a container.  Users can specify a component's alsolute
 * location or a location relative to the previous component.  The component's
 * initial size is also specified using this class.
 *
 * To specify a location in absolute coordinates, simply use positive values
 * for the x and y parameters.  A negative value means use the last component's
 * properties as a reference.  For example, specifying x = -10 means that this
 * component's x coordinate will be previousComponent.x + previousComponent.width + 10.
 * For width and height, -1 means use the value from the previous component.
 */
public class PointLayoutProperties
{
   // data members
   private int                    x;
   private int                    y;
   private int                    width;
   private int                    height;
   private PointLayout.ResizeMode resizeMode;



   /**
    * constructor.
    * the resize mode is set to PointLayout.NOACTION by default.
    * <br/><br/>
    * @param x        the x location of the component - see class description for more information.
    * @param y        the y location of the component - see class description for more information.
    * @param height   the height of the component.
    * @param width    the width  of the component.
    */
   public PointLayoutProperties(int x, int y, int height, int width)
   {
      this(x, y, height, width, PointLayout.ResizeMode.NOACTION);
   }

   /**
    * constructor
    * <br/><br/>
    * @param x        the x location of the component - see class description for more information.
    * @param y        the y location of the component - see class description for more information.
    * @param height   the height of the component.
    * @param width    the width  of the component.
    * @param mode     resize mode.
    */
   public PointLayoutProperties(int x, int y, int height, int width, PointLayout.ResizeMode mode)
   {
      this.x          = x;
      this.y          = y;
      this.width      = width;
      this.height     = height;
      this.resizeMode = mode;
   }

   /**
    * constructor.
    * the resize mode is set to PointLayout.NOACTION by default.
    * <br/><br/>
    * @param rectangle  bounding rectangle of the component.
    */
   public PointLayoutProperties(Rectangle rectangle)
   {
      this(rectangle, PointLayout.ResizeMode.NOACTION);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param rectangle  bounding rectangle of the component.
    * @param mode       resize mode.
    */
   public PointLayoutProperties(Rectangle rectangle, PointLayout.ResizeMode mode)
   {
      this(rectangle.x, rectangle.y, rectangle.height, rectangle.width, mode);
   }

   /**
    * @return the horizontal x part of the component's location.
    */
   public int getX()
   {
      return(x);
   }

   /**
    * sets the horizontal x part of the component's location.
    * <br/><br/>
    * @param x   the x coordinate.
    */
   public void setX(int x)
   {
      this.x = x;
   }

   /**
    * @return the vertical y part of the component's location.
    */
   public int getY()
   {
      return(y);
   }

   /**
    * set's the vertical y part of the component's location.
    * <br/><br/>
    * @param y   the y coordinate.
    */
   public void setY(int y)
   {
      this.y = y;
   }

   /**
    * @return the height of the component.
    */
   public int getHeight()
   {
      return(height);
   }

   /**
    * sets the height of the component.
    * <br/><br/>
    * @param height   the height of the component.
    */
   public void setHeight(int height)
   {
      this.height = height;
   }

   /**
    * @return the width of the component.
    */
   public int getWidth()
   {
      return(width);
   }

   /**
    * sets the width of the component.
    * <br/><br/>
    * @param width   the width of the component.
    */
   public void setWidth(int width)
   {
      this.width = width;
   }

   /**
    * @return the mode used to resize the component.
    */
   public PointLayout.ResizeMode getResizeMode()
   {
      return(resizeMode);
   }

   /**
    * sets the mode used to resize the component.
    * <br/><br/>
    * @param resizeMode   the resize mode.
    */
   public void setResizeMode(PointLayout.ResizeMode resizeMode)
   {
      this.resizeMode = resizeMode;
   }

   /**
    * @return a string with the description of this object.
    */
   @Override
   public String toString()
   {
      return("PointLayoutProperties: ("+ x + ", " + y + "),  width: " + width + "  height: " + height);
   }
}
