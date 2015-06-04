package nl.tudelft.context.controller.overlay;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import nl.tudelft.context.controller.DefaultController;
import nl.tudelft.context.controller.MainController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 21-5-2015
 */
public class OverlayController extends DefaultController<StackPane> {

    /**
     * Create an overlay controller.
     *
     * @param mainController Main controller to get the menu
     * @param stackPane      FXML stack pane
     */
    public OverlayController(final MainController mainController, final StackPane stackPane) {

        super(stackPane);
        setVisibility(false);

        MenuItem toggleOverlay = mainController.getMenuController().getToggleOverlay();
        toggleOverlay.setOnAction(event -> setVisibility(!getVisibilityProperty().getValue()));

        loadFXML("/application/overlay.fxml");

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }
}
