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
     * Menu bar used in this class.
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
        menuBar.setUseSystemMenuBar(true);

        menuBar.getMenus().add(createMenu("_File",
                createWorkspaceLoader("Select Workspace Folder"),
                createMenuItem("Exit",
                        event -> mainController.exitProgram())));
        menuBar.getMenus().add(createMenu("_Navigate",
                createMenuItem("Previous",
                        new KeyCodeCombination(KeyCode.ESCAPE),
                        event -> mainController.previousView()),
                createMenuItem("Show Phylogenetic tree",
                        new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN),
                        event -> mainController.toggleNewick())));
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
     * The function that returns a menuItem without a shortcut.
     *
     * @param title Title of the menuItem.
     * @param event Event that the item will use.
     * @return The menu item with the title and the event attached.
     */
    private MenuItem createMenuItem(final String title, final EventHandler<ActionEvent> event) {

        final MenuItem res = new MenuItem(title);
        res.setOnAction(event);

        return res;

    }

    /**
     * Function used to create a workspace loader.
     *
     * @param title Title of the menuItem.
     * @return Returns the menuItem with the workspace loader attached.
     */
    private MenuItem createWorkspaceLoader(final String title) {

        final MenuItem res = new MenuItem(title);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Workspace Folder");
        res.setOnAction(event -> Workspace.chooseWorkspace(mainController));

        return res;

    }
}
