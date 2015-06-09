package nl.tudelft.context.model.resistance;

/**
 * Annotation for drug resistance causing mutations.
 * filter is always pass.
 *
 * @author Jasper on 26-5-2015.
 * @version 1.0
 * @since 26-5-2015
 */
public class Resistance {
    /**
     * The name of the gene.
     */
    String geneName;

    /**
     * The type of the mutation that inflicts the resistance.
     */
    String typeOfMutation;

    /**
     * The description of what happens exactly in the mutation.
     */
    String change;

    /**
     * The filter that was used to get the data.
     */
    String filter;

    /**
     * The position in the genome.
     */
    int genomePosition;

    /**
     * The name of the drug for which the mutation causes resistance.
     */
    String drugName;

    /**
     * Create an resistance empty annotation.
     */
    public Resistance() {
        this.geneName = "none";
        this.typeOfMutation = "none";
        this.change = "none";
        this.genomePosition = Integer.MAX_VALUE;
        this.filter = "none";
        this.drugName = "none";

    }

    /**
     * Create an resistance annotation.
     *
     * @param geneName       The name of the gene.
     * @param typeOfMutation The kind of mutation.
     * @param change         The description of what happens exactly in the mutation.
     * @param genomePosition The position in the genome.
     * @param filter         The filter that was used to get the data.
     * @param drugName       The name of the drug for which the mutation causes resistance.
     */
    public Resistance(final String geneName,
                      final String typeOfMutation,
                      final String change,
                      final int genomePosition,
                      final String filter,
                      final String drugName) {
        this.geneName = geneName;
        this.typeOfMutation = typeOfMutation;
        this.change = change;
        this.genomePosition = genomePosition;
        this.filter = filter;
        this.drugName = drugName;

    }

    /**
     * getter for geneName.
     *
     * @return geneName
     */
    public String getGeneName() {
        return geneName;
    }

    /**
     * getter for typeOfMutation.
     *
     * @return typeOfMutation
     */
    public String getTypeOfMutation() {
        return typeOfMutation;
    }

    /**
     * getter for change.
     *
     * @return change
     */
    public String getChange() {
        return change;
    }

    /**
     * getter for genomePosition.
     *
     * @return genomePosition
     */
    public int getGenomePosition() {
        return genomePosition;
    }

    /**
     * getter for drugName.
     *
     * @return drugName
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * getter for filter.
     *
     * @return filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Checks if resistance is equal to an other resistance.
     *
     * @param other the object that should be compared
     * @return if the resitance is equal to an other resistance
     */
    @Override
    public final boolean equals(final Object other) {
        if (other instanceof Resistance) {
            Resistance that = (Resistance) other;
            return geneName.equals(that.geneName)
                    && typeOfMutation.equals(that.typeOfMutation)
                    && change.equals(that.change)
                    && genomePosition == that.genomePosition
                    && drugName.equals(that.drugName);
        }
        return false;
    }

    /**
     * Creates an toString for annotation.
     *
     * @return string representing the annotation
     */
    @Override
    public final String toString() {
        return "("
                + this.getGeneName() + ", "
                + this.getTypeOfMutation() + ", "
                + this.getChange() + ", "
                + this.getGenomePosition() + ", "
                + this.getFilter() + ", "
                + this.getDrugName()
                + ")";
    }

    /**
     * Creates an hashCode for resistance.
     *
     * @return unique hashCode by geneName, genomePosition and drugName
     */
    @Override
    public final int hashCode() {
        return getGeneName().hashCode() + 37 * getDrugName().hashCode() + 37 * 37 * genomePosition;
    }


}
