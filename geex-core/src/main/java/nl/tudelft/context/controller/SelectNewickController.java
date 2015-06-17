package nl.tudelft.context.controller;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import nl.tudelft.context.controller.search.NewickSearchController;
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
public class SelectNewickController extends AbstractNewickController {

    /**
     * Main controller to set views.
     */
    private final MainController mainController;

    /**
     * Graph controller to update the selection.
     */
    private final GraphController graphController;

    /**
     * Create s select Newick controller.
     *
     * @param mainController  Main controller to set views
     * @param newickIn        Newick object from the workspace, might not be loaded
     * @param graphController Controller where to update the sources
     */
    public SelectNewickController(final MainController mainController,
                                  final GraphController graphController,
                                  final ReadOnlyObjectProperty<Newick> newickIn) {

        super(newickIn);
        this.mainController = mainController;
        this.graphController = graphController;
        this.showInBreadcrumb = false;

        loadFXML("/application/newick.fxml");

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        root.setId("newickSelectContainer");

        MenuItem toggleSelect = mainController.getMenuController().getToggleSelect();
        activeProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                toggleSelect.setOnAction(event -> mainController.previousView());
                toggleSelect.disableProperty().bind(activeProperty.not());
            }
        });
    }

    @Override
    void showTree(final Newick newick) {
        DrawableNewick subNewick = new DrawableNewick(newick.getSelectedSubGraph());
        subNewick.getNewick().getRoot().getSourcesProperty().addListener(event -> graphController
                        .updateSelectedSources(subNewick.getNewick().getRoot().getSources()));

        // Bind edges
        List<DrawableEdge> edgeList = subNewick.edgeSet().stream()
                .map(edge -> new DrawableEdge(subNewick, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<Label> nodeList = subNewick.vertexSet().stream()
                .map(NewickLabel::new)
                .collect(Collectors.toList());

        newickContainer.getChildren().addAll(edgeList);
        newickContainer.getChildren().addAll(nodeList);

        new NewickSearchController(nodeList, search, newickScroller);

    }

    @Override
    public String getBreadcrumbName() {
        throw new UnsupportedOperationException();
    }

}
