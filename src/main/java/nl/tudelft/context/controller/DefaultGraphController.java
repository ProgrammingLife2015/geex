package nl.tudelft.context.controller;

import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 2-6-2015
 */
public class DefaultGraphController extends  ViewController<AnchorPane> {

    /**
     * Reference to the MainController of the app.
     */
    MainController mainController;

    public DefaultGraphController(MainController mainController) {

        super(new AnchorPane());

        this.mainController = mainController;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public String getBreadcrumbName() {
        return "Sub graph";
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }
}
