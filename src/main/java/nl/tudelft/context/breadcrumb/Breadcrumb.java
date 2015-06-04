package nl.tudelft.context.breadcrumb;

import javafx.collections.ListChangeListener;
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
     * Observable list to keep track on view list.
     */
    ObservableList<ViewController> viewList;

    /**
     * Create a breadcrumb that listen to a view stack.
     *
     * @param mainController Controller to set new views
     * @param viewList       Stack to listen to
     */
    public Breadcrumb(final MainController mainController, final ObservableList<ViewController> viewList) {

        this.mainController = mainController;

        getStyleClass().add("breadcrumb");

        this.viewList = viewList;
        this.viewList.addListener((ListChangeListener<ViewController>) c -> update());

    }

    /**
     * Update the breadcrumb.
     */
    public void update() {

        List<HBox> items = viewList.stream()
                .map(viewController -> {
                    final Label label = new Label(viewController.getBreadcrumbName());
                    label.setOnMouseClicked(event -> mainController.toView(viewController));
                    viewController.getVisibilityProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            label.getStyleClass().remove("inactive");
                        } else {
                            label.getStyleClass().add("inactive");
                        }
                    });
                    return new HBox(label);
                })
                .collect(Collectors.toList());

        if (!items.isEmpty()) {
            items.get(items.size() - 1).getStyleClass().add("last");
        }
        getChildren().setAll(items);

    }

}
