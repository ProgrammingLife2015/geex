package nl.tudelft.context.annotation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


/**
 * @author Jasper on 21-5-2015.
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationParser {

    /**
     * Create an AnnotationMap from a file.
     *
     * @param annotationFile the file with the annotations
     * @return the annotationMap
     * @throws FileNotFoundException        when the file does not exist.
     * @throws UnsupportedEncodingException when the encoding is incorrect.
     */
    public final AnnotationMap getAnnotationMap(final File annotationFile)
            throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader fileReader =
                new BufferedReader(new InputStreamReader(new FileInputStream(annotationFile), "UTF-8"));
        return parseAnnotations(fileReader);

    }

    /**
     * Parse the file and generate the annotations.
     *
     * @param bufferedReader the reader for the file
     * @return an annotationMap with the annotations
     */
    public final AnnotationMap parseAnnotations(final BufferedReader bufferedReader) {
        AnnotationMap annotationMap = new AnnotationMap();
        String line = "";
        String cvsSplitBy = ",";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(cvsSplitBy);
                annotationMap.put(splitLine[1], getAnnotation(splitLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        Boolean strand = splitLine[2].equals("-");
        int start = Integer.parseInt(splitLine[3]);
        int end = Integer.parseInt(splitLine[4]);
        String proteinName = splitLine[5];
        Annotation annotation = new Annotation(id, name, strand, start, end, proteinName);
        System.out.println("annotation = " + annotation.toString());
        return annotation;
    }
}
