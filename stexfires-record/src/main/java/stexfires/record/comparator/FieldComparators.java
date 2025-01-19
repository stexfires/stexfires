package stexfires.record.comparator;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.util.SortNulls;

import java.util.*;

import static java.util.Comparator.*;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link stexfires.record.TextField}s.
 *
 * @see stexfires.record.comparator.RecordComparators
 * @see stexfires.util.SortNulls
 * @see stexfires.util.StringComparators
 * @see java.util.Comparator
 * @since 0.1
 */
public final class FieldComparators {

    private FieldComparators() {
    }

    public static Comparator<TextField> index() {
        return comparingInt(TextField::index);
    }

    public static Comparator<TextField> maxIndex() {
        return comparingInt(TextField::maxIndex);
    }

    public static Comparator<TextField> text(Comparator<@Nullable String> comparator) {
        Objects.requireNonNull(comparator);
        return (textField1, textField2) -> comparator.compare(textField1.text(), textField2.text());
    }

    public static Comparator<TextField> text(Comparator<String> comparator,
                                             SortNulls sortNulls) {
        Objects.requireNonNull(comparator);
        return text(sortNulls.wrap(comparator));
    }

    public static Comparator<TextField> isFirstField() {
        return comparing(TextField::isFirstField);
    }

    public static Comparator<TextField> isLastField() {
        return comparing(TextField::isLastField);
    }

    public static Comparator<TextField> recordSize() {
        return comparingInt(TextField::recordSize);
    }

    public static Comparator<TextField> isNotNull() {
        return comparing(TextField::isNotNull);
    }

    public static Comparator<TextField> isNull() {
        return comparing(TextField::isNull);
    }

    public static Comparator<TextField> isEmpty() {
        return comparing(TextField::isEmpty);
    }

    public static Comparator<TextField> isNullOrEmpty() {
        return comparing(TextField::isNullOrEmpty);
    }

    public static Comparator<TextField> length() {
        return comparingInt(TextField::length);
    }

}
