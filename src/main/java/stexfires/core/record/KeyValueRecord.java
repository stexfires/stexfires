package stexfires.core.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.core.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
public class KeyValueRecord extends PairRecord implements KeyRecord, ValueRecord {

    public static final int KEY_INDEX = PairRecord.FIRST_VALUE_INDEX;
    public static final int VALUE_INDEX = PairRecord.SECOND_VALUE_INDEX;
    public static final int FIELD_SIZE = PairRecord.FIELD_SIZE;

    public KeyValueRecord(@NotNull String key, @Nullable String value) {
        super(Objects.requireNonNull(key), value);
    }

    public KeyValueRecord(@Nullable String category, @Nullable Long recordId,
                          @NotNull String key, @Nullable String value) {
        super(category, recordId, Objects.requireNonNull(key), value);
    }

    @Override
    public KeyValueRecord newValueRecord(@Nullable String value) {
        return new KeyValueRecord(category(), recordId(), valueOfKeyField(), value);
    }

    @Override
    public final @NotNull Field keyField() {
        return firstField();
    }

    @Override
    public final @NotNull Field valueField() {
        return secondField();
    }

    @Override
    public String toString() {
        return "KeyValueRecord{" +
                "category=" + category() +
                ", recordId=" + recordId() +
                ", key=" + valueOfKeyField() +
                ", value=" + valueOfValueField() +
                '}';
    }

}
