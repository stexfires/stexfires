package stexfires.record.comparator;

import org.jspecify.annotations.Nullable;
import stexfires.record.*;
import stexfires.record.message.RecordMessage;
import stexfires.util.SortNulls;

import java.util.*;
import java.util.function.*;

import static java.util.Comparator.*;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link stexfires.record.TextRecord}s.
 *
 * @see stexfires.record.comparator.FieldComparators
 * @see stexfires.util.SortNulls
 * @see stexfires.util.StringComparators
 * @see java.util.Comparator
 * @since 0.1
 */
public final class RecordComparators {

    private RecordComparators() {
    }

    public static <T extends TextRecord> Comparator<T> category(Comparator<@Nullable String> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(TextRecord::category, comparator);
    }

    public static <T extends TextRecord> Comparator<T> category(Comparator<String> comparator,
                                                                SortNulls sortNulls) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(sortNulls);
        return comparing(TextRecord::category, sortNulls.wrap(comparator));
    }

    public static <T extends TextRecord> Comparator<T> recordId(Comparator<@Nullable Long> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(TextRecord::recordId, comparator);
    }

    public static <T extends TextRecord> Comparator<T> recordId(Comparator<Long> comparator,
                                                                SortNulls sortNulls) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(sortNulls);
        return comparing(TextRecord::recordId, sortNulls.wrap(comparator));
    }

    public static <T extends TextRecord> Comparator<T> recordId(SortNulls sortNulls) {
        Objects.requireNonNull(sortNulls);
        Comparator<Long> naturalOrderComparator = naturalOrder();
        return comparing(TextRecord::recordId, sortNulls.wrap(naturalOrderComparator));
    }

    public static <T extends TextRecord> Comparator<T> size() {
        return comparingInt(TextRecord::size);
    }

    public static <T extends TextRecord> Comparator<T> field(Function<? super T, @Nullable TextField> fieldFunction,
                                                             Comparator<TextField> comparator,
                                                             SortNulls sortNulls) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(sortNulls);
        return comparing(fieldFunction, sortNulls.wrap(comparator));
    }

    public static <T extends TextRecord> Comparator<T> fieldAt(int index,
                                                               Comparator<TextField> comparator,
                                                               SortNulls sortNulls) {
        return field(record -> record.fieldAt(index), comparator, sortNulls);
    }

    public static <T extends TextRecord> Comparator<T> firstField(Comparator<TextField> comparator,
                                                                  SortNulls sortNulls) {
        return field(TextRecord::firstField, comparator, sortNulls);
    }

    public static <T extends TextRecord> Comparator<T> lastField(Comparator<TextField> comparator,
                                                                 SortNulls sortNulls) {
        return field(TextRecord::lastField, comparator, sortNulls);
    }

    public static <T extends TextRecord> Comparator<T> text(Function<? super T, @Nullable String> textFunction,
                                                            Comparator<String> comparator,
                                                            SortNulls sortNulls) {
        Objects.requireNonNull(textFunction);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(sortNulls);
        return comparing(textFunction, sortNulls.wrap(comparator));
    }

    public static <T extends TextRecord> Comparator<T> textAt(int index,
                                                              Comparator<String> comparator,
                                                              SortNulls sortNulls) {
        return text(record -> record.textAt(index), comparator, sortNulls);
    }

    public static <T extends TextRecord> Comparator<T> firstText(Comparator<String> comparator,
                                                                 SortNulls sortNulls) {
        return text(TextRecord::firstText, comparator, sortNulls);
    }

    public static <T extends TextRecord> Comparator<T> lastText(Comparator<String> comparator,
                                                                SortNulls sortNulls) {
        return text(TextRecord::lastText, comparator, sortNulls);
    }

    public static <T extends KeyRecord> Comparator<T> keyField(Comparator<TextField> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(KeyRecord::keyField, comparator);
    }

    public static <T extends KeyRecord> Comparator<T> key(Comparator<String> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(KeyRecord::key, comparator);
    }

    public static <T extends ValueRecord> Comparator<T> valueField(Comparator<TextField> comparator,
                                                                   SortNulls sortNulls) {
        return field(ValueRecord::valueField, comparator, sortNulls);
    }

    public static <T extends ValueRecord> Comparator<T> value(Comparator<String> comparator,
                                                              SortNulls sortNulls) {
        return text(ValueRecord::value, comparator, sortNulls);
    }

    public static <T extends CommentRecord> Comparator<T> commentField(Comparator<TextField> comparator,
                                                                       SortNulls sortNulls) {
        return field(CommentRecord::commentField, comparator, sortNulls);
    }

    public static <T extends CommentRecord> Comparator<T> comment(Comparator<String> comparator,
                                                                  SortNulls sortNulls) {
        return text(CommentRecord::comment, comparator, sortNulls);
    }

    public static <T extends TextRecord> Comparator<T> message(RecordMessage<? super T> recordMessage,
                                                               Comparator<String> comparator,
                                                               SortNulls sortNulls) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(sortNulls);
        return comparing(recordMessage::createMessage, sortNulls.wrap(comparator));
    }

}
