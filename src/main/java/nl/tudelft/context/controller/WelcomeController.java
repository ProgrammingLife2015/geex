package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import nl.tudelft.context.workspace.Workspace;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 18-5-2015
 */
public class WelcomeController extends ViewController<GridPane> {
    /**
     * The load button.
     */
    @FXML
    Button load;
    /**
     * The maincontroller of the application.
     */
    private MainController mainController;

    /**
     * Create a WelcomeController.
     * @param mainController The MainController of the application
     */
    public WelcomeController(final MainController mainController) {
        super(new GridPane());
        this.mainController = mainController;
        loadFXML("/application/welcome.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        load.setOnMouseClicked(event -> Workspace.chooseWorkspace(mainController));
    }

    @Override
    public final String getBreadcrumbName() {
        return "Welcome";
    }

    @Override
    public void activate() {
        // empty method
    }

    @Override
    public void deactivate() {
        // empty method
    }
}
