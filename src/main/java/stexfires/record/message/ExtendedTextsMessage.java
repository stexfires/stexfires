package stexfires.record.message;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ExtendedTextsMessage<T extends TextRecord> implements RecordMessage<T> {

    private static final int INITIAL_STRING_BUILDER_CAPACITY = 64;

    private final String prefix;
    private final String postfix;
    private final String prefixFirstText;
    private final String postfixLastText;

    public ExtendedTextsMessage(@Nullable String prefix, @Nullable String postfix) {
        this(prefix, postfix, prefix, postfix);
    }

    public ExtendedTextsMessage(@Nullable String prefix, @Nullable String postfix, @Nullable String prefixFirstText, @Nullable String postfixLastText) {
        this.prefix = prefix;
        this.postfix = postfix;
        this.prefixFirstText = prefixFirstText;
        this.postfixLastText = postfixLastText;
    }

    @Override
    public final @NotNull String createMessage(T record) {
        StringBuilder builder = new StringBuilder(INITIAL_STRING_BUILDER_CAPACITY);

        for (Field field : record.listOfFields()) {
            if (prefixFirstText != null && field.isFirstField()) {
                builder.append(prefixFirstText);
            } else if (prefix != null && !field.isFirstField()) {
                builder.append(prefix);
            }
            if (!field.isNull()) {
                builder.append(field.text());
            }
            if (postfixLastText != null && field.isLastField()) {
                builder.append(postfixLastText);
            } else if (postfix != null && !field.isLastField()) {
                builder.append(postfix);
            }
        }

        return builder.toString();
    }

}
