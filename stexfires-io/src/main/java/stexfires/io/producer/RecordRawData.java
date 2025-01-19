package stexfires.io.producer;

import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * @since 0.1
 */
public record RecordRawData(@Nullable String category, @Nullable Long recordId, String rawData) {

    public RecordRawData {
        Objects.requireNonNull(rawData);
    }

    public static Optional<RecordRawData> buildOptionalRecordRawData(@Nullable String category,
                                                                     @Nullable Long recordId,
                                                                     @Nullable String rawData) {
        return (rawData == null) ? Optional.empty() : Optional.of(new RecordRawData(category, recordId, rawData));
    }

}
