package stexfires.io.fixedwidth;

import stexfires.core.Record;
import stexfires.core.producer.ProducerException;
import stexfires.core.producer.UncheckedProducerException;
import stexfires.core.record.StandardRecord;
import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.util.Alignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static stexfires.util.Alignment.END;
import static stexfires.util.Alignment.START;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class FixedWidthProducer extends AbstractReadableProducer<Record> {

    private static final String NO_VALUE = null;
    private static final String ONLY_FILL_CHAR_VALUE = "";

    protected final FixedWidthFileSpec fileSpec;

    public FixedWidthProducer(BufferedReader reader, FixedWidthFileSpec fileSpec) {
        super(reader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    protected static String removeFillCharacters(String value, Character fillCharacter, Alignment alignment) {
        int beginIndex = 0;
        int endIndex = value.length();

        if (alignment != START) {
            while ((beginIndex < endIndex)
                    && ((int) value.charAt(beginIndex) == fillCharacter)) {
                beginIndex++;
            }
        }
        if (alignment != END) {
            while ((beginIndex < endIndex)
                    && ((int) value.charAt(endIndex - 1) == fillCharacter)) {
                endIndex--;
            }
        }

        return (beginIndex < endIndex) ? value.substring(beginIndex, endIndex) : ONLY_FILL_CHAR_VALUE;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() throws UncheckedProducerException {
        return new FixedWidthIterator(reader, fileSpec);
    }

    @Override
    protected Optional<Record> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        StandardRecord record = null;
        String rawData = recordRawData.getRawData();

        boolean skipEmptyLine = fileSpec.isSkipEmptyLines() && rawData.isEmpty();

        if (!skipEmptyLine) {
            int dataLength = Math.min(rawData.length(), fileSpec.getRecordWidth());
            boolean nonEmptyFound = false;

            // Convert rawData to values
            List<String> newValues = new ArrayList<>(fileSpec.getFieldSpecs().size());
            for (FixedWidthFieldSpec fieldSpec : fileSpec.getFieldSpecs()) {
                Character fillCharacter = fieldSpec.getFillCharacter() != null ? fieldSpec.getFillCharacter() : fileSpec.getFillCharacter();
                Alignment alignment = fieldSpec.getAlignment() != null ? fieldSpec.getAlignment() : fileSpec.getAlignment();

                int beginIndex = Math.max(fieldSpec.getStartIndex(), 0);
                int endIndex = Math.min(fieldSpec.getStartIndex() + fieldSpec.getWidth(), dataLength);

                String value = NO_VALUE;
                if (beginIndex < endIndex) {
                    value = rawData.substring(beginIndex, endIndex);
                    value = removeFillCharacters(value, fillCharacter, alignment);
                    nonEmptyFound = nonEmptyFound || !value.isEmpty();
                }

                newValues.add(value);
            }

            boolean skipAllNullOrEmpty = fileSpec.isSkipAllNullOrEmpty() && !nonEmptyFound;

            if (!skipAllNullOrEmpty) {
                record = new StandardRecord(recordRawData.getCategory(), recordRawData.getRecordId(), newValues);
            }
        }

        return Optional.ofNullable(record);
    }

    protected static final class FixedWidthIterator extends AbstractRecordRawDataIterator {

        private final FixedWidthFileSpec fileSpec;

        public FixedWidthIterator(BufferedReader reader, FixedWidthFileSpec fileSpec) {
            super(reader, fileSpec.getIgnoreFirst(), fileSpec.getIgnoreLast());
            this.fileSpec = fileSpec;
        }

        @Override
        protected RecordRawData readNext(BufferedReader reader, long recordIndex) throws ProducerException, IOException {
            if (fileSpec.isSeparateRecordsByLineSeparator()) {
                return readNextRecordRawDataLines(reader, recordIndex);
            } else {
                return readNextRecordRawDataWidth(reader, recordIndex);
            }
        }

        @SuppressWarnings("MethodMayBeStatic")
        RecordRawData readNextRecordRawDataLines(BufferedReader reader, long recordIndex) throws IOException {
            String rawData = reader.readLine();
            if (rawData == null) {
                return null;
            }
            return new RecordRawData(null, recordIndex, rawData);

        }

        RecordRawData readNextRecordRawDataWidth(BufferedReader reader, long recordIndex) throws IOException {
            char[] c = new char[fileSpec.getRecordWidth()];
            int r = reader.read(c);
            if (r < 0) {
                return null;
            }
            return new RecordRawData(null, recordIndex, String.valueOf(c));
        }

    }

}
