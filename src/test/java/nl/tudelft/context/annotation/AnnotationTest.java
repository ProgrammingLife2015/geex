package nl.tudelft.context.annotation;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationTest {

    protected static Annotation annotation1, annotation2;

    /**
     * Set up by creating two annotations
     */
    @BeforeClass
    public static void BeforeClass() {
        annotation1 = new Annotation(1, "Rv0001", true, 0, 1524, "dnaA");
        annotation2 = new Annotation(2, "Rv0002", true, 2051, 3260, "dnaN");

    }

    /**
     * Test id
     *
     * @throws Exception
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals(1, annotation1.getId());
        assertEquals(1, annotation1.getId());

    }

    /**
     * Test name
     *
     * @throws Exception
     */
    @Test
    public void testGetName() throws Exception {
        assertEquals("Rv0001", annotation1.getName());
        assertEquals("Rv0002", annotation2.getName());

    }

    /**
     * test strand
     *
     * @throws Exception
     */
    @Test
    public void testGetStrand() throws Exception {
        assertTrue(annotation1.getStrand());
        assertTrue(annotation2.getStrand());
    }

    /**
     * Test start
     *
     * @throws Exception
     */
    @Test
    public void testGetStart() throws Exception {
        assertEquals(0, annotation1.getStart());
        assertEquals(2051, annotation2.getStart());

    }

    /**
     * Test end
     *
     * @throws Exception
     */
    @Test
    public void testGetEnd() throws Exception {
        assertEquals(1524, annotation1.getEnd());
        assertEquals(3260, annotation2.getEnd());

    }

    /**
     * Test proteinName
     *
     * @throws Exception
     */
    @Test
    public void testGetProteinName() throws Exception {
        assertEquals("dnaA", annotation1.getProteinName());
        assertEquals("dnaN", annotation2.getProteinName());

    }

    /**
     * Test equals
     *
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        assertTrue(annotation1.equals(annotation1));
        assertFalse(annotation1.equals(annotation2));
    }

    /**
     * Test equalsLoose
     *
     * @throws Exception
     */
    @Test
    public void testEqualsLoose() throws Exception {
        assertTrue(annotation1.equalsLoose(annotation1));
        assertFalse(annotation1.equalsLoose(annotation2));

    }

    /**
     * Test hashcode
     *
     * @throws Exception
     */
    @Test
    public void testHashCode() throws Exception {
        assertEquals(1, annotation1.getId());
        assertEquals(2, annotation2.getId());

    }
}