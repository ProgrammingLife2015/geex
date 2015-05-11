package nl.tudelft.context.workspace;

import junit.framework.TestCase;
import org.mockito.Mock;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 9-5-2015
 */
public class FinderTest extends TestCase {
    public void testFind() throws Exception {

        Finder finder = new Finder("*.java");
        Path myPath = new File("test.java").toPath();
        finder.find(myPath);

        assertEquals(1, finder.getMatches().size());

        assertEquals(myPath, finder.getMatches().get(0));
    }

    public void testFindNothing() throws Exception {

        Finder finder = new Finder("*.java");
        Path myPath = new File("test.jav").toPath();
        finder.find(myPath);

        assertEquals(0, finder.getMatches().size());
    }

    public void testVisitFile() throws Exception {
        Finder finder = new Finder("*.java");
        FileVisitResult fvr = finder.visitFile(new File("test.java").toPath(), null);

        assertEquals(1, finder.getMatches().size());

        assertEquals(FileVisitResult.CONTINUE, fvr);
    }

    public void testNullFile() throws Exception {
        Finder finder = new Finder("*.java");
        Path path = mock(Path.class);
        doReturn(null).when(path).getFileName();

        finder.find(path);

        assertEquals(0, finder.getMatches().size());
    }
}
