package nl.tudelft.context.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * @param <T> fxml root type
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
public abstract class DefaultController<T extends Parent> {

    protected final T root;

    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     *
     * @param root     the root of fxml
     */
    public DefaultController(T root) {

        this.root = root;

    }

    /**
     * Separate loadFXML method to call at the end of the parent class constructor to prevent uninitialized Objects.
     *
     * @param fxmlFile the fxml file
     */
    protected void loadFXML(String fxmlFile) {

        FXMLLoader fxmlLoader = new FXMLLoader(DefaultController.class.getResource(fxmlFile));

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
    public Parent getRoot() {

        return root;

    }

}
