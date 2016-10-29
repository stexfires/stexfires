package stexfires.core.mapper;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.core.record.StandardRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NewValuesMapper<T extends Record> implements RecordMapper<T, Record> {

    protected final Function<? super T, List<String>> valuesFunction;

    public NewValuesMapper(Function<T, List<String>> valuesFunction) {
        Objects.requireNonNull(valuesFunction);
        this.valuesFunction = valuesFunction;
    }

    @SafeVarargs
    public static <T extends Record> NewValuesMapper<T> createMessages(RecordMessage<? super T>... recordMessages) {
        Objects.requireNonNull(recordMessages);
        return new NewValuesMapper<>(r -> Arrays.stream(recordMessages)
                                                .map(f -> f.createMessage(r))
                                                .collect(Collectors.toList()));
    }

    public static <T extends Record> NewValuesMapper<T> createMessages(List<RecordMessage<? super T>> recordMessages) {
        Objects.requireNonNull(recordMessages);
        return new NewValuesMapper<>(r -> recordMessages.stream()
                                                        .map(f -> f.createMessage(r))
                                                        .collect(Collectors.toList()));
    }

    @SafeVarargs
    public static <T extends Record> NewValuesMapper<T> applyFunctions(Function<? super T, String>... functions) {
        Objects.requireNonNull(functions);
        return new NewValuesMapper<>(r -> Arrays.stream(functions)
                                                .map(f -> f.apply(r))
                                                .collect(Collectors.toList()));
    }

    public static <T extends Record> NewValuesMapper<T> applyFunctions(List<Function<? super T, String>> functions) {
        Objects.requireNonNull(functions);
        return new NewValuesMapper<>(r -> functions.stream()
                                                   .map(f -> f.apply(r))
                                                   .collect(Collectors.toList()));
    }

    @Override
    public Record map(T record) {
        return new StandardRecord(record.getCategory(), record.getRecordId(), valuesFunction.apply(record));
    }

}
