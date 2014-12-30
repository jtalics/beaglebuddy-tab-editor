@echo off
: -----------------------------------------------------------------------------
: this command runs the beaglebuddy power tab editor
: -----------------------------------------------------------------------------
call check_env_vars.cmd run 
: if errorlevel 1 goto done

setlocal
cls
title=Beaglebuddy Tab Editor
pushd

set path=%path%;%java_home%\bin
set classpath=%tab_home%\lib\jhall.jar;%tab_home%\lib\beaglebuddy_ptb_file_reader.jar;%tab_home%\lib\beaglebuddy_tab.jar

"%java_home%\bin\java" -Dtab.home=%tab_home% -enableassertions com.beaglebuddy.tab.gui.mainframe.MainFrame

popd