package nl.tudelft.context.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
     * The menu items
     */
    public MenuItem
            loadGenomeGraph,
            zoomIn,
            zoomOut;

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
                createMenuItem("Select workspace folder",
                        new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN),
                        event -> Workspace.chooseWorkspace(mainController),
                        false),
                createMenuItem("Exit", null,
                        event -> mainController.exitProgram(),
                        false)));
    }

    /**
     * Initialize file menu.
     */
    private void initNavigateMenu() {

        menuBar.getMenus().add(createMenu("_Navigate",
                createMenuItem("Previous view",
                        new KeyCodeCombination(KeyCode.ESCAPE),
                        event -> mainController.previousView(),
                        false),
                createMenuItem("Show Phylogenetic tree",
                        new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                        event -> mainController.toggleNewick(),
                        false),
                loadGenomeGraph = createMenuItem("Load Genome graph",
                        new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN),
                        null,
                        true),
                zoomIn = createMenuItem("Zoom in on graph",
                        new KeyCodeCombination(KeyCode.UP, KeyCombination.SHORTCUT_DOWN),
                        null,
                        true),
                zoomOut = createMenuItem("Zoom out on graph",
                        new KeyCodeCombination(KeyCode.DOWN, KeyCombination.SHORTCUT_DOWN),
                        null,
                        true)));

    }

    /**
     * Initialize help menu.
     */
    private void initHelpMenu() {

        menuBar.getMenus().add(createMenu("_Help",
                createMenuItem("Shortcuts",
                        new KeyCodeCombination(KeyCode.F1),
                        event -> mainController.toggleOverlay(),
                        false)));

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
     * @param title    The title of the menuItem.
     * @param keyComb  The shortcut to this menuItem.
     * @param event    Event that the item will use.
     * @param disabled If menu item is disabled
     * @return The menuItem with the parameters attached.
     */
    private MenuItem createMenuItem(final String title,
                                    final KeyCombination keyComb,
                                    final EventHandler<ActionEvent> event,
                                    final boolean disabled) {

        final MenuItem res = new MenuItem(title);
        res.setAccelerator(keyComb);
        res.setOnAction(event);
        res.setDisable(disabled);

        return res;

    }

}
