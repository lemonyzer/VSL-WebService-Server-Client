@echo off

rem ###############################################################
rem set JAVA=C:\Programme\Java\jdk1.6.0_29\bin
rem set path=%JAVA%;%path%
rem ###############################################################


cd /d %~dp0
echo %cd%


rem ################################################################
set /p ip=IP: 
if not defined ip (
  echo Server läuft lokal
  set ip=localhost
)
rem ################################################################

wsimport -s . http://%ip%:8080/jaxws/server?WSDL

pause