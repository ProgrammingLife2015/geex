package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public class BaseController extends DefaultController<StackPane> {

    /**
     * JavaFX Text holder.
     */
    @FXML
    protected Text base;

    /**
     * line of bases that is displayed.
     */
    private String sequence;

    /**
     * Init a controller at graph.fxml.
     *
     * @param sequence line of bases to display
     */
    public BaseController(final String sequence) {

        super(new StackPane());

        this.sequence = sequence;

        loadFXML("/application/base.fxml");

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root
     *                  object, or <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if the root object was not localized.
     */
    @Override
    public final void initialize(final URL location,
                                 final ResourceBundle resources) {

        base.setText(sequence);

    }

}
