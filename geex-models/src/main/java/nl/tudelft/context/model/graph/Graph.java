package nl.tudelft.context.model.graph;

import nl.tudelft.context.model.annotation.AnnotationMap;

/**
 * Graph.
 *
 * @author René Vennik
 * @version 1.0
 * @since 23-4-2015
 */
public class Graph extends StackGraph {

    /**
     * Create a clean with default edges and nodes.
     */
    public Graph() {

    }

    /**
     * Sets the codingSequences to all of the graph's nodes.
     *
     * @param codingSequenceMap The map with codingSequences to add
     */
    public void setAnnotations(final AnnotationMap codingSequenceMap) {
        vertexSet().parallelStream()
                .forEach(node -> node.setCodingSequences(codingSequenceMap));
    }

    /**
     * Sets the resistance to all of the graph's nodes.
     *
     * @param resistanceMap The map with resistance mutations to add
     */
    public void setResistance(final AnnotationMap resistanceMap) {
        vertexSet().parallelStream()
                .forEach(node -> node.setResistance(resistanceMap));
    }

}
