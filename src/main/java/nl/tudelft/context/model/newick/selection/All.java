package nl.tudelft.context.model.newick.selection;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 22-5-2015
 */
public class All implements Selection {

    @Override
    public boolean useSources() {
        return true;
    }

    @Override
    public boolean isAny() {
        return true;
    }

    @Override
    public Selection toggle() {
        return new None();
    }

    @Override
    public Selection merge(final Selection selection) {
        return selection.mergeAll();
    }

    @Override
    public Selection mergeNone() {
        return new Partial();
    }

    @Override
    public Selection mergeAll() {
        return this;
    }

    @Override
    public String styleClass() {
        return "selected";
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof All;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
