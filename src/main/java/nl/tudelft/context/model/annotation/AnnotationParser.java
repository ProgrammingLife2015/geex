package nl.tudelft.context.model.annotation;

import nl.tudelft.context.model.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationParser extends Parser<AnnotationMap> {

    /**
     * Create a new AnnotationParser.
     * @param file File to parse
     * @throws FileNotFoundException The file is not found.
     * @throws UnsupportedEncodingException The file contains an unsupported encoding (not UTF-8).
     */
    public AnnotationParser(final File file) throws FileNotFoundException, UnsupportedEncodingException {
        super(file);
    }

    /**
     * Parse the file into an AnnotationMap.
     * @param reader Reader to read.
     * @return A parsed annotationmap.
     */
    public AnnotationMap parse(final BufferedReader reader) {
        Scanner sc = new Scanner(reader);
        AnnotationMap annotationMap = new AnnotationMap();
        String line = "";
        String cvsSplitBy = ",";
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] splitLine = line.split(cvsSplitBy);
            annotationMap.put(splitLine[1], getAnnotation(splitLine));
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
        int id = Integer.parseInt(splitLine[0]);
        String name = splitLine[1];
        Boolean strand = splitLine[2].equals("+");
        int start = Integer.parseInt(splitLine[3]);
        int end = Integer.parseInt(splitLine[4]);
        String proteinName = splitLine[5];
        Annotation annotation = new Annotation(id, name, strand, start, end, proteinName);
        return annotation;
    }
}
