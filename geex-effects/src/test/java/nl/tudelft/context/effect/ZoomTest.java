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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
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
        scroll = new ScrollPane();
        scroll.setMinWidth(1024);
        sequences = mock(Group.class);
        map = new HashMap<>();
        List<Region> labels = new ArrayList<>();
        map.put(1, labels);
        label = mock(Region.class);
        labels.add(label);
        labelProperty = new SimpleObjectProperty<>(labels);

        zoom = new Zoom(scroll, sequences, labelProperty);
    }

    @Test
    public void testCalculateBounds() {
        assertEquals((1.0 - 2.0) * .3, zoom.getBound(1.0, 2.0, .3), 1e-12);
    }

    @Test
    public void testListeners() {
        assertNull(scroll.getOnMouseEntered());
        assertNull(scroll.getOnMouseMoved());
        zoom.setEvents(Collections.emptyList());
        assertNotNull(scroll.getOnMouseEntered());
        assertNotNull(scroll.getOnMouseMoved());
    }

    @Test
    public void testScale() {
        assertEquals(0, zoom.getScale(5, 10, 15, 20, 25, 30), 1e-6);
        assertEquals(.416992, zoom.getScale(25, 50, 75, 100, 125, 150), 1e-6);
        assertEquals(1, zoom.getScale(50, 100, 150, 200, 250, 300), 1e-6);
    }

}