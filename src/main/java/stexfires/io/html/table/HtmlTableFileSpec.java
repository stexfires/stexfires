package stexfires.io.html.table;

import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class HtmlTableFileSpec {

    public static final String TABLE_BEGIN = "<table>";
    public static final String TABLE_END = "</table>";
    public static final String TABLE_ROW_BEGIN = "<tr>";
    public static final String TABLE_ROW_END = "</tr>";
    public static final String TABLE_HEADER_BEGIN = "<th>";
    public static final String TABLE_HEADER_END = "</th>";
    public static final String TABLE_DATA_BEGIN = "<td>";
    public static final String TABLE_DATA_END = "</td>";
    public static final String NON_BREAKING_SPACE = "&nbsp;";

    public static final CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;
    private final List<HtmlTableFieldSpec> fieldSpecs;
    private final String beforeTable;
    private final String afterTable;
    private final LineSeparator lineSeparator;

    public HtmlTableFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                             List<HtmlTableFieldSpec> fieldSpecs,
                             String beforeTable, String afterTable,
                             LineSeparator lineSeparator) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(lineSeparator);

        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.fieldSpecs = new ArrayList<>(fieldSpecs);
        this.beforeTable = beforeTable;
        this.afterTable = afterTable;
        this.lineSeparator = lineSeparator;
    }

    public static HtmlTableFileSpec write(Charset charset,
                                          List<HtmlTableFieldSpec> fieldSpecs,
                                          String beforeTable, String afterTable,
                                          LineSeparator lineSeparator) {
        return new HtmlTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                fieldSpecs,
                beforeTable, afterTable,
                lineSeparator);
    }

    public static HtmlTableFileSpec write(Charset charset,
                                          List<HtmlTableFieldSpec> fieldSpecs,
                                          LineSeparator lineSeparator) {
        return new HtmlTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                fieldSpecs,
                null, null,
                lineSeparator);
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

    public List<HtmlTableFieldSpec> getFieldSpecs() {
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

