package nl.tudelft.context.model.annotation;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

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
        codingSequenceMap1 = new CodingSequenceMap(Arrays.asList(codingSequence1, codingSequence2));
    }

    @Test
    public void test() throws Exception {
        // ???
    }
}