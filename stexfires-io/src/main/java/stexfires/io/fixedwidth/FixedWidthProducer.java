package stexfires.io.fixedwidth;

import org.jspecify.annotations.Nullable;
import stexfires.io.internal.AbstractInternalReadableProducer;
import stexfires.io.producer.AbstractRecordRawDataIterator;
import stexfires.io.producer.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.Alignment;
import stexfires.util.Strings;
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
 * @since 0.1
 */
public final class FixedWidthProducer extends AbstractInternalReadableProducer<TextRecord> {

    private static final @Nullable String NO_TEXT = null;

    private final FixedWidthFileSpec fileSpec;
    private final List<FixedWidthFieldSpec> fieldSpecs;

    public FixedWidthProducer(BufferedReader bufferedReader, FixedWidthFileSpec fileSpec,
                              List<FixedWidthFieldSpec> fieldSpecs) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        Objects.requireNonNull(fieldSpecs);
        this.fileSpec = fileSpec;
        this.fieldSpecs = fieldSpecs;
    }

    static String removeFillCharacters(String text, Character fillCharacter, Alignment alignment) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(fillCharacter);
        Objects.requireNonNull(alignment);

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

        return (beginIndex < endIndex) ? text.substring(beginIndex, endIndex) : Strings.EMPTY;
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
        return new FixedWidthIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<TextRecord> createRecord(RecordRawData recordRawData) {
        TextRecord record = null;
        String rawData = recordRawData.rawData();

        boolean skipEmptyLine = fileSpec.producerSkipEmptyLines() && rawData.isEmpty();
        if (!skipEmptyLine) {
            List<@Nullable String> texts = convertRawDataIntoTexts(rawData);

            boolean skipAllNullOrEmpty = fileSpec.producerSkipAllNullOrEmpty()
                    && texts.stream().allMatch(StringPredicates.isNullOrEmpty());

            if (!skipAllNullOrEmpty) {
                record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
            }
        }

        return Optional.ofNullable(record);
    }

    private List<@Nullable String> convertRawDataIntoTexts(String rawData) {
        Objects.requireNonNull(rawData);
        List<@Nullable String> texts = new ArrayList<>(fieldSpecs.size());
        int beginIndex;
        int endIndex;
        int dataLength = Math.min(rawData.length(), fileSpec.recordWidth());
        for (FixedWidthFieldSpec fieldSpec : fieldSpecs) {
            String text = NO_TEXT;

            beginIndex = Math.max(fieldSpec.startIndex(), 0);
            endIndex = Math.min(fieldSpec.startIndex() + fieldSpec.width(), dataLength);
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
            super(reader, fileSpec.producerIgnoreFirstRecords(), fileSpec.producerIgnoreLastRecords());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            String rawData;
            if (fileSpec.separateRecordsByLineSeparator()) {
                rawData = reader.readLine();
            } else {
                char[] c = new char[fileSpec.recordWidth()];
                int r = reader.read(c);
                if (r < 0) {
                    // end of stream reached
                    rawData = null;
                } else {
                    rawData = String.valueOf(c);
                }
            }
            return RecordRawData.buildOptionalRecordRawData(null, recordIndex, rawData);
        }

    }

}
