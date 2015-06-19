package nl.tudelft.context.workspace;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Gerben Oolbekkink
 * @version 1.1
 * @since 9-5-2015
 */
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

}
