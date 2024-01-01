package stexfires.io.markdown.table;

import org.jspecify.annotations.Nullable;
import stexfires.util.Alignment;

import java.util.Objects;

import static stexfires.io.markdown.table.MarkdownTableFileSpec.COLUMN_MIN_WIDTH;

/**
 * @since 0.1
 */
public record MarkdownTableFieldSpec(
        String name,
        int minWidth,
        @Nullable Alignment alignment
) {

    public MarkdownTableFieldSpec {
        Objects.requireNonNull(name);
        if (minWidth < COLUMN_MIN_WIDTH) {
            throw new IllegalArgumentException("minWidth < " + COLUMN_MIN_WIDTH);
        }
        if (minWidth < name.length()) {
            throw new IllegalArgumentException("minWidth < name length");
        }
    }

    public MarkdownTableFieldSpec(String name, @Nullable Alignment alignment) {
        this(Objects.requireNonNull(name),
                Math.max(COLUMN_MIN_WIDTH, Objects.requireNonNull(name).length()),
                alignment);
    }

    public MarkdownTableFieldSpec(String name) {
        this(Objects.requireNonNull(name),
                Math.max(COLUMN_MIN_WIDTH, Objects.requireNonNull(name).length()),
                null);
    }

    int differenceToMinWidth(int width) {
        return Math.max(0, minWidth - width);
    }

    Alignment determineAlignment(MarkdownTableFileSpec fileSpec) {
        Objects.requireNonNull(fileSpec);
        return alignment != null ? alignment : fileSpec.consumerAlignment();
    }

}
