package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.controller.message.Message;
import nl.tudelft.context.controller.message.MessageType;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Jim Hommes
 * @version 1.0
 * @since 19-05-2015
 */
public class MessageController extends AbstractController<VBox> {


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
    public MessageController() {
        super(new VBox());

        loadFXML("/application/footer.fxml");
    }

    /**
     * Show a standard Info message.
     *
     * @param msg Message to display
     */
    public final void displayMessage(final Message msg) {
        displayMessage(msg, MessageType.INFO);
    }

    /**
     * The function used to display a message and remove the previous one.
     *
     * @param msg  Message to display.
     * @param type Type of the message
     */
    public final void displayMessage(final Message msg, final MessageType type) {
        root.getStyleClass().removeAll(MessageType.types());
        root.getStyleClass().add(type.getType());
        message.setText(msg.getMessage());
    }

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        displayMessage(Message.MESSAGE_READY);
    }
}
