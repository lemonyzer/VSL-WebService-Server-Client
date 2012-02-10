@echo off

set JAVA=C:\Programme\Java\jdk1.7.0_02\bin
set path=%JAVA%;%path%

cd /d %~dp0
echo Projektverzeichnis: %cd%
set bin=%cd%\bin
echo Compilierte Dateien: %bin%
echo.
cd src

echo compiling ServerPublisher.java
javac -d %bin% -cp . de/jxws/ServerPublisher.java
echo.

cd ..
echo starting ServerPublisher
java -cp %bin% de.jxws.ServerPublisher

pause
cmd