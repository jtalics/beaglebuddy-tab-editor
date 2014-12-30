set classpath=%tab_home%\lib\beaglebuddy_ptb_file_reader.jar;%tab_home%\lib\beaglebuddy_tab.jar
set ptb_song_dir=%tab_home%\songs
set tab_results_dir=%tab_home%\tmp\bbt_txt
del %tab_results_dir%\*.txt
cls

%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_1_no_layout.ptb     > %tab_results_dir%\drawing_position_1_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_1_after_layout.ptb  > %tab_results_dir%\drawing_position_1_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_2_no_layout.ptb     > %tab_results_dir%\drawing_position_2_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_2_after_layout.ptb  > %tab_results_dir%\drawing_position_2_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_3_after_layout.ptb  > %tab_results_dir%\drawing_position_3_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_3_after_layout.ptb  > %tab_results_dir%\drawing_position_3_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_4_no_layout.ptb     > %tab_results_dir%\drawing_position_4_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_4_after_layout.ptb  > %tab_results_dir%\drawing_position_4_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_5_after_layout.ptb  > %tab_results_dir%\drawing_position_5_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_5_after_layout.ptb  > %tab_results_dir%\drawing_position_5_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_6_no_layout.ptb     > %tab_results_dir%\drawing_position_6_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_6_after_layout.ptb  > %tab_results_dir%\drawing_position_6_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_7_no_layout.ptb     > %tab_results_dir%\drawing_position_7_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_7_after_layout.ptb  > %tab_results_dir%\drawing_position_7_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_8_no_layout.ptb     > %tab_results_dir%\drawing_position_8_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_8_after_layout.ptb  > %tab_results_dir%\drawing_position_8_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_9_no_layout.ptb     > %tab_results_dir%\drawing_position_9_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_9_after_layout.ptb  > %tab_results_dir%\drawing_position_9_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_10_no_layout.ptb    > %tab_results_dir%\drawing_position_10_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_10_after_layout.ptb > %tab_results_dir%\drawing_position_10_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_11_no_layout.ptb    > %tab_results_dir%\drawing_position_11_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_11_after_layout.ptb > %tab_results_dir%\drawing_position_11_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_12_no_layout.ptb    > %tab_results_dir%\drawing_position_12_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_12_after_layout.ptb > %tab_results_dir%\drawing_position_12_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_13_no_layout.ptb    > %tab_results_dir%\drawing_position_13_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_13_after_layout.ptb > %tab_results_dir%\drawing_position_13_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_14_no_layout.ptb    > %tab_results_dir%\drawing_position_14_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_14_after_layout.ptb > %tab_results_dir%\drawing_position_14_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_15_no_layout.ptb    > %tab_results_dir%\drawing_position_15_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_15_after_layout.ptb > %tab_results_dir%\drawing_position_15_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_16_no_layout.ptb    > %tab_results_dir%\drawing_position_16_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_16_after_layout.ptb > %tab_results_dir%\drawing_position_16_after_layout_ptb.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.tab.test.ReadTest    %ptb_song_dir%\drawing_position_17_no_layout.ptb    > %tab_results_dir%\drawing_position_17_after_layout_bbt.txt
%java_home%\bin\java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest %ptb_song_dir%\drawing_position_17_after_layout.ptb > %tab_results_dir%\drawing_position_17_after_layout_ptb.txt