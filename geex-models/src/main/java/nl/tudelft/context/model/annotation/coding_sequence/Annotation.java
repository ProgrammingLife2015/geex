package nl.tudelft.context.model.annotation.coding_sequence;

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
    String seqId;

    /**
     * The source is a free text qualifier intended to describe the algorithm
     * or operating procedure that generated this feature.
     * Typically this is the name of a piece of software, such as "Genescan" or a database name, such as "Genbank."
     * In effect, the source is used to extend the feature ontology
     * by adding a qualifier to the type creating a new composite type
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
     * The start coordinates of the feature are given in positive 1-based integer coordinates,
     * relative to the landmark given in column one.
     * Start is always less than or equal to end.
     * For features that cross the origin of a circular feature
     * (e.g. most bacterial genomes, plasmids, and some viral genomes),
     * the requirement for start to be less than or equal to end is satisfied
     * by making end = the position of the end + the length of the landmark feature.
     * For zero-length features, such as insertion sites,
     * start equals end and the implied site is to the right of the indicated base in the direction of the landmark.
     */
    int start;

    /**
     * the end coordinate, see start.
     */
    int end;


    /**
     * The score of the feature, a floating point number. As in earlier versions of the format,
     * the semantics of the score are ill-defined.
     * It is strongly recommended that E-values be used for sequence similarity features,
     * and that P-values be used for ab initio gene prediction features.
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
     * The phase is one of the integers 0, 1, or 2, indicating the number of bases
     * that should be removed from the beginning of this feature
     * to reach the first base of the next codon.
     * In other words, a phase of "0" indicates that:
     * the next codon begins at the first base of the region described by the current line,
     * a phase of "1" indicates that the next codon begins at the second base of this region,
     * and a phase of "2" indicates that the codon begins at the third base of this region.
     * This is NOT to be confused with the frame, which is simply start modulo 3.
     * For forward strand features, phase is counted from the start field. For reverse strand features,
     * phase is counted from the end field.
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
     * @param seqId      sequence ID for coordinates.
     * @param source     describes which procedure was used to retrieve.
     * @param type       type of the feature.
     * @param start      start position
     * @param end        end position.
     * @param score      score of the feature.
     * @param strand     strand of the feature.
     * @param phase      for CDS, indication if it has reference to read1ing frame.
     * @param attributes list of attributes.
     */
    public Annotation(final String seqId,
                      final String source,
                      final String type,
                      final int start,
                      final int end,
                      final float score,
                      final char strand,
                      final char phase,
                      final String attributes) {
        this.seqId = seqId;
        this.source = source;
        this.type = type;
        this.start = start;
        this.end = end;
        this.score = score;
        this.strand = strand;
        this.phase = phase;
        this.attributes = attributes;
    }

    /**
     * Gets the start position of the annotation.
     *
     * @return The start position
     */
    public int getStart() {
        return start;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Annotation that = (Annotation) o;

        if (start != that.start) {
            return false;
        }
        if (end != that.end) {
            return false;
        }
        if (Float.compare(that.score, score) != 0) {
            return false;
        }
        if (strand != that.strand) {
            return false;
        }
        if (phase != that.phase) {
            return false;
        }
        if (!seqId.equals(that.seqId)) {
            return false;
        }
        if (!source.equals(that.source)) {
            return false;
        }
        if (!type.equals(that.type)) {
            return false;
        }
        return attributes.equals(that.attributes);

    }

    @Override
    public int hashCode() {
        int result = seqId.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + start;
        result = 31 * result + end;
        result = 31 * result + (int) strand;
        result = 31 * result + (int) phase;
        result = 31 * result + attributes.hashCode();
        return result;
    }

    /**
     * Creates an toString for annotation.
     *
     * @return string representing the annotation
     */
    @Override
    public String toString() {
        return "Sequence: " + seqId + System.getProperty("line.separator")
                + "Source: " + source + System.getProperty("line.separator")
                + "Type: " + type + System.getProperty("line.separator")
                + "Start: " + start + System.getProperty("line.separator")
                + "End: " + end + System.getProperty("line.separator")
                + "Score: " + score + System.getProperty("line.separator")
                + "Strand (Sense): " + strand + System.getProperty("line.separator")
                + "Phase: " + phase + System.getProperty("line.separator")
                + "Attributes: " + attributes + System.getProperty("line.separator");
    }

}
