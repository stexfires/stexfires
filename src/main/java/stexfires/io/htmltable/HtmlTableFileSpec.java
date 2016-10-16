package stexfires.io.htmltable;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
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

    public static final CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;

    private final LineSeparator lineSeparator;
    private final String beforeTable;
    private final String afterTable;
    private final List<RecordMessage<Record>> recordMessages;

    @SafeVarargs
    public HtmlTableFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                             LineSeparator lineSeparator,
                             String beforeTable, String afterTable,
                             RecordMessage<Record>... recordMessages) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(recordMessages);
        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.lineSeparator = lineSeparator;
        this.beforeTable = beforeTable;
        this.afterTable = afterTable;
        this.recordMessages = Collections.unmodifiableList(Arrays.asList(recordMessages));
    }

    public static HtmlTableFileSpec write(Charset charset,
                                          LineSeparator lineSeparator,
                                          String beforeTable, String afterTable,
                                          RecordMessage<Record>... recordMessages) {
        return new HtmlTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                lineSeparator,
                beforeTable, afterTable,
                recordMessages);
    }

    public static HtmlTableFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                          LineSeparator lineSeparator,
                                          String beforeTable, String afterTable,
                                          RecordMessage<Record>... recordMessages) {
        return new HtmlTableFileSpec(charset, codingErrorAction,
                lineSeparator,
                beforeTable, afterTable,
                recordMessages);
    }

    public HtmlTableFile file(Path path) {
        return new HtmlTableFile(path, this);
    }

    public Charset getCharset() {
        return charset;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
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

