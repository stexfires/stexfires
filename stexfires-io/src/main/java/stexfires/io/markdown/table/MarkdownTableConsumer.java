package stexfires.io.markdown.table;

import org.jspecify.annotations.Nullable;
import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.util.Alignment;
import stexfires.util.Strings;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

import static stexfires.io.markdown.table.MarkdownTableFileSpec.*;
import static stexfires.util.Alignment.*;

/**
 * @since 0.1
 */
public final class MarkdownTableConsumer extends AbstractInternalWritableConsumer<TextRecord> {

    private final MarkdownTableFileSpec fileSpec;
    private final List<MarkdownTableFieldSpec> fieldSpecs;
    private final Pattern escapePattern;

    public MarkdownTableConsumer(BufferedWriter bufferedWriter, MarkdownTableFileSpec fileSpec,
                                 List<MarkdownTableFieldSpec> fieldSpecs) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        Objects.requireNonNull(fieldSpecs);
        this.fileSpec = fileSpec;
        this.fieldSpecs = fieldSpecs;
        escapePattern = Pattern.compile(ESCAPE_TARGET, Pattern.LITERAL);
    }

    StringBuilder buildHeaderRow() {
        StringBuilder b = new StringBuilder();

        for (MarkdownTableFieldSpec fieldSpec : fieldSpecs) {
            b.append(FIELD_DELIMITER);

            b.append(buildCell(fieldSpec, fieldSpec.name()));
        }

        // last field delimiter
        b.append(FIELD_DELIMITER);

        return b;
    }

    StringBuilder buildSubHeaderRow() {
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

    StringBuilder buildRecordRow(TextRecord record) {
        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            b.append(FIELD_DELIMITER);

            b.append(buildCell(fieldSpecs.get(fieldIndex), record.textAt(fieldIndex)));
        }

        // last field delimiter
        b.append(FIELD_DELIMITER);

        return b;
    }

    String buildCell(MarkdownTableFieldSpec fieldSpec, @Nullable String cellText) {
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

        return FILL_CHARACTER.repeat(fillBefore) + text + FILL_CHARACTER.repeat(fillAfter);
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

    private void writeStringBuilderRow(StringBuilder tableRow) throws IOException {
        writeCharSequence(tableRow);
        writeLineSeparator(fileSpec.consumerLineSeparator());
    }

}
