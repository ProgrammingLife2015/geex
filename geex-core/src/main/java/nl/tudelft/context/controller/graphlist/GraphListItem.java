package nl.tudelft.context.controller.graphlist;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import nl.tudelft.context.logger.Log;

/**
 * @author René Vennik
 * @version 1.0
 * @since 17-6-2015
 */
public class GraphListItem extends Label {
    static DataFormat df = new DataFormat("mycell");


    public GraphFilterEnum getGraph() {
        return graph;
    }

    private final GraphFilterEnum graph;

    /**
     * @param graph               Graph it represents
     * @param graphList ObservableList of filters.
     */
    public GraphListItem(final GraphFilterEnum graph, final ObservableList<GraphFilterEnum> graphList) {
        this.graph = graph;

        getStyleClass().add("graph-item");
        //setOnMouseClicked(event -> activeGraphProperty.set(graph));
        setText(graph.toString());
        //getChildren().add(new Label(graph.getName()));

        setOnMouseClicked(event1 -> {
            graphList.forEach(graphFilterEnum -> {
                if (!graphFilterEnum.equals(graph)) {
                    graphFilterEnum.setActive(true);
                    setActive();
                } else {
                    graphFilterEnum.setActive(false);
                }
            });

            graphList.removeAll((GraphFilterEnum) null);
        });

        setOnDragDetected(event -> {
            Log.debug("Drag detected");

            GraphListItem item = (GraphListItem) event.getSource();
            Dragboard db = item.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.put(df, item.toString());
            db.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            Log.debug("Drag over");


            this.setStyle("-fx-padding-bottom: 30px;");

            event.acceptTransferModes(TransferMode.MOVE);

            event.consume();
        });

        setOnDragDropped(event -> {
            Log.debug("Drag dropped");
            if (event.getGestureTarget() instanceof GraphListItem) {
                GraphListItem other = (GraphListItem) event.getGestureTarget();
                GraphListItem self = (GraphListItem) event.getGestureSource();
                final int i = graphList.indexOf(other.getGraph());

                graphList.remove(self.getGraph());
                graphList.add(i, self.getGraph());

                System.out.println("From: " + self.toString());
                System.out.println("To: " + other.toString());
                graphList.forEach(stackGraph -> System.out.println(stackGraph.toString()));
            }
            event.consume();
        });

    }

    /**
     * Make this item active.
     */
    public void setActive() {
        getStyleClass().add("active");
    }

}
