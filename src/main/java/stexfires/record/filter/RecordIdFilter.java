package stexfires.record.filter;

import stexfires.record.TextRecord;
import stexfires.util.function.NumberPredicates.PrimitiveLongPredicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.LongPredicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class RecordIdFilter<T extends TextRecord> implements RecordFilter<T> {

    private final LongPredicate recordIdPredicate;

    public RecordIdFilter(LongPredicate recordIdPredicate) {
        Objects.requireNonNull(recordIdPredicate);
        this.recordIdPredicate = recordIdPredicate;
    }

    public static <T extends TextRecord> RecordIdFilter<T> equalTo(long compareRecordId) {
        return new RecordIdFilter<>(PrimitiveLongPredicates.equalTo(compareRecordId));
    }

    public static <T extends TextRecord> RecordIdFilter<T> isNotNull() {
        return new RecordIdFilter<>(anyExistingRecordId -> true);
    }

    public static <T extends TextRecord> RecordFilter<T> isNull() {
        return r -> !r.hasRecordId();
    }

    public static <T extends TextRecord> RecordIdFilter<T> containedIn(Collection<Long> recordIds) {
        return new RecordIdFilter<>(PrimitiveLongPredicates.containedIn(recordIds));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static <T extends TextRecord> RecordIdFilter<T> containedIn(Long... recordIds) {
        return containedIn(Arrays.asList(recordIds));
    }

    public static <T extends TextRecord> RecordIdFilter<T> between(long from, long to) {
        return new RecordIdFilter<>(PrimitiveLongPredicates.between(from, to));
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public final boolean isValid(T record) {
        return record.hasRecordId() && recordIdPredicate.test(record.recordId());
    }

}
