package stexfires.io.markdown.list;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownListFileSpec implements ReadableWritableRecordFileSpec<ValueRecord, ValueRecord> {

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

    // FIELD - both
    private final CharsetCoding charsetCoding;
    private final LineSeparator lineSeparator;
    private final String textBefore;
    private final String textAfter;

    // FIELD - write
    private final BulletPoint bulletPoint;
    private final boolean skipNullValue;

    public MarkdownListFileSpec(CharsetCoding charsetCoding,
                                LineSeparator lineSeparator,
                                @Nullable String textBefore,
                                @Nullable String textAfter,
                                BulletPoint bulletPoint,
                                boolean skipNullValue) {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(bulletPoint);

        // both
        this.charsetCoding = charsetCoding;
        this.lineSeparator = lineSeparator;
        this.textBefore = textBefore;
        this.textAfter = textAfter;

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
    public ReadableRecordProducer<ValueRecord> producer(BufferedReader bufferedReader) {
        throw new UnsupportedOperationException("producer(BufferedReader) not implemented");
    }

    @Override
    public ReadableRecordProducer<ValueRecord> producer(InputStream inputStream) {
        throw new UnsupportedOperationException("producer(InputStream) not implemented");
    }

    @Override
    public MarkdownListConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new MarkdownListConsumer(bufferedWriter, this);
    }

    @Override
    public MarkdownListConsumer consumer(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return consumer(charsetCoding().newBufferedWriter(outputStream));
    }

    @Override
    public CharsetCoding charsetCoding() {
        return charsetCoding;
    }

    @Override
    public LineSeparator lineSeparator() {
        return lineSeparator;
    }

    @Override
    public @Nullable String textBefore() {
        return textBefore;
    }

    @Override
    public @Nullable String textAfter() {
        return textAfter;
    }

    public BulletPoint bulletPoint() {
        return bulletPoint;
    }

    public boolean skipNullValue() {
        return skipNullValue;
    }

}

