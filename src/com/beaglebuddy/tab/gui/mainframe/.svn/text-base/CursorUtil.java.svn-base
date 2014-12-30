package com.beaglebuddy.tab.gui.mainframe;

import java.awt.Event;
import java.awt.Point;
import java.awt.event.KeyEvent;
import com.beaglebuddy.tab.model.Barline;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.Section;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.model.staff.Staff;



/**
 * @author ANDY
 */
public class CursorUtil
{
   /**
    * @return   the location nearest to the given x,y point.
    * @param point
    * @param song
    * @param cursorMap
    */
   public static Location mapLocation(Point point, Song song, CursorMap cursorMap)
   {
      Location l = cursorMap.toLocation(point);
      if (l == null)
      {
         return null;
      }

      if (l.getPosition() < Location.MINIMUM_POSITION)
      {
         l.setPosition(Location.MINIMUM_POSITION);
      }
      else
      {
         // Post-process the location to ensure only valid positions.
         Section s = song.getScore().getSections().get(l.getSection());
         byte maxPosition = maxPosition(s);
         if (l.getPosition() > maxPosition)
         {
            l.setPosition(maxPosition);
         }
      }
      return l;
   }

   /**
    * @return the location succeeding the given current location and key event.
    * <br/><br/>
    * @param ke
    * @param song
    * @param currentLocation
    * <br/><br/>
    * @throws Exception  if the key event results in the loss of keyboard focus
    */
   public static Location mapLocation(KeyEvent ke, Song song, Location currentLocation)
      throws Exception
   {
      Location l = currentLocation;
      if (currentLocation != null)
      {
         switch (ke.getKeyCode())
         {
            case KeyEvent.VK_LEFT:
               l = leftLocation(currentLocation, song);
               break;
            case KeyEvent.VK_RIGHT:
               l = rightLocation(currentLocation, song);
               break;
            case KeyEvent.VK_UP:
               l = upLocation(currentLocation, song);
               break;
            case KeyEvent.VK_DOWN:
               l = downLocation(currentLocation, song);
               break;
            case KeyEvent.VK_PAGE_UP:
               l = pageUpLocation(currentLocation, song);
               break;
            case KeyEvent.VK_PAGE_DOWN:
               l = pageDownLocation(currentLocation, song);
               break;
            case KeyEvent.VK_HOME:
               if (isCtrlDown(ke))
               {
                  l = firstLocation();
               }
               else
               {
                  l = homeLocation(currentLocation, song);
               }
               break;
            case KeyEvent.VK_END:
               if (isCtrlDown(ke))
               {
                  l = lastLocation(song);
               }
               else
               {
                  l = endLocation(currentLocation, song);
               }
               break;
            case KeyEvent.VK_ESCAPE:
               l = null;
               throw new Exception();
            default:
               break;
         }
      }

      return l;
   }

   /**
    * @param ke
    * @return
    */
   private static boolean isCtrlDown(KeyEvent ke)
   {
      int modifiers = ke.getModifiers();
      int ctrlBit = modifiers & Event.CTRL_MASK;
      return (ctrlBit == Event.CTRL_MASK);
   }

   private static Location leftLocation(Location ref, Song song)
   {
      Location l = new Location(ref);
      try
      {
         toPreviousPosition(l, song);
      }
      catch (Exception e1)
      {
         try
         {
            toPreviousSection(l, song);
            toLastPosition(l, getSection(l, song));
         }
         catch (Exception e2)
         {
            l = ref;
         }
      }
      return l;
   }

   private static Location rightLocation(Location ref, Song song)
   {
      Location l = new Location(ref);
      try
      {
         toNextPosition(l, song);
      }
      catch (Exception e1)
      {
         try
         {
            toNextSection(l, song);
            toFirstPosition(l, getSection(l, song));
         }
         catch (Exception e2)
         {
            l = ref;
         }
      }
      return l;
   }

   private static Location upLocation(Location ref, Song song)
   {
      Location l = new Location(ref);
      toPreviousTabLine(l, getStaff(l, getSection(l, song)));
      return l;
   }

   private static Location downLocation(Location ref, Song song)
   {
      Location l = new Location(ref);
      toNextTabLine(l, getStaff(l, getSection(l, song)));
      return l;
   }

   private static Location pageUpLocation(Location ref, Song song)
   {
      Location l = new Location(ref);
      try
      {
         toPreviousStaff(l, song);
      }
      catch (Exception e1)
      {
         try
         {
            toPreviousSection(l, song);
         }
         catch (Exception e2)
         {
            l = ref;
         }
      }
      return l;
   }

   private static Location pageDownLocation(Location ref, Song song)
   {
      Location l = new Location(ref);
      try
      {
         toNextStaff(l, song);
      }
      catch (Exception e1)
      {
         try
         {
            toNextSection(l, song);
         }
         catch (Exception e2)
         {
            l = ref;
         }
      }
      return l;
   }

   private static Location homeLocation(Location ref, Song song)
   {
      Location l = new Location(ref);
      toFirstPosition(l, getSection(l, song));
      return l;
   }

   private static Location endLocation(Location ref, Song song)
   {
      Location l = new Location(ref);
      toLastPosition(l, getSection(l, song));
      return l;
   }

   private static Location firstLocation()
   {
      return new Location();
   }

   private static Location lastLocation(Song song)
   {
      Location l = new Location();
      l.setSection(maxSectionIndex(song));
      Section section = getSection(l, song);
      l.setStaff(maxStaffIndex(section));
      l.setBarline(maxBarline(section));

      int numMeasures = 0;
      for (Section s : song.getScore().getSections())
      {
         numMeasures += s.getNumMeasures();
      }
      l.setMeasure((short)(numMeasures - 1));

      l.setPosition(maxPosition(section));
      Staff staff = section.getStaffs().get(l.getStaff());
      int maxTabLine = staff.getNumTabLines() - 1;
      l.setTabIndex(maxTabLine);

      return l;
   }

   private static void toFirstPosition(Location ref, Section s)
   {
      int oldBarline = ref.getBarline();
      ref.setBarline(Location.MINIMUM_BARLINE);
      int measuresSkipped = oldBarline - ref.getBarline();
      ref.setMeasure((short)(ref.getMeasure() - measuresSkipped));
      ref.setPosition(Location.MINIMUM_POSITION);
   }

   private static void toLastPosition(Location ref, Section s)
   {
      byte maxBarline = maxBarline(s);
      int measuresSkipped = maxBarline - ref.getBarline();
      ref.setBarline(maxBarline);
      ref.setMeasure((short)(ref.getMeasure() + measuresSkipped));
      ref.setPosition(maxPosition(s));
   }

   private static void toNextPosition(Location ref, Song song) throws Exception
   {
      if (ref.getPosition() >= maxPosition(getSection(ref, song)))
      {
         throw new Exception();
      }
      ref.setPosition((byte)(ref.getPosition() + 1));
      if (ref.getBarline() != getBarline(getSection(ref, song), ref.getPosition()))
      {
         ref.setBarline((byte)(ref.getBarline() + 1));
         ref.setMeasure((short)(ref.getMeasure() + 1));
      }
   }

   private static void toPreviousPosition(Location ref, Song song) throws Exception
   {
      if (ref.getPosition() <= Location.MINIMUM_POSITION)
      {
         throw new Exception();
      }
      ref.setPosition((byte)(ref.getPosition() - 1));
      if (ref.getBarline() != getBarline(getSection(ref, song), ref.getPosition()))
      {
         ref.setBarline((byte)(ref.getBarline() - 1));
         ref.setMeasure((short)(ref.getMeasure() - 1));
      }
   }

   private static void toNextTabLine(Location ref, Staff staff)
   {
      int maxTabLine = staff.getNumTabLines() - 1;
      ref.setTabIndex(ref.getTabIndex() + 1);
      if (ref.getTabIndex() > maxTabLine)
      {
         ref.setTabIndex(0);
      }
   }

   private static void toPreviousTabLine(Location ref, Staff staff)
   {
      ref.setTabIndex(ref.getTabIndex() - 1);
      if (ref.getTabIndex() < 0)
      {
         int maxTabLine = staff.getNumTabLines() - 1;
         ref.setTabIndex(maxTabLine);
      }
   }

   private static void toNextStaff(Location ref, Song song) throws Exception
   {
      if (ref.getStaff() >= maxStaffIndex(getSection(ref, song)))
      {
         throw new Exception();
      }
      ref.setStaff((byte)(ref.getStaff() + 1));
   }

   private static void toPreviousStaff(Location ref, Song song) throws Exception
   {
      if (ref.getStaff() <= 0)
      {
         throw new Exception();
      }
      ref.setStaff((byte)(ref.getStaff() - 1));
   }

   private static void toNextSection(Location ref, Song song) throws Exception
   {
      if (ref.getSection() >= maxSectionIndex(song))
      {
         throw new Exception();
      }

      // Remember how many measures are being skipped in this section.
      int measuresSkipped = maxBarline(getSection(ref, song)) - ref.getBarline() + 1;

      // Go to the next section.
      ref.setSection((short)(ref.getSection() + 1));

      // Go to the first staff.
      ref.setStaff((byte)0);

      // Find the closest position in this section.
      ref.setPosition((byte)Math.min(ref.getPosition(),
                                     maxPosition(getSection(ref, song))));

      // Adjust the measure and barline.
      ref.setBarline(getBarline(getSection(ref, song), ref.getPosition()));
      measuresSkipped += ref.getBarline();
      ref.setMeasure((short)(ref.getMeasure() + measuresSkipped));
   }

   private static void toPreviousSection(Location ref, Song song) throws Exception
   {
      if (ref.getSection() == 0)
      {
         throw new Exception();
      }

      // Remember how many measures are being skipped in this section.
      int measuresSkipped = ref.getBarline();

      // Go to the previous section.
      ref.setSection((short)(ref.getSection() - 1));

      // Go to the last staff of the section.
      ref.setStaff(maxStaffIndex(getSection(ref, song)));

      // Find the closest position in this section.
      ref.setPosition((byte)Math.min(ref.getPosition(),
                                     maxPosition(getSection(ref, song))));

      // Adjust the measure and barline.
      ref.setBarline(getBarline(getSection(ref, song), ref.getPosition()));
      measuresSkipped += maxBarline(getSection(ref, song)) - ref.getBarline() + 1;
      ref.setMeasure((short)(ref.getMeasure() - measuresSkipped));
   }

   private static Section getSection(Location ref, Song song)
   {
      return song.getScore().getSections().get(ref.getSection());
   }

   private static Staff getStaff(Location ref, Section section)
   {
      return section.getStaffs().get(ref.getStaff());
   }

   private static short maxSectionIndex(Song song)
   {
      return (short)(song.getScore().getSections().size() - 1);
   }

   private static byte maxStaffIndex(Section section)
   {
      return (byte)(section.getStaffs().size() - 1);
   }

   /** Returns the barline containing the given position.
    *  @param section
    *  @param position
    *  @return
    */
   private static byte getBarline(Section section, byte position)
   {
      byte barline = -1;
      for (Barline b : section.getBarlines())
      {
         byte barlinePosition = b.getPosition();
         if (position < barlinePosition)
         {
            break;
         }
         ++barline;
      }
      return barline;
   }

   private static byte maxBarline(Section section)
   {
      // Subtract two because last barline isn't selectable (minus one),
      // and barlines are zero-indexed (minus one).
      return (byte)(section.getBarlines().size() - 2);
   }

   private static byte maxPosition(Section section)
   {
      return (byte)(section.getNumDrawingPositions() - 1);
   }
}
