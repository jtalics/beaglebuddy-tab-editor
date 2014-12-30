@echo off
: -----------------------------------------------------------------------------
: this command runs the beaglebuddy power tab file reader on all songs
:
: dir /b /one /s *.ptb > list.txt
: -----------------------------------------------------------------------------
call ..\check_env_vars.cmd run
: if errorlevel 1 goto done

setlocal
cls
title=Ptb File Reader Test
pushd

cd %tab_home%\bin\test
set path=%path%;%java_home%\bin
set classpath=%tab_home%\lib\beaglebuddy_ptb_file_reader.jar


: set the directories where the power tab songs are located and where the resulting conversions will be written to
set ptb_song_dir=%tab_home%\songs
set ptb_results_dir=%tab_home%\tmp\ptb_txt

del %ptb_results_dir%\*.txt


@echo on
: java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_1.ptb"  > "%ptb_results_dir%\irregular_grouping_1.txt"
: java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_2.ptb"  > "%ptb_results_dir%\irregular_grouping_2.txt"
: java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_3.ptb"  > "%ptb_results_dir%\irregular_grouping_3.txt"
: java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\durations\irregular_grouping_4.ptb"  > "%ptb_results_dir%\irregular_grouping_4.txt"

java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\augmented_chords.ptb"                 > "%ptb_results_dir%\augmented_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\augmented_7th_chords.ptb"             > "%ptb_results_dir%\augmented_7th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\diminished_chords.ptb"                > "%ptb_results_dir%\diminished_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\diminished_7th_chords.ptb"            > "%ptb_results_dir%\diminished_7th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\dominant_7th_chords.ptb"              > "%ptb_results_dir%\dominant_7th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\dominant_7th_suspended_chords.ptb"    > "%ptb_results_dir%\dominant_7th_suspended_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\major_chords.ptb"                     > "%ptb_results_dir%\major_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\major_6th_chords.ptb"                 > "%ptb_results_dir%\major_6th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\major_7th_chords.ptb"                 > "%ptb_results_dir%\major_7th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\minor_chords.ptb"                     > "%ptb_results_dir%\minor_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\minor_6th_chords.ptb"                 > "%ptb_results_dir%\minor_6th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\minor_7th_chords.ptb"                 > "%ptb_results_dir%\minor_7th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\min_maj_7th_chords.ptb"               > "%ptb_results_dir%\min_maj_7th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\power_5th_chords.ptb"                 > "%ptb_results_dir%\power_5th_chords.txt"
java -enableassertions com.beaglebuddy.ptb.test.PTBFileTest "%ptb_song_dir%\test\chord_name\suspended_chords.ptb"                 > "%ptb_results_dir%\suspended_chords.txt"


popd
endlocal
:done
