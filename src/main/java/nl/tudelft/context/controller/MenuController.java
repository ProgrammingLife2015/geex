package nl.tudelft.context.controller;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import nl.tudelft.context.controller.overlay.OverlayController;
import nl.tudelft.context.workspace.Workspace;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public final class MenuController {
    /**
     * Reference to the MainController of the application.
     */
    MainController mainController;

    /**
     * Menu bar used in this class.
     */
    MenuBar menuBar;

    /**
     * Menu items used in other classes.
     */
    MenuItem previous;

    /**
     * Create a new menu.
     *
     * @param mainController The MainController of the application.
     * @param menuBar The menubar this Menu should hook onto.
     */
    public MenuController(final MainController mainController, final MenuBar menuBar) {
        this.mainController = mainController;
        this.menuBar = menuBar;
        menuBar.setUseSystemMenuBar(true);

        initFileMenu();
        initHelpMenu();

    }

    /**
     * Set up file menu.
     */
    public void initFileMenu() {

        final Menu fileMenu = new Menu("File");

        MenuItem load = new MenuItem("Load Workspace...");
        load.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Workspace Folder");
        load.setOnAction(event -> Workspace.chooseWorkspace(mainController));

        previous = new MenuItem("Previous");
        previous.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
        previous.setOnAction(event -> mainController.previousView());

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> mainController.exitProgram());

        fileMenu.getItems().addAll(load, previous, exit);
        menuBar.getMenus().add(fileMenu);

    }

    /**
     * Set up help menu.
     */
    public void initHelpMenu() {

        final Menu menuHelp = new Menu("Help");

        MenuItem shortcuts = new MenuItem("Shortcuts");
        shortcuts.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        shortcuts.setOnAction(event -> mainController.toggleOverlay(new OverlayController()));

        menuHelp.getItems().addAll(shortcuts);

        menuBar.getMenus().add(menuHelp);

    }
}
