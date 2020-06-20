package stexfires.io.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordRawData {

    private final String category;
    private final Long recordId;
    private final String rawData;

    public RecordRawData(@NotNull String rawData) {
        this(null, null, rawData);
    }

    public RecordRawData(@Nullable String category, @Nullable Long recordId, @NotNull String rawData) {
        Objects.requireNonNull(rawData);
        this.category = category;
        this.recordId = recordId;
        this.rawData = rawData;
    }

    public @Nullable String getCategory() {
        return category;
    }

    public @Nullable Long getRecordId() {
        return recordId;
    }

    public @NotNull String getRawData() {
        return rawData;
    }

    @Override
    public String toString() {
        return "RecordRawData{" +
                "category='" + category + '\'' +
                ", recordId=" + recordId +
                ", rawData='" + rawData + '\'' +
                '}';
    }

}
