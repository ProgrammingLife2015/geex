package nl.tudelft.context.controller;

import javafx.beans.property.ReadOnlyObjectProperty;
import nl.tudelft.context.model.newick.Newick;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 5-6-2015
 */
public class SelectNewickController extends DefaultNewickController {

    /**
     * Create s select Newick controller.
     *
     * @param newickIn Newick object from the workspace, might not be loaded.
     */
    public SelectNewickController(final ReadOnlyObjectProperty<Newick> newickIn) {

        super(newickIn);

        loadFXML("/application/newick.fxml");
    }

    @Override
    void showTree(Newick newick) {

    }

    @Override
    public String getBreadcrumbName() {
        return "Select strains ( " + 0 + ")";
    }

}
