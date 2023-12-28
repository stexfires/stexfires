package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class CategoryMessage<T extends TextRecord> implements RecordMessage<T> {

    private final String nullCategory;

    public CategoryMessage() {
        this(null);
    }

    public CategoryMessage(@Nullable String nullCategory) {
        this.nullCategory = nullCategory;
    }

    @Override
    public final @Nullable String createMessage(T record) {
        return record.categoryAsOptional().orElse(nullCategory);
    }

}
