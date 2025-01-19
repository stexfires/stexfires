package stexfires.io.message;

import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.util.*;

/**
 * @since 0.1
 */
public record RecordMessageFileSpec(
        CharsetCoding charsetCoding,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        RecordMessage<TextRecord> consumerRecordMessage,
        boolean consumerSkipNullOrEmptyMessages
) implements WritableRecordFileSpec<TextRecord, RecordMessageConsumer> {

    public static final @Nullable String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final @Nullable String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final boolean DEFAULT_CONSUMER_SKIP_NULL_OR_EMPTY_MESSAGES = false;

    public RecordMessageFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(consumerRecordMessage);
    }

    public static RecordMessageFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                         LineSeparator consumerLineSeparator,
                                                         RecordMessage<TextRecord> consumerRecordMessage) {
        return new RecordMessageFileSpec(
                charsetCoding,
                consumerLineSeparator,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                consumerRecordMessage,
                DEFAULT_CONSUMER_SKIP_NULL_OR_EMPTY_MESSAGES
        );
    }

    public static RecordMessageFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                         LineSeparator consumerLineSeparator,
                                                         @Nullable String consumerTextBefore,
                                                         @Nullable String consumerTextAfter,
                                                         RecordMessage<TextRecord> consumerRecordMessage,
                                                         boolean consumerSkipNullOrEmptyMessages) {
        return new RecordMessageFileSpec(
                charsetCoding,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerRecordMessage,
                consumerSkipNullOrEmptyMessages
        );
    }

    @Override
    public RecordMessageConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new RecordMessageConsumer(bufferedWriter, this);
    }

}
