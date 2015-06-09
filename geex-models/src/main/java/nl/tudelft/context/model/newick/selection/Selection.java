package nl.tudelft.context.model.newick.selection;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 22-5-2015
 */
public interface Selection {

    /**
     * Check if should use the sources of this selection.
     *
     * @return If sources should be used
     */
    boolean useSources();

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
