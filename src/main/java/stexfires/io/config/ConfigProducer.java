package stexfires.io.config;

import stexfires.record.impl.KeyValueRecord;
import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConfigProducer extends AbstractReadableProducer<KeyValueRecord> {

    protected final ConfigFileSpec fileSpec;

    public ConfigProducer(BufferedReader reader, ConfigFileSpec fileSpec) {
        super(reader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new ConfigIterator(reader);
    }

    @Override
    protected Optional<KeyValueRecord> createRecord(RecordRawData recordRawData) {
        String rawData = recordRawData.getRawData();
        String key;
        String value;
        int index = rawData.indexOf(fileSpec.getValueDelimiter());
        if (index >= 0) {
            key = rawData.substring(0, index);
            value = rawData.substring(index + 1);
        } else {
            key = rawData;
            value = null;
        }
        return Optional.of(new KeyValueRecord(recordRawData.getCategory(), recordRawData.getRecordId(), key, value));
    }

    protected static final class ConfigIterator extends AbstractRecordRawDataIterator {

        private String currentCategory;

        public ConfigIterator(BufferedReader bufferedReader) {
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
                        && rawData.startsWith(ConfigFileSpec.CATEGORY_PREFIX)
                        && rawData.endsWith(ConfigFileSpec.CATEGORY_POSTFIX)) {
                    categoryFound = true;
                    currentCategory = rawData.substring(1, rawData.length() - 1);
                }
            } while (categoryFound);

            if (rawData == null) {
                return Optional.empty();
            }
            return Optional.of(new RecordRawData(currentCategory, recordIndex, rawData));
        }
    }

}
