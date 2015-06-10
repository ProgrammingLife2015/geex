package nl.tudelft.context.model.newick.selection;

import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 22-5-2015
 */
public class None implements Selection {

    @Override
    public void addSource(final Set<String> sources, final String name) {
        // do nothing
    }

    @Override
    public boolean isAny() {
        return false;
    }

    @Override
    public Selection toggle() {
        return new All();
    }

    @Override
    public Selection merge(final Selection selection) {
        return selection.mergeNone();
    }

    @Override
    public Selection mergeNone() {
        return this;
    }

    @Override
    public Selection mergeAll() {
        return new Partial();
    }

    @Override
    public String styleClass() {
        return "none";
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof None;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
