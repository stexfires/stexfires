package stexfires.core.filter;

import stexfires.core.record.ValueRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueEqualsFilter<T extends ValueRecord> implements RecordFilter<T> {

    protected final String value;

    public ValueEqualsFilter(String value) {
        this.value = value;
    }

    @Override
    public boolean isValid(T record) {
        return Objects.equals(record.getValueOfValueField(), value);
    }

}
