@echo off
: -----------------------------------------------------------------------------
: check that all necessary environment variables are set.
: -----------------------------------------------------------------------------

: determine whether or not ant is needed
set command=%1
if %command%==build goto check_ant
if %command%==run   goto ant_ok

: ----------------------
: check ant
: ----------------------
:check_ant
if "%ant_home%".=="".                 goto ant_home_not_set
if not exist "%ant_home%"             goto ant_home_dir_doesnt_exist
if not exist "%ant_home%\bin\ant.bat" goto ant_home_dir_invalid
goto ant_ok

:ant_home_not_set
echo The ANT_HOME environment variable has not been set.
exit /b 1

:ant_home_dir_doesnt_exist
echo The directory specified by the ANT_HOME environment variable, %ANT_HOME%, does not exist.
exit /b 1

:ant_home_dir_invalid
echo The directory specified by the ANT_HOME environment variable, %ANT_HOME%, does not contain ant.
exit /b 1

: ----------------------
: check java
: ----------------------
:ant_ok
if "%java_home%".=="".                  goto java_home_not_set
if not exist "%java_home%"              goto java_home_dir_doesnt_exist
if not exist "%java_home%\bin\java.exe" goto java_home_dir_invalid
goto java_ok

:java_home_not_set
echo The JAVA_HOME environment variable has not been set.
exit /b 1

:java_home_dir_doesnt_exist
echo The directory specified by the JAVA_HOME environment variable, %JAVA_HOME%, does not exist.
exit /b 1

:java_home_dir_invalid
echo The directory specified by the JAVA_HOME environment variable, %JAVA_HOME%, does not contain java.exe.
exit /b 1

: ----------------------
: check tab
: ----------------------
:java_ok
if "%tab_home%".=="".                  goto tab_home_not_set
if not exist "%tab_home%"              goto tab_home_dir_doesnt_exist
if not exist "%tab_home%\bin\make.cmd" goto tab_home_dir_invalid
goto tab_ok

:tab_home_not_set
echo The TAB_HOME environment variable has not been set.
exit /b 1

:tab_home_dir_doesnt_exist
echo The directory specified by the TAB_HOME environment variable, %TAB_HOME%, does not exist.
exit /b 1

:tab_home_dir_invalid
echo The directory specified by the TAB_HOME environment variable, %TAB_HOME%, does not contain make.cmd.
exit /b 1


:tab_ok
