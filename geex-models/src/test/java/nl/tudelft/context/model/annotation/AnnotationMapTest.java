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
        annotationMap1.addAnnotation(annotation1);
        annotationMap1.addAnnotation(annotation2);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("[Sequence: seqId" + System.getProperty("line.separator")
                + "Source: source" + System.getProperty("line.separator")
                + "Type: type" + System.getProperty("line.separator")
                + "Start: 0" + System.getProperty("line.separator")
                + "End: 1" + System.getProperty("line.separator")
                + "Score: 0.0" + System.getProperty("line.separator")
                + "Strand (Sense): A" + System.getProperty("line.separator")
                + "Phase: B" + System.getProperty("line.separator")
                + "Attributes:attributes" + System.getProperty("line.separator")
                + "]" + System.getProperty("line.separator")
                + "[Sequence: seqId1" + System.getProperty("line.separator")
                + "Source: source1" + System.getProperty("line.separator")
                + "Type: type1" + System.getProperty("line.separator")
                + "Start: 2" + System.getProperty("line.separator")
                + "End: 3" + System.getProperty("line.separator")
                + "Score: 1.0" + System.getProperty("line.separator")
                + "Strand (Sense): C" + System.getProperty("line.separator")
                + "Phase: D" + System.getProperty("line.separator")
                + "Attributes:attributes2" + System.getProperty("line.separator")
                + "]"
                + System.getProperty("line.separator")
                , annotationMap1.toString());
    }
}