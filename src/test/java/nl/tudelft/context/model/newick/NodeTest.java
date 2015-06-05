package nl.tudelft.context.model.newick;

import nl.tudelft.context.model.newick.node.AbstractNode;
import nl.tudelft.context.model.newick.node.StrandNode;
import nl.tudelft.context.model.newick.selection.All;
import nl.tudelft.context.model.newick.selection.None;
import nl.tudelft.context.model.newick.selection.Partial;
import nl.tudelft.context.model.newick.selection.Selection;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
        AbstractNode n = new StrandNode("a", 0);
        AbstractNode m = new StrandNode("b", 1);
        n.addChild(m);
        assertEquals(n.getChildren(), Collections.singletonList(m));
    }

    /**
     * The selection NONE should be toggled to ALL.
     */
    @Test
    public void testToggleSelectionFromNoneToAll() {
        AbstractNode n = new StrandNode("", 1);
        n.setSelection(new None());
        n.toggleSelected();
        assertThat(n.getSelection(), instanceOf(All.class));
    }

    /**
     * The selection PARTIAL should be toggled to ALL.
     */
    @Test
    public void testToggleSelectionFromPartialToAll() {
        AbstractNode n = new StrandNode("", 1);
        n.setSelection(new Partial());
        n.toggleSelected();
        assertThat(n.getSelection(), instanceOf(All.class));
    }

    /**
     * The selection ALL should be toggled to NONE.
     */
    @Test
    public void testToggleSelectionFromAllToNone() {
        AbstractNode n = new StrandNode("", 1);
        n.setSelection(new All());
        n.toggleSelected();
        assertThat(n.getSelection(), instanceOf(None.class));
    }

    /**
     * Test if the node calls setSelection on its children.
     */
    @Test
    public void testSetSelection() {
        AbstractNode n1 = new StrandNode("", 1);
        AbstractNode n2 = mock(AbstractNode.class);
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
        AbstractNode n1 = new StrandNode("", 1);
        AbstractNode n2 = new StrandNode("a", 2);
        AbstractNode n3 = new StrandNode("b", 3);
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
        AbstractNode n1 = new StrandNode("", 1);
        AbstractNode n2 = new StrandNode("a", 2);
        AbstractNode n3 = new StrandNode("b", 3);
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
        AbstractNode n1 = new StrandNode("", 1);
        AbstractNode n2 = new StrandNode("a", 2);
        AbstractNode n3 = new StrandNode("b", 3);
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
        AbstractNode n1 = new StrandNode("", 1);
        AbstractNode n2 = new StrandNode("a", 2);
        AbstractNode n3 = new StrandNode("b", 3);
        n1.addChild(n2);
        n1.addChild(n3);
        n2.setSelection(new All());
        n3.setSelection(new None());
        n1.updateSelected();
        assertThat(n1.getSelection(), instanceOf(Partial.class));
    }
}
