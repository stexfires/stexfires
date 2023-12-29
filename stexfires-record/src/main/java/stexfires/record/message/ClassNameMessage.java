package stexfires.record.message;

import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class ClassNameMessage<T extends TextRecord> implements NotNullRecordMessage<T> {

    public static final boolean DEFAULT_WITH_HASH_CODE = false;
    public static final char HASH_CODE_PREFIX = '@';

    private final boolean withHashCode;

    public ClassNameMessage() {
        this(DEFAULT_WITH_HASH_CODE);
    }

    public ClassNameMessage(boolean withHashCode) {
        this.withHashCode = withHashCode;
    }

    @Override
    public final String createMessage(T record) {
        return withHashCode
                ? record.getClass().getName() + HASH_CODE_PREFIX + Integer.toHexString(record.hashCode())
                : record.getClass().getName();
    }

}
