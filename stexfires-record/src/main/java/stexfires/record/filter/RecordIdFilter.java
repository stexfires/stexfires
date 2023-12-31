package stexfires.record.filter;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.util.function.NumberPredicates;
import stexfires.util.function.NumberPredicates.PrimitiveLongPredicates;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @since 0.1
 */
public class RecordIdFilter<T extends TextRecord> implements RecordFilter<T> {

    private final Predicate<@Nullable Long> recordIdPredicate;

    public RecordIdFilter(Predicate<@Nullable Long> recordIdPredicate) {
        Objects.requireNonNull(recordIdPredicate);
        this.recordIdPredicate = recordIdPredicate;
    }

    public static <T extends TextRecord> RecordIdFilter<T> equalTo(long compareRecordId) {
        return new RecordIdFilter<>(PrimitiveLongPredicates.isNotNullAnd(PrimitiveLongPredicates.equalTo(compareRecordId)));
    }

    public static <T extends TextRecord> RecordIdFilter<T> isNotNull() {
        return new RecordIdFilter<>(NumberPredicates.isNotNull());
    }

    public static <T extends TextRecord> RecordFilter<T> isNull() {
        return new RecordIdFilter<>(NumberPredicates.isNull());
    }

    public static <T extends TextRecord> RecordIdFilter<T> containedIn(Collection<Long> recordIds) {
        Objects.requireNonNull(recordIds);
        return new RecordIdFilter<>(NumberPredicates.containedIn(recordIds));
    }

    public static <T extends TextRecord> RecordIdFilter<T> containedIn(long... recordIds) {
        Objects.requireNonNull(recordIds);
        return new RecordIdFilter<>(PrimitiveLongPredicates.isNotNullAnd(PrimitiveLongPredicates.containedIn(recordIds)));
    }

    public static <T extends TextRecord> RecordIdFilter<T> between(long from, long to) {
        return new RecordIdFilter<>(PrimitiveLongPredicates.isNotNullAnd(PrimitiveLongPredicates.between(from, to)));
    }

    @Override
    public final boolean isValid(T record) {
        return recordIdPredicate.test(record.recordId());
    }

}
