package nl.tudelft.context.model.resistance;

import nl.tudelft.context.model.resistance.Resistance;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
        resistance1 = new Resistance("lorem", "ipsum", "Q264P", 5, "dolor", "set");
        resistance2 = new Resistance("set", "amet", "-11", 6, "consecteur", "adipiscing");
        resistance3 = new Resistance("lorem", "ipsum", "Q264P", 5, "dolor", "set");
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

        resistance3 = new Resistance("Different", "ipsum", "Q264P", 5, "dolor", "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "Different", "Q264P", 5, "dolor", "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Different", 5, "dolor", "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Q264P", 9, "Different", "set");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Q264P", 5, "dolor", "Different");
        assertFalse(resistance1.equals(resistance3));

        resistance3 = new Resistance("lorem", "ipsum", "Q264P", 5, "dolor", "set");
    }


    @Test
    public void testEqualsNotA() throws Exception {
        assertFalse(resistance1.equals("not a resistance"));
    }


    @Test
    public void testToString() throws Exception {
        assertEquals("(lorem, ipsum, Q264P, 5, dolor, set)", resistance1.toString());
        assertEquals("(set, amet, -11, 6, consecteur, adipiscing)", resistance2.toString());

    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(107375902, resistance1.hashCode());
        assertEquals(1346001911, resistance2.hashCode());

    }
}