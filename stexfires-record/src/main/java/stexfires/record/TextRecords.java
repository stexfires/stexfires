package stexfires.record;

import org.jspecify.annotations.Nullable;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.util.function.Suppliers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.SequencedCollection;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods
 * for operating on {@link TextRecord}s.
 *
 * @see TextRecord
 * @see TextRecordStreams
 * @since 0.1
 */
public final class TextRecords {

    public static final long DEFAULT_INITIAL_RECORD_ID = 1L;

    private static final EmptyRecord EMPTY_RECORD = new EmptyRecord();

    private TextRecords() {
    }

    public static EmptyRecord empty() {
        return EMPTY_RECORD;
    }

    public static ValueRecord ofText(@Nullable String text) {
        return new ValueFieldRecord(text);
    }

    public static TextRecord ofTexts(Collection<@Nullable String> texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static TextRecord ofTexts(Stream<String> texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static TextRecord ofTexts(String... texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static TextRecord ofNullable(@Nullable String category, @Nullable Long recordId, @Nullable SequencedCollection<@Nullable String> texts) {
        if (category == null && recordId == null) {
            if (texts == null || texts.isEmpty()) {
                return empty();
            } else if (texts.size() == 1) {
                return new ValueFieldRecord(texts.getFirst());
            } else if (texts.size() == 2) {
                return new TwoFieldsRecord(texts.getFirst(), texts.getLast());
            } else {
                return new ManyFieldsRecord(texts);
            }
        } else {
            if (texts == null || texts.isEmpty()) {
                return new ManyFieldsRecord(category, recordId);
            } else if (texts.size() == 1) {
                return new ValueFieldRecord(category, recordId, texts.getFirst());
            } else if (texts.size() == 2) {
                return new TwoFieldsRecord(category, recordId, texts.getFirst(), texts.getLast());
            } else {
                return new ManyFieldsRecord(category, recordId, texts);
            }
        }
    }

    public static <T extends TextRecord> List<T> list(T record) {
        Objects.requireNonNull(record);
        return Collections.singletonList(record);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> List<T> list(T... records) {
        Objects.requireNonNull(records);
        return Arrays.stream(records).toList();
    }

    public static <T extends TextRecord> Stream<T> stream(T record) {
        Objects.requireNonNull(record);
        return Stream.of(record);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> Stream<T> stream(T... records) {
        Objects.requireNonNull(records);
        return Stream.of(records);
    }

    public static Supplier<Long> recordIdSequence() {
        return Suppliers.sequenceAsLong(DEFAULT_INITIAL_RECORD_ID);
    }

    public static Supplier<Long> recordIdSequence(long initialValue) {
        return Suppliers.sequenceAsLong(initialValue);
    }

    public static LongSupplier recordIdPrimitiveSequence() {
        return Suppliers.sequenceAsPrimitiveLong(DEFAULT_INITIAL_RECORD_ID);
    }

    public static LongSupplier recordIdPrimitiveSequence(long initialValue) {
        return Suppliers.sequenceAsPrimitiveLong(initialValue);
    }

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("ParameterHidesMemberVariable")
    public static final class Builder implements Consumer<String> {

        private @Nullable String category;
        private @Nullable Long recordId;
        private @Nullable List<@Nullable String> textList;

        private Builder() {
            textList = new ArrayList<>();
        }

        public synchronized Builder category(@Nullable String category) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            this.category = category;
            return this;
        }

        public synchronized Builder recordId(@Nullable Long recordId) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            this.recordId = recordId;
            return this;
        }

        @Override
        public synchronized void accept(@Nullable String text) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            textList.add(text);
        }

        public synchronized Builder add(@Nullable String text) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            textList.add(text);
            return this;
        }

        public synchronized Builder addAll(Collection<@Nullable String> texts) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            Objects.requireNonNull(texts);
            textList.addAll(texts);
            return this;
        }

        public synchronized TextRecord build() {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            TextRecord record = ofNullable(category, recordId, textList);
            category = null;
            recordId = null;
            textList = null;
            return record;
        }

    }

}
