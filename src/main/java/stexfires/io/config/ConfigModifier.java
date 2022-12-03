package stexfires.io.config;

import org.jetbrains.annotations.NotNull;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecord;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.CompareMessageBuilder;
import stexfires.record.modifier.DistinctModifier;
import stexfires.record.modifier.MapModifier;
import stexfires.record.modifier.RecordStreamModifier;
import stexfires.record.modifier.SortModifier;
import stexfires.util.SortNulls;
import stexfires.util.function.StringUnaryOperators;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static stexfires.io.config.ConfigFileSpec.NULL_KEY;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConfigModifier<T extends TextRecord> implements RecordStreamModifier<T, KeyValueRecord> {

    private final RecordStreamModifier<T, KeyValueRecord> modifier;

    @SuppressWarnings("ConstantConditions")
    public ConfigModifier(Locale categoryOperatorLocale, int keyIndex, int valueIndex, boolean removeDuplicates) {
        Objects.requireNonNull(categoryOperatorLocale);

        UnaryOperator<String> categoryOperator = StringUnaryOperators.concat(
                StringUnaryOperators.removeVerticalWhitespaces(),
                StringUnaryOperators.trimToNull(),
                StringUnaryOperators.upperCase(categoryOperatorLocale));
        RecordMapper<T, KeyValueRecord> mapper = r -> new KeyValueFieldsRecord(
                categoryOperator.apply(r.category()),
                r.recordId(),
                r.textAtOrElse(keyIndex, NULL_KEY),
                r.textAt(valueIndex));
        MapModifier<T, KeyValueRecord> mapModifier = new MapModifier<>(mapper);

        Comparator<KeyValueRecord> recordComparator = RecordComparators
                .<KeyValueRecord>category(Comparator.naturalOrder(), SortNulls.FIRST)
                .thenComparing(RecordComparators.key(Comparator.naturalOrder()));
        SortModifier<KeyValueRecord> sortModifier = new SortModifier<>(recordComparator);

        if (removeDuplicates) {
            // Use of 'KeyValueFieldsRecord.KEY_INDEX' only possible and allowed,
            // because the constructor is used at the RecordMapper.
            DistinctModifier<KeyValueRecord> distinctModifier = new DistinctModifier<>(
                    new CompareMessageBuilder()
                            .category()
                            .textAt(KeyValueFieldsRecord.KEY_INDEX));

            modifier = mapModifier.andThen(sortModifier.andThen(distinctModifier));
        } else {
            modifier = mapModifier.andThen(sortModifier);
        }
    }

    @Override
    public @NotNull Stream<KeyValueRecord> modify(Stream<T> recordStream) {
        return modifier.modify(recordStream);
    }

}
