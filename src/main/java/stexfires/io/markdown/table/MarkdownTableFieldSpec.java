package stexfires.io.markdown.table;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.util.Alignment;

import java.util.Objects;

import static stexfires.io.markdown.table.MarkdownTableFileSpec.COLUMN_MIN_WIDTH;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record MarkdownTableFieldSpec(
        @NotNull String name,
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

    public MarkdownTableFieldSpec(@NotNull String name, @Nullable Alignment alignment) {
        this(Objects.requireNonNull(name),
                Math.max(COLUMN_MIN_WIDTH, Objects.requireNonNull(name).length()),
                alignment);
    }

    public MarkdownTableFieldSpec(@NotNull String name) {
        this(Objects.requireNonNull(name),
                Math.max(COLUMN_MIN_WIDTH, Objects.requireNonNull(name).length()),
                null);
    }

    int differenceToMinWidth(int width) {
        return Math.max(0, minWidth - width);
    }

    Alignment determineAlignment(MarkdownTableFileSpec fileSpec) {
        return alignment != null ? alignment : fileSpec.consumerAlignment();
    }

}
