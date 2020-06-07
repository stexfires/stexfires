package stexfires.io.config;

import stexfires.core.TextRecord;
import stexfires.core.comparator.NULLS;
import stexfires.core.comparator.RecordComparators;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.message.CompareMessageBuilder;
import stexfires.core.modifier.DistinctModifier;
import stexfires.core.modifier.MapModifier;
import stexfires.core.modifier.RecordStreamModifier;
import stexfires.core.modifier.SortModifier;
import stexfires.core.record.KeyValueRecord;
import stexfires.util.StringUnaryOperatorType;

import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConfigModifier<T extends TextRecord> implements RecordStreamModifier<T, KeyValueRecord> {

    protected final RecordStreamModifier<T, KeyValueRecord> modifier;

    public ConfigModifier(int keyIndex, int valueIndex, boolean removeDuplicates) {
        UnaryOperator<String> categoryOperator = c -> {
            String category = c;
            category = StringUnaryOperatorType.REMOVE_VERTICAL_WHITESPACE.operateString(category);
            category = StringUnaryOperatorType.TRIM_TO_NULL.operateString(category);
            category = StringUnaryOperatorType.UPPER_CASE.operateString(category); // TODO locale
            return category;
        };

        RecordMapper<T, KeyValueRecord> mapper = r -> new KeyValueRecord(
                categoryOperator.apply(r.getCategory()),
                r.getRecordId(),
                r.getValueAtOrElse(keyIndex, ConfigFileSpec.NULL_KEY),
                r.getValueAt(valueIndex));
        MapModifier<T, KeyValueRecord> mapModifier = new MapModifier<>(mapper);

        // TODO special category comparator (locale)
        Comparator<KeyValueRecord> recordComparator = RecordComparators
                .<KeyValueRecord>category(Comparator.naturalOrder(), NULLS.FIRST)
                .thenComparing(RecordComparators.valueOfKeyField(Comparator.naturalOrder()));
        SortModifier<KeyValueRecord> sortModifier = new SortModifier<>(recordComparator);

        DistinctModifier<KeyValueRecord> distinctModifier = new DistinctModifier<>(
                new CompareMessageBuilder()
                        .category()
                        .value(KeyValueRecord.KEY_INDEX));

        if (removeDuplicates) {
            modifier = mapModifier.andThen(sortModifier.andThen(distinctModifier));
        } else {
            modifier = mapModifier.andThen(sortModifier);
        }
    }

    @Override
    public Stream<KeyValueRecord> modify(Stream<T> recordStream) {
        return modifier.modify(recordStream);
    }

}
