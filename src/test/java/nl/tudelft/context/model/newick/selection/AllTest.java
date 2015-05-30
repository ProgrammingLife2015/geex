package nl.tudelft.context.model.newick.selection;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 30-05-2015
 */
public class AllTest {
    /**
     * The selection All.
     */
    Selection selection = new All();

    /**
     * Sources should be used.
     */
    @Test
    public void testUseSources() {
        assertTrue(selection.useSources());
    }

    /**
     * The selection contains at least one item.
     */
    @Test
    public void testIsAny() {
        assertTrue(selection.isAny());
    }

    /**
     * The selection toggles to None.
     */
    @Test
    public void testToggle() {
        assertThat(selection.toggle(), instanceOf(None.class));
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
        assertThat(selection.merge(new All()), instanceOf(All.class));
    }

    /**
     * Should merge into Partial.
     */
    @Test
    public void testMergeNone() {
        assertThat(selection.mergeNone(), instanceOf(Partial.class));
    }

    /**
     * Should merge into All.
     */
    @Test
    public void testMergeAll() {
        assertThat(selection.mergeAll(), instanceOf(All.class));
    }

    /**
     * StyleClass is <tt>selected</tt>.
     */
    @Test
    public void testStyleClass() {
        assertEquals("selected", selection.styleClass());
    }
}