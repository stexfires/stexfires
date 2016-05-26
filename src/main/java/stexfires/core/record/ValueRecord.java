package stexfires.core.record;

import stexfires.core.Field;
import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ValueRecord extends Record {

    ValueRecord newValueRecord(String value);

    Field getValueField();

    default String getValueOfValueField() {
        return getValueField().getValue();
    }

}
