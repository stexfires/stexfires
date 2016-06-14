package stexfires.core.modifier;

import stexfires.core.Record;
import stexfires.core.record.StandardRecord;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
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

    @Override
    public Stream<R> modify(Stream<T> recordStream) {
        return recordStream.map(unpivotFunction).flatMap(Function.identity());
    }

}
