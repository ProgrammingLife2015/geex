package nl.tudelft.context.effect;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
        scroll.setContent(new Pane());
        sequences = mock(Group.class);
        map = new HashMap<>();
        List<Region> labels = new ArrayList<>();
        map.put(1, labels);
        label = mock(Region.class);
        labels.add(label);
        labelProperty = new SimpleObjectProperty<>(labels);

        zoom = ZoomFactory.create(scroll, sequences, labelProperty);
    }

    @Test
    public void testCalculateBounds() {
        assertEquals((1.0 - 2.0) * .3, zoom.getBound(1.0, 2.0, .3), 1e-12);
    }

    @Test
    public void testListeners() {
        assertNull(scroll.getOnMouseEntered());
        assertNull(scroll.getOnMouseMoved());
        ChangeListener<List<Region>> changeListener = zoom.setEvents();
        changeListener.changed(null, null, Collections.emptyList());
        assertNotNull(scroll.getOnMouseEntered());
        assertNotNull(scroll.getOnMouseMoved());
    }

    @Test
    public void testScale() {
        assertEquals(0, zoom.getScale(5, 10, 15, 20, 25, 30), 1e-6);
        assertEquals(.416992, zoom.getScale(25, 50, 75, 100, 125, 150), 1e-6);
        assertEquals(1, zoom.getScale(50, 100, 150, 200, 250, 300), 1e-6);
    }

    @Test
    public void testAddScale() {
        Region region = new Region();
        zoom.addScale(region, .5);
        assertEquals(1.25, region.getScaleX(), 1e-12);
        assertEquals(1.25, region.getScaleY(), 1e-12);
    }

    @Test
    public void testMouse() {
        MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 7, 13, 11, 17, MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null);
        zoom.setMouse(mouseEvent);
        assertEquals(7, zoom.mouseX, 1e-12);
        assertEquals(13, zoom.mouseY, 1e-12);
    }

    @Test
    public void testBounds() {
        zoom.left = zoom.top = 11;
        zoom.calculateBounds();
        assertEquals(0, zoom.left, 1e-12);
        assertEquals(0, zoom.top, 1e-12);
    }

}