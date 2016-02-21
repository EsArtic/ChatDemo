@echo off
cd src
javac -d ..\bin -classpath .;..\jar\jsonAnalyzer.jar;..\jar\sqlite-jdbc-3.8.11.2.jar *.java
pause