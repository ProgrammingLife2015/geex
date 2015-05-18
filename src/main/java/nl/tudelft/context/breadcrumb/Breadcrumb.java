package nl.tudelft.context.breadcrumb;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.tudelft.context.controller.MainController;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 14-5-2015
 */
public final class Breadcrumb extends HBox implements Observer {

    /**
     * Main controller to set views.
     */
    MainController mainController;

    /**
     * View stack the breadcrumb displays.
     */
    ViewStack viewStack;

    /**
     * Create a breadcrumb that listen to a view stack.
     *
     * @param mainController Controller to set new views
     * @param viewStack      Stack to listen to
     */
    public Breadcrumb(final MainController mainController, final ViewStack viewStack) {

        this.mainController = mainController;
        this.viewStack = viewStack;

        viewStack.addObserver(this);
        getStyleClass().add("breadcrumb");

    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(final Observable o, final Object arg) {

        List<HBox> items = viewStack.stream()
                .map(viewController -> {
                    final Label label = new Label(viewController.getBreadcrumbName());
                    label.setOnMouseClicked(event -> mainController.backToView(viewController));
                    return new HBox(label);
                })
                .collect(Collectors.toList());

        if (!items.isEmpty()) {
            items.get(items.size() - 1).getStyleClass().add("last");
        }
        getChildren().setAll(items);

    }

}
