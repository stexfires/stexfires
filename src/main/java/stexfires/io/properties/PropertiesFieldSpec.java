package stexfires.io.properties;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesFieldSpec {

    private final String readNullReplacement;
    private final String writeNullReplacement;

    public PropertiesFieldSpec(@Nullable String readNullReplacement, @NotNull String writeNullReplacement) {
        Objects.requireNonNull(writeNullReplacement);
        this.readNullReplacement = readNullReplacement;
        this.writeNullReplacement = writeNullReplacement;
    }

    public @Nullable String getReadNullReplacement() {
        return readNullReplacement;
    }

    public @NotNull String getWriteNullReplacement() {
        return writeNullReplacement;
    }

}
