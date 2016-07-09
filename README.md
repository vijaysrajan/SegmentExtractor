# SegmentExtractor
==================

Rewrote the Segment Extractor to Generate the Json Output to be read by the UI.
This project is based on Maven. It generates a Uber Jar with all the dependencies.

Precis output files are "data" folder.

This program reads the data folder for the first 4 stages of precis output, Builds the Json Tree 
and writes the Json Tree as a flat file - "Segments.json" & "AllSegments.json" in "data" folder.

Segments.json - Prints only the Highest Spread of Dimensions.

AllSegments.json - Println all the dimension/value combination in case if the user want to navigate any dimension/value combination he/she wishes.

Compilation & Running:-
=======================

1) Checkout the git folder.

2) go to SegmentExtractor folder.

3) Exec maven command - "mvn clean package"

4) Exec java command - "java -cp target/SegmentExtractor-0.0.1-SNAPSHOT.jar com.fratics.segmentextractor.main.Main"

5) Check the resulting output file "Segments.json" in "data" folder.

6) Exec java command - "java -cp target/SegmentExtractor-0.0.1-SNAPSHOT.jar com.fratics.segmentextractor.main.MainAllCandidates"

7) Check the resulting output file "AllSegments.json" in "data" folder.

Happy preciiiing..


