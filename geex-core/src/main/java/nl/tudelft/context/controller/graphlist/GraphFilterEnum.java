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
    SINGLE_POINT(SinglePointGraph.class, "Point mutations"),
    INSERT_DELETE(InsertDeleteGraph.class, "Insertions / Deletions"),
    COLLAPSE(CollapseGraph.class, "Straight sequences"),
    UNKNOWN(UnknownGraph.class, "Unknown bases (" + (UnknownGraph.REMOVE_RATIO * 100) + "%)");

    private Class<? extends StackGraph> graph;
    private String name;
    private boolean active;

    public Class<? extends StackGraph> getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        return name;
    }

    GraphFilterEnum(Class<? extends StackGraph> graph, String name) {
        this.graph = graph;
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
