mkdir bin
javac -d bin -cp src src/SameGame.java
jar cfe SameGame.jar SameGame -C bin . 
java -jar SameGame.jar
@echo off
pause
