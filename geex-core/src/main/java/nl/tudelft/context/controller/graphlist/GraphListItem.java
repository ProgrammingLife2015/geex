package nl.tudelft.context.controller.graphlist;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import nl.tudelft.context.logger.Log;

/**
 * @author René Vennik
 * @version 1.0
 * @since 17-6-2015
 */
public class GraphListItem extends Label implements Observable {
    private static DataFormat df = new DataFormat("GraphListItem");
    private InvalidationListener listener;

    private final GraphFilterEnum graph;

    /**
     * @param graph               Graph it represents
     * @param graphList ObservableList of filters.
     */
    public GraphListItem(final GraphFilterEnum graph, final ObservableList<GraphListItem> graphList) {
        this.graph = graph;
        getStyleClass().add("graph-item");
        setText(graph.toString());

        setOnMouseClicked(mouseClicked(graphList));
        setOnDragDetected(dragDetected());
        setOnDragDropped(dragDropped(graphList));
    }

    private EventHandler<DragEvent> dragDropped(ObservableList<GraphListItem> graphList) {
        return event -> {
            Log.debug("Drag dropped");
            graphList.forEach(GraphListItem::deactivate);
            GraphListItem self = (GraphListItem) event.getGestureSource();
            // If landed on another GraphListItem
            if (event.getGestureTarget() instanceof GraphListItem) {
                GraphListItem other = (GraphListItem) event.getGestureTarget();
                final int i = graphList.indexOf(other);
                graphList.remove(self);
                graphList.add(i, self);
            }
            listener.invalidated(self);
            event.consume();
        };
    }

    private EventHandler<MouseEvent> dragDetected() {
        return event -> {
            Log.debug("Drag detected");

            GraphListItem item = (GraphListItem) event.getSource();
            Dragboard db = item.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.put(df, item.toString());
            db.setContent(content);

            event.consume();
        };
    }

    private EventHandler<MouseEvent> mouseClicked(ObservableList<GraphListItem> graphList) {
        return event -> {
            GraphListItem source = (GraphListItem) event.getSource();

            int index = graphList.indexOf(source);
            if (source.getGraph().isActive()) {
                index--;
            }

            graphList.forEach(GraphListItem::deactivate);
            graphList.stream()
                    .limit(index+1)
                    .forEach(GraphListItem::activate);

            listener.invalidated(source);
        };
    }

    private void deactivate() {
        getStyleClass().remove("active");
        graph.setActive(false);
    }

    /**
     * Make this item active.
     */
    public void activate() {
        getStyleClass().add("active");
        graph.setActive(true);
    }

    public GraphFilterEnum getGraph() {
        return graph;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        this.listener = null;
    }
}
