package stexfires.core.message;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoryMessage<T extends Record> implements RecordMessage<T> {

    private final String nullCategoryValue;

    public CategoryMessage() {
        this(null);
    }

    public CategoryMessage(@Nullable String nullCategoryValue) {
        this.nullCategoryValue = nullCategoryValue;
    }

    @Override
    public final @Nullable String createMessage(@NotNull T record) {
        return record.getCategoryOrElse(nullCategoryValue);
    }

}
