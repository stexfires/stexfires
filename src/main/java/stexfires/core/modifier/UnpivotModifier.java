package stexfires.core.modifier;

import stexfires.core.Record;
import stexfires.core.record.StandardRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class UnpivotModifier<T extends Record, R extends Record> implements RecordStreamModifier<T, R> {

    protected final Function<? super T, Stream<? extends R>> unpivotFunction;

    public UnpivotModifier(Function<? super T, Stream<? extends R>> unpivotFunction) {
        Objects.requireNonNull(unpivotFunction);
        this.unpivotFunction = unpivotFunction;
    }

    public static <T extends Record, R extends Record> UnpivotModifier<T, R> getInstance(Function<? super T, Collection<? extends R>> unpivotFunction) {
        Objects.requireNonNull(unpivotFunction);
        return new UnpivotModifier<>(record -> unpivotFunction.apply(record).stream());
    }

    public static <T extends Record> UnpivotModifier<T, Record> getInstance(int keyIndex, IntFunction<String> nameFunction) {
        Objects.requireNonNull(nameFunction);
        return new UnpivotModifier<>(record -> {
            List<StandardRecord> list = new ArrayList<>(record.size() - 1);
            for (int i = 0; i < record.size(); i++) {
                if (i == keyIndex) {
                    continue;
                }
                list.add(new StandardRecord(record.getCategory(), record.getRecordId(), record.getValueAt(keyIndex), nameFunction.apply(i), record.getValueAt(i)));
            }
            return list.stream();
        });
    }

    public static <T extends Record> UnpivotModifier<T, Record> getInstance(int keyIndex) {
        return getInstance(keyIndex, String::valueOf);
    }

    public static <T extends Record> UnpivotModifier<T, Record> getInstance(int keyIndex, Map<Integer, String> nameMap) {
        Objects.requireNonNull(nameMap);
        return getInstance(keyIndex, nameMap::get);
    }

    public static <T extends Record> UnpivotModifier<T, Record> getInstance(List<Integer> keyIndexes,
                                                                            List<List<Integer>> pivotIndexes,
                                                                            IntFunction<String> pivotIndexFunction) {
        Objects.requireNonNull(keyIndexes);
        Objects.requireNonNull(pivotIndexes);
        return new UnpivotModifier<>(record -> {
            int maxSize = pivotIndexes.stream().mapToInt(List::size).max().orElse(0);
            int valuesSize = keyIndexes.size() + pivotIndexes.size();
            if (pivotIndexFunction != null) {
                valuesSize++;
            }
            List<StandardRecord> records = new ArrayList<>(maxSize);
            for (int pivotIndex = 0; pivotIndex < maxSize; pivotIndex++) {
                List<String> newValues = new ArrayList<>(valuesSize);

                // add key values
                newValues.addAll(keyIndexes.stream().map(record::getValueAt).collect(Collectors.toList()));

                // add pivot values
                for (int i = 0; i < pivotIndexes.size(); i++) {
                    if (pivotIndexes.get(i).size() > pivotIndex) {
                        newValues.add(record.getValueAt(pivotIndexes.get(i).get(pivotIndex)));
                    } else {
                        newValues.add(null);
                    }
                }

                // add pivot index
                if (pivotIndexFunction != null) {
                    newValues.add(pivotIndexFunction.apply(pivotIndex));
                }

                records.add(new StandardRecord(record.getCategory(), record.getRecordId(), newValues));
            }
            return records.stream();
        });
    }

    public static <T extends Record> UnpivotModifier<T, Record> getInstance(List<Integer> keyValues, List<List<Integer>> p, boolean addPivotIndex) {
        if (addPivotIndex) {
            return getInstance(keyValues, p, String::valueOf);
        }
        return getInstance(keyValues, p, null);
    }

    @Override
    public Stream<R> modify(Stream<T> recordStream) {
        return recordStream.map(unpivotFunction).flatMap(Function.identity());
    }

}
