/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model.song;

import com.beaglebuddy.tab.conversion.ConversionManager;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.Information;
import com.beaglebuddy.tab.model.Score;
import com.beaglebuddy.tab.model.Utility;
import com.beaglebuddy.tab.model.Version;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.io.FileNotFoundException;
import java.util.ArrayList;





/**
 * this class can read and save songs to and from a file using the beaglebuddy tab file format.
 */
public class BaseSong
{
   // data members
   protected String                  filename;      // the name of the file under which the song is saved.
   protected Version                 version;       // the version (and hence the format) of the beaglebuddy tab file.
   protected Information             information;   // song information - who wrote the music, etc.
   protected ArrayList<ChordDiagram> chordDiagrams; // chord diagrams for the score
   protected Score                   score;         // the music for the song.



   /**
    * default constructor.
    */
   public BaseSong()
   {
      version       = new Version();
      information   = new Information();
      chordDiagrams = new ArrayList<ChordDiagram>(0);
      score         = new Score();
   }

   /**
    * constructor.
    * <br/><br/>
    * @param information    information about the song.
    * @param chordDiagrams  chord diagrams for the score.
    * @param score          the music for the song.
    */
   public BaseSong(Information information, ArrayList<ChordDiagram> chordDiagrams, Score score)

   {
      this.version = new Version();
      this.information   = information   == null ? new Information()              : information;
      this.chordDiagrams = chordDiagrams == null ? new ArrayList<ChordDiagram>(0) : chordDiagrams;
      this.score         = score         == null ? new Score()                    : score;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param filename   name of the beaglebuddy tab (.bbt) file
    * <br/><br/>
    * @throws FileReadException   if the specified beaglebuddy tab (.bbt) file can not be loaded.
    */
   public BaseSong(String filename) throws FileReadException
   {
      this();
      this.filename = filename;

      load(filename);
   }

   /**
    * @return the name of the beaglebuddy tab file.
    */
   public String getFilename()
   {
      return filename;
   }

   /**
    * sets the name of the beaglebuddy tab file.
    * <br/><br/>
    * @param filename the name of the beaglebuddy tab file.
    */
   public void setFilename(String filename)
   {
      this.filename = filename;
   }

   /**
    * @return the version of the beaglebuddy tab file.
    */
   public Version getVersion()
   {
      return version;
   }

   /**
    * @return the information about the song.
    */
   public Information getInformation()
   {
      return information;
   }

   /**
    * sets the information about the song.
    * <br/><br/>
    * @param information  the information about the song.
    */
   public void setInformation(Information information)
   {
      this.information = information;
   }

   /**
    * @return the chord diagrams used in the score.
    */
   public ArrayList<ChordDiagram> getChordDiagrams()
   {
      return chordDiagrams;
   }

   /**
    * set the chord diagrams used in the score.
    * <br/><br/>
    * @param chordDiagrams  chord diagrams used in the score.
    */
   public void setChordDiagrams(ArrayList<ChordDiagram> chordDiagrams)
   {
      this.chordDiagrams = new ArrayList<ChordDiagram>(0);

      if (chordDiagrams != null)
      {
         for(ChordDiagram chordDiagram : chordDiagrams)
            this.chordDiagrams.add(chordDiagram);
      }
   }

   /**
    * set the chord diagrams used in the score.
    * <br/><br/>
    * @param chordDiagrams   the chord diagrams used by the score.
    */
   private void setChordDiagrams(Object[] chordDiagrams)
   {
      this.chordDiagrams = new ArrayList<ChordDiagram>(0);

      if (chordDiagrams != null)
      {
         for(Object chordDiagram : chordDiagrams)
            this.chordDiagrams.add((ChordDiagram)chordDiagram);
      }
   }

   /**
    * @return the score containing the music for the song.
    */
   public Score getScore()
   {
      return score;
   }

   /**
    * sets the score containing the music for the song.
    * <br/><br/>
    * @param score  the score containing the music for the song.
    */
   public void setScore(Score score)
   {
      this.score = score;
   }

   /**
    * @return the number of measures in the song.
    */
   public int getNumMeasures()
   {
      return score.getNumMeasures();
   }

   /**
    * @return the number of sections in the song.
    */
   public int getNumSections()
   {
      return score.getNumSections();
   }

   /**
    * read in a song from a power tab 1.70 or from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param filename   the name of the tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in and parse the tab file.
    */
   public void load(String filename) throws FileReadException
   {
      Song song = ConversionManager.load(filename);

      information   = song.getInformation();
      chordDiagrams = song.getChordDiagrams();
      score         = song.getScore ();
   }

   /**
    * save a song to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the song to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      version    .save(file);
      information.save(file);
      file.writeArray(chordDiagrams.toArray());
      score      .save(file);
   }

   /**
    * save a song to a file using the beaglebuddy tab (.bbt) file format.
    * <br/><br/>
    * @param filename   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the song to a beaglebuddy tab file.
    */
   public void saveAs(String filename) throws FileWriteException
   {
      this.filename = filename;

      FileOutputStream file = null;
      try
      {
         file = new FileOutputStream(filename);
      }
      catch (FileNotFoundException ex)
      {
         throw new FileWriteException(ex, filename, 0L, 0, 0, ResourceBundle.getString("text.file_name"));
      }
      save(file);
   }

   /**
    * @return a string representation of the song.
    */
   @Override
  public String toString()
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(3);


      buffer.append(Utility.pad(ResourceBundle.getString("text.file"            ), "") + getFilename() + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.version"    ), "") + getVersion () + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("data_type.information"), "") + information   + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.chord_diagrams"  ), "") + chordDiagrams.size() + (chordDiagrams.size() == 0 ? "" : "\n"));
      for(int i=0; i<chordDiagrams.size(); ++i) buffer.append(Utility.pad(ResourceBundle.getString("text.chord_diagrams") + "[" + i + "]" , indentation) + chordDiagrams.get(i) + (i == chordDiagrams.size() -1 ? "" : "\n"));
      buffer.append(score .toString());

      return buffer.toString();
   }
}
