package stexfires.io.config;

import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.record.KeyValueCommentRecord;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.LineSeparator;
import stexfires.util.function.StringPredicates;
import stexfires.util.function.StringUnaryOperators;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import static stexfires.io.config.ConfigFileSpec.CATEGORY_BEGIN_MARKER;
import static stexfires.io.config.ConfigFileSpec.CATEGORY_END_MARKER;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConfigProducer extends AbstractReadableProducer<KeyValueCommentRecord> {

    /**
     * Use LineFeed as a separator between a comment line and a record line.
     */
    private static final String COMMENT_SEPARATOR = LineSeparator.LF.string();
    private static final UnaryOperator<String> TRIM_TO_EMPTY = StringUnaryOperators.trimToEmpty();

    private final ConfigFileSpec fileSpec;

    public ConfigProducer(BufferedReader bufferedReader, ConfigFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new ConfigIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<KeyValueCommentRecord> createRecord(RecordRawData recordRawData) {
        String rawData = recordRawData.rawData();
        String key;
        String value;
        String comment;
        int keyIndex;
        int delimiterIndex;

        try {
            if (rawData.startsWith(fileSpec.commentLinePrefix())) {
                int separatorIndex = rawData.indexOf(COMMENT_SEPARATOR);
                // extract and trim comment
                comment = TRIM_TO_EMPTY.apply(rawData.substring(fileSpec.commentLinePrefix().length(), separatorIndex));
                keyIndex = separatorIndex + COMMENT_SEPARATOR.length();
            } else {
                // no comment found
                comment = null;
                keyIndex = 0;
            }

            delimiterIndex = rawData.indexOf(fileSpec.valueDelimiter(), keyIndex);
            if (delimiterIndex >= keyIndex) {
                key = TRIM_TO_EMPTY.apply(rawData.substring(keyIndex, delimiterIndex));
                value = TRIM_TO_EMPTY.apply(rawData.substring(delimiterIndex + fileSpec.valueDelimiter().length()));
            } else {
                // no delimiter found -> everything is the key
                key = TRIM_TO_EMPTY.apply(rawData.substring(keyIndex).trim());
                value = null;
            }
        } catch (IndexOutOfBoundsException e) {
            // The IndexOutOfBoundsException should not normally occur. Therefore, this is only in for safety.
            throw new UncheckedProducerException(new ProducerException(e));
        }
        return Optional.of(new KeyValueCommentFieldsRecord(recordRawData.category(), recordRawData.recordId(),
                key, value, comment));
    }

    private static final class ConfigIterator extends AbstractRecordRawDataIterator {

        private final ConfigFileSpec fileSpec;
        private String currentCategory;

        private ConfigIterator(BufferedReader bufferedReader, ConfigFileSpec fileSpec) {
            super(bufferedReader);
            Objects.requireNonNull(fileSpec);
            this.fileSpec = fileSpec;

            currentCategory = null;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            String rawData = null;
            String currentComment = null;
            boolean endOfStreamFound;
            boolean blankLineFound;
            boolean commentLineFound;
            boolean categoryFound;
            do {
                String lineData = reader.readLine();

                // Evaluate line data
                endOfStreamFound = (lineData == null); // null indicates end of stream
                blankLineFound = !endOfStreamFound && StringPredicates.isBlank().test(lineData);
                commentLineFound = !endOfStreamFound && lineData.startsWith(fileSpec.commentLinePrefix());
                categoryFound = !endOfStreamFound && lineData.startsWith(CATEGORY_BEGIN_MARKER) && lineData.endsWith(CATEGORY_END_MARKER);

                if (blankLineFound) {
                    currentComment = null;
                } else if (commentLineFound) {
                    currentComment = lineData;
                } else if (categoryFound) {
                    currentComment = null;
                    currentCategory = lineData.substring(CATEGORY_BEGIN_MARKER.length(), lineData.length() - CATEGORY_END_MARKER.length());
                } else if (!endOfStreamFound) {
                    if (currentComment != null) {
                        // Combine both values to return them together.
                        rawData = currentComment + COMMENT_SEPARATOR + lineData;
                    } else {
                        rawData = lineData;
                    }
                }

                // Read until a record is found or the end is reached.
            } while (blankLineFound || commentLineFound || categoryFound);

            return RecordRawData.asOptional(currentCategory, recordIndex, rawData);
        }
    }

}
