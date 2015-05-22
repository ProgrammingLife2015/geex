package nl.tudelft.context.workspace;

import junit.framework.TestCase;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.1
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
        File[] pathList = new File[4];

        File edgePath = new File("mygraph.edge.graph");
        File nodePath = new File("mygraph.node.graph");
        File nwkPath = new File("mygraph.nwk");
        File annPath = new File ("mygraph.ann.csv");

        pathList[0] = edgePath;
        pathList[1] = nodePath;
        pathList[2] = nwkPath;
        pathList[3] = annPath;

        workspace.files = pathList;

        workspace.load();

        assertEquals(nodePath, workspace.getNodeFile());
        assertEquals(edgePath, workspace.getEdgeFile());
        assertEquals(nwkPath, workspace.getNwkFile());
        assertEquals(annPath, workspace.getAnnotationFile());
    }

    public void testNotFound() throws Exception {
        Workspace workspace = new Workspace(null);

        workspace.load();

        assertEquals(null, workspace.getNodeFile());
        assertEquals(null, workspace.getAnnotationFile());
    }

    public void testLoadNoAnnotationFile() {
        Workspace workspace1 = new Workspace(null);
        File[] pathList = new File[4];

        File edgePath1 = new File("mygraph.edge.graph");
        File nodePath1 = new File("mygraph.node.graph");
        File nwkPath1 = new File("mygraph.nwk");
        File annPath1 = new File ("not");

        pathList[0] = edgePath1;
        pathList[1] = nodePath1;
        pathList[2] = nwkPath1;
        pathList[3] = annPath1;

        workspace1.files = pathList;
        assertFalse(workspace1.load());

    }

    public void testLoadNoNwkFile() {
        Workspace workspace1 = new Workspace(null);
        File[] pathList = new File[4];

        File edgePath1 = new File("mygraph.edge.graph");
        File nodePath1 = new File("mygraph.node.graph");
        File nwkPath1 = new File("not");
        File annPath1 = new File ("mygraph.ann.csv");

        pathList[0] = edgePath1;
        pathList[1] = nodePath1;
        pathList[2] = nwkPath1;
        pathList[3] = annPath1;

        workspace1.files = pathList;
        assertFalse(workspace1.load());

    }

    public void testLoadNoNodeFile() {
        Workspace workspace1 = new Workspace(null);
        File[] pathList = new File[4];

        File edgePath1 = new File("mygraph.edge.graph");
        File nodePath1 = new File("not");
        File nwkPath1 = new File("mygraph.nwk");
        File annPath1 = new File ("mygraph.ann.csv");

        pathList[0] = edgePath1;
        pathList[1] = nodePath1;
        pathList[2] = nwkPath1;
        pathList[3] = annPath1;

        workspace1.files = pathList;
        assertFalse(workspace1.load());

    }

    public void testLoadNoEdgeFile() {
        Workspace workspace1 = new Workspace(null);
        File[] pathList = new File[4];

        File edgePath1 = new File("not");
        File nodePath1 = new File("mygraph.node.graph");
        File nwkPath1 = new File("mygraph.nwk");
        File annPath1 = new File ("mygraph.ann.csv");

        pathList[0] = edgePath1;
        pathList[1] = nodePath1;
        pathList[2] = nwkPath1;
        pathList[3] = annPath1;

        workspace1.files = pathList;
        assertFalse(workspace1.load());

    }

}