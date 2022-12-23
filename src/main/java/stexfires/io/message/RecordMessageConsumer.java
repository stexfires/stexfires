package stexfires.io.message;

import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.util.function.StringPredicates;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordMessageConsumer extends AbstractInternalWritableConsumer<TextRecord> {

    private final RecordMessageFileSpec fileSpec;
    private final Predicate<String> notNullAndNotEmpty;

    public RecordMessageConsumer(BufferedWriter bufferedWriter, RecordMessageFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;

        notNullAndNotEmpty = StringPredicates.isNotNullAndNotEmpty();
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write text before
        if (fileSpec.consumerTextBefore() != null) {
            writeString(fileSpec.consumerTextBefore());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        String message;
        try {
            message = fileSpec.consumerRecordMessage().createMessage(record);
        } catch (NullPointerException | UnsupportedOperationException | ClassCastException | IllegalArgumentException |
                 IllegalStateException e) {
            // Catch some possible RuntimeExceptions and throw them as an UncheckedConsumerException.
            throw new UncheckedConsumerException(new ConsumerException(record, e));
        }

        if (!fileSpec.consumerSkipNullOrEmptyMessages() || notNullAndNotEmpty.test(message)) {
            if (message != null) {
                writeString(message);
            }
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeAfter();

        // write text after
        if (fileSpec.consumerTextAfter() != null) {
            writeString(fileSpec.consumerTextAfter());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

}
