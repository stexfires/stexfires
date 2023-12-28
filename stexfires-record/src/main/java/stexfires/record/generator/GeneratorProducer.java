package stexfires.record.generator;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.producer.RecordProducer;
import stexfires.record.producer.UncheckedProducerException;

import java.time.Instant;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A GeneratorProducer produces a {@link Stream} of {@link stexfires.record.TextRecord} based on a
 * {@link RecordGenerator}. Its constructor is private. It can hava a known size or an unknown size.
 *
 * @see stexfires.record.generator.RecordGenerator
 * @since 0.1
 */
public final class GeneratorProducer<T extends TextRecord> implements RecordProducer<T> {

    private final RecordGenerator<T> generator;
    private final boolean knownSize;
    private final long size;
    private final @Nullable BiPredicate<GeneratorContext<T>, T> lastPredicate;

    private GeneratorProducer(RecordGenerator<T> generator,
                              boolean knownSize,
                              long size,
                              @Nullable BiPredicate<GeneratorContext<T>, T> lastPredicate) {
        Objects.requireNonNull(generator);
        if (size < 0) {
            throw new IllegalArgumentException("size must be greater than or equal to zero!");
        }
        this.generator = generator;
        this.knownSize = knownSize;
        this.size = size;
        this.lastPredicate = lastPredicate;
    }

    public static <T extends TextRecord> GeneratorProducer<T> knownSize(RecordGenerator<T> generator,
                                                                        long size) {
        Objects.requireNonNull(generator);
        return new GeneratorProducer<>(generator, true, size, null);
    }

    public static <T extends TextRecord> GeneratorProducer<T> knownSizeConcatenated(RecordGenerator<T> firstGenerator,
                                                                                    RecordGenerator<T> secondGenerator,
                                                                                    long firstSize,
                                                                                    long secondSize) {
        Objects.requireNonNull(firstGenerator);
        Objects.requireNonNull(secondGenerator);
        if (firstSize < 0) {
            throw new IllegalArgumentException("firstSize must be greater than or equal to zero!");
        }
        if (secondSize < 0) {
            throw new IllegalArgumentException("secondSize must be greater than or equal to zero!");
        }
        RecordGenerator<T> generator = (context) -> {
            if (context.recordIndex() < firstSize) {
                return firstGenerator.generate(context);
            } else {
                return secondGenerator.generate(context);
            }
        };
        return new GeneratorProducer<>(generator, true, firstSize + secondSize, null);
    }

    public static <T extends TextRecord> GeneratorProducer<T> unknownSize(RecordGenerator<T> generator) {
        Objects.requireNonNull(generator);
        return new GeneratorProducer<>(generator, false, Long.MAX_VALUE, null);
    }

    public static <T extends TextRecord> GeneratorProducer<T> unknownSize(RecordGenerator<T> generator,
                                                                          BiPredicate<GeneratorContext<T>, T> lastPredicate) {
        Objects.requireNonNull(generator);
        Objects.requireNonNull(lastPredicate);
        return new GeneratorProducer<>(generator, false, Long.MAX_VALUE, lastPredicate);
    }

    public static <T extends TextRecord> GeneratorProducer<T> unknownSize(RecordGenerator<T> generator,
                                                                          Predicate<T> lastPredicate) {
        Objects.requireNonNull(generator);
        Objects.requireNonNull(lastPredicate);
        return new GeneratorProducer<>(generator, false, Long.MAX_VALUE,
                (GeneratorContext<T> context, T record) -> lastPredicate.test(record));
    }

    @Override
    public Stream<T> produceStream() throws UncheckedProducerException {
        GeneratorIterator<T> iterator = new GeneratorIterator<>(generator, size, lastPredicate);
        Spliterator<T> spliterator;
        if (knownSize) {
            spliterator = Spliterators.spliterator(
                    iterator,
                    size,
                    Spliterator.SIZED | Spliterator.SUBSIZED |
                            Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE);
        } else {
            spliterator = Spliterators.spliteratorUnknownSize(
                    iterator,
                    Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE);
        }
        // Sequential execution is usually always significantly faster for this type of iterator.
        return StreamSupport.stream(spliterator, false);
    }

    private static final class GeneratorIterator<T extends TextRecord> implements Iterator<T> {

        private final RecordGenerator<T> generator;
        private final @Nullable BiPredicate<GeneratorContext<T>, T> lastPredicate;
        private final long maxRecordIndex;

        private long recordIndex;
        private boolean finished;
        private @Nullable T firstRecord;
        private @Nullable T previousRecord;

        private GeneratorIterator(RecordGenerator<T> generator,
                                  long maxSize,
                                  @Nullable BiPredicate<GeneratorContext<T>, T> lastPredicate) {
            Objects.requireNonNull(generator);
            this.generator = generator;
            this.lastPredicate = lastPredicate;

            // maxRecordIndex is one less than maxSize, because the index starts with 0.
            maxRecordIndex = maxSize - 1L;
            // if the size is lower or equal to zero, the iterator is already finished and hasNext() will return false.
            finished = (maxSize <= 0);

            // Starting with index -1, because the first call of next() will increment the index to 0
            recordIndex = -1L;
            // The firstRecord and previousRecord will be set in the first call of next().
            firstRecord = null;
            previousRecord = null;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return !finished;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws java.util.NoSuchElementException if the iteration has no more elements
         * @throws IllegalStateException            if the result of the generator is not valid
         */
        @Override
        public T next() {
            if (finished) {
                throw new NoSuchElementException("No more elements!");
            }

            recordIndex++;

            // Check if the maximum index is reached
            if (recordIndex >= maxRecordIndex) {
                finished = true;
            }

            var context = new GeneratorContext<>(
                    Instant.now(),
                    recordIndex,
                    recordIndex == 0,
                    finished,
                    Optional.ofNullable(firstRecord),
                    Optional.ofNullable(previousRecord));

            T generatedRecord = generator.generate(context);

            //noinspection ConstantValue
            if (generatedRecord == null) {
                throw new IllegalStateException("The generator returned a null result! context=" + context);
            }
            // Check if the generated record is the last record
            if (!finished && (lastPredicate != null) && lastPredicate.test(context, generatedRecord)) {
                finished = true;
            }
            // Store the record of the result for the next call of next().
            if (firstRecord == null) {
                firstRecord = generatedRecord;
            }
            previousRecord = generatedRecord;

            return generatedRecord;
        }

    }

}
