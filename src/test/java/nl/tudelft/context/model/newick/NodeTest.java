package nl.tudelft.context.model.newick;

import nl.tudelft.context.model.newick.selection.All;
import nl.tudelft.context.model.newick.selection.None;
import nl.tudelft.context.model.newick.selection.Partial;
import nl.tudelft.context.model.newick.selection.Selection;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 06-05-2015
 */
public class NodeTest {

    /**
     * Test if a child is successfully set
     */
    @Test
    public void testChild() {
        Node n = new Node("a", 0);
        Node m = new Node("b", 1);
        n.addChild(m);
        assertEquals(n.getChildren(), Collections.singletonList(m));
    }

    /**
     * Node shouldn't equal null
     */
    @Test
    public void testNotEqualsNull() {
        assertFalse(new Node("", 0).equals(null));
    }

    /**
     * Node shouldn't equal other objects
     */
    @Test
    public void testNotEqualsOtherObject() {
        assertFalse(new Node("", 0).equals(""));
    }

    /**
     * A node should be "unknown" if it's not a leaf node.
     */
    @Test
    public void testIsUnknown() {
        Node n1 = new Node("", 1);
        Node n2 = new Node("abc", 9);

        assertTrue(n1.isUnknown());
        assertFalse(n2.isUnknown());
    }

    /**
     * The selection NONE should be toggled to ALL.
     */
    @Test
    public void testToggleSelectionFromNoneToAll() {
        Node n = new Node("", 1);
        n.setSelection(new None());
        n.toggleSelected();
        assertThat(n.getSelection(), instanceOf(All.class));
    }

    /**
     * The selection PARTIAL should be toggled to ALL.
     */
    @Test
    public void testToggleSelectionFromPartialToAll() {
        Node n = new Node("", 1);
        n.setSelection(new Partial());
        n.toggleSelected();
        assertThat(n.getSelection(), instanceOf(All.class));
    }

    /**
     * The selection ALL should be toggled to NONE.
     */
    @Test
    public void testToggleSelectionFromAllToNone() {
        Node n = new Node("", 1);
        n.setSelection(new All());
        n.toggleSelected();
        assertThat(n.getSelection(), instanceOf(None.class));
    }

    /**
     * Test if the node calls setSelection on its children.
     */
    @Test
    public void testSetSelection() {
        Node n1 = new Node("", 1);
        Node n2 = mock(Node.class);
        n1.addChild(n2);
        Selection selection = new All();
        n1.setSelection(selection);
        verify(n2, times(1)).setSelection(selection);
    }

    /**
     * If all children's selection is ALL: ALL
     */
    @Test
    public void testUpdateSelectionAll() {
        Node n1 = new Node("", 1);
        Node n2 = new Node("a", 2);
        Node n3 = new Node("b", 3);
        n1.addChild(n2);
        n1.addChild(n3);
        n2.setSelection(new All());
        n3.setSelection(new All());
        n1.updateSelected();
        assertThat(n1.getSelection(), instanceOf(All.class));
    }

    /**
     * If all children's selection is NONE: NONE
     */
    @Test
    public void testUpdateSelectionNone() {
        Node n1 = new Node("", 1);
        Node n2 = new Node("a", 2);
        Node n3 = new Node("b", 3);
        n1.addChild(n2);
        n1.addChild(n3);
        n2.setSelection(new None());
        n3.setSelection(new None());
        n1.updateSelected();
        assertThat(n1.getSelection(), instanceOf(None.class));
    }

    /**
     * If one child's selection is PARTIAL: PARTIAL
     */
    @Test
    public void testUpdateSelectionPartialPartial() {
        Node n1 = new Node("", 1);
        Node n2 = new Node("a", 2);
        Node n3 = new Node("b", 3);
        n1.addChild(n2);
        n1.addChild(n3);
        n2.setSelection(new Partial());
        n3.setSelection(new None());
        n1.updateSelected();
        assertThat(n1.getSelection(), instanceOf(Partial.class));
    }

    /**
     * If children's selections differ: PARTIAL
     */
    @Test
    public void testUpdateSelectionPartialAll() {
        Node n1 = new Node("", 1);
        Node n2 = new Node("a", 2);
        Node n3 = new Node("b", 3);
        n1.addChild(n2);
        n1.addChild(n3);
        n2.setSelection(new All());
        n3.setSelection(new None());
        n1.updateSelected();
        assertThat(n1.getSelection(), instanceOf(Partial.class));
    }

    /**
     * Node shouldn't equals nodes with other names or weights
     */
    @Test
    public void testNotEqualsOtherNode() {
        assertFalse(new Node("", 0).equals(new Node("", 1)));
        assertFalse(new Node("", 0).equals(new Node("a", 0)));
    }

    /**
     * Node should equal a similar node
     */
    @Test
    public void testEqualsNode() {
        assertTrue(new Node("", 0).equals(new Node("", 0)));
    }
}
