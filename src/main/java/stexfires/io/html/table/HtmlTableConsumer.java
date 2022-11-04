package stexfires.io.html.table;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class HtmlTableConsumer extends AbstractWritableConsumer<TextRecord> {

    private final HtmlTableFileSpec fileSpec;

    public HtmlTableConsumer(BufferedWriter writer, HtmlTableFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    private static @NotNull String convertHtml(@Nullable String value) {
        String convertedValue;
        if (value == null || value.isEmpty()) {
            convertedValue = HtmlTableFileSpec.NON_BREAKING_SPACE;
        } else {
            convertedValue = value.replace("&", "&amp;")
                                  .replace("<", "&lt;")
                                  .replace(">", "&gt;");
        }
        return convertedValue;
    }

    private StringBuilder buildHeaderRow() {
        StringBuilder b = new StringBuilder();

        for (HtmlTableFieldSpec fieldSpec : fileSpec.getFieldSpecs()) {
            b.append(HtmlTableFileSpec.TABLE_HEADER_BEGIN);
            b.append(convertHtml(fieldSpec.name()));
            b.append(HtmlTableFileSpec.TABLE_HEADER_END);
        }

        return b;
    }

    private StringBuilder buildRecordRow(TextRecord record) {
        StringBuilder b = new StringBuilder();

        List<HtmlTableFieldSpec> fieldSpecs = fileSpec.getFieldSpecs();
        List<TextField> fields = record.listOfFields();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            String text = (fields.size() > fieldIndex) ? fields.get(fieldIndex).text() : null;

            b.append(HtmlTableFileSpec.TABLE_DATA_BEGIN);
            b.append(convertHtml(text));
            b.append(HtmlTableFileSpec.TABLE_DATA_END);
        }

        return b;
    }

    private void writeStringRow(String tableRow) throws IOException {
        if (fileSpec.getIndentation() != null) {
            writeString(fileSpec.getIndentation());
        }
        writeString(tableRow);
        writeLineSeparator(fileSpec.lineSeparator());
    }

    private void writeStringBuilderRow(StringBuilder tableRow) throws IOException {
        if (fileSpec.getIndentation() != null) {
            writeString(fileSpec.getIndentation());
        }
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

        writeStringRow(HtmlTableFileSpec.TABLE_BEGIN);
        writeStringRow(HtmlTableFileSpec.TABLE_ROW_BEGIN);
        writeStringBuilderRow(buildHeaderRow());
        writeStringRow(HtmlTableFileSpec.TABLE_ROW_END);
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        writeStringRow(HtmlTableFileSpec.TABLE_ROW_BEGIN);
        writeStringBuilderRow(buildRecordRow(record));
        writeStringRow(HtmlTableFileSpec.TABLE_ROW_END);
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();

        writeStringRow(HtmlTableFileSpec.TABLE_END);

        if (fileSpec.getAfterTable() != null) {
            writeString(fileSpec.getAfterTable());
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

}
