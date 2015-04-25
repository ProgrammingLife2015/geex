package nl.tudelft.context;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class Controller extends ScrollPane implements Initializable {

    @FXML
    protected GridPane ruler;

    /**
     * Init a controller at main.fxml.
     *
     * @throws IOException
     */
    public Controller() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/main.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

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

        for (int i = 0; i < 100; i++) {

            final Label label = new Label(Integer.toString(i));
            ruler.add(label, i, 0);

        }

        reverseScroll();

    }

    /**
     * On vertical scroll, make it horizontal.
     */
    protected void reverseScroll() {

        this.setOnScroll(event -> {
            final double displacement = event.getDeltaY() / this.getContent().getBoundsInLocal().getWidth();
            this.setHvalue(this.getHvalue() - displacement);
        });

    }

}
