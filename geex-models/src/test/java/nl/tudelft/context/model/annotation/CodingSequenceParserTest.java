package nl.tudelft.context.model.annotation;

import nl.tudelft.context.model.annotation.coding_sequence.CodingSequence;
import nl.tudelft.context.model.annotation.coding_sequence.CodingSequenceMap;
import nl.tudelft.context.model.annotation.coding_sequence.CodingSequenceParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * @author Jasper on 21-5-2015.
 * @version 1.0
 * @since 21-5-2015
 */
public class CodingSequenceParserTest {


    protected static CodingSequence codingSequence1, codingSequence2;
    protected static CodingSequenceMap codingSequenceMap1;
    protected static File annotationFile;


    /**
     * Set up by creating two annotations, adding them to an annotationMap, to compare to parsed input..
     */
    @BeforeClass
    public static void BeforeClass() throws Exception {
        codingSequence1 = new CodingSequence("seqId", "source", "type", 0, 1, 0f, '+', '.', "attributes=1;A=lotofthem; (1 or 3)");
        codingSequence2 = new CodingSequence("seqId1", "source1", "type1", 2, 3, 1f, '-', '.', "attributes=2;B=evenmore; (1 or 4)");

        codingSequenceMap1 = new CodingSequenceMap();
        codingSequenceMap1.addAnnotation(codingSequence1);
        codingSequenceMap1.addAnnotation(codingSequence2);

        annotationFile = new File(CodingSequenceParserTest.class.getResource("/annotation/test.gff").getPath());
    }

    @Test
    public void testGetAnnotationMap() throws Exception {
        CodingSequenceMap codingSequenceMap2 = new CodingSequenceParser().setFiles(annotationFile).load();
        assertEquals(codingSequenceMap1.toString(), codingSequenceMap2.toString());

    }
}