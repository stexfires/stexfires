package stexfires.io.html.table;

import org.jetbrains.annotations.Nullable;

/**
 * @since 0.1
 */
public record HtmlTableFieldSpec(
        @Nullable String name
) {

    public HtmlTableFieldSpec() {
        this(null);
    }

}
