package nl.tudelft.context.model.newick.node;

import nl.tudelft.context.model.newick.selection.All;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 10-06-2015
 */
public class StrandNodeTest {
    StrandNode parent = new StrandNode("parent", 1.23);
    StrandNode child1 = new StrandNode("child1", 2.34);
    StrandNode child2 = new StrandNode("child2", 3.45);

    /**
     * Set up basic structure.
     */
    @Before
    public void before() {
        connect(
                parent, child1,
                parent, child2
        );
    }

    /**
     * Test the name.
     */
    @Test
    public void testName() {
        assertEquals("parent", parent.getName());
        assertEquals("child1", child1.getName());
        assertEquals("child2", child2.getName());
    }


    /**
     * Test the weight.
     */
    @Test
    public void testWeight() {
        assertEquals(1.23, parent.getWeight(), 1e-12);
        assertEquals(2.34, child1.getWeight(), 1e-12);
        assertEquals(3.45, child2.getWeight(), 1e-12);
    }

    /**
     * Parent should contain no children.
     */
    @Test
    public void testChildren() {
        assertTrue(parent.getChildren().isEmpty());
        assertTrue(child1.getChildren().isEmpty());
        assertTrue(child2.getChildren().isEmpty());
    }

    /**
     * None selected; no sources.
     */
    @Test
    public void testUpdateSources() {
        parent.updateSources();
        assertTrue(parent.getSources().isEmpty());
    }

    /**
     * One source should be returned.
     */
    @Test
    public void testUpdateSourcesNotEmpty() {
        child1.setSelection(new All());
        child1.updateSources();
        assertEquals(1, child1.getSources().size());
        assertTrue(child1.getSources().contains("child1"));
    }

    /**
     * A new node should be returned with the same name and weight.
     */
    @Test
    public void testCopy() {
        AbstractNode node = parent.getCopy();
        assertEquals(node.getName(), parent.getName());
        assertEquals(node.getWeight(), parent.getWeight(), 1e-12);
        assertFalse(node == parent);
    }

    /**
     * Should return all selected nodes.
     */
    @Test
    public void testGetSelected() {
        parent.setSelection(new All());
        AbstractNode root = parent.getSelectedNodes();
        assertEquals(parent.getName(), root.getName());
        assertEquals(parent.getWeight(), root.getWeight(), 1e-12);
    }

    /**
     * The right class name should be returned.
     */
    @Test
    public void testClassName() {
        assertEquals("newick-strand", parent.getClassName());
    }

    /**
     * Translate x, on top of the parent's location.
     */
    @Test
    public void testTranslation() {
        int minWeight = 3;
        double weightScale = 11;
        int yPos = 7;
        child1.translate(minWeight, weightScale, yPos);
        assertEquals(
                minWeight + child1.getWeight() * weightScale,
                child1.translateXProperty().doubleValue(), 1e-12);
        assertEquals(yPos, child1.translateYProperty().intValue());
        parent.setTranslateX(3);
        child1.translate(minWeight, weightScale, yPos);
        assertEquals(
                minWeight + child1.getWeight() * weightScale + 3,
                child1.translateXProperty().doubleValue(), 1e-12);
    }

    /**
     * Test toString().
     */
    @Test
    public void testToString() {
        assertEquals("StrandNode<parent,1.23>", parent.toString());
    }

    /**
     * Connects every two nodes.
     *
     * @param nodes The nodes to connect
     */
    private void connect(AbstractNode... nodes) {
        for (int i = 0; i < nodes.length; i += 2) {
            connect(nodes[i], nodes[i + 1]);
        }
    }

    /**
     * Connects the parent to the child and visa versa.
     *
     * @param parent The parent node
     * @param child  The child node
     */
    private void connect(AbstractNode parent, AbstractNode child) {
        parent.addChild(child);
        child.setParent(parent);
    }
}