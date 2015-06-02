package nl.tudelft.context.model.newick.selection;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 22-5-2015
 */
public class Partial implements Selection {

    @Override
    public boolean useSources() {
        return false;
    }

    @Override
    public boolean isAny() {
        return true;
    }

    @Override
    public Selection toggle() {
        return new All();
    }

    @Override
    public Selection merge(final Selection selection) {
        return this;
    }

    @Override
    public Selection mergeNone() {
        return this;
    }

    @Override
    public Selection mergeAll() {
        return this;
    }

    @Override
    public String styleClass() {
        return "partial";
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Partial;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
