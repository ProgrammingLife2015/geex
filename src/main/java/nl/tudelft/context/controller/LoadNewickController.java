package nl.tudelft.context.controller;

import javafx.fxml.FXML;
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
public final class LoadNewickController extends DefaultController<GridPane> {

    @FXML
    Button
            loadNewick,
            load;

    @FXML
    TextField
            nwkSource;

    MainController mainController;

    File
            nwkFile;

    /**
     * Init a controller at load_newick.fxml.
     */
    public LoadNewickController(final MainController mainController) {

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
    public void initialize(final URL location, final ResourceBundle resources) {

        FileChooser nwkFileChooser = new FileChooser();
        nwkFileChooser.setTitle("Open Newick file");
        loadNewick.setOnAction(event -> nwkFile = loadFile(nwkFileChooser, nwkSource));

        load.setOnAction(event -> {
            NewickController newickController = new NewickController(new LoadNewickService(nwkFile));
            mainController.setBaseView(newickController.getRoot());
        });

    }

}
