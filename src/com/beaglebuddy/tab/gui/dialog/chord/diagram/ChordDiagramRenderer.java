/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.chord.diagram;

import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.Fretting;
import com.beaglebuddy.tab.model.instrument.Instrument;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.JPanel;




/**
 * renders a chord diagram in the selected size in the following manner:
 * 1. chord name
 * 2. X's and O's indicating unused and open strings
 * 3. fretboard showing fretted notes
 * 4. optional fingerings at the bottom of the fretboard
 */
public class ChordDiagramRenderer extends JPanel
{
   // enums
   public enum Size {Small, Large}

   // class members
   private static HashMap<Size, ChordDiagramRenderAttributes> sizeAttributesMap = new HashMap<Size, ChordDiagramRenderAttributes>();

   // data members
   protected ChordDiagram                 chordDiagram;               // chord diagram to be drawn
   protected ChordDiagramRenderAttributes attributes;                 // attributes of the chord diagram



   // static initialization block
   static
   { //                                                                   #   cir   nut    x      y    y    name   text
     //                                                                   str rad   hgt   strt   strt klug  size   size    editing
      sizeAttributesMap.put(Size.Small , new ChordDiagramRenderAttributes(6,   2,    2,     5,     3,   0,   10,    10,    false));
      sizeAttributesMap.put(Size.Large , new ChordDiagramRenderAttributes(6,   5,    3,    10,     5,  15,   12,    10,    true ));
   }




   /**
    * constructor.
    * <br/><br/>
    * @param size   relative size of the chord diagram drawing.
    */
   public ChordDiagramRenderer(Size size)
   {
      this(new ChordDiagram(), size);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param chordDiagram   chord diagram to draw.
    * @param size           relative size of the chord diagram drawing.
    */
   public ChordDiagramRenderer(ChordDiagram chordDiagram, Size size)
   {
      this.chordDiagram = chordDiagram;
      this.attributes   = sizeAttributesMap.get(size);

      if (chordDiagram != null)
         this.attributes.setNumStrings(chordDiagram.getNumStrings());
   }

   /**
    * @return the chord diagram being drawn.
    */
   public ChordDiagram getChordDiagram()
   {
      return chordDiagram;
   }

   /**
    * set the chord diagram to be drawn.
    * <br/><br/>
    * @param chordDiagram   chord diagram being edited.
    */
   public void setChordDiagram(ChordDiagram chordDiagram)
   {
      this.chordDiagram = chordDiagram;

      if (chordDiagram != null)
         this.attributes.setNumStrings(chordDiagram.getNumStrings());
   }

   /**
    * sets the name of the chord diagram being edited.
    * <br/><br/>
    * @param chordName   the new name for the chord diagram.
    */
   public void setChordName(String chordName)
   {
      chordDiagram.setName(chordName);
   }

   /**
    * @return the attributes of the chord diagram being drawn.
    */
   public ChordDiagramRenderAttributes getChordDiagramRenderAttributes()
   {
      return attributes;
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

      if (chordDiagram != null)
      {
         Fretting[]   fretting     = chordDiagram.getFretting();
         int          numStrings   = fretting.length;
         boolean      topFretIsNut = chordDiagram.getTopFret() == Instrument.Fret.Open;
         Font         dialogFont   = graphics.getFont();
         int          x            = attributes.getXStart();
         int          y            = attributes.getYStart();

         // set the font
         graphics.setFont(new Font(dialogFont.getFontName(), Font.BOLD, attributes.getFontNamePointSize()));
         FontMetrics  fontMetrics  = graphics.getFontMetrics();
         int          charHeight   = fontMetrics.getAscent();

         // set some attributes that are dependent on the instrument and the font being used to draw the diagram
         attributes.setNumStrings         (fretting.length);
         attributes.setYChordName         (attributes.getYStart() + fontMetrics.getAscent());
         attributes.setYOpenUnusedStrings (attributes.getYChordName() + attributes.getYKluge() + charHeight - fontMetrics.getDescent());
         attributes.setYFretboard         (attributes.getYOpenUnusedStrings() + fontMetrics.getDescent() + attributes.getNutHeight());
         attributes.setYFingeringNoteNames(attributes.getYFretboard() + attributes.getChordDiagramHeight() + fontMetrics.getDescent() + charHeight);

         // draw the chord name centered above the fretboard in bold
         String chordDiagramName = chordDiagram.getName();
         if (chordDiagramName != null && chordDiagramName.trim().length() != 0)
         {
            // get the width of the chord name so that it can be centered above the fretboard
            graphics.setFont(new Font(dialogFont.getFontName(), Font.BOLD, attributes.getFontNamePointSize()));
            char[] characters = new char[chordDiagramName.length()];
            chordDiagramName.getChars(0, characters.length, characters, 0);
            int nameWidth = fontMetrics.charsWidth(characters, 0, characters.length);

            x = attributes.getXStart();
            y = attributes.getYStart();
            graphics.drawString(chordDiagramName, x + (attributes.getChordDiagramWidth() / 2) - (nameWidth / 2), attributes.getYChordName());
            graphics.setFont(new Font(dialogFont.getFontName(), Font.PLAIN, attributes.getFontTextPointSize()));
         }

         // draw the fretboard's frets (horizontal lines)
         y = attributes.getYFretboard();
         for(int fret=0; fret<=attributes.getNumFrets(); ++fret)
         {
            graphics.drawLine(x, y, x + attributes.getChordDiagramWidth(), y);
            y += attributes.getDistanceBetweenFrets();
         }

         // draw the fretboard's strings (vertical lines)
         y = attributes.getYFretboard();
         for(int string=0; string<numStrings; ++string)
         {
            graphics.drawLine(x, y, x, y + attributes.getChordDiagramHeight());
            x += attributes.getDistanceBetweenStrings();
         }

         // draw the top fret - a thick bar for the nut, and a thin line for all other frets
         y = attributes.getYFretboard();
         if (topFretIsNut)
            graphics.fillRect(attributes.getXStart(), y - attributes.getNutHeight(), attributes.getChordDiagramWidth() + 1, attributes.getNutHeight());

         // draw the top fret + 1 number off to the right of the fretboard if the top fret is not the nut
         // the top fret indicates the physical fret shown as the top of the fretboard diagram.
         // however, we render the first fretting position, and hence add 1 to it.
         // ex: if you were to fret the 1st string at the 5th fret, you would place your finger between the 4th and 5th frets.
         //     thus, the physical top fret is 4, although you are said to be placing your finger at the 5th fret.
         //     we draw the fretting position, not the physical top fret.
         if (!attributes.isEditing() && !topFretIsNut)
         {
            Font fretFont = new Font(dialogFont.getFontName(), dialogFont.getStyle(), attributes.getFontTextPointSize());
            graphics.setFont(fretFont);
            Instrument.Fret topFrettingPosition = Instrument.getFret(chordDiagram.getTopFret().ordinal() + 1);
            graphics.drawString(topFrettingPosition.text(), attributes.getXStart() + attributes.getChordDiagramWidth() + attributes.getCircleDiameter(),
                                attributes.getYFretboard() + attributes.getDistanceBetweenFrets());
            graphics.setFont(dialogFont);
         }

         // draw the note fretting on the chord diagram by iterating over the strings from low to high (left to right)
         x = attributes.getXStart();
         for(int string=fretting.length-1; string>=0; --string)
         {
            // draw the fretting
            switch (fretting[string].getFret())
            {
               case Open:
                    graphics.drawOval(x - attributes.getCircleRadius(), attributes.getYOpenUnusedStrings() - attributes.getCircleDiameter(), attributes.getCircleDiameter(), attributes.getCircleDiameter());
               break;
               case Not_Used:
                    graphics.drawLine(x - attributes.getCircleRadius(), attributes.getYOpenUnusedStrings() - attributes.getCircleDiameter(), x + attributes.getCircleRadius(), attributes.getYOpenUnusedStrings());
                    graphics.drawLine(x - attributes.getCircleRadius(), attributes.getYOpenUnusedStrings()                                 , x + attributes.getCircleRadius(), attributes.getYOpenUnusedStrings() - attributes.getCircleDiameter());
               break;
               default:  // fretted note on fretboard
                    // determine if this fingering is the beginning of a "barre"
                    boolean isBarre          = true;
                    int     startBarreString = string;
                    int     endBarreString   = string;

                    if (fretting[string].getFinger() != null)
                    {
                       // is there is another note that is fretted on a different string at the same fret and fretted using the same finger,
                       for(int j=string-1; j>=0 && isBarre; --j)
                       {
                          isBarre = fretting[j].getFret() == Instrument.Fret.Not_Used ||                                                                                                         // string is unused
                                   (fretting[startBarreString].getFret().ordinal() <  fretting[j].getFret().ordinal() && fretting[startBarreString].getFinger() != fretting[j].getFinger()) ||   // string is fretted on a higher fret with a different finger
                                   (fretting[startBarreString].getFret().ordinal() == fretting[j].getFret().ordinal() && fretting[startBarreString].getFinger() == fretting[j].getFinger()) ||   // string is fretted on the same fret with the same    finger
                                   (fretting[startBarreString].getFret().ordinal() == fretting[j].getFret().ordinal() && fretting[j               ].getFinger() == null                   );     // string is fretted on the same fret with no finger specified

                          if (fretting[string].getFret() == fretting[j].getFret() && fretting[string].getFinger() == fretting[j].getFinger())
                             endBarreString = j;
                       }
                    }
                    isBarre = startBarreString != endBarreString;

                    int fret = fretting[string].getFret().ordinal() - chordDiagram.getTopFret().ordinal();
                    if (isBarre)
                    {
                       // draw the barre across the strings involved
                       graphics.fillRoundRect(x - attributes.getCircleRadius(),
                                              attributes.getYFretboard() + (attributes.getDistanceBetweenFrets() / 2) - attributes.getCircleRadius() + ((fret - 1) * attributes.getDistanceBetweenFrets()) + 1,
                                              Math.abs(endBarreString - startBarreString) * attributes.getDistanceBetweenStrings() + attributes.getCircleDiameter(),
                                              attributes.getCircleDiameter() - 1, attributes.getCircleDiameter(), attributes.getCircleDiameter());
                    }
                    else
                    {
                       // draw the fretting for the note on the current string
                       graphics.fillOval(x - attributes.getCircleRadius(),
                                         attributes.getYFretboard() + (attributes.getDistanceBetweenFrets() / 2) - attributes.getCircleRadius() + ((fret - 1) * attributes.getDistanceBetweenFrets()) + 1,
                                         attributes.getCircleDiameter(), attributes.getCircleDiameter());
                    }
               break;
            }

            // draw the fingering of the notes at the bottom of the fretboard
            if (fretting[string].getFinger() != null)
               graphics.drawString(fretting[string].getFinger().text(), x - attributes.getCircleRadius(), attributes.getYFingeringNoteNames());

            x += attributes.getDistanceBetweenStrings();   // move right to the next string
         }
         // restore the orignal dialog font
         graphics.setFont(new Font(dialogFont.getFontName(), dialogFont.getStyle(), dialogFont.getSize()));
      }
   }
}
