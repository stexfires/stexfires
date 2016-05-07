package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ExtendedValuesMessage implements RecordMessage<Record> {

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
    public String createMessage(Record record) {
        StringBuilder b = new StringBuilder();

        for (Field field : record.listOfFields()) {
            if (prefixFirstValue != null && field.isFirst()) {
                b.append(prefixFirstValue);
            } else if (prefix != null && !field.isFirst()) {
                b.append(prefix);
            }
            if (!field.valueIsNull()) {
                b.append(field.getValue());
            }
            if (postfixLastValue != null && field.isLast()) {
                b.append(postfixLastValue);
            } else if (postfix != null && !field.isLast()) {
                b.append(postfix);
            }
        }

        return b.toString();
    }

}
