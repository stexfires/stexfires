package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface KeyValueRecord extends KeyRecord, ValueRecord {

    @NotNull KeyValueRecord withKeyAndValue(@NotNull String key, @Nullable String value);

}
