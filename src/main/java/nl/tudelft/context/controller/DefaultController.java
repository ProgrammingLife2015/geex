package nl.tudelft.context.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * @param <T> fxml root type
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
public abstract class DefaultController<T extends Parent>
        implements Initializable {

    /**
     * The root of the controller, a javafx element which extends Parent.
     */
    final T root;

    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     *
     * @param root     the root of fxml
     */
    public DefaultController(final T root) {

        this.root = root;

    }

    /**
     * Separate loadFXML method to call at the end of the parent class
     * constructor to prevent uninitialized Objects.
     *+
     * @param fxmlFile the fxml file
     */
    protected final void loadFXML(final String fxmlFile) {

        FXMLLoader fxmlLoader =
                new FXMLLoader(DefaultController.class.getResource(fxmlFile));

        fxmlLoader.setRoot(root);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Returns root.
     *
     * @return root of fxml
     */
    public final Parent getRoot() {

        return root;

    }

    /**
     * Load file.
     *
     * @param fileChooser fileChooser for loading
     * @param source source Textfield, to update when finished
     * @return The selected file
     */
    protected final File loadFile(final FileChooser fileChooser, final TextField source) {

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (file != null) {
            source.setText(file.getName());
        } else {
            source.setText("");
        }

        return file;

    }

}
