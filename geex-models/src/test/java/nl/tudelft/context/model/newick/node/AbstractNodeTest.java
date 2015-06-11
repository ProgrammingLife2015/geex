package nl.tudelft.context.model.newick.node;

import javafx.beans.property.ObjectProperty;
import nl.tudelft.context.model.newick.selection.All;
import nl.tudelft.context.model.newick.selection.None;
import nl.tudelft.context.model.newick.selection.Partial;
import nl.tudelft.context.model.newick.selection.Selection;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 06-05-2015
 */
public class AbstractNodeTest {
    AbstractNode[] nodes;

    @Before
    public void before() {
        nodes = new AbstractNode[]{
                new AncestorNode(1.23),
                new DummyNode(),
                new StrandNode("a", 2.34),
                new DummyNode(),
                new AncestorNode(3.45),
                new DummyNode(),
                new StrandNode("b", 4.56),
                new DummyNode(),
                new StrandNode("c", 5.67)
        };

        connect(
                nodes[0], nodes[1],
                nodes[1], nodes[2],
                nodes[0], nodes[3],
                nodes[3], nodes[4],
                nodes[4], nodes[5],
                nodes[5], nodes[6],
                nodes[4], nodes[7],
                nodes[7], nodes[8]
        );
    }

    /**
     * Test if getParent() throws an exception when the parent isn't set.
     */
    @Test(expected = NoSuchElementException.class)
    public void testParentNull() {
        nodes[0].getParent();
    }

    /**
     * Test if the parent can be set and read.
     */
    @Test
    public void testParent() {
        AbstractNode n = new DummyNode();
        nodes[0].setParent(n);
        assertEquals(n, nodes[0].getParent());
    }

    /**
     * Test if the names are properly set.
     */
    @Test
    public void testName() {
        assertEquals("", nodes[0].getName());
        assertEquals("", nodes[1].getName());
        assertEquals("a", nodes[2].getName());
    }

    /**
     * Test if the weights are properly set.
     */
    @Test
    public void testWeight() {
        assertEquals(1.23, nodes[0].getWeight(), 1e-12);
        assertEquals(0, nodes[1].getWeight(), 1e-12);
        assertEquals(2.34, nodes[2].getWeight(), 1e-12);
    }

    /**
     * Sources should be initially empty.
     */
    @Test
    public void testSources() {
        assertTrue(nodes[0].getSources().isEmpty());
        nodes[2].toggleSelection();
        assertFalse(nodes[0].getSources().isEmpty());
    }

    /**
     * SourcesProperty is the property of sources.
     */
    @Test
    public void testSourcesProperty() {
        assertEquals(nodes[0].getSourcesProperty().get(), nodes[0].getSources());
    }

    /**
     * Toggle should toggle the selection and notify the parent.
     */
    @Test
    public void testToggle() {
        AbstractNode parent = mock(AbstractNode.class);
        nodes[0].setParent(parent);
        nodes[0].toggleSelection();
        assertThat(nodes[0].getSelection(), instanceOf(All.class));
        verify(parent, times(1)).updateSelection();
    }

    /**
     * Test if the node calls setSelection on its children.
     */
    @Test
    public void testSetSelection() {
        AbstractNode n1 = new AncestorNode(1);
        AbstractNode n2 = mock(AbstractNode.class);
        n1.addChild(n2);
        Selection selection = new All();
        n1.setSelection(selection);
        assertEquals(selection, n1.getSelection());
        verify(n2, times(1)).setSelection(selection);
    }

    /**
     * SelectionProperty should contain the selection.
     */
    @Test
    public void testSelectionProperty() {
        assertEquals(nodes[0].getSelection(), nodes[0].getSelectionProperty().get());
    }

    /**
     * The node's selection and sources should be updated. Also the parent must be called.
     */
    @Test
    public void testUpdateSelection() {
        Selection oldSelection = nodes[4].getSelection();
        Set<String> oldSources = nodes[4].getSources();
        nodes[5].setSelection(new All());
        nodes[7].setSelection(new All());
        AbstractNode parent = mock(DummyNode.class);
        nodes[4].setParent(parent);
        nodes[4].updateSelection();
        assertFalse(oldSelection == nodes[4].getSelection());
        assertFalse(oldSources == nodes[4].getSources());
        verify(parent, times(1)).updateSelection();
    }

    /**
     * Translation should work.
     */
    @Test
    public void testTranslation() {
        Double d1 = Math.random();
        Double d2 = Math.random();
        nodes[0].setTranslateX(d1);
        nodes[0].setTranslateY(d2);
        assertEquals(d1.doubleValue(), nodes[0].translateXProperty().doubleValue(), 1e-12);
        assertEquals(d2.doubleValue(), nodes[0].translateYProperty().doubleValue(), 1e-12);
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
