package stexfires.io.delimited.simple;

import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SimpleDelimitedProducer extends AbstractReadableProducer<TextRecord> {

    private static final String NO_TEXT = null;

    private final SimpleDelimitedFileSpec fileSpec;

    public SimpleDelimitedProducer(BufferedReader reader, SimpleDelimitedFileSpec fileSpec) {
        super(reader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new SimpleDelimitedIterator(getReader(), fileSpec);
    }

    @Override
    protected Optional<TextRecord> createRecord(RecordRawData recordRawData) {
        TextRecord record = null;
        String rawData = recordRawData.rawData();

        boolean skipEmptyLine = fileSpec.isSkipEmptyLines() && rawData.isEmpty();

        if (!skipEmptyLine) {
            boolean nonEmptyFound = false;

            // Convert rawData to values
            int beginIndex = 0;
            int endIndex;
            List<String> texts = new ArrayList<>(fileSpec.getFieldSpecs().size());
            for (SimpleDelimitedFieldSpec fieldSpec : fileSpec.getFieldSpecs()) {
                String text = NO_TEXT;

                endIndex = rawData.indexOf(fileSpec.getFieldDelimiter(), beginIndex);
                if (endIndex == -1) {
                    endIndex = rawData.length();
                }
                if (beginIndex < endIndex) {
                    text = rawData.substring(beginIndex, endIndex);
                    nonEmptyFound = nonEmptyFound || !text.isEmpty();
                }
                beginIndex = endIndex + 1;

                texts.add(text);
            }

            boolean skipAllNull = fileSpec.isSkipAllNull() && !nonEmptyFound;

            if (!skipAllNull) {
                record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
            }
        }

        return Optional.ofNullable(record);
    }

    private static final class SimpleDelimitedIterator extends AbstractRecordRawDataIterator {

        private SimpleDelimitedIterator(BufferedReader reader, SimpleDelimitedFileSpec fileSpec) {
            super(reader, fileSpec.getIgnoreFirst(), fileSpec.getIgnoreLast());
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            String rawData = reader.readLine();
            return RecordRawData.asOptional(null, recordIndex, rawData);
        }

    }

}
