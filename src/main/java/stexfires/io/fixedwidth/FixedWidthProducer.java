package stexfires.io.fixedwidth;

import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.Alignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static stexfires.util.Alignment.END;
import static stexfires.util.Alignment.START;

// TODO chars and codePoints

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class FixedWidthProducer extends AbstractReadableProducer<TextRecord> {

    private static final String NO_TEXT = null;
    private static final String ONLY_FILL_CHAR_VALUE = "";

    private final FixedWidthFileSpec fileSpec;

    public FixedWidthProducer(BufferedReader reader, FixedWidthFileSpec fileSpec) {
        super(reader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    private static String removeFillCharacters(String value, Character fillCharacter, Alignment alignment) {
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

        return (beginIndex < endIndex) ? value.substring(beginIndex, endIndex) : ONLY_FILL_CHAR_VALUE;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new FixedWidthIterator(reader, fileSpec);
    }

    @Override
    protected Optional<TextRecord> createRecord(RecordRawData recordRawData) {
        TextRecord record = null;
        String rawData = recordRawData.rawData();

        boolean skipEmptyLine = fileSpec.isSkipEmptyLines() && rawData.isEmpty();

        if (!skipEmptyLine) {
            int dataLength = Math.min(rawData.length(), fileSpec.getRecordWidth());
            boolean nonEmptyFound = false;

            // Convert rawData to values
            List<String> texts = new ArrayList<>(fileSpec.getFieldSpecs().size());
            for (FixedWidthFieldSpec fieldSpec : fileSpec.getFieldSpecs()) {
                Character fillCharacter = fieldSpec.getFillCharacter() != null ? fieldSpec.getFillCharacter() : fileSpec.getFillCharacter();
                Alignment alignment = fieldSpec.getAlignment() != null ? fieldSpec.getAlignment() : fileSpec.getAlignment();

                int beginIndex = Math.max(fieldSpec.getStartIndex(), 0);
                int endIndex = Math.min(fieldSpec.getStartIndex() + fieldSpec.getWidth(), dataLength);

                String text = NO_TEXT;
                if (beginIndex < endIndex) {
                    text = rawData.substring(beginIndex, endIndex);
                    text = removeFillCharacters(text, fillCharacter, alignment);
                    nonEmptyFound = nonEmptyFound || !text.isEmpty();
                }

                texts.add(text);
            }

            boolean skipAllNullOrEmpty = fileSpec.isSkipAllNullOrEmpty() && !nonEmptyFound;

            if (!skipAllNullOrEmpty) {
                record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
            }
        }

        return Optional.ofNullable(record);
    }

    private static final class FixedWidthIterator extends AbstractRecordRawDataIterator {

        private final FixedWidthFileSpec fileSpec;

        private FixedWidthIterator(BufferedReader reader, FixedWidthFileSpec fileSpec) {
            super(reader, fileSpec.getIgnoreFirst(), fileSpec.getIgnoreLast());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            if (fileSpec.isSeparateRecordsByLineSeparator()) {
                return readNextRecordRawDataLines(reader, recordIndex);
            } else {
                return readNextRecordRawDataWidth(reader, recordIndex);
            }
        }

        @SuppressWarnings("MethodMayBeStatic")
        private Optional<RecordRawData> readNextRecordRawDataLines(BufferedReader reader, long recordIndex) throws IOException {
            String rawData = reader.readLine();
            return RecordRawData.asOptional(null, recordIndex, rawData);

        }

        private Optional<RecordRawData> readNextRecordRawDataWidth(BufferedReader reader, long recordIndex) throws IOException {
            char[] c = new char[fileSpec.getRecordWidth()];
            int r = reader.read(c);
            if (r < 0) {
                return Optional.empty();
            }
            return RecordRawData.asOptional(null, recordIndex, String.valueOf(c));
        }

    }

}
