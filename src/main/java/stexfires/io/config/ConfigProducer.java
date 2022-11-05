package stexfires.io.config;

import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.record.KeyValueRecord;
import stexfires.record.impl.KeyValueFieldsRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static stexfires.io.config.ConfigFileSpec.CATEGORY_BEGIN_MARKER;
import static stexfires.io.config.ConfigFileSpec.CATEGORY_END_MARKER;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConfigProducer extends AbstractReadableProducer<KeyValueRecord> {

    private final ConfigFileSpec fileSpec;

    public ConfigProducer(BufferedReader bufferedReader, ConfigFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new ConfigIterator(bufferedReader());
    }

    @Override
    protected Optional<KeyValueRecord> createRecord(RecordRawData recordRawData) {
        String rawData = recordRawData.rawData();
        String key;
        String value;
        int index = rawData.indexOf(fileSpec.valueDelimiter());
        if (index >= 0) {
            key = rawData.substring(0, index);
            value = rawData.substring(index + 1);
        } else {
            key = rawData;
            value = null;
        }
        return Optional.of(new KeyValueFieldsRecord(recordRawData.category(), recordRawData.recordId(), key, value));
    }

    private static final class ConfigIterator extends AbstractRecordRawDataIterator {

        private String currentCategory;

        private ConfigIterator(BufferedReader bufferedReader) {
            super(bufferedReader);
            currentCategory = null;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            String rawData;
            boolean categoryFound;
            do {
                rawData = reader.readLine();
                categoryFound = false;
                if ((rawData != null)
                        && rawData.startsWith(CATEGORY_BEGIN_MARKER)
                        && rawData.endsWith(CATEGORY_END_MARKER)) {
                    categoryFound = true;
                    currentCategory = rawData.substring(1, rawData.length() - 1);
                }
            } while (categoryFound);

            return RecordRawData.asOptional(currentCategory, recordIndex, rawData);
        }
    }

}
