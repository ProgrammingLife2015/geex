package nl.tudelft.context.workspace;

import junit.framework.TestCase;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        File[] pathList = new File[3];

        File edgePath = new File("mygraph.edge.graph");
        File nodePath = new File("mygraph.node.graph");
        File nwkPath = new File("mygraph.nwk");

        pathList[0] = edgePath;
        pathList[1] = nodePath;
        pathList[2] = nwkPath;

        workspace.files = pathList;

        workspace.load();

        assertEquals(nodePath, workspace.getNodeFile());
        assertEquals(edgePath, workspace.getEdgeFile());
        assertEquals(nwkPath, workspace.getNwkFile());
    }

    public void testNotFound() throws Exception {
        Workspace workspace = new Workspace(null);

        workspace.load();

        assertEquals(null, workspace.getNodeFile());
    }
}