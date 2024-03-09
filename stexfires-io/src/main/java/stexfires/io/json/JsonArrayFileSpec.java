package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.util.List;
import java.util.Objects;

import static stexfires.io.json.JsonUtil.StringEscape.ESCAPE_STRING;
import static stexfires.io.json.JsonUtil.buildJsonString;
import static stexfires.io.json.JsonUtil.escapeJsonString;

/**
 * @since 0.1
 */
public record JsonArrayFileSpec(
        CharsetCoding charsetCoding,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSpaceAfterValueSeparator,
        String consumerArrayName,
        JsonUtil.StringEscape consumerNameEscape,
        boolean embeddedInJsonObject,
        List<JsonFieldSpec> fieldSpecs
) implements JsonFileSpec {

    public JsonArrayFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(consumerArrayName);
        Objects.requireNonNull(consumerNameEscape);
        fieldSpecs = List.copyOf(fieldSpecs);
    }

    public static JsonArrayFileSpec consumerFileSpec(boolean consumerSpaceAfterValueSeparator,
                                                     boolean embeddedInJsonObject,
                                                     String consumerArrayName,
                                                     JsonUtil.StringEscape consumerNameEscape,
                                                     List<JsonFieldSpec> fieldSpecs) {
        return new JsonArrayFileSpec(
                JSON_CHARSET_CODING,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                consumerSpaceAfterValueSeparator,
                consumerArrayName,
                consumerNameEscape,
                embeddedInJsonObject,
                fieldSpecs
        );
    }

    public static JsonArrayFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                     LineSeparator consumerLineSeparator,
                                                     @Nullable String consumerTextBefore,
                                                     @Nullable String consumerTextAfter,
                                                     boolean consumerSpaceAfterValueSeparator,
                                                     boolean embeddedInJsonObject,
                                                     String consumerArrayName,
                                                     JsonUtil.StringEscape consumerNameEscape,
                                                     List<JsonFieldSpec> fieldSpecs) {
        return new JsonArrayFileSpec(
                charsetCoding,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerSpaceAfterValueSeparator,
                consumerArrayName,
                consumerNameEscape,
                embeddedInJsonObject,
                fieldSpecs
        );
    }

    @Override
    public JsonConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new JsonConsumer(bufferedWriter, this, fieldSpecs);
    }

    public String arrayNameAsJsonString() {
        return buildJsonString(consumerNameEscape == ESCAPE_STRING ? escapeJsonString(consumerArrayName) : consumerArrayName);
    }

}

