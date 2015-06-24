package nl.tudelft.context.controller.search;

import de.saxsys.javafx.test.JfxRunner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 12-6-2015
 */
@RunWith(JfxRunner.class)
public class NewickSearchControllerTest {

    NewickSearchController nwc;
    Label search = new Label("search");
    Label search2 = new Label("search2");
    Label find = new Label("find");

    @Before
    public void setUp() throws Exception {
        List<Label> labels = new ArrayList<>();

        labels.add(search);
        labels.add(search2);
        labels.add(find);

        ScrollPane scrollPane = new ScrollPane();

        Pane contents = new Pane();

        contents.getChildren().addAll(labels);

        scrollPane.setContent(contents);

        nwc = new NewickSearchController(labels, scrollPane);

    }

    @Test
    public void testSearch() throws Exception {
        nwc.searchField.setText("sea");
        EventHandler<KeyEvent> sfeh = nwc.searchFieldEventHandler();

        KeyEvent key = new KeyEvent(null, null, KeyEvent.KEY_RELEASED, "C", "Hello", KeyCode.C, false, false, false, false);

        sfeh.handle(key);

        assertTrue(search.getStyleClass().contains("search"));
        assertTrue(search.getStyleClass().contains("search-focus"));
        assertTrue(search2.getStyleClass().contains("search"));
        assertFalse(find.getStyleClass().contains("search"));
    }

    @Test
    public void testNoSearch() throws Exception {
        nwc.searchField.setText("thing");
        EventHandler<KeyEvent> sfeh = nwc.searchFieldEventHandler();

        KeyEvent key = new KeyEvent(null, null, KeyEvent.KEY_RELEASED, "C", "Hello", KeyCode.C, false, false, false, false);

        sfeh.handle(key);

        assertFalse(search.getStyleClass().contains("search"));
        assertFalse(search.getStyleClass().contains("search-focus"));
        assertFalse(search2.getStyleClass().contains("search"));
        assertFalse(find.getStyleClass().contains("search"));
    }

    @Test
    public void testNotFoundSearch() throws Exception {
        nwc.searchField.setText("sea");
        EventHandler<KeyEvent> sfeh = nwc.searchFieldEventHandler();

        KeyEvent key = new KeyEvent(null, null, KeyEvent.KEY_RELEASED, "C", "Hello", KeyCode.ENTER, false, false, false, false);

        sfeh.handle(key);

        assertFalse(search.getStyleClass().contains("search"));
        assertFalse(search2.getStyleClass().contains("search"));
        assertFalse(find.getStyleClass().contains("search"));
    }

    @Test
    public void testEmptySearch() throws Exception {
        assertEquals(new ArrayList<>(), nwc.search(""));
    }

    @Test
    public void testNextSearch() throws Exception {
        nwc.searchField.setText("sea");
        EventHandler<KeyEvent> sfeh = nwc.searchFieldEventHandler();

        KeyEvent key = new KeyEvent(null, null, KeyEvent.KEY_RELEASED, "C", "Hello", KeyCode.C, false, false, false, false);

        sfeh.handle(key);

        EventHandler<ActionEvent> smeh = nwc.searchMoveEventHandler(1);

        ActionEvent action = new ActionEvent(null, null);

        smeh.handle(action);

        assertTrue(search.getStyleClass().contains("search"));
        assertTrue(search2.getStyleClass().contains("search"));
        assertTrue(search2.getStyleClass().contains("search-focus"));
        assertFalse(find.getStyleClass().contains("search"));
    }

    @Test
    public void testEmptyNextSearch() throws Exception {
        nwc.searchField.setText("thing");
        EventHandler<KeyEvent> sfeh = nwc.searchFieldEventHandler();

        KeyEvent key = new KeyEvent(null, null, KeyEvent.KEY_RELEASED, "C", "Hello", KeyCode.C, false, false, false, false);

        sfeh.handle(key);

        EventHandler<ActionEvent> smeh = nwc.searchMoveEventHandler(1);

        ActionEvent action = new ActionEvent(null, null);

        smeh.handle(action);

        assertFalse(search.getStyleClass().contains("search"));
        assertFalse(search2.getStyleClass().contains("search"));
        assertFalse(search2.getStyleClass().contains("search-focus"));
        assertFalse(find.getStyleClass().contains("search"));
    }

}