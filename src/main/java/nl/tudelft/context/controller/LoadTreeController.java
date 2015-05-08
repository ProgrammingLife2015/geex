package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import nl.tudelft.context.service.LoadNewickService;
import nl.tudelft.context.newick.Tree;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class LoadTreeController extends DefaultController<GridPane> implements Initializable {

    @FXML
    protected Button
            loadTree,
            load;

    @FXML
    protected TextField treeSource;

    protected LoadNewickService loadNewickService;
    protected ProgressIndicator progressIndicator;
    protected Group sequences;

    /**
     * Init a controller at load_tree.fxml.
     *
     * @param progressIndicator progress indicator of newick loading
     * @param sequences         grid to display newick
     * @throws RuntimeException
     */
    public LoadTreeController(ProgressIndicator progressIndicator, Group sequences) {

        super(new GridPane());

        this.progressIndicator = progressIndicator;
        this.sequences = sequences;

        loadFXML("/application/load_tree.fxml");

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

        progressIndicator.visibleProperty().bind(loadNewickService.runningProperty());

        FileChooser nwkFileChooser = new FileChooser();
        nwkFileChooser.setTitle("Open Newick file");
        loadTree.setOnAction(event -> loadNewickService.setNwkFile(loadFile(nwkFileChooser, treeSource)));

        load.setOnAction(event -> loadTree());

    }

    /**
     * Load file.
     */
    protected File loadFile(FileChooser fileChooser, TextField source) {

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (file != null) {
            source.setText(file.getName());
        } else {
            source.setText("");
        }

        return file;

    }

    /**
     * Load newick from source.
     */
    protected void loadTree() {
        loadNewickService.restart();
    }

    /**
     * Show the newick in console.
     *
     * @param tree newick to show
     */
    protected void showTree(Tree tree) {
        System.out.println(tree);
    }
}
