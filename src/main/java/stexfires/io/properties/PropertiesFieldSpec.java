package stexfires.io.properties;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record PropertiesFieldSpec(@Nullable String readNullReplacement, String writeNullReplacement) {

    public PropertiesFieldSpec {
        Objects.requireNonNull(writeNullReplacement);
    }

}
