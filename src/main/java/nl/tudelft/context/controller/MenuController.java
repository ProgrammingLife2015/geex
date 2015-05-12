package nl.tudelft.context.controller;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import nl.tudelft.context.workspace.Workspace;

import java.io.File;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public final class MenuController extends MenuBar {

    MainController mainController;

    /**
     * Create a new menu.
     */
    public MenuController(final MainController mainController) {

        this.mainController = mainController;

        initFileMenu();
        initHelpMenu();

    }

    /**
     * Set up file menu.
     */
    public void initFileMenu() {

        final Menu fileMenu = new Menu("File");

        MenuItem load = new MenuItem("Load Workspace...");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Workspace Folder");
        load.setOnAction(event -> {
            Window window = mainController.getRoot().getScene().getWindow();
            File workspaceDirectory = directoryChooser.showDialog(window);
            Workspace workspace = new Workspace(workspaceDirectory);
            workspace.walk();
            workspace.load();
            mainController.setWorkspace(workspace);
            initWorkspaceMenu();
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
        getMenus().add(menuHelp);

    }

    private void initWorkspaceMenu() {
        final Menu menuWorkspace = new Menu("Workspace");
        MenuItem loadGraph = new MenuItem("Load first graph");
        loadGraph.setOnAction(event -> mainController.setBaseView(new GraphController(mainController, mainController.getWorkspace().getGraphList().get(0)).getRoot()));
        MenuItem loadNewick = new MenuItem("Load first newick");
        loadNewick.setOnAction(event -> mainController.setBaseView(new NewickController(mainController.getWorkspace().getNewickList().get(0)).getRoot()));
        menuWorkspace.getItems().addAll(loadGraph, loadNewick);
        getMenus().add(menuWorkspace);
    }

}
