package stexfires.io.markdown.table;

import org.jetbrains.annotations.Nullable;
import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.util.Alignment;
import stexfires.util.Strings;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static stexfires.io.markdown.table.MarkdownTableFileSpec.ALIGNMENT_INDICATOR;
import static stexfires.io.markdown.table.MarkdownTableFileSpec.ESCAPE_REPLACEMENT;
import static stexfires.io.markdown.table.MarkdownTableFileSpec.ESCAPE_TARGET;
import static stexfires.io.markdown.table.MarkdownTableFileSpec.FIELD_DELIMITER;
import static stexfires.io.markdown.table.MarkdownTableFileSpec.FILL_CHARACTER;
import static stexfires.io.markdown.table.MarkdownTableFileSpec.HEADER_DELIMITER;
import static stexfires.util.Alignment.CENTER;
import static stexfires.util.Alignment.END;
import static stexfires.util.Alignment.START;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownTableConsumer extends AbstractInternalWritableConsumer<TextRecord> {

    private final MarkdownTableFileSpec fileSpec;
    private final Pattern escapePattern;
    private final List<MarkdownTableFieldSpec> fieldSpecs;

    public MarkdownTableConsumer(BufferedWriter bufferedWriter, MarkdownTableFileSpec fileSpec, List<MarkdownTableFieldSpec> fieldSpecs) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
        this.fieldSpecs = fieldSpecs;
        escapePattern = Pattern.compile(ESCAPE_TARGET, Pattern.LITERAL);
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write text before
        if (fileSpec.consumerTextBefore() != null) {
            writeString(fileSpec.consumerTextBefore());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }

        // write header
        if (!fieldSpecs.isEmpty()) {
            writeStringBuilderRow(buildHeaderRow());
            writeStringBuilderRow(buildSubHeaderRow());
        }
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        if (!fieldSpecs.isEmpty()) {
            writeStringBuilderRow(buildRecordRow(record));
        }
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeAfter();

        // write text after
        if (fileSpec.consumerTextAfter() != null) {
            writeString(fileSpec.consumerTextAfter());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

    private StringBuilder buildHeaderRow() {
        StringBuilder b = new StringBuilder();

        for (MarkdownTableFieldSpec fieldSpec : fieldSpecs) {
            b.append(FIELD_DELIMITER);

            writeCell(fieldSpec, b, fieldSpec.name());
        }

        // last field delimiter
        b.append(FIELD_DELIMITER);

        return b;
    }

    private StringBuilder buildSubHeaderRow() {
        StringBuilder b = new StringBuilder();

        for (MarkdownTableFieldSpec fieldSpec : fieldSpecs) {
            b.append(FIELD_DELIMITER);

            Alignment fieldAlignment = fieldSpec.determineAlignment(fileSpec);

            if (fieldAlignment != END) {
                b.append(ALIGNMENT_INDICATOR);
            }

            int additionalDelimiter = (fieldAlignment == CENTER) ? 0 : 1;
            b.append(HEADER_DELIMITER.repeat(fieldSpec.minWidth() + additionalDelimiter));

            if (fieldAlignment != START) {
                b.append(ALIGNMENT_INDICATOR);
            }
        }

        // last field delimiter
        b.append(FIELD_DELIMITER);

        return b;
    }

    private StringBuilder buildRecordRow(TextRecord record) {
        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            b.append(FIELD_DELIMITER);

            writeCell(fieldSpecs.get(fieldIndex), b, record.textAt(fieldIndex));
        }

        // last field delimiter
        b.append(FIELD_DELIMITER);

        return b;
    }

    private void writeCell(MarkdownTableFieldSpec fieldSpec, StringBuilder b, @Nullable String cellText) {
        String text = (cellText == null) ? Strings.EMPTY
                : escapePattern.matcher(cellText).replaceAll(ESCAPE_REPLACEMENT);

        int fillBefore = 0;
        int fillAfter = 0;
        int differenceToMinWidth = fieldSpec.differenceToMinWidth(text.length());
        switch (fieldSpec.determineAlignment(fileSpec)) {
            case START -> fillAfter = differenceToMinWidth;
            case CENTER -> {
                fillBefore = differenceToMinWidth / 2;
                fillAfter = (differenceToMinWidth + 1) / 2;
            }
            case END -> fillBefore = differenceToMinWidth;
        }

        // always write one additional fill character around the field delimiters
        fillBefore++;
        fillAfter++;

        b.append(FILL_CHARACTER.repeat(fillBefore));
        b.append(text);
        b.append(FILL_CHARACTER.repeat(fillAfter));
    }

    private void writeStringBuilderRow(StringBuilder tableRow) throws IOException {
        writeCharSequence(tableRow);
        writeLineSeparator(fileSpec.consumerLineSeparator());
    }

}
