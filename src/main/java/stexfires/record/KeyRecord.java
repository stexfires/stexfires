package stexfires.record;

import org.jetbrains.annotations.NotNull;

/**
 * The text of a key field must not be null.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public interface KeyRecord extends TextRecord {

    KeyRecord withKey(@NotNull String key);

    @NotNull Field keyField();

    @SuppressWarnings("ConstantConditions")
    default @NotNull String key() {
        return keyField().text();
    }

}
