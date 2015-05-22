package nl.tudelft.context.breadcrumb;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.controller.ViewController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 14-5-2015
 */
public final class Breadcrumb extends HBox {

    /**
     * Main controller to set views.
     */
    MainController mainController;

    /**
     * Property to keep track on view list.
     */
    SimpleListProperty<ViewController> viewProperty;

    /**
     * Create a breadcrumb that listen to a view stack.
     *
     * @param mainController Controller to set new views
     * @param viewList       Stack to listen to
     */
    public Breadcrumb(final MainController mainController, final ObservableList<ViewController> viewList) {

        this.mainController = mainController;

        getStyleClass().add("breadcrumb");

        this.viewProperty = new SimpleListProperty<>(viewList);
        this.viewProperty.addListener((observable, oldValue, newValue) -> update(newValue));

    }

    /**
     * Update the breadcrumb.
     *
     * @param viewList List to display
     */
    public void update(final ObservableList<ViewController> viewList) {

        List<HBox> items = viewList.stream()
                .map(viewController -> {
                    final Label label = new Label(viewController.getBreadcrumbName());
                    label.setOnMouseClicked(event -> mainController.toView(viewController));
                    return new HBox(label);
                })
                .collect(Collectors.toList());

        if (!items.isEmpty()) {
            items.get(items.size() - 1).getStyleClass().add("last");
        }
        getChildren().setAll(items);

    }

}
