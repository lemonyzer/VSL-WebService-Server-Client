@echo off
IPCONFIG |FIND "IP" > %temp%\TEMPIP.txt
FOR /F "tokens=2 delims=:" %%a in (%temp%\TEMPIP.txt) do set IP=%%a
del %temp%\TEMPIP.txt
set IP=%IP:~1%
echo %IP% >%temp%\ip.txt
echo The current IP address is "%IP%"
pause