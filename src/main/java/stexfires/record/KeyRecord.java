package stexfires.record;

import org.jetbrains.annotations.NotNull;

/**
 * The text of a key field must not be null.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public interface KeyRecord extends TextRecord {

    @NotNull KeyRecord withKey(@NotNull String key);

    @NotNull TextField keyField();

    int keyIndex();

    @SuppressWarnings("DataFlowIssue")
    default @NotNull String key() {
        return keyField().text();
    }

}
