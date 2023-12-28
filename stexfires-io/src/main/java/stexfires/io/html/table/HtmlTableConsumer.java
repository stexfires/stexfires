package stexfires.io.html.table;

import org.jspecify.annotations.Nullable;
import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static stexfires.io.html.table.HtmlTableFileSpec.*;

/**
 * @since 0.1
 */
public final class HtmlTableConsumer extends AbstractInternalWritableConsumer<TextRecord> {

    private final HtmlTableFileSpec fileSpec;
    private final List<HtmlTableFieldSpec> fieldSpecs;

    public HtmlTableConsumer(BufferedWriter bufferedWriter, HtmlTableFileSpec fileSpec,
                             List<HtmlTableFieldSpec> fieldSpecs) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        Objects.requireNonNull(fieldSpecs);
        this.fileSpec = fileSpec;
        this.fieldSpecs = fieldSpecs;
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write text before
        if (fileSpec.consumerTextBefore() != null) {
            writeString(fileSpec.consumerTextBefore());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }

        writeStringRow(TABLE_BEGIN);

        // write header
        if (!fieldSpecs.isEmpty()) {
            writeStringRow(TABLE_ROW_BEGIN);
            writeStringBuilderRow(buildHeaderRow());
            writeStringRow(TABLE_ROW_END);
        }
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        if (!fieldSpecs.isEmpty()) {
            writeStringRow(TABLE_ROW_BEGIN);
            writeStringBuilderRow(buildRecordRow(record));
            writeStringRow(TABLE_ROW_END);
        }
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeAfter();

        writeStringRow(TABLE_END);

        // write text after
        if (fileSpec.consumerTextAfter() != null) {
            writeString(fileSpec.consumerTextAfter());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

    private StringBuilder buildHeaderRow() {
        StringBuilder b = new StringBuilder();

        for (HtmlTableFieldSpec fieldSpec : fieldSpecs) {
            b.append(TABLE_HEADER_BEGIN);

            b.append(convertHtml(fieldSpec.name()));

            b.append(TABLE_HEADER_END);
        }

        return b;
    }

    private StringBuilder buildRecordRow(TextRecord record) {
        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            b.append(TABLE_DATA_BEGIN);

            b.append(convertHtml(record.textAt(fieldIndex)));

            b.append(TABLE_DATA_END);
        }

        return b;
    }

    private static String convertHtml(@Nullable String value) {
        String convertedValue;
        if (value == null || value.isEmpty()) {
            convertedValue = NON_BREAKING_SPACE;
        } else {
            convertedValue = value.replace("&", "&amp;")
                                  .replace("<", "&lt;")
                                  .replace(">", "&gt;");
        }
        return convertedValue;
    }

    private void writeStringRow(String tableRow) throws IOException {
        if (fileSpec.consumerIndentation() != null) {
            writeString(fileSpec.consumerIndentation());
        }
        writeString(tableRow);
        writeLineSeparator(fileSpec.consumerLineSeparator());
    }

    private void writeStringBuilderRow(StringBuilder tableRow) throws IOException {
        if (fileSpec.consumerIndentation() != null) {
            writeString(fileSpec.consumerIndentation());
        }
        writeCharSequence(tableRow);
        writeLineSeparator(fileSpec.consumerLineSeparator());
    }

}
