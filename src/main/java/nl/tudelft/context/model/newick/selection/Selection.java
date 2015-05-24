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
     * Style class of current selection.
     *
     * @return Style class
     */
    String styleClass();

}
