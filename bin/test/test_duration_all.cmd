@echo off
: -----------------------------------------------------------------------------
: this command runs the beaglebuddy power tab file reader on all duration tests
: -----------------------------------------------------------------------------
call ..\check_env_vars.cmd run
: if errorlevel 1 goto done

setlocal
cls
title=Ptb File Reader Test
pushd

cd %tab_home%\bin\test
set path=%path%;%java_home%\bin
set classpath=%tab_home%\lib\beaglebuddy_ptb_file_reader.jar;%tab_home%\lib\beaglebuddy_tab.jar


: set the directories where the power tab songs are located and where the resulting conversions will be written to
set ptb_song_dir=%tab_home%\songs
set bbt_results_dir=%tab_home%\tmp\bbt_txt
set ptb_results_dir=%tab_home%\tmp\ptb_txt

: del %ptb_results_dir%\*.txt


@echo on
java                       -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_1.ptb"  > "%ptb_results_dir%\irregular_grouping_1.txt"
java                       -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_2.ptb"  > "%ptb_results_dir%\irregular_grouping_2.txt"
java                       -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_3.ptb"  > "%ptb_results_dir%\irregular_grouping_3.txt"
java                       -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_4.ptb"  > "%ptb_results_dir%\irregular_grouping_4.txt"
java                       -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_5.ptb"  > "%ptb_results_dir%\irregular_grouping_5.txt"

java -Dtab.home=%tab_home% -enableassertions com.beaglebuddy.tab.test.ReadTest    "%ptb_song_dir%\test\durations\irregular_grouping_1.ptb"  > "%bbt_results_dir%\irregular_grouping_1.txt"
java -Dtab.home=%tab_home% -enableassertions com.beaglebuddy.tab.test.ReadTest    "%ptb_song_dir%\test\durations\irregular_grouping_2.ptb"  > "%bbt_results_dir%\irregular_grouping_2.txt"
java -Dtab.home=%tab_home% -enableassertions com.beaglebuddy.tab.test.ReadTest    "%ptb_song_dir%\test\durations\irregular_grouping_3.ptb"  > "%bbt_results_dir%\irregular_grouping_3.txt"
java -Dtab.home=%tab_home% -enableassertions com.beaglebuddy.tab.test.ReadTest    "%ptb_song_dir%\test\durations\irregular_grouping_4.ptb"  > "%bbt_results_dir%\irregular_grouping_4.txt"
java -Dtab.home=%tab_home% -enableassertions com.beaglebuddy.tab.test.ReadTest    "%ptb_song_dir%\test\durations\irregular_grouping_5.ptb"  > "%bbt_results_dir%\irregular_grouping_5.txt"

@echo off
popd
endlocal
:done
