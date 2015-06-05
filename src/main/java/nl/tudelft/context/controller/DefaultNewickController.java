package nl.tudelft.context.controller;

import javafx.scene.control.ScrollPane;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 5-6-2015
 */
public abstract class DefaultNewickController extends ViewController<ScrollPane> {

    /**
     * Create a default Newick controller.
     */
    public DefaultNewickController() {

        super(new ScrollPane());

    }

}
