package stexfires.io.html.table;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.TextRecord;
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

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class HtmlTableFileSpec implements ReadableWritableRecordFileSpec<TextRecord, TextRecord> {

    public static final String TABLE_BEGIN = "<table>";
    public static final String TABLE_END = "</table>";
    public static final String TABLE_ROW_BEGIN = "<tr>";
    public static final String TABLE_ROW_END = "</tr>";
    public static final String TABLE_HEADER_BEGIN = "<th>";
    public static final String TABLE_HEADER_END = "</th>";
    public static final String TABLE_DATA_BEGIN = "<td>";
    public static final String TABLE_DATA_END = "</td>";
    public static final String NON_BREAKING_SPACE = "&nbsp;";

    // FIELD - both
    private final CharsetCoding charsetCoding;
    private final LineSeparator lineSeparator;
    private final String textBefore;
    private final String textAfter;
    private final List<HtmlTableFieldSpec> fieldSpecs;

    // FIELD - write
    private final String indentation;

    public HtmlTableFileSpec(CharsetCoding charsetCoding,
                             LineSeparator lineSeparator,
                             @Nullable String textBefore,
                             @Nullable String textAfter,
                             List<HtmlTableFieldSpec> fieldSpecs,
                             @Nullable String indentation) {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(fieldSpecs);

        // both
        this.charsetCoding = charsetCoding;
        this.lineSeparator = lineSeparator;
        this.textBefore = textBefore;
        this.textAfter = textAfter;
        this.fieldSpecs = new ArrayList<>(fieldSpecs);

        // write
        this.indentation = indentation;
    }

    public static HtmlTableFileSpec write(CharsetCoding charsetCoding,
                                          LineSeparator lineSeparator,
                                          @Nullable String textBefore,
                                          @Nullable String textAfter,
                                          List<HtmlTableFieldSpec> fieldSpecs,
                                          @Nullable String indentation) {
        return new HtmlTableFileSpec(charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                fieldSpecs,
                indentation);
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
    public HtmlTableConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new HtmlTableConsumer(bufferedWriter, this);
    }

    @Override
    public HtmlTableConsumer consumer(OutputStream outputStream) {
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

    public List<HtmlTableFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public @Nullable String indentation() {
        return indentation;
    }

}

