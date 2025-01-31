package stexfires.io.json;

import com.google.gson.*;
import stexfires.io.producer.AbstractReadableProducer;
import stexfires.io.producer.AbstractRecordRawDataIterator;
import stexfires.io.producer.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @since 0.1
 */
public final class JsonProducer extends AbstractReadableProducer<TextRecord> {

    private final JsonFileSpec fileSpec;
    private final Gson gson;

    public JsonProducer(BufferedReader bufferedReader, JsonFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
        gson = new GsonBuilder()
                .disableJdkUnsafe()
                .setStrictness(Strictness.STRICT)
                .serializeNulls()
                .create();
    }

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        if (fileSpec instanceof JsonStreamingFileSpec jsonStreamingFileSpec) {
            if (!jsonStreamingFileSpec.recordSeparatorBefore()) {
                // Skip first lines by reading lines from the buffer without reading Records with the Iterator.
                if (jsonStreamingFileSpec.producerSkipFirstLines() > 0) {
                    for (int i = 0; i < jsonStreamingFileSpec.producerSkipFirstLines(); i++) {
                        bufferedReader().readLine();
                    }
                }
            } else {
                // TODO
            }
        }

        super.readBefore();
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        if (fileSpec instanceof JsonStreamingFileSpec jsonStreamingFileSpec) {
            if (!jsonStreamingFileSpec.recordSeparatorBefore()) {
                return new JsonIterator(bufferedReader(), jsonStreamingFileSpec);
            }
        }
        return new JsonIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<TextRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        Objects.requireNonNull(recordRawData);

        String rawData = recordRawData.rawData();
        TextRecord record = null;

        JsonElement jsonElement;
        try {
            jsonElement = gson.fromJson(rawData, JsonElement.class);
        } catch (JsonSyntaxException e) {
            throw new UncheckedProducerException(new ProducerException("Parse Exception"));
        }

        if (fileSpec.recordJsonType() == JsonFileSpec.RecordJsonType.OBJECT) {
            if (jsonElement.isJsonObject()) {
                List<String> texts = new ArrayList<>();
                texts.add(jsonElement.toString()); // TODO
                record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
            } else {
                throw new UncheckedProducerException(new ProducerException("Not a JSON object"));
            }
        } else if (fileSpec.recordJsonType() == JsonFileSpec.RecordJsonType.ARRAY) {
            if (jsonElement.isJsonArray()) {
                List<String> texts = new ArrayList<>();
                texts.add(jsonElement.toString()); // TODO
                record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
            } else {
                throw new UncheckedProducerException(new ProducerException("Not a JSON array"));
            }
        }

        return Optional.ofNullable(record);
    }

    private static final class JsonIterator extends AbstractRecordRawDataIterator {

        private final JsonFileSpec fileSpec;

        private JsonIterator(BufferedReader bufferedReader, JsonFileSpec fileSpec) {
            super(bufferedReader, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_FIRST);
            this.fileSpec = fileSpec;
        }

        private JsonIterator(BufferedReader bufferedReader, JsonStreamingFileSpec fileSpec) {
            super(bufferedReader, fileSpec.producerIgnoreFirstRecords(), fileSpec.producerIgnoreLastRecords());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws UncheckedProducerException {
            String rawData = null;

            if (fileSpec instanceof JsonStreamingFileSpec jsonStreamingFileSpec) {
                if (!jsonStreamingFileSpec.recordSeparatorBefore()) {
                    rawData = jsonStreamingFileSpec.producerReadLineHandling().readAndHandleLine(reader);
                }
            } else {
                // TODO
            }

            return RecordRawData.buildOptionalRecordRawData(null, recordIndex, rawData);
        }

    }

}
