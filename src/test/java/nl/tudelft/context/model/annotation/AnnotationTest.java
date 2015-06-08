package nl.tudelft.context.model.annotation;

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
        annotation1 = new Annotation("Rv0001", true, 0, 1524, "dnaA");
        annotation2 = new Annotation("Rv0001", true, 0, 1524, "dnaA");

    }

    /**
     * Test id.
     *
     * @throws Exception
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals(1, annotation1.getId());
        assertEquals(1, annotation1.getId());

    }

    /**
     * Test name.
     *
     * @throws Exception
     */
    @Test
    public void testGetName() throws Exception {
        assertEquals("Rv0001", annotation1.getName());
        assertEquals("Rv0002", annotation2.getName());

    }

    /**
     * test strand.
     *
     * @throws Exception
     */
    @Test
    public void testGetStrand() throws Exception {
        assertTrue(annotation1.getStrand());
        assertTrue(annotation2.getStrand());
    }

    /**
     * Test start.
     *
     * @throws Exception
     */
    @Test
    public void testGetStart() throws Exception {
        assertEquals(0, annotation1.getStart());
        assertEquals(2051, annotation2.getStart());

    }

    /**
     * Test end.
     *
     * @throws Exception
     */
    @Test
    public void testGetEnd() throws Exception {
        assertEquals(1524, annotation1.getEnd());
        assertEquals(3260, annotation2.getEnd());

    }

    /**
     * Test proteinName.
     *
     * @throws Exception
     */
    @Test
    public void testGetProteinName() throws Exception {
        assertEquals("dnaA", annotation1.getProteinName());
        assertEquals("dnaN", annotation2.getProteinName());

    }

    /**
     * Test equals.
     *
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        assertTrue(annotation1.equals(annotation1));
        assertFalse(annotation1.equals(annotation2));
        assertFalse(annotation2.equals(annotation4));
    }

    /**
     * Test equals extensive.
     *
     * @throws Exception
     */
    @Test
    public void testEqualsExtensive() throws Exception {
        Annotation annotationA = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        Annotation annotationB = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        assertTrue(annotationA.equals(annotationB));

        //Start with only one item of B different.
        annotationA = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        annotationB = new Annotation(2, "Rv0001", true, 0, 1524, "dnaA");
        assertFalse(annotationA.equals(annotationB));

        annotationA = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        annotationB = new Annotation(1, "Rv0002", true, 0, 1524, "dnaA");
        assertFalse(annotationA.equals(annotationB));

        annotationA = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        annotationB = new Annotation(1, "Rv0001", false, 0, 1524, "dnaA");
        assertFalse(annotationA.equals(annotationB));

        annotationA = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        annotationB = new Annotation(1, "Rv0001", true, 1, 1524, "dnaA");
        assertFalse(annotationA.equals(annotationB));

        annotationA = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        annotationB = new Annotation(1, "Rv0001", true, 0, 2, "dnaA");
        assertFalse(annotationA.equals(annotationB));

        annotationA = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        annotationB = new Annotation(1, "Rv0001", true, 0, 1524, "dnaB");
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
     * Test equals with an anti sense annotation.
     *
     * @throws Exception
     */
    @Test
    public void testEqualsWithAntiSenseAnnotation() throws Exception {
        assertFalse(annotation1.equals(annotation3));

    }


    /**
     * Test toString.
     *
     * @throws Exception
     */
    @Test
    public void testToString() throws Exception {
        assertEquals("(1, Rv0001, true, 0, 1524, dnaA)", annotation1.toString());
        assertEquals("(2, Rv0002, true, 2051, 3260, dnaN)", annotation2.toString());
        assertEquals("(8, Rv0008c, false, 11873, 12311, none)", annotation3.toString());

    }

    /**
     * Test hashcode
     *
     * @throws Exception
     */
    @Test
    public void testHashCode() throws Exception {
        assertEquals(1, annotation1.hashCode());
        assertEquals(2, annotation2.hashCode());

    }
}