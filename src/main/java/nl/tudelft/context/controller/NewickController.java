package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.newick.Tree;
import nl.tudelft.context.service.LoadNewickService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public final class NewickController extends ViewController<ScrollPane> {

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
    LoadNewickService loadNewickService;

    /**
     * Init a controller at newick.fxml.
     *
     * @param mainController   MainController for the application
     */
    public NewickController(final MainController mainController) {

        super(new ScrollPane());

        try {
            this.mainController = mainController;

            this.loadNewickService = mainController.getWorkspace().getNewickList().get(0);
            loadFXML("/application/newick.fxml");
        } catch (IndexOutOfBoundsException e) {
            mainController.displayMessage("Could not load phylogenetic tree.");
            mainController.breadCrumb.setOpacity(0);
        }



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

        loadTree();

    }

    /**
     * Load newick from source.
     */
    public void loadTree() {

        loadNewickService.setOnSucceeded(event -> {
            showTree(loadNewickService.getValue());
            mainController.breadCrumb.setOpacity(1);
            mainController.displayMessage("Phylogenetic tree loaded successfully.");
        });
        loadNewickService.setOnFailed(event -> mainController.displayMessage("Could not load phylogenetic tree."));
        loadNewickService.restart();

    }

    /**
     * Show the newick in console.
     *
     * @param tree newick to show
     */
    protected void showTree(final Tree tree) {

        // Bind edges
        List<DrawableEdge> edgeList = tree.edgeSet().stream()
                .map(edge -> new DrawableEdge(tree, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<Label> nodeList = tree.vertexSet().stream()
                .map(node -> {
                    final Label label = new Label(node.getName());
                    label.setCache(true);
                    label.translateXProperty().bind(node.translateXProperty());
                    label.translateYProperty().bind(node.translateYProperty());
                    if (node.isUnknown()) {
                        label.getStyleClass().add("ancestor");
                    }
                    label.setOnMouseClicked(event ->
                            mainController.setView(new GraphController(mainController, node.getSources())));
                    return label;
                }).collect(Collectors.toList());

        newick.getChildren().addAll(edgeList);
        newick.getChildren().addAll(nodeList);
    }

    @Override
    public String getBreadcrumbName() {
        return "Phylogenetic tree";
    }

}
