package nl.tudelft.context.annotation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;


/**
 * @author Jasper on 21-5-2015.
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationParser {

    public HashMap<String, Annotation> getAnnotationMap(final File annotationFile) throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(annotationFile), "UTF-8"));
        return parseAnnotations(fileReader);

    }

    public HashMap<String, Annotation> parseAnnotations(final BufferedReader bufferedReader) {
        HashMap<String, Annotation> annotationMap = new HashMap<String, Annotation>();
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

    public Annotation getAnnotation(String[] splitLine) throws NumberFormatException {
        int id = Integer.parseInt(splitLine[0]);
        String name = splitLine[1];
        Boolean strand = splitLine[2].equals("-");
        int start = Integer.parseInt(splitLine[3]);
        int end = Integer.parseInt(splitLine[4]);
        String proteinName = splitLine[5];

        return new Annotation(id, name, strand, start, end, proteinName);
    }
}
