package stexfires.io.markdown.table;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
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
public record MarkdownTableFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull LineSeparator lineSeparator,
        @Nullable String textBefore,
        @Nullable String textAfter,
        @NotNull List<MarkdownTableFieldSpec> fieldSpecs,
        @NotNull Alignment alignment
) implements WritableRecordFileSpec<TextRecord, MarkdownTableConsumer> {

    public static final String ESCAPE_TARGET = "|";
    public static final String ESCAPE_REPLACEMENT = Matcher.quoteReplacement("\\|");
    public static final String FILL_CHARACTER = " ";
    public static final String FIELD_DELIMITER = "| ";
    public static final String LAST_FIELD_DELIMITER = "|";
    public static final String ALIGNMENT_INDICATOR = ":";
    public static final String HEADER_DELIMITER = "-";
    public static final int COLUMN_MIN_WIDTH = 5;
    public static final Alignment DEFAULT_ALIGNMENT = START;

    public MarkdownTableFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(alignment);

        fieldSpecs = new ArrayList<>(fieldSpecs);
    }

    public static MarkdownTableFileSpec write(CharsetCoding charsetCoding,
                                              LineSeparator lineSeparator,
                                              @Nullable String textBefore,
                                              @Nullable String textAfter,
                                              List<MarkdownTableFieldSpec> fieldSpecs,
                                              Alignment alignment) {
        return new MarkdownTableFileSpec(
                charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                fieldSpecs,
                alignment);
    }

    @Override
    public MarkdownTableConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new MarkdownTableConsumer(bufferedWriter, this);
    }

    @Override
    public List<MarkdownTableFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

}

