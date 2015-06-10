package nl.tudelft.context.model.newick.selection;

import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 22-5-2015
 */
public interface Selection {

    /**
     * Adds the name to the sources, dependent on the class.
     *
     * @param sources The set with sources
     * @param name    The potentially new source
     */
    void addSource(final Set<String> sources, final String name);

    /**
     * Checks if the selection includes at least one element.
     *
     * @return true if at least one element is selected; otherwise false.
     */
    boolean isAny();

    /**
     * Toggle the selection.
     *
     * @return New selection
     */
    Selection toggle();

    /**
     * Merge this selection with an other selection.
     *
     * @param selection Selection to merge with
     * @return The new selection
     */
    Selection merge(Selection selection);

    /**
     * Merge this selection with None.
     *
     * @return The new selection
     */
    Selection mergeNone();

    /**
     * Merge this selection with All.
     *
     * @return The new selection
     */
    Selection mergeAll();

    /**
     * Style class of current selection.
     *
     * @return Style class
     */
    String styleClass();

}
