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
import java.util.Optional;

import static stexfires.util.Alignment.*;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class FixedWidthProducer extends AbstractReadableProducer<Record> {

    protected final FixedWidthFileSpec fileSpec;

    public FixedWidthProducer(BufferedReader reader, FixedWidthFileSpec fileSpec) {
        super(reader);
        this.fileSpec = fileSpec;
    }

    protected static String removeFillCharacters(String value, Character fillCharacter, Alignment alignment) {
        int beginIndex = 0;
        int endIndex = value.length();

        if (alignment != START) {
            while ((beginIndex < endIndex)
                    && (value.charAt(beginIndex) == fillCharacter)) {
                beginIndex++;
            }
        }
        if (alignment != END) {
            while ((beginIndex < endIndex)
                    && (value.charAt(endIndex - 1) == fillCharacter)) {
                endIndex--;
            }
        }

        return (beginIndex < endIndex) ? value.substring(beginIndex, endIndex) : null;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() throws UncheckedProducerException {
        return new FixedWidthIterator(reader, fileSpec);
    }

    @Override
    protected Optional<Record> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        String rawData = recordRawData.getRawData();

        // TODO recordWidth and skipEmptyLines

        // Convert rawData to values
        List<String> newValues = new ArrayList<>(fileSpec.getFieldSpecs().size());
        for (FixedWidthFieldSpec fieldSpec : fileSpec.getFieldSpecs()) {
            Character fillCharacter = fieldSpec.getFillCharacter() != null ? fieldSpec.getFillCharacter() : fileSpec.getFillCharacter();
            Alignment alignment = fieldSpec.getAlignment() != null ? fieldSpec.getAlignment() : fileSpec.getAlignment();

            int beginIndex = fieldSpec.getStartIndex();
            int endIndex = Math.min(fieldSpec.getStartIndex() + fieldSpec.getWidth(), rawData.length());

            String value = null;
            if ((beginIndex >= 0) && (beginIndex < endIndex)) {
                value = rawData.substring(beginIndex, endIndex);
                value = removeFillCharacters(value, fillCharacter, alignment);
            }

            newValues.add(value);
        }

        return Optional.of(new StandardRecord(recordRawData.getCategory(), recordRawData.getRecordId(), newValues));
    }

    protected static final class FixedWidthIterator extends AbstractRecordRawDataIterator {

        protected final FixedWidthFileSpec fileSpec;

        protected FixedWidthIterator(BufferedReader reader, FixedWidthFileSpec fileSpec) {
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

        protected RecordRawData readNextRecordRawDataLines(BufferedReader reader, long recordIndex) throws IOException {
            String rawData = reader.readLine();
            if (rawData == null) {
                return null;
            }
            return new RecordRawData(null, recordIndex, rawData);

        }

        protected RecordRawData readNextRecordRawDataWidth(BufferedReader reader, long recordIndex) throws IOException {
            char[] c = new char[fileSpec.getRecordWidth()];
            int r = reader.read(c);
            if (r < 0) {
                return null;
            }
            return new RecordRawData(null, recordIndex, String.valueOf(c));
        }

    }

}
