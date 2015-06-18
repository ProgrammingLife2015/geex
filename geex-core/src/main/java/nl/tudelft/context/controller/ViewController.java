package nl.tudelft.context.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * @param <T> fxml root type
 * @author René Vennik
 * @version 1.0
 * @since 14-5-2015
 */
public abstract class ViewController<T extends Parent> extends AbstractController<T> {

    /**
     * Tells whether the controller is currently active (top view).
     */
    BooleanProperty activeProperty = new SimpleBooleanProperty(false);

    /**
     * Show in breadcrumb.
     */
    boolean showInBreadcrumb = true;

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
     * Set active or not.
     *
     * @param active New activated state
     */
    public void setActivated(final boolean active) {
        activeProperty.set(active);
    }

    /**
     * Check if view controller should be shown in breadcrumb.
     *
     * @return If breadcrumb should be shown in breadcrumb
     */
    public boolean getShowInBreadcrumb() {
        return showInBreadcrumb;
    }

}
