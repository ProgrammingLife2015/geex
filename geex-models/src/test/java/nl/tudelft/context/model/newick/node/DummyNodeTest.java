package nl.tudelft.context.model.newick.node;

import nl.tudelft.context.model.newick.selection.All;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 10-06-2015
 */
public class DummyNodeTest {
    DummyNode parent = new DummyNode();
    DummyNode child1 = new DummyNode();
    DummyNode child2 = new DummyNode();

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
        assertEquals("", parent.getName());
    }


    /**
     * Test the weight.
     */
    @Test
    public void testWeight() {
        assertEquals(0, parent.getWeight(), 1e-12);
    }


    /**
     * Parent should contain last child.
     */
    @Test
    public void testChildren() {
        assertEquals(child2, parent.getChildren().get(0));
        assertEquals(1, parent.getChildren().size());
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
        AbstractNode node = mock(AbstractNode.class);
        Set<String> source = new HashSet<>();
        source.add("cheese");
        when(node.getSources()).thenReturn(source);

        child1.addChild(node);
        child1.updateSources();
        assertEquals(1, child1.getSources().size());
        assertTrue(child1.getSources().contains("cheese"));
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
        assertEquals(1, root.getChildren().size());
    }

    /**
     * The right class name should be returned.
     */
    @Test
    public void testClassName() {
        assertEquals("newick-dummy", parent.getClassName());
    }

    /**
     * Don't translate x, keep the parent's location.
     */
    @Test
    public void testTranslation() {
        int minWeight = 3;
        double weightScale = 11;
        int yPos = 7;
        child2.translate(minWeight, weightScale, yPos);
        assertEquals(0, child2.translateXProperty().doubleValue(), 1e-12);
        assertEquals(yPos, child2.translateYProperty().intValue());
        parent.setTranslateX(3);
        child2.translate(minWeight, weightScale, yPos);
        assertEquals(3, child2.translateXProperty().doubleValue(), 1e-12);
    }

    /**
     * Test toString().
     */
    @Test
    public void testToString() {
        assertEquals("DummyNode", parent.toString());
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