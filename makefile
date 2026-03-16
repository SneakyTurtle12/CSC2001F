JC = javac
JVM = java
JFLAGS = -g

CLASSES = src/PlaceExperiment.java \
          src/PlaceNameEntry.java \
          srcPlaceNameArray.java \
          src/PlaceNameBST.java \
          src/PlaceSearchArray.java \
          src/PlaceSearchBST.java

MAIN = PlaceExperiment

default: classes

classes: $(CLASSES:.java=.class)

%.class: %.java
	$(JC) $(JFLAGS) $<

run: classes
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
