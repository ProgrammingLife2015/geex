package nl.tudelft.context.breadcrumb;

import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.context.controller.BaseController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.controller.ViewController;
import nl.tudelft.context.model.graph.NodeParser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author René Vennik
 * @version 1.0
 * @since 24-6-2015
 */
@RunWith(JfxRunner.class)
public class BreadcrumbTest {

    static Breadcrumb breadcrumb;
    static ObservableList<ViewController> viewList;

    /**
     * Set up breadcrumb.
     */
    @BeforeClass
    public static void beforeClass() {

        MainController mainController = mock(MainController.class);

        viewList = FXCollections.observableList(new ArrayList<>());
        breadcrumb = new Breadcrumb(mainController, viewList);

    }

    /**
     * Test update of the breadcrumb.
     */
    @Test
    public void testUpdate() {

        viewList.setAll(
                new BaseController(new NodeParser().getNode(new Scanner(">0 | Cat,Dog | 5 | 7\nA\n"))),
                new BaseController(new NodeParser().getNode(new Scanner(">1 | Dog | 8 | 10\nATC\n")))
        );

        assertTrue(breadcrumb.getChildren().get(1).getStyleClass().contains("last"));
        assertEquals(2, breadcrumb.getChildren().size());

    }

    /**
     * Test empty update of the breadcrumb.
     */
    @Test
    public void testEmptyUpdate() {

        viewList.setAll();
        assertTrue(breadcrumb.getChildren().isEmpty());

    }

}