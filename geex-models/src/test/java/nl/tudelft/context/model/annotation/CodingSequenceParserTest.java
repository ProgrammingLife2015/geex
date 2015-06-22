package nl.tudelft.context.model.annotation;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Jasper on 21-5-2015.
 * @version 1.0
 * @since 21-5-2015
 */
public class CodingSequenceParserTest {


    protected static CodingSequence codingSequence1, codingSequence2;
    protected static CodingSequenceMap codingSequenceMap1;
    protected static File codingSequenceFile;
    protected static CodingSequenceParser codingSequenceParser;


    /**
     * Set up by creating two codingSequences, adding them to an codingSequenceMap, to compare to parsed input..
     */
    @Before
    public void Before() throws Exception {
        codingSequence1 = new CodingSequence("seqId", "source", "type", 0, 1, 0f, '+', '.',
                "attributes=1;A=lotofthem; (1 or 3)");
        codingSequence2 = new CodingSequence("seqId1", "source1", "type1", 2, 3, 1f, '-', '.',
                "attributes=2;B=evenmore; (1 or 4)");

        codingSequenceMap1 = new CodingSequenceMap(Arrays.asList(codingSequence1, codingSequence2));

        codingSequenceParser = new CodingSequenceParser();
        codingSequenceFile = new File(CodingSequenceParserTest.class.getResource("/annotation/test.rcm.gff").getPath());
    }

    /**
     * Test is the exception is thrown.
     *
     * @throws NumberFormatException No numbers = exception!
     */
    @Test(expected = NumberFormatException.class)
    public void testException() throws Exception {
        codingSequenceParser.getCodingSequence("there should be at least one number format exception".split(" "));
    }

    /**
     * The file must be read and result in the two known coding sequences.
     *
     * @throws FileNotFoundException Shouldn't happen though.
     */
    @Test
    public void testExceptionOnParse() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(codingSequenceFile));
        CodingSequenceMap map = codingSequenceParser.parse(bufferedReader);
        assertEquals(codingSequence1, map.annotationsByStart.get(0).get(0));
        assertEquals(codingSequence2, map.annotationsByStart.get(2).get(0));

    }

    /**
     * A canceled parser should not parse.
     *
     * @throws FileNotFoundException Shouldn't happen though.
     */
    @Test
    public void testParserCanceled() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(codingSequenceFile));
        codingSequenceParser.cancel();
        CodingSequenceMap map = codingSequenceParser.parse(bufferedReader);
        assertEquals(0, map.annotationsByStart.size());
    }
}