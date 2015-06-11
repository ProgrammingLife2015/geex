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
     * Sets the annotations to all of the graph's nodes.
     *
     * @param annotationMap The map with annotations to add
     */
    public void setAnnotations(final AnnotationMap annotationMap) {
        vertexSet().parallelStream()
                .forEach(node -> node.setAnnotations(annotationMap));
    }

}
