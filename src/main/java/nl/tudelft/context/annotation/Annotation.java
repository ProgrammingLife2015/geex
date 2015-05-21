package nl.tudelft.context.annotation;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class Annotation {

    /**
     * The id of the annotation (from Tabel Browser, wrongfully labeled score).
     */
    int id;

    /**
     * Name of the genome.
     */
    String name;

    /**
     * Positve (true) or Negative (false) strand (Sense and Anti-Sense).
     */
    boolean strand;

    /**
     * Transcription start position.
     */
    int start;

    /**
     * Transcription end position.
     */
    int end;

    /**
     * Name of the protein.
     */
    String proteinName;

    /**
     * Create an annotation.
     *
     * @param id          The id of the annotation (from Tabel Browser, wrongfully labeled score).
     * @param name        Name of the genome.
     * @param strand      Positve (true) or Negative (false) strand (Sense and Anti-Sense).
     * @param start       Transcription start position.
     * @param end         Transcription end position.
     * @param proteinName Name of the protein.
     */
    public Annotation(final int id,
                      final String name,
                      final Boolean strand,
                      final int start,
                      final int end,
                      final String proteinName) {
        this.id = id;
        this.name = name;
        this.strand = strand;
        this.start = start;
        this.end = end;
        this.proteinName = proteinName;

    }

    /**
     * Getter for id.
     *
     * @return id
     */
    public final int getId() {
        return id;
    }

    /**
     * Getter for name.
     *
     * @return name
     */
    public final String getName() {
        return name;
    }

    /**
     * Getter for strand.
     *
     * @return strand
     */
    public final Boolean getStrand() {
        return strand;
    }

    /**
     * Getter for start.
     *
     * @return start
     */
    public final int getStart() {
        return start;
    }

    /**
     * Getter for end.
     *
     * @return end
     */
    public final int getEnd() {
        return end;
    }

    /**
     * Getter for proteinName.
     *
     * @return proteinName
     */
    public final String getProteinName() {
        return proteinName;
    }

    /**
     * Checks if annotation is equal to an other annotation.
     * This is the strict equals, for only checking id use equalsLoose
     *
     * @param other the object that should be compared
     * @return if node is equal to an other node
     */
    @Override
    public final boolean equals(final Object other) {
        if (other instanceof Annotation) {
            Annotation that = (Annotation) other;
            return id == that.id
                    && name.equals(that.name)
                    && strand == that.strand
                    && start == that.start
                    && end == that.end
                    && proteinName.equals(that.proteinName);
        }
        return false;
    }

    /**
     * Checks if annotation id is equal to an other annotation id.
     * Only checks id, for comprehensive comparison use equals
     *
     * @param other the object that should be compared
     * @return if node is equal to an other node
     */
    public final boolean equalsLoose(final Object other) {
        if (other instanceof Annotation) {
            Annotation that = (Annotation) other;
            return id == that.id;
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
                + this.getId() + ", "
                + this.getName() + ", "
                + this.getStrand() + ", "
                + this.getStart() + ", "
                + this.getEnd() + ", "
                + this.getProteinName()
                + ")";
    }

    /**
     * Creates an hashCode for annotation.
     *
     * @return unique hashCode by id
     */
    @Override

    public final int hashCode() {
        return this.id;
    }

}
