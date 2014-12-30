/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.model.Midi;
import com.beaglebuddy.tab.model.song.Song;
import javax.sound.midi.MidiDevice;



/**
 * This main frame base class manages the following aspects of the application:
 * <ol>
 *    <li>the current song being edited</li>
 * </ol>
 */
@SuppressWarnings("serial")
public class MainFrameSong extends MainFrameBase {
   // data members

	/** current song user is editing */
   protected Song       song;

   /** filename where song is saved on disk */
   protected String     filename;

   /**
    * a "dirty" flag indicating if the user has made any changes to the song since the last time it was saved
    */
   protected boolean    songHasBeenEdited;

   /**
    * the midi device on which songs will be played back on.
    */
   protected MidiDevice midiDevice;



   /**
    * default constructor.
    */
   public MainFrameSong()
   {
      // by default, start with a new blank song
      this.song = new Song();

      // load the information about the midi device the user wants to play the songs back on
      restoreMidiDevice();
   }

   /**
    * @return the filename where the song is saved on disk.
    */
   public String getFilename()
   {
      return filename;
   }

   /**
    * sets the path to where the song is saved on disk.
    * <br/><br/>
    * @param filename   the path to where the song is saved on disk.
    */
   public void setFilename(String filename)
   {
      this.filename = filename;
   }

   /**
    * @return the current song being edited.
    */
   public Song getSong()
   {
      return(song);
   }

   /**
    * sets the song currently being edited.
    * <br/><br/>
    * @param song  the current song being edited.
    */
   public void setSong(Song song)
   {
      this.song = song;
   }

   /**
    * @return the midi midiDevice on which songs will be played back on.
    */
   public MidiDevice getMidiDevice()
   {
      return midiDevice;
   }

   /**
    * sets the midi device on which songs will be played back on.
    * <br/><br/>
    * @param midiDevice  the midi device on which to play back songs.
    */
   public void setMidiDevice(MidiDevice midiDevice)
   {
      this.midiDevice = midiDevice;
   }

   /**
    * retrieves the midi device on which the user wishes to have songs played back on from previously stored preferences.
    */
   private void restoreMidiDevice()
   {
      // if the midiDevice is installed on the computer, then retrieve it.
      // otherwise, the user will have to choose a new device from the midi midiDevice menu.
      this.midiDevice = Midi.getDevice(Preferences.getString(Preferences.key_midi_device_name       ),
                                       Preferences.getString(Preferences.key_midi_device_vendor     ),
                                       Preferences.getString(Preferences.key_midi_device_description),
                                       Preferences.getString(Preferences.key_midi_device_version    ));
   }

   /**
    * saves information about the user's preferred midi device on which songs will be played back on.
    * <br/><br/>
    * @param midiDeviceInfo   the midi device information for the midi device on which songs will be played back on.
    */
   public void saveMidiDeviceInfo(MidiDevice.Info midiDeviceInfo)
   {
      Preferences.setString(Preferences.key_midi_device_name       , midiDeviceInfo.getName       ());
      Preferences.setString(Preferences.key_midi_device_vendor     , midiDeviceInfo.getVendor     ());
      Preferences.setString(Preferences.key_midi_device_description, midiDeviceInfo.getDescription());
      Preferences.setString(Preferences.key_midi_device_version    , midiDeviceInfo.getVersion    ());
   }

   /**
    * @return whether the user has made any changes to the song since the last time it was saved.
    */
   public boolean songHasBeenEdited()
   {
      return songHasBeenEdited;
   }

   /**
    * sets whether the user has made any changes to the song since the last time it was saved.
    * <br/><br/>
    * @param songHasBeenEdited   whether the user has made any changes to the song since the last time it was saved.
    */
   public void setSongHasBeenEdited(boolean songHasBeenEdited)
   {
      this.songHasBeenEdited = songHasBeenEdited;
   }
}
