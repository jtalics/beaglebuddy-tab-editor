@echo off
: -----------------------------------------------------------------------------
: ant make command
:
: this command uses the makefile.xml file located in the current directory
: -----------------------------------------------------------------------------
call check_env_vars.cmd build

setlocal
cls
title=Tab

: ----------------------
: set path, classpath, vars
: ----------------------
set target=%1
set path=%path%;%ant_home%\bin
set classpath=%tab_home%\lib\jhall.jar;%tab_home%\lib\beaglebuddy_ptb_file_reader.jar

: ----------------------
: run ant
: ----------------------
ant -Dbasedir=%tab_home% -Dtmp=%temp% -Ddebug=true -buildfile build.xml %target%

endlocal
:done
