package nl.tudelft.context.model.annotation;

import nl.tudelft.context.model.Parser;

import java.io.BufferedReader;
import java.util.Scanner;


/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationParser extends Parser<AnnotationMap> {

    /**
     * Parse the file into an AnnotationMap.
     *
     * @param readerList Reader to read.
     * @return A parsed annotationmap.
     */
    public AnnotationMap parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        Scanner sc = new Scanner(reader);
        AnnotationMap annotationMap = new AnnotationMap();
        String line = "";
        String fileSplitBy = "\\t";
        int index = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] splitLine = line.split(fileSplitBy);
            annotationMap.put(index, getAnnotation(splitLine));
            index++;
        }

        return annotationMap;
    }

    /**
     * Read a splitted line and generate an annotation.
     *
     * @param splitLine the line with information for the annotation
     * @return Annotation
     * @throws NumberFormatException when the data isn't correct
     */
    public final Annotation getAnnotation(final String[] splitLine) throws NumberFormatException {

        String seqId = splitLine[0];
        String source = splitLine[1];
        String type = splitLine[2];
        int start = Integer.parseInt(splitLine[3]);
        int end = Integer.parseInt(splitLine[4]);
        float score = Float.parseFloat(splitLine[5]);
        char strand = splitLine[6].charAt(0);
        char phase = splitLine[7].charAt(0);
        String attributes = splitLine[8];

        Annotation annotation = new Annotation(seqId, source, type, start, end, score, strand, phase, attributes);
        return annotation;
    }
}
