/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
//import java.awt.Rectangle;
import java.util.Vector;




/**
 * This layout manager class uses PointLayoutProperties to arrange components within a container.
 * The properties specify the location and size of the component in pixels.
 */
public class PointLayout implements LayoutManager2
{
   // resize modes
   public enum ResizeMode {NOACTION                   ,
                           MOVE_VERTICAL              ,
                           MOVE_HORIZONTAL            ,
                           MOVE_VERTICAL_CENTER       ,
                           MOVE_HORIZONTAL_CENTER     ,
                           MOVE_BOTH                  ,
                           MOVE_BOTH_CENTER           ,
                           MOVE_BOTH_VERTICAL_CENTER  ,
                           MOVE_BOTH_HORIZONTAL_CENTER,
                           SIZE_VERTICAL              ,
                           SIZE_HORIZONTAL            ,
                           SIZE_BOTH}

   // data members
   private Vector<Component>             components;
   private Vector<PointLayoutProperties> properties;
   private Dimension                     oldSize;
   private PointLayoutProperties         prevProperties;



   /**
    * default constructor.
    */
   public PointLayout()
   {
      components     = new Vector<Component>();
      properties     = new Vector<PointLayoutProperties>();
      oldSize        = new Dimension(0, 0);
      prevProperties = new PointLayoutProperties(0, 0, 0, 0);
   }

   /**
    * Set the Insets object on this container.
    * If the parent window already has insets, do not set the insets for the layout manager.
    * <br/><br/>
    * @param insets   insets to use for this layout manager.
    */
   public void setInsets(Insets insets)
   {
      // no code necessary
   }

   /**
    * @return the current Insets object being used for this container.
    */
   public Insets getInsets()
   {
      return new Insets(0,0,0,0);
   }

   /**
    * Add the specified component with the specified name.
    * @deprecated Use addLayoutComponent(Component, Object) instead.
    */
   @Deprecated
  public void addLayoutComponent(String name, Component c)
   {
      // this should never get called
      // can't throw an exception, because then it won't match the LayoutManager2
      // interface.
      System.err.println("PointLayout.addLayoutComponent() not supported.");
   }

   public PointLayoutProperties getComponentProperties(Component c)
   {
      PointLayoutProperties properties = null;

      for(int i=0; i <components.size() && properties == null; i++)
      {
         if (c == components.elementAt(i))
            properties = this.properties.elementAt(i);
      }
      return(properties);
   }

   /**
    * Add the specified component and a PointLayoutProperties object
    * that specifies its location and behavior when the container
    * is resized.
    * @see PointLayoutProperties
    */
   public void addLayoutComponent(Component c, Object p)
   {
      if (!(p instanceof PointLayoutProperties))
         throw new IllegalArgumentException("Constraints object is not a PointLayoutProperties: " + p.getClass().getName());

      components.addElement(c);
      PointLayoutProperties properties = (PointLayoutProperties)p;

      // Check for properties relative to the last component
      if (properties.getX() < 0)
         properties.setX(prevProperties.getX() + prevProperties.getWidth() - properties.getX());
      else if (properties.getX() == 0)
         properties.setX(prevProperties.getX());

      if (properties.getY() < 0)
         properties.setY(prevProperties.getY() + prevProperties.getHeight() - properties.getY());
      else if (properties.getY() == 0)
         properties.setY(prevProperties.getY());

      if (properties.getWidth() <= 0)
         properties.setWidth(prevProperties.getWidth() + properties.getWidth());

      if (properties.getHeight() <= 0)
         properties.setHeight(prevProperties.getHeight() + properties.getHeight());

      this.properties.addElement(properties);
      prevProperties = properties;
   }

   /**
    * Remove the specified component from the layout manager
    */
   public void removeLayoutComponent(Component c)
   {
      int i;
      for (i=0;i<components.size();i++)
      {
         if (c == components.elementAt(i))
         {
            components.removeElementAt(i);
            this.properties.removeElementAt(i);
            break;
         }
      }
   }

   /**
    * Returns the minimum size of the container
    */
   public Dimension minimumLayoutSize(Container target)
   {
      return new Dimension(0,0);
      //return preferredLayoutSize(target);
   }

   /**
    * Returns the preferred size of the container
    */
   public Dimension preferredLayoutSize(Container target)
   {
      PointLayoutProperties properties;
      int w = 0,
          h = 0;

      for(int i=0; i<this.properties.size(); i++)
      {
         properties = this.properties.elementAt(i);
         if ((properties.getX() + properties.getWidth()) > w)
            w = properties.getX() + properties.getWidth();
         if ((properties.getY() + properties.getHeight()) > h)
            h = properties.getY() + properties.getHeight();
      }
      oldSize.width  = w + 10;
      oldSize.height = h + 7;

      return new Dimension(w, h);
   }

   /**
    * Returns the maximum size of the container
    */
   public Dimension maximumLayoutSize(Container target)
   {
      return preferredLayoutSize(target);
   }

   /**
    * Returns the X alignment property of the layout manager
    * (Always 0.0 for PointLayout)
    */
   public float getLayoutAlignmentX(Container target)
   {
      return (float)0.0;
   }

   /**
    * Returns the Y alignment property of the layout manager
    * (Always 0.0 for PointLayout)
    */
   public float getLayoutAlignmentY(Container target)
   {
      return (float)0.0;
   }

   /**
    * Invalidate any cached layout information.
    * PointLayout doesn't cache any information so this is an empty call.
    */
   public void invalidateLayout(Container target)
   {
   }

   /**
    * Cause the layout manager to layout the components in the container.
    */
   public void layoutContainer(Container target)
   {
//      Rectangle bounds  = target.getBounds();
      Dimension newSize = target.getSize();

      if (newSize.width == 0 && newSize.height == 0)
         newSize = preferredLayoutSize(target);

      int dx = newSize.width - oldSize.width;
      int dy = newSize.height - oldSize.height;

      Component             c;
      PointLayoutProperties p;

      for( int i=0; i < components.size(); i++)
      {
         c = components.elementAt(i);
         p = this.properties.elementAt(i);

         switch(p.getResizeMode())
         {
            case NOACTION:
            break;
            case MOVE_BOTH:
                 p.setX(p.getX() + dx);
                 p.setY(p.getY() + dy);
            break;
            case MOVE_BOTH_CENTER:
                 p.setX(p.getX() + (dx / 2));
                 p.setY(p.getY() + (dy / 2));
            break;
            case MOVE_BOTH_HORIZONTAL_CENTER:
                 p.setX(p.getX() + (dx / 2));
                 p.setY(p.getY() + dy);
            break;
            case MOVE_BOTH_VERTICAL_CENTER:
                 p.setX(p.getX() + dx);
                 p.setY(p.getY() + (dy / 2));
            break;
            case MOVE_VERTICAL:
                 p.setY(p.getY() + dy);
            break;
            case MOVE_VERTICAL_CENTER:
                 p.setY(p.getY() + (dy / 2));
            break;
            case MOVE_HORIZONTAL:
                 p.setX(p.getX() + dx);
            break;
            case MOVE_HORIZONTAL_CENTER:
                 p.setX(p.getX() + (dx / 2));
            break;
            case SIZE_VERTICAL:
                 p.setHeight(p.getHeight() + dy);
            break;
            case SIZE_HORIZONTAL:
                 p.setWidth(p.getWidth() + dx);
            break;
            case SIZE_BOTH:
                 p.setWidth (p.getWidth () + dx);
                 p.setHeight(p.getHeight() + dy);
            break;
         }
         int x = (p.getX()      >= 0 ? p.getX()      : 0);
         int y = (p.getY()      >= 0 ? p.getY()      : 0);
         int w = (p.getWidth()  >= 0 ? p.getWidth()  : 0);
         int h = (p.getHeight() >= 0 ? p.getHeight() : 0);

         c.setBounds(x,y,w,h);
      }
      oldSize = newSize;
   }
}
