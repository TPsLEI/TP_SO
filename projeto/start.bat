javac -cp ./lib/json.jar;./lib/jfreechart.jar;./lib/jcommon.jar -d ./classes -encoding UTF-8 *.java
java -cp .;./lib/json.jar;./lib/jfreechart.jar;./lib/jcommon.jar;./classes Kernel