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
     * @param mainController The MainController of the application
     */
    public WelcomeController(final MainController mainController) {
        super(new GridPane());
        this.mainController = mainController;
        loadFXML("/application/welcome.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        previous.getItems().clear();
        previous.getItems().addAll(
                Database.instance().getList("workspace", new String[]{"name", "location"}, 5).stream().map(file -> {
                    Label label = new Label(file[0]);
                    System.out.println(file[0]);
                    label.setOnMouseClicked(event -> {
                        Workspace workspace = new Workspace(new File(file[1]));
                        try {
                            workspace.load();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        mainController.setWorkspace(workspace);
                        mainController.setBaseView(new NewickController(mainController,
                                mainController.getMenuController().getLoadGenomeGraph(), workspace.getNewick()));
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
