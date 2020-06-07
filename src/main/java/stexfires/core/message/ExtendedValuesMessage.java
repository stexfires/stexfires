package stexfires.core.message;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.core.Field;
import stexfires.core.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ExtendedValuesMessage<T extends TextRecord> implements RecordMessage<T> {

    private static final int INITIAL_STRING_BUILDER_CAPACITY = 64;

    private final String prefix;
    private final String postfix;
    private final String prefixFirstValue;
    private final String postfixLastValue;

    public ExtendedValuesMessage(@Nullable String prefix, @Nullable String postfix) {
        this(prefix, postfix, prefix, postfix);
    }

    public ExtendedValuesMessage(@Nullable String prefix, @Nullable String postfix, @Nullable String prefixFirstValue, @Nullable String postfixLastValue) {
        this.prefix = prefix;
        this.postfix = postfix;
        this.prefixFirstValue = prefixFirstValue;
        this.postfixLastValue = postfixLastValue;
    }

    @Override
    public final @NotNull String createMessage(T record) {
        StringBuilder builder = new StringBuilder(INITIAL_STRING_BUILDER_CAPACITY);

        for (Field field : record.listOfFields()) {
            if (prefixFirstValue != null && field.isFirst()) {
                builder.append(prefixFirstValue);
            } else if (prefix != null && !field.isFirst()) {
                builder.append(prefix);
            }
            if (!field.valueIsNull()) {
                builder.append(field.getValue());
            }
            if (postfixLastValue != null && field.isLast()) {
                builder.append(postfixLastValue);
            } else if (postfix != null && !field.isLast()) {
                builder.append(postfix);
            }
        }

        return builder.toString();
    }

}
