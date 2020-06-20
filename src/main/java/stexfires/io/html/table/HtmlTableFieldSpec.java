package stexfires.io.html.table;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class HtmlTableFieldSpec {

    private final String name;

    public HtmlTableFieldSpec() {
        this(null);
    }

    public HtmlTableFieldSpec(@Nullable String name) {
        this.name = name;
    }

    public @Nullable String getName() {
        return name;
    }

}
