package nl.tudelft.context.model.annotation;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        annotation1 = new Annotation("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        annotation2 = new Annotation("seqId1", "source1", "type1", 2, 3, 1f, 'C', 'D', "attributes2");
        annotationMap1 = new AnnotationMap();
        annotationMap1.put(1, annotation1);
        annotationMap1.put(2, annotation2);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Annotation(seqId:'seqId, source:'source, type:'type, start:0, end:1, score:0.0, strand:A, phase:B, attributes:attributes)"
                + System.getProperty("line.separator")
                + "Annotation(seqId:'seqId1, source:'source1, type:'type1, start:2, end:3, score:1.0, strand:C, phase:D, attributes:attributes2)"
                + System.getProperty("line.separator")
                , annotationMap1.toString());
    }
}