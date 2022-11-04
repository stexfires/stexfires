package stexfires.io.markdown.table;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.util.Alignment;

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
import static stexfires.io.markdown.table.MarkdownTableFileSpec.LAST_FIELD_DELIMITER;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownTableConsumer extends AbstractWritableConsumer<TextRecord> {

    private final MarkdownTableFileSpec fileSpec;
    private final Pattern escapePattern;

    public MarkdownTableConsumer(BufferedWriter writer, MarkdownTableFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
        escapePattern = Pattern.compile(ESCAPE_TARGET, Pattern.LITERAL);
    }

    private StringBuilder buildHeaderRow() {
        StringBuilder b = new StringBuilder();

        for (MarkdownTableFieldSpec fieldSpec : fileSpec.getFieldSpecs()) {
            b.append(FIELD_DELIMITER);

            // header name
            int valueWidth;
            if (fieldSpec.name() != null) {
                // Escape pipe
                String value = escapePattern.matcher(fieldSpec.name()).replaceAll(ESCAPE_REPLACEMENT);
                b.append(value);
                valueWidth = value.length();
            } else {
                valueWidth = 0;
            }

            // fill character
            b.append(FILL_CHARACTER.repeat(Math.max(0, fieldSpec.minWidth() - valueWidth) + 1));
        }

        if (!b.isEmpty()) {
            b.append(LAST_FIELD_DELIMITER);
        }

        return b;
    }

    private StringBuilder buildSubHeaderRow() {
        StringBuilder b = new StringBuilder();

        for (MarkdownTableFieldSpec fieldSpec : fileSpec.getFieldSpecs()) {
            b.append(FIELD_DELIMITER);

            // header underline
            Alignment fieldAlignment = (fieldSpec.alignment() != null) ? fieldSpec.alignment() : fileSpec.getAlignment();

            if (fieldAlignment != Alignment.END) {
                b.append(ALIGNMENT_INDICATOR);
            }

            int valueWidth = (fieldAlignment == Alignment.CENTER) ? 2 : 1;
            b.append(HEADER_DELIMITER.repeat(Math.max(0, fieldSpec.minWidth() - valueWidth)));

            if (fieldAlignment != Alignment.START) {
                b.append(ALIGNMENT_INDICATOR);
            }

            b.append(FILL_CHARACTER);
        }

        if (!b.isEmpty()) {
            b.append(LAST_FIELD_DELIMITER);
        }

        return b;
    }

    private StringBuilder buildRecordRow(TextRecord record) {
        StringBuilder b = new StringBuilder();

        List<MarkdownTableFieldSpec> fieldSpecs = fileSpec.getFieldSpecs();
        List<TextField> fields = record.listOfFields();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            MarkdownTableFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);
            int minWidth = fieldSpec.minWidth();

            TextField field = (fields.size() > fieldIndex) ? fields.get(fieldIndex) : null;
            String text = (field != null) ? field.text() : null;
            if (text != null) {
                // Escape pipe
                text = escapePattern.matcher(text).replaceAll(ESCAPE_REPLACEMENT);
            }
            int textWidth = (text != null) ? text.length() : 0;

            b.append(FIELD_DELIMITER);

            if (text != null) {
                b.append(text);
            }

            b.append(FILL_CHARACTER.repeat(Math.max(0, minWidth - textWidth)));

            b.append(FILL_CHARACTER);
        }

        if (!b.isEmpty()) {
            b.append(LAST_FIELD_DELIMITER);
        }

        return b;
    }

    private void writeStringBuilderRow(StringBuilder tableRow) throws IOException {
        writeCharSequence(tableRow);
        writeLineSeparator(fileSpec.lineSeparator());
    }

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();

        if (fileSpec.getBeforeTable() != null) {
            writeString(fileSpec.getBeforeTable());
            writeLineSeparator(fileSpec.lineSeparator());
        }

        writeStringBuilderRow(buildHeaderRow());
        writeStringBuilderRow(buildSubHeaderRow());
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        writeStringBuilderRow(buildRecordRow(record));
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();

        if (fileSpec.getAfterTable() != null) {
            writeString(fileSpec.getAfterTable());
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

}
