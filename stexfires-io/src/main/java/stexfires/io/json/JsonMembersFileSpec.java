package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;
import stexfires.record.message.ToStringMessage;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @since 0.1
 */
public record JsonMembersFileSpec(
        CharsetCoding charsetCoding,
        boolean embeddedInJsonObject,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSpaceAfterValueSeparator,
        RecordMessage<TextRecord> consumerNameRecordMessage,
        boolean consumerNameEscape,
        List<JsonFieldSpec> fieldSpecs
) implements JsonFileSpec {

    public static final RecordMessage<TextRecord> DEFAULT_CONSUMER_NAME_RECORD_MESSAGE = new ToStringMessage<>();
    public static final boolean DEFAULT_CONSUMER_NAME_ESCAPE = true;

    public JsonMembersFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(consumerNameRecordMessage);
        Objects.requireNonNull(fieldSpecs);
        fieldSpecs = List.copyOf(fieldSpecs);
    }

    public static JsonMembersFileSpec producerFileSpec(boolean embeddedInJsonObject,
                                                       List<JsonFieldSpec> fieldSpecs) {
        return new JsonMembersFileSpec(
                JSON_CHARSET_CODING,
                embeddedInJsonObject,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SPACE_AFTER_VALUE_SEPARATOR,
                DEFAULT_CONSUMER_NAME_RECORD_MESSAGE,
                DEFAULT_CONSUMER_NAME_ESCAPE,
                fieldSpecs
        );
    }

    public static JsonMembersFileSpec consumerFileSpec(boolean embeddedInJsonObject,
                                                       boolean consumerSpaceAfterValueSeparator,
                                                       RecordMessage<TextRecord> consumerNameRecordMessage,
                                                       boolean consumerNameEscape,
                                                       List<JsonFieldSpec> fieldSpecs) {
        return new JsonMembersFileSpec(
                JSON_CHARSET_CODING,
                embeddedInJsonObject,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                consumerSpaceAfterValueSeparator,
                consumerNameRecordMessage,
                consumerNameEscape,
                fieldSpecs
        );
    }

    public static JsonMembersFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                       boolean embeddedInJsonObject,
                                                       LineSeparator consumerLineSeparator,
                                                       @Nullable String consumerTextBefore,
                                                       @Nullable String consumerTextAfter,
                                                       boolean consumerSpaceAfterValueSeparator,
                                                       RecordMessage<TextRecord> consumerNameRecordMessage,
                                                       boolean consumerNameEscape,
                                                       List<JsonFieldSpec> fieldSpecs) {
        return new JsonMembersFileSpec(
                charsetCoding,
                embeddedInJsonObject,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerSpaceAfterValueSeparator,
                consumerNameRecordMessage,
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

    public Optional<String> escapedJsonNameByMessage(TextRecord record) {
        Objects.requireNonNull(record);
        String nameMessage = consumerNameRecordMessage.createMessage(record);
        if (nameMessage == null) {
            return Optional.empty();
        } else if (consumerNameEscape) {
            return Optional.of(JsonUtil.escapeJsonString(nameMessage));
        } else {
            return Optional.of(nameMessage);
        }
    }

}

