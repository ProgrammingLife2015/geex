package nl.tudelft.context.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;

/**
 * @param <T> fxml root type
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 14-5-2015
 */
public abstract class ViewController<T extends Parent> extends DefaultController<T> {

    /**
     * Tells whether the controller is currently active (top view).
     */
    BooleanProperty activeProperty = new SimpleBooleanProperty(false);

    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     *
     * @param root the root of fxml
     */
    public ViewController(final T root) {

        super(root);

    }

    /**
     * Implement to set name for the breadcrumb.
     *
     * @return Name for the breadcrumb
     */
    public abstract String getBreadcrumbName();

    /**
     * Called whenever a view is activated.
     */
    public void activate() {
        activeProperty.set(true);
    }

    /**
     * Called whenever a view is deactivated.
     */
    public void deactivate() {
        activeProperty.set(false);
    }

}
