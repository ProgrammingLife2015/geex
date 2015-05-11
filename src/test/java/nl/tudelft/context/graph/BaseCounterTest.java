package nl.tudelft.context.graph;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * @author Jasper on 6-5-2015.
 * @version 1f
 * @since 6-5-2015
 */
public class BaseCounterTest {

    protected static BaseCounter baseCounter1, baseCounter2, baseCounter3;

    @BeforeClass
    public static void setUp() throws Exception {
        baseCounter1 = new BaseCounter("AAAAAAAAAA");
        baseCounter2 = new BaseCounter("ATCGATCGATCG");
        baseCounter3 = new BaseCounter("ATTCGCTCACANNNNNNNNNNNNNATCCCTTTACCCG");
    }

    @Test
    public void testGetIntOnOnlyThisBaseInDna() throws Exception {
        assertEquals(10, baseCounter1.getCount('A'));
    }

    @Test
    public void testGetIntNotOccuringInDna() throws Exception {
        assertEquals(0, baseCounter1.getCount('T'));
    }

    @Test
    public void testGetIntCombinedButNotN() throws Exception {
        assertEquals(3, baseCounter2.getCount('A'));
        assertEquals(3, baseCounter2.getCount('T'));
        assertEquals(3, baseCounter2.getCount('C'));
        assertEquals(3, baseCounter2.getCount('G'));
        assertEquals(0, baseCounter2.getCount('N'));

    }

    @Test
    public void testGetIntAllCombined() throws Exception {
        assertEquals(5, baseCounter3.getCount('A'));
        assertEquals(7, baseCounter3.getCount('T'));
        assertEquals(10, baseCounter3.getCount('C'));
        assertEquals(2, baseCounter3.getCount('G'));
        assertEquals(13, baseCounter3.getCount('N'));
    }

    @Test
    public void testGetPercentageOnOnlyThisBaseInDna() throws Exception {
        assertEquals(100f, baseCounter1.getPercentage('A'), 0.E-4f);
        assertEquals(0f, baseCounter1.getPercentage('T'), 0.E-4f);
        assertEquals(0f, baseCounter1.getPercentage('C'), 0.E-4f);
        assertEquals(0f, baseCounter1.getPercentage('G'), 0.E-4f);
        assertEquals(0f, baseCounter1.getPercentage('N'), 0.E-4f);
    }

    @Test
    public void testGetPercentageNotOccuringInDna() throws Exception {
        assertEquals(0f, baseCounter1.getPercentage('T'), 0.E-4f);
    }

    @Test
    public void testGetPercentageCombinedButNotN() throws Exception {
        assertEquals(25f, baseCounter2.getPercentage('A'), 0.E-4f);
        assertEquals(25f, baseCounter2.getPercentage('T'), 0.E-4f);
        assertEquals(25f, baseCounter2.getPercentage('C'), 0.E-4f);
        assertEquals(25f, baseCounter2.getPercentage('G'), 0.E-4f);
        assertEquals(0f, baseCounter2.getPercentage('N'), 0.E-4f);

    }

    @Test
    public void testGetPercentageAllCombined() throws Exception {
        assertEquals(13.513514f, baseCounter3.getPercentage('A'), 0.E-4f);
        assertEquals(18.918919f, baseCounter3.getPercentage('T'), 0.E-4f);
        assertEquals(27.027027f, baseCounter3.getPercentage('C'), 0.E-4f);
        assertEquals(5.4054055f, baseCounter3.getPercentage('G'), 0.E-4f);
        assertEquals(35.135136f, baseCounter3.getPercentage('N'), 0.E-4f);
    }

    @Test
    public void testGetPercentageStringRounding() throws Exception {
        assertEquals("13.51", baseCounter3.getPercentageString('A'));
        assertEquals("18.92", baseCounter3.getPercentageString('T'));
        assertEquals("27.03", baseCounter3.getPercentageString('C'));
        assertEquals("5.41", baseCounter3.getPercentageString('G'));
        assertEquals("35.14", baseCounter3.getPercentageString('N'));
    }

    /**
     * Test getPercentages
     */
    @Test
    public void testGetPercentages() {
        NodeFactory nodeFactory = new NodeFactory();

        nl.tudelft.context.graph.Node node1 = nodeFactory.getNode(new Scanner(">0 | Cat,Dog | 5 | 7\nA\n"));
        nl.tudelft.context.graph.Node node2 = nodeFactory.getNode(new Scanner(">1 | Dog | 8 | 10\nATC\n"));

        assertEquals("A: 100.0%, T: 0.0%, C: 0.0%, G: 0.0%, N: 0.0%", node1.getBaseCounter().toString());
        assertEquals("A: 33.33%, T: 33.33%, C: 33.33%, G: 0.0%, N: 0.0%", node2.getBaseCounter().toString());

    }

}