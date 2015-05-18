package nl.tudelft.context.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Created by Jim on 5/18/2015.
 */
public class FooterController extends VBox {

    MainController mainController;
    HBox hbox;

    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     *
     */
    public FooterController(MainController mainController) {

        this.mainController = mainController;

        hbox = new HBox();

        hbox.getChildren().addAll(new Text("Footer Item"));
        this.getChildren().addAll(new Separator(), hbox);
    }


}
