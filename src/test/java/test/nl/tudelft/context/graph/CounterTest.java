package test.nl.tudelft.context.graph;

import junit.framework.TestCase;
import nl.tudelft.context.graph.Counter;
import org.junit.Test;

/**
 * @author Jasper on 6-5-2015.
 * @version 1f
 * @since 6-5-2015
 */
public class CounterTest extends TestCase {

    protected Counter Counter1, Counter2, Counter3, Counter4, Counter5;

    public void setUp() throws Exception {
        super.setUp();
        Counter1 = new Counter("AAAAAAAAAA");
        Counter2 = new Counter("ATCGATCGATCG");
        Counter3 = new Counter("ATTCGCTCACANNNNNNNNNNNNNATCCCTTTACCCG");
        Counter4 = new Counter("");
        Counter5 = new Counter("ATCGNZ");
    }

    @Test
    public void testGetIntEmptyString() throws Exception {
        assertEquals(0, Counter4.getInt('A'));
        assertEquals(0,Counter4.getInt('T'));
        assertEquals(0,Counter4.getInt('C'));
        assertEquals(0,Counter4.getInt('G'));
        assertEquals(0,Counter4.getInt('N'));

    }

    @Test
    public void testGetIntOnOnlyThisBaseInDna() throws Exception {
        assertEquals(10,Counter1.getInt('A'));
    }

    @Test
    public void testGetIntNotOccuringInDna() throws Exception {
        assertEquals(0, Counter1.getInt('T'));
    }

    @Test
    public void testGetIntCombinedButNotN() throws Exception {
        assertEquals(3,Counter2.getInt('A'));
        assertEquals(3,Counter2.getInt('T'));
        assertEquals(3,Counter2.getInt('C'));
        assertEquals(3,Counter2.getInt('G'));
        assertEquals(0,Counter2.getInt('N'));

    }

    @Test
    public void testGetIntAllCombined() throws Exception {
        assertEquals(5, Counter3.getInt('A'));
        assertEquals(7,Counter3.getInt('T'));
        assertEquals(10,Counter3.getInt('C'));
        assertEquals(2,Counter3.getInt('G'));
        assertEquals(13,Counter3.getInt('N'));
    }

    @Test
    public void testGetIntInvalidCharInDNA() throws Exception {
        assertEquals(1, Counter5.getInt('A'));
        assertEquals(1,Counter5.getInt('T'));
        assertEquals(1,Counter5.getInt('C'));
        assertEquals(1,Counter5.getInt('G'));
        assertEquals(1,Counter5.getInt('N'));
        assertEquals(0,Counter5.getInt('Z'));
    }


    @Test
    public void testGetIntNotExistingChar() throws Exception {
        assertEquals(0,Counter3.getInt('H'));
    }

    @Test
    public void testgetPercentageEmptyString() throws Exception {
        assertEquals(0f,Counter4.getPercentage('A'));
        assertEquals(0f,Counter4.getPercentage('T'));
        assertEquals(0f,Counter4.getPercentage('C'));
        assertEquals(0f,Counter4.getPercentage('G'));
        assertEquals(0f,Counter4.getPercentage('N'));

    }

    @Test
    public void testGetPercentageOnOnlyThisBaseInDna() throws Exception {
        assertEquals(100f,Counter1.getPercentage('A'));
        assertEquals(0f,Counter1.getPercentage('T'));
        assertEquals(0f,Counter1.getPercentage('C'));
        assertEquals(0f,Counter1.getPercentage('G'));
        assertEquals(0f,Counter1.getPercentage('N'));
    }

    @Test
    public void testGetPercentageNotOccuringInDna() throws Exception {
        assertEquals(0f, Counter1.getPercentage('T'));
    }

    @Test
    public void testGetPercentageCombinedButNotN() throws Exception {
        assertEquals(25f,Counter2.getPercentage('A'));
        assertEquals(25f,Counter2.getPercentage('T'));
        assertEquals(25f,Counter2.getPercentage('C'));
        assertEquals(25f,Counter2.getPercentage('G'));
        assertEquals(0f,Counter2.getPercentage('N'));

    }

    @Test
    public void testGetPercentageAllCombined() throws Exception {
        assertEquals(13.513514f,Counter3.getPercentage('A'));
        assertEquals(18.918919f,Counter3.getPercentage('T'));
        assertEquals(27.027027f,Counter3.getPercentage('C'));
        assertEquals(5.4054055f,Counter3.getPercentage('G'));
        assertEquals(35.135136f,Counter3.getPercentage('N'));
    }


    @Test
    public void testGetPercentageInNotExistingChar() throws Exception {
        assertEquals(0f,Counter3.getPercentage('H'));
    }

    @Test
    public void testGetPercentageInvalidCharInDNA() throws Exception {
        assertEquals(20f,Counter5.getPercentage('A'));
        assertEquals(20f,Counter5.getPercentage('T'));
        assertEquals(20f,Counter5.getPercentage('C'));
        assertEquals(20f,Counter5.getPercentage('G'));
        assertEquals(20f,Counter5.getPercentage('N'));
        assertEquals(0f,Counter5.getPercentage('Z'));
    }

    @Test
    public void testGetPercStringRoundNumber() throws Exception {
        assertEquals("20.00",Counter5.getPercString('A'));
        assertEquals("20.00",Counter5.getPercString('T'));
        assertEquals("20.00",Counter5.getPercString('C'));
        assertEquals("20.00",Counter5.getPercString('G'));
        assertEquals("20.00",Counter5.getPercString('N'));
        assertEquals("0.00",Counter5.getPercString('Z'));
    }

    @Test
    public void testGetPercStringRounding() throws Exception {
        assertEquals("13.51",Counter3.getPercString('A'));
        assertEquals("18.92",Counter3.getPercString('T'));
        assertEquals("27.03",Counter3.getPercString('C'));
        assertEquals("5.41",Counter3.getPercString('G'));
        assertEquals("35.14",Counter3.getPercString('N'));
        assertEquals("0.00",Counter3.getPercString('Z'));
    }

}