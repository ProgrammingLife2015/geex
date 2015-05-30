package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.GraphParser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class GraphControllerTest {

    protected final static File nodeFile = new File(GraphControllerTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(GraphControllerTest.class.getResource("/graph/edge.graph").getPath());

    protected static final int sequencesAmount = 4;

    protected static GraphController graphController;


    /**
     * Setup Load Graph Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        MainController mainController = mock(MainController.class);
        mainController.messageController = new MessageController();

        ReadOnlyObjectProperty<GraphMap> graphMapReadOnlyObjectProperty = mock(ReadOnlyObjectProperty.class);
        ReadOnlyObjectProperty<AnnotationMap> annotationMapReadOnlyObjectProperty = mock(ReadOnlyObjectProperty.class);

        graphController = new GraphController(mainController, new HashSet<>(Arrays.asList("Cat", "Dog")),
                graphMapReadOnlyObjectProperty, annotationMapReadOnlyObjectProperty);

    }

    @Test
    public void testUpdateGraph() throws Exception {
        SimpleObjectProperty<GraphMap> graphMapReadOnlyObjectProperty = new SimpleObjectProperty<>();
        ReadOnlyObjectProperty<AnnotationMap> annotationMapReadOnlyObjectProperty = new SimpleObjectProperty<>();

        GraphMap graphMap = new GraphParser().setReader(nodeFile, edgeFile).parse();

        GraphController graphController = new GraphController(mock(MainController.class), new HashSet<>(Arrays.asList("Cat", "Dog")), graphMapReadOnlyObjectProperty, annotationMapReadOnlyObjectProperty);

        CompletableFuture<Boolean> sequencesAdded = new CompletableFuture<>();

        graphController.sequences.getChildren().addListener((ListChangeListener<? super Node>) event -> {
            if (graphController.sequences.getChildren().size() == sequencesAmount) {
                sequencesAdded.complete(true);
            }
        });

        graphMapReadOnlyObjectProperty.setValue(graphMap);

        assertEquals(true, sequencesAdded.get(5000, TimeUnit.MILLISECONDS));
    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        graphController.loadFXML("");

    }


    /**
     * Test getBreadcrumbname on graphController
     */
    @Test
    public void testGetBreadcrumbName() {
        assertEquals("Genome graph (2)", graphController.getBreadcrumbName());
    }

}
