package nl.tudelft.context.controller;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Label;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.NewickLabel;
import nl.tudelft.context.model.newick.Newick;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 5-6-2015
 */
public class SelectNewickController extends DefaultNewickController {

    /**
     * Graph controller to update the selection.
     */
    private final GraphController graphController;

    /**
     * Create s select Newick controller.
     *
     * @param newickIn Newick object from the workspace, might not be loaded.
     */
    public SelectNewickController(final GraphController graphController, final ReadOnlyObjectProperty<Newick> newickIn) {

        super(newickIn);
        this.graphController = graphController;

        loadFXML("/application/newick.fxml");

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        root.setId("newick-select");
    }

    @Override
    void showTree(Newick newick) {

        newick.getRoot().getSelectionProperty().addListener(event -> {
                graphController.updateSelectedSources(newick.getRoot().getSources());
        });

        // Bind edges
        List<DrawableEdge> edgeList = newick.edgeSet().stream()
                .map(edge -> new DrawableEdge(newick, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<Label> nodeList = newick.vertexSet().stream()
                .map(NewickLabel::new)
                .collect(Collectors.toList());

        this.newick.getChildren().addAll(edgeList);
        this.newick.getChildren().addAll(nodeList);

    }

    @Override
    public String getBreadcrumbName() {
        return "Select strains (" + 0 + ")";
    }

}
