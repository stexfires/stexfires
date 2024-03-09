package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @since 0.1
 */
public record JsonMembersFileSpec(
        CharsetCoding charsetCoding,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSpaceAfterValueSeparator,
        RecordMessage<TextRecord> consumerNameRecordMessage,
        JsonUtil.StringEscape consumerNameEscape,
        boolean embeddedInJsonObject,
        List<JsonFieldSpec> fieldSpecs
) implements JsonFileSpec {

    public JsonMembersFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(consumerNameRecordMessage);
        Objects.requireNonNull(consumerNameEscape);
        fieldSpecs = List.copyOf(fieldSpecs);
    }

    public static JsonMembersFileSpec consumerFileSpec(boolean consumerSpaceAfterValueSeparator,
                                                       boolean embeddedInJsonObject,
                                                       RecordMessage<TextRecord> consumerNameRecordMessage,
                                                       JsonUtil.StringEscape consumerNameEscape,
                                                       List<JsonFieldSpec> fieldSpecs) {
        return new JsonMembersFileSpec(
                JSON_CHARSET_CODING,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                consumerSpaceAfterValueSeparator,
                consumerNameRecordMessage,
                consumerNameEscape,
                embeddedInJsonObject,
                fieldSpecs
        );
    }

    public static JsonMembersFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                       LineSeparator consumerLineSeparator,
                                                       @Nullable String consumerTextBefore,
                                                       @Nullable String consumerTextAfter,
                                                       boolean consumerSpaceAfterValueSeparator,
                                                       boolean embeddedInJsonObject,
                                                       RecordMessage<TextRecord> consumerNameRecordMessage,
                                                       JsonUtil.StringEscape consumerNameEscape,
                                                       List<JsonFieldSpec> fieldSpecs) {
        return new JsonMembersFileSpec(
                charsetCoding,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerSpaceAfterValueSeparator,
                consumerNameRecordMessage,
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

    public Optional<String> escapedJsonNameByMessage(TextRecord record) {
        Objects.requireNonNull(record);
        String nameMessage = consumerNameRecordMessage.createMessage(record);
        if (nameMessage == null) {
            return Optional.empty();
        } else if (consumerNameEscape == JsonUtil.StringEscape.ESCAPE_STRING) {
            return Optional.of(JsonUtil.escapeJsonString(nameMessage));
        } else {
            return Optional.of(nameMessage);
        }
    }

}

