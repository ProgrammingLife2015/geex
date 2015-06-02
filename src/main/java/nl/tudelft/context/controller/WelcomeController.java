package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import nl.tudelft.context.workspace.Database;
import nl.tudelft.context.workspace.Workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;

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
     * ListView containing previous workspace locations.
     */
    @FXML
    ListView<Label> previous;
    /**
     * The maincontroller of the application.
     */
    private MainController mainController;

    /**
     * Create a WelcomeController.
     *
     * @param mainController The MainController of the application
     */
    public WelcomeController(final MainController mainController) {
        super(new GridPane());
        this.mainController = mainController;
        loadFXML("/application/welcome.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        previous.getItems().setAll(Database.instance().getList("workspace", new String[]{"name", "location"}, 5).stream().map(row -> {
            Label label = new Label(row[0]);
            System.out.println(row[0]);
            label.setOnMouseClicked(event -> {
                try {
                    Workspace workspace = new Workspace(new File(row[1]));
                    workspace.load();

                    mainController.setWorkspace(workspace);
                    mainController.setBaseView(new NewickController(mainController,
                            mainController.getMenuController().getLoadGenomeGraph(), workspace.getNewick()));
                    mainController.displayMessage(MessageController.SUCCESS_LOAD_WORKSPACE);
                } catch (FileNotFoundException ex) {
                    mainController.displayMessage(MessageController.FAIL_LOAD_WORKSPACE);
                }
            });
            return label;
        }).collect(toList()));

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
