package nl.tudelft.context.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author René Vennik
 * @version 1.0
 * @since 8-5-2015
 */
public final class MenuController {

    /**
     * Reference to the MainController of the application.
     */
    MainController mainController;

    /**
     * The menu items.
     */
    private MenuItem
            loadGenomeGraph,
            toggleOverlay,
            toggleSelect,
            zoomIn,
            zoomOut,
            selectWorkspace,
            resetView;

    /**
     * The menu's.
     */
    private Menu
            selectRecentWorkspace;

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

        selectWorkspace = createMenuItem("Select workspace folder",
                new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN),
                null,
                false);

        selectRecentWorkspace = new Menu("_Select recent workspace");
        selectRecentWorkspace.setDisable(true);

        menuBar.getMenus().add(createMenu("_File",
                selectWorkspace,
                selectRecentWorkspace,
                createMenuItem("Exit", null,
                        event -> mainController.exitProgram(),
                        false)));
    }

    /**
     * Initialize file menu.
     */
    private void initNavigateMenu() {

        loadGenomeGraph = createMenuItem("Load Genome graph",
                new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN),
                null,
                true);

        zoomIn = createMenuItem("Zoom in on graph",
                new KeyCodeCombination(KeyCode.UP, KeyCombination.SHORTCUT_DOWN),
                null,
                true);

        zoomOut = createMenuItem("Zoom out on graph",
                new KeyCodeCombination(KeyCode.DOWN, KeyCombination.SHORTCUT_DOWN),
                null,
                true);

        toggleSelect = createMenuItem("Show Phylogenetic tree",
                new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                null,
                true);

        resetView = createMenuItem("Reset the view",
                new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                null,
                true);

        menuBar.getMenus().add(createMenu("_Navigate",
                createMenuItem("Previous view",
                        new KeyCodeCombination(KeyCode.ESCAPE),
                        event -> mainController.previousView(),
                        false),
                toggleSelect,
                loadGenomeGraph,
                zoomIn,
                zoomOut,
                resetView));

    }

    /**
     * Initialize help menu.
     */
    private void initHelpMenu() {

        toggleOverlay = createMenuItem("Shortcuts",
                new KeyCodeCombination(KeyCode.F1),
                null,
                false);

        menuBar.getMenus().add(createMenu("_Help",
                toggleOverlay));

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
     * The function that returns a menuItem with a title, shift and event attached.
     *
     * @param title    The title of the menuItem.
     * @param keyComb  The shift to this menuItem.
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

    /**
     * Get the menu item to select a workspace.
     *
     * @return MenuItem for the welcomeController.
     */
    public MenuItem getSelectWorkspace() {
        return selectWorkspace;
    }

    /**
     * Get the menu item to select a recent workspace.
     *
     * @return Menu for the welcomeController.
     */
    public Menu getSelectRecentWorkspace() {
        return selectRecentWorkspace;
    }

    /**
     * Get the menu item to load the genome graph.
     *
     * @return The menu item to load the genome graph
     */
    public MenuItem getLoadGenomeGraph() {
        return loadGenomeGraph;
    }

    /**
     * Get the menu item to zoom in the graph.
     *
     * @return The menu item to zoom in the graph
     */
    public MenuItem getZoomIn() {
        return zoomIn;
    }

    /**
     * Get the menu item to zoom out the graph.
     *
     * @return The menu item to zoom out the graph
     */
    public MenuItem getZoomOut() {
        return zoomOut;
    }

    /**
     * Get the menu item to toggle the overlay.
     *
     * @return The menu item to toggle the overlay
     */
    public MenuItem getToggleOverlay() {
        return toggleOverlay;
    }

    /**
     * Get the menu item to toggle the select view.
     *
     * @return The menu item to toggle the select view
     */
    public MenuItem getToggleSelect() {
        return toggleSelect;
    }

    /**
     * Get the menu item that resets the view.
     *
     * @return The menu item that sets the view.
     */
    public MenuItem getResetView() {
        return resetView;
    }

}
