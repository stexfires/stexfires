package stexfires.io.html.table;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.core.consumer.ConsumerException;
import stexfires.io.internal.AbstractWritableConsumer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class HtmlTableConsumer extends AbstractWritableConsumer<Record> {

    protected final HtmlTableFileSpec fileSpec;

    public HtmlTableConsumer(BufferedWriter writer, HtmlTableFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    protected static String createRecordString(List<HtmlTableFieldSpec> fieldSpecs, List<Field> fields) {
        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            // HtmlTableFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);

            Field field = (fields.size() > fieldIndex) ? fields.get(fieldIndex) : null;
            String value = (field != null) ? field.getValue() : null;
            value = convertHtml(value);

            b.append(HtmlTableFileSpec.TABLE_DATA_BEGIN);

            if (value != null) {
                b.append(value);
            }

            b.append(HtmlTableFileSpec.TABLE_DATA_END);
        }

        return b.toString();
    }

    protected static String createHeaderString(List<HtmlTableFieldSpec> fieldSpecs) {
        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            HtmlTableFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);

            b.append(HtmlTableFileSpec.TABLE_HEADER_BEGIN);

            String value = fieldSpec.getName();
            value = convertHtml(value);

            if (value != null) {
                b.append(value);
            }

            b.append(HtmlTableFileSpec.TABLE_HEADER_END);
        }

        return b.toString();
    }

    protected static String convertHtml(String value) {
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

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();
        String separator = fileSpec.getLineSeparator().string();

        if (fileSpec.getBeforeTable() != null) {
            write(fileSpec.getBeforeTable());
            write(separator);
        }

        write(HtmlTableFileSpec.TABLE_BEGIN);
        write(separator);
        write(HtmlTableFileSpec.TABLE_ROW_BEGIN);
        write(separator);

        write(createHeaderString(fileSpec.getFieldSpecs()));

        write(separator);
        write(HtmlTableFileSpec.TABLE_ROW_END);
        write(separator);
    }

    @Override
    public void writeRecord(Record record) throws IOException, ConsumerException {
        super.writeRecord(record);
        String separator = fileSpec.getLineSeparator().string();

        write(HtmlTableFileSpec.TABLE_ROW_BEGIN);
        write(separator);

        write(createRecordString(fileSpec.getFieldSpecs(), record.listOfFields()));

        write(separator);
        write(HtmlTableFileSpec.TABLE_ROW_END);
        write(separator);
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();
        write(HtmlTableFileSpec.TABLE_END);
        if (fileSpec.getAfterTable() != null) {
            write(fileSpec.getLineSeparator().string());
            write(fileSpec.getAfterTable());
        }
    }

}
