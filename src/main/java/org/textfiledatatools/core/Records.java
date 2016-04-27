package org.textfiledatatools.core;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class Records {

    public static final int FIRST_FIELD_INDEX = 0;
    public static final long DEFAULT_INITIAL_RECORD_ID = 1L;
    public static final String DEFAULT_FIELD_VALUE_DELIMITER = ", ";

    private Records() {
    }

    public static Supplier<Long> createRecordIdSequence() {
        return createRecordIdSequence(DEFAULT_INITIAL_RECORD_ID);
    }

    public static Supplier<Long> createRecordIdSequence(long initialValue) {
        AtomicLong n = new AtomicLong(initialValue);
        return () -> n.getAndIncrement();
    }

    public static List<String> collectFieldValuesToList(Record record) {
        return record.toNewStream().map(Field::getValue).collect(Collectors.toList());
    }

    public static String joinFieldValues(Record record) {
        return joinFieldValues(record, DEFAULT_FIELD_VALUE_DELIMITER);
    }

    public static String joinFieldValues(Record record, CharSequence delimiter) {
        return record.toNewStream().map(Field::getValue).collect(Collectors.joining(delimiter));
    }

    public static String joinFieldValues(Field[] fields) {
        return joinFieldValues(fields, DEFAULT_FIELD_VALUE_DELIMITER);
    }

    public static String joinFieldValues(Field[] fields, CharSequence delimiter) {
        return Arrays.stream(fields).map(Field::getValue).collect(Collectors.joining(delimiter));
    }

}
