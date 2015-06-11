package nl.tudelft.context.effect;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 03-06-2015
 */
@RunWith(JfxRunner.class)
public class ZoomTest {

    static ScrollPane scroll;
    static Group sequences;
    static Map<Integer, List<Region>> map;
    static Zoom zoom;
    static Region label;
    static SimpleObjectProperty<List<Region>> labelProperty;

    @BeforeClass
    public static void beforeClass() {

        scroll = mock(ScrollPane.class);

        sequences = mock(Group.class);
        map = new HashMap<>();
        List<Region> labels = new ArrayList<>();
        map.put(1, labels);
        label = mock(Region.class);
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