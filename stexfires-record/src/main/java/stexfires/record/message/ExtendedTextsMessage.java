package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class ExtendedTextsMessage<T extends TextRecord> implements RecordMessage<T> {

    private static final int INITIAL_STRING_BUILDER_CAPACITY = 64;

    private final String prefix;
    private final String suffix;
    private final String prefixFirstText;
    private final String suffixLastText;

    public ExtendedTextsMessage(@Nullable String prefix, @Nullable String suffix) {
        this(prefix, suffix, prefix, suffix);
    }

    public ExtendedTextsMessage(@Nullable String prefix, @Nullable String suffix, @Nullable String prefixFirstText, @Nullable String suffixLastText) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.prefixFirstText = prefixFirstText;
        this.suffixLastText = suffixLastText;
    }

    @Override
    public final String createMessage(T record) {
        StringBuilder builder = new StringBuilder(INITIAL_STRING_BUILDER_CAPACITY);

        for (TextField field : record.listOfFields()) {
            if (prefixFirstText != null && field.isFirstField()) {
                builder.append(prefixFirstText);
            } else if (prefix != null && !field.isFirstField()) {
                builder.append(prefix);
            }
            if (!field.isNull()) {
                builder.append(field.text());
            }
            if (suffixLastText != null && field.isLastField()) {
                builder.append(suffixLastText);
            } else if (suffix != null && !field.isLastField()) {
                builder.append(suffix);
            }
        }

        return builder.toString();
    }

}
