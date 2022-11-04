package stexfires.io.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record RecordRawData(@Nullable String category, @Nullable Long recordId, @NotNull String rawData) {

    public RecordRawData {
        Objects.requireNonNull(rawData);
    }

    public static Optional<RecordRawData> asOptional(@Nullable String category,
                                                     @Nullable Long recordId,
                                                     @Nullable String rawData) {
        return (rawData == null) ? Optional.empty() : Optional.of(new RecordRawData(category, recordId, rawData));
    }

}
