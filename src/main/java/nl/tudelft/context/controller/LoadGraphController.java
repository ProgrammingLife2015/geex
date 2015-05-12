package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import nl.tudelft.context.service.LoadGraphService;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
public final class LoadGraphController extends DefaultController<GridPane> {

    /**
     * Buttons for showing filechoosers and loading the graph.
     */
    @FXML
    Button
            loadNodes,
            loadEdges,
            load;

    /**
     * Textfields pointing to the source files.
     */
    @FXML
    TextField
            nodeSource,
            edgeSource;

    /**
     * Reference to the MainController of the application.
     */
    MainController mainController;

    /**
     * Files containing graph information.
     */
    File
            nodeFile,
            edgeFile;

    /**
     * Init a controller at load_graph.fxml.
     *
     * @param mainController    main controller to set view
     */
    public LoadGraphController(final MainController mainController) {

        super(new GridPane());

        this.mainController = mainController;

        loadFXML("/application/load_graph.fxml");

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

        FileChooser nodeFileChooser = new FileChooser();
        nodeFileChooser.setTitle("Open node file");
        loadNodes.setOnAction(event -> nodeFile = loadFile(nodeFileChooser, nodeSource));

        FileChooser edgeFileChooser = new FileChooser();
        edgeFileChooser.setTitle("Open edge file");
        loadEdges.setOnAction(event -> edgeFile = loadFile(edgeFileChooser, edgeSource));

        load.setOnAction(event -> {
            GraphController graphController = new GraphController(mainController, new LoadGraphService(nodeFile, edgeFile));
            mainController.setBaseView(graphController.getRoot());
        });

    }

}
