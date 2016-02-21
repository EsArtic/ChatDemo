@echo off
cd bin
java -classpath .;..\jar\jsonAnalyzer.jar;..\jar\sqlite-jdbc-3.8.11.2.jar Server
pause