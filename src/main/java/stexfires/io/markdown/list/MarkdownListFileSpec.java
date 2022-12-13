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
        @NotNull BulletPoint bulletPoint,
        @NotNull LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSkipNullValueLines
) implements WritableRecordFileSpec<ValueRecord, MarkdownListConsumer> {

    public enum BulletPoint {

        DASH("-"),
        NUMBER("."),
        STAR("*");

        private final String characters;

        BulletPoint(String characters) {
            this.characters = characters;
        }

        public final String linePrefix(long lineNumber) {
            if (this == NUMBER) {
                return lineNumber + characters + BULLET_POINT_SEPARATOR;
            } else {
                return characters + BULLET_POINT_SEPARATOR;
            }
        }

    }

    public static final BulletPoint DEFAULT_BULLET_POINT = BulletPoint.STAR;
    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final boolean DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES = false;

    public static final String BULLET_POINT_SEPARATOR = " ";
    public static final long BULLET_POINT_START_NUMBER = 1L;

    public MarkdownListFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(bulletPoint);
        Objects.requireNonNull(consumerLineSeparator);
    }

    public static MarkdownListFileSpec write(@NotNull CharsetCoding charsetCoding,
                                             @NotNull BulletPoint bulletPoint,
                                             @NotNull LineSeparator consumerLineSeparator,
                                             @Nullable String consumerTextBefore,
                                             @Nullable String consumerTextAfter,
                                             boolean consumerSkipNullValueLines) {
        return new MarkdownListFileSpec(
                charsetCoding,
                bulletPoint,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerSkipNullValueLines);
    }

    @Override
    public MarkdownListConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new MarkdownListConsumer(bufferedWriter, this);
    }

}

