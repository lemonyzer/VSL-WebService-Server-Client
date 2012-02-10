@echo off

rem ###############################################################
set JAVA=C:\Programme\Java\jdk1.6.0_29\bin
set path=%JAVA%;%path%
rem ###############################################################


cd /d %~dp0
echo %cd%


rem ################################################################
IPCONFIG |FIND "IP" > %temp%\TEMPIP.txt
FOR /F "tokens=2 delims=:" %%a in (%temp%\TEMPIP.txt) do set IP=%%a
del %temp%\TEMPIP.txt
set IP=%IP:~1%
echo %IP% >%temp%\ip.txt
echo The current IP address is "%IP%"
pause
rem ################################################################

wsimport -s . http://%IP%:8080/jaxws/server?WSDL

pause