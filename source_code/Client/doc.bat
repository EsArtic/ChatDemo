@echo off
cd src
javadoc -private -author -version -d ..\doc -classpath .;..\jar\jsonAnalyzer.jar Client.java thread\*.java jsonAnalyzer\*.java data\*.java ui\*.java
cd ..
pause