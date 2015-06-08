package nl.tudelft.context.model.annotation;

/**
 * Annotations for coding part of genome.
 * Descriptions of fields retrieved from http://www.sequenceontology.org/gff3.shtml
 *
 * @author Jasper Nieuwdorp
 * @version 1.1
 * @since 21-5-2015
 */
public class Annotation {


    /**
     * The ID of the landmark used to establish the coordinate system for the current feature.
     * IDs may contain any characters, but must escape any characters not in the set [a-zA-Z0-9.:^*$@!+_?-|].
     * In particular, IDs may not contain unescaped whitespace and must not begin with an unescaped ">".
     */
    String seqid;

    /**
     * The source is a free text qualifier intended to describe the algorithm or operating procedure that generated this feature.
     * Typically this is the name of a piece of software, such as "Genescan" or a database name, such as "Genbank."
     * In effect, the source is used to extend the feature ontology by adding a qualifier to the type creating a new composite type
     * that is a subclass of the type in the type column.
     */
    String source;

    /**
     * The type of the feature (previously called the "method").
     * This is constrained to be either: (a)a term from the "lite" version of the Sequence Ontology
     * - SOFA, a term from the full Sequence Ontology -
     * it must be an is_a child of sequence_feature (SO:0000110) or (c) a SOFA or SO accession number.
     * The latter alternative is distinguished using the syntax SO:000000.
     */
    String type;

    /**
     * The start coordinates of the feature are given in positive 1-based integer coordinates, relative to the landmark given in column one.
     * Start is always less than or equal to end.
     * For features that cross the origin of a circular feature (e.g. most bacterial genomes, plasmids, and some viral genomes),
     * the requirement for start to be less than or equal to end is satisfied by making end = the position of the end + the length of the landmark feature.
     * For zero-length features, such as insertion sites, start equals end and the implied site is to the right of the indicated base in the direction of the landmark.
     */
    int start;

    /**
     * the end coordinate, see start.
     */
    int end;


    /**
     * The score of the feature, a floating point number. As in earlier versions of the format, the semantics of the score are ill-defined.
     * It is strongly recommended that E-values be used for sequence similarity features, and that P-values be used for ab initio gene prediction features.
     */
    float score;

    /**
     * The strand of the feature.
     * + for positive strand (relative to the landmark), - for minus strand, and . for features that are not stranded.
     * In addition, ? can be used for features whose strandedness is relevant, but unknown.
     */
    char strand;

    /**
     * For features of type "CDS", the phase indicates where the feature begins with reference to the reading frame.
     * The phase is one of the integers 0, 1, or 2, indicating the number of bases that should be removed from the beginning of this feature to reach the first base of the next codon.
     * In other words, a phase of "0" indicates that the next codon begins at the first base of the region described by the current line,
     * a phase of "1" indicates that the next codon begins at the second base of this region, and a phase of "2" indicates that the codon begins at the third base of this region.
     * This is NOT to be confused with the frame, which is simply start modulo 3.
     * For forward strand features, phase is counted from the start field. For reverse strand features, phase is counted from the end field.
     * The phase is REQUIRED for all CDS features.
     */
    char phase;


    /**
     * A list of feature attributes in the format tag=value.
     */
    String attributes;

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
     * @return if annotaion is equal to an other annotation
     */
    public final boolean equalsLoose(final Annotation other) {
        return id == other.id;
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
