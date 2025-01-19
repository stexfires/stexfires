package stexfires.record;

import org.jspecify.annotations.Nullable;
import stexfires.record.impl.*;
import stexfires.util.supplier.SequenceSupplier;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

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

    public static TextRecord ofTextCollection(Collection<@Nullable String> texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static TextRecord ofTextStream(Stream<@Nullable String> texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static TextRecord ofTexts(@Nullable String... texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static TextRecord ofNullable(@Nullable String category, @Nullable Long recordId,
                                        @Nullable SequencedCollection<@Nullable String> texts) {
        if ((category == null) && (recordId == null)) {
            if ((texts == null) || texts.isEmpty()) {
                return empty();
            } else if (texts.size() == 1) {
                return new ValueFieldRecord(texts.getFirst());
            } else if (texts.size() == 2) {
                return new TwoFieldsRecord(texts.getFirst(), texts.getLast());
            } else {
                return new ManyFieldsRecord(texts);
            }
        } else {
            if ((texts == null) || texts.isEmpty()) {
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

    public static Supplier<Long> recordIdSequence() {
        return SequenceSupplier.asLong(DEFAULT_INITIAL_RECORD_ID);
    }

    public static Supplier<Long> recordIdSequence(long initialValue) {
        return SequenceSupplier.asLong(initialValue);
    }

    public static LongSupplier recordIdPrimitiveSequence() {
        return SequenceSupplier.asPrimitiveLong(DEFAULT_INITIAL_RECORD_ID);
    }

    public static LongSupplier recordIdPrimitiveSequence(long initialValue) {
        return SequenceSupplier.asPrimitiveLong(initialValue);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Consumer<String> {

        private @Nullable String category;
        private @Nullable Long recordId;
        private @Nullable List<@Nullable String> textList;

        private Builder() {
            textList = new ArrayList<>();
        }

        @SuppressWarnings("ParameterHidesMemberVariable")
        public synchronized Builder category(@Nullable String category) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            this.category = category;
            return this;
        }

        @SuppressWarnings("ParameterHidesMemberVariable")
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
