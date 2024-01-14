package stexfires.record;

import org.jspecify.annotations.Nullable;

/**
 * @since 0.1
 */
public interface KeyValueRecord extends KeyRecord, ValueRecord {

    KeyValueRecord withKeyAndValue(String key, @Nullable String value);

}
