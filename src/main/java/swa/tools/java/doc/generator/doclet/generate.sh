#!/bin/bash

cp /home/jinyan/Desktop/Doclava.class /home/jinyan/Documents/github/toos/target/classes/swa/tools/java/doc/generator/doclet
cp /home/jinyan/Desktop/Doclava.java /home/jinyan/Documents/github/toos/src/main/java/swa/tools/java/doc/generator/doclet
cd /home/jinyan/Documents//github/toos/src/main/java/swa/tools &&
javadoc -doclet swa.tools.java.doc.generator.doclet.Doclava -docletpath /home/jinyan/Documents/github/toos/target/classes/  *.java

rm /home/jinyan/Documents/github/toos/target/classes/swa/tools/java/doc/generator/doclet/Doclava.class
rm /home/jinyan/Documents/github/toos/src/main/java/swa/tools/java/doc/generator/doclet/Doclava.java

