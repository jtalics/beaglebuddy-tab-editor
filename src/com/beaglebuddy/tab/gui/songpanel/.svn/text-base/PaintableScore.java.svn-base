/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andy will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import com.beaglebuddy.tab.model.Score;
import com.beaglebuddy.tab.model.Section;
import java.util.ArrayList;
import java.util.List;





/**
 *  A paintable version of the score model.
 */
public class PaintableScore extends Score
{
   // data members
   private final List<PaintableSection> paintableSections;




   /**
    * constructor.
    * <br/><br/>
    * @param song    the song that this score belongs to.
    * @param score   the model of the score to be painted.
    */
   public PaintableScore(PaintableSong song, Score score)
   {
      super();

      getInstruments().addAll(score.getInstruments());
      getFloatingTexts().addAll(score.getFloatingTexts());
      int numSections = score.getSections().size();
      this.paintableSections = new ArrayList<PaintableSection>(numSections);
      this.getSections().ensureCapacity(numSections);
      int sectionIndex = 0;
      int measureNumber = 1;
      int y = PaintableSection.TOP_MARGIN;
      for (Section s : score.getSections())
      {
         // construct the next section.
         PaintableSection ps = new PaintableSection(song, s, sectionIndex++,
                                                    measureNumber, y);
         y += ps.getBoundingRectangle().height;
         y += PaintableSection.INTERSECTION_MARGIN;

         this.paintableSections.add(ps);
         this.getSections().add(ps);

         // Increment the measure number for the next section.
         measureNumber += s.getNumMeasures();
      }
   }

   /**
    * @return the paintable section corresponding to the given section index.
    * <br/><br/>
    * @param sectionIndex   index of the section.
    */
   public PaintableSection getPaintableSection(int sectionIndex)
   {
      return paintableSections.get(sectionIndex);
   }

   /**
    * @return the list of paintable sections.
    */
   public List<PaintableSection> getPaintableSections()
   {
      return paintableSections;
   }

   /**
    * todo: ???
    */
   public void replaceSection(PaintableSection newSection)
   {
      // assign the new paintable section in the list
      PaintableSection oldSection = paintableSections.remove(newSection.sectionIndex);
      paintableSections.add(newSection.sectionIndex, newSection);

      // start laying out the remainder of the score at the old section's y coordinate.
      // also track the measure number changes.
      int y = oldSection.getStartY();
      int measureNumber = oldSection.getFirstMeasureNumber();
      layoutScore(newSection.sectionIndex, y, measureNumber);
   }

   /**
    * lays out the sections in the score.
    */
   public void layoutScore()
   {
      int y = PaintableSection.TOP_MARGIN;
      int measureNumber = 1;
      layoutScore(0, y, measureNumber);
   }

   /**
    * lays out the sections' bounding boxes, starting with the given section index and reusing the first section's measure number and y coordinate.
    * <br/><br/>
    * @param sectionIndex  index of first section to be laid out
    */
   public void layoutScore(int sectionIndex)
   {
      // start laying out the remainder of the score at the old section's y coordinate.
      // also track the measure number changes.
      PaintableSection section = this.paintableSections.get(sectionIndex);
      int y = section.getStartY();
      int measureNumber = section.getFirstMeasureNumber();
      layoutScore(sectionIndex, y, measureNumber);
   }

   /**
    * lays out the sections' bounding boxes, starting with the given section index, starting with the provided y coordinate and measure number.
    * <br/><br/>
    * @param sectionIndex    index of first section to be laid out
    * @param y               the y coordinate to assign to the first section being laid out
    * @param measureNumber   the first measure number of the starting section
    */
   public void layoutScore(int sectionIndex, int y, int measureNumber)
   {
      // layout the score, starting with the newly inserted section
      for (int i = sectionIndex; i < this.paintableSections.size(); ++i)
      {
         // assign the next section's y coordinate and measure number
         PaintableSection ps = this.paintableSections.get(i);
         ps.setStartY(y);
         ps.setFirstMeasureNumber(measureNumber);
         y += ps.getBoundingRectangle().height;
         y += PaintableSection.INTERSECTION_MARGIN;

         // increment the measure number for the next section
         measureNumber += ps.getNumMeasures();
      }
   }
}
