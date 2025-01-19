package stexfires.io.config;

import org.jspecify.annotations.Nullable;
import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.KeyValueCommentRecord;
import stexfires.record.TextField;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

import static stexfires.io.config.ConfigFileSpec.*;

/**
 * @since 0.1
 */
public final class ConfigConsumer extends AbstractInternalWritableConsumer<KeyValueCommentRecord> {

    private final ConfigFileSpec fileSpec;

    private boolean categoryFound;
    private @Nullable String currentCategory;

    public ConfigConsumer(BufferedWriter bufferedWriter, ConfigFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write comment lines before the records
        if (fileSpec.consumerCommentLinesBefore() != null) {
            fileSpec.consumerCommentLinesBefore().lines().forEachOrdered(this::writeComment);
        }

        // init
        categoryFound = false;
        currentCategory = null;
    }

    @Override
    public void writeRecord(KeyValueCommentRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        if (!Objects.equals(currentCategory, record.category())) {
            if (categoryFound) {
                // Separate categories
                if (fileSpec.consumerSeparateCategoriesByLine()) {
                    writeLineSeparator(fileSpec.consumerLineSeparator());
                }
            } else {
                categoryFound = true;
            }
            currentCategory = record.category();
            writeCategory(currentCategory);
        }

        writeComment(record.comment());
        writeKeyValue(record.keyField(), record.valueField());
    }

    private void writeCategory(@Nullable String category) throws IOException {
        writeString(CATEGORY_BEGIN_MARKER);
        writeString(Objects.requireNonNullElse(category, NULL_CATEGORY));
        writeString(CATEGORY_END_MARKER);
        writeLineSeparator(fileSpec.consumerLineSeparator());
    }

    private void writeComment(@Nullable String comment) throws UncheckedConsumerException {
        try {
            if (comment != null) {
                writeString(fileSpec.commentLinePrefix());
                if (fileSpec.consumerSeparateByWhitespace()) {
                    writeString(WHITESPACE_SEPARATOR);
                }
                writeString(comment);
                writeLineSeparator(fileSpec.consumerLineSeparator());
            }
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
    }

    private void writeKeyValue(TextField keyField, TextField valueField) throws IOException {
        Objects.requireNonNull(keyField);
        Objects.requireNonNull(valueField);

        // Key should never be null/empty.
        writeString(keyField.asOptional().orElseThrow());
        if (fileSpec.consumerSeparateByWhitespace()) {
            writeString(WHITESPACE_SEPARATOR);
        }
        writeString(fileSpec.valueDelimiter());
        if (fileSpec.consumerSeparateByWhitespace()) {
            writeString(WHITESPACE_SEPARATOR);
        }
        writeString(valueField.orElse(NULL_VALUE));
        writeLineSeparator(fileSpec.consumerLineSeparator());
    }

}
