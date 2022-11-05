package stexfires.io.markdown.list;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.LineSeparator;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
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

    public MarkdownListFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                @Nullable String decoderReplacement, @Nullable String encoderReplacement,
                                LineSeparator lineSeparator,
                                @Nullable String textBefore, @Nullable String textAfter,
                                BulletPoint bulletPoint, boolean skipNullValue) {
        super(charset, codingErrorAction, decoderReplacement, encoderReplacement, lineSeparator, textBefore, textAfter);
        Objects.requireNonNull(bulletPoint);

        // write
        this.bulletPoint = bulletPoint;
        this.skipNullValue = skipNullValue;
    }

    public static MarkdownListFileSpec write(Charset charset,
                                             LineSeparator lineSeparator) {
        return new MarkdownListFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                lineSeparator,
                null, null,
                DEFAULT_BULLET_POINT, DEFAULT_SKIP_NULL_VALUE);
    }

    public static MarkdownListFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                             @Nullable String encoderReplacement,
                                             LineSeparator lineSeparator) {
        return new MarkdownListFileSpec(charset, codingErrorAction,
                null, encoderReplacement,
                lineSeparator,
                null, null,
                DEFAULT_BULLET_POINT, DEFAULT_SKIP_NULL_VALUE);
    }

    public static MarkdownListFileSpec write(Charset charset,
                                             LineSeparator lineSeparator,
                                             @Nullable String textBefore, @Nullable String textAfter,
                                             BulletPoint bulletPoint, boolean skipNullValue) {
        return new MarkdownListFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                lineSeparator,
                textBefore, textAfter,
                bulletPoint, skipNullValue);
    }

    public static MarkdownListFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                             @Nullable String encoderReplacement,
                                             LineSeparator lineSeparator,
                                             @Nullable String textBefore, @Nullable String textAfter,
                                             BulletPoint bulletPoint, boolean skipNullValue) {
        return new MarkdownListFileSpec(charset, codingErrorAction,
                null, encoderReplacement,
                lineSeparator,
                textBefore, textAfter,
                bulletPoint, skipNullValue);
    }

    @Override
    public MarkdownListConsumer consumer(OutputStream outputStream) {
        return new MarkdownListConsumer(newBufferedWriter(outputStream), this);
    }

    public BulletPoint bulletPoint() {
        return bulletPoint;
    }

    public boolean skipNullValue() {
        return skipNullValue;
    }

}

