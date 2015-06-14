package nl.tudelft.context.logger.message;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 13-6-2015
 */
public enum Message {
    /**
     * Message used when loading previous workspaces fails.
     */
    FAIL_LOAD_PREVIOUS("Could not load previous workspaces."),

    /**
     * Message used when graph loading fails.
     */
    FAIL_LOAD_GRAPH("Could not load genome graph."),

    /**
     * Message used when graph loading succeeds.
     */
    SUCCESS_LOAD_GRAPH("Genome graph loaded successfully."),

    /**
     * Message used when workspace loading fails.
     */
    FAIL_LOAD_WORKSPACE("Could not load workspace."),

    /**
     * Message used when workspace loading succeeds.
     */
    SUCCESS_LOAD_WORKSPACE("Workspace loaded successfully."),

    /**
     * Message used when tree loading fails.
     */
    FAIL_LOAD_TREE("Could not load phylogenetic tree."),

    /**
     * Message used when tree loading succeeds.
     */
    SUCCESS_LOAD_TREE("Phylogenetic tree loaded successfully."),

    /**
     * Message used when annotation loading fails.
     */
    FAIL_LOAD_ANNOTATION("Could not load annotations."),

    /**
     * Message used when annotation loading succeeds.
     */
    SUCCESS_LOAD_ANNOTATION("Annotations loaded successfully."),
    /**
     * The text that is shown.
     */

    /**
     * Message used when resistance loading fails.
     */
    FAIL_LOAD_RESISTANCE("Could not load resistance annotations."),

    /**
     * Message used when annotation loading succeeds.
     */
    SUCCESS_LOAD_RESISTANCE("Resistance Annotations loaded successfully."),

    /**
     * Message used when annotation loading succeeds.
     */
    FAIL_LOAD_RECENTWORKSPACE("Could not load recent workspace."),

    /**
     * Message used when the application is ready.
     */
    MESSAGE_READY("Ready"),

    /**
     * Message used to show debugging is enabled.
     */
    DEBUGGER_READY("Debug logger enabled.");

    /**
     * Message string in this message.
     */
    private String message;

    /**
     * Create a new Message.
     *
     * @param message Message string
     */
    Message(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
