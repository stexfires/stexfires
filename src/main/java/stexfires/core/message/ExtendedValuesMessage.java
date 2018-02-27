package stexfires.core.message;

import stexfires.core.Field;
import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ExtendedValuesMessage<T extends Record> implements RecordMessage<T> {

    private static final int INITIAL_STRING_BUILDER_CAPACITY = 64;

    protected final String prefix;
    protected final String postfix;
    protected final String prefixFirstValue;
    protected final String postfixLastValue;

    public ExtendedValuesMessage(String prefix, String postfix) {
        this(prefix, postfix, prefix, postfix);
    }

    public ExtendedValuesMessage(String prefix, String postfix, String prefixFirstValue, String postfixLastValue) {
        this.prefix = prefix;
        this.postfix = postfix;
        this.prefixFirstValue = prefixFirstValue;
        this.postfixLastValue = postfixLastValue;
    }

    @Override
    public String createMessage(T record) {
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
