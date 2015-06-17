package nl.tudelft.context.model.annotation;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class CodingSequenceTest {

    protected static CodingSequence codingSequence1, codingSequence2, codingSequence3, codingSequence4;

    /**
     * Set up by creating four annotations.
     */
    @BeforeClass
    public static void BeforeClass() {
        codingSequence1 = new CodingSequence("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        codingSequence2 = new CodingSequence("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        codingSequence3 = new CodingSequence("seqId1", "source1", "type1", 2, 3, 1f, 'C', 'D', "attributes2");

    }

    /**
     * Test equals.
     *
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        assertTrue(codingSequence1.equals(codingSequence1));
        assertFalse(codingSequence2.equals(codingSequence3));
    }

    /**
     * Test equals extensive.
     *
     * @throws Exception
     */
    @Test
    public void testEqualsExtensive() throws Exception {
        CodingSequence codingSequenceA = new CodingSequence("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        CodingSequence codingSequenceB = new CodingSequence("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        assertTrue(codingSequenceA.equals(codingSequenceB));

        //Start with only one item of B different.
        codingSequenceB = new CodingSequence("Different", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        assertFalse(codingSequenceA.equals(codingSequenceB));

        codingSequenceB = new CodingSequence("seqId", "Different", "type", 0, 1, 0f, 'A', 'B', "attributes");
        assertFalse(codingSequenceA.equals(codingSequenceB));

        codingSequenceB = new CodingSequence("seqId", "source", "Different", 0, 1, 0f, 'A', 'B', "attributes");
        assertFalse(codingSequenceA.equals(codingSequenceB));

        codingSequenceB = new CodingSequence("seqId", "source", "type", 1, 1, 0f, 'A', 'B', "attributes");
        assertFalse(codingSequenceA.equals(codingSequenceB));

        codingSequenceB = new CodingSequence("seqId", "source", "type", 0, 2, 0f, 'A', 'B', "attributes");
        assertFalse(codingSequenceA.equals(codingSequenceB));

        codingSequenceB = new CodingSequence("seqId", "source", "type", 0, 1, 1f, 'A', 'B', "attributes");
        assertFalse(codingSequenceA.equals(codingSequenceB));

        codingSequenceB = new CodingSequence("seqId", "source", "type", 0, 1, 0f, 'Z', 'B', "attributes");
        assertFalse(codingSequenceA.equals(codingSequenceB));

        codingSequenceB = new CodingSequence("seqId", "source", "type", 0, 1, 0f, 'A', 'Z', "attributes");
        assertFalse(codingSequenceA.equals(codingSequenceB));

        codingSequenceB = new CodingSequence("seqId", "source", "type", 0, 1, 0f, 'A', 'Z', "Different");
        assertFalse(codingSequenceA.equals(codingSequenceB));

    }

    /**
     * Test equals with a non-annotation object.
     *
     * @throws Exception
     */
    @Test
    public void testEqualsWithOtherObject() throws Exception {
        assertFalse(codingSequence1.equals("Not an annotation"));
    }


    /**
     * Test equals with a null object.
     *
     * @throws Exception
     */
    @Test
    public void testEqualsWithNull() throws Exception {
        assertFalse(codingSequence1.equals(null));
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
                + "Attributes: attributes" + System.lineSeparator(), codingSequence1.toString());

        assertEquals("Sequence: seqId" + System.lineSeparator()
                + "Source: source" + System.lineSeparator()
                + "Type: type" + System.lineSeparator()
                + "Start: 0" + System.lineSeparator()
                + "End: 1" + System.lineSeparator()
                + "Score: 0.0" + System.lineSeparator()
                + "Strand (Sense): A" + System.lineSeparator()
                + "Phase: B" + System.lineSeparator()
                + "Attributes: attributes" + System.lineSeparator(), codingSequence2.toString());

        assertEquals("Sequence: seqId1" + System.lineSeparator()
                + "Source: source1" + System.lineSeparator()
                + "Type: type1" + System.lineSeparator()
                + "Start: 2" + System.lineSeparator()
                + "End: 3" + System.lineSeparator()
                + "Score: 1.0" + System.lineSeparator()
                + "Strand (Sense): C" + System.lineSeparator()
                + "Phase: D" + System.lineSeparator()
                + "Attributes: attributes2" + System.lineSeparator(), codingSequence3.toString());

    }

    /**
     * Test hashcode
     *
     * @throws Exception
     */
    @Test
    public void testHashCode() throws Exception {
        assertEquals(-813067396, codingSequence1.hashCode());
        assertEquals(-813067396, codingSequence2.hashCode());
        assertEquals(2002990401, codingSequence3.hashCode());

    }
}