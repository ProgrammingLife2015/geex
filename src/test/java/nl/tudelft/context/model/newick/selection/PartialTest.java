package nl.tudelft.context.model.newick.selection;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 30-05-2015
 */
public class PartialTest {
    /**
     * The selection Partial.
     */
    Selection selection = new Partial();

    /**
     * Sources should not be used.
     */
    @Test
    public void testAddSource() {
        Set<String> set = new HashSet<>();
        selection.addSource(set, "bla");
        assertTrue(set.isEmpty());
    }

    /**
     * The selection contains at least one item.
     */
    @Test
    public void testIsAny() {
        assertTrue(selection.isAny());
    }

    /**
     * The selection toggles to All.
     */
    @Test
    public void testToggle() {
        assertThat(selection.toggle(), instanceOf(All.class));
    }

    /**
     * The selection should merge according to the following table.
     *
     *         | None     Partial  All
     * --------|--------------------------
     *    None | None     Partial  Partial
     * Partial | Partial  Partial  Partial
     *     All | Partial  Partial  All
     */
    @Test
    public void testMerge() {
        assertThat(selection.merge(new None()), instanceOf(Partial.class));
        assertThat(selection.merge(new Partial()), instanceOf(Partial.class));
        assertThat(selection.merge(new All()), instanceOf(Partial.class));
    }

    /**
     * Should merge into Partial.
     */
    @Test
    public void testMergeNone() {
        assertThat(selection.mergeNone(), instanceOf(Partial.class));
    }

    /**
     * Should merge into Partial.
     */
    @Test
    public void testMergeAll() {
        assertThat(selection.mergeAll(), instanceOf(Partial.class));
    }

    /**
     * StyleClass is <tt>partial</tt>.
     */
    @Test
    public void testStyleClass() {
        assertEquals("partial", selection.styleClass());
    }
}