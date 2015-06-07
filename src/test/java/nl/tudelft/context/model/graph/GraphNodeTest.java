package nl.tudelft.context.model.graph;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.controller.DefaultGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.AbstractDrawableNode;
import nl.tudelft.context.drawable.DrawableGraphNode;
import nl.tudelft.context.drawable.DrawableNode;
import nl.tudelft.context.drawable.SinglePointLabel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 3-6-2015
 */
@RunWith(JfxRunner.class)
public class GraphNodeTest {

    static StackGraph graph;
    static GraphNode graphNode;
    static Map<Integer, DefaultNode> nodeMap = new HashMap<>();

    /**
     * Set up by parsing a single node1
     */
    @BeforeClass
    public static void beforeClass() throws FileNotFoundException, UnsupportedEncodingException {

        File nodeFile = new File(GraphTest.class.getResource("/graph/single-point-mutation.node.graph").getPath());
        File edgeFile = new File(GraphTest.class.getResource("/graph/single-point-mutation.edge.graph").getPath());

        graph = new GraphParser().setReader(nodeFile, edgeFile).parse().flat(new HashSet<>(Arrays.asList("Cat", "Dog")));

        graph.vertexSet().stream().forEach(vertex -> nodeMap.put(vertex.hashCode(), vertex)); // Ad by id (hashcode)

        graphNode = new GraphNode(graph, nodeMap.get(0), nodeMap.get(3));

    }

    /**
     * Test if contains the right nodes.
     */
    @Test
    public void testNodes() {

        Set<DefaultNode> nodes = new HashSet<>(nodeMap.values());
        nodes.remove(nodeMap.get(3));

        assertEquals(nodes, graphNode.getNodes());

    }

    /**
     * Test if the label is good.
     */
    @Test
    public void testLabel() {

        AbstractDrawableNode abstractDrawableNode = new DrawableGraphNode(mock(GraphNode.class));
        DefaultLabel defaultLabel = abstractDrawableNode.getLabel(new MainController(), mock(DefaultGraphController.class));

        assertThat(defaultLabel, instanceOf(SinglePointLabel.class));

    }

}
