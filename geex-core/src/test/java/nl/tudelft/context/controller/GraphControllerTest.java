package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import nl.tudelft.context.model.annotation.CodingSequenceMap;
import nl.tudelft.context.model.annotation.ResistanceMap;
import nl.tudelft.context.model.annotation.ResistanceParser;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.parser.GraphParser;
import nl.tudelft.context.service.LoadService;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
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

    protected final static File resistanceFile = new File(GraphControllerTest.class.getResource("/annotation/test.cs.txt").getPath());

    protected static final int sequencesAmount = 5;

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
        ReadOnlyObjectProperty<CodingSequenceMap> codingSequenceMapReadOnlyObjectProperty = mock(ReadOnlyObjectProperty.class);
        ReadOnlyObjectProperty<ResistanceMap> resistanceMapReadOnlyObjectProperty = mock(ReadOnlyObjectProperty.class);

        Workspace workspace = mock(Workspace.class);

        when(workspace.getNewick()).thenReturn(new SimpleObjectProperty<>());

        when(mainController.getWorkspace()).thenReturn(workspace);

        graphController = new GraphController(mainController, new HashSet<>(Arrays.asList("Cat", "Dog")),
                graphMapReadOnlyObjectProperty, codingSequenceMapReadOnlyObjectProperty, resistanceMapReadOnlyObjectProperty);

    }


    @Test
    public void testUpdateGraph() throws Exception {
        SimpleObjectProperty<GraphMap> graphMapReadOnlyObjectProperty = new SimpleObjectProperty<>();
        SimpleObjectProperty<CodingSequenceMap> codingSequenceMapReadOnlyObjectProperty = new SimpleObjectProperty<>();
        SimpleObjectProperty<ResistanceMap> resistanceMapReadOnlyObjectProperty = new SimpleObjectProperty<>();

        GraphMap graphMap = new GraphParser().setFiles(nodeFile, edgeFile).load();

        GraphController graphController = new GraphController(
                mainController,
                new HashSet<>(Arrays.asList("Cat", "TKK_REF")),
                graphMapReadOnlyObjectProperty,
                codingSequenceMapReadOnlyObjectProperty,
                resistanceMapReadOnlyObjectProperty);

        CompletableFuture<Boolean> sequencesAdded = new CompletableFuture<>();

        graphController.sequences.getChildren().addListener((ListChangeListener<? super Node>) event -> {
            if (graphController.sequences.getChildren().size() == sequencesAmount) {
                sequencesAdded.complete(true);
            }
        });

        LoadService<ResistanceMap> resistanceMapLoadService = new LoadService<>(ResistanceParser.class, resistanceFile);
        Platform.runLater(resistanceMapLoadService::start);

        graphMapReadOnlyObjectProperty.setValue(graphMap);
        codingSequenceMapReadOnlyObjectProperty.setValue(new CodingSequenceMap(Collections.emptyList()));
        Platform.runLater(() -> resistanceMapReadOnlyObjectProperty.bind(resistanceMapLoadService.valueProperty()));

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
