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
 * @author Gerben Oolbekkink
 * @version 2.0
 * @since 17-6-2015
 */
public class GraphFilterLabel extends Label implements Observable, Destroyable {
    /**
     * In order to make this drag and drop work.
     */
    private static DataFormat df = new DataFormat("GraphListItem");
    /**
     * The class which is listening to this item.
     */
    private InvalidationListener listener;

    /**
     * The filter for this item.
     */
    private final GraphFilter graph;
    private ObservableList<GraphFilterLabel> graphList;

    /**
     * Whether this filter is active.
     */
    private boolean active;

    /**
     * @param graph     Graph it represents
     * @param graphList ObservableList of filters.
     */
    public GraphFilterLabel(final GraphFilter graph, final ObservableList<GraphFilterLabel> graphList) {
        this.graph = graph;
        this.graphList = graphList;
        activate();
        getStyleClass().add("graph-item");
        setText(graph.toString());

        setOnMouseClicked(mouseClicked(graphList));
        setOnDragDetected(dragDetected());
        setOnDragOver(dragOver());
        setOnDragDropped(dragDropped(graphList));
    }

    /**
     * Response to the drag over event, by moving the item.
     *
     * @return The event handler, associated with it.
     */
    private EventHandler<? super DragEvent> dragOver() {
        return event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        };
    }

    /**
     * EventHandler for dragDropped.
     * <p/>
     * Will move the active element to the right position in the list.
     *
     * @param graphList List containing all the items.
     * @return A new eventhandler for dragdropped.
     */
    private EventHandler<DragEvent> dragDropped(final ObservableList<GraphFilterLabel> graphList) {
        return event -> {
            Log.debug("Drag dropped");
            graphList.forEach(GraphFilterLabel::deactivate);
            GraphFilterLabel self = (GraphFilterLabel) event.getGestureSource();
            // If landed on another GraphListItem
            if (event.getGestureTarget() instanceof GraphFilterLabel) {
                GraphFilterLabel other = (GraphFilterLabel) event.getGestureTarget();
                final int i = graphList.indexOf(other);
                graphList.remove(self);
                graphList.add(i, self);
            }
            listener.invalidated(self);
            event.consume();
        };
    }

    /**
     * An eventHandler for dragDetected.
     * <p/>
     * Will init a new drag and drop.
     *
     * @return EventHandler used for dragDetected.
     */
    private EventHandler<MouseEvent> dragDetected() {
        return event -> {
            Log.debug("Drag detected");

            GraphFilterLabel item = (GraphFilterLabel) event.getSource();
            Dragboard db = item.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.put(df, item.toString());
            db.setContent(content);

            event.consume();
        };
    }

    /**
     * EventHandler for mouseClicked.
     * <p/>
     * Will activate the item and make the listener reload.
     *
     * @param graphList List of items to change.
     * @return An eventHandler.
     */
    private EventHandler<MouseEvent> mouseClicked(final ObservableList<GraphFilterLabel> graphList) {
        return event -> {
            GraphFilterLabel source = (GraphFilterLabel) event.getSource();

            int index = graphList.indexOf(source);
            if (source.isActive()) {
                index--;
            }

            graphList.forEach(GraphFilterLabel::deactivate);
            graphList.stream()
                    .limit(index + 1)
                    .forEach(GraphFilterLabel::activate);

            listener.invalidated(source);
        };
    }

    /**
     * Deactivate this item.
     */
    private void deactivate() {
        getStyleClass().remove("active");
        active = false;
    }

    /**
     * Make this item active.
     */
    private void activate() {
        getStyleClass().add("active");
        active = true;
    }

    /**
     * Check the active state of this filter.
     *
     * @return whether this filter is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Get the graph of this item.
     *
     * @return The graph of this item.
     */
    public GraphFilter getFilter() {
        return graph;
    }

    @Override
    public void addListener(final InvalidationListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener(final InvalidationListener listener) {
        this.listener = null;
    }

    public void destroy() {
        graphList.remove(this);
    }
}
