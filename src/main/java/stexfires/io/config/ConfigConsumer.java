package stexfires.io.config;

import stexfires.core.consumer.ConsumerException;
import stexfires.core.consumer.UncheckedConsumerException;
import stexfires.core.record.KeyValueRecord;
import stexfires.io.internal.AbstractWritableConsumer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

import static stexfires.io.config.ConfigFileSpec.CATEGORY_POSTFIX;
import static stexfires.io.config.ConfigFileSpec.CATEGORY_PREFIX;
import static stexfires.io.config.ConfigFileSpec.NULL_CATEGORY;
import static stexfires.io.config.ConfigFileSpec.NULL_VALUE;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConfigConsumer extends AbstractWritableConsumer<KeyValueRecord> {

    protected final ConfigFileSpec fileSpec;

    private String currentCategory;

    public ConfigConsumer(BufferedWriter writer, ConfigFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();

        currentCategory = null;
    }

    @Override
    public void writeRecord(KeyValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        if (!Objects.equals(currentCategory, record.getCategory())) {
            currentCategory = record.getCategory();
            writeString(CATEGORY_PREFIX);
            writeString(Objects.requireNonNullElse(currentCategory, NULL_CATEGORY));
            writeString(CATEGORY_POSTFIX);
            writeLineSeparator(fileSpec.getLineSeparator());
        }

        writeString(record.getValueOfKeyField());
        writeString(fileSpec.getValueDelimiter());
        writeString(record.getValueField().valueOrElse(NULL_VALUE));
        writeLineSeparator(fileSpec.getLineSeparator());
    }

}
