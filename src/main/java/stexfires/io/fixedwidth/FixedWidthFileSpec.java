package stexfires.io.fixedwidth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordFileSpec;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record FixedWidthFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        int recordWidth,
        boolean separateRecordsByLineSeparator,
        @NotNull Alignment alignment,
        @NotNull Character fillCharacter,
        @NotNull List<FixedWidthFieldSpec> fieldSpecs,
        int ignoreFirst,
        int ignoreLast,
        boolean skipEmptyLines,
        boolean skipAllNullOrEmpty
) implements ReadableRecordFileSpec<TextRecord, FixedWidthProducer>, WritableRecordFileSpec<TextRecord, FixedWidthConsumer> {
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;
    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_SKIP_ALL_NULL_OR_EMPTY = false;
    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;

    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;

    public FixedWidthFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(alignment);
        Objects.requireNonNull(fillCharacter);
        Objects.requireNonNull(fieldSpecs);
        if (recordWidth < 0) {
            throw new IllegalArgumentException("recordWidth < 0");
        }
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }

        fieldSpecs = new ArrayList<>(fieldSpecs);
    }

    public static FixedWidthFileSpec read(CharsetCoding charsetCoding,
                                          int recordWidth,
                                          boolean separateRecordsByLineSeparator,
                                          Alignment alignment,
                                          Character fillCharacter,
                                          List<FixedWidthFieldSpec> fieldSpecs,
                                          int ignoreFirst,
                                          int ignoreLast,
                                          boolean skipEmptyLines,
                                          boolean skipAllNullOrEmpty) {
        return new FixedWidthFileSpec(
                charsetCoding,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                recordWidth,
                separateRecordsByLineSeparator,
                alignment,
                fillCharacter,
                fieldSpecs,
                ignoreFirst,
                ignoreLast,
                skipEmptyLines,
                skipAllNullOrEmpty);
    }

    public static FixedWidthFileSpec write(CharsetCoding charsetCoding,
                                           LineSeparator lineSeparator,
                                           @Nullable String textBefore,
                                           @Nullable String textAfter,
                                           int recordWidth,
                                           boolean separateRecordsByLineSeparator,
                                           Alignment alignment,
                                           Character fillCharacter,
                                           List<FixedWidthFieldSpec> fieldSpecs) {
        return new FixedWidthFileSpec(
                charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                recordWidth,
                separateRecordsByLineSeparator,
                alignment,
                fillCharacter,
                fieldSpecs,
                DEFAULT_IGNORE_FIRST,
                DEFAULT_IGNORE_LAST,
                DEFAULT_SKIP_EMPTY_LINES,
                DEFAULT_SKIP_ALL_NULL_OR_EMPTY);
    }

    @Override
    public FixedWidthProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new FixedWidthProducer(bufferedReader, this);
    }

    @Override
    public FixedWidthConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new FixedWidthConsumer(bufferedWriter, this);
    }

    @Override
    public List<FixedWidthFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

}
