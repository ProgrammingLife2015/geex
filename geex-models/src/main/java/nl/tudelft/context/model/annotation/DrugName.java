package nl.tudelft.context.model.annotation;

/**
 * This enum contains all possible DrugNames a bacteria can be resistant to.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 22-6-2015
 */
public enum DrugName {
    /**
     * rifampicin.
     */
    R("rifampicin"),
    /**
     * ethionomide.
     */
    T("ethionomide"),
    /**
     * ethionomide.
     */
    M("ethionomide"),
    /**
     * isoniazid.
     */
    I("isoniazid"),
    /**
     * ofloxacin.
     */
    O("ofloxacin"),
    /**
     * streptomycin.
     */
    S("streptomycin"),
    /**
     * kanamycin.
     */
    K("kanamycin"),
    /**
     * pyrazinamide.
     */
    P("pyrazinamide"),
    /**
     * ethambutol.
     */
    E("ethambutol");

    /**
     * The name of this durg.
     */
    private final String drug;

    /**
     * Create a drugName enum.
     *
     * @param drug Name of the drug.
     */
    DrugName(final String drug) {
        this.drug = drug;
    }

    @Override
    public String toString() {
        return drug;
    }
}
