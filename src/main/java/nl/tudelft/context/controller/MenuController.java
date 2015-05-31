package nl.tudelft.context.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
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
     * The menu item for loading the genome graph.
     */
    MenuItem loadGenomeGraph;

    /**
     * FXML menu bar.
     */
    MenuBar menuBar;

    /**
     * Create a new menu.
     *
     * @param mainController The MainController of the application.
     * @param menuBar        The menubar this Menu should hook onto.
     */
    public MenuController(final MainController mainController, final MenuBar menuBar) {

        this.mainController = mainController;
        this.menuBar = menuBar;

        initFileMenu();
        initNavigateMenu();
        initHelpMenu();

    }

    /**
     * Initialize file menu.
     */
    private void initFileMenu() {

        menuBar.getMenus().add(createMenu("_File",
                createWorkspaceLoader("Select workspace folder",
                        new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN)),
                createMenuItem("Exit", null,
                        event -> mainController.exitProgram())));
    }

    /**
     * Initialize file menu.
     */
    private void initNavigateMenu() {

        menuBar.getMenus().add(createMenu("_Navigate",
                createMenuItem("Previous view",
                        new KeyCodeCombination(KeyCode.ESCAPE),
                        event -> mainController.previousView()),
                createMenuItem("Show Phylogenetic tree",
                        new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                        event -> mainController.toggleNewick()),
                createGenomeGraphLoader("Load Genome graph",
                        new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN))));

    }

    /**
     * Initialize help menu.
     */
    private void initHelpMenu() {

        menuBar.getMenus().add(createMenu("_Help",
                createMenuItem("Shortcuts",
                        new KeyCodeCombination(KeyCode.F1),
                        event -> mainController.toggleOverlay())));

    }

    /**
     * The function that will create a menu with an undefined number of menuItems.
     *
     * @param title     Title of the menu.
     * @param menuItems The menu items that will be added to this menu.
     * @return Menu with the menuItems attached.
     */
    private Menu createMenu(final String title, final MenuItem... menuItems) {

        final Menu menu = new Menu(title);
        menu.getItems().addAll(menuItems);
        return menu;

    }

    /**
     * The function that returns a menuItem with a title, shortcut and event attached.
     *
     * @param title   The title of the menuItem.
     * @param keyComb The shortcut to this menuItem.
     * @param event   Event that the item will use.
     * @return The menuItem with the parameters attached.
     */
    private MenuItem createMenuItem(final String title,
                                    final KeyCombination keyComb,
                                    final EventHandler<ActionEvent> event) {

        final MenuItem res = new MenuItem(title);
        res.setAccelerator(keyComb);
        res.setOnAction(event);

        return res;

    }

    /**
     * Function used to create a workspace loader.
     *
     * @param title   Title of the menuItem.
     * @param keyComb The KeyCodeCombination to be attached.
     * @return Returns the menuItem with the workspace loader attached.
     */
    private MenuItem createWorkspaceLoader(final String title, final KeyCodeCombination keyComb) {

        final MenuItem res = new MenuItem(title);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Workspace Folder");
        res.setOnAction(event -> Workspace.chooseWorkspace(mainController));
        res.setAccelerator(keyComb);

        return res;

    }

    /**
     * The function used to create the menuItem with the genomegraphloader.
     *
     * @param title   The title of the menuItem.
     * @param keyComb The keycombination to be attached.
     * @return The menuitem with all params.
     */
    private MenuItem createGenomeGraphLoader(final String title, final KeyCombination keyComb) {
        loadGenomeGraph = new MenuItem(title);
        loadGenomeGraph.setAccelerator(keyComb);
        loadGenomeGraph.setDisable(true);
        return loadGenomeGraph;
    }

    /**
     * Gets the menu item that is responsible for loading the genome graph.
     *
     * @return the menu item for loading the genome graph
     */
    public MenuItem getLoadGenomeGraph() {
        return loadGenomeGraph;
    }
}
