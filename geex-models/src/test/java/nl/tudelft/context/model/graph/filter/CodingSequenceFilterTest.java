package nl.tudelft.context.model.graph.filter;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.model.annotation.CodingSequence;
import nl.tudelft.context.model.annotation.CodingSequenceMap;
import nl.tudelft.context.model.annotation.Resistance;
import nl.tudelft.context.model.annotation.ResistanceMap;
import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.GraphParser;
import nl.tudelft.context.model.graph.Node;
import nl.tudelft.context.model.graph.StackGraph;
import nl.tudelft.context.service.LoadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author René Vennik
 * @version 1.0
 * @since 24-6-2015
 */
@RunWith(JfxRunner.class)
public class CodingSequenceFilterTest {

    File nodeFile = new File(CodingSequenceFilterTest.class.getResource("/graph/node.graph").getPath());
    File edgeFile = new File(CodingSequenceFilterTest.class.getResource("/graph/edge.graph").getPath());

    CodingSequence codingSequence;
    Resistance resistance;

    StackGraph graph;
    Map<Integer, DefaultNode> nodeMap;
    StackGraph codingGraph;

    /**
     * Set up the graphs and node map.
     *
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException {

        codingSequence = new CodingSequence("seqId", "source", "type", 8, 11, 0f, 'A', 'B', "attributes");
        resistance = new Resistance("lorem", "ipsum", "Q264P", "dolor", 8, "set");

        LoadService<GraphMap> loadGraphService = new LoadService<>(GraphParser.class, nodeFile, edgeFile);
        CompletableFuture<GraphMap> graphMap = new CompletableFuture<>();

        loadGraphService.valueProperty().addListener((observable, oldValue, newValue) -> {
            newValue.setCodingSequence(new CodingSequenceMap(Collections.singletonList(codingSequence)));
            newValue.setResistance(new ResistanceMap(Collections.singletonList(resistance)));
            graphMap.complete(newValue);
        });
        loadGraphService.start();

        graph = graphMap.get(5, TimeUnit.SECONDS).flat(new HashSet<>(Arrays.asList("Dog", "Cat")));
        codingGraph = new CodingSequenceFilter(graph).getFilterGraph();

        nodeMap = graph.vertexSet().stream().collect(Collectors.toMap(
                node -> ((Node) node).getId(),
                Function.identity()
        ));

    }

    /**
     * Test if the right nodes are removed.
     */
    @Test
    public void testRemoved() {

        assertFalse(codingGraph.containsVertex(nodeMap.get(0))); // Removed node

        assertTrue(codingGraph.containsVertex(nodeMap.get(1)));
        assertTrue(codingGraph.containsVertex(nodeMap.get(2)));
        assertTrue(codingGraph.containsVertex(nodeMap.get(3)));

    }

}