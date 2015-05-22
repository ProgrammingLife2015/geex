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
     * Visibility property.
     */
    private BooleanProperty visibilityProperty = new SimpleBooleanProperty(true);

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

        root.visibleProperty().bind(visibilityProperty);

    }

    /**
     * Implement to set name for the breadcrumb.
     *
     * @return Name for the breadcrumb
     */
    public abstract String getBreadcrumbName();

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
