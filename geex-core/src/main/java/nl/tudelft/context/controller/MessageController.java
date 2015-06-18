package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.context.logger.Log;
import nl.tudelft.context.logger.Logger;
import nl.tudelft.context.logger.message.Message;
import nl.tudelft.context.logger.message.MessageType;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Jim Hommes
 * @author Gerben Oolbekkink
 * @version 2.0
 * @since 19-05-2015
 */
public class MessageController extends AbstractController<VBox> implements Logger {
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
     * The function used to display a message and remove the previous one.
     *
     * @param msg  Message to display.
     * @param messageType Type of this message.
     */
    @Override
    public final void log(final String  msg, final MessageType messageType) {
        root.getStyleClass().removeAll(MessageType.types());
        root.getStyleClass().add(messageType.toString());
        message.setText(msg);
    }

    @Override
    public final MessageType getLevel() {
        return MessageType.INFO;
    }

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        Log.instance().addLogger(this);
    }
}
