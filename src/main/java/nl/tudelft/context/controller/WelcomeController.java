package nl.tudelft.context.controller;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 18-5-2015
 */
public class WelcomeController extends ViewController<GridPane> {
    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     */
    public WelcomeController() {
        super(new GridPane());
        loadFXML("/application/welcome.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

    @Override
    public final String getBreadcrumbName() {
        return "Welcome";
    }
}
