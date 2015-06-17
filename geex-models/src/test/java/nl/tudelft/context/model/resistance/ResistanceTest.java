package nl.tudelft.context.model.resistance;

import nl.tudelft.context.model.annotation.resistance.Resistance;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jasper on 26-5-2015.
 * @version 1.0
 * @since 26-5-2015
 */
public class ResistanceTest {

    protected static Resistance resistance1, resistance2, resistance3;

    /**
     * Set up by creating two annotations.
     */
    @BeforeClass
    public static void BeforeClass() {
        resistance1 = new Resistance("lorem", "ipsum", "Q264P", "dolor", 5, "set");
        resistance2 = new Resistance("set", "amet", "-11", "consecteur", 6, "adipiscing");
        resistance3 = new Resistance("lorem", "ipsum", "Q264P", "dolor", 5, "set");
    }

    @Test
    public void testGetGeneName() throws Exception {
        assertEquals("lorem", resistance1.getGeneName());
        assertEquals("set", resistance2.getGeneName());
    }

    @Test
    public void testGetTypeOfMutation() throws Exception {
        assertEquals("ipsum", resistance1.getTypeOfMutation());
        assertEquals("amet", resistance2.getTypeOfMutation());

    }

    @Test
    public void testGetChange() throws Exception {
        assertEquals("Q264P", resistance1.getChange());
        assertEquals("-11", resistance2.getChange());
    }

    @Test
    public void testGetGenomePosition() throws Exception {
        assertEquals(5, resistance1.getGenomePosition());
        assertEquals(6, resistance2.getGenomePosition());
    }

    @Test
    public void testGetDrugName() throws Exception {
        assertEquals("set", resistance1.getDrugName());
        assertEquals("adipiscing", resistance2.getDrugName());
    }

    @Test
    public void testGetFilter() throws Exception {
        assertEquals("dolor", resistance1.getFilter());
        assertEquals("consecteur", resistance2.getFilter());
    }

    @Test
    public void testEquals() throws Exception {
        assertFalse(resistance1.equals(resistance2));
        assertTrue(resistance1.equals(resistance3));
        assertFalse(resistance1.equals("not a resistance"));
    }


    @Test
    public void testEqualsExtensive() throws Exception {
        assertTrue(resistance1.equals(resistance3));

        resistance3 = new Resistance("Different", "ipsum", "Q264P", "dolor", 5, "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "Different", "Q264P", "dolor", 5, "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Different", "dolor", 5, "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Q264P", "Different", 5, "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Q264P", "dolor", 9, "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Q264P", "dolor", 5, "Different");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Q264P", "dolor", 5, "set");
    }


    @Test
    public void testEqualsNotAResistance() throws Exception {
        assertFalse(resistance1.equals("not a resistance"));
        Resistance resistance4 = new Resistance("set", "amet", "-11", "consecteur", 6, "adipiscing");
        assertFalse(resistance1.equals(resistance4));
    }

    @Test
    public void testEqualsFilter() throws Exception {
        assertTrue(resistance1.equals(resistance1));
        assertFalse(resistance1.equals(resistance2));
        assertTrue(resistance1.equals(resistance3));
    }


    @Test
    public void testToString() throws Exception {
        assertEquals("(lorem, ipsum, Q264P, dolor, 5, set)", resistance1.toString());
        assertEquals("(set, amet, -11, consecteur, 6, adipiscing)", resistance2.toString());

    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(107375902, resistance1.hashCode());
        assertEquals(1346001911, resistance2.hashCode());

    }
}