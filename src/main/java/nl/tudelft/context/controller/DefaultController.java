package nl.tudelft.context.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

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
     * Visibility property.
     */
    private BooleanProperty visibilityProperty = new SimpleBooleanProperty(true);

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
        root.visibleProperty().bind(visibilityProperty);

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
     * Set the visibility.
     *
     * @param visibility True if visible
     */
    public void setVisibility(final Boolean visibility) {
        visibilityProperty.setValue(visibility);
    }

    /**
     * Get the visibility property.
     *
     * @return Visibility property
     */
    public BooleanProperty getVisibilityProperty() {
        return visibilityProperty;
    }

}
