/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: andrew Will $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.songpanel;

import com.beaglebuddy.tab.model.Information;
import com.beaglebuddy.tab.model.song.Song;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import static com.beaglebuddy.tab.gui.font.FontManager.*;




/**
 * This class manages drawing the song information and chord diagrams for the song panel for painting and/or printing.
 */
public class SongPanelHeader
{
   // data members
   private final Song song;




   /**
    * constructor.
    * <br/><br/>
    * @param song   the song whose information and chord diagrams are being drawn.
    */
   public SongPanelHeader(Song song)
   {
      this.song = song;
   }

   /**
    * @return the height (in pixels) of the page number.
    * <br/><br/>
    * @param pageNumber   page number.
    */
   public double getPageNumberHeight(int pageNumber)
   {
      Rectangle2D bounds = drawPageNumber(GraphicsStub.INSTANCE, PaintableSection.WIDTH, pageNumber);

      return bounds.getHeight();
   }

   /**
    * draws the specified page number.
    * <br/><br/>
    * @param graphics     graphics context.
    * @param width        width of todo: what?
    * @param pageNumber   page number.
    * <br/><br/>
    * @return todo: what?
    */
   public Rectangle2D drawPageNumber(Graphics2D graphics, double width, int pageNumber)
   {
      // align page number on the right for odd page numbers, left for even
      HeaderText.Alignment alignment = (pageNumber & 1) == 1 ? HeaderText.Alignment.RIGHT : HeaderText.Alignment.LEFT;

      // build the page header
      List<HeaderText> pageHeader = new LinkedList<HeaderText>();
      pageHeader.add(new HeaderText(Integer.toString(pageNumber), SMALL_ITALIC_FONT, alignment, HeaderText.IS_EOL));

      // draw the page header
      return draw(graphics, width, pageHeader);
   }

   /**
    * @return the height of the title header.
    */
   public double getTitleHeight()
   {
      Rectangle2D bounds = drawTitle(GraphicsStub.INSTANCE, PaintableSection.WIDTH);

      return bounds.getHeight();
   }

   /**
    * Draws the title header in the given graphics context.
    * <br/><br/>
    * @param g       graphics context.
    * @param width   width of todo: what?
    * <br/><br/>
    * @return todo: what?
    */
   protected Rectangle2D drawTitle(Graphics2D graphics, double width)
   {
      // retrieve the song information
      List<HeaderText> titleStrings = getTitleStrings();

      // reformat the title strings to fit in the width
      titleStrings = reformat(titleStrings, width);

      // draw the title header
      Rectangle2D bounds = draw(graphics, width, titleStrings);

      return bounds;
   }

   /**
    * draws the headers in the graphics context, limited to the provided width.
    * <br/><br/>
    * @param graphics  graphics context.
    * @param width     max width of the header; used for centering, etc.
    * @param header    list of text objects comprising the header.
    * <br/><br/>
    * @return the rectangle bounds occupied by the header.
    */
   private Rectangle2D draw(Graphics2D graphics, double width, List<HeaderText> header)
   {
      // get the starting point for the title
      Rectangle2D boundRectangle = new Rectangle2D.Double(0.0, 0.0, width, 0.0);

      // paint each line
      for (HeaderText line : header)
         line.paint(graphics, boundRectangle);

      return boundRectangle;
   }

   /**
    * @return list of text objects comprising the title header.
    */
   private List<HeaderText> getTitleStrings()
   {
      // get the song information
      Information info  = song.getInformation();
      String      title = info.getSongTitle();

      if (title == null || title.isEmpty())
         title = ResourceBundle.getString("gui.text.untitled");
      title = title.toUpperCase();

      String recordingArtist = info.getArtist();
      short  albumYear       = info.getAlbumReleaseYear();
      String albumTitle      = info.getAlbumTitle() == null ? null : info.getAlbumTitle().toUpperCase();
      String transcriber     = info.getTranscribedBy();
      String lyricsist       = info.getWordsBy();
      String composer        = info.getMusicBy();

      // create the return object
      LinkedList<HeaderText> retVal = new LinkedList<HeaderText>();

      // add recording artist
      retVal.addLast(new HeaderText(title, LARGE_FONT, HeaderText.Alignment.CENTER, HeaderText.IS_EOL));
      if (recordingArtist != null && !recordingArtist.isEmpty())
         retVal.addLast(new HeaderText(ResourceBundle.getString("gui.text.song_information.as_recorded_by") + " " + recordingArtist, MEDIUM_FONT, HeaderText.Alignment.CENTER, HeaderText.IS_EOL));

      // add album title
      if (albumTitle != null && !albumTitle.isEmpty())
      {
         StringBuffer sb = new StringBuffer();
         sb.append(ResourceBundle.getString("gui.text.song_information.from_the"));
         if (albumYear > 0)
         {
            sb.append(" ");
            sb.append(albumYear);
            sb.append(" ");
         }
         sb.append(ResourceBundle.getString("gui.text.song_information.album"));
         sb.append(" ");
         sb.append(albumTitle);
         sb.append(")");
         retVal.addLast(new HeaderText(sb.toString(), SMALL_FONT, HeaderText.Alignment.CENTER, HeaderText.IS_EOL));
      }

      // add a blank line
      retVal.addLast(new HeaderText("", SMALL_FONT, HeaderText.Alignment.CENTER, HeaderText.IS_EOL));

      // add transcription credit, if present
      if (transcriber != null && !transcriber.isEmpty())
         retVal.addLast(new HeaderText(ResourceBundle.getString("gui.text.song_information.transcribed_by") + " " + transcriber, SMALL_ITALIC_FONT, HeaderText.Alignment.LEFT, HeaderText.IS_NOT_EOL));

      // add words/music credit, if present
      if (lyricsist != null && !lyricsist.isEmpty() && composer != null && composer.equals(lyricsist))
      {
         retVal.addLast(new HeaderText(ResourceBundle.getString("gui.text.song_information.words_and_music_by") + " " + lyricsist, SMALL_ITALIC_FONT, HeaderText.Alignment.RIGHT, HeaderText.IS_EOL));
      }
      else
      {
         // different lyricsist and composer
         if (lyricsist != null && !lyricsist.isEmpty())
            retVal.addLast(new HeaderText(ResourceBundle.getString("gui.text.song_information.words_by") + " " + lyricsist, SMALL_ITALIC_FONT, HeaderText.Alignment.RIGHT, HeaderText.IS_EOL));
         if (composer != null && !composer.isEmpty())
            retVal.addLast(new HeaderText(ResourceBundle.getString("gui.text.song_information.music_by") + " " + composer , SMALL_ITALIC_FONT, HeaderText.Alignment.RIGHT, HeaderText.IS_EOL));

      }
      return retVal;
   }

   /**
    * ensures that none of the given text exceeds the given width.
    * <br/><br/>
    * @return newly formatted list.
    * <br/><br/>
    * @param strings    list of headers.
    * @param maxWidth   width used for formatting the text.
    */
   private List<HeaderText> reformat(List<HeaderText> strings, double maxWidth)
   {
      List<HeaderText> newList = new LinkedList<HeaderText>();

      // iterate through each of the strings, working with whole lines
      List<HeaderText> lineList = new LinkedList<HeaderText>();
      for (HeaderText header : strings)
      {
         lineList.add(header);
         if (header.isEol())
         {
            newList.addAll(reformatLines(maxWidth, lineList));
            lineList.clear();
         }
      }
      return newList;
   }

   /**
    * given the list of headers occupying the same line, reformats into a multi-line list to fit the width, if necessary.
    * @param maxWidth   // todo: what?
    * @param lineList   // todo: what?
    * @return           // todo: what?
    */
   private List<HeaderText> reformatLines(double maxWidth, List<HeaderText> lineList)
   {
      // Check present width.
      double width = 0.0;
      for (HeaderText header : lineList)
      {
         width += header.getWidth();
      }
      if (width <= maxWidth)
      {
         return lineList;
      }

      // divide the max width evenly among the header columns
      double columnWidth = maxWidth/lineList.size();
      List<List<HeaderText>> columnList = new LinkedList<List<HeaderText>>();
      for (HeaderText header : lineList)
      {
         List<HeaderText> column = header.reformat(columnWidth);
         columnList.add(column);
      }

      // round-robin the columns to the new list
      List<HeaderText> newList = new LinkedList<HeaderText>();
      while (!columnList.isEmpty())
      {
         // Iterate through each column.
         int i = 0;
         while (i < columnList.size())
         {
            if (columnList.get(i).isEmpty())
            {
               columnList.remove(i);
            }
            else
            {
               HeaderText header = columnList.get(i).remove(0);
               newList.add(header);
               ++i;
            }
         }

         // make sure the last added column has a newline
         newList.get(newList.size() - 1).setEol(true);
      }
      return newList;
   }
}
