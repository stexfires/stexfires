package stexfires.io.markdown.table;

import stexfires.util.Alignment;
import stexfires.util.LineSeparator;

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
public final class MarkdownTableFileSpec {

    public static final String ESCAPE_TARGET = "|";
    public static final String ESCAPE_REPLACEMENT = Matcher.quoteReplacement("\\|");
    public static final String FILL_CHARACTER = " ";
    public static final String FIELD_DELIMITER = "| ";
    public static final String LAST_FIELD_DELIMITER = "|";

    public static final CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;
    public static final Alignment DEFAULT_ALIGNMENT = Alignment.START;

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;
    private final Alignment alignment;
    private final List<MarkdownTableFieldSpec> fieldSpecs;
    private final String beforeTable;
    private final String afterTable;
    private final LineSeparator lineSeparator;

    public MarkdownTableFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                 Alignment alignment,
                                 List<MarkdownTableFieldSpec> fieldSpecs,
                                 String beforeTable, String afterTable,
                                 LineSeparator lineSeparator) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(alignment);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(lineSeparator);

        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.alignment = alignment;
        this.fieldSpecs = new ArrayList(fieldSpecs);
        this.beforeTable = beforeTable;
        this.afterTable = afterTable;
        this.lineSeparator = lineSeparator;
    }

    public static MarkdownTableFileSpec write(Charset charset,
                                              Alignment alignment,
                                              List<MarkdownTableFieldSpec> fieldSpecs,
                                              String beforeTable, String afterTable,
                                              LineSeparator lineSeparator) {
        return new MarkdownTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                alignment, fieldSpecs,
                beforeTable, afterTable,
                lineSeparator);
    }

    public static MarkdownTableFileSpec write(Charset charset,
                                              List<MarkdownTableFieldSpec> fieldSpecs,
                                              LineSeparator lineSeparator) {
        return new MarkdownTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                DEFAULT_ALIGNMENT, fieldSpecs,
                null, null,
                lineSeparator);
    }

    public MarkdownTableFile file(Path path) {
        return new MarkdownTableFile(path, this);
    }

    public Charset getCharset() {
        return charset;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public List<MarkdownTableFieldSpec> getFieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public String getBeforeTable() {
        return beforeTable;
    }

    public String getAfterTable() {
        return afterTable;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

}

