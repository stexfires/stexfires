package stexfires.io.config;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.KeyValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

import static stexfires.io.config.ConfigFileSpec.CATEGORY_BEGIN_MARKER;
import static stexfires.io.config.ConfigFileSpec.CATEGORY_END_MARKER;
import static stexfires.io.config.ConfigFileSpec.NULL_CATEGORY;
import static stexfires.io.config.ConfigFileSpec.NULL_VALUE;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConfigConsumer extends AbstractWritableConsumer<KeyValueRecord> {

    private final ConfigFileSpec fileSpec;

    private String currentCategory;

    public ConfigConsumer(BufferedWriter bufferedWriter, ConfigFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        currentCategory = null;
    }

    @Override
    public void writeRecord(KeyValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        if (!Objects.equals(currentCategory, record.category())) {
            currentCategory = record.category();
            writeString(CATEGORY_BEGIN_MARKER);
            writeString(Objects.requireNonNullElse(currentCategory, NULL_CATEGORY));
            writeString(CATEGORY_END_MARKER);
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }

        writeString(record.key());
        writeString(fileSpec.valueDelimiter());
        writeString(record.valueField().orElse(NULL_VALUE));
        writeLineSeparator(fileSpec.consumerLineSeparator());
    }

}
