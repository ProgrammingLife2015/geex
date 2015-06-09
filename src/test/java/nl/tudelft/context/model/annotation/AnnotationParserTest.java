package nl.tudelft.context.model.annotation;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * @author Jasper on 21-5-2015.
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationParserTest {


    protected static Annotation annotation1, annotation2;
    protected static AnnotationMap annotationMap1;
    protected static File annotationFile;


    /**
     * Set up by creating two annotations, adding them to an annotationMap, to compare to parsed input..
     */
    @BeforeClass
    public static void BeforeClass() throws Exception {
        //annotation1 = new Annotation(1, "lorem", true, 0, 2, "ipsum");
        //annotation2 = new Annotation(2, "dolor", false, 4, 6, "set");

        annotationMap1 = new AnnotationMap();

        annotationMap1.put(1, annotation1);
        annotationMap1.put(2, annotation2);
        annotationFile = new File(AnnotationParserTest.class.getResource("/annotation/test.ann.csv").getPath());
    }

    @Test
    public void testGetAnnotationMap() throws Exception {
        AnnotationMap annotationMap2 = new AnnotationParser().setReader(annotationFile).parse();
        assertEquals(annotationMap1.toString(), annotationMap2.toString());

    }
}