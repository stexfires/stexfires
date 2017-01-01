package stexfires.io.properties;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesFieldSpec {

    private final String readNullReplacement;
    private final String writeNullReplacement;

    public PropertiesFieldSpec() {
        this(null, null);
    }

    public PropertiesFieldSpec(String readNullReplacement, String writeNullReplacement) {
        this.readNullReplacement = readNullReplacement;
        this.writeNullReplacement = writeNullReplacement;
    }

    public String getReadNullReplacement() {
        return readNullReplacement;
    }

    public String getWriteNullReplacement() {
        return writeNullReplacement;
    }

}
