package nl.tudelft.context.controller;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import nl.tudelft.context.model.newick.Newick;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik
 * @version 1.0
 * @since 5-6-2015
 */
public abstract class AbstractNewickController extends ViewController<AnchorPane> {

    /**
     * ProgressIndicator to show when the tree is loading.
     */
    @FXML
    ProgressIndicator progressIndicator;

    /**
     * The container of the Newick tree.
     */
    @FXML
    Group newickContainer;

    @FXML
    ScrollPane newickScroller;

    @FXML
    HBox search;

    /**
     * Property with Newick tree.
     */
    ReadOnlyObjectProperty<Newick> newickIn;

    /**
     * Create a default Newick controller.
     *
     * @param newickIn Newick object from the workspace, might not be loaded.
     */
    public AbstractNewickController(final ReadOnlyObjectProperty<Newick> newickIn) {

        super(new AnchorPane());
        this.newickIn = newickIn;

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        SimpleObjectProperty<Newick> newickObjectProperty = new SimpleObjectProperty<>();

        newickObjectProperty.addListener((observable, oldValue, newValue) -> {
            showTree(newValue);
        });

        newickObjectProperty.bind(newickIn);

        progressIndicator.visibleProperty().bind(newickObjectProperty.isNull());
    }

    /**
     * Show the phylogenetic tree.
     *
     * @param newick newick to show
     */
    abstract void showTree(final Newick newick);

}
