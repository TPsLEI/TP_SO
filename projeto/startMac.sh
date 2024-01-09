#! /bin/bash
javac -cp ./lib/miglayout.jar:./lib/miglayoutcore.jar:./lib/flatlaf.jar:./lib/json.jar:./lib/jfreechart.jar:./lib/jcommon.jar -d ./classes -encoding UTF-8 *.java
java -cp .:./lib/miglayout.jar:./lib/miglayoutcore.jar:./lib/flatlaf.jar:./lib/json.jar:./lib/jfreechart.jar:./lib/jcommon.jar:./classes Kernel