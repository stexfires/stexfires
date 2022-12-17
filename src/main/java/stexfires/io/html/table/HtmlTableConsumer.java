package stexfires.io.html.table;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static stexfires.io.html.table.HtmlTableFileSpec.*;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class HtmlTableConsumer extends AbstractInternalWritableConsumer<TextRecord> {

    private final HtmlTableFileSpec fileSpec;

    public HtmlTableConsumer(BufferedWriter bufferedWriter, HtmlTableFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
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
        writeStringRow(TABLE_BEGIN);
        writeStringRow(TABLE_ROW_BEGIN);
        writeStringBuilderRow(buildHeaderRow());
        writeStringRow(TABLE_ROW_END);
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        writeStringRow(TABLE_ROW_BEGIN);
        writeStringBuilderRow(buildRecordRow(record));
        writeStringRow(TABLE_ROW_END);
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

    private static @NotNull String convertHtml(@Nullable String value) {
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

    private StringBuilder buildHeaderRow() {
        StringBuilder b = new StringBuilder();

        for (HtmlTableFieldSpec fieldSpec : fileSpec.fieldSpecs()) {
            b.append(TABLE_HEADER_BEGIN);
            b.append(convertHtml(fieldSpec.name()));
            b.append(TABLE_HEADER_END);
        }

        return b;
    }

    private StringBuilder buildRecordRow(TextRecord record) {
        StringBuilder b = new StringBuilder();

        List<HtmlTableFieldSpec> fieldSpecs = fileSpec.fieldSpecs();
        List<TextField> fields = record.listOfFields();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            String text = (fields.size() > fieldIndex) ? fields.get(fieldIndex).text() : null;

            b.append(TABLE_DATA_BEGIN);
            b.append(convertHtml(text));
            b.append(TABLE_DATA_END);
        }

        return b;
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
