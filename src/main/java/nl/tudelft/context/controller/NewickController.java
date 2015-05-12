package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
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
public final class NewickController extends DefaultController<ScrollPane> {

    @FXML
    Group newick;

    LoadNewickService loadNewickService;

    /**
     * Init a controller at newick.fxml.
     *
     * @param loadNewickService service with file locations
     */
    public NewickController(final LoadNewickService loadNewickService) {

        super(new ScrollPane());
        this.loadNewickService = loadNewickService;

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

        loadNewickService.setOnSucceeded(event -> showTree(loadNewickService.getValue()));

        loadTree();

    }

    /**
     * Load newick from source.
     */
    public void loadTree() {

        newick.getChildren().clear();
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
                    return label;
                }).collect(Collectors.toList());

        newick.getChildren().addAll(edgeList);
        newick.getChildren().addAll(nodeList);
    }

}
