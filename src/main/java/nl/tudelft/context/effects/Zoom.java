package nl.tudelft.context.effects;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableGraph;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * The sequences group, needed for calculating positions.
     */
    final Group sequences;
    /**
     * A map with all labels.
     */
    final Map<Integer, List<DefaultLabel>> labelsMap;
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
     * Constructs a Zoom effect.
     *
     * @param scroll    The scroll panel
     * @param sequences The sequences group
     * @param labelsMap The map with all labels
     */
    public Zoom(final ScrollPane scroll, final Group sequences, final Map<Integer, List<DefaultLabel>> labelsMap) {
        this.scroll = scroll;
        this.sequences = sequences;
        this.labelsMap = labelsMap;

        scroll.widthProperty().addListener(event ->  bindEffect());
        scroll.hvalueProperty().addListener(event -> bindEffect());
        scroll.vvalueProperty().addListener(event -> bindEffect());
    }

    /**
     * Binds the effect to all visible labels.
     */
    private void bindEffect() {
        Bounds layoutBounds = scroll.getContent().layoutBoundsProperty().getValue();
        double left = (layoutBounds.getWidth() - scroll.getWidth())
                * scroll.getHvalue();
        double top = (layoutBounds.getHeight() - scroll.getHeight())
                * scroll.getVvalue();
        int indexFrom = (int) Math.floor(left / DrawableGraph.LABEL_SPACING) - 1;
        int indexTo = indexFrom + (int) Math.ceil(scroll.getWidth() / DrawableGraph.LABEL_SPACING) + 1;

        List<DefaultLabel> infoLabels = getLabels(indexFrom, indexTo);

        setEvents(infoLabels, left, top);
    }

    /**
     * Sets the listeners to the scroll panel. The effect is based on the mouse's position.
     *
     * @param infoLabels The labels that should zoom
     * @param left       The x-offset of the scroll panel (including scroll)
     * @param top        The y-offset of the scroll panel (including scroll)
     */
    private void setEvents(final List<DefaultLabel> infoLabels, final double left, final double top) {
        scroll.setOnMouseEntered(event -> {
            setMouse(event);
            applyAll(infoLabels, left, top);
        });
        scroll.setOnMouseMoved(event -> {
            setMouse(event);
            applyAll(infoLabels, left, top);
        });
        applyAll(infoLabels, left, top);
    }

    /**
     * Get all labels that are currently visible.
     *
     * @param indexFrom The first column that is visible
     * @param indexTo   The last column that is visible
     * @return          A list of labels that are visible
     */
    private List<DefaultLabel> getLabels(final int indexFrom, final int indexTo) {
        return IntStream.rangeClosed(indexFrom, indexTo)
                .mapToObj(labelsMap::get)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Sets the last known mouse position.
     *
     * @param event      The event that triggered this
     */
    public void setMouse(final MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    /**
     * Applies the zoom effect to the labels.
     *
     * @param infoLabels The labels to apply the effect to
     * @param left       The x-offset of the scroll panel (including scroll)
     * @param top        The y-offset of the scroll panel (including scroll)
     */
    public void applyAll(final List<DefaultLabel> infoLabels, final double left, final double top) {
        infoLabels.forEach(label ->
                apply(label, mouseX - sequences.getLayoutX() + left,  mouseY - sequences.getLayoutY() + top));
    }

    /**
     * Applies the zoom effect to the label.
     *
     * @param label  The label to apply the effect to
     * @param mouseX The mouse's x-position
     * @param mouseY The mouse's y-position
     */
    public void apply(final DefaultLabel label, final double mouseX, final double mouseY) {
        double dx = mouseX - label.getTranslateX() - (label.getWidth() / 2);
        double dy = mouseY - label.getTranslateY() - (label.getHeight() / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);
        distance = Math.max(0, Math.min(MAX_DISTANCE, distance - PEEK_RADIUS));
        addScale(label, distance / MAX_DISTANCE);
    }

    /**
     * Adds a scale to the InfoLabel, based on a given ratio [.0, 1.0].
     *
     * @param ratio A ratio between .0 and 1.0.
     */
    /**
     * Adds a scale to the InfoLabel, based on a given ratio [.0, 1.0].
     *
     * @param label The label to scale
     * @param ratio The ratio to add to the scale
     */
    private void addScale(final DefaultLabel label, final double ratio) {
        double scale = 1 + SCALE_ADDED * (Math.cos(ratio * Math.PI) + 1);
        label.setScaleX(scale);
        label.setScaleY(scale);
        label.setCache(true);
    }
}
