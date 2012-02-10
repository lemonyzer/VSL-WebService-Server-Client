@echo off

rem ###############################################################
set JAVA=C:\Programme\Java\jdk1.6.0_29\bin
set path=%JAVA%;%path%
rem ###############################################################


cd /d %~dp0
echo %cd%


rem ################################################################
set /p ip=IP des JAXWS Servers:
rem ################################################################

wsimport -s . http://%IP%:8080/jaxws/server?WSDL

echo Transfer komplett?
pause
exit