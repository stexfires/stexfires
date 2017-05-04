package stexfires.io.markdown.table;

import stexfires.io.spec.AbstractRecordFileSpec;
import stexfires.util.Alignment;
import stexfires.util.LineSeparator;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownTableFileSpec extends AbstractRecordFileSpec {

    public static final String ESCAPE_TARGET = "|";
    public static final String ESCAPE_REPLACEMENT = Matcher.quoteReplacement("\\|");
    public static final String FILL_CHARACTER = " ";
    public static final String FIELD_DELIMITER = "| ";
    public static final String LAST_FIELD_DELIMITER = "|";
    public static final String ALIGNMENT_INDICATOR = ":";
    public static final String HEADER_DELIMITER = "-";

    public static final Alignment DEFAULT_ALIGNMENT = Alignment.START;

    // both
    private final List<MarkdownTableFieldSpec> fieldSpecs;

    // write
    private final Alignment alignment;
    private final String beforeTable;
    private final String afterTable;

    public MarkdownTableFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                 List<MarkdownTableFieldSpec> fieldSpecs,
                                 LineSeparator lineSeparator,
                                 Alignment alignment,
                                 String beforeTable, String afterTable) {
        super(charset, codingErrorAction, lineSeparator);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(alignment);

        // both
        this.fieldSpecs = new ArrayList<>(fieldSpecs);

        // write
        this.alignment = alignment;
        this.beforeTable = beforeTable;
        this.afterTable = afterTable;
    }

    public static MarkdownTableFileSpec write(Charset charset,
                                              List<MarkdownTableFieldSpec> fieldSpecs,
                                              LineSeparator lineSeparator) {
        return new MarkdownTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                fieldSpecs,
                lineSeparator,
                DEFAULT_ALIGNMENT,
                null, null);
    }


    public static MarkdownTableFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                              List<MarkdownTableFieldSpec> fieldSpecs,
                                              LineSeparator lineSeparator) {
        return new MarkdownTableFileSpec(charset, codingErrorAction,
                fieldSpecs,
                lineSeparator,
                DEFAULT_ALIGNMENT,
                null, null);
    }

    public static MarkdownTableFileSpec write(Charset charset,
                                              List<MarkdownTableFieldSpec> fieldSpecs,
                                              LineSeparator lineSeparator,
                                              Alignment alignment,
                                              String beforeTable, String afterTable) {
        return new MarkdownTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                fieldSpecs,
                lineSeparator,
                alignment,
                beforeTable, afterTable);
    }

    public static MarkdownTableFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                              List<MarkdownTableFieldSpec> fieldSpecs,
                                              LineSeparator lineSeparator,
                                              Alignment alignment,
                                              String beforeTable, String afterTable) {
        return new MarkdownTableFileSpec(charset, codingErrorAction,
                fieldSpecs,
                lineSeparator,
                alignment,
                beforeTable, afterTable);
    }

    public MarkdownTableFile file(Path path) {
        return new MarkdownTableFile(path, this);
    }

    public MarkdownTableConsumer consumer(OutputStream outputStream) {
        return new MarkdownTableConsumer(newBufferedWriter(outputStream), this);
    }

    public List<MarkdownTableFieldSpec> getFieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getBeforeTable() {
        return beforeTable;
    }

    public String getAfterTable() {
        return afterTable;
    }

}

