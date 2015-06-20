package nl.tudelft.context.workspace;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.annotation.AnnotationParser;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.GraphParser;
import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.NewickParser;
import nl.tudelft.context.model.resistance.ResistanceMap;
import nl.tudelft.context.model.resistance.ResistanceParser;
import nl.tudelft.context.service.LoadService;
import nl.tudelft.context.service.Loadable;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Gerben Oolbekkink
 * @version 1.1
 * @since 9-5-2015
 */
@RunWith(value = JfxRunner.class)
public class WorkspaceTest {

    @Test(expected = FileNotFoundException.class)
    public void testNullDirectory() throws Exception {
        new Workspace(null);
    }

    @Test
    public void testDirectory() throws Exception {
        File file = File.createTempFile("mydir", "wp");
        Workspace workspace = new Workspace(file);

        assertEquals(file, workspace.directory);
    }

    @Test(expected = FileNotFoundException.class)
    public void testNoDirectory() throws Exception {
        File file = new File("mydir");
        new Workspace(file);
    }

    @Test
    public void testOneGraph() throws Exception {
        Workspace workspace = new Workspace(File.createTempFile("mydir", "workspace"));
        File[] pathList = new File[5];

        File edgePath = new File("mygraph.edge.graph");
        File nodePath = new File("mygraph.node.graph");
        File nwkPath = new File("mygraph.nwk");
        File annPath = new File("mygraph.gff");
        File immPath = new File("mygraph.txt");

        pathList[0] = edgePath;
        pathList[1] = nodePath;
        pathList[2] = nwkPath;
        pathList[3] = annPath;
        pathList[4] = immPath;

        workspace.files = pathList;

        workspace.load();

        assertEquals(nodePath, workspace.nodeFile);
        assertEquals(edgePath, workspace.edgeFile);
        assertEquals(nwkPath, workspace.nwkFile);
        assertEquals(annPath, workspace.codingSequenceFile);
        assertEquals(immPath, workspace.resistanceFile);
    }

    @Test(expected = FileNotFoundException.class)
    public void testNotFound() throws Exception {
        Workspace workspace = new Workspace(null);

        workspace.load();
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadNoAnnotationFile() throws Exception {
        Workspace workspace1 = new Workspace(null);
        File[] pathList = new File[5];

        File edgePath1 = new File("mygraph.edge.graph");
        File nodePath1 = new File("mygraph.node.graph");
        File nwkPath1 = new File("mygraph.nwk");
        File annPath1 = new File("not");
        File immPath1 = new File("mygraph.txt");

        pathList[0] = edgePath1;
        pathList[1] = nodePath1;
        pathList[2] = nwkPath1;
        pathList[3] = annPath1;
        pathList[4] = immPath1;

        workspace1.files = pathList;

        workspace1.load();
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadNoNwkFile() throws Exception {
        Workspace workspace1 = new Workspace(null);
        File[] pathList = new File[5];

        File edgePath1 = new File("mygraph.edge.graph");
        File nodePath1 = new File("mygraph.node.graph");
        File nwkPath1 = new File("not");
        File annPath1 = new File("mygraph.gff");
        File immPath1 = new File("mygraph.txt");

        pathList[0] = edgePath1;
        pathList[1] = nodePath1;
        pathList[2] = nwkPath1;
        pathList[3] = annPath1;
        pathList[4] = immPath1;

        workspace1.files = pathList;

        workspace1.load();

    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadNoNodeFile() throws Exception {
        Workspace workspace1 = new Workspace(null);
        File[] pathList = new File[5];

        File edgePath1 = new File("mygraph.edge.graph");
        File nodePath1 = new File("not");
        File nwkPath1 = new File("mygraph.nwk");
        File annPath1 = new File("mygraph.gff");
        File immPath1 = new File("mygraph.txt");

        pathList[0] = edgePath1;
        pathList[1] = nodePath1;
        pathList[2] = nwkPath1;
        pathList[3] = annPath1;
        pathList[4] = immPath1;

        workspace1.files = pathList;
        workspace1.load();

    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadNoEdgeFile() throws Exception {
        Workspace workspace1 = new Workspace(null);
        File[] pathList = new File[4];

        File edgePath1 = new File("not");
        File nodePath1 = new File("mygraph.node.graph");
        File nwkPath1 = new File("mygraph.nwk");
        File annPath1 = new File("mygraph.gff");

        pathList[0] = edgePath1;
        pathList[1] = nodePath1;
        pathList[2] = nwkPath1;
        pathList[3] = annPath1;

        workspace1.files = pathList;

        workspace1.load();

    }

    @Test
    public void testPreload() throws Exception {
        Workspace workspace = new Workspace(File.createTempFile("favourite", "dir"));

        workspace.loadNewickService = mock(LoadService.class);
        workspace.loadAnnotationService = mock(LoadService.class);
        workspace.loadGraphService = mock(LoadService.class);
        workspace.loadResistanceService = mock(LoadService.class);

        workspace.preload();

        verify(workspace.loadNewickService).start();
        verify(workspace.loadAnnotationService).start();
        verify(workspace.loadGraphService).start();
        verify(workspace.loadResistanceService).start();
    }

    @Test
    public void testClose() throws Exception {
        Workspace workspace = new Workspace(File.createTempFile("favourite", "dir"));

        workspace.loadNewickService = mock(LoadService.class);
        workspace.loadAnnotationService = mock(LoadService.class);
        workspace.loadGraphService = mock(LoadService.class);
        workspace.loadResistanceService = mock(LoadService.class);

        workspace.close();

        verify(workspace.loadNewickService).cancel();
        verify(workspace.loadAnnotationService).cancel();
        verify(workspace.loadGraphService).cancel();
        verify(workspace.loadResistanceService).cancel();
    }

    @Test
    public void testProperties() throws Exception {
        Workspace workspace = new Workspace(File.createTempFile("favourite", "dir"));

        workspace.loadNewickService = new LoadService<>(NewickParser.class);
        workspace.loadAnnotationService = new LoadService<>(AnnotationParser.class);
        workspace.loadGraphService = new LoadService<>(GraphParser.class);
        workspace.loadResistanceService = new LoadService<>(ResistanceParser.class);

        assertNull(workspace.getNewick().get());
        assertNull(workspace.getAnnotation().get());
        assertNull(workspace.getGraph().get());
        assertNull(workspace.getResistance().get());
    }

}
