package nl.tudelft.context.controller.message;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 13-6-2015
 */
public enum Message {
    FAIL_LOAD_PREVIOUS("Could not load previous workspaces."),
    FAIL_LOAD_GRAPH("Could not load genome graph."),
    SUCCESS_LOAD_GRAPH("Genome graph loaded successfully."),
    FAIL_LOAD_WORKSPACE("Could not load workspace."),
    SUCCESS_LOAD_WORKSPACE("Workspace loaded successfully."),
    FAIL_LOAD_TREE("Could not load phylogenetic tree."),
    SUCCESS_LOAD_TREE("Phylogenetic tree loaded successfully."),
    FAIL_LOAD_ANNOTATION("Could not load annotations."),
    SUCCESS_LOAD_ANNOTATION("Annotations loaded successfully."),
    FAIL_LOAD_RESISTANCE("Could not load resistance annotations."),
    SUCCESS_LOAD_RESISTANCE("Resistance Annotations loaded successfully."),
    FAIL_LOAD_RECENTWORKSPACE("Could not load recent workspace."),
    MESSAGE_READY("Ready"),
    ;

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
