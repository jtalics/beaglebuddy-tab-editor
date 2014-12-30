/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;




/**
 * This class allows for custom component traversal orders that differ from the order in which components were added to a container.
 * <p>
 * usage:
 *    JPanel      panel      = new JPanel();
 *    Component[] components = {jtfField1, jtfField2, jtfField3 jtfField4};
 *    ...
 *    add components to the container
 *    ...
 *    panel.setFocusTraversalPolicy(new ICFocusTraversalPolicy(components));
 * </p>
 */
public class CustomFocusTraversalPolicy extends FocusTraversalPolicy
{
   // data members
   private ArrayList<Component> components;



   /**
    * constructor.
    * <br/><br/>
    * @param components   list of components in the order in which they are to be traversed.
    */
   public CustomFocusTraversalPolicy(Component[] components)
   {
      this.components = new ArrayList<Component>(components == null ? 0 : components.length);

      if (components != null)
      {
         for(Component component : components)
            if (isTraversable(component))
               this.components.add(component);
      }
   }

   /**
    * returns the component that should receive the focus after the specified component.
    * <br/><br/>
    * @param container   a focus cycle root of the specified component or a focus traversal policy provider.
    * @param component   a (possibly indirect) child of the specified container, or the container itself.
    * <br/><br/>
    * @throws IllegalArgumentException  if container is not a focus cycle root of component or a focus traversal policy provider, or if either container or component is null.
    */
   @Override
   public Component getComponentAfter(Container container, Component component) throws IllegalArgumentException
   {
      if (container == null)
         throw new IllegalArgumentException("Invalid container specified.  It can not be null.");
      if (component == null)
         throw new IllegalArgumentException("Invalid component specified.  It can not be null.");

      int index = components.indexOf(component);
      return index == -1 ? null : components.get((index == (components.size() - 1) ? 0 : index + 1));
   }

   /**
    * returns the component that should receive the focus before the specified component.
    * <br/><br/>
    * @param container   a focus cycle root of the specified component or a focus traversal policy provider.
    * @param component   a (possibly indirect) child of the specified container, or the container itself.
    * <br/><br/>
    * @throws IllegalArgumentException  if container is not a focus cycle root of component or a focus traversal policy provider, or if either container or component is null.
    */
   @Override
   public Component getComponentBefore(Container container, Component component) throws IllegalArgumentException
   {
      if (container == null)
         throw new IllegalArgumentException("Invalid container specified.  It can not be null.");
      if (component == null)
         throw new IllegalArgumentException("Invalid component specified.  It can not be null.");

      int index = components.indexOf(component);
      return index == -1 ? null : components.get((index == 0 ? components.size() - 1 : index - 1));
   }

   /**
    * returns the default component to focus.
    * this component will be the first to receive focus when traversing down into a new focus traversal cycle rooted at the specified container.
    * <br/><br/>
    * @param container   the focus cycle root or focus traversal policy provider whose first component is to be returned.
    * <br/><br/>
    * @throws IllegalArgumentException   if the container is null.
    * <br/><br/>
    * @return the default component to focus.
    */
   @Override
   public Component getDefaultComponent(Container container) throws IllegalArgumentException
   {
      if (container == null)
         throw new IllegalArgumentException("Invalid container specified.  It can not be null.");

      return getFirstComponent(container);
   }

   /**
    * returns the first component in the traversal cycle.
    * this method is used to determine the next component to focus when traversal wraps in the forward direction.
    * <br/><br/>
    * @param container   the focus cycle root or focus traversal policy provider whose first component is to be returned.
    * <br/><br/>
    * @throws IllegalArgumentException   if the container is null.
    * <br/><br/>
    * @return the first component in the traversal cycle.
    */
   @Override
   public Component getFirstComponent(Container container) throws IllegalArgumentException
   {
      if (container == null)
         throw new IllegalArgumentException("Invalid container specified.  It can not be null.");

      return (components.size() == 0 ? null : components.get(0));
   }

   /**
    * returns the last component in the traversal cycle.
    * this method is used to determine the next component to focus when traversal wraps in the reverse direction.
    * <br/><br/>
    * @param container   the focus cycle root or focus traversal policy provider whose last component is to be returned.
    * <br/><br/>
    * @throws IllegalArgumentException   if the container is null.
    * <br/><br/>
    * @return the last component in the traversal cycle.
    */
   @Override
   public Component getLastComponent(Container container) throws IllegalArgumentException
   {
      if (container == null)
         throw new IllegalArgumentException("Invalid container specified.  It can not be null.");

      return (components.size() == 0 ? null : components.get(components.size() - 1));
   }

   /**
    * @return whether the specified component can be in the focus traversal policy.
    * <br/><br/>
    * @param component   a (possibly indirect) child of the specified container, or the container itself.
    */
   private boolean isTraversable(Component component)
   {
      return  component.isVisible() && component.isDisplayable() && component.isFocusable() && component.isEnabled();
   }
}
