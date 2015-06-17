package nl.tudelft.context.model.annotation;

import nl.tudelft.context.model.annotation.coding_sequence.Annotation;
import nl.tudelft.context.model.annotation.coding_sequence.AnnotationMap;
import nl.tudelft.context.model.annotation.coding_sequence.AnnotationParser;
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
        annotation1 = new Annotation("seqId", "source", "type", 0, 1, 0f, '+', '.', "attributes=1;A=lotofthem; (1 or 3)");
        annotation2 = new Annotation("seqId1", "source1", "type1", 2, 3, 1f, '-', '.', "attributes=2;B=evenmore; (1 or 4)");

        annotationMap1 = new AnnotationMap();
        annotationMap1.addAnnotation(annotation1);
        annotationMap1.addAnnotation(annotation2);

        annotationFile = new File(AnnotationParserTest.class.getResource("/annotation/test.gff").getPath());
    }

    @Test
    public void testGetAnnotationMap() throws Exception {
        AnnotationMap annotationMap2 = new AnnotationParser().setFiles(annotationFile).load();
        assertEquals(annotationMap1.toString(), annotationMap2.toString());

    }
}