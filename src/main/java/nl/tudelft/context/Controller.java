package nl.tudelft.context;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import nl.tudelft.context.graph.Graph;

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

    protected Graph graph;

    /**
     * Init a controller at main.fxml.
     *
     * @throws IOException
     * @param graph
     */
    public Controller(Graph graph) throws IOException {

        this.graph = graph;

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

        initRuler();

        reverseScroll();

    }

    /**
     * Init ruler with reference points.
     */
    protected void initRuler() {

        int row = 0;
        for (int referencePoint : graph.getReferencePoints()) {
            final Label label = new Label(Integer.toString(referencePoint));
            ruler.add(label, row, 0);
            row++;
        }

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
