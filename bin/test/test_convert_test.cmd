@echo off 
set classpath=%tab_home%\lib\beaglebuddy_ptb_file_reader.jar;%tab_home%\lib\beaglebuddy_tab.jar
set ptb_song_dir=%tab_home%\songs
set tab_results_dir=%tab_home%\tmp\bbt_txt


: %java_home%\bin\java -Dtab.home=%tab_home% -enableassertions com.beaglebuddy.tab.test.ReadTest "c:\instrument_in_test.ptb"    > "%tab_results_dir%\instrument_in_test.ptb.txt"
%java_home%\bin\java -Dtab.home=%tab_home% -enableassertions com.beaglebuddy.tab.test.ReadTest "%ptb_song_dir%\king crimson\lady_of_the_dancing_water.ptb"    > "%tab_results_dir%\lady_of_the_dancing_water.txt"                                                        