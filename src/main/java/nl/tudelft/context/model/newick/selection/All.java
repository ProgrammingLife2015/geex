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
    public Selection toggle() {
        return new None();
    }

    @Override
    public Selection merge(final Selection selection) {
        if (selection instanceof All) {
            return this;
        }
        return new Partial();
    }

    @Override
    public String styleClass() {
        return "selected";
    }

}
