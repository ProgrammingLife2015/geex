package nl.tudelft.context;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import nl.tudelft.context.graph.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class SequenceController extends Label implements Initializable {

    Node node;

    /**
     * Init a controller at sequence.fxml.
     *
     * @param node node to show in view
     * @throws RuntimeException
     */
    public SequenceController(Node node) {

        this.node = node;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/sequence.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.setText("id: " + node.getId());

    }

}
