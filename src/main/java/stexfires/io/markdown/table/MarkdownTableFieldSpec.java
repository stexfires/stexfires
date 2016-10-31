package stexfires.io.markdown.table;

import stexfires.util.Alignment;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownTableFieldSpec {

    private final String name;
    private final int minWidth;
    private final Alignment alignment;

    public MarkdownTableFieldSpec(String name, int minWidth) {
        this(name, minWidth, null);
    }

    public MarkdownTableFieldSpec(String name, int minWidth, Alignment alignment) {
        if (minWidth < 5) {
            throw new IllegalArgumentException("minWidth < 5");
        }
        this.name = name;
        this.minWidth = minWidth;
        this.alignment = alignment;
    }

    public String getName() {
        return name;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public Alignment getAlignment() {
        return alignment;
    }

}
