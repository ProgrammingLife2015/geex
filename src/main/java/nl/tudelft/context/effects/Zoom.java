package nl.tudelft.context.effects;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import nl.tudelft.context.drawable.InfoLabel;
import nl.tudelft.context.model.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static nl.tudelft.context.drawable.InfoLabel.LABEL_WIDTH;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 01-06-2015
 */
public class Zoom {
    final ScrollPane scroll;
    final Group sequences;
    final Map<Integer, List<InfoLabel>> map;
    double mouseX = .0;
    double mouseY = .0;
    public static final int PEEK_RADIUS = 60;

    public Zoom(ScrollPane scroll, Group sequences, Map<Integer, List<InfoLabel>> map) {
        this.scroll = scroll;
        this.sequences = sequences;
        this.map = map;

        scroll.widthProperty().addListener(event ->  apply());
        scroll.hvalueProperty().addListener(event -> apply());
        scroll.vvalueProperty().addListener(event -> apply());
    }

    private IntStream getInfoLabelsRange() {
        double width = scroll.getWidth();
        double left = (scroll.getContent().layoutBoundsProperty().getValue().getWidth() - width)
                * scroll.getHvalue();
        int indexFrom = (int) Math.floor(left / Graph.LABEL_SPACING) - 1;
        int indexTo = indexFrom + (int) Math.ceil(width / Graph.LABEL_SPACING) + 1;

        return IntStream.rangeClosed(indexFrom, indexTo);
    }

    public void apply() {

        List<InfoLabel> infoLabels = getInfoLabelsRange()
                .mapToObj(map::get)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        double left = (scroll.getContent().layoutBoundsProperty().getValue().getWidth() - scroll.getWidth())
                * scroll.getHvalue();
        double top = (scroll.getContent().layoutBoundsProperty().getValue().getHeight() - scroll.getHeight())
                * scroll.getVvalue();

        scroll.setOnMouseEntered(event -> mouseOver(event, infoLabels, left, top));
        scroll.setOnMouseMoved(event -> mouseOver(event, infoLabels, left, top));
        mouseOver(infoLabels, left, top);
    }

    public void mouseOver(final MouseEvent event, List<InfoLabel> infoLabels, final double left, final double top) {
        mouseX = event.getX();
        mouseY = event.getY();
        mouseOver(infoLabels, left, top);
    }

    public void mouseOver(final List<InfoLabel> infoLabels, final double left, final double top) {
        infoLabels.forEach(label ->
                mouseOver(
                        label,
                        mouseX - sequences.getLayoutX() + left,
                        mouseY - sequences.getLayoutY() + top));
    }

    /**
     * Initiates the mouseover effect, based on the position of the mouse and the InfoLabel's position.
     *
     * @param mouseX The x-position of the mouse.
     * @param mouseY The y-position of the mouse.
     */
    public void mouseOver(InfoLabel label, final double mouseX, final double mouseY) {
        double dx = mouseX - label.getTranslateX() - (label.getWidth() / 2);
        double dy = mouseY - label.getTranslateY() - (label.getHeight() / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);
        double maxDistance = 2 * PEEK_RADIUS;
        distance = Math.max(0, Math.min(maxDistance, distance - (.5 * PEEK_RADIUS)));
        addScale(label, distance / maxDistance);
    }

    /**
     * Adds a scale to the InfoLabel, based on a given ratio [.0, 1.0].
     *
     * @param ratio A ratio between .0 and 1.0.
     */
    private void addScale(InfoLabel label, final double ratio) {
        double scale = 1 + .25 * (Math.cos(ratio * Math.PI) + 1);
        label.setScaleX(scale);
        label.setScaleY(scale);
    }
}
