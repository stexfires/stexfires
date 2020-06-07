package stexfires.io.config;

import stexfires.core.producer.ProducerException;
import stexfires.core.producer.UncheckedProducerException;
import stexfires.core.record.KeyValueRecord;
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
    protected AbstractRecordRawDataIterator createIterator() throws UncheckedProducerException {
        return new ConfigIterator(reader, fileSpec);
    }

    @Override
    protected Optional<KeyValueRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
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

        @SuppressWarnings("FieldCanBeLocal")
        private final ConfigFileSpec fileSpec;

        private String currentCategory;

        public ConfigIterator(BufferedReader reader, ConfigFileSpec fileSpec) {
            super(reader);
            Objects.requireNonNull(fileSpec);
            this.fileSpec = fileSpec;
            currentCategory = null;
        }

        @Override
        protected RecordRawData readNext(BufferedReader reader, long recordIndex) throws ProducerException, IOException {
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
                return null;
            }

            return new RecordRawData(currentCategory, recordIndex, rawData);
        }
    }

}
