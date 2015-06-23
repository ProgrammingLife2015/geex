package nl.tudelft.context.model.graph;

import de.saxsys.javafx.test.JfxRunner;
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
public class InsertDeleteGraphTest {

    File nodeFile = new File(InsertDeleteGraphTest.class.getResource("/graph/insert-delete-graph.node.graph").getPath());
    File edgeFile = new File(InsertDeleteGraphTest.class.getResource("/graph/insert-delete-graph.edge.graph").getPath());

    StackGraph graph;
    Map<Integer, DefaultNode> nodeMap;
    InsertDeleteGraph insertDeleteGraph;

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
        insertDeleteGraph = new InsertDeleteGraph(graph);

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

        assertTrue(insertDeleteGraph.containsVertex(nodeMap.get(0)));
        assertTrue(insertDeleteGraph.containsVertex(nodeMap.get(1)));
        assertTrue(insertDeleteGraph.containsVertex(nodeMap.get(2)));

        assertFalse(insertDeleteGraph.containsVertex(nodeMap.get(3))); // Collapsed node
        assertFalse(insertDeleteGraph.containsVertex(nodeMap.get(4))); // Collapsed node

        assertTrue(insertDeleteGraph.containsVertex(nodeMap.get(5)));
        assertTrue(insertDeleteGraph.containsVertex(nodeMap.get(6)));

    }

    /**
     * Test if the graph node contains the collapsed nodes.
     */
    @Test
    public void testGraphNode() {

        List<GraphNode> graphNodes = insertDeleteGraph.vertexSet().stream()
                .filter(node -> node instanceof GraphNode)
                .map(node -> (GraphNode) node)
                .collect(Collectors.toList());

        assertEquals(1, graphNodes.size());

        Set<DefaultNode> expectedNodes = new HashSet<>(Arrays.asList(nodeMap.get(3), nodeMap.get(4)));

        assertEquals(expectedNodes, graphNodes.get(0).nodes);

    }

}
