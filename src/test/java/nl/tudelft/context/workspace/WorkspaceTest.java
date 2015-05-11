package nl.tudelft.context.workspace;

import junit.framework.TestCase;
import nl.tudelft.context.service.LoadGraphService;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 9-5-2015
 */
public class WorkspaceTest extends TestCase {

    public void testNullDirectory() throws Exception {
        Workspace workspace = new Workspace(null);

        assertEquals(null, workspace.directory);
    }

    public void testDirectory() throws Exception {
        File file = new File("mydir");
        Workspace workspace = new Workspace(file);

        assertEquals(file, workspace.directory);
    }

    public void testOneGraph() throws Exception {
        Workspace workspace = new Workspace(null);
        List<Path> pathList = new ArrayList<>();

        Path edgePath = new File("mygraph.edge.graph").toPath();
        Path nodePath = new File("mygraph.node.graph").toPath();

        pathList.add(edgePath);
        pathList.add(nodePath);

        workspace.matches = pathList;

        workspace.load();

        assertEquals(1, workspace.getGraphList().size());
    }

    public void testZeroGraph() throws Exception {
        Workspace workspace = new Workspace(null);
        List<Path> pathList = new ArrayList<>();

        Path edgePath = new File("mygraph.edge.graph").toPath();
        Path nodePath = new File("mygraph2.node.graph").toPath();

        pathList.add(edgePath);
        pathList.add(nodePath);

        workspace.matches = pathList;

        workspace.load();

        assertEquals(0, workspace.getGraphList().size());
    }

    public void testNewick() throws Exception {
        Workspace workspace = new Workspace(null);
        List<Path> pathList = new ArrayList<>();

        Path edgePath = new File("mygraph.nwk").toPath();

        pathList.add(edgePath);

        workspace.matches = pathList;

        workspace.load();

        assertEquals(1, workspace.getNewickList().size());
    }
}