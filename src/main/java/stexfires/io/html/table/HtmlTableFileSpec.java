package stexfires.io.html.table;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record HtmlTableFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull LineSeparator lineSeparator,
        @Nullable String textBefore,
        @Nullable String textAfter,
        @NotNull List<HtmlTableFieldSpec> fieldSpecs,
        @Nullable String indentation
) implements WritableRecordFileSpec<TextRecord, HtmlTableConsumer> {

    public static final String TABLE_BEGIN = "<table>";
    public static final String TABLE_END = "</table>";
    public static final String TABLE_ROW_BEGIN = "<tr>";
    public static final String TABLE_ROW_END = "</tr>";
    public static final String TABLE_HEADER_BEGIN = "<th>";
    public static final String TABLE_HEADER_END = "</th>";
    public static final String TABLE_DATA_BEGIN = "<td>";
    public static final String TABLE_DATA_END = "</td>";
    public static final String NON_BREAKING_SPACE = "&nbsp;";
    public static final String DEFAULT_INDENTATION = null;

    public HtmlTableFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(fieldSpecs);

        fieldSpecs = new ArrayList<>(fieldSpecs);
    }

    public static HtmlTableFileSpec write(CharsetCoding charsetCoding,
                                          LineSeparator lineSeparator,
                                          @Nullable String textBefore,
                                          @Nullable String textAfter,
                                          List<HtmlTableFieldSpec> fieldSpecs,
                                          @Nullable String indentation) {
        return new HtmlTableFileSpec(
                charsetCoding,
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
    public List<HtmlTableFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

}

