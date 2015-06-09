package nl.tudelft.context.drawable.graph;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.Node;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mock;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 7-6-2015
 */
public class DrawableNodeFactoryTest {
    @Test
    public void testCreateNode() throws Exception {
        AbstractDrawableNode drawableNode = DrawableNodeFactory.create(mock(Node.class));

        assertThat(drawableNode, instanceOf(DrawableNode.class));
    }

    @Test
    public void testCreateGraphNode() throws Exception {
        AbstractDrawableNode drawableNode = DrawableNodeFactory.create(mock(GraphNode.class));

        assertThat(drawableNode, instanceOf(DrawableGraphNode.class));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateFalse() throws Exception {
        DrawableNodeFactory.create(mock(DefaultNode.class));
    }

}