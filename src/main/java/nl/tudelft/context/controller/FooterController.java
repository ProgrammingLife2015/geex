package nl.tudelft.context.controller;

import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;


/**
 * @author Jim Hommes
 * @version 1.0
 * @date 19-05-2015
 *
 */
public class FooterController extends VBox {

    /**
     * A reference to the main controller.
     */
    MainController mainController;

    /**
     * The Hbox that will display the text.
     */
    HBox hbox;

    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     * @param mainController
     * A reference to the main controller.
     */
    public FooterController(MainController mainController) {

        super(new BorderPane());
        this.mainController = mainController;

        hbox = new HBox();
        this.getChildren().addAll(new Separator(), hbox);
        hbox.setPadding(new Insets(2));

        displayMessage("Ready");
    }


    /**
     * The function used to display a message and remove the previous one.
     * @param text The string to display.
     */
    public void displayMessage(String text) {
        if(hbox.getChildren().size() > 0)
            hbox.getChildren().remove(0);
        Text ntext = new Text(text);
        ntext.setFill(Paint.valueOf("#868686"));
        hbox.getChildren().addAll(ntext);
    }


}
