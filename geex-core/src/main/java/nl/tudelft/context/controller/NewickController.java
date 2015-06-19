package nl.tudelft.context.controller;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import nl.tudelft.context.logger.Log;
import nl.tudelft.context.logger.message.Message;
import nl.tudelft.context.controller.search.NewickSearchController;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.DrawableNewick;
import nl.tudelft.context.drawable.NewickLabel;
import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.selection.None;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 8-5-2015
 */
public final class NewickController extends AbstractNewickController {

    /**
     * The main controller used to set views.
     */
    MainController mainController;

    /**
     * The current selection of the tree.
     */
    Set<String> selection;

    /**
     * The GraphController that belongs to the current selection.
     */
    GraphController graphController;

    /**
     * Init a controller at newickContainer.fxml.
     *
     * @param mainController MainController for the application
     * @param newickIn       Newick object from the workspace, might not be loaded.
     */
    public NewickController(final MainController mainController,
                            final ReadOnlyObjectProperty<Newick> newickIn) {

        super(newickIn);
        this.mainController = mainController;

        loadFXML("/application/newick.fxml");
    }

    @Override
    protected void showTree(final Newick newick) {
        DrawableNewick drawableNewick = new DrawableNewick(newick);

        // Bind edges
        List<DrawableEdge> edgeList = drawableNewick.edgeSet().stream()
                .map(edge -> new DrawableEdge(drawableNewick, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<Label> nodeList = drawableNewick.vertexSet().stream()
                .map(NewickLabel::new)
                .collect(Collectors.toList());

        newickContainer.getChildren().addAll(edgeList);
        newickContainer.getChildren().addAll(nodeList);

        MenuItem loadGenomeGraph = mainController.getMenuController().getLoadGenomeGraph();
        loadGenomeGraph.setOnAction(event -> loadGraph(newick));
        loadGenomeGraph.disableProperty().bind(
                newick.getRoot().getSelectionProperty().isEqualTo(new None()).or(activeProperty.not())
        );

        Log.info(Message.SUCCESS_LOAD_TREE);

        new NewickSearchController(nodeList, search, newickScroller);
    }

    /**
     * Loads the graph of the selected strands.
     *
     * @param newick the tree with the nodes to show.
     */
    protected void loadGraph(final Newick newick) {
        Set<String> newSelection = newick.getRoot().getSources();
        if (!newSelection.isEmpty()) {
            if (!newSelection.equals(selection)) {
                graphController = new GraphController(mainController,
                        newSelection,
                        mainController.getWorkspace().getGraph(),
                        mainController.getWorkspace().getAnnotation(),
                        mainController.getWorkspace().getResistance());
                mainController.setView(this, graphController);
            } else {
                mainController.toView(graphController);
            }
            selection = newSelection;
        }
    }

    @Override
    public String getBreadcrumbName() {
        return "Phylogenetic tree";
    }

}
