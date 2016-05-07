package org.textfiledatatools.core;

import org.textfiledatatools.core.producer.RecordProducer;
import org.textfiledatatools.core.producer.UncheckedProducerException;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on record streams.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordStreams {

    private RecordStreams() {
    }

    public static Stream<Record> empty() {
        return Stream.empty();
    }

    public static <T extends Record> Stream<T> of(T record) {
        return Stream.of(record);
    }

    @SafeVarargs
    public static <T extends Record> Stream<T> of(T... records) {
        return Stream.of(records);
    }

    public static <T extends Record> Stream<T> produceStream(RecordProducer<T> recordProducer) throws UncheckedProducerException {
        return recordProducer.produceStream();
    }

    public static <T extends Record> Stream<T> generate(Supplier<T> recordSupplier) {
        return Stream.generate(recordSupplier);
    }

    public static <T extends Record> Stream<T> concat(Stream<T> firstRecordStream, Stream<T> secondRecordStream) {
        return Stream.concat(firstRecordStream, secondRecordStream);
    }

    @SafeVarargs
    public static <T extends Record> Stream<T> concat(Stream<T>... recordStreams) {
        // TODO Validate: concatWithFlatMap or concatWithReduce
        return concatWithFlatMap(recordStreams);
    }

    @SafeVarargs
    static <T extends Record> Stream<T> concatWithFlatMap(Stream<T>... recordStreams) {
        return Stream.of(recordStreams).flatMap(Function.identity());
    }

    @SafeVarargs
    static <T extends Record> Stream<T> concatWithReduce(Stream<T>... recordStreams) {
        return Stream.of(recordStreams).reduce(Stream.empty(), Stream::concat);
    }

    public static <T extends Record> Stream<T> distinct(Stream<T> stream,
                                                        Function<? super T, String> recordEqualsString) {
        Objects.requireNonNull(stream, "Parameter 'stream' must not be null");
        Objects.requireNonNull(recordEqualsString, "Parameter 'recordEqualsString' must not be null");
        //noinspection RedundantTypeArguments
        return stream.map(record -> new DistinctRecordWrapper<>(record, recordEqualsString.apply(record)))
                .distinct()
                .map(DistinctRecordWrapper<T>::getRecord);
    }

    public static <T extends Record> Stream<T> sorted(Stream<T> stream, Comparator<? super T> recordComparator) {
        Objects.requireNonNull(stream, "Parameter 'stream' must not be null");
        Objects.requireNonNull(recordComparator, "Parameter 'recordComparator' must not be null");
        return stream.sorted(recordComparator);
    }

    private static final class DistinctRecordWrapper<T extends Record> {

        private final T record;
        private final String equalsString;

        public DistinctRecordWrapper(T record, String equalsString) {
            this.record = record;
            this.equalsString = equalsString;
        }

        public T getRecord() {
            return record;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DistinctRecordWrapper<?> that = (DistinctRecordWrapper<?>) o;
            return Objects.equals(equalsString, that.equalsString);
        }

        @Override
        public int hashCode() {
            return Objects.hash(equalsString);
        }

        @Override
        public String toString() {
            return "DistinctRecordWrapper{" +
                    "record=" + record +
                    ", equalsString='" + equalsString + '\'' +
                    '}';
        }
    }

}
