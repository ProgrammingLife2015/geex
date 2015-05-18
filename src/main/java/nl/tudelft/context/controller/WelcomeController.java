package nl.tudelft.context.controller;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gerben on 17/05/2015.
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
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public String getBreadcrumbName() {
        return "Welcome";
    }
}
