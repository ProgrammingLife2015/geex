package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import nl.tudelft.context.workspace.Database;
import nl.tudelft.context.workspace.Workspace;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 18-5-2015
 */
public class WelcomeController extends ViewController<GridPane> {
    /**
     * Number of previous workspaces.
     */
    private static final int NO_OF_PREVIOUS_WORKSPACES = 9;
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
     * The main controller of the application.
     */
    private MainController mainController;

    /**
     * Select recent workspaces menu.
     */
    private Menu selectRecentWorkspace;

    /**
     * Create a WelcomeController.
     *
     * @param mainController        The MainController of the application
     * @param welcomeMenuItem       Menu item for selecting a workspace
     * @param selectRecentWorkspace Menu item for selecting a recent workspace
     */
    public WelcomeController(final MainController mainController,
                             final MenuItem welcomeMenuItem, final Menu selectRecentWorkspace) {
        super(new GridPane());
        this.mainController = mainController;
        this.selectRecentWorkspace = selectRecentWorkspace;

        welcomeMenuItem.setOnAction(event -> selectWorkspace(mainController.getRoot().getScene().getWindow()));

        loadFXML("/application/welcome.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        reloadListView();
        load.setOnMouseClicked(event -> selectWorkspace(mainController.getRoot().getScene().getWindow()));
    }

    /**
     * Reload the listView with previous workspaces.
     */
    private void reloadListView() {
        try {
            final List<String[]> workspaces = Database.instance()
                    .getList("workspace", new String[]{"location", "name"}, NO_OF_PREVIOUS_WORKSPACES);

            refreshRecentMenu(workspaces);

            previous.getItems().setAll(workspaces.stream()
                    .map(row -> new Label(row[1])).collect(toList()));

            previous.setOnMouseClicked(event -> previous.getSelectionModel().getSelectedIndices().stream()
                    .forEach(x -> loadWorkspace(new File(workspaces.get(x)[0]))));

            if (previous.getItems().size() == 0) {
                previous.setDisable(true);
            }
        } catch (SqlJetException e) {
            mainController.displayMessage(MessageController.FAIL_LOAD_PREVIOUS);
            // Continue, this doesn't break the software.
        }
    }

    /**
     * Refresh recent menu.
     *
     * @param workspaces Workspaces to put in the menu
     */
    private void refreshRecentMenu(final List<String[]> workspaces) {

        List<MenuItem> items = workspaces.stream()
                .map(workspace -> {
                    MenuItem menuItem = new MenuItem(workspace[1]);
                    menuItem.setOnAction(x -> loadWorkspace(new File(workspace[0])));
                    return menuItem;
                })
                .collect(toList());

        selectRecentWorkspace.getItems().setAll(items);
        selectRecentWorkspace.setDisable(items.size() == 0);

    }

    /**
     * Use a directoryChooser to select a new workspace.
     *
     * @param window Window for the directoryChooser
     */
    private void selectWorkspace(final Window window) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Workspace Folder");
        File workspaceDirectory = directoryChooser.showDialog(window);

        loadWorkspace(workspaceDirectory);
    }

    /**
     * Load the workspace from the directory.
     *
     * @param directory Directory to set as workspace.
     */
    private void loadWorkspace(final File directory) {
        try {
            Workspace workspace = new Workspace(directory);
            workspace.load();
            workspace.save();

            mainController.setWorkspace(workspace);
            mainController.setBaseView(new NewickController(mainController, workspace.getNewick()));
            mainController.displayMessage(MessageController.SUCCESS_LOAD_WORKSPACE);
        } catch (FileNotFoundException | SqlJetException ex) {
            mainController.displayMessage(MessageController.FAIL_LOAD_WORKSPACE);
            try {
                Database.instance().remove("workspace", directory.getAbsolutePath());
            } catch (SqlJetException | NullPointerException ignored) {
                mainController.displayMessage(MessageController.FAIL_LOAD_RECENTWORKSPACE);
            }
        }
        reloadListView();
    }

    @Override
    public final String getBreadcrumbName() {
        return "Welcome";
    }

}
