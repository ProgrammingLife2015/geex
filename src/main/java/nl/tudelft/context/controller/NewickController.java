package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.NewickLabel;
import nl.tudelft.context.model.newick.Newick;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public final class NewickController extends ViewController<ScrollPane> {

    /**
     * Tells whether the controller is currently active (top view).
     */
    boolean active = false;
    /**
     * ProgressIndicator to show when the tree is loading.
     */
    @FXML
    ProgressIndicator progressIndicator;

    /**
     * The container of the newick tree.
     */
    @FXML
    Group newick;

    /**
     * The main controller used to set views.
     */
    MainController mainController;

    /**
     * The newick object, can change.
     */
    ObjectProperty<Newick> newickObjectProperty;

    /**
     * The menu item that initiates loadGraph().
     */
    MenuItem menuItem;

    /**
     * The current selection of the tree.
     */
    Set<String> selection;

    /**
     * The GraphController that belongs to the current selection.
     */
    GraphController graphController;

    /**
>>>>>>> origin/master
     * Init a controller at newick.fxml.
     *
     * @param mainController   MainController for the application
     * @param menuItem       MenuItem for the application
     * @param newickIn Newick object from the workspace, might not be loaded.
     */
    public NewickController(final MainController mainController, final MenuItem menuItem, final ReadOnlyObjectProperty<Newick> newickIn) {
        super(new ScrollPane());

        this.mainController = mainController;
        this.menuItem = menuItem;
        newickObjectProperty = new SimpleObjectProperty<>();

        newickObjectProperty.addListener((observable, oldValue, newValue) -> {
            showTree(newValue);
        });

        newickObjectProperty.bind(newickIn);

        loadFXML("/application/newick.fxml");
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        progressIndicator.visibleProperty().bind(newickObjectProperty.isNull());

        mainController.newickLifted.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                root.toFront();
            } else {
                root.toBack();
            }
        });
    }

    /**
     * Show the phylogenetic tree.
     *
     * @param newick newick to show
     */
    protected void showTree(final Newick newick) {
        // Bind edges
        List<DrawableEdge> edgeList = newick.edgeSet().stream()
                .map(edge -> new DrawableEdge(newick, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<Label> nodeList = newick.vertexSet().stream()
                .map(NewickLabel::new)
                .collect(Collectors.toList());

        this.newick.getChildren().addAll(edgeList);
        this.newick.getChildren().addAll(nodeList);

        menuItem.setOnAction(event -> loadGraph(newick));
        newick.getRoot().getSelectionProperty().addListener((observable, oldValue, newValue) ->
                menuItem.setDisable(!(active && newValue.isAny())));

        mainController.displayMessage(MessageController.SUCCESS_LOAD_TREE);
    }

    /**
     * Loads the graph of the selected strands.
     *
     * @param newick the tree with the nodes to show.
     */
    protected void loadGraph(final Newick newick) {
        Set<String> newSelection = newick.getRoot().getSources();
        if (!newSelection.isEmpty()) {
            if (!newSelection.equals(selection)) {
                graphController = new GraphController(mainController,
                        newSelection,
                        mainController.getWorkspace().getGraph(),
                        mainController.getWorkspace().getAnnotation());
                mainController.setView(this, graphController);
            } else {
                mainController.toView(graphController);
            }
            selection = newSelection;
        }
    }

    @Override
    public String getBreadcrumbName() {
        return "Phylogenetic tree";
    }

    @Override
    public void activate() {
        active = true;
        if (newickObjectProperty.isNull().get()) {
            menuItem.setDisable(!newickObjectProperty.get().getRoot().getSelection().isAny());
        }
    }

    @Override
    public void deactivate() {
        active = false;
        menuItem.setDisable(true);
    }

}
