package nl.tudelft.context.model.annotation;

import nl.tudelft.context.model.annotation.coding_sequence.Annotation;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationTest {

    protected static Annotation annotation1, annotation2, annotation3, annotation4;

    /**
     * Set up by creating four annotations.
     */
    @BeforeClass
    public static void BeforeClass() {
        annotation1 = new Annotation("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        annotation2 = new Annotation("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        annotation3 = new Annotation("seqId1", "source1", "type1", 2, 3, 1f, 'C', 'D', "attributes2");

    }

    /**
     * Test equals.
     *
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        assertTrue(annotation1.equals(annotation1));
        assertFalse(annotation2.equals(annotation3));
    }

    /**
     * Test equals extensive.
     *
     * @throws Exception
     */
    @Test
    public void testEqualsExtensive() throws Exception {
        Annotation annotationA = new Annotation("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        Annotation annotationB = new Annotation("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        assertTrue(annotationA.equals(annotationB));

        //Start with only one item of B different.
        annotationB = new Annotation("Different", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        assertFalse(annotationA.equals(annotationB));

        annotationB = new Annotation("seqId", "Different", "type", 0, 1, 0f, 'A', 'B', "attributes");
        assertFalse(annotationA.equals(annotationB));

        annotationB = new Annotation("seqId", "source", "Different", 0, 1, 0f, 'A', 'B', "attributes");
        assertFalse(annotationA.equals(annotationB));

        annotationB = new Annotation("seqId", "source", "type", 1, 1, 0f, 'A', 'B', "attributes");
        assertFalse(annotationA.equals(annotationB));

        annotationB = new Annotation("seqId", "source", "type", 0, 2, 0f, 'A', 'B', "attributes");
        assertFalse(annotationA.equals(annotationB));

        annotationB = new Annotation("seqId", "source", "type", 0, 1, 1f, 'A', 'B', "attributes");
        assertFalse(annotationA.equals(annotationB));

        annotationB = new Annotation("seqId", "source", "type", 0, 1, 0f, 'Z', 'B', "attributes");
        assertFalse(annotationA.equals(annotationB));

        annotationB = new Annotation("seqId", "source", "type", 0, 1, 0f, 'A', 'Z', "attributes");
        assertFalse(annotationA.equals(annotationB));

        annotationB = new Annotation("seqId", "source", "type", 0, 1, 0f, 'A', 'Z', "Different");
        assertFalse(annotationA.equals(annotationB));

    }

    /**
     * Test equals with a non-annotation object.
     *
     * @throws Exception
     */
    @Test
    public void testEqualsWithOtherObject() throws Exception {
        assertFalse(annotation1.equals("Not an annotation"));
    }


    /**
     * Test equals with a null object.
     *
     * @throws Exception
     */
    @Test
    public void testEqualsWithNull() throws Exception {
        assertFalse(annotation1.equals(null));
    }


    /**
     * Test toString.
     *
     * @throws Exception
     */
    @Test
    public void testToString() throws Exception {


        assertEquals("Sequence: seqId" + System.lineSeparator()
                + "Source: source" + System.lineSeparator()
                + "Type: type" + System.lineSeparator()
                + "Start: 0" + System.lineSeparator()
                + "End: 1" + System.lineSeparator()
                + "Score: 0.0" + System.lineSeparator()
                + "Strand (Sense): A" + System.lineSeparator()
                + "Phase: B" + System.lineSeparator()
                + "Attributes: attributes" + System.lineSeparator(), annotation1.toString());

        assertEquals("Sequence: seqId" + System.lineSeparator()
                + "Source: source" + System.lineSeparator()
                + "Type: type" + System.lineSeparator()
                + "Start: 0" + System.lineSeparator()
                + "End: 1" + System.lineSeparator()
                + "Score: 0.0" + System.lineSeparator()
                + "Strand (Sense): A" + System.lineSeparator()
                + "Phase: B" + System.lineSeparator()
                + "Attributes: attributes" + System.lineSeparator(), annotation2.toString());

        assertEquals("Sequence: seqId1" + System.lineSeparator()
                + "Source: source1" + System.lineSeparator()
                + "Type: type1" + System.lineSeparator()
                + "Start: 2" + System.lineSeparator()
                + "End: 3" + System.lineSeparator()
                + "Score: 1.0" + System.lineSeparator()
                + "Strand (Sense): C" + System.lineSeparator()
                + "Phase: D" + System.lineSeparator()
                + "Attributes: attributes2" + System.lineSeparator(), annotation3.toString());

    }

    /**
     * Test hashcode
     *
     * @throws Exception
     */
    @Test
    public void testHashCode() throws Exception {
        assertEquals(-813067396, annotation1.hashCode());
        assertEquals(-813067396, annotation2.hashCode());
        assertEquals(2002990401, annotation3.hashCode());

    }
}