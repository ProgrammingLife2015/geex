package nl.tudelft.context.controller;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import nl.tudelft.context.controller.overlay.ShortcutController;
import nl.tudelft.context.workspace.Workspace;

import java.io.File;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public final class MenuController extends MenuBar {
    /**
     * Reference to the MainController of the application.
     */
    MainController mainController;

    /**
     * Create a new menu.
     *
     * @param mainController The MainController of the application.
     */
    public MenuController(final MainController mainController) {

        this.mainController = mainController;
        this.setUseSystemMenuBar(true);

        initFileMenu();
        initHelpMenu();

    }

    /**
     * Set up file menu.
     */
    public void initFileMenu() {

        final Menu fileMenu = new Menu("File");

        MenuItem load = new MenuItem("Load Workspace...");
        load.setAccelerator(KeyCombination.keyCombination("ctrl+o"));
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Workspace Folder");
        load.setOnAction(event -> {
            Window window = mainController.getRoot().getScene().getWindow();
            File workspaceDirectory = directoryChooser.showDialog(window);
            if (workspaceDirectory != null) {
                Workspace workspace = new Workspace(workspaceDirectory);
                if (workspace.load()) {
                    mainController.displayMessage(MessageController.SUCCESS_LOAD_WORKSPACE);
                } else {
                    mainController.displayMessage(MessageController.FAIL_LOAD_WORKSPACE);
                }
                mainController.setWorkspace(workspace);
                mainController.setBaseView(new NewickController(mainController));
            }
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> mainController.exitProgram());

        fileMenu.getItems().addAll(load, exit);
        getMenus().add(fileMenu);

    }

    /**
     * Set up help menu.
     */
    public void initHelpMenu() {

        final Menu menuHelp = new Menu("Help");

        MenuItem shortcuts = new MenuItem("Shortcuts");
        shortcuts.setAccelerator(KeyCombination.keyCombination("f1"));
        shortcuts.setOnAction(event -> {
            mainController.setOverlay(new ShortcutController());
        });

        menuHelp.getItems().addAll(shortcuts);

        getMenus().add(menuHelp);

    }

}
