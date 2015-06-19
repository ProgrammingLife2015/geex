package nl.tudelft.context.model.resistance;

import nl.tudelft.context.model.annotation.Resistance;
import nl.tudelft.context.model.annotation.ResistanceMap;
import nl.tudelft.context.model.annotation.ResistanceFormatException;
import nl.tudelft.context.model.annotation.ResistanceParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

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
    @BeforeClass
    public static void BeforeClass() throws Exception {
        resistance1 = new Resistance("geneName", "TypeOfMutation", "change", "filter", 0, "rifampicin");
        resistance2 = new Resistance("geneName1", "TypeOfMutation1", "change1", "filter1", 1, "ethionomide");


        resistanceMap1 = new ResistanceMap(Arrays.asList(resistance1, resistance2));

        resistanceParser = new ResistanceParser();
        resistanceFile = new File(ResistanceParserTest.class.getResource("/resistance/test.txt").getPath());
        resistanceFile2 = new File(ResistanceParserTest.class.getResource("/resistance/test2.txt").getPath());
    }

    @Test
    public void testGetResistanceMap() throws Exception {
//        ResistanceMap resistanceMap2 = new ResistanceParser().setFiles(resistanceFile).load();
//        assertEquals(resistanceMap1.toString(), resistanceMap2.toString());

        // ???
    }

    @Test(expected = ResistanceFormatException.class)
    public void testException() throws Exception {
        resistanceParser.getResistance("regex not found");
    }

    @Test
    public void testExceptionOnParse() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(resistanceFile2));
        resistanceParser.parse(bufferedReader);
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