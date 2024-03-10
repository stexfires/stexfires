package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
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
        boolean embeddedInJsonObject,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSpaceAfterValueSeparator,
        String consumerArrayName,
        JsonUtil.StringEscape consumerNameEscape,
        List<JsonFieldSpec> fieldSpecs
) implements JsonFileSpec {

    public static final String DEFAULT_CONSUMER_ARRAY_NAME = "DefaultConsumerArrayName";
    public static final JsonUtil.StringEscape DEFAULT_CONSUMER_NAME_ESCAPE = JsonUtil.StringEscape.ESCAPE_NOT_NECESSARY;

    public JsonArrayFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(consumerArrayName);
        Objects.requireNonNull(consumerNameEscape);
        Objects.requireNonNull(fieldSpecs);
        fieldSpecs = List.copyOf(fieldSpecs);
    }

    public static JsonArrayFileSpec producerFileSpec(boolean embeddedInJsonObject,
                                                     List<JsonFieldSpec> fieldSpecs) {
        return new JsonArrayFileSpec(
                JSON_CHARSET_CODING,
                embeddedInJsonObject,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SPACE_AFTER_VALUE_SEPARATOR,
                DEFAULT_CONSUMER_ARRAY_NAME,
                DEFAULT_CONSUMER_NAME_ESCAPE,
                fieldSpecs
        );
    }

    public static JsonArrayFileSpec consumerFileSpec(boolean embeddedInJsonObject,
                                                     boolean consumerSpaceAfterValueSeparator,
                                                     String consumerArrayName,
                                                     JsonUtil.StringEscape consumerNameEscape,
                                                     List<JsonFieldSpec> fieldSpecs) {
        return new JsonArrayFileSpec(
                JSON_CHARSET_CODING,
                embeddedInJsonObject,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                consumerSpaceAfterValueSeparator,
                consumerArrayName,
                consumerNameEscape,
                fieldSpecs
        );
    }

    public static JsonArrayFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                     boolean embeddedInJsonObject,
                                                     LineSeparator consumerLineSeparator,
                                                     @Nullable String consumerTextBefore,
                                                     @Nullable String consumerTextAfter,
                                                     boolean consumerSpaceAfterValueSeparator,
                                                     String consumerArrayName,
                                                     JsonUtil.StringEscape consumerNameEscape,
                                                     List<JsonFieldSpec> fieldSpecs) {
        return new JsonArrayFileSpec(
                charsetCoding,
                embeddedInJsonObject,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerSpaceAfterValueSeparator,
                consumerArrayName,
                consumerNameEscape,
                fieldSpecs
        );
    }

    @Override
    public JsonProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new JsonProducer(bufferedReader, this);
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

