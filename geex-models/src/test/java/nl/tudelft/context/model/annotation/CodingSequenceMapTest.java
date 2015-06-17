package nl.tudelft.context.model.annotation;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class CodingSequenceMapTest {

    protected static CodingSequence codingSequence1, codingSequence2;
    protected static CodingSequenceMap codingSequenceMap1;


    /**
     * Set up by creating two annotations.
     */
    @BeforeClass
    public static void BeforeClass() {
        codingSequence1 = new CodingSequence("seqId", "source", "type", 0, 1, 0f, 'A', 'B', "attributes");
        codingSequence2 = new CodingSequence("seqId1", "source1", "type1", 2, 3, 1f, 'C', 'D', "attributes2");
        codingSequenceMap1 = new CodingSequenceMap();
        codingSequenceMap1.addAnnotation(codingSequence1);
        codingSequenceMap1.addAnnotation(codingSequence2);
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
                + "Attributes: attributes" + System.getProperty("line.separator")
                + "]" + System.getProperty("line.separator")
                + "[Sequence: seqId1" + System.getProperty("line.separator")
                + "Source: source1" + System.getProperty("line.separator")
                + "Type: type1" + System.getProperty("line.separator")
                + "Start: 2" + System.getProperty("line.separator")
                + "End: 3" + System.getProperty("line.separator")
                + "Score: 1.0" + System.getProperty("line.separator")
                + "Strand (Sense): C" + System.getProperty("line.separator")
                + "Phase: D" + System.getProperty("line.separator")
                + "Attributes: attributes2" + System.getProperty("line.separator")
                + "]"
                + System.getProperty("line.separator")
                , codingSequenceMap1.toString());
    }
}