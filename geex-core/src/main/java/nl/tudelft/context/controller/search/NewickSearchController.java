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

    /**
     * List of nodes in the current Newick.
     */
    private List<Label> nodes;

    /**
     * ScrollPane to move when focusing a label.
     */
    private ScrollPane newickScroller;

    /**
     * TextField used for a search query.
     */
    private TextField searchField;

    /**
     * Buttons used for moving the cursor to the previous/next label.
     */
    private Button searchPrev, searchNext;

    /**
     * List of currently found nodes.
     */
    private List<Label> selectedNodes;
    /**
     * Index of the active found node.
     */
    private int searchIndex;

    /**
     * Create a new NewickSearchController.
     *
     * @param nodes Nodes to search in.
     * @param container Container for the search bar.
     * @param newickScroller Scroller containing the Labels in nodes.
     */
    public NewickSearchController(final List<Label> nodes, final HBox container, final ScrollPane newickScroller) {
        this.nodes = nodes;
        this.newickScroller = newickScroller;

        searchField = new TextField();
        searchPrev = new Button("\u25b2");
        searchNext = new Button("\u25bc");

        container.getChildren().setAll(searchField, searchNext, searchPrev);

        selectedNodes = new ArrayList<>();

        searchField.setOnAction(event1 -> searchNext.fire());
        searchField.setOnKeyReleased(searchFieldEventHandler());
        searchNext.setOnAction(searchMoveEventHandler(1));
        searchPrev.setOnAction(searchMoveEventHandler(-1));
    }

    /**
     * Create an eventhandler for searching.
     *
     * @return Code to be executed when searching.
     */
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

    /**
     * Create an eventhandler for moving the search.
     *
     * @param dir Direction to move into. (1 or -1)
     * @return Code to be executed when moving.
     */
    public EventHandler<ActionEvent> searchMoveEventHandler(final int dir) {
        return event -> {
            if (selectedNodes.size() > 0) {
                selectedNodes.forEach(label -> label.getStyleClass().remove("search-focus"));

                searchIndex += dir + selectedNodes.size();
                searchIndex %= selectedNodes.size();

                ensureVisible(selectedNodes.get(searchIndex));
            }
        };
    }

    /**
     * Perform a search operation.
     *
     * @param query Query to search for.
     * @return A list of found Labels.
     */
    public List<Label> search(final String query) {
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

    /**
     * Make sure this Node is visible in the current scrollPane.
     * @param node Node to be made visible.
     */
    private void ensureVisible(final Node node) {
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
