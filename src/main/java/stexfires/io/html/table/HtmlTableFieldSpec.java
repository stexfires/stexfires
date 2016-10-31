package stexfires.io.html.table;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class HtmlTableFieldSpec {

    private final String name;

    public HtmlTableFieldSpec() {
        this(null);
    }

    public HtmlTableFieldSpec(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
