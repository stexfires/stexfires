package stexfires.io.delimited.simple;

import org.jspecify.annotations.Nullable;
import stexfires.io.internal.AbstractInternalReadableProducer;
import stexfires.io.producer.AbstractRecordRawDataIterator;
import stexfires.io.producer.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.function.StringPredicates;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @since 0.1
 */
public final class SimpleDelimitedProducer extends AbstractInternalReadableProducer<TextRecord> {

    private static final @Nullable String NO_TEXT = null;

    private final SimpleDelimitedFileSpec fileSpec;

    public SimpleDelimitedProducer(BufferedReader bufferedReader, SimpleDelimitedFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        // Skip first lines by reading lines from the buffer without reading Records with the Iterator.
        if (fileSpec.producerSkipFirstLines() > 0) {
            for (int i = 0; i < fileSpec.producerSkipFirstLines(); i++) {
                bufferedReader().readLine();
            }
        }

        super.readBefore();
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new SimpleDelimitedIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<TextRecord> createRecord(RecordRawData recordRawData) {
        TextRecord record;

        List<@Nullable String> texts = convertRawDataIntoTexts(recordRawData.rawData());

        boolean skipAllNullOrEmpty = fileSpec.producerSkipAllNullOrEmpty()
                && texts.stream().allMatch(StringPredicates.isNullOrEmpty());

        if (skipAllNullOrEmpty) {
            record = null;
        } else {
            record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
        }

        return Optional.ofNullable(record);
    }

    private List<@Nullable String> convertRawDataIntoTexts(String rawData) {
        Objects.requireNonNull(rawData);
        List<@Nullable String> texts = new ArrayList<>(fileSpec.fieldSpecs().size());
        int beginIndex = 0;
        int endIndex;
        for (SimpleDelimitedFieldSpec fieldSpec : fileSpec.fieldSpecs()) {
            String text = NO_TEXT;

            endIndex = rawData.indexOf(fileSpec.fieldDelimiter(), beginIndex);
            if (endIndex == -1) {
                endIndex = rawData.length();
            }
            if (beginIndex < endIndex) {
                text = rawData.substring(beginIndex, endIndex);
            }
            beginIndex = endIndex + 1;

            texts.add(text);
        }
        return texts;
    }

    private static final class SimpleDelimitedIterator extends AbstractRecordRawDataIterator {

        private final SimpleDelimitedFileSpec fileSpec;

        private SimpleDelimitedIterator(BufferedReader bufferedReader, SimpleDelimitedFileSpec fileSpec) {
            super(bufferedReader, fileSpec.producerIgnoreFirstRecords(), fileSpec.producerIgnoreLastRecords());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws UncheckedProducerException {
            String rawData = fileSpec.producerReadLineHandling().readAndHandleLine(reader);
            return RecordRawData.buildOptionalRecordRawData(null, recordIndex, rawData);
        }

    }

}
