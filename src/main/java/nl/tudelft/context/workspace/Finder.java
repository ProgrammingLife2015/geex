package nl.tudelft.context.workspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.*;

/**
 * Glob files in the file system.
 *
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public class Finder extends SimpleFileVisitor<Path> {
    private final PathMatcher matcher;

    private final List<Path> matches;

    public Finder(String pattern) {
        matcher = FileSystems.getDefault()
                .getPathMatcher("glob:" + pattern);

        matches = new ArrayList<>();
    }

    void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            matches.add(file);
        }
    }

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    public List<Path> getMatches() {
        return matches;
    }
}
