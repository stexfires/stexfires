package stexfires.io.markdown.table;

import org.jetbrains.annotations.Nullable;
import stexfires.util.Alignment;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record MarkdownTableFieldSpec(@Nullable String name, int minWidth, @Nullable Alignment alignment) {

    public MarkdownTableFieldSpec {
        if (minWidth < MarkdownTableFileSpec.COLUMN_MIN_WIDTH) {
            throw new IllegalArgumentException("minWidth < " + MarkdownTableFileSpec.COLUMN_MIN_WIDTH);
        }
    }

}
