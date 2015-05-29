package nl.tudelft.context.controller;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.NewickLabel;
import nl.tudelft.context.model.newick.Tree;
import nl.tudelft.context.model.newick.TreeParser;
import nl.tudelft.context.service.LoadService;
import nl.tudelft.context.workspace.Workspace;

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
     * The service used for loading the newick tree.
     */
    Service<Tree> loadNewickService;

    /**
     * The menu item that initiates loadGraph().
     */
    MenuItem menuItem;

    /**
     * The loaded phylogenetic tree.
     */
    Tree tree;

    /**
     * Init a controller at newick.fxml.
     *
     * @param mainController   MainController for the application
     * @param menuItem   MenuItem for the application
     */
    public NewickController(final MainController mainController, final MenuItem menuItem) {

        super(new ScrollPane());

        this.mainController = mainController;
        this.menuItem = menuItem;

        Workspace workspace = mainController.getWorkspace();
        this.loadNewickService = new LoadService<>(TreeParser.class, workspace.getNwkFile());

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

        progressIndicator.visibleProperty().bind(loadNewickService.runningProperty());

        mainController.newickLifted.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                root.toFront();
            } else {
                root.toBack();
            }
        });

        loadTree();

    }

    /**
     * Load newick from source.
     */
    public void loadTree() {

        loadNewickService.setOnSucceeded(event -> showTree(loadNewickService.getValue()));
        loadNewickService.setOnFailed(event -> mainController.displayMessage(MessageController.FAIL_LOAD_TREE));
        loadNewickService.restart();

    }

    /**
     * Show the phylogenetic tree.
     *
     * @param tree newick to show
     */
    protected void showTree(final Tree tree) {

        this.tree = tree;

        // Bind edges
        List<DrawableEdge> edgeList = tree.edgeSet().stream()
                .map(edge -> new DrawableEdge(tree, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<Label> nodeList = tree.vertexSet().stream()
                .map(NewickLabel::new)
                .collect(Collectors.toList());

        newick.getChildren().addAll(edgeList);
        newick.getChildren().addAll(nodeList);


        menuItem.setOnAction(event -> loadGraph(tree));
        tree.getRoot().getSelectionProperty().addListener((observable, oldValue, newValue) ->
                menuItem.setDisable(!(active && newValue.isAny())));

        mainController.displayMessage(MessageController.SUCCESS_LOAD_TREE);
    }

    /**
     * Loads the graph of the selected strands.
     *
     * @param tree the tree with the nodes to show.
     */
    protected void loadGraph(final Tree tree) {
        Set<String> sources = tree.getRoot().getSources();
        if (!sources.isEmpty()) {
            mainController.setView(this, new GraphController(mainController, sources));
        }
    }

    @Override
    public String getBreadcrumbName() {
        return "Phylogenetic tree";
    }

    @Override
    public void activate() {
        active = true;
        if (tree != null) {
            menuItem.setDisable(!tree.getRoot().getSelection().isAny());
        }
    }

    @Override
    public void deactivate() {
        active = false;
        menuItem.setDisable(true);
    }

}
