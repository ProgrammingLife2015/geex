package nl.tudelft.context.breadcrumb;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 14-5-2015
 */
public class Breadcrumb extends HBox {

    public Breadcrumb() {

        getStyleClass().add("breadcrumb");
        HBox last = new HBox(new Label("Base: 123"));
        last.getStyleClass().add("last");
        getChildren().addAll(
                new HBox(new Label("Newick tree")),
                new HBox(new Label("Genome graph")),
                last
        );

    }

}
