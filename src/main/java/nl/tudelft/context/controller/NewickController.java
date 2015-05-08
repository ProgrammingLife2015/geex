package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.newick.Tree;
import nl.tudelft.context.service.LoadNewickService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public class NewickController extends DefaultController<ScrollPane> implements Initializable {

    @FXML
    protected Group newick;

    protected MainController mainController;
    protected LoadNewickService loadNewickService;

    /**
     * Init a controller at graph.fxml.
     *
     * @param loadNewickService service with file locations
     */
    public NewickController(MainController mainController, LoadNewickService loadNewickService) {

        super(new ScrollPane());

        this.mainController = mainController;
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
    public void initialize(URL location, ResourceBundle resources) {

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
    protected void showTree(Tree tree) {

        // Bind edges
        tree.edgeSet().stream().forEach(edge -> {
            final DrawableEdge line = new DrawableEdge(tree, edge);
            newick.getChildren().add(line);
        });

        // Bind nodes
        tree.vertexSet().stream().forEach(node -> {
            final Label label = new Label(node.getName());
            label.translateXProperty().bind(node.translateXProperty());
            label.translateYProperty().bind(node.translateYProperty());
            newick.getChildren().add(label);
        });
    }

}
