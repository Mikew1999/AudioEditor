# Audio Editor

Audio editor is a java application designed to convert audio files to .wav with the following settings:
  - 8000 hz
  - Mono audio
  - 32 bit floating point

# Compilation and creating jar file
  - open the directory in the command prompt
  - "Main-Class: main.Main" > manifest.txt
  To compile for java version 8:
  - javac -source 8 -target 8 -sourcepath ./src/ -d ./out/ ./src/main/Main.java
  To create jar file
  - jar cvfm AudioEditor.jar manifest.txt -C ./out/ .