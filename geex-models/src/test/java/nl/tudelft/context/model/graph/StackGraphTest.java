package nl.tudelft.context.model.graph;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.model.graph.parser.GraphParser;
import nl.tudelft.context.service.LoadService;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author René Vennik
 * @version 1.0
 * @since 18-6-2015
 */
@RunWith(JfxRunner.class)
public class StackGraphTest {

    File nodeFile = new File(StraightFilterTest.class.getResource("/graph/collapse-graph.node.graph").getPath());
    File edgeFile = new File(StraightFilterTest.class.getResource("/graph/collapse-graph.edge.graph").getPath());

    StackGraph graph;
    Map<Integer, DefaultNode> nodeMap;

    @Test
    public void testCreateSubGraph() throws InterruptedException, ExecutionException, TimeoutException {

        LoadService<GraphMap> loadGraphService = new LoadService<>(GraphParser.class, nodeFile, edgeFile);
        CompletableFuture<GraphMap> graphMap = new CompletableFuture<>();

        loadGraphService.valueProperty().addListener((observable, oldValue, newValue) -> {
            graphMap.complete(newValue);
        });
        loadGraphService.start();

        graph = graphMap.get(5, TimeUnit.SECONDS).flat(new HashSet<>(Arrays.asList("Dog", "Cat")));

        nodeMap = graph.vertexSet().stream().collect(Collectors.toMap(
                node -> ((Node) node).getId(),
                Function.identity()
        ));

        Set<DefaultNode> nodes = new HashSet<>(Arrays.asList(nodeMap.get(3), nodeMap.get(4), nodeMap.get(5)));
        Graph subGraph = graph.createSubGraph(nodes);

        assertTrue(subGraph.containsEdge(nodeMap.get(3), nodeMap.get(4)));
        assertTrue(subGraph.containsEdge(nodeMap.get(4), nodeMap.get(5)));
        assertEquals(nodes, subGraph.vertexSet());
        assertEquals(2, subGraph.edgeSet().size());

    }

}
