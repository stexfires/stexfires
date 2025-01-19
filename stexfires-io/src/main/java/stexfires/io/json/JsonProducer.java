package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.io.internal.AbstractInternalReadableProducer;
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
public final class JsonProducer extends AbstractInternalReadableProducer<TextRecord> {

    private final JsonFileSpec fileSpec;

    public JsonProducer(BufferedReader bufferedReader, JsonFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
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
        TextRecord record = null;
        List<@Nullable String> texts = new ArrayList<>();
/*
        Map<String, @Nullable String> jsonMap = JsonUtil.parseJsonObject(recordRawData.rawData());


        for (JsonFieldSpec fieldSpec : fileSpec.fieldSpecs()) {
            String name = fieldSpec.escapedName();
            if (jsonMap.containsKey(name)) {
                String value = jsonMap.get(name);
                // TODO Check value and remove quotation marks
                String text = null;
                switch (fieldSpec.valueType()) {
                    case OBJECT -> {
                    }
                    case ARRAY -> {
                    }
                    case NUMBER -> {
                    }
                    case STRING -> {
                    }
                    case BOOLEAN -> {
                    }
                }
                texts.add(text);
            }
        }
 */

        if (!texts.isEmpty()) { // TODO new fileSpec for skipping
            record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
        }

        return Optional.ofNullable(record);
    }

    private static final class JsonIterator extends AbstractRecordRawDataIterator {

        private final JsonFileSpec fileSpec;

        private JsonIterator(BufferedReader reader, JsonFileSpec fileSpec) {
            super(reader, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_FIRST);
            this.fileSpec = fileSpec;
        }

        private JsonIterator(BufferedReader reader, JsonStreamingFileSpec fileSpec) {
            super(reader, fileSpec.producerIgnoreFirstRecords(), fileSpec.producerIgnoreLastRecords());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws UncheckedProducerException {
            String rawData = null;

            if (fileSpec instanceof JsonStreamingFileSpec jsonStreamingFileSpec) {
                if (!jsonStreamingFileSpec.recordSeparatorBefore()) {
                    rawData = jsonStreamingFileSpec.producerReadLineHandling().readAndHandleLine(reader);
                }
            }

            return RecordRawData.buildOptionalRecordRawData(null, recordIndex, rawData);
        }

    }

}
