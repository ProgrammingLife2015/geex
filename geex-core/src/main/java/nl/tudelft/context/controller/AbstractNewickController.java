package nl.tudelft.context.controller;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.DrawableNewick;
import nl.tudelft.context.drawable.NewickLabel;
import nl.tudelft.context.model.newick.Newick;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    /**
     * Scroll container.
     */
    @FXML
    ScrollPane newickScroller;

    /**
     * Container for search bar.
     */
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
     * Create a list of newickLabels based on a DrawableNewick.
     *
     * @param subNewick DrawableNewick containing vertices.
     * @return A list of NewickLabels.
     */
    protected List<Label> createNewickLabels(final DrawableNewick subNewick) {
        return subNewick.vertexSet().stream()
                .map(NewickLabel::new)
                .collect(Collectors.toList());
    }

    /**
     * Create a list of DrawableEdges based on a DrawableNewick.
     * @param subNewick DrawableNewick containing edges.
     * @return A list of DrawableEdges.
     */
    protected List<DrawableEdge> createDrawableEdges(final DrawableNewick subNewick) {
        return subNewick.edgeSet().stream()
                .map(edge -> new DrawableEdge(subNewick, edge))
                .collect(Collectors.toList());
    }

    /**
     * Show the phylogenetic tree.
     *
     * @param newick newick to show
     */
    abstract void showTree(final Newick newick);

}
