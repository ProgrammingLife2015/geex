package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import nl.tudelft.context.model.annotation.coding_sequence.CodingSequenceMap;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.GraphParser;
import nl.tudelft.context.model.annotation.resistance.ResistanceMap;
import nl.tudelft.context.workspace.Workspace;
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
import static org.mockito.Mockito.when;

/**
 * @author René Vennik
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class GraphControllerTest {

    protected final static File nodeFile = new File(GraphControllerTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(GraphControllerTest.class.getResource("/graph/edge.graph").getPath());

    protected static final int sequencesAmount = 4;

    protected static GraphController graphController;

    static MainController mainController;


    /**
     * Setup Load Graph Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        mainController = mock(MainController.class);
        mainController.messageController = new MessageController();
        when(mainController.getMenuController()).thenReturn(new MenuController(mainController, new MenuBar()));

        ReadOnlyObjectProperty<GraphMap> graphMapReadOnlyObjectProperty = mock(ReadOnlyObjectProperty.class);
        ReadOnlyObjectProperty<CodingSequenceMap> annotationMapReadOnlyObjectProperty = mock(ReadOnlyObjectProperty.class);
        ReadOnlyObjectProperty<ResistanceMap> resistanceMapReadOnlyObjectProperty = mock(ReadOnlyObjectProperty.class);

        Workspace workspace = mock(Workspace.class);

        when(workspace.getNewick()).thenReturn(new SimpleObjectProperty<>());

        when(mainController.getWorkspace()).thenReturn(workspace);

        graphController = new GraphController(mainController, new HashSet<>(Arrays.asList("Cat", "Dog")),
                graphMapReadOnlyObjectProperty, annotationMapReadOnlyObjectProperty, resistanceMapReadOnlyObjectProperty);

    }


    @Test
    public void testUpdateGraph() throws Exception {
        SimpleObjectProperty<GraphMap> graphMapReadOnlyObjectProperty = new SimpleObjectProperty<>();
        SimpleObjectProperty<CodingSequenceMap> annotationMapReadOnlyObjectProperty = new SimpleObjectProperty<>();
        ReadOnlyObjectProperty<ResistanceMap> resistanceMapReadOnlyObjectProperty = new SimpleObjectProperty<>();

        GraphMap graphMap = new GraphParser().setFiles(nodeFile, edgeFile).load();

        GraphController graphController = new GraphController(
                mainController,
                new HashSet<>(Arrays.asList("Cat", "Dog")),
                graphMapReadOnlyObjectProperty,
                annotationMapReadOnlyObjectProperty,
                resistanceMapReadOnlyObjectProperty);

        CompletableFuture<Boolean> sequencesAdded = new CompletableFuture<>();

        graphController.sequences.getChildren().addListener((ListChangeListener<? super Node>) event -> {
            if (graphController.sequences.getChildren().size() == sequencesAmount) {
                sequencesAdded.complete(true);
            }
        });

        graphMapReadOnlyObjectProperty.setValue(graphMap);
        annotationMapReadOnlyObjectProperty.setValue(new CodingSequenceMap());


        assertEquals(true, sequencesAdded.get(50, TimeUnit.MILLISECONDS));
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
