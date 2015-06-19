package nl.tudelft.context.model.annotation;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author Jasper on 21-5-2015.
 * @version 1.0
 * @since 21-5-2015
 */
public class CodingSequenceParserTest {


    protected static CodingSequence codingSequence1, codingSequence2;
    protected static CodingSequenceMap codingSequenceMap1;
    protected static File codingSequenceFile;


    /**
     * Set up by creating two codingSequences, adding them to an codingSequenceMap, to compare to parsed input..
     */
    @BeforeClass
    public static void BeforeClass() throws Exception {
        codingSequence1 = new CodingSequence("seqId", "source", "type", 0, 1, 0f, '+', '.', "attributes=1;A=lotofthem; (1 or 3)");
        codingSequence2 = new CodingSequence("seqId1", "source1", "type1", 2, 3, 1f, '-', '.', "attributes=2;B=evenmore; (1 or 4)");

        codingSequenceMap1 = new CodingSequenceMap(Arrays.asList(codingSequence1, codingSequence2));

        codingSequenceFile = new File(CodingSequenceParserTest.class.getResource("/annotation/test.gff").getPath());
    }

    @Test
    public void testGetCodingSequenceMap() throws Exception {
//        CodingSequenceMap codingSequenceMap2 = new CodingSequenceParser().setFiles(codingSequenceFile).load();
//        assertEquals(codingSequenceMap1.toString(), codingSequenceMap2.toString());

        // ???
    }
}