set classpath=%tab_home%\lib\beaglebuddy_ptb_file_reader.jar;%tab_home%\lib\beaglebuddy_tab.jar
set bbt_song_dir=%tab_home%\songs
set tab_results_dir=%tab_home%\tmp\bbt_txt


%java_home%\bin\java com.beaglebuddy.tab.test.ReadTest "%bbt_song_dir%\test.bbt"   >   %tab_results_dir%\"test.txt"
