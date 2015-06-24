package nl.tudelft.context.model.graph;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.model.graph.filter.SinglePointFilter;
import nl.tudelft.context.service.LoadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author René Vennik
 * @version 1.0
 * @since 18-6-2015
 */
@RunWith(JfxRunner.class)
public class SinglePointFilterTest {

    File nodeFile = new File(InsertDeleteFilterTest.class.getResource("/graph/single-point-mutation.node.graph").getPath());
    File edgeFile = new File(InsertDeleteFilterTest.class.getResource("/graph/single-point-mutation.edge.graph").getPath());

    StackGraph graph;
    Map<Integer, DefaultNode> nodeMap;
    StackGraph singlePointGraph;

    /**
     * Set up the graphs and node map.
     *
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException {

        LoadService<GraphMap> loadGraphService = new LoadService<>(GraphParser.class, nodeFile, edgeFile);
        CompletableFuture<GraphMap> graphMap = new CompletableFuture<>();

        loadGraphService.valueProperty().addListener((observable, oldValue, newValue) -> {
            graphMap.complete(newValue);
        });
        loadGraphService.start();

        graph = graphMap.get(5, TimeUnit.SECONDS).flat(new HashSet<>(Arrays.asList("Dog", "Cat")));
        singlePointGraph = new SinglePointFilter(graph).getFilterGraph();

        nodeMap = graph.vertexSet().stream().collect(Collectors.toMap(
                node -> ((Node) node).getId(),
                Function.identity()
        ));

    }

    /**
     * Test that the insert delete graph doesn't contain the nodes that are collapsed but contains the other nodes.
     */
    @Test
    public void testCollapsed() {

        assertFalse(singlePointGraph.containsVertex(nodeMap.get(0))); // Collapsed node
        assertFalse(singlePointGraph.containsVertex(nodeMap.get(1))); // Collapsed node
        assertFalse(singlePointGraph.containsVertex(nodeMap.get(2))); // Collapsed node
        assertTrue(singlePointGraph.containsVertex(nodeMap.get(3)));

    }

    /**
     * Test if the graph node contains the collapsed nodes.
     */
    @Test
    public void testGraphNode() {

        List<GraphNode> graphNodes = singlePointGraph.vertexSet().stream()
                .filter(node -> node instanceof GraphNode)
                .map(node -> (GraphNode) node)
                .collect(Collectors.toList());

        assertEquals(1, graphNodes.size());

        Set<DefaultNode> expectedNodes = new HashSet<>(Arrays.asList(nodeMap.get(0), nodeMap.get(1), nodeMap.get(2)));

        assertEquals(expectedNodes, graphNodes.get(0).nodes);

    }

}
