/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog;

import com.beaglebuddy.tab.gui.dialog.CustomFocusTraversalPolicy;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.Information;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;





/**
 * This class is a dialog box which allows a user to enter information about the song being edited.
 */
public class SongInformationDialog extends BaseDialog implements ItemListener
{
   // class members
   private static final int GAP_WIDTH        = 100;
   private static final int TEXT_FIELD_SIZE  = 250;
   private static final int TEXT_AREA_HEIGHT = 293;
   private static final int TEXT_AREA_WIDTH  = 767;

   // data members
   private Information information;

   // dialog box controls
   private JPanel       panelType;
   private JPanel       panelSong;
   private JPanel       panelLesson;

   private ButtonGroup  radioButtonGroupType;
   private JRadioButton radioButtonTypeSong;
   private JRadioButton radioButtonTypeLesson;

   private JLabel       labelArtist;
   private JLabel       labelAlbum;
   private JLabel       labelSong;
   private JLabel       labelYearReleased;
   private JLabel       labelMusicBy;
   private JLabel       labelWordsBy;
   private JLabel       labelTranscribedBy;
   private JLabel       labelCopyright;
   private JLabel       labelLesson;
   private JLabel       labelLessonSubTitle;
   private JLabel       labelLessonBy;

   private JTextField   textFieldArtist;
   private JTextField   textFieldAlbum;
   private JTextField   textFieldSong;
   private JTextField   textFieldYearReleased;
   private JTextField   textFieldMusicBy;
   private JTextField   textFieldWordsBy;
   private JTextField   textFieldTranscribedBy;
   private JTextField   textFieldCopyright;
   private JTextField   textFieldLesson;
   private JTextField   textFieldLessonSubTitle;
   private JTextField   textFieldLessonBy;

   private JLabel       labelPerformanceNotes;
   private JScrollPane  scrollPane;
   private JTextArea    textAreaPerformanceNotes;




   /**
    * constructor.
    */
   public SongInformationDialog(MainFrame frame)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.song.information");

      // get the current song information from the song
      information = new Information(frame.getSong().getInformation());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.song_information.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      // create the three panels
      panelSong   = createPanelSong  ();
      panelLesson = createPanelLesson();
      panelType   = createPanelType  ();

      // create the performance notes text area
      labelPerformanceNotes    = new JLabel(ResourceBundle.getString("dialog.song_information.label.performance_notes"));
      scrollPane               = new JScrollPane();
      textAreaPerformanceNotes = new JTextArea(information.getPerformanceNotes() == null ? "" : information.getPerformanceNotes());
      textAreaPerformanceNotes.setColumns(20);
      textAreaPerformanceNotes.setRows   (10);
      textAreaPerformanceNotes.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.performance_notes"));
      scrollPane.setViewportView(textAreaPerformanceNotes);

      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      // layout the three panels and the text area
      layout.setHorizontalGroup(
          layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
              .addContainerGap()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                  .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, TEXT_AREA_WIDTH, Short.MAX_VALUE)
                  .addComponent(panelType , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                      .addComponent(panelSong  , GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(panelLesson, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                  .addComponent(labelPerformanceNotes, GroupLayout.Alignment.LEADING))
              .addContainerGap())
      );
      layout.setVerticalGroup(
          layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
              .addComponent(panelType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                      .addComponent(panelSong, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                      .addGap(24, 24, 24)
                      .addComponent(labelPerformanceNotes))
                  .addComponent(panelLesson, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, TEXT_AREA_HEIGHT, GroupLayout.PREFERRED_SIZE)
              .addContainerGap())
      );
      layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {panelLesson, panelSong});

      // invoke the layout manager
      pack();

      // set which controls are initially enabled\disabled and the order in which the user moves from field to field.
      setEnabled             (information.getFileType());
      setFocusTraversalPolicy(information.getFileType());
   }

   /**
    * @return a panel with the "type" radio buttons in it.
    */
   private JPanel createPanelType()
   {
      // create "type" label and radio buttons
      radioButtonTypeSong   = new JRadioButton(ResourceBundle.getString("dialog.song_information.radio_button.type.song"  ), information.getFileType() == Information.FileType.Song  );
      radioButtonTypeLesson = new JRadioButton(ResourceBundle.getString("dialog.song_information.radio_button.type.lesson"), information.getFileType() == Information.FileType.Lesson);
      radioButtonTypeSong  .setActionCommand("song");
      radioButtonTypeLesson.setActionCommand("lesson");
      radioButtonTypeSong  .setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.type.song"  ));
      radioButtonTypeLesson.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.type.lesson"));
      radioButtonTypeSong  .addItemListener(this);
      radioButtonTypeLesson.addItemListener(this);
      radioButtonTypeSong  .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      radioButtonTypeLesson.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      radioButtonTypeSong  .setMargin(new java.awt.Insets(0, 0, 0, 0));
      radioButtonTypeLesson.setMargin(new java.awt.Insets(0, 0, 0, 0));

      // create the button group that groups the radio buttons together
      radioButtonGroupType = new ButtonGroup();
      radioButtonGroupType.add(radioButtonTypeSong  );
      radioButtonGroupType.add(radioButtonTypeLesson);

      // create the panel that will hold all the components
      panelType = new JPanel();
      panelType.setBorder(BorderFactory.createTitledBorder(null, ResourceBundle.getString("dialog.song_information.border.type"), TitledBorder.LEFT, TitledBorder.TOP));

      // layout the components in the panel
      GroupLayout panelTypeLayout = new GroupLayout(panelType);
      panelType.setLayout(panelTypeLayout);

      panelTypeLayout.setHorizontalGroup(
         panelTypeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelTypeLayout.createSequentialGroup()
            .addGroup(panelTypeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(radioButtonTypeSong)
                .addComponent(radioButtonTypeLesson))
            .addContainerGap(GAP_WIDTH, Short.MAX_VALUE))
      );
      panelTypeLayout.setVerticalGroup(
         panelTypeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelTypeLayout.createSequentialGroup()
            .addComponent(radioButtonTypeSong)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonTypeLesson))
      );
      return panelType;
   }

   /**
    * @return a panel with the "song" labels and text fields.
    */
   private JPanel createPanelSong()
   {
      // create the "artist" label and text field
      labelArtist = new JLabel(ResourceBundle.getString("dialog.song_information.label.artist") + " ");
      labelArtist.setHorizontalAlignment(JLabel.RIGHT);
      textFieldArtist = new JTextField();
      textFieldArtist.setText(information.getArtist() == null ? "" : information.getArtist());
      textFieldArtist.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.artist"));

      // create the "album" label and text field
      labelAlbum = new JLabel(ResourceBundle.getString("dialog.song_information.label.album") + " ");
      labelAlbum.setHorizontalAlignment(JLabel.RIGHT);
      textFieldAlbum = new JTextField();
      textFieldAlbum.setText(information.getAlbumTitle() == null ? "" : information.getAlbumTitle());
      textFieldAlbum.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.album"));

      // create the "song" label and text field
      labelSong = new JLabel(ResourceBundle.getString("dialog.song_information.label.song") + " ");
      labelSong.setHorizontalAlignment(JLabel.RIGHT);
      textFieldSong = new JTextField();
      textFieldSong.setText(information.getSongTitle() == null ? "" : information.getSongTitle());
      textFieldSong.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.song"));

      // create the "year released" label and text field
      labelYearReleased = new JLabel(ResourceBundle.getString("dialog.song_information.label.album_release_year") + " ");
      labelYearReleased.setHorizontalAlignment(JLabel.RIGHT);
      textFieldYearReleased = new JTextField();
      textFieldYearReleased.setText(information.getAlbumReleaseYear() == -1 ? "" : String.valueOf(information.getAlbumReleaseYear()));
      textFieldYearReleased.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.album_release_year"));

      // create the "music by" label and text field
      labelMusicBy = new JLabel(ResourceBundle.getString("dialog.song_information.label.music_by") + " ");
      labelMusicBy.setHorizontalAlignment(JLabel.RIGHT);
      textFieldMusicBy = new JTextField();
      textFieldMusicBy.setText(information.getMusicBy() == null ? "" : information.getMusicBy());
      textFieldMusicBy.setScrollOffset(0);
      textFieldMusicBy.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.music_by"));

      // create the "words by" label and text field
      labelWordsBy = new JLabel(ResourceBundle.getString("dialog.song_information.label.words_by") + " ");
      labelWordsBy.setHorizontalAlignment(JLabel.RIGHT);
      textFieldWordsBy = new JTextField();
      textFieldWordsBy.setText(information.getWordsBy() == null ? "" : information.getWordsBy());
      textFieldWordsBy.setScrollOffset(0);
      textFieldWordsBy.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.words_by"));

      // create the "transcribed by" label and text field
      labelTranscribedBy = new JLabel(ResourceBundle.getString("dialog.song_information.label.transcribed_by") + " ");
      labelTranscribedBy.setHorizontalAlignment(JLabel.RIGHT);
      textFieldTranscribedBy = new JTextField();
      textFieldTranscribedBy.setText(information.getTranscribedBy() == null ? "" : information.getTranscribedBy());
      textFieldTranscribedBy.setScrollOffset(0);
      textFieldTranscribedBy.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.transcribed_by"));

      // create the "copyright" label and text field
      labelCopyright = new JLabel(ResourceBundle.getString("dialog.song_information.label.copyright") + " ");
      labelCopyright.setHorizontalAlignment(JLabel.RIGHT);
      textFieldCopyright = new JTextField();
      textFieldCopyright.setText(information.getCopyright() == null ? "" : information.getCopyright());
      textFieldCopyright.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.copyright"));

      // create the panel that will hold all the components
      panelSong = new JPanel();
      panelSong.setBorder(BorderFactory.createTitledBorder(null, ResourceBundle.getString("dialog.song_information.border.song"), TitledBorder.LEFT, TitledBorder.TOP));

      // layout the components in the panel
      GroupLayout panelSongLayout = new GroupLayout(panelSong);
      panelSong.setLayout(panelSongLayout);
      panelSongLayout.setHorizontalGroup(
         panelSongLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelSongLayout.createSequentialGroup()
            .addGap(10, 10, 10)
            .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(labelArtist       , GroupLayout.Alignment.TRAILING)
               .addComponent(labelAlbum        , GroupLayout.Alignment.TRAILING)
               .addComponent(labelSong         , GroupLayout.Alignment.TRAILING)
               .addComponent(labelYearReleased , GroupLayout.Alignment.TRAILING)
               .addComponent(labelMusicBy      , GroupLayout.Alignment.TRAILING)
               .addComponent(labelWordsBy      , GroupLayout.Alignment.TRAILING)
               .addComponent(labelTranscribedBy, GroupLayout.Alignment.TRAILING)
               .addComponent(labelCopyright    , GroupLayout.Alignment.TRAILING))
            .addGap(10, 10, 10)
            .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(textFieldArtist       , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldAlbum        , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldSong         , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldYearReleased , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldMusicBy      , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldWordsBy      , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldTranscribedBy, GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldCopyright    , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      panelSongLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {textFieldAlbum, textFieldArtist, textFieldCopyright, textFieldMusicBy, textFieldSong, textFieldTranscribedBy, textFieldWordsBy, textFieldYearReleased});

      panelSongLayout.setVerticalGroup(
          panelSongLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(panelSongLayout.createSequentialGroup()
              .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(labelArtist)
                  .addComponent(textFieldArtist, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(labelAlbum)
                  .addComponent(textFieldAlbum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(labelSong)
                  .addComponent(textFieldSong, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(labelYearReleased)
                  .addComponent(textFieldYearReleased, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(labelMusicBy)
                  .addComponent(textFieldMusicBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(labelWordsBy)
                  .addComponent(textFieldWordsBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(textFieldTranscribedBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                  .addComponent(labelTranscribedBy))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(panelSongLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(labelCopyright)
                  .addComponent(textFieldCopyright, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
      );
      panelSongLayout.linkSize(SwingConstants.VERTICAL, new Component[] {textFieldAlbum, textFieldArtist, textFieldCopyright, textFieldMusicBy, textFieldSong, textFieldTranscribedBy, textFieldWordsBy, textFieldYearReleased});

      return panelSong;
   }

   /**
    * @return a panel with the "lesson" labels and text fields.
    */
   private JPanel createPanelLesson()
   {
      // create the "lesson" label and text field
      labelLesson = new JLabel(ResourceBundle.getString("dialog.song_information.label.lesson_title") + " ");
      labelLesson.setHorizontalAlignment(JLabel.RIGHT);
      textFieldLesson = new JTextField();
      textFieldLesson.setText(information.getLessonTitle() == null ? "" : information.getLessonTitle());
      textFieldLesson.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.lesson_title"));

      // create the "lesson subtitle" label and text field
      labelLessonSubTitle = new JLabel(ResourceBundle.getString("dialog.song_information.label.lesson_sub_title") + " ");
      labelLessonSubTitle.setHorizontalAlignment(JLabel.RIGHT);
      textFieldLessonSubTitle = new JTextField();
      textFieldLessonSubTitle.setText(information.getLessonSubTitle() == null ? "" : information.getLessonSubTitle());
      textFieldLessonSubTitle.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.lesson_sub_title"));

      // create the "lesson by" label and text field
      labelLessonBy = new JLabel(ResourceBundle.getString("dialog.song_information.label.lesson_by") + " ");
      labelLessonBy.setHorizontalAlignment(JLabel.RIGHT);
      textFieldLessonBy = new JTextField();
      textFieldLessonBy.setText(information.getLessonBy() == null ? "" : information.getLessonBy());
      textFieldLessonBy.setToolTipText(ResourceBundle.getString("dialog.song_information.tooltip.lesson_by"));

      // create the panel that will hold all the components
      panelLesson = new JPanel();
      panelLesson.setBorder(BorderFactory.createTitledBorder(null, ResourceBundle.getString("dialog.song_information.border.lesson"), TitledBorder.LEFT, TitledBorder.TOP));

      // layout the components in the panel
      GroupLayout panelLessonLayout = new GroupLayout(panelLesson);
      panelLesson.setLayout(panelLessonLayout);

      panelLessonLayout.setHorizontalGroup(
         panelLessonLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelLessonLayout.createSequentialGroup()
            .addGap(10, 10, 10)
            .addGroup(panelLessonLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(labelLesson        , GroupLayout.Alignment.TRAILING)
               .addComponent(labelLessonSubTitle, GroupLayout.Alignment.TRAILING)
               .addComponent(labelLessonBy      , GroupLayout.Alignment.TRAILING))
            .addGap(10, 10, 10)
            .addGroup(panelLessonLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(textFieldLesson        , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldLessonSubTitle, GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(textFieldLessonBy      , GroupLayout.PREFERRED_SIZE, TEXT_FIELD_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      panelLessonLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {textFieldLesson, textFieldLessonBy, textFieldLessonSubTitle});

      panelLessonLayout.setVerticalGroup(
         panelLessonLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(panelLessonLayout.createSequentialGroup()
            .addGroup(panelLessonLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelLesson)
               .addComponent(textFieldLesson        , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLessonLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelLessonSubTitle)
               .addComponent(textFieldLessonSubTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLessonLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
               .addComponent(labelLessonBy)
               .addComponent(textFieldLessonBy      , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(130, 130, 130))
      );
      panelLessonLayout.linkSize(SwingConstants.VERTICAL, new Component[] {textFieldLesson, textFieldLessonBy, textFieldLessonSubTitle});

      return panelLesson;
   }

   /**
    * returns the song information.
    */
   public Information getInformation()
   {
      return information;
   }

   /**
    * implements the ItemListener interface, and is used to detect when the "song" or "lesson" radio buttons are clicked.
    * <br/><br/>
    * @param event   the button the user clicked.
    */
   public void itemStateChanged(ItemEvent event)
   {
      String radioButton = ((AbstractButton)event.getItemSelectable()).getActionCommand();

      if (radioButton.equals("song"))
      {
         Information.FileType filetype = event.getStateChange() == ItemEvent.SELECTED ? Information.FileType.Song : Information.FileType.Lesson;
         setEnabled             (filetype);
         setFocusTraversalPolicy(filetype);
      }
   }

   /**
    * implements the abstract method declared in the base class and is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
      // store the updated information back in the song
      information.setFileType         (radioButtonGroupType.getSelection().getActionCommand().equals("song") ? Information.FileType.Song : Information.FileType.Lesson);
      information.setAlbumReleaseYear (textFieldYearReleased.getText().trim().length() == 0 ? -1 : Short.valueOf(textFieldYearReleased.getText().trim()).shortValue());

      information.setArtist           (textFieldArtist         .getText().trim().length() == 0 ? null : textFieldArtist         .getText().trim());
      information.setAlbumTitle       (textFieldAlbum          .getText().trim().length() == 0 ? null : textFieldAlbum          .getText().trim());
      information.setSongTitle        (textFieldSong           .getText().trim().length() == 0 ? null : textFieldSong           .getText().trim());
      information.setWordsBy          (textFieldWordsBy        .getText().trim().length() == 0 ? null : textFieldWordsBy        .getText().trim());
      information.setMusicBy          (textFieldMusicBy        .getText().trim().length() == 0 ? null : textFieldMusicBy        .getText().trim());
      information.setTranscribedBy    (textFieldTranscribedBy  .getText().trim().length() == 0 ? null : textFieldTranscribedBy  .getText().trim());
      information.setCopyright        (textFieldCopyright      .getText().trim().length() == 0 ? null : textFieldCopyright      .getText().trim());
      information.setPerformanceNotes (textAreaPerformanceNotes.getText().trim().length() == 0 ? null : textAreaPerformanceNotes.getText().trim());
      information.setLessonTitle      (textFieldLesson         .getText().trim().length() == 0 ? null : textFieldLesson         .getText().trim());
      information.setLessonSubTitle   (textFieldLessonSubTitle .getText().trim().length() == 0 ? null : textFieldLessonSubTitle .getText().trim());
      information.setLessonBy         (textFieldLessonBy       .getText().trim().length() == 0 ? null : textFieldLessonBy       .getText().trim());
   }

   /**
    * enables\disables the controls in the dialog box based on which radio button the user selected.
    * <br/><br/>
    * @param fileType   the type of tab file the user wants to create.
    */
   private void setEnabled(Information.FileType fileType)
   {
      Component[] songComponents     = {textFieldArtist, textFieldAlbum, textFieldSong, textFieldYearReleased, textFieldMusicBy, textFieldWordsBy, textFieldTranscribedBy, textFieldCopyright};
      Component[] lessonComponents   = {textFieldLesson, textFieldLessonSubTitle, textFieldLessonBy};
      Component[] enabledComponents  = fileType == Information.FileType.Song ? songComponents   : lessonComponents;
      Component[] disabledComponents = fileType == Information.FileType.Song ? lessonComponents : songComponents;

      for(Component component : enabledComponents)
         component.setEnabled(true);
      for(Component component : disabledComponents)
         component.setEnabled(false);
   }

   /**
    * sets the focus traversal policy of the dialog box based on which radio button the user selected.
    * <br/><br/>
    * @param fileType   the type of tab file the user wants to create.
    */
   private void setFocusTraversalPolicy(Information.FileType fileType)
   {
      OkCancelHelpButtonPanel okCancelHelpButtonPanel = getOkCancelHelpButtonPanel();
      Component[]             songComponents          = {radioButtonTypeSong, radioButtonTypeLesson,
                                                         textFieldArtist, textFieldAlbum, textFieldSong, textFieldYearReleased, textFieldMusicBy, textFieldWordsBy, textFieldTranscribedBy, textFieldCopyright, textAreaPerformanceNotes,
                                                         okCancelHelpButtonPanel.getOkButton(), okCancelHelpButtonPanel.getCancelButton(), okCancelHelpButtonPanel.getHelpButton()};
      Component[]             lessonComponents        = {radioButtonTypeSong, radioButtonTypeLesson,
                                                         textFieldLesson, textFieldLessonSubTitle, textFieldLessonBy, textAreaPerformanceNotes,
                                                         okCancelHelpButtonPanel.getOkButton(), okCancelHelpButtonPanel.getCancelButton(), okCancelHelpButtonPanel.getHelpButton()};

      setFocusTraversalPolicy(new CustomFocusTraversalPolicy(fileType == Information.FileType.Song ? songComponents : lessonComponents));
   }
}
