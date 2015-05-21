package nl.tudelft.context.annotation;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.Assert.*;

/**
 * @author Jasper on 21-5-2015.
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationParserTest {


    protected static Annotation annotation1, annotation2;
    protected static AnnotationMap annotationMap1;
    protected static AnnotationParser annotationParser;
    protected static File annotationFile;


    /**
     * Set up by creating two annotations, adding them to an annotationMap, to compare to parsed input..
     */
    @BeforeClass
    public static void BeforeClass() {
        annotation1 = new Annotation(1, "lorem", true, 0, 2, "ipsum");
        annotation2 = new Annotation(2, "dolor", false, 4, 6, "set");

        annotationMap1 = new AnnotationMap();

        annotationMap1.put("1",annotation1);
        annotationMap1.put("2",annotation2);

        annotationParser = new AnnotationParser();

        annotationFile = new File(AnnotationParserTest.class.getResource("/annotation/test.ann.csv").getPath());
    }

    @Test
    public void testGetAnnotationMap() throws Exception {
        AnnotationMap annotationMap2 = annotationParser.getAnnotationMap(annotationFile);
        assertEquals(annotationMap1.toString(),annotationMap2.toString());

    }


    @Test (expected = IOException.class)
    public void testParseAnnotationsException() throws Exception {
        BufferedReader mockReader = Mockito.mock(BufferedReader.class);
        Mockito.doThrow(new IOException()).when(mockReader).readLine();
        annotationParser.parseAnnotations(mockReader);
    }
}