/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: Andy Will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import com.beaglebuddy.tab.gui.mainframe.CursorMap;
import com.beaglebuddy.tab.model.Location;
import com.beaglebuddy.tab.model.song.Song;




/**
 * A paintable version of the song model object.
 */
public class PaintableSong extends Song
{
   // data members
   private       Location       location;              // the current location within the song where the cursor is.
   private final PaintableScore paintableScore;        // a score with GUI functionality
   private final CursorMap      cursorMap;             // helper class for mapping location-related regions of the song panel
   private final InstrumentMap  instrumentMap;         // helper class for mapping instruments for sections and staves




   /**
    * Construct a paintable song from a song model object.
    * @param song input song
    */
   public PaintableSong(Song song)
   {
      super();
      this.location      = new Location();
      this.filename      = song.getFilename();
      this.information   = song.getInformation();
      this.chordDiagrams = song.getChordDiagrams();
      this.instrumentMap = new InstrumentMap(song.getScore().getSections(), song.getScore().getInstruments());
      this.score         = this.paintableScore = new PaintableScore(this, song.getScore());
      this.version       = song.getVersion();
      this.cursorMap     = new CursorMap();
   }

   /**
    * @return the song's current location.
    */
   public Location getCurrentLocation()
   {
      return this.location;
   }

   /**
    * sets the location of the cursor within the song.
    * <br/><br/>
    * @param location   the location of the cursor within the song.
    */
   public void setCurrentLocation(Location location)
   {
      this.location = location;
   }

   /**
    * @return the cursor map for the song.
    */
   public CursorMap getCursorMap()
   {
      return this.cursorMap;
   }

   /**
    * @return the paintable score for the song.
    */
   public PaintableScore getPaintableScore()
   {
      return this.paintableScore;
   }

   /**
    * @return the instrument active for the given section index and staff.
    */
   public InstrumentMap getInstrumentMap()
   {
      return this.instrumentMap;
   }
}
