package nl.tudelft.context.effects;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import nl.tudelft.context.drawable.graph.AbstractLabel;
import nl.tudelft.context.drawable.graph.DrawableNodeLabel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 03-06-2015
 */
@RunWith(JfxRunner.class)
public class ZoomTest {

    static ScrollPane scroll;
    static Group sequences;
    static Map<Integer, List<AbstractLabel>> map;
    static Zoom zoom;
    static AbstractLabel label;

    @BeforeClass
    public static void beforeClass() {

        scroll = mock(ScrollPane.class);

        sequences = mock(Group.class);
        map = new HashMap<>();
        List<AbstractLabel> labels = new ArrayList<>();
        map.put(1, labels);
        label = mock(DrawableNodeLabel.class);
        labels.add(label);

        zoom = new Zoom(scroll, sequences, map);

    }

    /**
     * At least it should give no errors.
     */
    @Test
    public void testConstruct() {

        zoom = new Zoom(scroll, sequences, map);

    }

}