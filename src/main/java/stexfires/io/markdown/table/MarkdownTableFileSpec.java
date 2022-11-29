package stexfires.io.markdown.table;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import static stexfires.util.Alignment.START;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownTableFileSpec implements ReadableWritableRecordFileSpec<TextRecord, TextRecord> {

    public static final String ESCAPE_TARGET = "|";
    public static final String ESCAPE_REPLACEMENT = Matcher.quoteReplacement("\\|");
    public static final String FILL_CHARACTER = " ";
    public static final String FIELD_DELIMITER = "| ";
    public static final String LAST_FIELD_DELIMITER = "|";
    public static final String ALIGNMENT_INDICATOR = ":";
    public static final String HEADER_DELIMITER = "-";
    public static final int COLUMN_MIN_WIDTH = 5;

    // DEFAULT - write
    public static final Alignment DEFAULT_ALIGNMENT = START;

    // FIELD - both
    private final CharsetCoding charsetCoding;
    private final LineSeparator lineSeparator;
    private final String textBefore;
    private final String textAfter;
    private final List<MarkdownTableFieldSpec> fieldSpecs;

    // FIELD - write
    private final Alignment alignment;

    public MarkdownTableFileSpec(CharsetCoding charsetCoding,
                                 LineSeparator lineSeparator,
                                 @Nullable String textBefore,
                                 @Nullable String textAfter,
                                 List<MarkdownTableFieldSpec> fieldSpecs,
                                 Alignment alignment) {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(alignment);

        // both
        this.charsetCoding = charsetCoding;
        this.lineSeparator = lineSeparator;
        this.textBefore = textBefore;
        this.textAfter = textAfter;
        this.fieldSpecs = new ArrayList<>(fieldSpecs);

        // write
        this.alignment = alignment;
    }

    public static MarkdownTableFileSpec write(CharsetCoding charsetCoding,
                                              LineSeparator lineSeparator,
                                              List<MarkdownTableFieldSpec> fieldSpecs) {
        return new MarkdownTableFileSpec(charsetCoding,
                lineSeparator,
                null,
                null,
                fieldSpecs,
                DEFAULT_ALIGNMENT);
    }

    public static MarkdownTableFileSpec write(CharsetCoding charsetCoding,
                                              LineSeparator lineSeparator,
                                              @Nullable String textBefore,
                                              @Nullable String textAfter,
                                              List<MarkdownTableFieldSpec> fieldSpecs,
                                              Alignment alignment) {
        return new MarkdownTableFileSpec(charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                fieldSpecs,
                alignment);
    }

    @Override
    public ReadableRecordProducer<TextRecord> producer(BufferedReader bufferedReader) {
        throw new UnsupportedOperationException("producer(BufferedReader) not implemented");
    }

    @Override
    public ReadableRecordProducer<TextRecord> producer(InputStream inputStream) {
        throw new UnsupportedOperationException("producer(InputStream) not implemented");
    }

    @Override
    public MarkdownTableConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new MarkdownTableConsumer(bufferedWriter, this);
    }

    @Override
    public MarkdownTableConsumer consumer(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return consumer(charsetCoding().newBufferedWriter(outputStream));
    }

    @Override
    public CharsetCoding charsetCoding() {
        return charsetCoding;
    }

    @Override
    public LineSeparator lineSeparator() {
        return lineSeparator;
    }

    @Override
    public @Nullable String textBefore() {
        return textBefore;
    }

    @Override
    public @Nullable String textAfter() {
        return textAfter;
    }

    public List<MarkdownTableFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public Alignment alignment() {
        return alignment;
    }

}

