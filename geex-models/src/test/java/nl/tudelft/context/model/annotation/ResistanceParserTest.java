package nl.tudelft.context.model.annotation;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jasper on 9-6-2015.
 * @version 1.0
 * @since 9-6-2015
 */
public class ResistanceParserTest {


    protected static Resistance resistance1, resistance2;
    protected static ResistanceMap resistanceMap1;
    protected static File resistanceFile, resistanceFile2;
    protected static ResistanceParser resistanceParser;

    /**
     * Set up by creating two resistances, adding them to an resistanceMap, to compare to parsed input.
     */
    @Before
    public void Before() throws Exception {
        resistance1 = new Resistance("geneName", "TypeOfMutation", "change", "filter", 0, "rifampicin");
        resistance2 = new Resistance("geneName1", "TypeOfMutation1", "change1", "filter1", 1, "ethionomide");

        resistanceMap1 = new ResistanceMap(Arrays.asList(resistance1, resistance2));

        resistanceParser = new ResistanceParser();
        resistanceFile = new File(ResistanceParserTest.class.getResource("/annotation/test.cs.txt").getPath());
        resistanceFile2 = new File(ResistanceParserTest.class.getResource("/annotation/test2.cs.txt").getPath());
    }

    /**
     * Test is the exception is thrown.
     *
     * @throws ResistanceFormatException Jibberish = exception!
     */
    @Test(expected = ResistanceFormatException.class)
    public void testException() throws Exception {
        resistanceParser.getResistance("regex not found");
    }

    /**
     * An exception is thrown under the hood.
     *
     * @throws FileNotFoundException Shouldn't happen though.
     */
    @Test
    public void testSilentException() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(resistanceFile2));
        ResistanceMap map = resistanceParser.parse(bufferedReader);
        assertTrue(map.annotationsByStart.isEmpty());
    }

    /**
     * The file must be read and result in the two known coding sequences.
     *
     * @throws FileNotFoundException Shouldn't happen though.
     */
    @Test
    public void testParse() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(resistanceFile));
        ResistanceMap map = resistanceParser.parse(bufferedReader);
        assertEquals(resistance1, map.annotationsByStart.get(0).get(0));
        assertEquals(resistance2, map.annotationsByStart.get(1).get(0));
    }

    /**
     * A canceled parser should not parse.
     *
     * @throws FileNotFoundException Shouldn't happen though.
     */
    @Test
    public void testParserCanceled() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(resistanceFile));
        resistanceParser.cancelled();
        ResistanceMap map = resistanceParser.parse(bufferedReader);
        assertEquals(0, map.annotationsByStart.size());
    }

    @Test
    public void testGetDrugName() {
        assertEquals(resistanceParser.getDrugName('R'), "rifampicin");
        assertEquals(resistanceParser.getDrugName('T'), "ethionomide");
        assertEquals(resistanceParser.getDrugName('M'), "ethionomide");
        assertEquals(resistanceParser.getDrugName('I'), "isoniazid");
        assertEquals(resistanceParser.getDrugName('O'), "ofloxacin");
        assertEquals(resistanceParser.getDrugName('S'), "streptomycin");
        assertEquals(resistanceParser.getDrugName('K'), "kanamycin");
        assertEquals(resistanceParser.getDrugName('P'), "pyrazinamide");
        assertEquals(resistanceParser.getDrugName('E'), "ethambutol");
        assertEquals(resistanceParser.getDrugName('Q'), "none");
    }

}