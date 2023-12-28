package stexfires.io.html.table;

import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @since 0.1
 */
public record HtmlTableFileSpec(
        CharsetCoding charsetCoding,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        @Nullable String consumerIndentation,
        List<HtmlTableFieldSpec> fieldSpecs
) implements WritableRecordFileSpec<TextRecord, HtmlTableConsumer> {

    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final String DEFAULT_CONSUMER_INDENTATION = null;

    public static final String TABLE_BEGIN = "<table>";
    public static final String TABLE_END = "</table>";
    public static final String TABLE_ROW_BEGIN = "<tr>";
    public static final String TABLE_ROW_END = "</tr>";
    public static final String TABLE_HEADER_BEGIN = "<th>";
    public static final String TABLE_HEADER_END = "</th>";
    public static final String TABLE_DATA_BEGIN = "<td>";
    public static final String TABLE_DATA_END = "</td>";
    public static final String NON_BREAKING_SPACE = "&nbsp;";

    public HtmlTableFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(fieldSpecs);

        fieldSpecs = new ArrayList<>(fieldSpecs);
    }

    public static HtmlTableFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                     LineSeparator consumerLineSeparator,
                                                     List<HtmlTableFieldSpec> fieldSpecs) {
        return new HtmlTableFileSpec(
                charsetCoding,
                consumerLineSeparator,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_INDENTATION,
                fieldSpecs
        );
    }

    public static HtmlTableFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                     LineSeparator consumerLineSeparator,
                                                     @Nullable String consumerTextBefore,
                                                     @Nullable String consumerTextAfter,
                                                     @Nullable String consumerIndentation,
                                                     List<HtmlTableFieldSpec> fieldSpecs) {
        return new HtmlTableFileSpec(
                charsetCoding,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerIndentation,
                fieldSpecs
        );
    }

    @Override
    public HtmlTableConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new HtmlTableConsumer(bufferedWriter, this, fieldSpecs);
    }

    @Override
    public List<HtmlTableFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

}

