package stexfires.io.htmltable;

import stexfires.core.Record;
import stexfires.core.consumer.ConsumerException;
import stexfires.io.internal.AbstractWritableConsumer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();
        if (fileSpec.getBeforeTable() != null) {
            write(fileSpec.getBeforeTable());
            write(fileSpec.getLineSeparator().getSeparator());
        }
        write("<table>");
        write(fileSpec.getLineSeparator().getSeparator());
    }

    @Override
    public void writeRecord(Record record) throws IOException, ConsumerException {
        super.writeRecord(record);
        String separator = fileSpec.getLineSeparator().getSeparator();

        write("<tr>");
        write(separator);
        write(fileSpec.streamOfRecordMessages()
                      .map(msg -> {
                          String m = msg.createMessage(record);
                          if (m == null || m.isEmpty()) {
                              m = "&nbsp;";
                          } else {
                              m = m.replaceAll("&", "&amp;")
                                   .replaceAll("<", "&lt;")
                                   .replaceAll(">", "&gt;");
                          }
                          return "<td>" + m + "</td>";
                      })
                      .collect(Collectors.joining(separator)));
        // TODO Escape singleQuote and doubleQuote ?
        write(separator);
        write("</tr>");
        write(separator);
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();
        write("</table>");
        if (fileSpec.getAfterTable() != null) {
            write(fileSpec.getLineSeparator().getSeparator());
            write(fileSpec.getAfterTable());
        }
    }

}
