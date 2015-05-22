package nl.tudelft.context.newick.selection;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 22-5-2015
 */
public abstract class Selection {

    /**
     * Check if should use the sources of this selection.
     *
     * @return If sources should be used
     */
    public abstract boolean useSources();

    /**
     * Toggle the selection.
     *
     * @return New selection
     */
    public abstract Selection toggle();

    /**
     * Merge this selection with an other selection.
     *
     * @param selection Selection to merge with
     * @return The new selection
     */
    public abstract Selection merge(Selection selection);

    /**
     * Style class of current selection.
     *
     * @return Style class
     */
    public abstract String styleClass();

}
