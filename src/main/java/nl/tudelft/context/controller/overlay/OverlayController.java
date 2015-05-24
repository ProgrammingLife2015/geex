package nl.tudelft.context.controller.overlay;

import javafx.scene.layout.GridPane;
import nl.tudelft.context.controller.DefaultController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 21-5-2015
 */
public class OverlayController extends DefaultController<GridPane> {
    /**
     * Create an overlay controller.
     */
    public OverlayController() {
        super(new GridPane());

        setVisibility(false);

        loadFXML("/application/overlay.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }
}
