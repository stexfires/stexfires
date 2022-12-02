package stexfires.io.markdown.list;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record MarkdownListFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull LineSeparator lineSeparator,
        @Nullable String textBefore,
        @Nullable String textAfter,
        @NotNull BulletPoint bulletPoint,
        boolean skipNullValue
) implements WritableRecordFileSpec<ValueRecord, MarkdownListConsumer> {

    public enum BulletPoint {
        NUMBER, STAR, DASH
    }

    public static final String BULLET_POINT_NUMBER = ".";
    public static final String BULLET_POINT_STAR = "*";
    public static final String BULLET_POINT_DASH = "-";
    public static final String FILL_CHARACTER = " ";
    public static final long START_NUMBER = 1L;
    public static final BulletPoint DEFAULT_BULLET_POINT = BulletPoint.STAR;
    public static final boolean DEFAULT_SKIP_NULL_VALUE = false;

    public MarkdownListFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(bulletPoint);
    }

    public static MarkdownListFileSpec write(CharsetCoding charsetCoding,
                                             LineSeparator lineSeparator,
                                             @Nullable String textBefore,
                                             @Nullable String textAfter,
                                             BulletPoint bulletPoint,
                                             boolean skipNullValue) {
        return new MarkdownListFileSpec(
                charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                bulletPoint,
                skipNullValue);
    }

    @Override
    public MarkdownListConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new MarkdownListConsumer(bufferedWriter, this);
    }

}

