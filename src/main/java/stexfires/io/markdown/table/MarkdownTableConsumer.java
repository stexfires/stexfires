package stexfires.io.markdown.table;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.core.consumer.ConsumerException;
import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.util.Alignment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MarkdownTableConsumer extends AbstractWritableConsumer<Record> {

    protected final MarkdownTableFileSpec fileSpec;

    public MarkdownTableConsumer(BufferedWriter writer, MarkdownTableFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    protected static String createRecordString(List<MarkdownTableFieldSpec> fieldSpecs, List<Field> fields) {
        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            MarkdownTableFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);
            int minWidth = fieldSpec.getMinWidth();

            Field field = (fields.size() > fieldIndex) ? fields.get(fieldIndex) : null;
            String value = (field != null) ? field.getValue() : null;
            if (value != null) {
                // Escape pipe
                value = value.replaceAll("\\|", "\\\\|");
            }
            int valueWidth = (value != null) ? value.length() : 0;

            b.append("| ");

            if (value != null) {
                b.append(value);
            }

            for (int i = valueWidth; i < minWidth; i++) {
                b.append(" ");
            }

            b.append(" ");
        }

        if (b.length() > 0) {
            b.append("|");
        }

        return b.toString();
    }

    protected static String createHeaderString(List<MarkdownTableFieldSpec> fieldSpecs) {
        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            MarkdownTableFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);
            int minWidth = fieldSpec.getMinWidth();

            String value = fieldSpec.getName();
            if (value != null) {
                // Escape pipe
                value = value.replaceAll("\\|", "\\\\|");
            }
            int valueWidth = (value != null) ? value.length() : 0;

            b.append("| ");

            if (value != null) {
                b.append(value);
            }

            for (int i = valueWidth; i < minWidth; i++) {
                b.append(" ");
            }

            b.append(" ");
        }

        if (b.length() > 0) {
            b.append("|");
        }

        return b.toString();
    }

    protected static String createSubHeaderString(Alignment alignment, List<MarkdownTableFieldSpec> fieldSpecs) {
        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            MarkdownTableFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);
            int minWidth = fieldSpec.getMinWidth();

            Alignment fieldAlignment = (fieldSpec.getAlignment() != null) ? fieldSpec.getAlignment() : alignment;

            String value = null;
            switch (fieldAlignment) {
                case START:
                    value = ":---";
                    break;
                case CENTER:
                    value = ":---:";
                    break;
                case END:
                    value = "---:";
                    break;
            }

            int valueWidth = (value != null) ? value.length() : 0;

            b.append("| ");

            if (value != null) {
                b.append(value);
            }

            for (int i = valueWidth; i < minWidth; i++) {
                b.append("-");
            }

            b.append(" ");
        }

        if (b.length() > 0) {
            b.append("|");
        }

        return b.toString();
    }

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();
        if (fileSpec.getBeforeTable() != null) {
            write(fileSpec.getBeforeTable());
            write(fileSpec.getLineSeparator().string());
        }
        write(createHeaderString(fileSpec.getFieldSpecs()));
        write(fileSpec.getLineSeparator().string());
        write(createSubHeaderString(fileSpec.getAlignment(), fileSpec.getFieldSpecs()));
        write(fileSpec.getLineSeparator().string());
    }

    @Override
    public void writeRecord(Record record) throws IOException, ConsumerException {
        super.writeRecord(record);

        write(createRecordString(fileSpec.getFieldSpecs(), record.listOfFields()));

        write(fileSpec.getLineSeparator().string());
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();
        if (fileSpec.getAfterTable() != null) {
            write(fileSpec.getLineSeparator().string());
            write(fileSpec.getAfterTable());
        }
    }

}
