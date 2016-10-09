package stexfires.io.htmltable;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class HtmlTableFileSpec {

    private final Charset charset;

    private final LineSeparator lineSeparator;
    private final String beforeTable;
    private final String afterTable;
    private final List<RecordMessage<Record>> recordMessages;

    public HtmlTableFileSpec(Charset charset, LineSeparator lineSeparator, String beforeTable, String afterTable,
                             RecordMessage<Record>... recordMessages) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(lineSeparator);
        this.charset = charset;
        this.lineSeparator = lineSeparator;
        this.beforeTable = beforeTable;
        this.afterTable = afterTable;
        this.recordMessages = Collections.unmodifiableList(Arrays.asList(recordMessages));
    }

    public Charset getCharset() {
        return charset;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

    public String getBeforeTable() {
        return beforeTable;
    }

    public String getAfterTable() {
        return afterTable;
    }

    public int recordMessagesSize() {
        return recordMessages.size();
    }

    public Stream<RecordMessage<Record>> streamOfRecordMessages() {
        return recordMessages.stream();
    }

}
