package stexfires.io.fixedwidth;

import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.Alignment;
import stexfires.util.function.StringPredicates;

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
public final class FixedWidthProducer extends AbstractReadableProducer<TextRecord> {

    private static final String NO_TEXT = null;
    private static final String ONLY_FILL_CHAR_VALUE = "";

    private final FixedWidthFileSpec fileSpec;

    public FixedWidthProducer(BufferedReader bufferedReader, FixedWidthFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    private static String removeFillCharacters(String text, Character fillCharacter, Alignment alignment) {
        int beginIndex = 0;
        int endIndex = text.length();

        if (alignment != START) {
            while ((beginIndex < endIndex)
                    && (text.charAt(beginIndex) == fillCharacter)) {
                beginIndex++;
            }
        }
        if (alignment != END) {
            while ((beginIndex < endIndex)
                    && (text.charAt(endIndex - 1) == fillCharacter)) {
                endIndex--;
            }
        }

        return (beginIndex < endIndex) ? text.substring(beginIndex, endIndex) : ONLY_FILL_CHAR_VALUE;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new FixedWidthIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<TextRecord> createRecord(RecordRawData recordRawData) {
        TextRecord record = null;
        String rawData = recordRawData.rawData();

        boolean skipEmptyLine = fileSpec.skipEmptyLines() && rawData.isEmpty();
        if (!skipEmptyLine) {
            List<String> texts = convertRawDataIntoTexts(rawData);

            boolean skipAllNullOrEmpty = fileSpec.skipAllNullOrEmpty()
                    && texts.stream().allMatch(StringPredicates.isNullOrEmpty());

            if (!skipAllNullOrEmpty) {
                record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
            }
        }

        return Optional.ofNullable(record);
    }

    private List<String> convertRawDataIntoTexts(String rawData) {
        int dataLength = Math.min(rawData.length(), fileSpec.recordWidth());
        List<String> texts = new ArrayList<>(fileSpec.fieldSpecs().size());
        for (FixedWidthFieldSpec fieldSpec : fileSpec.fieldSpecs()) {
            int beginIndex = Math.max(fieldSpec.startIndex(), 0);
            int endIndex = Math.min(fieldSpec.startIndex() + fieldSpec.width(), dataLength);

            String text = NO_TEXT;
            if (beginIndex < endIndex) {
                text = rawData.substring(beginIndex, endIndex);
                text = removeFillCharacters(text,
                        fieldSpec.determineFillCharacter(fileSpec),
                        fieldSpec.determineAlignment(fileSpec));
            }

            texts.add(text);
        }
        return texts;
    }

    private static final class FixedWidthIterator extends AbstractRecordRawDataIterator {

        private final FixedWidthFileSpec fileSpec;

        private FixedWidthIterator(BufferedReader reader, FixedWidthFileSpec fileSpec) {
            super(reader, fileSpec.ignoreFirst(), fileSpec.ignoreLast());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            if (fileSpec.separateRecordsByLineSeparator()) {
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
            char[] c = new char[fileSpec.recordWidth()];
            int r = reader.read(c);
            if (r < 0) {
                return Optional.empty();
            }
            return RecordRawData.asOptional(null, recordIndex, String.valueOf(c));
        }

    }

}
