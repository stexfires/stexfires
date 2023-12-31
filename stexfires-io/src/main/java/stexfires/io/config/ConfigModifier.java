package stexfires.io.config;

import stexfires.record.KeyValueCommentRecord;
import stexfires.record.TextRecord;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.filter.TextFilter;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.CompareMessageBuilder;
import stexfires.record.modifier.DistinctModifier;
import stexfires.record.modifier.FilterModifier;
import stexfires.record.modifier.MapModifier;
import stexfires.record.modifier.RecordStreamModifier;
import stexfires.record.modifier.SortModifier;
import stexfires.util.SortNulls;
import stexfires.util.function.StringPredicates;
import stexfires.util.function.StringUnaryOperators;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static stexfires.io.config.ConfigFileSpec.NULL_KEY;

/**
 * @since 0.1
 */
public final class ConfigModifier<T extends TextRecord> implements RecordStreamModifier<T, KeyValueCommentRecord> {

    public static final int ILLEGAL_COMMENT_INDEX = -1;

    private final RecordStreamModifier<T, KeyValueCommentRecord> modifier;

    public ConfigModifier(UnaryOperator<String> categoryOperator, boolean removeNullOrBlankKey, boolean removeDuplicates,
                          int keyIndex, int valueIndex) {
        this(categoryOperator, removeNullOrBlankKey, removeDuplicates, keyIndex, valueIndex, ILLEGAL_COMMENT_INDEX);
    }

    @SuppressWarnings("DataFlowIssue")
    public ConfigModifier(UnaryOperator<String> categoryOperator, boolean removeNullOrBlankKey, boolean removeDuplicates,
                          int keyIndex, int valueIndex, int commentIndex) {
        Objects.requireNonNull(categoryOperator);

        // MapModifier
        RecordMapper<T, KeyValueCommentRecord> mapper = r -> new KeyValueCommentFieldsRecord(
                categoryOperator.apply(r.category()),
                r.recordId(),
                r.textAtOrElse(keyIndex, NULL_KEY),
                r.textAt(valueIndex),
                r.textAt(commentIndex));
        MapModifier<T, KeyValueCommentRecord> mapModifier = new MapModifier<>(mapper);

        // FilterModifier
        FilterModifier<KeyValueCommentRecord> filterModifier;
        if (removeNullOrBlankKey) {
            // Use of 'KeyValueFieldsRecord.KEY_INDEX' only possible and allowed,
            // because the constructor is used at the RecordMapper.
            filterModifier = new FilterModifier<>(
                    new TextFilter<>(KeyValueFieldsRecord.KEY_INDEX, StringPredicates.isNotNullAndNotBlank()));
        } else {
            filterModifier = null;
        }

        // SortModifier
        Comparator<KeyValueCommentRecord> recordComparator = RecordComparators
                .<KeyValueCommentRecord>category(Comparator.naturalOrder(), SortNulls.FIRST)
                .thenComparing(RecordComparators.key(Comparator.naturalOrder()));
        SortModifier<KeyValueCommentRecord> sortModifier = new SortModifier<>(recordComparator);

        // DistinctModifier
        DistinctModifier<KeyValueCommentRecord> distinctModifier;
        if (removeDuplicates) {
            // Use of 'KeyValueFieldsRecord.KEY_INDEX' only possible and allowed,
            // because the constructor is used at the RecordMapper.
            distinctModifier = new DistinctModifier<>(
                    new CompareMessageBuilder()
                            .category()
                            .textAt(KeyValueFieldsRecord.KEY_INDEX));
        } else {
            distinctModifier = null;
        }

        // Combine modifiers
        RecordStreamModifier<T, KeyValueCommentRecord> combinedModifier = mapModifier;
        if (filterModifier != null) {
            combinedModifier = combinedModifier.andThen(filterModifier);
        }
        combinedModifier = combinedModifier.andThen(sortModifier);
        if (distinctModifier != null) {
            combinedModifier = combinedModifier.andThen(distinctModifier);
        }
        modifier = combinedModifier;
    }

    public static UnaryOperator<String> categoryTrimAndUppercase(Locale locale) {
        Objects.requireNonNull(locale);
        return StringUnaryOperators.concat(
                StringUnaryOperators.removeVerticalWhitespaces(),
                StringUnaryOperators.trimToNull(),
                StringUnaryOperators.upperCase(locale));
    }

    public static UnaryOperator<String> categoryTrimAndLowercase(Locale locale) {
        Objects.requireNonNull(locale);
        return StringUnaryOperators.concat(
                StringUnaryOperators.removeVerticalWhitespaces(),
                StringUnaryOperators.trimToNull(),
                StringUnaryOperators.lowerCase(locale));
    }

    @Override
    public Stream<KeyValueCommentRecord> modify(Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        return modifier.modify(recordStream);
    }

}
