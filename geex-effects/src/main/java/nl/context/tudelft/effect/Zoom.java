package nl.context.tudelft.effect;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 01-06-2015
 */
public class Zoom {
    /**
     * The scroll panel, needed for calculating positions.
     */
    final ScrollPane scroll;
    /**
     * The graph group, needed for calculating positions.
     */
    final Group sequences;
    /**
     * The mouse's last known x-position.
     */
    double mouseX = 0;
    /**
     * The mouse's last known y-position.
     */
    double mouseY = 0;
    /**
     * The radius from the label's center in which the nodes are at their biggest.
     */
    public static final int PEEK_RADIUS = 30;
    /**
     * (A portion of) this gets added to the scale of the labels affected by the zoom.
     */
    public static final double SCALE_ADDED = .25;
    /**
     * Only labels that are MAX_DISTANCE away from the mouse are affected by the zoom.
     */
    public static final int MAX_DISTANCE = 120;
    /**
     * The x-offset of the scroll panel (including scroll).
     */
    double left;
    /**
     * The y-offset of the scroll panel (including scroll).
     */
    double top;

    /**
     * Constructs a Zoom effect.
     *
     * @param scroll                The scroll panel
     * @param sequences             The graph
     * @param currentLabelsProperty Property with the labels that are shown
     */
    public Zoom(final ScrollPane scroll, final Group sequences,
                final ObjectProperty<Set<Region>> currentLabelsProperty) {

        this.scroll = scroll;
        this.sequences = sequences;

        scroll.widthProperty().addListener(event -> calculateBounds());
        scroll.hvalueProperty().addListener(event -> calculateBounds());
        scroll.vvalueProperty().addListener(event -> calculateBounds());

        currentLabelsProperty.addListener((observable, oldValue, newValue) -> {
            setEvents(newValue.stream().collect(Collectors.toList()));
        });
    }

    /**
     * Calculate the bounds.
     */
    public void calculateBounds() {
        Bounds layoutBounds = scroll.getContent().layoutBoundsProperty().getValue();
        left = (layoutBounds.getWidth() - scroll.getWidth()) * scroll.getHvalue();
        top = (layoutBounds.getHeight() - scroll.getHeight()) * scroll.getVvalue();
    }

    /**
     * Tells the labels to apply this effect whenever the mouse moves or enters the scroll panel.
     *
     * @param regions The regions that should zoom
     */
    private void setEvents(final List<Region> regions) {
        scroll.setOnMouseEntered(event -> {
            setMouse(event);
            applyAll(regions);
        });
        scroll.setOnMouseMoved(event -> {
            setMouse(event);
            applyAll(regions);
        });
    }

    /**
     * Sets the last known mouse position.
     *
     * @param event The event that triggered this
     */
    public void setMouse(final MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    /**
     * Applies the zoom effect to the labels.
     *
     * @param regions The regions to apply the effect to
     */
    public void applyAll(final List<Region> regions) {
        regions.forEach(label ->
                apply(label, mouseX - sequences.getLayoutX() + left, mouseY - sequences.getLayoutY() + top));
    }

    /**
     * Applies the zoom effect to a region.
     *
     * @param regions The regions to apply the effect to
     * @param mouseX  The mouse's x-position
     * @param mouseY  The mouse's y-position
     */
    public void apply(final Region regions, final double mouseX, final double mouseY) {
        double dx = mouseX - regions.getTranslateX() - (regions.getWidth() / 2);
        double dy = mouseY - regions.getTranslateY() - (regions.getHeight() / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);
        distance = Math.max(0, Math.min(MAX_DISTANCE, distance - PEEK_RADIUS));
        addScale(regions, distance / MAX_DISTANCE);
    }

    /**
     * Adds a scale to a region, based on a given ratio [.0, 1.0].
     *
     * @param regions The regions to scale
     * @param ratio   The ratio to add to the scale
     */
    private void addScale(final Region regions, final double ratio) {
        double scale = 1 + SCALE_ADDED * (Math.cos(ratio * Math.PI) + 1);

        regions.setScaleX(scale);
        regions.setScaleY(scale);

        regions.setCacheHint(CacheHint.SCALE);
    }
}
