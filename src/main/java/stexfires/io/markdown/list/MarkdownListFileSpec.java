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
        @NotNull LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        @NotNull BulletPoint consumerBulletPoint,
        boolean consumerSkipNullValueLines
) implements WritableRecordFileSpec<ValueRecord, MarkdownListConsumer> {

    public enum BulletPoint {
        NUMBER, STAR, DASH
    }

    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final BulletPoint DEFAULT_CONSUMER_BULLET_POINT = BulletPoint.STAR;
    public static final boolean DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES = false;

    public static final String BULLET_POINT_NUMBER = ".";
    public static final String BULLET_POINT_STAR = "*";
    public static final String BULLET_POINT_DASH = "-";
    public static final String FILL_CHARACTER = " ";
    public static final long START_NUMBER = 1L;

    public MarkdownListFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(consumerBulletPoint);
    }

    public static MarkdownListFileSpec write(@NotNull CharsetCoding charsetCoding,
                                             @NotNull LineSeparator consumerLineSeparator,
                                             @Nullable String consumerTextBefore,
                                             @Nullable String consumerTextAfter,
                                             @NotNull BulletPoint consumerBulletPoint,
                                             boolean consumerSkipNullValueLines) {
        return new MarkdownListFileSpec(
                charsetCoding,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerBulletPoint,
                consumerSkipNullValueLines);
    }

    @Override
    public MarkdownListConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new MarkdownListConsumer(bufferedWriter, this);
    }

}

