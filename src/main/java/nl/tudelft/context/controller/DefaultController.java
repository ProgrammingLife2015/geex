package nl.tudelft.context.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
public abstract class DefaultController<T extends Parent> {

    protected final T root;
    protected final String fxmlFile;

    /**
     * Create a generic controller.
     *
     * @param root     the root of fxml
     * @param fxmlFile the fxml file
     */
    public DefaultController(T root, String fxmlFile) {

        this.root = root;
        this.fxmlFile = fxmlFile;

    }

    /**
     * Create a separate loadFXML method to call at the end of the parent class constructor to prevent uninitialized Objects.
     */
    protected void loadFXML() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

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
