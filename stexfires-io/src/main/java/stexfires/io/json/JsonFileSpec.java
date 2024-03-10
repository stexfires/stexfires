package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;
import stexfires.util.Strings;

import java.util.List;

/**
 * @since 0.1
 */
public sealed interface JsonFileSpec
        extends ReadableRecordFileSpec<TextRecord, JsonProducer>, WritableRecordFileSpec<TextRecord, JsonConsumer>
        permits JsonArrayFileSpec, JsonMembersFileSpec, JsonStreamingFileSpec {

    CharsetCoding JSON_CHARSET_CODING = CharsetCoding.UTF_8_REPORTING;
    LineSeparator JSON_CONSUMER_LINE_SEPARATOR = LineSeparator.LF;

    @Nullable String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    @Nullable String DEFAULT_CONSUMER_TEXT_AFTER = null;
    boolean DEFAULT_CONSUMER_SPACE_AFTER_VALUE_SEPARATOR = false;

    @SuppressWarnings("SameReturnValue")
    boolean embeddedInJsonObject();

    @Nullable String consumerTextBefore();

    @Nullable String consumerTextAfter();

    boolean consumerSpaceAfterValueSeparator();

    List<JsonFieldSpec> fieldSpecs();

    default String whitespacesAfterValueSeparator() {
        return consumerSpaceAfterValueSeparator() ? JsonUtil.WS_SPACE : Strings.EMPTY;
    }

}
