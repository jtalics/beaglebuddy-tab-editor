/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.diagram;

import com.beaglebuddy.tab.gui.mainframe.MenuItemInfo;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.Fretting;
import com.beaglebuddy.tab.model.attribute.chord.FingerFretHand;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;




/**
 * allows a user to edit a chord diagram, which is drawn in the following manner:
 * 1. chord name
 * 2. X's and O's indicating unused and open strings, and top fret of the chord diagram
 * 3. fretboard showing fretted notes
 * 4. fingering
 * <p>
 * note: there are calls to repaint() that force the panel to redraw itself.
 *       repaint() was used because, contrary to the documentation, neither calling invalidate() on the panel nor calling pack() on the parent dialog worked.
 * </p>
 */
public class ChordDiagramEditor extends ChordDiagramRenderer implements ActionListener, ItemListener, MouseListener
{
   private static final Dimension FRETBOARD_PANEL_DIMENSION_6_STRING = new Dimension(145, 145);
   private static final Dimension FRETBOARD_PANEL_DIMENSION_7_STRING = new Dimension(155, 145);

   // controls
   private   JComboBox   comboBoxTopFret;            // the top fret of the fretboard
   protected Rectangle[] stringBoundingRectangles;   // array of rectangles, one for each string       , that is used for detecting mouse clicks on a string on the fretboard.
   protected Rectangle[] fretBoundingRectangles;     // array of rectangles, one for each fret position, that is used for detecting mouse clicks on a fret   on the fretboard.
   protected JPopupMenu  popupMenuFingering;         // popup menu which displays the list of fingers for fretting a note on the fretboard

   // data members
   private Fretting   fretting;                      // the current string and fret selected for editing - needed by the popup menu action handler
   private boolean    dirty;                         // dirty flag indicating whether the chord diagram has been modified.
   private Observable observable;                    // used to notify objects when the chord diagram is edited - note: this is not the java observable, but rather a hacked up one




   /**
    * default constructor.
    */
   public ChordDiagramEditor()
   {
      this(new ChordDiagram(), Instrument.Fret.Not_Used, null, false);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param chordDiagram     chord diagram to edit.
    * @param capo             if the instrument uses a capo, then the fret at which it is installed.
    * @param observer         object that wishes to be notified whenever the user edits the chord diagram
    * @param hasBeenChanged   whether to mark the chord diagram as having been changed or not.
    */
   public ChordDiagramEditor(ChordDiagram chordDiagram, Instrument.Fret capo, Observer observer, boolean hasBeenChanged)
   {
      // create a panel with a white background which will hold all the other panels/controls
      super(chordDiagram, ChordDiagramRenderer.Size.Large);

      observable = new Observable();
      if (observer != null)
         observable.addObserver(observer);

      // set the size and color of the panel
      addMouseListener(this);
      setBorder(BorderFactory.createTitledBorder(""));
      setOpaque(true);        // setting opaque to true allows calls to setBackground() to work.
      setForeground   (Color.BLACK);
      setBackground   (Color.WHITE);
      setMinimumSize  (chordDiagram.getNumStrings() == 6 ? FRETBOARD_PANEL_DIMENSION_6_STRING : FRETBOARD_PANEL_DIMENSION_7_STRING);
      setMaximumSize  (chordDiagram.getNumStrings() == 6 ? FRETBOARD_PANEL_DIMENSION_6_STRING : FRETBOARD_PANEL_DIMENSION_7_STRING);
      setPreferredSize(chordDiagram.getNumStrings() == 6 ? FRETBOARD_PANEL_DIMENSION_6_STRING : FRETBOARD_PANEL_DIMENSION_7_STRING);

      // create the top fret combo box that will appear at the top right of the fretboard
      // if the instrument uses a capo, then only allow the user to choose frets above the capo
      int      numFrets      = Instrument.MAX_NUM_FRETS - attributes.getNumFrets() + 1 - (capo == Instrument.Fret.Not_Used ? 0 : capo.ordinal() - 1);
      int      selectedIndex = -1;
      Object[] frets         = new Object[numFrets];

      for(int i=0, fret=(capo == Instrument.Fret.Not_Used ? 1 : capo.ordinal()); i<numFrets; ++i, fret++)
      {
         frets[i] = String.valueOf(fret);
         if (chordDiagram.getTopFret().ordinal() == (fret-1))
            selectedIndex = i;
      }

      comboBoxTopFret = new JComboBox(frets);
      comboBoxTopFret.setToolTipText(ResourceBundle.getString("dialog.chord_diagrams.tooltip.top_fret"));
      comboBoxTopFret.setEditable(false);
      comboBoxTopFret.setSelectedIndex(selectedIndex);
      comboBoxTopFret.setMaximumRowCount(5);
      comboBoxTopFret.addItemListener(this);

      // the bounding rectangles for each string and fret are initialized in the paint() method, analagous to the base class, since the graphics object is needed.

      // create the popup menu for the fingering
      popupMenuFingering = buildPopupMenu();

      // initialize the chord diagram as not having been modified
      dirty = hasBeenChanged;

      // layout the controls in the white panel
      GroupLayout layout = new GroupLayout(this);
      setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap(72, Short.MAX_VALUE)
            .addComponent(comboBoxTopFret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGap(39, 39, 39)
            .addComponent(comboBoxTopFret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      );
   }

   /**
    * implementation of the ActionListener interface handles the user's selection from the fret hand finger popup menu.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   public void actionPerformed(ActionEvent event)
   {
      String finger = event.getActionCommand();
      if (finger.equals(ResourceBundle.getString("dialog.fingering.fret_hand.label.none")))
         fretting.setFinger(null);
      else if (finger.equals(ResourceBundle.getString("dialog.fingering.fret_hand.label.thumb")))
         fretting.setFinger(FingerFretHand.Finger.Thumb);
      else
         fretting.setFinger(FingerFretHand.getFinger(finger));

      // mark the chord diagram as having been modified
      dirty = true;

      // notify observers
      observable.notifyObservers();

      // force the panel to redraw itself
      repaint();
   }

   /**
    * @return the popup menu for the fingering.
    */
   private JPopupMenu buildPopupMenu()
   {
      JPopupMenu     menu          = new JPopupMenu   ("popup menu label");
      MenuItemInfo[] menuItemInfos = {new MenuItemInfo(ResourceBundle.getString("dialog.fingering.fret_hand.label.none") , null, null, null, null, this, JMenuItem.class),
                                      new MenuItemInfo(ResourceBundle.getString("dialog.fingering.fret_hand.label.thumb"), null, null, null, null, this, JMenuItem.class),
                                      new MenuItemInfo("1"                                                               , null, null, null, null, this, JMenuItem.class),
                                      new MenuItemInfo("2"                                                               , null, null, null, null, this, JMenuItem.class),
                                      new MenuItemInfo("3"                                                               , null, null, null, null, this, JMenuItem.class),
                                      new MenuItemInfo("4"                                                               , null, null, null, null, this, JMenuItem.class)};

      for(MenuItemInfo menuItemInfo : menuItemInfos)
      {
         if (menuItemInfo.isSeparator())
            menu.addSeparator();
         else
            menu.add(menuItemInfo.getMenuItem());
      }
      return menu;
   }

   /**
    * @return whether the chord diagram has been modified.
    */
   public boolean hasBeenChanged()
   {
      return dirty;
   }

   /**
    * initialize the bounding rectangles for vertical strings as well as the horizontal fret positions.
    * these rectangles are used to detect where on the fretboard a user clicked their mouse.
    * <br/><br/>
    * @param graphics   the graphics context used to draw on the panel.
    */
   private void initializeBoundingRectangles(Graphics graphics)
   {
      // create a bounding rectangle for each string on the fretboard that also includes the open/unused string symbols and the fingerings at the bottom of the fretboard
      stringBoundingRectangles = new Rectangle[chordDiagram.getNumStrings()];
      int x      = attributes.getXStart() - attributes.getDistanceBetweenStrings()/2;
      int y      = attributes.getYChordName() + attributes.getYKluge() - attributes.getNutHeight();
      int width  = attributes.getDistanceBetweenStrings() + 1;
      int length = attributes.getYFingeringNoteNames() + attributes.getDistanceBetweenFrets() - attributes.getYOpenUnusedStrings() + attributes.getNutHeight();

      for(int string=stringBoundingRectangles.length - 1; string>=0; --string)
      {
         stringBoundingRectangles[string] = new Rectangle(x, y, width, length);
         x += attributes.getDistanceBetweenStrings();
      }

      // create a bounding rectangle for each fret position, as well as one for the open/unused strings, and one for the fingerings at the bottom of the fretboard
      fretBoundingRectangles = new Rectangle[attributes.getNumFrets() + 1 + 1];
      x      = attributes.getXStart() - attributes.getDistanceBetweenStrings()/2;
      y      = attributes.getYFretboard() - attributes.getDistanceBetweenFrets();
      width  = attributes.getChordDiagramWidth() + attributes.getDistanceBetweenStrings();
      length = attributes.getDistanceBetweenFrets() + 1;

      for(int fret=0; fret<fretBoundingRectangles.length; ++fret)
      {
         fretBoundingRectangles[fret] = new Rectangle(x, y, width, length);
         y += attributes.getDistanceBetweenFrets();
      }
      // add a few pixels on to the height of the open/unused rectangle
      fretBoundingRectangles[0] = new Rectangle((int)fretBoundingRectangles[0].getX(), (int)fretBoundingRectangles[0].getY() - attributes.getNutHeight(), width, length + attributes.getNutHeight());
   }

   /**
    * implements the ItemListener interface and is called whenever the user selects a top fret.
    * <br/><br/>
    * @param event   the top fret combo box selection event.
    */
   public void itemStateChanged(ItemEvent event)
   {
      if (event.getStateChange() == ItemEvent.SELECTED && event.getSource() == comboBoxTopFret)
      {
         Instrument.Fret oldTopFret = chordDiagram.getTopFret();
         Instrument.Fret newTopFret = Instrument.getFret(Integer.valueOf((String)comboBoxTopFret.getSelectedItem()).intValue() - 1);

//       System.out.println("top fret changed to " + newTopFret);
         int diff = newTopFret.ordinal() - oldTopFret.ordinal();
         Fretting[] frettings = chordDiagram.getFretting();
         for(Fretting fretting : frettings)
         {
            if (fretting.getFret() != Instrument.Fret.Open && fretting.getFret() != Instrument.Fret.Not_Used)
               fretting.setFret(Instrument.getFret(fretting.getFret().ordinal() + diff));
         }
         // update the top fret
         chordDiagram.setTopFret(newTopFret);
         // mark the chord diagram as having been modified
         dirty = true;
         // notify observers
         observable.notifyObservers();
         // force the panel to redraw itself
         repaint();
      }
   }

   /**
    * implements the MouseListener interface, and is called whenever the user clicks on the chord diagram panel.
    * <br/><br/>
    * @param event   mouse clicked event.
    */
   public void mouseClicked(MouseEvent event)
   {
      if (event.getButton() == MouseEvent.BUTTON1)
      {
         int string = -1;
         int fret   = -1;

         // determine which string the user clicked on
         for(string=0; string<stringBoundingRectangles.length && !stringBoundingRectangles[string].contains(event.getX(), event.getY()); ++string){}

         // determine which fret the user clicked on, including the open/unused strings (O's and X's), or the fingering at the bottom of the fretboard
         for(fret=0; fret<fretBoundingRectangles.length && !fretBoundingRectangles[fret].contains(event.getX(), event.getY()); ++fret){}

         if (string >= 0 && string < stringBoundingRectangles.length && fret >= 0 && fret < fretBoundingRectangles.length)
         {
//          System.out.println("User clicked on string " + string + " and fret " + fret);
            fretting = chordDiagram.getFretting()[string];

            if (fret == 0)                                      // open/unused strings area
            {
               switch (fretting.getFret())
               {
                  case Open:
                       fretting.setFret(Instrument.Fret.Not_Used);
                  break;
                  case Not_Used:
                       fretting.setFret(Instrument.Fret.Open);
                  break;
                  default:
                       fretting.setFret(Instrument.Fret.Open);
                  break;
               }
               // mark the chord diagram as having been modified
               dirty = true;

               // notify observers
               observable.notifyObservers();
            }
            else if (fret ==  fretBoundingRectangles.length-1)  // fingering area
            {
               // only allow a user to choose a fingering for a string that is being fretted - not for unused or open strings
               if (fretting.getFret() != Instrument.Fret.Not_Used && fretting.getFret() != Instrument.Fret.Open)
                  popupMenuFingering.show(this, event.getX(), event.getY());
            }
            else                                                // fretboard area
            {
               // if the string was open or unused, or was fretted on a different fret, then set the fretting
               if (fretting.getFret() == Instrument.Fret.Open || fretting.getFret() == Instrument.Fret.Not_Used || fretting.getFret() != Instrument.getFret(chordDiagram.getTopFret().ordinal() + fret))
               {
                  fretting.setFret(Instrument.getFret(chordDiagram.getTopFret().ordinal() + fret));
               }
               // otherwise (the user clicked on the same fret), toggle it to unused
               else
               {
                  fretting.setFret(Instrument.Fret.Not_Used);
                  fretting.setFinger(null);
               }
               // mark the chord diagram as having been modified
               dirty = true;

               // notify observers
               observable.notifyObservers();
            }
            // force the panel to redraw itself
            repaint();

         }
//       super.processMouseEvent(event);
      }
      // if the user right clicked the mouse
      else if (event.isPopupTrigger())
      {
         int string = -1;
         int fret   = -1;

         // determine which string the user clicked on
         for(string=0; string<stringBoundingRectangles.length && !stringBoundingRectangles[string].contains(event.getX(), event.getY()); ++string){}

         // determine which fret the user clicked on, including the open/unused strings (O's and X's), or the fingering at the bottom of the fretboard
         for(fret=0; fret<fretBoundingRectangles.length && !fretBoundingRectangles[fret].contains(event.getX(), event.getY()); ++fret){}

         if (string >= 0 && string < stringBoundingRectangles.length && fret >= 0 && fret < fretBoundingRectangles.length)
         {
            System.out.println("User right clicked on string " + string + " and fret " + fret);
            if (fret ==  fretBoundingRectangles.length-1)  // fingering area
               popupMenuFingering.show(this, event.getX(), event.getY());
         }
      }
   }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse entered event.
    */
   public void mouseEntered(MouseEvent event)
   {
      // no code necessary
   }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse exited event.
    */
   public void mouseExited(MouseEvent event)
   {
      // no code necessary
   }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse pressed event.
    */
   public void mousePressed(MouseEvent event)
   {
      // no code necessary
   }

   /**
    * implements the MouseListener interface.
    * <br/><br/>
    * @param event   mouse released event.
    */
   public void mouseReleased(MouseEvent event)
   {
      // no code necessary
   }

   /**
    * paint the chord diagram in the panel.
    * <br/><br/>
    * @param graphics   the graphics context used to draw on the panel.
    */
   @Override
   public void paint(Graphics graphics)
   {
      super.paint(graphics);

      if (chordDiagram != null && stringBoundingRectangles == null)
         initializeBoundingRectangles(graphics);
   }

   /**
    * resets whether the chord diagram has been modified to false.
    */
   public void resetHasBeenChanged()
   {
      dirty = false;
   }

   /**
    * set the chord diagram to be drawn.
    * <br/><br/>
    * @param chordDiagram   chord diagram being edited.
    */
   @Override
   public void setChordDiagram(ChordDiagram chordDiagram)
   {
      super.setChordDiagram(chordDiagram);

      // set the selected top fret in the combo box
      int selectedIndex = -1;
      int topFret       = chordDiagram.getTopFret().ordinal();
      for(int index=0; index < comboBoxTopFret.getItemCount() && selectedIndex == -1; ++index)
      {
         int fret = Integer.valueOf(((String)comboBoxTopFret.getItemAt(index)));
         if (topFret == (fret-1))
            selectedIndex = index;
      }
      comboBoxTopFret.setSelectedIndex(selectedIndex);
   }

   /**
    * sets the name of the chord diagram being edited.
    * <br/><br/>
    * @param chordName   the new name for the chord diagram.
    */
   @Override
   public void setChordName(String chordName)
   {
      super.setChordName(chordName);
      dirty = true;
   }
}
