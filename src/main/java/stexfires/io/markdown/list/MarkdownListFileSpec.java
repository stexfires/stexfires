package stexfires.io.markdown.list;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownListFileSpec extends ReadableWritableRecordFileSpec<ValueRecord, ValueRecord> {

    public enum BulletPoint {
        NUMBER, STAR, DASH
    }

    public static final String BULLET_POINT_NUMBER = ".";
    public static final String BULLET_POINT_STAR = "*";
    public static final String BULLET_POINT_DASH = "-";

    public static final String FILL_CHARACTER = " ";
    public static final long START_NUMBER = 1L;

    // DEFAULT - write
    public static final BulletPoint DEFAULT_BULLET_POINT = BulletPoint.STAR;
    public static final boolean DEFAULT_SKIP_NULL_VALUE = false;

    // FIELD - write
    private final BulletPoint bulletPoint;
    private final boolean skipNullValue;

    public MarkdownListFileSpec(CharsetCoding charsetCoding,
                                LineSeparator lineSeparator,
                                @Nullable String textBefore,
                                @Nullable String textAfter,
                                BulletPoint bulletPoint,
                                boolean skipNullValue) {
        super(charsetCoding, lineSeparator, textBefore, textAfter);
        Objects.requireNonNull(bulletPoint);

        // write
        this.bulletPoint = bulletPoint;
        this.skipNullValue = skipNullValue;
    }

    public static MarkdownListFileSpec write(CharsetCoding charsetCoding,
                                             LineSeparator lineSeparator) {
        return new MarkdownListFileSpec(charsetCoding,
                lineSeparator,
                null,
                null,
                DEFAULT_BULLET_POINT,
                DEFAULT_SKIP_NULL_VALUE);
    }

    public static MarkdownListFileSpec write(CharsetCoding charsetCoding,
                                             LineSeparator lineSeparator,
                                             @Nullable String textBefore,
                                             @Nullable String textAfter,
                                             BulletPoint bulletPoint,
                                             boolean skipNullValue) {
        return new MarkdownListFileSpec(charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                bulletPoint,
                skipNullValue);
    }

    @Override
    public MarkdownListConsumer consumer(OutputStream outputStream) {
        return new MarkdownListConsumer(charsetCoding().newBufferedWriter(outputStream), this);
    }

    @Override
    public MarkdownListConsumer consumer(BufferedWriter bufferedWriter) {
        return new MarkdownListConsumer(bufferedWriter, this);
    }

    public BulletPoint bulletPoint() {
        return bulletPoint;
    }

    public boolean skipNullValue() {
        return skipNullValue;
    }

}

