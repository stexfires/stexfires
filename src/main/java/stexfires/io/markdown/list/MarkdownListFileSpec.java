package stexfires.io.markdown.list;

import stexfires.io.spec.AbstractRecordFileSpec;
import stexfires.util.LineSeparator;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownListFileSpec extends AbstractRecordFileSpec {

    public enum BulletPoint {
        NUMBER, STAR, DASH
    }

    public static final String BULLET_POINT_NUMBER = ".";
    public static final String BULLET_POINT_STAR = "*";
    public static final String BULLET_POINT_DASH = "-";

    public static final BulletPoint DEFAULT_BULLET_POINT = BulletPoint.STAR;
    public static final boolean DEFAULT_SKIP_NULL_VALUE = false;

    private final String beforeList;
    private final String afterList;
    private final BulletPoint bulletPoint;
    private final boolean skipNullValue;

    public MarkdownListFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                String beforeList, String afterList,
                                BulletPoint bulletPoint, boolean skipNullValue,
                                LineSeparator lineSeparator) {
        super(charset, codingErrorAction, lineSeparator);
        Objects.requireNonNull(bulletPoint);

        this.beforeList = beforeList;
        this.afterList = afterList;
        this.bulletPoint = bulletPoint;
        this.skipNullValue = skipNullValue;
    }

    public static MarkdownListFileSpec write(Charset charset,
                                             String beforeList, String afterList,
                                             BulletPoint bulletPoint, boolean skipNullValue,
                                             LineSeparator lineSeparator) {
        return new MarkdownListFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                beforeList, afterList,
                bulletPoint, skipNullValue, lineSeparator);
    }

    public static MarkdownListFileSpec write(Charset charset,
                                             LineSeparator lineSeparator) {
        return new MarkdownListFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                DEFAULT_BULLET_POINT, DEFAULT_SKIP_NULL_VALUE, lineSeparator);
    }

    public MarkdownListFile file(Path path) {
        return new MarkdownListFile(path, this);
    }

    public MarkdownListConsumer consumer(OutputStream outputStream) {
        return new MarkdownListConsumer(newBufferedWriter(outputStream), this);
    }

    public String getBeforeList() {
        return beforeList;
    }

    public String getAfterList() {
        return afterList;
    }

    public BulletPoint getBulletPoint() {
        return bulletPoint;
    }

    public boolean isSkipNullValue() {
        return skipNullValue;
    }

}

