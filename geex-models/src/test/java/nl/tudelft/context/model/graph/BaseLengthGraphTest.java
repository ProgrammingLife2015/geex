package nl.tudelft.context.model.graph;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.model.graph.filter.BaseLengthGraph;
import nl.tudelft.context.model.graph.parser.GraphParser;
import nl.tudelft.context.service.LoadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author René Vennik
 * @version 1.0
 * @since 18-6-2015
 */
@RunWith(JfxRunner.class)
public class BaseLengthGraphTest {

    File nodeFile = new File(BaseLengthGraphTest.class.getResource("/graph/base-length-graph.node.graph").getPath());
    File edgeFile = new File(BaseLengthGraphTest.class.getResource("/graph/base-length-graph.edge.graph").getPath());

    StackGraph graph;
    Map<Integer, DefaultNode> nodeMap;
    StackGraph baseLengthGraph;

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
        baseLengthGraph = new BaseLengthGraph(graph).getFilterGraph();

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

        assertTrue(baseLengthGraph.containsVertex(nodeMap.get(0)));
        assertTrue(baseLengthGraph.containsVertex(nodeMap.get(1)));
        assertTrue(baseLengthGraph.containsVertex(nodeMap.get(2)));
        assertTrue(baseLengthGraph.containsVertex(nodeMap.get(3)));
        assertTrue(baseLengthGraph.containsVertex(nodeMap.get(4)));
        assertTrue(baseLengthGraph.containsVertex(nodeMap.get(5)));
        assertFalse(baseLengthGraph.containsVertex(nodeMap.get(6)));

    }

    /**
     * Test that there is no graph node created.
     */
    @Test
    public void testNoGraphNode() {

        List<GraphNode> graphNodes = baseLengthGraph.vertexSet().stream()
                .filter(node -> node instanceof GraphNode)
                .map(node -> (GraphNode) node)
                .collect(Collectors.toList());

        assertEquals(0, graphNodes.size());

    }

}
