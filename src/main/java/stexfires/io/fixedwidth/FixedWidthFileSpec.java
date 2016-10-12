package stexfires.io.fixedwidth;

import stexfires.util.Alignment;
import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.List;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class FixedWidthFileSpec {

    private final Charset charset;

    private final int recordWidth;
    private final boolean separateRecordsByLineSeparator;
    private final LineSeparator lineSeparator;
    private final Alignment alignment;
    private final Character fillCharacter;
    private final List<FixedWidthFieldSpec> fieldSpecs;
    private final CodingErrorAction codingErrorAction;

    public FixedWidthFileSpec(Charset charset,
                              int recordWidth,
                              boolean separateRecordsByLineSeparator,
                              LineSeparator lineSeparator,
                              Alignment alignment,
                              Character fillCharacter,
                              List<FixedWidthFieldSpec> fieldSpecs,
                              CodingErrorAction codingErrorAction) {
        this.charset = charset;
        this.recordWidth = recordWidth;
        this.separateRecordsByLineSeparator = separateRecordsByLineSeparator;
        this.lineSeparator = lineSeparator;
        this.alignment = alignment;
        this.fillCharacter = fillCharacter;
        this.fieldSpecs = fieldSpecs;
        this.codingErrorAction = codingErrorAction;
    }

    public Charset getCharset() {
        return charset;
    }

    public int getRecordWidth() {
        return recordWidth;
    }

    public boolean isSeparateRecordsByLineSeparator() {
        return separateRecordsByLineSeparator;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Character getFillCharacter() {
        return fillCharacter;
    }

    public List<FixedWidthFieldSpec> getFieldSpecs() {
        return fieldSpecs;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

}
