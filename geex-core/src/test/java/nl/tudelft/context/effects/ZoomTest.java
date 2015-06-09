package nl.tudelft.context.effects;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import nl.tudelft.context.drawable.graph.AbstractLabel;
import nl.tudelft.context.drawable.graph.DrawableNodeLabel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 03-06-2015
 */
@RunWith(JfxRunner.class)
public class ZoomTest {

    static ScrollPane scroll;
    static Group sequences;
    static Map<Integer, Set<AbstractLabel>> map;
    static Zoom zoom;
    static AbstractLabel label;
    static SimpleObjectProperty<Set<AbstractLabel>> labelProperty;

    @BeforeClass
    public static void beforeClass() {

        scroll = mock(ScrollPane.class);

        sequences = mock(Group.class);
        map = new HashMap<>();
        Set<AbstractLabel> labels = new HashSet<>();
        map.put(1, labels);
        label = mock(DrawableNodeLabel.class);
        labels.add(label);
        labelProperty = new SimpleObjectProperty<>(labels);

        zoom = new Zoom(scroll, sequences, labelProperty);

    }

    /**
     * At least it should give no errors.
     */
    @Test
    public void testConstruct() {

        zoom = new Zoom(scroll, sequences, labelProperty);

    }

}