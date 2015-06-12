package nl.tudelft.context.controller.search;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 12-6-2015
 */
public class NewickSearchController {

    private List<Label> nodes;
    private ScrollPane newickScroller;

    private TextField searchField;
    private Button searchPrev, searchNext;

    private List<Label> selectedNodes;
    private int searchIndex;

    public NewickSearchController(final List<Label> nodes, HBox container, ScrollPane newickScroller) {
        this.nodes = nodes;
        this.newickScroller = newickScroller;

        searchField = new TextField();
        searchPrev = new Button("\u25b2");
        searchNext = new Button("\u25bc");

        container.getChildren().setAll(searchField, searchNext, searchPrev);

        searchField.setOnAction(event1 -> searchNext.fire());
        searchField.setOnKeyReleased(searchFieldEventHandler());
        searchNext.setOnAction(searchMoveEventHandler(1));
        searchPrev.setOnAction(searchMoveEventHandler(-1));
    }

    public EventHandler<KeyEvent> searchFieldEventHandler() {
        return event -> {
            if (event.getCode() == KeyCode.ENTER) {
                return;
            }
            selectedNodes = NewickSearchController.this.search(searchField.getText());
            searchIndex = 0;

            if (selectedNodes.size() > 0) {
                NewickSearchController.this.ensureVisible(selectedNodes.get(searchIndex));
            }
        };
    }

    public EventHandler<ActionEvent> searchMoveEventHandler(int dir) {
        return event -> {
            selectedNodes.forEach(label -> label.getStyleClass().remove("search-focus"));

            searchIndex += dir;
            searchIndex %= selectedNodes.size();

            ensureVisible(selectedNodes.get(searchIndex));
        };
    }

    public List<Label> search(String query) {
        nodes.stream().forEach(label -> label.getStyleClass().removeAll("search", "search-focus"));
        if (query.length() < 1) {
            return new ArrayList<>();
        }

        List<Label> selectedLabels = nodes.stream()
                .filter(label -> label.getText().contains(query))
                .collect(Collectors.toList());

        selectedLabels.forEach(label -> label.getStyleClass().add("search"));

        return selectedLabels;
    }

    private void ensureVisible(Node node) {
        double width = newickScroller.getContent().getBoundsInLocal().getWidth();
        double height = newickScroller.getContent().getBoundsInLocal().getHeight();

        double x = node.getBoundsInParent().getMaxX();
        double y = node.getBoundsInParent().getMaxY();

        // scrolling values range from 0 to 1
        newickScroller.setVvalue(y / height);
        newickScroller.setHvalue(x / width);

        node.getStyleClass().add("search-focus");
    }
}
