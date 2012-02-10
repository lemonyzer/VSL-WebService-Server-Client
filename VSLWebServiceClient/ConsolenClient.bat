@echo off

rem set JAVA=C:\Programme\Java\jdk1.7.0_02\bin
rem set path=%JAVA%;%path%

cd /d %~dp0

cd .\src
start "" /wait EnterIPcreateServerClasses.bat
cd ..

echo Projektverzeichnis: %cd%
set bin=%cd%\bin
echo Compilierte Dateien: %bin%
echo.
cd src

echo compilingConsolenClient.java
javac -d %bin% -cp . de/jxws/ConsolenClient.java
echo.

cd ..
echo starting Clientconsole
java -cp %bin% de.jxws.ConsolenClient

pause
cmd