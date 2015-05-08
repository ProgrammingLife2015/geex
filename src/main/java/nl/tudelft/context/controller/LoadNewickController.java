package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.newick.Tree;
import nl.tudelft.context.service.LoadNewickService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class LoadNewickController extends DefaultController<GridPane> implements Initializable {

    @FXML
    protected Button
            loadNewick,
            load;

    @FXML
    protected TextField
            nwkSource;

    protected LoadNewickService loadNewickService;
    protected ProgressIndicator progressIndicator;
    protected Group sequences;

    /**
     * Init a controller at load_newick.fxml.
     *
     * @param progressIndicator progress indicator of Newick loading
     * @throws RuntimeException
     */
    public LoadNewickController(ProgressIndicator progressIndicator, Group sequences) {

        super(new GridPane());

        this.progressIndicator = progressIndicator;
        this.sequences = sequences;

        loadFXML("/application/load_newick.fxml");


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

        loadNewickService = new LoadNewickService();
        loadNewickService.setOnSucceeded(event -> showTree(loadNewickService.getValue()));

        FileChooser nwkFileChooser = new FileChooser();
        nwkFileChooser.setTitle("Open Newick file");
        loadNewick.setOnAction(event -> loadNewickService.setNwkFile(loadFile(nwkFileChooser, nwkSource)));

        load.setOnAction(event -> loadTree());

    }

    /**
     * Load newick from source.
     */
    protected void loadTree() {

        sequences.getChildren().clear();
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
            sequences.getChildren().add(line);
        });

        // Bind nodes
        tree.vertexSet().stream().forEach(node -> {
            final Label label = new Label(node.getName());
            label.translateXProperty().bind(node.translateXProperty());
            label.translateYProperty().bind(node.translateYProperty());
            sequences.getChildren().add(label);
        });
    }

}
