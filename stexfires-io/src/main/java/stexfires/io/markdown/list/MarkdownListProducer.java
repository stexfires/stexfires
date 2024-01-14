package stexfires.io.markdown.list;

import stexfires.io.internal.AbstractInternalReadableProducer;
import stexfires.io.producer.AbstractRecordRawDataIterator;
import stexfires.io.producer.RecordRawData;
import stexfires.record.ValueRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.function.StringUnaryOperators;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @since 0.1
 */
public final class MarkdownListProducer extends AbstractInternalReadableProducer<ValueRecord> {

    private final MarkdownListFileSpec fileSpec;

    public MarkdownListProducer(BufferedReader bufferedReader, MarkdownListFileSpec fileSpec) {
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
        return new MarkdownListIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<ValueRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        var splitResult = MarkdownListMarker.split(recordRawData.rawData());
        if (splitResult.isEmpty()) {
            throw new UncheckedProducerException(new ProducerException("Line is not a valid markdown list item! index=" + recordRawData.recordId()));
        }

        String value;
        if (fileSpec.producerTrimValueToEmpty()) {
            // trim to empty
            value = Objects.requireNonNull(StringUnaryOperators.trimToEmpty().apply(splitResult.get().value()));
        } else {
            value = splitResult.get().value();
        }

        ValueRecord record;
        if (fileSpec.producerSkipEmptyValue() && value.isEmpty()) {
            // skip empty
            record = null;
        } else {
            String category = fileSpec.producerLinePrefixAsCategory() ? splitResult.get().linePrefix() : null;
            record = new ValueFieldRecord(category, recordRawData.recordId(), value);
        }

        return Optional.ofNullable(record);
    }

    private static final class MarkdownListIterator extends AbstractRecordRawDataIterator {

        private final MarkdownListFileSpec fileSpec;

        private MarkdownListIterator(BufferedReader reader, MarkdownListFileSpec fileSpec) {
            super(reader, fileSpec.producerIgnoreFirstRecords(), fileSpec.producerIgnoreLastRecords());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws UncheckedProducerException {
            String rawData = fileSpec.producerReadLineHandling().readAndHandleLine(reader);
            return RecordRawData.buildOptionalRecordRawData(null, recordIndex, rawData);
        }

    }

}
