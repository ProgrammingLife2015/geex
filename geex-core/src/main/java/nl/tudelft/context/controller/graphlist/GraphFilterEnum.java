package nl.tudelft.context.controller.graphlist;

import nl.tudelft.context.model.graph.CollapseGraph;
import nl.tudelft.context.model.graph.InsertDeleteGraph;
import nl.tudelft.context.model.graph.SinglePointGraph;
import nl.tudelft.context.model.graph.StackGraph;
import nl.tudelft.context.model.graph.UnknownGraph;

/**
 * List the available graph filters.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 18-6-2015
 */
public enum GraphFilterEnum {
    SINGLE_POINT(SinglePointGraph.class),
    INSERT_DELETE(InsertDeleteGraph.class),
    COLLAPSE(CollapseGraph.class),
    UNKNOWN(UnknownGraph.class);

    private Class<? extends StackGraph> graph;

    public Class<? extends StackGraph> getGraph() {
        return graph;
    }

    GraphFilterEnum(Class<? extends StackGraph> graph) {
        this.graph = graph;
    }
}
