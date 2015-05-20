package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Jim Hommes
 * @version 1.0
 * @since 19-05-2015
 */
public class FooterController extends DefaultController<VBox> {

    /**
     * The text that is shown.
     */
    @FXML
    Text message;

    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     * A reference to the main controller.
     */
    public FooterController() {

        super(new VBox());

        loadFXML("/application/footer.fxml");
    }


    /**
     * The function used to display a message and remove the previous one.
     * @param text The string to display.
     */
    public void displayMessage(final String text) {
        message.setText(text);
    }


    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        displayMessage("Ready");
    }
}
