package nl.tudelft.context.annotation;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationMapTest {

    protected static Annotation annotation1, annotation2;
    protected static AnnotationMap annotationMap1;


    /**
     * Set up by creating two annotations.
     */
    @BeforeClass
    public static void BeforeClass() {
        annotation1 = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        annotation2 = new Annotation(2, "Rv0002", true, 2051, 3260, "dnaN");
        annotationMap1 = new AnnotationMap();
        annotationMap1.put("1",annotation1);
       // annotationMap1.put("2",annotation2);
    }

    @Test
    public void testToString() throws Exception {
       // assertEquals("(1, Rv0001, true, 0, 1524, dnaA)\r\n" +
       //         "(2, Rv0002, true, 2051, 3260, dnaN)\r\n", annotationMap1.toString());
        assertEquals("(1, Rv0001, true, 0, 1524, dnaA)"+System.getProperty("line.separator"), annotationMap1.toString());
    }
}