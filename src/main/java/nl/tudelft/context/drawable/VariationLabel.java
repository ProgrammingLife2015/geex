package nl.tudelft.context.drawable;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import nl.tudelft.context.controller.DefaultGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.Node;

/**
 * @author Jim
 * @since 6/4/2015
 */
public class VariationLabel extends InfoLabel {

    /**
     * Constructor for the InfoLabel.
     *
     * @param mainController  MainController indicating the controller
     * @param graphController GraphController to place the next view on
     * @param drawableNode    Node indicating drawable
     * @param node            Node indicating the node
     */
    public VariationLabel(MainController mainController, DefaultGraphController graphController, DrawableNode drawableNode, DefaultNode node) {
        super(mainController, graphController, drawableNode, (Node) node);

//        getChildren().clear();
//        getChildren().add(initMainLabel());
//        getChildren().get(0).getStyleClass().remove(0);
//        getChildren().get(0).getStyleClass().add("variation-label");
    }

    /**
     * Initialize the Label without the BaseLabels shown.
     *
     * @return Initialized Upper label
     */
    private Label initMainLabel() {

        final Label label = new Label(Integer.toString(node.getId()));
        label.setCache(true);
        label.getStyleClass().add("variation-label");

        final Tooltip percentages = new Tooltip(node.getBaseCounter().toString());
        label.setTooltip(percentages);

        return label;

    }

    @Override
    public final void init() {

        getChildren().add(initMainLabel());

    }



}
