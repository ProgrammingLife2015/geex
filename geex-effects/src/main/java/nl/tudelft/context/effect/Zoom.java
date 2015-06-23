package nl.tudelft.context.effect;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.util.List;

/**
 * @author Jasper Boot
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
     * @param scroll             The scroll panel
     * @param sequences          The graph
     * @param zoomLabelsProperty Property with the labels that need zoom
     */
    public Zoom(final ScrollPane scroll, final Group sequences,
                final ObjectProperty<List<Region>> zoomLabelsProperty) {

        this.scroll = scroll;
        this.sequences = sequences;

        scroll.widthProperty().addListener(event -> calculateBounds());
        scroll.hvalueProperty().addListener(event -> calculateBounds());
        scroll.vvalueProperty().addListener(event -> calculateBounds());

        zoomLabelsProperty.addListener(setEvents());
    }

    /**
     * Calculate the bounds.
     */
    public void calculateBounds() {
        Bounds bounds = scroll.getContent().layoutBoundsProperty().getValue();
        left = getBound(bounds.getWidth(), scroll.getWidth(), scroll.getHvalue());
        top = getBound(bounds.getHeight(), scroll.getHeight(), scroll.getVvalue());
    }

    /**
     * Calculate the bounds.
     *
     * @param boundSize    The size of the bound
     * @param scrollSize   The size of the scroll
     * @param scrollScalar The scale of the scroll
     * @return Returns the calculated bounds.
     */
    public double getBound(final double boundSize, final double scrollSize, final double scrollScalar) {
        return (boundSize - scrollSize) * scrollScalar;
    }

    /**
     * ChangeListener to tell the labels to apply this effect whenever the mouse moves or enters the scroll panel.
     *
     * @return Returns the new listener
     */
    public ChangeListener<List<Region>> setEvents() {
        return (observable, oldValue, newValue) -> {
            scroll.setOnMouseEntered(getMouseEventEventHandler(newValue));
            scroll.setOnMouseMoved(getMouseEventEventHandler(newValue));
        };
    }

    /**
     * Create an event handler for the mouse to interact with the labels.
     *
     * @param labels Labels to interact with
     * @return Mouse event handler to will update the labels
     */
    private EventHandler<MouseEvent> getMouseEventEventHandler(List<Region> labels) {
        return event -> {
            setMouse(event);
            applyAll(labels);
        };
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
                addScale(label,
                        getScale(
                                label.getTranslateX(),
                                label.getTranslateY(),
                                label.getWidth(),
                                label.getHeight(),
                                mouseX - sequences.getLayoutX() + left,
                                mouseY - sequences.getLayoutY() + top)));
    }

    /**
     * Gets the scale to add to the label.
     *
     * @param labelX The label's x-position
     * @param labelY The label's y-position
     * @param labelW The label's width
     * @param labelH The label's height
     * @param mouseX The mouse's x-position
     * @param mouseY The mouse's y-position
     * @return The scale to add.
     */
    public double getScale(final double labelX, final double labelY, final double labelW, final double labelH,
                           final double mouseX, final double mouseY) {
        double dx = mouseX - labelX - (labelW / 2);
        double dy = mouseY - labelY - (labelH / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);
        distance = Math.max(0, Math.min(MAX_DISTANCE, distance - PEEK_RADIUS));
        return distance / MAX_DISTANCE;
    }

    /**
     * Adds a scale to a region, based on a given ratio [.0, 1.0].
     *
     * @param region The region to scale
     * @param ratio  The ratio to add to the scale
     */
    public void addScale(final Region region, final double ratio) {
        double scale = 1 + SCALE_ADDED * (Math.cos(ratio * Math.PI) + 1);

        region.setScaleX(scale);
        region.setScaleY(scale);
    }
}
