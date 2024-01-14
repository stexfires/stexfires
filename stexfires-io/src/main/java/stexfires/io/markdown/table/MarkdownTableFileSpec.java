package stexfires.io.markdown.table;

import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
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
 * @since 0.1
 */
public record MarkdownTableFileSpec(
        CharsetCoding charsetCoding,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        Alignment consumerAlignment,
        List<MarkdownTableFieldSpec> fieldSpecs
) implements WritableRecordFileSpec<TextRecord, MarkdownTableConsumer> {

    public static final @Nullable String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final @Nullable String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final Alignment DEFAULT_CONSUMER_ALIGNMENT = START;

    public static final String ESCAPE_TARGET = "|";
    public static final String ESCAPE_REPLACEMENT = Matcher.quoteReplacement("\\|");
    public static final String FILL_CHARACTER = " ";
    public static final String FIELD_DELIMITER = "|";
    public static final String ALIGNMENT_INDICATOR = ":";
    public static final String HEADER_DELIMITER = "-";
    public static final int COLUMN_MIN_WIDTH = 5;

    public MarkdownTableFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(consumerAlignment);
        Objects.requireNonNull(fieldSpecs);

        fieldSpecs = new ArrayList<>(fieldSpecs);
    }

    public static MarkdownTableFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                         LineSeparator consumerLineSeparator,
                                                         List<MarkdownTableFieldSpec> fieldSpecs) {
        return new MarkdownTableFileSpec(
                charsetCoding,
                consumerLineSeparator,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_ALIGNMENT,
                fieldSpecs
        );
    }

    public static MarkdownTableFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                         LineSeparator consumerLineSeparator,
                                                         @Nullable String consumerTextBefore,
                                                         @Nullable String consumerTextAfter,
                                                         Alignment consumerAlignment,
                                                         List<MarkdownTableFieldSpec> fieldSpecs) {
        return new MarkdownTableFileSpec(
                charsetCoding,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerAlignment,
                fieldSpecs
        );
    }

    @Override
    public MarkdownTableConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new MarkdownTableConsumer(bufferedWriter, this, fieldSpecs);
    }

    @Override
    public List<MarkdownTableFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

}

