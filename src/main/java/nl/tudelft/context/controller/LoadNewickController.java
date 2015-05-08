package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import nl.tudelft.context.service.LoadNewickService;

import java.io.File;
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

    protected MainController mainController;
    protected LoadNewickService loadNewickService;

    protected File
            nwkFile;

    /**
     * Init a controller at load_newick.fxml.
     */
    public LoadNewickController(MainController mainController) {

        super(new GridPane());

        this.mainController = mainController;

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

        FileChooser nwkFileChooser = new FileChooser();
        nwkFileChooser.setTitle("Open Newick file");
        loadNewick.setOnAction(event -> nwkFile = loadFile(nwkFileChooser, nwkSource));

        load.setOnAction(event -> {
            NewickController newickController = new NewickController(mainController, new LoadNewickService(nwkFile));
            mainController.setBaseView(newickController.getRoot());
        });

    }

}
