package nl.tudelft.context.effect;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.List;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 23-06-2015
 */
public class ZoomFactory {
    /**
     * Private constructor to force static use.
     */
    private ZoomFactory() {

    }

    /**
     * Creates and returns a new Zoom effect.
     *
     * @param scroll             The scroll panel
     * @param sequences          The graph
     * @param zoomLabelsProperty Property with the labels that need zoom
     */
    public static Zoom create(final ScrollPane scroll, final Group sequences,
                final ObjectProperty<List<Region>> zoomLabelsProperty) {

        Zoom zoom = new Zoom(scroll, sequences);
        zoomLabelsProperty.addListener(zoom.setEvents());

        return zoom;
    }
}
