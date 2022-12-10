package stexfires.io.singlevalue;

import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.record.ValueRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.function.StringUnaryOperators;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueProducer extends AbstractReadableProducer<ValueRecord> {

    private final SingleValueFileSpec fileSpec;
    private final UnaryOperator<String> rawDataOperator;

    public SingleValueProducer(BufferedReader bufferedReader, SingleValueFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;

        UnaryOperator<String> combinedOperator = null;
        if (fileSpec.linePrefix() != null) {
            combinedOperator = StringUnaryOperators.removeStringFromStart(fileSpec.linePrefix());
        }
        if (fileSpec.trimToEmpty()) {
            if (combinedOperator == null) {
                combinedOperator = StringUnaryOperators.trimToEmpty();
            } else {
                combinedOperator = StringUnaryOperators.concat(combinedOperator, StringUnaryOperators.trimToEmpty());
            }
        }
        rawDataOperator = Objects.requireNonNullElseGet(combinedOperator, StringUnaryOperators::identity);
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new SingleValueIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<ValueRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        // Check missing linePrefix
        if ((fileSpec.linePrefix() != null) && !recordRawData.rawData().startsWith(fileSpec.linePrefix())) {
            throw new UncheckedProducerException(new ProducerException("Missing linePrefix! " + recordRawData));
        }

        ValueRecord record;

        String valueText = rawDataOperator.apply(recordRawData.rawData());

        if (fileSpec.skipEmptyLines() && valueText.isEmpty()) {
            // skip empty line
            record = null;
        } else {
            record = new ValueFieldRecord(recordRawData.category(), recordRawData.recordId(), valueText);
        }

        return Optional.ofNullable(record);
    }

    private static final class SingleValueIterator extends AbstractRecordRawDataIterator {

        private SingleValueIterator(BufferedReader reader, SingleValueFileSpec fileSpec) {
            super(reader, fileSpec.ignoreFirst(), fileSpec.ignoreLast());
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            String rawData = reader.readLine();
            return RecordRawData.asOptional(null, recordIndex, rawData);
        }
    }

}
