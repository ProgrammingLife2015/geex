package nl.tudelft.context.model.newick.selection;

import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 22-5-2015
 */
public class Partial implements Selection {

    @Override
    public void addSource(final Set<String> sources, final String name) {
        // do nothing
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
