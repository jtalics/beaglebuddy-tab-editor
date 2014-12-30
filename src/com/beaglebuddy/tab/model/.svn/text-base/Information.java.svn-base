/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.io.IOException;



/**
 * This class represents a beaglebuddy tab song information and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 */
public class Information
{
   // enumerations
   public enum FileType {Song, Lesson}

    // data members
   private FileType fileType;             // the type of beaglebuddy tab file (song or lesson)
   private String   artist;               // the artist who performed the song.
   private String   albumTitle;           // the title of the album\cd the song was released on.
   private String   songTitle;            // the tile of the song.
   private short    albumReleaseYear;     // the year the album\cd was released.
   private String   musicBy;              // the person who wrote the song's music.
   private String   wordsBy;              // the person who wrote the song's lyrics.
   private String   transcribedBy;        // the person who transcribed the music.
   private String   copyright;            // any copyright notice.
   private String   performanceNotes;     // advice on how to play the song or lesson.
   private String   lessonTitle;          // the title of the lesson.
   private String   lessonSubTitle;       // a sub title for the lesson.
   private String   lessonBy;             // the person who created the lesson.




   /**
    * default constructor.
    */
   public Information()
   {
      this.fileType         = FileType.Song;
      this.albumReleaseYear = -1;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param information  information to make a copy of.
    */
   public Information(Information information)
   {
      this(information.fileType                                                                     ,
           information.artist             == null ? null : new String(information.artist           ),
           information.albumTitle         == null ? null : new String(information.albumTitle       ),
           information.songTitle          == null ? null : new String(information.songTitle        ),
           information.albumReleaseYear                                                             ,
           information.musicBy            == null ? null : new String(information.musicBy          ),
           information.wordsBy            == null ? null : new String(information.wordsBy          ),
           information.transcribedBy      == null ? null : new String(information.transcribedBy    ),
           information.copyright          == null ? null : new String(information.copyright        ),
           information.performanceNotes   == null ? null : new String(information.performanceNotes ),
           information.lessonTitle        == null ? null : new String(information.lessonTitle      ),
           information.lessonSubTitle     == null ? null : new String(information.lessonSubTitle   ),
           information.lessonBy           == null ? null : new String(information.lessonBy         ));
   }

   /**
    * constructor.
    * <br/><br/>
    * @param fileType           the type of beaglebuddy tab file (song or lesson).
    * @param artist             the artist who performed the song.
    * @param albumTitle         the title of the album\cd the song was released on.
    * @param songTitle          the tile of the song.
    * @param albumReleaseYear   the year the album\cd was released.
    * @param musicBy            the person who wrote the song's music.
    * @param wordsBy            the person who wrote the song's lyrics.
    * @param transcribedBy      the person who transcribed the music.
    * @param copyright          any copyright notice.
    * @param performanceNotes   advice on how to play the song or lesson.
    * @param lessonTitle        the title of the lesson.
    * @param lessonSubTitle     a sub title for the lesson.
    * @param lessonBy           the person who created the lesson.
    */
   public Information(FileType fileType, String artist, String albumTitle, String songTitle, short  albumReleaseYear, String musicBy, String wordsBy,
                      String transcribedBy, String copyright, String performanceNotes, String lessonTitle, String lessonSubTitle,String lessonBy)
   {
      this.fileType         = fileType;
      this.artist           = artist;
      this.albumTitle       = albumTitle;
      this.songTitle        = songTitle;
      this.albumReleaseYear = albumReleaseYear;
      this.musicBy          = musicBy;
      this.wordsBy          = wordsBy;
      this.transcribedBy    = transcribedBy;
      this.copyright        = copyright;
      this.performanceNotes = performanceNotes;
      this.lessonTitle      = lessonTitle;
      this.lessonSubTitle   = lessonSubTitle;
      this.lessonBy         = lessonBy;
   }

   /**
    * @return the beaglebuddy tab information file type.
    */
   public FileType getFileType()
   {
      return fileType;
   }

   /**
    * sets the beaglebuddy tab information file type.
    * <br/><br/>
    * @param fileType type of beaglebuddy information.
    */
   public void setFileType(FileType fileType)
   {
      this.fileType = fileType;
   }

   /**
    * @return the title of the album.
    */
   public String getAlbumTitle()
   {
      return albumTitle;
   }

   /**
    * sets the title of the album.
    * <br/><br/>
    * @param albumTitle the title of the album.
    */
   public void setAlbumTitle(String albumTitle)
   {
      this.albumTitle = albumTitle;
   }

   /**
    * @return the title of the song.
    */
   public String getSongTitle()
   {
      return songTitle;
   }

   /**
    * sets the title of the song.
    * <br/><br/>
    * @param songTitle the title of the song.
    */
   public void setSongTitle(String songTitle)
   {
      this.songTitle = songTitle;
   }

   /**
    * @return the artist(s) who composed the song.
    */
   public String getArtist()
   {
      return artist;
   }

   /**
    * sets the artist(s) who composed the song.
    * <br/><br/>
    * @param artist the artist(s) who composed the song.
    */
   public void setArtist(String artist)
   {
      this.artist = artist;
   }

   /**
    * @return the year the album was released.
    */
   public short getAlbumReleaseYear()
   {
      return albumReleaseYear;
   }

   /**
    * sets the year the album was released.
    * <br/><br/>
    * @param albumReleaseYear the year the album was released.
    */
   public void setAlbumReleaseYear(short albumReleaseYear)
   {
      this.albumReleaseYear = albumReleaseYear;
   }

   /**
    * @return the author of the composition's music.
    */
   public String getMusicBy()
   {
      return musicBy;
   }

   /**
    * sets the author of the composition's music.
    * <br/><br/>
    * @param musicBy the author of the composition's music.
    */
   public void setMusicBy(String musicBy)
   {
      this.musicBy = musicBy;
   }

   /**
    * @return the author of the composition's lyrics.
    */
   public String getWordsBy()
   {
      return wordsBy;
   }

   /**
    * sets the author of the composition's lyrics.
    * <br/><br/>
    * @param wordsBy the author of the composition's lyrics.
    */
   public void setWordsBy(String wordsBy)
   {
      this.wordsBy = wordsBy;
   }

   /**
    * @return the author of the composition's transcription.
    */
   public String getTranscribedBy()
   {
      return transcribedBy;
   }

   /**
    * sets the author of the composition's transcription.
    * <br/><br/>
    * @param transcribedBy the author of the composition's transcription.
    */
   public void setTranscribedBy(String transcribedBy)
   {
      this.transcribedBy = transcribedBy;
   }

   /**
    * @return the copyright notice.
    */
   public String getCopyright()
   {
      return copyright;
   }

   /**
    * sets the copyright notice.
    * <br/><br/>
    * @param copyright the copyright notice.
    */
   public void setCopyright(String copyright)
   {
      this.copyright = copyright;
   }

   /**
    * @return the performance notes for the composition.
    */
   public String getPerformanceNotes()
   {
      return performanceNotes;
   }

   /**
    * sets the performance notes for the composition.
    * <br/><br/>
    * @param performanceNotes the performance notes for the composition.
    */
   public void setPerformanceNotes(String performanceNotes)
   {
      this.performanceNotes = performanceNotes;
   }

   /**
    * @return the title of the lesson.
    */
   public String getLessonTitle()
   {
      return lessonTitle;
   }

   /**
    * sets the title of the lesson.
    * <br/><br/>
    * @param lessonTitle the title of the lesson.
    */
   public void setLessonTitle(String lessonTitle)
   {
      this.lessonTitle = lessonTitle;
   }

   /**
    * @return the sub title of the lesson.
    */
   public String getLessonSubTitle()
   {
      return lessonSubTitle;
   }

   /**
    * sets the sub title of the lesson.
    * <br/><br/>
    * @param lessonSubTitle the sub title of the lesson.
    */
   public void setLessonSubTitle(String lessonSubTitle)
   {
      this.lessonSubTitle = lessonSubTitle;
   }

   /**
    * @return the author of the lesson.
    */
   public String getLessonBy()
   {
      return lessonBy;
   }

   /**
    * sets the author of the lesson.
    * <br/><br/>
    * @param lessonBy the author of the lesson.
    */
   public void setLessonBy(String lessonBy)
   {
      this.lessonBy = lessonBy;
   }

   /**
    * @return whether the information is equal to this information.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof Information)
      {
         Information information = (Information)object;
         equal = fileType         == information.fileType         &&
                 albumReleaseYear == information.albumReleaseYear &&
                 ((artist           == null && information.artist           == null) || (artist           != null && information.artist           != null && artist          .equals(information.artist          ))) &&
                 ((albumTitle       == null && information.albumTitle       == null) || (albumTitle       != null && information.albumTitle       != null && albumTitle      .equals(information.albumTitle      ))) &&
                 ((songTitle        == null && information.songTitle        == null) || (songTitle        != null && information.songTitle        != null && songTitle       .equals(information.songTitle       ))) &&
                 ((musicBy          == null && information.musicBy          == null) || (musicBy          != null && information.musicBy          != null && musicBy         .equals(information.musicBy         ))) &&
                 ((wordsBy          == null && information.wordsBy          == null) || (wordsBy          != null && information.wordsBy          != null && wordsBy         .equals(information.wordsBy         ))) &&
                 ((transcribedBy    == null && information.transcribedBy    == null) || (transcribedBy    != null && information.transcribedBy    != null && transcribedBy   .equals(information.transcribedBy   ))) &&
                 ((copyright        == null && information.copyright        == null) || (copyright        != null && information.copyright        != null && copyright       .equals(information.copyright       ))) &&
                 ((performanceNotes == null && information.performanceNotes == null) || (performanceNotes != null && information.performanceNotes != null && performanceNotes.equals(information.performanceNotes))) &&
                 ((lessonTitle      == null && information.lessonTitle      == null) || (lessonTitle      != null && information.lessonTitle      != null && lessonTitle     .equals(information.lessonTitle     ))) &&
                 ((lessonSubTitle   == null && information.lessonSubTitle   == null) || (lessonSubTitle   != null && information.lessonSubTitle   != null && lessonSubTitle  .equals(information.lessonSubTitle  ))) &&
                 ((lessonBy         == null && information.lessonBy         == null) || (lessonBy         != null && information.lessonBy         != null && lessonBy        .equals(information.lessonBy        )));
      }
      return equal;
   }

   /**
    * read in the information from a beaglebuddy tab file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read from.
    * <br/><br/>
    * @throws FileReadException  if the information can not be read from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();

         fileType = (file.read() == 0x00 ? FileType.Song : FileType.Lesson);
         switch (getFileType())
         {
            case Lesson:
                 lessonTitle      = file.readString();
                 lessonSubTitle   = file.readString();
                 lessonBy         = file.readString();
                 performanceNotes = file.readString();
                 copyright        = file.readString();
            break;
            case Song:
                 songTitle        = file.readString();
                 artist           = file.readString();
                 albumTitle       = file.readString();
                 albumReleaseYear = file.readShort();
                 musicBy          = file.readString();
                 wordsBy          = file.readString();
                 transcribedBy    = file.readString();
                 copyright        = file.readString();
                 performanceNotes = file.readString();
            break;
         }
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.information"));
      }
   }

   /**
    * save a information to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the information to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();

         file.write(fileType.ordinal());
         switch (getFileType())
         {
            case Lesson:
                 file.writeString(lessonTitle);
                 file.writeString(lessonSubTitle);
                 file.writeString(lessonBy);
                 file.writeString(performanceNotes);
                 file.writeString(copyright);
            break;
            case Song:
                 file.writeString(songTitle);
                 file.writeString(artist);
                 file.writeString(albumTitle);
                 file.writeShort (albumReleaseYear);
                 file.writeString(musicBy);
                 file.writeString(wordsBy);
                 file.writeString(transcribedBy);
                 file.writeString(copyright);
                 file.writeString(performanceNotes);
            break;
         }
      }
      catch (IOException ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.information"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab information.
    */
   @Override
   public String toString()
   {
      return toString(6);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab information.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(ResourceBundle.getString("data_type.information") + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.type"             ), indentation) + getFileType()         + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.artist"           ), indentation) + getArtist()           + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.album_title"      ), indentation) + getAlbumTitle()       + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.song_title"       ), indentation) + getSongTitle()        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.year_released"    ), indentation) + getAlbumReleaseYear() + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.music_by"         ), indentation) + getMusicBy()          + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.words_by"         ), indentation) + getWordsBy()          + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.transcribed_by"   ), indentation) + getTranscribedBy()    + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.copyright"        ), indentation) + getCopyright()        + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.performance_notes"), indentation) + getPerformanceNotes() + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.lesson_title"     ), indentation) + getLessonTitle()      + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.lesson_sub_title" ), indentation) + getLessonSubTitle()   + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.lesson_author"    ), indentation) + getLessonBy()               );

      return buffer.toString();
   }
}
