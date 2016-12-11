package stexfires.core.filter;

import stexfires.core.Record;
import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;

import java.util.Collection;
import java.util.Objects;
import java.util.function.LongPredicate;

import static stexfires.util.NumberComparisonType.*;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class RecordIdFilter<T extends Record> implements RecordFilter<T> {

    protected final LongPredicate recordIdPredicate;

    public RecordIdFilter(LongPredicate recordIdPredicate) {
        Objects.requireNonNull(recordIdPredicate);
        this.recordIdPredicate = recordIdPredicate;
    }

    public static <T extends Record> RecordIdFilter<T> compare(NumberComparisonType numberComparisonType,
                                                               long compareRecordId) {
        return new RecordIdFilter<>(numberComparisonType.longPredicate(compareRecordId));
    }

    public static <T extends Record> RecordIdFilter<T> check(NumberCheckType numberCheckType) {
        return new RecordIdFilter<>(numberCheckType.longPredicate());
    }

    public static <T extends Record> RecordIdFilter<T> equalTo(long compareRecordId) {
        return new RecordIdFilter<>(EQUAL_TO.longPredicate(compareRecordId));
    }

    public static <T extends Record> RecordIdFilter<T> notNull() {
        return new RecordIdFilter<>((long value) -> true);
    }

    public static <T extends Record> RecordIdFilter<T> containedIn(Collection<Long> recordIds) {
        return new RecordIdFilter<>(recordIds::contains);
    }

    public static <T extends Record> RecordIdFilter<T> between(int from, int to) {
        return new RecordIdFilter<>(
                GREATER_THAN_OR_EQUAL_TO.longPredicate(from)
                                        .and(LESS_THAN.longPredicate(to)));
    }

    @Override
    public boolean isValid(T record) {
        return record.hasRecordId() && recordIdPredicate.test(record.getRecordId());
    }

}
