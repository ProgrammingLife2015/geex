package nl.tudelft.context.controller.graphlist;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 19-6-2015
 */
@RunWith(value = JfxRunner.class)
public class GraphListItemTest {

    @Test
    public void testMouseClicked() throws Exception {
        ObservableList<GraphListItem> list = FXCollections.observableArrayList();
        GraphListItem gli1 = new GraphListItem(GraphFilterEnum.COLLAPSE, list);
        GraphListItem gli2 = new GraphListItem(GraphFilterEnum.INSERT_DELETE, list);
        GraphListItem gli3 = new GraphListItem(GraphFilterEnum.SINGLE_POINT, list);

        list.addAll(gli1, gli2, gli3);

        InvalidationListener mockListener = mock(InvalidationListener.class);

        gli1.addListener(mockListener);

        EventHandler<? super MouseEvent> eh = gli1.getOnMouseClicked();

        MouseEvent myMouseEvent = new MouseEvent(gli1, gli1, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);

        assertTrue(gli1.getStyleClass().contains("active"));
        eh.handle(myMouseEvent);

        assertFalse(gli1.getStyleClass().contains("active"));
        assertTrue(gli1.getGraph().isActive());
        verify(mockListener).invalidated(gli1);
    }
}