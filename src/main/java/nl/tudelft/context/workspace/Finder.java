package nl.tudelft.context.workspace;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Glob files in the file system.
 *
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public class Finder extends SimpleFileVisitor<Path> {
    /**
     * The matcher used for matching files.
     */
    private final PathMatcher matcher;

    /**
     * The results of the matching.
     */
    private final List<Path> matches;

    /**
     * Create new finder with pattern.
     * @param pattern The pattern to use when searching
     */
    public Finder(final String pattern) {
        matcher = FileSystems.getDefault()
                .getPathMatcher("glob:" + pattern);

        matches = new ArrayList<>();
    }

    /**
     * Check if file matches this finder.
     * @param file File to match
     */
    final void find(final Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            matches.add(file);
        }
    }

    /**
     * Called by walkfiletree, check if file matches.
     * @param file File to match
     * @param attrs Not used
     * @return CONTINUE
     */
    @Override
    public final FileVisitResult visitFile(final Path file,
                                     final BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    /**
     * Get all matched files.
     * @return Matched files
     */
    public final List<Path> getMatches() {
        return matches;
    }
}
