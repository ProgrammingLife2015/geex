package nl.tudelft.context.model.graph;

import nl.tudelft.context.model.annotation.CodingSequence;
import nl.tudelft.context.model.annotation.CodingSequenceMap;
import nl.tudelft.context.model.annotation.Resistance;
import nl.tudelft.context.model.annotation.ResistanceMap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 1-6-2015
 */
public class GraphNode extends DefaultNode {

    /**
     * Parent graph.
     */
    StackGraph parentGraph;

    /**
     * Nodes of sub graph.
     */
    Set<DefaultNode> nodes = new HashSet<>();

    /**
     * Reference start and end node.
     */
    int refStart, refEnd;

    /**
     * Type of graph node.
     */
    String type;

    /**
     * The Counter for the number of ACTG.
     */
    BaseCounter baseCounter = new BaseCounter();

    /**
     * Create a graph node.
     *
     * @param parentGraph Parent graph
     * @param start       Start of sub graph
     * @param end         End of sub graph
     * @param type        Type of the sub graph
     */
    public GraphNode(final StackGraph parentGraph, final DefaultNode start, final DefaultNode end, final String type) {

        this.parentGraph = parentGraph;
        this.type = type;

        sources = start.getSources();

        Queue<DefaultNode> queue = new LinkedList<>();
        queue.add(start);
        nodes.add(start);

        while (!queue.isEmpty()) {

            DefaultNode node = queue.remove();

            parentGraph.getTargets(node).stream()
                    .filter(n -> !nodes.contains(n) && !n.equals(end))
                    .forEach(n -> {
                        queue.add(n);
                        nodes.add(n);
                    });

        }

        calculateProperties();

    }

    /**
     * Calculate the node properties: base counter, reference positions.
     */
    private void calculateProperties() {

        nodes.stream()
                .map(DefaultNode::getBaseCounter)
                .forEach(baseCounter::addBaseCounter);

        refStart = nodes.stream().mapToInt(DefaultNode::getRefStartPosition).min().getAsInt();
        refEnd = nodes.stream().mapToInt(DefaultNode::getRefEndPosition).max().getAsInt();

    }

    @Override
    public int getSize() {
        return nodes.stream().mapToInt(DefaultNode::getSize).sum();
    }

    @Override
    public String getContent() {
        return Integer.toString(getSize());
    }

    @Override
    public BaseCounter getBaseCounter() {
        return baseCounter;
    }

    /**
     * Get the parent graph of this graph.
     *
     * @return Parent graph of this graph
     */
    public StackGraph getParentGraph() {
        return parentGraph;
    }

    /**
     * Get the type of the graph.
     *
     * @return Type of graph.
     */
    public String getType() {
        return type;
    }

    /**
     * Add a node to the sub graph.
     *
     * @param node Node to add.
     */
    public void addNode(final DefaultNode node) {
        nodes.add(node);
        baseCounter.addBaseCounter(node.getBaseCounter());
        refStart = Math.min(refStart, node.getRefStartPosition());
        refEnd = Math.max(refEnd, node.getRefStartPosition());
    }

    /**
     * Get nodes of sub graph.
     *
     * @return Nodes of sub graph
     */
    public Set<DefaultNode> getNodes() {
        return nodes;
    }

    @Override
    public int getRefStartPosition() {
        return refStart;
    }

    @Override
    public int getRefEndPosition() {
        return refEnd;
    }

    @Override
    public void setCodingSequences(final CodingSequenceMap codingSequenceMap) {
        // do nothing
    }

    @Override
    public List<CodingSequence> getCodingSequences() {
        return nodes.stream()
                .map(DefaultNode::getCodingSequences)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public  void setResistances(final ResistanceMap resistanceMap) {
        // do nothing
    }

    @Override
    public List<Resistance> getResistances() {
        return Collections.emptyList();
    }

}
