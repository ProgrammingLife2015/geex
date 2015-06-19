package nl.tudelft.context.controller.graphlist;

import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 19-6-2015
 */
@RunWith(value = JfxRunner.class)
public class GraphListItemTest {

    @Test
    public void testMouseClicked() {
        ObservableList<GraphListItem> list = FXCollections.emptyObservableList();
        GraphListItem gli1 = new GraphListItem(GraphFilterEnum.COLLAPSE, list);
        GraphListItem gli2 = new GraphListItem(GraphFilterEnum.INSERT_DELETE, list);
        GraphListItem gli3 = new GraphListItem(GraphFilterEnum.SINGLE_POINT, list);

        list.addAll(gli1, gli2, gli3);

        EventHandler eh = gli1.getOnMouseClicked();

        //eh.handle();
    }

    @Test
    public void testDeactivate() {
        GraphListItem gli = new GraphListItem(GraphFilterEnum.COLLAPSE, null);

        gli.
    }

}