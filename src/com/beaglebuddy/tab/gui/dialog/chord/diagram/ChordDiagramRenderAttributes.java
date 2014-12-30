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
 * this class holds a set of values needed to draw a chord diagram.
 * the basic chord diagram is drawn as shown below:
 *
 *                 JPanel ----------------
 *
 *                yStart  ----------------
 *                         F#min7
 *            yChordName  ----------------
 *
 *                yKluge  ----------------
 *    yOpenUnusedStrings  X     O  O     X
 *            yFretboard  ----------------
 *                        |  |  |  |  |  |
 *                        ----------------
 *                        |  |  |  |  |  |
 *                        ----------------
 *                        |  |  |  |  |  |
 *                        ----------------
 *   yFingeringNoteNames     3        1
 */
public class ChordDiagramRenderAttributes
{
   // class members
   private int         distanceBetweenFrets;           // vertical   distance (in pixels) between each fret
   private int         distanceBetweenStrings;         // horizontal distance (in pixels) between each string
   private int         chordDiagramHeight;             // height  (in pixels) of the chord diagram (number of frets       * distance between frets  )
   private int         chordDiagramWidth;              // width   (in pixels) of the chord diagram (number of strings - 1 * distance between strings)
   private int         numStrings;                     // number of strings to draw.
   private int         numFrets;                       // number of frets   to draw.
   private int         nutHeight;                      // thickness of rectangle used to draw the nut on the fretboard.
   private int         circleRadius;                   // radius   of the circle (in pixels) used to draw fretted notes on the fretboard.
   private int         circleDiameter;                 // diameter of the circle (in pixels) used to draw fretted notes on the fretboard.
   private int         fontNamePointSize;              // point size of the font to use when rendering the chord name
   private int         fontTextPointSize;              // point size of the font to use when rendering fingering, X's and O's for unused\open strings, etc.
   private int         xStart;                         // the horizontal x location on the panel where the chord diagram will be drawn.
   private int         yStart;                         // the vertical   y location on the panel where the chord diagram will be drawn.
   private int         yChordName;                     // the vertical   y location on the panel where the chord diagram chord name           will be drawn.
   private int         yKluge;                         // the vertical   y distance (in pixels) to place between the chord name and the open/unused strings.
   private int         yOpenUnusedStrings;             // the vertical   y location on the panel where the chord diagram open/unused strings  will be drawn.
   private int         yFretboard;                     // the vertical   y location on the panel where the chord diagram fretboard            will be drawn.
   private int         yFingeringNoteNames;            // the vertical   y location on the panel where the chord diagram fingering/note names will be drawn.
   private boolean     editing;                        // whether the chord diagram is being drawn for viewing or editing.
   private boolean     drawTopFretNumber;              // whether to draw the top fret number off to the right of the top fret of the fretboard



   /**
    * constructor.
    * <br/><br/>
    * @param numStrings          the number of strings to draw.
    * @param circleRadius        radius of the circle (in pixels) used to draw fretted notes on the fretboard.
    * @param nutHeight           thickness of rectangle used to draw the nut on the fretboard.
    * @param xStart              the horizontal x location on the panel where the chord diagram will be drawn.
    * @param yStart              the vertical   y location on the panel where the chord diagram will be drawn.
    * @param yKluge              the vertical space (in pixels) to place between the chord name and the open/unused strings.
    * @param fontNamePointSize   point size of the font to use when rendering the chord name.
    * @param fontTextPointSize   point size of the font to use when rendering fingering, X's and O's for unused\open strings, etc.
    * @param editing             whether the chord diagram is being drawn for editing or not. If not, the fingerings at the bottom of the fretboard are drawn.
    */
   public ChordDiagramRenderAttributes(int numStrings, int circleRadius, int nutHeight, int xStart, int yStart, int yKluge, int fontNamePointSize, int fontTextPointSize, boolean editing)
   {
      assert(numStrings   >= 6);
      assert(circleRadius >  0);
      assert(nutHeight    >  0);
      assert(xStart       >= circleRadius);
      assert(yStart       >= 0);

      this.numStrings               = numStrings;
      this.numFrets                 = 5;
      this.nutHeight                = nutHeight;
      this.circleRadius             = circleRadius;
      this.circleDiameter           = 2 * circleRadius;
      this.fontNamePointSize        = fontNamePointSize;
      this.fontTextPointSize        = fontTextPointSize;
      this.distanceBetweenFrets     = (int)(1.5 * circleDiameter);
      this.distanceBetweenStrings   = distanceBetweenFrets;
      this.chordDiagramHeight       = numFrets         * distanceBetweenFrets;
      this.chordDiagramWidth        = (numStrings - 1) * distanceBetweenStrings;
      this.xStart                   = xStart;
      this.yStart                   = yStart;
      this.yKluge                   = yKluge;
      this.yChordName               = 0;       // these four locations depend on the character height of the font being used to draw the chord diagram
      this.yOpenUnusedStrings       = 0;       // and hence are not set here.  Setters are provided to set their values.
      this.yFretboard               = 0;
      this.yFingeringNoteNames      = 0;
      this.editing                  = editing;
      this.drawTopFretNumber        = !editing;
   }

   /**
    * @return the vertical distance (in pixels) between each fret.
    */
   public int getDistanceBetweenFrets()
   {
      return distanceBetweenFrets;
   }

   /**
    * @return horizontal distance (in pixels) between each string.
    */
   public int getDistanceBetweenStrings()
   {
      return distanceBetweenStrings;
   }

   /**
    * @return the height (in pixels) of a chord diagram, which is equal to the number of frets * the distance between frets.
    */
   public int getChordDiagramHeight()
   {
      return chordDiagramHeight;
   }

   /**
    * @return width (in pixels) of the chord diagram, which is equal to (number of strings - 1) * distance between strings.
    */
   public int getChordDiagramWidth()
   {
      return chordDiagramWidth;
   }

   /**
    * @return   the number of strings to draw.
    */
   public int getNumStrings()
   {
      return numStrings;
   }

   /**
    * sets the number of strings to draw.
    * <br/><br/>
    * @param numStrings   the number of strings to draw.
    */
   public void setNumStrings(int numStrings)
   {
      assert(numStrings >= 6);

      this.numStrings        =  numStrings;
      this.chordDiagramWidth = (numStrings - 1) * distanceBetweenStrings;
   }

   /**
    * @return   the number of frets to draw.
    */
   public int getNumFrets()
   {
      return numFrets;
   }

   /**
    * @return the thickness of rectangle used to draw the nut on the fretboard.
    */
   public int getNutHeight()
   {
      return nutHeight;
   }

   /**
    * @return   the radius of the circle (in pixels) used to draw fretted notes on the fretboard.
    */
   public int getCircleRadius()
   {
      return circleRadius;
   }

   /**
    * @return   the diameter of the circle (in pixels) used to draw fretted notes on the fretboard.
    */
   public int getCircleDiameter()
   {
      return circleDiameter;
   }

   /**
    * @return   whether the chord diagram is being drawn for viewing or editing.
    */
   public boolean isEditing()
   {
      return editing;
   }

   /**
    * @return   whether to draw the top fret number off to the right of the top fret of the fretboard.
    *           in edit mode, the top fret is drawn as a drop down box, while in render mode it is drawn as text.
    */
   public boolean getDrawTopFretNumber()
   {
      return drawTopFretNumber;
   }

   /**
    * @return   point size of the font to use when rendering the chord name
    */
   public int getFontNamePointSize()
   {
      return fontNamePointSize;
   }

   /**
    * @return   point size of the font to use when rendering fingering, X's and O's for unused\open strings, etc.
    */
   public int getFontTextPointSize()
   {
      return fontTextPointSize;
   }

   /**
    * @return  the horizontal x location on the panel where the chord diagram will be drawn.
    */
   public int getXStart()
   {
      return xStart;
   }

   /**
    * @return  the vertical   y location on the panel where the chord diagram will be drawn.
    */
   public int getYStart()
   {
      return yStart;
   }

   /**
    * @return the vertical y location on the panel where the chord diagram chord name will be drawn.
    */
   public int getYChordName()
   {
      return yChordName;
   }

   /**
    * set the vertical y location on the panel where the chord diagram chord name will be drawn.
    * <br/><br/>
    * @param yChordName   the vertical y location on the panel where the chord diagram chord name will be drawn.
    */
   public void setYChordName(int yChordName)
   {
      this.yChordName = yChordName;
   }

   /**
    * @return   the vertical y distance (in pixels) to place between the chord name and the open/unused strings.
    */
   public int getYKluge()
   {
      return yKluge;
   }

   /**
    * @return   the vertical y location on the panel where the chord diagram open/unused strings will be drawn.
    */
   public int getYOpenUnusedStrings()
   {
      return yOpenUnusedStrings;
   }

   /**
    * sets the vertical y location on the panel where the chord diagram open/unused strings will be drawn.
    * <br/><br/>
    * @param yOpenUnusedStrings  the vertical y location on the panel where the chord diagram open/unused strings will be drawn.
    */
   public void setYOpenUnusedStrings(int yOpenUnusedStrings)
   {
      this.yOpenUnusedStrings = yOpenUnusedStrings;
   }

   /**
    * @return   the vertical y location on the panel where the chord diagram fretboard will be drawn.
    */
   public int getYFretboard()
   {
      return yFretboard;
   }

   /**
    * sets the vertical y location on the panel where the chord diagram fretboard will be drawn.
    * <br/><br/>
    * @param yFretboard  the vertical y location on the panel where the chord diagram fretboard will be drawn.
    */
   public void setYFretboard(int yFretboard)
   {
      this.yFretboard = yFretboard;
   }

   /**
    * @return   the vertical y location on the panel where the chord diagram fingering\note names will be drawn.
    */
   public int getYFingeringNoteNames()
   {
      return yFingeringNoteNames;
   }

   /**
    * sets the vertical y location on the panel where the chord diagram fingering\note names will be drawn.
    * <br/><br/>
    * param yFingeringNoteNames  the vertical y location on the panel where the chord diagram fingering\note names will be drawn.
    */
   public void setYFingeringNoteNames(int yFingeringNoteNames)
   {
      this.yFingeringNoteNames = yFingeringNoteNames;
   }

   /**
    * @return a string representation of the attributes.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append("distance between frets....: " + distanceBetweenFrets   + "\n");
      buffer.append("distance between strings..: " + distanceBetweenStrings + "\n");
      buffer.append("chord diagram height......: " + chordDiagramHeight     + "\n");
      buffer.append("chord diagram width.......: " + chordDiagramWidth      + "\n");
      buffer.append("number of strings.........: " + numStrings             + "\n");
      buffer.append("number of frets...........: " + numFrets               + "\n");
      buffer.append("nut height................: " + nutHeight              + "\n");
      buffer.append("fret circle radius........: " + circleRadius           + "\n");
      buffer.append("fret circle diameter......: " + circleDiameter         + "\n");
      buffer.append("font name point size......: " + fontNamePointSize      + "\n");
      buffer.append("font text point size......: " + fontTextPointSize      + "\n");
      buffer.append("editing...................: " + editing                + "\n");
      buffer.append("draw top fret number......: " + drawTopFretNumber      + "\n");
      buffer.append("x start...................: " + xStart                 + "\n");
      buffer.append("y start...................: " + yStart                 + "\n");
      buffer.append("y chord name..............: " + yChordName             + "\n");
      buffer.append("y open and unused strings.: " + yOpenUnusedStrings     + "\n");
      buffer.append("y fretboard...............: " + yFretboard             + "\n");
      buffer.append("y fingering and note names: " + yFingeringNoteNames          );

      return buffer.toString();
   }
}
