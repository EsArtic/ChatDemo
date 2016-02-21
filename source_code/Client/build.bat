@echo off
cd src
javac -d ..\bin -classpath .;..\jar\jsonAnalyzer.jar;..\bin Client.java thread\*.java jsonAnalyzer\*.java data\*.java ui\*.java
pause