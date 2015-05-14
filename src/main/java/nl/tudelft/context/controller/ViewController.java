package nl.tudelft.context.controller;

import javafx.scene.Parent;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 14-5-2015
 */
public abstract class ViewController<T extends Parent> extends DefaultController<T> {

    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     *
     * @param root the root of fxml
     */
    public ViewController(T root) {

        super(root);

    }

    /**
     * Implement to set name for the breadcrumb.
     *
     * @return Name for the breadcrumb
     */
    public abstract String getBreadcrumbName();

}
