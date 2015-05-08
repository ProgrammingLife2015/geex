package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class MainController extends DefaultController<BorderPane> implements Initializable {

    @FXML
    protected VBox control;

    protected Stack<Node> viewList = new Stack<>();

    /**
     * Init a controller at main.fxml.
     */
    public MainController() {

        super(new BorderPane());

        loadFXML("/application/main.fxml");

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        control.getChildren().add(new LoadGraphController(this).getRoot());
        control.getChildren().add(new LoadNewickController().getRoot());

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                previousView();
        });

        //MenuBar
        MenuBar menubar = new MenuBar();

        //The options in the menu
        final Menu menuFile = new Menu("File");
        final Menu menuHelp = new Menu("Help");

        //The items within the options
        MenuItem load = new MenuItem("Load...");

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> exitProgram());

        //Set all the items and menus
        menuFile.getItems().addAll(load, exit);
        menubar.getMenus().addAll(menuFile, menuHelp);

        //Set the menubar in place
        root.setTop(menubar);

    }

    /**
     * Set a new base view (clear the stack).
     *
     * @param node javaFX element
     */
    public void setBaseView(Node node) {

        viewList.clear();
        setView(node);

    }

    /**
     * Set a new main view and push it on the view stack.
     *
     * @param node javaFX element
     */
    public void setView(Node node) {

        viewList.add(node);
        root.setCenter(node);

    }

    /**
     * Set the previous view as view.
     */
    public void previousView() {

        if(viewList.size() > 1) {
            viewList.pop();
            root.setCenter(viewList.peek());
        }

    }

    /**
     * Exits the program.
     */
    private void exitProgram() {
        System.exit(0);
    }

}
