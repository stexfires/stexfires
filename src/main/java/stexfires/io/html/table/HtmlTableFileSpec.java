package stexfires.io.html.table;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class HtmlTableFileSpec extends ReadableWritableRecordFileSpec<TextRecord, TextRecord> {

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
    private final List<HtmlTableFieldSpec> fieldSpecs;

    // FIELD - write
    private final String indentation;

    public HtmlTableFileSpec(CharsetCoding charsetCoding,
                             LineSeparator lineSeparator,
                             @Nullable String textBefore,
                             @Nullable String textAfter,
                             List<HtmlTableFieldSpec> fieldSpecs,
                             @Nullable String indentation) {
        super(charsetCoding, lineSeparator, textBefore, textAfter);
        Objects.requireNonNull(fieldSpecs);

        // both
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
    public HtmlTableConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new HtmlTableConsumer(bufferedWriter, this);
    }

    @Override
    public HtmlTableConsumer consumer(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return consumer(charsetCoding().newBufferedWriter(outputStream));
    }

    public List<HtmlTableFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public @Nullable String indentation() {
        return indentation;
    }

}

