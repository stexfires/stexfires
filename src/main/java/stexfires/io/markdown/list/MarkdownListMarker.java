package stexfires.io.markdown.list;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum MarkdownListMarker {

    BULLET_ASTERISK("*", false),
    BULLET_HYPHEN_MINUS("-", false),
    BULLET_PLUS_SIGN("+", false),
    ORDERED_PERIOD(".", true),
    ORDERED_PARENTHESIS(")", true);

    private final String markerCharacter;
    private final boolean ordered;

    MarkdownListMarker(String markerCharacter, boolean ordered) {
        this.markerCharacter = markerCharacter;
        this.ordered = ordered;
    }

    public final boolean isOrdered() {
        return ordered;
    }

    public final String linePrefix(long lineNumber) {
        if (this.ordered) {
            return lineNumber + markerCharacter + MarkdownListFileSpec.LIST_MARKER_SEPARATOR;
        } else {
            return markerCharacter + MarkdownListFileSpec.LIST_MARKER_SEPARATOR;
        }
    }

}
