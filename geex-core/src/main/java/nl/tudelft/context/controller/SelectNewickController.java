package nl.tudelft.context.controller;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Label;
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
     * Graph controller to update the selection.
     */
    private final GraphController graphController;

    /**
     * Create s select Newick controller.
     *
     * @param newickIn        Newick object from the workspace, might not be loaded
     * @param graphController Controller where to update the sources
     */
    public SelectNewickController(final GraphController graphController,
                                  final ReadOnlyObjectProperty<Newick> newickIn) {

        super(newickIn);
        this.graphController = graphController;

        loadFXML("/application/newick.fxml");

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        root.setId("newickSelectContainer");
    }

    @Override
    void showTree(final Newick newick) {
        try {
            DrawableNewick subNewick = new DrawableNewick(newick.getSelectedSubGraph());
            subNewick.getNewick().getRoot().getSelectionProperty().addListener(event -> {
                graphController.updateSelectedSources(subNewick.getNewick().getRoot().getSources());
            });

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBreadcrumbName() {
        return "Select strains (" + 0 + ")";
    }

}
