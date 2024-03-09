package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.util.List;
import java.util.Objects;

/**
 * @since 0.1
 */
public record JsonStreamingFileSpec(
        CharsetCoding charsetCoding,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSpaceAfterValueSeparator,
        boolean recordSeparatorBeforeJsonObject,
        List<JsonFieldSpec> fieldSpecs
) implements JsonFileSpec {

    public JsonStreamingFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(fieldSpecs);
        fieldSpecs = List.copyOf(fieldSpecs);
    }

    public static JsonStreamingFileSpec consumerFileSpec(boolean consumerSpaceAfterValueSeparator,
                                                         boolean recordSeparatorBeforeJsonObject,
                                                         List<JsonFieldSpec> fieldSpecs) {
        return new JsonStreamingFileSpec(
                JSON_CHARSET_CODING,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                consumerSpaceAfterValueSeparator,
                recordSeparatorBeforeJsonObject,
                fieldSpecs
        );
    }

    public static JsonStreamingFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                         LineSeparator consumerLineSeparator,
                                                         @Nullable String consumerTextBefore,
                                                         @Nullable String consumerTextAfter,
                                                         boolean consumerSpaceAfterValueSeparator,
                                                         boolean recordSeparatorBeforeJsonObject,
                                                         List<JsonFieldSpec> fieldSpecs) {
        return new JsonStreamingFileSpec(
                charsetCoding,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerSpaceAfterValueSeparator,
                recordSeparatorBeforeJsonObject,
                fieldSpecs
        );
    }

    @Override
    public JsonConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new JsonConsumer(bufferedWriter, this, fieldSpecs);
    }

    @Override
    public boolean embeddedInJsonObject() {
        return false;
    }

}

