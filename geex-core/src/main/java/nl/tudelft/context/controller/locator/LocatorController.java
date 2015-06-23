package nl.tudelft.context.controller.locator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
import nl.tudelft.context.model.annotation.Resistance;
import nl.tudelft.context.model.graph.DefaultNode;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 10-6-2015
 */
public class LocatorController {

    /**
     * The locator of the graph.
     */
    Pane locator;

    /**
     * The locator indicator of the graph.
     */
    Rectangle locatorIndicator;

    /**
     * Height of the locator.
     */
    private static final int LOCATOR_HEIGHT = 43;

    /**
     * Min and max references map by column.
     */
    Optional<Map<Integer, List<Integer>>> optionalTotalMap = Optional.empty();

    /**
     * Location currently shown (columns).
     */
    ObjectProperty<List<Integer>> positionProperty = new SimpleObjectProperty<>();

    /**
     * Minimum and maximum of ref positions.
     */
    int minRefPosition = Integer.MAX_VALUE, maxRefPosition = Integer.MIN_VALUE;

    /**
     * Current min and max of ref positions displayed.
     */
    int min = 0, max = 0;

    /**
     * Init the locator controller that shows the current position on the reference genome.
     *
     * @param locator          The locator pane
     * @param labelMapProperty Currently active nodes
     * @param positionProperty Location currently shown (columns)
     * @param graphController  Graph controller to update the position
     */
    public LocatorController(final Pane locator,
                             final ObjectProperty<Map<Integer, List<AbstractDrawableNode>>> labelMapProperty,
                             final ObjectProperty<List<Integer>> positionProperty,
                             final AbstractGraphController graphController) {

        this.locator = locator;
        this.positionProperty = positionProperty;

        initIndicator();

        labelMapProperty.addListener((observable, oldValue, newValue) -> {
            int oldRefPosition = (max + min) / 2;
            initIndicator();
            initLabelMap(newValue);
            initResistances(newValue);
            setPosition();
            if (!oldValue.isEmpty()) {
                goToRefPosition(oldRefPosition, graphController);
            }
        });

        positionProperty.addListener(event -> setPosition());
        locator.widthProperty().addListener(event -> {
            initResistances(labelMapProperty.get());
            setPosition();
        });

        initInteraction(graphController);

    }

    /**
     * Init indicator.
     */
    private void initIndicator() {

        locatorIndicator = new Rectangle();
        locatorIndicator.getStyleClass().add("indicator");
        locatorIndicator.setHeight(LOCATOR_HEIGHT);
        locatorIndicator.setTranslateY(2);
        locator.getChildren().setAll(locatorIndicator);

    }

    /**
     * Init the mouse clicks on the locator.
     *
     * @param graphController Graph controller to update the position
     */
    private void initInteraction(final AbstractGraphController graphController) {

        locator.setOnMouseClicked(event -> goToRefPosition((int) (event.getX() / getScale()), graphController));

    }

    /**
     * Let a graph go to a certain ref position.
     *
     * @param refPosition     Ref position to go to
     * @param graphController Graph to move
     */
    private void goToRefPosition(final int refPosition, final AbstractGraphController graphController) {
        optionalTotalMap.ifPresent(totalMap -> {
            int column = totalMap.entrySet().stream()
                    .reduce((a, b) -> {
                        if (getRefDeviation(a, refPosition) < getRefDeviation(b, refPosition)) {
                            return a;
                        } else {
                            return b;
                        }
                    }).get().getKey();
            graphController.setPosition(column);
        });
    }

    /**
     * Get deviation from ref position of a column.
     *
     * @param column      Map entry representing a column
     * @param refPosition Position that is deviated from
     * @return Deviation from ref position of a column
     */
    private int getRefDeviation(final Map.Entry<Integer, List<Integer>> column, final int refPosition) {
        List<Integer> positions = column.getValue();
        int refColumn = positions.get(0) + (positions.get(1) - positions.get(0)) / 2;
        return Math.abs(refColumn - refPosition);
    }

    /**
     * Init label map.
     *
     * @param labelMap Map to locate
     */
    private void initLabelMap(final Map<Integer, List<AbstractDrawableNode>> labelMap) {

        Map<Integer, List<Integer>> totalMap = new HashMap<>();
        labelMap.forEach((column, labels) -> {
            min = labels.stream()
                    .map(AbstractDrawableNode::getNode)
                    .mapToInt(DefaultNode::getRefStartPosition)
                    .min().getAsInt();
            max = labels.stream()
                    .map(AbstractDrawableNode::getNode)
                    .mapToInt(DefaultNode::getRefEndPosition)
                    .max().getAsInt();
            totalMap.put(column, Arrays.asList(min, max));
            minRefPosition = Math.min(minRefPosition, min);
            maxRefPosition = Math.max(maxRefPosition, max);
        });
        optionalTotalMap = Optional.of(totalMap);

    }

    /**
     * Display the resistances on the locator.
     *
     * @param labelMap Map containing all the nodes with possible resistances
     */
    private void initResistances(final Map<Integer, List<AbstractDrawableNode>> labelMap) {
        final Function<Resistance, Rectangle> resistanceRectangleFunction = createResistanceRectangle(getScale());

        locator.getChildren().removeIf(node -> !node.getStyleClass().contains("indicator"));
        locator.getChildren().addAll(labelMap.values().stream()
                .flatMap(Collection::stream)
                .map(AbstractDrawableNode::getNode)
                .map(DefaultNode::getResistances)
                .flatMap(Collection::stream)
                .map(resistanceRectangleFunction)
                .collect(Collectors.toList()));

        locatorIndicator.toFront();

    }

    /**
     * Create a rectangle for a resistance in the locator bar.
     *
     * @param scale Scale of the rectangle.
     * @return A function mapping Resistance to Rectangle.
     */
    private Function<Resistance, Rectangle> createResistanceRectangle(final double scale) {
        return resistance -> {
            final Rectangle rectangle = new Rectangle();
            double start = resistance.getStart() * scale;
            double end = resistance.getEnd() * scale;
            rectangle.setTranslateY(1);
            rectangle.setHeight(LOCATOR_HEIGHT);
            rectangle.setWidth(Math.max(DrawableEdge.MINIMUM_LINE_WIDTH, end - start));
            rectangle.setTranslateX(start);
            return rectangle;
        };
    }

    /**
     * Get the scale based on the locator width and the max ref position.
     *
     * @return Scale based on the locator width and the max ref position
     */
    private double getScale() {
        return locator.getWidth() / maxRefPosition;
    }

    /**
     * Sets the position of the indicator.
     */
    private void setPosition() {

        optionalTotalMap.ifPresent(totalMap -> {
            List<List<Integer>> list = positionProperty.get().stream()
                    .map(totalMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!list.isEmpty()) {
                min = list.stream()
                        .mapToInt(x -> x.get(0))
                        .min().getAsInt() - minRefPosition;

                max = list.stream()
                        .mapToInt(x -> x.get(1))
                        .max().getAsInt();

                double scale = getScale();

                locatorIndicator.setTranslateX(min * scale);
                locatorIndicator.setWidth((max - min) * scale);
            }
        });

    }

}
